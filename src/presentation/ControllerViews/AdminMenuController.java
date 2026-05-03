package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.LiveMatchesWidgetService;
import presentation.Views.AdminMenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Controlador del menú de administrador dentro del CardLayout. */
public class AdminMenuController implements ActionListener, DeletePlayerListener, MenuController, LeagueViewActions {
    private final AdminMenuView adminMenuViewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final AppNavigator navigatorFieldReference;

    public AdminMenuController(AdminMenuView adminMenuViewInterfaceParameterValue,
                               PlayerManager playerProfileManagerServiceParameterValue,
                               AppNavigator navigatorParameterValue) {
        this.adminMenuViewInterfaceFieldReference = adminMenuViewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.adminMenuViewInterfaceFieldReference.registerController(this);
    }

    public AdminMenuController(AdminMenuView adminMenuViewInterfaceParameterValue,
                               PlayerManager playerProfileManagerServiceParameterValue) {
        this(
                adminMenuViewInterfaceParameterValue,
                playerProfileManagerServiceParameterValue,
                AppNavigator.getInstance()
        );
    }

    @Override
    public void showMenu() {
        showAdminMenu();
    }

    @Override
    public void returnToMenu() {
        showAdminMenu();
    }

    @Override
    public void onPlayersDeleted() {
        showAdminMenu();
    }

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

    public void handleStats() {
        navigatorFieldReference.show(AppNavigator.STATISTICS);
    }

    @Override
    public void handleCreateLeague() {
        navigatorFieldReference.show(AppNavigator.CREATE_LEAGUE);
    }

    @Override
    public void handleDeleteLeague() {
        navigatorFieldReference.show(AppNavigator.DELETE_LEAGUE);
    }

    @Override
    public void handleViewLeagues() {
        navigatorFieldReference.show(AppNavigator.AVAILABLE_LEAGUES);
    }

    @Override
    public void handleLogout() {
        LiveMatchesWidgetService.hide();
        playerProfileManagerServiceFieldReference.setCurrentIdentifier(null);
        navigatorFieldReference.show(AppNavigator.LOGIN);
    }

    public void showAdminMenu() {
        navigatorFieldReference.show(AppNavigator.ADMIN_MENU);
    }

    private void handleDeletePlayer() {
        navigatorFieldReference.show(AppNavigator.DELETE_PLAYER);
    }

    private void handleDeleteTeam() {
        navigatorFieldReference.show(AppNavigator.DELETE_TEAM);
    }

    private void handleCreateTeam() {
        navigatorFieldReference.show(AppNavigator.CREATE_TEAM);
    }

    private void handleGames() {
        navigatorFieldReference.show(AppNavigator.LIVE_MATCHES);
    }

    public PlayerManager getPlayerManager() {
        return playerProfileManagerServiceFieldReference;
    }

    @Override
    public void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue =
                Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());

        configDialogLocalVariableValue.registerController(eventArgumentParameterValue -> {
            configDialogLocalVariableValue.dispose();

            switch (eventArgumentParameterValue.getActionCommand()) {
                case Rounded.ConfigDialog.LOGOUT:
                    handleLogout();
                    break;
                case Rounded.ConfigDialog.DELETE_ACCOUNT:
                    handleDeleteAccount();
                    break;
                case Rounded.ConfigDialog.CHANGE_PASSWORD:
                    openChangePasswordView();
                    break;
                default:
                    break;
            }
        });

        configDialogLocalVariableValue.setBackButtonListener(
                eventArgumentParameterValue -> configDialogLocalVariableValue.dispose()
        );

        configDialogLocalVariableValue.setVisible(true);
    }

    private void openChangePasswordView() {
        navigatorFieldReference.setChangePasswordReturnAction(
                () -> navigatorFieldReference.show(AppNavigator.ADMIN_MENU)
        );
        navigatorFieldReference.show(AppNavigator.CHANGE_PASSWORD);
    }

    private void handleDeleteAccount() {
        JOptionPane.showMessageDialog(
                navigatorFieldReference.getMainView(),
                "The admin account cannot be deleted."
        );
    }
}