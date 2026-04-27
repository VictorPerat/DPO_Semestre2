package presentation.ControllerViews;

// Interfaz con las acciones que necesita la pantalla de borrar jugadores
public interface DeletePlayerListener {

    // Se ejecuta cuando ya se han eliminado los jugadores seleccionados
    void onPlayersDeleted();

    // Cierra la sesion del usuario actual
    void handleLogout();

    // Devuelve al usuario al menu correspondiente
    void returnToMenu();
}
