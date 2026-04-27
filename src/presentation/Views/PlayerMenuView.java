package presentation.Views;

import shared.ProjectPathResolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Vista gráfica para el menú del jugador.
 * Proporciona opciones como ver partidos, ver ligas disponibles, eliminar cuenta, cerrar sesión y configuración.
 */
public class PlayerMenuView extends JFrame {

    // Comandos de acción para los botones
    public static final String WATCH_MATCHES = "WATCH_MATCHES";
    public static final String VIEW_LEAGUES = "VIEW_LEAGUES";
    public static final String DELETE_PLAYER = "DELETE_PLAYER";
    public static final String LOGOUT = "LOGOUT";

    private JButton buttonControlWatchMatchesFieldReference;
    private JButton buttonControlViewInterfaceLeaguesFieldReference;
    private JButton buttonControlDeletePlayerProfileFieldReference;
    private JButton buttonControlLogoutFieldReference;
    private JButton configButtonFieldReference;
    private ActionListener configControllerHandlerFieldReference;
    public static final String CONFIG = "CONFIG";

    /**
     * Constructor de la clase PlayerMenuScreen.
     * Inicializa y configura todos los componentes gráficos del menú del jugador.
     */
    public PlayerMenuView() {
        setTitle("Menu Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        JPanel panelLocalVariableValue = new JPanel(new BorderLayout());

        // Panel superior (Header) con título e íconos de pelotas
        JPanel headerPanelLocalVariableValue = new JPanel(new BorderLayout());
        headerPanelLocalVariableValue.setBackground(new Color(0, 30, 60));
        headerPanelLocalVariableValue.setPreferredSize(new Dimension(600, 60));

        // Imagen de pelota
        ImageIcon ballIconLocalVariableValue =
                new ImageIcon(ProjectPathResolver.resolveProjectPath("photos/football.png"));
        Image ballImageLocalVariableValue = ballIconLocalVariableValue.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledBallIconLocalVariableValue = new ImageIcon(ballImageLocalVariableValue);
        JLabel leftBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);

        // Etiqueta de título
        JPanel centerPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanelLocalVariableValue.setOpaque(false);
        JLabel titleLabelLocalVariableValue = new JLabel("MENU PLAYER");
        titleLabelLocalVariableValue.setForeground(Color.WHITE);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel rightBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);
        centerPanelLocalVariableValue.add(leftBallLocalVariableValue);
        centerPanelLocalVariableValue.add(titleLabelLocalVariableValue);
        centerPanelLocalVariableValue.add(rightBallLocalVariableValue);
        headerPanelLocalVariableValue.add(centerPanelLocalVariableValue, BorderLayout.CENTER);

        // Botón de configuración (ícono de engranaje)
        configButtonFieldReference = Rounded.HeaderButtonHelper.createConfigButton(eventArgumentParameterValue -> {
            if (configControllerHandlerFieldReference != null) {
                configControllerHandlerFieldReference.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CONFIG));
            }
        });
        headerPanelLocalVariableValue.add(configButtonFieldReference, BorderLayout.EAST);

        // Panel de botones principales
        JPanel buttonPanelLocalVariableValue = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        buttonControlWatchMatchesFieldReference = new Rounded.RoundedButton("Watch live matches", 15);
        buttonControlViewInterfaceLeaguesFieldReference = new Rounded.RoundedButton("View available leagues", 15);
        buttonControlDeletePlayerProfileFieldReference = new Rounded.RoundedButton("Delete Account", 15);
        buttonControlLogoutFieldReference = new Rounded.RoundedButton("Logout", 15);

        buttonControlWatchMatchesFieldReference.setActionCommand(WATCH_MATCHES);
        buttonControlViewInterfaceLeaguesFieldReference.setActionCommand(VIEW_LEAGUES);
        buttonControlDeletePlayerProfileFieldReference.setActionCommand(DELETE_PLAYER);
        buttonControlLogoutFieldReference.setActionCommand(LOGOUT);

        styleButton(buttonControlWatchMatchesFieldReference);
        styleButton(buttonControlViewInterfaceLeaguesFieldReference);
        styleButton(buttonControlDeletePlayerProfileFieldReference);
        styleButton(buttonControlLogoutFieldReference);

        buttonPanelLocalVariableValue.add(buttonControlWatchMatchesFieldReference);
        buttonPanelLocalVariableValue.add(buttonControlViewInterfaceLeaguesFieldReference);
        buttonPanelLocalVariableValue.add(buttonControlDeletePlayerProfileFieldReference);
        buttonPanelLocalVariableValue.add(buttonControlLogoutFieldReference);

        panelLocalVariableValue.add(headerPanelLocalVariableValue, BorderLayout.NORTH);
        panelLocalVariableValue.add(buttonPanelLocalVariableValue, BorderLayout.CENTER);

        add(panelLocalVariableValue);
    }

    /**
     * Aplica estilo uniforme a los botones del menú.
     * @param button El botón al que se le aplica el estilo.
     */
    private void styleButton(JButton buttonParameterValue) {
        buttonParameterValue.setBackground(new Color(20, 40, 70));
        buttonParameterValue.setOpaque(true);
        buttonParameterValue.setForeground(Color.WHITE);
        buttonParameterValue.setFont(new Font("Arial", Font.BOLD, 14));
        buttonParameterValue.setFocusPainted(false);
        buttonParameterValue.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonParameterValue.setPreferredSize(new Dimension(buttonParameterValue.getPreferredSize().width, 30));
    }

    /**
     * Registra un controlador común para los botones del menú.
     * @param controller Controlador de eventos (ActionListener).
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        buttonControlWatchMatchesFieldReference.addActionListener(controllerHandlerParameterValue);
        buttonControlViewInterfaceLeaguesFieldReference.addActionListener(controllerHandlerParameterValue);
        buttonControlDeletePlayerProfileFieldReference.addActionListener(controllerHandlerParameterValue);
        buttonControlLogoutFieldReference.addActionListener(controllerHandlerParameterValue);
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje personalizado.
     * @param message El mensaje a mostrar.
     */
    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(this, messageParameterValue, "DELETE", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Establece un controlador para el botón de configuración.
     * @param controller El ActionListener a usar para la acción de configuración.
     */
    public void setConfigController(ActionListener controllerHandlerParameterValue2) {
        this.configControllerHandlerFieldReference = controllerHandlerParameterValue2;
    }
}
