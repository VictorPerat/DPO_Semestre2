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
 * Coordina la pantalla del directo partidos widget.
 */
public class LiveMatchesWidgetController implements LiveMatchesScoreboard.ScoreboardListener {

    private static final int REFRESH_INTERVAL_MS = 2_000;

    private final LiveMatchesWidget widgetViewFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final LeagueManager leagueReferenceManagerServiceFieldReference;

    private final Timer refreshTimerFieldReference;
    private final boolean isAdminViewerFieldReference;


    /**
     * Crea una instancia de el directo partidos widget.
     *
     * @param widgetViewParameterValue widget vista.
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     * @param isAdminViewerParameterValue administrador que usa la operacion.
     */
    public LiveMatchesWidgetController(LiveMatchesWidget widgetViewParameterValue,
                                       PlayerManager playerProfileManagerServiceParameterValue,
                                       boolean isAdminViewerParameterValue) {
        this.widgetViewFieldReference = widgetViewParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.leagueReferenceManagerServiceFieldReference = new LeagueManager();
        this.isAdminViewerFieldReference = isAdminViewerParameterValue;


        LiveMatchesScoreboard.getInstance().addListener(this);


        refreshTimerFieldReference = new Timer(
                REFRESH_INTERVAL_MS,
                eventArgumentParameterValueRefresh -> refreshNow()
        );
        refreshTimerFieldReference.setRepeats(true);
        refreshTimerFieldReference.start();


        refreshNow();
    }


    /**
     * Gestiona esta operacion.
     */
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


    /**
     * Gestiona esta operacion.
     */
    @Override
    public void onScoreboardChanged() {
        refreshNow();
    }


    /**
     * Gestiona esta operacion.
     */
    public void shutdown() {
        if (refreshTimerFieldReference != null) {
            refreshTimerFieldReference.stop();
        }
        LiveMatchesScoreboard.getInstance().removeListener(this);
        widgetViewFieldReference.dispose();
    }


    /**
     * Busca el liga actual jugador.
     *
     * @return resultado de la busqueda.
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


