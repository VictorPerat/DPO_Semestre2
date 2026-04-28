package presentation;

import bussines.LiveMatchesScoreboard;
import bussines.managers.PlayerManager;
import presentation.ControllerViews.LiveMatchesWidgetController;
import presentation.Views.LiveMatchesWidget;

import javax.swing.SwingUtilities;

/**
 * Servicio singleton que controla el ciclo de vida del widget de
 * partidos en directo (apartado 2.9 del enunciado).
 *
 * - {@link #show(PlayerManager, boolean)} se llama tras un login
 *   correcto para arrancar el widget en pantalla.
 * - {@link #hide()} se llama al hacer logout para retirarlo y vaciar
 *   el {@link LiveMatchesScoreboard}.
 *
 * Como es singleton, cualquier controller (Admin, Player, ...) puede
 * llamarlo sin necesidad de pasarse referencias.
 */
public final class LiveMatchesWidgetService {

    private static LiveMatchesWidget widgetViewFieldReference;
    private static LiveMatchesWidgetController widgetControllerHandlerFieldReference;

    private LiveMatchesWidgetService() {
    }

    /**
     * Crea (si no existe) y muestra el widget. Si ya estaba mostrado,
     * lo trae al frente.
     */
    public static synchronized void show(PlayerManager playerProfileManagerServiceParameterValue,
                                         boolean isAdminViewerParameterValue) {
        Runnable showRunnableLocalVariableValue = () -> {
            if (widgetViewFieldReference == null) {
                widgetViewFieldReference = new LiveMatchesWidget();
                widgetControllerHandlerFieldReference = new LiveMatchesWidgetController(
                        widgetViewFieldReference,
                        playerProfileManagerServiceParameterValue,
                        isAdminViewerParameterValue
                );
            }
            widgetViewFieldReference.setVisible(true);
            widgetViewFieldReference.toFront();
        };

        if (SwingUtilities.isEventDispatchThread()) {
            showRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(showRunnableLocalVariableValue);
        }
    }

    /**
     * Cierra el widget y vacía el marcador. Llamar al hacer logout.
     */
    public static synchronized void hide() {
        Runnable hideRunnableLocalVariableValue = () -> {
            if (widgetControllerHandlerFieldReference != null) {
                widgetControllerHandlerFieldReference.shutdown();
                widgetControllerHandlerFieldReference = null;
            }
            widgetViewFieldReference = null;
            // Al cerrar sesión, los marcadores en memoria ya no son
            // relevantes y deben limpiarse.
            LiveMatchesScoreboard.getInstance().clear();
        };

        if (SwingUtilities.isEventDispatchThread()) {
            hideRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(hideRunnableLocalVariableValue);
        }
    }
}
