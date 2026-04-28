package bussines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Marcador en memoria de los partidos que se están jugando en directo.
 *
 * Sirve de fuente única para el widget global de "Live Matches"
 * (apartado 2.9 del enunciado). Cada vez que la simulación marca un
 * gol, llama a {@link #updateScore(int, ScoreSnapshot)} con el
 * snapshot actualizado; cuando el partido acaba, se llama a
 * {@link #remove(int)} para que ya no se muestre.
 *
 * Es thread-safe porque la simulación corre en un hilo dedicado por
 * partido y el widget consulta desde el EDT de Swing.
 */
public final class LiveMatchesScoreboard {

    private static final LiveMatchesScoreboard INSTANCE = new LiveMatchesScoreboard();

    /** Snapshot del marcador de un partido en directo. */
    public static final class ScoreSnapshot {
        private final int gameIdFieldReference;
        private final int leagueIdFieldReference;
        private final String homeNameFieldReference;
        private final String awayNameFieldReference;
        private final int homeGoalsFieldReference;
        private final int awayGoalsFieldReference;

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

        public int getGameId() { return gameIdFieldReference; }
        public int getLeagueId() { return leagueIdFieldReference; }
        public String getHomeName() { return homeNameFieldReference; }
        public String getAwayName() { return awayNameFieldReference; }
        public int getHomeGoals() { return homeGoalsFieldReference; }
        public int getAwayGoals() { return awayGoalsFieldReference; }
    }

    /** Listener al que se notifica cuando cambia el scoreboard. */
    public interface ScoreboardListener {
        void onScoreboardChanged();
    }

    private final ConcurrentHashMap<Integer, ScoreSnapshot> scoresFieldReference =
            new ConcurrentHashMap<>();

    private final CopyOnWriteArrayList<ScoreboardListener> listenersFieldReference =
            new CopyOnWriteArrayList<>();

    private LiveMatchesScoreboard() {
    }

    public static LiveMatchesScoreboard getInstance() {
        return INSTANCE;
    }

    /**
     * Inserta o actualiza el marcador de un partido y notifica a los
     * listeners.
     */
    public void updateScore(int gameIdParameterValue, ScoreSnapshot snapshotParameterValue) {
        scoresFieldReference.put(gameIdParameterValue, snapshotParameterValue);
        notifyListeners();
    }

    /**
     * Elimina un partido del marcador (típicamente al acabar).
     */
    public void remove(int gameIdParameterValue) {
        scoresFieldReference.remove(gameIdParameterValue);
        notifyListeners();
    }

    /**
     * Vacía el marcador. Útil al hacer logout.
     */
    public void clear() {
        scoresFieldReference.clear();
        notifyListeners();
    }

    /** Devuelve una copia inmutable de los partidos en curso. */
    public List<ScoreSnapshot> getAll() {
        return new ArrayList<>(scoresFieldReference.values());
    }

    /** Devuelve solo los partidos de las ligas indicadas. */
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

    public void addListener(ScoreboardListener listenerParameterValue) {
        listenersFieldReference.add(listenerParameterValue);
    }

    public void removeListener(ScoreboardListener listenerParameterValue) {
        listenersFieldReference.remove(listenerParameterValue);
    }

    private void notifyListeners() {
        for (ScoreboardListener listenerLocalVariableValue : listenersFieldReference) {
            try {
                listenerLocalVariableValue.onScoreboardChanged();
            } catch (Exception eventArgumentExceptionParameterValue) {
                eventArgumentExceptionParameterValue.printStackTrace();
            }
        }
    }
}
