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
 * Widget global de partidos en directo.
 *
 * Se coloca debajo de la zona principal de los menús y se mantiene
 * visible para cumplir el apartado 2.9 del enunciado.
 */
public class LiveMatchesWidget extends JFrame {

    public static final String OPEN_LIVE_MATCHES = "OPEN_LIVE_MATCHES";

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color DARK_BLUE = new Color(22, 49, 72);
    private static final Color CARD_BACKGROUND = new Color(255, 255, 255, 245);
    private static final Color TEXT_COLOR = new Color(35, 45, 65);
    private static final Color SECONDARY_TEXT = new Color(105, 116, 135);
    private static final Color LIVE_RED = new Color(218, 37, 42);
    private static final Color BORDER_COLOR = new Color(210, 222, 240);

    private static final int WIDGET_HEIGHT = 112;
    private static final int HORIZONTAL_MARGIN = 48;
    private static final int MAX_WIDGET_WIDTH = 1120;
    private static final int MIN_WIDGET_WIDTH = 760;
    private static final int LEFT_BACKGROUND_MENU_OFFSET = 160;
    private static final double MENU_WIDGET_VERTICAL_RATIO = 0.72;

    private static final String LIVE_ICON_PRIMARY_PATH =
            ProjectPathResolver.resolveProjectPath("photos/Television.png");

    private static final String LIVE_ICON_FALLBACK_PATH =
            ProjectPathResolver.resolveProjectPath("photos/Partidos_Live.png");

    private final JPanel matchesContainerFieldReference;
    private final JLabel countLabelFieldReference = new JLabel("0 matches");

    private ActionListener openLiveMatchesListenerFieldReference;

    private Window lastAnchorWindowFieldReference;
    private int lastExtraVerticalOffsetFieldReference = 0;

    public LiveMatchesWidget() {
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusableWindowState(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));

        JPanel rootPanelLocalVariableValue = new RoundedWidgetPanel();
        rootPanelLocalVariableValue.setLayout(new BorderLayout(18, 0));
        rootPanelLocalVariableValue.setOpaque(false);
        rootPanelLocalVariableValue.setBorder(new EmptyBorder(16, 24, 16, 24));

        JPanel titlePanelLocalVariableValue = buildTitlePanel();

        matchesContainerFieldReference = new JPanel();
        matchesContainerFieldReference.setOpaque(false);
        matchesContainerFieldReference.setLayout(
                new BoxLayout(matchesContainerFieldReference, BoxLayout.X_AXIS)
        );

        JScrollPane scrollPaneLocalVariableValue =
                new JScrollPane(matchesContainerFieldReference);

