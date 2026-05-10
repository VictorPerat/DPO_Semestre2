package presentation.Views;

import bussines.managers.TeamManager;
import bussines.objects.Team;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * Representa la vista del equipo.
 */
public class DeleteTeamView extends JPanel {

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);

    private static final Color CARD_TITLE_COLOR = new Color(28, 35, 51);
    private static final Color CARD_BODY_COLOR = new Color(100, 112, 135);
    private static final Color FIELD_BACKGROUND = new Color(245, 249, 255);
    private static final Color FIELD_BORDER = new Color(190, 210, 235);

    private static final Color DANGER_RED = new Color(220, 60, 60);
    private static final Color DANGER_RED_DARK = new Color(170, 35, 35);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    private static final String TEAM_ICON_PRIMARY_PATH =
            ProjectPathResolver.resolveProjectPath("photos/create_team.png");

    private static final String TEAM_ICON_FALLBACK_PATH =
            ProjectPathResolver.resolveProjectPath("photos/football.png");

    private ActionListener controllerHandlerFieldReference;

    private final ArrayList<JCheckBox> teamReferenceCheckboxesFieldReference = new ArrayList<>();
    private ArrayList<Team> loadedTeamsFieldReference = new ArrayList<>();

    private final TeamManager teamReferenceManagerServiceFieldReference = new TeamManager();

    private JPanel teamsListPanelFieldReference;

    private Rounded.RoundedButton backButtonFieldReference;
    private Rounded.RoundedButton deleteButtonFieldReference;


    /**
     * Crea una instancia de el equipo.
     */
    public DeleteTeamView() {
        setLayout(new BorderLayout());
        loadedTeamsFieldReference = teamReferenceManagerServiceFieldReference.getAllTeams();
        add(buildBackgroundPanel(), BorderLayout.CENTER);
    }


    /**
     * Actualiza el contenido.
     *
     * @param controllerHandlerParameterValue dato de entrada de la operacion.
     */
    public void setController(ActionListener controllerHandlerParameterValue) {
        this.controllerHandlerFieldReference = controllerHandlerParameterValue;
    }


    /**
     * Carga los equipos.
     *
     * @param teamsParameterValue equipos que usa la operacion.
     */
    public void loadTeams(ArrayList<Team> teamsParameterValue) {
        loadedTeamsFieldReference =
                teamsParameterValue == null ? new ArrayList<>() : teamsParameterValue;
        populateTeamsList();
    }


    /**
     * Gestiona esta operacion.
     */
    public void refreshTeamsList() {
        loadedTeamsFieldReference = teamReferenceManagerServiceFieldReference.getAllTeams();
        populateTeamsList();
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
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        this
                );

                Graphics2D g2LocalVariableValue =
                        (Graphics2D) graphicsParameterValue.create();
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

        JPanel rightPanelLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rightPanelLocalVariableValue.setOpaque(false);

        backButtonFieldReference = new Rounded.RoundedButton("< BACK", 18);
        backButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 15));
        backButtonFieldReference.setForeground(ACCENT_COLOR);
        backButtonFieldReference.setBackground(new Color(255, 255, 255, 230));
        backButtonFieldReference.setOutlineMode(ACCENT_COLOR, 2);
        backButtonFieldReference.setShadowEnabled(false);
        backButtonFieldReference.setPreferredSize(new Dimension(145, 44));

        backButtonFieldReference.addActionListener(
                eventArgumentParameterValue -> fireCommand("BACK")
        );

        rightPanelLocalVariableValue.add(backButtonFieldReference);
        topBarLocalVariableValue.add(rightPanelLocalVariableValue, BorderLayout.WEST);

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
        JPanel deleteCardLocalVariableValue = buildDeleteCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteCardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(38));
        contentPanelLocalVariableValue.add(deleteCardLocalVariableValue);

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.weighty = 1.0;
        constraintsLocalVariableValue.anchor = GridBagConstraints.CENTER;

        constraintsLocalVariableValue.insets = new Insets(0, 0, 0, 310);

        centerWrapperLocalVariableValue.add(
                contentPanelLocalVariableValue,
                constraintsLocalVariableValue
        );

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

        JPanel titleLineLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLineLocalVariableValue.setOpaque(false);

        JLabel deleteLabelLocalVariableValue = new JLabel("DELETE ");
        deleteLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        deleteLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        JLabel teamLabelLocalVariableValue = new JLabel("TEAM");
        teamLabelLocalVariableValue.setForeground(TITLE_WHITE);
        teamLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        titleLineLocalVariableValue.add(deleteLabelLocalVariableValue);
        titleLineLocalVariableValue.add(teamLabelLocalVariableValue);

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
                "Select teams to remove from the system",
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
    private JPanel buildDeleteCard() {
        JPanel cardLocalVariableValue = new RoundedCardPanel();
        cardLocalVariableValue.setOpaque(false);
        cardLocalVariableValue.setLayout(
                new BoxLayout(cardLocalVariableValue, BoxLayout.Y_AXIS)
        );
        cardLocalVariableValue.setBorder(new EmptyBorder(24, 34, 30, 34));

        cardLocalVariableValue.setPreferredSize(new Dimension(820, 560));
        cardLocalVariableValue.setMinimumSize(new Dimension(820, 560));
        cardLocalVariableValue.setMaximumSize(new Dimension(820, 560));

        JPanel iconPanelLocalVariableValue = buildTeamIconPanel();
        iconPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabelLocalVariableValue =
                new JLabel("TEAM LIST", SwingConstants.CENTER);
        titleLabelLocalVariableValue.setForeground(CARD_TITLE_COLOR);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel helperLabelLocalVariableValue = new JLabel(
                "Mark one or more teams and confirm the deletion",
                SwingConstants.CENTER
        );
        helperLabelLocalVariableValue.setForeground(CARD_BODY_COLOR);
        helperLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 14));
        helperLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardLocalVariableValue.add(iconPanelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(6));
        cardLocalVariableValue.add(titleLabelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(8));
        cardLocalVariableValue.add(helperLabelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(18));

        JScrollPane teamsScrollPaneLocalVariableValue = createTeamsPanel();
        teamsScrollPaneLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardLocalVariableValue.add(teamsScrollPaneLocalVariableValue);

        cardLocalVariableValue.add(Box.createVerticalStrut(18));

        deleteButtonFieldReference = new Rounded.RoundedButton("DELETE SELECTED", 18);
        deleteButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 15));
        deleteButtonFieldReference.setForeground(Color.WHITE);
        deleteButtonFieldReference.setBackground(DANGER_RED);
        deleteButtonFieldReference.setGradientColors(DANGER_RED, DANGER_RED_DARK);
        deleteButtonFieldReference.setPreferredSize(new Dimension(620, 46));
        deleteButtonFieldReference.setMaximumSize(new Dimension(620, 46));
        deleteButtonFieldReference.setMinimumSize(new Dimension(620, 46));
        deleteButtonFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);

        deleteButtonFieldReference.addActionListener(
                eventArgumentParameterValue -> fireCommand("DELETE_TEAMS")
        );

        cardLocalVariableValue.add(deleteButtonFieldReference);

        return cardLocalVariableValue;
    }


    /**
     * Construye el equipo.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildTeamIconPanel() {
        JPanel iconPanelLocalVariableValue = new JPanel(new GridBagLayout());
        iconPanelLocalVariableValue.setOpaque(false);

        iconPanelLocalVariableValue.setPreferredSize(new Dimension(120, 120));
        iconPanelLocalVariableValue.setMaximumSize(new Dimension(120, 120));
        iconPanelLocalVariableValue.setMinimumSize(new Dimension(120, 120));

        JLabel iconLabelLocalVariableValue = new JLabel();
        iconLabelLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabelLocalVariableValue.setVerticalAlignment(SwingConstants.CENTER);

        try {
            String iconPathLocalVariableValue =
                    new java.io.File(TEAM_ICON_PRIMARY_PATH).exists()
                            ? TEAM_ICON_PRIMARY_PATH
                            : TEAM_ICON_FALLBACK_PATH;

            Image iconImageLocalVariableValue =
                    new ImageIcon(iconPathLocalVariableValue).getImage();

            Image scaledIconLocalVariableValue =
                    iconImageLocalVariableValue.getScaledInstance(
                            115,
                            115,
                            Image.SCALE_SMOOTH
                    );

            iconLabelLocalVariableValue.setIcon(
                    new ImageIcon(scaledIconLocalVariableValue)
            );
        } catch (Exception ignoredExceptionParameterValue) {
            iconLabelLocalVariableValue.setText("ÃƒÂ¢Ã…Â¡Ã‚Â½");
            iconLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 62));
        }

        iconPanelLocalVariableValue.add(iconLabelLocalVariableValue);

        return iconPanelLocalVariableValue;
    }


    /**
     * Crea los equipos.
     *
     * @return elemento creado por el metodo.
     */
    private JScrollPane createTeamsPanel() {
        teamsListPanelFieldReference = new JPanel();
        teamsListPanelFieldReference.setLayout(
                new BoxLayout(teamsListPanelFieldReference, BoxLayout.Y_AXIS)
        );
        teamsListPanelFieldReference.setBackground(FIELD_BACKGROUND);
        teamsListPanelFieldReference.setBorder(new EmptyBorder(10, 12, 10, 12));

        JScrollPane scrollPaneLocalVariableValue =
                new JScrollPane(teamsListPanelFieldReference);

        scrollPaneLocalVariableValue.setBorder(
                BorderFactory.createLineBorder(FIELD_BORDER, 1)
        );
        scrollPaneLocalVariableValue.getViewport().setBackground(FIELD_BACKGROUND);
        scrollPaneLocalVariableValue.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPaneLocalVariableValue.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollPaneLocalVariableValue.setPreferredSize(new Dimension(620, 220));
        scrollPaneLocalVariableValue.setMaximumSize(new Dimension(620, 220));
        scrollPaneLocalVariableValue.setMinimumSize(new Dimension(620, 220));

        populateTeamsList();

        return scrollPaneLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     */
    private void populateTeamsList() {
        if (teamsListPanelFieldReference == null) {
            return;
        }

        teamsListPanelFieldReference.removeAll();
        teamReferenceCheckboxesFieldReference.clear();

        JPanel headerPanelLocalVariableValue = buildListHeader();
        teamsListPanelFieldReference.add(headerPanelLocalVariableValue);
        teamsListPanelFieldReference.add(Box.createVerticalStrut(8));

        if (loadedTeamsFieldReference == null || loadedTeamsFieldReference.isEmpty()) {
            JLabel emptyLabelLocalVariableValue =
                    new JLabel("No teams available.", SwingConstants.CENTER);
            emptyLabelLocalVariableValue.setForeground(new Color(120, 132, 155));
            emptyLabelLocalVariableValue.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyLabelLocalVariableValue.setBorder(new EmptyBorder(62, 0, 20, 0));

            teamsListPanelFieldReference.add(emptyLabelLocalVariableValue);
        } else {
            for (Team teamReferenceLocalVariableValue : loadedTeamsFieldReference) {
                teamsListPanelFieldReference.add(
                        createTeamRow(teamReferenceLocalVariableValue)
                );
                teamsListPanelFieldReference.add(Box.createVerticalStrut(8));
            }
        }

        teamsListPanelFieldReference.revalidate();
        teamsListPanelFieldReference.repaint();
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildListHeader() {
        JPanel headerPanelLocalVariableValue = new JPanel(new GridLayout(1, 3));
        headerPanelLocalVariableValue.setOpaque(false);
        headerPanelLocalVariableValue.setMaximumSize(new Dimension(580, 34));
        headerPanelLocalVariableValue.setPreferredSize(new Dimension(580, 34));
        headerPanelLocalVariableValue.setMinimumSize(new Dimension(580, 34));
        headerPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] headersLocalVariableValue = {"SELECT", "TEAM", "ID"};

        for (String headerLocalVariableValue : headersLocalVariableValue) {
            JLabel labelLocalVariableValue =
                    new JLabel(headerLocalVariableValue, SwingConstants.CENTER);
            labelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 13));
            labelLocalVariableValue.setForeground(new Color(75, 88, 115));
            headerPanelLocalVariableValue.add(labelLocalVariableValue);
        }

        return headerPanelLocalVariableValue;
    }


    /**
     * Crea el equipo.
     *
     * @param teamReferenceParameterValue equipo asociado.
     * @return elemento creado por el metodo.
     */
    private JPanel createTeamRow(Team teamReferenceParameterValue) {
        JPanel rowPanelLocalVariableValue = new SelectableRowPanel();
        rowPanelLocalVariableValue.setLayout(new GridLayout(1, 3));
        rowPanelLocalVariableValue.setOpaque(false);
        rowPanelLocalVariableValue.setBorder(new EmptyBorder(8, 16, 8, 16));
        rowPanelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        rowPanelLocalVariableValue.setPreferredSize(new Dimension(580, 48));
        rowPanelLocalVariableValue.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rowPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JCheckBox checkBoxLocalVariableValue = new JCheckBox();
        checkBoxLocalVariableValue.setOpaque(false);
        checkBoxLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
        checkBoxLocalVariableValue.setCursor(new Cursor(Cursor.HAND_CURSOR));

        teamReferenceCheckboxesFieldReference.add(checkBoxLocalVariableValue);

        JLabel nameLabelLocalVariableValue = createRowLabel(
                teamReferenceParameterValue.getName(),
                Font.BOLD,
                CARD_TITLE_COLOR
        );

        JLabel idLabelLocalVariableValue = createRowLabel(
                String.valueOf(teamReferenceParameterValue.getId()),
                Font.PLAIN,
                new Color(54, 66, 87)
        );

        rowPanelLocalVariableValue.add(checkBoxLocalVariableValue);
        rowPanelLocalVariableValue.add(nameLabelLocalVariableValue);
        rowPanelLocalVariableValue.add(idLabelLocalVariableValue);

        rowPanelLocalVariableValue.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent eventArgumentParameterValue) {
                checkBoxLocalVariableValue.setSelected(
                        !checkBoxLocalVariableValue.isSelected()
                );
            }
        });

        return rowPanelLocalVariableValue;
    }


    /**
     * Crea el contenido.
     *
     * @param textParameterValue texto que usa la operacion.
     * @param styleParameterValue dato de entrada de la operacion.
     * @param colorParameterValue dato de entrada de la operacion.
     * @return elemento creado por el metodo.
     */
    private JLabel createRowLabel(String textParameterValue,
                                  int styleParameterValue,
                                  Color colorParameterValue) {
        JLabel labelLocalVariableValue =
                new JLabel(textParameterValue == null ? "-" : textParameterValue,
                        SwingConstants.CENTER);
        labelLocalVariableValue.setFont(new Font("Arial", styleParameterValue, 14));
        labelLocalVariableValue.setForeground(colorParameterValue);
        return labelLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param commandParameterValue dato de entrada de la operacion.
     */
    private void fireCommand(String commandParameterValue) {
        if (controllerHandlerFieldReference != null) {
            controllerHandlerFieldReference.actionPerformed(
                    new ActionEvent(
                            this,
                            ActionEvent.ACTION_PERFORMED,
                            commandParameterValue
                    )
            );
        }
    }


    /**
     * Devuelve los equipos.
     *
     * @return los equipos.
     */
    public ArrayList<String> getSelectedTeams() {
        ArrayList<String> selectedLocalVariableValue = new ArrayList<>();

        for (int indexCounterLocalVariableValue = 0;
             indexCounterLocalVariableValue < teamReferenceCheckboxesFieldReference.size();
             indexCounterLocalVariableValue++) {

            if (teamReferenceCheckboxesFieldReference.get(indexCounterLocalVariableValue).isSelected()
                    && indexCounterLocalVariableValue < loadedTeamsFieldReference.size()) {
                selectedLocalVariableValue.add(
                        loadedTeamsFieldReference.get(indexCounterLocalVariableValue).getName()
                );
            }
        }

        return selectedLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param countParameterValue dato de entrada de la operacion.
     * @return resultado de la operacion.
     */
    public int confirmDeleteTeams(int countParameterValue) {
        return JOptionPane.showConfirmDialog(
                SwingUtilities.getWindowAncestor(this),
                "Are you sure you want to delete "
                        + countParameterValue
                        + " team(s)?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
        );
    }


    /**
     * Muestra el resultado.
     *
     * @param messageParameterValue dato de entrada de la operacion.
     */
    public void showDeletionResult(String messageParameterValue) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                messageParameterValue,
                "Teams Deleted",
                JOptionPane.INFORMATION_MESSAGE
        );
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
                "DELETE",
                JOptionPane.WARNING_MESSAGE
        );
    }


    /**
     * Gestiona esta operacion.
     *
     * @param primaryRelativePathParameterValue ruta que usa la operacion.
     * @param fallbackRelativePathParameterValue ruta que usa la operacion.
     * @return resultado de la operacion.
     */
    private String resolveExistingIconPath(String primaryRelativePathParameterValue,
                                           String fallbackRelativePathParameterValue) {
        String primaryAbsolutePathLocalVariableValue =
                ProjectPathResolver.resolveProjectPath(primaryRelativePathParameterValue);

        if (new java.io.File(primaryAbsolutePathLocalVariableValue).exists()) {
            return primaryAbsolutePathLocalVariableValue;
        }

        return ProjectPathResolver.resolveProjectPath(fallbackRelativePathParameterValue);
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
                    8,
                    10,
                    widthLocalVariableValue - 16,
                    heightLocalVariableValue - 10,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.setColor(new Color(255, 255, 255, 250));
            g2LocalVariableValue.fillRoundRect(
                    0,
                    0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 4,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.setColor(new Color(22, 150, 83));
            g2LocalVariableValue.setStroke(new BasicStroke(2f));
            g2LocalVariableValue.drawRoundRect(
                    0,
                    0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 5,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }


    /**
     * Agrupa la logica de esta parte de la aplicacion.
     */
    private static class SelectableRowPanel extends JPanel {
        private boolean hoverFieldReference = false;


        /**
         * Crea una instancia de selectablerowpanel.
         */
        SelectableRowPanel() {
            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent eventArgumentParameterValue) {
                    hoverFieldReference = true;
                    repaint();
                }


                @Override
                public void mouseExited(MouseEvent eventArgumentParameterValue) {
                    hoverFieldReference = false;
                    repaint();
                }
            });
        }


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

            int radiusLocalVariableValue = 16;

            g2LocalVariableValue.setColor(
                    hoverFieldReference
                            ? new Color(255, 238, 238)
                            : Color.WHITE
            );

            g2LocalVariableValue.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.setColor(
                    hoverFieldReference
                            ? DANGER_RED
                            : new Color(218, 228, 242)
            );

            g2LocalVariableValue.setStroke(new BasicStroke(1.4f));
            g2LocalVariableValue.drawRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }
}


