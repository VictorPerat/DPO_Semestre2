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
import java.util.Comparator;
import java.util.List;

/**
 * Vista detallada de una liga.
 *
 * Muestra los datos generales de la liga y una {@link JTable} con la
 * clasificación de los equipos (apartado 2.7 del enunciado): nombre,
 * número de jugadores, ganados, empatados, perdidos y puntos. La tabla
 * se ordena por puntos descendentes y puede refrescarse en tiempo real
 * a través de {@link #refreshStandings(ArrayList, ArrayList, PlayerManager)}.
 */
public class LeagueDetailView extends JFrame {

    // Columnas mostradas en la JTable de clasificación.
    private static final String[] COLUMN_NAMES = {
            "Position", "Team", "Players", "Won", "Drawn", "Lost", "Points"
    };

    private JTable tableFieldReference;
    private DefaultTableModel tableModelFieldReference;

    // Botones de navegación y acciones de la vista
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
     * Constructor de la vista detallada.
     */
    public LeagueDetailView(League leagueReferenceParameterValue,
                            ArrayList<TeamInfo> informationOfTeamsParameterValue,
                            ArrayList<Team> teamsParameterValue,
                            PlayerManager playerProfileManagerServiceParameterValue) {
        setTitle("League: " + leagueReferenceParameterValue.getName());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanelLocalVariableValue = new JPanel(new BorderLayout());
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

        // Panel de botones de acciones (stats / calendar)
        JPanel topButtonsPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        topButtonsPanelLocalVariableValue.setBackground(BACKGROUND);

        statsButtonFieldReference = new JButton("Show Stats");
        calendarButtonFieldReference = new JButton("Show Calendar");

        statsButtonFieldReference.setForeground(Color.WHITE);
        calendarButtonFieldReference.setForeground(Color.WHITE);

        for (JButton buttonControlLocalVariableValue
                : new JButton[]{statsButtonFieldReference, calendarButtonFieldReference}) {
            buttonControlLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 14));
            buttonControlLocalVariableValue.setBackground(DARK_BLUE);
            buttonControlLocalVariableValue.setFocusPainted(false);
            buttonControlLocalVariableValue.setPreferredSize(new Dimension(150, 35));
        }

        topButtonsPanelLocalVariableValue.add(statsButtonFieldReference);
        topButtonsPanelLocalVariableValue.add(calendarButtonFieldReference);

        // Título de la liga
        JLabel leagueReferenceTitleLocalVariableValue = new JLabel(
                leagueReferenceParameterValue.getName()
                        + " | Start: " + leagueReferenceParameterValue.getStartDate(),
                SwingConstants.CENTER
        );
        leagueReferenceTitleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 18));
        leagueReferenceTitleLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        // Tabla con el modelo inicial vacío; se rellena con buildRowsForStandings
        tableModelFieldReference = new DefaultTableModel(new Object[0][COLUMN_NAMES.length], COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int rowParameterValue, int columnParameterValue) {
                return false;
            }
        };
        tableFieldReference = new JTable(tableModelFieldReference);
        tableFieldReference.setFont(new Font("Arial", Font.PLAIN, 14));
        tableFieldReference.setRowHeight(28);
        tableFieldReference.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableFieldReference.setAutoCreateRowSorter(false);

        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(tableFieldReference);

        // Panel central: título arriba, tabla abajo (la tabla ocupa el grueso)
        JPanel centerPanelLocalVariableValue = new JPanel(new BorderLayout());
        centerPanelLocalVariableValue.setBackground(BACKGROUND);
        centerPanelLocalVariableValue.add(leagueReferenceTitleLocalVariableValue, BorderLayout.NORTH);
        centerPanelLocalVariableValue.add(scrollPaneLocalVariableValue, BorderLayout.CENTER);

        mainPanelLocalVariableValue.add(topButtonsPanelLocalVariableValue, BorderLayout.NORTH);
        mainPanelLocalVariableValue.add(centerPanelLocalVariableValue, BorderLayout.CENTER);

        add(headerLocalVariableValue, BorderLayout.NORTH);
        add(mainPanelLocalVariableValue, BorderLayout.CENTER);

        // Carga inicial de la tabla con los datos recibidos
        refreshStandings(
                informationOfTeamsParameterValue,
                teamsParameterValue,
                playerProfileManagerServiceParameterValue
        );

        setVisible(true);
    }

    /**
     * Vuelve a calcular y mostrar la clasificación. Pensado para que el
     * controlador lo llame desde un timer cada pocos segundos.
     */
    public void refreshStandings(ArrayList<TeamInfo> informationOfTeamsParameterValue,
                                 ArrayList<Team> teamsParameterValue,
                                 PlayerManager playerProfileManagerServiceParameterValue) {
        if (informationOfTeamsParameterValue == null || teamsParameterValue == null) {
            return;
        }

        // Ordenamos por puntos descendentes (apartado 2.7)
        List<TeamInfo> sortedTeamsLocalVariableValue = new ArrayList<>(informationOfTeamsParameterValue);
        sortedTeamsLocalVariableValue.sort(
                Comparator.comparingInt(TeamInfo::getPoints).reversed()
        );

        Object[][] rowsLocalVariableValue =
                buildRowsForStandings(
                        sortedTeamsLocalVariableValue,
                        teamsParameterValue,
                        playerProfileManagerServiceParameterValue
                );

        // Reemplazamos los datos del modelo en el EDT
        Runnable updateRunnableLocalVariableValue = () -> {
            tableModelFieldReference.setDataVector(rowsLocalVariableValue, COLUMN_NAMES);
            tableModelFieldReference.fireTableDataChanged();
        };
        if (SwingUtilities.isEventDispatchThread()) {
            updateRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(updateRunnableLocalVariableValue);
        }
    }

    /**
     * Construye las filas de la tabla a partir de la lista ordenada de
     * TeamInfo y la lista de Teams reales (para resolver nombres).
     */
    private Object[][] buildRowsForStandings(List<TeamInfo> sortedTeamsParameterValue,
                                             ArrayList<Team> teamsParameterValue2,
                                             PlayerManager playerProfileManagerServiceParameterValue2) {
        Object[][] dataLocalVariableValue =
                new Object[sortedTeamsParameterValue.size()][COLUMN_NAMES.length];

        for (int indexCounterLocalVariableValue = 0;
             indexCounterLocalVariableValue < sortedTeamsParameterValue.size();
             indexCounterLocalVariableValue++) {

            TeamInfo informationLocalVariableValue =
                    sortedTeamsParameterValue.get(indexCounterLocalVariableValue);

            String teamNameLocalVariableValue =
                    String.valueOf(informationLocalVariableValue.getTeamId());

            for (Team teamReferenceLocalVariableValue : teamsParameterValue2) {
                if (teamReferenceLocalVariableValue.getId() == informationLocalVariableValue.getTeamId()) {
                    teamNameLocalVariableValue = teamReferenceLocalVariableValue.getName();
                    break;
                }
            }

            int playerCountLocalVariableValue =
                    playerProfileManagerServiceParameterValue2 != null
                            ? playerProfileManagerServiceParameterValue2
                                    .getNumberOfPlayersByTeamName(teamNameLocalVariableValue)
                            : 0;

            dataLocalVariableValue[indexCounterLocalVariableValue][0] = indexCounterLocalVariableValue + 1;
            dataLocalVariableValue[indexCounterLocalVariableValue][1] = teamNameLocalVariableValue;
            dataLocalVariableValue[indexCounterLocalVariableValue][2] = playerCountLocalVariableValue;
            dataLocalVariableValue[indexCounterLocalVariableValue][3] = informationLocalVariableValue.getWins();
            dataLocalVariableValue[indexCounterLocalVariableValue][4] = informationLocalVariableValue.getTies();
            dataLocalVariableValue[indexCounterLocalVariableValue][5] = informationLocalVariableValue.getDefeats();
            dataLocalVariableValue[indexCounterLocalVariableValue][6] = informationLocalVariableValue.getPoints();
        }

        return dataLocalVariableValue;
    }

    /**
     * Registra el controlador de eventos de los botones interactivos.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.setActionCommand(BACK);

        statsButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        calendarButtonFieldReference.addActionListener(controllerHandlerParameterValue);

        tableFieldReference.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent eventArgumentParameterValue) {
                int rowLocalVariableValue =
                        tableFieldReference.rowAtPoint(eventArgumentParameterValue.getPoint());
                if (rowLocalVariableValue >= 0) {
                    String teamReferenceDisplayNameLocalVariableValue =
                            tableFieldReference.getValueAt(rowLocalVariableValue, 1).toString();
                    ActionEvent eventLocalVariableValue = new ActionEvent(
                            teamReferenceDisplayNameLocalVariableValue,
                            ActionEvent.ACTION_PERFORMED,
                            "Show Team Info"
                    );
                    controllerHandlerParameterValue.actionPerformed(eventLocalVariableValue);
                }
            }
        });
    }
}
