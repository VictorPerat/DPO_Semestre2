package presentation.ControllerViews;

import bussines.managers.LeagueManager;
import bussines.managers.PlayerManager;
import bussines.objects.League;
import bussines.objects.LeagueListEntry;
import presentation.AppNavigator;
import presentation.Views.AvailableLeaguesView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.util.ArrayList;

/** Controlador de la tarjeta de ligas disponibles. */
public class AvailableLeaguesController implements ActionListener {
    private final AvailableLeaguesView viewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();
    private final AppNavigator navigatorFieldReference;
    private LeagueDetailController leagueDetailControllerHandlerFieldReference;
    private Timer autoRefreshTimerFieldReference;
    private static final int AUTO_REFRESH_INTERVAL_MS = 5_000;

    public AvailableLeaguesController(AvailableLeaguesView viewInterfaceParameterValue,
                                      PlayerManager playerProfileManagerServiceParameterValue,
                                      AppNavigator navigatorParameterValue,
                                      LeagueDetailController leagueDetailControllerHandlerParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.leagueDetailControllerHandlerFieldReference = leagueDetailControllerHandlerParameterValue;
        setupView();
    }

    public AvailableLeaguesController(AvailableLeaguesView viewInterfaceParameterValue,
                                      AdminMenuController adminMenuControllerHandlerParameterValue,
                                      PlayerManager playerProfileManagerServiceParameterValue) {
        this(viewInterfaceParameterValue, playerProfileManagerServiceParameterValue, AppNavigator.getInstance(), null);
    }

    public AvailableLeaguesController(AvailableLeaguesView viewInterfaceParameterValue,
                                      PlayerMenuController playerProfileMenuControllerHandlerParameterValue,
                                      PlayerManager playerProfileManagerServiceParameterValue) {
        this(viewInterfaceParameterValue, playerProfileManagerServiceParameterValue, AppNavigator.getInstance(), null);
    }

    public void setLeagueDetailController(LeagueDetailController controllerParameterValue) {
        this.leagueDetailControllerHandlerFieldReference = controllerParameterValue;
    }

    private void setupView() {
        viewInterfaceFieldReference.setConfigController(this);
        viewInterfaceFieldReference.setBackButtonListener(this);
        viewInterfaceFieldReference.addHierarchyListener(eventArgumentParameterValue -> {
            if ((eventArgumentParameterValue.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (viewInterfaceFieldReference.isShowing()) {
                    startAutoRefresh();
                } else {
                    stopAutoRefresh();
                }
            }
        });
        loadAndDisplayLeagues();
    }

    public void startAutoRefresh() {
        loadAndDisplayLeagues();
        if (autoRefreshTimerFieldReference != null && autoRefreshTimerFieldReference.isRunning()) { return; }
        autoRefreshTimerFieldReference = new Timer(AUTO_REFRESH_INTERVAL_MS, eventArgumentParameterValue -> loadAndDisplayLeagues());
        autoRefreshTimerFieldReference.start();
    }

    public void stopAutoRefresh() {
        if (autoRefreshTimerFieldReference != null) {
            autoRefreshTimerFieldReference.stop();
            autoRefreshTimerFieldReference = null;
        }
    }

    public void openLeagueDetails(League leagueReferenceParameterValue) {
        if (leagueDetailControllerHandlerFieldReference != null) {
            leagueDetailControllerHandlerFieldReference.openLeague(leagueReferenceParameterValue);
        } else {
            navigatorFieldReference.show(AppNavigator.LEAGUE_DETAIL);
        }
    }

    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();
        if ("BACK".equals(commandLocalVariableValue)) {
            String currentIdentifierLocalVariableValue = playerProfileManagerServiceFieldReference.getCurrentIdentifier();
            if (PlayerManager.ADMIN_IDENTIFIER.equalsIgnoreCase(currentIdentifierLocalVariableValue)) {
                navigatorFieldReference.show(AppNavigator.ADMIN_MENU);
            } else {
                navigatorFieldReference.show(AppNavigator.PLAYER_MENU);
            }
        } else if ("CONFIG".equals(commandLocalVariableValue)) {
            showConfigDialog();
        }
    }

    private void loadAndDisplayLeagues() {
        boolean isAdminLocalVariableValue = PlayerManager.ADMIN_IDENTIFIER.equalsIgnoreCase(playerProfileManagerServiceFieldReference.getCurrentIdentifier());
        String userTeamNameLocalVariableValue = playerProfileManagerServiceFieldReference.getCurrentPlayer() == null
                ? null
                : playerProfileManagerServiceFieldReference.getCurrentPlayer().getTeam();
        ArrayList<LeagueListEntry> entriesLocalVariableValue = leagueReferenceManagerServiceFieldReference.getLeagueListEntries(
                isAdminLocalVariableValue,
                userTeamNameLocalVariableValue
        );
        viewInterfaceFieldReference.displayLeagues(entriesLocalVariableValue, isAdminLocalVariableValue, this);
    }

    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        configDialogLocalVariableValue.registerController(eventArgumentParameterValue -> {
            configDialogLocalVariableValue.dispose();
            switch (eventArgumentParameterValue.getActionCommand()) {
                case Rounded.ConfigDialog.LOGOUT:
                    playerProfileManagerServiceFieldReference.logoutCurrentUser();
                    navigatorFieldReference.show(AppNavigator.LOGIN);
                    break;
                case Rounded.ConfigDialog.CHANGE_PASSWORD:
                    navigatorFieldReference.setChangePasswordReturnAction(() -> navigatorFieldReference.show(AppNavigator.AVAILABLE_LEAGUES));
                    navigatorFieldReference.show(AppNavigator.CHANGE_PASSWORD);
                    break;
                default:
                    break;
            }
        });
        configDialogLocalVariableValue.setBackButtonListener(eventArgumentParameterValue -> configDialogLocalVariableValue.dispose());
        configDialogLocalVariableValue.setVisible(true);
    }
}
