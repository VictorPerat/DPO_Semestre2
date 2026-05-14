package presentation;

import presentation.Views.MainView;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Coordina la navegacion principal.
 */
public class AppNavigator {

    /**
     * Identificador de la pantalla del inicio de sesion.
     */
    public static final String LOGIN = "LOGIN";
    /**
     * Identificador de la pantalla del registro.
     */
    public static final String SIGNUP = "SIGNUP";
    /**
     * Identificador de la pantalla del cambio de contrasena.
     */
    public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";
    /**
     * Identificador de la pantalla del menu de administrador.
     */
    public static final String ADMIN_MENU = "ADMIN_MENU";
    /**
     * Identificador de la pantalla del menu de jugador.
     */
    public static final String PLAYER_MENU = "PLAYER_MENU";
    /**
     * Identificador de la pantalla de las ligas disponibles.
     */
    public static final String AVAILABLE_LEAGUES = "AVAILABLE_LEAGUES";
    /**
     * Identificador de la pantalla del detalle de la liga.
     */
    public static final String LEAGUE_DETAIL = "LEAGUE_DETAIL";
    /**
     * Identificador de la pantalla de la creacion de liga.
     */
    public static final String CREATE_LEAGUE = "CREATE_LEAGUE";
    /**
     * Identificador de la pantalla de la seleccion de equipo.
     */
    public static final String TEAM_SELECTION = "TEAM_SELECTION";
    /**
     * Identificador de la pantalla de la creacion de equipo.
     */
    public static final String CREATE_TEAM = "CREATE_TEAM";
    /**
     * Identificador de la pantalla de la eliminacion de liga.
     */
    public static final String DELETE_LEAGUE = "DELETE_LEAGUE";
    /**
     * Identificador de la pantalla de la eliminacion de equipo.
     */
    public static final String DELETE_TEAM = "DELETE_TEAM";
    /**
     * Identificador de la pantalla de la eliminacion de jugador.
     */
    public static final String DELETE_PLAYER = "DELETE_PLAYER";
    /**
     * Identificador de la pantalla del calendario.
     */
    public static final String CALENDAR = "CALENDAR";
    /**
     * Identificador de la pantalla del detalle del equipo.
     */
    public static final String TEAM_DETAIL = "TEAM_DETAIL";
    /**
     * Identificador de la pantalla de las estadisticas.
     */
    public static final String STATISTICS = "STATISTICS";
    /**
     * Identificador de la pantalla del partido en directo.
     */
    public static final String LIVE_MATCH = "LIVE_MATCH";
    /**
     * Identificador de la pantalla de los partidos en directo.
     */
    public static final String LIVE_MATCHES = "LIVE_MATCHES";
    /**
     * Identificador de la pantalla del error de base de datos.
     */
    public static final String DB_ERROR = "DB_ERROR";

    private static AppNavigator instanceFieldReference;

    private final MainView mainViewInterfaceFieldReference;
    private final Map<String, Runnable> onShowHooksFieldReference = new HashMap<>();
    private final Map<String, Runnable> onHideHooksFieldReference = new HashMap<>();

    private Runnable changePasswordReturnActionFieldReference;
    private Runnable returnActionFieldReference;
    private String currentScreenIdentifierFieldReference;


    /**
     * Crea una instancia de appnavigator.
     *
     * @param mainViewInterfaceParameterValue vista principal que usa el navegador.
     */
    public AppNavigator(MainView mainViewInterfaceParameterValue) {
        this.mainViewInterfaceFieldReference = mainViewInterfaceParameterValue;
        instanceFieldReference = this;
    }


    /**
     * Devuelve el instancia.
     *
     * @return el instancia.
     */
    public static AppNavigator getInstance() {
        return instanceFieldReference;
    }


