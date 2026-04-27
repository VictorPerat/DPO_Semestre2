package presentation.Views;

import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista gráfica de la pantalla de inicio de sesión.
 *
 * Refactorizada: ahora es un JPanel que se registra como tarjeta dentro
 * de {@link MainView}. Ya no gestiona ventana propia (sin setTitle, setSize,
 * setVisible, EXIT_ON_CLOSE, ...).
 */
public class LoginView extends JPanel {

    private JTextField emailAddressFieldFieldReference;
    private JPasswordField userPasswordFieldFieldReference;
    private JButton accessButtonFieldReference;
    private JButton signUpButtonFieldReference;

    public static final String ACCESS_BUTTON = "ACCESS_BUTTON";
    public static final String SIGN_UP_BUTTON = "SIGN_UP_BUTTON";

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);
    private static final Color CARD_BORDER = new Color(44, 92, 214);
    private static final Color FIELD_BACKGROUND = new Color(245, 247, 252, 230);
    private static final Color LABEL_COLOR = new Color(108, 127, 154);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    public LoginView() {
        setLayout(new BorderLayout());
        add(buildBackgroundPanel(), BorderLayout.CENTER);
    }

    /**
     * Limpia los campos del formulario. Útil al volver a la pantalla
     * de login (logout, registro completado, etc.).
     */
    public void clearForm() {
        if (emailAddressFieldFieldReference != null) {
            emailAddressFieldFieldReference.setText("");
        }
        if (userPasswordFieldFieldReference != null) {
            userPasswordFieldFieldReference.setText("");
        }
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
        JPanel loginCardLocalVariableValue = buildLoginCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginCardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(34));
        contentPanelLocalVariableValue.add(loginCardLocalVariableValue);

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

        int mainTitleSizeLocalVariableValue = Math.max(62, (int) (screenHeightLocalVariableValue * 0.082));
        int subtitleSizeLocalVariableValue = Math.max(24, (int) (screenHeightLocalVariableValue * 0.031));

        JPanel titleContainerLocalVariableValue = new JPanel();
        titleContainerLocalVariableValue.setOpaque(false);
        titleContainerLocalVariableValue.setLayout(new BoxLayout(titleContainerLocalVariableValue, BoxLayout.Y_AXIS));

        JPanel titleLinePanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLinePanelLocalVariableValue.setOpaque(false);

        JLabel playerLabelLocalVariableValue = new JLabel("PLAYER ");
        playerLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        playerLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, mainTitleSizeLocalVariableValue));

        JLabel loginLabelLocalVariableValue = new JLabel("LOGIN");
        loginLabelLocalVariableValue.setForeground(TITLE_WHITE);
        loginLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, mainTitleSizeLocalVariableValue));

        titleLinePanelLocalVariableValue.add(playerLabelLocalVariableValue);
        titleLinePanelLocalVariableValue.add(loginLabelLocalVariableValue);

        JPanel underlineWrapperLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        underlineWrapperLocalVariableValue.setOpaque(false);

        JPanel underlinePanelLocalVariableValue = new JPanel();
        underlinePanelLocalVariableValue.setBackground(ACCENT_COLOR);
        underlinePanelLocalVariableValue.setPreferredSize(new Dimension(180, 6));
        underlinePanelLocalVariableValue.setMinimumSize(new Dimension(180, 6));
        underlinePanelLocalVariableValue.setMaximumSize(new Dimension(180, 6));

        underlineWrapperLocalVariableValue.add(underlinePanelLocalVariableValue);

        JLabel subtitleLabelLocalVariableValue = new JLabel("Enter your credentials to continue", SwingConstants.CENTER);
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

    private JPanel buildLoginCard() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidthLocalVariableValue = screenSizeLocalVariableValue.width;

        int cardWidthLocalVariableValue = Math.max(540, Math.min(620, (int) (screenWidthLocalVariableValue * 0.40)));

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
        cardPanelLocalVariableValue.setPreferredSize(new Dimension(cardWidthLocalVariableValue, 500));
        cardPanelLocalVariableValue.setMaximumSize(new Dimension(cardWidthLocalVariableValue, 500));
        cardPanelLocalVariableValue.setBorder(new EmptyBorder(34, 38, 34, 38));

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.fill = GridBagConstraints.HORIZONTAL;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.insets = new Insets(10, 0, 10, 0);

        JLabel userIdentifierLabelLocalVariableValue = buildFieldLabel("USUARIO (EMAIL)");
        emailAddressFieldFieldReference = buildTextField("example@email.com");

        JLabel passLabelLocalVariableValue = buildFieldLabel("CONTRASEÑA");
        userPasswordFieldFieldReference = buildPasswordField("••••••••");

        accessButtonFieldReference = new Rounded.RoundedButton("ACCESS", 16);
        Rounded.RoundedButton accessButtonLocalVariableValue =
                (Rounded.RoundedButton) accessButtonFieldReference;
        accessButtonLocalVariableValue.setActionCommand(ACCESS_BUTTON);
        accessButtonLocalVariableValue.setForeground(Color.WHITE);
        accessButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 28));
        accessButtonLocalVariableValue.setPreferredSize(new Dimension(0, 62));
        accessButtonLocalVariableValue.setGradientColors(new Color(64, 116, 226), new Color(42, 85, 191));
        accessButtonLocalVariableValue.setShadowEnabled(true);

        JLabel forgotPasswordLabelLocalVariableValue = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "<span style='color:#8F9AAF;'>Forgot your password? </span>"
                        + "<span style='color:#2A5BC9;'><b>Recover it here</b></span>"
                        + "</div></html>",
                SwingConstants.CENTER
        );
        forgotPasswordLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 15));
        forgotPasswordLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel dividerPanelLocalVariableValue = buildDividerPanel();

        signUpButtonFieldReference = new Rounded.RoundedButton("SIGN UP", 16);
        Rounded.RoundedButton signUpButtonLocalVariableValue =
                (Rounded.RoundedButton) signUpButtonFieldReference;
        signUpButtonLocalVariableValue.setActionCommand(SIGN_UP_BUTTON);
        signUpButtonLocalVariableValue.setForeground(new Color(40, 84, 198));
        signUpButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 25));
        signUpButtonLocalVariableValue.setPreferredSize(new Dimension(0, 56));
        signUpButtonLocalVariableValue.setBackground(new Color(255, 255, 255, 20));
        signUpButtonLocalVariableValue.setOutlineMode(new Color(40, 84, 198), 2);
        signUpButtonLocalVariableValue.setShadowEnabled(false);

        constraintsLocalVariableValue.gridy = 0;
        cardPanelLocalVariableValue.add(userIdentifierLabelLocalVariableValue, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 1;
        cardPanelLocalVariableValue.add(emailAddressFieldFieldReference, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 2;
        constraintsLocalVariableValue.insets = new Insets(16, 0, 10, 0);
        cardPanelLocalVariableValue.add(passLabelLocalVariableValue, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 3;
        constraintsLocalVariableValue.insets = new Insets(10, 0, 12, 0);
        cardPanelLocalVariableValue.add(userPasswordFieldFieldReference, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 4;
        constraintsLocalVariableValue.insets = new Insets(18, 0, 10, 0);
        cardPanelLocalVariableValue.add(accessButtonFieldReference, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 5;
        constraintsLocalVariableValue.insets = new Insets(10, 0, 10, 0);
        cardPanelLocalVariableValue.add(forgotPasswordLabelLocalVariableValue, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 6;
        constraintsLocalVariableValue.insets = new Insets(16, 0, 10, 0);
        cardPanelLocalVariableValue.add(dividerPanelLocalVariableValue, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 7;
        constraintsLocalVariableValue.insets = new Insets(10, 0, 0, 0);
        cardPanelLocalVariableValue.add(signUpButtonFieldReference, constraintsLocalVariableValue);

        return cardPanelLocalVariableValue;
    }

    private JLabel buildFieldLabel(String textParameterValue) {
        JLabel labelLocalVariableValue = new JLabel(textParameterValue);
        labelLocalVariableValue.setForeground(LABEL_COLOR);
        labelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 17));
        return labelLocalVariableValue;
    }

    private JTextField buildTextField(String placeholderParameterValue) {
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

    private JPasswordField buildPasswordField(String placeholderParameterValue) {
        Rounded.RoundedPasswordField passwordFieldLocalVariableValue =
                new Rounded.RoundedPasswordField(20, 14);

        passwordFieldLocalVariableValue.setPlaceholder(placeholderParameterValue);
        passwordFieldLocalVariableValue.setBackground(FIELD_BACKGROUND);
        passwordFieldLocalVariableValue.setForeground(new Color(54, 66, 87));
        passwordFieldLocalVariableValue.setCaretColor(new Color(54, 66, 87));
        passwordFieldLocalVariableValue.setBorderColor(new Color(212, 219, 230));
        passwordFieldLocalVariableValue.setFocusBorderColor(new Color(52, 102, 219));
        passwordFieldLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordFieldLocalVariableValue.setPreferredSize(new Dimension(100, 50));

        return passwordFieldLocalVariableValue;
    }

    private JPanel buildDividerPanel() {
        JPanel dividerPanelLocalVariableValue = new JPanel();
        dividerPanelLocalVariableValue.setOpaque(false);
        dividerPanelLocalVariableValue.setLayout(new BoxLayout(dividerPanelLocalVariableValue, BoxLayout.X_AXIS));

        JComponent lineLeftLocalVariableValue = buildDividerLine();
        JComponent lineRightLocalVariableValue = buildDividerLine();

        JLabel orLabelLocalVariableValue = new JLabel("OR");
        orLabelLocalVariableValue.setForeground(new Color(182, 188, 198));
        orLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 15));
        orLabelLocalVariableValue.setBorder(new EmptyBorder(0, 14, 0, 14));

        dividerPanelLocalVariableValue.add(lineLeftLocalVariableValue);
        dividerPanelLocalVariableValue.add(orLabelLocalVariableValue);
        dividerPanelLocalVariableValue.add(lineRightLocalVariableValue);

        return dividerPanelLocalVariableValue;
    }

    private JComponent buildDividerLine() {
        return new JComponent() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 1);
            }

            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                Graphics2D g2LocalVariableValue = (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setColor(new Color(214, 220, 230));
                g2LocalVariableValue.fillRect(0, getHeight() / 2, getWidth(), 1);
                g2LocalVariableValue.dispose();
            }
        };
    }

    public String getEmailText() {
        return emailAddressFieldFieldReference.getText();
    }

    public String getPasswordText() {
        return new String(userPasswordFieldFieldReference.getPassword());
    }

    public void registerController(ActionListener controllerHandlerParameterValue) {
        accessButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        signUpButtonFieldReference.addActionListener(controllerHandlerParameterValue);
    }

    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                messageParameterValue,
                "Login",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
