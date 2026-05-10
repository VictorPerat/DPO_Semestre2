package presentation.Views;

import bussines.objects.Player;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;


/**
 * Representa la vista del usuario perfil.
 */
public class UserProfileView extends JPanel {

    /**
     * Constante para el valor.
     */
    public static final String LOGOUT_BUTTON = "LOGOUT_BUTTON";
    /**
     * Constante para el cambio contrasena.
     */
    public static final String CHANGE_PASSWORD_BUTTON = "CHANGE_PASSWORD_BUTTON";

    private final JLabel displayNameFieldReference = new JLabel("-");
    private final JLabel nationalIdentityDocumentFieldReference = new JLabel("-");
    private final JLabel emailAddressFieldReference = new JLabel("-");
    private final JLabel teamReferenceFieldReference = new JLabel("-");
    private final JLabel jerseyNumberFieldReference = new JLabel("-");
    private final JLabel phoneNumberFieldReference = new JLabel("-");

    private final JButton logoutButtonFieldReference;
    private final JButton changePasswordButtonFieldReference;

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);
    private static final Color CARD_BORDER = new Color(44, 92, 214);
    private static final Color LABEL_COLOR = new Color(108, 127, 154);
    private static final Color FIELD_BACKGROUND = new Color(245, 247, 252, 235);
    private static final Color VALUE_TEXT_COLOR = new Color(54, 66, 87);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");


    /**
     * Crea una instancia de el usuario perfil.
     */
    public UserProfileView() {
        logoutButtonFieldReference = new Rounded.RoundedButton("LOGOUT", 16);
        changePasswordButtonFieldReference = new Rounded.RoundedButton("CHANGE PASSWORD", 16);

        setLayout(new BorderLayout());
        add(buildBackgroundPanel(), BorderLayout.CENTER);
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildBackgroundPanel() {
        Image backgroundImageLocalVariableValue = new ImageIcon(BACKGROUND_IMAGE_PATH).getImage();

        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidthLocalVariableValue = screenSizeLocalVariableValue.width;
        int horizontalOffsetLocalVariableValue = Math.max(70, (int) (screenWidthLocalVariableValue * 0.05));

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

                Graphics2D g2LocalVariableValue = (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setColor(new Color(0, 0, 0, 35));
                g2LocalVariableValue.fillRect(0, 0, getWidth(), getHeight());
                g2LocalVariableValue.dispose();
            }
        };

        backgroundPanelLocalVariableValue.setLayout(new GridBagLayout());

        JPanel contentPanelLocalVariableValue = new JPanel();
        contentPanelLocalVariableValue.setOpaque(false);
        contentPanelLocalVariableValue.setLayout(new BoxLayout(contentPanelLocalVariableValue, BoxLayout.Y_AXIS));
        contentPanelLocalVariableValue.setBorder(new EmptyBorder(20, 0, 20, 0));

        JPanel titleBlockLocalVariableValue = buildTitleBlock();
        JPanel profileCardLocalVariableValue = buildProfileCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileCardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(28));
        contentPanelLocalVariableValue.add(profileCardLocalVariableValue);

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.weighty = 1.0;
        constraintsLocalVariableValue.anchor = GridBagConstraints.CENTER;

        constraintsLocalVariableValue.insets = new Insets(0, horizontalOffsetLocalVariableValue, 0, 310);

        backgroundPanelLocalVariableValue.add(contentPanelLocalVariableValue, constraintsLocalVariableValue);

        return backgroundPanelLocalVariableValue;
    }


    /**
     * Construye el titulo.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildTitleBlock() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeightLocalVariableValue = screenSizeLocalVariableValue.height;

        int mainTitleSizeLocalVariableValue = Math.max(56, (int) (screenHeightLocalVariableValue * 0.072));
        int subtitleSizeLocalVariableValue = Math.max(22, (int) (screenHeightLocalVariableValue * 0.029));

        JPanel titleContainerLocalVariableValue = new JPanel();
        titleContainerLocalVariableValue.setOpaque(false);
        titleContainerLocalVariableValue.setLayout(new BoxLayout(titleContainerLocalVariableValue, BoxLayout.Y_AXIS));

        JPanel titleLinePanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLinePanelLocalVariableValue.setOpaque(false);

        JLabel userLabelLocalVariableValue = new JLabel("USER ");
        userLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        userLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, mainTitleSizeLocalVariableValue));

        JLabel informationLabelLocalVariableValue = new JLabel("INFORMATION");
        informationLabelLocalVariableValue.setForeground(TITLE_WHITE);
        informationLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, mainTitleSizeLocalVariableValue));

        titleLinePanelLocalVariableValue.add(userLabelLocalVariableValue);
        titleLinePanelLocalVariableValue.add(informationLabelLocalVariableValue);

        JPanel underlineWrapperLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        underlineWrapperLocalVariableValue.setOpaque(false);

        JPanel underlinePanelLocalVariableValue = new JPanel();
        underlinePanelLocalVariableValue.setBackground(ACCENT_COLOR);
        underlinePanelLocalVariableValue.setPreferredSize(new Dimension(210, 6));
        underlinePanelLocalVariableValue.setMinimumSize(new Dimension(210, 6));
        underlinePanelLocalVariableValue.setMaximumSize(new Dimension(210, 6));

        underlineWrapperLocalVariableValue.add(underlinePanelLocalVariableValue);

        JLabel subtitleLabelLocalVariableValue = new JLabel("Check your player data and manage your account", SwingConstants.CENTER);
        subtitleLabelLocalVariableValue.setForeground(SUBTITLE_WHITE);
        subtitleLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, subtitleSizeLocalVariableValue));
        subtitleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabelLocalVariableValue.setBorder(new EmptyBorder(22, 0, 0, 0));

        titleContainerLocalVariableValue.add(titleLinePanelLocalVariableValue);
        titleContainerLocalVariableValue.add(Box.createVerticalStrut(14));
        titleContainerLocalVariableValue.add(underlineWrapperLocalVariableValue);
        titleContainerLocalVariableValue.add(subtitleLabelLocalVariableValue);

        return titleContainerLocalVariableValue;
    }


    /**
     * Construye el perfil.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildProfileCard() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidthLocalVariableValue = screenSizeLocalVariableValue.width;

        int cardWidthLocalVariableValue = Math.max(900, Math.min(1020, (int) (screenWidthLocalVariableValue * 0.64)));

        JPanel cardPanelLocalVariableValue = new JPanel(new GridBagLayout()) {

            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                Graphics2D g2LocalVariableValue = (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2LocalVariableValue.setColor(new Color(8, 20, 46, 55));
                g2LocalVariableValue.fillRoundRect(10, 14, getWidth() - 20, getHeight() - 18, 30, 30);

                g2LocalVariableValue.setColor(new Color(255, 255, 255, 238));
                g2LocalVariableValue.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 12, 28, 28);

                g2LocalVariableValue.setColor(CARD_BORDER);
                g2LocalVariableValue.setStroke(new BasicStroke(2f));
                g2LocalVariableValue.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 12, 28, 28);

                g2LocalVariableValue.dispose();
                super.paintComponent(graphicsParameterValue);
            }
        };

        cardPanelLocalVariableValue.setOpaque(false);
        cardPanelLocalVariableValue.setPreferredSize(new Dimension(cardWidthLocalVariableValue, 560));
        cardPanelLocalVariableValue.setMaximumSize(new Dimension(cardWidthLocalVariableValue, 560));
        cardPanelLocalVariableValue.setBorder(new EmptyBorder(34, 34, 34, 34));

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.fill = GridBagConstraints.HORIZONTAL;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.insets = new Insets(12, 12, 12, 12);

        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        cardPanelLocalVariableValue.add(buildInfoBlock("NAME", displayNameFieldReference), constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridx = 1;
        constraintsLocalVariableValue.gridy = 0;
        cardPanelLocalVariableValue.add(buildInfoBlock("DNI", nationalIdentityDocumentFieldReference), constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 1;
        cardPanelLocalVariableValue.add(buildInfoBlock("EMAIL", emailAddressFieldReference), constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridx = 1;
        constraintsLocalVariableValue.gridy = 1;
        cardPanelLocalVariableValue.add(buildInfoBlock("TEAM", teamReferenceFieldReference), constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 2;
        cardPanelLocalVariableValue.add(buildInfoBlock("DORSAL", jerseyNumberFieldReference), constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridx = 1;
        constraintsLocalVariableValue.gridy = 2;
        cardPanelLocalVariableValue.add(buildInfoBlock("PHONE", phoneNumberFieldReference), constraintsLocalVariableValue);

        configureButtons();

        JPanel buttonPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 22, 0));
        buttonPanelLocalVariableValue.setOpaque(false);
        buttonPanelLocalVariableValue.add(changePasswordButtonFieldReference);
        buttonPanelLocalVariableValue.add(logoutButtonFieldReference);

        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 3;
        constraintsLocalVariableValue.gridwidth = 2;
        constraintsLocalVariableValue.insets = new Insets(26, 12, 0, 12);
        cardPanelLocalVariableValue.add(buttonPanelLocalVariableValue, constraintsLocalVariableValue);

        return cardPanelLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @param labelTextParameterValue texto que usa la operacion.
     * @param valueLabelParameterValue dato de entrada de la operacion.
     * @return resultado de la operacion.
     */
    private JPanel buildInfoBlock(String labelTextParameterValue, JLabel valueLabelParameterValue) {
        JPanel blockPanelLocalVariableValue = new JPanel();
        blockPanelLocalVariableValue.setOpaque(false);
        blockPanelLocalVariableValue.setLayout(new BoxLayout(blockPanelLocalVariableValue, BoxLayout.Y_AXIS));

        JLabel labelLocalVariableValue = new JLabel(labelTextParameterValue);
        labelLocalVariableValue.setForeground(LABEL_COLOR);
        labelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 17));
        labelLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel valueContainerLocalVariableValue = buildValueContainer(valueLabelParameterValue);
        valueContainerLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        blockPanelLocalVariableValue.add(labelLocalVariableValue);
        blockPanelLocalVariableValue.add(Box.createVerticalStrut(8));
        blockPanelLocalVariableValue.add(valueContainerLocalVariableValue);

        return blockPanelLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @param valueLabelParameterValue dato de entrada de la operacion.
     * @return resultado de la operacion.
     */
    private JPanel buildValueContainer(JLabel valueLabelParameterValue) {
        valueLabelParameterValue.setFont(new Font("Arial", Font.PLAIN, 20));
        valueLabelParameterValue.setForeground(VALUE_TEXT_COLOR);

        JPanel valuePanelLocalVariableValue = new JPanel(new BorderLayout()) {

            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                Graphics2D g2LocalVariableValue = (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2LocalVariableValue.setColor(FIELD_BACKGROUND);
                g2LocalVariableValue.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);

                g2LocalVariableValue.setColor(new Color(212, 219, 230));
                g2LocalVariableValue.setStroke(new BasicStroke(1.5f));
                g2LocalVariableValue.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);

                g2LocalVariableValue.dispose();
                super.paintComponent(graphicsParameterValue);
            }
        };

        valuePanelLocalVariableValue.setOpaque(false);
        valuePanelLocalVariableValue.setPreferredSize(new Dimension(100, 52));
        valuePanelLocalVariableValue.setBorder(new EmptyBorder(0, 16, 0, 16));
        valuePanelLocalVariableValue.add(valueLabelParameterValue, BorderLayout.CENTER);

        return valuePanelLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     */
    private void configureButtons() {
        Rounded.RoundedButton changePasswordButtonLocalVariableValue =
                (Rounded.RoundedButton) changePasswordButtonFieldReference;
        changePasswordButtonLocalVariableValue.setActionCommand(CHANGE_PASSWORD_BUTTON);
        changePasswordButtonLocalVariableValue.setForeground(Color.WHITE);
        changePasswordButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 22));
        changePasswordButtonLocalVariableValue.setPreferredSize(new Dimension(290, 56));
        changePasswordButtonLocalVariableValue.setGradientColors(new Color(64, 116, 226), new Color(42, 85, 191));
        changePasswordButtonLocalVariableValue.setShadowEnabled(true);

        Rounded.RoundedButton logoutButtonLocalVariableValue =
                (Rounded.RoundedButton) logoutButtonFieldReference;
        logoutButtonLocalVariableValue.setActionCommand(LOGOUT_BUTTON);
        logoutButtonLocalVariableValue.setForeground(new Color(40, 84, 198));
        logoutButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 21));
        logoutButtonLocalVariableValue.setPreferredSize(new Dimension(210, 56));
        logoutButtonLocalVariableValue.setBackground(new Color(255, 255, 255, 20));
        logoutButtonLocalVariableValue.setOutlineMode(new Color(40, 84, 198), 2);
        logoutButtonLocalVariableValue.setShadowEnabled(false);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param playerProfileParameterValue jugador perfil.
     */
    public void displayUserInformation(Player playerProfileParameterValue) {
        displayNameFieldReference.setText(playerProfileParameterValue.getNamePlayer());
        nationalIdentityDocumentFieldReference.setText(playerProfileParameterValue.getDniPlayer());
        emailAddressFieldReference.setText(playerProfileParameterValue.getMail());
        teamReferenceFieldReference.setText(playerProfileParameterValue.getTeamId());
        jerseyNumberFieldReference.setText(String.valueOf(playerProfileParameterValue.getDorsal()));
        phoneNumberFieldReference.setText(String.valueOf(playerProfileParameterValue.getPhoneNumber()));
    }


    /**
     * Registra la accion.
     *
     * @param controllerHandlerParameterValue dato de entrada de la operacion.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        logoutButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        changePasswordButtonFieldReference.addActionListener(controllerHandlerParameterValue);
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
                "User Profile",
                JOptionPane.WARNING_MESSAGE
        );
    }
}


