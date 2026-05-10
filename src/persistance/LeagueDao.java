package persistance;

import bussines.objects.League;
import shared.DaoErrorHandler;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * Gestiona el acceso a datos del liga.
 */
public class LeagueDao {


    /**
     * Devuelve los ligas.
     *
     * @return los ligas.
     */
    public ArrayList<League> getAllLeagues() {
        ArrayList<League> leaguesLocalVariableValue = new ArrayList<>();
        String queryLocalVariableValue = "SELECT id, name, start_datetime FROM leagues ORDER BY id";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue);
             ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {

            while (resultLocalVariableValue.next()) {
                leaguesLocalVariableValue.add(new League(
                        resultLocalVariableValue.getString("name"),
                        resultLocalVariableValue.getString("start_datetime"),
                        getTeamNamesByLeagueId(
                                connectionLocalVariableValue,
                                resultLocalVariableValue.getInt("id")
                        ),
                        resultLocalVariableValue.getInt("id")
                ));
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("LeagueDao", eventArgumentExceptionParameter);
        }

        return leaguesLocalVariableValue;
    }


    /**
     * Devuelve el ligas usuario equipo.
     *
     * @param teamReferenceDisplayNameParameterValue nombre del equipo.
     * @return el ligas usuario equipo.
     */
    public ArrayList<League> getLeaguesByUserTeam(String teamReferenceDisplayNameParameterValue) {
        ArrayList<League> leaguesLocalVariableValue = new ArrayList<>();

        String queryLocalVariableValue =
                "SELECT l.id, l.name, l.start_datetime " +
                        "FROM leagues l " +
                        "JOIN league_teams lt ON lt.league_id = l.id " +
                        "JOIN teams t ON t.id = lt.team_id " +
                        "WHERE t.name = ? " +
                        "ORDER BY l.id";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setString(1, teamReferenceDisplayNameParameterValue);

            try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                while (resultLocalVariableValue.next()) {
                    leaguesLocalVariableValue.add(new League(
                            resultLocalVariableValue.getString("name"),
                            resultLocalVariableValue.getString("start_datetime"),
                            getTeamNamesByLeagueId(
                                    connectionLocalVariableValue,
                                    resultLocalVariableValue.getInt("id")
                            ),
                            resultLocalVariableValue.getInt("id")
                    ));
                }
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("LeagueDao", eventArgumentExceptionParameter);
        }

