package presentation.Views;

import bussines.objects.Player;
import bussines.managers.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Vista para eliminar jugadores del sistema.
 * Muestra una lista de jugadores disponibles con casillas de verificación,
 * un botón para eliminar los seleccionados, y controles para navegación y configuración.
 */
public class DeletePlayerView extends JFrame {

    private static final Color DARK_BLUE = new Color(22, 49, 72);
    private static final Color LIGHT_BLUE = new Color(195, 216, 236);
    private static final Color BACKGROUND = new Color(255, 255, 255);
    private static final Color DARK_RED = new Color(180, 40, 40);
    private static final Color BRIGHT_RED = new Color(220, 60, 60);

    public final String DELETE_PLAYERS_BUTTON = "DELETE_PLAYERS";

    private List<JCheckBox> playerProfileCheckboxesFieldReference = new ArrayList<>();
    private ArrayList<Player> loadedPlayersFieldReference = new ArrayList<>();
    private ActionListener controllerHandlerFieldReference;
    private PlayerManager playerProfileManagerServiceFieldReference = new PlayerManager();

    /**
     * Asigna el controlador que gestionará los eventos de esta vista.
     *
     * @param controller ActionListener para manejar eventos.
     */
    public void setController(ActionListener controllerHandlerParameterValue) {this.controllerHandlerFieldReference = controllerHandlerParameterValue;}

    /**
     * Constructor que inicializa la ventana con la lista de jugadores y controles.
     */
    public DeletePlayerView() {
        setTitle("Eliminar Jugadores");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanelLocalVariableValue = new JPanel(new BorderLayout());
        mainPanelLocalVariableValue.setBackground(BACKGROUND);
        mainPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanelLocalVariableValue.add(createTitlePanel(), BorderLayout.NORTH);
        mainPanelLocalVariableValue.add(createPlayersPanel(), BorderLayout.CENTER);
        mainPanelLocalVariableValue.add(createDeleteButtonPanel(), BorderLayout.SOUTH);


        add(mainPanelLocalVariableValue);
        setVisible(true);
    }

    /**
     * Crea el panel de la cabecera con botones para volver atrás,
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

        // Pelotas y título
        ImageIcon ballIconLocalVariableValue = new ImageIcon("S2-Project-E-LeagueManager-2/Project/photos/football.png");
        Image scaledLocalVariableValue = ballIconLocalVariableValue.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledBallIconLocalVariableValue = new ImageIcon(scaledLocalVariableValue);

        JLabel leftBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);
        JLabel rightBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);

        JLabel titleLocalVariableValue = new JLabel("DELETE PLAYER");
        titleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 18));
        titleLocalVariableValue.setForeground(Color.WHITE);

        JPanel centerPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanelLocalVariableValue.setOpaque(false);
        centerPanelLocalVariableValue.add(leftBallLocalVariableValue);
        centerPanelLocalVariableValue.add(titleLocalVariableValue);
        centerPanelLocalVariableValue.add(rightBallLocalVariableValue);

        panelLocalVariableValue.add(centerPanelLocalVariableValue, BorderLayout.CENTER);

        JButton configButtonLocalVariableValue = Rounded.HeaderButtonHelper.createConfigButton(null);
        panelLocalVariableValue.add(configButtonLocalVariableValue, BorderLayout.EAST);

        return panelLocalVariableValue;
    }

    /**
     * Crea el panel central con la lista de jugadores disponibles,
     * mostrando una casilla para seleccionar cada jugador.
     *
     * @return JScrollPane con la lista de jugadores.
     */
    private JScrollPane createPlayersPanel() {
        loadedPlayersFieldReference = playerProfileManagerServiceFieldReference.getPlayers();

        JPanel containerLocalVariableValue = new JPanel();
        containerLocalVariableValue.setLayout(new BoxLayout(containerLocalVariableValue, BoxLayout.Y_AXIS));
        containerLocalVariableValue.setBackground(BACKGROUND);
        containerLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        JPanel headerPanelLocalVariableValue = new JPanel(new BorderLayout());
        headerPanelLocalVariableValue.setBackground(DARK_BLUE);
        headerPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        headerPanelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel headerLabelLocalVariableValue = new JLabel("AVAILABLE PLAYERS", SwingConstants.CENTER);
        headerLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabelLocalVariableValue.setForeground(Color.WHITE);
        headerPanelLocalVariableValue.add(headerLabelLocalVariableValue, BorderLayout.CENTER);

        containerLocalVariableValue.add(headerPanelLocalVariableValue);
        containerLocalVariableValue.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel columnsPanelLocalVariableValue = new JPanel(new GridLayout(0, 3, 10, 10));
        columnsPanelLocalVariableValue.setBackground(BACKGROUND);

        playerProfileCheckboxesFieldReference.clear();
        for (Player playerProfileLocalVariableValue : loadedPlayersFieldReference) {
            JPanel playerProfilePanelLocalVariableValue = new JPanel(new BorderLayout());
            playerProfilePanelLocalVariableValue.setBackground(Color.WHITE);
            playerProfilePanelLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(LIGHT_BLUE, 1),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));

