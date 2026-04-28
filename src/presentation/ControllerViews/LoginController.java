package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.LiveMatchesWidgetService;
import presentation.Views.AdminMenuView;
import presentation.Views.LoginView;
import presentation.Views.PlayerMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador del login.
 *
 * Tras un login correcto:
 *  - Si el usuario es el administrador (apartado 2.2 del enunciado:
 *    el literal "admin" + la contraseña de config.json), se le redirige
 *    al {@link AdminMenuView}.
 *  - Si es un jugador, se le redirige al {@link PlayerMenuView}.
 *
 * En ambos casos se oculta el {@link presentation.Views.MainView} para
 * dejar paso al menú legacy correspondiente.
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

        // Caso 1: el usuario intenta entrar como admin
        // (literal "admin" según el apartado 2.2 del enunciado).
        if ("admin".equalsIgnoreCase(userIdentifierLocalVariableValue)) {
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

        // Caso 2: jugador normal contra base de datos.
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
     * Oculta el MainView y abre el menú legacy del administrador.
     * Arranca también el widget global de partidos en directo
     * (apartado 2.9), que el admin verá en todas las pantallas.
     */
    private void openAdminMenu() {
        navigatorFieldReference.show(AppNavigator.ADMIN_MENU);

        LiveMatchesWidgetService.show(
                playerProfileManagerServiceFieldReference,
                true
        );
    }

    /**
     * Oculta el MainView y abre el menú legacy del jugador.
     * Arranca también el widget de partidos en directo, filtrado por
     * las ligas en las que participa su equipo (apartado 2.9).
     */
    private void openPlayerMenu() {
        navigatorFieldReference.hideMainWindow();

        PlayerMenuView playerMenuViewInterfaceLocalVariableValue = new PlayerMenuView();
        new PlayerMenuController(
                playerMenuViewInterfaceLocalVariableValue,
                playerProfileManagerServiceFieldReference
        );
        playerMenuViewInterfaceLocalVariableValue.setVisible(true);

        LiveMatchesWidgetService.show(
                playerProfileManagerServiceFieldReference,
                false
        );
    }

    private boolean isValidAccessInput(String userIdentifierParameterValue, String userPasswordParameterValue) {
        if (userIdentifierParameterValue.isEmpty() || userPasswordParameterValue.isEmpty()) {
            loginViewInterfaceFieldReference.showMessageDialog("Username and password are required.");
            return false;
        }

        boolean isAdminAttemptLocalVariableValue =
                "admin".equalsIgnoreCase(userIdentifierParameterValue);

        if (!isValidDni(userIdentifierParameterValue)
                && !isValidEmail(userIdentifierParameterValue)
                && !isAdminAttemptLocalVariableValue) {
            loginViewInterfaceFieldReference.showMessageDialog("Use a valid DNI or email address.");
            return false;
        }

        // La contraseña del admin la fija config.json, así que no
        // aplicamos la restricción de longitud mínima en ese caso.
        if (!isAdminAttemptLocalVariableValue && userPasswordParameterValue.length() < 8) {
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
