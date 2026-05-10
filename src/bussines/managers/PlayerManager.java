package bussines.managers;

import bussines.objects.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import persistance.PlayerDao;
import shared.PasswordHasher;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


/**
 * Gestiona las operaciones del jugador.
 */
public class PlayerManager {


    private final PlayerDao playerDataAccessObjectFieldReference;


    /**
     * Guarda el identificador.
     */
    public static String currentIdentifierFieldReference;


    /**
     * Crea una instancia de el jugador.
     */
    public PlayerManager() {
        this.playerDataAccessObjectFieldReference = new PlayerDao();
    }


    /**
     * Indica el estado actual.
     *
     * @param identifierParameterValue identificador del usuario.
     * @param userPasswordParameterValue contrasena del usuario.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean isAdmin(String identifierParameterValue, String userPasswordParameterValue) {
        if (identifierParameterValue == null || userPasswordParameterValue == null) {
            return false;
        }

        if (!ConfigManager.getAdminIdentifier().equalsIgnoreCase(identifierParameterValue)) {
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
     * Busca el jugador.
     *
     * @param userIdentifierParameterValue identificador del usuario.
     * @param userPasswordParameterValue contrasena del usuario.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean searchPlayer(String userIdentifierParameterValue, String userPasswordParameterValue) {

        if (isAdmin(userIdentifierParameterValue, userPasswordParameterValue)) {
            currentIdentifierFieldReference = ConfigManager.getAdminIdentifier();
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
     * Gestiona esta operacion.
     *
     * @param userIdentifierParameterValue identificador del usuario.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean playerExists(String userIdentifierParameterValue) {
        if (userIdentifierParameterValue == null) {
            return false;
        }
        if (ConfigManager.getAdminIdentifier().equalsIgnoreCase(userIdentifierParameterValue)) {
            return true;
        }
        return playerDataAccessObjectFieldReference.findPlayerByIdentifier(userIdentifierParameterValue) != null;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param userIdentifierParameterValue identificador del usuario.
     * @param userPasswordParameterValue contrasena del usuario.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean verifyPassword(String userIdentifierParameterValue, String userPasswordParameterValue) {
        if (userIdentifierParameterValue == null || userPasswordParameterValue == null) {
            return false;
        }


        if (ConfigManager.getAdminIdentifier().equalsIgnoreCase(userIdentifierParameterValue)) {
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


    /**
     * Elimina el jugador.
     *
     * @param nationalIdentityDocumentParameterValue documento de identidad del jugador.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean deletePlayer(String nationalIdentityDocumentParameterValue) {
        return playerDataAccessObjectFieldReference.deletePlayer(nationalIdentityDocumentParameterValue);
    }


    /**
     * Devuelve el actual jugador.
     *
     * @return el actual jugador.
     */
    public Player getCurrentPlayer() {
        if (currentIdentifierFieldReference == null) {
            return null;
        }
        if (ConfigManager.getAdminIdentifier().equalsIgnoreCase(currentIdentifierFieldReference)) {
            return null;
        }
        return playerDataAccessObjectFieldReference.findPlayerByIdentifier(currentIdentifierFieldReference);
    }


