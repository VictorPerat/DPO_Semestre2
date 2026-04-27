package Rounded;

import javax.swing.*;
import java.awt.*;

// Campo de contrasena redondeado con placeholder y borde personalizado
public class RoundedPasswordField extends JPasswordField {

    // Radio usado para redondear el campo
    private final int radiusFieldReference;

    // Texto guia y colores del borde normal y enfocado
    private String placeholderFieldReference = "";
    private Color borderColorFieldReference = new Color(212, 219, 230);
    private Color focusBorderColorFieldReference = new Color(52, 102, 219);

    // Configura el campo para que se pinte manualmente
    public RoundedPasswordField(int columnsParameterValue, int radiusParameterValue) {
        super(columnsParameterValue);
        this.radiusFieldReference = radiusParameterValue;

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        setForeground(new Color(54, 66, 87));
        setCaretColor(new Color(54, 66, 87));
    }

    // Actualiza el texto guia cuando el campo esta vacio
    public void setPlaceholder(String placeholderParameterValue) {
        this.placeholderFieldReference = placeholderParameterValue;
        repaint();
    }

    // Cambia el color del borde en reposo
    public void setBorderColor(Color borderColorParameterValue) {
        this.borderColorFieldReference = borderColorParameterValue;
        repaint();
    }

    // Cambia el color del borde cuando el campo tiene foco
    public void setFocusBorderColor(Color focusBorderColorParameterValue) {
        this.focusBorderColorFieldReference = focusBorderColorParameterValue;
        repaint();
    }

    // Dibuja el fondo redondeado y el placeholder cuando procede
    @Override
    protected void paintComponent(Graphics graphicsParameterValue) {
        Graphics2D g2LocalVariableValue =
                (Graphics2D) graphicsParameterValue.create();
        g2LocalVariableValue.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2LocalVariableValue.setColor(getBackground());
        g2LocalVariableValue.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                radiusFieldReference,
                radiusFieldReference
        );

        g2LocalVariableValue.dispose();

        super.paintComponent(graphicsParameterValue);

        // Si no hay texto ni foco, mostramos el placeholder
        if (getPassword().length == 0
                && !isFocusOwner()
                && placeholderFieldReference != null
                && !placeholderFieldReference.isEmpty()) {
            Graphics2D placeholderGraphicsLocalVariableValue =
                    (Graphics2D) graphicsParameterValue.create();
            placeholderGraphicsLocalVariableValue.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            );
            placeholderGraphicsLocalVariableValue.setFont(getFont());
            placeholderGraphicsLocalVariableValue.setColor(
                    new Color(164, 172, 186)
            );

            Insets insetsLocalVariableValue = getInsets();
            FontMetrics fontMetricsLocalVariableValue =
                    placeholderGraphicsLocalVariableValue.getFontMetrics();
            int textYLocalVariableValue =
                    ((getHeight() - fontMetricsLocalVariableValue.getHeight()) / 2)
                            + fontMetricsLocalVariableValue.getAscent();

            placeholderGraphicsLocalVariableValue.drawString(
                    placeholderFieldReference,
                    insetsLocalVariableValue.left,
                    textYLocalVariableValue
            );
            placeholderGraphicsLocalVariableValue.dispose();
        }
    }

    // Dibuja el borde usando un color distinto si el campo esta enfocado
    @Override
    protected void paintBorder(Graphics graphicsParameterValue) {
        Graphics2D g2LocalVariableValue =
                (Graphics2D) graphicsParameterValue.create();
        g2LocalVariableValue.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2LocalVariableValue.setStroke(new BasicStroke(1.5f));
        g2LocalVariableValue.setColor(
                hasFocus() ? focusBorderColorFieldReference : borderColorFieldReference
        );
        g2LocalVariableValue.drawRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                radiusFieldReference,
                radiusFieldReference
        );

        g2LocalVariableValue.dispose();
    }
}
