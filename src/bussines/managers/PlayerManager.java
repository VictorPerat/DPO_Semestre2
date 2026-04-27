package bussines.managers;

import bussines.objects.Player;
import persistance.PlayerDao;
import shared.PasswordHasher;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se encarga de gestionar lo relacionado con los jugadores.
 */
public class PlayerManager {

    // DAO que se usa para acceder a los datos de los jugadores
    private final PlayerDao playerDataAccessObjectFieldReference;

    // Guarda el identificador del usuario que ha iniciado sesión
    public static String currentIdentifierFieldReference;

    // Constructor que inicializa el DAO de jugadores
    public PlayerManager() {
        this.playerDataAccessObjectFieldReference = new PlayerDao();
    }

    // Comprueba si el usuario es el administrador
    public boolean isAdmin(String identifierParameterValue, String userPasswordParameterValue) {
        return false;
    }

    // Busca un jugador y comprueba si las credenciales son correctas
    public boolean searchPlayer(String userIdentifierParameterValue, String userPasswordParameterValue) {
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

    // Comprueba si la contraseña introducida coincide con la guardada
    public boolean verifyPassword(String userIdentifierParameterValue, String userPasswordParameterValue) {
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

    // Devuelve el jugador que tiene la sesión iniciada
    public Player getCurrentPlayer() {
        if (currentIdentifierFieldReference == null) {
            return null;
        }
        return playerDataAccessObjectFieldReference.findPlayerByIdentifier(currentIdentifierFieldReference);
    }

    // Actualiza la contraseña de un usuario
    public boolean updatePassword(String identifierParameterValue, String newUserPasswordParameterValue) {
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

    // Borra la cuenta del usuario que tiene la sesión iniciada
    public boolean deleteCurrentPlayer() {
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