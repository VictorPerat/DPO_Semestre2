import bussines.managers.PlayerManager;
import persistance.DatabaseConnector;
import presentation.ControllerViews.LoginController;
import presentation.Views.LoginView;

import javax.swing.*;

/**
 * Punto de entrada de la aplicación.
 * De momento tenemos login, registro, perfil del usuario, canvio de contraseña y logout.
 */

public class Main {

    public static void main(String[] args) {

        // Pedimos la instacia de DatabaseConnector
        DatabaseConnector sqlConnectorLocalVariableValue = DatabaseConnector.getInstance();

        // Si no esta conectada la base de datos, entonces imprimimos error
        // "La base de datos no esta conectada. Inicia MySQL en XAMPP antes de abrir la aplicación."
        if (!sqlConnectorLocalVariableValue.isConnectionAvailable()) {
            System.exit(1);
        }

        //
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception eventArgumentExceptionParameterValue) {
            eventArgumentExceptionParameterValue.printStackTrace();
        }

        //
        SwingUtilities.invokeLater(() -> {
            PlayerManager playerProfileManagerServiceLocalVariableValue = new PlayerManager();
            LoginView loginViewInterfaceLocalVariableValue = new LoginView();
            new LoginController(loginViewInterfaceLocalVariableValue, playerProfileManagerServiceLocalVariableValue);
        });
    }
}
