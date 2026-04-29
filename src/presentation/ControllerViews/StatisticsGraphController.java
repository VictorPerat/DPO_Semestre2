package presentation.ControllerViews;

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

/** Controlador del gráfico de estadísticas. */
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

    public StatisticsGraphController(StatisticsGraphView viewInterfaceParameterValue,
                                     MenuController menuControllerHandlerParameterValue,
                                     PlayerManager playerProfileManagerServiceParameterValue) {
        this(viewInterfaceParameterValue, playerProfileManagerServiceParameterValue, AppNavigator.getInstance());
    }

    public void refreshLeaguesList() {
        if (PlayerManager.ADMIN_IDENTIFIER.equalsIgnoreCase(playerProfileManagerServiceFieldReference.getCurrentIdentifier())) {
            leaguesFieldReference = leagueReferenceManagerServiceFieldReference.getAllLeagues();
        } else {
            Player actualPlayerProfileLocalVariableValue = playerProfileManagerServiceFieldReference.getCurrentPlayer();
            leaguesFieldReference = actualPlayerProfileLocalVariableValue == null
                    ? new ArrayList<>()
                    : leagueReferenceManagerServiceFieldReference.getLeaguesByUserTeam(actualPlayerProfileLocalVariableValue.getTeam());
        }
        viewInterfaceFieldReference.setLeagues(leaguesFieldReference, this);
    }

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

    public void showLeagueData(int leagueReferenceIdentifierParameterValue,
                               String leagueReferenceDisplayNameParameterValue) {
        currentLeagueIdFieldReference = leagueReferenceIdentifierParameterValue;
        currentLeagueNameFieldReference = leagueReferenceDisplayNameParameterValue;
        refreshChartDataFromDatabase();
        startChartAutoRefresh();
    }

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

    public void startChartAutoRefresh() {
        if (currentLeagueIdFieldReference == -1) { return; }
        if (chartAutoRefreshTimerFieldReference != null && chartAutoRefreshTimerFieldReference.isRunning()) { return; }
        chartAutoRefreshTimerFieldReference = new Timer(CHART_REFRESH_INTERVAL_MS, eventArgumentParameterValue -> refreshChartDataFromDatabase());
        chartAutoRefreshTimerFieldReference.start();
    }

    public void stopChartAutoRefresh() {
        if (chartAutoRefreshTimerFieldReference != null) {
            chartAutoRefreshTimerFieldReference.stop();
            chartAutoRefreshTimerFieldReference = null;
        }
    }

    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        configDialogLocalVariableValue.registerController(eventArgumentParameterValue -> {
            configDialogLocalVariableValue.dispose();
            if (Rounded.ConfigDialog.LOGOUT.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.show(AppNavigator.LOGIN);
            } else if (Rounded.ConfigDialog.CHANGE_PASSWORD.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.setChangePasswordReturnAction(() -> navigatorFieldReference.show(AppNavigator.STATISTICS));
                navigatorFieldReference.show(AppNavigator.CHANGE_PASSWORD);
            }
        });
        configDialogLocalVariableValue.setBackButtonListener(eventArgumentParameterValue -> configDialogLocalVariableValue.dispose());
        configDialogLocalVariableValue.setVisible(true);
    }
}
