package presentation.Views;

import bussines.objects.League;
import bussines.objects.LeagueListEntry;
import presentation.ControllerViews.AvailableLeaguesController;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


/**
 * Representa la vista de las ligas disponibles.
 */
public class AvailableLeaguesView extends JPanel {

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);

    private static final Color CARD_TITLE_COLOR = new Color(28, 35, 51);
    private static final Color CARD_BODY_COLOR = new Color(100, 112, 135);
    private static final Color FIELD_BACKGROUND = new Color(245, 249, 255);
    private static final Color FIELD_BORDER = new Color(190, 210, 235);

    private static final Color STATUS_PENDING_COLOR = new Color(245, 137, 47);
    private static final Color STATUS_RUNNING_COLOR = new Color(55, 109, 230);
    private static final Color STATUS_FINISHED_COLOR = new Color(38, 166, 91);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    private static final String LEAGUES_ICON_PATH =
            ProjectPathResolver.resolveProjectPath("photos/Leagues.png");
    private ActionListener backControllerHandlerFieldReference;

    private JPanel leaguesListPanelFieldReference;

    private Rounded.RoundedButton backButtonFieldReference;


    /**
     * Crea una instancia de los disponibles ligas.
     */
    public AvailableLeaguesView() {
        setLayout(new BorderLayout());
        add(buildBackgroundPanel(), BorderLayout.CENTER);
    }


    /**
     * Actualiza el vuelta.
     *
     * @param listenerParameterValue listener que se registra.
     */
    public void setBackButtonListener(ActionListener listenerParameterValue) {
        this.backControllerHandlerFieldReference = listenerParameterValue;
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
        backButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 15));
        backButtonFieldReference.setForeground(ACCENT_COLOR);
        backButtonFieldReference.setBackground(new Color(255, 255, 255, 230));
        backButtonFieldReference.setOutlineMode(ACCENT_COLOR, 2);
        backButtonFieldReference.setShadowEnabled(false);
        backButtonFieldReference.setPreferredSize(new Dimension(145, 44));

        backButtonFieldReference.addActionListener(eventArgumentParameterValue -> {
            if (backControllerHandlerFieldReference != null) {
                backControllerHandlerFieldReference.actionPerformed(
                        new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "BACK")
                );
            }
        });

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
        JPanel listCardLocalVariableValue = buildLeaguesCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        listCardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(38));
        contentPanelLocalVariableValue.add(listCardLocalVariableValue);

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

        JLabel availableLabelLocalVariableValue = new JLabel("AVAILABLE ");
        availableLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        availableLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        JLabel leaguesLabelLocalVariableValue = new JLabel("LEAGUES");
        leaguesLabelLocalVariableValue.setForeground(TITLE_WHITE);
        leaguesLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        titleLineLocalVariableValue.add(availableLabelLocalVariableValue);
        titleLineLocalVariableValue.add(leaguesLabelLocalVariableValue);

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
                "Browse and select a league",
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
     * Construye los ligas.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildLeaguesCard() {
        JPanel cardLocalVariableValue = new RoundedCardPanel();
        cardLocalVariableValue.setOpaque(false);
        cardLocalVariableValue.setLayout(
                new BoxLayout(cardLocalVariableValue, BoxLayout.Y_AXIS)
        );
        cardLocalVariableValue.setBorder(new EmptyBorder(24, 34, 30, 34));

        cardLocalVariableValue.setPreferredSize(new Dimension(820, 560));
        cardLocalVariableValue.setMinimumSize(new Dimension(820, 560));
        cardLocalVariableValue.setMaximumSize(new Dimension(820, 560));

        JPanel iconPanelLocalVariableValue = buildLeaguesIconPanel();
        iconPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabelLocalVariableValue =
                new JLabel("LEAGUE LIST", SwingConstants.CENTER);
        titleLabelLocalVariableValue.setForeground(CARD_TITLE_COLOR);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel helperLabelLocalVariableValue = new JLabel(
                "Double click or select one league to see its details",
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
        cardLocalVariableValue.add(buildTableHeader());
        cardLocalVariableValue.add(Box.createVerticalStrut(8));
        cardLocalVariableValue.add(createLeaguesScrollPane());

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

        iconPanelLocalVariableValue.setPreferredSize(new Dimension(120, 120));
        iconPanelLocalVariableValue.setMaximumSize(new Dimension(120, 120));
        iconPanelLocalVariableValue.setMinimumSize(new Dimension(120, 120));

        JLabel iconLabelLocalVariableValue = new JLabel();
        iconLabelLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabelLocalVariableValue.setVerticalAlignment(SwingConstants.CENTER);

        try {
            Image iconImageLocalVariableValue =
                    new ImageIcon(LEAGUES_ICON_PATH).getImage();

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
            iconLabelLocalVariableValue.setText("🏆");
            iconLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 62));
        }

        iconPanelLocalVariableValue.add(iconLabelLocalVariableValue);

        return iconPanelLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildTableHeader() {
        JPanel headerPanelLocalVariableValue = new JPanel(new GridLayout(1, 3));
        headerPanelLocalVariableValue.setOpaque(false);
        headerPanelLocalVariableValue.setMaximumSize(new Dimension(720, 36));
        headerPanelLocalVariableValue.setPreferredSize(new Dimension(720, 36));
        headerPanelLocalVariableValue.setMinimumSize(new Dimension(720, 36));
        headerPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] headersLocalVariableValue = {"LEAGUE", "TEAMS", "STATUS"};

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
     * Crea los ligas.
     *
     * @return elemento creado por el metodo.
     */
    private JScrollPane createLeaguesScrollPane() {
        leaguesListPanelFieldReference = new JPanel();
        leaguesListPanelFieldReference.setLayout(
                new BoxLayout(leaguesListPanelFieldReference, BoxLayout.Y_AXIS)
        );
        leaguesListPanelFieldReference.setBackground(FIELD_BACKGROUND);
        leaguesListPanelFieldReference.setBorder(new EmptyBorder(10, 12, 10, 12));

        JScrollPane scrollPaneLocalVariableValue =
                new JScrollPane(leaguesListPanelFieldReference);

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

        scrollPaneLocalVariableValue.setPreferredSize(new Dimension(720, 245));
        scrollPaneLocalVariableValue.setMaximumSize(new Dimension(720, 245));
        scrollPaneLocalVariableValue.setMinimumSize(new Dimension(720, 245));
        scrollPaneLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        return scrollPaneLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param entriesParameterValue dato de entrada de la operacion.
     * @param isAdminParameterValue administrador que usa la operacion.
     * @param listenerParameterValue listener que se registra.
     */
    public void displayLeagues(List<LeagueListEntry> entriesParameterValue,
                               boolean isAdminParameterValue,
                               ActionListener listenerParameterValue) {
        leaguesListPanelFieldReference.removeAll();

        if (entriesParameterValue == null || entriesParameterValue.isEmpty()) {
            JLabel noLeaguesLabelLocalVariableValue =
                    new JLabel("No leagues available.", SwingConstants.CENTER);
            noLeaguesLabelLocalVariableValue.setFont(new Font("Arial", Font.ITALIC, 16));
            noLeaguesLabelLocalVariableValue.setForeground(new Color(120, 132, 155));
            noLeaguesLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
            noLeaguesLabelLocalVariableValue.setBorder(
                    BorderFactory.createEmptyBorder(70, 0, 20, 0)
            );

            leaguesListPanelFieldReference.add(noLeaguesLabelLocalVariableValue);
        } else {
            for (int indexCounterLocalVariableValue = 0;
                 indexCounterLocalVariableValue < entriesParameterValue.size();
                 indexCounterLocalVariableValue++) {

                LeagueListEntry entryLocalVariableValue =
                        entriesParameterValue.get(indexCounterLocalVariableValue);

                leaguesListPanelFieldReference.add(
                        createLeagueRow(entryLocalVariableValue, listenerParameterValue)
                );

                if (indexCounterLocalVariableValue < entriesParameterValue.size() - 1) {
                    leaguesListPanelFieldReference.add(Box.createVerticalStrut(8));
                }
            }
        }

        leaguesListPanelFieldReference.revalidate();
        leaguesListPanelFieldReference.repaint();
    }


    /**
     * Crea el liga.
     *
     * @param entryParameterValue dato de entrada de la operacion.
     * @param listenerParameterValue listener que se registra.
     * @return elemento creado por el metodo.
     */
    private JPanel createLeagueRow(LeagueListEntry entryParameterValue,
                                   ActionListener listenerParameterValue) {
        League leagueReferenceLocalVariableValue = entryParameterValue.getLeague();

        JPanel rowPanelLocalVariableValue = new LeagueRowPanel();
        rowPanelLocalVariableValue.setLayout(new GridLayout(1, 3));
        rowPanelLocalVariableValue.setOpaque(false);
        rowPanelLocalVariableValue.setBorder(new EmptyBorder(10, 16, 10, 16));
        rowPanelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        rowPanelLocalVariableValue.setPreferredSize(new Dimension(680, 48));
        rowPanelLocalVariableValue.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rowPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel leagueNameLabelLocalVariableValue = createRowLabel(
                leagueReferenceLocalVariableValue == null
                        ? "-"
                        : leagueReferenceLocalVariableValue.getName(),
                Font.BOLD,
                CARD_TITLE_COLOR
        );

        JLabel teamCountLabelLocalVariableValue = createRowLabel(
                String.valueOf(entryParameterValue.getTeamCount()),
                Font.PLAIN,
                new Color(54, 66, 87)
        );

        JLabel statusLabelLocalVariableValue = createStatusLabel(
                entryParameterValue.getStatusLabel()
        );

        rowPanelLocalVariableValue.add(leagueNameLabelLocalVariableValue);
        rowPanelLocalVariableValue.add(teamCountLabelLocalVariableValue);
        rowPanelLocalVariableValue.add(statusLabelLocalVariableValue);

        rowPanelLocalVariableValue.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent eventArgumentParameterValue) {
                if (listenerParameterValue instanceof AvailableLeaguesController) {
                    ((AvailableLeaguesController) listenerParameterValue)
                            .openLeagueDetails(leagueReferenceLocalVariableValue);
                } else if (listenerParameterValue != null) {
                    listenerParameterValue.actionPerformed(
                            new ActionEvent(
                                    leagueReferenceLocalVariableValue,
                                    ActionEvent.ACTION_PERFORMED,
                                    "OPEN_LEAGUE"
                            )
                    );
                }
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
                new JLabel(textParameterValue, SwingConstants.CENTER);
        labelLocalVariableValue.setFont(new Font("Arial", styleParameterValue, 14));
        labelLocalVariableValue.setForeground(colorParameterValue);
        return labelLocalVariableValue;
    }


    /**
     * Crea el contenido.
     *
     * @param statusParameterValue dato de entrada de la operacion.
     * @return elemento creado por el metodo.
     */
    private JLabel createStatusLabel(String statusParameterValue) {
        JLabel labelLocalVariableValue =
                new JLabel(statusParameterValue, SwingConstants.CENTER);

        labelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 13));
        labelLocalVariableValue.setOpaque(false);

        if (statusParameterValue == null) {
            labelLocalVariableValue.setForeground(CARD_BODY_COLOR);
        } else if (statusParameterValue.equalsIgnoreCase(LeagueListEntry.STATUS_FINISHED)) {
            labelLocalVariableValue.setForeground(STATUS_FINISHED_COLOR);
        } else if (statusParameterValue.equalsIgnoreCase(LeagueListEntry.STATUS_PENDING)) {
            labelLocalVariableValue.setForeground(STATUS_PENDING_COLOR);
        } else {
            labelLocalVariableValue.setForeground(STATUS_RUNNING_COLOR);
        }

        return labelLocalVariableValue;
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
                "Available leagues",
                JOptionPane.WARNING_MESSAGE
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
     * Agrupa la logica de el liga.
     */
    private static class LeagueRowPanel extends JPanel {
        private boolean hoverFieldReference = false;


        /**
         * Crea una instancia de el liga.
         */
        LeagueRowPanel() {
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
                            ? new Color(236, 242, 255)
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
                            ? ACCENT_COLOR
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


