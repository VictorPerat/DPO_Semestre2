package persistance;

import bussines.objects.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Esta clase se encarga de acceder a los datos de los equipos en la base de datos.
 */
public class TeamDao {

    // Devuelve todos los equipos guardados
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
            eventArgumentExceptionParameter.printStackTrace();
        }

        return teamsLocalVariableValue;
    }

    // Devuelve los ids de los equipos que ya están asignados a alguna liga
    public Set<Integer> getAssignedTeamIds() {
        Set<Integer> assignedTeamReferenceIdentifiersLocalVariableValue = new HashSet<>();
        String queryLocalVariableValue = "SELECT team_id FROM league_teams";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue);
             ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {

            while (resultLocalVariableValue.next()) {
                assignedTeamReferenceIdentifiersLocalVariableValue.add(
                        resultLocalVariableValue.getInt("team_id")
                );
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            eventArgumentExceptionParameter.printStackTrace();
        }

        return assignedTeamReferenceIdentifiersLocalVariableValue;
    }

    // Borra un equipo usando su nombre
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

            // Busca el id del equipo
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

            // Si no encuentra el equipo, devuelve false
            if (teamReferenceIdentifierLocalVariableValue == -1) {
                connectionLocalVariableValue.rollback();
                connectionLocalVariableValue.setAutoCommit(true);
                return false;
            }

            // Borra los partidos donde aparece ese equipo
            try (PreparedStatement preparedStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(deleteGamesQueryLocalVariableValue)) {

                preparedStatementLocalVariableValue.setInt(1, teamReferenceIdentifierLocalVariableValue);
                preparedStatementLocalVariableValue.setInt(2, teamReferenceIdentifierLocalVariableValue);
                preparedStatementLocalVariableValue.executeUpdate();
            }

            // Borra la relación del equipo con las ligas
            try (PreparedStatement preparedStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(deleteLeagueTeamsQueryLocalVariableValue)) {

                preparedStatementLocalVariableValue.setInt(1, teamReferenceIdentifierLocalVariableValue);
                preparedStatementLocalVariableValue.executeUpdate();
            }

            // Borra la relación del equipo con los jugadores
            try (PreparedStatement preparedStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(deletePlayerTeamsQueryLocalVariableValue)) {

                preparedStatementLocalVariableValue.setInt(1, teamReferenceIdentifierLocalVariableValue);
                preparedStatementLocalVariableValue.executeUpdate();
            }

            boolean deletedLocalVariableValue;

            // Finalmente borra el equipo
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
            eventArgumentExceptionParameter.printStackTrace();
            return false;
        }
    }

    // Devuelve el id de un equipo a partir de su nombre
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
            eventArgumentExceptionParameter.printStackTrace();
        }

        return -1;
    }

    // Inserta un equipo nuevo y devuelve su id
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