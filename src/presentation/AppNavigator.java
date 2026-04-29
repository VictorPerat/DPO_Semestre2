package presentation;

import presentation.Views.MainView;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/** Navegador central: un único JFrame (MainView) + CardLayout. */
public class AppNavigator {

    public static final String LOGIN = "LOGIN";
    public static final String SIGNUP = "SIGNUP";
    public static final String PROFILE = "PROFILE";
    public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";
    public static final String ADMIN_MENU = "ADMIN_MENU";
    public static final String PLAYER_MENU = "PLAYER_MENU";
    public static final String AVAILABLE_LEAGUES = "AVAILABLE_LEAGUES";
    public static final String LEAGUE_DETAIL = "LEAGUE_DETAIL";
    public static final String CREATE_LEAGUE = "CREATE_LEAGUE";
    public static final String TEAM_SELECTION = "TEAM_SELECTION";
    public static final String CREATE_TEAM = "CREATE_TEAM";
    public static final String DELETE_LEAGUE = "DELETE_LEAGUE";
    public static final String DELETE_TEAM = "DELETE_TEAM";
    public static final String DELETE_PLAYER = "DELETE_PLAYER";
    public static final String CALENDAR = "CALENDAR";
    public static final String TEAM_DETAIL = "TEAM_DETAIL";
    public static final String STATISTICS = "STATISTICS";
    public static final String LIVE_MATCH = "LIVE_MATCH";
    public static final String LIVE_MATCHES = "LIVE_MATCHES";
    public static final String DB_ERROR = "DB_ERROR";

    private static AppNavigator instanceFieldReference;
    private final MainView mainViewInterfaceFieldReference;
    private final Map<String, Runnable> onShowHooksFieldReference = new HashMap<>();
    private final Map<String, Runnable> onHideHooksFieldReference = new HashMap<>();
    private Runnable changePasswordReturnActionFieldReference;
    private Runnable returnActionFieldReference;
    private String currentScreenIdentifierFieldReference;

    public AppNavigator(MainView mainViewInterfaceParameterValue) {
        this.mainViewInterfaceFieldReference = mainViewInterfaceParameterValue;
        instanceFieldReference = this;
    }

    public static AppNavigator getInstance() { return instanceFieldReference; }

    public void show(String screenIdentifierParameterValue) {
        if (currentScreenIdentifierFieldReference != null
                && !currentScreenIdentifierFieldReference.equals(screenIdentifierParameterValue)) {
            Runnable hideHookLocalVariableValue = onHideHooksFieldReference.get(currentScreenIdentifierFieldReference);
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

        Runnable hookLocalVariableValue = onShowHooksFieldReference.get(screenIdentifierParameterValue);
        if (hookLocalVariableValue != null) {
            hookLocalVariableValue.run();
        }
    }

    public void registerOnShowHook(String screenIdentifierParameterValue, Runnable hookParameterValue) {
        onShowHooksFieldReference.put(screenIdentifierParameterValue, hookParameterValue);
    }

    public void registerOnHideHook(String screenIdentifierParameterValue, Runnable hookParameterValue) {
        onHideHooksFieldReference.put(screenIdentifierParameterValue, hookParameterValue);
    }

    public MainView getMainView() { return mainViewInterfaceFieldReference; }

    public void hideMainWindow() { mainViewInterfaceFieldReference.setVisible(false); }

    public void setChangePasswordReturnAction(Runnable returnActionParameterValue) {
        this.changePasswordReturnActionFieldReference = returnActionParameterValue;
    }

    public void finishChangePasswordFlow() {
        Runnable pendingActionLocalVariableValue = this.changePasswordReturnActionFieldReference;
        this.changePasswordReturnActionFieldReference = null;
        if (pendingActionLocalVariableValue != null) {
            SwingUtilities.invokeLater(pendingActionLocalVariableValue);
        } else {
            show(PROFILE);
        }
    }

    public void setReturnAction(Runnable returnActionParameterValue) {
        this.returnActionFieldReference = returnActionParameterValue;
    }

    public void runReturnActionOrShow(String fallbackScreenIdentifierParameterValue) {
        Runnable pendingActionLocalVariableValue = this.returnActionFieldReference;
        this.returnActionFieldReference = null;
        if (pendingActionLocalVariableValue != null) {
            SwingUtilities.invokeLater(pendingActionLocalVariableValue);
        } else {
            show(fallbackScreenIdentifierParameterValue);
        }
    }
}
