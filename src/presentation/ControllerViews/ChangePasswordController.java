package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import presentation.Views.ChangePasswordView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador simplificado para la gestión del cambio de contraseña.
 */
public class ChangePasswordController implements ActionListener {

    private final ChangePasswordView viewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final JFrame previousViewInterfaceFieldReference;

    public ChangePasswordController(
            ChangePasswordView viewInterfaceParameterValue,
            PlayerManager playerProfileManagerServiceParameterValue,
            JFrame previousViewInterfaceParameterValue) {

        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.previousViewInterfaceFieldReference = previousViewInterfaceParameterValue;
        this.viewInterfaceFieldReference.registerController(this);
    }

    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String actionCommandLocalVariableValue = eventArgumentParameterValue.getActionCommand();

        if (ChangePasswordView.BACK_BUTTON.equals(actionCommandLocalVariableValue)) {
            previousViewInterfaceFieldReference.setVisible(true);
            viewInterfaceFieldReference.dispose();
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
            previousViewInterfaceFieldReference.setVisible(true);
            viewInterfaceFieldReference.dispose();
        } else {
            viewInterfaceFieldReference.showMessageDialog("Could not update password.");
        }
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
