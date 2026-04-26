package bussines.managers;

import bussines.objects.Game;
import persistance.GameDao;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code GameManager} actúa como intermediario entre la capa de negocio
 * y la capa de persistencia para gestionar las operaciones relacionadas con partidos.
 * <p>
 * Utiliza un objeto {@code SQLGame} como DAO para interactuar con la base de datos.
 */
public class GameManager {
    /** Objeto DAO que gestiona las operaciones con la base de datos relacionadas con los partidos. */
    private GameDao gameEntityDataAccessObjectFieldReference = new GameDao();
    /**
            * Obtiene todos los partidos asociados a una liga específica.
     *
             * @param leagueId identificador de la liga
     * @return lista de partidos pertenecientes a la liga
     */
    public ArrayList<Game> getGamesByLeague(int leagueReferenceIdentifierParameterValue) {
        return gameEntityDataAccessObjectFieldReference.getGamesByLeague(leagueReferenceIdentifierParameterValue);
    }
    /**
     * Actualiza el estado de inicio de un partido.
     *
     * @param game_id identificador del partido
     * @param començat {@code true} si el partido ha comenzado, {@code false} si no
     */
    public void actualitzaComençat(int gameEntityIdentifierParameterValue, boolean començatParameterValue) {
        gameEntityDataAccessObjectFieldReference.actualitzaComençat(gameEntityIdentifierParameterValue, començatParameterValue);
    }
    /**
     * Obtiene el identificador de un partido a partir de los nombres de los equipos y la liga.
     *
     * @param local nombre del equipo local
     * @param visitant nombre del equipo visitante
     * @param id identificador de la liga
     * @return el ID del partido correspondiente
     */
    public int getGameIdByLeague(String localParameterValue, String visitantParameterValue, int identifierParameterValue) {
        return gameEntityDataAccessObjectFieldReference.getGameId(localParameterValue, visitantParameterValue, identifierParameterValue);
    }
    /**
     * Elimina todos los partidos en los que participa un equipo dado.
     *
     * @param name nombre del equipo
     */
    public void deleteGamesByTeam(String displayNameParameterValue) {
        gameEntityDataAccessObjectFieldReference.deleteGamesByTeam(displayNameParameterValue);
    }
    /**
     * Obtiene los partidos que están actualmente en directo (comenzados pero no finalizados).
     *
     * @return lista de partidos en directo representados como arreglos de {@code String}
     */
    public List<String[]> getLiveGames () {
        return gameEntityDataAccessObjectFieldReference.getLiveGames();
    }
    /**
     * Marca un partido como finalizado en la base de datos.
     *
     * @param gameId identificador del partido
     */
    public void marcarPartitComAcabat(int gameEntityIdentifierParameterValue2) {
        gameEntityDataAccessObjectFieldReference.finishGame(gameEntityIdentifierParameterValue2);
    }
    /**
     * Verifica si alguno de los equipos proporcionados está jugando actualmente.
     *
     * @param selectedTeams lista de nombres de equipos a verificar
     * @return {@code true} si al menos uno de los equipos está jugando, {@code false} en caso contrario
     */
    public boolean teamIsPlaying(ArrayList <String> selectedTeamsParameterValue) {
        ArrayList <Game> gamesLocalVariableValue = gameEntityDataAccessObjectFieldReference.searchPlayingTeams(selectedTeamsParameterValue);
        if (gamesLocalVariableValue.size() > 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
