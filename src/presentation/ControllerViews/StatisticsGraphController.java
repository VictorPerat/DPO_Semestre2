package presentation.ControllerViews;

import bussines.managers.ConfigManager;
import bussines.managers.LeagueManager;
import bussines.managers.PlayerManager;
import bussines.objects.League;
import bussines.objects.Player;
import presentation.AppNavigator;
import presentation.Views.StatisticsGraphView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.util.ArrayList;
import java.util.List;


/**
 * Coordina la pantalla del estadisticas grafica.
 */
public class StatisticsGraphController implements ActionListener {
    private final StatisticsGraphView viewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();
    private final AppNavigator navigatorFieldReference;
    private List<League> leaguesFieldReference = new ArrayList<>();
    private int currentLeagueIdFieldReference = -1;
    private String currentLeagueNameFieldReference = "";
    private Timer chartAutoRefreshTimerFieldReference;
    private static final int CHART_REFRESH_INTERVAL_MS = 5_000;


    /**
     * Crea una instancia de el estadisticas grafica.
     *
     * @param viewInterfaceParameterValue vista que usa la operacion.
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
    public StatisticsGraphController(StatisticsGraphView viewInterfaceParameterValue,
                                     PlayerManager playerProfileManagerServiceParameterValue,
                                     AppNavigator navigatorParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.viewInterfaceFieldReference.registerController(this);
        refreshLeaguesList();
        this.viewInterfaceFieldReference.addHierarchyListener(eventArgumentParameterValue -> {
            if ((eventArgumentParameterValue.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (viewInterfaceFieldReference.isShowing()) { startChartAutoRefresh(); }
                else { stopChartAutoRefresh(); }
            }
        });
    }


    /**
     * Gestiona esta operacion.
     */
    public void refreshLeaguesList() {
        if (ConfigManager.getAdminIdentifier().equalsIgnoreCase(playerProfileManagerServiceFieldReference.getCurrentIdentifier())) {
            leaguesFieldReference = leagueReferenceManagerServiceFieldReference.getAllLeagues();
        } else {
            Player actualPlayerProfileLocalVariableValue = playerProfileManagerServiceFieldReference.getCurrentPlayer();
            leaguesFieldReference = actualPlayerProfileLocalVariableValue == null
                    ? new ArrayList<>()
                    : leagueReferenceManagerServiceFieldReference.getLeaguesByUserTeam(actualPlayerProfileLocalVariableValue.getTeam());
        }
        viewInterfaceFieldReference.setLeagues(leaguesFieldReference, this);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param eventArgumentParameterValue dato de entrada de la operacion.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();
        if ("BACK".equals(commandLocalVariableValue)) {
            navigatorFieldReference.show(AppNavigator.LEAGUE_DETAIL);
        } else if ("CONFIG".equals(commandLocalVariableValue)) {
            showConfigDialog();
        } else if (commandLocalVariableValue != null && commandLocalVariableValue.startsWith("LEAGUE_")) {
            int indexLocalVariableValue = Integer.parseInt(commandLocalVariableValue.split("_")[1]);
            if (indexLocalVariableValue >= 0 && indexLocalVariableValue < leaguesFieldReference.size()) {
                League selectedLeagueReferenceLocalVariableValue = leaguesFieldReference.get(indexLocalVariableValue);
                showLeagueData(selectedLeagueReferenceLocalVariableValue.getId(), selectedLeagueReferenceLocalVariableValue.getName());
            }
        }
    }


    /**
     * Muestra el liga.
     *
     * @param leagueReferenceIdentifierParameterValue liga identificador.
     * @param leagueReferenceDisplayNameParameterValue nombre de la liga.
     */
    public void showLeagueData(int leagueReferenceIdentifierParameterValue,
                               String leagueReferenceDisplayNameParameterValue) {
        currentLeagueIdFieldReference = leagueReferenceIdentifierParameterValue;
        currentLeagueNameFieldReference = leagueReferenceDisplayNameParameterValue;
        refreshChartDataFromDatabase();
        startChartAutoRefresh();
    }


    /**
     * Gestiona esta operacion.
     */
    private void refreshChartDataFromDatabase() {
        if (currentLeagueIdFieldReference == -1) { return; }
        LeagueManager.StandingsTimeline timelineLocalVariableValue = leagueReferenceManagerServiceFieldReference.computeStandingsTimeline(currentLeagueIdFieldReference);
        viewInterfaceFieldReference.updateChartData(
                currentLeagueNameFieldReference,
                timelineLocalVariableValue.getCumulativePoints(),
                timelineLocalVariableValue.getTotalRounds(),
                timelineLocalVariableValue.getTeamNames(),
                timelineLocalVariableValue.getTeamNames().length
        );
    }


    /**
     * Gestiona esta operacion.
     */
    public void startChartAutoRefresh() {
        if (currentLeagueIdFieldReference == -1) { return; }
        if (chartAutoRefreshTimerFieldReference != null && chartAutoRefreshTimerFieldReference.isRunning()) { return; }
        chartAutoRefreshTimerFieldReference = new Timer(CHART_REFRESH_INTERVAL_MS, eventArgumentParameterValue -> refreshChartDataFromDatabase());
        chartAutoRefreshTimerFieldReference.start();
    }


    /**
     * Gestiona esta operacion.
     */
    public void stopChartAutoRefresh() {
        if (chartAutoRefreshTimerFieldReference != null) {
            chartAutoRefreshTimerFieldReference.stop();
            chartAutoRefreshTimerFieldReference = null;
        }
    }


    /**
     * Muestra el configuracion dialogo.
     */
    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue =
                Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        presentation.AccountSettingsWidgetService.configureDialogForCurrentSession(
                configDialogLocalVariableValue,
                AppNavigator.STATISTICS
        );
        configDialogLocalVariableValue.setVisible(true);
    }
}


