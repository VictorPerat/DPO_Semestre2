package presentation.Views;

import presentation.ControllerViews.LiveMatchController;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Vista gráfica que representa un partido en directo.
 * Muestra el marcador, el tiempo y los eventos del partido en tiempo real simulados.
 */
public class LiveMatchView extends JFrame {

    private static final Color DARK_BLUE = new Color(0, 30, 60);
    private static final Color BACKGROUND = new Color(225, 238, 250);
    private static final Color TEXT_WHITE = Color.WHITE;
    private static final Color RED = new Color(200, 0, 0);

    public static final String CONFIG = "CONFIG";
    // Listener externo para configuracion y controlador del cierre del partido
    private ActionListener configControllerHandlerFieldReference;
    private LiveMatchController liveMatchViewInterfaceControllerHandlerFieldReference;

    private JLabel scoreLabelFieldReference = new JLabel("0 - 0", SwingConstants.CENTER);
    private JLabel timerLabelFieldReference = new JLabel("00:00", SwingConstants.CENTER);
    private JTextPane eventsPaneFieldReference = new JTextPane();

    private int homeGoalsFieldReference = 0;
    private int awayGoalsFieldReference = 0;
    private final AtomicBoolean isRunningFieldReference = new AtomicBoolean(true);
    private final Random randomFieldReference = new Random();
    private String homeTeamReferenceDisplayNameFieldReference;
    private String awayTeamReferenceDisplayNameFieldReference;
    private int gameEntityIdentifierFieldReference;

    private final String[] eventsFieldReference = {
            "GOAL!!!", "Foul", "Yellow card", "Red card",
            "Corner", "Throw-in", "Offside", "Shot on target",
            "Penalty", "Save", "Counter attack"
    };

    /**
     * Constructor de la vista del partido en directo.
     *
     * @param homeTeam Nombre del equipo local
     * @param awayTeam Nombre del equipo visitante
     * @param gameId   Identificador del partido
     */
    public LiveMatchView(String homeTeamReferenceParameterValue, String awayTeamReferenceParameterValue, int gameEntityIdentifierParameterValue) {
        this.homeTeamReferenceDisplayNameFieldReference = homeTeamReferenceParameterValue;
        this.awayTeamReferenceDisplayNameFieldReference = awayTeamReferenceParameterValue;
        this.gameEntityIdentifierFieldReference = gameEntityIdentifierParameterValue;
        setTitle(homeTeamReferenceParameterValue + " vs " + awayTeamReferenceParameterValue);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND);

