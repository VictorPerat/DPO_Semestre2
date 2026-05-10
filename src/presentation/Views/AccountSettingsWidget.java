package presentation.Views;

import Rounded.RoundedButton;
import presentation.UserRole;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Representa el widget de los cuenta ajustes.
 */
public class AccountSettingsWidget extends JPanel {

    /**
     * Constante para el cambio contrasena.
     */
    public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";
    /**
     * Constante para el cuenta.
     */
    public static final String DELETE_ACCOUNT = "DELETE_ACCOUNT";
    /**
     * Constante para el valor.
     */
    public static final String LOGOUT = "LOGOUT";

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color DESTRUCTIVE_COLOR = new Color(200, 45, 50);
    private static final Color CARD_BACKGROUND = new Color(255, 255, 255, 248);
    private static final Color TEXT_COLOR = new Color(35, 45, 65);
    private static final Color SECONDARY_TEXT = new Color(105, 116, 135);

    private static final int WIDGET_WIDTH = 220;
    private static final int WIDGET_HEIGHT = 290;
    private static final int LEFT_MARGIN = 24;
    private static final int BOTTOM_MARGIN = 24;

    private static final String SETTINGS_ICON_PRIMARY_PATH =
            ProjectPathResolver.resolveProjectPath("photos/Rueda_Ajustes.png");
    private static final String SETTINGS_ICON_FALLBACK_PATH =
            ProjectPathResolver.resolveProjectPath("photos/Rueda Ajustes.png");

    private ActionListener actionListenerFieldReference;
    private Window lastAnchorWindowFieldReference;
    private RoundedButton deleteAccountButtonFieldReference;
    private Component deleteAccountSpacerFieldReference;


    /**
     * Crea una instancia de los cuenta ajustes.
     */
    public AccountSettingsWidget() {
        setOpaque(false);
        setLayout(new BorderLayout());

        JPanel rootPanelLocalVariableValue = new RoundedWidgetPanel();
        rootPanelLocalVariableValue.setLayout(
                new BoxLayout(rootPanelLocalVariableValue, BoxLayout.Y_AXIS));
        rootPanelLocalVariableValue.setOpaque(false);
        rootPanelLocalVariableValue.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel titlePanelLocalVariableValue = buildTitlePanel();
        titlePanelLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel buttonsPanelLocalVariableValue = buildButtonsPanel();
        buttonsPanelLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        rootPanelLocalVariableValue.add(titlePanelLocalVariableValue);
        rootPanelLocalVariableValue.add(Box.createVerticalStrut(14));
        rootPanelLocalVariableValue.add(buttonsPanelLocalVariableValue);

        add(rootPanelLocalVariableValue, BorderLayout.CENTER);

        Dimension widgetSizeLocalVariableValue =
                new Dimension(WIDGET_WIDTH, WIDGET_HEIGHT);
        setPreferredSize(widgetSizeLocalVariableValue);
        setMinimumSize(widgetSizeLocalVariableValue);
        setMaximumSize(widgetSizeLocalVariableValue);
        setSize(widgetSizeLocalVariableValue);
    }


