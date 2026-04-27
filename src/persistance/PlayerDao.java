package persistance;

import bussines.objects.Player;

import java.sql.*;
import java.util.ArrayList;

/**
 * Esta clase se encarga de acceder a los datos de los jugadores en la base de datos.
 */
public class PlayerDao {

    // Devuelve todos los jugadores guardados
    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> playersLocalVariableValue = new ArrayList<>();

        String queryLocalVariableValue =
                "SELECT p.id, p.name, p.email, p.dni, p.password, p.dorsal, p.phone, " +
                        "COALESCE(MIN(t.name), '') AS team_name " +
                        "FROM players p " +
                        "LEFT JOIN player_teams pt ON pt.player_id = p.id " +
                        "LEFT JOIN teams t ON t.id = pt.team_id " +
                        "GROUP BY p.id, p.name, p.email, p.dni, p.password, p.dorsal, p.phone";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().getConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue);
             ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {

            while (resultLocalVariableValue.next()) {
                playersLocalVariableValue.add(mapPlayer(resultLocalVariableValue));
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            eventArgumentExceptionParameter.printStackTrace();
        }

        return playersLocalVariableValue;
    }

    // Busca un jugador usando su DNI o su email
    public Player findPlayerByIdentifier(String identifierParameterValue) {
        String queryLocalVariableValue =
                "SELECT p.id, p.name, p.email, p.dni, p.password, p.dorsal, p.phone, " +
                        "COALESCE(MIN(t.name), '') AS team_name " +
                        "FROM players p " +
                        "LEFT JOIN player_teams pt ON pt.player_id = p.id " +
                        "LEFT JOIN teams t ON t.id = pt.team_id " +
                        "WHERE p.dni = ? OR p.email = ? " +
                        "GROUP BY p.id, p.name, p.email, p.dni, p.password, p.dorsal, p.phone";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().getConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setString(1, identifierParameterValue);
            preparedStatementLocalVariableValue.setString(2, identifierParameterValue);

            try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                if (resultLocalVariableValue.next()) {
                    return mapPlayer(resultLocalVariableValue);
                }
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            eventArgumentExceptionParameter.printStackTrace();
        }

        return null;
    }

    // Comprueba si ya existe un jugador con ese DNI o email
    public boolean existsByDniOrEmail(String nationalIdentityDocumentParameterValue, String emailAddressParameterValue) {
        String queryLocalVariableValue = "SELECT COUNT(*) FROM players WHERE dni = ? OR email = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().getConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setString(1, nationalIdentityDocumentParameterValue);
            preparedStatementLocalVariableValue.setString(2, emailAddressParameterValue);

            try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                if (resultLocalVariableValue.next()) {
                    return resultLocalVariableValue.getInt(1) > 0;
                }
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            eventArgumentExceptionParameter.printStackTrace();
        }

        return false;
    }

    // Borra un jugador usando su DNI
    public boolean deletePlayer(String nationalIdentityDocumentParameterValue) {
        String queryLocalVariableValue = "DELETE FROM players WHERE dni = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().getConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setString(1, nationalIdentityDocumentParameterValue);
            return preparedStatementLocalVariableValue.executeUpdate() > 0;

        } catch (SQLException eventArgumentExceptionParameter) {
            eventArgumentExceptionParameter.printStackTrace();
            return false;
        }
    }

    // Actualiza la contraseña de un jugador
    public boolean updatePassword(String identifierParameterValue, String newUserPasswordParameterValue) {
        String queryLocalVariableValue =
                "UPDATE players SET password = ? WHERE dni = ? OR email = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().getConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setString(1, newUserPasswordParameterValue);
            preparedStatementLocalVariableValue.setString(2, identifierParameterValue);
            preparedStatementLocalVariableValue.setString(3, identifierParameterValue);

            return preparedStatementLocalVariableValue.executeUpdate() > 0;

        } catch (SQLException eventArgumentExceptionParameter) {
            eventArgumentExceptionParameter.printStackTrace();
            return false;
        }
    }

    // Inserta un jugador nuevo en la base de datos
    public boolean insertPlayer(Player playerProfileParameterValue) {
        String searchTeamQueryLocalVariableValue = "SELECT id FROM teams WHERE name = ?";
        String insertTeamQueryLocalVariableValue = "INSERT INTO teams (name) VALUES (?)";
        String insertPlayerQueryLocalVariableValue =
                "INSERT INTO players (dni, name, email, password, dorsal, phone) VALUES (?, ?, ?, ?, ?, ?)";
        String insertRelationQueryLocalVariableValue =
                "INSERT INTO player_teams (player_id, team_id) VALUES (?, ?)";

        Connection connectionLocalVariableValue = null;

        try {
            connectionLocalVariableValue =
                    DatabaseConnector.getInstance().getConnection();
            connectionLocalVariableValue.setAutoCommit(false);

            int teamIdLocalVariableValue = -1;

            // Busca si el equipo ya existe
            try (PreparedStatement searchTeamStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(searchTeamQueryLocalVariableValue)) {

                searchTeamStatementLocalVariableValue.setString(1, playerProfileParameterValue.getTeamId());

                try (ResultSet resultLocalVariableValue = searchTeamStatementLocalVariableValue.executeQuery()) {
                    if (resultLocalVariableValue.next()) {
                        teamIdLocalVariableValue = resultLocalVariableValue.getInt("id");
                    }
                }
            }

            // Si el equipo no existe, lo crea
            if (teamIdLocalVariableValue == -1) {
                try (PreparedStatement insertTeamStatementLocalVariableValue =
                             connectionLocalVariableValue.prepareStatement(
                                     insertTeamQueryLocalVariableValue,
                                     Statement.RETURN_GENERATED_KEYS
                             )) {

                    insertTeamStatementLocalVariableValue.setString(1, playerProfileParameterValue.getTeamId());
                    insertTeamStatementLocalVariableValue.executeUpdate();

                    try (ResultSet generatedKeysLocalVariableValue =
                                 insertTeamStatementLocalVariableValue.getGeneratedKeys()) {
                        if (generatedKeysLocalVariableValue.next()) {
                            teamIdLocalVariableValue = generatedKeysLocalVariableValue.getInt(1);
                        }
                    }
                }
            }

            int playerIdLocalVariableValue;

            // Inserta el jugador
            try (PreparedStatement insertPlayerStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(
                                 insertPlayerQueryLocalVariableValue,
                                 Statement.RETURN_GENERATED_KEYS
                         )) {

                insertPlayerStatementLocalVariableValue.setString(1, playerProfileParameterValue.getDniPlayer());
                insertPlayerStatementLocalVariableValue.setString(2, playerProfileParameterValue.getNamePlayer());
                insertPlayerStatementLocalVariableValue.setString(3, playerProfileParameterValue.getMail());
                insertPlayerStatementLocalVariableValue.setString(4, playerProfileParameterValue.getPassword());
                insertPlayerStatementLocalVariableValue.setInt(5, playerProfileParameterValue.getDorsal());
                insertPlayerStatementLocalVariableValue.setString(6, String.valueOf(playerProfileParameterValue.getPhoneNumber()));

                insertPlayerStatementLocalVariableValue.executeUpdate();

                try (ResultSet generatedKeysLocalVariableValue = insertPlayerStatementLocalVariableValue.getGeneratedKeys()) {
                    if (!generatedKeysLocalVariableValue.next()) {
                        throw new SQLException("No se pudo recuperar el id del jugador insertado.");
                    }
                    playerIdLocalVariableValue = generatedKeysLocalVariableValue.getInt(1);
                }
            }

            // Crea la relación entre el jugador y el equipo
            try (PreparedStatement insertRelationStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(insertRelationQueryLocalVariableValue)) {

                insertRelationStatementLocalVariableValue.setInt(1, playerIdLocalVariableValue);
                insertRelationStatementLocalVariableValue.setInt(2, teamIdLocalVariableValue);
                insertRelationStatementLocalVariableValue.executeUpdate();
            }

            connectionLocalVariableValue.commit();
            connectionLocalVariableValue.setAutoCommit(true);
            return true;

        } catch (SQLException eventArgumentExceptionParameter) {
            eventArgumentExceptionParameter.printStackTrace();

            try {
                if (connectionLocalVariableValue != null) {
                    connectionLocalVariableValue.rollback();
                    connectionLocalVariableValue.setAutoCommit(true);
                }
            } catch (SQLException ignoredParameterValue) {
            }

            return false;
        }
    }

    // Convierte una fila del ResultSet en un objeto Player
    private Player mapPlayer(ResultSet resultLocalVariableValue) throws SQLException {
        String displayNameLocalVariableValue = resultLocalVariableValue.getString("name");
        String emailAddressLocalVariableValue = resultLocalVariableValue.getString("email");
        String nationalIdentityDocumentLocalVariableValue = resultLocalVariableValue.getString("dni");
        String teamNameLocalVariableValue = resultLocalVariableValue.getString("team_name");
        int jerseyNumberLocalVariableValue = resultLocalVariableValue.getInt("dorsal");
        String passwordLocalVariableValue = resultLocalVariableValue.getString("password");
        String phoneRawLocalVariableValue = resultLocalVariableValue.getString("phone");

        int phoneNumberLocalVariableValue = 0;

        // Limpia el teléfono para quedarse solo con números
        if (phoneRawLocalVariableValue != null) {
            String digitsLocalVariableValue = phoneRawLocalVariableValue.replaceAll("\\D", "");

            if (!digitsLocalVariableValue.isEmpty()) {
                if (digitsLocalVariableValue.length() > 9) {
                    digitsLocalVariableValue =
                            digitsLocalVariableValue.substring(digitsLocalVariableValue.length() - 9);
                }

                phoneNumberLocalVariableValue = Integer.parseInt(digitsLocalVariableValue);
            }
        }

        return new Player(
                displayNameLocalVariableValue,
                emailAddressLocalVariableValue,
                nationalIdentityDocumentLocalVariableValue,
                teamNameLocalVariableValue,
                jerseyNumberLocalVariableValue,
                passwordLocalVariableValue,
                phoneNumberLocalVariableValue
        );
    }
}