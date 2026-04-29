package bussines;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registro en memoria de los partidos que se están simulando ahora
 * mismo. Permite localizar el controlador asociado a un gameId para
 * abortar la simulación cuando el admin borra una liga (apartado 2.10)
 * o un equipo (apartado 2.11) del enunciado.
 *
 * El controlador concreto (LiveMatchController) implementa
 * {@link AbortableMatch} para evitar que esta clase, que vive en la
 * capa de negocio, dependa de la capa de presentación.
 */
public final class LiveMatchesRegistry {

    /**
     * Contrato mínimo para que un controlador de partido se pueda
     * abortar desde fuera. Permite invertir la dependencia: la capa
     * de negocio solo conoce esta interfaz, no la clase concreta.
     */
    public interface AbortableMatch {
        int getGameId();
        int getLeagueId();
        String getHomeTeamName();
        String getAwayTeamName();
        void showMatchWindow();
        void abortMatch();
    }

    private static final LiveMatchesRegistry INSTANCE = new LiveMatchesRegistry();

    private final ConcurrentHashMap<Integer, AbortableMatch> activeMatchesFieldReference =
            new ConcurrentHashMap<>();

    private LiveMatchesRegistry() {
    }

    public static LiveMatchesRegistry getInstance() {
        return INSTANCE;
    }

    /** Registra un partido en curso (al iniciarse la simulación). */
    public void register(AbortableMatch matchParameterValue) {
        if (matchParameterValue != null) {
            activeMatchesFieldReference.put(matchParameterValue.getGameId(), matchParameterValue);
        }
    }

    /** Quita un partido del registro (al acabar normalmente). */
    public void unregister(int gameIdParameterValue) {
        activeMatchesFieldReference.remove(gameIdParameterValue);
    }

    /** Indica si ya existe una simulación activa para ese partido. */
    public boolean isRegistered(int gameIdParameterValue) {
        return activeMatchesFieldReference.containsKey(gameIdParameterValue);
    }

    /**
     * Intenta mostrar la ventana asociada al partido en curso.
     *
     * @return true si el partido estaba registrado y se pudo delegar la
     *         apertura de su ventana; false si no existe simulación
     *         activa para ese gameId.
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
     * Aborta todos los partidos en curso de una liga concreta. Útil
     * antes de borrar una liga (apartado 2.10).
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
     * Aborta todos los partidos en curso en los que participa un equipo
     * concreto. Útil antes de borrar un equipo (apartado 2.11).
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

    private void abortAll(List<AbortableMatch> matchesParameterValue) {
        for (AbortableMatch matchLocalVariableValue : matchesParameterValue) {
            try {
                matchLocalVariableValue.abortMatch();
            } catch (Exception eventArgumentExceptionParameterValue) {
                eventArgumentExceptionParameterValue.printStackTrace();
            } finally {
                activeMatchesFieldReference.remove(matchLocalVariableValue.getGameId());
            }
        }
    }
}
