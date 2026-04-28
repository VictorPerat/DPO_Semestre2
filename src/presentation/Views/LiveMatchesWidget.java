package presentation.Views;

import bussines.LiveMatchesScoreboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Widget global "always-on-top" que muestra los partidos en directo.
 *
 * Cumple el apartado 2.9 del enunciado: la información de los partidos
 * en curso ha de estar siempre visible para el usuario autenticado,
 * independientemente de la pantalla en la que se encuentre.
 *
 * - Es un {@link JFrame} sin decoración (sin barra de título), que se
 *   ancla en la esquina superior derecha de la pantalla.
 * - {@code setAlwaysOnTop(true)} asegura que se mantiene encima del
 *   resto de ventanas de la aplicación.
 * - Mostrar el marcador real de cada partido lo hace
 *   {@link LiveMatchesScoreboard}, que es la fuente única de la
 *   información que se renderiza aquí.
 */
public class LiveMatchesWidget extends JFrame {

    private static final Color HEADER_COLOR = new Color(22, 49, 72);
    private static final Color BACKGROUND = new Color(255, 255, 255);
    private static final Color BORDER_COLOR = new Color(180, 190, 210);
    private static final Color TEXT_COLOR = new Color(40, 40, 40);
    private static final Color SECONDARY_TEXT = new Color(100, 100, 100);

    private static final int WIDGET_WIDTH = 320;
    private static final int WIDGET_TOP_OFFSET = 80;
    private static final int WIDGET_RIGHT_OFFSET = 30;

    private final JPanel matchesContainerFieldReference;
    private final JLabel headerLabelFieldReference;

    public LiveMatchesWidget() {
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusableWindowState(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getRootPane().setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));

        JPanel rootPanelLocalVariableValue = new JPanel(new BorderLayout());
        rootPanelLocalVariableValue.setBackground(BACKGROUND);

        // Cabecera
        JPanel headerPanelLocalVariableValue = new JPanel(new BorderLayout());
        headerPanelLocalVariableValue.setBackground(HEADER_COLOR);
        headerPanelLocalVariableValue.setBorder(new EmptyBorder(8, 12, 8, 12));

        headerLabelFieldReference = new JLabel("LIVE MATCHES (0)");
        headerLabelFieldReference.setForeground(Color.WHITE);
        headerLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanelLocalVariableValue.add(headerLabelFieldReference, BorderLayout.WEST);

        // Lista de partidos
        matchesContainerFieldReference = new JPanel();
        matchesContainerFieldReference.setLayout(
                new BoxLayout(matchesContainerFieldReference, BoxLayout.Y_AXIS)
        );
        matchesContainerFieldReference.setBackground(BACKGROUND);
        matchesContainerFieldReference.setBorder(new EmptyBorder(6, 6, 6, 6));

        JScrollPane scrollPaneLocalVariableValue =
                new JScrollPane(matchesContainerFieldReference);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneLocalVariableValue.getVerticalScrollBar().setUnitIncrement(12);

        rootPanelLocalVariableValue.add(headerPanelLocalVariableValue, BorderLayout.NORTH);
        rootPanelLocalVariableValue.add(scrollPaneLocalVariableValue, BorderLayout.CENTER);

        setContentPane(rootPanelLocalVariableValue);
        setSize(WIDGET_WIDTH, 220);
        positionTopRight();

