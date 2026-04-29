package presentation.Views;

import presentation.ControllerViews.AdminMenuController;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Vista del menú principal del administrador.
 *
 * Estilo tipo mockup:
 * - fondo de campo completo
 * - la barra lateral izquierda ya viene integrada en el fondo
 * - título grande ADMIN MENU
 * - card principal con botones outline
 * - card lateral para live matches
 */
public class AdminMenuView extends JPanel {

    private JButton createLeagueReferenceButtonFieldReference;
    private JButton deleteLeagueReferenceButtonFieldReference;
    private JButton viewInterfaceLeaguesButtonFieldReference;
    private JButton createTeamReferenceButtonFieldReference;
    private JButton deleteTeamReferenceButtonFieldReference;
    private JButton logoutButtonFieldReference;
    private JButton deletePlayerProfileButtonFieldReference;
    private JButton viewInterfaceGamesButtonFieldReference;
    private JPanel liveMatchesListPanelFieldReference;
    private JLabel liveMatchesTitleLabelFieldReference;

    private AdminMenuController controllerHandlerFieldReference;

    public static final String CREATE_LEAGUE = "CREATE_LEAGUE";
    public static final String DELETE_LEAGUE = "DELETE_LEAGUE";
    public static final String VIEW_LEAGUES = "VIEW_LEAGUES";
    public static final String CREATE_TEAM = "CREATE_TEAM";
    public static final String DELETE_TEAM = "DELETE_TEAM";
    public static final String LOGOUT = "LOGOUT";
    public static final String DELETE_PLAYER = "DELETE_PLAYER";
    public static final String VIEW_GAMES = "VIEW_GAMES";

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);
    private static final Color CARD_TITLE_COLOR = new Color(28, 35, 51);

    private static final Color DANGER_RED = new Color(218, 37, 42);
    private static final Color DANGER_RED_DARK = new Color(180, 30, 36);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    private static final String LIVE_ICON_PRIMARY_PATH =
            ProjectPathResolver.resolveProjectPath("photos/Television.png");

    private static final String LIVE_ICON_FALLBACK_PATH =
            ProjectPathResolver.resolveProjectPath("photos/Partidos_Live.png");

    public AdminMenuView() {
        setLayout(new BorderLayout());
        buildButtons();
        add(buildBackgroundPanel(), BorderLayout.CENTER);
    }

    private void buildButtons() {
        createLeagueReferenceButtonFieldReference = buildOutlineMenuButton("C R E A T E   L E A G U E");
        deleteLeagueReferenceButtonFieldReference = buildOutlineMenuButton("D E L E T E   L E A G U E");
        createTeamReferenceButtonFieldReference = buildOutlineMenuButton("C R E A T E   T E A M");
        deleteTeamReferenceButtonFieldReference = buildOutlineMenuButton("D E L E T E   T E A M");
        viewInterfaceLeaguesButtonFieldReference = buildOutlineMenuButton("V I E W   L E A G U E S");
        deletePlayerProfileButtonFieldReference = buildOutlineMenuButton("D E L E T E   P L A Y E R");

        viewInterfaceGamesButtonFieldReference = new Rounded.RoundedButton("O P E N", 10);
        Rounded.RoundedButton liveButtonLocalVariableValue =
                (Rounded.RoundedButton) viewInterfaceGamesButtonFieldReference;

        liveButtonLocalVariableValue.setForeground(Color.WHITE);
        liveButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 17));
        liveButtonLocalVariableValue.setBackground(new Color(42, 85, 191));
        liveButtonLocalVariableValue.setGradientColors(
                new Color(52, 102, 219),
                new Color(42, 85, 191)
        );
        liveButtonLocalVariableValue.setShadowEnabled(false);
        liveButtonLocalVariableValue.setPreferredSize(new Dimension(250, 48));
        liveButtonLocalVariableValue.setMinimumSize(new Dimension(250, 48));
        liveButtonLocalVariableValue.setMaximumSize(new Dimension(250, 48));

        logoutButtonFieldReference = new Rounded.RoundedButton("L O G O U T", 10);
        Rounded.RoundedButton logoutButtonLocalVariableValue =
                (Rounded.RoundedButton) logoutButtonFieldReference;

        logoutButtonLocalVariableValue.setForeground(Color.WHITE);
        logoutButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 17));
        logoutButtonLocalVariableValue.setBackground(DANGER_RED);
        logoutButtonLocalVariableValue.setGradientColors(DANGER_RED, DANGER_RED_DARK);
        logoutButtonLocalVariableValue.setShadowEnabled(false);
        logoutButtonLocalVariableValue.setPreferredSize(new Dimension(240, 46));
        logoutButtonLocalVariableValue.setMinimumSize(new Dimension(240, 46));
        logoutButtonLocalVariableValue.setMaximumSize(new Dimension(240, 46));
    }

    private JButton buildOutlineMenuButton(String labelTextParameterValue) {
        Rounded.RoundedButton buttonLocalVariableValue =
                new Rounded.RoundedButton(labelTextParameterValue, 10);

        buttonLocalVariableValue.setForeground(new Color(36, 83, 204));
        buttonLocalVariableValue.setBackground(new Color(255, 255, 255, 245));
        buttonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 15));
        buttonLocalVariableValue.setOutlineMode(new Color(36, 83, 204), 2);
        buttonLocalVariableValue.setShadowEnabled(false);

        buttonLocalVariableValue.setPreferredSize(new Dimension(238, 46));
        buttonLocalVariableValue.setMinimumSize(new Dimension(238, 46));
        buttonLocalVariableValue.setMaximumSize(new Dimension(238, 46));

        return buttonLocalVariableValue;
    }

    private JPanel buildBackgroundPanel() {
        Image backgroundImageLocalVariableValue =
                new ImageIcon(BACKGROUND_IMAGE_PATH).getImage();

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

                Graphics2D g2LocalVariableValue =
                        (Graphics2D) graphicsParameterValue.create();

                g2LocalVariableValue.setColor(new Color(0, 0, 0, 55));
                g2LocalVariableValue.fillRect(0, 0, getWidth(), getHeight());

                g2LocalVariableValue.dispose();
            }
        };

        backgroundPanelLocalVariableValue.setLayout(new BorderLayout());

        JPanel leftSpacerLocalVariableValue = new JPanel();
        leftSpacerLocalVariableValue.setOpaque(false);
        leftSpacerLocalVariableValue.setPreferredSize(new Dimension(130, 0));
        leftSpacerLocalVariableValue.setMinimumSize(new Dimension(130, 0));

        JPanel centerWrapperLocalVariableValue = new JPanel(new GridBagLayout());
        centerWrapperLocalVariableValue.setOpaque(false);

        JPanel contentPanelLocalVariableValue = new JPanel();
        contentPanelLocalVariableValue.setOpaque(false);
        contentPanelLocalVariableValue.setLayout(
                new BoxLayout(contentPanelLocalVariableValue, BoxLayout.Y_AXIS)
        );

        JPanel titleBlockLocalVariableValue = buildTitleBlock();
        JPanel cardsRowLocalVariableValue = buildCardsRow();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardsRowLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(30));
        contentPanelLocalVariableValue.add(cardsRowLocalVariableValue);

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.weighty = 1.0;
        constraintsLocalVariableValue.anchor = GridBagConstraints.CENTER;
        constraintsLocalVariableValue.insets = new Insets(10, 0, 20, 20);

        centerWrapperLocalVariableValue.add(
                contentPanelLocalVariableValue,
                constraintsLocalVariableValue
        );

        backgroundPanelLocalVariableValue.add(leftSpacerLocalVariableValue, BorderLayout.WEST);
        backgroundPanelLocalVariableValue.add(centerWrapperLocalVariableValue, BorderLayout.CENTER);

        return backgroundPanelLocalVariableValue;
    }

    private JPanel buildTitleBlock() {
        JPanel titleContainerLocalVariableValue = new JPanel();
        titleContainerLocalVariableValue.setOpaque(false);
        titleContainerLocalVariableValue.setLayout(
                new BoxLayout(titleContainerLocalVariableValue, BoxLayout.Y_AXIS)
        );

        JPanel titleLineLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLineLocalVariableValue.setOpaque(false);

        JLabel adminLabelLocalVariableValue = new JLabel("ADMIN ");
        adminLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        adminLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 74));

        JLabel menuLabelLocalVariableValue = new JLabel("MENU");
        menuLabelLocalVariableValue.setForeground(TITLE_WHITE);
        menuLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 74));

        titleLineLocalVariableValue.add(adminLabelLocalVariableValue);
        titleLineLocalVariableValue.add(menuLabelLocalVariableValue);

        JLabel subtitleLabelLocalVariableValue = new JLabel("Manage the league system");
        subtitleLabelLocalVariableValue.setForeground(SUBTITLE_WHITE);
        subtitleLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 24));
        subtitleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabelLocalVariableValue.setBorder(new EmptyBorder(10, 0, 0, 0));

        titleContainerLocalVariableValue.add(titleLineLocalVariableValue);
        titleContainerLocalVariableValue.add(subtitleLabelLocalVariableValue);

        return titleContainerLocalVariableValue;
    }

    private JPanel buildCardsRow() {
        JPanel rowLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 0));
        rowLocalVariableValue.setOpaque(false);

        rowLocalVariableValue.add(buildMainMenuCard());

        return rowLocalVariableValue;
    }

    private JPanel buildMainMenuCard() {
        JPanel cardLocalVariableValue = new WhiteCardPanel(false);
        cardLocalVariableValue.setOpaque(false);
        cardLocalVariableValue.setLayout(new GridBagLayout());
        cardLocalVariableValue.setBorder(new EmptyBorder(34, 26, 32, 26));
        cardLocalVariableValue.setPreferredSize(new Dimension(545, 412));
        cardLocalVariableValue.setMinimumSize(new Dimension(545, 412));
        cardLocalVariableValue.setMaximumSize(new Dimension(545, 412));

        JPanel buttonsGridLocalVariableValue = new JPanel(new GridLayout(3, 2, 26, 54));
        buttonsGridLocalVariableValue.setOpaque(false);

        buttonsGridLocalVariableValue.add(createLeagueReferenceButtonFieldReference);
        buttonsGridLocalVariableValue.add(deleteLeagueReferenceButtonFieldReference);
        buttonsGridLocalVariableValue.add(createTeamReferenceButtonFieldReference);
        buttonsGridLocalVariableValue.add(deleteTeamReferenceButtonFieldReference);
        buttonsGridLocalVariableValue.add(viewInterfaceLeaguesButtonFieldReference);
        buttonsGridLocalVariableValue.add(deletePlayerProfileButtonFieldReference);

        JPanel logoutPanelLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        logoutPanelLocalVariableValue.setOpaque(false);
        logoutPanelLocalVariableValue.add(logoutButtonFieldReference);

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.fill = GridBagConstraints.HORIZONTAL;

        cardLocalVariableValue.add(buttonsGridLocalVariableValue, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 1;
        constraintsLocalVariableValue.insets = new Insets(54, 0, 0, 0);
        cardLocalVariableValue.add(logoutPanelLocalVariableValue, constraintsLocalVariableValue);

        return cardLocalVariableValue;
    }

    private JPanel buildLiveMatchesCard() {
        JPanel cardLocalVariableValue = new WhiteCardPanel(true);
        cardLocalVariableValue.setOpaque(false);
        cardLocalVariableValue.setLayout(
                new BoxLayout(cardLocalVariableValue, BoxLayout.Y_AXIS)
        );
        cardLocalVariableValue.setBorder(new EmptyBorder(28, 32, 28, 32));
        cardLocalVariableValue.setPreferredSize(new Dimension(335, 412));
        cardLocalVariableValue.setMinimumSize(new Dimension(335, 412));
        cardLocalVariableValue.setMaximumSize(new Dimension(335, 412));

        JPanel iconPanelLocalVariableValue = buildLiveIconPanel();
        iconPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        liveMatchesTitleLabelFieldReference =
                new JLabel("LIVE MATCHES", SwingConstants.CENTER);
        liveMatchesTitleLabelFieldReference.setForeground(CARD_TITLE_COLOR);
        liveMatchesTitleLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 20));
        liveMatchesTitleLabelFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);

        liveMatchesListPanelFieldReference = new JPanel();
        liveMatchesListPanelFieldReference.setOpaque(false);
        liveMatchesListPanelFieldReference.setLayout(
                new BoxLayout(liveMatchesListPanelFieldReference, BoxLayout.Y_AXIS)
        );

        JScrollPane liveScrollPaneLocalVariableValue =
                new JScrollPane(liveMatchesListPanelFieldReference);
        liveScrollPaneLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        liveScrollPaneLocalVariableValue.setOpaque(false);
        liveScrollPaneLocalVariableValue.getViewport().setOpaque(false);
        liveScrollPaneLocalVariableValue.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        liveScrollPaneLocalVariableValue.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        );
        liveScrollPaneLocalVariableValue.setPreferredSize(new Dimension(260, 120));
        liveScrollPaneLocalVariableValue.setMaximumSize(new Dimension(260, 120));
        liveScrollPaneLocalVariableValue.setMinimumSize(new Dimension(260, 120));
        liveScrollPaneLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        viewInterfaceGamesButtonFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardLocalVariableValue.add(iconPanelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(18));
        cardLocalVariableValue.add(liveMatchesTitleLabelFieldReference);
        cardLocalVariableValue.add(Box.createVerticalStrut(16));
        cardLocalVariableValue.add(liveScrollPaneLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalGlue());
        cardLocalVariableValue.add(viewInterfaceGamesButtonFieldReference);
        cardLocalVariableValue.add(Box.createVerticalStrut(8));

        updateLiveMatches(new ArrayList<>());

        return cardLocalVariableValue;
    }

    public void updateLiveMatches(List<String[]> liveGamesParameterValue) {
        if (liveMatchesListPanelFieldReference == null) {
            return;
        }

        liveMatchesListPanelFieldReference.removeAll();

        List<String[]> liveGamesLocalVariableValue =
                liveGamesParameterValue == null ? new ArrayList<>() : liveGamesParameterValue;

        if (liveMatchesTitleLabelFieldReference != null) {
            liveMatchesTitleLabelFieldReference.setText(
                    "LIVE MATCHES (" + liveGamesLocalVariableValue.size() + ")"
            );
        }

        if (liveGamesLocalVariableValue.isEmpty()) {
            JLabel emptyLabelLocalVariableValue = new JLabel(
                    "<html><div style='text-align:center;'>No hay partidos en directo actualmente.</div></html>",
                    SwingConstants.CENTER
            );
            emptyLabelLocalVariableValue.setFont(new Font("Arial", Font.ITALIC, 14));
            emptyLabelLocalVariableValue.setForeground(new Color(95, 105, 125));
            emptyLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyLabelLocalVariableValue.setBorder(new EmptyBorder(14, 8, 14, 8));

            liveMatchesListPanelFieldReference.add(emptyLabelLocalVariableValue);
        } else {
            for (String[] liveGameLocalVariableValue : liveGamesLocalVariableValue) {
                liveMatchesListPanelFieldReference.add(
                        buildLiveMatchPreviewRow(liveGameLocalVariableValue)
                );
                liveMatchesListPanelFieldReference.add(Box.createVerticalStrut(8));
            }
        }

        liveMatchesListPanelFieldReference.revalidate();
        liveMatchesListPanelFieldReference.repaint();
    }

    private JPanel buildLiveMatchPreviewRow(String[] liveGameParameterValue) {
        JPanel rowPanelLocalVariableValue = new JPanel(new BorderLayout(8, 0));
        rowPanelLocalVariableValue.setBackground(new Color(245, 247, 252));
        rowPanelLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 225, 240), 1),
                new EmptyBorder(8, 10, 8, 10)
        ));
        rowPanelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        JLabel liveLabelLocalVariableValue = new JLabel("LIVE");
        liveLabelLocalVariableValue.setForeground(new Color(210, 40, 45));
        liveLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 11));

        String homeTeamLocalVariableValue =
                liveGameParameterValue.length > 0 ? liveGameParameterValue[0] : "Home";
        String awayTeamLocalVariableValue =
                liveGameParameterValue.length > 1 ? liveGameParameterValue[1] : "Away";

        JLabel matchLabelLocalVariableValue = new JLabel(
                homeTeamLocalVariableValue + "  VS  " + awayTeamLocalVariableValue,
                SwingConstants.CENTER
        );
        matchLabelLocalVariableValue.setForeground(new Color(28, 35, 51));
        matchLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 13));

        rowPanelLocalVariableValue.add(liveLabelLocalVariableValue, BorderLayout.WEST);
        rowPanelLocalVariableValue.add(matchLabelLocalVariableValue, BorderLayout.CENTER);

        return rowPanelLocalVariableValue;
    }

    private JPanel buildLiveIconPanel() {
        JPanel iconOuterLocalVariableValue = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                Graphics2D g2LocalVariableValue =
                        (Graphics2D) graphicsParameterValue.create();

                g2LocalVariableValue.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                int sizeLocalVariableValue = Math.min(getWidth(), getHeight()) - 8;
                int xLocalVariableValue = (getWidth() - sizeLocalVariableValue) / 2;
                int yLocalVariableValue = (getHeight() - sizeLocalVariableValue) / 2;

                g2LocalVariableValue.setColor(new Color(55, 109, 230, 70));
                g2LocalVariableValue.fillOval(
                        xLocalVariableValue,
                        yLocalVariableValue,
                        sizeLocalVariableValue,
                        sizeLocalVariableValue
                );

                g2LocalVariableValue.setColor(new Color(35, 105, 190));
                g2LocalVariableValue.fillOval(
                        xLocalVariableValue + 10,
                        yLocalVariableValue + 10,
                        sizeLocalVariableValue - 20,
                        sizeLocalVariableValue - 20
                );

                g2LocalVariableValue.dispose();
                super.paintComponent(graphicsParameterValue);
            }
        };

        iconOuterLocalVariableValue.setOpaque(false);
        iconOuterLocalVariableValue.setPreferredSize(new Dimension(108, 108));
        iconOuterLocalVariableValue.setMinimumSize(new Dimension(108, 108));
        iconOuterLocalVariableValue.setMaximumSize(new Dimension(108, 108));

        JLabel iconLabelLocalVariableValue = new JLabel();
        iconLabelLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabelLocalVariableValue.setVerticalAlignment(SwingConstants.CENTER);

        try {
            String iconPathLocalVariableValue =
                    new java.io.File(LIVE_ICON_PRIMARY_PATH).exists()
                            ? LIVE_ICON_PRIMARY_PATH
                            : LIVE_ICON_FALLBACK_PATH;

            Image iconImageLocalVariableValue =
                    new ImageIcon(iconPathLocalVariableValue).getImage();

            Image scaledIconLocalVariableValue =
                    iconImageLocalVariableValue.getScaledInstance(
                            64,
                            64,
                            Image.SCALE_SMOOTH
                    );

            iconLabelLocalVariableValue.setIcon(new ImageIcon(scaledIconLocalVariableValue));
        } catch (Exception ignoredExceptionParameterValue) {
            iconLabelLocalVariableValue.setText("📺");
            iconLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 42));
        }

        iconOuterLocalVariableValue.add(iconLabelLocalVariableValue);

        return iconOuterLocalVariableValue;
    }

    public void registerController(ActionListener controllerHandlerParameterValue) {
        createLeagueReferenceButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        createLeagueReferenceButtonFieldReference.setActionCommand(CREATE_LEAGUE);

        deleteLeagueReferenceButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        deleteLeagueReferenceButtonFieldReference.setActionCommand(DELETE_LEAGUE);

        viewInterfaceLeaguesButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        viewInterfaceLeaguesButtonFieldReference.setActionCommand(VIEW_LEAGUES);

        createTeamReferenceButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        createTeamReferenceButtonFieldReference.setActionCommand(CREATE_TEAM);

        deleteTeamReferenceButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        deleteTeamReferenceButtonFieldReference.setActionCommand(DELETE_TEAM);

        logoutButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        logoutButtonFieldReference.setActionCommand(LOGOUT);

        deletePlayerProfileButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        deletePlayerProfileButtonFieldReference.setActionCommand(DELETE_PLAYER);

        viewInterfaceGamesButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        viewInterfaceGamesButtonFieldReference.setActionCommand(VIEW_GAMES);
    }

    public void registerController(AdminMenuController controllerHandlerParameterValue2) {
        this.controllerHandlerFieldReference = controllerHandlerParameterValue2;
        registerController((ActionListener) controllerHandlerParameterValue2);
    }

    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                messageParameterValue,
                "Admin Menu",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private static class WhiteCardPanel extends JPanel {
        private final boolean drawBlueBorderFieldReference;

        WhiteCardPanel(boolean drawBlueBorderParameterValue) {
            this.drawBlueBorderFieldReference = drawBlueBorderParameterValue;
        }

        @Override
        protected void paintComponent(Graphics graphicsParameterValue) {
            Graphics2D g2LocalVariableValue =
                    (Graphics2D) graphicsParameterValue.create();

            g2LocalVariableValue.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int radiusLocalVariableValue = 10;

            g2LocalVariableValue.setColor(new Color(8, 20, 46, 45));
            g2LocalVariableValue.fillRoundRect(
                    6,
                    8,
                    getWidth() - 12,
                    getHeight() - 8,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.setColor(new Color(255, 255, 255, 250));
            g2LocalVariableValue.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            if (drawBlueBorderFieldReference) {
                g2LocalVariableValue.setColor(ACCENT_COLOR);
                g2LocalVariableValue.setStroke(new BasicStroke(2f));
                g2LocalVariableValue.drawRoundRect(
                        0,
                        0,
                        getWidth() - 1,
                        getHeight() - 1,
                        radiusLocalVariableValue,
                        radiusLocalVariableValue
                );
            }

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }
}
