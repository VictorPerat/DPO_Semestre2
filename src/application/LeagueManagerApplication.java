package application;

import bussines.MatchSimulationScheduler;
import bussines.managers.ConfigManager;
import bussines.managers.GameManager;
import bussines.managers.LeagueManager;
import bussines.managers.PlayerManager;
import persistance.DatabaseConnector;
import presentation.AppNavigator;
import presentation.ControllerViews.ChangePasswordController;
import presentation.ControllerViews.LoginController;
import presentation.ControllerViews.SignUpController;
import presentation.ControllerViews.UserProfileController;
import presentation.Views.ChangePasswordView;
import presentation.Views.LoginView;
import presentation.Views.MainView;
import presentation.Views.SignUpView;
import presentation.Views.UserProfileView;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class LeagueManagerApplication {

    // Evita que se pueda crear un objeto de esta clase
    private LeagueManagerApplication() {
    }

    // Función principal para arrancar la aplicación
    public static void launch() {
        // Cargamos la configuración (config.json) antes que nada
        // para que el resto del sistema (BD, login admin, ...) la
        // tenga disponible.
        ConfigManager configManagerServiceLocalVariableValue = new ConfigManager();

        // Primero comprobamos si la base de datos está disponible
        if (!isDatabaseAvailable()) {
            System.exit(1);
        }

        // Aplica migraciones idempotentes (winner_name en games, etc.)
        DatabaseConnector.getInstance().ensureSchema();

        // Aplicamos el estilo visual del sistema operativo
        configureLookAndFeel();

        // Arrancamos la simulación automática de partidos en segundo
        // plano (apartados 2.6.1 y 2.8 del enunciado). El scheduler
        // mantendrá un MatchRunner por liga, también para las ligas
        // creadas mientras la app esté corriendo.
        startMatchSimulation(configManagerServiceLocalVariableValue);

        // Iniciamos la interfaz en el hilo de Swing
        SwingUtilities.invokeLater(LeagueManagerApplication::initializeApplication);
    }

    /**
     * Lanza el supervisor que crea un {@link bussines.MatchRunner} por
     * cada liga existente y por cada liga que se cree en runtime.
     */
    private static void startMatchSimulation(ConfigManager configParameterValue) {
        GameManager gameEntityManagerServiceLocalVariableValue = new GameManager();
        LeagueManager leagueReferenceManagerServiceLocalVariableValue = new LeagueManager();

        MatchSimulationScheduler.start(
                gameEntityManagerServiceLocalVariableValue,
                leagueReferenceManagerServiceLocalVariableValue,
                configParameterValue
        );
    }

    // Comprueba si hay conexión con la base de datos
    private static boolean isDatabaseAvailable() {
        DatabaseConnector sqlConnectorLocalVariableValue = DatabaseConnector.getInstance();
        return sqlConnectorLocalVariableValue.isConnectionAvailable();
    }

    // Configura el aspecto visual de la app
    private static void configureLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception eventArgumentExceptionParameterValue) {
            eventArgumentExceptionParameterValue.printStackTrace();
        }
    }

    // Crea e inicializa todas las vistas, controladores y navegación
    private static void initializeApplication() {
        // Creamos el manager principal de jugadores
        PlayerManager playerProfileManagerServiceLocalVariableValue = new PlayerManager();

        // Creamos la ventana principal
        MainView mainViewInterfaceLocalVariableValue = new MainView();

        // Creamos el navegador para cambiar entre pantallas
        AppNavigator navigatorLocalVariableValue =
                new AppNavigator(mainViewInterfaceLocalVariableValue);

        // Creamos las vistas
        LoginView loginViewInterfaceLocalVariableValue = new LoginView();
        SignUpView signUpViewInterfaceLocalVariableValue = new SignUpView();
        UserProfileView userProfileViewInterfaceLocalVariableValue = new UserProfileView();
        ChangePasswordView changePasswordViewInterfaceLocalVariableValue = new ChangePasswordView();

        // Creamos los controladores y los conectamos con las vistas
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

        // Cuando se abra el perfil, actualizamos los datos del usuario actual
        navigatorLocalVariableValue.registerOnShowHook(
                AppNavigator.PROFILE,
                userProfileControllerHandlerLocalVariableValue::refreshCurrentPlayer
        );

        // Cuando se abra la pantalla de cambiar contraseña, limpiamos el formulario
        navigatorLocalVariableValue.registerOnShowHook(
                AppNavigator.CHANGE_PASSWORD,
                changePasswordViewInterfaceLocalVariableValue::clearForm
        );

        // Añadimos las pantallas a la ventana principal
        mainViewInterfaceLocalVariableValue.addScreen(
                AppNavigator.LOGIN, loginViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(
                AppNavigator.SIGNUP, signUpViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(
                AppNavigator.PROFILE, userProfileViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(
                AppNavigator.CHANGE_PASSWORD, changePasswordViewInterfaceLocalVariableValue);

        // Mostramos primero la pantalla de login
        navigatorLocalVariableValue.show(AppNavigator.LOGIN);

        // Hacemos visible la ventana principal
        mainViewInterfaceLocalVariableValue.setVisible(true);
    }
}