package application;

import bussines.MatchSimulationScheduler;
import bussines.managers.ConfigManager;
import bussines.managers.GameManager;
import bussines.managers.LeagueManager;
import bussines.managers.PlayerManager;
import persistance.DatabaseConnector;
import presentation.AppNavigator;
import presentation.ControllerViews.AdminMenuController;
import presentation.ControllerViews.ChangePasswordController;
import presentation.ControllerViews.LoginController;
import presentation.ControllerViews.SignUpController;
import presentation.ControllerViews.UserProfileController;
import presentation.Views.AdminMenuView;
import presentation.Views.ChangePasswordView;
import presentation.Views.LoginView;
import presentation.Views.MainView;
import presentation.Views.SignUpView;
import presentation.Views.UserProfileView;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class LeagueManagerApplication {

    private LeagueManagerApplication() {
    }

    public static void launch() {
        ConfigManager configManagerServiceLocalVariableValue = new ConfigManager();

        if (!isDatabaseAvailable()) {
            System.exit(1);
        }

        DatabaseConnector.getInstance().ensureSchema();

        configureLookAndFeel();

        startMatchSimulation(configManagerServiceLocalVariableValue);

        SwingUtilities.invokeLater(LeagueManagerApplication::initializeApplication);
    }

    private static void startMatchSimulation(ConfigManager configParameterValue) {
        GameManager gameEntityManagerServiceLocalVariableValue = new GameManager();
        LeagueManager leagueReferenceManagerServiceLocalVariableValue = new LeagueManager();

        MatchSimulationScheduler.start(
                gameEntityManagerServiceLocalVariableValue,
                leagueReferenceManagerServiceLocalVariableValue,
                configParameterValue
        );
    }

    private static boolean isDatabaseAvailable() {
        DatabaseConnector sqlConnectorLocalVariableValue = DatabaseConnector.getInstance();
        return sqlConnectorLocalVariableValue.isConnectionAvailable();
    }

    private static void configureLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception eventArgumentExceptionParameterValue) {
            eventArgumentExceptionParameterValue.printStackTrace();
        }
    }

    private static void initializeApplication() {

        PlayerManager playerProfileManagerServiceLocalVariableValue = new PlayerManager();

        MainView mainViewInterfaceLocalVariableValue = new MainView();

        AppNavigator navigatorLocalVariableValue =
                new AppNavigator(mainViewInterfaceLocalVariableValue);

        // Vistas
        LoginView loginViewInterfaceLocalVariableValue = new LoginView();
        SignUpView signUpViewInterfaceLocalVariableValue = new SignUpView();
        UserProfileView userProfileViewInterfaceLocalVariableValue = new UserProfileView();
        ChangePasswordView changePasswordViewInterfaceLocalVariableValue = new ChangePasswordView();

        // ✅ NUEVA VISTA ADMIN
        AdminMenuView adminMenuViewInterfaceLocalVariableValue = new AdminMenuView();

        // Controladores
        new LoginController(
                loginViewInterfaceLocalVariableValue,
                playerProfileManagerServiceLocalVariableValue,
                navigatorLocalVariableValue
        );

        new SignUpController(
                signUpViewInterfaceLocalVariableValue,
                playerProfileManagerServiceLocalVariableValue,
                navigatorLocalVariableValue
        );

        UserProfileController userProfileControllerHandlerLocalVariableValue =
                new UserProfileController(
                        userProfileViewInterfaceLocalVariableValue,
                        playerProfileManagerServiceLocalVariableValue,
                        navigatorLocalVariableValue
                );

        new ChangePasswordController(
                changePasswordViewInterfaceLocalVariableValue,
                playerProfileManagerServiceLocalVariableValue,
                navigatorLocalVariableValue
        );

        // ✅ CONTROLADOR ADMIN
        new AdminMenuController(
                adminMenuViewInterfaceLocalVariableValue,
                playerProfileManagerServiceLocalVariableValue
        );

        navigatorLocalVariableValue.registerOnShowHook(
                AppNavigator.PROFILE,
                userProfileControllerHandlerLocalVariableValue::refreshCurrentPlayer
        );

        navigatorLocalVariableValue.registerOnShowHook(
                AppNavigator.CHANGE_PASSWORD,
                changePasswordViewInterfaceLocalVariableValue::clearForm
        );

        // Pantallas
        mainViewInterfaceLocalVariableValue.addScreen(
                AppNavigator.LOGIN, loginViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(
                AppNavigator.SIGNUP, signUpViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(
                AppNavigator.PROFILE, userProfileViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(
                AppNavigator.CHANGE_PASSWORD, changePasswordViewInterfaceLocalVariableValue);

        mainViewInterfaceLocalVariableValue.addScreen(
                AppNavigator.ADMIN_MENU, adminMenuViewInterfaceLocalVariableValue);

        navigatorLocalVariableValue.show(AppNavigator.LOGIN);

        mainViewInterfaceLocalVariableValue.setVisible(true);
    }
}

