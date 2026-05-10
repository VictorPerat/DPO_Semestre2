package presentation;

import Rounded.ConfigDialog;
import bussines.managers.ConfigManager;
import bussines.managers.PlayerManager;
import presentation.Views.AccountSettingsWidget;

import javax.swing.*;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


/**
 * Gestiona la logica del cuenta ajustes widget.
 */
public final class AccountSettingsWidgetService {

    private static AccountSettingsWidget widgetViewFieldReference;
    private static PlayerManager playerProfileManagerServiceFieldReference;
    private static UserRole currentUserRoleFieldReference = UserRole.PLAYER;

    private static Window anchoredMainWindowFieldReference;
    private static ComponentAdapter mainWindowComponentListenerFieldReference;


    /**
     * Crea una instancia de el cuenta ajustes widget.
     */
    private AccountSettingsWidgetService() {
    }


    /**
     * Muestra el contenido.
     *
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     */
    public static synchronized void show(PlayerManager playerProfileManagerServiceParameterValue) {
        Runnable showRunnableLocalVariableValue = () -> {
            playerProfileManagerServiceFieldReference =
                    playerProfileManagerServiceParameterValue;
            currentUserRoleFieldReference = resolveCurrentUserRole();

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

                            if (AccountSettingsWidget.DELETE_ACCOUNT.equals(commandLocalVariableValue)) {
                                handleDeleteAccount();
                                return;
                            }

                            if (AccountSettingsWidget.LOGOUT.equals(commandLocalVariableValue)) {
                                handleLogout();
                            }
                        }
                );
            }

            widgetViewFieldReference.configureForRole(currentUserRoleFieldReference);
            attachWidgetToMainWindow();

            widgetViewFieldReference.setVisible(true);
            widgetViewFieldReference.positionBottomLeftAttachedTo(resolveMainWindow());
            bringWidgetToFront();
        };

        if (SwingUtilities.isEventDispatchThread()) {
            showRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(showRunnableLocalVariableValue);
        }
    }


    /**
     * Oculta el contenido.
     */
    public static synchronized void hide() {
        Runnable hideRunnableLocalVariableValue = AccountSettingsWidgetService::shutdownWidgetOnly;

        if (SwingUtilities.isEventDispatchThread()) {
            hideRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(hideRunnableLocalVariableValue);
        }
    }


    /**
     * Gestiona esta operacion.
     */
    public static synchronized void refreshPosition() {
        Runnable refreshRunnableLocalVariableValue = () -> {
            if (widgetViewFieldReference != null) {
                widgetViewFieldReference.configureForRole(resolveCurrentUserRole());
                widgetViewFieldReference.positionBottomLeftAttachedTo(resolveMainWindow());
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            refreshRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(refreshRunnableLocalVariableValue);
        }
    }


    /**
     * Gestiona esta operacion.
     */
    private static void handleChangePassword() {
        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();
        String currentScreenLocalVariableValue =
                navigatorLocalVariableValue == null
                        ? null
                        : navigatorLocalVariableValue.getCurrentScreenIdentifier();

        openChangePasswordFor(currentScreenLocalVariableValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param dialogParameterValue dialogo que usa la operacion.
     * @param returnScreenIdentifierParameterValue identificador de la pantalla.
     */
    public static void configureDialogForCurrentSession(ConfigDialog dialogParameterValue,
                                                        String returnScreenIdentifierParameterValue) {
        dialogParameterValue.configureForRole(resolveCurrentUserRole());
        dialogParameterValue.registerController(eventArgumentParameterValue -> {
            dialogParameterValue.dispose();

            String commandLocalVariableValue =
                    eventArgumentParameterValue.getActionCommand();

            if (ConfigDialog.LOGOUT.equals(commandLocalVariableValue)) {
                handleLogout();
                return;
            }

            if (ConfigDialog.DELETE_ACCOUNT.equals(commandLocalVariableValue)) {
                handleDeleteAccount();
                return;
            }

            if (ConfigDialog.CHANGE_PASSWORD.equals(commandLocalVariableValue)) {
                openChangePasswordFor(returnScreenIdentifierParameterValue);
            }
        });
        dialogParameterValue.setBackButtonListener(
                eventArgumentParameterValue -> dialogParameterValue.dispose()
        );
    }


    /**
     * Abre el cambio contrasena.
     *
     * @param returnScreenIdentifierParameterValue identificador de la pantalla.
     */
    private static void openChangePasswordFor(String returnScreenIdentifierParameterValue) {
        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();

        if (navigatorLocalVariableValue == null) {
            return;
        }

        String returnScreenLocalVariableValue =
                returnScreenIdentifierParameterValue != null
                        ? returnScreenIdentifierParameterValue
                        : navigatorLocalVariableValue.getCurrentScreenIdentifier();

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


    /**
     * Gestiona esta operacion.
     */
    private static void handleLogout() {
        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();
        ConfigDialog.closeInstance();

        LiveMatchesWidgetService.hide();
        hide();

        if (playerProfileManagerServiceFieldReference != null) {
            playerProfileManagerServiceFieldReference.setCurrentIdentifier(null);
        }

        if (navigatorLocalVariableValue != null) {
            navigatorLocalVariableValue.show(AppNavigator.LOGIN);
        }
    }


    /**
     * Gestiona esta operacion.
     */
    private static void handleDeleteAccount() {
        if (playerProfileManagerServiceFieldReference == null) {
            return;
        }

        String currentIdentifierLocalVariableValue =
                playerProfileManagerServiceFieldReference.getCurrentIdentifier();


        if (ConfigManager.getAdminIdentifier()
                .equalsIgnoreCase(currentIdentifierLocalVariableValue)) {
            JOptionPane.showMessageDialog(
                    resolveMainWindow(),
                    "The admin account cannot be deleted.",
                    "Delete Account",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirmLocalVariableValue = JOptionPane.showConfirmDialog(
                resolveMainWindow(),
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmLocalVariableValue != JOptionPane.YES_OPTION) {
            return;
        }


        playerProfileManagerServiceFieldReference.purgeUserDataFromDisk();


        boolean successLocalVariableValue =
                playerProfileManagerServiceFieldReference.deleteCurrentPlayer();


        playerProfileManagerServiceFieldReference.setCurrentIdentifier(null);
        LiveMatchesWidgetService.hide();
        hide();

        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();
        if (navigatorLocalVariableValue != null) {
            navigatorLocalVariableValue.show(AppNavigator.LOGIN);
        }

        if (!successLocalVariableValue) {


            JOptionPane.showMessageDialog(
                    null,
                    "Account deletion encountered an error, but you have been logged out.",
                    "Delete Account",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }


    /**
     * Gestiona esta operacion.
     *
     * @return resultado de la operacion.
     */
    private static UserRole resolveCurrentUserRole() {
        String currentIdentifierLocalVariableValue = null;

        if (playerProfileManagerServiceFieldReference != null) {
            currentIdentifierLocalVariableValue =
                    playerProfileManagerServiceFieldReference.getCurrentIdentifier();
        }

        if (currentIdentifierLocalVariableValue == null) {
            currentIdentifierLocalVariableValue =
                    PlayerManager.currentIdentifierFieldReference;
        }

        if (currentIdentifierLocalVariableValue != null
                && ConfigManager.getAdminIdentifier().equalsIgnoreCase(
                currentIdentifierLocalVariableValue
        )) {
            currentUserRoleFieldReference = UserRole.ADMIN;
        } else {
            currentUserRoleFieldReference = UserRole.PLAYER;
        }

        return currentUserRoleFieldReference;
    }


    /**
     * Gestiona esta operacion.
     */
    private static void attachWidgetToMainWindow() {
        Window mainWindowLocalVariableValue = resolveMainWindow();

        if (mainWindowLocalVariableValue == null || widgetViewFieldReference == null) {
            return;
        }

        if (anchoredMainWindowFieldReference == mainWindowLocalVariableValue
                && mainWindowComponentListenerFieldReference != null) {
            ensureWidgetAttachedToLayer(mainWindowLocalVariableValue);
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

        ensureWidgetAttachedToLayer(mainWindowLocalVariableValue);
        widgetViewFieldReference.positionBottomLeftAttachedTo(mainWindowLocalVariableValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @return resultado de la operacion.
     */
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


    /**
     * Gestiona esta operacion.
     */
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


    /**
     * Gestiona esta operacion.
     */
    private static void shutdownWidgetOnly() {
        detachMainWindowListener();
        ConfigDialog.closeInstance();

        if (widgetViewFieldReference != null) {
            Container parentLocalVariableValue = widgetViewFieldReference.getParent();
            if (parentLocalVariableValue != null) {
                parentLocalVariableValue.remove(widgetViewFieldReference);
                parentLocalVariableValue.revalidate();
                parentLocalVariableValue.repaint();
            }
            widgetViewFieldReference = null;
        }

        currentUserRoleFieldReference = UserRole.PLAYER;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param mainWindowParameterValue principal ventana.
     */
    private static void ensureWidgetAttachedToLayer(Window mainWindowParameterValue) {
        if (widgetViewFieldReference == null
                || !(mainWindowParameterValue instanceof RootPaneContainer)) {
            return;
        }

        JLayeredPane layeredPaneLocalVariableValue =
                ((RootPaneContainer) mainWindowParameterValue).getLayeredPane();

        if (widgetViewFieldReference.getParent() != layeredPaneLocalVariableValue) {
            Container currentParentLocalVariableValue = widgetViewFieldReference.getParent();
            if (currentParentLocalVariableValue != null) {
                currentParentLocalVariableValue.remove(widgetViewFieldReference);
            }

            layeredPaneLocalVariableValue.add(
                    widgetViewFieldReference,
                    JLayeredPane.PALETTE_LAYER
            );
        }

        layeredPaneLocalVariableValue.revalidate();
        layeredPaneLocalVariableValue.repaint();
    }


    /**
     * Gestiona esta operacion.
     */
    private static void bringWidgetToFront() {
        if (widgetViewFieldReference == null) {
            return;
        }

        Container parentLocalVariableValue = widgetViewFieldReference.getParent();
        if (parentLocalVariableValue instanceof JLayeredPane) {
            JLayeredPane layeredPaneLocalVariableValue =
                    (JLayeredPane) parentLocalVariableValue;
            layeredPaneLocalVariableValue.moveToFront(widgetViewFieldReference);
            layeredPaneLocalVariableValue.repaint();
        }
    }
}


