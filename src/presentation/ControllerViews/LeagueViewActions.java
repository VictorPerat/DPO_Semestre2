package presentation.ControllerViews;

/**
 * Interfaz que extiende MenuController y define las acciones específicas
 * relacionadas con la gestión de ligas en la vista de ligas.
 */
public interface LeagueViewActions extends MenuController {

    /**
     * Maneja la acción para visualizar las ligas existentes.
     */
    void handleViewLeagues();

    /**
     * Maneja la acción para crear una nueva liga.
     */
    void handleCreateLeague();

    /**
     * Maneja la acción para eliminar una liga existente.
     */
    void handleDeleteLeague();
}

