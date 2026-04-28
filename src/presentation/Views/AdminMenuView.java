package presentation.Views;

import presentation.ControllerViews.AdminMenuController;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista del menú principal del administrador.
 *
 * Refactorizada visualmente (sin tocar API): mismo lenguaje gráfico
 * que Login/SignUp — pantalla completa, fondo de imagen con overlay,
 * título grande con subrayado de acento, card central translúcida y
 * botones {@link Rounded.RoundedButton} grandes con gradiente.
 */
public class AdminMenuView extends JFrame {

    // Botones de acción del menú
    private JButton createLeagueReferenceButtonFieldReference;
    private JButton deleteLeagueReferenceButtonFieldReference;
    private JButton viewInterfaceLeaguesButtonFieldReference;
    private JButton createTeamReferenceButtonFieldReference;
    private JButton deleteTeamReferenceButtonFieldReference;
    private JButton logoutButtonFieldReference;
    private JButton deletePlayerProfileButtonFieldReference;
    private JButton viewInterfaceGamesButtonFieldReference;
    private JButton showStatsButtonFieldReference;
    private AdminMenuController controllerHandlerFieldReference;

    // Constantes de comandos de acción para identificar los eventos de botón
    public static final String CREATE_LEAGUE = "CREATE_LEAGUE";
    public static final String DELETE_LEAGUE = "DELETE_LEAGUE";
    public static final String VIEW_LEAGUES = "VIEW_LEAGUES";
    public static final String CREATE_TEAM = "CREATE_TEAM";
    public static final String DELETE_TEAM = "DELETE_TEAM";
    public static final String LOGOUT = "LOGOUT";
    public static final String DELETE_PLAYER = "DELETE_PLAYER";
    public static final String VIEW_GAMES = "VIEW_GAMES";

