package presentation.ControllerViews;

import presentation.AppNavigator;
import presentation.Views.CalendarView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Esta clase se encarga de controlar la pantalla del calendario.
 */
public class CalendarController implements ActionListener {

    // Vista del calendario
    private CalendarView calendarScreenInterfaceFieldReference;

    // Controlador del menú de admin para volver atrás o cerrar sesión
    private AdminMenuController adminMenuControllerHandlerFieldReference;

    // Constructor que conecta la vista con este controlador
    public CalendarController(CalendarView calendarScreenInterfaceParameterValue,
                              AdminMenuController adminMenuControllerHandlerParameterValue) {

        this.calendarScreenInterfaceFieldReference = calendarScreenInterfaceParameterValue;
        this.adminMenuControllerHandlerFieldReference = adminMenuControllerHandlerParameterValue;

        this.calendarScreenInterfaceFieldReference.registerController(this);
    }

    // Gestiona las acciones que llegan desde la vista
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

    // Cierra la pantalla del calendario
    private void handleBack() {
        calendarScreenInterfaceFieldReference.dispose();
    }

    // Muestra el diálogo de configuración
    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue =
                new Rounded.ConfigDialog(calendarScreenInterfaceFieldReference);

        configDialogLocalVariableValue.registerController(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgumentParameterValue2) {
                configDialogLocalVariableValue.dispose();
                handleConfigAction(eventArgumentParameterValue2.getActionCommand());
            }
        });

        // Botón para volver atrás
        configDialogLocalVariableValue.setBackButtonListener(eventArgumentParameterValue3 -> {
            configDialogLocalVariableValue.dispose();
        });

        configDialogLocalVariableValue.setVisible(true);
    }

    // Gestiona la opción elegida dentro del panel de configuración
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

    // Convierte una fecha y hora a String con un formato concreto
    public static String formatDateTime(LocalDateTime dateTimeParameterValue) {
        DateTimeFormatter formatterLocalVariableValue =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return dateTimeParameterValue.format(formatterLocalVariableValue);
    }

    // Cierra el calendario y delega el logout al controlador de admin
    private void handleLogout() {
        calendarScreenInterfaceFieldReference.dispose();
        adminMenuControllerHandlerFieldReference.handleLogout();
    }

    // Pregunta si el usuario quiere borrar la cuenta
    private void handleDeleteAccount() {
        int confirmLocalVariableValue = JOptionPane.showConfirmDialog(
                calendarScreenInterfaceFieldReference,
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmLocalVariableValue == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                    calendarScreenInterfaceFieldReference,
                    "Account deleted successfully"
            );
            handleLogout();
        }
    }

    // Abre la pantalla de cambiar contraseña y guarda la acción de vuelta
    private void openChangePasswordView() {
        final CalendarView previousScreenLocalVariableValue =
                calendarScreenInterfaceFieldReference;

        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();

        previousScreenLocalVariableValue.setVisible(false);

        navigatorLocalVariableValue.setChangePasswordReturnAction(() -> {
            navigatorLocalVariableValue.hideMainWindow();
            previousScreenLocalVariableValue.setVisible(true);
        });

        navigatorLocalVariableValue.show(AppNavigator.CHANGE_PASSWORD);
    }
}