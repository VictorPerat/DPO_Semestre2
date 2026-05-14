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