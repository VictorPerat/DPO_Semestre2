package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import presentation.Views.LoginView;
import presentation.Views.SignUpView;
import presentation.Views.UserProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador simplificado para login.
 */
public class LoginController implements ActionListener {
    private final LoginView loginViewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;

    public LoginController(LoginView loginViewInterfaceParameterValue, PlayerManager playerProfileManagerServiceParameterValue) {
        this.loginViewInterfaceFieldReference = loginViewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.loginViewInterfaceFieldReference.registerController(this);
    }

    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();

        if (LoginView.ACCESS_BUTTON.equals(commandLocalVariableValue)) {
            handleLogin();
            return;
        }

        if (LoginView.SIGN_UP_BUTTON.equals(commandLocalVariableValue)) {
            loginViewInterfaceFieldReference.dispose();
            SignUpView signUpViewInterfaceLocalVariableValue = new SignUpView();
            new SignUpController(signUpViewInterfaceLocalVariableValue, playerProfileManagerServiceFieldReference);
        }
    }

    private void handleLogin() {
        String userIdentifierLocalVariableValue = loginViewInterfaceFieldReference.getEmailText().trim();
        String userPasswordLocalVariableValue = loginViewInterfaceFieldReference.getPasswordText();

        if (!isValidAccessInput(userIdentifierLocalVariableValue, userPasswordLocalVariableValue)) {
            return;
        }

        boolean loginCorrectLocalVariableValue = playerProfileManagerServiceFieldReference.searchPlayer(userIdentifierLocalVariableValue, userPasswordLocalVariableValue);

        if (!loginCorrectLocalVariableValue) {
            loginViewInterfaceFieldReference.showMessageDialog("Les credencials introduïdes són incorrectes");
            return;
        }

        playerProfileManagerServiceFieldReference.setCurrentIdentifier(userIdentifierLocalVariableValue);
        loginViewInterfaceFieldReference.dispose();

        UserProfileView userProfileViewInterfaceLocalVariableValue = new UserProfileView();
        new UserProfileController(userProfileViewInterfaceLocalVariableValue, playerProfileManagerServiceFieldReference);
    }

    private boolean isValidAccessInput(String userIdentifierParameterValue, String userPasswordParameterValue) {
        if (userIdentifierParameterValue.isEmpty() || userPasswordParameterValue.isEmpty()) {
            loginViewInterfaceFieldReference.showMessageDialog("Username and password are required.");
            return false;
        }

        if (!isValidDni(userIdentifierParameterValue) && !isValidEmail(userIdentifierParameterValue) && !"admin".equalsIgnoreCase(userIdentifierParameterValue)) {
            loginViewInterfaceFieldReference.showMessageDialog("Use a valid DNI or email address.");
            return false;
        }

        if (userPasswordParameterValue.length() < 8) {
            loginViewInterfaceFieldReference.showMessageDialog("Password must be at least 8 characters long.");
            return false;
        }

        return true;
    }

    private boolean isValidDni(String nationalIdentityDocumentParameterValue) {
        return nationalIdentityDocumentParameterValue.matches("^\\d{8}[A-Z]$");
    }

    private boolean isValidEmail(String emailAddressParameterValue) {
        return emailAddressParameterValue.matches("^[^@]+@[^@]+\\.[^@]+$");
    }
}
