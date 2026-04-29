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
 *
 * Cumple el apartado 2.9:
 * - se muestra tras login;
 * - queda siempre visible encima de la app;
 * - admin ve todos los partidos;
 * - jugador ve solo partidos de sus ligas;
 * - se oculta en logout.
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
            widgetViewFieldReference.positionBottomAttachedTo(resolveMainWindow());
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

    private static void attachWidgetToMainWindow() {
        Window mainWindowLocalVariableValue = resolveMainWindow();

        if (mainWindowLocalVariableValue == null || widgetViewFieldReference == null) {
            return;
        }

        if (anchoredMainWindowFieldReference == mainWindowLocalVariableValue
                && mainWindowComponentListenerFieldReference != null) {
            widgetViewFieldReference.positionBottomAttachedTo(mainWindowLocalVariableValue);
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
                    widgetViewFieldReference.positionBottomAttachedTo(anchoredMainWindowFieldReference);
                }
            }
        };

        anchoredMainWindowFieldReference.addComponentListener(
                mainWindowComponentListenerFieldReference
        );

        widgetViewFieldReference.positionBottomAttachedTo(mainWindowLocalVariableValue);
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
