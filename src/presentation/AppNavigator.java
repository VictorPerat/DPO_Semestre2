package presentation;

import presentation.Views.MainView;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Navegador central de la aplicación.
 *
 * - Centraliza la navegación entre las pantallas registradas en {@link MainView}.
 * - Sustituye al antiguo patrón "dispose() + new OtraView()".
 * - Se expone como singleton para que controladores legacy (PlayerMenu,
 *   AdminMenu, etc., que de momento siguen siendo JFrame independientes)
 *   puedan invocar la navegación al login o al cambio de contraseña sin
 *   necesidad de pasarse referencias entre constructores.
 */
public class AppNavigator {

    public static final String LOGIN = "LOGIN";
    public static final String SIGNUP = "SIGNUP";
    public static final String PROFILE = "PROFILE";
    public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";

    private static AppNavigator instanceFieldReference;

    private final MainView mainViewInterfaceFieldReference;

    /**
     * Acción a ejecutar cuando el flujo de cambio de contraseña termina
     * (ya sea por "Back" o por guardar correctamente). Permite que las
     * pantallas legacy de tipo JFrame (PlayerMenu, AdminMenu, ...) puedan
     * indicar a dónde volver tras cambiar la contraseña.
     *
     * Si es null, ChangePasswordController vuelve por defecto al PROFILE.
     */
    private Runnable changePasswordReturnActionFieldReference;

    /**
     * Hooks que se ejecutan cuando una pantalla se muestra.
     * Permite, por ejemplo, refrescar los datos del perfil cada vez
     * que se navega a PROFILE sin acoplar el LoginController al
     * UserProfileController.
     */
    private final Map<String, Runnable> onShowHooksFieldReference = new HashMap<>();

    public AppNavigator(MainView mainViewInterfaceParameterValue) {
        this.mainViewInterfaceFieldReference = mainViewInterfaceParameterValue;
        instanceFieldReference = this;
    }

    public static AppNavigator getInstance() {
        return instanceFieldReference;
    }

    /**
     * Muestra la pantalla con el identificador indicado y asegura que la
     * ventana principal esté visible y al frente.
     */
    public void show(String screenIdentifierParameterValue) {
        mainViewInterfaceFieldReference.showScreen(screenIdentifierParameterValue);
        if (!mainViewInterfaceFieldReference.isVisible()) {
            mainViewInterfaceFieldReference.setVisible(true);
        }
        mainViewInterfaceFieldReference.toFront();
        mainViewInterfaceFieldReference.requestFocus();

        Runnable hookLocalVariableValue = onShowHooksFieldReference.get(screenIdentifierParameterValue);
        if (hookLocalVariableValue != null) {
            hookLocalVariableValue.run();
        }
    }

    /**
     * Registra una acción que se ejecutará cada vez que se muestre la
     * pantalla indicada. Útil para refrescar datos al entrar.
     */
    public void registerOnShowHook(String screenIdentifierParameterValue, Runnable hookParameterValue) {
        onShowHooksFieldReference.put(screenIdentifierParameterValue, hookParameterValue);
    }

    /**
     * Oculta la ventana principal sin destruirla.
     * Útil cuando la navegación pasa a una pantalla legacy basada en JFrame.
     */
    public void hideMainWindow() {
        mainViewInterfaceFieldReference.setVisible(false);
    }

    public MainView getMainView() {
        return mainViewInterfaceFieldReference;
    }

    /**
     * Permite a una pantalla legacy registrar a dónde quiere volver
     * después del cambio de contraseña.
     */
    public void setChangePasswordReturnAction(Runnable returnActionParameterValue) {
        this.changePasswordReturnActionFieldReference = returnActionParameterValue;
    }

    /**
     * Llamado por ChangePasswordController al terminar el flujo.
     * Si hay una acción registrada, la consume y la ejecuta. Si no,
     * vuelve a la pantalla de PROFILE por defecto.
     */
    public void finishChangePasswordFlow() {
        Runnable pendingActionLocalVariableValue = this.changePasswordReturnActionFieldReference;
        this.changePasswordReturnActionFieldReference = null;

        if (pendingActionLocalVariableValue != null) {
            SwingUtilities.invokeLater(pendingActionLocalVariableValue);
        } else {
            show(PROFILE);
        }
    }
}
