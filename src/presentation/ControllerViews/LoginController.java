package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.Views.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador del login.
 *
 * Refactorizado: ya no destruye ni crea ventanas. Cuando el login es
 * correcto, simplemente le pide al {@link AppNavigator} que muestre la
 * pantalla de PROFILE. Cuando el usuario quiere registrarse, navega
 * a SIGNUP.
 */
public class LoginController implements ActionListener {

    private final LoginView loginViewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final AppNavigator navigatorFieldReference;

    public LoginController(
            LoginView loginViewInterfaceParameterValue,
            PlayerManager playerProfileManagerServiceParameterValue,
            AppNavigator navigatorParameterValue) {
        this.loginViewInterfaceFieldReference = loginViewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
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
            navigatorFieldReference.show(AppNavigator.SIGNUP);
        }
    }

    private void handleLogin() {
        String userIdentifierLocalVariableValue = loginViewInterfaceFieldReference.getEmailText().trim();
        String userPasswordLocalVariableValue = loginViewInterfaceFieldReference.getPasswordText();

        if (!isValidAccessInput(userIdentifierLocalVariableValue, userPasswordLocalVariableValue)) {
            return;
        }

        boolean loginCorrectLocalVariableValue = playerProfileManagerServiceFieldReference.searchPlayer(
                userIdentifierLocalVariableValue,
                userPasswordLocalVariableValue
        );

        if (!loginCorrectLocalVariableValue) {
            loginViewInterfaceFieldReference.showMessageDialog("Les credencials introduïdes són incorrectes");
            return;
        }

        playerProfileManagerServiceFieldReference.setCurrentIdentifier(userIdentifierLocalVariableValue);
        loginViewInterfaceFieldReference.clearForm();
        navigatorFieldReference.show(AppNavigator.PROFILE);
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
