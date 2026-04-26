package bussines.managers;

import bussines.objects.Player;
import persistance.PlayerDao;
import shared.PasswordHasher;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestor simplificado de jugadores conectado a MySQL.
 * Mantiene login/registro/perfil/logout y aplica SHA-256 a las contraseñas.
 */
public class PlayerManager {
    private final PlayerDao playerDataAccessObjectFieldReference;
    public static String currentIdentifierFieldReference;

    public PlayerManager() {
        this.playerDataAccessObjectFieldReference = new PlayerDao();
    }

    public boolean isAdmin(String identifierParameterValue, String userPasswordParameterValue) {
        return false;
    }

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

    public boolean deletePlayer(String nationalIdentityDocumentParameterValue) {
        return playerDataAccessObjectFieldReference.deletePlayer(nationalIdentityDocumentParameterValue);
    }

    public Player getCurrentPlayer() {
        if (currentIdentifierFieldReference == null) {
            return null;
        }
        return playerDataAccessObjectFieldReference.findPlayerByIdentifier(currentIdentifierFieldReference);
    }

    public boolean updatePassword(String identifierParameterValue, String newUserPasswordParameterValue) {
        String hashedPasswordLocalVariableValue =
                PasswordHasher.hashPassword(newUserPasswordParameterValue);
        return playerDataAccessObjectFieldReference.updatePassword(identifierParameterValue, hashedPasswordLocalVariableValue);
    }

    public List<String[]> getLiveMatches() {
        return new ArrayList<>();
    }

    public boolean deleteCurrentPlayer() {
        Player currentPlayerProfileLocalVariableValue = getCurrentPlayer();
        if (currentPlayerProfileLocalVariableValue == null) {
            return false;
        }

        boolean removedLocalVariableValue =
                playerDataAccessObjectFieldReference.deletePlayer(currentPlayerProfileLocalVariableValue.getDniPlayer());

        if (removedLocalVariableValue) {
            currentIdentifierFieldReference = null;
        }
        return removedLocalVariableValue;
    }

    public String deletePlayers(ArrayList<Player> selectedPlayersParameterValue) {
        StringBuilder resultLocalVariableValue = new StringBuilder("Deleted:\n");
        for (Player playerProfileLocalVariableValue : selectedPlayersParameterValue) {
            boolean deletedLocalVariableValue =
                    playerDataAccessObjectFieldReference.deletePlayer(playerProfileLocalVariableValue.getDniPlayer());

            if (deletedLocalVariableValue) {
                resultLocalVariableValue.append("- ").append(playerProfileLocalVariableValue.getNamePlayer()).append("\n");
            } else {
                resultLocalVariableValue.append("- Failed to delete: ")
                        .append(playerProfileLocalVariableValue.getNamePlayer()).append("\n");
            }
        }
        return resultLocalVariableValue.toString();
    }

    public boolean registerPlayer(String nationalIdentityDocumentParameterValue,
                                  String displayNameParameterValue,
                                  String emailAddressParameterValue,
                                  String userPasswordParameterValue,
                                  String jerseyNumberParameterValue,
                                  String teamReferenceParameterValue,
                                  String phoneNumberParameterValue) {

        if (playerDataAccessObjectFieldReference.existsByDniOrEmail(
                nationalIdentityDocumentParameterValue, emailAddressParameterValue)) {
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

    public ArrayList<Player> getPlayers() {
        return playerDataAccessObjectFieldReference.getAllPlayers();
    }

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

    public int getNumberOfPlayersByTeamName(String teamReferenceDisplayNameParameterValue) {
        return getPlayersByTeam(teamReferenceDisplayNameParameterValue).size();
    }

    public String getCurrentIdentifier() {
        return currentIdentifierFieldReference;
    }

    public void setCurrentIdentifier(String currentIdentifierParameterValue) {
        currentIdentifierFieldReference = currentIdentifierParameterValue;
    }

    public void logoutCurrentUser() {
        currentIdentifierFieldReference = null;
    }
}
