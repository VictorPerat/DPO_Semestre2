package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import bussines.objects.Player;
import presentation.AppNavigator;
import presentation.Views.DeletePlayerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Coordina la pantalla del jugador.
 */
public class DeletePlayerController implements ActionListener {
    private final DeletePlayerView viewInterfaceFieldReference;
    private final DeletePlayerListener listenerFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final AppNavigator navigatorFieldReference;


    /**
     * Crea una instancia de el jugador.
     *
     * @param viewInterfaceParameterValue vista que usa la operacion.
     * @param listenerParameterValue listener que se registra.
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
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


    /**
     * Gestiona esta operacion.
     */
    public void refreshPlayers() { viewInterfaceFieldReference.refreshPlayersList(); }


    /**
     * Gestiona esta operacion.
     *
     * @param eventArgumentParameterValue dato de entrada de la operacion.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();
        if (viewInterfaceFieldReference.DELETE_PLAYERS_BUTTON.equals(commandLocalVariableValue)) {
            deleteSelectedPlayers();
        } else if ("BACK".equals(commandLocalVariableValue)) {
            if (listenerFieldReference != null) { listenerFieldReference.returnToMenu(); }
            else { navigatorFieldReference.show(AppNavigator.ADMIN_MENU); }
        }
    }


    /**
     * Elimina los jugadores.
     */
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
}


