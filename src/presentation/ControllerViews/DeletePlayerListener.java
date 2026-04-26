/**
 * Interfaz para manejar eventos relacionados con la eliminación de jugadores.
 * Define métodos para responder a la eliminación de jugadores,
 * para regresar al menú principal y para gestionar el cierre de sesión.
 */
package presentation.ControllerViews;

public interface DeletePlayerListener {

    /**
     * Se llama cuando los jugadores han sido eliminados exitosamente.
     */
    void onPlayersDeleted();

    /**
     * Gestiona el proceso de cierre de sesión del usuario.
     */
    void handleLogout();

    /**
     * Indica que se debe retornar al menú principal.
     */
    void returnToMenu();


}
