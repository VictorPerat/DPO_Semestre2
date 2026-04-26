package presentation.Views;

import bussines.objects.League;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Vista para eliminar ligas del sistema.
 * Muestra una lista de ligas disponibles con casillas de verificación,
 * un botón para eliminar las ligas seleccionadas,
 * y una cabecera con botones para volver atrás y configurar.
 */
public class DeleteLeagueView extends JFrame {

    private static final Color DARK_BLUE = new Color(22, 49, 72);
    private static final Color LIGHT_BLUE = new Color(195, 216, 236);
    private static final Color BACKGROUND = new Color(255, 255, 255); // Fondo blanco
    private static final Color DARK_RED = new Color(180, 40, 40);
    private static final Color BRIGHT_RED = new Color(220, 60, 60);

    private List<JCheckBox> leagueReferenceCheckboxesFieldReference = new ArrayList<>();
    private ActionListener configControllerHandlerFieldReference;
    private ArrayList<League> leaguesFieldReference;
    public final String DELETE_LEAGUES_BUTTON = "DELETE_LEAGUES";

    /**
     * Constructor que inicializa la ventana con la lista de ligas y controles.
     *
     * @param leagues Lista de ligas disponibles para mostrar.
     */
    public DeleteLeagueView(ArrayList<League> leaguesParameterValue) {
        this.leaguesFieldReference = leaguesParameterValue;
        setTitle("Eliminar Ligas");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanelLocalVariableValue = new JPanel(new BorderLayout());
        mainPanelLocalVariableValue.setBackground(BACKGROUND); // Fondo blanco principal
        mainPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Panel del título
        mainPanelLocalVariableValue.add(createTitlePanel(), BorderLayout.NORTH);

        // 2. Panel de ligas compacto
        mainPanelLocalVariableValue.add(createCompactLeaguesPanel(), BorderLayout.CENTER);

        // 3. Panel del botón DELETE con recuadro rojo sobre fondo blanco
        mainPanelLocalVariableValue.add(createDeleteButtonPanel(), BorderLayout.SOUTH);

        add(mainPanelLocalVariableValue);
    }

    /**
     * Asigna el controlador que manejará eventos de configuración.
     *
     * @param controller ActionListener para los eventos.
     */
    public void setConfigController(ActionListener controllerHandlerParameterValue) {
        this.configControllerHandlerFieldReference = controllerHandlerParameterValue;
    }

