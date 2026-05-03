package presentation.Views;

import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class AccountSettingsWidget extends JFrame {

    public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";
    public static final String LOGOUT = "LOGOUT";

    private static final int WIDGET_SIZE = 78;
    private static final int LEFT_OFFSET = 50;
    private static final int BOTTOM_OFFSET = 62;

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color DARK_BLUE = new Color(22, 49, 72);
    private static final Color WHITE_BACKGROUND = new Color(255, 255, 255, 245);

    private static final String SETTINGS_ICON_PRIMARY_PATH =
            ProjectPathResolver.resolveProjectPath("photos/Rueda_Ajustes.png");

    private static final String SETTINGS_ICON_FALLBACK_PATH =
            ProjectPathResolver.resolveProjectPath("photos/Rueda Ajustes.png");

    private JButton settingsButtonFieldReference;
    private ActionListener actionListenerFieldReference;
    private Window lastAnchorWindowFieldReference;

    public AccountSettingsWidget() {
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusableWindowState(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));

        JPanel rootPanelLocalVariableValue = new RoundedSettingsPanel();
        rootPanelLocalVariableValue.setLayout(new GridBagLayout());
        rootPanelLocalVariableValue.setOpaque(false);
        rootPanelLocalVariableValue.setBorder(new EmptyBorder(8, 8, 8, 8));

        settingsButtonFieldReference = buildSettingsButton();
        rootPanelLocalVariableValue.add(settingsButtonFieldReference);

        setContentPane(rootPanelLocalVariableValue);
        setSize(WIDGET_SIZE, WIDGET_SIZE);
    }

    private JButton buildSettingsButton() {
        JButton buttonLocalVariableValue = new JButton();
        buttonLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        buttonLocalVariableValue.setContentAreaFilled(false);
        buttonLocalVariableValue.setFocusPainted(false);
        buttonLocalVariableValue.setOpaque(false);
        buttonLocalVariableValue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        try {
            String iconPathLocalVariableValue =
                    new java.io.File(SETTINGS_ICON_PRIMARY_PATH).exists()
                            ? SETTINGS_ICON_PRIMARY_PATH
                            : SETTINGS_ICON_FALLBACK_PATH;

            Image rawImageLocalVariableValue =
                    new ImageIcon(iconPathLocalVariableValue).getImage();

            Image scaledImageLocalVariableValue =
                    rawImageLocalVariableValue.getScaledInstance(
                            52,
                            52,
                            Image.SCALE_SMOOTH
                    );

            buttonLocalVariableValue.setIcon(new ImageIcon(scaledImageLocalVariableValue));
        } catch (Exception ignoredExceptionParameterValue) {
            buttonLocalVariableValue.setText("⚙");
            buttonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 34));
            buttonLocalVariableValue.setForeground(Color.WHITE);
        }

        buttonLocalVariableValue.addActionListener(
                eventArgumentParameterValue -> showOptionsMenu()
        );

        return buttonLocalVariableValue;
    }

    public void setAccountActionListener(ActionListener listenerParameterValue) {
        this.actionListenerFieldReference = listenerParameterValue;
    }

    public void positionBottomLeftAttachedTo(Window anchorWindowParameterValue) {
        this.lastAnchorWindowFieldReference = anchorWindowParameterValue;

        Rectangle boundsLocalVariableValue;

        if (anchorWindowParameterValue == null) {
            boundsLocalVariableValue =
                    GraphicsEnvironment.getLocalGraphicsEnvironment()
                            .getMaximumWindowBounds();
        } else {
            boundsLocalVariableValue = anchorWindowParameterValue.getBounds();
        }

        int xLocalVariableValue = boundsLocalVariableValue.x + LEFT_OFFSET;

        int yLocalVariableValue =
                boundsLocalVariableValue.y
                        + boundsLocalVariableValue.height
                        - WIDGET_SIZE
                        - BOTTOM_OFFSET;

        setLocation(xLocalVariableValue, yLocalVariableValue);
    }

    public void refreshLastPosition() {
        positionBottomLeftAttachedTo(lastAnchorWindowFieldReference);
    }

    private void showOptionsMenu() {
        JPopupMenu menuLocalVariableValue = new JPopupMenu();
        menuLocalVariableValue.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));

        JMenuItem changePasswordItemLocalVariableValue =
                buildMenuItem("Change Password", CHANGE_PASSWORD);

        JMenuItem logoutItemLocalVariableValue =
                buildMenuItem("Logout", LOGOUT);

        menuLocalVariableValue.add(changePasswordItemLocalVariableValue);
        menuLocalVariableValue.addSeparator();
        menuLocalVariableValue.add(logoutItemLocalVariableValue);

        menuLocalVariableValue.show(
                settingsButtonFieldReference,
                WIDGET_SIZE - 4,
                -4
        );
    }

    private JMenuItem buildMenuItem(String textParameterValue,
                                    String commandParameterValue) {
        JMenuItem itemLocalVariableValue = new JMenuItem(textParameterValue);
        itemLocalVariableValue.setActionCommand(commandParameterValue);
        itemLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 14));
        itemLocalVariableValue.setForeground(DARK_BLUE);
        itemLocalVariableValue.setBackground(Color.WHITE);
        itemLocalVariableValue.setPreferredSize(new Dimension(180, 38));

        itemLocalVariableValue.addActionListener(eventArgumentParameterValue -> {
            if (actionListenerFieldReference != null) {
                actionListenerFieldReference.actionPerformed(eventArgumentParameterValue);
            }
        });

        return itemLocalVariableValue;
    }

    private static class RoundedSettingsPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics graphicsParameterValue) {
            Graphics2D g2LocalVariableValue =
                    (Graphics2D) graphicsParameterValue.create();

            g2LocalVariableValue.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int sizeLocalVariableValue =
                    Math.min(getWidth(), getHeight()) - 8;

            int xLocalVariableValue =
                    (getWidth() - sizeLocalVariableValue) / 2;

            int yLocalVariableValue =
                    (getHeight() - sizeLocalVariableValue) / 2;

            g2LocalVariableValue.setColor(new Color(8, 20, 46, 80));
            g2LocalVariableValue.fillOval(
                    xLocalVariableValue + 4,
                    yLocalVariableValue + 6,
                    sizeLocalVariableValue,
                    sizeLocalVariableValue
            );

            g2LocalVariableValue.setColor(WHITE_BACKGROUND);
            g2LocalVariableValue.fillOval(
                    xLocalVariableValue,
                    yLocalVariableValue,
                    sizeLocalVariableValue,
                    sizeLocalVariableValue
            );

            g2LocalVariableValue.setColor(ACCENT_COLOR);
            g2LocalVariableValue.setStroke(new BasicStroke(2.5f));
            g2LocalVariableValue.drawOval(
                    xLocalVariableValue,
                    yLocalVariableValue,
                    sizeLocalVariableValue,
                    sizeLocalVariableValue
            );

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }
}