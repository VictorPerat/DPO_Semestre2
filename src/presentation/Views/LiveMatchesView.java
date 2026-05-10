package presentation.Views;

import presentation.ControllerViews.LiveMatchesController;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Representa la vista de los partidos en directo.
 */
public class LiveMatchesView extends JPanel {

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);
    private static final Color CARD_BORDER = new Color(44, 92, 214);
    private static final Color LIVE_GREEN = new Color(34, 180, 94);
    private static final Color MATCH_CARD_TEAM = new Color(28, 35, 51);
    private static final Color MATCH_CARD_BODY = new Color(110, 122, 142);

    private JPanel matchesPanelFieldReference;
    private ActionListener matchClickListenerFieldReference;
    private ActionListener backControllerHandlerFieldReference;
    private Timer autoRefreshTimerFieldReference;


    /**
     * Constante para el configuracion.
     */
    public static final String CONFIG = "CONFIG";

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");


    /**
     * Crea una instancia de los directo partidos.
     */
    public LiveMatchesView() {
        this(new ArrayList<>());
    }


    /**
     * Crea una instancia de los directo partidos.
     *
     * @param matchesParameterValue partidos que usa la operacion.
     */
    public LiveMatchesView(List<String[]> matchesParameterValue) {
        setLayout(new BorderLayout());
        add(buildBackgroundPanel(), BorderLayout.CENTER);
        updateMatches(matchesParameterValue);
    }


    /**
     * Carga los directo partidos.
     *
     * @param matchesParameterValue partidos que usa la operacion.
     */
    public void loadLiveGames(List<String[]> matchesParameterValue) {
        updateMatches(matchesParameterValue);
    }


    /**
     * Actualiza el vuelta.
     *
     * @param listenerParameterValue listener que se registra.
     */
    public void setBackButtonListener(ActionListener listenerParameterValue) {
        this.backControllerHandlerFieldReference = listenerParameterValue;
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public JButton getRefreshButton() {
        return new JButton();
    }


    /**
     * Actualiza los partidos.
     *
     * @param matchesParameterValue partidos que usa la operacion.
     */
    public void updateMatches(List<String[]> matchesParameterValue) {
        if (matchesPanelFieldReference == null) {
            return;
        }

        matchesPanelFieldReference.removeAll();

        List<String[]> matchesLocalVariableValue =
                matchesParameterValue == null ? new ArrayList<>() : matchesParameterValue;

        if (matchesLocalVariableValue.isEmpty()) {
            matchesPanelFieldReference.add(Box.createVerticalStrut(45));

            JLabel emptyTitleLocalVariableValue =
                    new JLabel("No live matches right now", SwingConstants.CENTER);
            emptyTitleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 28));
            emptyTitleLocalVariableValue.setForeground(MATCH_CARD_TEAM);
            emptyTitleLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel emptyBodyLocalVariableValue = new JLabel(
                    "<html><div style='text-align:center;'>There are currently no matches in progress.<br>Come back in a moment.</div></html>",
                    SwingConstants.CENTER
            );
            emptyBodyLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 17));
            emptyBodyLocalVariableValue.setForeground(MATCH_CARD_BODY);
            emptyBodyLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

            matchesPanelFieldReference.add(emptyTitleLocalVariableValue);
            matchesPanelFieldReference.add(Box.createVerticalStrut(14));
            matchesPanelFieldReference.add(emptyBodyLocalVariableValue);
        }

        for (String[] teamsLocalVariableValue : matchesLocalVariableValue) {
            matchesPanelFieldReference.add(buildMatchCard(teamsLocalVariableValue));
            matchesPanelFieldReference.add(Box.createRigidArea(new Dimension(0, 16)));
        }

        matchesPanelFieldReference.revalidate();
        matchesPanelFieldReference.repaint();
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildBackgroundPanel() {
        Image backgroundImageLocalVariableValue = new ImageIcon(BACKGROUND_IMAGE_PATH).getImage();

        JPanel backgroundPanelLocalVariableValue = new JPanel() {

            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                super.paintComponent(graphicsParameterValue);
                graphicsParameterValue.drawImage(
                        backgroundImageLocalVariableValue,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        this
                );

                Graphics2D g2LocalVariableValue = (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setColor(new Color(0, 0, 0, 35));
                g2LocalVariableValue.fillRect(0, 0, getWidth(), getHeight());
                g2LocalVariableValue.dispose();
            }
        };

        backgroundPanelLocalVariableValue.setLayout(new BorderLayout());

        JPanel centerWrapperLocalVariableValue = new JPanel(new GridBagLayout());
        centerWrapperLocalVariableValue.setOpaque(false);

        JPanel contentPanelLocalVariableValue = new JPanel();
        contentPanelLocalVariableValue.setOpaque(false);
        contentPanelLocalVariableValue.setLayout(
                new BoxLayout(contentPanelLocalVariableValue, BoxLayout.Y_AXIS)
        );


        contentPanelLocalVariableValue.setBorder(new EmptyBorder(50, 40, 120, 40));

        JPanel topBarLocalVariableValue = buildTopBar();
        JPanel titleBlockLocalVariableValue = buildTitleBlock();
        JPanel contentCardLocalVariableValue = buildContentWrapper();

        topBarLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentCardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(topBarLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(12));
        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(28));
        contentPanelLocalVariableValue.add(contentCardLocalVariableValue);

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.weighty = 1.0;
        constraintsLocalVariableValue.anchor = GridBagConstraints.CENTER;

        constraintsLocalVariableValue.insets = new Insets(0, 0, 0, 310);

        centerWrapperLocalVariableValue.add(contentPanelLocalVariableValue, constraintsLocalVariableValue);
        backgroundPanelLocalVariableValue.add(centerWrapperLocalVariableValue, BorderLayout.CENTER);

        return backgroundPanelLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildTopBar() {
        JPanel topBarLocalVariableValue = new JPanel(new BorderLayout());
        topBarLocalVariableValue.setOpaque(false);
        topBarLocalVariableValue.setMaximumSize(new Dimension(1180, 60));
        topBarLocalVariableValue.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel rightPanelLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rightPanelLocalVariableValue.setOpaque(false);

        Rounded.RoundedButton backButtonLocalVariableValue =
                new Rounded.RoundedButton("← BACK", 18);
        backButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 15));
        backButtonLocalVariableValue.setForeground(ACCENT_COLOR);
        backButtonLocalVariableValue.setBackground(new Color(255, 255, 255, 230));
        backButtonLocalVariableValue.setOutlineMode(ACCENT_COLOR, 2);
        backButtonLocalVariableValue.setShadowEnabled(false);
        backButtonLocalVariableValue.setPreferredSize(new Dimension(145, 44));

        backButtonLocalVariableValue.addActionListener(eventArgumentParameterValue -> {
            if (backControllerHandlerFieldReference != null) {
                backControllerHandlerFieldReference.actionPerformed(
                        new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "BACK")
                );
            }
        });

        rightPanelLocalVariableValue.add(backButtonLocalVariableValue);
        topBarLocalVariableValue.add(rightPanelLocalVariableValue, BorderLayout.WEST);

        return topBarLocalVariableValue;
    }


    /**
     * Construye el titulo.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildTitleBlock() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeightLocalVariableValue = screenSizeLocalVariableValue.height;

        int mainTitleSizeLocalVariableValue = Math.max(56, (int) (screenHeightLocalVariableValue * 0.07));
        int subtitleSizeLocalVariableValue = Math.max(22, (int) (screenHeightLocalVariableValue * 0.027));

        JPanel titleContainerLocalVariableValue = new JPanel();
        titleContainerLocalVariableValue.setOpaque(false);
        titleContainerLocalVariableValue.setLayout(
                new BoxLayout(titleContainerLocalVariableValue, BoxLayout.Y_AXIS)
        );

        JPanel titleLineLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLineLocalVariableValue.setOpaque(false);

        JLabel liveTitleLocalVariableValue = new JLabel("LIVE");
        liveTitleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue));
        liveTitleLocalVariableValue.setForeground(ACCENT_COLOR);

        JLabel matchesTitleLocalVariableValue = new JLabel(" MATCHES");
        matchesTitleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue));
        matchesTitleLocalVariableValue.setForeground(TITLE_WHITE);

        titleLineLocalVariableValue.add(liveTitleLocalVariableValue);
        titleLineLocalVariableValue.add(matchesTitleLocalVariableValue);

        JPanel underlineWrapperLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        underlineWrapperLocalVariableValue.setOpaque(false);

        JPanel underlinePanelLocalVariableValue = new JPanel();
        underlinePanelLocalVariableValue.setBackground(ACCENT_COLOR);
        underlinePanelLocalVariableValue.setPreferredSize(new Dimension(190, 6));
        underlinePanelLocalVariableValue.setMinimumSize(new Dimension(190, 6));
        underlinePanelLocalVariableValue.setMaximumSize(new Dimension(190, 6));

        underlineWrapperLocalVariableValue.add(underlinePanelLocalVariableValue);

        JLabel subtitleLabelLocalVariableValue =
                new JLabel("Choose a match to open its live detail", SwingConstants.CENTER);
        subtitleLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, subtitleSizeLocalVariableValue));
        subtitleLabelLocalVariableValue.setForeground(SUBTITLE_WHITE);
        subtitleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabelLocalVariableValue.setBorder(new EmptyBorder(16, 0, 0, 0));

        titleContainerLocalVariableValue.add(titleLineLocalVariableValue);
        titleContainerLocalVariableValue.add(Box.createVerticalStrut(12));
        titleContainerLocalVariableValue.add(underlineWrapperLocalVariableValue);
        titleContainerLocalVariableValue.add(subtitleLabelLocalVariableValue);

        return titleContainerLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildContentWrapper() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidthLocalVariableValue = screenSizeLocalVariableValue.width;
        int cardWidthLocalVariableValue =
                Math.max(860, Math.min(1120, (int) (screenWidthLocalVariableValue * 0.72)));

        JPanel wrapperLocalVariableValue = new JPanel(new BorderLayout());
        wrapperLocalVariableValue.setOpaque(false);

        JPanel contentCardLocalVariableValue = new JPanel(new BorderLayout()) {

            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                Graphics2D g2LocalVariableValue = (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2LocalVariableValue.setColor(new Color(8, 20, 46, 55));
                g2LocalVariableValue.fillRoundRect(10, 14, getWidth() - 20, getHeight() - 18, 30, 30);

                g2LocalVariableValue.setColor(new Color(255, 255, 255, 240));
                g2LocalVariableValue.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 12, 28, 28);

                g2LocalVariableValue.setColor(CARD_BORDER);
                g2LocalVariableValue.setStroke(new BasicStroke(2f));
                g2LocalVariableValue.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 12, 28, 28);

                g2LocalVariableValue.dispose();
                super.paintComponent(graphicsParameterValue);
            }
        };

        contentCardLocalVariableValue.setOpaque(false);
        contentCardLocalVariableValue.setPreferredSize(new Dimension(cardWidthLocalVariableValue, 430));
        contentCardLocalVariableValue.setMaximumSize(new Dimension(cardWidthLocalVariableValue, 430));
        contentCardLocalVariableValue.setBorder(new EmptyBorder(22, 26, 22, 26));
        contentCardLocalVariableValue.add(buildLiveNowHeader(), BorderLayout.NORTH);

        matchesPanelFieldReference = new JPanel();
        matchesPanelFieldReference.setLayout(new BoxLayout(matchesPanelFieldReference, BoxLayout.Y_AXIS));
        matchesPanelFieldReference.setOpaque(false);

        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(matchesPanelFieldReference);
        scrollPaneLocalVariableValue.setOpaque(false);
        scrollPaneLocalVariableValue.getViewport().setOpaque(false);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneLocalVariableValue.getVerticalScrollBar().setUnitIncrement(16);

        contentCardLocalVariableValue.add(scrollPaneLocalVariableValue, BorderLayout.CENTER);
        wrapperLocalVariableValue.add(contentCardLocalVariableValue, BorderLayout.CENTER);

        return wrapperLocalVariableValue;
    }


    /**
     * Construye el directo.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildLiveNowHeader() {
        JPanel sectionHeaderLocalVariableValue = new JPanel();
        sectionHeaderLocalVariableValue.setOpaque(false);
        sectionHeaderLocalVariableValue.setLayout(new BoxLayout(sectionHeaderLocalVariableValue, BoxLayout.Y_AXIS));
        sectionHeaderLocalVariableValue.setBorder(new EmptyBorder(0, 0, 14, 0));

        JPanel rowLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rowLocalVariableValue.setOpaque(false);

        JLabel liveNowLabelLocalVariableValue = new JLabel("  ● LIVE NOW  ") {

            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                Graphics2D g2LocalVariableValue = (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2LocalVariableValue.setColor(LIVE_GREEN);
                g2LocalVariableValue.fillRoundRect(0, 2, getWidth(), getHeight() - 4, getHeight(), getHeight());
                g2LocalVariableValue.dispose();
                super.paintComponent(graphicsParameterValue);
            }
        };

        liveNowLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 12));
        liveNowLabelLocalVariableValue.setForeground(Color.WHITE);
        liveNowLabelLocalVariableValue.setOpaque(false);

        rowLocalVariableValue.add(liveNowLabelLocalVariableValue);

        JPanel dividerLineLocalVariableValue = new JPanel() {

            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                Graphics2D g2LocalVariableValue = (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setColor(new Color(214, 220, 230));
                g2LocalVariableValue.fillRect(0, getHeight() / 2, getWidth(), 1);
                g2LocalVariableValue.dispose();
            }


            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 2);
            }
        };

        dividerLineLocalVariableValue.setOpaque(false);
        dividerLineLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));

        sectionHeaderLocalVariableValue.add(rowLocalVariableValue);
        sectionHeaderLocalVariableValue.add(Box.createVerticalStrut(10));
        sectionHeaderLocalVariableValue.add(dividerLineLocalVariableValue);

        return sectionHeaderLocalVariableValue;
    }


    /**
     * Construye el partido.
     *
     * @param teamsParameterValue equipos que usa la operacion.
     * @return resultado de la operacion.
     */
    private JPanel buildMatchCard(String[] teamsParameterValue) {
        String homeLocalVariableValue = teamsParameterValue.length > 0 ? teamsParameterValue[0] : "Home";
        String awayLocalVariableValue = teamsParameterValue.length > 1 ? teamsParameterValue[1] : "Away";
        boolean[] hoverStateLocalVariableValue = {false};

        JPanel cardLocalVariableValue = new JPanel(new BorderLayout(20, 0)) {

            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                Graphics2D g2LocalVariableValue = (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2LocalVariableValue.setColor(new Color(8, 20, 46, 22));
                g2LocalVariableValue.fillRoundRect(4, 6, getWidth() - 8, getHeight() - 8, 18, 18);

                Color bgColorLocalVariableValue =
                        hoverStateLocalVariableValue[0]
                                ? new Color(243, 247, 255)
                                : Color.WHITE;

                g2LocalVariableValue.setColor(bgColorLocalVariableValue);
                g2LocalVariableValue.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 6, 16, 16);

                g2LocalVariableValue.setColor(new Color(218, 226, 242));
                g2LocalVariableValue.setStroke(new BasicStroke(1.5f));
                g2LocalVariableValue.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 6, 16, 16);

                g2LocalVariableValue.dispose();
                super.paintComponent(graphicsParameterValue);
            }
        };

        cardLocalVariableValue.setOpaque(false);
        cardLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 92));
        cardLocalVariableValue.setPreferredSize(new Dimension(0, 92));
        cardLocalVariableValue.setBorder(new EmptyBorder(14, 18, 18, 18));
        cardLocalVariableValue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        cardLocalVariableValue.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent eParameterValue) {
                hoverStateLocalVariableValue[0] = true;
                cardLocalVariableValue.repaint();
            }


            @Override
            public void mouseExited(java.awt.event.MouseEvent eParameterValue) {
                hoverStateLocalVariableValue[0] = false;
                cardLocalVariableValue.repaint();
            }


            @Override
            public void mouseClicked(java.awt.event.MouseEvent eParameterValue) {
                fireMatchClick(teamsParameterValue);
            }
        });

        JPanel leftPanelLocalVariableValue = new JPanel();
        leftPanelLocalVariableValue.setOpaque(false);
        leftPanelLocalVariableValue.setLayout(new BoxLayout(leftPanelLocalVariableValue, BoxLayout.Y_AXIS));

        JLabel homeLabelLocalVariableValue = new JLabel(homeLocalVariableValue);
        homeLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 18));
        homeLabelLocalVariableValue.setForeground(MATCH_CARD_TEAM);

        leftPanelLocalVariableValue.add(buildLiveBadge());
        leftPanelLocalVariableValue.add(Box.createVerticalStrut(6));
        leftPanelLocalVariableValue.add(homeLabelLocalVariableValue);

        JLabel vsLabelLocalVariableValue = new JLabel("VS", SwingConstants.CENTER);
        vsLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 24));
        vsLabelLocalVariableValue.setForeground(new Color(170, 185, 210));

        JPanel rightPanelLocalVariableValue = new JPanel();
        rightPanelLocalVariableValue.setOpaque(false);
        rightPanelLocalVariableValue.setLayout(new BoxLayout(rightPanelLocalVariableValue, BoxLayout.Y_AXIS));

        JLabel awayLabelLocalVariableValue = new JLabel(awayLocalVariableValue, SwingConstants.RIGHT);
        awayLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 18));
        awayLabelLocalVariableValue.setForeground(MATCH_CARD_TEAM);
        awayLabelLocalVariableValue.setAlignmentX(Component.RIGHT_ALIGNMENT);

        Rounded.RoundedButton detailButtonLocalVariableValue =
                new Rounded.RoundedButton("MATCH DETAIL", 10);
        detailButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 12));
        detailButtonLocalVariableValue.setForeground(Color.WHITE);
        detailButtonLocalVariableValue.setGradientColors(
                new Color(55, 109, 230),
                new Color(36, 80, 200)
        );
        detailButtonLocalVariableValue.setShadowEnabled(false);
        detailButtonLocalVariableValue.setPreferredSize(new Dimension(150, 32));
        detailButtonLocalVariableValue.setMaximumSize(new Dimension(150, 32));
        detailButtonLocalVariableValue.setAlignmentX(Component.RIGHT_ALIGNMENT);
        detailButtonLocalVariableValue.addActionListener(
                eventArgumentParameterValue -> fireMatchClick(teamsParameterValue)
        );

        rightPanelLocalVariableValue.add(awayLabelLocalVariableValue);
        rightPanelLocalVariableValue.add(Box.createVerticalStrut(7));
        rightPanelLocalVariableValue.add(detailButtonLocalVariableValue);

        cardLocalVariableValue.add(leftPanelLocalVariableValue, BorderLayout.WEST);
        cardLocalVariableValue.add(vsLabelLocalVariableValue, BorderLayout.CENTER);
        cardLocalVariableValue.add(rightPanelLocalVariableValue, BorderLayout.EAST);

        return cardLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param teamsParameterValue equipos que usa la operacion.
     */
    private void fireMatchClick(String[] teamsParameterValue) {
        if (matchClickListenerFieldReference != null) {
            matchClickListenerFieldReference.actionPerformed(
                    new ActionEvent(teamsParameterValue, ActionEvent.ACTION_PERFORMED, "MATCH_CLICK")
            );
        }
    }


    /**
     * Construye el directo.
     *
     * @return resultado de la operacion.
     */
    private JLabel buildLiveBadge() {
        JLabel badgeLocalVariableValue = new JLabel("  ● LIVE  ") {

            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                Graphics2D g2LocalVariableValue = (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2LocalVariableValue.setColor(LIVE_GREEN);
                g2LocalVariableValue.fillRoundRect(0, 1, getWidth(), getHeight() - 2, getHeight(), getHeight());
                g2LocalVariableValue.dispose();
                super.paintComponent(graphicsParameterValue);
            }
        };

        badgeLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 11));
        badgeLocalVariableValue.setForeground(Color.WHITE);
        badgeLocalVariableValue.setOpaque(false);
        return badgeLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param matchesControllerHandlerParameterValue partidos que usa la operacion.
     */
    public void startAutoRefresh(LiveMatchesController matchesControllerHandlerParameterValue) {
        stopAutoRefresh();
        autoRefreshTimerFieldReference = new Timer(
                3000,
                eventArgumentParameterValue ->
                        updateMatches(matchesControllerHandlerParameterValue.getterLiveGames())
        );
        autoRefreshTimerFieldReference.start();
    }


    /**
     * Gestiona esta operacion.
     */
    public void stopAutoRefresh() {
        if (autoRefreshTimerFieldReference != null) {
            autoRefreshTimerFieldReference.stop();
            autoRefreshTimerFieldReference = null;
        }
    }


    /**
     * Actualiza el partido.
     *
     * @param listenerParameterValue listener que se registra.
     */
    public void setMatchClickListener(ActionListener listenerParameterValue) {
        this.matchClickListenerFieldReference = listenerParameterValue;
    }


    /**
     * Actualiza el configuracion.
     *
     * @param controllerHandlerParameterValue dato de entrada de la operacion.
     */
    public void setConfigController(ActionListener controllerHandlerParameterValue) {
    }


    /**
     * Muestra el dialogo.
     *
     * @param messageParameterValue dato de entrada de la operacion.
     */
    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                messageParameterValue,
                "VIEW_GAMES",
                JOptionPane.WARNING_MESSAGE
        );
    }
}