        return leaguesLocalVariableValue;
    }


    /**
     * Elimina el liga nombre.
     *
     * @param leagueReferenceDisplayNameParameterValue nombre de la liga.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean deleteLeagueByName(String leagueReferenceDisplayNameParameterValue) {
        String queryLocalVariableValue = "DELETE FROM leagues WHERE name = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setString(1, leagueReferenceDisplayNameParameterValue);
            return preparedStatementLocalVariableValue.executeUpdate() > 0;

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("LeagueDao", eventArgumentExceptionParameter);
            return false;
        }
    }


    /**
     * Gestiona esta operacion.
     *
     * @param localTeamReferenceParameterValue equipo que usa la operacion.
     * @param awayTeamReferenceParameterValue equipo que usa la operacion.
     * @param leagueReferenceIdentifierParameterValue liga identificador.
     * @param jornadaParameterValue dato de entrada de la operacion.
     * @param timeParameterValue dato de entrada de la operacion.
     */
    public void insertGame(String localTeamReferenceParameterValue,
                           String awayTeamReferenceParameterValue,
                           int leagueReferenceIdentifierParameterValue,
                           int jornadaParameterValue,
                           LocalDateTime timeParameterValue) {

        String queryLocalVariableValue =
                "INSERT INTO games (league_id, home_team_id, away_team_id, round_number, scheduled_at, started, finished) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            int localTeamReferenceIdentifierLocalVariableValue =
                    getTeamIdByName(connectionLocalVariableValue, localTeamReferenceParameterValue);

            int awayTeamReferenceIdentifierLocalVariableValue =
                    getTeamIdByName(connectionLocalVariableValue, awayTeamReferenceParameterValue);

            if (localTeamReferenceIdentifierLocalVariableValue == -1
                    || awayTeamReferenceIdentifierLocalVariableValue == -1) {
                return;
            }

            preparedStatementLocalVariableValue.setInt(1, leagueReferenceIdentifierParameterValue);
            preparedStatementLocalVariableValue.setInt(2, localTeamReferenceIdentifierLocalVariableValue);
            preparedStatementLocalVariableValue.setInt(3, awayTeamReferenceIdentifierLocalVariableValue);
            preparedStatementLocalVariableValue.setInt(4, jornadaParameterValue);
            preparedStatementLocalVariableValue.setTimestamp(5, Timestamp.valueOf(timeParameterValue));
            preparedStatementLocalVariableValue.setBoolean(6, false);
            preparedStatementLocalVariableValue.setBoolean(7, false);
            preparedStatementLocalVariableValue.executeUpdate();

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("LeagueDao", eventArgumentExceptionParameter);
        }
    }


    /**
     * Crea el liga.
     *
     * @param leagueReferenceParameterValue liga que usa la operacion.
     */
    public void createLeague(League leagueReferenceParameterValue) {
        String insertLeagueQueryLocalVariableValue =
                "INSERT INTO leagues (name, start_datetime) VALUES (?, ?)";

        String insertLeagueTeamQueryLocalVariableValue =
                "INSERT INTO league_teams (league_id, team_id, wins, defeats, ties, points) VALUES (?, ?, 0, 0, 0, 0)";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection()) {

            connectionLocalVariableValue.setAutoCommit(false);
            int leagueReferenceIdentifierLocalVariableValue;

            try (PreparedStatement preparedStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(
                                 insertLeagueQueryLocalVariableValue,
                                 Statement.RETURN_GENERATED_KEYS
                         )) {

                preparedStatementLocalVariableValue.setString(1, leagueReferenceParameterValue.getName());
                preparedStatementLocalVariableValue.setTimestamp(
                        2,
                        parseTimestamp(leagueReferenceParameterValue.getStartDate())
                );
                preparedStatementLocalVariableValue.executeUpdate();

                try (ResultSet generatedKeysLocalVariableValue =
                             preparedStatementLocalVariableValue.getGeneratedKeys()) {
                    if (!generatedKeysLocalVariableValue.next()) {
                        throw new SQLException("No league id generated.");
                    }
                    leagueReferenceIdentifierLocalVariableValue =
                            generatedKeysLocalVariableValue.getInt(1);
                }
            }

            try (PreparedStatement preparedStatementLocalVariableValue =
                         connectionLocalVariableValue.prepareStatement(insertLeagueTeamQueryLocalVariableValue)) {

                for (String teamReferenceDisplayNameLocalVariableValue :
                        leagueReferenceParameterValue.getParticipatingTeams()) {

                    int teamReferenceIdentifierLocalVariableValue =
                            getTeamIdByName(connectionLocalVariableValue, teamReferenceDisplayNameLocalVariableValue);

                    if (teamReferenceIdentifierLocalVariableValue == -1) {
                        continue;
                    }

                    preparedStatementLocalVariableValue.setInt(1, leagueReferenceIdentifierLocalVariableValue);
                    preparedStatementLocalVariableValue.setInt(2, teamReferenceIdentifierLocalVariableValue);
                    preparedStatementLocalVariableValue.addBatch();
                }

                preparedStatementLocalVariableValue.executeBatch();
            }

            connectionLocalVariableValue.commit();
            connectionLocalVariableValue.setAutoCommit(true);

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("LeagueDao", eventArgumentExceptionParameter);
        }
    }


    /**
     * Devuelve el liga equipo.
     *
     * @param teamReferenceDisplayNameParameterValue2 nombre del equipo.
     * @return el liga equipo.
     */
    public int getLeagueIdByTeam(String teamReferenceDisplayNameParameterValue2) {
        String queryLocalVariableValue =
                "SELECT l.id FROM leagues l " +
                        "JOIN league_teams lt ON lt.league_id = l.id " +
                        "JOIN teams t ON t.id = lt.team_id " +
                        "WHERE t.name = ? LIMIT 1";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setString(1, teamReferenceDisplayNameParameterValue2);

            try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                if (resultLocalVariableValue.next()) {
                    return resultLocalVariableValue.getInt("id");
                }
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("LeagueDao", eventArgumentExceptionParameter);
        }

        return -1;
    }


    /**
     * Devuelve el liga nombre.
     *
     * @param leagueReferenceDisplayName2ParameterValue nombre de la liga.
     * @return el liga nombre.
     */
    public int getLeagueIdByName(String leagueReferenceDisplayName2ParameterValue) {
        String queryLocalVariableValue = "SELECT id FROM leagues WHERE name = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setString(1, leagueReferenceDisplayName2ParameterValue);

            try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                if (resultLocalVariableValue.next()) {
                    return resultLocalVariableValue.getInt("id");
                }
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("LeagueDao", eventArgumentExceptionParameter);
        }

        return -1;
    }


    /**
     * Devuelve el equipo nombre.
     *
     * @param connectionParameterValue conexion que usa la operacion.
     * @param teamReferenceDisplayNameParameterValue nombre del equipo.
     * @return el equipo nombre.
     */
    private int getTeamIdByName(Connection connectionParameterValue,
                                String teamReferenceDisplayNameParameterValue) throws SQLException {

        String queryLocalVariableValue = "SELECT id FROM teams WHERE name = ?";

        try (PreparedStatement preparedStatementLocalVariableValue =
                     connectionParameterValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setString(1, teamReferenceDisplayNameParameterValue);

            try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                if (resultLocalVariableValue.next()) {
                    return resultLocalVariableValue.getInt("id");
                }
            }
        }

        return -1;
    }


    /**
     * Devuelve el equipo liga.
     *
     * @param connectionParameterValue conexion que usa la operacion.
     * @param leagueReferenceIdentifierParameterValue liga identificador.
     * @return el equipo liga.
     */
    private ArrayList<String> getTeamNamesByLeagueId(Connection connectionParameterValue,
                                                     int leagueReferenceIdentifierParameterValue) throws SQLException {

        ArrayList<String> teamNamesLocalVariableValue = new ArrayList<>();

        String queryLocalVariableValue =
                "SELECT t.name FROM teams t JOIN league_teams lt ON lt.team_id = t.id WHERE lt.league_id = ? ORDER BY t.name";

        try (PreparedStatement preparedStatementLocalVariableValue =
                     connectionParameterValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setInt(1, leagueReferenceIdentifierParameterValue);

            try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                while (resultLocalVariableValue.next()) {
                    teamNamesLocalVariableValue.add(resultLocalVariableValue.getString("name"));
                }
            }
        }

        return teamNamesLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param dateParameterValue dato de entrada de la operacion.
     * @return resultado de la operacion.
     */
    private Timestamp parseTimestamp(String dateParameterValue) {
        try {
            return Timestamp.valueOf(
                    LocalDateTime.parse(
                            dateParameterValue,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    )
            );
        } catch (Exception ignoredParameterValue) {
        }

        try {
            return Timestamp.valueOf(
                    LocalDateTime.parse(
                            dateParameterValue,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    )
            );
        } catch (Exception ignoredParameterValue) {
        }

        return Timestamp.valueOf(LocalDateTime.now());
    }
}