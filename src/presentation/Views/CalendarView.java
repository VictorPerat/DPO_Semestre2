package presentation.Views;

import bussines.objects.Game;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * Representa la vista del calendario.
 */
public class CalendarView extends JPanel {

    /**
     * Constante para el vuelta.
     */
    public static final String BACK = "BACK";
    /**
     * Constante para el configuracion.
     */
    public static final String CONFIG = "CONFIG";


    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);
    private static final Color CARD_TITLE_COLOR = new Color(28, 35, 51);
    private static final Color CARD_BODY_COLOR = new Color(100, 112, 135);
    private static final Color FIELD_BORDER = new Color(190, 210, 235);

    private static final Color ROW_ALT = new Color(245, 249, 255);
    private static final Color ROW_BASE = Color.WHITE;

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private ArrayList<String> teamsFieldReference = new ArrayList<>();
    private ArrayList<Game> gamesFieldReference = new ArrayList<>();

    private Rounded.RoundedButton backButtonFieldReference;
    private JButton configButtonFieldReference;
    private JPanel roundsPanelFieldReference;
    private JPanel matchesPanelFieldReference;
    private JLabel roundTitleLabelFieldReference;


    /**
     * Crea una instancia de el calendario.
     */
    public CalendarView() {
        setLayout(new BorderLayout());


        configButtonFieldReference = new JButton();
        configButtonFieldReference.setVisible(false);

        add(buildBackgroundPanel(), BorderLayout.CENTER);
    }


    /**
     * Crea una instancia de el calendario.
     *
     * @param teamsParameterValue equipos que usa la operacion.
     * @param gamesParameterValue partidos que usa la operacion.
     */
    public CalendarView(ArrayList<String> teamsParameterValue,
                        ArrayList<Game> gamesParameterValue) {
        this();
        loadCalendar(teamsParameterValue, gamesParameterValue);
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
                new BoxLayout(contentPanelLocalVariableValue, BoxLayout.Y_AXIS));
        contentPanelLocalVariableValue.setBorder(new EmptyBorder(0, 50, 16, 50));

        JPanel titleBlockLocalVariableValue = buildTitleBlock();
        JPanel cardLocalVariableValue = buildCalendarCard();

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

        JLabel leagueLabelLocalVariableValue = new JLabel("LEAGUE ");
        leagueLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        leagueLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue));

        JLabel calendarLabelLocalVariableValue = new JLabel("CALENDAR");
        calendarLabelLocalVariableValue.setForeground(TITLE_WHITE);
        calendarLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue));

        titleLineLocalVariableValue.add(leagueLabelLocalVariableValue);
        titleLineLocalVariableValue.add(calendarLabelLocalVariableValue);

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
                "Match days and scheduled fixtures",
                SwingConstants.CENTER);
        subtitleLabelLocalVariableValue.setForeground(SUBTITLE_WHITE);
        subtitleLabelLocalVariableValue.setFont(
                new Font("Arial", Font.PLAIN, subtitleSizeLocalVariableValue));
        subtitleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabelLocalVariableValue.setBorder(new EmptyBorder(18, 0, 0, 0));

        titleContainerLocalVariableValue.add(titleLineLocalVariableValue);
        titleContainerLocalVariableValue.add(Box.createVerticalStrut(12));
        titleContainerLocalVariableValue.add(underlineWrapperLocalVariableValue);
        titleContainerLocalVariableValue.add(subtitleLabelLocalVariableValue);

        return titleContainerLocalVariableValue;
    }


    /**
     * Construye el calendario.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildCalendarCard() {
        JPanel cardLocalVariableValue = new RoundedCardPanel();
        cardLocalVariableValue.setOpaque(false);
        cardLocalVariableValue.setLayout(new BorderLayout(0, 12));
        cardLocalVariableValue.setBorder(new EmptyBorder(22, 30, 28, 30));
        cardLocalVariableValue.setPreferredSize(new Dimension(900, 620));
        cardLocalVariableValue.setMinimumSize(new Dimension(900, 620));
        cardLocalVariableValue.setMaximumSize(new Dimension(900, 620));


        JLabel cardTitleLocalVariableValue =
                new JLabel("MATCH SCHEDULE", SwingConstants.CENTER);
        cardTitleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 22));
        cardTitleLocalVariableValue.setForeground(CARD_TITLE_COLOR);

        JLabel cardSubLocalVariableValue = new JLabel(
                "Pick a match week to see its fixtures",
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


        roundsPanelFieldReference = new JPanel();
        roundsPanelFieldReference.setLayout(
                new BoxLayout(roundsPanelFieldReference, BoxLayout.X_AXIS));
        roundsPanelFieldReference.setOpaque(false);
        roundsPanelFieldReference.setBorder(new EmptyBorder(4, 4, 4, 4));

        JScrollPane roundsScrollLocalVariableValue =
                new JScrollPane(roundsPanelFieldReference);
        roundsScrollLocalVariableValue.setOpaque(false);
        roundsScrollLocalVariableValue.getViewport().setOpaque(false);
        roundsScrollLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        roundsScrollLocalVariableValue.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        roundsScrollLocalVariableValue.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        roundsScrollLocalVariableValue.setPreferredSize(new Dimension(840, 64));
        roundsScrollLocalVariableValue.setMinimumSize(new Dimension(840, 64));
        roundsScrollLocalVariableValue.setMaximumSize(new Dimension(840, 64));
        roundsScrollLocalVariableValue.getHorizontalScrollBar().setUnitIncrement(20);


        roundTitleLabelFieldReference = new JLabel("MATCHES", SwingConstants.LEFT);
        roundTitleLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 16));
        roundTitleLabelFieldReference.setForeground(Color.WHITE);
        roundTitleLabelFieldReference.setOpaque(true);
        roundTitleLabelFieldReference.setBackground(new Color(34, 64, 110));
        roundTitleLabelFieldReference.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 3, 0, ACCENT_COLOR),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)));


        matchesPanelFieldReference = new JPanel();
        matchesPanelFieldReference.setLayout(
                new BoxLayout(matchesPanelFieldReference, BoxLayout.Y_AXIS));
        matchesPanelFieldReference.setBackground(ROW_BASE);
        matchesPanelFieldReference.setBorder(new EmptyBorder(8, 0, 8, 0));

        JScrollPane matchesScrollLocalVariableValue =
                new JScrollPane(matchesPanelFieldReference);
        matchesScrollLocalVariableValue.setBorder(
                BorderFactory.createLineBorder(FIELD_BORDER, 1));
        matchesScrollLocalVariableValue.getViewport().setBackground(ROW_BASE);
        matchesScrollLocalVariableValue.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        matchesScrollLocalVariableValue.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        matchesScrollLocalVariableValue.getVerticalScrollBar().setUnitIncrement(16);

        JPanel matchesContainerLocalVariableValue = new JPanel(new BorderLayout());
        matchesContainerLocalVariableValue.setOpaque(false);
        matchesContainerLocalVariableValue.add(roundTitleLabelFieldReference, BorderLayout.NORTH);
        matchesContainerLocalVariableValue.add(matchesScrollLocalVariableValue, BorderLayout.CENTER);


        JPanel topAreaLocalVariableValue = new JPanel();
        topAreaLocalVariableValue.setOpaque(false);
        topAreaLocalVariableValue.setLayout(
                new BoxLayout(topAreaLocalVariableValue, BoxLayout.Y_AXIS));
        cardHeaderLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundsScrollLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        topAreaLocalVariableValue.add(cardHeaderLocalVariableValue);
        topAreaLocalVariableValue.add(Box.createVerticalStrut(14));
        topAreaLocalVariableValue.add(roundsScrollLocalVariableValue);

        cardLocalVariableValue.add(topAreaLocalVariableValue, BorderLayout.NORTH);
        cardLocalVariableValue.add(matchesContainerLocalVariableValue, BorderLayout.CENTER);

        return cardLocalVariableValue;
    }


    /**
     * Carga el calendario.
     *
     * @param teamsParameterValue equipos que usa la operacion.
     * @param gamesParameterValue partidos que usa la operacion.
     */
    public void loadCalendar(ArrayList<String> teamsParameterValue,
                             ArrayList<Game> gamesParameterValue) {
        teamsFieldReference = teamsParameterValue == null ? new ArrayList<>() : teamsParameterValue;
        gamesFieldReference = gamesParameterValue == null ? new ArrayList<>() : gamesParameterValue;
        rebuildRounds();
        if (!gamesFieldReference.isEmpty()) {
            showMatchesForRound(gamesFieldReference.get(0).getJornada());
        } else {
            showMatchesForRound(1);
        }
    }


    /**
     * Gestiona esta operacion.
     */
    private void rebuildRounds() {
        roundsPanelFieldReference.removeAll();
        int totalRoundsLocalVariableValue = 0;
        for (Game gameEntityLocalVariableValue : gamesFieldReference) {
            totalRoundsLocalVariableValue = Math.max(
                    totalRoundsLocalVariableValue,
                    gameEntityLocalVariableValue.getJornada());
        }
        if (totalRoundsLocalVariableValue == 0 && teamsFieldReference.size() > 1) {
            totalRoundsLocalVariableValue = 2 * (teamsFieldReference.size() - 1);
        }

        for (int indexCounterLocalVariableValue = 1;
             indexCounterLocalVariableValue <= totalRoundsLocalVariableValue;
             indexCounterLocalVariableValue++) {

            final int roundNumberLocalVariableValue = indexCounterLocalVariableValue;

            Rounded.RoundedButton roundButtonLocalVariableValue =
                    new Rounded.RoundedButton(
                            "ROUND " + indexCounterLocalVariableValue, 14);
            roundButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 13));
            roundButtonLocalVariableValue.setForeground(ACCENT_COLOR);
            roundButtonLocalVariableValue.setBackground(new Color(255, 255, 255, 235));
            roundButtonLocalVariableValue.setOutlineMode(ACCENT_COLOR, 2);
            roundButtonLocalVariableValue.setShadowEnabled(false);
            roundButtonLocalVariableValue.setPreferredSize(new Dimension(120, 38));
            roundButtonLocalVariableValue.setMinimumSize(new Dimension(120, 38));
            roundButtonLocalVariableValue.setMaximumSize(new Dimension(120, 38));
            roundButtonLocalVariableValue.addActionListener(
                    eventArgumentParameterValue ->
                            showMatchesForRound(roundNumberLocalVariableValue));

            if (indexCounterLocalVariableValue > 1) {
                roundsPanelFieldReference.add(Box.createRigidArea(new Dimension(8, 0)));
            }
            roundsPanelFieldReference.add(roundButtonLocalVariableValue);
        }

        roundsPanelFieldReference.revalidate();
        roundsPanelFieldReference.repaint();
    }


    /**
     * Muestra los partidos.
     *
     * @param roundNumericValueParameterValue dato de entrada de la operacion.
     */
    private void showMatchesForRound(int roundNumericValueParameterValue) {
        matchesPanelFieldReference.removeAll();
        roundTitleLabelFieldReference.setText(
                "MATCHES  ·  ROUND " + roundNumericValueParameterValue);

        String[][] roundMatchesLocalVariableValue =
                generateMatchesForRound(roundNumericValueParameterValue);

        if (roundMatchesLocalVariableValue.length == 0) {
            JLabel emptyLabelLocalVariableValue =
                    new JLabel("No matches for this round.", SwingConstants.CENTER);
            emptyLabelLocalVariableValue.setForeground(CARD_BODY_COLOR);
            emptyLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 14));
            emptyLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyLabelLocalVariableValue.setBorder(new EmptyBorder(40, 0, 40, 0));
            matchesPanelFieldReference.add(emptyLabelLocalVariableValue);
        }

        for (int indexCounterLocalVariableValue = 0;
             indexCounterLocalVariableValue < roundMatchesLocalVariableValue.length;
             indexCounterLocalVariableValue++) {
            String[] matchInformationLocalVariableValue =
                    roundMatchesLocalVariableValue[indexCounterLocalVariableValue];
            matchesPanelFieldReference.add(buildMatchRow(
                    matchInformationLocalVariableValue,
                    indexCounterLocalVariableValue));
        }


        matchesPanelFieldReference.add(Box.createVerticalGlue());

        matchesPanelFieldReference.revalidate();
        matchesPanelFieldReference.repaint();
    }


    /**
     * Construye el partido.
     *
     * @param matchInformationParameterValue partido que usa la operacion.
     * @param indexCounterParameterValue dato de entrada de la operacion.
     * @return resultado de la operacion.
     */
    private JPanel buildMatchRow(String[] matchInformationParameterValue,
                                 int indexCounterParameterValue) {
        JPanel rowLocalVariableValue = new JPanel(new BorderLayout(16, 0));
        rowLocalVariableValue.setOpaque(true);
        rowLocalVariableValue.setBackground(
                indexCounterParameterValue % 2 == 0 ? ROW_BASE : ROW_ALT);
        rowLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, FIELD_BORDER),
                BorderFactory.createEmptyBorder(14, 22, 14, 22)));
        rowLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        rowLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);


        JLabel orderLabelLocalVariableValue =
                new JLabel(String.valueOf(indexCounterParameterValue + 1),
                        SwingConstants.CENTER);
        orderLabelLocalVariableValue.setOpaque(true);
        orderLabelLocalVariableValue.setBackground(ACCENT_COLOR);
        orderLabelLocalVariableValue.setForeground(Color.WHITE);
        orderLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 13));
        orderLabelLocalVariableValue.setPreferredSize(new Dimension(28, 28));
        orderLabelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));

        JPanel orderWrapperLocalVariableValue =
                new JPanel(new GridBagLayout());
        orderWrapperLocalVariableValue.setOpaque(false);
        orderWrapperLocalVariableValue.setPreferredSize(new Dimension(40, 40));
        orderWrapperLocalVariableValue.add(orderLabelLocalVariableValue);


        JLabel teamsLabelLocalVariableValue =
                new JLabel(matchInformationParameterValue[0]);
        teamsLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 16));
        teamsLabelLocalVariableValue.setForeground(CARD_TITLE_COLOR);


        JLabel detailsLabelLocalVariableValue =
                new JLabel(matchInformationParameterValue[1].isEmpty()
                        ? "TBD"
                        : matchInformationParameterValue[1]);
        detailsLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 13));
        detailsLabelLocalVariableValue.setForeground(CARD_BODY_COLOR);
        detailsLabelLocalVariableValue.setHorizontalAlignment(SwingConstants.RIGHT);

        rowLocalVariableValue.add(orderWrapperLocalVariableValue, BorderLayout.WEST);
        rowLocalVariableValue.add(teamsLabelLocalVariableValue, BorderLayout.CENTER);
        rowLocalVariableValue.add(detailsLabelLocalVariableValue, BorderLayout.EAST);

        return rowLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param roundNumericValueParameterValue dato de entrada de la operacion.
     * @return resultado de la operacion.
     */
    private String[][] generateMatchesForRound(int roundNumericValueParameterValue) {
        List<String[]> roundMatchesLocalVariableValue = new ArrayList<>();
        for (Game gameEntityLocalVariableValue : gamesFieldReference) {
            if (gameEntityLocalVariableValue.getJornada() == roundNumericValueParameterValue) {
                roundMatchesLocalVariableValue.add(createMatch(gameEntityLocalVariableValue));
            }
        }
        return roundMatchesLocalVariableValue.toArray(new String[0][]);
    }


    /**
     * Crea el partido.
     *
     * @param gameEntityParameterValue partido que usa la operacion.
     * @return elemento creado por el metodo.
     */
    private String[] createMatch(Game gameEntityParameterValue) {
        String dateTimeLocalVariableValue = gameEntityParameterValue.getData() == null
                ? ""
                : gameEntityParameterValue.getData().format(DATE_TIME_FORMATTER);
        return new String[] {
                gameEntityParameterValue.getNomLocal()
                        + "  vs  "
                        + gameEntityParameterValue.getNomVisitant(),
                dateTimeLocalVariableValue
        };
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
}


