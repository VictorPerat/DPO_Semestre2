package presentation.Views;

import bussines.objects.League;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Gráfico de estadísticas como JPanel. */
public class StatisticsGraphView extends JPanel {
    private JPanel topPanelFieldReference;
    private JLabel titleLabelFieldReference;
    private LeagueManagerChartPanelComponent chartPanelFieldReference;
    private JButton backButtonFieldReference;
    private JButton configButtonFieldReference;
    private String[] teamReferenceNamesFieldReference = new String[0];
    private int countIdentifiersFieldReference = 0;

    public StatisticsGraphView() {
        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245));
        topPanelFieldReference = new JPanel();
        topPanelFieldReference.setLayout(new BoxLayout(topPanelFieldReference, BoxLayout.Y_AXIS));
        topPanelFieldReference.setBackground(new Color(245,245,245));

        JPanel headerPanelLocalVariableValue = new JPanel(new BorderLayout());
        headerPanelLocalVariableValue.setBackground(new Color(22, 49, 72));
        headerPanelLocalVariableValue.setPreferredSize(new Dimension(800, 60));
        backButtonFieldReference = Rounded.HeaderButtonHelper.createBackButton(null);
        configButtonFieldReference = Rounded.HeaderButtonHelper.createConfigButton(null);
        titleLabelFieldReference = new JLabel("STATISTICS", SwingConstants.CENTER);
        titleLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabelFieldReference.setForeground(Color.WHITE);
        headerPanelLocalVariableValue.add(backButtonFieldReference, BorderLayout.WEST);
        headerPanelLocalVariableValue.add(titleLabelFieldReference, BorderLayout.CENTER);
        headerPanelLocalVariableValue.add(configButtonFieldReference, BorderLayout.EAST);
        topPanelFieldReference.add(headerPanelLocalVariableValue);

        chartPanelFieldReference = new LeagueManagerChartPanelComponent();
        chartPanelFieldReference.setPreferredSize(new Dimension(900, 500));
        add(topPanelFieldReference, BorderLayout.NORTH);
        add(chartPanelFieldReference, BorderLayout.CENTER);
    }

    public void updateChartData(String leagueReferenceDisplayNameParameterValue,
                                int[][] teamReferenceDataParameterValue,
                                int numberWeeksParameterValue,
                                String[] teamReferenceIdentifierParameterValue,
                                int countIdentifiersParameterValue) {
        titleLabelFieldReference.setText("STATISTICS - " + leagueReferenceDisplayNameParameterValue);
        this.teamReferenceNamesFieldReference = teamReferenceIdentifierParameterValue == null ? new String[0] : teamReferenceIdentifierParameterValue;
        this.countIdentifiersFieldReference = countIdentifiersParameterValue;
        chartPanelFieldReference.updateData(teamReferenceDataParameterValue, numberWeeksParameterValue);
    }

    public void registerController(ActionListener controllerHandlerParameterValue) {
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.setActionCommand("BACK");
        configButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        configButtonFieldReference.setActionCommand("CONFIG");
    }

    public void setLeagues(List<League> leaguesParameterValue, ActionListener listenerParameterValue) {
        JPanel leaguesPanelLocalVariableValue = createLeaguesPanel(leaguesParameterValue, listenerParameterValue);
        while (topPanelFieldReference.getComponentCount() > 1) {
            topPanelFieldReference.remove(1);
        }
        topPanelFieldReference.add(leaguesPanelLocalVariableValue);
        topPanelFieldReference.revalidate();
        topPanelFieldReference.repaint();
    }

    private JPanel createLeaguesPanel(List<League> leaguesParameterValue, ActionListener listenerParameterValue) {
        JPanel panelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        panelLocalVariableValue.setBackground(new Color(245,245,245));
        List<League> leaguesLocalVariableValue = leaguesParameterValue == null ? new ArrayList<>() : leaguesParameterValue;
        for (int indexCounterLocalVariableValue = 0; indexCounterLocalVariableValue < leaguesLocalVariableValue.size(); indexCounterLocalVariableValue++) {
            League leagueReferenceLocalVariableValue = leaguesLocalVariableValue.get(indexCounterLocalVariableValue);
            JButton buttonLocalVariableValue = new JButton(leagueReferenceLocalVariableValue.getName());
            buttonLocalVariableValue.setActionCommand("LEAGUE_" + indexCounterLocalVariableValue);
            buttonLocalVariableValue.addActionListener(listenerParameterValue);
            panelLocalVariableValue.add(buttonLocalVariableValue);
        }
        return panelLocalVariableValue;
    }

    public void updateData(int[][] teamReferencePointsParameterValue, int currentWeekParameterValue) {
        chartPanelFieldReference.updateData(teamReferencePointsParameterValue, currentWeekParameterValue);
    }

    class LeagueManagerChartPanelComponent extends JPanel {
        private int[][] teamReferencePointsFieldReference;
        private int currentWeekFieldReference;
        private final Color[] teamReferenceColorsFieldReference = {
                Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.CYAN,
                Color.PINK, Color.GRAY, new Color(75, 0, 130), new Color(46, 139, 87)
        };

        public void updateData(int[][] teamReferencePointsParameterValue, int currentWeekParameterValue) {
            this.teamReferencePointsFieldReference = teamReferencePointsParameterValue;
            this.currentWeekFieldReference = currentWeekParameterValue;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphicsParameterValue) {
            super.paintComponent(graphicsParameterValue);
            if (teamReferencePointsFieldReference == null || teamReferencePointsFieldReference.length == 0 || currentWeekFieldReference < 1) {
                graphicsParameterValue.drawString("No statistics available", 40, 40);
                return;
            }
            int widthLocalVariableValue = getWidth();
            int heightLocalVariableValue = getHeight();
            int paddingLocalVariableValue = 60;
            int legendHeightLocalVariableValue = 60;
            int chartWidthLocalVariableValue = Math.max(1, widthLocalVariableValue - 2 * paddingLocalVariableValue);
            int chartHeightLocalVariableValue = Math.max(1, heightLocalVariableValue - 2 * paddingLocalVariableValue - legendHeightLocalVariableValue);
            int maxYLocalVariableValue = Arrays.stream(teamReferencePointsFieldReference).flatMapToInt(Arrays::stream).max().orElse(1);
            maxYLocalVariableValue = Math.max(1, maxYLocalVariableValue);

            graphicsParameterValue.setColor(Color.BLACK);
            graphicsParameterValue.drawLine(paddingLocalVariableValue, heightLocalVariableValue - paddingLocalVariableValue - legendHeightLocalVariableValue, paddingLocalVariableValue, paddingLocalVariableValue);
            graphicsParameterValue.drawLine(paddingLocalVariableValue, heightLocalVariableValue - paddingLocalVariableValue - legendHeightLocalVariableValue, widthLocalVariableValue - paddingLocalVariableValue, heightLocalVariableValue - paddingLocalVariableValue - legendHeightLocalVariableValue);

            double xScaleLocalVariableValue = currentWeekFieldReference > 1 ? (double) chartWidthLocalVariableValue / (currentWeekFieldReference - 1) : 0;
            double yScaleLocalVariableValue = (double) chartHeightLocalVariableValue / maxYLocalVariableValue;
            for (int teamIndexLocalVariableValue = 0; teamIndexLocalVariableValue < Math.min(countIdentifiersFieldReference, teamReferencePointsFieldReference.length); teamIndexLocalVariableValue++) {
                graphicsParameterValue.setColor(teamReferenceColorsFieldReference[teamIndexLocalVariableValue % teamReferenceColorsFieldReference.length]);
                for (int weekLocalVariableValue = 1; weekLocalVariableValue < Math.min(currentWeekFieldReference, teamReferencePointsFieldReference[teamIndexLocalVariableValue].length); weekLocalVariableValue++) {
                    int x1LocalVariableValue = paddingLocalVariableValue + (int)((weekLocalVariableValue - 1) * xScaleLocalVariableValue);
                    int y1LocalVariableValue = heightLocalVariableValue - paddingLocalVariableValue - legendHeightLocalVariableValue - (int)(teamReferencePointsFieldReference[teamIndexLocalVariableValue][weekLocalVariableValue - 1] * yScaleLocalVariableValue);
                    int x2LocalVariableValue = paddingLocalVariableValue + (int)(weekLocalVariableValue * xScaleLocalVariableValue);
                    int y2LocalVariableValue = heightLocalVariableValue - paddingLocalVariableValue - legendHeightLocalVariableValue - (int)(teamReferencePointsFieldReference[teamIndexLocalVariableValue][weekLocalVariableValue] * yScaleLocalVariableValue);
                    graphicsParameterValue.drawLine(x1LocalVariableValue, y1LocalVariableValue, x2LocalVariableValue, y2LocalVariableValue);
                }
                String labelLocalVariableValue = teamIndexLocalVariableValue < teamReferenceNamesFieldReference.length ? teamReferenceNamesFieldReference[teamIndexLocalVariableValue] : "Team " + (teamIndexLocalVariableValue + 1);
                graphicsParameterValue.drawString(labelLocalVariableValue, paddingLocalVariableValue + 120 * (teamIndexLocalVariableValue % 5), heightLocalVariableValue - 25 - 15 * (teamIndexLocalVariableValue / 5));
            }
        }
    }
}
