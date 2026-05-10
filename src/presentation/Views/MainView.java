package presentation.Views;

import javax.swing.*;
import java.awt.*;


/**
 * Representa la vista del principal.
 */
public class MainView extends JFrame {

    private final CardLayout cardLayoutManagerFieldReference;
    private final JPanel cardsContainerPanelFieldReference;


    /**
     * Crea una instancia de el principal.
     */
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
     * Gestiona esta operacion.
     *
     * @param screenIdentifierParameterValue identificador de la pantalla.
     * @param screenPanelParameterValue pantalla que usa la operacion.
     */
    public void addScreen(String screenIdentifierParameterValue, JPanel screenPanelParameterValue) {
        cardsContainerPanelFieldReference.add(screenPanelParameterValue, screenIdentifierParameterValue);
    }


    /**
     * Muestra el pantalla.
     *
     * @param screenIdentifierParameterValue identificador de la pantalla.
     */
    public void showScreen(String screenIdentifierParameterValue) {
        cardLayoutManagerFieldReference.show(cardsContainerPanelFieldReference, screenIdentifierParameterValue);
        cardsContainerPanelFieldReference.revalidate();
        cardsContainerPanelFieldReference.repaint();
    }
}


