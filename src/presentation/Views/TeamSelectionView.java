package presentation.Views;

import bussines.objects.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** Selección de equipos como JPanel reutilizable. */
public class TeamSelectionView extends JPanel {
    public static final String CREATE_LEAGUE = "CREATE_LEAGUE";
    public static final String BACK = "BACK";

    private JButton createLeagueReferenceButtonFieldReference;
    private JPanel availableTeamsPanelFieldReference;
    private JPanel addedTeamsPanelFieldReference;
    private JButton backButtonFieldReference;
    private ActionListener createControllerHandlerFieldReference;

    public TeamSelectionView() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel headerPanelLocalVariableValue = new JPanel(new BorderLayout());
        headerPanelLocalVariableValue.setBackground(new Color(20, 40, 70));
        headerPanelLocalVariableValue.setPreferredSize(new Dimension(800, 50));
        backButtonFieldReference = new JButton("←");
        backButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 20));
        backButtonFieldReference.setForeground(Color.WHITE);
        backButtonFieldReference.setBackground(new Color(20, 40, 70));
        backButtonFieldReference.setBorderPainted(false);
        backButtonFieldReference.setFocusPainted(false);
        backButtonFieldReference.setContentAreaFilled(false);
        backButtonFieldReference.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButtonFieldReference.setActionCommand(BACK);
        JLabel titleLabelLocalVariableValue = new JLabel("AVAILABLE TEAMS", SwingConstants.CENTER);
        titleLabelLocalVariableValue.setForeground(Color.WHITE);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanelLocalVariableValue.add(backButtonFieldReference, BorderLayout.WEST);
        headerPanelLocalVariableValue.add(titleLabelLocalVariableValue, BorderLayout.CENTER);
        add(headerPanelLocalVariableValue, BorderLayout.NORTH);

        JPanel mainPanelLocalVariableValue = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        availableTeamsPanelFieldReference = new JPanel(new GridLayout(0, 1, 5, 5));
        addedTeamsPanelFieldReference = new JPanel(new GridLayout(0, 1, 5, 5));
        JScrollPane availableScrollPaneLocalVariableValue = new JScrollPane(availableTeamsPanelFieldReference);
        availableScrollPaneLocalVariableValue.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        availableScrollPaneLocalVariableValue.setBorder(BorderFactory.createTitledBorder("Available Teams"));
        JScrollPane addedScrollPaneLocalVariableValue = new JScrollPane(addedTeamsPanelFieldReference);
        addedScrollPaneLocalVariableValue.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        addedScrollPaneLocalVariableValue.setBorder(BorderFactory.createTitledBorder("Teams Added"));
        mainPanelLocalVariableValue.add(availableScrollPaneLocalVariableValue);
        mainPanelLocalVariableValue.add(addedScrollPaneLocalVariableValue);

        createLeagueReferenceButtonFieldReference = new Rounded.RoundedButton("CREATE LEAGUE", 15);
        styleButton(createLeagueReferenceButtonFieldReference);
        createLeagueReferenceButtonFieldReference.setPreferredSize(new Dimension(200, 40));
        createLeagueReferenceButtonFieldReference.setActionCommand(CREATE_LEAGUE);
        JPanel buttonPanelLocalVariableValue = new JPanel();
        buttonPanelLocalVariableValue.add(createLeagueReferenceButtonFieldReference);

        add(mainPanelLocalVariableValue, BorderLayout.CENTER);
        add(buttonPanelLocalVariableValue, BorderLayout.SOUTH);
    }

    public TeamSelectionView(ArrayList<Team> teamsParameterValue) {
        this();
        loadAvailableTeams(teamsParameterValue);
    }

    public void loadAvailableTeams(ArrayList<Team> teamsParameterValue) {
        availableTeamsPanelFieldReference.removeAll();
        addedTeamsPanelFieldReference.removeAll();
        if (teamsParameterValue != null) {
            for (Team teamReferenceLocalVariableValue : teamsParameterValue) {
                JButton teamReferenceButtonLocalVariableValue = new Rounded.RoundedButton(teamReferenceLocalVariableValue.getName(), 15);
                teamReferenceButtonLocalVariableValue.putClientProperty("team", teamReferenceLocalVariableValue);
                styleButton(teamReferenceButtonLocalVariableValue);
                availableTeamsPanelFieldReference.add(teamReferenceButtonLocalVariableValue);
                teamReferenceButtonLocalVariableValue.addActionListener(eventArgumentParameterValue -> {
                    JButton buttonControlLocalVariableValue = (JButton) eventArgumentParameterValue.getSource();
                    toggleTeam(buttonControlLocalVariableValue, availableTeamsPanelFieldReference, addedTeamsPanelFieldReference);
                });
            }
        }
        availableTeamsPanelFieldReference.revalidate();
        addedTeamsPanelFieldReference.revalidate();
        availableTeamsPanelFieldReference.repaint();
        addedTeamsPanelFieldReference.repaint();
    }

    public JPanel getAddedTeamsPanel() { return addedTeamsPanelFieldReference; }

    public ArrayList<String> getSelectedTeamNames() {
        ArrayList<String> namesLocalVariableValue = new ArrayList<>();
        for (Component componentLocalVariableValue : addedTeamsPanelFieldReference.getComponents()) {
            if (componentLocalVariableValue instanceof JButton) {
                namesLocalVariableValue.add(((JButton) componentLocalVariableValue).getText());
            }
        }
        return namesLocalVariableValue;
    }

    public void registerController(ActionListener controllerHandlerParameterValue) {
        this.createControllerHandlerFieldReference = controllerHandlerParameterValue;
        createLeagueReferenceButtonFieldReference.addActionListener(controllerHandlerParameterValue);
    }

    private void styleButton(JButton buttonParameterValue) {
        buttonParameterValue.setBackground(new Color(0, 30, 60));
        buttonParameterValue.setForeground(Color.WHITE);
        buttonParameterValue.setFont(new Font("Arial", Font.BOLD, 14));
        buttonParameterValue.setFocusPainted(false);
        buttonParameterValue.setOpaque(true);
        buttonParameterValue.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        buttonParameterValue.setContentAreaFilled(true);
        buttonParameterValue.setBorderPainted(true);
    }

    private void toggleTeam(JButton buttonControlParameterValue, JPanel fromParameterValue, JPanel toParameterValue) {
        fromParameterValue.remove(buttonControlParameterValue);
        toParameterValue.add(buttonControlParameterValue);
        styleButton(buttonControlParameterValue);
        fromParameterValue.revalidate();
        fromParameterValue.repaint();
        toParameterValue.revalidate();
        toParameterValue.repaint();
        for (ActionListener listenerLocalVariableValue : buttonControlParameterValue.getActionListeners()) {
            buttonControlParameterValue.removeActionListener(listenerLocalVariableValue);
        }
        buttonControlParameterValue.addActionListener(eventArgumentParameterValue -> toggleTeam(buttonControlParameterValue, toParameterValue, fromParameterValue));
    }

    public void setBackButtonListener(ActionListener listenerParameterValue) {
        backButtonFieldReference.addActionListener(listenerParameterValue);
    }

    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), messageParameterValue, "Team selection", JOptionPane.WARNING_MESSAGE);
    }
}
