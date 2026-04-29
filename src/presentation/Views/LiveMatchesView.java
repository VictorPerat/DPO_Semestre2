package presentation.Views;

import presentation.ControllerViews.LiveMatchesController;
import shared.ProjectPathResolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/** Lista de partidos en directo como JPanel. */
public class LiveMatchesView extends JPanel {
    private static final Color DARK_BLUE = new Color(22, 49, 72);
    private static final Color LIGHT_BLUE = new Color(195, 216, 236);
    private static final Color BACKGROUND = new Color(240, 240, 240);
    private static final Color LIVE_RED = new Color(200, 0, 0);
    private JButton refreshButtonFieldReference;
    private JPanel matchesPanelFieldReference;
    private ActionListener configControllerHandlerFieldReference;
    private ActionListener matchClickListenerFieldReference;
    private ActionListener backControllerHandlerFieldReference;
    private Timer autoRefreshTimerFieldReference;
    public static final String CONFIG = "CONFIG";

    public LiveMatchesView() { this(new ArrayList<>()); }

    public LiveMatchesView(List<String[]> matchesParameterValue) {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND);
        JPanel mainPanelLocalVariableValue = new JPanel(new BorderLayout());
        mainPanelLocalVariableValue.setBackground(BACKGROUND);
        mainPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanelLocalVariableValue.add(createTitlePanel(), BorderLayout.NORTH);
        mainPanelLocalVariableValue.add(createMatchesPanel(), BorderLayout.CENTER);
        add(mainPanelLocalVariableValue, BorderLayout.CENTER);
        initRefreshButton();
        updateMatches(matchesParameterValue);
    }

    public void loadLiveGames(List<String[]> matchesParameterValue) { updateMatches(matchesParameterValue); }

    public void setBackButtonListener(ActionListener listenerParameterValue) { this.backControllerHandlerFieldReference = listenerParameterValue; }

    private void initRefreshButton() { refreshButtonFieldReference = new JButton("Update matches"); }
    public JButton getRefreshButton() { return refreshButtonFieldReference; }

    public void updateMatches(List<String[]> matchesParameterValue) {
        matchesPanelFieldReference.removeAll();
        List<String[]> matchesLocalVariableValue = matchesParameterValue == null ? new ArrayList<>() : matchesParameterValue;
        if (matchesLocalVariableValue.isEmpty()) {
            JLabel emptyLocalVariableValue = new JLabel("There are no live matches at the moment.", SwingConstants.CENTER);
            emptyLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
            matchesPanelFieldReference.add(emptyLocalVariableValue);
        }
        for (String[] teamsLocalVariableValue : matchesLocalVariableValue) {
            matchesPanelFieldReference.add(createMatchPanel(teamsLocalVariableValue));
            matchesPanelFieldReference.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        matchesPanelFieldReference.revalidate();
        matchesPanelFieldReference.repaint();
    }

    private JPanel createTitlePanel() {
        JPanel panelLocalVariableValue = new JPanel(new BorderLayout());
        panelLocalVariableValue.setBackground(DARK_BLUE);
        panelLocalVariableValue.setPreferredSize(new Dimension(800, 60));
        JButton backButtonLocalVariableValue = Rounded.HeaderButtonHelper.createBackButton(eventArgumentParameterValue -> {
            if (backControllerHandlerFieldReference != null) {
                backControllerHandlerFieldReference.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "BACK"));
            }
        });
        panelLocalVariableValue.add(backButtonLocalVariableValue, BorderLayout.WEST);

        ImageIcon ballIconLocalVariableValue = new ImageIcon(ProjectPathResolver.resolveProjectPath("photos/football.png"));
        Image scaledLocalVariableValue = ballIconLocalVariableValue.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JPanel centerPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanelLocalVariableValue.setOpaque(false);
        centerPanelLocalVariableValue.add(new JLabel(new ImageIcon(scaledLocalVariableValue)));
        JLabel titleLocalVariableValue = new JLabel("LIVE MATCHES");
        titleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 18));
        titleLocalVariableValue.setForeground(Color.WHITE);
        centerPanelLocalVariableValue.add(titleLocalVariableValue);
        centerPanelLocalVariableValue.add(new JLabel(new ImageIcon(scaledLocalVariableValue)));
        panelLocalVariableValue.add(centerPanelLocalVariableValue, BorderLayout.CENTER);

        JButton configButtonLocalVariableValue = Rounded.HeaderButtonHelper.createConfigButton(eventArgumentParameterValue -> {
            if (configControllerHandlerFieldReference != null) {
                configControllerHandlerFieldReference.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CONFIG));
            }
        });
        panelLocalVariableValue.add(configButtonLocalVariableValue, BorderLayout.EAST);
        return panelLocalVariableValue;
    }

    private JScrollPane createMatchesPanel() {
        JPanel containerLocalVariableValue = new JPanel(new BorderLayout());
        containerLocalVariableValue.setBackground(BACKGROUND);
        matchesPanelFieldReference = new JPanel();
        matchesPanelFieldReference.setLayout(new BoxLayout(matchesPanelFieldReference, BoxLayout.Y_AXIS));
        matchesPanelFieldReference.setBackground(BACKGROUND);
        matchesPanelFieldReference.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JPanel blueContainerLocalVariableValue = new JPanel(new BorderLayout());
        blueContainerLocalVariableValue.setBackground(LIGHT_BLUE);
        blueContainerLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        blueContainerLocalVariableValue.add(matchesPanelFieldReference, BorderLayout.CENTER);
        containerLocalVariableValue.add(blueContainerLocalVariableValue, BorderLayout.CENTER);
        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(containerLocalVariableValue);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        return scrollPaneLocalVariableValue;
    }

    private JPanel createMatchPanel(String[] teamsParameterValue) {
        JPanel matchPanelLocalVariableValue = new JPanel(new BorderLayout());
        matchPanelLocalVariableValue.setBackground(Color.WHITE);
        matchPanelLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT_BLUE, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        matchPanelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        JLabel liveLabelLocalVariableValue = new JLabel("LIVE");
        liveLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 12));
        liveLabelLocalVariableValue.setForeground(LIVE_RED);
        liveLabelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        matchPanelLocalVariableValue.add(liveLabelLocalVariableValue, BorderLayout.WEST);
        String homeLocalVariableValue = teamsParameterValue.length > 0 ? teamsParameterValue[0] : "Home";
        String awayLocalVariableValue = teamsParameterValue.length > 1 ? teamsParameterValue[1] : "Away";
        JLabel teamsLabelLocalVariableValue = new JLabel(homeLocalVariableValue + "  VS  " + awayLocalVariableValue, SwingConstants.CENTER);
        teamsLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 16));
        matchPanelLocalVariableValue.add(teamsLabelLocalVariableValue, BorderLayout.CENTER);
        matchPanelLocalVariableValue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        matchPanelLocalVariableValue.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent eventArgumentParameterValue) {
                if (matchClickListenerFieldReference != null) {
                    matchClickListenerFieldReference.actionPerformed(new ActionEvent(teamsParameterValue, ActionEvent.ACTION_PERFORMED, "MATCH_CLICK"));
                }
            }
        });
        return matchPanelLocalVariableValue;
    }

    public void startAutoRefresh(LiveMatchesController matchesControllerHandlerParameterValue) {
        stopAutoRefresh();
        autoRefreshTimerFieldReference = new Timer(3000, eventArgumentParameterValue -> updateMatches(matchesControllerHandlerParameterValue.getterLiveGames()));
        autoRefreshTimerFieldReference.start();
    }

    public void stopAutoRefresh() {
        if (autoRefreshTimerFieldReference != null) {
            autoRefreshTimerFieldReference.stop();
            autoRefreshTimerFieldReference = null;
        }
    }

    public void setMatchClickListener(ActionListener listenerParameterValue) { this.matchClickListenerFieldReference = listenerParameterValue; }
    public void setConfigController(ActionListener controllerHandlerParameterValue) { this.configControllerHandlerFieldReference = controllerHandlerParameterValue; }
    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), messageParameterValue, "VIEW_GAMES", JOptionPane.WARNING_MESSAGE);
    }
}
