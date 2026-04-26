package Rounded;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import presentation.ControllerViews.MenuController;

/**
 * Dialog personalizado para la configuración de cuenta de usuario.
 * Incluye botones para cerrar sesión, eliminar cuenta y cambiar contraseña.
 * Se implementa como un singleton.
 */
public class ConfigDialog extends JDialog {
    private JButton logoutButtonFieldReference;
    private JButton deleteAccountButtonFieldReference;
    private JButton changeUserPasswordButtonFieldReference;
    private ActionListener backControllerHandlerFieldReference;
    private MenuController menuControllerHandlerFieldReference;

    /** Acción para cerrar sesión */
    public static final String LOGOUT = "LOGOUT_CONFIG";
    /** Acción para eliminar cuenta */
    public static final String DELETE_ACCOUNT = "DELETE_ACCOUNT";
    /** Acción para cambiar contraseña */
    public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";

    // Colores usados en la interfaz
    private static final Color DARK_BLUE = new Color(0, 30, 60);
    private static final Color LIGHT_BLUE = new Color(195, 216, 236);
    private static final Color DARK_RED = new Color(180, 40, 40);
    private static final Color BRIGHT_RED = new Color(220, 60, 60);
    private static final Color TEXT_COLOR = Color.WHITE;

    /** Instancia única (singleton) del diálogo */
    private static Rounded.ConfigDialog instanceFieldReference;

    /**
     * Devuelve la instancia única del diálogo, creándola si no existe.
     * @param parent la ventana padre del diálogo
     * @return la instancia de {@code ConfigDialog}
     */
    public static Rounded.ConfigDialog getInstance(JFrame parentParameterValue) {
        if (instanceFieldReference == null) {
            instanceFieldReference = new Rounded.ConfigDialog(parentParameterValue);
        }
        instanceFieldReference.setLocationRelativeTo(parentParameterValue);
        return instanceFieldReference;
    }

    /**
     * Establece el controlador del menú asociado.
     * @param controller instancia de {@code MenuController}
     */
    public void setMenuController(MenuController controllerHandlerParameterValue) {
        this.menuControllerHandlerFieldReference = controllerHandlerParameterValue;
    }

    /**
     * Constructor privado para inicializar el diálogo con el padre dado.
     * @param parent la ventana padre
     */
    public ConfigDialog(JFrame parentParameterValue2) {
        super(parentParameterValue2, "Settings", true);
        initializeUI();
        setLocationRelativeTo(parentParameterValue2);
    }

    /**
     * Inicializa los componentes gráficos del diálogo.
     */
    private void initializeUI() {
        setSize(800, 500);
        setResizable(false);

        getContentPane().setBackground(LIGHT_BLUE);
        setLayout(new BorderLayout());

        // Panel de encabezado con botón atrás
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Panel principal de opciones
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
        mainPanelLocalVariableValue.add(Box.createRigidArea(new Dimension(0, 20)));

        logoutButtonFieldReference = createMenuButton("Logout", new Color(100, 100, 120));
        mainPanelLocalVariableValue.add(logoutButtonFieldReference);

        add(mainPanelLocalVariableValue, BorderLayout.CENTER);
    }

    /**
     * Crea un botón personalizado con estilo redondeado y efectos hover.
     * @param text texto a mostrar en el botón
     * @param bgColor color de fondo por defecto del botón
     * @return un botón estilizado con efectos visuales personalizados
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
                // Sin borde
            }

            @Override
            public void paint(Graphics gParameterValue3) {
                super.paint(gParameterValue3);
                if (isFocusPainted() && hasFocus()) {
                    // No se pinta nada adicional al enfocar
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

        // Efecto de hover para cambiar el cursor y color de fondo
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
     * Registra un controlador de eventos para manejar acciones de los botones.
     * @param controller el {@code ActionListener} que manejará los eventos de los botones
     */
    public void registerController(ActionListener controllerHandlerParameterValue2) {
        logoutButtonFieldReference.setActionCommand(LOGOUT);
        logoutButtonFieldReference.addActionListener(controllerHandlerParameterValue2);

        deleteAccountButtonFieldReference.setActionCommand(DELETE_ACCOUNT);
        deleteAccountButtonFieldReference.addActionListener(controllerHandlerParameterValue2);

        changeUserPasswordButtonFieldReference.setActionCommand(CHANGE_PASSWORD);
        changeUserPasswordButtonFieldReference.addActionListener(controllerHandlerParameterValue2);
    }

    /**
     * Oculta el diálogo sin eliminar la instancia.
     */
    @Override
    public void dispose() {
        super.setVisible(false);
    }

    /**
     * Cierra y elimina la instancia única del diálogo.
     */
    public static void closeInstance() {
        if (instanceFieldReference != null) {
            instanceFieldReference.setVisible(false);
            instanceFieldReference = null;
        }
    }

    /**
     * Crea el panel del encabezado con el botón de "volver" y título.
     * @return el panel configurado con estilo y funcionalidad
     */
    private JPanel createHeaderPanel() {
        JPanel panelLocalVariableValue = new JPanel(new BorderLayout());
        panelLocalVariableValue.setBackground(DARK_BLUE);
        panelLocalVariableValue.setPreferredSize(new Dimension(getWidth(), 60));

        JButton backButtonLocalVariableValue = HeaderButtonHelper.createBackButton(eventArgumentParameterValue -> {
            if (backControllerHandlerFieldReference != null) {
                backControllerHandlerFieldReference.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "BACK"));
            } else {
                dispose(); // comportamiento por defecto si no hay controlador
            }
        });

        panelLocalVariableValue.add(backButtonLocalVariableValue, BorderLayout.WEST);



        return panelLocalVariableValue;
    }

    /**
     * Establece el listener para el botón de volver.
     * @param listener el {@code ActionListener} que manejará la acción de volver
     */
    public void setBackButtonListener(ActionListener listenerParameterValue) {
        this.backControllerHandlerFieldReference = listenerParameterValue;
    }
}
