package bussines.managers;

import bussines.objects.Player;
import persistance.PlayerDao;
import shared.PasswordHasher;

import java.util.ArrayList;
import java.util.List;

/*
 * Identificador reservado para el usuario administrador. Según el enunciado
 * (apartado 2.2), el admin se autentica con el literal "admin" y la
 * contraseña que esté escrita en config.json, no contra la base de datos.
 */

/**
 * Esta clase se encarga de gestionar lo relacionado con los jugadores.
 */
public class PlayerManager {

    // Identificador reservado para el usuario administrador.
    public static final String ADMIN_IDENTIFIER = "admin";

    // DAO que se usa para acceder a los datos de los jugadores
    private final PlayerDao playerDataAccessObjectFieldReference;

    // Guarda el identificador del usuario que ha iniciado sesión
    public static String currentIdentifierFieldReference;

    // Constructor que inicializa el DAO de jugadores
    public PlayerManager() {
        this.playerDataAccessObjectFieldReference = new PlayerDao();
    }

    /**
     * Comprueba si las credenciales corresponden al administrador.
     *
     * Según el apartado 2.2 del enunciado, el administrador se autentica
     * con el literal "admin" y la contraseña almacenada en config.json,
     * sin pasar por la base de datos.
     */
    public boolean isAdmin(String identifierParameterValue, String userPasswordParameterValue) {
        if (identifierParameterValue == null || userPasswordParameterValue == null) {
            return false;
        }

        if (!ADMIN_IDENTIFIER.equalsIgnoreCase(identifierParameterValue)) {
            return false;
        }

        String adminPasswordFromConfigLocalVariableValue;
        try {
            adminPasswordFromConfigLocalVariableValue = ConfigManager.getAdminPassword();
        } catch (RuntimeException eventArgumentExceptionParameterValue) {
            return false;
        }

        return userPasswordParameterValue.equals(adminPasswordFromConfigLocalVariableValue);
    }

    /**
     * Busca un jugador y comprueba si las credenciales son correctas.
     *
     * Si las credenciales coinciden con el administrador (definido en
     * config.json), se autentica como admin sin consultar la base de
     * datos. En cualquier otro caso, se delega en el DAO de jugadores.
     */
    public boolean searchPlayer(String userIdentifierParameterValue, String userPasswordParameterValue) {
        // Caso especial: login del administrador contra config.json.
        if (isAdmin(userIdentifierParameterValue, userPasswordParameterValue)) {
            currentIdentifierFieldReference = ADMIN_IDENTIFIER;
            return true;
        }

        Player playerProfileLocalVariableValue =
                playerDataAccessObjectFieldReference.findPlayerByIdentifier(userIdentifierParameterValue);

        if (playerProfileLocalVariableValue == null) {
            return false;
        }

        if (!verifyPassword(userIdentifierParameterValue, userPasswordParameterValue)) {
            return false;
        }

        currentIdentifierFieldReference = userIdentifierParameterValue;
        return true;
    }

    /**
     * Comprueba si existe un jugador con el identificador indicado, sin
     * validar contraseña. El literal "admin" se considera siempre
     * existente, ya que su credencial vive en config.json y no en la
     * base de datos.
     */
    public boolean playerExists(String userIdentifierParameterValue) {
        if (userIdentifierParameterValue == null) {
            return false;
        }
        if (ADMIN_IDENTIFIER.equalsIgnoreCase(userIdentifierParameterValue)) {
            return true;
        }
        return playerDataAccessObjectFieldReference.findPlayerByIdentifier(userIdentifierParameterValue) != null;
    }

    /**
     * Comprueba si la contraseña introducida coincide con la almacenada.
     *
     * Para el administrador, la fuente de la contraseña es config.json
     * (no la base de datos) y se compara en texto plano. Para el resto
     * de jugadores, se compara el hash almacenado en BD.
     */
    public boolean verifyPassword(String userIdentifierParameterValue, String userPasswordParameterValue) {
        if (userIdentifierParameterValue == null || userPasswordParameterValue == null) {
            return false;
        }

        // El administrador se valida contra config.json, no contra BD.
        if (ADMIN_IDENTIFIER.equalsIgnoreCase(userIdentifierParameterValue)) {
            return userPasswordParameterValue.equals(ConfigManager.getAdminPassword());
        }

        Player playerProfileLocalVariableValue =
                playerDataAccessObjectFieldReference.findPlayerByIdentifier(userIdentifierParameterValue);

        if (playerProfileLocalVariableValue == null) {
            return false;
        }

        String hashedPasswordLocalVariableValue =
                PasswordHasher.hashPassword(userPasswordParameterValue);

        return hashedPasswordLocalVariableValue.equals(playerProfileLocalVariableValue.getPassword());
    }

    // Borra un jugador usando su DNI
    public boolean deletePlayer(String nationalIdentityDocumentParameterValue) {
        return playerDataAccessObjectFieldReference.deletePlayer(nationalIdentityDocumentParameterValue);
    }

    // Devuelve el jugador que tiene la sesión iniciada.
    // El administrador no es un jugador real, así que devuelve null.
    public Player getCurrentPlayer() {
        if (currentIdentifierFieldReference == null) {
            return null;
        }
        if (ADMIN_IDENTIFIER.equalsIgnoreCase(currentIdentifierFieldReference)) {
            return null;
        }
        return playerDataAccessObjectFieldReference.findPlayerByIdentifier(currentIdentifierFieldReference);
    }

