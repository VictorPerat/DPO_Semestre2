package presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import bussines.objects.Team;

/**
 * Vista gráfica para la selección de equipos en una liga.
 * Permite al usuario mover equipos entre una lista de disponibles y una lista de seleccionados,
 * y crear una liga a partir de los equipos añadidos.
 *
 * Extiende {@link JFrame} y utiliza componentes de Swing para la interfaz gráfica.
 */

public class TeamSelectionView extends JFrame {
    /**
     * Comando para identificar la acción de crear una liga.
     */
    public static final String CREATE_LEAGUE = "CREATE_LEAGUE";

    // Boton principal y paneles donde se mueven los equipos
    private JButton createLeagueReferenceButtonFieldReference;
    private JPanel availableTeamsPanelFieldReference;
    private JPanel addedTeamsPanelFieldReference;
    private JScrollPane availableScrollPaneFieldReference;
    private JScrollPane addedScrollPaneFieldReference;
    private JButton backButtonFieldReference;

    /**
     * Constructor que inicializa la vista con la lista de equipos disponibles.
     *
     * @param teams Lista de equipos disponibles para selección.
     */
    public TeamSelectionView(ArrayList<Team> teamsParameterValue) {
        setTitle("Available Teams");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        getContentPane().setBackground(new Color(240, 240, 240));
        setLayout(new BorderLayout());
        // Header con botón de retroceso y título
        JPanel headerPanelLocalVariableValue = new JPanel(new BorderLayout());
        headerPanelLocalVariableValue.setBackground(new Color(20, 40, 70));
        headerPanelLocalVariableValue.setPreferredSize(new Dimension(getWidth(), 50));

        backButtonFieldReference = new JButton("←");
        backButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 20));
        backButtonFieldReference.setForeground(Color.WHITE);
        backButtonFieldReference.setBackground(new Color(20, 40, 70));
        backButtonFieldReference.setBorderPainted(false);
        backButtonFieldReference.setFocusPainted(false);
        backButtonFieldReference.setContentAreaFilled(false);
        backButtonFieldReference.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButtonFieldReference.setActionCommand("BACK");

        JLabel titleLabelLocalVariableValue = new JLabel("AVAILABLE TEAMS", SwingConstants.CENTER);
        titleLabelLocalVariableValue.setForeground(Color.WHITE);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 20));

        headerPanelLocalVariableValue.add(backButtonFieldReference, BorderLayout.WEST);
        headerPanelLocalVariableValue.add(titleLabelLocalVariableValue, BorderLayout.CENTER);

        add(headerPanelLocalVariableValue, BorderLayout.NORTH);
        // Paneles principales de equipos disponibles y añadidos
        JPanel mainPanelLocalVariableValue = new JPanel();
        mainPanelLocalVariableValue.setLayout(new GridLayout(2, 1, 10, 10));
        mainPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        availableTeamsPanelFieldReference = new JPanel(new GridLayout(0, 1, 5, 5));

        addedTeamsPanelFieldReference = new JPanel(new GridLayout(0, 1, 5, 5));


        for (Team teamReferenceLocalVariableValue : teamsParameterValue) {
            JButton teamReferenceButtonLocalVariableValue = new Rounded.RoundedButton(teamReferenceLocalVariableValue.getName(), 15);
            styleButton(teamReferenceButtonLocalVariableValue);
            availableTeamsPanelFieldReference.add(teamReferenceButtonLocalVariableValue);
            // Acción al hacer clic: mover de disponibles a añadidos
            teamReferenceButtonLocalVariableValue.addActionListener(eventArgumentParameterValue -> {
                JButton buttonControlLocalVariableValue = (JButton) eventArgumentParameterValue.getSource();
                toggleTeam(buttonControlLocalVariableValue, availableTeamsPanelFieldReference, addedTeamsPanelFieldReference); //te permite hacer el add i el remove reusando codigo
            });

        }

        // Scroll para paneles de equipos
        availableScrollPaneFieldReference = new JScrollPane(availableTeamsPanelFieldReference);
        availableScrollPaneFieldReference.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        availableScrollPaneFieldReference.setBorder(BorderFactory.createTitledBorder("Available Teams"));

        addedScrollPaneFieldReference = new JScrollPane(addedTeamsPanelFieldReference);
        addedScrollPaneFieldReference.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        addedScrollPaneFieldReference.setBorder(BorderFactory.createTitledBorder("Teams Added"));

        mainPanelLocalVariableValue.add(availableScrollPaneFieldReference);
        mainPanelLocalVariableValue.add(addedScrollPaneFieldReference);
        // Botón de crear liga
        createLeagueReferenceButtonFieldReference = new Rounded.RoundedButton("CREATE LEAGUE", 15);
        styleButton(createLeagueReferenceButtonFieldReference);
        createLeagueReferenceButtonFieldReference.setPreferredSize(new Dimension(200, 40));
        createLeagueReferenceButtonFieldReference.setActionCommand(CREATE_LEAGUE);

        JPanel buttonPanelLocalVariableValue = new JPanel();
        buttonPanelLocalVariableValue.add(createLeagueReferenceButtonFieldReference);

        add(mainPanelLocalVariableValue, BorderLayout.CENTER);
        add(buttonPanelLocalVariableValue, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }
    /**
     * Devuelve el panel que contiene los equipos añadidos por el usuario.
     *
     * @return JPanel con los equipos seleccionados.
     */
    public JPanel getAddedTeamsPanel() {
        return addedTeamsPanelFieldReference;
    }
    /**
     * Registra un controlador para el botón de "Create League".
     *
     * @param controller Acción a ejecutar al hacer clic en el botón.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        createLeagueReferenceButtonFieldReference.addActionListener(controllerHandlerParameterValue);
    }
    /**
     * Aplica estilo visual personalizado a un botón.
     *
     * @param button Botón al que se le aplicará el estilo.
     */
    private void styleButton(JButton buttonParameterValue) {
        buttonParameterValue.setBackground(new Color(0, 30, 60));
        buttonParameterValue.setForeground(Color.WHITE);
        buttonParameterValue.setFont(new Font("Arial", Font.BOLD, 14));
        buttonParameterValue.setFocusPainted(false);
        buttonParameterValue.setOpaque(true);
        buttonParameterValue.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        buttonParameterValue.setContentAreaFilled(true);
        buttonParameterValue.setOpaque(true);
        buttonParameterValue.setBorderPainted(true);
    }
    /**
     * Mueve un botón entre paneles y cambia su acción para que pueda volver al panel original.
     *
     * @param btn  Botón que representa a un equipo.
     * @param from Panel del que se va a quitar el botón.
     * @param to   Panel al que se va a añadir el botón.
     */
    private void toggleTeam(JButton buttonControlParameterValue, JPanel fromParameterValue, JPanel toParameterValue) {
        fromParameterValue.remove(buttonControlParameterValue);
        toParameterValue.add(buttonControlParameterValue);
        styleButton(buttonControlParameterValue);
        fromParameterValue.revalidate();
        fromParameterValue.repaint();
        toParameterValue.revalidate();
        toParameterValue.repaint();
        // Se actualiza el listener para permitir que el botón vuelva al panel original
        for (ActionListener alLocalVariableValue : buttonControlParameterValue.getActionListeners()) {
            buttonControlParameterValue.removeActionListener(alLocalVariableValue);
        }

        buttonControlParameterValue.addActionListener(eventArgumentParameterValue2 -> toggleTeam(buttonControlParameterValue, toParameterValue, fromParameterValue));
    }

    /**
     * Registra un listener para el botón de retroceso.
     *
     * @param listener Acción a ejecutar al hacer clic en el botón de retroceso.
     */
    public void setBackButtonListener(ActionListener listenerParameterValue) {
        backButtonFieldReference.addActionListener(listenerParameterValue);
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de advertencia.
     *
     * @param message Texto que se mostrará en el cuadro de diálogo.
     */
    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(this, messageParameterValue, "Team selection", JOptionPane.WARNING_MESSAGE);
    }
}
