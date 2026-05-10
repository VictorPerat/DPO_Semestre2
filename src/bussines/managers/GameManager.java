package bussines.managers;

import bussines.objects.Game;
import persistance.GameDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Gestiona las operaciones del partido.
 */
public class GameManager {


    private final GameDao gameEntityDataAccessObjectFieldReference = new GameDao();


    /**
     * Devuelve el partidos liga.
     *
     * @param leagueReferenceIdentifierParameterValue liga identificador.
     * @return el partidos liga.
     */
    public ArrayList<Game> getGamesByLeague(int leagueReferenceIdentifierParameterValue) {
        return gameEntityDataAccessObjectFieldReference.getGamesByLeague(leagueReferenceIdentifierParameterValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param gameEntityIdentifierParameterValue partido identificador.
     * @param començatParameterValue dato de entrada de la operacion.
     */
    public void actualitzaComençat(int gameEntityIdentifierParameterValue, boolean començatParameterValue) {
        gameEntityDataAccessObjectFieldReference.actualitzaComençat(gameEntityIdentifierParameterValue, començatParameterValue);
    }


    /**
     * Devuelve los directo partidos.
     *
     * @return los directo partidos.
     */
    public List<String[]> getLiveGames() {
        return gameEntityDataAccessObjectFieldReference.getLiveGames();
    }


    /**
     * Devuelve el directo partidos liga.
     *
     * @param leagueIdsParameterValue liga que usa la operacion.
     * @return el directo partidos liga.
     */
    public List<String[]> getLiveGamesByLeagueIds(Collection<Integer> leagueIdsParameterValue) {
        return gameEntityDataAccessObjectFieldReference.getLiveGamesByLeagueIds(leagueIdsParameterValue);
    }


    /**
     * Devuelve el partido liga.
     *
     * @param localParameterValue dato de entrada de la operacion.
     * @param visitantParameterValue dato de entrada de la operacion.
     * @param identifierParameterValue identificador del usuario.
     * @return el partido liga.
     */
    public int getGameIdByLeague(String localParameterValue,
                                 String visitantParameterValue,
                                 int identifierParameterValue) {
        return gameEntityDataAccessObjectFieldReference.getGameId(
                localParameterValue,
                visitantParameterValue,
                identifierParameterValue
        );
    }


    /**
     * Elimina el partidos equipo.
     *
     * @param displayNameParameterValue nombre que se muestra.
     */
    public void deleteGamesByTeam(String displayNameParameterValue) {
        gameEntityDataAccessObjectFieldReference.deleteGamesByTeam(displayNameParameterValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param gameEntityIdentifierParameterValue2 partido identificador.
     */
    public void marcarPartitComAcabat(int gameEntityIdentifierParameterValue2) {
        gameEntityDataAccessObjectFieldReference.finishGame(gameEntityIdentifierParameterValue2);
    }


    /**
     * Actualiza el partido.
     *
     * @param gameIdParameterValue partido que usa la operacion.
     * @param winnerNameParameterValue nombre que usa la operacion.
     */
    public void setGameWinner(int gameIdParameterValue, String winnerNameParameterValue) {
        gameEntityDataAccessObjectFieldReference.setGameWinner(
                gameIdParameterValue,
                winnerNameParameterValue
        );
    }


    /**
     * Gestiona esta operacion.
     *
     * @param selectedTeamsParameterValue equipos que usa la operacion.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean teamIsPlaying(ArrayList<String> selectedTeamsParameterValue) {
        ArrayList<Game> gamesLocalVariableValue =
                gameEntityDataAccessObjectFieldReference.searchPlayingTeams(selectedTeamsParameterValue);

        return gamesLocalVariableValue.size() > 0;
    }
}