    /**
     * Actualiza la contraseña de un usuario.
     *
     * El administrador no puede cambiar su contraseña desde la app: su
     * contraseña vive en config.json (apartado 2.1 del enunciado) y
     * cualquier intento de actualizarla a través de la BD se rechaza.
     */
    public boolean updatePassword(String identifierParameterValue, String newUserPasswordParameterValue) {
        if (ADMIN_IDENTIFIER.equalsIgnoreCase(identifierParameterValue)) {
            return false;
        }

        String hashedPasswordLocalVariableValue =
                PasswordHasher.hashPassword(newUserPasswordParameterValue);

        return playerDataAccessObjectFieldReference.updatePassword(
                identifierParameterValue,
                hashedPasswordLocalVariableValue
        );
    }

    // Devuelve los partidos en directo
    public List<String[]> getLiveMatches() {
        return new ArrayList<>();
    }

    /**
     * Borra la cuenta del usuario que tiene la sesión iniciada.
     *
     * El administrador es una excepción del derecho al olvido (RGPD)
     * según el apartado 2.3 del enunciado y nunca puede eliminar su
     * propia cuenta.
     */
    public boolean deleteCurrentPlayer() {
        if (ADMIN_IDENTIFIER.equalsIgnoreCase(currentIdentifierFieldReference)) {
            return false;
        }

        Player currentPlayerProfileLocalVariableValue = getCurrentPlayer();

        if (currentPlayerProfileLocalVariableValue == null) {
            return false;
        }

        boolean removedLocalVariableValue =
                playerDataAccessObjectFieldReference.deletePlayer(
                        currentPlayerProfileLocalVariableValue.getDniPlayer()
                );

        if (removedLocalVariableValue) {
            currentIdentifierFieldReference = null;
        }

        return removedLocalVariableValue;
    }

    // Borra varios jugadores y devuelve un mensaje con el resultado
    public String deletePlayers(ArrayList<Player> selectedPlayersParameterValue) {
        StringBuilder resultLocalVariableValue = new StringBuilder("Deleted:\n");

        for (Player playerProfileLocalVariableValue : selectedPlayersParameterValue) {
            boolean deletedLocalVariableValue =
                    playerDataAccessObjectFieldReference.deletePlayer(
                            playerProfileLocalVariableValue.getDniPlayer()
                    );

            if (deletedLocalVariableValue) {
                resultLocalVariableValue.append("- ")
                        .append(playerProfileLocalVariableValue.getNamePlayer())
                        .append("\n");
            } else {
                resultLocalVariableValue.append("- Failed to delete: ")
                        .append(playerProfileLocalVariableValue.getNamePlayer())
                        .append("\n");
            }
        }

        return resultLocalVariableValue.toString();
    }

    // Registra un nuevo jugador en el sistema
    public boolean registerPlayer(String nationalIdentityDocumentParameterValue,
                                  String displayNameParameterValue,
                                  String emailAddressParameterValue,
                                  String userPasswordParameterValue,
                                  String jerseyNumberParameterValue,
                                  String teamReferenceParameterValue,
                                  String phoneNumberParameterValue) {

        // Comprueba que no exista ya un jugador con el mismo DNI o email
        if (playerDataAccessObjectFieldReference.existsByDniOrEmail(
                nationalIdentityDocumentParameterValue,
                emailAddressParameterValue)) {
            return false;
        }

        int jerseyNumberIntLocalVariableValue;
        int phoneNumberIntLocalVariableValue;

        try {
            jerseyNumberIntLocalVariableValue = Integer.parseInt(jerseyNumberParameterValue);
            phoneNumberIntLocalVariableValue = Integer.parseInt(phoneNumberParameterValue);
        } catch (NumberFormatException eventArgumentExceptionParameterValue) {
            return false;
        }

        Player newPlayerProfileLocalVariableValue = new Player(
                displayNameParameterValue,
                emailAddressParameterValue,
                nationalIdentityDocumentParameterValue,
                teamReferenceParameterValue,
                jerseyNumberIntLocalVariableValue,
                PasswordHasher.hashPassword(userPasswordParameterValue),
                phoneNumberIntLocalVariableValue
        );

        return playerDataAccessObjectFieldReference.insertPlayer(newPlayerProfileLocalVariableValue);
    }

    // Devuelve todos los jugadores
    public ArrayList<Player> getPlayers() {
        return playerDataAccessObjectFieldReference.getAllPlayers();
    }

    // Devuelve los jugadores de un equipo concreto
    public ArrayList<Player> getPlayersByTeam(String teamReferenceDisplayNameParameterValue) {
        ArrayList<Player> allPlayersLocalVariableValue =
                playerDataAccessObjectFieldReference.getAllPlayers();

        ArrayList<Player> teamPlayersLocalVariableValue = new ArrayList<>();

        for (Player playerProfileLocalVariableValue : allPlayersLocalVariableValue) {
            if (playerProfileLocalVariableValue.getTeam().equalsIgnoreCase(teamReferenceDisplayNameParameterValue)) {
                teamPlayersLocalVariableValue.add(playerProfileLocalVariableValue);
            }
        }

        return teamPlayersLocalVariableValue;
    }

    // Devuelve cuántos jugadores tiene un equipo
    public int getNumberOfPlayersByTeamName(String teamReferenceDisplayNameParameterValue) {
        return getPlayersByTeam(teamReferenceDisplayNameParameterValue).size();
    }

    // Devuelve el identificador del usuario actual
    public String getCurrentIdentifier() {
        return currentIdentifierFieldReference;
    }

    // Guarda el identificador del usuario actual
    public void setCurrentIdentifier(String currentIdentifierParameterValue) {
        currentIdentifierFieldReference = currentIdentifierParameterValue;
    }

    // Cierra la sesión del usuario actual
    public void logoutCurrentUser() {
        currentIdentifierFieldReference = null;
    }
}