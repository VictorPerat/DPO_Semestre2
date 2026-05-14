package persistance;

import bussines.objects.Team;
import shared.DaoErrorHandler;

import java.sql.*;
import java.util.ArrayList;


/**
 * Gestiona el acceso a datos del equipo.
 */
public class TeamDao {


    /**
     * Devuelve los equipos.
     *
     * @return los equipos.
     */
    public ArrayList<Team> getAllTeams() {
        ArrayList<Team> teamsLocalVariableValue = new ArrayList<>();
        String queryLocalVariableValue = "SELECT id, name FROM teams ORDER BY name";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue);
             ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {

            while (resultLocalVariableValue.next()) {
                teamsLocalVariableValue.add(new Team(
                        resultLocalVariableValue.getInt("id"),
                        resultLocalVariableValue.getString("name")
                ));
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("TeamDao", eventArgumentExceptionParameter);
        }

        return teamsLocalVariableValue;
    }


    /**
     * Elimina el equipo nombre.
     *
     * @param displayNameParameterValue nombre que se muestra.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean deleteTeamByName(String displayNameParameterValue) {
        String selectTeamQueryLocalVariableValue = "SELECT id FROM teams WHERE name = ?";
        String deleteGamesQueryLocalVariableValue = "DELETE FROM games WHERE home_team_id = ? OR away_team_id = ?";
        String deleteLeagueTeamsQueryLocalVariableValue = "DELETE FROM league_teams WHERE team_id = ?";
        String deletePlayerTeamsQueryLocalVariableValue = "DELETE FROM player_teams WHERE team_id = ?";
        String deleteTeamQueryLocalVariableValue = "DELETE FROM teams WHERE id = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection()) {

            connectionLocalVariableValue.setAutoCommit(false);

            int teamReferenceIdentifierLocalVariableValue = -1;


            try (PreparedStatement preparedStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(selectTeamQueryLocalVariableValue)) {

                preparedStatementLocalVariableValue.setString(1, displayNameParameterValue);

                try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                    if (resultLocalVariableValue.next()) {
                        teamReferenceIdentifierLocalVariableValue =
                                resultLocalVariableValue.getInt("id");
                    }
                }
            }


            if (teamReferenceIdentifierLocalVariableValue == -1) {
                connectionLocalVariableValue.rollback();
                connectionLocalVariableValue.setAutoCommit(true);
                return false;
            }


            try (PreparedStatement preparedStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(deleteGamesQueryLocalVariableValue)) {

                preparedStatementLocalVariableValue.setInt(1, teamReferenceIdentifierLocalVariableValue);
                preparedStatementLocalVariableValue.setInt(2, teamReferenceIdentifierLocalVariableValue);
                preparedStatementLocalVariableValue.executeUpdate();
            }


            try (PreparedStatement preparedStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(deleteLeagueTeamsQueryLocalVariableValue)) {

                preparedStatementLocalVariableValue.setInt(1, teamReferenceIdentifierLocalVariableValue);
                preparedStatementLocalVariableValue.executeUpdate();
            }


            try (PreparedStatement preparedStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(deletePlayerTeamsQueryLocalVariableValue)) {

                preparedStatementLocalVariableValue.setInt(1, teamReferenceIdentifierLocalVariableValue);
                preparedStatementLocalVariableValue.executeUpdate();
            }

            boolean deletedLocalVariableValue;


            try (PreparedStatement preparedStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(deleteTeamQueryLocalVariableValue)) {

                preparedStatementLocalVariableValue.setInt(1, teamReferenceIdentifierLocalVariableValue);
                deletedLocalVariableValue =
                        preparedStatementLocalVariableValue.executeUpdate() > 0;
            }

            connectionLocalVariableValue.commit();
            connectionLocalVariableValue.setAutoCommit(true);
            return deletedLocalVariableValue;

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("TeamDao", eventArgumentExceptionParameter);
            return false;
        }
    }


    /**
     * Devuelve el equipo.
     *
     * @param teamReferenceDisplayNameParameterValue nombre del equipo.
     * @return el equipo.
     */
    public int getTeamId(String teamReferenceDisplayNameParameterValue) {
        String queryLocalVariableValue = "SELECT id FROM teams WHERE name = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setString(1, teamReferenceDisplayNameParameterValue);

            try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                if (resultLocalVariableValue.next()) {
                    return resultLocalVariableValue.getInt("id");
                }
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("TeamDao", eventArgumentExceptionParameter);
        }

        return -1;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param teamReferenceDisplayNameParameterValue nombre del equipo.
     * @return resultado de la operacion.
     */
    public int insertTeam(String teamReferenceDisplayNameParameterValue) {
        String queryLocalVariableValue = "INSERT INTO teams (name) VALUES (?)";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(
                             queryLocalVariableValue,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            preparedStatementLocalVariableValue.setString(1, teamReferenceDisplayNameParameterValue);
            preparedStatementLocalVariableValue.executeUpdate();

            try (ResultSet generatedKeysLocalVariableValue =
                         preparedStatementLocalVariableValue.getGeneratedKeys()) {
                if (generatedKeysLocalVariableValue.next()) {
                    return generatedKeysLocalVariableValue.getInt(1);
                }
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            System.err.println("Error inserting team: " + eventArgumentExceptionParameter.getMessage());
        }

        return -1;
    }
}