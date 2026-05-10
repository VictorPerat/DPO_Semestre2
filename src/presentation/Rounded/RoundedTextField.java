package Rounded;

import javax.swing.*;
import java.awt.*;


/**
 * Agrupa la logica de el texto.
 */
public class RoundedTextField extends JTextField {


    private final int radiusFieldReference;


    private String placeholderFieldReference = "";
    private Color borderColorFieldReference = new Color(212, 219, 230);
    private Color focusBorderColorFieldReference = new Color(52, 102, 219);


    /**
     * Crea una instancia de el texto.
     *
     * @param columnsParameterValue dato de entrada de la operacion.
     * @param radiusParameterValue dato de entrada de la operacion.
     */
    public RoundedTextField(int columnsParameterValue, int radiusParameterValue) {
        super(columnsParameterValue);
        this.radiusFieldReference = radiusParameterValue;

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        setForeground(new Color(54, 66, 87));
        setCaretColor(new Color(54, 66, 87));
    }


    /**
     * Actualiza el contenido.
     *
     * @param placeholderParameterValue dato de entrada de la operacion.
     */
    public void setPlaceholder(String placeholderParameterValue) {
        this.placeholderFieldReference = placeholderParameterValue;
        repaint();
    }


    /**
     * Actualiza el contenido.
     *
     * @param borderColorParameterValue dato de entrada de la operacion.
     */
    public void setBorderColor(Color borderColorParameterValue) {
        this.borderColorFieldReference = borderColorParameterValue;
        repaint();
    }


    /**
     * Actualiza el contenido.
     *
     * @param focusBorderColorParameterValue dato de entrada de la operacion.
     */
    public void setFocusBorderColor(Color focusBorderColorParameterValue) {
        this.focusBorderColorFieldReference = focusBorderColorParameterValue;
        repaint();
    }


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


        if (getText().isEmpty()
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


    /**
     * Gestiona esta operacion.
     *
     * @param graphicsParameterValue dato de entrada de la operacion.
     */
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


