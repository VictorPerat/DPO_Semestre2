package presentation;

import bussines.managers.PlayerManager;
import presentation.ControllerViews.LiveMatchesWidgetController;
import presentation.Views.LiveMatchesWidget;

import javax.swing.SwingUtilities;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


/**
 * Gestiona la logica del directo partidos widget.
 */
public final class LiveMatchesWidgetService {

    private static LiveMatchesWidget widgetViewFieldReference;
    private static LiveMatchesWidgetController widgetControllerHandlerFieldReference;
    private static Boolean currentAdminModeFieldReference;

    private static Window anchoredMainWindowFieldReference;
    private static ComponentAdapter mainWindowComponentListenerFieldReference;


    /**
     * Crea una instancia de el directo partidos widget.
     */
    private LiveMatchesWidgetService() {
    }


    /**
     * Muestra el contenido.
     *
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     * @param isAdminViewerParameterValue administrador que usa la operacion.
     */
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

            widgetViewFieldReference.positionRightAttachedTo(
                    resolveMainWindow()
            );

            widgetViewFieldReference.toFront();
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
        Runnable hideRunnableLocalVariableValue = LiveMatchesWidgetService::shutdownWidgetOnly;

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
            if (widgetViewFieldReference == null) {
                return;
            }

            widgetViewFieldReference.positionRightAttachedTo(
                    resolveMainWindow()
            );
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
    private static void attachWidgetToMainWindow() {
        Window mainWindowLocalVariableValue = resolveMainWindow();

        if (mainWindowLocalVariableValue == null || widgetViewFieldReference == null) {
            return;
        }

        if (anchoredMainWindowFieldReference == mainWindowLocalVariableValue
                && mainWindowComponentListenerFieldReference != null) {
            widgetViewFieldReference.positionRightAttachedTo(
                    mainWindowLocalVariableValue
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
                    widgetViewFieldReference.positionRightAttachedTo(
                            anchoredMainWindowFieldReference
                    );
                }
            }
        };

        anchoredMainWindowFieldReference.addComponentListener(
                mainWindowComponentListenerFieldReference
        );

        widgetViewFieldReference.positionRightAttachedTo(
                mainWindowLocalVariableValue
        );
    }


    /**
     * Gestiona esta operacion.
     *
     * @return resultado de la operacion.
     */
    @SuppressWarnings("unused")
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

            case AppNavigator.ADMIN_MENU:
                return 130;

            case AppNavigator.PLAYER_MENU:
                return 0;


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


            case AppNavigator.PROFILE:
                return 130;

            case AppNavigator.CHANGE_PASSWORD:
                return 150;

            default:
                return 0;
        }
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