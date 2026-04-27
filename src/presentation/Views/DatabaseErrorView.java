package presentation.Views;

import javax.swing.*;
import java.awt.*;

/**
 * Vista que representa un error de conexion con la base de datos.
 * Se muestra cuando la aplicacion no puede acceder a la base de datos.
 */
public class DatabaseErrorView extends JFrame {

    // Construye la ventana que informa del fallo de conexion
    public DatabaseErrorView() {
        setTitle("Database Error");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(0, 0, 51));

        // Titulo principal del mensaje de error
        JLabel titleLocalVariableValue = new JLabel("ERROR");
        titleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 36));
        titleLocalVariableValue.setForeground(Color.WHITE);
        titleLocalVariableValue.setBounds(230, 30, 200, 40);
        add(titleLocalVariableValue);

        // Mensaje con la causa general y la accion recomendada
        JLabel messageLocalVariableValue = new JLabel(
                "<html><div style='text-align: center;'>"
                        + "No se pudo conectar a la base de datos.<br>"
                        + "Por favor, verifica tu conexion o contacta con soporte."
                        + "</div></html>"
        );
        messageLocalVariableValue.setForeground(Color.LIGHT_GRAY);
        messageLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLocalVariableValue.setBounds(100, 100, 400, 80);
        add(messageLocalVariableValue);
    }
}