    // Paleta y dimensiones tomadas de Login/SignUp para mantener
    // coherencia visual a lo largo de toda la aplicación.
    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);
    private static final Color CARD_BORDER = new Color(44, 92, 214);
    private static final Color LABEL_COLOR = new Color(108, 127, 154);
    private static final Color PRIMARY_GRADIENT_TOP = new Color(64, 116, 226);
    private static final Color PRIMARY_GRADIENT_BOTTOM = new Color(42, 85, 191);
    private static final Color DANGER_GRADIENT_TOP = new Color(214, 73, 73);
    private static final Color DANGER_GRADIENT_BOTTOM = new Color(170, 40, 40);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    public AdminMenuView() {
        setTitle("League Manager — Admin");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1280, 800));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        buildButtons();

        setContentPane(buildBackgroundPanel());
        setVisible(true);
    }

    /**
     * Crea las instancias de los botones (sin layout aún) para que
     * puedan ser referenciadas tanto al construir el panel como al
     * registrar el listener desde fuera.
     */
    private void buildButtons() {
        createLeagueReferenceButtonFieldReference = buildPrimaryMenuButton("Create new league");
        deleteLeagueReferenceButtonFieldReference = buildPrimaryMenuButton("Delete league");
        viewInterfaceLeaguesButtonFieldReference = buildPrimaryMenuButton("View available leagues");
        createTeamReferenceButtonFieldReference = buildPrimaryMenuButton("Create new team");
        deleteTeamReferenceButtonFieldReference = buildPrimaryMenuButton("Delete team");
        deletePlayerProfileButtonFieldReference = buildPrimaryMenuButton("Delete player");
        viewInterfaceGamesButtonFieldReference = buildPrimaryMenuButton("View games");

        logoutButtonFieldReference = new Rounded.RoundedButton("LOGOUT", 16);
        Rounded.RoundedButton logoutLocalVariableValue =
                (Rounded.RoundedButton) logoutButtonFieldReference;
        logoutLocalVariableValue.setForeground(Color.WHITE);
        logoutLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 22));
        logoutLocalVariableValue.setPreferredSize(new Dimension(260, 56));
        logoutLocalVariableValue.setGradientColors(DANGER_GRADIENT_TOP, DANGER_GRADIENT_BOTTOM);
        logoutLocalVariableValue.setShadowEnabled(true);
    }

    private JButton buildPrimaryMenuButton(String labelTextParameterValue) {
        Rounded.RoundedButton buttonLocalVariableValue =
                new Rounded.RoundedButton(labelTextParameterValue, 16);
        buttonLocalVariableValue.setForeground(Color.WHITE);
        buttonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 20));
        buttonLocalVariableValue.setPreferredSize(new Dimension(0, 60));
        buttonLocalVariableValue.setGradientColors(PRIMARY_GRADIENT_TOP, PRIMARY_GRADIENT_BOTTOM);
        buttonLocalVariableValue.setShadowEnabled(true);
        return buttonLocalVariableValue;
    }

    /**
     * Panel de fondo que pinta la imagen + overlay oscuro y centra
     * (vía GridBagLayout) el bloque de título y la card de botones.
     */
    private JPanel buildBackgroundPanel() {
        Image backgroundImageLocalVariableValue = new ImageIcon(BACKGROUND_IMAGE_PATH).getImage();

        JPanel backgroundPanelLocalVariableValue = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                super.paintComponent(graphicsParameterValue);
                graphicsParameterValue.drawImage(
                        backgroundImageLocalVariableValue,
                        0, 0, getWidth(), getHeight(), this
                );

                Graphics2D g2LocalVariableValue = (Graphics2D) graphicsParameterValue.create();
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
        contentPanelLocalVariableValue.setBorder(new EmptyBorder(20, 0, 20, 0));

        JPanel titleBlockLocalVariableValue = buildTitleBlock();
        JPanel menuCardLocalVariableValue = buildMenuCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuCardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(28));
        contentPanelLocalVariableValue.add(menuCardLocalVariableValue);

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.weighty = 1.0;
        constraintsLocalVariableValue.anchor = GridBagConstraints.CENTER;

        backgroundPanelLocalVariableValue.add(contentPanelLocalVariableValue, constraintsLocalVariableValue);

        return backgroundPanelLocalVariableValue;
    }

    /**
     * Bloque de título: "ADMIN MENU" en grande con la primera palabra
     * en color de acento + barra subrayada + subtítulo.
     */
    private JPanel buildTitleBlock() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeightLocalVariableValue = screenSizeLocalVariableValue.height;

        int mainTitleSizeLocalVariableValue = Math.max(58, (int) (screenHeightLocalVariableValue * 0.075));
        int subtitleSizeLocalVariableValue = Math.max(23, (int) (screenHeightLocalVariableValue * 0.030));

        JPanel titleContainerLocalVariableValue = new JPanel();
        titleContainerLocalVariableValue.setOpaque(false);
        titleContainerLocalVariableValue.setLayout(
                new BoxLayout(titleContainerLocalVariableValue, BoxLayout.Y_AXIS)
        );

        JPanel titleLinePanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLinePanelLocalVariableValue.setOpaque(false);

        JLabel adminLabelLocalVariableValue = new JLabel("ADMIN ");
        adminLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        adminLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, mainTitleSizeLocalVariableValue));

        JLabel menuLabelLocalVariableValue = new JLabel("MENU");
        menuLabelLocalVariableValue.setForeground(TITLE_WHITE);
        menuLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, mainTitleSizeLocalVariableValue));

        titleLinePanelLocalVariableValue.add(adminLabelLocalVariableValue);
        titleLinePanelLocalVariableValue.add(menuLabelLocalVariableValue);

        JPanel underlineWrapperLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        underlineWrapperLocalVariableValue.setOpaque(false);

        JPanel underlinePanelLocalVariableValue = new JPanel();
        underlinePanelLocalVariableValue.setBackground(ACCENT_COLOR);
        underlinePanelLocalVariableValue.setPreferredSize(new Dimension(190, 6));
        underlinePanelLocalVariableValue.setMinimumSize(new Dimension(190, 6));
        underlinePanelLocalVariableValue.setMaximumSize(new Dimension(190, 6));

        underlineWrapperLocalVariableValue.add(underlinePanelLocalVariableValue);

        JLabel subtitleLabelLocalVariableValue = new JLabel(
                "Manage leagues, teams, players and matches",
                SwingConstants.CENTER
        );
        subtitleLabelLocalVariableValue.setForeground(SUBTITLE_WHITE);
        subtitleLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, subtitleSizeLocalVariableValue));
        subtitleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabelLocalVariableValue.setBorder(new EmptyBorder(22, 0, 0, 0));

        titleContainerLocalVariableValue.add(titleLinePanelLocalVariableValue);
        titleContainerLocalVariableValue.add(Box.createVerticalStrut(14));
        titleContainerLocalVariableValue.add(underlineWrapperLocalVariableValue);
        titleContainerLocalVariableValue.add(subtitleLabelLocalVariableValue);

        return titleContainerLocalVariableValue;
    }

    /**
     * Card translúcida con todos los botones del menú + el botón de
     * logout. Mismo estilo que la card del SignUp.
     */
    private JPanel buildMenuCard() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidthLocalVariableValue = screenSizeLocalVariableValue.width;

        int cardWidthLocalVariableValue = Math.max(820, Math.min(960, (int) (screenWidthLocalVariableValue * 0.58)));

        JPanel cardPanelLocalVariableValue = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                Graphics2D g2LocalVariableValue = (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2LocalVariableValue.setColor(new Color(8, 20, 46, 55));
                g2LocalVariableValue.fillRoundRect(10, 14, getWidth() - 20, getHeight() - 18, 30, 30);

                g2LocalVariableValue.setColor(new Color(255, 255, 255, 238));
                g2LocalVariableValue.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 12, 28, 28);

                g2LocalVariableValue.setColor(CARD_BORDER);
                g2LocalVariableValue.setStroke(new BasicStroke(2f));
                g2LocalVariableValue.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 12, 28, 28);

                g2LocalVariableValue.dispose();
                super.paintComponent(graphicsParameterValue);
            }
        };

        cardPanelLocalVariableValue.setOpaque(false);
        cardPanelLocalVariableValue.setPreferredSize(new Dimension(cardWidthLocalVariableValue, 520));
        cardPanelLocalVariableValue.setMaximumSize(new Dimension(cardWidthLocalVariableValue, 520));
        cardPanelLocalVariableValue.setBorder(new EmptyBorder(36, 38, 36, 38));

        // Grid 4×2 con los botones del menú
        JPanel buttonGridLocalVariableValue = new JPanel(new GridLayout(4, 2, 22, 22));
        buttonGridLocalVariableValue.setOpaque(false);
        buttonGridLocalVariableValue.add(createLeagueReferenceButtonFieldReference);
        buttonGridLocalVariableValue.add(deleteLeagueReferenceButtonFieldReference);
        buttonGridLocalVariableValue.add(createTeamReferenceButtonFieldReference);
        buttonGridLocalVariableValue.add(deleteTeamReferenceButtonFieldReference);
        buttonGridLocalVariableValue.add(viewInterfaceLeaguesButtonFieldReference);
        buttonGridLocalVariableValue.add(viewInterfaceGamesButtonFieldReference);
        buttonGridLocalVariableValue.add(deletePlayerProfileButtonFieldReference);
        // Octava celda vacía para mantener simetría
        JPanel emptyCellLocalVariableValue = new JPanel();
        emptyCellLocalVariableValue.setOpaque(false);
        buttonGridLocalVariableValue.add(emptyCellLocalVariableValue);

        // Logout debajo, centrado
        JPanel logoutPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        logoutPanelLocalVariableValue.setOpaque(false);
        logoutPanelLocalVariableValue.add(logoutButtonFieldReference);

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.fill = GridBagConstraints.HORIZONTAL;
        cardPanelLocalVariableValue.add(buttonGridLocalVariableValue, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 1;
        constraintsLocalVariableValue.insets = new Insets(28, 0, 0, 0);
        cardPanelLocalVariableValue.add(logoutPanelLocalVariableValue, constraintsLocalVariableValue);

        return cardPanelLocalVariableValue;
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
        JOptionPane.showMessageDialog(this, messageParameterValue, "Admin Menu", JOptionPane.INFORMATION_MESSAGE);
    }
}