        // Mostrar lista vacía inicial
        renderMatches(java.util.Collections.emptyList());
    }

    /**
     * Coloca el widget en la esquina superior derecha de la pantalla.
     */
    private void positionTopRight() {
        Rectangle screenBoundsLocalVariableValue =
                GraphicsEnvironment.getLocalGraphicsEnvironment()
                        .getMaximumWindowBounds();

        int xLocalVariableValue = screenBoundsLocalVariableValue.x
                + screenBoundsLocalVariableValue.width
                - WIDGET_WIDTH
                - WIDGET_RIGHT_OFFSET;
        int yLocalVariableValue = screenBoundsLocalVariableValue.y + WIDGET_TOP_OFFSET;
        setLocation(xLocalVariableValue, yLocalVariableValue);
    }

    /**
     * Repinta la lista de partidos visible. Se garantiza ejecución en
     * el EDT de Swing.
     */
    public void renderMatches(List<LiveMatchesScoreboard.ScoreSnapshot> snapshotsParameterValue) {
        Runnable updateRunnableLocalVariableValue = () -> {
            matchesContainerFieldReference.removeAll();
            headerLabelFieldReference.setText(
                    "LIVE MATCHES (" + snapshotsParameterValue.size() + ")"
            );

            if (snapshotsParameterValue.isEmpty()) {
                JLabel emptyLabelLocalVariableValue =
                        new JLabel("No matches in progress");
                emptyLabelLocalVariableValue.setForeground(SECONDARY_TEXT);
                emptyLabelLocalVariableValue.setFont(new Font("Arial", Font.ITALIC, 12));
                emptyLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
                emptyLabelLocalVariableValue.setBorder(new EmptyBorder(20, 0, 20, 0));
                matchesContainerFieldReference.add(emptyLabelLocalVariableValue);
            } else {
                for (LiveMatchesScoreboard.ScoreSnapshot snapshotLocalVariableValue
                        : snapshotsParameterValue) {
                    matchesContainerFieldReference.add(
                            buildMatchRow(snapshotLocalVariableValue)
                    );
                    matchesContainerFieldReference.add(Box.createVerticalStrut(4));
                }
            }

            // Ajusta altura según contenido (pero limitada)
            int desiredHeightLocalVariableValue = Math.min(
                    320,
                    Math.max(120, 50 + 36 * Math.max(1, snapshotsParameterValue.size()))
            );
            setSize(WIDGET_WIDTH, desiredHeightLocalVariableValue);

            matchesContainerFieldReference.revalidate();
            matchesContainerFieldReference.repaint();
        };

        if (SwingUtilities.isEventDispatchThread()) {
            updateRunnableLocalVariableValue.run();
        } else {
            SwingUtilities.invokeLater(updateRunnableLocalVariableValue);
        }
    }

    /**
     * Construye una fila visual con el marcador de un partido.
     */
    private JPanel buildMatchRow(LiveMatchesScoreboard.ScoreSnapshot snapshotParameterValue) {
        JPanel rowLocalVariableValue = new JPanel(new BorderLayout(8, 0));
        rowLocalVariableValue.setBackground(new Color(245, 247, 250));
        rowLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(6, 8, 6, 8)
        ));
        rowLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        rowLocalVariableValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel homeLabelLocalVariableValue =
                new JLabel(snapshotParameterValue.getHomeName(), SwingConstants.RIGHT);
        homeLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 12));
        homeLabelLocalVariableValue.setForeground(TEXT_COLOR);

        JLabel awayLabelLocalVariableValue =
                new JLabel(snapshotParameterValue.getAwayName(), SwingConstants.LEFT);
        awayLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 12));
        awayLabelLocalVariableValue.setForeground(TEXT_COLOR);

        JLabel scoreLabelLocalVariableValue = new JLabel(
                snapshotParameterValue.getHomeGoals()
                        + " - " + snapshotParameterValue.getAwayGoals(),
                SwingConstants.CENTER
        );
        scoreLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 14));
        scoreLabelLocalVariableValue.setForeground(HEADER_COLOR);
        scoreLabelLocalVariableValue.setPreferredSize(new Dimension(50, 22));

        rowLocalVariableValue.add(homeLabelLocalVariableValue, BorderLayout.WEST);
        rowLocalVariableValue.add(scoreLabelLocalVariableValue, BorderLayout.CENTER);
        rowLocalVariableValue.add(awayLabelLocalVariableValue, BorderLayout.EAST);

        return rowLocalVariableValue;
    }
}