    /**
     * Muestra el contenido.
     *
     * @param screenIdentifierParameterValue identificador de la pantalla.
     */
    public void show(String screenIdentifierParameterValue) {
        if (currentScreenIdentifierFieldReference != null
                && !currentScreenIdentifierFieldReference.equals(screenIdentifierParameterValue)) {

            Runnable hideHookLocalVariableValue =
                    onHideHooksFieldReference.get(currentScreenIdentifierFieldReference);

            if (hideHookLocalVariableValue != null) {
                hideHookLocalVariableValue.run();
            }
        }

        currentScreenIdentifierFieldReference = screenIdentifierParameterValue;

        mainViewInterfaceFieldReference.showScreen(screenIdentifierParameterValue);

        if (!mainViewInterfaceFieldReference.isVisible()) {
            mainViewInterfaceFieldReference.setVisible(true);
        }

        mainViewInterfaceFieldReference.toFront();
        mainViewInterfaceFieldReference.requestFocus();

        Runnable hookLocalVariableValue =
                onShowHooksFieldReference.get(screenIdentifierParameterValue);

        if (hookLocalVariableValue != null) {
            hookLocalVariableValue.run();
        }


        LiveMatchesWidgetService.refreshPosition();
        AccountSettingsWidgetService.refreshPosition();
    }


    /**
     * Registra la accion.
     *
     * @param screenIdentifierParameterValue identificador de la pantalla.
     * @param hookParameterValue accion que se registra en este punto.
     */
    public void registerOnShowHook(String screenIdentifierParameterValue,
                                   Runnable hookParameterValue) {
        onShowHooksFieldReference.put(screenIdentifierParameterValue, hookParameterValue);
    }


    /**
     * Registra la accion.
     *
     * @param screenIdentifierParameterValue identificador de la pantalla.
     * @param hookParameterValue accion que se registra en este punto.
     */
    public void registerOnHideHook(String screenIdentifierParameterValue,
                                   Runnable hookParameterValue) {
        onHideHooksFieldReference.put(screenIdentifierParameterValue, hookParameterValue);
    }


    /**
     * Devuelve el principal vista.
     *
     * @return el principal vista.
     */
    public MainView getMainView() {
        return mainViewInterfaceFieldReference;
    }


    /**
     * Devuelve el actual pantalla identificador.
     *
     * @return el actual pantalla identificador.
     */
    public String getCurrentScreenIdentifier() {
        return currentScreenIdentifierFieldReference;
    }


    /**
     * Actualiza el cambio contrasena vuelta accion.
     *
     * @param returnActionParameterValue accion que se ejecuta al volver.
     */
    public void setChangePasswordReturnAction(Runnable returnActionParameterValue) {
        this.changePasswordReturnActionFieldReference = returnActionParameterValue;
    }


    /**
     * Gestiona esta operacion.
     */
    public void finishChangePasswordFlow() {
        Runnable pendingActionLocalVariableValue =
                this.changePasswordReturnActionFieldReference;

        this.changePasswordReturnActionFieldReference = null;

        if (pendingActionLocalVariableValue != null) {
            SwingUtilities.invokeLater(pendingActionLocalVariableValue);
        } else {
            show(LOGIN);
        }
    }


    /**
     * Actualiza el vuelta accion.
     *
     * @param returnActionParameterValue accion que se ejecuta al volver.
     */
    public void setReturnAction(Runnable returnActionParameterValue) {
        this.returnActionFieldReference = returnActionParameterValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param fallbackScreenIdentifierParameterValue pantalla que se usa cuando no hay accion de vuelta.
     */
    public void runReturnActionOrShow(String fallbackScreenIdentifierParameterValue) {
        Runnable pendingActionLocalVariableValue =
                this.returnActionFieldReference;

        this.returnActionFieldReference = null;

        if (pendingActionLocalVariableValue != null) {
            SwingUtilities.invokeLater(pendingActionLocalVariableValue);
        } else {
            show(fallbackScreenIdentifierParameterValue);
        }
    }
}


