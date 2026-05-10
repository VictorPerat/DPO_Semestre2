package Rounded;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Agrupa utilidades para esta parte de la aplicacion.
 */
public class HeaderButtonHelper {


    /**
     * Crea el vuelta.
     *
     * @param listenerParameterValue listener que se registra.
     * @return elemento creado por el metodo.
     */
    public static JButton createBackButton(ActionListener listenerParameterValue) {
        JButton buttonLocalVariableValue = new JButton("←");
        styleHeaderButton(buttonLocalVariableValue);
        buttonLocalVariableValue.addActionListener(listenerParameterValue);
        return buttonLocalVariableValue;
    }


    /**
     * Crea el configuracion.
     *
     * @param listenerParameterValue2 dato de entrada de la operacion.
     * @return elemento creado por el metodo.
     */
    public static JButton createConfigButton(ActionListener listenerParameterValue2) {
        JButton buttonLocalVariableValue2 = new JButton("⚙️");
        buttonLocalVariableValue2.addActionListener(listenerParameterValue2);
        buttonLocalVariableValue2.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        buttonLocalVariableValue2.setContentAreaFilled(false);
        buttonLocalVariableValue2.setForeground(Color.WHITE);
        buttonLocalVariableValue2.setFont(
                buttonLocalVariableValue2.getFont().deriveFont(Font.PLAIN, 16f)
        );

        return buttonLocalVariableValue2;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param buttonParameterValue dato de entrada de la operacion.
     */
    private static void styleHeaderButton(JButton buttonParameterValue) {
        buttonParameterValue.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        buttonParameterValue.setContentAreaFilled(false);
        buttonParameterValue.setForeground(Color.WHITE);
        buttonParameterValue.setFocusPainted(false);

        buttonParameterValue.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent evtParameterValue) {
                buttonParameterValue.setForeground(new Color(200, 200, 200));
            }


            public void mouseExited(MouseEvent evtParameterValue2) {
                buttonParameterValue.setForeground(Color.WHITE);
            }
        });
    }
}


