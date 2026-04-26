package presentation.Views;

import presentation.ControllerViews.LiveMatchesController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Vista principal para mostrar los partidos en directo.
 * Proporciona una interfaz gráfica con actualizaciones automáticas y clics interactivos.
 */
public class LiveMatchesView extends JFrame {

    // Colores personalizados (para coherencia visual con el resto de la aplicación)
    private static final Color DARK_BLUE = new Color(22, 49, 72);    // #163148
    private static final Color LIGHT_BLUE = new Color(195, 216, 236); // #C3D8EC
    private static final Color BACKGROUND = new Color(240, 240, 240);
    private static final Color LIVE_RED = new Color(200, 0, 0);      // Rojo para indicar en directo
    private JButton refreshButtonFieldReference;
    private JPanel matchesPanelFieldReference;
    private JButton configButtonFieldReference;
    private ActionListener configControllerHandlerFieldReference;
    private ActionListener matchClickListenerFieldReference;
    private LiveMatchesController liveMatchesControllerHandlerFieldReference;
    private ActionListener backControllerHandlerFieldReference;
    public static final String CONFIG = "CONFIG";


    /**
     * Constructor que inicializa la ventana con una lista de partidos.
     *
     * @param matches Lista de partidos en formato [equipoLocal, equipoVisitante].
     */
    public LiveMatchesView(List<String[]> matchesParameterValue) {
        setTitle("Live Matches");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanelLocalVariableValue = new JPanel(new BorderLayout());
        mainPanelLocalVariableValue.setBackground(BACKGROUND);
        mainPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Panel del título
        mainPanelLocalVariableValue.add(createTitlePanel(), BorderLayout.NORTH);

        // 2. Panel de partidos en directo
        mainPanelLocalVariableValue.add(createMatchesPanel(matchesParameterValue), BorderLayout.CENTER);

        add(mainPanelLocalVariableValue);
        setVisible(true);
        initRefreshButton();
        updateMatches(matchesParameterValue);
    }

    public void setBackButtonListener(ActionListener listenerParameterValue) {
        this.backControllerHandlerFieldReference = listenerParameterValue;
    }


    /**
     *
     * Inicializa el botón de refrescar (no se añade a ningún panel directamente aquí).
     */
    private void initRefreshButton() {
        refreshButtonFieldReference = new JButton("Update matches");
    }

    /**
     * Devuelve el botón de refresco.
     *
     * @return Botón de actualización.
     */
    public JButton getRefreshButton() {
        return refreshButtonFieldReference;
    }

    /**
     * Actualiza los partidos mostrados en pantalla.
     *
     * @param matches Lista actualizada de partidos.
     */
    public void updateMatches(List<String[]> matchesParameterValue2) {
        matchesPanelFieldReference.removeAll();
        for (String[] teamsLocalVariableValue : matchesParameterValue2) {
            JPanel matchPanelLocalVariableValue = createMatchPanel(teamsLocalVariableValue);
            matchesPanelFieldReference.add(matchPanelLocalVariableValue);
            matchesPanelFieldReference.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        matchesPanelFieldReference.revalidate();
        matchesPanelFieldReference.repaint();
    }


    /**
     * Crea el panel de título con botones de volver y configuración.
     *
     * @return JPanel del encabezado.
     */
    private JPanel createTitlePanel() {
        JPanel panelLocalVariableValue = new JPanel(new BorderLayout());
        panelLocalVariableValue.setBackground(DARK_BLUE);
        panelLocalVariableValue.setPreferredSize(new Dimension(getWidth(), 60));

        JButton backButtonLocalVariableValue = Rounded.HeaderButtonHelper.createBackButton(eventArgumentParameterValue -> {
            if (backControllerHandlerFieldReference != null) {
                backControllerHandlerFieldReference.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "BACK"));
            } else {
                dispose(); // comportamiento por defecto si no hay listener
            }
        });
        backButtonLocalVariableValue.setActionCommand("BACK"); //no fa falts

        panelLocalVariableValue.add(backButtonLocalVariableValue, BorderLayout.WEST);

