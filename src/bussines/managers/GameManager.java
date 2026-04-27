package bussines.managers;

import bussines.objects.Game;
import persistance.GameDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se encarga de gestionar las operaciones relacionadas con los partidos.
 */
public class GameManager {

    // DAO que se usa para acceder a los datos de los partidos
    private GameDao gameEntityDataAccessObjectFieldReference = new GameDao();

    // Devuelve todos los partidos de una liga
    public ArrayList<Game> getGamesByLeague(int leagueReferenceIdentifierParameterValue) {
        return gameEntityDataAccessObjectFieldReference.getGamesByLeague(leagueReferenceIdentifierParameterValue);
    }

    // Actualiza si un partido ha comenzado o no
    public void actualitzaComençat(int gameEntityIdentifierParameterValue, boolean començatParameterValue) {
        gameEntityDataAccessObjectFieldReference.actualitzaComençat(gameEntityIdentifierParameterValue, començatParameterValue);
    }

    // Busca el id de un partido usando los nombres de los equipos y la liga
    public int getGameIdByLeague(String localParameterValue, String visitantParameterValue, int identifierParameterValue) {
        return gameEntityDataAccessObjectFieldReference.getGameId(localParameterValue, visitantParameterValue, identifierParameterValue);
    }

    // Borra todos los partidos donde participa un equipo
    public void deleteGamesByTeam(String displayNameParameterValue) {
        gameEntityDataAccessObjectFieldReference.deleteGamesByTeam(displayNameParameterValue);
    }

    // Devuelve los partidos que están en juego en este momento
    public List<String[]> getLiveGames() {
        return gameEntityDataAccessObjectFieldReference.getLiveGames();
    }

    // Marca un partido como finalizado
    public void marcarPartitComAcabat(int gameEntityIdentifierParameterValue2) {
        gameEntityDataAccessObjectFieldReference.finishGame(gameEntityIdentifierParameterValue2);
    }

    // Comprueba si alguno de los equipos seleccionados está jugando ahora mismo
    public boolean teamIsPlaying(ArrayList<String> selectedTeamsParameterValue) {
        ArrayList<Game> gamesLocalVariableValue =
                gameEntityDataAccessObjectFieldReference.searchPlayingTeams(selectedTeamsParameterValue);

        if (gamesLocalVariableValue.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}