    /**
     * Construye el titulo.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildTitlePanel() {
        JPanel titlePanelLocalVariableValue = new JPanel();
        titlePanelLocalVariableValue.setOpaque(false);
        titlePanelLocalVariableValue.setLayout(
                new BoxLayout(titlePanelLocalVariableValue, BoxLayout.Y_AXIS));

        JPanel topRowLocalVariableValue = new JPanel(new BorderLayout(10, 0));
        topRowLocalVariableValue.setOpaque(false);

        JLabel iconLabelLocalVariableValue = new JLabel();
        iconLabelLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabelLocalVariableValue.setVerticalAlignment(SwingConstants.CENTER);
        iconLabelLocalVariableValue.setPreferredSize(new Dimension(46, 46));

        try {
            String iconPathLocalVariableValue =
                    new java.io.File(SETTINGS_ICON_PRIMARY_PATH).exists()
                            ? SETTINGS_ICON_PRIMARY_PATH
                            : SETTINGS_ICON_FALLBACK_PATH;
            Image rawImageLocalVariableValue =
                    new ImageIcon(iconPathLocalVariableValue).getImage();
            Image scaledImageLocalVariableValue =
                    rawImageLocalVariableValue.getScaledInstance(42, 42, Image.SCALE_SMOOTH);
            iconLabelLocalVariableValue.setIcon(new ImageIcon(scaledImageLocalVariableValue));
        } catch (Exception ignoredExceptionParameterValue) {
            iconLabelLocalVariableValue.setText("S");
            iconLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 22));
            iconLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        }

        JLabel titleLabelLocalVariableValue = new JLabel("ACCOUNT");
        titleLabelLocalVariableValue.setForeground(TEXT_COLOR);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 17));

        topRowLocalVariableValue.add(iconLabelLocalVariableValue, BorderLayout.WEST);
        topRowLocalVariableValue.add(titleLabelLocalVariableValue, BorderLayout.CENTER);
        topRowLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel accentBarLocalVariableValue = new JPanel();
        accentBarLocalVariableValue.setBackground(ACCENT_COLOR);
        accentBarLocalVariableValue.setMaximumSize(new Dimension(60, 4));
        accentBarLocalVariableValue.setPreferredSize(new Dimension(60, 4));

        JPanel accentBarWrapperLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        accentBarWrapperLocalVariableValue.setOpaque(false);
        accentBarWrapperLocalVariableValue.add(accentBarLocalVariableValue);
        accentBarWrapperLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subLabelLocalVariableValue = new JLabel("Manage your session");
        subLabelLocalVariableValue.setForeground(SECONDARY_TEXT);
        subLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 12));
        subLabelLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        titlePanelLocalVariableValue.add(topRowLocalVariableValue);
        titlePanelLocalVariableValue.add(Box.createVerticalStrut(8));
        titlePanelLocalVariableValue.add(accentBarWrapperLocalVariableValue);
        titlePanelLocalVariableValue.add(Box.createVerticalStrut(6));
        titlePanelLocalVariableValue.add(subLabelLocalVariableValue);

        return titlePanelLocalVariableValue;
    }


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildButtonsPanel() {
        JPanel buttonsPanelLocalVariableValue = new JPanel();
        buttonsPanelLocalVariableValue.setOpaque(false);
        buttonsPanelLocalVariableValue.setLayout(
                new BoxLayout(buttonsPanelLocalVariableValue, BoxLayout.Y_AXIS));

        RoundedButton changePasswordButtonLocalVariableValue =
                buildActionButton("Change Password", CHANGE_PASSWORD, ACCENT_COLOR);
        deleteAccountButtonFieldReference =
                buildActionButton("Delete Account", DELETE_ACCOUNT, DESTRUCTIVE_COLOR);
        RoundedButton logoutButtonLocalVariableValue =
                buildActionButton("Logout", LOGOUT, ACCENT_COLOR);

        logoutButtonLocalVariableValue.setForeground(Color.WHITE);
        logoutButtonLocalVariableValue.setBackground(new Color(0, 0, 0, 0));
        logoutButtonLocalVariableValue.setOutlineMode(null, 0);
        logoutButtonLocalVariableValue.setGradientColors(
                ACCENT_COLOR, new Color(36, 80, 180));
        logoutButtonLocalVariableValue.setShadowEnabled(true);

        buttonsPanelLocalVariableValue.add(changePasswordButtonLocalVariableValue);
        buttonsPanelLocalVariableValue.add(Box.createVerticalStrut(8));
        buttonsPanelLocalVariableValue.add(deleteAccountButtonFieldReference);
        deleteAccountSpacerFieldReference = Box.createVerticalStrut(8);
        buttonsPanelLocalVariableValue.add(deleteAccountSpacerFieldReference);
        buttonsPanelLocalVariableValue.add(logoutButtonLocalVariableValue);

        return buttonsPanelLocalVariableValue;
    }


    /**
     * Construye el accion.
     *
     * @param textParameterValue texto que usa la operacion.
     * @param commandParameterValue dato de entrada de la operacion.
     * @param colorParameterValue dato de entrada de la operacion.
     * @return resultado de la operacion.
     */
    private RoundedButton buildActionButton(String textParameterValue,
                                            String commandParameterValue,
                                            Color colorParameterValue) {
        RoundedButton buttonLocalVariableValue =
                new RoundedButton(textParameterValue, 14);
        buttonLocalVariableValue.setActionCommand(commandParameterValue);
        buttonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 13));
        buttonLocalVariableValue.setForeground(colorParameterValue);
        buttonLocalVariableValue.setBackground(new Color(255, 255, 255, 235));
        buttonLocalVariableValue.setOutlineMode(colorParameterValue, 2);
        buttonLocalVariableValue.setShadowEnabled(false);

        Dimension buttonSizeLocalVariableValue =
                new Dimension(WIDGET_WIDTH - 36, 38);
        buttonLocalVariableValue.setPreferredSize(buttonSizeLocalVariableValue);
        buttonLocalVariableValue.setMinimumSize(buttonSizeLocalVariableValue);
        buttonLocalVariableValue.setMaximumSize(buttonSizeLocalVariableValue);
        buttonLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        buttonLocalVariableValue.addActionListener(eventArgumentParameterValue -> {
            if (actionListenerFieldReference == null) {
                return;
            }

            actionListenerFieldReference.actionPerformed(new ActionEvent(
                    buttonLocalVariableValue,
                    ActionEvent.ACTION_PERFORMED,
                    commandParameterValue
            ));
        });

        return buttonLocalVariableValue;
    }


    /**
     * Actualiza el cuenta accion.
     *
     * @param listenerParameterValue listener que se registra.
     */
    public void setAccountActionListener(ActionListener listenerParameterValue) {
        this.actionListenerFieldReference = listenerParameterValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param roleParameterValue rol que usa la operacion.
     */
    public void configureForRole(UserRole roleParameterValue) {
        boolean shouldShowDeleteAccountLocalVariableValue =
                roleParameterValue == UserRole.PLAYER;

        if (deleteAccountButtonFieldReference != null) {
            deleteAccountButtonFieldReference.setVisible(
                    shouldShowDeleteAccountLocalVariableValue
            );
        }

        if (deleteAccountSpacerFieldReference != null) {
            deleteAccountSpacerFieldReference.setVisible(
                    shouldShowDeleteAccountLocalVariableValue
            );
        }

        revalidate();
        repaint();
    }


    /**
     * Gestiona esta operacion.
     *
     * @param anchorWindowParameterValue ventana que se usa como referencia.
     */
    public void positionBottomLeftAttachedTo(Window anchorWindowParameterValue) {
        this.lastAnchorWindowFieldReference = anchorWindowParameterValue;

        Container parentLocalVariableValue = getParent();
        int availableHeightLocalVariableValue;

        if (parentLocalVariableValue != null) {
            availableHeightLocalVariableValue = parentLocalVariableValue.getHeight();
        } else if (anchorWindowParameterValue != null) {
            availableHeightLocalVariableValue = anchorWindowParameterValue.getHeight();
        } else {
            Rectangle boundsLocalVariableValue =
                    GraphicsEnvironment.getLocalGraphicsEnvironment()
                            .getMaximumWindowBounds();
            availableHeightLocalVariableValue = boundsLocalVariableValue.height;
        }

        int xLocalVariableValue = LEFT_MARGIN;
        int yLocalVariableValue =
                Math.max(BOTTOM_MARGIN,
                        availableHeightLocalVariableValue - WIDGET_HEIGHT - BOTTOM_MARGIN);

        setBounds(xLocalVariableValue, yLocalVariableValue, WIDGET_WIDTH, WIDGET_HEIGHT);
    }


    /**
     * Gestiona esta operacion.
     */
    public void refreshLastPosition() {
        positionBottomLeftAttachedTo(lastAnchorWindowFieldReference);
    }


    /**
     * Agrupa la logica de el widget.
     */
    private static class RoundedWidgetPanel extends JPanel {


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
            int radiusLocalVariableValue = 24;

            g2LocalVariableValue.setColor(new Color(8, 20, 46, 70));
            g2LocalVariableValue.fillRoundRect(8, 10,
                    widthLocalVariableValue - 16,
                    heightLocalVariableValue - 12,
                    radiusLocalVariableValue, radiusLocalVariableValue);

            g2LocalVariableValue.setColor(CARD_BACKGROUND);
            g2LocalVariableValue.fillRoundRect(0, 0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 8,
                    radiusLocalVariableValue, radiusLocalVariableValue);

            g2LocalVariableValue.setColor(ACCENT_COLOR);
            g2LocalVariableValue.setStroke(new BasicStroke(2f));
            g2LocalVariableValue.drawRoundRect(0, 0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 8,
                    radiusLocalVariableValue, radiusLocalVariableValue);

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }
}