            JCheckBox checkBoxLocalVariableValue = new JCheckBox(playerProfileLocalVariableValue.getNamePlayer());
            checkBoxLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 12));
            checkBoxLocalVariableValue.setBackground(Color.WHITE);
            playerProfileCheckboxesFieldReference.add(checkBoxLocalVariableValue);

            playerProfilePanelLocalVariableValue.add(checkBoxLocalVariableValue, BorderLayout.CENTER);
            columnsPanelLocalVariableValue.add(playerProfilePanelLocalVariableValue);
        }

        containerLocalVariableValue.add(columnsPanelLocalVariableValue);

        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(containerLocalVariableValue);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneLocalVariableValue.getViewport().setBackground(BACKGROUND);
        scrollPaneLocalVariableValue.setPreferredSize(new Dimension(450, 200));

        return scrollPaneLocalVariableValue;
    }

    /**
     * Crea el panel inferior con el botón para eliminar jugadores seleccionados.
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
        deleteButtonLocalVariableValue.setForeground(Color.RED);
        deleteButtonLocalVariableValue.setBackground(BRIGHT_RED);
        deleteButtonLocalVariableValue.setFocusPainted(false);
        deleteButtonLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 30, 30), 2),
                BorderFactory.createEmptyBorder(8, 40, 8, 40)
        ));

        deleteButtonLocalVariableValue.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evtParameterValue) {
                deleteButtonLocalVariableValue.setBackground(new Color(200, 50, 50));
            }

            public void mouseExited(MouseEvent evtParameterValue2) {
                deleteButtonLocalVariableValue.setBackground(BRIGHT_RED);
            }
        });

        deleteButtonLocalVariableValue.addActionListener(eventArgumentParameterValue2 -> {
            if (controllerHandlerFieldReference != null) {
                ((ActionListener) controllerHandlerFieldReference).actionPerformed(
                        new ActionEvent(deleteButtonLocalVariableValue, ActionEvent.ACTION_PERFORMED, "DELETE_PLAYERS")

                );
            }
        });

        JPanel buttonContainerLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonContainerLocalVariableValue.setBackground(DARK_RED);
        buttonContainerLocalVariableValue.add(deleteButtonLocalVariableValue);

        redPanelLocalVariableValue.add(buttonContainerLocalVariableValue, BorderLayout.CENTER);
        outerPanelLocalVariableValue.add(redPanelLocalVariableValue, BorderLayout.CENTER);

        return outerPanelLocalVariableValue;
    }

    /**
     * Refresca la lista de jugadores, limpiando y reconstruyendo la interfaz.
     */
    public void refreshPlayersList() {
        getContentPane().removeAll();
        playerProfileCheckboxesFieldReference.clear();

        JPanel mainPanelLocalVariableValue2 = new JPanel(new BorderLayout());
        mainPanelLocalVariableValue2.setBackground(BACKGROUND);
        mainPanelLocalVariableValue2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanelLocalVariableValue2.add(createTitlePanel(), BorderLayout.NORTH);
        mainPanelLocalVariableValue2.add(createPlayersPanel(), BorderLayout.CENTER);
        mainPanelLocalVariableValue2.add(createDeleteButtonPanel(), BorderLayout.SOUTH);

        add(mainPanelLocalVariableValue2);
        revalidate();
        repaint();
    }

    /**
     * Obtiene la lista de jugadores seleccionados para eliminar.
     *
     * @return ArrayList con los jugadores seleccionados.
     */
    public ArrayList<Player> getSelectedPlayers() {
        ArrayList<Player> selectedLocalVariableValue = new ArrayList<>();
        for (int indexCounterLocalVariableValue = 0; indexCounterLocalVariableValue < playerProfileCheckboxesFieldReference.size(); indexCounterLocalVariableValue++) {
            if (playerProfileCheckboxesFieldReference.get(indexCounterLocalVariableValue).isSelected()) {
                selectedLocalVariableValue.add(loadedPlayersFieldReference.get(indexCounterLocalVariableValue));
            }
        }
        return selectedLocalVariableValue;
    }

    /**
     * Muestra un diálogo de confirmación para eliminar jugadores.
     *
     * @param numPlayers Número de jugadores seleccionados.
     * @return Entero con la opción elegida por el usuario (Sí/No).
     */
    public int confirmDeletePlayers(int numberPlayersParameterValue) {
        return JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete " + numberPlayersParameterValue + " player(s)?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
        );
    }

    /**
     * Muestra el resultado de la eliminación en un cuadro de diálogo informativo.
     *
     * @param message Mensaje a mostrar.
     */
    public void showDeletionResult(String messageParameterValue) {
        JOptionPane.showMessageDialog(this, messageParameterValue, "Players Deleted", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de advertencia con un texto personalizado.
     *
     * @param message Texto a mostrar en el diálogo.
     */
    public void showMessageDialog(String messageParameterValue2) {
        JOptionPane.showMessageDialog(this, messageParameterValue2, "DELETE", JOptionPane.WARNING_MESSAGE);
    }

}
