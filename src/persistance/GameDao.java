package persistance;

import bussines.objects.Game;
import shared.DaoErrorHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;


/**
 * Gestiona el acceso a datos del partido.
 */
public class GameDao {


    /**
     * Devuelve el partidos liga.
     *
     * @param leagueReferenceIdentifierParameterValue liga identificador.
     * @return el partidos liga.
     */
    public ArrayList<Game> getGamesByLeague(int leagueReferenceIdentifierParameterValue) {
        ArrayList<Game> gamesLocalVariableValue = new ArrayList<>();

        String queryLocalVariableValue =
                "SELECT g.id, g.league_id, g.round_number, g.scheduled_at, g.started, g.finished, g.winner_name, " +
                        "th.name AS home_name, ta.name AS away_name " +
                        "FROM games g " +
                        "JOIN teams th ON th.id = g.home_team_id " +
                        "JOIN teams ta ON ta.id = g.away_team_id " +
                        "WHERE g.league_id = ? ORDER BY g.round_number, g.id";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setInt(1, leagueReferenceIdentifierParameterValue);

            try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                while (resultLocalVariableValue.next()) {
                    gamesLocalVariableValue.add(mapGame(resultLocalVariableValue));
                }
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("GameDao", eventArgumentExceptionParameter);
        }

