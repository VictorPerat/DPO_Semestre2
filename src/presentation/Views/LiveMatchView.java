package presentation.Views;

import bussines.managers.ConfigManager;
import presentation.ControllerViews.LiveMatchController;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Representa la vista del partido en directo.
 *
 * Refactorizada para integrarse en el CardLayout principal (ya NO abre
 * una ventana JFrame nueva). Sigue el mismo lenguaje visual que el
 * resto de vistas del proyecto:
 *  - Fondo con imagen y overlay oscuro (alpha 35).
 *  - Título grande "LIVE MATCH" con acento + subrayado + subtítulo.
 *  - Card blanca redondeada con marcador, cronómetro y eventos.
 *  - Botón BACK en la esquina superior izquierda.
 *
 * Es una JPanel que se registra como pantalla en AppNavigator/MainView.
 */
public class LiveMatchView extends JPanel {

    /**
     * Constante para el configuracion.
     */
    public static final String CONFIG = "CONFIG";

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);
    private static final Color CARD_TITLE_COLOR = new Color(28, 35, 51);
    private static final Color CARD_BODY_COLOR = new Color(100, 112, 135);
    private static final Color FIELD_BORDER = new Color(190, 210, 235);
    private static final Color SCORE_COLOR = new Color(22, 49, 72);
    private static final Color LIVE_RED = new Color(218, 37, 42);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    // Estado de la simulación
    private final JLabel scoreLabelFieldReference =
            new JLabel("0 - 0", SwingConstants.CENTER);
    private final JLabel timerLabelFieldReference =
            new JLabel("00:00", SwingConstants.CENTER);
    private final JLabel teamsLabelFieldReference =
            new JLabel("", SwingConstants.CENTER);
    private final JLabel liveBadgeFieldReference = new JLabel("● LIVE");
    private final JTextPane eventsPaneFieldReference = new JTextPane();
    private Rounded.RoundedButton backButtonFieldReference;
    private JButton configButtonFieldReference;

    private int homeGoalsFieldReference = 0;
    private int awayGoalsFieldReference = 0;
    private final AtomicBoolean isRunningFieldReference = new AtomicBoolean(true);
    private final Random randomFieldReference = new Random();

    private final String homeTeamReferenceDisplayNameFieldReference;
    private final String awayTeamReferenceDisplayNameFieldReference;
    private final int gameEntityIdentifierFieldReference;

    private ActionListener configControllerHandlerFieldReference;
    private ActionListener backControllerHandlerFieldReference;
    private LiveMatchController liveMatchViewInterfaceControllerHandlerFieldReference;

    private final String[] eventsFieldReference = {
            "GOAL!!!", "Foul", "Yellow card", "Red card",
            "Corner", "Throw-in", "Offside", "Shot on target",
            "Penalty", "Save", "Counter attack"
    };


    /**
     * Crea una instancia de el directo partido.
     *
     * @param homeTeamReferenceParameterValue equipo local.
     * @param awayTeamReferenceParameterValue equipo visitante.
     * @param gameEntityIdentifierParameterValue identificador del partido.
     */
    public LiveMatchView(String homeTeamReferenceParameterValue,
                         String awayTeamReferenceParameterValue,
                         int gameEntityIdentifierParameterValue) {
        this.homeTeamReferenceDisplayNameFieldReference = homeTeamReferenceParameterValue;
        this.awayTeamReferenceDisplayNameFieldReference = awayTeamReferenceParameterValue;
        this.gameEntityIdentifierFieldReference = gameEntityIdentifierParameterValue;

        // configButton "phantom" — no se pinta, mantiene la API del controller
        configButtonFieldReference = new JButton();
        configButtonFieldReference.setVisible(false);

        setLayout(new BorderLayout());
        add(buildBackgroundPanel(), BorderLayout.CENTER);

        startMatchSimulation();
    }


    // ────────────────────────────────────────────────────────────
    //  Construcción visual
    // ────────────────────────────────────────────────────────────

    private JPanel buildBackgroundPanel() {
        Image backgroundImageLocalVariableValue =
                new ImageIcon(BACKGROUND_IMAGE_PATH).getImage();

        JPanel backgroundPanelLocalVariableValue = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                super.paintComponent(graphicsParameterValue);
                graphicsParameterValue.drawImage(
                        backgroundImageLocalVariableValue,
                        0, 0, getWidth(), getHeight(), this
                );
                Graphics2D g2LocalVariableValue =
                        (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setColor(new Color(0, 0, 0, 35));
                g2LocalVariableValue.fillRect(0, 0, getWidth(), getHeight());
                g2LocalVariableValue.dispose();
            }
        };

        backgroundPanelLocalVariableValue.setLayout(new BorderLayout());
        backgroundPanelLocalVariableValue.add(buildTopBar(), BorderLayout.NORTH);
        backgroundPanelLocalVariableValue.add(buildCenterContent(), BorderLayout.CENTER);

        return backgroundPanelLocalVariableValue;
    }


    private JPanel buildTopBar() {
        JPanel topBarLocalVariableValue = new JPanel(new BorderLayout());
        topBarLocalVariableValue.setOpaque(false);
        topBarLocalVariableValue.setBorder(new EmptyBorder(28, 40, 0, 40));

        JPanel leftPanelLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanelLocalVariableValue.setOpaque(false);

        backButtonFieldReference = new Rounded.RoundedButton("< BACK", 18);
        backButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 15));
        backButtonFieldReference.setForeground(ACCENT_COLOR);
        backButtonFieldReference.setBackground(new Color(255, 255, 255, 230));
        backButtonFieldReference.setOutlineMode(ACCENT_COLOR, 2);
        backButtonFieldReference.setShadowEnabled(false);
        backButtonFieldReference.setPreferredSize(new Dimension(145, 44));
        backButtonFieldReference.addActionListener(eventArgumentParameterValue -> {
            if (backControllerHandlerFieldReference != null) {
                backControllerHandlerFieldReference.actionPerformed(eventArgumentParameterValue);
            }
        });

        leftPanelLocalVariableValue.add(backButtonFieldReference);
        topBarLocalVariableValue.add(leftPanelLocalVariableValue, BorderLayout.WEST);

        return topBarLocalVariableValue;
    }


    private JPanel buildCenterContent() {
        JPanel centerWrapperLocalVariableValue = new JPanel(new GridBagLayout());
        centerWrapperLocalVariableValue.setOpaque(false);

        JPanel contentPanelLocalVariableValue = new JPanel();
        contentPanelLocalVariableValue.setOpaque(false);
        contentPanelLocalVariableValue.setLayout(
                new BoxLayout(contentPanelLocalVariableValue, BoxLayout.Y_AXIS)
        );
        contentPanelLocalVariableValue.setBorder(new EmptyBorder(0, 50, 16, 50));

        JPanel titleBlockLocalVariableValue = buildTitleBlock();
        JPanel cardLocalVariableValue = buildMatchCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(24));
        contentPanelLocalVariableValue.add(cardLocalVariableValue);

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.weighty = 1.0;
        constraintsLocalVariableValue.anchor = GridBagConstraints.CENTER;

        centerWrapperLocalVariableValue.add(
                contentPanelLocalVariableValue, constraintsLocalVariableValue
        );

        return centerWrapperLocalVariableValue;
    }


    private JPanel buildTitleBlock() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeightLocalVariableValue = screenSizeLocalVariableValue.height;
        int mainTitleSizeLocalVariableValue =
                Math.max(58, (int) (screenHeightLocalVariableValue * 0.075));
        int subtitleSizeLocalVariableValue =
                Math.max(20, (int) (screenHeightLocalVariableValue * 0.025));

        JPanel titleContainerLocalVariableValue = new JPanel();
        titleContainerLocalVariableValue.setOpaque(false);
        titleContainerLocalVariableValue.setLayout(
                new BoxLayout(titleContainerLocalVariableValue, BoxLayout.Y_AXIS)
        );

        JPanel titleLineLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLineLocalVariableValue.setOpaque(false);

        JLabel liveLabelLocalVariableValue = new JLabel("LIVE ");
        liveLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        liveLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        JLabel matchLabelLocalVariableValue = new JLabel("MATCH");
        matchLabelLocalVariableValue.setForeground(TITLE_WHITE);
        matchLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        titleLineLocalVariableValue.add(liveLabelLocalVariableValue);
        titleLineLocalVariableValue.add(matchLabelLocalVariableValue);

        JPanel underlineWrapperLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        underlineWrapperLocalVariableValue.setOpaque(false);
        JPanel underlinePanelLocalVariableValue = new JPanel();
        underlinePanelLocalVariableValue.setBackground(ACCENT_COLOR);
        underlinePanelLocalVariableValue.setPreferredSize(new Dimension(170, 6));
        underlinePanelLocalVariableValue.setMinimumSize(new Dimension(170, 6));
        underlinePanelLocalVariableValue.setMaximumSize(new Dimension(170, 6));
        underlineWrapperLocalVariableValue.add(underlinePanelLocalVariableValue);

        JLabel subtitleLabelLocalVariableValue = new JLabel(
                homeTeamReferenceDisplayNameFieldReference
                        + "  vs  "
                        + awayTeamReferenceDisplayNameFieldReference,
                SwingConstants.CENTER
        );
        subtitleLabelLocalVariableValue.setForeground(SUBTITLE_WHITE);
        subtitleLabelLocalVariableValue.setFont(
                new Font("Arial", Font.PLAIN, subtitleSizeLocalVariableValue)
        );
        subtitleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabelLocalVariableValue.setBorder(new EmptyBorder(18, 0, 0, 0));

        titleContainerLocalVariableValue.add(titleLineLocalVariableValue);
        titleContainerLocalVariableValue.add(Box.createVerticalStrut(12));
        titleContainerLocalVariableValue.add(underlineWrapperLocalVariableValue);
        titleContainerLocalVariableValue.add(subtitleLabelLocalVariableValue);

        return titleContainerLocalVariableValue;
    }


    /**
     * Card central con: badge LIVE + nombres de equipos + marcador
     * gigante + cronómetro + log de eventos.
     */
    private JPanel buildMatchCard() {
        JPanel cardLocalVariableValue = new RoundedCardPanel();
        cardLocalVariableValue.setOpaque(false);
        cardLocalVariableValue.setLayout(
                new BoxLayout(cardLocalVariableValue, BoxLayout.Y_AXIS)
        );
        cardLocalVariableValue.setBorder(new EmptyBorder(22, 32, 26, 32));
        cardLocalVariableValue.setPreferredSize(new Dimension(960, 560));
        cardLocalVariableValue.setMinimumSize(new Dimension(960, 560));
        cardLocalVariableValue.setMaximumSize(new Dimension(960, 560));

        // Badge LIVE
        liveBadgeFieldReference.setForeground(LIVE_RED);
        liveBadgeFieldReference.setFont(new Font("Arial", Font.BOLD, 14));
        JPanel badgeWrapperLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        badgeWrapperLocalVariableValue.setOpaque(false);
        badgeWrapperLocalVariableValue.add(liveBadgeFieldReference);
        badgeWrapperLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        badgeWrapperLocalVariableValue.setMaximumSize(new Dimension(800, 24));

        // Teams label (under badge)
        teamsLabelFieldReference.setText(
                homeTeamReferenceDisplayNameFieldReference
                + "   ·   "
                + awayTeamReferenceDisplayNameFieldReference
        );
        teamsLabelFieldReference.setForeground(CARD_TITLE_COLOR);
        teamsLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 22));
        teamsLabelFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Score (huge)
        scoreLabelFieldReference.setForeground(SCORE_COLOR);
        scoreLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 96));
        scoreLabelFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Timer
        timerLabelFieldReference.setForeground(CARD_BODY_COLOR);
        timerLabelFieldReference.setFont(new Font("Arial", Font.PLAIN, 32));
        timerLabelFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Events title
        JLabel eventsTitleLocalVariableValue =
                new JLabel("Live events", SwingConstants.CENTER);
        eventsTitleLocalVariableValue.setForeground(CARD_BODY_COLOR);
        eventsTitleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 14));
        eventsTitleLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Events pane
        eventsPaneFieldReference.setEditable(false);
        eventsPaneFieldReference.setBackground(Color.WHITE);
        eventsPaneFieldReference.setFont(new Font("Consolas", Font.PLAIN, 13));
        eventsPaneFieldReference.setBorder(new EmptyBorder(8, 12, 8, 12));

        JScrollPane scrollPaneLocalVariableValue =
                new JScrollPane(eventsPaneFieldReference);
        scrollPaneLocalVariableValue.setBorder(
                BorderFactory.createLineBorder(FIELD_BORDER, 1)
        );
        scrollPaneLocalVariableValue.getViewport().setBackground(Color.WHITE);
        scrollPaneLocalVariableValue.setPreferredSize(new Dimension(800, 170));
        scrollPaneLocalVariableValue.setMaximumSize(new Dimension(800, 170));
        scrollPaneLocalVariableValue.setMinimumSize(new Dimension(800, 170));
        scrollPaneLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPaneLocalVariableValue.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        );
        scrollPaneLocalVariableValue.getVerticalScrollBar().setUnitIncrement(16);

        cardLocalVariableValue.add(badgeWrapperLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(6));
        cardLocalVariableValue.add(teamsLabelFieldReference);
        cardLocalVariableValue.add(Box.createVerticalStrut(14));
        cardLocalVariableValue.add(scoreLabelFieldReference);
        cardLocalVariableValue.add(Box.createVerticalStrut(4));
        cardLocalVariableValue.add(timerLabelFieldReference);
        cardLocalVariableValue.add(Box.createVerticalStrut(22));
        cardLocalVariableValue.add(eventsTitleLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(8));
        cardLocalVariableValue.add(scrollPaneLocalVariableValue);

        return cardLocalVariableValue;
    }


    // ────────────────────────────────────────────────────────────
    //  Lógica de simulación (idéntica a la versión anterior)
    // ────────────────────────────────────────────────────────────

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
            for (int minLocalVariableValue = 0;
                 minLocalVariableValue <= 90 && isRunningFieldReference.get();
                 minLocalVariableValue++) {
                int finalMinLocalVariableValue = minLocalVariableValue;
                SwingUtilities.invokeLater(() ->
                        timerLabelFieldReference.setText(
                                String.format("%02d:00", finalMinLocalVariableValue))
                );

                if (minLocalVariableValue < 90 && randomFieldReference.nextDouble() < 0.3) {
                    String eventLocalVariableValue =
                            eventsFieldReference[
                                    randomFieldReference.nextInt(eventsFieldReference.length)
                            ];
                    updateGoals(minLocalVariableValue,
                            eventLocalVariableValue, goalsLocalVariableValue);
                }

                try {
                    Thread.sleep(sleepPerSimMinuteMsLocalVariableValue);
                } catch (InterruptedException ignoredExceptionParameter) { }
            }

            SwingUtilities.invokeLater(() -> {
                appendEvent(90, "MATCH ENDED!");
                finishedLocalVariableValue.set(true);
            });
        }).start();

        new Thread(() -> {
            while (!finishedLocalVariableValue.get()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignoredExceptionParameter2) { }
            }
            if (goalsLocalVariableValue[0] > goalsLocalVariableValue[1]) {
                finishMatchSimulation(homeTeamReferenceDisplayNameFieldReference,
                        gameEntityIdentifierFieldReference);
            } else if (goalsLocalVariableValue[1] > goalsLocalVariableValue[0]) {
                finishMatchSimulation(awayTeamReferenceDisplayNameFieldReference,
                        gameEntityIdentifierFieldReference);
            } else {
                finishMatchSimulation("DRAW", gameEntityIdentifierFieldReference);
            }
        }).start();
    }


    private void appendEvent(int minParameterValue2, String textParameterValue) {
        try {
            StyledDocument docLocalVariableValue = eventsPaneFieldReference.getStyledDocument();
            Style styleLocalVariableValue = docLocalVariableValue.addStyle("Style", null);
            StyleConstants.setForeground(styleLocalVariableValue, CARD_TITLE_COLOR);
            StyleConstants.setBold(styleLocalVariableValue, true);

            String timestampLocalVariableValue =
                    String.format("[%02d'] ", minParameterValue2);
            docLocalVariableValue.insertString(
                    docLocalVariableValue.getLength(),
                    timestampLocalVariableValue + textParameterValue + "\n",
                    styleLocalVariableValue
            );
        } catch (Exception eventArgumentExceptionParameter) {
            shared.DaoErrorHandler.log("LiveMatchView.appendEvent",
                    eventArgumentExceptionParameter);
        }
    }


    private void updateGoals(int minParameterValue3,
                             String eventParameterValue2,
                             int[] goalsParameterValue) {
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
                    scoreLabelFieldReference.setText(
                            homeGoalsFieldReference + " - " + awayGoalsFieldReference)
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


    private void finishMatchSimulation(String winnerParameterValue,
                                       int gameEntityIdentifierParameterValue2) {
        if (liveMatchViewInterfaceControllerHandlerFieldReference != null) {
            liveMatchViewInterfaceControllerHandlerFieldReference.finalitzarPartit(
                    winnerParameterValue, gameEntityIdentifierParameterValue2
            );
        }
    }


    // ────────────────────────────────────────────────────────────
    //  API pública (conservada / extendida)
    // ────────────────────────────────────────────────────────────

    /**
     * Actualiza el configuracion.
     *
     * @param controllerHandlerParameterValue dato de entrada de la operacion.
     */
    public void setConfigController(ActionListener controllerHandlerParameterValue) {
        this.configControllerHandlerFieldReference = controllerHandlerParameterValue;
    }


    /**
     * Establece el listener del botón BACK. El controller debe pasar un
     * Runnable que navegue a la pantalla anterior (típicamente
     * LIVE_MATCHES o el menú principal).
     *
     * @param listenerParameterValue listener que se registra.
     */
    public void setBackButtonListener(ActionListener listenerParameterValue) {
        this.backControllerHandlerFieldReference = listenerParameterValue;
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
    public int getGameId() {
        return gameEntityIdentifierFieldReference;
    }


    /**
     * Devuelve el equipo nombre.
     *
     * @return el equipo nombre.
     */
    public String getHomeTeamName() {
        return homeTeamReferenceDisplayNameFieldReference;
    }


    /**
     * Devuelve el equipo nombre.
     *
     * @return el equipo nombre.
     */
    public String getAwayTeamName() {
        return awayTeamReferenceDisplayNameFieldReference;
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
     * Registra el controller del config button (phantom — se mantiene
     * por compatibilidad).
     *
     * @param controllerHandlerParameterValue listener.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        configButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        configButtonFieldReference.setActionCommand(CONFIG);
    }


    // ────────────────────────────────────────────────────────────
    //  Card redondeada con sombra y borde de acento
    // ────────────────────────────────────────────────────────────

    private static class RoundedCardPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics graphicsParameterValue) {
            Graphics2D g2LocalVariableValue =
                    (Graphics2D) graphicsParameterValue.create();
            g2LocalVariableValue.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int widthLocalVariableValue = getWidth();
            int heightLocalVariableValue = getHeight();
            int radiusLocalVariableValue = 22;

            // Sombra
            g2LocalVariableValue.setColor(new Color(8, 20, 46, 42));
            g2LocalVariableValue.fillRoundRect(
                    8, 10,
                    widthLocalVariableValue - 16,
                    heightLocalVariableValue - 10,
                    radiusLocalVariableValue, radiusLocalVariableValue
            );

            // Cuerpo blanco
            g2LocalVariableValue.setColor(new Color(255, 255, 255, 250));
            g2LocalVariableValue.fillRoundRect(
                    0, 0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 4,
                    radiusLocalVariableValue, radiusLocalVariableValue
            );

            // Borde de acento
            g2LocalVariableValue.setColor(ACCENT_COLOR);
            g2LocalVariableValue.setStroke(new BasicStroke(2f));
            g2LocalVariableValue.drawRoundRect(
                    0, 0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 5,
                    radiusLocalVariableValue, radiusLocalVariableValue
            );

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }
}
