package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.LiveMatchesWidgetService;
import presentation.Views.PlayerMenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import presentation.AccountSettingsWidgetService;

/** Controlador del menú de jugador dentro del CardLayout. */
public class PlayerMenuController implements ActionListener, MenuController, DeletePlayerListener {
    private final PlayerMenuView playerProfileMenuScreenInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final AppNavigator navigatorFieldReference;

    private Timer liveMatchesPreviewTimerFieldReference;
    private static final int LIVE_MATCHES_REFRESH_INTERVAL_MS = 3_000;

    public PlayerMenuController(PlayerMenuView playerProfileMenuScreenInterfaceParameterValue,
                                PlayerManager playerProfileManagerServiceParameterValue,
                                AppNavigator navigatorParameterValue) {
        this.playerProfileMenuScreenInterfaceFieldReference = playerProfileMenuScreenInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.playerProfileMenuScreenInterfaceFieldReference.registerController(this);
        this.playerProfileMenuScreenInterfaceFieldReference.setConfigController(this);
        refreshLiveMatchesPreview();
    }

    public PlayerMenuController(PlayerMenuView playerProfileMenuScreenInterfaceParameterValue,
                                PlayerManager playerProfileManagerServiceParameterValue) {
        this(playerProfileMenuScreenInterfaceParameterValue, playerProfileManagerServiceParameterValue, AppNavigator.getInstance());
    }

    @Override public void showMenu() { showPlayerMenu(); }
    @Override public void onPlayersDeleted() { showPlayerMenu(); }
    @Override public void returnToMenu() { showPlayerMenu(); }

    public void startLiveMatchesPreviewAutoRefresh() {
        refreshLiveMatchesPreview();

        if (liveMatchesPreviewTimerFieldReference != null
                && liveMatchesPreviewTimerFieldReference.isRunning()) {
            return;
        }

        liveMatchesPreviewTimerFieldReference = new Timer(
                LIVE_MATCHES_REFRESH_INTERVAL_MS,
                eventArgumentParameterValue -> refreshLiveMatchesPreview()
        );
        liveMatchesPreviewTimerFieldReference.start();
    }

    public void stopLiveMatchesPreviewAutoRefresh() {
        if (liveMatchesPreviewTimerFieldReference != null) {
            liveMatchesPreviewTimerFieldReference.stop();
            liveMatchesPreviewTimerFieldReference = null;
        }
    }

    public void refreshLiveMatchesPreview() {
        List<String[]> liveGamesLocalVariableValue =
                playerProfileManagerServiceFieldReference.getLiveMatches();

        playerProfileMenuScreenInterfaceFieldReference.updateLiveMatches(liveGamesLocalVariableValue);
    }

    @Override
    public void handleLogout() {
        stopLiveMatchesPreviewAutoRefresh();
        LiveMatchesWidgetService.hide();
        AccountSettingsWidgetService.hide();
        playerProfileManagerServiceFieldReference.setCurrentIdentifier(null);
        navigatorFieldReference.show(AppNavigator.LOGIN);
    }

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
            case PlayerMenuView.CONFIG:
                showConfigDialog();
                break;
            default:
                break;
        }
    }

    private void handleWatchMatches() {
        navigatorFieldReference.show(AppNavigator.LIVE_MATCHES);
    }

    private void handleViewLeagues() {
        navigatorFieldReference.show(AppNavigator.AVAILABLE_LEAGUES);
    }

    public void showPlayerMenu() {
        navigatorFieldReference.show(AppNavigator.PLAYER_MENU);
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
                () -> navigatorFieldReference.show(AppNavigator.PLAYER_MENU)
        );
        navigatorFieldReference.show(AppNavigator.CHANGE_PASSWORD);
    }

    private void handleDeleteAccount() {
        int confirmLocalVariableValue = JOptionPane.showConfirmDialog(
                navigatorFieldReference.getMainView(),
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmLocalVariableValue == JOptionPane.YES_OPTION) {
            boolean successLocalVariableValue =
                    playerProfileManagerServiceFieldReference.deleteCurrentPlayer();

            if (successLocalVariableValue) {
                playerProfileMenuScreenInterfaceFieldReference.showMessageDialog("Account deleted successfully");
                handleLogout();
            } else {
                playerProfileMenuScreenInterfaceFieldReference.showMessageDialog("Error deleting account");
            }
        }
    }
}
