package presentation.ControllerViews;

import bussines.managers.GameManager;
import bussines.managers.TeamManager;
import presentation.AppNavigator;
import presentation.Views.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Controlador encargado de gestionar la lógica para eliminar equipos.
 * Gestiona la interacción entre la vista DeleteTeamScreen y los gestores de negocio,
 * además de controlar la navegación y configuraciones del menú administrador.
 */
public class DeleteTeamController implements ActionListener {
    /** Vista para eliminar equipos */
    private DeleteTeamView viewInterfaceFieldReference;

    /** Controlador del menú administrador */
    private AdminMenuController adminMenuControllerHandlerFieldReference;

    /** Gestor de equipos */
    private TeamManager teamReferenceManagerServiceFieldReference = new TeamManager();

    /** Gestor de juegos */
    private GameManager gameEntityManagerServiceFieldReference = new GameManager();

    public boolean logoutFieldReference;


    public DeleteTeamController(DeleteTeamView viewInterfaceParameterValue, AdminMenuController adminMenuControllerHandlerParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.adminMenuControllerHandlerFieldReference = adminMenuControllerHandlerParameterValue;

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


    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue2) {
        String commandLocalVariableValue = eventArgumentParameterValue2.getActionCommand();

        if (viewInterfaceFieldReference.DELETE_TEAMS_BUTTON.equals(commandLocalVariableValue)) {
            ArrayList<String> selectedTeamsLocalVariableValue = viewInterfaceFieldReference.getSelectedTeams();

            if (selectedTeamsLocalVariableValue.isEmpty()) {
                viewInterfaceFieldReference.showMessageDialog("Please select at least one team!");
                return;
            }
            if (gameEntityManagerServiceFieldReference.teamIsPlaying(selectedTeamsLocalVariableValue)) {
                viewInterfaceFieldReference.showMessageDialog("One of the selected teams is currently playing!");
                return;
            }


            int confirmLocalVariableValue = viewInterfaceFieldReference.confirmDeleteTeams(selectedTeamsLocalVariableValue.size());
            if (confirmLocalVariableValue == 0) {
                String resultLocalVariableValue = teamReferenceManagerServiceFieldReference.deleteTeams(selectedTeamsLocalVariableValue);
                viewInterfaceFieldReference.showDeletionResult(resultLocalVariableValue);
                viewInterfaceFieldReference.refreshTeamsList();
            }
        }
    }

    /**
     * Muestra el diálogo de configuración y registra el controlador para manejar acciones.
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
                //configDialog.setVisible(false);
                handleConfigAction(eventArgumentParameterValue3.getActionCommand());
            }
        });
        configDialogLocalVariableValue.setVisible(true);
    }


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
     * Procesa el cierre de sesión, cerrando el diálogo y la vista actual,
     * y devolviendo al menú administrador.
     */
    private void handleLogout() {
        Rounded.ConfigDialog.closeInstance();
        viewInterfaceFieldReference.dispose();
        adminMenuControllerHandlerFieldReference.handleLogout();
    }

    /**
     * Muestra un cuadro de diálogo para confirmar la eliminación de la cuenta del usuario.
     * Si se confirma, muestra un mensaje y procede a cerrar sesión.
     */
    private void handleDeleteAccount() {
        int confirmLocalVariableValue2 = JOptionPane.showConfirmDialog(
                viewInterfaceFieldReference,
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmLocalVariableValue2 == JOptionPane.YES_OPTION) {
            logoutFieldReference = true;
            JOptionPane.showMessageDialog(viewInterfaceFieldReference, "Account deleted successfully");
            handleLogout();
        }
    }

    /**
     * Abre la vista para cambiar la contraseña a través del navegador,
     * registrando un retorno a la vista de eliminar equipo al finalizar.
     */
    private void openChangePasswordView() {
        final DeleteTeamView previousScreenLocalVariableValue =
                viewInterfaceFieldReference;
        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();

        previousScreenLocalVariableValue.setVisible(false);
        navigatorLocalVariableValue.setChangePasswordReturnAction(() -> {
            navigatorLocalVariableValue.hideMainWindow();
            previousScreenLocalVariableValue.setVisible(true);
        });
        navigatorLocalVariableValue.show(AppNavigator.CHANGE_PASSWORD);
    }

    /**
     * Método para cerrar la vista actual y volver al menú administrador.
     */
    public void returnToAdminMenu() {
        viewInterfaceFieldReference.dispose();
        adminMenuControllerHandlerFieldReference.showAdminMenu();
    }
}

