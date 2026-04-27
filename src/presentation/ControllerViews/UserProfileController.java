package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import bussines.objects.Player;
import presentation.AppNavigator;
import presentation.Views.UserProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador del perfil del usuario autenticado.
 *
 * Refactorizado: usa {@link AppNavigator} para navegar al cambio de
 * contraseña y al logout. La carga de datos del jugador actual se
 * dispara mediante {@link #refreshCurrentPlayer()}, que el navegador
 * o el flujo de login deben llamar al mostrar la pantalla.
 */
public class UserProfileController implements ActionListener {

    private final UserProfileView userProfileViewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final AppNavigator navigatorFieldReference;

    public UserProfileController(
            UserProfileView userProfileViewInterfaceParameterValue,
            PlayerManager playerProfileManagerServiceParameterValue,
            AppNavigator navigatorParameterValue) {
        this.userProfileViewInterfaceFieldReference = userProfileViewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.userProfileViewInterfaceFieldReference.registerController(this);
    }

    /**
     * Carga (o recarga) los datos del jugador actualmente autenticado en
     * la vista de perfil. Debe llamarse cada vez que el usuario llega a
     * esta pantalla (después de login o al volver desde Change Password).
     */
    public void refreshCurrentPlayer() {
        Player currentPlayerProfileLocalVariableValue = playerProfileManagerServiceFieldReference.getCurrentPlayer();
        if (currentPlayerProfileLocalVariableValue == null) {
            userProfileViewInterfaceFieldReference.showMessageDialog("No active user found. Returning to login.");
            logoutAndReturnToLogin();
            return;
        }
        userProfileViewInterfaceFieldReference.displayUserInformation(currentPlayerProfileLocalVariableValue);
    }

    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String actionCommandLocalVariableValue = eventArgumentParameterValue.getActionCommand();

        if (UserProfileView.LOGOUT_BUTTON.equals(actionCommandLocalVariableValue)) {
            logoutAndReturnToLogin();
            return;
        }

        if (UserProfileView.CHANGE_PASSWORD_BUTTON.equals(actionCommandLocalVariableValue)) {
            navigatorFieldReference.show(AppNavigator.CHANGE_PASSWORD);
        }
    }

    private void logoutAndReturnToLogin() {
        playerProfileManagerServiceFieldReference.logoutCurrentUser();
        navigatorFieldReference.show(AppNavigator.LOGIN);
    }
}
