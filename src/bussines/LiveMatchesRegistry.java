package bussines;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Agrupa la logica de los directo partidos.
 */
public final class LiveMatchesRegistry {


    /**
     * Define el contrato del partido.
     */
    public interface AbortableMatch {


        /**
         * Devuelve el partido.
         *
         * @return el partido.
         */
        int getGameId();


        /**
         * Devuelve el liga.
         *
         * @return el liga.
         */
        int getLeagueId();


        /**
         * Devuelve el equipo nombre.
         *
         * @return el equipo nombre.
         */
        String getHomeTeamName();


        /**
         * Devuelve el equipo nombre.
         *
         * @return el equipo nombre.
         */
        String getAwayTeamName();


        /**
         * Muestra el partido ventana.
         */
        void showMatchWindow();


        /**
         * Gestiona esta operacion.
         */
        void abortMatch();
    }

    private static final LiveMatchesRegistry INSTANCE = new LiveMatchesRegistry();

    private final ConcurrentHashMap<Integer, AbortableMatch> activeMatchesFieldReference =
            new ConcurrentHashMap<>();


    /**
     * Crea una instancia de los directo partidos.
     */
    private LiveMatchesRegistry() {
    }


    /**
     * Devuelve el instancia.
     *
     * @return el instancia.
     */
    public static LiveMatchesRegistry getInstance() {
        return INSTANCE;
    }


    /**
     * Registra la accion.
     *
     * @param matchParameterValue partido que usa la operacion.
     */
    public void register(AbortableMatch matchParameterValue) {
        if (matchParameterValue != null) {
            activeMatchesFieldReference.put(matchParameterValue.getGameId(), matchParameterValue);
        }
    }


    /**
     * Gestiona esta operacion.
     *
     * @param gameIdParameterValue partido que usa la operacion.
     */
    public void unregister(int gameIdParameterValue) {
        activeMatchesFieldReference.remove(gameIdParameterValue);
    }


    /**
     * Indica el estado actual.
     *
     * @param gameIdParameterValue partido que usa la operacion.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean isRegistered(int gameIdParameterValue) {
        return activeMatchesFieldReference.containsKey(gameIdParameterValue);
    }


    /**
     * Muestra el partido ventana.
     *
     * @param gameIdParameterValue partido que usa la operacion.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean showMatchWindow(int gameIdParameterValue) {
        AbortableMatch matchLocalVariableValue =
                activeMatchesFieldReference.get(gameIdParameterValue);

        if (matchLocalVariableValue == null) {
            return false;
        }

        matchLocalVariableValue.showMatchWindow();
        return true;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param leagueIdParameterValue liga que usa la operacion.
     */
    public void abortMatchesByLeague(int leagueIdParameterValue) {
        List<AbortableMatch> toAbortLocalVariableValue = new ArrayList<>();
        for (AbortableMatch matchLocalVariableValue : activeMatchesFieldReference.values()) {
            if (matchLocalVariableValue.getLeagueId() == leagueIdParameterValue) {
                toAbortLocalVariableValue.add(matchLocalVariableValue);
            }
        }
        abortAll(toAbortLocalVariableValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param teamNameParameterValue nombre del equipo.
     */
    public void abortMatchesByTeam(String teamNameParameterValue) {
        if (teamNameParameterValue == null) {
            return;
        }
        List<AbortableMatch> toAbortLocalVariableValue = new ArrayList<>();
        for (AbortableMatch matchLocalVariableValue : activeMatchesFieldReference.values()) {
            if (teamNameParameterValue.equalsIgnoreCase(matchLocalVariableValue.getHomeTeamName())
                    || teamNameParameterValue.equalsIgnoreCase(matchLocalVariableValue.getAwayTeamName())) {
                toAbortLocalVariableValue.add(matchLocalVariableValue);
            }
        }
        abortAll(toAbortLocalVariableValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param matchesParameterValue partidos que usa la operacion.
     */
    private void abortAll(List<AbortableMatch> matchesParameterValue) {
        for (AbortableMatch matchLocalVariableValue : matchesParameterValue) {
            try {
                matchLocalVariableValue.abortMatch();
            } catch (Exception eventArgumentExceptionParameterValue) {
                shared.DaoErrorHandler.log("LiveMatchesRegistry.abortAll", eventArgumentExceptionParameterValue);
            } finally {
                activeMatchesFieldReference.remove(matchLocalVariableValue.getGameId());
            }
        }
    }
}