        scrollPaneLocalVariableValue.setOpaque(false);
        scrollPaneLocalVariableValue.getViewport().setOpaque(false);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneLocalVariableValue.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scrollPaneLocalVariableValue.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER
        );

        rootPanelLocalVariableValue.add(titlePanelLocalVariableValue, BorderLayout.WEST);
        rootPanelLocalVariableValue.add(scrollPaneLocalVariableValue, BorderLayout.CENTER);

        enableOpenLiveMatchesOnClick(rootPanelLocalVariableValue);
        enableOpenLiveMatchesOnClick(titlePanelLocalVariableValue);

        setContentPane(rootPanelLocalVariableValue);
        setSize(900, WIDGET_HEIGHT);

        renderMatches(java.util.Collections.emptyList());
    }

    private JPanel buildTitlePanel() {
        JPanel titlePanelLocalVariableValue = new JPanel(new BorderLayout(12, 0));
        titlePanelLocalVariableValue.setOpaque(false);
        titlePanelLocalVariableValue.setPreferredSize(new Dimension(250, 86));

        JLabel iconLabelLocalVariableValue = new JLabel();
        iconLabelLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabelLocalVariableValue.setVerticalAlignment(SwingConstants.CENTER);
        iconLabelLocalVariableValue.setPreferredSize(new Dimension(72, 72));

        try {
            String iconPathLocalVariableValue =
                    new java.io.File(LIVE_ICON_PRIMARY_PATH).exists()
                            ? LIVE_ICON_PRIMARY_PATH
                            : LIVE_ICON_FALLBACK_PATH;

            Image iconImageLocalVariableValue =
                    new ImageIcon(iconPathLocalVariableValue).getImage();

            Image scaledIconLocalVariableValue =
                    iconImageLocalVariableValue.getScaledInstance(
                            58,
                            58,
                            Image.SCALE_SMOOTH
                    );

            iconLabelLocalVariableValue.setIcon(new ImageIcon(scaledIconLocalVariableValue));
        } catch (Exception ignoredExceptionParameterValue) {
            iconLabelLocalVariableValue.setText("LIVE");
            iconLabelLocalVariableValue.setForeground(ACCENT_COLOR);
            iconLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 14));
        }

        JPanel textPanelLocalVariableValue = new JPanel();
        textPanelLocalVariableValue.setOpaque(false);
        textPanelLocalVariableValue.setLayout(
                new BoxLayout(textPanelLocalVariableValue, BoxLayout.Y_AXIS)
        );

        JLabel titleLabelLocalVariableValue = new JLabel("LIVE MATCHES");
        titleLabelLocalVariableValue.setForeground(TEXT_COLOR);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 19));
        titleLabelLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        countLabelFieldReference.setForeground(SECONDARY_TEXT);
        countLabelFieldReference.setFont(new Font("Arial", Font.PLAIN, 13));
        countLabelFieldReference.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel accentBarLocalVariableValue = new JPanel();
        accentBarLocalVariableValue.setBackground(ACCENT_COLOR);
        accentBarLocalVariableValue.setPreferredSize(new Dimension(72, 4));
        accentBarLocalVariableValue.setMaximumSize(new Dimension(72, 4));
        accentBarLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanelLocalVariableValue.add(Box.createVerticalGlue());
        textPanelLocalVariableValue.add(titleLabelLocalVariableValue);
        textPanelLocalVariableValue.add(Box.createVerticalStrut(6));
        textPanelLocalVariableValue.add(accentBarLocalVariableValue);
        textPanelLocalVariableValue.add(Box.createVerticalStrut(7));
        textPanelLocalVariableValue.add(countLabelFieldReference);
        textPanelLocalVariableValue.add(Box.createVerticalGlue());

        titlePanelLocalVariableValue.add(iconLabelLocalVariableValue, BorderLayout.WEST);
        titlePanelLocalVariableValue.add(textPanelLocalVariableValue, BorderLayout.CENTER);

        return titlePanelLocalVariableValue;
    }

    /**
     * Compatibilidad con LiveMatchesWidgetService.
     */
    public void positionBottomAttachedTo(Window anchorWindowParameterValue) {
        positionBelowMenuAttachedTo(anchorWindowParameterValue, 0);
    }

    /**
     * Compatibilidad con LiveMatchesWidgetService.
     */
    public void positionBottomAttachedTo(Window anchorWindowParameterValue,
                                         int extraVerticalOffsetParameterValue) {
        positionBelowMenuAttachedTo(
                anchorWindowParameterValue,
                extraVerticalOffsetParameterValue
        );
    }

    public void positionBelowMenuAttachedTo(Window anchorWindowParameterValue) {
        positionBelowMenuAttachedTo(anchorWindowParameterValue, 0);
    }

    public void positionBelowMenuAttachedTo(Window anchorWindowParameterValue,
                                            int extraVerticalOffsetParameterValue) {
        this.lastAnchorWindowFieldReference = anchorWindowParameterValue;
        this.lastExtraVerticalOffsetFieldReference = extraVerticalOffsetParameterValue;

        Rectangle boundsLocalVariableValue;

        if (anchorWindowParameterValue == null) {
            boundsLocalVariableValue =
                    GraphicsEnvironment.getLocalGraphicsEnvironment()
                            .getMaximumWindowBounds();
        } else {
            boundsLocalVariableValue = anchorWindowParameterValue.getBounds();
        }

        int contentStartXLocalVariableValue =
                boundsLocalVariableValue.x + LEFT_BACKGROUND_MENU_OFFSET;

        int contentWidthLocalVariableValue =
                boundsLocalVariableValue.width - LEFT_BACKGROUND_MENU_OFFSET;

        int widgetWidthLocalVariableValue = Math.min(
                MAX_WIDGET_WIDTH,
                Math.max(
                        MIN_WIDGET_WIDTH,
                        contentWidthLocalVariableValue - (HORIZONTAL_MARGIN * 2)
                )
        );

        widgetWidthLocalVariableValue = Math.min(
                widgetWidthLocalVariableValue,
                contentWidthLocalVariableValue - 30
        );

        setSize(widgetWidthLocalVariableValue, WIDGET_HEIGHT);

        int xLocalVariableValue =
                contentStartXLocalVariableValue
                        + (contentWidthLocalVariableValue - widgetWidthLocalVariableValue) / 2;

        int yLocalVariableValue =
                boundsLocalVariableValue.y
                        + (int) (boundsLocalVariableValue.height * MENU_WIDGET_VERTICAL_RATIO)
                        + extraVerticalOffsetParameterValue;

        int maxYLocalVariableValue =
                boundsLocalVariableValue.y
                        + boundsLocalVariableValue.height
                        - WIDGET_HEIGHT
                        - 18;

        if (yLocalVariableValue > maxYLocalVariableValue) {
            yLocalVariableValue = maxYLocalVariableValue;
        }

        setLocation(xLocalVariableValue, yLocalVariableValue);
    }

    public void setOpenLiveMatchesListener(ActionListener listenerParameterValue) {
        this.openLiveMatchesListenerFieldReference = listenerParameterValue;
    }

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
                matchesContainerFieldReference.add(emptyStatePanelLocalVariableValue);
            } else {
                for (LiveMatchesScoreboard.ScoreSnapshot snapshotLocalVariableValue
                        : snapshotsParameterValue) {
                    JPanel matchCardPanelLocalVariableValue =
                            buildMatchCard(snapshotLocalVariableValue);
                    enableOpenLiveMatchesOnClick(matchCardPanelLocalVariableValue);
                    matchesContainerFieldReference.add(matchCardPanelLocalVariableValue);
                    matchesContainerFieldReference.add(Box.createHorizontalStrut(12));
                }
            }

            matchesContainerFieldReference.revalidate();
            matchesContainerFieldReference.repaint();

            if (lastAnchorWindowFieldReference != null) {
                positionBelowMenuAttachedTo(
                        lastAnchorWindowFieldReference,
                        lastExtraVerticalOffsetFieldReference
                );
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            updateRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(updateRunnableLocalVariableValue);
        }
    }

    private void fireOpenLiveMatches() {
        if (openLiveMatchesListenerFieldReference == null) {
            return;
        }

        openLiveMatchesListenerFieldReference.actionPerformed(
                new ActionEvent(this, ActionEvent.ACTION_PERFORMED, OPEN_LIVE_MATCHES)
        );
    }

    private void enableOpenLiveMatchesOnClick(Component componentParameterValue) {
        componentParameterValue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        componentParameterValue.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent eventParameterValue) {
                fireOpenLiveMatches();
            }
        });
    }

    private JPanel buildEmptyState() {
        JPanel emptyPanelLocalVariableValue = new JPanel(new GridBagLayout());
        emptyPanelLocalVariableValue.setOpaque(false);
        emptyPanelLocalVariableValue.setPreferredSize(new Dimension(520, 76));
        emptyPanelLocalVariableValue.setMaximumSize(new Dimension(520, 76));

        JLabel emptyLabelLocalVariableValue =
                new JLabel("No hay partidos en directo actualmente");
        emptyLabelLocalVariableValue.setForeground(SECONDARY_TEXT);
        emptyLabelLocalVariableValue.setFont(new Font("Arial", Font.ITALIC, 16));

        emptyPanelLocalVariableValue.add(emptyLabelLocalVariableValue);

        return emptyPanelLocalVariableValue;
    }

    private JPanel buildMatchCard(LiveMatchesScoreboard.ScoreSnapshot snapshotParameterValue) {
        JPanel cardLocalVariableValue = new JPanel(new BorderLayout(10, 0));
        cardLocalVariableValue.setBackground(Color.WHITE);
        cardLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(10, 14, 10, 14)
        ));

        cardLocalVariableValue.setPreferredSize(new Dimension(270, 76));
        cardLocalVariableValue.setMinimumSize(new Dimension(270, 76));
        cardLocalVariableValue.setMaximumSize(new Dimension(270, 76));

        JLabel liveLabelLocalVariableValue = new JLabel("LIVE");
        liveLabelLocalVariableValue.setForeground(LIVE_RED);
        liveLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel scoreLabelLocalVariableValue = new JLabel(
                snapshotParameterValue.getHomeGoals()
                        + " - "
                        + snapshotParameterValue.getAwayGoals(),
                SwingConstants.CENTER
        );
        scoreLabelLocalVariableValue.setForeground(DARK_BLUE);
        scoreLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 22));
        scoreLabelLocalVariableValue.setPreferredSize(new Dimension(70, 40));

        JLabel teamsLabelLocalVariableValue = new JLabel(
                "<html><div style='text-align:center;'>"
                        + snapshotParameterValue.getHomeName()
                        + "<br/>"
                        + snapshotParameterValue.getAwayName()
                        + "</div></html>",
                SwingConstants.CENTER
        );
        teamsLabelLocalVariableValue.setForeground(TEXT_COLOR);
        teamsLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 13));

        cardLocalVariableValue.add(liveLabelLocalVariableValue, BorderLayout.WEST);
        cardLocalVariableValue.add(scoreLabelLocalVariableValue, BorderLayout.CENTER);
        cardLocalVariableValue.add(teamsLabelLocalVariableValue, BorderLayout.EAST);

        return cardLocalVariableValue;
    }

    private static class RoundedWidgetPanel extends JPanel {
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
            int radiusLocalVariableValue = 24;

            g2LocalVariableValue.setColor(new Color(8, 20, 46, 70));
            g2LocalVariableValue.fillRoundRect(
                    8,
                    10,
                    widthLocalVariableValue - 16,
                    heightLocalVariableValue - 12,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.setColor(CARD_BACKGROUND);
            g2LocalVariableValue.fillRoundRect(
                    0,
                    0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 8,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.setColor(ACCENT_COLOR);
            g2LocalVariableValue.setStroke(new BasicStroke(2f));
            g2LocalVariableValue.drawRoundRect(
                    0,
                    0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 8,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }
}