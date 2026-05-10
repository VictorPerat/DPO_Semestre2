package presentation.Views;

import javax.swing.*;
import java.awt.*;


/**
 * Representa la vista del base de datos error.
 */
public class DatabaseErrorView extends JPanel {


    /**
     * Crea una instancia de el base de datos error.
     */
    public DatabaseErrorView() {
        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245));
        JLabel titleLocalVariableValue = new JLabel("Database connection error", SwingConstants.CENTER);
        titleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 26));
        JLabel messageLocalVariableValue = new JLabel("The app could not connect to the database. Please check config.json and MySQL.", SwingConstants.CENTER);
        messageLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 16));
        JPanel panelLocalVariableValue = new JPanel(new GridLayout(2, 1, 0, 10));
        panelLocalVariableValue.setOpaque(false);
        panelLocalVariableValue.add(titleLocalVariableValue);
        panelLocalVariableValue.add(messageLocalVariableValue);
        add(panelLocalVariableValue, BorderLayout.CENTER);
    }
}


