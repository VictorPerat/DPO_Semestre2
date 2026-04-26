package presentation.Views;

import presentation.ControllerViews.AdminMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista del menú principal para usuarios administradores.
 * Proporciona botones para gestionar ligas, equipos, jugadores, ver estadísticas y cerrar sesión.
 * Organiza los botones en una cuadrícula y tiene un encabezado estilizado.
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
//    public static final String SHOW_STATS = "SHOW_STATS";

    /**
     * Constructor que inicializa y construye la interfaz gráfica del menú de administrador.
     */
    public AdminMenuView() {
        setTitle("MENU ADMIN");
        //setSize(700, 500);
        setSize(800, 500);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear encabezado con título y bolas de fútbol
        JPanel headerPanelLocalVariableValue = new JPanel(new BorderLayout());
        headerPanelLocalVariableValue.setBackground(new Color(0, 30, 60));
        headerPanelLocalVariableValue.setPreferredSize(new Dimension(600, 60));

        ImageIcon ballIconLocalVariableValue = new ImageIcon("S2-Project-E-LeagueManager-2/Project/photos/football.png");
        Image ballImageLocalVariableValue = ballIconLocalVariableValue.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledBallIconLocalVariableValue = new ImageIcon(ballImageLocalVariableValue);
        JLabel leftBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);
        JLabel rightBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);

        JPanel centerPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanelLocalVariableValue.setOpaque(false);
        JLabel titleLabelLocalVariableValue = new JLabel("MENU ADMIN");
        titleLabelLocalVariableValue.setForeground(Color.WHITE);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 20));
        centerPanelLocalVariableValue.add(leftBallLocalVariableValue);
        centerPanelLocalVariableValue.add(titleLabelLocalVariableValue);
        centerPanelLocalVariableValue.add(rightBallLocalVariableValue);

        headerPanelLocalVariableValue.add(centerPanelLocalVariableValue, BorderLayout.CENTER);

        // Botón de configuración
        JButton configButtonLocalVariableValue = new JButton("⚙️");
        configButtonLocalVariableValue.addActionListener(eventArgumentParameterValue -> {
            if (controllerHandlerFieldReference != null) controllerHandlerFieldReference.showConfigDialog();
        });
        configButtonLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        configButtonLocalVariableValue.setContentAreaFilled(false);
        configButtonLocalVariableValue.setForeground(Color.WHITE);
        headerPanelLocalVariableValue.add(configButtonLocalVariableValue, BorderLayout.EAST);

        // Crear y estilizar los botones
        Dimension buttonSizeLocalVariableValue = new Dimension(200, 40);

        createLeagueReferenceButtonFieldReference = createStyledButton("Create new league", buttonSizeLocalVariableValue);
        deleteLeagueReferenceButtonFieldReference = createStyledButton("Delete league", buttonSizeLocalVariableValue);
        viewInterfaceLeaguesButtonFieldReference = createStyledButton("View available leagues", buttonSizeLocalVariableValue);
        createTeamReferenceButtonFieldReference = createStyledButton("Create new team", buttonSizeLocalVariableValue);
        deleteTeamReferenceButtonFieldReference = createStyledButton("Delete team", buttonSizeLocalVariableValue);
        deletePlayerProfileButtonFieldReference = createStyledButton("Delete player", buttonSizeLocalVariableValue);
        viewInterfaceGamesButtonFieldReference = createStyledButton("View Games", buttonSizeLocalVariableValue);

        logoutButtonFieldReference = new Rounded.RoundedButton("Logout", 15);
        logoutButtonFieldReference.setBackground(new Color(180, 40, 40));
        logoutButtonFieldReference.setForeground(Color.WHITE);
        logoutButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 18));
        logoutButtonFieldReference.setPreferredSize(buttonSizeLocalVariableValue);

        // Panel de botones en una cuadrícula
        JPanel buttonPanelLocalVariableValue = new JPanel(new GridLayout(4, 2, 20, 20));
        buttonPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        buttonPanelLocalVariableValue.add(createLeagueReferenceButtonFieldReference);
        buttonPanelLocalVariableValue.add(deleteLeagueReferenceButtonFieldReference);
        buttonPanelLocalVariableValue.add(createTeamReferenceButtonFieldReference);
        buttonPanelLocalVariableValue.add(deleteTeamReferenceButtonFieldReference);
        buttonPanelLocalVariableValue.add(viewInterfaceLeaguesButtonFieldReference);
        buttonPanelLocalVariableValue.add(viewInterfaceGamesButtonFieldReference);
        buttonPanelLocalVariableValue.add(deletePlayerProfileButtonFieldReference);

        // Panel para el botón de logout
        JPanel logoutPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoutPanelLocalVariableValue.setOpaque(false);
        logoutPanelLocalVariableValue.add(logoutButtonFieldReference);

        // Panel principal
        JPanel mainPanelLocalVariableValue = new JPanel(new BorderLayout());
        mainPanelLocalVariableValue.add(buttonPanelLocalVariableValue, BorderLayout.CENTER);
        mainPanelLocalVariableValue.add(logoutPanelLocalVariableValue, BorderLayout.SOUTH);

        // Añadir paneles al frame principal
        add(headerPanelLocalVariableValue, BorderLayout.NORTH);
        add(mainPanelLocalVariableValue, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Crea un botón estilizado con fondo azul y bordes redondeados.
     *
     * @param text Texto que mostrará el botón.
     * @param size Tamaño preferido del botón.
     * @return JButton estilizado.
     */
    private JButton createStyledButton(String textParameterValue, Dimension sizeParameterValue) {
        JButton buttonLocalVariableValue = new Rounded.RoundedButton(textParameterValue, 15);
        buttonLocalVariableValue.setBackground(new Color(0, 30, 60));
        buttonLocalVariableValue.setForeground(Color.WHITE);
        buttonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 18));
        buttonLocalVariableValue.setPreferredSize(sizeParameterValue);
        return buttonLocalVariableValue;
    }

    /**
     * Registra un ActionListener general para todos los botones.
     *
     * @param controller Listener que manejará los eventos de acción.
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

        viewInterfaceGamesButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        viewInterfaceGamesButtonFieldReference.setActionCommand(VIEW_GAMES);

    }

    /**
     * Registra un controlador específico del tipo AdminMenuController.
     *
     * @param controller Controlador que manejará las acciones del menú de administrador.
     */
    public void registerController(AdminMenuController controllerHandlerParameterValue2) {
        this.controllerHandlerFieldReference = controllerHandlerParameterValue2;
        registerController((ActionListener) controllerHandlerParameterValue2);
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje informativo.
     *
     * @param message Mensaje que se mostrará al usuario.
     */
    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(this, messageParameterValue, "Admin Menu", JOptionPane.INFORMATION_MESSAGE);
    }
}
