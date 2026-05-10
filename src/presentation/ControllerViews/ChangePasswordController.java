package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.Views.ChangePasswordView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Coordina la pantalla del cambio contrasena.
 */
public class ChangePasswordController implements ActionListener {

    private final ChangePasswordView viewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final AppNavigator navigatorFieldReference;


    /**
     * Crea una instancia de el cambio contrasena.
     *
     * @param viewInterfaceParameterValue vista que usa la operacion.
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
    public ChangePasswordController(
            ChangePasswordView viewInterfaceParameterValue,
            PlayerManager playerProfileManagerServiceParameterValue,
            AppNavigator navigatorParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.viewInterfaceFieldReference.registerController(this);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param eventArgumentParameterValue dato de entrada de la operacion.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String actionCommandLocalVariableValue = eventArgumentParameterValue.getActionCommand();

        if (ChangePasswordView.BACK_BUTTON.equals(actionCommandLocalVariableValue)) {
            cancelAndReturn();
            return;
        }

        if (ChangePasswordView.CHANGE_BUTTON.equals(actionCommandLocalVariableValue)) {
            handlePasswordChange();
        }
    }


    /**
     * Gestiona esta operacion.
     */
    private void handlePasswordChange() {
        String currentPassLocalVariableValue = viewInterfaceFieldReference.getActualPassword();
        String newPassLocalVariableValue = viewInterfaceFieldReference.getNewPassword();
        String confirmPassLocalVariableValue = viewInterfaceFieldReference.getConfirmPassword();

        if (isNullOrBlank(currentPassLocalVariableValue)
                || isNullOrBlank(newPassLocalVariableValue)
                || isNullOrBlank(confirmPassLocalVariableValue)) {
            viewInterfaceFieldReference.showMessageDialog("All fields must be filled out.");
            return;
        }

        if (!newPassLocalVariableValue.equals(confirmPassLocalVariableValue)) {
            viewInterfaceFieldReference.showMessageDialog("The new passwords do not match.");
            return;
        }

        if (!isValidPassword(newPassLocalVariableValue)) {
            viewInterfaceFieldReference.showMessageDialog(
                    "The password must be at least 8 characters long and include an uppercase letter, a lowercase letter, and a number."
            );
            return;
        }

        String userIdentifierLocalVariableValue = playerProfileManagerServiceFieldReference.getCurrentIdentifier();
        if (isNullOrBlank(userIdentifierLocalVariableValue)) {
            viewInterfaceFieldReference.showMessageDialog("No authenticated user found.");
            return;
        }

        if (!playerProfileManagerServiceFieldReference.verifyPassword(userIdentifierLocalVariableValue, currentPassLocalVariableValue)) {
            viewInterfaceFieldReference.showMessageDialog("The current password is incorrect.");
            return;
        }

        boolean updatedLocalVariableValue =
                playerProfileManagerServiceFieldReference.updatePassword(userIdentifierLocalVariableValue, newPassLocalVariableValue);

        if (updatedLocalVariableValue) {
            viewInterfaceFieldReference.showMessageDialog("Password changed successfully!");
            cancelAndReturn();
        } else {
            viewInterfaceFieldReference.showMessageDialog("Could not update password.");
        }
    }


    /**
     * Indica el estado actual.
     */
    private void cancelAndReturn() {
        viewInterfaceFieldReference.clearForm();
        navigatorFieldReference.finishChangePasswordFlow();
    }


    /**
     * Indica el estado actual.
     *
     * @param userPasswordParameterValue contrasena del usuario.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    private boolean isValidPassword(String userPasswordParameterValue) {
        return userPasswordParameterValue.length() >= 8 &&
                userPasswordParameterValue.matches(".*[A-Z].*") &&
                userPasswordParameterValue.matches(".*[a-z].*") &&
                userPasswordParameterValue.matches(".*[0-9].*");
    }


    /**
     * Indica el estado actual.
     *
     * @param textParameterValue texto que usa la operacion.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    private boolean isNullOrBlank(String textParameterValue) {
        return textParameterValue == null || textParameterValue.trim().isEmpty();
    }
}


