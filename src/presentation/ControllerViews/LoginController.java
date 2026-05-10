package presentation.ControllerViews;

import bussines.managers.ConfigManager;
import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.LiveMatchesWidgetService;
import presentation.Views.AdminMenuView;
import presentation.Views.LoginView;
import presentation.Views.PlayerMenuView;
import presentation.AccountSettingsWidgetService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Coordina la pantalla del inicio de sesion.
 */
public class LoginController implements ActionListener {

    private final LoginView loginViewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final AppNavigator navigatorFieldReference;


    /**
     * Crea una instancia de el inicio de sesion.
     *
     * @param loginViewInterfaceParameterValue inicio de sesion vista.
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
    public LoginController(
            LoginView loginViewInterfaceParameterValue,
            PlayerManager playerProfileManagerServiceParameterValue,
            AppNavigator navigatorParameterValue) {
        this.loginViewInterfaceFieldReference = loginViewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.loginViewInterfaceFieldReference.registerController(this);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param eventArgumentParameterValue dato de entrada de la operacion.
     */
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


    /**
     * Gestiona esta operacion.
     */
    private void handleLogin() {
        String userIdentifierLocalVariableValue = loginViewInterfaceFieldReference.getEmailText().trim();
        String userPasswordLocalVariableValue = loginViewInterfaceFieldReference.getPasswordText();

        if (!isValidAccessInput(userIdentifierLocalVariableValue, userPasswordLocalVariableValue)) {
            return;
        }


        if (ConfigManager.getAdminIdentifier().equalsIgnoreCase(userIdentifierLocalVariableValue)) {
            boolean isAdminCorrectLocalVariableValue =
                    playerProfileManagerServiceFieldReference.isAdmin(
                            userIdentifierLocalVariableValue,
                            userPasswordLocalVariableValue
                    );

            if (isAdminCorrectLocalVariableValue) {
                playerProfileManagerServiceFieldReference.searchPlayer(
                        userIdentifierLocalVariableValue,
                        userPasswordLocalVariableValue
                );
                loginViewInterfaceFieldReference.clearForm();
                openAdminMenu();
            } else {
                loginViewInterfaceFieldReference.showMessageDialog("Incorrect admin password");
            }
            return;
        }


        if (!playerProfileManagerServiceFieldReference.playerExists(userIdentifierLocalVariableValue)) {
            loginViewInterfaceFieldReference.showMessageDialog("This user does not exist");
            return;
        }

        boolean loginCorrectLocalVariableValue =
                playerProfileManagerServiceFieldReference.searchPlayer(
                        userIdentifierLocalVariableValue,
                        userPasswordLocalVariableValue
                );

        if (!loginCorrectLocalVariableValue) {
            loginViewInterfaceFieldReference.showMessageDialog("Incorrect password");
            return;
        }

        loginViewInterfaceFieldReference.clearForm();
        openPlayerMenu();
    }


    /**
     * Abre el administrador menu.
     */
    private void openAdminMenu() {
        navigatorFieldReference.show(AppNavigator.ADMIN_MENU);

        LiveMatchesWidgetService.show(
                playerProfileManagerServiceFieldReference,
                true
        );

        AccountSettingsWidgetService.show(
                playerProfileManagerServiceFieldReference
        );
    }


    /**
     * Abre el jugador menu.
     */
    private void openPlayerMenu() {
        navigatorFieldReference.show(AppNavigator.PLAYER_MENU);

        LiveMatchesWidgetService.show(
                playerProfileManagerServiceFieldReference,
                false
        );

        AccountSettingsWidgetService.show(
                playerProfileManagerServiceFieldReference
        );
    }


    /**
     * Indica el estado actual.
     *
     * @param userIdentifierParameterValue identificador del usuario.
     * @param userPasswordParameterValue contrasena del usuario.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    private boolean isValidAccessInput(String userIdentifierParameterValue, String userPasswordParameterValue) {
        if (userIdentifierParameterValue.isEmpty() || userPasswordParameterValue.isEmpty()) {
            loginViewInterfaceFieldReference.showMessageDialog("DNI/email and password are required.");
            return false;
        }

        boolean isAdminAttemptLocalVariableValue =
                ConfigManager.getAdminIdentifier().equalsIgnoreCase(userIdentifierParameterValue);


        if (!isValidDni(userIdentifierParameterValue)
                && !isValidEmail(userIdentifierParameterValue)
                && !isAdminAttemptLocalVariableValue) {
            loginViewInterfaceFieldReference.showMessageDialog("Use a valid DNI or email address.");
            return false;
        }


        if (!isAdminAttemptLocalVariableValue && userPasswordParameterValue.length() < 8) {
            loginViewInterfaceFieldReference.showMessageDialog("Password must be at least 8 characters long.");
            return false;
        }

        return true;
    }


    /**
     * Indica el estado actual.
     *
     * @param nationalIdentityDocumentParameterValue documento de identidad del jugador.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    private boolean isValidDni(String nationalIdentityDocumentParameterValue) {
        return nationalIdentityDocumentParameterValue.matches("^\\d{8}[A-Z]$");
    }


    /**
     * Indica el estado actual.
     *
     * @param emailAddressParameterValue direccion de email.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    private boolean isValidEmail(String emailAddressParameterValue) {
        return emailAddressParameterValue.matches("^[^@]+@[^@]+\\.[^@]+$");
    }
}


