package presentation.Views;

import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista Swing para permitir al usuario cambiar su contraseña
 * con el mismo estilo visual que login, register y user profile.
 *
 * Refactorizada: extiende JPanel y se muestra como tarjeta dentro de
 * {@link MainView}. El controlador navega con AppNavigator en lugar
 * de gestionar dispose() y JFrame anteriores.
 */
public class ChangePasswordView extends JPanel {

    public static final String CHANGE_BUTTON = "CHANGE_BUTTON";
    public static final String BACK_BUTTON = "BACK_BUTTON";

    private JPasswordField actualUserPasswordFieldFieldReference;
    private JPasswordField newUserPasswordFieldFieldReference;
    private JPasswordField confirmUserPasswordFieldFieldReference;
    private JButton changeButtonFieldReference;
    private JButton backButtonFieldReference;

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);
    private static final Color CARD_BORDER = new Color(44, 92, 214);
    private static final Color FIELD_BACKGROUND = new Color(245, 247, 252, 230);
    private static final Color LABEL_COLOR = new Color(108, 127, 154);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    public ChangePasswordView() {
        setLayout(new BorderLayout());
        add(buildBackgroundPanel(), BorderLayout.CENTER);
    }

    /**
     * Limpia los campos de contraseña al entrar en la pantalla.
     */
    public void clearForm() {
        if (actualUserPasswordFieldFieldReference != null) actualUserPasswordFieldFieldReference.setText("");
        if (newUserPasswordFieldFieldReference != null) newUserPasswordFieldFieldReference.setText("");
        if (confirmUserPasswordFieldFieldReference != null) confirmUserPasswordFieldFieldReference.setText("");
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
        JPanel changePasswordCardLocalVariableValue = buildChangePasswordCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        changePasswordCardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(28));
        contentPanelLocalVariableValue.add(changePasswordCardLocalVariableValue);

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

        int mainTitleSizeLocalVariableValue = Math.max(56, (int) (screenHeightLocalVariableValue * 0.072));
        int subtitleSizeLocalVariableValue = Math.max(22, (int) (screenHeightLocalVariableValue * 0.029));

        JPanel titleContainerLocalVariableValue = new JPanel();
        titleContainerLocalVariableValue.setOpaque(false);
        titleContainerLocalVariableValue.setLayout(new BoxLayout(titleContainerLocalVariableValue, BoxLayout.Y_AXIS));

        JPanel titleLinePanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLinePanelLocalVariableValue.setOpaque(false);

        JLabel changeLabelLocalVariableValue = new JLabel("CHANGE ");
        changeLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        changeLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, mainTitleSizeLocalVariableValue));

        JLabel passwordLabelLocalVariableValue = new JLabel("PASSWORD");
        passwordLabelLocalVariableValue.setForeground(TITLE_WHITE);
        passwordLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, mainTitleSizeLocalVariableValue));

        titleLinePanelLocalVariableValue.add(changeLabelLocalVariableValue);
        titleLinePanelLocalVariableValue.add(passwordLabelLocalVariableValue);

        JPanel underlineWrapperLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        underlineWrapperLocalVariableValue.setOpaque(false);

        JPanel underlinePanelLocalVariableValue = new JPanel();
        underlinePanelLocalVariableValue.setBackground(ACCENT_COLOR);
        underlinePanelLocalVariableValue.setPreferredSize(new Dimension(220, 6));
        underlinePanelLocalVariableValue.setMinimumSize(new Dimension(220, 6));
        underlinePanelLocalVariableValue.setMaximumSize(new Dimension(220, 6));

        underlineWrapperLocalVariableValue.add(underlinePanelLocalVariableValue);

        JLabel subtitleLabelLocalVariableValue = new JLabel(
                "Update your password securely",
                SwingConstants.CENTER
        );
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

    private JPanel buildChangePasswordCard() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidthLocalVariableValue = screenSizeLocalVariableValue.width;

        int cardWidthLocalVariableValue = Math.max(760, Math.min(860, (int) (screenWidthLocalVariableValue * 0.54)));

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
        cardPanelLocalVariableValue.setPreferredSize(new Dimension(cardWidthLocalVariableValue, 540));
        cardPanelLocalVariableValue.setMaximumSize(new Dimension(cardWidthLocalVariableValue, 540));
        cardPanelLocalVariableValue.setBorder(new EmptyBorder(38, 40, 42, 40));

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.fill = GridBagConstraints.HORIZONTAL;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.insets = new Insets(10, 0, 10, 0);

        JLabel currentPasswordLabelLocalVariableValue = buildFieldLabel("CURRENT PASSWORD");
        actualUserPasswordFieldFieldReference = buildPasswordField("••••••••");

        JLabel newPasswordLabelLocalVariableValue = buildFieldLabel("NEW PASSWORD");
        newUserPasswordFieldFieldReference = buildPasswordField("••••••••");

        JLabel confirmPasswordLabelLocalVariableValue = buildFieldLabel("CONFIRM NEW PASSWORD");
        confirmUserPasswordFieldFieldReference = buildPasswordField("••••••••");

        JPanel buttonPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 22, 0));
        buttonPanelLocalVariableValue.setOpaque(false);

        backButtonFieldReference = new Rounded.RoundedButton("← BACK", 16);
        Rounded.RoundedButton backButtonLocalVariableValue =
                (Rounded.RoundedButton) backButtonFieldReference;
        backButtonLocalVariableValue.setActionCommand(BACK_BUTTON);
        backButtonLocalVariableValue.setForeground(new Color(40, 84, 198));
        backButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 21));
        backButtonLocalVariableValue.setPreferredSize(new Dimension(220, 56));
        backButtonLocalVariableValue.setBackground(new Color(255, 255, 255, 20));
        backButtonLocalVariableValue.setOutlineMode(new Color(40, 84, 198), 2);
        backButtonLocalVariableValue.setShadowEnabled(false);

        changeButtonFieldReference = new Rounded.RoundedButton("CHANGE PASSWORD", 16);
        Rounded.RoundedButton changeButtonLocalVariableValue =
                (Rounded.RoundedButton) changeButtonFieldReference;
        changeButtonLocalVariableValue.setActionCommand(CHANGE_BUTTON);
        changeButtonLocalVariableValue.setForeground(Color.WHITE);
        changeButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 23));
        changeButtonLocalVariableValue.setPreferredSize(new Dimension(300, 56));
        changeButtonLocalVariableValue.setGradientColors(new Color(64, 116, 226), new Color(42, 85, 191));
        changeButtonLocalVariableValue.setShadowEnabled(true);

        buttonPanelLocalVariableValue.add(backButtonFieldReference);
        buttonPanelLocalVariableValue.add(changeButtonFieldReference);

        constraintsLocalVariableValue.gridy = 0;
        cardPanelLocalVariableValue.add(currentPasswordLabelLocalVariableValue, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 1;
        cardPanelLocalVariableValue.add(actualUserPasswordFieldFieldReference, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 2;
        constraintsLocalVariableValue.insets = new Insets(16, 0, 10, 0);
        cardPanelLocalVariableValue.add(newPasswordLabelLocalVariableValue, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 3;
        constraintsLocalVariableValue.insets = new Insets(10, 0, 10, 0);
        cardPanelLocalVariableValue.add(newUserPasswordFieldFieldReference, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 4;
        constraintsLocalVariableValue.insets = new Insets(16, 0, 10, 0);
        cardPanelLocalVariableValue.add(confirmPasswordLabelLocalVariableValue, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 5;
        constraintsLocalVariableValue.insets = new Insets(10, 0, 12, 0);
        cardPanelLocalVariableValue.add(confirmUserPasswordFieldFieldReference, constraintsLocalVariableValue);

        constraintsLocalVariableValue.gridy = 6;
        constraintsLocalVariableValue.insets = new Insets(30, 0, 0, 0);
        cardPanelLocalVariableValue.add(buttonPanelLocalVariableValue, constraintsLocalVariableValue);

        return cardPanelLocalVariableValue;
    }

    private JLabel buildFieldLabel(String textParameterValue) {
        JLabel labelLocalVariableValue = new JLabel(textParameterValue);
        labelLocalVariableValue.setForeground(LABEL_COLOR);
        labelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 17));
        return labelLocalVariableValue;
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

    public String getActualPassword() {
        return new String(actualUserPasswordFieldFieldReference.getPassword());
    }

    public String getNewPassword() {
        return new String(newUserPasswordFieldFieldReference.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmUserPasswordFieldFieldReference.getPassword());
    }

    public void registerController(ActionListener controllerHandlerParameterValue) {
        changeButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
    }

    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                messageParameterValue,
                "Change Password",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
