package presentation.Views;

import bussines.objects.League;
import bussines.managers.PlayerManager;
import bussines.objects.Team;
import bussines.objects.TeamInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Clase que representa la vista detallada de una liga específica.
 * Muestra información como el nombre, ID, fecha de inicio y estadísticas de los equipos participantes.
 */
public class LeagueDetailView extends JFrame {
    private JTable tableFieldReference;

    private final JButton backButtonFieldReference;
    private final JButton configButtonFieldReference;
    private final JButton statsButtonFieldReference;
    private final JButton calendarButtonFieldReference;

    /** Constante para identificar la acción de volver atrás. */
    public static final String BACK = "BACK";
    /** Constante para identificar la acción de configuración. */
    public static final String CONFIG = "CONFIG";

    // Colores de diseño
    private static final Color DARK_BLUE = new Color(22, 49, 72);
    private static final Color BACKGROUND = new Color(245, 245, 245);

    /**
     * Constructor que inicializa la vista detallada de una liga.
     *
     * @param league       Objeto {@link League} que contiene los datos generales de la liga.
     * @param infoOfTeams  Lista de objetos {@link infoTeam} con la información de cada equipo en la liga.
     */
    public LeagueDetailView(League leagueReferenceParameterValue, ArrayList<TeamInfo> informationOfTeamsParameterValue, ArrayList<Team> teamsParameterValue, PlayerManager playerProfileManagerServiceParameterValue) {
        setTitle("League: " + leagueReferenceParameterValue.getName());
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

        JLabel titleLabelLocalVariableValue = new JLabel("LEAGUE DETAILS", SwingConstants.CENTER);
        titleLabelLocalVariableValue.setForeground(Color.WHITE);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 20));

        headerLocalVariableValue.add(backButtonFieldReference, BorderLayout.WEST);
        headerLocalVariableValue.add(titleLabelLocalVariableValue, BorderLayout.CENTER);
        headerLocalVariableValue.add(configButtonFieldReference, BorderLayout.EAST);

        // Panel de botones superior
        JPanel topButtonsPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        topButtonsPanelLocalVariableValue.setBackground(BACKGROUND);

        statsButtonFieldReference = new JButton("Show Stats");
        calendarButtonFieldReference = new JButton("Show Calendar");

        statsButtonFieldReference.setForeground(Color.WHITE);
        calendarButtonFieldReference.setForeground(Color.WHITE);

        for (JButton buttonControlLocalVariableValue : new JButton[]{statsButtonFieldReference, calendarButtonFieldReference}) {
            buttonControlLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 14));
            buttonControlLocalVariableValue.setBackground(DARK_BLUE);
            buttonControlLocalVariableValue.setFocusPainted(false);
            buttonControlLocalVariableValue.setPreferredSize(new Dimension(150, 35));
        }

        topButtonsPanelLocalVariableValue.add(statsButtonFieldReference);
        topButtonsPanelLocalVariableValue.add(calendarButtonFieldReference);

        // Título de la liga
        JLabel leagueReferenceTitleLocalVariableValue = new JLabel(
                leagueReferenceParameterValue.getName() + " - ID: " + leagueReferenceParameterValue.getId() + " | Start: " + leagueReferenceParameterValue.getStartDate(),
                SwingConstants.CENTER
        );
        leagueReferenceTitleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 18));
        leagueReferenceTitleLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        for (int indexCounterLocalVariableValue = 0; indexCounterLocalVariableValue < informationOfTeamsParameterValue.size() - 1; indexCounterLocalVariableValue++) {
            for (int secondaryIndexCounterLocalVariableValue = 0; secondaryIndexCounterLocalVariableValue < informationOfTeamsParameterValue.size() - indexCounterLocalVariableValue - 1; secondaryIndexCounterLocalVariableValue++) {
                TeamInfo t1LocalVariableValue = informationOfTeamsParameterValue.get(secondaryIndexCounterLocalVariableValue);
                TeamInfo t2LocalVariableValue = informationOfTeamsParameterValue.get(secondaryIndexCounterLocalVariableValue + 1);
                if (t1LocalVariableValue.getPoints() < t2LocalVariableValue.getPoints()) {
                    informationOfTeamsParameterValue.set(secondaryIndexCounterLocalVariableValue, t2LocalVariableValue);
                    informationOfTeamsParameterValue.set(secondaryIndexCounterLocalVariableValue + 1, t1LocalVariableValue);
                }
            }
        }

        // Tabla con estadísticas de los equipos
        String[] columnsLocalVariableValue = {"Position", "Team", "PTS", "PLD", "DIF", "Nº Players"};
        Object[][] dataLocalVariableValue = new Object[informationOfTeamsParameterValue.size()][6];

        for (int indexCounterLocalVariableValue2 = 0; indexCounterLocalVariableValue2 < informationOfTeamsParameterValue.size(); indexCounterLocalVariableValue2++) {
            TeamInfo informationLocalVariableValue = informationOfTeamsParameterValue.get(indexCounterLocalVariableValue2);
            String teamReferenceDisplayNameLocalVariableValue = String.valueOf(informationLocalVariableValue.getTeamId()); // por defecto: el ID como texto

            for (Team tLocalVariableValue : teamsParameterValue) {
                if (tLocalVariableValue.getId() == informationLocalVariableValue.getTeamId()) {
                    teamReferenceDisplayNameLocalVariableValue = tLocalVariableValue.getName(); // reemplaza el ID por el nombre real del equipo
                    break;
                }
            }
            dataLocalVariableValue[indexCounterLocalVariableValue2][0] = indexCounterLocalVariableValue2 + 1;
            dataLocalVariableValue[indexCounterLocalVariableValue2][1] = teamReferenceDisplayNameLocalVariableValue;
            dataLocalVariableValue[indexCounterLocalVariableValue2][2] = informationLocalVariableValue.getPoints();
            dataLocalVariableValue[indexCounterLocalVariableValue2][3] = informationLocalVariableValue.getWins();
            dataLocalVariableValue[indexCounterLocalVariableValue2][4] = informationLocalVariableValue.getTies();
            dataLocalVariableValue[indexCounterLocalVariableValue2][5] = playerProfileManagerServiceParameterValue.getNumberOfPlayersByTeamName(teamReferenceDisplayNameLocalVariableValue);
        }


        tableFieldReference = new JTable(new DefaultTableModel(dataLocalVariableValue, columnsLocalVariableValue));
        tableFieldReference.setFont(new Font("Arial", Font.PLAIN, 14));
        tableFieldReference.setRowHeight(28);
        tableFieldReference.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(tableFieldReference);

        // Añadir componentes al panel principal
        mainPanelLocalVariableValue.add(topButtonsPanelLocalVariableValue, BorderLayout.NORTH);
        mainPanelLocalVariableValue.add(leagueReferenceTitleLocalVariableValue, BorderLayout.CENTER);
        mainPanelLocalVariableValue.add(scrollPaneLocalVariableValue, BorderLayout.SOUTH);

        // Añadir paneles al frame
        add(headerLocalVariableValue, BorderLayout.NORTH);
        add(mainPanelLocalVariableValue, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Registra el controlador de eventos para los botones interactivos de la vista.
     *
     * @param controller Acción a ejecutar cuando se presionan los botones.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.setActionCommand(BACK);

//        configButton.addActionListener(controller); //boton config comentados para inhabilitarlo
//        configButton.setActionCommand(CONFIG);

        statsButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        calendarButtonFieldReference.addActionListener(controllerHandlerParameterValue);

        tableFieldReference.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent eventArgumentParameterValue) {
                int rowLocalVariableValue = tableFieldReference.rowAtPoint(eventArgumentParameterValue.getPoint());
                if (rowLocalVariableValue >= 0) {
                    String teamReferenceDisplayNameLocalVariableValue2 = tableFieldReference.getValueAt(rowLocalVariableValue, 1).toString();
                    ActionEvent eventLocalVariableValue = new ActionEvent(teamReferenceDisplayNameLocalVariableValue2, ActionEvent.ACTION_PERFORMED, "Show Team Info");
                    controllerHandlerParameterValue.actionPerformed(eventLocalVariableValue);
                }
            }
        });

    }

}
