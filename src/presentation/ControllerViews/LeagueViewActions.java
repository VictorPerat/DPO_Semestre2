package presentation.ControllerViews;

// Interfaz con las acciones concretas de navegacion para ligas
public interface LeagueViewActions extends MenuController {

    // Abre la pantalla que lista las ligas disponibles
    void handleViewLeagues();

    // Abre el flujo para crear una nueva liga
    void handleCreateLeague();

    // Abre la pantalla para borrar una liga
    void handleDeleteLeague();
}
