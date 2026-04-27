package Rounded;

import javax.swing.*;
import java.awt.*;

// Boton redondeado con soporte para degradado, outline y sombra
public class RoundedButton extends JButton {

    // Radio usado para redondear el borde del boton
    private final int radiusFieldReference;

    // Colores del degradado principal
    private Color startColorFieldReference;
    private Color endColorFieldReference;

    // Estado y configuracion del modo borde
    private boolean outlineModeFieldReference = false;
    private Color outlineColorFieldReference = new Color(52, 102, 219);
    private int outlineThicknessFieldReference = 2;

    // Permite activar o desactivar la sombra inferior
    private boolean shadowEnabledFieldReference = true;

    // Construye el boton base y desactiva la pintura estandar
    public RoundedButton(String textParameterValue, int radiusParameterValue) {
        super(textParameterValue);
        this.radiusFieldReference = radiusParameterValue;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setRolloverEnabled(true);
    }

    // Activa el modo degradado con los colores recibidos
    public void setGradientColors(Color startColorParameterValue,
                                  Color endColorParameterValue) {
        this.startColorFieldReference = startColorParameterValue;
        this.endColorFieldReference = endColorParameterValue;
        this.outlineModeFieldReference = false;
        repaint();
    }

    // Activa el modo outline y configura grosor y color del borde
    public void setOutlineMode(Color outlineColorParameterValue,
                               int outlineThicknessParameterValue) {
        this.outlineModeFieldReference = true;
        this.outlineColorFieldReference = outlineColorParameterValue;
        this.outlineThicknessFieldReference = outlineThicknessParameterValue;
        repaint();
    }

    // Permite mostrar o quitar la sombra del boton
    public void setShadowEnabled(boolean shadowEnabledParameterValue) {
        this.shadowEnabledFieldReference = shadowEnabledParameterValue;
        repaint();
    }

    // Dibuja el boton completo con el estilo personalizado
    @Override
    protected void paintComponent(Graphics graphicsParameterValue) {
        Graphics2D g2LocalVariableValue =
                (Graphics2D) graphicsParameterValue.create();
        g2LocalVariableValue.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2LocalVariableValue.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );

        ButtonModel buttonModelLocalVariableValue = getModel();

        int shadowOffsetLocalVariableValue =
                (!outlineModeFieldReference && shadowEnabledFieldReference) ? 6 : 0;
        int drawHeightLocalVariableValue =
                getHeight() - shadowOffsetLocalVariableValue;

        // Primero se pinta la sombra cuando el boton la usa
        if (!outlineModeFieldReference && shadowEnabledFieldReference) {
            g2LocalVariableValue.setColor(new Color(16, 34, 74, 55));
            g2LocalVariableValue.fillRoundRect(
                    6,
                    shadowOffsetLocalVariableValue,
                    getWidth() - 12,
                    Math.max(0, drawHeightLocalVariableValue),
                    radiusFieldReference,
                    radiusFieldReference
            );
        }

        // Segun el modo activo se dibuja outline o degradado
        if (outlineModeFieldReference) {
            Color fillColorLocalVariableValue = getBackground() != null
                    ? new Color(
                    getBackground().getRed(),
                    getBackground().getGreen(),
                    getBackground().getBlue(),
                    Math.max(25, getBackground().getAlpha())
            )
                    : new Color(255, 255, 255, 20);

            if (buttonModelLocalVariableValue.isRollover()) {
                fillColorLocalVariableValue = new Color(236, 242, 255, 110);
            }

            if (buttonModelLocalVariableValue.isPressed()) {
                fillColorLocalVariableValue = new Color(220, 230, 255, 140);
            }

            g2LocalVariableValue.setColor(fillColorLocalVariableValue);
            g2LocalVariableValue.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    drawHeightLocalVariableValue - 1,
                    radiusFieldReference,
                    radiusFieldReference
            );

            g2LocalVariableValue.setStroke(
                    new BasicStroke(outlineThicknessFieldReference)
            );
            g2LocalVariableValue.setColor(
                    buttonModelLocalVariableValue.isRollover()
                            ? shiftColor(outlineColorFieldReference, 18)
                            : outlineColorFieldReference
            );

            g2LocalVariableValue.drawRoundRect(
                    outlineThicknessFieldReference / 2,
                    outlineThicknessFieldReference / 2,
                    getWidth() - outlineThicknessFieldReference - 1,
                    drawHeightLocalVariableValue - outlineThicknessFieldReference - 1,
                    radiusFieldReference,
                    radiusFieldReference
            );
        } else {
            Color topColorLocalVariableValue =
                    startColorFieldReference != null
                            ? startColorFieldReference
                            : getBackground();
            Color bottomColorLocalVariableValue =
                    endColorFieldReference != null
                            ? endColorFieldReference
                            : getBackground().darker();

            if (buttonModelLocalVariableValue.isPressed()) {
                topColorLocalVariableValue = topColorLocalVariableValue.darker();
                bottomColorLocalVariableValue =
                        bottomColorLocalVariableValue.darker();
            } else if (buttonModelLocalVariableValue.isRollover()) {
                topColorLocalVariableValue =
                        shiftColor(topColorLocalVariableValue, 18);
                bottomColorLocalVariableValue =
                        shiftColor(bottomColorLocalVariableValue, 10);
            }

            GradientPaint gradientPaintLocalVariableValue = new GradientPaint(
                    0,
                    0,
                    topColorLocalVariableValue,
                    0,
                    drawHeightLocalVariableValue,
                    bottomColorLocalVariableValue
            );

            g2LocalVariableValue.setPaint(gradientPaintLocalVariableValue);
            g2LocalVariableValue.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    drawHeightLocalVariableValue - 1,
                    radiusFieldReference,
                    radiusFieldReference
            );
        }

        // El texto se centra manualmente dentro del boton
        g2LocalVariableValue.setFont(getFont());
        g2LocalVariableValue.setColor(getForeground());

        FontMetrics fontMetricsLocalVariableValue =
                g2LocalVariableValue.getFontMetrics();
        int textXLocalVariableValue =
                (getWidth()
                        - fontMetricsLocalVariableValue.stringWidth(getText())) / 2;
        int textYLocalVariableValue =
                ((drawHeightLocalVariableValue
                        - fontMetricsLocalVariableValue.getHeight()) / 2)
                        + fontMetricsLocalVariableValue.getAscent();

        g2LocalVariableValue.drawString(
                getText(),
                textXLocalVariableValue,
                textYLocalVariableValue
        );

        g2LocalVariableValue.dispose();
    }

    // No usamos el borde por defecto de Swing
    @Override
    protected void paintBorder(Graphics graphicsParameterValue) {
        // Sin borde por defecto
    }

    // Ajusta un color sumando intensidad sin superar el maximo
    private Color shiftColor(Color baseColorParameterValue, int amountParameterValue) {
        int redLocalVariableValue = Math.min(
                255,
                baseColorParameterValue.getRed() + amountParameterValue
        );
        int greenLocalVariableValue = Math.min(
                255,
                baseColorParameterValue.getGreen() + amountParameterValue
        );
        int blueLocalVariableValue = Math.min(
                255,
                baseColorParameterValue.getBlue() + amountParameterValue
        );

        return new Color(
                redLocalVariableValue,
                greenLocalVariableValue,
                blueLocalVariableValue,
                baseColorParameterValue.getAlpha()
        );
    }
}
