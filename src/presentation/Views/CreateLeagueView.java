package presentation.Views;

import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;


/**
 * Representa la vista del liga.
 */
public class CreateLeagueView extends JPanel {

    private JTextField leagueReferenceDisplayNameFieldFieldReference;
    private JTextField seasonFieldFieldReference;
    private JTextField dateFieldFieldReference;
    private JTextField startTimeFieldFieldReference;

    private Rounded.RoundedButton availableTeamsButtonFieldReference;
    private Rounded.RoundedButton backButtonFieldReference;

    /**
     * Constante para los disponibles equipos.
     */
    public static final String AVAILABLE_TEAMS_BUTTON = "AVAILABLE_TEAMS_BUTTON";
    /**
     * Constante para el vuelta.
     */
    public static final String BACK = "BACK";

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);

    private static final Color CARD_TITLE_COLOR = new Color(28, 35, 51);
    private static final Color FIELD_BACKGROUND = new Color(245, 249, 255);
    private static final Color FIELD_BORDER = new Color(190, 210, 235);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    private static final String TROPHY_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/trofeo.png");


    /**
     * Crea una instancia de el liga.
     */
    public CreateLeagueView() {
        setLayout(new BorderLayout());
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

        JPanel rightPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rightPanelLocalVariableValue.setOpaque(false);

        backButtonFieldReference = new Rounded.RoundedButton("< BACK", 18);
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
        JPanel formCardLocalVariableValue = buildFormCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);


        contentPanelLocalVariableValue.add(Box.createVerticalStrut(38));

        contentPanelLocalVariableValue.add(formCardLocalVariableValue);

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

        JPanel titleLineLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLineLocalVariableValue.setOpaque(false);

        JLabel createLabelLocalVariableValue = new JLabel("CREATE ");
        createLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        createLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        JLabel leagueLabelLocalVariableValue = new JLabel("LEAGUE");
        leagueLabelLocalVariableValue.setForeground(TITLE_WHITE);
        leagueLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        titleLineLocalVariableValue.add(createLabelLocalVariableValue);
        titleLineLocalVariableValue.add(leagueLabelLocalVariableValue);

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
                "Set up a new league",
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
    private JPanel buildFormCard() {
        JPanel cardLocalVariableValue = new RoundedCardPanel();
        cardLocalVariableValue.setOpaque(false);
        cardLocalVariableValue.setLayout(
                new BoxLayout(cardLocalVariableValue, BoxLayout.Y_AXIS)
        );


        cardLocalVariableValue.setBorder(new EmptyBorder(18, 34, 26, 34));


        cardLocalVariableValue.setPreferredSize(new Dimension(760, 600));
        cardLocalVariableValue.setMinimumSize(new Dimension(760, 600));
        cardLocalVariableValue.setMaximumSize(new Dimension(760, 600));

        JPanel trophyPanelLocalVariableValue = buildTrophyPanel();
        trophyPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabelLocalVariableValue = new JLabel("NEW LEAGUE", SwingConstants.CENTER);
        titleLabelLocalVariableValue.setForeground(CARD_TITLE_COLOR);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardLocalVariableValue.add(trophyPanelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(6));
        cardLocalVariableValue.add(titleLabelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(14));

        leagueReferenceDisplayNameFieldFieldReference = createInputField(
                cardLocalVariableValue,
                "LEAGUE NAME",
                "Enter league name"
        );

        seasonFieldFieldReference = createInputField(
                cardLocalVariableValue,
                "SEASON",
                "2024-2025"
        );

        dateFieldFieldReference = createInputField(
                cardLocalVariableValue,
                "START DATE",
                "yyyy-MM-dd"
        );

        startTimeFieldFieldReference = createInputField(
                cardLocalVariableValue,
                "START TIME",
                "HH:mm"
        );

        cardLocalVariableValue.add(Box.createVerticalStrut(8));

        availableTeamsButtonFieldReference = new Rounded.RoundedButton("AVAILABLE TEAMS", 18);
        availableTeamsButtonFieldReference.setActionCommand(AVAILABLE_TEAMS_BUTTON);
        availableTeamsButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 15));
        availableTeamsButtonFieldReference.setForeground(ACCENT_COLOR);
        availableTeamsButtonFieldReference.setBackground(new Color(255, 255, 255, 235));
        availableTeamsButtonFieldReference.setOutlineMode(ACCENT_COLOR, 2);
        availableTeamsButtonFieldReference.setShadowEnabled(false);


        availableTeamsButtonFieldReference.setPreferredSize(new Dimension(620, 46));
        availableTeamsButtonFieldReference.setMaximumSize(new Dimension(620, 46));
        availableTeamsButtonFieldReference.setMinimumSize(new Dimension(620, 46));
        availableTeamsButtonFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardLocalVariableValue.add(availableTeamsButtonFieldReference);

        return cardLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildTrophyPanel() {
        JPanel trophyPanelLocalVariableValue = new JPanel(new GridBagLayout());
        trophyPanelLocalVariableValue.setOpaque(false);


        trophyPanelLocalVariableValue.setPreferredSize(new Dimension(138, 138));
        trophyPanelLocalVariableValue.setMaximumSize(new Dimension(138, 138));
        trophyPanelLocalVariableValue.setMinimumSize(new Dimension(138, 138));

        JLabel trophyLabelLocalVariableValue = new JLabel();
        trophyLabelLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
        trophyLabelLocalVariableValue.setVerticalAlignment(SwingConstants.CENTER);

        try {
            Image trophyImageLocalVariableValue =
                    new ImageIcon(TROPHY_IMAGE_PATH).getImage();

            Image scaledTrophyLocalVariableValue =
                    trophyImageLocalVariableValue.getScaledInstance(
                            132,
                            132,
                            Image.SCALE_SMOOTH
                    );

            trophyLabelLocalVariableValue.setIcon(
                    new ImageIcon(scaledTrophyLocalVariableValue)
            );
        } catch (Exception ignoredExceptionParameterValue) {
            trophyLabelLocalVariableValue.setText("ÃƒÂ°Ã…Â¸Ã‚ÂÃ¢â‚¬Â ");
            trophyLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 62));
        }

        trophyPanelLocalVariableValue.add(trophyLabelLocalVariableValue);

        return trophyPanelLocalVariableValue;
    }


    /**
     * Crea el contenido.
     *
     * @param parentPanelParameterValue dato de entrada de la operacion.
     * @param labelTextParameterValue texto que usa la operacion.
     * @param placeholderParameterValue dato de entrada de la operacion.
     * @return elemento creado por el metodo.
     */
    private JTextField createInputField(JPanel parentPanelParameterValue,
                                        String labelTextParameterValue,
                                        String placeholderParameterValue) {
        JLabel labelLocalVariableValue = new JLabel(labelTextParameterValue);
        labelLocalVariableValue.setForeground(new Color(75, 88, 115));
        labelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 12));
        labelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        Rounded.RoundedTextField textFieldLocalVariableValue =
                new Rounded.RoundedTextField(24, 16);

        textFieldLocalVariableValue.setPlaceholder(placeholderParameterValue);
        textFieldLocalVariableValue.setBackground(FIELD_BACKGROUND);
        textFieldLocalVariableValue.setBorderColor(FIELD_BORDER);
        textFieldLocalVariableValue.setFocusBorderColor(ACCENT_COLOR);
        textFieldLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 15));


        textFieldLocalVariableValue.setPreferredSize(new Dimension(620, 42));
        textFieldLocalVariableValue.setMaximumSize(new Dimension(620, 42));
        textFieldLocalVariableValue.setMinimumSize(new Dimension(620, 42));
        textFieldLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        parentPanelParameterValue.add(labelLabelWrapper(labelLocalVariableValue));
        parentPanelParameterValue.add(Box.createVerticalStrut(6));
        parentPanelParameterValue.add(textFieldLocalVariableValue);
        parentPanelParameterValue.add(Box.createVerticalStrut(12));

        return textFieldLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param labelParameterValue dato de entrada de la operacion.
     * @return resultado de la operacion.
     */
    private JPanel labelLabelWrapper(JLabel labelParameterValue) {
        JPanel wrapperLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapperLocalVariableValue.setOpaque(false);
        wrapperLocalVariableValue.setPreferredSize(new Dimension(620, 16));
        wrapperLocalVariableValue.setMaximumSize(new Dimension(620, 16));
        wrapperLocalVariableValue.setMinimumSize(new Dimension(620, 16));
        wrapperLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapperLocalVariableValue.add(labelParameterValue);
        return wrapperLocalVariableValue;
    }


    /**
     * Devuelve el liga nombre.
     *
     * @return el liga nombre.
     */
    public String getLeagueName() {
        return leagueReferenceDisplayNameFieldFieldReference.getText();
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public String getDate() {
        return dateFieldFieldReference.getText();
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public String getStartTime() {
        return startTimeFieldFieldReference.getText();
    }


    /**
     * Registra la accion.
     *
     * @param controllerHandlerParameterValue dato de entrada de la operacion.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        availableTeamsButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
    }


    /**
     * Gestiona esta operacion.
     */
    public void clearForm() {
        leagueReferenceDisplayNameFieldFieldReference.setText("");
        seasonFieldFieldReference.setText("");
        dateFieldFieldReference.setText("");
        startTimeFieldFieldReference.setText("");
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
                "Create League",
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
}


