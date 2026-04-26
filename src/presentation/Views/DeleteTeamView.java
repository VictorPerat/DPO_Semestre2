package presentation.Views;

import bussines.objects.Team;
import bussines.managers.TeamManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Vista para eliminar equipos de la aplicación.
 * Muestra una lista de equipos disponibles con casillas de verificación,
 * un botón para eliminar los equipos seleccionados,
 * y una cabecera con botones para volver atrás y configurar.
 */
public class DeleteTeamView extends JFrame {

    private static final Color DARK_BLUE = new Color(22, 49, 72);
    private static final Color LIGHT_BLUE = new Color(195, 216, 236);
    private static final Color BACKGROUND = new Color(255, 255, 255); // Fondo blanco
    private static final Color DARK_RED = new Color(180, 40, 40);
    private static final Color BRIGHT_RED = new Color(220, 60, 60);

    private List<JCheckBox> teamReferenceCheckboxesFieldReference = new ArrayList<>();
    private ActionListener configControllerHandlerFieldReference;
    public final String DELETE_TEAMS_BUTTON = "DELETE_TEAMS";
    private TeamManager teamReferenceManagerServiceFieldReference = new TeamManager();

    /**
     * Asigna el controlador que gestionará los eventos de configuración.
     *
     * @param controller ActionListener para la configuración.
     */
    public void setConfigController(ActionListener controllerHandlerParameterValue) {this.configControllerHandlerFieldReference = controllerHandlerParameterValue;}

    /**
     * Constructor que inicializa la ventana con la lista de equipos
     * y los controles para eliminar equipos.
     */
    public DeleteTeamView() {
        setTitle("Eliminar Equipos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanelLocalVariableValue = new JPanel(new BorderLayout());
        mainPanelLocalVariableValue.setBackground(BACKGROUND); // Fondo blanco principal
        mainPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanelLocalVariableValue.add(createTitlePanel(), BorderLayout.NORTH);

        mainPanelLocalVariableValue.add(createCompactTeamsPanel(), BorderLayout.CENTER);

        mainPanelLocalVariableValue.add(createDeleteButtonPanel(), BorderLayout.SOUTH);

        add(mainPanelLocalVariableValue);
    }

    /**
     * Crea el panel de la cabecera con botones de retroceso,
     * título y botón de configuración.
     *
     * @return JPanel con la cabecera configurada.
     */
    private JPanel createTitlePanel() {
        JPanel panelLocalVariableValue = new JPanel(new BorderLayout());
        panelLocalVariableValue.setBackground(DARK_BLUE);
        panelLocalVariableValue.setPreferredSize(new Dimension(getWidth(), 60));

        JButton backButtonLocalVariableValue = Rounded.HeaderButtonHelper.createBackButton(eventArgumentParameterValue -> dispose());
        panelLocalVariableValue.add(backButtonLocalVariableValue, BorderLayout.WEST);

        // Cargar la imagen de la pelota
        ImageIcon ballIconLocalVariableValue = new ImageIcon("S2-Project-E-LeagueManager-2/Project/photos/football.png");
        Image ballImageLocalVariableValue = ballIconLocalVariableValue.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledBallIconLocalVariableValue = new ImageIcon(ballImageLocalVariableValue);

        JLabel leftBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);
        JLabel rightBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);

        // Crear panel central con título y pelotas
        JPanel centerPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanelLocalVariableValue.setOpaque(false);

        JLabel titleLabelLocalVariableValue = new JLabel("DELETE TEAM");
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabelLocalVariableValue.setForeground(Color.WHITE);

        centerPanelLocalVariableValue.add(leftBallLocalVariableValue);
        centerPanelLocalVariableValue.add(titleLabelLocalVariableValue);
        centerPanelLocalVariableValue.add(rightBallLocalVariableValue);

        panelLocalVariableValue.add(centerPanelLocalVariableValue, BorderLayout.CENTER);

