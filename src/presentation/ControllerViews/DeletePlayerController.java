package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import bussines.objects.Player;
import presentation.AppNavigator;
import presentation.Views.DeletePlayerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** Controlador para borrar jugadores desde CardLayout. */
public class DeletePlayerController implements ActionListener {
    private final DeletePlayerView viewInterfaceFieldReference;
    private final DeletePlayerListener listenerFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final AppNavigator navigatorFieldReference;

    public DeletePlayerController(DeletePlayerView viewInterfaceParameterValue,
                                  DeletePlayerListener listenerParameterValue,
                                  PlayerManager playerProfileManagerServiceParameterValue,
                                  AppNavigator navigatorParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.listenerFieldReference = listenerParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.viewInterfaceFieldReference.setController(this);
    }

    public DeletePlayerController(DeletePlayerView viewInterfaceParameterValue, DeletePlayerListener listenerParameterValue) {
        this(viewInterfaceParameterValue, listenerParameterValue, new PlayerManager(), AppNavigator.getInstance());
    }

    public void refreshPlayers() { viewInterfaceFieldReference.refreshPlayersList(); }

    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();
        if (viewInterfaceFieldReference.DELETE_PLAYERS_BUTTON.equals(commandLocalVariableValue)) {
            deleteSelectedPlayers();
        } else if ("BACK".equals(commandLocalVariableValue)) {
            if (listenerFieldReference != null) { listenerFieldReference.returnToMenu(); }
            else { navigatorFieldReference.show(AppNavigator.ADMIN_MENU); }
        } else if ("CONFIG".equals(commandLocalVariableValue)) {
            showConfigDialog();
        }
    }

    private void deleteSelectedPlayers() {
        ArrayList<Player> selectedPlayersLocalVariableValue = viewInterfaceFieldReference.getSelectedPlayers();
        if (selectedPlayersLocalVariableValue.isEmpty()) {
            viewInterfaceFieldReference.showMessageDialog("No players selected!");
            return;
        }
        if (viewInterfaceFieldReference.confirmDeletePlayers(selectedPlayersLocalVariableValue.size()) == 0) {
            String resultLocalVariableValue = playerProfileManagerServiceFieldReference.deletePlayers(selectedPlayersLocalVariableValue);
            viewInterfaceFieldReference.showDeletionResult(resultLocalVariableValue);
            refreshPlayers();
            if (listenerFieldReference != null) { listenerFieldReference.onPlayersDeleted(); }
        }
    }

    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        configDialogLocalVariableValue.registerController(eventArgumentParameterValue -> {
            configDialogLocalVariableValue.dispose();
            if (Rounded.ConfigDialog.LOGOUT.equals(eventArgumentParameterValue.getActionCommand())) {
                if (listenerFieldReference != null) { listenerFieldReference.handleLogout(); }
                else { navigatorFieldReference.show(AppNavigator.LOGIN); }
            } else if (Rounded.ConfigDialog.CHANGE_PASSWORD.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.setChangePasswordReturnAction(() -> navigatorFieldReference.show(AppNavigator.DELETE_PLAYER));
                navigatorFieldReference.show(AppNavigator.CHANGE_PASSWORD);
            }
        });
        configDialogLocalVariableValue.setBackButtonListener(eventArgumentParameterValue -> configDialogLocalVariableValue.dispose());
        configDialogLocalVariableValue.setVisible(true);
    }
}
