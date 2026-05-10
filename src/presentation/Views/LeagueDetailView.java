package presentation.Views;

import bussines.managers.PlayerManager;
import bussines.objects.League;
import bussines.objects.Team;
import bussines.objects.TeamInfo;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Representa la vista del liga detalle.
 */
public class LeagueDetailView extends JPanel {

    private static final String[] COLUMN_NAMES =
            {"Position", "Team", "Players", "Won", "Drawn", "Lost", "Points"};

    /**
     * Constante para el vuelta.
     */
    public static final String BACK = "BACK";
    /**
     * Constante para el configuracion.
     */
    public static final String CONFIG = "CONFIG";
    /**
     * Accion para mostrar el valor.
     */
    public static final String SHOW_STATS = "SHOW_STATS";
    /**
     * Accion para mostrar el calendario.
     */
    public static final String SHOW_CALENDAR = "SHOW_CALENDAR";


    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);

    private static final Color CARD_TITLE_COLOR = new Color(28, 35, 51);
    private static final Color FIELD_BORDER = new Color(190, 210, 235);

    private static final Color TABLE_HEADER_BG = new Color(34, 64, 110);
    private static final Color TABLE_HEADER_FG = Color.WHITE;
    private static final Color TABLE_ROW_ALT = new Color(245, 249, 255);
    private static final Color TABLE_ROW_BASE = Color.WHITE;
    private static final Color TABLE_GRID = new Color(220, 230, 245);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    private static final String LEAGUES_ICON_PATH =
            ProjectPathResolver.resolveProjectPath("photos/Leagues.png");


    private JTable tableFieldReference;
    private DefaultTableModel tableModelFieldReference;
    private JLabel leagueReferenceTitleLabelFieldReference;
    private Rounded.RoundedButton backButtonFieldReference;
    private JButton configButtonFieldReference;
    private Rounded.RoundedButton statsButtonFieldReference;
    private Rounded.RoundedButton calendarButtonFieldReference;
    private League currentLeagueFieldReference;
    private ArrayList<Team> currentTeamsFieldReference = new ArrayList<>();


    /**
     * Crea una instancia de el liga detalle.
     */
    public LeagueDetailView() {
        setLayout(new BorderLayout());


        configButtonFieldReference = new JButton();
        configButtonFieldReference.setVisible(false);

        add(buildBackgroundPanel(), BorderLayout.CENTER);
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

        backButtonFieldReference = new Rounded.RoundedButton("← BACK", 18);
        backButtonFieldReference.setActionCommand(BACK);
        backButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 15));
        backButtonFieldReference.setForeground(ACCENT_COLOR);
        backButtonFieldReference.setBackground(new Color(255, 255, 255, 230));
        backButtonFieldReference.setOutlineMode(ACCENT_COLOR, 2);
        backButtonFieldReference.setShadowEnabled(false);
        backButtonFieldReference.setPreferredSize(new Dimension(145, 44));

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
        JPanel cardLocalVariableValue = buildContentCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(34));
        contentPanelLocalVariableValue.add(cardLocalVariableValue);

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

        JLabel leagueLabelLocalVariableValue = new JLabel("LEAGUE ");
        leagueLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        leagueLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        JLabel detailsLabelLocalVariableValue = new JLabel("DETAILS");
        detailsLabelLocalVariableValue.setForeground(TITLE_WHITE);
        detailsLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        titleLineLocalVariableValue.add(leagueLabelLocalVariableValue);
        titleLineLocalVariableValue.add(detailsLabelLocalVariableValue);

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
                "Standings, statistics and calendar at a glance",
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
    private JPanel buildContentCard() {
        JPanel cardLocalVariableValue = new RoundedCardPanel();
        cardLocalVariableValue.setOpaque(false);
        cardLocalVariableValue.setLayout(
                new BoxLayout(cardLocalVariableValue, BoxLayout.Y_AXIS)
        );
        cardLocalVariableValue.setBorder(new EmptyBorder(22, 32, 28, 32));

        cardLocalVariableValue.setPreferredSize(new Dimension(900, 600));
        cardLocalVariableValue.setMinimumSize(new Dimension(900, 600));
        cardLocalVariableValue.setMaximumSize(new Dimension(900, 600));

        JPanel iconPanelLocalVariableValue = buildLeaguesIconPanel();
        iconPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        leagueReferenceTitleLabelFieldReference = new JLabel(
                "No league selected",
                SwingConstants.CENTER
        );
        leagueReferenceTitleLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 22));
        leagueReferenceTitleLabelFieldReference.setForeground(CARD_TITLE_COLOR);
        leagueReferenceTitleLabelFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel actionsPanelLocalVariableValue = buildActionsPanel();
        actionsPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComponent tablePanelLocalVariableValue = buildStandingsTablePanel();
        tablePanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardLocalVariableValue.add(iconPanelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(4));
        cardLocalVariableValue.add(leagueReferenceTitleLabelFieldReference);
        cardLocalVariableValue.add(Box.createVerticalStrut(14));
        cardLocalVariableValue.add(actionsPanelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(16));
        cardLocalVariableValue.add(tablePanelLocalVariableValue);

        return cardLocalVariableValue;
    }


    /**
     * Construye los ligas.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildLeaguesIconPanel() {
        JPanel iconPanelLocalVariableValue = new JPanel(new GridBagLayout());
        iconPanelLocalVariableValue.setOpaque(false);
        iconPanelLocalVariableValue.setPreferredSize(new Dimension(96, 96));
        iconPanelLocalVariableValue.setMaximumSize(new Dimension(96, 96));
        iconPanelLocalVariableValue.setMinimumSize(new Dimension(96, 96));

        JLabel iconLabelLocalVariableValue = new JLabel();
        iconLabelLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabelLocalVariableValue.setVerticalAlignment(SwingConstants.CENTER);

        try {
            Image rawImageLocalVariableValue =
                    new ImageIcon(LEAGUES_ICON_PATH).getImage();
            Image scaledImageLocalVariableValue =
                    rawImageLocalVariableValue.getScaledInstance(86, 86, Image.SCALE_SMOOTH);
            iconLabelLocalVariableValue.setIcon(new ImageIcon(scaledImageLocalVariableValue));
        } catch (Exception ignoredExceptionParameterValue) {
            iconLabelLocalVariableValue.setText("🏆");
            iconLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 50));
        }

        iconPanelLocalVariableValue.add(iconLabelLocalVariableValue);
        return iconPanelLocalVariableValue;
    }


    /**
     * Construye los acciones.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildActionsPanel() {
        JPanel actionsPanelLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        actionsPanelLocalVariableValue.setOpaque(false);

        statsButtonFieldReference = new Rounded.RoundedButton("📊  SHOW STATS", 16);
        statsButtonFieldReference.setActionCommand(SHOW_STATS);
        statsButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 14));
        statsButtonFieldReference.setForeground(Color.WHITE);
        statsButtonFieldReference.setGradientColors(ACCENT_COLOR, new Color(36, 80, 180));
        statsButtonFieldReference.setShadowEnabled(true);
        statsButtonFieldReference.setPreferredSize(new Dimension(200, 42));

        calendarButtonFieldReference = new Rounded.RoundedButton("📅  SHOW CALENDAR", 16);
        calendarButtonFieldReference.setActionCommand(SHOW_CALENDAR);
        calendarButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 14));
        calendarButtonFieldReference.setForeground(ACCENT_COLOR);
        calendarButtonFieldReference.setBackground(new Color(255, 255, 255, 230));
        calendarButtonFieldReference.setOutlineMode(ACCENT_COLOR, 2);
        calendarButtonFieldReference.setShadowEnabled(false);
        calendarButtonFieldReference.setPreferredSize(new Dimension(220, 42));

        actionsPanelLocalVariableValue.add(statsButtonFieldReference);
        actionsPanelLocalVariableValue.add(calendarButtonFieldReference);

        return actionsPanelLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JComponent buildStandingsTablePanel() {
        tableModelFieldReference = new DefaultTableModel(
                new Object[0][COLUMN_NAMES.length],
                COLUMN_NAMES
        ) {

            @Override
            public boolean isCellEditable(int rowParameterValue, int columnParameterValue) {
                return false;
            }
        };

        tableFieldReference = new JTable(tableModelFieldReference) {

            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer rendererParameter,
                                             int rowParameterValue,
                                             int columnParameterValue) {
                Component componentLocalVariableValue =
                        super.prepareRenderer(rendererParameter, rowParameterValue, columnParameterValue);
                if (!isRowSelected(rowParameterValue)) {
                    componentLocalVariableValue.setBackground(
                            rowParameterValue % 2 == 0 ? TABLE_ROW_BASE : TABLE_ROW_ALT
                    );
                    componentLocalVariableValue.setForeground(CARD_TITLE_COLOR);
                } else {
                    componentLocalVariableValue.setBackground(new Color(220, 232, 252));
                    componentLocalVariableValue.setForeground(CARD_TITLE_COLOR);
                }
                return componentLocalVariableValue;
            }
        };
        tableFieldReference.setFont(new Font("Arial", Font.PLAIN, 14));
        tableFieldReference.setRowHeight(32);
        tableFieldReference.setShowGrid(true);
        tableFieldReference.setGridColor(TABLE_GRID);
        tableFieldReference.setAutoCreateRowSorter(false);
        tableFieldReference.setFillsViewportHeight(true);
        tableFieldReference.setSelectionBackground(new Color(220, 232, 252));
        tableFieldReference.setSelectionForeground(CARD_TITLE_COLOR);


        JTableHeader headerLocalVariableValue = tableFieldReference.getTableHeader();
        headerLocalVariableValue.setReorderingAllowed(false);
        headerLocalVariableValue.setResizingAllowed(false);
        headerLocalVariableValue.setPreferredSize(new Dimension(0, 42));
        headerLocalVariableValue.setDefaultRenderer(buildHeaderRenderer());


        applyTableRenderers();

        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(tableFieldReference);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createLineBorder(FIELD_BORDER, 1));
        scrollPaneLocalVariableValue.getViewport().setBackground(TABLE_ROW_BASE);


        scrollPaneLocalVariableValue.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneLocalVariableValue.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        scrollPaneLocalVariableValue.getVerticalScrollBar().setUnitIncrement(16);


        scrollPaneLocalVariableValue.getVerticalScrollBar()
                .setPreferredSize(new Dimension(10, 0));

        scrollPaneLocalVariableValue.setPreferredSize(new Dimension(820, 290));
        scrollPaneLocalVariableValue.setMinimumSize(new Dimension(820, 290));
        scrollPaneLocalVariableValue.setMaximumSize(new Dimension(820, 290));

        return scrollPaneLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private javax.swing.table.TableCellRenderer buildHeaderRenderer() {
        return new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable tableParameterValue,
                                                           Object valueParameterValue,
                                                           boolean isSelectedParameterValue,
                                                           boolean hasFocusParameterValue,
                                                           int rowParameterValue,
                                                           int columnParameterValue) {
                JLabel labelLocalVariableValue = (JLabel) super.getTableCellRendererComponent(
                        tableParameterValue,
                        valueParameterValue,
                        isSelectedParameterValue,
                        hasFocusParameterValue,
                        rowParameterValue,
                        columnParameterValue
                );
                labelLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
                labelLocalVariableValue.setBackground(TABLE_HEADER_BG);
                labelLocalVariableValue.setForeground(TABLE_HEADER_FG);
                labelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 14));
                labelLocalVariableValue.setOpaque(true);
                labelLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 3, 0, ACCENT_COLOR),
                        BorderFactory.createEmptyBorder(0, 8, 0, 8)
                ));
                return labelLocalVariableValue;
            }
        };
    }


    /**
     * Gestiona esta operacion.
     */
    private void applyTableRenderers() {
        DefaultTableCellRenderer cellRendererLocalVariableValue = new DefaultTableCellRenderer();
        cellRendererLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);


        int[] columnWidthsLocalVariableValue = { 90, 240, 100, 90, 90, 90, 110 };

        for (int columnIndexLocalVariableValue = 0;
             columnIndexLocalVariableValue < tableFieldReference.getColumnCount();
             columnIndexLocalVariableValue++) {

            javax.swing.table.TableColumn columnLocalVariableValue =
                    tableFieldReference.getColumnModel().getColumn(columnIndexLocalVariableValue);

            columnLocalVariableValue.setCellRenderer(cellRendererLocalVariableValue);

            if (columnIndexLocalVariableValue < columnWidthsLocalVariableValue.length) {
                columnLocalVariableValue.setPreferredWidth(
                        columnWidthsLocalVariableValue[columnIndexLocalVariableValue]
                );
            }
        }
    }


    /**
     * Carga el liga.
     *
     * @param leagueReferenceParameterValue liga que usa la operacion.
     * @param informationOfTeamsParameterValue equipos que usa la operacion.
     * @param teamsParameterValue equipos que usa la operacion.
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     */
    public void loadLeague(League leagueReferenceParameterValue,
                           ArrayList<TeamInfo> informationOfTeamsParameterValue,
                           ArrayList<Team> teamsParameterValue,
                           PlayerManager playerProfileManagerServiceParameterValue) {
        this.currentLeagueFieldReference = leagueReferenceParameterValue;
        this.currentTeamsFieldReference = teamsParameterValue == null ? new ArrayList<>() : teamsParameterValue;
        if (leagueReferenceParameterValue != null) {
            leagueReferenceTitleLabelFieldReference.setText(
                    leagueReferenceParameterValue.getName()
                            + "  ·  Start: "
                            + leagueReferenceParameterValue.getStartDate()
            );
        } else {
            leagueReferenceTitleLabelFieldReference.setText("No league selected");
        }
        refreshStandings(
                informationOfTeamsParameterValue,
                this.currentTeamsFieldReference,
                playerProfileManagerServiceParameterValue
        );
    }


    /**
     * Devuelve el actual liga.
     *
     * @return el actual liga.
     */
    public League getCurrentLeague() {
        return currentLeagueFieldReference;
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public JTable getStandingsTable() {
        return tableFieldReference;
    }


    /**
     * Devuelve los actual equipos.
     *
     * @return los actual equipos.
     */
    public ArrayList<Team> getCurrentTeams() {
        return currentTeamsFieldReference;
    }


    /**
     * Devuelve el equipo nombre vista.
     *
     * @param rowParameterValue dato de entrada de la operacion.
     * @return el equipo nombre vista.
     */
    public String getTeamNameAtViewRow(int rowParameterValue) {
        if (rowParameterValue < 0) {
            return null;
        }
        int modelRowLocalVariableValue =
                tableFieldReference.convertRowIndexToModel(rowParameterValue);
        Object valueLocalVariableValue =
                tableModelFieldReference.getValueAt(modelRowLocalVariableValue, 1);
        return valueLocalVariableValue == null ? null : valueLocalVariableValue.toString();
    }


    /**
     * Gestiona esta operacion.
     *
     * @param informationOfTeamsParameterValue equipos que usa la operacion.
     * @param teamsParameterValue equipos que usa la operacion.
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     */
    public void refreshStandings(ArrayList<TeamInfo> informationOfTeamsParameterValue,
                                 ArrayList<Team> teamsParameterValue,
                                 PlayerManager playerProfileManagerServiceParameterValue) {
        if (informationOfTeamsParameterValue == null || teamsParameterValue == null) {
            tableModelFieldReference.setDataVector(
                    new Object[0][COLUMN_NAMES.length], COLUMN_NAMES
            );
            return;
        }
        List<TeamInfo> sortedTeamsLocalVariableValue =
                new ArrayList<>(informationOfTeamsParameterValue);
        sortedTeamsLocalVariableValue.sort(
                Comparator.comparingInt(TeamInfo::getPoints).reversed()
        );

        Object[][] rowsLocalVariableValue =
                new Object[sortedTeamsLocalVariableValue.size()][COLUMN_NAMES.length];

        for (int indexCounterLocalVariableValue = 0;
             indexCounterLocalVariableValue < sortedTeamsLocalVariableValue.size();
             indexCounterLocalVariableValue++) {

            TeamInfo infoLocalVariableValue =
                    sortedTeamsLocalVariableValue.get(indexCounterLocalVariableValue);
            String teamNameLocalVariableValue =
                    resolveTeamName(infoLocalVariableValue.getTeamId(), teamsParameterValue);

            rowsLocalVariableValue[indexCounterLocalVariableValue][0] = indexCounterLocalVariableValue + 1;
            rowsLocalVariableValue[indexCounterLocalVariableValue][1] = teamNameLocalVariableValue;
            rowsLocalVariableValue[indexCounterLocalVariableValue][2] =
                    playerProfileManagerServiceParameterValue == null
                            ? 0
                            : playerProfileManagerServiceParameterValue
                                    .getNumberOfPlayersByTeamName(teamNameLocalVariableValue);
            rowsLocalVariableValue[indexCounterLocalVariableValue][3] = infoLocalVariableValue.getWins();
            rowsLocalVariableValue[indexCounterLocalVariableValue][4] = infoLocalVariableValue.getTies();
            rowsLocalVariableValue[indexCounterLocalVariableValue][5] = infoLocalVariableValue.getDefeats();
            rowsLocalVariableValue[indexCounterLocalVariableValue][6] = infoLocalVariableValue.getPoints();
        }
        tableModelFieldReference.setDataVector(rowsLocalVariableValue, COLUMN_NAMES);


        applyTableRenderers();
    }


    /**
     * Gestiona esta operacion.
     *
     * @param teamIdParameterValue equipo que usa la operacion.
     * @param teamsParameterValue equipos que usa la operacion.
     * @return resultado de la operacion.
     */
    private String resolveTeamName(int teamIdParameterValue,
                                   ArrayList<Team> teamsParameterValue) {
        for (Team teamReferenceLocalVariableValue : teamsParameterValue) {
            if (teamReferenceLocalVariableValue.getId() == teamIdParameterValue) {
                return teamReferenceLocalVariableValue.getName();
            }
        }
        return "Team " + teamIdParameterValue;
    }


    /**
     * Registra la accion.
     *
     * @param controllerHandlerParameterValue dato de entrada de la operacion.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.setActionCommand(BACK);


        configButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        configButtonFieldReference.setActionCommand(CONFIG);
        statsButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        calendarButtonFieldReference.addActionListener(controllerHandlerParameterValue);
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


            g2LocalVariableValue.setColor(ACCENT_COLOR);
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
}


