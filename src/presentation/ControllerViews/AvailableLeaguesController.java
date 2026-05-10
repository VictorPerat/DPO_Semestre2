package presentation.ControllerViews;

import bussines.managers.ConfigManager;
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


/**
 * Coordina la pantalla de las ligas disponibles.
 */
public class AvailableLeaguesController implements ActionListener {
    private final AvailableLeaguesView viewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();
    private final AppNavigator navigatorFieldReference;
    private LeagueDetailController leagueDetailControllerHandlerFieldReference;
    private Timer autoRefreshTimerFieldReference;
    private static final int AUTO_REFRESH_INTERVAL_MS = 5_000;


    /**
     * Crea una instancia de los disponibles ligas.
     *
     * @param viewInterfaceParameterValue vista que usa la operacion.
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     * @param navigatorParameterValue navegacion que usa la operacion.
     * @param leagueDetailControllerHandlerParameterValue liga detalle.
     */
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


    /**
     * Actualiza el liga detalle.
     *
     * @param controllerParameterValue dato de entrada de la operacion.
     */
    public void setLeagueDetailController(LeagueDetailController controllerParameterValue) {
        this.leagueDetailControllerHandlerFieldReference = controllerParameterValue;
    }


    /**
     * Actualiza el vista.
     */
    private void setupView() {
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


    /**
     * Gestiona esta operacion.
     */
    public void startAutoRefresh() {
        loadAndDisplayLeagues();
        if (autoRefreshTimerFieldReference != null && autoRefreshTimerFieldReference.isRunning()) { return; }
        autoRefreshTimerFieldReference = new Timer(AUTO_REFRESH_INTERVAL_MS, eventArgumentParameterValue -> loadAndDisplayLeagues());
        autoRefreshTimerFieldReference.start();
    }


    /**
     * Gestiona esta operacion.
     */
    public void stopAutoRefresh() {
        if (autoRefreshTimerFieldReference != null) {
            autoRefreshTimerFieldReference.stop();
            autoRefreshTimerFieldReference = null;
        }
    }


    /**
     * Abre el liga.
     *
     * @param leagueReferenceParameterValue liga que usa la operacion.
     */
    public void openLeagueDetails(League leagueReferenceParameterValue) {
        if (leagueDetailControllerHandlerFieldReference != null) {
            leagueDetailControllerHandlerFieldReference.openLeague(leagueReferenceParameterValue);
        } else {
            navigatorFieldReference.show(AppNavigator.LEAGUE_DETAIL);
        }
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
            String currentIdentifierLocalVariableValue = playerProfileManagerServiceFieldReference.getCurrentIdentifier();
            if (ConfigManager.getAdminIdentifier().equalsIgnoreCase(currentIdentifierLocalVariableValue)) {
                navigatorFieldReference.show(AppNavigator.ADMIN_MENU);
            } else {
                navigatorFieldReference.show(AppNavigator.PLAYER_MENU);
            }
        } else if ("CONFIG".equals(commandLocalVariableValue)) {
            showConfigDialog();
        }
    }


    /**
     * Carga los ligas.
     */
    private void loadAndDisplayLeagues() {
        boolean isAdminLocalVariableValue = ConfigManager.getAdminIdentifier().equalsIgnoreCase(playerProfileManagerServiceFieldReference.getCurrentIdentifier());
        String userTeamNameLocalVariableValue = playerProfileManagerServiceFieldReference.getCurrentPlayer() == null
                ? null
                : playerProfileManagerServiceFieldReference.getCurrentPlayer().getTeam();
        ArrayList<LeagueListEntry> entriesLocalVariableValue = leagueReferenceManagerServiceFieldReference.getLeagueListEntries(
                isAdminLocalVariableValue,
                userTeamNameLocalVariableValue
        );
        viewInterfaceFieldReference.displayLeagues(entriesLocalVariableValue, isAdminLocalVariableValue, this);
    }


    /**
     * Muestra el configuracion dialogo.
     */
    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue =
                Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        presentation.AccountSettingsWidgetService.configureDialogForCurrentSession(
                configDialogLocalVariableValue,
                AppNavigator.AVAILABLE_LEAGUES
        );
        configDialogLocalVariableValue.setVisible(true);
    }
}


