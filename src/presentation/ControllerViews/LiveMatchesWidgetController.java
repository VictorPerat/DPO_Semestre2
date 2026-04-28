package presentation.ControllerViews;

import bussines.LiveMatchesScoreboard;
import bussines.managers.LeagueManager;
import bussines.managers.PlayerManager;
import bussines.objects.League;
import bussines.objects.Player;
import presentation.Views.LiveMatchesWidget;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Controlador del widget global de partidos en directo.
 *
 * Mantiene el {@link LiveMatchesWidget} sincronizado con el
 * {@link LiveMatchesScoreboard}: cada vez que el scoreboard cambia o
 * el timer dispara, el widget recibe la lista actualizada.
 *
 * Aplica la regla del apartado 2.9 del enunciado:
 *  - Admin → ve todos los partidos en curso.
 *  - Jugador → solo los partidos de las ligas en las que participa
 *    su equipo.
 */
public class LiveMatchesWidgetController implements LiveMatchesScoreboard.ScoreboardListener {

    private static final int REFRESH_INTERVAL_MS = 2_000;

    private final LiveMatchesWidget widgetViewFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final LeagueManager leagueReferenceManagerServiceFieldReference;

    private final Timer refreshTimerFieldReference;
    private final boolean isAdminViewerFieldReference;

    public LiveMatchesWidgetController(LiveMatchesWidget widgetViewParameterValue,
                                       PlayerManager playerProfileManagerServiceParameterValue,
                                       boolean isAdminViewerParameterValue) {
        this.widgetViewFieldReference = widgetViewParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.leagueReferenceManagerServiceFieldReference = new LeagueManager();
        this.isAdminViewerFieldReference = isAdminViewerParameterValue;

        // Subscribirse al scoreboard para refrescar inmediatamente al
        // recibir cambios (gol, fin de partido).
        LiveMatchesScoreboard.getInstance().addListener(this);

        // Timer secundario para asegurar refresco aunque no haya
        // listeners disparados (defensa frente a eventos perdidos).
        refreshTimerFieldReference = new Timer(
                REFRESH_INTERVAL_MS,
                eventArgumentParameterValueRefresh -> refreshNow()
        );
        refreshTimerFieldReference.setRepeats(true);
        refreshTimerFieldReference.start();

        // Render inicial
        refreshNow();
    }

    /** Vuelve a calcular y enviar la lista de partidos al widget. */
    public void refreshNow() {
        List<LiveMatchesScoreboard.ScoreSnapshot> snapshotsLocalVariableValue;

        if (isAdminViewerFieldReference) {
            snapshotsLocalVariableValue = LiveMatchesScoreboard.getInstance().getAll();
        } else {
            snapshotsLocalVariableValue =
                    LiveMatchesScoreboard.getInstance().getByLeagueIds(
                            findLeagueIdsForCurrentPlayer()
                    );
        }

        widgetViewFieldReference.renderMatches(snapshotsLocalVariableValue);
    }

    @Override
    public void onScoreboardChanged() {
        refreshNow();
    }

    /**
     * Detiene el widget: cierra ventana, para timer, libera listener.
     * Se llama al hacer logout o cerrar sesión.
     */
    public void shutdown() {
        if (refreshTimerFieldReference != null) {
            refreshTimerFieldReference.stop();
        }
        LiveMatchesScoreboard.getInstance().removeListener(this);
        widgetViewFieldReference.dispose();
    }

    /**
     * Devuelve los ids de las ligas del equipo del jugador actual.
     */
    private HashSet<Integer> findLeagueIdsForCurrentPlayer() {
        HashSet<Integer> resultLocalVariableValue = new HashSet<>();

        Player currentPlayerLocalVariableValue =
                playerProfileManagerServiceFieldReference.getCurrentPlayer();
        if (currentPlayerLocalVariableValue == null) {
            return resultLocalVariableValue;
        }

        ArrayList<League> playerLeaguesLocalVariableValue =
                leagueReferenceManagerServiceFieldReference.getLeaguesByUserTeam(
                        currentPlayerLocalVariableValue.getTeam()
                );

        for (League leagueLocalVariableValue : playerLeaguesLocalVariableValue) {
            int leagueIdLocalVariableValue =
                    leagueReferenceManagerServiceFieldReference.getLeagueIdByName(
                            leagueLocalVariableValue.getName()
                    );
            if (leagueIdLocalVariableValue != -1) {
                resultLocalVariableValue.add(leagueIdLocalVariableValue);
            }
        }

        return resultLocalVariableValue;
    }
}
