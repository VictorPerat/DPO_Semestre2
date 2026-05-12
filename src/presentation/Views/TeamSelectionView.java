package presentation.Views;

import bussines.objects.Team;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Representa la vista del equipo.
 */
public class TeamSelectionView extends JPanel {

    /**
     * Constante para el liga.
     */
    public static final String CREATE_LEAGUE = "CREATE_LEAGUE";

    /**
     * Constante para el vuelta.
     */
    public static final String BACK = "BACK";

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);
    private static final Color CARD_TITLE_COLOR = new Color(28, 35, 51);
    private static final Color CARD_BODY_COLOR = new Color(100, 112, 135);
    private static final Color FIELD_BACKGROUND = new Color(245, 249, 255);
    private static final Color FIELD_BORDER = new Color(190, 210, 235);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");
    private static final String TEAM_ICON_PATH =
            ProjectPathResolver.resolveProjectPath("photos/create_team.png");

    private JPanel availableTeamsPanelFieldReference;
    private JPanel addedTeamsPanelFieldReference;
    private Rounded.RoundedButton createLeagueButtonFieldReference;
    private ActionListener backButtonListenerFieldReference;


    /**
     * Crea una instancia de el equipo.
     */
    public TeamSelectionView() {
        setLayout(new BorderLayout());
        add(buildBackgroundPanel(), BorderLayout.CENTER);
    }


    /**
     * Crea una instancia de el equipo.
     *
     * @param teamsParameterValue equipos que usa la operacion.
     */
    public TeamSelectionView(ArrayList<Team> teamsParameterValue) {
        this();
        loadAvailableTeams(teamsParameterValue);
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildBackgroundPanel() {
        Image backgroundImageLocalVariableValue =
                new ImageIcon(BACKGROUND_IMAGE_PATH).getImage();

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

        backgroundPanelLocalVariableValue.setLayout(new BorderLayout());
        backgroundPanelLocalVariableValue.add(buildTopBar(), BorderLayout.NORTH);
        backgroundPanelLocalVariableValue.add(buildCenterContent(), BorderLayout.CENTER);

        return backgroundPanelLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildTopBar() {
        JPanel topBarLocalVariableValue = new JPanel(new BorderLayout());
        topBarLocalVariableValue.setOpaque(false);
        topBarLocalVariableValue.setBorder(new EmptyBorder(28, 40, 0, 40));

        JPanel leftPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanelLocalVariableValue.setOpaque(false);

        Rounded.RoundedButton backButtonLocalVariableValue = new Rounded.RoundedButton("< BACK", 18);
        backButtonLocalVariableValue.setActionCommand(BACK);
        backButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 15));
        backButtonLocalVariableValue.setForeground(ACCENT_COLOR);
        backButtonLocalVariableValue.setBackground(new Color(255, 255, 255, 230));
        backButtonLocalVariableValue.setOutlineMode(ACCENT_COLOR, 2);
        backButtonLocalVariableValue.setShadowEnabled(false);
        backButtonLocalVariableValue.setPreferredSize(new Dimension(145, 44));
        backButtonLocalVariableValue.addActionListener(eventArgumentParameterValue -> {
            if (backButtonListenerFieldReference != null) {
                backButtonListenerFieldReference.actionPerformed(eventArgumentParameterValue);
            }
        });

        leftPanelLocalVariableValue.add(backButtonLocalVariableValue);
        topBarLocalVariableValue.add(leftPanelLocalVariableValue, BorderLayout.WEST);

        return topBarLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildCenterContent() {
        JPanel centerWrapperLocalVariableValue = new JPanel(new GridBagLayout());
        centerWrapperLocalVariableValue.setOpaque(false);

        JPanel contentPanelLocalVariableValue = new JPanel();
        contentPanelLocalVariableValue.setOpaque(false);
        contentPanelLocalVariableValue.setLayout(
                new BoxLayout(contentPanelLocalVariableValue, BoxLayout.Y_AXIS)
        );
        contentPanelLocalVariableValue.setBorder(new EmptyBorder(0, 50, 16, 50));

        JPanel titleBlockLocalVariableValue = buildTitleBlock();
        JPanel cardLocalVariableValue = buildTeamsCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(38));
        contentPanelLocalVariableValue.add(cardLocalVariableValue);

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.weighty = 1.0;
        constraintsLocalVariableValue.anchor = GridBagConstraints.CENTER;

        centerWrapperLocalVariableValue.add(contentPanelLocalVariableValue, constraintsLocalVariableValue);

        return centerWrapperLocalVariableValue;
    }


    /**
     * Construye el titulo.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildTitleBlock() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeightLocalVariableValue = screenSizeLocalVariableValue.height;
        int mainTitleSizeLocalVariableValue =
                Math.max(58, (int) (screenHeightLocalVariableValue * 0.075));
        int subtitleSizeLocalVariableValue =
                Math.max(20, (int) (screenHeightLocalVariableValue * 0.025));

        JPanel titleContainerLocalVariableValue = new JPanel();
        titleContainerLocalVariableValue.setOpaque(false);
        titleContainerLocalVariableValue.setLayout(
                new BoxLayout(titleContainerLocalVariableValue, BoxLayout.Y_AXIS)
        );

        JPanel titleLineLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLineLocalVariableValue.setOpaque(false);

        JLabel availableLabelLocalVariableValue = new JLabel("AVAILABLE ");
        availableLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        availableLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        JLabel teamsLabelLocalVariableValue = new JLabel("TEAMS");
        teamsLabelLocalVariableValue.setForeground(TITLE_WHITE);
        teamsLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        titleLineLocalVariableValue.add(availableLabelLocalVariableValue);
        titleLineLocalVariableValue.add(teamsLabelLocalVariableValue);

        JPanel underlineWrapperLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        underlineWrapperLocalVariableValue.setOpaque(false);

        JPanel underlinePanelLocalVariableValue = new JPanel();
        underlinePanelLocalVariableValue.setBackground(ACCENT_COLOR);
        underlinePanelLocalVariableValue.setPreferredSize(new Dimension(170, 6));
        underlinePanelLocalVariableValue.setMinimumSize(new Dimension(170, 6));
        underlinePanelLocalVariableValue.setMaximumSize(new Dimension(170, 6));
        underlineWrapperLocalVariableValue.add(underlinePanelLocalVariableValue);

        JLabel subtitleLabelLocalVariableValue = new JLabel(
                "Select teams to create your league",
                SwingConstants.CENTER
        );
        subtitleLabelLocalVariableValue.setForeground(SUBTITLE_WHITE);
        subtitleLabelLocalVariableValue.setFont(
                new Font("Arial", Font.PLAIN, subtitleSizeLocalVariableValue)
        );
        subtitleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabelLocalVariableValue.setBorder(new EmptyBorder(18, 0, 0, 0));

        titleContainerLocalVariableValue.add(titleLineLocalVariableValue);
        titleContainerLocalVariableValue.add(Box.createVerticalStrut(12));
        titleContainerLocalVariableValue.add(underlineWrapperLocalVariableValue);
        titleContainerLocalVariableValue.add(subtitleLabelLocalVariableValue);

        return titleContainerLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildTeamsCard() {
        JPanel cardLocalVariableValue = new RoundedCardPanel();
        cardLocalVariableValue.setOpaque(false);
        cardLocalVariableValue.setLayout(
                new BoxLayout(cardLocalVariableValue, BoxLayout.Y_AXIS)
        );
        cardLocalVariableValue.setBorder(new EmptyBorder(20, 34, 24, 34));
        cardLocalVariableValue.setPreferredSize(new Dimension(860, 620));
        cardLocalVariableValue.setMinimumSize(new Dimension(860, 620));
        cardLocalVariableValue.setMaximumSize(new Dimension(860, 620));

        JPanel iconPanelLocalVariableValue = buildIconPanel();
        iconPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cardTitleLocalVariableValue = new JLabel("SELECT TEAMS", SwingConstants.CENTER);
        cardTitleLocalVariableValue.setForeground(CARD_TITLE_COLOR);
        cardTitleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 24));
        cardTitleLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel helperLabelLocalVariableValue = new JLabel(
                "Click a team to move it between lists",
                SwingConstants.CENTER
        );
        helperLabelLocalVariableValue.setForeground(CARD_BODY_COLOR);
        helperLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 14));
        helperLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardLocalVariableValue.add(iconPanelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(6));
        cardLocalVariableValue.add(cardTitleLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(8));
        cardLocalVariableValue.add(helperLabelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(18));

        JPanel columnsLocalVariableValue = buildTeamsColumns();
        columnsLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardLocalVariableValue.add(columnsLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(22));

        createLeagueButtonFieldReference = new Rounded.RoundedButton("CREATE LEAGUE", 18);
        createLeagueButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 15));
        createLeagueButtonFieldReference.setForeground(Color.WHITE);
        createLeagueButtonFieldReference.setGradientColors(ACCENT_COLOR, new Color(36, 80, 180));
        createLeagueButtonFieldReference.setShadowEnabled(true);
        createLeagueButtonFieldReference.setPreferredSize(new Dimension(780, 46));
        createLeagueButtonFieldReference.setMaximumSize(new Dimension(780, 46));
        createLeagueButtonFieldReference.setMinimumSize(new Dimension(780, 46));
        createLeagueButtonFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);
        createLeagueButtonFieldReference.setActionCommand(CREATE_LEAGUE);

        cardLocalVariableValue.add(createLeagueButtonFieldReference);

        return cardLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildTeamsColumns() {
        JPanel wrapperLocalVariableValue = new JPanel(new GridLayout(1, 2, 16, 0));
        wrapperLocalVariableValue.setOpaque(false);
        wrapperLocalVariableValue.setMaximumSize(new Dimension(780, 320));
        wrapperLocalVariableValue.setPreferredSize(new Dimension(780, 320));
        wrapperLocalVariableValue.setMinimumSize(new Dimension(780, 320));

        wrapperLocalVariableValue.add(buildTeamColumn("AVAILABLE TEAMS", true));
        wrapperLocalVariableValue.add(buildTeamColumn("SELECTED TEAMS", false));

        return wrapperLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @param headerParameterValue dato de entrada de la operacion.
     * @param isAvailableParameterValue dato de entrada de la operacion.
     * @return resultado de la operacion.
     */
    private JPanel buildTeamColumn(String headerParameterValue, boolean isAvailableParameterValue) {
        JPanel columnLocalVariableValue = new JPanel();
        columnLocalVariableValue.setOpaque(false);
        columnLocalVariableValue.setLayout(new BoxLayout(columnLocalVariableValue, BoxLayout.Y_AXIS));

        JLabel headerLabelLocalVariableValue =
                new JLabel(headerParameterValue, SwingConstants.CENTER);
        headerLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 13));
        headerLabelLocalVariableValue.setForeground(new Color(75, 88, 115));
        headerLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerLabelLocalVariableValue.setBorder(new EmptyBorder(0, 0, 8, 0));

        JPanel teamsListLocalVariableValue = new JPanel(new GridLayout(0, 1, 0, 6));
        teamsListLocalVariableValue.setBackground(FIELD_BACKGROUND);
        teamsListLocalVariableValue.setBorder(new EmptyBorder(10, 12, 10, 12));

        if (isAvailableParameterValue) {
            availableTeamsPanelFieldReference = teamsListLocalVariableValue;
        } else {
            addedTeamsPanelFieldReference = teamsListLocalVariableValue;
        }

        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(teamsListLocalVariableValue);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createLineBorder(FIELD_BORDER, 1));
        scrollPaneLocalVariableValue.getViewport().setBackground(FIELD_BACKGROUND);
        scrollPaneLocalVariableValue.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPaneLocalVariableValue.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        );
        scrollPaneLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        columnLocalVariableValue.add(headerLabelLocalVariableValue);
        columnLocalVariableValue.add(scrollPaneLocalVariableValue);

        return columnLocalVariableValue;
    }


    /**
     * Construye el equipo.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildIconPanel() {
        JPanel iconPanelLocalVariableValue = new JPanel(new GridBagLayout());
        iconPanelLocalVariableValue.setOpaque(false);
        iconPanelLocalVariableValue.setPreferredSize(new Dimension(100, 100));
        iconPanelLocalVariableValue.setMaximumSize(new Dimension(100, 100));
        iconPanelLocalVariableValue.setMinimumSize(new Dimension(100, 100));

        JLabel iconLabelLocalVariableValue = new JLabel();
        iconLabelLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabelLocalVariableValue.setVerticalAlignment(SwingConstants.CENTER);

        try {
            Image iconImageLocalVariableValue =
                    new ImageIcon(TEAM_ICON_PATH).getImage();
            Image scaledIconLocalVariableValue =
                    iconImageLocalVariableValue.getScaledInstance(95, 95, Image.SCALE_SMOOTH);
            iconLabelLocalVariableValue.setIcon(new ImageIcon(scaledIconLocalVariableValue));
        } catch (Exception ignoredExceptionParameterValue) {
            iconLabelLocalVariableValue.setText("T");
            iconLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 72));
        }

        iconPanelLocalVariableValue.add(iconLabelLocalVariableValue);

        return iconPanelLocalVariableValue;
    }


    /**
     * Carga los disponibles equipos.
     *
     * @param teamsParameterValue equipos que usa la operacion.
     */
    public void loadAvailableTeams(ArrayList<Team> teamsParameterValue) {
        availableTeamsPanelFieldReference.removeAll();
        addedTeamsPanelFieldReference.removeAll();
        if (teamsParameterValue != null) {
            for (Team teamLocalVariableValue : teamsParameterValue) {
                JButton btnLocalVariableValue =
                        new Rounded.RoundedButton(teamLocalVariableValue.getName(), 15);
                btnLocalVariableValue.putClientProperty("team", teamLocalVariableValue);
                styleTeamButton(btnLocalVariableValue, false);
                availableTeamsPanelFieldReference.add(btnLocalVariableValue);
                btnLocalVariableValue.addActionListener(eventArgumentParameterValue -> {
                    JButton srcLocalVariableValue =
                            (JButton) eventArgumentParameterValue.getSource();
                    toggleTeam(srcLocalVariableValue,
                            availableTeamsPanelFieldReference,
                            addedTeamsPanelFieldReference,
                            true);
                });
            }
        }
        availableTeamsPanelFieldReference.revalidate();
        addedTeamsPanelFieldReference.revalidate();
        availableTeamsPanelFieldReference.repaint();
        addedTeamsPanelFieldReference.repaint();
    }


    /**
     * Devuelve los equipos.
     *
     * @return los equipos.
     */
    public JPanel getAddedTeamsPanel() {
        return addedTeamsPanelFieldReference;
    }


    /**
     * Devuelve el equipo.
     *
     * @return el equipo.
     */
    public ArrayList<String> getSelectedTeamNames() {
        ArrayList<String> namesLocalVariableValue = new ArrayList<>();
        for (Component componentLocalVariableValue : addedTeamsPanelFieldReference.getComponents()) {
            if (componentLocalVariableValue instanceof JButton) {
                namesLocalVariableValue.add(((JButton) componentLocalVariableValue).getText());
            }
        }
        return namesLocalVariableValue;
    }


    /**
     * Registra la accion.
     *
     * @param controllerHandlerParameterValue dato de entrada de la operacion.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        createLeagueButtonFieldReference.addActionListener(controllerHandlerParameterValue);
    }


    /**
     * Actualiza el vuelta.
     *
     * @param listenerParameterValue listener que se registra.
     */
    public void setBackButtonListener(ActionListener listenerParameterValue) {
        backButtonListenerFieldReference = listenerParameterValue;
    }


    /**
     * Muestra el dialogo.
     *
     * @param messageParameterValue dato de entrada de la operacion.
     */
    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                messageParameterValue,
                "Team selection",
                JOptionPane.WARNING_MESSAGE
        );
    }


    /**
     * Gestiona esta operacion.
     *
     * @param buttonParameterValue dato de entrada de la operacion.
     * @param selectedParameterValue dato de entrada de la operacion.
     */
    private void styleTeamButton(JButton buttonParameterValue, boolean selectedParameterValue) {
        buttonParameterValue.setFont(new Font("Arial", Font.BOLD, 14));
        buttonParameterValue.setFocusPainted(false);
        buttonParameterValue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        if (buttonParameterValue instanceof Rounded.RoundedButton) {
            Rounded.RoundedButton rbLocalVariableValue =
                    (Rounded.RoundedButton) buttonParameterValue;
            if (selectedParameterValue) {
                buttonParameterValue.setForeground(Color.WHITE);
                rbLocalVariableValue.setGradientColors(
                        new Color(22, 150, 83), new Color(15, 110, 60)
                );
                rbLocalVariableValue.setShadowEnabled(false);
            } else {
                buttonParameterValue.setForeground(ACCENT_COLOR);
                buttonParameterValue.setBackground(new Color(255, 255, 255, 230));
                rbLocalVariableValue.setOutlineMode(ACCENT_COLOR, 2);
                rbLocalVariableValue.setShadowEnabled(false);
            }
        }
    }


    /**
     * Gestiona esta operacion.
     *
     * @param buttonParameterValue dato de entrada de la operacion.
     * @param fromParameterValue dato de entrada de la operacion.
     * @param toParameterValue dato de entrada de la operacion.
     * @param nowSelectedParameterValue dato de entrada de la operacion.
     */
    private void toggleTeam(JButton buttonParameterValue,
                            JPanel fromParameterValue,
                            JPanel toParameterValue,
                            boolean nowSelectedParameterValue) {
        fromParameterValue.remove(buttonParameterValue);
        styleTeamButton(buttonParameterValue, nowSelectedParameterValue);
        toParameterValue.add(buttonParameterValue);

        fromParameterValue.revalidate();
        fromParameterValue.repaint();
        toParameterValue.revalidate();
        toParameterValue.repaint();

        for (ActionListener listenerLocalVariableValue : buttonParameterValue.getActionListeners()) {
            buttonParameterValue.removeActionListener(listenerLocalVariableValue);
        }
        buttonParameterValue.addActionListener(eventArgumentParameterValue ->
                toggleTeam(buttonParameterValue, toParameterValue, fromParameterValue,
                        !nowSelectedParameterValue)
        );
    }


    /**
     * Agrupa la logica de esta parte de la aplicacion.
     */
    private static class RoundedCardPanel extends JPanel {


        /**
         * Gestiona esta operacion.
         *
         * @param graphicsParameterValue dato de entrada de la operacion.
         */
        @Override
        protected void paintComponent(Graphics graphicsParameterValue) {
            Graphics2D g2LocalVariableValue =
                    (Graphics2D) graphicsParameterValue.create();

            g2LocalVariableValue.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int widthLocalVariableValue = getWidth();
            int heightLocalVariableValue = getHeight();
            int radiusLocalVariableValue = 22;

            g2LocalVariableValue.setColor(new Color(8, 20, 46, 42));
            g2LocalVariableValue.fillRoundRect(
                    8, 10,
                    widthLocalVariableValue - 16,
                    heightLocalVariableValue - 10,
                    radiusLocalVariableValue, radiusLocalVariableValue
            );

            g2LocalVariableValue.setColor(new Color(255, 255, 255, 250));
            g2LocalVariableValue.fillRoundRect(
                    0, 0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 4,
                    radiusLocalVariableValue, radiusLocalVariableValue
            );

            g2LocalVariableValue.setColor(ACCENT_COLOR);
            g2LocalVariableValue.setStroke(new BasicStroke(2f));
            g2LocalVariableValue.drawRoundRect(
                    0, 0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 5,
                    radiusLocalVariableValue, radiusLocalVariableValue
            );

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }
}
