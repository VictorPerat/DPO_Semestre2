package persistance;

import bussines.objects.TeamInfo;
import shared.DaoErrorHandler;

import java.sql.*;
import java.util.ArrayList;


/**
 * Gestiona el acceso a datos del equipo.
 */
public class TeamInfoDao {


    /**
     * Crea el equipo.
     *
     * @param logicTeamParameterValue equipo que usa la operacion.
     */
    public void createInfoTeam(TeamInfo logicTeamParameterValue) {
        String queryLocalVariableValue =
                "INSERT IGNORE INTO league_teams (league_id, team_id, wins, defeats, ties, points) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setInt(1, logicTeamParameterValue.getLeagueId());
            preparedStatementLocalVariableValue.setInt(2, logicTeamParameterValue.getTeamId());
            preparedStatementLocalVariableValue.setInt(3, logicTeamParameterValue.getWins());
            preparedStatementLocalVariableValue.setInt(4, logicTeamParameterValue.getDefeats());
            preparedStatementLocalVariableValue.setInt(5, logicTeamParameterValue.getTies());
            preparedStatementLocalVariableValue.setInt(6, logicTeamParameterValue.getPoints());
            preparedStatementLocalVariableValue.executeUpdate();

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("TeamInfoDao", eventArgumentExceptionParameter);
        }
    }


    /**
     * Elimina el equipo.
     *
     * @param teamReferenceIdentifierParameterValue equipo identificador.
     */
    public void deleteInfoTeam(int teamReferenceIdentifierParameterValue) {
        String queryLocalVariableValue = "DELETE FROM league_teams WHERE team_id = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setInt(1, teamReferenceIdentifierParameterValue);
            preparedStatementLocalVariableValue.executeUpdate();

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("TeamInfoDao", eventArgumentExceptionParameter);
        }
    }


    /**
     * Devuelve el equipo.
     *
     * @return el equipo.
     */
    public ArrayList<TeamInfo> getAlInfoTeam() {
        return getTeamsInLeague(-1);
    }


    /**
     * Devuelve el equipos liga.
     *
     * @param leagueReferenceIdentifierParameterValue liga identificador.
     * @return el equipos liga.
     */
    public ArrayList<TeamInfo> getTeamsInLeague(int leagueReferenceIdentifierParameterValue) {
        ArrayList<TeamInfo> informationTeamsLocalVariableValue = new ArrayList<>();

        String queryLocalVariableValue = leagueReferenceIdentifierParameterValue == -1
                ? "SELECT league_id, team_id, wins, defeats, ties, points FROM league_teams"
                : "SELECT league_id, team_id, wins, defeats, ties, points FROM league_teams WHERE league_id = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            if (leagueReferenceIdentifierParameterValue != -1) {
                preparedStatementLocalVariableValue.setInt(1, leagueReferenceIdentifierParameterValue);
            }

            try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                while (resultLocalVariableValue.next()) {
                    informationTeamsLocalVariableValue.add(new TeamInfo(
                            resultLocalVariableValue.getInt("league_id"),
                            resultLocalVariableValue.getInt("team_id"),
                            resultLocalVariableValue.getInt("wins"),
                            resultLocalVariableValue.getInt("defeats"),
                            resultLocalVariableValue.getInt("ties"),
                            resultLocalVariableValue.getInt("points")
                    ));
                }
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("TeamInfoDao", eventArgumentExceptionParameter);
        }

        return informationTeamsLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param nomEquipParameterValue dato de entrada de la operacion.
     * @param lligaIdParameterValue dato de entrada de la operacion.
     * @param columnNameParameterValue nombre que usa la operacion.
     */
    public void incrementCounter(String nomEquipParameterValue,
                                 int lligaIdParameterValue,
                                 String columnNameParameterValue) {

        if (!"wins".equals(columnNameParameterValue)
                && !"defeats".equals(columnNameParameterValue)
                && !"ties".equals(columnNameParameterValue)) {
            return;
        }

        String teamQueryLocalVariableValue = "SELECT id FROM teams WHERE name = ?";
        String updateQueryLocalVariableValue =
                "UPDATE league_teams SET " + columnNameParameterValue
                        + " = " + columnNameParameterValue + " + 1"
                        + " WHERE league_id = ? AND team_id = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection()) {

            int teamReferenceIdentifierLocalVariableValue = -1;

            try (PreparedStatement preparedStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(teamQueryLocalVariableValue)) {

                preparedStatementLocalVariableValue.setString(1, nomEquipParameterValue);

                try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                    if (resultLocalVariableValue.next()) {
                        teamReferenceIdentifierLocalVariableValue = resultLocalVariableValue.getInt("id");
                    }
                }
            }

            if (teamReferenceIdentifierLocalVariableValue == -1) {
                return;
            }

            try (PreparedStatement preparedStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(updateQueryLocalVariableValue)) {

                preparedStatementLocalVariableValue.setInt(1, lligaIdParameterValue);
                preparedStatementLocalVariableValue.setInt(2, teamReferenceIdentifierLocalVariableValue);
                preparedStatementLocalVariableValue.executeUpdate();
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("TeamInfoDao", eventArgumentExceptionParameter);
        }
    }


    /**
     * Gestiona esta operacion.
     *
     * @param nomEquipGuanyadorParameterValue dato de entrada de la operacion.
     * @param lligaIdParameterValue dato de entrada de la operacion.
     * @param puntsParameterValue dato de entrada de la operacion.
     */
    public void afegirPunts(String nomEquipGuanyadorParameterValue,
                            int lligaIdParameterValue,
                            int puntsParameterValue) {

        String teamQueryLocalVariableValue = "SELECT id FROM teams WHERE name = ?";
        String updateQueryLocalVariableValue =
                "UPDATE league_teams SET points = points + ? WHERE league_id = ? AND team_id = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection()) {

            int teamReferenceIdentifierLocalVariableValue = -1;


            try (PreparedStatement preparedStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(teamQueryLocalVariableValue)) {

                preparedStatementLocalVariableValue.setString(1, nomEquipGuanyadorParameterValue);

                try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                    if (resultLocalVariableValue.next()) {
                        teamReferenceIdentifierLocalVariableValue = resultLocalVariableValue.getInt("id");
                    }
                }
            }


            if (teamReferenceIdentifierLocalVariableValue == -1) {
                return;
            }


            try (PreparedStatement preparedStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(updateQueryLocalVariableValue)) {

                preparedStatementLocalVariableValue.setInt(1, puntsParameterValue);
                preparedStatementLocalVariableValue.setInt(2, lligaIdParameterValue);
                preparedStatementLocalVariableValue.setInt(3, teamReferenceIdentifierLocalVariableValue);
                preparedStatementLocalVariableValue.executeUpdate();
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("TeamInfoDao", eventArgumentExceptionParameter);
        }
    }
}