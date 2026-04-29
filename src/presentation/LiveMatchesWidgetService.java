package presentation;

import bussines.LiveMatchesScoreboard;
import bussines.managers.PlayerManager;

/**
 * Servicio desactivado.
 *
 * Antes mostraba el widget flotante always-on-top de partidos en directo.
 * Ahora no crea ningún JFrame porque los live matches se muestran dentro
 * de AdminMenuView y PlayerMenuView.
 */
public final class LiveMatchesWidgetService {

    private LiveMatchesWidgetService() {
    }

    public static synchronized void show(PlayerManager playerProfileManagerServiceParameterValue,
                                         boolean isAdminViewerParameterValue) {
        // Widget flotante desactivado intencionadamente.
    }

    public static synchronized void hide() {
        // Mantenemos la limpieza al cerrar sesión.
        LiveMatchesScoreboard.getInstance().clear();
    }
}