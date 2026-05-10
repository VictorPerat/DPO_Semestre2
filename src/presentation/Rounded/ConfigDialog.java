package Rounded;

import presentation.UserRole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Representa el dialogo de la configuracion.
 */
public class ConfigDialog extends JDialog {

    private JButton logoutButtonFieldReference;
    private JButton deleteAccountButtonFieldReference;
    private JButton changeUserPasswordButtonFieldReference;
    private Component deleteAccountSpacerFieldReference;


    private ActionListener backControllerHandlerFieldReference;

    /**
     * Constante para el valor.
     */
    public static final String LOGOUT = "LOGOUT";
    /**
     * Constante para el cuenta.
     */
    public static final String DELETE_ACCOUNT = "DELETE_ACCOUNT";
    /**
     * Constante para el cambio contrasena.
     */
    public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";


    private static final Color DARK_BLUE = new Color(0, 30, 60);
    private static final Color LIGHT_BLUE = new Color(195, 216, 236);
    private static final Color DARK_RED = new Color(180, 40, 40);
    private static final Color BRIGHT_RED = new Color(220, 60, 60);
    private static final Color TEXT_COLOR = Color.WHITE;


    private static Rounded.ConfigDialog instanceFieldReference;


    /**
     * Devuelve el instancia.
     *
     * @param parentParameterValue dato de entrada de la operacion.
     * @return el instancia.
     */
    public static Rounded.ConfigDialog getInstance(JFrame parentParameterValue) {
        if (instanceFieldReference == null) {
            instanceFieldReference = new Rounded.ConfigDialog(parentParameterValue);
        }
        instanceFieldReference.setLocationRelativeTo(parentParameterValue);
        return instanceFieldReference;
    }


    /**
     * Crea una instancia de el configuracion.
     *
     * @param parentParameterValue2 dato de entrada de la operacion.
     */
    public ConfigDialog(JFrame parentParameterValue2) {
        super(parentParameterValue2, "Settings", true);
        initializeUI();
        setLocationRelativeTo(parentParameterValue2);
    }


    /**
     * Gestiona esta operacion.
     */
    private void initializeUI() {
        setSize(800, 500);
        setResizable(false);

        getContentPane().setBackground(LIGHT_BLUE);
        setLayout(new BorderLayout());


        add(createHeaderPanel(), BorderLayout.NORTH);


        JPanel mainPanelLocalVariableValue = new JPanel();
        mainPanelLocalVariableValue.setLayout(new BoxLayout(mainPanelLocalVariableValue, BoxLayout.Y_AXIS));
        mainPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanelLocalVariableValue.setBackground(LIGHT_BLUE);

        JLabel titleLabelLocalVariableValue = new JLabel("Account Settings", SwingConstants.CENTER);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabelLocalVariableValue.setForeground(Color.WHITE);
        titleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanelLocalVariableValue.add(titleLabelLocalVariableValue);
        mainPanelLocalVariableValue.add(Box.createRigidArea(new Dimension(0, 40)));

        changeUserPasswordButtonFieldReference = createMenuButton("Change Password", DARK_BLUE);
        mainPanelLocalVariableValue.add(changeUserPasswordButtonFieldReference);
        mainPanelLocalVariableValue.add(Box.createRigidArea(new Dimension(0, 20)));

        deleteAccountButtonFieldReference = createMenuButton("Delete Account", DARK_RED);
        mainPanelLocalVariableValue.add(deleteAccountButtonFieldReference);
        deleteAccountSpacerFieldReference = Box.createRigidArea(new Dimension(0, 20));
        mainPanelLocalVariableValue.add(deleteAccountSpacerFieldReference);

        logoutButtonFieldReference = createMenuButton("Logout", new Color(100, 100, 120));
        mainPanelLocalVariableValue.add(logoutButtonFieldReference);

        add(mainPanelLocalVariableValue, BorderLayout.CENTER);
    }


