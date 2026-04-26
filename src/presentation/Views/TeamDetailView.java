package presentation.Views;

import bussines.objects.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Vista que muestra los detalles de un equipo, incluyendo una tabla con la información
 * de los jugadores que forman parte del equipo.
 *
 * La interfaz incluye botones para volver atrás y para acceder a la configuración.
 * La tabla muestra columnas con datos del jugador como DNI, nombre, email, equipo, dorsal y teléfono.
 */
public class TeamDetailView extends JFrame {
    private final JButton backButtonFieldReference;
    private final JButton configButtonFieldReference;

    private static final Color DARK_BLUE = new Color(22, 49, 72);
    private static final Color BACKGROUND = new Color(245, 245, 245);

    public static final String BACK = "BACK";
    public static final String CONFIG = "CONFIG";

    /**
     * Constructor que crea la ventana con los detalles del equipo y la lista de jugadores.
     *
     * @param teamName Nombre del equipo que se mostrará en la cabecera y título.
     * @param players Lista de jugadores que forman el equipo y cuyos datos se mostrarán en la tabla.
     */
    public TeamDetailView(String teamReferenceDisplayNameParameterValue, List<Player> playersParameterValue) {
        setTitle("Team Details: " + teamReferenceDisplayNameParameterValue);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanelLocalVariableValue = new JPanel();
        mainPanelLocalVariableValue.setLayout(new BorderLayout());
        mainPanelLocalVariableValue.setBackground(BACKGROUND);

        // Cabecera
        JPanel headerLocalVariableValue = new JPanel(new BorderLayout());
        headerLocalVariableValue.setBackground(DARK_BLUE);
        headerLocalVariableValue.setPreferredSize(new Dimension(800, 50));

        backButtonFieldReference = Rounded.HeaderButtonHelper.createBackButton(null);
        configButtonFieldReference = Rounded.HeaderButtonHelper.createConfigButton(null);

        JLabel titleLabelLocalVariableValue = new JLabel(teamReferenceDisplayNameParameterValue + " - " + playersParameterValue.size() + " players", SwingConstants.CENTER);
        titleLabelLocalVariableValue.setForeground(Color.WHITE);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 20));

        headerLocalVariableValue.add(backButtonFieldReference, BorderLayout.WEST);
        headerLocalVariableValue.add(titleLabelLocalVariableValue, BorderLayout.CENTER);
        headerLocalVariableValue.add(configButtonFieldReference, BorderLayout.EAST);

        JPanel topButtonsPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        topButtonsPanelLocalVariableValue.setBackground(BACKGROUND);

        JPanel contentPanelLocalVariableValue = new JPanel();
        contentPanelLocalVariableValue.setBackground(BACKGROUND);
        String[] columnNamesLocalVariableValue = {"DNI", "Name", "Email", "Team", "Dorsal", "Phone"};
        String[][] playerProfileDataLocalVariableValue = new String[playersParameterValue.size()][6];


        for (int indexCounterLocalVariableValue = 0; indexCounterLocalVariableValue < playersParameterValue.size(); indexCounterLocalVariableValue++) {
            Player itemValueLocalVariableValue = playersParameterValue.get(indexCounterLocalVariableValue);
            playerProfileDataLocalVariableValue[indexCounterLocalVariableValue][0] = itemValueLocalVariableValue.getDni();
            playerProfileDataLocalVariableValue[indexCounterLocalVariableValue][1] = itemValueLocalVariableValue.getName();
            playerProfileDataLocalVariableValue[indexCounterLocalVariableValue][2] = itemValueLocalVariableValue.getEmail();
            playerProfileDataLocalVariableValue[indexCounterLocalVariableValue][3] = itemValueLocalVariableValue.getTeam();
            playerProfileDataLocalVariableValue[indexCounterLocalVariableValue][4] = String.valueOf(itemValueLocalVariableValue.getDorsal());
            playerProfileDataLocalVariableValue[indexCounterLocalVariableValue][5] = String.valueOf(itemValueLocalVariableValue.getPhoneNumber());
        }

        JTable tableLocalVariableValue = new JTable(playerProfileDataLocalVariableValue, columnNamesLocalVariableValue);
        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(tableLocalVariableValue);
        mainPanelLocalVariableValue.add(scrollPaneLocalVariableValue, BorderLayout.CENTER);

        add(headerLocalVariableValue, BorderLayout.NORTH);
        add(mainPanelLocalVariableValue, BorderLayout.CENTER);

        setVisible(false);
    }

    /**
     * Registra un controlador para los botones de la vista.
     *
     * @param controller ActionListener que manejará los eventos de los botones.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.setActionCommand(BACK);

        configButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        configButtonFieldReference.setActionCommand(CONFIG);
    }

}