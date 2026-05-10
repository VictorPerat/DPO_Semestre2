package presentation.Views;

import bussines.LiveMatchesScoreboard;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


/**
 * Representa el widget de los partidos en directo.
 */
public class LiveMatchesWidget extends JFrame {

    /**
     * Accion para abrir los directo partidos.
     */
    public static final String OPEN_LIVE_MATCHES = "OPEN_LIVE_MATCHES";


    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color DARK_BLUE = new Color(22, 49, 72);
    private static final Color CARD_BACKGROUND = new Color(255, 255, 255, 248);
    private static final Color TEXT_COLOR = new Color(35, 45, 65);
    private static final Color SECONDARY_TEXT = new Color(105, 116, 135);
    private static final Color LIVE_RED = new Color(218, 37, 42);
    private static final Color BORDER_COLOR = new Color(210, 222, 240);


    private static final int WIDGET_WIDTH = 290;
    private static final int RIGHT_MARGIN = 162;
    private static final int TOP_OFFSET_FROM_WINDOW = 95;
    private static final int BOTTOM_MARGIN_FROM_WINDOW = 40;
    private static final int MIN_WIDGET_HEIGHT = 320;

    private static final String LIVE_ICON_PRIMARY_PATH =
            ProjectPathResolver.resolveProjectPath("photos/Television.png");
    private static final String LIVE_ICON_FALLBACK_PATH =
            ProjectPathResolver.resolveProjectPath("photos/Partidos_Live.png");


    private final JPanel matchesContainerFieldReference;
    private final JLabel countLabelFieldReference = new JLabel("0 matches");
    private ActionListener openLiveMatchesListenerFieldReference;
    private Window lastAnchorWindowFieldReference;


    /**
     * Crea una instancia de los directo partidos.
     */
    public LiveMatchesWidget() {
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusableWindowState(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));

        JPanel rootPanelLocalVariableValue = new RoundedWidgetPanel();
        rootPanelLocalVariableValue.setLayout(new BorderLayout(0, 12));
        rootPanelLocalVariableValue.setOpaque(false);
        rootPanelLocalVariableValue.setBorder(new EmptyBorder(18, 18, 18, 18));


        JPanel titlePanelLocalVariableValue = buildTitlePanel();


        matchesContainerFieldReference = new JPanel();
        matchesContainerFieldReference.setOpaque(false);
        matchesContainerFieldReference.setLayout(
                new BoxLayout(matchesContainerFieldReference, BoxLayout.Y_AXIS));

        JScrollPane scrollPaneLocalVariableValue =
                new JScrollPane(matchesContainerFieldReference);
        scrollPaneLocalVariableValue.setOpaque(false);
        scrollPaneLocalVariableValue.getViewport().setOpaque(false);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneLocalVariableValue.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneLocalVariableValue.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneLocalVariableValue.getVerticalScrollBar().setUnitIncrement(16);

        rootPanelLocalVariableValue.add(titlePanelLocalVariableValue, BorderLayout.NORTH);
        rootPanelLocalVariableValue.add(scrollPaneLocalVariableValue, BorderLayout.CENTER);

        enableOpenLiveMatchesOnClick(rootPanelLocalVariableValue);
        enableOpenLiveMatchesOnClick(titlePanelLocalVariableValue);

        setContentPane(rootPanelLocalVariableValue);
        setSize(WIDGET_WIDTH, MIN_WIDGET_HEIGHT);

