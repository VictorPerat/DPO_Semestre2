package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import bussines.objects.Player;
import presentation.Views.ChangePasswordView;
import presentation.Views.LoginView;
import presentation.Views.UserProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador de la pantalla simple de usuario autenticado.
 */
public class UserProfileController implements ActionListener {
    private final UserProfileView userProfileViewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;

    public UserProfileController(UserProfileView userProfileViewInterfaceParameterValue, PlayerManager playerProfileManagerServiceParameterValue) {
        this.userProfileViewInterfaceFieldReference = userProfileViewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.userProfileViewInterfaceFieldReference.registerController(this);
        loadCurrentPlayerData();
    }

    private void loadCurrentPlayerData() {
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
            openChangePasswordView();
        }
    }

    private void openChangePasswordView() {
        ChangePasswordView changePasswordViewInterfaceLocalVariableValue = new ChangePasswordView();
        new ChangePasswordController(
                changePasswordViewInterfaceLocalVariableValue,
                playerProfileManagerServiceFieldReference,
                userProfileViewInterfaceFieldReference
        );
        userProfileViewInterfaceFieldReference.setVisible(false);
    }

    private void logoutAndReturnToLogin() {
        playerProfileManagerServiceFieldReference.logoutCurrentUser();
        userProfileViewInterfaceFieldReference.dispose();
        LoginView loginViewInterfaceLocalVariableValue = new LoginView();
        new LoginController(loginViewInterfaceLocalVariableValue, playerProfileManagerServiceFieldReference);
    }
}