    /**
     * Actualiza el contrasena.
     *
     * @param identifierParameterValue identificador del usuario.
     * @param newUserPasswordParameterValue nueva contrasena del usuario.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean updatePassword(String identifierParameterValue, String newUserPasswordParameterValue) {
        if (ConfigManager.getAdminIdentifier().equalsIgnoreCase(identifierParameterValue)) {
            return ConfigManager.updateAdminPassword(
                    newUserPasswordParameterValue
            );
        }

        String hashedPasswordLocalVariableValue =
                PasswordHasher.hashPassword(newUserPasswordParameterValue);

        return playerDataAccessObjectFieldReference.updatePassword(
                identifierParameterValue,
                hashedPasswordLocalVariableValue
        );
    }


    /**
     * Devuelve los directo partidos.
     *
     * @return los directo partidos.
     */
    public List<String[]> getLiveMatches() {
        Player currentPlayerProfileLocalVariableValue = getCurrentPlayer();

        if (currentPlayerProfileLocalVariableValue == null
                || currentPlayerProfileLocalVariableValue.getTeam() == null
                || currentPlayerProfileLocalVariableValue.getTeam().trim().isEmpty()) {
            return new ArrayList<>();
        }

        LeagueManager leagueReferenceManagerServiceLocalVariableValue = new LeagueManager();
        GameManager gameEntityManagerServiceLocalVariableValue = new GameManager();

        ArrayList<bussines.objects.League> playerLeaguesLocalVariableValue =
                leagueReferenceManagerServiceLocalVariableValue.getLeaguesByUserTeam(
                        currentPlayerProfileLocalVariableValue.getTeam()
                );

        java.util.HashSet<Integer> leagueIdsLocalVariableValue = new java.util.HashSet<>();

        for (bussines.objects.League leagueReferenceLocalVariableValue : playerLeaguesLocalVariableValue) {
            int leagueIdLocalVariableValue = leagueReferenceLocalVariableValue.getId();

            if (leagueIdLocalVariableValue <= 0) {
                leagueIdLocalVariableValue =
                        leagueReferenceManagerServiceLocalVariableValue.getLeagueIdByName(
                                leagueReferenceLocalVariableValue.getName()
                        );
            }

            if (leagueIdLocalVariableValue != -1) {
                leagueIdsLocalVariableValue.add(leagueIdLocalVariableValue);
            }
        }

        return gameEntityManagerServiceLocalVariableValue.getLiveGamesByLeagueIds(
                leagueIdsLocalVariableValue
        );
    }


    /**
     * Elimina el actual jugador.
     *
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean deleteCurrentPlayer() {
        if (ConfigManager.getAdminIdentifier().equalsIgnoreCase(currentIdentifierFieldReference)) {
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


    /**
     * Elimina los jugadores.
     *
     * @param selectedPlayersParameterValue jugadores que usa la operacion.
     * @return resultado de la operacion.
     */
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


    /**
     * Registra la accion.
     *
     * @param nationalIdentityDocumentParameterValue documento de identidad del jugador.
     * @param displayNameParameterValue nombre que se muestra.
     * @param emailAddressParameterValue direccion de email.
     * @param userPasswordParameterValue contrasena del usuario.
     * @param jerseyNumberParameterValue dorsal del jugador.
     * @param teamReferenceParameterValue equipo asociado.
     * @param phoneNumberParameterValue numero de telefono.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean registerPlayer(String nationalIdentityDocumentParameterValue,
                                  String displayNameParameterValue,
                                  String emailAddressParameterValue,
                                  String userPasswordParameterValue,
                                  String jerseyNumberParameterValue,
                                  String teamReferenceParameterValue,
                                  String phoneNumberParameterValue) {


        if (playerDataAccessObjectFieldReference.existsByDniOrEmail(
                nationalIdentityDocumentParameterValue,
                emailAddressParameterValue)) {
            return false;
        }


        if (!isValidGeneratedPassword(userPasswordParameterValue)) {
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


    /**
     * Devuelve los jugadores.
     *
     * @return los jugadores.
     */
    public ArrayList<Player> getPlayers() {
        return playerDataAccessObjectFieldReference.getAllPlayers();
    }


    /**
     * Devuelve el jugadores equipo.
     *
     * @param teamReferenceDisplayNameParameterValue nombre del equipo.
     * @return el jugadores equipo.
     */
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


    /**
     * Devuelve el numero jugadores equipo nombre.
     *
     * @param teamReferenceDisplayNameParameterValue nombre del equipo.
     * @return el numero jugadores equipo nombre.
     */
    public int getNumberOfPlayersByTeamName(String teamReferenceDisplayNameParameterValue) {
        return getPlayersByTeam(teamReferenceDisplayNameParameterValue).size();
    }


    /**
     * Devuelve el actual identificador.
     *
     * @return el actual identificador.
     */
    public String getCurrentIdentifier() {
        return currentIdentifierFieldReference;
    }


