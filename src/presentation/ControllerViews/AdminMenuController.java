package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.LiveMatchesWidgetService;
import presentation.Views.AdminMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Coordina la pantalla del administrador menu.
 */
public class AdminMenuController implements ActionListener, DeletePlayerListener, MenuController, LeagueViewActions {
    private final AdminMenuView adminMenuViewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final AppNavigator navigatorFieldReference;


    /**
     * Crea una instancia de el administrador menu.
     *
     * @param adminMenuViewInterfaceParameterValue administrador menu vista.
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
    public AdminMenuController(AdminMenuView adminMenuViewInterfaceParameterValue,
                               PlayerManager playerProfileManagerServiceParameterValue,
                               AppNavigator navigatorParameterValue) {
        this.adminMenuViewInterfaceFieldReference = adminMenuViewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.adminMenuViewInterfaceFieldReference.registerController(this);
    }


    /**
     * Muestra el menu.
     */
    @Override
    public void showMenu() {
        showAdminMenu();
    }


    /**
     * Gestiona esta operacion.
     */
    @Override
    public void returnToMenu() {
        showAdminMenu();
    }


    /**
     * Gestiona esta operacion.
     */
    @Override
    public void onPlayersDeleted() {
        showAdminMenu();
    }


    /**
     * Gestiona esta operacion.
     *
     * @param eventArgumentParameterValue dato de entrada de la operacion.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();

        switch (commandLocalVariableValue) {
            case AdminMenuView.CREATE_LEAGUE:
                handleCreateLeague();
                break;
            case AdminMenuView.DELETE_LEAGUE:
                handleDeleteLeague();
                break;
            case AdminMenuView.VIEW_LEAGUES:
                handleViewLeagues();
                break;
            case AdminMenuView.CREATE_TEAM:
                handleCreateTeam();
                break;
            case AdminMenuView.DELETE_TEAM:
                handleDeleteTeam();
                break;
            case AdminMenuView.LOGOUT:
                handleLogout();
                break;
            case AdminMenuView.DELETE_PLAYER:
                handleDeletePlayer();
                break;
            case AdminMenuView.VIEW_GAMES:
                handleGames();
                break;
            default:
                break;
        }
    }


    /**
     * Gestiona esta operacion.
     */
    public void handleStats() {
        navigatorFieldReference.show(AppNavigator.STATISTICS);
    }


    /**
     * Gestiona esta operacion.
     */
    @Override
    public void handleCreateLeague() {
        navigatorFieldReference.show(AppNavigator.CREATE_LEAGUE);
    }


    /**
     * Gestiona esta operacion.
     */
    @Override
    public void handleDeleteLeague() {
        navigatorFieldReference.show(AppNavigator.DELETE_LEAGUE);
    }


    /**
     * Gestiona esta operacion.
     */
    @Override
    public void handleViewLeagues() {
        navigatorFieldReference.show(AppNavigator.AVAILABLE_LEAGUES);
    }


    /**
     * Gestiona esta operacion.
     */
    @Override
    public void handleLogout() {
        LiveMatchesWidgetService.hide();
        playerProfileManagerServiceFieldReference.setCurrentIdentifier(null);
        navigatorFieldReference.show(AppNavigator.LOGIN);
    }


    /**
     * Muestra el administrador menu.
     */
    public void showAdminMenu() {
        navigatorFieldReference.show(AppNavigator.ADMIN_MENU);
    }


    /**
     * Gestiona esta operacion.
     */
    private void handleDeletePlayer() {
        navigatorFieldReference.show(AppNavigator.DELETE_PLAYER);
    }


    /**
     * Gestiona esta operacion.
     */
    private void handleDeleteTeam() {
        navigatorFieldReference.show(AppNavigator.DELETE_TEAM);
    }


    /**
     * Gestiona esta operacion.
     */
    private void handleCreateTeam() {
        navigatorFieldReference.show(AppNavigator.CREATE_TEAM);
    }


    /**
     * Gestiona esta operacion.
     */
    private void handleGames() {
        navigatorFieldReference.show(AppNavigator.LIVE_MATCHES);
    }


    /**
     * Devuelve el jugador.
     *
     * @return el jugador.
     */
    public PlayerManager getPlayerManager() {
        return playerProfileManagerServiceFieldReference;
    }


    /**
     * Muestra el configuracion dialogo.
     */
    @Override
    public void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue =
                Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        presentation.AccountSettingsWidgetService.configureDialogForCurrentSession(
                configDialogLocalVariableValue,
                AppNavigator.ADMIN_MENU
        );
        configDialogLocalVariableValue.setVisible(true);
    }

}


