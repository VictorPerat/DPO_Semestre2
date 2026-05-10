package presentation.Views;

import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Consumer;


/**
 * Representa la vista del equipo.
 */
public class CreateTeamView extends JPanel {

    private static final String TEAMS_DIR = "data/teams";

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);

    private static final Color CARD_TITLE_COLOR = new Color(28, 35, 51);
    private static final Color FIELD_BACKGROUND = new Color(245, 249, 255);
    private static final Color FIELD_BORDER = new Color(190, 210, 235);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    private static final String TEAM_ICON_PATH =
            ProjectPathResolver.resolveProjectPath("photos/create_team.png");

    private ActionListener backButtonListenerFieldReference;
    private ActionListener validateListenerFieldReference;
    private Consumer<File> fileUploadListenerFieldReference;

    private JPanel filesPanelFieldReference;

    private Rounded.RoundedButton backButtonFieldReference;
    private Rounded.RoundedButton validateButtonFieldReference;
    private Rounded.RoundedButton uploadButtonFieldReference;


    /**
     * Crea una instancia de el equipo.
     */
    public CreateTeamView() {
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
        backButtonFieldReference.setActionCommand("BACK");
        backButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 15));
        backButtonFieldReference.setForeground(ACCENT_COLOR);
        backButtonFieldReference.setBackground(new Color(255, 255, 255, 230));
        backButtonFieldReference.setOutlineMode(ACCENT_COLOR, 2);
        backButtonFieldReference.setShadowEnabled(false);
        backButtonFieldReference.setPreferredSize(new Dimension(145, 44));

        backButtonFieldReference.addActionListener(eventArgumentParameterValue -> {
            if (backButtonListenerFieldReference != null) {
                backButtonListenerFieldReference.actionPerformed(eventArgumentParameterValue);
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

        JLabel teamLabelLocalVariableValue = new JLabel("TEAM");
        teamLabelLocalVariableValue.setForeground(TITLE_WHITE);
        teamLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        titleLineLocalVariableValue.add(createLabelLocalVariableValue);
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
                "Import a new team from JSON",
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
        cardLocalVariableValue.setBorder(new EmptyBorder(24, 34, 30, 34));


        cardLocalVariableValue.setPreferredSize(new Dimension(760, 600));
        cardLocalVariableValue.setMinimumSize(new Dimension(760, 600));
        cardLocalVariableValue.setMaximumSize(new Dimension(760, 600));

        JPanel iconPanelLocalVariableValue = buildTeamIconPanel();
        iconPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabelLocalVariableValue = new JLabel("NEW TEAM", SwingConstants.CENTER);
        titleLabelLocalVariableValue.setForeground(CARD_TITLE_COLOR);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel helperLabelLocalVariableValue = new JLabel(
                "Choose one or more JSON files from data/teams",
                SwingConstants.CENTER
        );
        helperLabelLocalVariableValue.setForeground(new Color(100, 112, 135));
        helperLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 14));
        helperLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardLocalVariableValue.add(iconPanelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(8));
        cardLocalVariableValue.add(titleLabelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(8));
        cardLocalVariableValue.add(helperLabelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(18));

        JScrollPane filesScrollPaneLocalVariableValue = createFilesScrollPane();
        filesScrollPaneLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardLocalVariableValue.add(filesScrollPaneLocalVariableValue);

        cardLocalVariableValue.add(Box.createVerticalStrut(18));

        validateButtonFieldReference = new Rounded.RoundedButton("VALIDATE AND CONTINUE", 18);
        validateButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 15));
        validateButtonFieldReference.setForeground(Color.WHITE);
        validateButtonFieldReference.setBackground(new Color(30, 154, 86));
        validateButtonFieldReference.setGradientColors(
                new Color(40, 178, 100),
                new Color(20, 130, 70)
        );
        validateButtonFieldReference.setPreferredSize(new Dimension(620, 46));
        validateButtonFieldReference.setMaximumSize(new Dimension(620, 46));
        validateButtonFieldReference.setMinimumSize(new Dimension(620, 46));
        validateButtonFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);

        validateButtonFieldReference.addActionListener(eventArgumentParameterValue -> {
            if (validateListenerFieldReference != null) {
                validateListenerFieldReference.actionPerformed(eventArgumentParameterValue);
            }
        });

        cardLocalVariableValue.add(validateButtonFieldReference);
        cardLocalVariableValue.add(Box.createVerticalStrut(12));

        uploadButtonFieldReference = new Rounded.RoundedButton("LOAD NEW FILE", 18);
        uploadButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 15));
        uploadButtonFieldReference.setForeground(ACCENT_COLOR);
        uploadButtonFieldReference.setBackground(new Color(255, 255, 255, 235));
        uploadButtonFieldReference.setOutlineMode(ACCENT_COLOR, 2);
        uploadButtonFieldReference.setShadowEnabled(false);
        uploadButtonFieldReference.setPreferredSize(new Dimension(620, 46));
        uploadButtonFieldReference.setMaximumSize(new Dimension(620, 46));
        uploadButtonFieldReference.setMinimumSize(new Dimension(620, 46));
        uploadButtonFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);

        uploadButtonFieldReference.addActionListener(
                eventArgumentParameterValue -> openFileChooser()
        );

        cardLocalVariableValue.add(uploadButtonFieldReference);

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

        iconPanelLocalVariableValue.setPreferredSize(new Dimension(138, 138));
        iconPanelLocalVariableValue.setMaximumSize(new Dimension(138, 138));
        iconPanelLocalVariableValue.setMinimumSize(new Dimension(138, 138));

        JLabel iconLabelLocalVariableValue = new JLabel();
        iconLabelLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabelLocalVariableValue.setVerticalAlignment(SwingConstants.CENTER);

        try {
            Image iconImageLocalVariableValue =
                    new ImageIcon(TEAM_ICON_PATH).getImage();

            Image scaledIconLocalVariableValue =
                    iconImageLocalVariableValue.getScaledInstance(
                            132,
                            132,
                            Image.SCALE_SMOOTH
                    );

            iconLabelLocalVariableValue.setIcon(
                    new ImageIcon(scaledIconLocalVariableValue)
            );
        } catch (Exception ignoredExceptionParameterValue) {
            iconLabelLocalVariableValue.setText("ÃƒÂ¢Ã…Â¡Ã‚Â½");
            iconLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 72));
        }

        iconPanelLocalVariableValue.add(iconLabelLocalVariableValue);

        return iconPanelLocalVariableValue;
    }


    /**
     * Crea el contenido.
     *
     * @return elemento creado por el metodo.
     */
    private JScrollPane createFilesScrollPane() {
        getTeamsDirectory().mkdirs();

        filesPanelFieldReference = new JPanel();
        filesPanelFieldReference.setLayout(
                new BoxLayout(filesPanelFieldReference, BoxLayout.Y_AXIS)
        );
        filesPanelFieldReference.setBackground(FIELD_BACKGROUND);
        filesPanelFieldReference.setBorder(new EmptyBorder(10, 12, 10, 12));

        JScrollPane scrollPaneLocalVariableValue =
                new JScrollPane(filesPanelFieldReference);

        scrollPaneLocalVariableValue.setBorder(
                BorderFactory.createLineBorder(FIELD_BORDER, 1)
        );


        scrollPaneLocalVariableValue.setPreferredSize(new Dimension(620, 150));
        scrollPaneLocalVariableValue.setMaximumSize(new Dimension(620, 150));
        scrollPaneLocalVariableValue.setMinimumSize(new Dimension(620, 150));

        scrollPaneLocalVariableValue.getViewport().setBackground(FIELD_BACKGROUND);
        scrollPaneLocalVariableValue.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPaneLocalVariableValue.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        refreshFileList();

        return scrollPaneLocalVariableValue;
    }


    /**
     * Abre el contenido.
     */
    private void openFileChooser() {
        JFileChooser fileChooserLocalVariableValue = new JFileChooser();
        fileChooserLocalVariableValue.setDialogTitle("Seleccionar archivo JSON");
        fileChooserLocalVariableValue.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooserLocalVariableValue.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter("JSON files", "json")
        );

        int resultLocalVariableValue =
                fileChooserLocalVariableValue.showOpenDialog(
                        SwingUtilities.getWindowAncestor(this)
                );

        if (resultLocalVariableValue == JFileChooser.APPROVE_OPTION
                && fileUploadListenerFieldReference != null) {
            fileUploadListenerFieldReference.accept(
                    fileChooserLocalVariableValue.getSelectedFile()
            );
        }
    }


    /**
     * Gestiona esta operacion.
     */
    public void refreshFileList() {
        if (filesPanelFieldReference == null) {
            return;
        }

        filesPanelFieldReference.removeAll();

        JLabel headerLabelLocalVariableValue =
                new JLabel("AVAILABLE JSON FILES", SwingConstants.CENTER);
        headerLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 13));
        headerLabelLocalVariableValue.setForeground(new Color(75, 88, 115));
        headerLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        filesPanelFieldReference.add(headerLabelLocalVariableValue);
        filesPanelFieldReference.add(Box.createVerticalStrut(8));

        File[] filesLocalVariableValue = getTeamsDirectory().listFiles();

        boolean hasFilesLocalVariableValue = false;

        if (filesLocalVariableValue != null) {
            for (File fileLocalVariableValue : filesLocalVariableValue) {
                if (fileLocalVariableValue.isFile()
                        && fileLocalVariableValue.getName().toLowerCase().endsWith(".json")) {
                    hasFilesLocalVariableValue = true;
                    filesPanelFieldReference.add(createFileRow(fileLocalVariableValue.getName()));
                    filesPanelFieldReference.add(Box.createVerticalStrut(6));
                }
            }
        }

        if (!hasFilesLocalVariableValue) {
            JLabel emptyLabelLocalVariableValue = new JLabel(
                    "No JSON files found yet",
                    SwingConstants.CENTER
            );
            emptyLabelLocalVariableValue.setForeground(new Color(145, 155, 175));
            emptyLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 14));
            emptyLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
            filesPanelFieldReference.add(Box.createVerticalStrut(24));
            filesPanelFieldReference.add(emptyLabelLocalVariableValue);
        }

        filesPanelFieldReference.revalidate();
        filesPanelFieldReference.repaint();
    }


    /**
     * Crea el contenido.
     *
     * @param fileNameParameterValue nombre que usa la operacion.
     * @return elemento creado por el metodo.
     */
    private JPanel createFileRow(String fileNameParameterValue) {
        JPanel rowPanelLocalVariableValue = new JPanel(new BorderLayout());
        rowPanelLocalVariableValue.setBackground(Color.WHITE);
        rowPanelLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 228, 242), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));
        rowPanelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        JLabel iconLabelLocalVariableValue = new JLabel("JSON");
        iconLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        iconLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 11));
        iconLabelLocalVariableValue.setBorder(new EmptyBorder(0, 0, 0, 12));

        JLabel fileNameLabelLocalVariableValue = new JLabel(fileNameParameterValue);
        fileNameLabelLocalVariableValue.setForeground(new Color(54, 66, 87));
        fileNameLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 14));

        rowPanelLocalVariableValue.add(iconLabelLocalVariableValue, BorderLayout.WEST);
        rowPanelLocalVariableValue.add(fileNameLabelLocalVariableValue, BorderLayout.CENTER);

        return rowPanelLocalVariableValue;
    }


    /**
     * Devuelve los equipos.
     *
     * @return los equipos.
     */
    private File getTeamsDirectory() {
        return new File(TEAMS_DIR);
    }


    /**
     * Actualiza el vuelta.
     *
     * @param listenerParameterValue listener que se registra.
     */
    public void setBackButtonListener(ActionListener listenerParameterValue) {
        this.backButtonListenerFieldReference = listenerParameterValue;
    }


    /**
     * Actualiza el contenido.
     *
     * @param listenerParameterValue listener que se registra.
     */
    public void setValidateListener(ActionListener listenerParameterValue) {
        this.validateListenerFieldReference = listenerParameterValue;
    }


    /**
     * Actualiza el contenido.
     *
     * @param listenerParameterValue listener que se registra.
     */
    public void setFileUploadListener(Consumer<File> listenerParameterValue) {
        this.fileUploadListenerFieldReference = listenerParameterValue;
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


