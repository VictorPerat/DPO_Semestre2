package presentation.Views;

import bussines.objects.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/** Calendario de liga como tarjeta CardLayout. */
public class CalendarView extends JPanel {
    private ArrayList<String> teamsFieldReference = new ArrayList<>();
    private ArrayList<Game> gamesFieldReference = new ArrayList<>();

    private static final Color DARK_BLUE = new Color(22, 49, 72);
    private static final Color LIGHT_BLUE = new Color(195, 216, 236);
    private static final Color BACKGROUND = new Color(240, 240, 240);
    private static final Color DIVIDER_COLOR = new Color(150, 150, 150);
    public static final String BACK = "BACK";
    public static final String CONFIG = "CONFIG";

    private JButton backButtonFieldReference;
    private JButton configButtonFieldReference;
    private JPanel roundsPanelFieldReference;
    private JPanel matchesPanelFieldReference;
    private JLabel roundTitleLabelFieldReference;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public CalendarView() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND);
        buildLayout();
    }

    public CalendarView(ArrayList<String> teamsParameterValue, ArrayList<Game> gamesParameterValue) {
        this();
        loadCalendar(teamsParameterValue, gamesParameterValue);
    }

    private void buildLayout() {
        JPanel mainPanelLocalVariableValue = new JPanel(new BorderLayout(0, 10));
        mainPanelLocalVariableValue.setBackground(BACKGROUND);
        mainPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanelLocalVariableValue.add(createTitlePanel(), BorderLayout.NORTH);

        roundsPanelFieldReference = new JPanel();
        roundsPanelFieldReference.setLayout(new BoxLayout(roundsPanelFieldReference, BoxLayout.X_AXIS));
        roundsPanelFieldReference.setBackground(LIGHT_BLUE);
        JScrollPane roundsScrollPaneLocalVariableValue = new JScrollPane(roundsPanelFieldReference);
        roundsScrollPaneLocalVariableValue.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        roundsScrollPaneLocalVariableValue.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        roundsScrollPaneLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        roundsScrollPaneLocalVariableValue.setPreferredSize(new Dimension(800, 80));
        mainPanelLocalVariableValue.add(roundsScrollPaneLocalVariableValue, BorderLayout.CENTER);

        JPanel matchesContainerLocalVariableValue = new JPanel(new BorderLayout());
        matchesContainerLocalVariableValue.setBackground(BACKGROUND);
        JPanel matchesHeaderLocalVariableValue = new JPanel(new BorderLayout());
        matchesHeaderLocalVariableValue.setBackground(DARK_BLUE);
        matchesHeaderLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        roundTitleLabelFieldReference = new JLabel("MATCHES", SwingConstants.LEFT);
        roundTitleLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 20));
        roundTitleLabelFieldReference.setForeground(Color.WHITE);
        matchesHeaderLocalVariableValue.add(roundTitleLabelFieldReference, BorderLayout.CENTER);
        matchesContainerLocalVariableValue.add(matchesHeaderLocalVariableValue, BorderLayout.NORTH);

        matchesPanelFieldReference = new JPanel();
        matchesPanelFieldReference.setLayout(new BoxLayout(matchesPanelFieldReference, BoxLayout.Y_AXIS));
        matchesPanelFieldReference.setBackground(BACKGROUND);
        matchesPanelFieldReference.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        JScrollPane matchesScrollLocalVariableValue = new JScrollPane(matchesPanelFieldReference);
        matchesScrollLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        matchesScrollLocalVariableValue.getViewport().setBackground(BACKGROUND);
        matchesContainerLocalVariableValue.add(matchesScrollLocalVariableValue, BorderLayout.CENTER);
        mainPanelLocalVariableValue.add(matchesContainerLocalVariableValue, BorderLayout.SOUTH);
        add(mainPanelLocalVariableValue, BorderLayout.CENTER);
    }

    private JPanel createTitlePanel() {
        JPanel panelLocalVariableValue = new JPanel(new BorderLayout());
        panelLocalVariableValue.setBackground(DARK_BLUE);
        panelLocalVariableValue.setPreferredSize(new Dimension(800, 70));
        backButtonFieldReference = Rounded.HeaderButtonHelper.createBackButton(null);
        configButtonFieldReference = Rounded.HeaderButtonHelper.createConfigButton(null);
        JLabel titleLocalVariableValue = new JLabel("LEAGUE CALENDAR", SwingConstants.CENTER);
        titleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 28));
        titleLocalVariableValue.setForeground(Color.WHITE);
        panelLocalVariableValue.add(backButtonFieldReference, BorderLayout.WEST);
        panelLocalVariableValue.add(titleLocalVariableValue, BorderLayout.CENTER);
        panelLocalVariableValue.add(configButtonFieldReference, BorderLayout.EAST);
        return panelLocalVariableValue;
    }

    public void loadCalendar(ArrayList<String> teamsParameterValue, ArrayList<Game> gamesParameterValue) {
        teamsFieldReference = teamsParameterValue == null ? new ArrayList<>() : teamsParameterValue;
        gamesFieldReference = gamesParameterValue == null ? new ArrayList<>() : gamesParameterValue;
        rebuildRounds();
        if (!gamesFieldReference.isEmpty()) {
            showMatchesForRound(gamesFieldReference.get(0).getJornada());
        } else {
            showMatchesForRound(1);
        }
    }

    private void rebuildRounds() {
        roundsPanelFieldReference.removeAll();
        int totalRoundsLocalVariableValue = 0;
        for (Game gameEntityLocalVariableValue : gamesFieldReference) {
            totalRoundsLocalVariableValue = Math.max(totalRoundsLocalVariableValue, gameEntityLocalVariableValue.getJornada());
        }
        if (totalRoundsLocalVariableValue == 0 && teamsFieldReference.size() > 1) {
            totalRoundsLocalVariableValue = 2 * (teamsFieldReference.size() - 1);
        }
        for (int indexCounterLocalVariableValue = 1; indexCounterLocalVariableValue <= totalRoundsLocalVariableValue; indexCounterLocalVariableValue++) {
            JButton roundButtonLocalVariableValue = new JButton("ROUND " + indexCounterLocalVariableValue);
            roundButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 14));
            roundButtonLocalVariableValue.setForeground(DARK_BLUE);
            roundButtonLocalVariableValue.setBackground(Color.WHITE);
            roundButtonLocalVariableValue.setFocusPainted(false);
            roundButtonLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(DARK_BLUE, 1),
                    BorderFactory.createEmptyBorder(5, 12, 5, 12)
            ));
            if (indexCounterLocalVariableValue > 1) {
                roundsPanelFieldReference.add(Box.createRigidArea(new Dimension(8, 0)));
            }
            final int roundNumericValueLocalVariableValue = indexCounterLocalVariableValue;
            roundButtonLocalVariableValue.addActionListener(eventArgumentParameterValue -> showMatchesForRound(roundNumericValueLocalVariableValue));
            roundsPanelFieldReference.add(roundButtonLocalVariableValue);
        }
        roundsPanelFieldReference.revalidate();
        roundsPanelFieldReference.repaint();
    }

    private void showMatchesForRound(int roundNumericValueParameterValue) {
        matchesPanelFieldReference.removeAll();
        roundTitleLabelFieldReference.setText("MATCHES - ROUND " + roundNumericValueParameterValue);
        String[][] roundMatchesLocalVariableValue = generateMatchesForRound(roundNumericValueParameterValue);
        if (roundMatchesLocalVariableValue.length == 0) {
            JLabel emptyLabelLocalVariableValue = new JLabel("No matches for this round.");
            emptyLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
            matchesPanelFieldReference.add(emptyLabelLocalVariableValue);
        }
        for (String[] matchInformationLocalVariableValue : roundMatchesLocalVariableValue) {
            JPanel matchPanelLocalVariableValue = new JPanel(new BorderLayout());
            matchPanelLocalVariableValue.setBackground(Color.WHITE);
            matchPanelLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, DIVIDER_COLOR),
                    BorderFactory.createEmptyBorder(12, 15, 12, 15)
            ));
            matchPanelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
            JLabel teamsLabelLocalVariableValue = new JLabel(matchInformationLocalVariableValue[0]);
            teamsLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 16));
            JLabel detailsLabelLocalVariableValue = new JLabel(matchInformationLocalVariableValue[1]);
            detailsLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 14));
            detailsLabelLocalVariableValue.setForeground(new Color(100, 100, 100));
            JPanel leftPanelLocalVariableValue = new JPanel(new BorderLayout());
            leftPanelLocalVariableValue.setBackground(Color.WHITE);
            leftPanelLocalVariableValue.add(teamsLabelLocalVariableValue, BorderLayout.NORTH);
            leftPanelLocalVariableValue.add(detailsLabelLocalVariableValue, BorderLayout.SOUTH);
            matchPanelLocalVariableValue.add(leftPanelLocalVariableValue, BorderLayout.CENTER);
            matchesPanelFieldReference.add(matchPanelLocalVariableValue);
        }
        matchesPanelFieldReference.revalidate();
        matchesPanelFieldReference.repaint();
    }

    private String[][] generateMatchesForRound(int roundNumericValueParameterValue) {
        List<String[]> roundMatchesLocalVariableValue = new ArrayList<>();
        for (Game gameEntityLocalVariableValue : gamesFieldReference) {
            if (gameEntityLocalVariableValue.getJornada() == roundNumericValueParameterValue) {
                roundMatchesLocalVariableValue.add(createMatch(gameEntityLocalVariableValue));
            }
        }
        return roundMatchesLocalVariableValue.toArray(new String[0][]);
    }

    private String[] createMatch(Game gameEntityParameterValue) {
        String dateTimeLocalVariableValue = gameEntityParameterValue.getData() == null ? "" : gameEntityParameterValue.getData().format(DATE_TIME_FORMATTER);
        return new String[] {
                gameEntityParameterValue.getNomLocal() + " vs " + gameEntityParameterValue.getNomVisitant(),
                dateTimeLocalVariableValue
        };
    }

    public void registerController(ActionListener controllerHandlerParameterValue) {
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.setActionCommand(BACK);
        configButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        configButtonFieldReference.setActionCommand(CONFIG);
    }
}
