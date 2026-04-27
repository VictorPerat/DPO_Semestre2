package presentation.ControllerViews;

// Contrato comun para los controladores que gobiernan un menu principal
public interface MenuController {

    // Muestra el menu principal del usuario actual
    void showMenu();

    // Gestiona el cierre de sesion
    void handleLogout();

    // Abre el dialogo de configuracion de la cuenta
    void showConfigDialog();
}
