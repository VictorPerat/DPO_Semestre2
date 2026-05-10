package presentation.Views;

import bussines.managers.ConfigManager;
import presentation.ControllerViews.LiveMatchController;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Representa la vista del partido en directo.
 */
public class LiveMatchView extends JFrame {

    private static final Color DARK_BLUE = new Color(0, 30, 60);
    private static final Color BACKGROUND = new Color(225, 238, 250);
    private static final Color TEXT_WHITE = Color.WHITE;

    /**
     * Constante para el configuracion.
     */
    public static final String CONFIG = "CONFIG";

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
     * Crea una instancia de el directo partido.
     *
     * @param homeTeamReferenceParameterValue equipo que usa la operacion.
     * @param awayTeamReferenceParameterValue equipo que usa la operacion.
     * @param gameEntityIdentifierParameterValue partido identificador.
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
     * Actualiza el contenido.
     *
     * @param homeTeamReferenceParameterValue2 equipo que usa la operacion.
     * @param awayTeamReferenceParameterValue2 equipo que usa la operacion.
     */
    private void setupUI(String homeTeamReferenceParameterValue2, String awayTeamReferenceParameterValue2) {
        setLayout(new BorderLayout(10, 10));


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


        JPanel centerPanelLocalVariableValue = new JPanel(new GridLayout(2, 1));
        centerPanelLocalVariableValue.setBackground(BACKGROUND);

        scoreLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 40));
        timerLabelFieldReference.setFont(new Font("Arial", Font.PLAIN, 24));

        centerPanelLocalVariableValue.add(scoreLabelFieldReference);
        centerPanelLocalVariableValue.add(timerLabelFieldReference);


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
     * Gestiona esta operacion.
     */
    private void startMatchSimulation() {
        final int[] goalsLocalVariableValue = new int[2];
        AtomicBoolean finishedLocalVariableValue = new AtomicBoolean(false);


        int matchTimeMinutesLocalVariableValue;
        try {
            matchTimeMinutesLocalVariableValue = Math.max(1, ConfigManager.getDurationMatch());
        } catch (RuntimeException ignoredExceptionParameter0) {
            matchTimeMinutesLocalVariableValue = 1;
        }


        final long sleepPerSimMinuteMsLocalVariableValue =
                (matchTimeMinutesLocalVariableValue * 60_000L) / 90L;

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
                    Thread.sleep(sleepPerSimMinuteMsLocalVariableValue);
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
                    Thread.sleep(100);
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
     * Gestiona esta operacion.
     *
     * @param minParameterValue2 dato de entrada de la operacion.
     * @param textParameterValue texto que usa la operacion.
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
            shared.DaoErrorHandler.log("LiveMatchView.appendEvent", eventArgumentExceptionParameter);
        }
    }


    /**
     * Actualiza el configuracion.
     *
     * @param controllerHandlerParameterValue dato de entrada de la operacion.
     */
    public void setConfigController(ActionListener controllerHandlerParameterValue) {
        this.configControllerHandlerFieldReference = controllerHandlerParameterValue;
    }


    /**
     * Gestiona esta operacion.
     */
    public void stopSimulation() {
        isRunningFieldReference.set(false);
    }


    /**
     * Devuelve el partido.
     *
     * @return el partido.
     */
    public int getGameId(){return gameEntityIdentifierFieldReference;}


    /**
     * Devuelve el equipo nombre.
     *
     * @return el equipo nombre.
     */
    public String getHomeTeamName() {return homeTeamReferenceDisplayNameFieldReference;}


    /**
     * Devuelve el equipo nombre.
     *
     * @return el equipo nombre.
     */
    public String getAwayTeamName() {return awayTeamReferenceDisplayNameFieldReference;}


    /**
     * Gestiona esta operacion.
     *
     * @param winnerParameterValue dato de entrada de la operacion.
     * @param gameEntityIdentifierParameterValue2 partido identificador.
     */
    private void finishMatchSimulation(String winnerParameterValue, int gameEntityIdentifierParameterValue2) {
        if (liveMatchViewInterfaceControllerHandlerFieldReference != null) {
            liveMatchViewInterfaceControllerHandlerFieldReference.finalitzarPartit(winnerParameterValue, gameEntityIdentifierParameterValue2);
        }
    }


    /**
     * Actualiza el directo partido vista.
     *
     * @param controllerHandlerParameterValue2 dato de entrada de la operacion.
     */
    public void setLiveMatchViewController(LiveMatchController controllerHandlerParameterValue2) {
        this.liveMatchViewInterfaceControllerHandlerFieldReference = controllerHandlerParameterValue2;
    }


    /**
     * Actualiza el contenido.
     *
     * @param minParameterValue3 dato de entrada de la operacion.
     * @param eventParameterValue2 dato de entrada de la operacion.
     * @param goalsParameterValue dato de entrada de la operacion.
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