    /**
     * Crea el panel de cabecera con botones para volver atrás,
     * título con iconos y botón de configuración.
     *
     * @return JPanel con la cabecera configurada.
     */
    private JPanel createTitlePanel() {
        JPanel panelLocalVariableValue = new JPanel(new BorderLayout());
        panelLocalVariableValue.setBackground(DARK_BLUE);
        panelLocalVariableValue.setPreferredSize(new Dimension(getWidth(), 60));

        JButton backButtonLocalVariableValue = Rounded.HeaderButtonHelper.createBackButton(eventArgumentParameterValue -> dispose());
        panelLocalVariableValue.add(backButtonLocalVariableValue, BorderLayout.WEST);

        ImageIcon ballIconLocalVariableValue = new ImageIcon("S2-Project-E-LeagueManager-2/Project/photos/football.png"); // Ajusta el path si es necesario
        Image ballImageLocalVariableValue = ballIconLocalVariableValue.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledBallIconLocalVariableValue = new ImageIcon(ballImageLocalVariableValue);

        JLabel leftBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);
        JLabel rightBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);

        JPanel centerPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanelLocalVariableValue.setOpaque(false);

        JLabel titleLocalVariableValue = new JLabel("DELETE LEAGUE");
        titleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 20));
        titleLocalVariableValue.setForeground(Color.WHITE);

        centerPanelLocalVariableValue.add(leftBallLocalVariableValue);
        centerPanelLocalVariableValue.add(titleLocalVariableValue);
        centerPanelLocalVariableValue.add(rightBallLocalVariableValue);

        panelLocalVariableValue.add(centerPanelLocalVariableValue, BorderLayout.CENTER);

        // Botón de configuración (derecha) inhabilitado
        JButton configButtonLocalVariableValue = Rounded.HeaderButtonHelper.createConfigButton(eventArgumentParameterValue2 -> {
//            if (configController != null) {
//                ((DeleteLeagueController) configController).showConfigDialog();
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
     * Crea un panel compacto que muestra la lista de ligas
     * con casillas de verificación para seleccionar.
     *
     * @return JScrollPane con la lista de ligas.
     */
    private JScrollPane createCompactLeaguesPanel() {
        JPanel containerLocalVariableValue = new JPanel();
        containerLocalVariableValue.setLayout(new BoxLayout(containerLocalVariableValue, BoxLayout.Y_AXIS));
        containerLocalVariableValue.setBackground(BACKGROUND);
        containerLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Cabecera
        JPanel headerPanelLocalVariableValue = new JPanel(new BorderLayout());
        headerPanelLocalVariableValue.setBackground(DARK_BLUE);
        headerPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        headerPanelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel headerLabelLocalVariableValue = new JLabel("AVAILABLE LEAGUES", SwingConstants.CENTER);
        headerLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabelLocalVariableValue.setForeground(Color.WHITE);
        headerPanelLocalVariableValue.add(headerLabelLocalVariableValue, BorderLayout.CENTER);

        containerLocalVariableValue.add(headerPanelLocalVariableValue);
        containerLocalVariableValue.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel columnsPanelLocalVariableValue = new JPanel(new GridLayout(0, 3, 10, 10));
        columnsPanelLocalVariableValue.setBackground(BACKGROUND);

        for (League leagueReferenceLocalVariableValue : leaguesFieldReference) {
            JPanel leagueReferencePanelLocalVariableValue = new JPanel(new BorderLayout());
            leagueReferencePanelLocalVariableValue.setBackground(Color.WHITE);
            leagueReferencePanelLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(LIGHT_BLUE, 1),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));

            JCheckBox checkBoxLocalVariableValue = new JCheckBox(leagueReferenceLocalVariableValue.getName());
            checkBoxLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 12));
            checkBoxLocalVariableValue.setBackground(Color.WHITE);
            leagueReferenceCheckboxesFieldReference.add(checkBoxLocalVariableValue); // seguimos guardando para luego poder eliminarlas

            leagueReferencePanelLocalVariableValue.add(checkBoxLocalVariableValue, BorderLayout.CENTER);
            columnsPanelLocalVariableValue.add(leagueReferencePanelLocalVariableValue);
        }

        containerLocalVariableValue.add(columnsPanelLocalVariableValue);

        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(containerLocalVariableValue);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneLocalVariableValue.getViewport().setBackground(BACKGROUND);
        scrollPaneLocalVariableValue.setPreferredSize(new Dimension(450, 200));

        return scrollPaneLocalVariableValue;
    }

    /**
     * Crea el panel inferior con el botón para eliminar las ligas seleccionadas.
     *
     * @return JPanel con el botón eliminar.
     */
    private JPanel createDeleteButtonPanel() {
        // Panel exterior con fondo blanco
        JPanel outerPanelLocalVariableValue = new JPanel(new BorderLayout());
        outerPanelLocalVariableValue.setBackground(BACKGROUND);
        outerPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Panel interior con fondo rojo (el recuadro rojo)
        JPanel redPanelLocalVariableValue = new JPanel(new BorderLayout());
        redPanelLocalVariableValue.setBackground(DARK_RED);
        redPanelLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 1, 0, DARK_RED),
                BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        // Botón DELETE
        JButton deleteButtonLocalVariableValue = new JButton("DELETE");
        deleteButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 18));
        deleteButtonLocalVariableValue.setForeground(Color.WHITE);
        deleteButtonLocalVariableValue.setBackground(BRIGHT_RED);
        deleteButtonLocalVariableValue.setFocusPainted(false);
        deleteButtonLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 30, 30), 2),
                BorderFactory.createEmptyBorder(8, 40, 8, 40)
        ));

        // Efecto hover
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
                        new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "DELETE_LEAGUES")
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
     * Refresca la lista de ligas eliminando y reconstruyendo la interfaz.
     */
    public void refreshLeaguesList() {
        getContentPane().removeAll();
        leagueReferenceCheckboxesFieldReference.clear();

        JPanel mainPanelLocalVariableValue2 = new JPanel(new BorderLayout());
        mainPanelLocalVariableValue2.setBackground(BACKGROUND);
        mainPanelLocalVariableValue2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanelLocalVariableValue2.add(createTitlePanel(), BorderLayout.NORTH);
        mainPanelLocalVariableValue2.add(createCompactLeaguesPanel(), BorderLayout.CENTER);
        mainPanelLocalVariableValue2.add(createDeleteButtonPanel(), BorderLayout.SOUTH);

        add(mainPanelLocalVariableValue2);
        revalidate();
        repaint();
    }

    /**
     * Obtiene la lista de nombres de ligas seleccionadas para eliminar.
     *
     * @return ArrayList con los nombres de ligas seleccionadas.
     */
    public ArrayList<String> getSelectedLeagues() {
        ArrayList<String> selectedLocalVariableValue = new ArrayList<>();
        for (JCheckBox checkBoxLocalVariableValue2 : leagueReferenceCheckboxesFieldReference) {
            if (checkBoxLocalVariableValue2.isSelected()) {
                selectedLocalVariableValue.add(checkBoxLocalVariableValue2.getText());
            }
        }
        return selectedLocalVariableValue;
    }

    /**
     * Muestra un diálogo de advertencia con un mensaje personalizado.
     *
     * @param message Texto a mostrar.
     */
    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(this, messageParameterValue, "DELETE", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Muestra un diálogo de confirmación antes de eliminar ligas.
     *
     * @param leaguesToDelete Lista de ligas seleccionadas para eliminar.
     * @return Entero con la opción elegida por el usuario (Sí/No).
     */
    public int confirmDelete (List<String> leaguesToDeleteParameterValue) {
        int resultLocalVariableValue  = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete " + leaguesToDeleteParameterValue.size() + " league(s)?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        return resultLocalVariableValue;
    }

    /**
     * Muestra un mensaje con el resultado de la eliminación.
     *
     * @param message Mensaje a mostrar.
     */
    public void messageDelete (String messageParameterValue2) {
        JOptionPane.showMessageDialog(this, messageParameterValue2,
                "Deletion Result", JOptionPane.INFORMATION_MESSAGE);
    }
}