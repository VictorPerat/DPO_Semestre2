package persistance;

import bussines.objects.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se encarga de acceder a los datos de los partidos en la base de datos.
 */
public class GameDao {

    // Devuelve todos los partidos de una liga
    public ArrayList<Game> getGamesByLeague(int leagueReferenceIdentifierParameterValue) {
        ArrayList<Game> gamesLocalVariableValue = new ArrayList<>();

        String queryLocalVariableValue =
                "SELECT g.id, g.league_id, g.round_number, g.scheduled_at, g.started, g.finished, " +
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
            eventArgumentExceptionParameter.printStackTrace();
        }

        return gamesLocalVariableValue;
    }

    // Borra todos los partidos en los que participa un equipo
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
            eventArgumentExceptionParameter.printStackTrace();
        }
    }

    // Actualiza si un partido ha empezado o no
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
            eventArgumentExceptionParameter.printStackTrace();
        }
    }

    // Devuelve el id de un partido a partir de los equipos y la liga
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
            eventArgumentExceptionParameter.printStackTrace();
        }

        return -1;
    }

    // Devuelve los partidos que están en directo
    public List<String[]> getLiveGames() {
        List<String[]> liveGamesLocalVariableValue = new ArrayList<>();

        String queryLocalVariableValue =
                "SELECT th.name AS home_name, ta.name AS away_name " +
                        "FROM games g " +
                        "JOIN teams th ON th.id = g.home_team_id " +
                        "JOIN teams ta ON ta.id = g.away_team_id " +
                        "WHERE g.started = TRUE AND g.finished = FALSE";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue);
             ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {

            while (resultLocalVariableValue.next()) {
                liveGamesLocalVariableValue.add(new String[]{
                        resultLocalVariableValue.getString("home_name"),
                        resultLocalVariableValue.getString("away_name")
                });
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            eventArgumentExceptionParameter.printStackTrace();
        }

        return liveGamesLocalVariableValue;
    }

    // Marca un partido como finalizado
    public void finishGame(int matchIdentifierParameterValue) {
        String queryLocalVariableValue = "UPDATE games SET started = TRUE, finished = TRUE WHERE id = ?";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            preparedStatementLocalVariableValue.setInt(1, matchIdentifierParameterValue);
            preparedStatementLocalVariableValue.executeUpdate();

        } catch (SQLException eventArgumentExceptionParameter) {
            eventArgumentExceptionParameter.printStackTrace();
        }
    }

    // Busca si alguno de los equipos seleccionados está jugando ahora mismo
    public ArrayList<Game> searchPlayingTeams(ArrayList<String> selectedTeamsParameterValue) {
        ArrayList<Game> gamesLocalVariableValue = new ArrayList<>();

        if (selectedTeamsParameterValue == null || selectedTeamsParameterValue.isEmpty()) {
            return gamesLocalVariableValue;
        }

        StringBuilder placeholdersLocalVariableValue = new StringBuilder();
        for (int indexCounterLocalVariableValue = 0; indexCounterLocalVariableValue < selectedTeamsParameterValue.size(); indexCounterLocalVariableValue++) {
            if (indexCounterLocalVariableValue > 0) {
                placeholdersLocalVariableValue.append(", ");
            }
            placeholdersLocalVariableValue.append("?");
        }

        String queryLocalVariableValue =
                "SELECT g.id, g.league_id, g.round_number, g.scheduled_at, g.started, g.finished, " +
                        "th.name AS home_name, ta.name AS away_name " +
                        "FROM games g " +
                        "JOIN teams th ON th.id = g.home_team_id " +
                        "JOIN teams ta ON ta.id = g.away_team_id " +
                        "WHERE g.started = TRUE AND g.finished = FALSE AND " +
                        "(th.name IN (" + placeholdersLocalVariableValue + ") OR ta.name IN (" + placeholdersLocalVariableValue + "))";

        try (Connection connectionLocalVariableValue =
                     DatabaseConnector.getInstance().createConnection();
             PreparedStatement preparedStatementLocalVariableValue =
                     connectionLocalVariableValue.prepareStatement(queryLocalVariableValue)) {

            int parameterCounterLocalVariableValue = 1;

            for (String teamReferenceDisplayNameLocalVariableValue : selectedTeamsParameterValue) {
                preparedStatementLocalVariableValue.setString(
                        parameterCounterLocalVariableValue++,
                        teamReferenceDisplayNameLocalVariableValue
                );
            }

            for (String teamReferenceDisplayNameLocalVariableValue : selectedTeamsParameterValue) {
                preparedStatementLocalVariableValue.setString(
                        parameterCounterLocalVariableValue++,
                        teamReferenceDisplayNameLocalVariableValue
                );
            }

            try (ResultSet resultLocalVariableValue = preparedStatementLocalVariableValue.executeQuery()) {
                while (resultLocalVariableValue.next()) {
                    gamesLocalVariableValue.add(mapGame(resultLocalVariableValue));
                }
            }

        } catch (SQLException eventArgumentExceptionParameter) {
            eventArgumentExceptionParameter.printStackTrace();
        }

        return gamesLocalVariableValue;
    }

    // Convierte una fila de la base de datos en un objeto Game
    private Game mapGame(ResultSet resultLocalVariableValue) throws SQLException {
        return new Game(
                resultLocalVariableValue.getInt("id"),
                resultLocalVariableValue.getString("home_name"),
                resultLocalVariableValue.getString("away_name"),
                resultLocalVariableValue.getTimestamp("scheduled_at").toLocalDateTime(),
                resultLocalVariableValue.getInt("league_id"),
                resultLocalVariableValue.getInt("round_number"),
                resultLocalVariableValue.getBoolean("started"),
                resultLocalVariableValue.getBoolean("finished")
        );
    }
}