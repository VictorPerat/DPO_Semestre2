package presentation.Views;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal única de la aplicación.
 *
 * Sustituye al patrón anterior de "cada pantalla = un JFrame distinto".
 * Aquí mantenemos un solo JFrame que vive durante toda la sesión y un
 * CardLayout interno donde registramos las vistas (LoginView, SignUpView,
 * UserProfileView, ChangePasswordView, ...) como tarjetas.
 *
 * La navegación entre pantallas la realiza {@link presentation.AppNavigator},
 * que pide a este MainView que muestre la tarjeta correspondiente.
 */
public class MainView extends JFrame {

    private final CardLayout cardLayoutManagerFieldReference;
    private final JPanel cardsContainerPanelFieldReference;

    public MainView() {
        setTitle("Player App");
        setMinimumSize(new Dimension(1280, 800));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayoutManagerFieldReference = new CardLayout();
        cardsContainerPanelFieldReference = new JPanel(cardLayoutManagerFieldReference);
        setContentPane(cardsContainerPanelFieldReference);
    }

    /**
     * Registra una pantalla en el contenedor de tarjetas.
     *
     * @param screenIdentifierParameterValue Identificador único (ej. "LOGIN", "PROFILE", ...).
     * @param screenPanelParameterValue      Panel de la pantalla a añadir.
     */
    public void addScreen(String screenIdentifierParameterValue, JPanel screenPanelParameterValue) {
        cardsContainerPanelFieldReference.add(screenPanelParameterValue, screenIdentifierParameterValue);
    }

    /**
     * Muestra la pantalla con el identificador indicado.
     */
    public void showScreen(String screenIdentifierParameterValue) {
        cardLayoutManagerFieldReference.show(cardsContainerPanelFieldReference, screenIdentifierParameterValue);
        cardsContainerPanelFieldReference.revalidate();
        cardsContainerPanelFieldReference.repaint();
    }
}
