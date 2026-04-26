package presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista Swing para la creación de una nueva liga.
 * Proporciona un formulario para introducir nombre de la liga, fecha y hora de inicio,
 * así como un botón para seleccionar los equipos disponibles.
 */
public class CreateLeagueView extends JFrame {
    private JTextField leagueReferenceDisplayNameFieldFieldReference;
    private JTextField dateFieldFieldReference;
    private JTextField startTimeFieldFieldReference;
    private JButton availableTeamsButtonFieldReference;
    private ActionListener configControllerHandlerFieldReference;

    /** Comando del botón "Available Teams" para ser usado por el controlador. */
    public static final String AVAILABLE_TEAMS_BUTTON = "AVAILABLE_TEAMS_BUTTON";

    /**
     * Constructor de la vista que configura y muestra el formulario de creación de liga.
     */
    public CreateLeagueView() {
        setTitle("CREATE NEW LEAGUE");
        setSize(800, 500);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanelLocalVariableValue = createHeaderPanel();
        JPanel formPanelLocalVariableValue = createFormPanel();

        add(headerPanelLocalVariableValue, BorderLayout.NORTH);
        add(formPanelLocalVariableValue, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Crea el panel del encabezado con título, botón de retroceso y botón de configuración.
     *
     * @return JPanel que contiene el encabezado.
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanelLocalVariableValue2 = new JPanel(new BorderLayout());
        headerPanelLocalVariableValue2.setBackground(new Color(0, 30, 60));
        headerPanelLocalVariableValue2.setPreferredSize(new Dimension(600, 60));

        JButton backButtonLocalVariableValue = Rounded.HeaderButtonHelper.createBackButton(eventArgumentParameterValue -> dispose());
        headerPanelLocalVariableValue2.add(backButtonLocalVariableValue, BorderLayout.WEST);

        JButton configButtonLocalVariableValue = Rounded.HeaderButtonHelper.createConfigButton(eventArgumentParameterValue2 -> { //inhabilitamos
//            if (configController != null) {
//                ((CreateLeagueController) configController).showConfigDialog();
//            } else {
//                JOptionPane.showMessageDialog(this,
//                        "Error: Controller not initialized",
//                        "Configuration Error",
//                        JOptionPane.ERROR_MESSAGE);
//            }
        });
        headerPanelLocalVariableValue2.add(configButtonLocalVariableValue, BorderLayout.EAST);

        // Añadir iconos de pelota de fútbol y el título
        ImageIcon ballIconLocalVariableValue = new ImageIcon("S2-Project-E-LeagueManager-2/Project/photos/football.png");
        Image scaledLocalVariableValue = ballIconLocalVariableValue.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledBallIconLocalVariableValue = new ImageIcon(scaledLocalVariableValue);

        JLabel leftBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);
        JLabel rightBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);

        JLabel headerLabelLocalVariableValue = new JLabel("CREATE NEW LEAGUE");
        headerLabelLocalVariableValue.setForeground(Color.WHITE);
        headerLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel centerPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanelLocalVariableValue.setOpaque(false);
        centerPanelLocalVariableValue.add(leftBallLocalVariableValue);
        centerPanelLocalVariableValue.add(headerLabelLocalVariableValue);
        centerPanelLocalVariableValue.add(rightBallLocalVariableValue);

        headerPanelLocalVariableValue2.add(centerPanelLocalVariableValue, BorderLayout.CENTER);

        return headerPanelLocalVariableValue2;
    }

    /**
     * Crea el panel del formulario con campos para nombre, fecha y hora de la liga,
     * y un botón para seleccionar equipos disponibles.
     *
     * @return JPanel con el formulario.
     */
    private JPanel createFormPanel() {
        JPanel formPanelLocalVariableValue2 = new JPanel();
        formPanelLocalVariableValue2.setLayout(new BoxLayout(formPanelLocalVariableValue2, BoxLayout.Y_AXIS));
        formPanelLocalVariableValue2.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        formPanelLocalVariableValue2.setOpaque(false);

        leagueReferenceDisplayNameFieldFieldReference = createFormField("League Name:", formPanelLocalVariableValue2);
        dateFieldFieldReference = createFormField("Date:", formPanelLocalVariableValue2);
        startTimeFieldFieldReference = createFormField("Start Time:", formPanelLocalVariableValue2);

        JPanel buttonPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanelLocalVariableValue.setOpaque(false);
        buttonPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));

        availableTeamsButtonFieldReference = new Rounded.RoundedButton("Available Teams", 15);
        availableTeamsButtonFieldReference.setBackground(new Color(0, 30, 60));
        availableTeamsButtonFieldReference.setForeground(Color.WHITE);
        availableTeamsButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 20));
        availableTeamsButtonFieldReference.setPreferredSize(new Dimension(200, 40));
        availableTeamsButtonFieldReference.setActionCommand(AVAILABLE_TEAMS_BUTTON);
        buttonPanelLocalVariableValue.add(availableTeamsButtonFieldReference);

        formPanelLocalVariableValue2.add(buttonPanelLocalVariableValue);

        return formPanelLocalVariableValue2;
    }

    /**
     * Crea un campo de texto personalizado con etiqueta y lo añade al panel padre.
     *
     * @param labelText    Texto de la etiqueta.
     * @param parentPanel  Panel donde se añadirá el campo.
     * @return JTextField personalizado creado.
     */
    private JTextField createFormField(String labelTextParameterValue, JPanel parentPanelParameterValue) {
        JPanel fieldPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldPanelLocalVariableValue.setOpaque(false);
        fieldPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel labelLocalVariableValue = new JLabel(labelTextParameterValue);
        labelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 14));
        fieldPanelLocalVariableValue.add(labelLocalVariableValue);

        JTextField textFieldLocalVariableValue = new Rounded.RoundedTextField(15, 20);
        textFieldLocalVariableValue.setBackground(new Color(0xC3D8EC));
        textFieldLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        fieldPanelLocalVariableValue.add(textFieldLocalVariableValue);

        parentPanelParameterValue.add(fieldPanelLocalVariableValue);
        parentPanelParameterValue.add(textFieldLocalVariableValue);

        return textFieldLocalVariableValue;
    }

    /**
     * Establece el controlador para el botón de configuración.
     *
     * @param controller Controlador que manejará los eventos del botón de configuración.
     */
    public void setConfigController(ActionListener controllerHandlerParameterValue) {
        this.configControllerHandlerFieldReference = controllerHandlerParameterValue;
    }

    /**
     * Obtiene el nombre de la liga introducido por el usuario.
     *
     * @return Nombre de la liga.
     */
    public String getLeagueName() {
        return leagueReferenceDisplayNameFieldFieldReference.getText();
    }

    /**
     * Obtiene la fecha introducida por el usuario.
     *
     * @return Fecha de la liga.
     */
    public String getDate() {
        return dateFieldFieldReference.getText();
    }

    /**
     * Obtiene la hora de inicio introducida por el usuario.
     *
     * @return Hora de inicio.
     */
    public String getStartTime() {
        return startTimeFieldFieldReference.getText();
    }

    /**
     * Registra un ActionListener para el botón "Available Teams".
     *
     * @param controller Controlador que manejará el evento.
     */
    public void registerController(ActionListener controllerHandlerParameterValue2) {
        availableTeamsButtonFieldReference.addActionListener(controllerHandlerParameterValue2);
    }

    /**
     * Muestra un mensaje de advertencia al usuario.
     *
     * @param message Mensaje que se mostrará.
     */
    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(this, messageParameterValue, "Create League", JOptionPane.WARNING_MESSAGE);
    }
}