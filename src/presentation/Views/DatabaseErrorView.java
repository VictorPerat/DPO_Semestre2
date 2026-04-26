package presentation.Views;

import javax.swing.*;
import java.awt.*;

/**
 * Vista que representa un error de conexión con la base de datos.
 * Se muestra cuando la aplicación no puede acceder a la base de datos.
 */
public class DatabaseErrorView extends JFrame {

    /**
     * Constructor que configura y muestra una ventana de error de base de datos.
     */
    public DatabaseErrorView() {
        setTitle("Database Error");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(0, 0, 51)); // Fondo azul oscuro

        // Título del error
        JLabel titleLocalVariableValue = new JLabel("ERROR");
        titleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 36));
        titleLocalVariableValue.setForeground(Color.WHITE);
        titleLocalVariableValue.setBounds(230, 30, 200, 40);
        add(titleLocalVariableValue);

        // Mensaje explicativo
        JLabel messageLocalVariableValue = new JLabel("<html><div style='text-align: center;'>No se pudo conectar a la base de datos.<br>Por favor, verifica tu conexión o contacta con soporte.</div></html>");
        messageLocalVariableValue.setForeground(Color.LIGHT_GRAY);
        messageLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLocalVariableValue.setBounds(100, 100, 400, 80);
        add(messageLocalVariableValue);

    }
}