    /**
     * Crea el menu.
     *
     * @param textParameterValue texto que usa la operacion.
     * @param bgColorParameterValue dato de entrada de la operacion.
     * @return elemento creado por el metodo.
     */
    private JButton createMenuButton(String textParameterValue, Color bgColorParameterValue) {
        JButton buttonLocalVariableValue = new JButton(textParameterValue) {

            @Override
            protected void paintComponent(Graphics gParameterValue) {
                Graphics2D g2LocalVariableValue = (Graphics2D) gParameterValue.create();
                g2LocalVariableValue.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2LocalVariableValue.setColor(bgColorParameterValue);
                g2LocalVariableValue.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2LocalVariableValue.dispose();
                super.paintComponent(gParameterValue);
            }


            @Override
            protected void paintBorder(Graphics gParameterValue2) {

            }


            @Override
            public void paint(Graphics gParameterValue3) {
                super.paint(gParameterValue3);
                if (isFocusPainted() && hasFocus()) {

                }
            }
        };

        buttonLocalVariableValue.setContentAreaFilled(false);
        buttonLocalVariableValue.setOpaque(false);
        buttonLocalVariableValue.setFocusPainted(false);
        buttonLocalVariableValue.setForeground(TEXT_COLOR);
        buttonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 18));
        buttonLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        buttonLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonLocalVariableValue.setMaximumSize(new Dimension(300, 50));


        buttonLocalVariableValue.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent evtParameterValue) {
                buttonLocalVariableValue.setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (bgColorParameterValue == DARK_RED) {
                    buttonLocalVariableValue.setBackground(BRIGHT_RED);
                } else {
                    buttonLocalVariableValue.setBackground(bgColorParameterValue.brighter());
                }
            }

            public void mouseExited(MouseEvent evtParameterValue2) {
                buttonLocalVariableValue.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                buttonLocalVariableValue.setBackground(bgColorParameterValue);
            }
        });

        return buttonLocalVariableValue;
    }


    /**
     * Registra la accion.
     *
     * @param controllerHandlerParameterValue2 dato de entrada de la operacion.
     */
    public void registerController(ActionListener controllerHandlerParameterValue2) {
        registerAction(logoutButtonFieldReference, LOGOUT, controllerHandlerParameterValue2);
        registerAction(deleteAccountButtonFieldReference, DELETE_ACCOUNT, controllerHandlerParameterValue2);
        registerAction(changeUserPasswordButtonFieldReference, CHANGE_PASSWORD, controllerHandlerParameterValue2);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param roleParameterValue rol que usa la operacion.
     */
    public void configureForRole(UserRole roleParameterValue) {
        boolean shouldShowDeleteAccountLocalVariableValue =
                roleParameterValue == UserRole.PLAYER;

        deleteAccountButtonFieldReference.setVisible(
                shouldShowDeleteAccountLocalVariableValue
        );
        deleteAccountSpacerFieldReference.setVisible(
                shouldShowDeleteAccountLocalVariableValue
        );

        revalidate();
        repaint();
    }


    /**
     * Gestiona esta operacion.
     */
    @Override
    public void dispose() {
        super.setVisible(false);
    }


    /**
     * Cierra el instancia.
     */
    public static void closeInstance() {
        if (instanceFieldReference != null) {
            instanceFieldReference.setVisible(false);
            instanceFieldReference = null;
        }
    }


    /**
     * Crea el contenido.
     *
     * @return elemento creado por el metodo.
     */
    private JPanel createHeaderPanel() {
        JPanel panelLocalVariableValue = new JPanel(new BorderLayout());
        panelLocalVariableValue.setBackground(DARK_BLUE);
        panelLocalVariableValue.setPreferredSize(new Dimension(getWidth(), 60));

        JButton backButtonLocalVariableValue = HeaderButtonHelper.createBackButton(eventArgumentParameterValue -> {
            if (backControllerHandlerFieldReference != null) {
                backControllerHandlerFieldReference.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "BACK"));
            } else {
                dispose();
            }
        });

        panelLocalVariableValue.add(backButtonLocalVariableValue, BorderLayout.WEST);


        return panelLocalVariableValue;
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
     * Registra la accion.
     *
     * @param buttonParameterValue dato de entrada de la operacion.
     * @param actionCommandParameterValue accion que usa la operacion.
     * @param controllerHandlerParameterValue dato de entrada de la operacion.
     */
    private void registerAction(JButton buttonParameterValue,
                                String actionCommandParameterValue,
                                ActionListener controllerHandlerParameterValue) {
        for (ActionListener existingListenerLocalVariableValue
                : buttonParameterValue.getActionListeners()) {
            buttonParameterValue.removeActionListener(existingListenerLocalVariableValue);
        }

        buttonParameterValue.setActionCommand(actionCommandParameterValue);
        buttonParameterValue.addActionListener(controllerHandlerParameterValue);
    }
}