        JButton configButtonLocalVariableValue = Rounded.HeaderButtonHelper.createConfigButton(eventArgumentParameterValue2 -> { //boton comentado para inhabilitarlo
//            if (configController != null) {
//                ((DeleteTeamController) configController).showConfigDialog();
//            } else {
//                JOptionPane.showMessageDialog(this,
//                        "Error: Controller not initialized",
//                        "Configuration Error",
//                        JOptionPane.ERROR_MESSAGE);
//            }
        });
        panelLocalVariableValue.add(configButtonLocalVariableValue, BorderLayout.EAST);

        return panelLocalVariableValue;
    }

    /**
     * Crea el panel central que muestra la lista de equipos disponibles
     * con casillas de verificación para seleccionar.
     *
     * @return JScrollPane que contiene la lista de equipos.
     */
    private JScrollPane createCompactTeamsPanel() {
        JPanel containerLocalVariableValue = new JPanel();
        containerLocalVariableValue.setLayout(new BoxLayout(containerLocalVariableValue, BoxLayout.Y_AXIS));
        containerLocalVariableValue.setBackground(BACKGROUND);
        containerLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Cabecera
        JPanel headerPanelLocalVariableValue = new JPanel(new BorderLayout());
        headerPanelLocalVariableValue.setBackground(DARK_BLUE);
        headerPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        headerPanelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel headerLabelLocalVariableValue = new JLabel("AVAILABLE TEAMS", SwingConstants.CENTER);
        headerLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabelLocalVariableValue.setForeground(Color.WHITE);
        headerPanelLocalVariableValue.add(headerLabelLocalVariableValue, BorderLayout.CENTER);

        containerLocalVariableValue.add(headerPanelLocalVariableValue);
        containerLocalVariableValue.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel columnsPanelLocalVariableValue = new JPanel(new GridLayout(0, 3, 10, 10));
        columnsPanelLocalVariableValue.setBackground(BACKGROUND);


        ArrayList<Team> teamsLocalVariableValue = teamReferenceManagerServiceFieldReference.getAllTeams();

        for (Team teamReferenceLocalVariableValue : teamsLocalVariableValue) {
            JPanel teamReferencePanelLocalVariableValue = new JPanel(new BorderLayout());
            teamReferencePanelLocalVariableValue.setBackground(Color.WHITE);
            teamReferencePanelLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(LIGHT_BLUE, 1),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));

            JCheckBox checkBoxLocalVariableValue = new JCheckBox(teamReferenceLocalVariableValue.getName());
            checkBoxLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 12));
            checkBoxLocalVariableValue.setBackground(Color.WHITE);
            teamReferenceCheckboxesFieldReference.add(checkBoxLocalVariableValue); // Guardamos las checkboxes para eliminarlas luego

            teamReferencePanelLocalVariableValue.add(checkBoxLocalVariableValue, BorderLayout.CENTER);
            columnsPanelLocalVariableValue.add(teamReferencePanelLocalVariableValue);
        }

        containerLocalVariableValue.add(columnsPanelLocalVariableValue);

        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(containerLocalVariableValue);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneLocalVariableValue.getViewport().setBackground(BACKGROUND);
        scrollPaneLocalVariableValue.setPreferredSize(new Dimension(450, 200));

        return scrollPaneLocalVariableValue;
    }

    /**
     * Crea el panel inferior con el botón para eliminar los equipos seleccionados.
     *
     * @return JPanel con el botón de eliminación.
     */
    private JPanel createDeleteButtonPanel() {

        JPanel outerPanelLocalVariableValue = new JPanel(new BorderLayout());
        outerPanelLocalVariableValue.setBackground(BACKGROUND);
        outerPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel redPanelLocalVariableValue = new JPanel(new BorderLayout());
        redPanelLocalVariableValue.setBackground(DARK_RED);
        redPanelLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 1, 0, DARK_RED),
                BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        JButton deleteButtonLocalVariableValue = new JButton("DELETE");
        deleteButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 18));
        deleteButtonLocalVariableValue.setForeground(Color.WHITE);
        deleteButtonLocalVariableValue.setBackground(BRIGHT_RED);
        deleteButtonLocalVariableValue.setFocusPainted(false);
        deleteButtonLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 30, 30), 2),
                BorderFactory.createEmptyBorder(8, 40, 8, 40)
        ));

        deleteButtonLocalVariableValue.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evtParameterValue) {
                deleteButtonLocalVariableValue.setBackground(new Color(255, 255, 255));
            }
            public void mouseExited(MouseEvent evtParameterValue2) {
                deleteButtonLocalVariableValue.setBackground(BRIGHT_RED);
            }
        });

        deleteButtonLocalVariableValue.addActionListener(eventArgumentParameterValue3 -> {
            if (configControllerHandlerFieldReference != null) {
                ((ActionListener) configControllerHandlerFieldReference).actionPerformed(
                        new ActionEvent(deleteButtonLocalVariableValue, ActionEvent.ACTION_PERFORMED, "DELETE_TEAMS")

                );
            }
        });

        // Centrar el botón en el panel rojo
        JPanel buttonContainerLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonContainerLocalVariableValue.setBackground(DARK_RED);
        buttonContainerLocalVariableValue.add(deleteButtonLocalVariableValue);

        redPanelLocalVariableValue.add(buttonContainerLocalVariableValue, BorderLayout.CENTER);
        outerPanelLocalVariableValue.add(redPanelLocalVariableValue, BorderLayout.CENTER);

        return outerPanelLocalVariableValue;
    }

    /**
     * Obtiene la lista de nombres de equipos que están seleccionados para eliminar.
     *
     * @return ArrayList con los nombres de equipos seleccionados.
     */
    public ArrayList<String> getSelectedTeams() {
        ArrayList<String> selectedLocalVariableValue = new ArrayList<>();
        for (JCheckBox checkBoxLocalVariableValue2 : teamReferenceCheckboxesFieldReference) {
            if (checkBoxLocalVariableValue2.isSelected()) {
                selectedLocalVariableValue.add(checkBoxLocalVariableValue2.getText());
            }
        }
        return selectedLocalVariableValue;
    }

    /**
     * Muestra un diálogo de confirmación antes de eliminar los equipos.
     *
     * @param count Número de equipos seleccionados para eliminar.
     * @return Valor entero que representa la opción elegida por el usuario.
     */
    public int confirmDeleteTeams(int countParameterValue) {
        return JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete " + countParameterValue + " team(s)?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
        );
    }

    /**
     * Muestra un mensaje con el resultado de la eliminación de equipos.
     *
     * @param message Texto a mostrar en el diálogo informativo.
     */
    public void showDeletionResult(String messageParameterValue) {
        JOptionPane.showMessageDialog(this, messageParameterValue, "Deletion Result", JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Refresca la lista de equipos eliminando y reconstruyendo la interfaz.
     * Limpia las checkboxes y vuelve a cargar los equipos disponibles.
     */
    public void refreshTeamsList() {
        getContentPane().removeAll();
        teamReferenceCheckboxesFieldReference.clear();

        JPanel mainPanelLocalVariableValue2 = new JPanel(new BorderLayout());
        mainPanelLocalVariableValue2.setBackground(BACKGROUND);
        mainPanelLocalVariableValue2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanelLocalVariableValue2.add(createTitlePanel(), BorderLayout.NORTH);
        mainPanelLocalVariableValue2.add(createCompactTeamsPanel(), BorderLayout.CENTER);
        mainPanelLocalVariableValue2.add(createDeleteButtonPanel(), BorderLayout.SOUTH);

        add(mainPanelLocalVariableValue2);
        revalidate();
        repaint();
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de advertencia para eliminar.
     *
     * @param message Texto a mostrar en el diálogo.
     */
    public void showMessageDialog(String messageParameterValue2) {
        JOptionPane.showMessageDialog(this, messageParameterValue2, "DELETE", JOptionPane.WARNING_MESSAGE);
    }
}
