package Rounded;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Utilidad para crear y estilizar botones de cabecera (header) en una interfaz Swing.
 * Incluye métodos para generar botones de navegación y configuración con un estilo unificado.
 */
public class HeaderButtonHelper {

    /**
     * Crea un botón de retroceso con estilo de cabecera.
     *
     * @param listener el {@link ActionListener} que se ejecutará al hacer clic en el botón
     * @return un botón estilizado con el símbolo de retroceso ("←")
     */
    public static JButton createBackButton(ActionListener listenerParameterValue) {
        JButton buttonLocalVariableValue = new JButton("←");
        styleHeaderButton(buttonLocalVariableValue);
        buttonLocalVariableValue.addActionListener(listenerParameterValue);
        return buttonLocalVariableValue;
    }

    /**
     * Crea un botón de configuración con un símbolo de engranaje ("⚙️").
     *
     * @param listener el {@link ActionListener} que se ejecutará al hacer clic en el botón
     * @return un botón estilizado con el símbolo de configuración
     */
    public static JButton createConfigButton(ActionListener listenerParameterValue2) {
        JButton buttonLocalVariableValue2 = new JButton("⚙️");
        buttonLocalVariableValue2.addActionListener(listenerParameterValue2);
        buttonLocalVariableValue2.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        buttonLocalVariableValue2.setContentAreaFilled(false);
        buttonLocalVariableValue2.setForeground(Color.WHITE);
        buttonLocalVariableValue2.setFont(buttonLocalVariableValue2.getFont().deriveFont(Font.PLAIN, 16f));

        return buttonLocalVariableValue2;
    }

    /**
     * Aplica estilo estándar a un botón de cabecera, incluyendo espaciado, color de texto y efecto hover.
     *
     * @param button el botón al que se le aplicará el estilo
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
