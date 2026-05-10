package presentation.Views;

import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;


/**
 * Representa la vista del administrador menu.
 */
public class AdminMenuView extends JPanel {

    private JButton createLeagueReferenceButtonFieldReference;
    private JButton deleteLeagueReferenceButtonFieldReference;
    private JButton viewInterfaceLeaguesButtonFieldReference;
    private JButton createTeamReferenceButtonFieldReference;
    private JButton deleteTeamReferenceButtonFieldReference;
    private JButton logoutButtonFieldReference;
    private JButton deletePlayerProfileButtonFieldReference;

    /**
     * Constante para el liga.
     */
    public static final String CREATE_LEAGUE = "CREATE_LEAGUE";
    /**
     * Constante para el liga.
     */
    public static final String DELETE_LEAGUE = "DELETE_LEAGUE";
    /**
     * Constante para los vista ligas.
     */
    public static final String VIEW_LEAGUES = "VIEW_LEAGUES";
    /**
     * Constante para el equipo.
     */
    public static final String CREATE_TEAM = "CREATE_TEAM";
    /**
     * Constante para el equipo.
     */
    public static final String DELETE_TEAM = "DELETE_TEAM";
    /**
     * Constante para el valor.
     */
    public static final String LOGOUT = "LOGOUT";
    /**
     * Constante para el jugador.
     */
    public static final String DELETE_PLAYER = "DELETE_PLAYER";
    /**
     * Constante para los vista partidos.
     */
    public static final String VIEW_GAMES = "VIEW_GAMES";

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);
    private static final Color CARD_BORDER = new Color(44, 92, 214);

    private static final Color PRIMARY_GRADIENT_TOP = new Color(64, 116, 226);
    private static final Color PRIMARY_GRADIENT_BOTTOM = new Color(42, 85, 191);

    private static final Color DANGER_GRADIENT_TOP = new Color(218, 37, 42);
    private static final Color DANGER_GRADIENT_BOTTOM = new Color(180, 30, 36);

    private static final Color DANGER_TEXT = new Color(200, 45, 50);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");


    /**
     * Crea una instancia de el administrador menu.
     */
    public AdminMenuView() {
        setLayout(new BorderLayout());
        buildButtons();
        add(buildBackgroundPanel(), BorderLayout.CENTER);
    }


    /**
     * Construye el contenido.
     */
    private void buildButtons() {
        createLeagueReferenceButtonFieldReference = buildPrimaryMenuButton("CREATE LEAGUE");
        createTeamReferenceButtonFieldReference = buildPrimaryMenuButton("CREATE TEAM");
        viewInterfaceLeaguesButtonFieldReference = buildPrimaryMenuButton("VIEW LEAGUES");

        deleteLeagueReferenceButtonFieldReference = buildDangerOutlineMenuButton("DELETE LEAGUE");
        deleteTeamReferenceButtonFieldReference = buildDangerOutlineMenuButton("DELETE TEAM");
        deletePlayerProfileButtonFieldReference = buildDangerOutlineMenuButton("DELETE PLAYER");

        logoutButtonFieldReference = new Rounded.RoundedButton("LOGOUT", 16);
        Rounded.RoundedButton logoutButtonLocalVariableValue =
                (Rounded.RoundedButton) logoutButtonFieldReference;

        logoutButtonLocalVariableValue.setForeground(Color.WHITE);
        logoutButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 24));
        logoutButtonLocalVariableValue.setPreferredSize(new Dimension(0, 58));
        logoutButtonLocalVariableValue.setMinimumSize(new Dimension(0, 58));
        logoutButtonLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        logoutButtonLocalVariableValue.setGradientColors(
                DANGER_GRADIENT_TOP,
                DANGER_GRADIENT_BOTTOM
        );
        logoutButtonLocalVariableValue.setShadowEnabled(true);
    }


    /**
     * Construye el menu.
     *
     * @param labelTextParameterValue texto que usa la operacion.
     * @return resultado de la operacion.
     */
    private JButton buildPrimaryMenuButton(String labelTextParameterValue) {
        Rounded.RoundedButton buttonLocalVariableValue =
                new Rounded.RoundedButton(labelTextParameterValue, 16);

        buttonLocalVariableValue.setForeground(Color.WHITE);
        buttonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 19));
        buttonLocalVariableValue.setPreferredSize(new Dimension(260, 58));
        buttonLocalVariableValue.setMinimumSize(new Dimension(260, 58));
        buttonLocalVariableValue.setMaximumSize(new Dimension(260, 58));
        buttonLocalVariableValue.setGradientColors(
                PRIMARY_GRADIENT_TOP,
                PRIMARY_GRADIENT_BOTTOM
        );
        buttonLocalVariableValue.setShadowEnabled(true);

        return buttonLocalVariableValue;
    }


    /**
     * Construye el menu.
     *
     * @param labelTextParameterValue texto que usa la operacion.
     * @return resultado de la operacion.
     */
    private JButton buildDangerOutlineMenuButton(String labelTextParameterValue) {
        Rounded.RoundedButton buttonLocalVariableValue =
                new Rounded.RoundedButton(labelTextParameterValue, 16);

        buttonLocalVariableValue.setForeground(DANGER_TEXT);
        buttonLocalVariableValue.setBackground(new Color(255, 255, 255, 30));
        buttonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 19));
        buttonLocalVariableValue.setPreferredSize(new Dimension(260, 58));
        buttonLocalVariableValue.setMinimumSize(new Dimension(260, 58));
        buttonLocalVariableValue.setMaximumSize(new Dimension(260, 58));
        buttonLocalVariableValue.setOutlineMode(DANGER_TEXT, 2);
        buttonLocalVariableValue.setShadowEnabled(false);

        return buttonLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildBackgroundPanel() {
        Image backgroundImageLocalVariableValue =
                new ImageIcon(BACKGROUND_IMAGE_PATH).getImage();

        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidthLocalVariableValue = screenSizeLocalVariableValue.width;

        int horizontalOffsetLocalVariableValue =
                Math.max(70, (int) (screenWidthLocalVariableValue * 0.05));

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

                g2LocalVariableValue.setColor(new Color(0, 0, 0, 35));
                g2LocalVariableValue.fillRect(0, 0, getWidth(), getHeight());

                g2LocalVariableValue.dispose();
            }
        };

        backgroundPanelLocalVariableValue.setLayout(new GridBagLayout());

        JPanel contentPanelLocalVariableValue = new JPanel();
        contentPanelLocalVariableValue.setOpaque(false);
        contentPanelLocalVariableValue.setLayout(
                new BoxLayout(contentPanelLocalVariableValue, BoxLayout.Y_AXIS)
        );
        contentPanelLocalVariableValue.setBorder(new EmptyBorder(0, 0, 175, 0));

        JPanel titleBlockLocalVariableValue = buildTitleBlock();
        JPanel menuCardLocalVariableValue = buildMenuCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuCardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(34));
        contentPanelLocalVariableValue.add(menuCardLocalVariableValue);

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.weighty = 1.0;
        constraintsLocalVariableValue.anchor = GridBagConstraints.CENTER;
        constraintsLocalVariableValue.insets = new Insets(
                0,
                horizontalOffsetLocalVariableValue,
                0,
                310
        );

        backgroundPanelLocalVariableValue.add(
                contentPanelLocalVariableValue,
                constraintsLocalVariableValue
        );

        return backgroundPanelLocalVariableValue;
    }


    /**
     * Construye el titulo.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildTitleBlock() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeightLocalVariableValue = screenSizeLocalVariableValue.height;

        int mainTitleSizeLocalVariableValue =
                Math.max(62, (int) (screenHeightLocalVariableValue * 0.082));

        int subtitleSizeLocalVariableValue =
                Math.max(24, (int) (screenHeightLocalVariableValue * 0.031));

        JPanel titleContainerLocalVariableValue = new JPanel();
        titleContainerLocalVariableValue.setOpaque(false);
        titleContainerLocalVariableValue.setLayout(
                new BoxLayout(titleContainerLocalVariableValue, BoxLayout.Y_AXIS)
        );

        JPanel titleLinePanelLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLinePanelLocalVariableValue.setOpaque(false);

        JLabel adminLabelLocalVariableValue = new JLabel("ADMIN ");
        adminLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        adminLabelLocalVariableValue.setFont(
                new Font("Arial", Font.PLAIN, mainTitleSizeLocalVariableValue)
        );

        JLabel menuLabelLocalVariableValue = new JLabel("MENU");
        menuLabelLocalVariableValue.setForeground(TITLE_WHITE);
        menuLabelLocalVariableValue.setFont(
                new Font("Arial", Font.PLAIN, mainTitleSizeLocalVariableValue)
        );

        titleLinePanelLocalVariableValue.add(adminLabelLocalVariableValue);
        titleLinePanelLocalVariableValue.add(menuLabelLocalVariableValue);

        JPanel underlineWrapperLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        underlineWrapperLocalVariableValue.setOpaque(false);

        JPanel underlinePanelLocalVariableValue = new JPanel();
        underlinePanelLocalVariableValue.setBackground(ACCENT_COLOR);
        underlinePanelLocalVariableValue.setPreferredSize(new Dimension(190, 6));
        underlinePanelLocalVariableValue.setMinimumSize(new Dimension(190, 6));
        underlinePanelLocalVariableValue.setMaximumSize(new Dimension(190, 6));

        underlineWrapperLocalVariableValue.add(underlinePanelLocalVariableValue);

        JLabel subtitleLabelLocalVariableValue =
                new JLabel("Manage leagues, teams and players", SwingConstants.CENTER);
        subtitleLabelLocalVariableValue.setForeground(SUBTITLE_WHITE);
        subtitleLabelLocalVariableValue.setFont(
                new Font("Arial", Font.PLAIN, subtitleSizeLocalVariableValue)
        );
        subtitleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabelLocalVariableValue.setBorder(new EmptyBorder(22, 0, 0, 0));

        titleContainerLocalVariableValue.add(titleLinePanelLocalVariableValue);
        titleContainerLocalVariableValue.add(Box.createVerticalStrut(14));
        titleContainerLocalVariableValue.add(underlineWrapperLocalVariableValue);
        titleContainerLocalVariableValue.add(subtitleLabelLocalVariableValue);

        return titleContainerLocalVariableValue;
    }


    /**
     * Construye el menu.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildMenuCard() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidthLocalVariableValue = screenSizeLocalVariableValue.width;

        int cardWidthLocalVariableValue =
                Math.max(660, Math.min(760, (int) (screenWidthLocalVariableValue * 0.48)));

        JPanel cardPanelLocalVariableValue = new LoginStyleCardPanel();
        cardPanelLocalVariableValue.setOpaque(false);
        cardPanelLocalVariableValue.setLayout(new GridBagLayout());
        cardPanelLocalVariableValue.setPreferredSize(new Dimension(cardWidthLocalVariableValue, 500));
        cardPanelLocalVariableValue.setMaximumSize(new Dimension(cardWidthLocalVariableValue, 500));
        cardPanelLocalVariableValue.setBorder(new EmptyBorder(38, 42, 38, 42));

        JPanel buttonsGridLocalVariableValue = new JPanel(new GridLayout(3, 2, 24, 24));
        buttonsGridLocalVariableValue.setOpaque(false);

        buttonsGridLocalVariableValue.add(createLeagueReferenceButtonFieldReference);
        buttonsGridLocalVariableValue.add(deleteLeagueReferenceButtonFieldReference);
        buttonsGridLocalVariableValue.add(createTeamReferenceButtonFieldReference);
        buttonsGridLocalVariableValue.add(deleteTeamReferenceButtonFieldReference);
        buttonsGridLocalVariableValue.add(viewInterfaceLeaguesButtonFieldReference);
        buttonsGridLocalVariableValue.add(deletePlayerProfileButtonFieldReference);

        JPanel dividerPanelLocalVariableValue = buildDividerPanel();

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.fill = GridBagConstraints.HORIZONTAL;
        constraintsLocalVariableValue.weightx = 1.0;

        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.insets = new Insets(0, 0, 24, 0);
        cardPanelLocalVariableValue.add(buttonsGridLocalVariableValue, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 1;
        constraintsLocalVariableValue.insets = new Insets(4, 0, 22, 0);
        cardPanelLocalVariableValue.add(dividerPanelLocalVariableValue, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 2;
        constraintsLocalVariableValue.insets = new Insets(0, 100, 0, 100);
        cardPanelLocalVariableValue.add(logoutButtonFieldReference, constraintsLocalVariableValue);

        return cardPanelLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildDividerPanel() {
        JPanel dividerPanelLocalVariableValue = new JPanel();
        dividerPanelLocalVariableValue.setOpaque(false);
        dividerPanelLocalVariableValue.setLayout(
                new BoxLayout(dividerPanelLocalVariableValue, BoxLayout.X_AXIS)
        );

        JComponent lineLeftLocalVariableValue = buildDividerLine();
        JComponent lineRightLocalVariableValue = buildDividerLine();

        JLabel adminLabelLocalVariableValue = new JLabel("ADMIN");
        adminLabelLocalVariableValue.setForeground(new Color(150, 160, 176));
        adminLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 14));
        adminLabelLocalVariableValue.setBorder(new EmptyBorder(0, 14, 0, 14));

        dividerPanelLocalVariableValue.add(lineLeftLocalVariableValue);
        dividerPanelLocalVariableValue.add(adminLabelLocalVariableValue);
        dividerPanelLocalVariableValue.add(lineRightLocalVariableValue);

        return dividerPanelLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JComponent buildDividerLine() {
        return new JComponent() {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 1);
            }


            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                Graphics2D g2LocalVariableValue =
                        (Graphics2D) graphicsParameterValue.create();

                g2LocalVariableValue.setColor(new Color(214, 220, 230));
                g2LocalVariableValue.fillRect(
                        0,
                        getHeight() / 2,
                        getWidth(),
                        1
                );

                g2LocalVariableValue.dispose();
            }
        };
    }


    /**
     * Registra la accion.
     *
     * @param controllerHandlerParameterValue dato de entrada de la operacion.
     */
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
                "Admin Menu",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    /**
     * Agrupa la logica de el inicio de sesion.
     */
    private static class LoginStyleCardPanel extends JPanel {


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
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2LocalVariableValue.setColor(new Color(8, 20, 46, 55));
            g2LocalVariableValue.fillRoundRect(
                    10,
                    14,
                    getWidth() - 20,
                    getHeight() - 18,
                    30,
                    30
            );

            g2LocalVariableValue.setColor(new Color(255, 255, 255, 238));
            g2LocalVariableValue.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 12,
                    28,
                    28
            );

            g2LocalVariableValue.setColor(CARD_BORDER);
            g2LocalVariableValue.setStroke(new BasicStroke(2f));
            g2LocalVariableValue.drawRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 12,
                    28,
                    28
            );

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }
}


