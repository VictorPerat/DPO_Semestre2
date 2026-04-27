package presentation.ControllerViews;

import bussines.managers.LeagueManager;
import presentation.AppNavigator;
import presentation.Views.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Controlador para la pantalla de eliminación de ligas.
 * Gestiona la interacción del usuario para seleccionar y eliminar ligas,
 * así como la configuración y eventos asociados.
 */
public class DeleteLeagueController implements ActionListener {
    private DeleteLeagueView viewInterfaceFieldReference;
    private AdminMenuController adminMenuControllerHandlerFieldReference;
    private LeagueManager leagueReferenceManagerServiceFieldReference;
    public boolean logoutFieldReference, noFieldReference;

    /**
     * Constructor que inicializa el controlador, la vista y registra eventos.
     * También añade un listener para gestionar el cierre de la ventana.
     *
     * @param view Vista para eliminar ligas.
     * @param adminMenuController Controlador del menú administrador para navegación.
     */
    public DeleteLeagueController(DeleteLeagueView viewInterfaceParameterValue, AdminMenuController adminMenuControllerHandlerParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.adminMenuControllerHandlerFieldReference = adminMenuControllerHandlerParameterValue;
        this.viewInterfaceFieldReference.setConfigController(this);
        this.leagueReferenceManagerServiceFieldReference = new LeagueManager();

        // ESTO
        viewInterfaceParameterValue.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent eventArgumentParameterValue) {
                if (!logoutFieldReference) {
                    adminMenuControllerHandlerParameterValue.showAdminMenu();
                }
            }
        });
    }

    /**
     * Maneja los eventos de acción generados por la vista.
     * Procesa la eliminación de ligas seleccionadas tras la confirmación.
     *
     * @param e Evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue2) {
        String commandLocalVariableValue = eventArgumentParameterValue2.getActionCommand();

        if (viewInterfaceFieldReference.DELETE_LEAGUES_BUTTON.equals(commandLocalVariableValue)) {
            ArrayList<String> leaguesToDeleteLocalVariableValue = viewInterfaceFieldReference.getSelectedLeagues();

            if (leaguesToDeleteLocalVariableValue == null || leaguesToDeleteLocalVariableValue.isEmpty()) {
                    viewInterfaceFieldReference.showMessageDialog("(ERROR) Please select a league!");
                return;
            }

            int confirmLocalVariableValue = viewInterfaceFieldReference.confirmDelete(leaguesToDeleteLocalVariableValue);

            if (confirmLocalVariableValue == 0) {
                String messageLocalVariableValue = leagueReferenceManagerServiceFieldReference.deleteLeague(leaguesToDeleteLocalVariableValue);
                viewInterfaceFieldReference.messageDelete(messageLocalVariableValue);
                viewInterfaceFieldReference.refreshLeaguesList();
            }
        }
    }

    /**
     * Muestra el diálogo de configuración con opciones como cerrar sesión,
     * eliminar cuenta o cambiar contraseña.
     */
    public void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = Rounded.ConfigDialog.getInstance(viewInterfaceFieldReference);
        configDialogLocalVariableValue.registerController(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgumentParameterValue3) {
                if (eventArgumentParameterValue3.getActionCommand() == Rounded.ConfigDialog.LOGOUT) {
                    logoutFieldReference = true;
                } else { logoutFieldReference = false; }
                //configDialog.setVisible(false);
                configDialogLocalVariableValue.dispose();
                handleConfigAction(eventArgumentParameterValue3.getActionCommand());
            }
        });
        configDialogLocalVariableValue.setVisible(true);
    }

    /**
     * Gestiona las acciones seleccionadas en el diálogo de configuración.
     *
     * @param action Comando de acción seleccionado.
     */
    private void handleConfigAction(String actionParameterValue) {
        switch(actionParameterValue) {
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
     * Maneja la acción de cerrar sesión: cierra la vista y delega al controlador admin.
     */
    private void handleLogout() {
        Rounded.ConfigDialog.closeInstance();
        viewInterfaceFieldReference.dispose();
        adminMenuControllerHandlerFieldReference.handleLogout();
    }

    /**
     * Maneja la acción de intentar eliminar la cuenta de admin.
     * Muestra advertencia y fuerza el cierre de sesión.
     */
    private void handleDeleteAccount() {
        int confirmLocalVariableValue2 = JOptionPane.showConfirmDialog(
                viewInterfaceFieldReference,
                "As admin, you cannot delete your account. This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmLocalVariableValue2 == JOptionPane.YES_OPTION) {
            logoutFieldReference = true;
            JOptionPane.showMessageDialog(viewInterfaceFieldReference, "As admin, you cannot delete the account. Bringing you to the LOGIN.");
            handleLogout();
        }

    }

    /**
     * Abre la vista para cambiar la contraseña a través del navegador,
     * registrando un retorno a la vista de eliminación de liga al terminar.
     */
    private void openChangePasswordView() {
        final DeleteLeagueView previousScreenLocalVariableValue =
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