        renderMatches(java.util.Collections.emptyList());
    }


    /**
     * Construye el titulo.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildTitlePanel() {
        JPanel titlePanelLocalVariableValue = new JPanel();
        titlePanelLocalVariableValue.setOpaque(false);
        titlePanelLocalVariableValue.setLayout(
                new BoxLayout(titlePanelLocalVariableValue, BoxLayout.Y_AXIS));


        JPanel topRowLocalVariableValue = new JPanel(new BorderLayout(10, 0));
        topRowLocalVariableValue.setOpaque(false);

        JLabel iconLabelLocalVariableValue = new JLabel();
        iconLabelLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabelLocalVariableValue.setVerticalAlignment(SwingConstants.CENTER);
        iconLabelLocalVariableValue.setPreferredSize(new Dimension(50, 50));

        try {
            String iconPathLocalVariableValue =
                    new java.io.File(LIVE_ICON_PRIMARY_PATH).exists()
                            ? LIVE_ICON_PRIMARY_PATH
                            : LIVE_ICON_FALLBACK_PATH;
            Image iconImageLocalVariableValue =
                    new ImageIcon(iconPathLocalVariableValue).getImage();
            Image scaledIconLocalVariableValue =
                    iconImageLocalVariableValue.getScaledInstance(46, 46, Image.SCALE_SMOOTH);
            iconLabelLocalVariableValue.setIcon(new ImageIcon(scaledIconLocalVariableValue));
        } catch (Exception ignoredExceptionParameterValue) {
            iconLabelLocalVariableValue.setText("📺");
            iconLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 30));
        }

        JLabel titleLabelLocalVariableValue = new JLabel("LIVE MATCHES");
        titleLabelLocalVariableValue.setForeground(TEXT_COLOR);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 17));

        topRowLocalVariableValue.add(iconLabelLocalVariableValue, BorderLayout.WEST);
        topRowLocalVariableValue.add(titleLabelLocalVariableValue, BorderLayout.CENTER);


        JPanel accentBarLocalVariableValue = new JPanel();
        accentBarLocalVariableValue.setBackground(ACCENT_COLOR);
        accentBarLocalVariableValue.setMaximumSize(new Dimension(60, 4));
        accentBarLocalVariableValue.setPreferredSize(new Dimension(60, 4));
        accentBarLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);


        countLabelFieldReference.setForeground(SECONDARY_TEXT);
        countLabelFieldReference.setFont(new Font("Arial", Font.PLAIN, 12));
        countLabelFieldReference.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel accentBarWrapperLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        accentBarWrapperLocalVariableValue.setOpaque(false);
        accentBarWrapperLocalVariableValue.add(accentBarLocalVariableValue);
        accentBarWrapperLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        topRowLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        titlePanelLocalVariableValue.add(topRowLocalVariableValue);
        titlePanelLocalVariableValue.add(Box.createVerticalStrut(8));
        titlePanelLocalVariableValue.add(accentBarWrapperLocalVariableValue);
        titlePanelLocalVariableValue.add(Box.createVerticalStrut(6));
        titlePanelLocalVariableValue.add(countLabelFieldReference);

        return titlePanelLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param anchorWindowParameterValue ventana que se usa como referencia.
     */
    public void positionRightAttachedTo(Window anchorWindowParameterValue) {
        this.lastAnchorWindowFieldReference = anchorWindowParameterValue;

        Rectangle boundsLocalVariableValue;
        if (anchorWindowParameterValue == null) {
            boundsLocalVariableValue =
                    GraphicsEnvironment.getLocalGraphicsEnvironment()
                            .getMaximumWindowBounds();
        } else {
            boundsLocalVariableValue = anchorWindowParameterValue.getBounds();
        }

        int targetHeightLocalVariableValue = Math.max(
                MIN_WIDGET_HEIGHT,
                boundsLocalVariableValue.height
                        - TOP_OFFSET_FROM_WINDOW
                        - BOTTOM_MARGIN_FROM_WINDOW
        );

        setSize(WIDGET_WIDTH, targetHeightLocalVariableValue);

        int xLocalVariableValue =
                boundsLocalVariableValue.x
                        + boundsLocalVariableValue.width
                        - WIDGET_WIDTH
                        - RIGHT_MARGIN;
        int yLocalVariableValue =
                boundsLocalVariableValue.y + TOP_OFFSET_FROM_WINDOW;

        setLocation(xLocalVariableValue, yLocalVariableValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param anchorWindowParameterValue ventana que se usa como referencia.
     */
    public void positionBottomAttachedTo(Window anchorWindowParameterValue) {
        positionRightAttachedTo(anchorWindowParameterValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param anchorWindowParameterValue ventana que se usa como referencia.
     * @param extraVerticalOffsetParameterValue dato de entrada de la operacion.
     */
    public void positionBottomAttachedTo(Window anchorWindowParameterValue,
                                         int extraVerticalOffsetParameterValue) {
        positionRightAttachedTo(anchorWindowParameterValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param anchorWindowParameterValue ventana que se usa como referencia.
     */
    public void positionBelowMenuAttachedTo(Window anchorWindowParameterValue) {
        positionRightAttachedTo(anchorWindowParameterValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param anchorWindowParameterValue ventana que se usa como referencia.
     * @param extraVerticalOffsetParameterValue dato de entrada de la operacion.
     */
    public void positionBelowMenuAttachedTo(Window anchorWindowParameterValue,
                                            int extraVerticalOffsetParameterValue) {
        positionRightAttachedTo(anchorWindowParameterValue);
    }


    /**
     * Actualiza los abrir directo partidos.
     *
     * @param listenerParameterValue listener que se registra.
     */
    public void setOpenLiveMatchesListener(ActionListener listenerParameterValue) {
        this.openLiveMatchesListenerFieldReference = listenerParameterValue;
    }


    /**
     * Dibuja los partidos.
     *
     * @param snapshotsParameterValue dato de entrada de la operacion.
     */
    public void renderMatches(List<LiveMatchesScoreboard.ScoreSnapshot> snapshotsParameterValue) {
        Runnable updateRunnableLocalVariableValue = () -> {
            matchesContainerFieldReference.removeAll();

            int countLocalVariableValue =
                    snapshotsParameterValue == null ? 0 : snapshotsParameterValue.size();

            countLabelFieldReference.setText(
                    countLocalVariableValue == 1
                            ? "1 match in progress"
                            : countLocalVariableValue + " matches in progress"
            );

            if (snapshotsParameterValue == null || snapshotsParameterValue.isEmpty()) {
                JPanel emptyStatePanelLocalVariableValue = buildEmptyState();
                enableOpenLiveMatchesOnClick(emptyStatePanelLocalVariableValue);
                emptyStatePanelLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);
                matchesContainerFieldReference.add(emptyStatePanelLocalVariableValue);
            } else {
                for (int indexCounterLocalVariableValue = 0;
                     indexCounterLocalVariableValue < snapshotsParameterValue.size();
                     indexCounterLocalVariableValue++) {
                    LiveMatchesScoreboard.ScoreSnapshot snapshotLocalVariableValue =
                            snapshotsParameterValue.get(indexCounterLocalVariableValue);
                    JPanel matchCardPanelLocalVariableValue =
                            buildMatchCard(snapshotLocalVariableValue);
                    enableOpenLiveMatchesOnClick(matchCardPanelLocalVariableValue);
                    matchCardPanelLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);
                    matchesContainerFieldReference.add(matchCardPanelLocalVariableValue);

                    if (indexCounterLocalVariableValue < snapshotsParameterValue.size() - 1) {
                        matchesContainerFieldReference.add(Box.createVerticalStrut(10));
                    }
                }
            }

            matchesContainerFieldReference.add(Box.createVerticalGlue());
            matchesContainerFieldReference.revalidate();
            matchesContainerFieldReference.repaint();

            if (lastAnchorWindowFieldReference != null) {
                positionRightAttachedTo(lastAnchorWindowFieldReference);
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            updateRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(updateRunnableLocalVariableValue);
        }
    }


    /**
     * Gestiona esta operacion.
     */
    private void fireOpenLiveMatches() {
        if (openLiveMatchesListenerFieldReference == null) {
            return;
        }
        openLiveMatchesListenerFieldReference.actionPerformed(
                new ActionEvent(this, ActionEvent.ACTION_PERFORMED, OPEN_LIVE_MATCHES));
    }


    /**
     * Gestiona esta operacion.
     *
     * @param componentParameterValue dato de entrada de la operacion.
     */
    private void enableOpenLiveMatchesOnClick(Component componentParameterValue) {
        componentParameterValue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        componentParameterValue.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent eventParameterValue) {
                fireOpenLiveMatches();
            }
        });
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildEmptyState() {
        JPanel emptyPanelLocalVariableValue = new JPanel(new GridBagLayout());
        emptyPanelLocalVariableValue.setOpaque(false);
        emptyPanelLocalVariableValue.setPreferredSize(new Dimension(WIDGET_WIDTH - 36, 80));
        emptyPanelLocalVariableValue.setMaximumSize(new Dimension(WIDGET_WIDTH - 36, 80));

        JLabel emptyLabelLocalVariableValue = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "No live matches<br/>at the moment"
                        + "</div></html>",
                SwingConstants.CENTER);
        emptyLabelLocalVariableValue.setForeground(SECONDARY_TEXT);
        emptyLabelLocalVariableValue.setFont(new Font("Arial", Font.ITALIC, 13));

        emptyPanelLocalVariableValue.add(emptyLabelLocalVariableValue);
        return emptyPanelLocalVariableValue;
    }


    /**
     * Construye el partido.
     *
     * @param snapshotParameterValue dato de entrada de la operacion.
     * @return resultado de la operacion.
     */
    private JPanel buildMatchCard(LiveMatchesScoreboard.ScoreSnapshot snapshotParameterValue) {
        JPanel cardLocalVariableValue = new JPanel();
        cardLocalVariableValue.setLayout(new BoxLayout(cardLocalVariableValue, BoxLayout.Y_AXIS));
        cardLocalVariableValue.setBackground(Color.WHITE);
        cardLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(10, 12, 10, 12)));

        Dimension cardSizeLocalVariableValue = new Dimension(WIDGET_WIDTH - 40, 92);
        cardLocalVariableValue.setPreferredSize(cardSizeLocalVariableValue);
        cardLocalVariableValue.setMinimumSize(cardSizeLocalVariableValue);
        cardLocalVariableValue.setMaximumSize(cardSizeLocalVariableValue);


        JPanel liveBadgeRowLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        liveBadgeRowLocalVariableValue.setOpaque(false);
        liveBadgeRowLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel liveDotLocalVariableValue = new JLabel("●");
        liveDotLocalVariableValue.setForeground(LIVE_RED);
        liveDotLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel liveLabelLocalVariableValue = new JLabel("LIVE");
        liveLabelLocalVariableValue.setForeground(LIVE_RED);
        liveLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 11));

        liveBadgeRowLocalVariableValue.add(liveDotLocalVariableValue);
        liveBadgeRowLocalVariableValue.add(liveLabelLocalVariableValue);


        JPanel homeRowLocalVariableValue = buildTeamRow(
                snapshotParameterValue.getHomeName(),
                snapshotParameterValue.getHomeGoals());
        homeRowLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);


        JPanel awayRowLocalVariableValue = buildTeamRow(
                snapshotParameterValue.getAwayName(),
                snapshotParameterValue.getAwayGoals());
        awayRowLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        cardLocalVariableValue.add(liveBadgeRowLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(6));
        cardLocalVariableValue.add(homeRowLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(2));
        cardLocalVariableValue.add(awayRowLocalVariableValue);

        return cardLocalVariableValue;
    }


    /**
     * Construye el equipo.
     *
     * @param teamNameParameterValue nombre del equipo.
     * @param goalsParameterValue dato de entrada de la operacion.
     * @return resultado de la operacion.
     */
    private JPanel buildTeamRow(String teamNameParameterValue, int goalsParameterValue) {
        JPanel rowLocalVariableValue = new JPanel(new BorderLayout(8, 0));
        rowLocalVariableValue.setOpaque(false);

        String displayNameLocalVariableValue =
                teamNameParameterValue == null ? "?" : teamNameParameterValue;
        if (displayNameLocalVariableValue.length() > 22) {
            displayNameLocalVariableValue =
                    displayNameLocalVariableValue.substring(0, 21) + "…";
        }

        JLabel nameLabelLocalVariableValue = new JLabel(displayNameLocalVariableValue);
        nameLabelLocalVariableValue.setForeground(TEXT_COLOR);
        nameLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 13));

        JLabel goalsLabelLocalVariableValue =
                new JLabel(String.valueOf(goalsParameterValue), SwingConstants.RIGHT);
        goalsLabelLocalVariableValue.setForeground(DARK_BLUE);
        goalsLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 18));
        goalsLabelLocalVariableValue.setPreferredSize(new Dimension(28, 22));

        rowLocalVariableValue.add(nameLabelLocalVariableValue, BorderLayout.CENTER);
        rowLocalVariableValue.add(goalsLabelLocalVariableValue, BorderLayout.EAST);

        return rowLocalVariableValue;
    }


    /**
     * Agrupa la logica de el widget.
     */
    private static class RoundedWidgetPanel extends JPanel {


        /**
         * Gestiona esta operacion.
         *
         * @param graphicsParameterValue dato de entrada de la operacion.
         */
        @Override
        protected void paintComponent(Graphics graphicsParameterValue) {
            Graphics2D g2LocalVariableValue =
                    (Graphics2D) graphicsParameterValue.create();
            g2LocalVariableValue.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int widthLocalVariableValue = getWidth();
            int heightLocalVariableValue = getHeight();
            int radiusLocalVariableValue = 24;


            g2LocalVariableValue.setColor(new Color(8, 20, 46, 70));
            g2LocalVariableValue.fillRoundRect(8, 10,
                    widthLocalVariableValue - 16,
                    heightLocalVariableValue - 12,
                    radiusLocalVariableValue, radiusLocalVariableValue);


            g2LocalVariableValue.setColor(CARD_BACKGROUND);
            g2LocalVariableValue.fillRoundRect(0, 0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 8,
                    radiusLocalVariableValue, radiusLocalVariableValue);


            g2LocalVariableValue.setColor(ACCENT_COLOR);
            g2LocalVariableValue.setStroke(new BasicStroke(2f));
            g2LocalVariableValue.drawRoundRect(0, 0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 8,
                    radiusLocalVariableValue, radiusLocalVariableValue);

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }
}


