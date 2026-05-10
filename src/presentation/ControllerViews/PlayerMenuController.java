package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.LiveMatchesWidgetService;
import presentation.Views.PlayerMenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import presentation.AccountSettingsWidgetService;


/**
 * Coordina la pantalla del jugador menu.
 */
public class PlayerMenuController implements ActionListener, MenuController, DeletePlayerListener {
    private final PlayerMenuView playerProfileMenuScreenInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final AppNavigator navigatorFieldReference;


    /**
     * Crea una instancia de el jugador menu.
     *
     * @param playerProfileMenuScreenInterfaceParameterValue jugador perfil menu pantalla.
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
    public PlayerMenuController(PlayerMenuView playerProfileMenuScreenInterfaceParameterValue,
                                PlayerManager playerProfileManagerServiceParameterValue,
                                AppNavigator navigatorParameterValue) {
        this.playerProfileMenuScreenInterfaceFieldReference = playerProfileMenuScreenInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.playerProfileMenuScreenInterfaceFieldReference.registerController(this);
    }


    /**
     * Muestra el menu.
     */
    @Override public void showMenu() { showPlayerMenu(); }


    /**
     * Gestiona esta operacion.
     */
    @Override public void onPlayersDeleted() { showPlayerMenu(); }


    /**
     * Gestiona esta operacion.
     */
    @Override public void returnToMenu() { showPlayerMenu(); }


    /**
     * Gestiona esta operacion.
     */
    @Override
    public void handleLogout() {
        LiveMatchesWidgetService.hide();
        AccountSettingsWidgetService.hide();
        playerProfileManagerServiceFieldReference.setCurrentIdentifier(null);
        navigatorFieldReference.show(AppNavigator.LOGIN);
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
            case PlayerMenuView.WATCH_MATCHES:
                handleWatchMatches();
                break;
            case PlayerMenuView.VIEW_LEAGUES:
                handleViewLeagues();
                break;
            case PlayerMenuView.DELETE_PLAYER:
                handleDeleteAccount();
                break;
            case PlayerMenuView.LOGOUT:
                handleLogout();
                break;
            default:
                break;
        }
    }


    /**
     * Gestiona esta operacion.
     */
    private void handleWatchMatches() {
        navigatorFieldReference.show(AppNavigator.LIVE_MATCHES);
    }


    /**
     * Gestiona esta operacion.
     */
    private void handleViewLeagues() {
        navigatorFieldReference.show(AppNavigator.AVAILABLE_LEAGUES);
    }


    /**
     * Muestra el jugador menu.
     */
    public void showPlayerMenu() {
        navigatorFieldReference.show(AppNavigator.PLAYER_MENU);
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
                AppNavigator.PLAYER_MENU
        );
        configDialogLocalVariableValue.setVisible(true);
    }


    /**
     * Gestiona esta operacion.
     */
    private void handleDeleteAccount() {
        int confirmLocalVariableValue = JOptionPane.showConfirmDialog(
                navigatorFieldReference.getMainView(),
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmLocalVariableValue != JOptionPane.YES_OPTION) {
            return;
        }


        playerProfileManagerServiceFieldReference.purgeUserDataFromDisk();


        boolean successLocalVariableValue =
                playerProfileManagerServiceFieldReference.deleteCurrentPlayer();


        playerProfileManagerServiceFieldReference.setCurrentIdentifier(null);
        LiveMatchesWidgetService.hide();
        AccountSettingsWidgetService.hide();
        navigatorFieldReference.show(AppNavigator.LOGIN);

        if (!successLocalVariableValue) {
            JOptionPane.showMessageDialog(
                    null,
                    "Account deletion encountered an error, but you have been logged out.",
                    "Delete Account",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}