        return gamesLocalVariableValue;
    }


    /**
     * Elimina el partidos equipo.
     *
     * @param displayNameParameterValue nombre que se muestra.
     */
    public void deleteGamesByTeam(String displayNameParameterValue) {
        String queryLocalVariableValue =
                "DELETE g FROM games g JOIN teams t ON (g.home_team_id = t.id OR g.away_team_id = t.id) WHERE t.name = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setString(1, displayNameParameterValue);
            preparedStatementLocalVariableValue.executeUpdate();

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("GameDao", eventArgumentExceptionParameter);
        }
    }


    /**
     * Devuelve los directo partidos.
     *
     * @return los directo partidos.
     */
    public List<String[]> getLiveGames() {
        List<String[]> liveGamesLocalVariableValue = new ArrayList<>();

        String queryLocalVariableValue =
                "SELECT g.id, g.league_id, th.name AS home_name, ta.name AS away_name " +
                        "FROM games g " +
                        "JOIN teams th ON th.id = g.home_team_id " +
                        "JOIN teams ta ON ta.id = g.away_team_id " +
                        "WHERE g.started = TRUE AND g.finished = FALSE " +
                        "ORDER BY g.league_id, g.round_number, g.id";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue);
             ResultSet resultLocalVariableValue =
                     preparedStatementLocalVariableValue.executeQuery()) {

            while (resultLocalVariableValue.next()) {
                liveGamesLocalVariableValue.add(mapLiveGame(resultLocalVariableValue));
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("GameDao", eventArgumentExceptionParameter);
        }

        return liveGamesLocalVariableValue;
    }


    /**
     * Devuelve el directo partidos liga.
     *
     * @param leagueIdsParameterValue liga que usa la operacion.
     * @return el directo partidos liga.
     */
    public List<String[]> getLiveGamesByLeagueIds(Collection<Integer> leagueIdsParameterValue) {
        List<String[]> liveGamesLocalVariableValue = new ArrayList<>();

        if (leagueIdsParameterValue == null || leagueIdsParameterValue.isEmpty()) {
            return liveGamesLocalVariableValue;
        }

        StringBuilder placeholdersLocalVariableValue = new StringBuilder();

        for (int indexCounterLocalVariableValue = 0;
             indexCounterLocalVariableValue < leagueIdsParameterValue.size();
             indexCounterLocalVariableValue++) {

            if (indexCounterLocalVariableValue > 0) {
                placeholdersLocalVariableValue.append(", ");
            }

            placeholdersLocalVariableValue.append("?");
        }

        String queryLocalVariableValue =
                "SELECT g.id, g.league_id, th.name AS home_name, ta.name AS away_name " +
                        "FROM games g " +
                        "JOIN teams th ON th.id = g.home_team_id " +
                        "JOIN teams ta ON ta.id = g.away_team_id " +
                        "WHERE g.started = TRUE AND g.finished = FALSE " +
                        "AND g.league_id IN (" + placeholdersLocalVariableValue + ") " +
                        "ORDER BY g.league_id, g.round_number, g.id";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            int parameterCounterLocalVariableValue = 1;

            for (Integer leagueIdLocalVariableValue : leagueIdsParameterValue) {
                preparedStatementLocalVariableValue.setInt(
                        parameterCounterLocalVariableValue++,
                        leagueIdLocalVariableValue
                );
            }

            try (ResultSet resultLocalVariableValue =
                         preparedStatementLocalVariableValue.executeQuery()) {

                while (resultLocalVariableValue.next()) {
                    liveGamesLocalVariableValue.add(mapLiveGame(resultLocalVariableValue));
                }
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("GameDao", eventArgumentExceptionParameter);
        }

        return liveGamesLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param resultLocalVariableValue resultado que usa la operacion.
     * @return resultado de la operacion.
     */
    private String[] mapLiveGame(ResultSet resultLocalVariableValue) throws SQLException {
        return new String[]{
                resultLocalVariableValue.getString("home_name"),
                resultLocalVariableValue.getString("away_name"),
                String.valueOf(resultLocalVariableValue.getInt("id")),
                String.valueOf(resultLocalVariableValue.getInt("league_id"))
        };
    }


    /**
     * Gestiona esta operacion.
     *
     * @param gameEntityIdentifierParameterValue partido identificador.
     * @param startedParameterValue dato de entrada de la operacion.
     */
    public void actualitzaComençat(int gameEntityIdentifierParameterValue, boolean startedParameterValue) {
        String queryLocalVariableValue = "UPDATE games SET started = ? WHERE id = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setBoolean(1, startedParameterValue);
            preparedStatementLocalVariableValue.setInt(2, gameEntityIdentifierParameterValue);
            preparedStatementLocalVariableValue.executeUpdate();

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("GameDao", eventArgumentExceptionParameter);
        }
    }


    /**
     * Devuelve el partido.
     *
     * @param localTeamReferenceParameterValue equipo que usa la operacion.
     * @param awayTeamReferenceParameterValue equipo que usa la operacion.
     * @param leagueReferenceIdentifierParameterValue2 liga identificador.
     * @return el partido.
     */
    public int getGameId(String localTeamReferenceParameterValue,
                         String awayTeamReferenceParameterValue,
                         int leagueReferenceIdentifierParameterValue2) {

        String queryLocalVariableValue =
                "SELECT g.id FROM games g " +
                        "JOIN teams th ON th.id = g.home_team_id " +
                        "JOIN teams ta ON ta.id = g.away_team_id " +
                        "WHERE th.name = ? AND ta.name = ? AND g.league_id = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setString(1, localTeamReferenceParameterValue);
            preparedStatementLocalVariableValue.setString(2, awayTeamReferenceParameterValue);
            preparedStatementLocalVariableValue.setInt(3, leagueReferenceIdentifierParameterValue2);

            try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                if (resultLocalVariableValue.next()) {
                    return resultLocalVariableValue.getInt("id");
                }
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("GameDao", eventArgumentExceptionParameter);
        }

        return -1;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param matchIdentifierParameterValue partido identificador.
     */
    public void finishGame(int matchIdentifierParameterValue) {
        String queryLocalVariableValue = "UPDATE games SET started = TRUE, finished = TRUE WHERE id = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setInt(1, matchIdentifierParameterValue);
            preparedStatementLocalVariableValue.executeUpdate();

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("GameDao", eventArgumentExceptionParameter);
        }
    }


    /**
     * Actualiza el partido.
     *
     * @param gameIdParameterValue partido que usa la operacion.
     * @param winnerNameParameterValue nombre que usa la operacion.
     */
    public void setGameWinner(int gameIdParameterValue, String winnerNameParameterValue) {
        String queryLocalVariableValue = "UPDATE games SET winner_name = ? WHERE id = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setString(1, winnerNameParameterValue);
            preparedStatementLocalVariableValue.setInt(2, gameIdParameterValue);
            preparedStatementLocalVariableValue.executeUpdate();

        } catch (SQLException eventArgumentExceptionParameter) {
            DaoErrorHandler.log("GameDao", eventArgumentExceptionParameter);
        }
    }


    /**
     * Gestiona esta operacion.
     *
     * @param resultLocalVariableValue resultado que usa la operacion.
     * @return resultado de la operacion.
     */
    private Game mapGame(ResultSet resultLocalVariableValue) throws SQLException {
        Game gameLocalVariableValue = new Game(
                resultLocalVariableValue.getInt("id"),
                resultLocalVariableValue.getString("home_name"),
                resultLocalVariableValue.getString("away_name"),
                resultLocalVariableValue.getTimestamp("scheduled_at").toLocalDateTime(),
                resultLocalVariableValue.getInt("league_id"),
                resultLocalVariableValue.getInt("round_number"),
                resultLocalVariableValue.getBoolean("started"),
                resultLocalVariableValue.getBoolean("finished")
        );


        try {
            String winnerLocalVariableValue = resultLocalVariableValue.getString("winner_name");
            gameLocalVariableValue.setWinnerName(winnerLocalVariableValue);
        } catch (SQLException ignoredExceptionParameterValue) {

        }

        return gameLocalVariableValue;
    }
}