        ImageIcon ballIconLocalVariableValue = new ImageIcon("S2-Project-E-LeagueManager-2/Project/photos/football.png"); // Asegúrate de que exista esta ruta
        Image scaledLocalVariableValue = ballIconLocalVariableValue.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledBallIconLocalVariableValue = new ImageIcon(scaledLocalVariableValue);
        JLabel leftBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);
        JLabel rightBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);
        JLabel titleLocalVariableValue = new JLabel("LIVE MATCHES");
        titleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 18));
        titleLocalVariableValue.setForeground(Color.WHITE);
        JPanel centerPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanelLocalVariableValue.setOpaque(false);
        centerPanelLocalVariableValue.add(leftBallLocalVariableValue);
        centerPanelLocalVariableValue.add(titleLocalVariableValue);
        centerPanelLocalVariableValue.add(rightBallLocalVariableValue);

        panelLocalVariableValue.add(centerPanelLocalVariableValue, BorderLayout.CENTER);

        configButtonFieldReference = Rounded.HeaderButtonHelper.createConfigButton(eventArgumentParameterValue2 -> { //Botones que son funcionales pero no estan en uso
//            if (configController != null) {
//                configController.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CONFIG));
//            }
        });
        panelLocalVariableValue.add(configButtonFieldReference, BorderLayout.EAST);

        return panelLocalVariableValue;
    }

    /**
     * Crea el panel contenedor con scroll para los partidos.
     *
     * @param matches Lista de partidos.
     * @return JScrollPane con los partidos renderizados.
     */
    private JScrollPane createMatchesPanel(List<String[]> matchesParameterValue3) {
        JPanel containerLocalVariableValue = new JPanel(new BorderLayout());
        containerLocalVariableValue.setBackground(BACKGROUND);

        matchesPanelFieldReference = new JPanel();
        matchesPanelFieldReference.setLayout(new BoxLayout(matchesPanelFieldReference, BoxLayout.Y_AXIS));
        matchesPanelFieldReference.setBackground(BACKGROUND);
        matchesPanelFieldReference.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        for (String[] teamsLocalVariableValue2 : matchesParameterValue3) {
            JPanel matchPanelLocalVariableValue2 = new JPanel(new BorderLayout());
            matchPanelLocalVariableValue2.setBackground(Color.WHITE);
            matchPanelLocalVariableValue2.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(LIGHT_BLUE, 1),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            matchPanelLocalVariableValue2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

            JLabel liveLabelLocalVariableValue = new JLabel("LIVE");
            liveLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 12));
            liveLabelLocalVariableValue.setForeground(LIVE_RED);
            liveLabelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            matchPanelLocalVariableValue2.add(liveLabelLocalVariableValue, BorderLayout.WEST);

            JLabel teamsLabelLocalVariableValue = new JLabel(teamsLocalVariableValue2[0] + "  VS  " + teamsLocalVariableValue2[1], SwingConstants.CENTER);
            teamsLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 16));
            matchPanelLocalVariableValue2.add(teamsLabelLocalVariableValue, BorderLayout.CENTER);

            matchesPanelFieldReference.add(matchPanelLocalVariableValue2);
            matchesPanelFieldReference.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JPanel blueContainerLocalVariableValue = new JPanel(new BorderLayout());
        blueContainerLocalVariableValue.setBackground(LIGHT_BLUE);
        blueContainerLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        blueContainerLocalVariableValue.add(matchesPanelFieldReference, BorderLayout.CENTER);

        containerLocalVariableValue.add(blueContainerLocalVariableValue, BorderLayout.CENTER);

        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(containerLocalVariableValue);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneLocalVariableValue.getViewport().setBackground(BACKGROUND);

        return scrollPaneLocalVariableValue;
    }

    /**
     * Crea un panel individual para un partido con comportamiento de clic.
     *
     * @param teams Arreglo con equipo local y visitante.
     * @return JPanel representando el partido.
     */
    private JPanel createMatchPanel(String[] teamsParameterValue) {
        JPanel matchPanelLocalVariableValue3 = new JPanel(new BorderLayout());
        matchPanelLocalVariableValue3.setBackground(Color.WHITE);
        matchPanelLocalVariableValue3.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(195, 216, 236), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        matchPanelLocalVariableValue3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel liveLabelLocalVariableValue2 = new JLabel("LIVE");
        liveLabelLocalVariableValue2.setFont(new Font("Arial", Font.BOLD, 12));
        liveLabelLocalVariableValue2.setForeground(new Color(200, 0, 0));
        liveLabelLocalVariableValue2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        matchPanelLocalVariableValue3.add(liveLabelLocalVariableValue2, BorderLayout.WEST);

        JLabel teamsLabelLocalVariableValue2 = new JLabel(teamsParameterValue[0] + "  VS  " + teamsParameterValue[1], SwingConstants.CENTER);
        teamsLabelLocalVariableValue2.setFont(new Font("Arial", Font.BOLD, 16));
        matchPanelLocalVariableValue3.add(teamsLabelLocalVariableValue2, BorderLayout.CENTER);

        matchPanelLocalVariableValue3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        matchPanelLocalVariableValue3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent eventArgumentParameterValue3) {
                if (matchClickListenerFieldReference != null) {
                    matchClickListenerFieldReference.actionPerformed(new ActionEvent(teamsParameterValue, ActionEvent.ACTION_PERFORMED, "MATCH_CLICK"));
                }
            }
        });

        return matchPanelLocalVariableValue3;
    }

    /**
     * Inicia el refresco automático de partidos cada 3 segundos.
     *
     * @param matchesController Controlador para obtener partidos actualizados.
     */
    public void startAutoRefresh(LiveMatchesController matchesControllerHandlerParameterValue) {
        Timer timerLocalVariableValue = new Timer(3000, eventArgumentParameterValue4 -> {
            List<String[]> liveGamesLocalVariableValue = matchesControllerHandlerParameterValue.getterLiveGames();
            updateMatches(liveGamesLocalVariableValue);
        });
        timerLocalVariableValue.start();
    }

    /**
     * Establece el listener que maneja el clic sobre los partidos.
     *
     * @param listener ActionListener personalizado.
     */
    public void setMatchClickListener(ActionListener listenerParameterValue2) {
        this.matchClickListenerFieldReference = listenerParameterValue2;
    }

    /**
     * Establece el controlador del botón de configuración.
     *
     * @param controller ActionListener de configuración.
     */
    public void setConfigController(ActionListener controllerHandlerParameterValue) {
        this.configControllerHandlerFieldReference = controllerHandlerParameterValue;
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje.
     *
     * @param message Texto del mensaje a mostrar.
     */
    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(this, messageParameterValue, "VIEW_GAMES", JOptionPane.WARNING_MESSAGE);
    }
}
