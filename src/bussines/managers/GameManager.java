package bussines.managers;

import bussines.objects.Game;
import persistance.GameDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Esta clase se encarga de gestionar las operaciones relacionadas con los partidos.
 */
public class GameManager {

    // DAO que se usa para acceder a los datos de los partidos
    private final GameDao gameEntityDataAccessObjectFieldReference = new GameDao();

    // Devuelve todos los partidos de una liga
    public ArrayList<Game> getGamesByLeague(int leagueReferenceIdentifierParameterValue) {
        return gameEntityDataAccessObjectFieldReference.getGamesByLeague(leagueReferenceIdentifierParameterValue);
    }

    // Actualiza si un partido ha comenzado o no
    public void actualitzaComençat(int gameEntityIdentifierParameterValue, boolean començatParameterValue) {
        gameEntityDataAccessObjectFieldReference.actualitzaComençat(gameEntityIdentifierParameterValue, començatParameterValue);
    }

    // Devuelve los partidos en directo visibles para admin
    public List<String[]> getLiveGames() {
        return gameEntityDataAccessObjectFieldReference.getLiveGames();
    }

    // Devuelve los partidos en directo filtrados por ligas
    public List<String[]> getLiveGamesByLeagueIds(Collection<Integer> leagueIdsParameterValue) {
        return gameEntityDataAccessObjectFieldReference.getLiveGamesByLeagueIds(leagueIdsParameterValue);
    }

    // Busca el id de un partido usando los nombres de los equipos y la liga
    public int getGameIdByLeague(String localParameterValue,
                                 String visitantParameterValue,
                                 int identifierParameterValue) {
        return gameEntityDataAccessObjectFieldReference.getGameId(
                localParameterValue,
                visitantParameterValue,
                identifierParameterValue
        );
    }

    // Borra todos los partidos donde participa un equipo
    public void deleteGamesByTeam(String displayNameParameterValue) {
        gameEntityDataAccessObjectFieldReference.deleteGamesByTeam(displayNameParameterValue);
    }

    // Marca un partido como finalizado
    public void marcarPartitComAcabat(int gameEntityIdentifierParameterValue2) {
        gameEntityDataAccessObjectFieldReference.finishGame(gameEntityIdentifierParameterValue2);
    }

    /**
     * Guarda el ganador del partido o "DRAW" si fue empate.
     */
    public void setGameWinner(int gameIdParameterValue, String winnerNameParameterValue) {
        gameEntityDataAccessObjectFieldReference.setGameWinner(
                gameIdParameterValue,
                winnerNameParameterValue
        );
    }

    // Comprueba si alguno de los equipos seleccionados está jugando ahora mismo
    public boolean teamIsPlaying(ArrayList<String> selectedTeamsParameterValue) {
        ArrayList<Game> gamesLocalVariableValue =
                gameEntityDataAccessObjectFieldReference.searchPlayingTeams(selectedTeamsParameterValue);

        return gamesLocalVariableValue.size() > 0;
    }
}