package presentation;

import bussines.managers.PlayerManager;
import presentation.ControllerViews.LiveMatchesWidgetController;
import presentation.Views.LiveMatchesWidget;

import javax.swing.SwingUtilities;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Servicio singleton que controla el ciclo de vida del widget de
 * partidos en directo.
 */
public final class LiveMatchesWidgetService {

    private static LiveMatchesWidget widgetViewFieldReference;
    private static LiveMatchesWidgetController widgetControllerHandlerFieldReference;
    private static Boolean currentAdminModeFieldReference;

    private static Window anchoredMainWindowFieldReference;
    private static ComponentAdapter mainWindowComponentListenerFieldReference;

    private LiveMatchesWidgetService() {
    }

    public static synchronized void show(PlayerManager playerProfileManagerServiceParameterValue,
                                         boolean isAdminViewerParameterValue) {
        Runnable showRunnableLocalVariableValue = () -> {
            boolean mustRecreateLocalVariableValue =
                    widgetViewFieldReference == null
                            || widgetControllerHandlerFieldReference == null
                            || currentAdminModeFieldReference == null
                            || currentAdminModeFieldReference.booleanValue() != isAdminViewerParameterValue;

            if (mustRecreateLocalVariableValue) {
                shutdownWidgetOnly();

                widgetViewFieldReference = new LiveMatchesWidget();

                widgetViewFieldReference.setOpenLiveMatchesListener(eventArgumentParameterValue -> {
                    AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();
                    if (navigatorLocalVariableValue != null) {
                        navigatorLocalVariableValue.show(AppNavigator.LIVE_MATCHES);
                    }
                });

                widgetControllerHandlerFieldReference =
                        new LiveMatchesWidgetController(
                                widgetViewFieldReference,
                                playerProfileManagerServiceParameterValue,
                                isAdminViewerParameterValue
                        );

                currentAdminModeFieldReference = isAdminViewerParameterValue;
            }

            attachWidgetToMainWindow();

            widgetViewFieldReference.setVisible(true);

            widgetViewFieldReference.positionBottomAttachedTo(
                    resolveMainWindow(),
                    resolveVerticalOffsetForCurrentScreen()
            );

            widgetViewFieldReference.toFront();
        };

        if (SwingUtilities.isEventDispatchThread()) {
            showRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(showRunnableLocalVariableValue);
        }
    }

    public static synchronized void hide() {
        Runnable hideRunnableLocalVariableValue = LiveMatchesWidgetService::shutdownWidgetOnly;

        if (SwingUtilities.isEventDispatchThread()) {
            hideRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(hideRunnableLocalVariableValue);
        }
    }

    public static synchronized void refreshPosition() {
        Runnable refreshRunnableLocalVariableValue = () -> {
            if (widgetViewFieldReference == null) {
                return;
            }

            widgetViewFieldReference.positionBottomAttachedTo(
                    resolveMainWindow(),
                    resolveVerticalOffsetForCurrentScreen()
            );
        };

        if (SwingUtilities.isEventDispatchThread()) {
            refreshRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(refreshRunnableLocalVariableValue);
        }
    }

    private static void attachWidgetToMainWindow() {
        Window mainWindowLocalVariableValue = resolveMainWindow();

        if (mainWindowLocalVariableValue == null || widgetViewFieldReference == null) {
            return;
        }

        if (anchoredMainWindowFieldReference == mainWindowLocalVariableValue
                && mainWindowComponentListenerFieldReference != null) {
            widgetViewFieldReference.positionBottomAttachedTo(
                    mainWindowLocalVariableValue,
                    resolveVerticalOffsetForCurrentScreen()
            );
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
                if (widgetViewFieldReference != null && anchoredMainWindowFieldReference != null) {
                    widgetViewFieldReference.positionBottomAttachedTo(
                            anchoredMainWindowFieldReference,
                            resolveVerticalOffsetForCurrentScreen()
                    );
                }
            }
        };

        anchoredMainWindowFieldReference.addComponentListener(
                mainWindowComponentListenerFieldReference
        );

        widgetViewFieldReference.positionBottomAttachedTo(
                mainWindowLocalVariableValue,
                resolveVerticalOffsetForCurrentScreen()
        );
    }

    private static int resolveVerticalOffsetForCurrentScreen() {
        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();

        if (navigatorLocalVariableValue == null) {
            return 0;
        }

        String currentScreenLocalVariableValue =
                navigatorLocalVariableValue.getCurrentScreenIdentifier();

        if (currentScreenLocalVariableValue == null) {
            return 0;
        }

        switch (currentScreenLocalVariableValue) {
            // Menús principales
            case AppNavigator.ADMIN_MENU:
                return 130;

            case AppNavigator.PLAYER_MENU:
                return 0;

            // Formularios grandes
            case AppNavigator.CREATE_LEAGUE:
                return 170;

            case AppNavigator.CREATE_TEAM:
                return 170;

            case AppNavigator.DELETE_LEAGUE:
                return 150;

            case AppNavigator.DELETE_TEAM:
                return 150;

            case AppNavigator.DELETE_PLAYER:
                return 150;

            // Listados / selección
            case AppNavigator.AVAILABLE_LEAGUES:
                return 140;

            case AppNavigator.TEAM_SELECTION:
                return 150;

            case AppNavigator.LEAGUE_DETAIL:
                return 120;

            case AppNavigator.TEAM_DETAIL:
                return 120;

            case AppNavigator.CALENDAR:
                return 110;

            case AppNavigator.STATISTICS:
                return 120;

            case AppNavigator.LIVE_MATCHES:
                return 110;

            // Pantallas de cuenta
            case AppNavigator.PROFILE:
                return 130;

            case AppNavigator.CHANGE_PASSWORD:
                return 150;

            default:
                return 0;
        }
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

        if (widgetControllerHandlerFieldReference != null) {
            widgetControllerHandlerFieldReference.shutdown();
            widgetControllerHandlerFieldReference = null;
        } else if (widgetViewFieldReference != null) {
            widgetViewFieldReference.dispose();
        }

        widgetViewFieldReference = null;
        currentAdminModeFieldReference = null;
    }
}