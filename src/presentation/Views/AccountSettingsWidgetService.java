package presentation;

import bussines.managers.PlayerManager;
import presentation.Views.AccountSettingsWidget;

import javax.swing.*;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public final class AccountSettingsWidgetService {

    private static AccountSettingsWidget widgetViewFieldReference;
    private static PlayerManager playerProfileManagerServiceFieldReference;

    private static Window anchoredMainWindowFieldReference;
    private static ComponentAdapter mainWindowComponentListenerFieldReference;

    private AccountSettingsWidgetService() {
    }

    public static synchronized void show(PlayerManager playerProfileManagerServiceParameterValue) {
        Runnable showRunnableLocalVariableValue = () -> {
            playerProfileManagerServiceFieldReference =
                    playerProfileManagerServiceParameterValue;

            if (widgetViewFieldReference == null) {
                widgetViewFieldReference = new AccountSettingsWidget();

                widgetViewFieldReference.setAccountActionListener(
                        eventArgumentParameterValue -> {
                            String commandLocalVariableValue =
                                    eventArgumentParameterValue.getActionCommand();

                            if (AccountSettingsWidget.CHANGE_PASSWORD.equals(commandLocalVariableValue)) {
                                handleChangePassword();
                                return;
                            }

                            if (AccountSettingsWidget.LOGOUT.equals(commandLocalVariableValue)) {
                                handleLogout();
                            }
                        }
                );
            }

            attachWidgetToMainWindow();

            widgetViewFieldReference.setVisible(true);
            widgetViewFieldReference.positionBottomLeftAttachedTo(resolveMainWindow());
            widgetViewFieldReference.toFront();
        };

        if (SwingUtilities.isEventDispatchThread()) {
            showRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(showRunnableLocalVariableValue);
        }
    }

    public static synchronized void hide() {
        Runnable hideRunnableLocalVariableValue = AccountSettingsWidgetService::shutdownWidgetOnly;

        if (SwingUtilities.isEventDispatchThread()) {
            hideRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(hideRunnableLocalVariableValue);
        }
    }

    public static synchronized void refreshPosition() {
        Runnable refreshRunnableLocalVariableValue = () -> {
            if (widgetViewFieldReference != null) {
                widgetViewFieldReference.positionBottomLeftAttachedTo(resolveMainWindow());
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            refreshRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(refreshRunnableLocalVariableValue);
        }
    }

    private static void handleChangePassword() {
        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();

        if (navigatorLocalVariableValue == null) {
            return;
        }

        String returnScreenLocalVariableValue =
                navigatorLocalVariableValue.getCurrentScreenIdentifier();

        if (returnScreenLocalVariableValue == null
                || AppNavigator.LOGIN.equals(returnScreenLocalVariableValue)
                || AppNavigator.SIGNUP.equals(returnScreenLocalVariableValue)
                || AppNavigator.DB_ERROR.equals(returnScreenLocalVariableValue)) {
            return;
        }

        navigatorLocalVariableValue.setChangePasswordReturnAction(
                () -> navigatorLocalVariableValue.show(returnScreenLocalVariableValue)
        );

        navigatorLocalVariableValue.show(AppNavigator.CHANGE_PASSWORD);
    }

    private static void handleLogout() {
        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();

        LiveMatchesWidgetService.hide();
        hide();

        if (playerProfileManagerServiceFieldReference != null) {
            playerProfileManagerServiceFieldReference.setCurrentIdentifier(null);
        }

        if (navigatorLocalVariableValue != null) {
            navigatorLocalVariableValue.show(AppNavigator.LOGIN);
        }
    }

    private static void attachWidgetToMainWindow() {
        Window mainWindowLocalVariableValue = resolveMainWindow();

        if (mainWindowLocalVariableValue == null || widgetViewFieldReference == null) {
            return;
        }

        if (anchoredMainWindowFieldReference == mainWindowLocalVariableValue
                && mainWindowComponentListenerFieldReference != null) {
            widgetViewFieldReference.positionBottomLeftAttachedTo(mainWindowLocalVariableValue);
            return;
        }

        detachMainWindowListener();

        anchoredMainWindowFieldReference = mainWindowLocalVariableValue;

        mainWindowComponentListenerFieldReference = new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent eventArgumentParameterValue) {
                repositionWidget();
            }

            @Override
            public void componentResized(ComponentEvent eventArgumentParameterValue) {
                repositionWidget();
            }

            @Override
            public void componentShown(ComponentEvent eventArgumentParameterValue) {
                repositionWidget();
            }

            private void repositionWidget() {
                if (widgetViewFieldReference != null
                        && anchoredMainWindowFieldReference != null) {
                    widgetViewFieldReference.positionBottomLeftAttachedTo(
                            anchoredMainWindowFieldReference
                    );
                }
            }
        };

        anchoredMainWindowFieldReference.addComponentListener(
                mainWindowComponentListenerFieldReference
        );

        widgetViewFieldReference.positionBottomLeftAttachedTo(mainWindowLocalVariableValue);
    }

    private static Window resolveMainWindow() {
        try {
            AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();

            if (navigatorLocalVariableValue != null
                    && navigatorLocalVariableValue.getMainView() != null) {
                return navigatorLocalVariableValue.getMainView();
            }
        } catch (Exception ignoredExceptionParameterValue) {
        }

        return null;
    }

    private static void detachMainWindowListener() {
        if (anchoredMainWindowFieldReference != null
                && mainWindowComponentListenerFieldReference != null) {
            anchoredMainWindowFieldReference.removeComponentListener(
                    mainWindowComponentListenerFieldReference
            );
        }

        anchoredMainWindowFieldReference = null;
        mainWindowComponentListenerFieldReference = null;
    }

    private static void shutdownWidgetOnly() {
        detachMainWindowListener();

        if (widgetViewFieldReference != null) {
            widgetViewFieldReference.dispose();
            widgetViewFieldReference = null;
        }
    }
}