package presentation.ControllerViews;

import bussines.objects.Player;
import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.Views.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Controlador para la gestión de la eliminación de jugadores.
 * Maneja la interacción entre la vista DeletePlayerScreen y la lógica de negocio PlayerManager.
 * Permite seleccionar jugadores para eliminar, confirmar la acción y actualizar la lista.
 * Además, proporciona un diálogo de configuración para opciones como cerrar sesión,
 * eliminar cuenta y cambiar contraseña.
 */
public class DeletePlayerController implements ActionListener {

    /**
     * Vista para eliminar jugadores.
     */
    private final DeletePlayerView viewInterfaceFieldReference;

    /**
     * Listener que maneja eventos relacionados con eliminación de jugadores y navegación.
     */
    private final DeletePlayerListener listenerFieldReference;

    /**
     * Gestor de lógica de negocio para jugadores.
     */
    private PlayerManager playerProfileManagerServiceFieldReference;

    /**
     * Constructor que inicializa la vista, el listener y el PlayerManager.
     * También añade listeners para manejar el cierre de ventana.
     *
     * @param view Vista para eliminar jugadores.
     * @param listener Listener para eventos relacionados con eliminación y navegación.
     */


    public DeletePlayerController(DeletePlayerView viewInterfaceParameterValue, DeletePlayerListener listenerParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.listenerFieldReference = listenerParameterValue;
        this.playerProfileManagerServiceFieldReference = new PlayerManager();
        viewInterfaceParameterValue.setController(this);

        viewInterfaceParameterValue.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent eventArgumentParameterValue) {
                listenerParameterValue.returnToMenu();
            }

            @Override
            public void windowClosed(WindowEvent eventArgumentParameterValue2) {
                listenerParameterValue.returnToMenu();
            }
        });
    }

    /**
     * Maneja las acciones de la vista, como eliminar jugadores seleccionados.
     * Valida que se hayan seleccionado jugadores y confirma la acción antes de eliminar.
     *
     * @param e Evento de acción generado por la interfaz gráfica.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue3) {
        String commandLocalVariableValue = eventArgumentParameterValue3.getActionCommand();

        if (viewInterfaceFieldReference.DELETE_PLAYERS_BUTTON.equals(commandLocalVariableValue)) {
            ArrayList<Player> selectedPlayersLocalVariableValue = viewInterfaceFieldReference.getSelectedPlayers();
            if (selectedPlayersLocalVariableValue.isEmpty()) {
                viewInterfaceFieldReference.showMessageDialog("No players selected!");
                return;
            }
            int confirmLocalVariableValue = viewInterfaceFieldReference.confirmDeletePlayers(selectedPlayersLocalVariableValue.size());

            if (confirmLocalVariableValue == 0) {
                String resultLocalVariableValue = playerProfileManagerServiceFieldReference.deletePlayers(selectedPlayersLocalVariableValue);
                viewInterfaceFieldReference.showDeletionResult(resultLocalVariableValue);
                viewInterfaceFieldReference.refreshPlayersList();
            }
        }
    }

    /**
     * Muestra el diálogo de configuración y registra el controlador para las acciones.
     */
    public void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = Rounded.ConfigDialog.getInstance(viewInterfaceFieldReference);
        configDialogLocalVariableValue.registerController(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgumentParameterValue4) {
                configDialogLocalVariableValue.setVisible(false);
                handleConfigAction(eventArgumentParameterValue4.getActionCommand());
            }
        });
        configDialogLocalVariableValue.setVisible(true);
    }

    /**
     * Gestiona las acciones recibidas desde el diálogo de configuración.
     *
     * @param action Comando de acción proveniente del diálogo de configuración.
     */
    private void handleConfigAction(String actionParameterValue) {
        switch (actionParameterValue) {
            case Rounded.ConfigDialog.LOGOUT:
                handleLogout();
                break;
            case Rounded.ConfigDialog.DELETE_ACCOUNT:
                handleDeleteAccount();
                break;
            case Rounded.ConfigDialog.CHANGE_PASSWORD:
                openChangePasswordView();
                break;
        }
    }

    /**
     * Gestiona el cierre de sesión del usuario.
     */
    private void handleLogout() {
        Rounded.ConfigDialog.closeInstance();
        viewInterfaceFieldReference.dispose();
        listenerFieldReference.handleLogout();
    }

    /**
     * Muestra un cuadro de diálogo para confirmar la eliminación de la cuenta del usuario.
     * Si se confirma, se procede a cerrar sesión.
     */
    private void handleDeleteAccount() {
        int confirmLocalVariableValue2 = JOptionPane.showConfirmDialog(
                viewInterfaceFieldReference,
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmLocalVariableValue2 == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(viewInterfaceFieldReference, "Account deleted successfully");
            handleLogout();
        }
    }

    /**
     * Abre la vista para cambiar la contraseña a través del navegador,
     * registrando el retorno a la vista actual al finalizar.
     */
    private void openChangePasswordView() {
        final DeletePlayerView previousScreenLocalVariableValue =
                viewInterfaceFieldReference;
        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();

        previousScreenLocalVariableValue.setVisible(false);
        navigatorLocalVariableValue.setChangePasswordReturnAction(() -> {
            navigatorLocalVariableValue.hideMainWindow();
            previousScreenLocalVariableValue.setVisible(true);
        });
        navigatorLocalVariableValue.show(AppNavigator.CHANGE_PASSWORD);
    }
}
