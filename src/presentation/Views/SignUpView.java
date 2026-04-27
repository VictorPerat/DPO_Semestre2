package presentation.Views;

import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista de registro para nuevos usuarios.
 *
 * Refactorizada: extiende JPanel y se muestra como tarjeta dentro de
 * {@link MainView}. La navegación al login se delega en AppNavigator.
 */
public class SignUpView extends JPanel {

    private JTextField nationalIdentityDocumentFieldFieldReference;
    private JTextField displayNameFieldFieldReference;
    private JTextField emailAddressFieldFieldReference;
    private JTextField jerseyNumberFieldFieldReference;
    private JTextField teamReferenceFieldFieldReference;
    private JTextField phoneNumberFieldFieldReference;

    private JButton registerButtonFieldReference;
    private JButton backButtonFieldReference;

    public static final String REGISTER_BUTTON = "REGISTER_BUTTON";
    public static final String BACK_TO_LOGIN = "BACK_TO_LOGIN";

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);
    private static final Color CARD_BORDER = new Color(44, 92, 214);
    private static final Color FIELD_BACKGROUND = new Color(245, 247, 252, 230);
    private static final Color LABEL_COLOR = new Color(108, 127, 154);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    public SignUpView() {
        setLayout(new BorderLayout());
        add(buildBackgroundPanel(), BorderLayout.CENTER);
    }

    /**
     * Limpia todos los campos del formulario de registro.
     */
    public void clearForm() {
        if (nationalIdentityDocumentFieldFieldReference != null) nationalIdentityDocumentFieldFieldReference.setText("");
        if (displayNameFieldFieldReference != null) displayNameFieldFieldReference.setText("");
        if (emailAddressFieldFieldReference != null) emailAddressFieldFieldReference.setText("");
        if (jerseyNumberFieldFieldReference != null) jerseyNumberFieldFieldReference.setText("");
        if (teamReferenceFieldFieldReference != null) teamReferenceFieldFieldReference.setText("");
        if (phoneNumberFieldFieldReference != null) phoneNumberFieldFieldReference.setText("");
    }

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
        JPanel signUpCardLocalVariableValue = buildSignUpCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpCardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(28));
        contentPanelLocalVariableValue.add(signUpCardLocalVariableValue);

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.weighty = 1.0;
        constraintsLocalVariableValue.anchor = GridBagConstraints.CENTER;
        constraintsLocalVariableValue.insets = new Insets(0, horizontalOffsetLocalVariableValue, 0, 0);

        backgroundPanelLocalVariableValue.add(contentPanelLocalVariableValue, constraintsLocalVariableValue);

        return backgroundPanelLocalVariableValue;
    }

    private JPanel buildTitleBlock() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeightLocalVariableValue = screenSizeLocalVariableValue.height;

        int mainTitleSizeLocalVariableValue = Math.max(58, (int) (screenHeightLocalVariableValue * 0.075));
        int subtitleSizeLocalVariableValue = Math.max(23, (int) (screenHeightLocalVariableValue * 0.030));

        JPanel titleContainerLocalVariableValue = new JPanel();
        titleContainerLocalVariableValue.setOpaque(false);
        titleContainerLocalVariableValue.setLayout(new BoxLayout(titleContainerLocalVariableValue, BoxLayout.Y_AXIS));

        JPanel titleLinePanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLinePanelLocalVariableValue.setOpaque(false);

        JLabel playerLabelLocalVariableValue = new JLabel("PLAYER ");
        playerLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        playerLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, mainTitleSizeLocalVariableValue));

        JLabel loginLabelLocalVariableValue = new JLabel("SIGN UP");
        loginLabelLocalVariableValue.setForeground(TITLE_WHITE);
        loginLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, mainTitleSizeLocalVariableValue));

        titleLinePanelLocalVariableValue.add(playerLabelLocalVariableValue);
        titleLinePanelLocalVariableValue.add(loginLabelLocalVariableValue);

        JPanel underlineWrapperLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        underlineWrapperLocalVariableValue.setOpaque(false);

        JPanel underlinePanelLocalVariableValue = new JPanel();
        underlinePanelLocalVariableValue.setBackground(ACCENT_COLOR);
        underlinePanelLocalVariableValue.setPreferredSize(new Dimension(190, 6));
        underlinePanelLocalVariableValue.setMinimumSize(new Dimension(190, 6));
        underlinePanelLocalVariableValue.setMaximumSize(new Dimension(190, 6));

        underlineWrapperLocalVariableValue.add(underlinePanelLocalVariableValue);

        JLabel subtitleLabelLocalVariableValue = new JLabel("Complete your information to create the account", SwingConstants.CENTER);
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

    private JPanel buildSignUpCard() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidthLocalVariableValue = screenSizeLocalVariableValue.width;

        int cardWidthLocalVariableValue = Math.max(860, Math.min(980, (int) (screenWidthLocalVariableValue * 0.62)));

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

        nationalIdentityDocumentFieldFieldReference = buildInputField("12345678A");
        displayNameFieldFieldReference = buildInputField("John Smith");
        emailAddressFieldFieldReference = buildInputField("example@email.com");
        jerseyNumberFieldFieldReference = buildInputField("10");
        teamReferenceFieldFieldReference = buildInputField("League Manager FC");
        phoneNumberFieldFieldReference = buildInputField("600 000 000");

        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        cardPanelLocalVariableValue.add(buildFieldBlock("DNI", nationalIdentityDocumentFieldFieldReference), constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridx = 1;
        constraintsLocalVariableValue.gridy = 0;
        cardPanelLocalVariableValue.add(buildFieldBlock("NAME", displayNameFieldFieldReference), constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 1;
        cardPanelLocalVariableValue.add(buildFieldBlock("MAIL ADDRESS", emailAddressFieldFieldReference), constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridx = 1;
        constraintsLocalVariableValue.gridy = 1;
        cardPanelLocalVariableValue.add(buildFieldBlock("RIDGE", jerseyNumberFieldFieldReference), constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 2;
        cardPanelLocalVariableValue.add(buildFieldBlock("TEAM", teamReferenceFieldFieldReference), constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridx = 1;
        constraintsLocalVariableValue.gridy = 2;
        cardPanelLocalVariableValue.add(buildFieldBlock("PHONE NUMBER", phoneNumberFieldFieldReference), constraintsLocalVariableValue);

        JPanel buttonPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 22, 0));
        buttonPanelLocalVariableValue.setOpaque(false);

        backButtonFieldReference = new Rounded.RoundedButton("← BACK", 16);
        Rounded.RoundedButton backButtonLocalVariableValue =
                (Rounded.RoundedButton) backButtonFieldReference;
        backButtonLocalVariableValue.setActionCommand(BACK_TO_LOGIN);
        backButtonLocalVariableValue.setForeground(new Color(40, 84, 198));
        backButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 21));
        backButtonLocalVariableValue.setPreferredSize(new Dimension(220, 56));
        backButtonLocalVariableValue.setBackground(new Color(255, 255, 255, 20));
        backButtonLocalVariableValue.setOutlineMode(new Color(40, 84, 198), 2);
        backButtonLocalVariableValue.setShadowEnabled(false);

        registerButtonFieldReference = new Rounded.RoundedButton("SIGN UP", 16);
        Rounded.RoundedButton registerButtonLocalVariableValue =
                (Rounded.RoundedButton) registerButtonFieldReference;
        registerButtonLocalVariableValue.setActionCommand(REGISTER_BUTTON);
        registerButtonLocalVariableValue.setForeground(Color.WHITE);
        registerButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 24));
        registerButtonLocalVariableValue.setPreferredSize(new Dimension(260, 56));
        registerButtonLocalVariableValue.setGradientColors(new Color(64, 116, 226), new Color(42, 85, 191));
        registerButtonLocalVariableValue.setShadowEnabled(true);

        buttonPanelLocalVariableValue.add(backButtonFieldReference);
        buttonPanelLocalVariableValue.add(registerButtonFieldReference);

        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 3;
        constraintsLocalVariableValue.gridwidth = 2;
        constraintsLocalVariableValue.insets = new Insets(26, 12, 0, 12);
        cardPanelLocalVariableValue.add(buttonPanelLocalVariableValue, constraintsLocalVariableValue);

        return cardPanelLocalVariableValue;
    }

    private JPanel buildFieldBlock(String labelTextParameterValue, JTextField fieldParameterValue) {
        JPanel fieldBlockLocalVariableValue = new JPanel();
        fieldBlockLocalVariableValue.setOpaque(false);
        fieldBlockLocalVariableValue.setLayout(new BoxLayout(fieldBlockLocalVariableValue, BoxLayout.Y_AXIS));

        JLabel labelLocalVariableValue = new JLabel(labelTextParameterValue);
        labelLocalVariableValue.setForeground(LABEL_COLOR);
        labelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 17));
        labelLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        fieldParameterValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        fieldBlockLocalVariableValue.add(labelLocalVariableValue);
        fieldBlockLocalVariableValue.add(Box.createVerticalStrut(8));
        fieldBlockLocalVariableValue.add(fieldParameterValue);

        return fieldBlockLocalVariableValue;
    }

    private JTextField buildInputField(String placeholderParameterValue) {
        Rounded.RoundedTextField textFieldLocalVariableValue =
                new Rounded.RoundedTextField(20, 14);

        textFieldLocalVariableValue.setPlaceholder(placeholderParameterValue);
        textFieldLocalVariableValue.setBackground(FIELD_BACKGROUND);
        textFieldLocalVariableValue.setForeground(new Color(54, 66, 87));
        textFieldLocalVariableValue.setCaretColor(new Color(54, 66, 87));
        textFieldLocalVariableValue.setBorderColor(new Color(212, 219, 230));
        textFieldLocalVariableValue.setFocusBorderColor(new Color(52, 102, 219));
        textFieldLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 20));
        textFieldLocalVariableValue.setPreferredSize(new Dimension(100, 50));

        return textFieldLocalVariableValue;
    }

    public void registerController(ActionListener controllerHandlerParameterValue) {
        registerButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
    }

    public String getDni() {
        return nationalIdentityDocumentFieldFieldReference.getText();
    }

    public String getName() {
        return displayNameFieldFieldReference.getText();
    }

    public String getEmail() {
        return emailAddressFieldFieldReference.getText();
    }

    public String getDorsal() {
        return jerseyNumberFieldFieldReference.getText();
    }


    public String getTeam() {
        return teamReferenceFieldFieldReference.getText();
    }

    public String getPhone() {
        return phoneNumberFieldFieldReference.getText();
    }

    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                messageParameterValue,
                "Registro",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
