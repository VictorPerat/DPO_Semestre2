package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import bussines.objects.Player;
import presentation.AppNavigator;
import presentation.Views.UserProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Coordina la pantalla del usuario perfil.
 */
public class UserProfileController implements ActionListener {

    private final UserProfileView userProfileViewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final AppNavigator navigatorFieldReference;


    /**
     * Crea una instancia de el usuario perfil.
     *
     * @param userProfileViewInterfaceParameterValue usuario perfil vista.
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
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
     * Gestiona esta operacion.
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


    /**
     * Gestiona esta operacion.
     *
     * @param eventArgumentParameterValue dato de entrada de la operacion.
     */
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


    /**
     * Gestiona esta operacion.
     */
    private void logoutAndReturnToLogin() {
        playerProfileManagerServiceFieldReference.logoutCurrentUser();
        navigatorFieldReference.show(AppNavigator.LOGIN);
    }
}


