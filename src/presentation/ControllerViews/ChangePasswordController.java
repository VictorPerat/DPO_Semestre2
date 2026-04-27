package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.Views.ChangePasswordView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador del cambio de contraseña.
 *
 * Refactorizado: usa {@link AppNavigator} para volver al perfil (o a la
 * acción de retorno registrada por una pantalla legacy). Ya no recibe
 * un JFrame "anterior" ni hace dispose() / setVisible(true) sobre él.
 */
public class ChangePasswordController implements ActionListener {

    private final ChangePasswordView viewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final AppNavigator navigatorFieldReference;

    public ChangePasswordController(
            ChangePasswordView viewInterfaceParameterValue,
            PlayerManager playerProfileManagerServiceParameterValue,
            AppNavigator navigatorParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.viewInterfaceFieldReference.registerController(this);
    }

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
     * Limpia el formulario y delega en el navegador para volver:
     * - Si una pantalla legacy registró una acción de retorno, esa
     *   acción se ejecuta (típicamente: ocultar MainView y volver al
     *   JFrame del menú legacy).
     * - Si no, vuelve por defecto al PROFILE.
     */
    private void cancelAndReturn() {
        viewInterfaceFieldReference.clearForm();
        navigatorFieldReference.finishChangePasswordFlow();
    }

    private boolean isValidPassword(String userPasswordParameterValue) {
        return userPasswordParameterValue.length() >= 8 &&
                userPasswordParameterValue.matches(".*[A-Z].*") &&
                userPasswordParameterValue.matches(".*[a-z].*") &&
                userPasswordParameterValue.matches(".*[0-9].*");
    }

    private boolean isNullOrBlank(String textParameterValue) {
        return textParameterValue == null || textParameterValue.trim().isEmpty();
    }
}