    /**
     * Actualiza el actual identificador.
     *
     * @param currentIdentifierParameterValue actual identificador.
     */
    public void setCurrentIdentifier(String currentIdentifierParameterValue) {
        currentIdentifierFieldReference = currentIdentifierParameterValue;
    }


    /**
     * Gestiona esta operacion.
     */
    public void logoutCurrentUser() {
        currentIdentifierFieldReference = null;
    }


    /**
     * Indica el estado actual.
     *
     * @param userPasswordParameterValue contrasena del usuario.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    private boolean isValidGeneratedPassword(String userPasswordParameterValue) {
        if (userPasswordParameterValue == null
                || userPasswordParameterValue.length() < 8) {
            return false;
        }
        boolean hasLowercaseLocalVariableValue =
                userPasswordParameterValue.matches(".*[a-z].*");
        boolean hasUppercaseLocalVariableValue =
                userPasswordParameterValue.matches(".*[A-Z].*");
        boolean hasDigitLocalVariableValue =
                userPasswordParameterValue.matches(".*[0-9].*");

        return hasLowercaseLocalVariableValue
                && hasUppercaseLocalVariableValue
                && hasDigitLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     */
    public void purgeUserDataFromDisk() {
        Player currentPlayerProfileLocalVariableValue = getCurrentPlayer();

        if (currentPlayerProfileLocalVariableValue == null) {
            return;
        }

        String playerDniLocalVariableValue =
                currentPlayerProfileLocalVariableValue.getDniPlayer();

        File teamsFolderLocalVariableValue = new File("data/teams");

        if (!teamsFolderLocalVariableValue.exists()
                || !teamsFolderLocalVariableValue.isDirectory()) {
            return;
        }

        File[] jsonFilesLocalVariableValue = teamsFolderLocalVariableValue
                .listFiles((dirParameterValue, nameParameterValue) ->
                        nameParameterValue.toLowerCase().endsWith(".json"));

        if (jsonFilesLocalVariableValue == null) {
            return;
        }

        for (File jsonFileLocalVariableValue : jsonFilesLocalVariableValue) {
            try {
                String contentLocalVariableValue = new String(
                        Files.readAllBytes(jsonFileLocalVariableValue.toPath()),
                        StandardCharsets.UTF_8
                );

                JSONObject teamJsonLocalVariableValue =
                        new JSONObject(contentLocalVariableValue);

                if (!teamJsonLocalVariableValue.has("players")) {
                    continue;
                }

                JSONArray playersArrayLocalVariableValue =
                        teamJsonLocalVariableValue.getJSONArray("players");

                JSONArray filteredPlayersLocalVariableValue = new JSONArray();

                for (int indexCounterLocalVariableValue = 0;
                     indexCounterLocalVariableValue < playersArrayLocalVariableValue.length();
                     indexCounterLocalVariableValue++) {

                    JSONObject playerEntryLocalVariableValue =
                            playersArrayLocalVariableValue
                                    .getJSONObject(indexCounterLocalVariableValue);

                    String entryDniLocalVariableValue =
                            playerEntryLocalVariableValue.optString("dni", "");


                    boolean isSamePlayerLocalVariableValue =
                            playerDniLocalVariableValue.equalsIgnoreCase(entryDniLocalVariableValue);

                    if (!isSamePlayerLocalVariableValue) {
                        filteredPlayersLocalVariableValue.put(playerEntryLocalVariableValue);
                    }
                }


                if (filteredPlayersLocalVariableValue.length()
                        < playersArrayLocalVariableValue.length()) {
                    teamJsonLocalVariableValue.put("players",
                            filteredPlayersLocalVariableValue);
                    Files.write(
                            jsonFileLocalVariableValue.toPath(),
                            teamJsonLocalVariableValue.toString(2)
                                    .getBytes(StandardCharsets.UTF_8)
                    );
                }

            } catch (Exception eventArgumentExceptionParameterValue) {
                System.err.println(
                        "purgeUserDataFromDisk: error procesando "
                        + jsonFileLocalVariableValue.getName()
                        + " — "
                        + eventArgumentExceptionParameterValue.getMessage()
                );
            }
        }
    }
}


