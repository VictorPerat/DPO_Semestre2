package presentation.ControllerViews;

import presentation.Views.CalendarView;
import presentation.Views.ChangePasswordView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Controlador para la pantalla del calendario.
 * Maneja eventos de acción como volver al menú principal, abrir configuraciones, y gestión de cuenta.
 */
public class CalendarController implements ActionListener {

    /**
     * Vista del calendario gestionada por este controlador.
     */
    private CalendarView calendarScreenInterfaceFieldReference;

    /**
     * Controlador del menú de administración para navegación y acciones globales.
     */
    private AdminMenuController adminMenuControllerHandlerFieldReference;


    public CalendarController(CalendarView calendarScreenInterfaceParameterValue, AdminMenuController adminMenuControllerHandlerParameterValue) {
        this.calendarScreenInterfaceFieldReference = calendarScreenInterfaceParameterValue;
        this.adminMenuControllerHandlerFieldReference = adminMenuControllerHandlerParameterValue;

        this.calendarScreenInterfaceFieldReference.registerController(this);
    }


    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();
        switch (commandLocalVariableValue) {
            case CalendarView.BACK:
                handleBack();
                break;
            case "CONFIG":
                showConfigDialog();
                break;
        }
    }

    /**
     * Maneja la acción de volver al menú principal, cerrando la vista actual.
     */
    private void handleBack() {
        calendarScreenInterfaceFieldReference.dispose();
    }

    /**
     * Muestra un diálogo de configuración con opciones de usuario.
     */
    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = new Rounded.ConfigDialog(calendarScreenInterfaceFieldReference);
        configDialogLocalVariableValue.registerController(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgumentParameterValue2) {
                configDialogLocalVariableValue.dispose();
                handleConfigAction(eventArgumentParameterValue2.getActionCommand());
            }
        });
        configDialogLocalVariableValue.setBackButtonListener(eventArgumentParameterValue3 -> {
            configDialogLocalVariableValue.dispose();
            //leagueDetail.setVisible(true);
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


    public static String formatDateTime(LocalDateTime dateTimeParameterValue) {
        DateTimeFormatter formatterLocalVariableValue = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTimeParameterValue.format(formatterLocalVariableValue);
    }

    /**
     * Maneja el cierre de sesión, cerrando la vista actual y delegando al controlador padre.
     */
    private void handleLogout() {
        calendarScreenInterfaceFieldReference.dispose();
        adminMenuControllerHandlerFieldReference.handleLogout();
    }

    /**
     * Pregunta al usuario para confirmar eliminación de cuenta, y la elimina si confirma.
     */
    private void handleDeleteAccount() {
        int confirmLocalVariableValue = JOptionPane.showConfirmDialog(
                calendarScreenInterfaceFieldReference,
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmLocalVariableValue == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(calendarScreenInterfaceFieldReference, "Account deleted successfully");
            handleLogout();
        }
    }

    /**
     * Abre la vista para cambiar la contraseña, registrando su controlador.
     */
    private void openChangePasswordView() {
        ChangePasswordView changeUserPasswordViewInterfaceLocalVariableValue =
                new ChangePasswordView();

        calendarScreenInterfaceFieldReference.setVisible(false);

        new ChangePasswordController(
                changeUserPasswordViewInterfaceLocalVariableValue,
                adminMenuControllerHandlerFieldReference.getPlayerManager(),
                calendarScreenInterfaceFieldReference
        );

        changeUserPasswordViewInterfaceLocalVariableValue.setVisible(true);
    }
}
