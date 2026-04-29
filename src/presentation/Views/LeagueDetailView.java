package presentation.Views;

import bussines.managers.PlayerManager;
import bussines.objects.League;
import bussines.objects.Team;
import bussines.objects.TeamInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Detalle de una liga como tarjeta CardLayout. */
public class LeagueDetailView extends JPanel {
    private static final String[] COLUMN_NAMES = {"Position", "Team", "Players", "Won", "Drawn", "Lost", "Points"};
    public static final String BACK = "BACK";
    public static final String CONFIG = "CONFIG";
    public static final String SHOW_STATS = "SHOW_STATS";
    public static final String SHOW_CALENDAR = "SHOW_CALENDAR";

    private static final Color DARK_BLUE = new Color(22, 49, 72);
    private static final Color BACKGROUND = new Color(245, 245, 245);

    private JTable tableFieldReference;
    private DefaultTableModel tableModelFieldReference;
    private JLabel leagueReferenceTitleLabelFieldReference;
    private JButton backButtonFieldReference;
    private JButton configButtonFieldReference;
    private JButton statsButtonFieldReference;
    private JButton calendarButtonFieldReference;
    private League currentLeagueFieldReference;
    private ArrayList<Team> currentTeamsFieldReference = new ArrayList<>();

    public LeagueDetailView() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND);

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

        JPanel topButtonsPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        topButtonsPanelLocalVariableValue.setBackground(BACKGROUND);
        statsButtonFieldReference = new JButton("Show Stats");
        statsButtonFieldReference.setActionCommand(SHOW_STATS);
        calendarButtonFieldReference = new JButton("Show Calendar");
        calendarButtonFieldReference.setActionCommand(SHOW_CALENDAR);
        for (JButton buttonControlLocalVariableValue : new JButton[]{statsButtonFieldReference, calendarButtonFieldReference}) {
            buttonControlLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 14));
            buttonControlLocalVariableValue.setBackground(DARK_BLUE);
            buttonControlLocalVariableValue.setForeground(Color.WHITE);
            buttonControlLocalVariableValue.setFocusPainted(false);
            buttonControlLocalVariableValue.setPreferredSize(new Dimension(150, 35));
        }
        topButtonsPanelLocalVariableValue.add(statsButtonFieldReference);
        topButtonsPanelLocalVariableValue.add(calendarButtonFieldReference);

        leagueReferenceTitleLabelFieldReference = new JLabel("No league selected", SwingConstants.CENTER);
        leagueReferenceTitleLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 18));
        leagueReferenceTitleLabelFieldReference.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        tableModelFieldReference = new DefaultTableModel(new Object[0][COLUMN_NAMES.length], COLUMN_NAMES) {
            @Override public boolean isCellEditable(int rowParameterValue, int columnParameterValue) { return false; }
        };
        tableFieldReference = new JTable(tableModelFieldReference);
        tableFieldReference.setFont(new Font("Arial", Font.PLAIN, 14));
        tableFieldReference.setRowHeight(28);
        tableFieldReference.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableFieldReference.setAutoCreateRowSorter(false);

        JPanel centerPanelLocalVariableValue = new JPanel(new BorderLayout());
        centerPanelLocalVariableValue.setBackground(BACKGROUND);
        centerPanelLocalVariableValue.add(leagueReferenceTitleLabelFieldReference, BorderLayout.NORTH);
        centerPanelLocalVariableValue.add(new JScrollPane(tableFieldReference), BorderLayout.CENTER);

        JPanel mainPanelLocalVariableValue = new JPanel(new BorderLayout());
        mainPanelLocalVariableValue.setBackground(BACKGROUND);
        mainPanelLocalVariableValue.add(topButtonsPanelLocalVariableValue, BorderLayout.NORTH);
        mainPanelLocalVariableValue.add(centerPanelLocalVariableValue, BorderLayout.CENTER);

        add(headerLocalVariableValue, BorderLayout.NORTH);
        add(mainPanelLocalVariableValue, BorderLayout.CENTER);
    }

    public void loadLeague(League leagueReferenceParameterValue,
                           ArrayList<TeamInfo> informationOfTeamsParameterValue,
                           ArrayList<Team> teamsParameterValue,
                           PlayerManager playerProfileManagerServiceParameterValue) {
        this.currentLeagueFieldReference = leagueReferenceParameterValue;
        this.currentTeamsFieldReference = teamsParameterValue == null ? new ArrayList<>() : teamsParameterValue;
        if (leagueReferenceParameterValue != null) {
            leagueReferenceTitleLabelFieldReference.setText(leagueReferenceParameterValue.getName() + " | Start: " + leagueReferenceParameterValue.getStartDate());
        } else {
            leagueReferenceTitleLabelFieldReference.setText("No league selected");
        }
        refreshStandings(informationOfTeamsParameterValue, this.currentTeamsFieldReference, playerProfileManagerServiceParameterValue);
    }

    public League getCurrentLeague() { return currentLeagueFieldReference; }
    public JTable getStandingsTable() { return tableFieldReference; }
    public ArrayList<Team> getCurrentTeams() { return currentTeamsFieldReference; }

    public String getTeamNameAtViewRow(int rowParameterValue) {
        if (rowParameterValue < 0) { return null; }
        int modelRowLocalVariableValue = tableFieldReference.convertRowIndexToModel(rowParameterValue);
        Object valueLocalVariableValue = tableModelFieldReference.getValueAt(modelRowLocalVariableValue, 1);
        return valueLocalVariableValue == null ? null : valueLocalVariableValue.toString();
    }

    public void refreshStandings(ArrayList<TeamInfo> informationOfTeamsParameterValue,
                                 ArrayList<Team> teamsParameterValue,
                                 PlayerManager playerProfileManagerServiceParameterValue) {
        if (informationOfTeamsParameterValue == null || teamsParameterValue == null) {
            tableModelFieldReference.setDataVector(new Object[0][COLUMN_NAMES.length], COLUMN_NAMES);
            return;
        }
        List<TeamInfo> sortedTeamsLocalVariableValue = new ArrayList<>(informationOfTeamsParameterValue);
        sortedTeamsLocalVariableValue.sort(Comparator.comparingInt(TeamInfo::getPoints).reversed());
        Object[][] rowsLocalVariableValue = new Object[sortedTeamsLocalVariableValue.size()][COLUMN_NAMES.length];
        for (int indexCounterLocalVariableValue = 0; indexCounterLocalVariableValue < sortedTeamsLocalVariableValue.size(); indexCounterLocalVariableValue++) {
            TeamInfo infoLocalVariableValue = sortedTeamsLocalVariableValue.get(indexCounterLocalVariableValue);
            String teamNameLocalVariableValue = resolveTeamName(infoLocalVariableValue.getTeamId(), teamsParameterValue);
            rowsLocalVariableValue[indexCounterLocalVariableValue][0] = indexCounterLocalVariableValue + 1;
            rowsLocalVariableValue[indexCounterLocalVariableValue][1] = teamNameLocalVariableValue;
            rowsLocalVariableValue[indexCounterLocalVariableValue][2] = playerProfileManagerServiceParameterValue == null ? 0 : playerProfileManagerServiceParameterValue.getNumberOfPlayersByTeamName(teamNameLocalVariableValue);
            rowsLocalVariableValue[indexCounterLocalVariableValue][3] = infoLocalVariableValue.getWins();
            rowsLocalVariableValue[indexCounterLocalVariableValue][4] = infoLocalVariableValue.getTies();
            rowsLocalVariableValue[indexCounterLocalVariableValue][5] = infoLocalVariableValue.getDefeats();
            rowsLocalVariableValue[indexCounterLocalVariableValue][6] = infoLocalVariableValue.getPoints();
        }
        tableModelFieldReference.setDataVector(rowsLocalVariableValue, COLUMN_NAMES);
    }

    private String resolveTeamName(int teamIdParameterValue, ArrayList<Team> teamsParameterValue) {
        for (Team teamReferenceLocalVariableValue : teamsParameterValue) {
            if (teamReferenceLocalVariableValue.getId() == teamIdParameterValue) {
                return teamReferenceLocalVariableValue.getName();
            }
        }
        return "Team " + teamIdParameterValue;
    }

    public void registerController(ActionListener controllerHandlerParameterValue) {
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.setActionCommand(BACK);
        configButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        configButtonFieldReference.setActionCommand(CONFIG);
        statsButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        calendarButtonFieldReference.addActionListener(controllerHandlerParameterValue);
    }
}
