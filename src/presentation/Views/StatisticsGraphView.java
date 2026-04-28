package presentation.Views;

import bussines.objects.League;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
/**
 * {@code StatisticsGraphView} representa una vista gráfica de estadísticas de ligas.
 * Permite visualizar la evolución de puntos de los equipos a lo largo de las semanas
 * mediante un gráfico personalizado.
 *
 * La interfaz gráfica está compuesta por una barra superior, botones para seleccionar ligas,
 * y un gráfico lineal personalizado que representa la evolución de los puntos.
 */
public class StatisticsGraphView extends JFrame {

    private static final Color DARK_BLUE = new Color(22, 49, 72);
    private static final Color LIGHT_BLUE = new Color(195, 216, 236);
    private static final Color BACKGROUND = new Color(240, 240, 240);

    // Paneles principales de la ventana
    private JPanel mainPanelFieldReference;
    private JPanel topPanelFieldReference;
    private JPanel chartContainerFieldReference;
    private JPanel titlePanelFieldReference;

    private JButton backButtonFieldReference;
    private JButton configButtonFieldReference;
    private LeagueManagerChartPanelComponent chartPanelFieldReference;
    private JLabel leagueReferenceTitleLabelFieldReference;

    private Map<Integer, int[][]> leagueReferenceDataFieldReference = new HashMap<>();
    private Map<Integer, Integer> currentWeeksFieldReference = new HashMap<>();
    private static String[] teamReferenceNamesFieldReference;
    private static int countIdentifiersFieldReference = 0;
    /**
     * Constructor que inicializa la ventana principal de la vista de estadísticas.
     * Configura los componentes visuales y datos de prueba para representación inicial.
     */
    public StatisticsGraphView() {
        setTitle("Estadísticas de Liga");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanelFieldReference = new JPanel(new BorderLayout(0, 10));
        mainPanelFieldReference.setBackground(BACKGROUND);
        mainPanelFieldReference.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Top panel
        topPanelFieldReference = new JPanel();
        topPanelFieldReference.setLayout(new BoxLayout(topPanelFieldReference, BoxLayout.Y_AXIS));
        topPanelFieldReference.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        topPanelFieldReference.setAlignmentX(Component.LEFT_ALIGNMENT);

        titlePanelFieldReference = createTitlePanel();
        topPanelFieldReference.add(titlePanelFieldReference);

        chartContainerFieldReference = createChartPanel();
        mainPanelFieldReference.add(topPanelFieldReference, BorderLayout.NORTH);
        mainPanelFieldReference.add(chartContainerFieldReference, BorderLayout.CENTER);

        add(mainPanelFieldReference);
    }
    /**
     * Crea el panel de título con los botones de navegación y el título principal.
     * @return Panel de título configurado.
     */
    private JPanel createTitlePanel() {
        JPanel panelLocalVariableValue = new JPanel(new BorderLayout());
        panelLocalVariableValue.setBackground(DARK_BLUE);
        panelLocalVariableValue.setPreferredSize(new Dimension(800, 60));
        panelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        panelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        backButtonFieldReference = Rounded.HeaderButtonHelper.createBackButton(null);
        configButtonFieldReference = Rounded.HeaderButtonHelper.createConfigButton(null);

        JLabel titleLocalVariableValue = new JLabel("STATISTICS GRAPH", SwingConstants.CENTER);
        titleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 28));
        titleLocalVariableValue.setForeground(Color.WHITE);

        panelLocalVariableValue.add(backButtonFieldReference, BorderLayout.WEST);
        panelLocalVariableValue.add(titleLocalVariableValue, BorderLayout.CENTER);
        panelLocalVariableValue.add(configButtonFieldReference, BorderLayout.EAST);

        return panelLocalVariableValue;
    }
    /**
     * Crea un panel que muestra una lista horizontal de botones para seleccionar ligas.
     * @param leagues Lista de ligas disponibles.
     * @param listener Escuchador para manejar eventos de botón.
     * @return Panel que contiene los botones de ligas.
     */
    private JPanel createLeaguesPanel(List<League> leaguesParameterValue, ActionListener listenerParameterValue) {
        JPanel containerLocalVariableValue = new JPanel(new BorderLayout());
        containerLocalVariableValue.setBackground(LIGHT_BLUE);
        containerLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leaguesPanelLocalVariableValue = new JPanel();
        leaguesPanelLocalVariableValue.setLayout(new BoxLayout(leaguesPanelLocalVariableValue, BoxLayout.X_AXIS));
        leaguesPanelLocalVariableValue.setBackground(LIGHT_BLUE);

        int indexCounterLocalVariableValue = 0;
        for (League leagueReferenceLocalVariableValue : leaguesParameterValue) {
            JButton buttonControlLocalVariableValue = new JButton(leagueReferenceLocalVariableValue.getName());
            buttonControlLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 14));
            buttonControlLocalVariableValue.setForeground(DARK_BLUE);
            buttonControlLocalVariableValue.setBackground(Color.WHITE);
            buttonControlLocalVariableValue.setFocusPainted(false);
            buttonControlLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(DARK_BLUE, 1),
                    BorderFactory.createEmptyBorder(5, 12, 5, 12)
            ));

            buttonControlLocalVariableValue.setActionCommand("LEAGUE_" + indexCounterLocalVariableValue);
            buttonControlLocalVariableValue.addActionListener(listenerParameterValue);

            if (indexCounterLocalVariableValue > 0) leaguesPanelLocalVariableValue.add(Box.createRigidArea(new Dimension(8, 0)));
            leaguesPanelLocalVariableValue.add(buttonControlLocalVariableValue);
            indexCounterLocalVariableValue++;
        }

        JScrollPane scrollLocalVariableValue = new JScrollPane(leaguesPanelLocalVariableValue);
        scrollLocalVariableValue.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollLocalVariableValue.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        scrollLocalVariableValue.getViewport().setBackground(LIGHT_BLUE);

        containerLocalVariableValue.add(scrollLocalVariableValue, BorderLayout.CENTER);
        containerLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        containerLocalVariableValue.setPreferredSize(new Dimension(800, 100));

        return containerLocalVariableValue;
    }
    /**
     * Crea el contenedor del gráfico y su encabezado.
     * @return Panel con el gráfico y encabezado.
     */
    private JPanel createChartPanel() {
        JPanel containerLocalVariableValue2 = new JPanel(new BorderLayout());
        containerLocalVariableValue2.setBackground(BACKGROUND);

        JPanel headerLocalVariableValue = new JPanel(new BorderLayout());
        headerLocalVariableValue.setBackground(DARK_BLUE);
        headerLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        leagueReferenceTitleLabelFieldReference = new JLabel("STATISTICS - LEAGUE 1", SwingConstants.LEFT);
        leagueReferenceTitleLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 20));
        leagueReferenceTitleLabelFieldReference.setForeground(Color.WHITE);
        headerLocalVariableValue.add(leagueReferenceTitleLabelFieldReference, BorderLayout.CENTER);

        chartPanelFieldReference = new LeagueManagerChartPanelComponent();
        chartPanelFieldReference.setBackground(Color.WHITE);

        containerLocalVariableValue2.add(headerLocalVariableValue, BorderLayout.NORTH);
        containerLocalVariableValue2.add(chartPanelFieldReference, BorderLayout.CENTER);

        return containerLocalVariableValue2;
    }
    /**
     * Actualiza los datos del gráfico con información real.
     * @param leagueName Nombre de la liga.
     * @param teamData Puntos de los equipos.
     * @param numWeeks Número de semanas.
     * @param team_id IDs de los equipos.
     * @param countIds Cantidad de equipos.
     */
    public void updateChartData(String leagueReferenceDisplayNameParameterValue, int[][] teamReferenceDataParameterValue, int numberWeeksParameterValue, String[] teamReferenceIdentifierParameterValue, int countIdentifiersParameterValue) {
        leagueReferenceTitleLabelFieldReference.setText("STATISTICS - " + leagueReferenceDisplayNameParameterValue.toUpperCase());
        chartPanelFieldReference.updateData(teamReferenceDataParameterValue, numberWeeksParameterValue);
        StatisticsGraphView.countIdentifiersFieldReference = countIdentifiersParameterValue;
        teamReferenceNamesFieldReference = new String[countIdentifiersParameterValue];

        for (int indexCounterLocalVariableValue2 = 0; indexCounterLocalVariableValue2 < countIdentifiersParameterValue; indexCounterLocalVariableValue2++) {
            teamReferenceNamesFieldReference[indexCounterLocalVariableValue2] =teamReferenceIdentifierParameterValue[indexCounterLocalVariableValue2];
        }
    }
    /**
     * Registra un controlador común para los botones de navegación.
     * @param controller El ActionListener que manejará los eventos.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.setActionCommand("BACK");

//        configButton.addActionListener(controller);
//        configButton.setActionCommand("CONFIG");
    }
    /**
     * Establece el listado de ligas en la vista y les asigna un ActionListener.
     * @param leagues Lista de objetos League.
     * @param listener Escuchador para manejar los clics de selección.
     */
    public void setLeagues(List<League> leaguesParameterValue2, ActionListener listenerParameterValue2) {
        JPanel leaguesPanelLocalVariableValue2 = createLeaguesPanel(leaguesParameterValue2, listenerParameterValue2);

        if (topPanelFieldReference.getComponentCount() > 1) {
            topPanelFieldReference.remove(1); // elimina panel antiguo de ligas si existe
        }
        topPanelFieldReference.add(leaguesPanelLocalVariableValue2);
        topPanelFieldReference.revalidate();
        topPanelFieldReference.repaint();
    }
    /**
     * Panel interno encargado de renderizar el gráfico de evolución de puntos de equipos.
     */
    class LeagueManagerChartPanelComponent extends JPanel {
        private int[][] teamReferencePointsFieldReference;
        private int currentWeekFieldReference;
        private final Color[] teamReferenceColorsFieldReference = {
                new Color(0, 0, 255),
                new Color(255, 0, 0),
                new Color(0, 255, 0),
                new Color(128, 0, 128),
                new Color(100, 34, 34),
                new Color(0, 55, 12),
                new Color(180, 0, 18),
                new Color(255, 165, 0),     // naranja
                new Color(0, 255, 255),     // cian
                new Color(255, 192, 203),   // rosa claro
                new Color(75, 0, 130),      // índigo
                new Color(46, 139, 87),     // verde mar
                new Color(255, 215, 0),     // dorado
                new Color(220, 20, 60),     // carmesí
                new Color(0, 100, 0),       // verde oscuro
                new Color(70, 130, 180),    // azul acero
                new Color(240, 128, 128),   // rojo claro
                new Color(105, 105, 105),   // gris oscuro
                new Color(255, 69, 0),      // rojo anaranjado
                new Color(25, 25, 112)
        };
        /**
         * Actualiza los datos a mostrar en el gráfico.
         * @param teamPoints Matriz con los puntos de cada equipo por semana.
         * @param currentWeek Semana actual hasta la cual graficar.
         */
        public void updateData(int[][] teamReferencePointsParameterValue, int currentWeekParameterValue) {
            this.teamReferencePointsFieldReference = teamReferencePointsParameterValue;
            this.currentWeekFieldReference = currentWeekParameterValue;
            repaint();
        }
        /**
         * Dibuja el gráfico en el panel usando los datos actuales.
         * @param g Objeto Graphics para renderizar los elementos.
         */
        @Override
        protected void paintComponent(Graphics gParameterValue) {
            super.paintComponent(gParameterValue);
            if (teamReferencePointsFieldReference == null || currentWeekFieldReference < 1) return;

            int widthLocalVariableValue = getWidth();
            int heightLocalVariableValue = getHeight();
            int paddingLocalVariableValue = 60;
            int legendHeightLocalVariableValue = 40;

            gParameterValue.setColor(Color.WHITE);
            gParameterValue.fillRect(0, 0, widthLocalVariableValue, heightLocalVariableValue);

            int chartWidthLocalVariableValue = widthLocalVariableValue - 2 * paddingLocalVariableValue;
            int chartHeightLocalVariableValue = heightLocalVariableValue - 2 * paddingLocalVariableValue - legendHeightLocalVariableValue;
            int maxYLocalVariableValue = Arrays.stream(teamReferencePointsFieldReference).flatMapToInt(Arrays::stream).limit(currentWeekFieldReference * teamReferencePointsFieldReference.length).max().orElse(1);

            double xScaleLocalVariableValue = (currentWeekFieldReference > 1) ? (double) chartWidthLocalVariableValue / (currentWeekFieldReference - 1) : 0;
            double yScaleLocalVariableValue = (double) chartHeightLocalVariableValue / maxYLocalVariableValue;

            gParameterValue.setColor(Color.BLACK);
            gParameterValue.drawLine(paddingLocalVariableValue, heightLocalVariableValue - paddingLocalVariableValue - legendHeightLocalVariableValue, paddingLocalVariableValue, paddingLocalVariableValue);
            gParameterValue.drawLine(paddingLocalVariableValue, heightLocalVariableValue - paddingLocalVariableValue - legendHeightLocalVariableValue, widthLocalVariableValue - paddingLocalVariableValue, heightLocalVariableValue - paddingLocalVariableValue - legendHeightLocalVariableValue);

            gParameterValue.setFont(new Font("Arial", Font.PLAIN, 12));
            for (int weekLocalVariableValue2 = 0; weekLocalVariableValue2 < currentWeekFieldReference; weekLocalVariableValue2++) {
                int xLocalVariableValue = paddingLocalVariableValue + (int)(weekLocalVariableValue2 * xScaleLocalVariableValue);
                gParameterValue.drawString("Week " + (weekLocalVariableValue2 + 1), xLocalVariableValue - 15, heightLocalVariableValue - paddingLocalVariableValue - legendHeightLocalVariableValue + 20);
            }

            for (int indexCounterLocalVariableValue3 = 0; indexCounterLocalVariableValue3 <= maxYLocalVariableValue; indexCounterLocalVariableValue3 += Math.max(1, maxYLocalVariableValue / 5)) {
                int yLocalVariableValue = heightLocalVariableValue - paddingLocalVariableValue - legendHeightLocalVariableValue - (int)(indexCounterLocalVariableValue3 * yScaleLocalVariableValue);
                gParameterValue.drawString(String.valueOf(indexCounterLocalVariableValue3), paddingLocalVariableValue - 30, yLocalVariableValue + 5);
            }

            for (int teamReferenceLocalVariableValue2 = 0; teamReferenceLocalVariableValue2 < countIdentifiersFieldReference; teamReferenceLocalVariableValue2++) {
                gParameterValue.setColor(teamReferenceColorsFieldReference[teamReferenceLocalVariableValue2]);
                List<Point> pointsLocalVariableValue2 = new ArrayList<>();

                for (int weekLocalVariableValue3 = 0; weekLocalVariableValue3 < currentWeekFieldReference; weekLocalVariableValue3++) {
                    int xLocalVariableValue2 = paddingLocalVariableValue + (int)(weekLocalVariableValue3 * xScaleLocalVariableValue);
                    int yLocalVariableValue2 = heightLocalVariableValue - paddingLocalVariableValue - legendHeightLocalVariableValue - (int)(teamReferencePointsFieldReference[teamReferenceLocalVariableValue2][weekLocalVariableValue3] * yScaleLocalVariableValue);
                    pointsLocalVariableValue2.add(new Point(xLocalVariableValue2, yLocalVariableValue2));
                }

                for (int indexCounterLocalVariableValue4 = 1; indexCounterLocalVariableValue4 < pointsLocalVariableValue2.size(); indexCounterLocalVariableValue4++) {
                    Point prevLocalVariableValue = pointsLocalVariableValue2.get(indexCounterLocalVariableValue4 - 1);
                    Point currentLocalVariableValue = pointsLocalVariableValue2.get(indexCounterLocalVariableValue4);
                    gParameterValue.drawLine(prevLocalVariableValue.x, prevLocalVariableValue.y, currentLocalVariableValue.x, currentLocalVariableValue.y);
                }

                for (Point itemValueLocalVariableValue : pointsLocalVariableValue2) {
                    gParameterValue.fillOval(itemValueLocalVariableValue.x - 3, itemValueLocalVariableValue.y - 3, 6, 6);
                }
            }

            int legendItemWidthLocalVariableValue = 120;
            int totalLegendWidthLocalVariableValue = legendItemWidthLocalVariableValue * 4;
            int legendXLocalVariableValue = (widthLocalVariableValue - totalLegendWidthLocalVariableValue) / 2;
            int legendYLocalVariableValue = heightLocalVariableValue - paddingLocalVariableValue - legendHeightLocalVariableValue + 50;

            for (int indexCounterLocalVariableValue5 = 0; indexCounterLocalVariableValue5 < countIdentifiersFieldReference; indexCounterLocalVariableValue5++) {
                gParameterValue.setColor(teamReferenceColorsFieldReference[indexCounterLocalVariableValue5]);
                gParameterValue.fillRect(legendXLocalVariableValue, legendYLocalVariableValue, 20, 15);
                gParameterValue.setColor(Color.BLACK);
                gParameterValue.drawString(teamReferenceNamesFieldReference[indexCounterLocalVariableValue5], legendXLocalVariableValue + 25, legendYLocalVariableValue + 12);
                legendXLocalVariableValue += legendItemWidthLocalVariableValue;

            }
        }
    }
}
