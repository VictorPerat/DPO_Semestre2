package presentation.Views;

import bussines.objects.League;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Representa la vista del estadisticas grafica.
 */
public class StatisticsGraphView extends JPanel {


    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);
    private static final Color CARD_TITLE_COLOR = new Color(28, 35, 51);
    private static final Color CARD_BODY_COLOR = new Color(100, 112, 135);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");


    private JLabel titleSubLabelFieldReference;
    private LeagueManagerChartPanelComponent chartPanelFieldReference;
    private Rounded.RoundedButton backButtonFieldReference;
    private JButton configButtonFieldReference;
    private JPanel leaguesPickerSlotFieldReference;
    private String[] teamReferenceNamesFieldReference = new String[0];
    private int countIdentifiersFieldReference = 0;


    /**
     * Crea una instancia de el estadisticas grafica.
     */
    public StatisticsGraphView() {
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
                        0, 0, getWidth(), getHeight(), this);
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
        backButtonFieldReference.setActionCommand("BACK");
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
                new BoxLayout(contentPanelLocalVariableValue, BoxLayout.Y_AXIS));
        contentPanelLocalVariableValue.setBorder(new EmptyBorder(0, 50, 16, 50));

        JPanel titleBlockLocalVariableValue = buildTitleBlock();
        leaguesPickerSlotFieldReference = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        leaguesPickerSlotFieldReference.setOpaque(false);
        JPanel cardLocalVariableValue = buildChartCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaguesPickerSlotFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(20));
        contentPanelLocalVariableValue.add(leaguesPickerSlotFieldReference);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(8));
        contentPanelLocalVariableValue.add(cardLocalVariableValue);

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.weighty = 1.0;
        constraintsLocalVariableValue.anchor = GridBagConstraints.CENTER;

        constraintsLocalVariableValue.insets = new Insets(0, 0, 0, 310);

        centerWrapperLocalVariableValue.add(
                contentPanelLocalVariableValue, constraintsLocalVariableValue);
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
                new BoxLayout(titleContainerLocalVariableValue, BoxLayout.Y_AXIS));

        JPanel titleLineLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLineLocalVariableValue.setOpaque(false);

        JLabel statisticsLabelLocalVariableValue = new JLabel("STATIS");
        statisticsLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        statisticsLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue));

        JLabel ticsLabelLocalVariableValue = new JLabel("TICS");
        ticsLabelLocalVariableValue.setForeground(TITLE_WHITE);
        ticsLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue));

        titleLineLocalVariableValue.add(statisticsLabelLocalVariableValue);
        titleLineLocalVariableValue.add(ticsLabelLocalVariableValue);

        JPanel underlineWrapperLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        underlineWrapperLocalVariableValue.setOpaque(false);

        JPanel underlinePanelLocalVariableValue = new JPanel();
        underlinePanelLocalVariableValue.setBackground(ACCENT_COLOR);
        underlinePanelLocalVariableValue.setPreferredSize(new Dimension(170, 6));
        underlinePanelLocalVariableValue.setMinimumSize(new Dimension(170, 6));
        underlinePanelLocalVariableValue.setMaximumSize(new Dimension(170, 6));

        underlineWrapperLocalVariableValue.add(underlinePanelLocalVariableValue);

        titleSubLabelFieldReference = new JLabel(
                "Team performance over the season",
                SwingConstants.CENTER);
        titleSubLabelFieldReference.setForeground(SUBTITLE_WHITE);
        titleSubLabelFieldReference.setFont(
                new Font("Arial", Font.PLAIN, subtitleSizeLocalVariableValue));
        titleSubLabelFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleSubLabelFieldReference.setBorder(new EmptyBorder(18, 0, 0, 0));

        titleContainerLocalVariableValue.add(titleLineLocalVariableValue);
        titleContainerLocalVariableValue.add(Box.createVerticalStrut(12));
        titleContainerLocalVariableValue.add(underlineWrapperLocalVariableValue);
        titleContainerLocalVariableValue.add(titleSubLabelFieldReference);

        return titleContainerLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildChartCard() {
        JPanel cardLocalVariableValue = new RoundedCardPanel();
        cardLocalVariableValue.setOpaque(false);
        cardLocalVariableValue.setLayout(new BorderLayout());
        cardLocalVariableValue.setBorder(new EmptyBorder(22, 24, 26, 24));
        cardLocalVariableValue.setPreferredSize(new Dimension(960, 600));
        cardLocalVariableValue.setMinimumSize(new Dimension(960, 600));
        cardLocalVariableValue.setMaximumSize(new Dimension(960, 600));

        JLabel cardTitleLocalVariableValue =
                new JLabel("POINTS PER MATCH WEEK", SwingConstants.CENTER);
        cardTitleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 20));
        cardTitleLocalVariableValue.setForeground(CARD_TITLE_COLOR);

        JLabel cardSubLocalVariableValue = new JLabel(
                "Each line represents one team's evolution",
                SwingConstants.CENTER);
        cardSubLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 13));
        cardSubLocalVariableValue.setForeground(CARD_BODY_COLOR);

        JPanel cardHeaderLocalVariableValue = new JPanel();
        cardHeaderLocalVariableValue.setOpaque(false);
        cardHeaderLocalVariableValue.setLayout(
                new BoxLayout(cardHeaderLocalVariableValue, BoxLayout.Y_AXIS));
        cardTitleLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardSubLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardHeaderLocalVariableValue.add(cardTitleLocalVariableValue);
        cardHeaderLocalVariableValue.add(Box.createVerticalStrut(4));
        cardHeaderLocalVariableValue.add(cardSubLocalVariableValue);
        cardHeaderLocalVariableValue.setBorder(new EmptyBorder(0, 0, 14, 0));

        chartPanelFieldReference = new LeagueManagerChartPanelComponent();
        chartPanelFieldReference.setBackground(Color.WHITE);
        chartPanelFieldReference.setOpaque(true);

        cardLocalVariableValue.add(cardHeaderLocalVariableValue, BorderLayout.NORTH);
        cardLocalVariableValue.add(chartPanelFieldReference, BorderLayout.CENTER);

        return cardLocalVariableValue;
    }


    /**
     * Actualiza el contenido.
     *
     * @param leagueReferenceDisplayNameParameterValue nombre de la liga.
     * @param teamReferenceDataParameterValue equipo que usa la operacion.
     * @param numberWeeksParameterValue numero que usa la operacion.
     * @param teamReferenceIdentifierParameterValue equipo identificador.
     * @param countIdentifiersParameterValue dato de entrada de la operacion.
     */
    public void updateChartData(String leagueReferenceDisplayNameParameterValue,
                                int[][] teamReferenceDataParameterValue,
                                int numberWeeksParameterValue,
                                String[] teamReferenceIdentifierParameterValue,
                                int countIdentifiersParameterValue) {
        if (titleSubLabelFieldReference != null) {
            titleSubLabelFieldReference.setText(
                    leagueReferenceDisplayNameParameterValue == null
                            ? "Team performance over the season"
                            : leagueReferenceDisplayNameParameterValue
                                    + "  ·  Team performance over the season"
            );
        }
        this.teamReferenceNamesFieldReference =
                teamReferenceIdentifierParameterValue == null
                        ? new String[0]
                        : teamReferenceIdentifierParameterValue;
        this.countIdentifiersFieldReference = countIdentifiersParameterValue;
        chartPanelFieldReference.updateData(
                teamReferenceDataParameterValue, numberWeeksParameterValue);
    }


    /**
     * Registra la accion.
     *
     * @param controllerHandlerParameterValue dato de entrada de la operacion.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.setActionCommand("BACK");
        configButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        configButtonFieldReference.setActionCommand("CONFIG");
    }


    /**
     * Actualiza los ligas.
     *
     * @param leaguesParameterValue ligas que usa la operacion.
     * @param listenerParameterValue listener que se registra.
     */
    public void setLeagues(List<League> leaguesParameterValue,
                           ActionListener listenerParameterValue) {
        if (leaguesPickerSlotFieldReference == null) {
            return;
        }
        leaguesPickerSlotFieldReference.removeAll();
        List<League> leaguesLocalVariableValue =
                leaguesParameterValue == null ? new ArrayList<>() : leaguesParameterValue;

        for (int indexCounterLocalVariableValue = 0;
             indexCounterLocalVariableValue < leaguesLocalVariableValue.size();
             indexCounterLocalVariableValue++) {
            League leagueReferenceLocalVariableValue =
                    leaguesLocalVariableValue.get(indexCounterLocalVariableValue);
            Rounded.RoundedButton buttonLocalVariableValue =
                    new Rounded.RoundedButton(leagueReferenceLocalVariableValue.getName(), 14);
            buttonLocalVariableValue.setActionCommand("LEAGUE_" + indexCounterLocalVariableValue);
            buttonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 13));
            buttonLocalVariableValue.setForeground(ACCENT_COLOR);
            buttonLocalVariableValue.setBackground(new Color(255, 255, 255, 235));
            buttonLocalVariableValue.setOutlineMode(ACCENT_COLOR, 2);
            buttonLocalVariableValue.setShadowEnabled(false);
            buttonLocalVariableValue.setPreferredSize(new Dimension(180, 36));
            buttonLocalVariableValue.addActionListener(listenerParameterValue);
            leaguesPickerSlotFieldReference.add(buttonLocalVariableValue);
        }
        leaguesPickerSlotFieldReference.revalidate();
        leaguesPickerSlotFieldReference.repaint();
    }


    /**
     * Actualiza el contenido.
     *
     * @param teamReferencePointsParameterValue equipo que usa la operacion.
     * @param currentWeekParameterValue actual que usa la operacion.
     */
    public void updateData(int[][] teamReferencePointsParameterValue,
                           int currentWeekParameterValue) {
        chartPanelFieldReference.updateData(
                teamReferencePointsParameterValue, currentWeekParameterValue);
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
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int widthLocalVariableValue = getWidth();
            int heightLocalVariableValue = getHeight();
            int radiusLocalVariableValue = 22;


            g2LocalVariableValue.setColor(new Color(8, 20, 46, 42));
            g2LocalVariableValue.fillRoundRect(8, 10,
                    widthLocalVariableValue - 16,
                    heightLocalVariableValue - 10,
                    radiusLocalVariableValue, radiusLocalVariableValue);


            g2LocalVariableValue.setColor(new Color(255, 255, 255, 250));
            g2LocalVariableValue.fillRoundRect(0, 0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 4,
                    radiusLocalVariableValue, radiusLocalVariableValue);


            g2LocalVariableValue.setColor(ACCENT_COLOR);
            g2LocalVariableValue.setStroke(new BasicStroke(2f));
            g2LocalVariableValue.drawRoundRect(0, 0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 5,
                    radiusLocalVariableValue, radiusLocalVariableValue);

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }


    /**
     * Agrupa la logica de el liga.
     */
    class LeagueManagerChartPanelComponent extends JPanel {
        private int[][] teamReferencePointsFieldReference;
        private int currentWeekFieldReference;
        private final Color[] teamReferenceColorsFieldReference = {
                new Color(55, 109, 230),
                new Color(218, 37, 42),
                new Color(38, 166, 91),
                new Color(245, 137, 47),
                new Color(110, 70, 220),
                new Color(0, 168, 168),
                new Color(232, 84, 134),
                new Color(82, 96, 120),
                new Color(75, 0, 130),
                new Color(46, 139, 87)
        };


        /**
         * Actualiza el contenido.
         *
         * @param teamReferencePointsParameterValue equipo que usa la operacion.
         * @param currentWeekParameterValue actual que usa la operacion.
         */
        public void updateData(int[][] teamReferencePointsParameterValue,
                               int currentWeekParameterValue) {
            this.teamReferencePointsFieldReference = teamReferencePointsParameterValue;
            this.currentWeekFieldReference = currentWeekParameterValue;
            repaint();
        }


        /**
         * Gestiona esta operacion.
         *
         * @param graphicsParameterValue dato de entrada de la operacion.
         */
        @Override
        protected void paintComponent(Graphics graphicsParameterValue) {
            super.paintComponent(graphicsParameterValue);

            Graphics2D g2LocalVariableValue =
                    (Graphics2D) graphicsParameterValue.create();
            g2LocalVariableValue.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            if (teamReferencePointsFieldReference == null
                    || teamReferencePointsFieldReference.length == 0
                    || currentWeekFieldReference < 1) {
                g2LocalVariableValue.setColor(CARD_BODY_COLOR);
                g2LocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 14));
                String emptyMsgLocalVariableValue = "No statistics available yet";
                int textWidthLocalVariableValue = g2LocalVariableValue.getFontMetrics()
                        .stringWidth(emptyMsgLocalVariableValue);
                g2LocalVariableValue.drawString(
                        emptyMsgLocalVariableValue,
                        (getWidth() - textWidthLocalVariableValue) / 2,
                        getHeight() / 2);
                g2LocalVariableValue.dispose();
                return;
            }

            int widthLocalVariableValue = getWidth();
            int heightLocalVariableValue = getHeight();
            int paddingLocalVariableValue = 60;
            int legendHeightLocalVariableValue = 60;
            int chartWidthLocalVariableValue =
                    Math.max(1, widthLocalVariableValue - 2 * paddingLocalVariableValue);
            int chartHeightLocalVariableValue =
                    Math.max(1, heightLocalVariableValue - 2 * paddingLocalVariableValue
                            - legendHeightLocalVariableValue);
            int maxYLocalVariableValue = Arrays.stream(teamReferencePointsFieldReference)
                    .flatMapToInt(Arrays::stream).max().orElse(1);
            maxYLocalVariableValue = Math.max(1, maxYLocalVariableValue);


            g2LocalVariableValue.setColor(new Color(60, 75, 105));
            g2LocalVariableValue.setStroke(new BasicStroke(1.5f));
            int xAxisYLocalVariableValue =
                    heightLocalVariableValue - paddingLocalVariableValue - legendHeightLocalVariableValue;
            g2LocalVariableValue.drawLine(paddingLocalVariableValue, xAxisYLocalVariableValue,
                    paddingLocalVariableValue, paddingLocalVariableValue);
            g2LocalVariableValue.drawLine(paddingLocalVariableValue, xAxisYLocalVariableValue,
                    widthLocalVariableValue - paddingLocalVariableValue, xAxisYLocalVariableValue);

            double xScaleLocalVariableValue = currentWeekFieldReference > 1
                    ? (double) chartWidthLocalVariableValue / (currentWeekFieldReference - 1)
                    : 0;
            double yScaleLocalVariableValue =
                    (double) chartHeightLocalVariableValue / maxYLocalVariableValue;


            g2LocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 11));
            int yTicksLocalVariableValue = 5;
            for (int yTickIndexLocalVariableValue = 0;
                 yTickIndexLocalVariableValue <= yTicksLocalVariableValue;
                 yTickIndexLocalVariableValue++) {
                int tickValueLocalVariableValue =
                        (maxYLocalVariableValue * yTickIndexLocalVariableValue)
                                / yTicksLocalVariableValue;
                int tickYLocalVariableValue =
                        xAxisYLocalVariableValue
                                - (int) (tickValueLocalVariableValue * yScaleLocalVariableValue);


                g2LocalVariableValue.setColor(new Color(225, 232, 245));
                g2LocalVariableValue.drawLine(paddingLocalVariableValue + 1, tickYLocalVariableValue,
                        widthLocalVariableValue - paddingLocalVariableValue, tickYLocalVariableValue);


                g2LocalVariableValue.setColor(new Color(60, 75, 105));
                g2LocalVariableValue.drawLine(paddingLocalVariableValue - 4, tickYLocalVariableValue,
                        paddingLocalVariableValue, tickYLocalVariableValue);
                String yLabelLocalVariableValue = String.valueOf(tickValueLocalVariableValue);
                int yLabelWidthLocalVariableValue = g2LocalVariableValue.getFontMetrics()
                        .stringWidth(yLabelLocalVariableValue);
                g2LocalVariableValue.drawString(yLabelLocalVariableValue,
                        paddingLocalVariableValue - 8 - yLabelWidthLocalVariableValue,
                        tickYLocalVariableValue + 4);
            }


            Graphics2D g2YLabelLocalVariableValue =
                    (Graphics2D) g2LocalVariableValue.create();
            g2YLabelLocalVariableValue.setColor(CARD_TITLE_COLOR);
            g2YLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 12));
            g2YLabelLocalVariableValue.rotate(-Math.PI / 2);
            g2YLabelLocalVariableValue.drawString("Points",
                    -(paddingLocalVariableValue + chartHeightLocalVariableValue / 2 + 20),
                    18);
            g2YLabelLocalVariableValue.dispose();


            int xLabelStepLocalVariableValue = Math.max(1, currentWeekFieldReference / 12);
            for (int weekTickLocalVariableValue = 1;
                 weekTickLocalVariableValue <= currentWeekFieldReference;
                 weekTickLocalVariableValue++) {
                if (weekTickLocalVariableValue != currentWeekFieldReference
                        && (weekTickLocalVariableValue - 1) % xLabelStepLocalVariableValue != 0) {
                    continue;
                }
                int tickXLocalVariableValue =
                        paddingLocalVariableValue
                                + (int) ((weekTickLocalVariableValue - 1) * xScaleLocalVariableValue);
                g2LocalVariableValue.setColor(new Color(60, 75, 105));
                g2LocalVariableValue.drawLine(tickXLocalVariableValue, xAxisYLocalVariableValue,
                        tickXLocalVariableValue, xAxisYLocalVariableValue + 4);
                String xLabelLocalVariableValue = String.valueOf(weekTickLocalVariableValue);
                int xLabelWidthLocalVariableValue = g2LocalVariableValue.getFontMetrics()
                        .stringWidth(xLabelLocalVariableValue);
                g2LocalVariableValue.drawString(xLabelLocalVariableValue,
                        tickXLocalVariableValue - xLabelWidthLocalVariableValue / 2,
                        xAxisYLocalVariableValue + 18);
            }


            g2LocalVariableValue.setColor(CARD_TITLE_COLOR);
            g2LocalVariableValue.setFont(new Font("Arial", Font.BOLD, 12));
            String xAxisTitleLocalVariableValue = "Match week";
            int xAxisTitleWidthLocalVariableValue = g2LocalVariableValue.getFontMetrics()
                    .stringWidth(xAxisTitleLocalVariableValue);
            g2LocalVariableValue.drawString(xAxisTitleLocalVariableValue,
                    paddingLocalVariableValue
                            + (chartWidthLocalVariableValue - xAxisTitleWidthLocalVariableValue) / 2,
                    xAxisYLocalVariableValue + 36);


            g2LocalVariableValue.setStroke(new BasicStroke(2.2f));
            for (int teamIndexLocalVariableValue = 0;
                 teamIndexLocalVariableValue < Math.min(
                         countIdentifiersFieldReference,
                         teamReferencePointsFieldReference.length);
                 teamIndexLocalVariableValue++) {

                Color teamColorLocalVariableValue =
                        teamReferenceColorsFieldReference[
                                teamIndexLocalVariableValue
                                        % teamReferenceColorsFieldReference.length];
                g2LocalVariableValue.setColor(teamColorLocalVariableValue);

                for (int weekLocalVariableValue = 1;
                     weekLocalVariableValue < Math.min(
                             currentWeekFieldReference,
                             teamReferencePointsFieldReference[teamIndexLocalVariableValue].length);
                     weekLocalVariableValue++) {
                    int x1LocalVariableValue = paddingLocalVariableValue
                            + (int) ((weekLocalVariableValue - 1) * xScaleLocalVariableValue);
                    int y1LocalVariableValue = xAxisYLocalVariableValue
                            - (int) (teamReferencePointsFieldReference[teamIndexLocalVariableValue][weekLocalVariableValue - 1]
                                    * yScaleLocalVariableValue);
                    int x2LocalVariableValue = paddingLocalVariableValue
                            + (int) (weekLocalVariableValue * xScaleLocalVariableValue);
                    int y2LocalVariableValue = xAxisYLocalVariableValue
                            - (int) (teamReferencePointsFieldReference[teamIndexLocalVariableValue][weekLocalVariableValue]
                                    * yScaleLocalVariableValue);
                    g2LocalVariableValue.drawLine(
                            x1LocalVariableValue, y1LocalVariableValue,
                            x2LocalVariableValue, y2LocalVariableValue);
                }


                String labelLocalVariableValue =
                        teamIndexLocalVariableValue < teamReferenceNamesFieldReference.length
                                ? teamReferenceNamesFieldReference[teamIndexLocalVariableValue]
                                : "Team " + (teamIndexLocalVariableValue + 1);
                int legendXLocalVariableValue = paddingLocalVariableValue
                        + 150 * (teamIndexLocalVariableValue % 5);
                int legendYLocalVariableValue =
                        heightLocalVariableValue - 32 - 18 * (teamIndexLocalVariableValue / 5);

                g2LocalVariableValue.setColor(teamColorLocalVariableValue);
                g2LocalVariableValue.fillOval(legendXLocalVariableValue,
                        legendYLocalVariableValue - 10, 10, 10);
                g2LocalVariableValue.setColor(CARD_TITLE_COLOR);
                g2LocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 12));
                g2LocalVariableValue.drawString(labelLocalVariableValue,
                        legendXLocalVariableValue + 16,
                        legendYLocalVariableValue);
            }

            g2LocalVariableValue.dispose();
        }
    }
}


