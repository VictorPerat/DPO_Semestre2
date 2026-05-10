package bussines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Agrupa la logica de los directo partidos.
 */
public final class LiveMatchesScoreboard {

    private static final LiveMatchesScoreboard INSTANCE = new LiveMatchesScoreboard();


    /**
     * Agrupa la logica de esta parte de la aplicacion.
     */
    public static final class ScoreSnapshot {
        private final int gameIdFieldReference;
        private final int leagueIdFieldReference;
        private final String homeNameFieldReference;
        private final String awayNameFieldReference;
        private final int homeGoalsFieldReference;
        private final int awayGoalsFieldReference;


        /**
         * Crea una instancia de scoresnapshot.
         *
         * @param gameIdParameterValue partido que usa la operacion.
         * @param leagueIdParameterValue liga que usa la operacion.
         * @param homeNameParameterValue nombre que usa la operacion.
         * @param awayNameParameterValue nombre que usa la operacion.
         * @param homeGoalsParameterValue dato de entrada de la operacion.
         * @param awayGoalsParameterValue dato de entrada de la operacion.
         */
        public ScoreSnapshot(int gameIdParameterValue,
                             int leagueIdParameterValue,
                             String homeNameParameterValue,
                             String awayNameParameterValue,
                             int homeGoalsParameterValue,
                             int awayGoalsParameterValue) {
            this.gameIdFieldReference = gameIdParameterValue;
            this.leagueIdFieldReference = leagueIdParameterValue;
            this.homeNameFieldReference = homeNameParameterValue;
            this.awayNameFieldReference = awayNameParameterValue;
            this.homeGoalsFieldReference = homeGoalsParameterValue;
            this.awayGoalsFieldReference = awayGoalsParameterValue;
        }


        /**
         * Devuelve el partido.
         *
         * @return el partido.
         */
        public int getGameId() { return gameIdFieldReference; }


        /**
         * Devuelve el liga.
         *
         * @return el liga.
         */
        public int getLeagueId() { return leagueIdFieldReference; }


        /**
         * Devuelve el nombre.
         *
         * @return el nombre.
         */
        public String getHomeName() { return homeNameFieldReference; }


        /**
         * Devuelve el nombre.
         *
         * @return el nombre.
         */
        public String getAwayName() { return awayNameFieldReference; }


        /**
         * Devuelve el contenido.
         *
         * @return el contenido.
         */
        public int getHomeGoals() { return homeGoalsFieldReference; }


        /**
         * Devuelve el contenido.
         *
         * @return el contenido.
         */
        public int getAwayGoals() { return awayGoalsFieldReference; }
    }


    /**
     * Define el contrato de esta parte de la aplicacion.
     */
    public interface ScoreboardListener {


        /**
         * Gestiona esta operacion.
         */
        void onScoreboardChanged();
    }

    private final ConcurrentHashMap<Integer, ScoreSnapshot> scoresFieldReference =
            new ConcurrentHashMap<>();

    private final CopyOnWriteArrayList<ScoreboardListener> listenersFieldReference =
            new CopyOnWriteArrayList<>();


    /**
     * Crea una instancia de los directo partidos.
     */
    private LiveMatchesScoreboard() {
    }


    /**
     * Devuelve el instancia.
     *
     * @return el instancia.
     */
    public static LiveMatchesScoreboard getInstance() {
        return INSTANCE;
    }


    /**
     * Actualiza el contenido.
     *
     * @param gameIdParameterValue partido que usa la operacion.
     * @param snapshotParameterValue dato de entrada de la operacion.
     */
    public void updateScore(int gameIdParameterValue, ScoreSnapshot snapshotParameterValue) {
        scoresFieldReference.put(gameIdParameterValue, snapshotParameterValue);
        notifyListeners();
    }


    /**
     * Elimina el contenido.
     *
     * @param gameIdParameterValue partido que usa la operacion.
     */
    public void remove(int gameIdParameterValue) {
        scoresFieldReference.remove(gameIdParameterValue);
        notifyListeners();
    }


    /**
     * Gestiona esta operacion.
     */
    public void clear() {
        scoresFieldReference.clear();
        notifyListeners();
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public List<ScoreSnapshot> getAll() {
        return new ArrayList<>(scoresFieldReference.values());
    }


    /**
     * Devuelve el liga.
     *
     * @param leagueIdsParameterValue liga que usa la operacion.
     * @return el liga.
     */
    public List<ScoreSnapshot> getByLeagueIds(Collection<Integer> leagueIdsParameterValue) {
        ArrayList<ScoreSnapshot> filteredLocalVariableValue = new ArrayList<>();
        if (leagueIdsParameterValue == null || leagueIdsParameterValue.isEmpty()) {
            return filteredLocalVariableValue;
        }
        for (ScoreSnapshot snapshotLocalVariableValue : scoresFieldReference.values()) {
            if (leagueIdsParameterValue.contains(snapshotLocalVariableValue.getLeagueId())) {
                filteredLocalVariableValue.add(snapshotLocalVariableValue);
            }
        }
        return filteredLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param listenerParameterValue listener que se registra.
     */
    public void addListener(ScoreboardListener listenerParameterValue) {
        listenersFieldReference.add(listenerParameterValue);
    }


    /**
     * Elimina el contenido.
     *
     * @param listenerParameterValue listener que se registra.
     */
    public void removeListener(ScoreboardListener listenerParameterValue) {
        listenersFieldReference.remove(listenerParameterValue);
    }


    /**
     * Gestiona esta operacion.
     */
    private void notifyListeners() {
        for (ScoreboardListener listenerLocalVariableValue : listenersFieldReference) {
            try {
                listenerLocalVariableValue.onScoreboardChanged();
            } catch (Exception eventArgumentExceptionParameterValue) {
                shared.DaoErrorHandler.log("LiveMatchesScoreboard", eventArgumentExceptionParameterValue);
            }
        }
    }
}