        setupUI(homeTeamReferenceParameterValue, awayTeamReferenceParameterValue);
        startMatchSimulation();
    }

    /**
     * Configura todos los componentes gráficos de la interfaz.
     *
     * @param homeTeam Nombre del equipo local
     * @param awayTeam Nombre del equipo visitante
     */
    private void setupUI(String homeTeamReferenceParameterValue2, String awayTeamReferenceParameterValue2) {
        setLayout(new BorderLayout(10, 10));

        // Encabezado
        JPanel topPanelLocalVariableValue = new JPanel(new BorderLayout());
        topPanelLocalVariableValue.setBackground(DARK_BLUE);
        topPanelLocalVariableValue.setPreferredSize(new Dimension(getWidth(), 60));

        JButton backButtonLocalVariableValue = new JButton("←");
        backButtonLocalVariableValue.addActionListener(
                eventArgumentParameterValue -> setVisible(false)
        );
        backButtonLocalVariableValue.setForeground(TEXT_WHITE);
        backButtonLocalVariableValue.setBackground(DARK_BLUE);
        backButtonLocalVariableValue.setBorder(null);

        JLabel titleLocalVariableValue = new JLabel("Live Match: " + homeTeamReferenceParameterValue2 + " vs " + awayTeamReferenceParameterValue2, SwingConstants.CENTER);
        titleLocalVariableValue.setForeground(TEXT_WHITE);
        titleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 18));

        JButton configButtonLocalVariableValue = new JButton("⚙");
        configButtonLocalVariableValue.addActionListener(eventArgumentParameterValue2 -> {
            if (configControllerHandlerFieldReference != null) {
                configControllerHandlerFieldReference.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CONFIG));
            }
        });
        configButtonLocalVariableValue.setForeground(TEXT_WHITE);
        configButtonLocalVariableValue.setBackground(DARK_BLUE);
        configButtonLocalVariableValue.setBorder(null);

        topPanelLocalVariableValue.add(backButtonLocalVariableValue, BorderLayout.WEST);
        topPanelLocalVariableValue.add(titleLocalVariableValue, BorderLayout.CENTER);
        topPanelLocalVariableValue.add(configButtonLocalVariableValue, BorderLayout.EAST);

        // Panel de marcador y tiempo
        JPanel centerPanelLocalVariableValue = new JPanel(new GridLayout(2, 1));
        centerPanelLocalVariableValue.setBackground(BACKGROUND);

        scoreLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 40));
        timerLabelFieldReference.setFont(new Font("Arial", Font.PLAIN, 24));

        centerPanelLocalVariableValue.add(scoreLabelFieldReference);
        centerPanelLocalVariableValue.add(timerLabelFieldReference);

        // Panel de eventos
        eventsPaneFieldReference.setEditable(false);
        eventsPaneFieldReference.setBackground(Color.WHITE);
        eventsPaneFieldReference.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(eventsPaneFieldReference);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createTitledBorder("Live Events"));
        scrollPaneLocalVariableValue.setPreferredSize(new Dimension(getWidth(), 200));
        scrollPaneLocalVariableValue.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(topPanelLocalVariableValue, BorderLayout.NORTH);
        add(centerPanelLocalVariableValue, BorderLayout.CENTER);
        add(scrollPaneLocalVariableValue, BorderLayout.SOUTH);
    }

    /**
     * Inicia la simulación del partido con eventos aleatorios.
     */
    private void startMatchSimulation() {
        final int[] goalsLocalVariableValue = new int[2];
        AtomicBoolean finishedLocalVariableValue = new AtomicBoolean(false);
        new Thread(() -> {

            for (int minLocalVariableValue = 0; minLocalVariableValue <= 90 && isRunningFieldReference.get(); minLocalVariableValue++) {
                int finalMinLocalVariableValue = minLocalVariableValue;
                SwingUtilities.invokeLater(() ->
                        timerLabelFieldReference.setText(String.format("%02d:00", finalMinLocalVariableValue))
                );

                if (minLocalVariableValue < 90 && randomFieldReference.nextDouble() < 0.3) {
                    String eventLocalVariableValue = eventsFieldReference[randomFieldReference.nextInt(eventsFieldReference.length)];
                    updateGoals(minLocalVariableValue, eventLocalVariableValue, goalsLocalVariableValue);
                }

                try {
                    Thread.sleep(667);
                } catch (InterruptedException ignoredExceptionParameter) {}
            }

            SwingUtilities.invokeLater(() -> {
                appendEvent(90, "MATCH ENDED!");
                finishedLocalVariableValue.set(true);
                System.out.println(finishedLocalVariableValue);
            });

        }).start();
        new Thread(() -> {
            while (!finishedLocalVariableValue.get()) {
                try {
                    Thread.sleep(100); // Comprova cada 100ms
                } catch (InterruptedException ignoredExceptionParameter2) {}
            }

            if (goalsLocalVariableValue[0] > goalsLocalVariableValue[1]) {
                finishMatchSimulation(homeTeamReferenceDisplayNameFieldReference, gameEntityIdentifierFieldReference);
            } else if (goalsLocalVariableValue[1] > goalsLocalVariableValue[0]) {
                finishMatchSimulation(awayTeamReferenceDisplayNameFieldReference, gameEntityIdentifierFieldReference);
            } else {
                finishMatchSimulation("DRAW", gameEntityIdentifierFieldReference);
            }

        }).start();
    }

    /**
     * Maneja un evento de juego específico, actualizando el marcador si es un gol.
     *
     * @param min   Minuto en el que ocurre el evento
     * @param event Descripción del evento
     */
    private int[] handleEvent(int minParameterValue, String eventParameterValue) {
        int[] goalsLocalVariableValue2 = new int[2];
        if ("GOAL!!!".equals(eventParameterValue)) {
            if (randomFieldReference.nextBoolean()) homeGoalsFieldReference++; else awayGoalsFieldReference++;
            SwingUtilities.invokeLater(() ->
                    scoreLabelFieldReference.setText(homeGoalsFieldReference + " - " + awayGoalsFieldReference));
        }
        appendEvent(minParameterValue, eventParameterValue);
        goalsLocalVariableValue2 [0] = homeGoalsFieldReference;
        goalsLocalVariableValue2 [1] = awayGoalsFieldReference;
        return goalsLocalVariableValue2;
    }


    /**
     * Agrega un evento al panel de eventos con formato.
     *
     * @param min  Minuto del evento
     * @param text Texto del evento
     */
    private void appendEvent(int minParameterValue2, String textParameterValue) {
        try {
            StyledDocument docLocalVariableValue = eventsPaneFieldReference.getStyledDocument();
            Style styleLocalVariableValue = docLocalVariableValue.addStyle("Style", null);
            StyleConstants.setForeground(styleLocalVariableValue, DARK_BLUE);
            StyleConstants.setBold(styleLocalVariableValue, true);

            String timestampLocalVariableValue = String.format("[%02d'] ", minParameterValue2);
            docLocalVariableValue.insertString(docLocalVariableValue.getLength(), timestampLocalVariableValue + textParameterValue + "\n", styleLocalVariableValue);
        } catch (Exception eventArgumentExceptionParameter) {
            eventArgumentExceptionParameter.printStackTrace();
        }
    }

    /**
     * Establece el controlador para el botón de configuración.
     *
     * @param controller ActionListener que gestionará el evento de configuración.
     */
    public void setConfigController(ActionListener controllerHandlerParameterValue) {
        this.configControllerHandlerFieldReference = controllerHandlerParameterValue;
    }

    /**
     * Detiene el bucle de simulación de este partido. El thread que
     * recorre los minutos consultará la flag isRunning y saldrá de
     * forma controlada.
     *
     * Llamado por el controlador cuando se aborta el partido por
     * borrado de liga o equipo (apartados 2.10 y 2.11).
     */
    public void stopSimulation() {
        isRunningFieldReference.set(false);
    }

    /**
     * Devuelve el ID del partido en vivo.
     *
     * @return ID del partido
     */
    public int getGameId(){return gameEntityIdentifierFieldReference;}

    /**
     * Devuelve el nombre del equipo local.
     *
     * @return Nombre del equipo local
     */
    public String getHomeTeamName() {return homeTeamReferenceDisplayNameFieldReference;}

    /**
     * Devuelve el nombre del equipo visitante.
     *
     * @return Nombre del equipo visitante
     */
    public String getAwayTeamName() {return awayTeamReferenceDisplayNameFieldReference;}

    /**
     * Finaliza la simulación del partido notificando al controlador de la vista
     * del partido en directo sobre el ganador y el identificador del juego.
     *
     * @param winner Nombre o identificador del equipo ganador.
     * @param gameId Identificador único del partido.
     */
    private void finishMatchSimulation(String winnerParameterValue, int gameEntityIdentifierParameterValue2) {
        if (liveMatchViewInterfaceControllerHandlerFieldReference != null) {
            liveMatchViewInterfaceControllerHandlerFieldReference.finalitzarPartit(winnerParameterValue, gameEntityIdentifierParameterValue2);
        }
    }

    /**
     * Asigna el controlador que gestionará la vista del partido en directo.
     *
     * @param controller Instancia del controlador de la vista del partido en directo.
     */
    public void setLiveMatchViewController(LiveMatchController controllerHandlerParameterValue2) {
        this.liveMatchViewInterfaceControllerHandlerFieldReference = controllerHandlerParameterValue2;
    }

    /**
     * Actualiza el marcador cuando ocurre un evento de gol.
     * Incrementa los goles del equipo local o visitante aleatoriamente,
     * actualiza la etiqueta del marcador en la interfaz gráfica y añade el evento al registro.
     *
     * @param min    Minuto en que ocurre el evento (segundos a tiempo real).
     * @param event  Descripción del evento (ejemplo: "GOAL!!!").
     * @param goals  Array de dos enteros que almacena el conteo de goles:
     *               índice 0 para goles del equipo local,
     *               índice 1 para goles del equipo visitante.
     */
    private void updateGoals(int minParameterValue3, String eventParameterValue2, int[] goalsParameterValue) {
        if ("GOAL!!!".equals(eventParameterValue2)) {
            boolean homeLocalVariableValue = randomFieldReference.nextBoolean();
            if (homeLocalVariableValue) {
                homeGoalsFieldReference++;
                goalsParameterValue[0]++;
            } else {
                awayGoalsFieldReference++;
                goalsParameterValue[1]++;
            }

            SwingUtilities.invokeLater(() ->
                    scoreLabelFieldReference.setText(homeGoalsFieldReference + " - " + awayGoalsFieldReference)
            );

            // Publicamos el marcador actualizado al scoreboard global
            // para que el widget de partidos en directo (apartado 2.9)
            // muestre el resultado en tiempo real en cualquier pantalla.
            if (liveMatchViewInterfaceControllerHandlerFieldReference != null) {
                liveMatchViewInterfaceControllerHandlerFieldReference.reportScoreUpdate(
                        homeGoalsFieldReference,
                        awayGoalsFieldReference
                );
            }
        }

        appendEvent(minParameterValue3, eventParameterValue2);
    }


}
