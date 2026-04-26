package presentation.ControllerViews;

/**
 * Interfaz que define el comportamiento común de los controladores de menú
 * para diferentes tipos de usuarios (como administradores o jugadores).
 */
public interface MenuController {

    /**
     * Muestra el menú principal correspondiente al tipo de usuario.
     */
    void showMenu();

    /**
     * Maneja la acción de cerrar sesión del usuario actual.
     */
    void handleLogout();

    /**
     * Muestra el cuadro de diálogo de configuración del usuario.
     */
    void showConfigDialog();
}

