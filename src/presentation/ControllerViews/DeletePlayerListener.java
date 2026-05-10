package presentation.ControllerViews;


/**
 * Define el contrato del jugador.
 */
public interface DeletePlayerListener {


    /**
     * Gestiona esta operacion.
     */
    void onPlayersDeleted();


    /**
     * Gestiona esta operacion.
     */
    void handleLogout();


    /**
     * Gestiona esta operacion.
     */
    void returnToMenu();
}


