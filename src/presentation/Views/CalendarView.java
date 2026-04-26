package presentation.Views;

import bussines.objects.Game;
import presentation.ControllerViews.CalendarController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Vista que muestra el calendario de partidos de una liga.
 * Permite navegar por rondas y visualizar los partidos correspondientes a cada ronda.
 */
public class CalendarView extends JFrame {
    private ArrayList<String> teamsFieldReference;
    private ArrayList<Game> gamesFieldReference;

    private static final Color DARK_BLUE = new Color(22, 49, 72);
    private static final Color LIGHT_BLUE = new Color(195, 216, 236);
    private static final Color BACKGROUND = new Color(240, 240, 240);
    private static final Color DIVIDER_COLOR = new Color(150, 150, 150);

    private static JButton backButtonFieldReference;
    private static JButton configButtonFieldReference;

    /** Constante para identificar la acción de volver atrás. */
    public static final String BACK = "BACK";
    /** Constante para identificar la acción de configuración. */
    public static final String CONFIG = "CONFIG";

    private JPanel matchesPanelFieldReference;
    private JLabel roundTitleLabelFieldReference;
    private CalendarController controllerHandlerFieldReference;
    private JFrame previousViewInterfaceFieldReference = null;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Constructor que inicializa la ventana del calendario con la lista de equipos y partidos.
     * Configura el layout general, paneles y muestra la primera ronda por defecto.
     *
     * @param teams Lista de nombres de los equipos de la liga.
     * @param games Lista de objetos Game que representan los partidos.
     */
    public CalendarView(ArrayList<String> teamsParameterValue, ArrayList<Game> gamesParameterValue) {
        this.teamsFieldReference = teamsParameterValue;
        this.gamesFieldReference = gamesParameterValue;

        setTitle("Calendario de Liga");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanelLocalVariableValue = new JPanel(new BorderLayout(0, 10));
        mainPanelLocalVariableValue.setBackground(BACKGROUND);
        mainPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // 1. Panel del título
        mainPanelLocalVariableValue.add(createTitlePanel(), BorderLayout.NORTH);

        // 2. Panel de rondas compacto
        mainPanelLocalVariableValue.add(createCompactRoundsPanel(), BorderLayout.CENTER);

        // 3. Panel de partidos (más prominente)
        JPanel matchesContainerLocalVariableValue = new JPanel(new BorderLayout());
        matchesContainerLocalVariableValue.setBackground(BACKGROUND);

        // Cabecera de partidos
        JPanel matchesHeaderLocalVariableValue = new JPanel(new BorderLayout());
        matchesHeaderLocalVariableValue.setBackground(DARK_BLUE);
        matchesHeaderLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        roundTitleLabelFieldReference = new JLabel("MATCHES - ROUND 1", SwingConstants.LEFT);
        roundTitleLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 20));
        roundTitleLabelFieldReference.setForeground(Color.WHITE);
        matchesHeaderLocalVariableValue.add(roundTitleLabelFieldReference, BorderLayout.CENTER);

        matchesContainerLocalVariableValue.add(matchesHeaderLocalVariableValue, BorderLayout.NORTH);

        // Panel de partidos con scroll
        matchesPanelFieldReference = new JPanel();
        matchesPanelFieldReference.setLayout(new BoxLayout(matchesPanelFieldReference, BoxLayout.Y_AXIS));
        matchesPanelFieldReference.setBackground(BACKGROUND);
        matchesPanelFieldReference.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JScrollPane matchesScrollLocalVariableValue = new JScrollPane(matchesPanelFieldReference);
        matchesScrollLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        matchesScrollLocalVariableValue.getViewport().setBackground(BACKGROUND);

        matchesContainerLocalVariableValue.add(matchesScrollLocalVariableValue, BorderLayout.CENTER);

        mainPanelLocalVariableValue.add(matchesContainerLocalVariableValue, BorderLayout.SOUTH);

        add(mainPanelLocalVariableValue);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent eventArgumentParameterValue) {
                if (previousViewInterfaceFieldReference != null) {
                    previousViewInterfaceFieldReference.setVisible(true);
                }
            }
        });


        // Mostrar partidos de la primera ronda por defecto
        showMatchesForRound(1);
    }

    /**
     * Crea el panel de título con el nombre de la aplicación.
     *
     * @return JPanel con el título centrado.
     */
    private JPanel createTitlePanel() {
        JPanel panelLocalVariableValue = new JPanel(new BorderLayout());
        panelLocalVariableValue.setBackground(DARK_BLUE);
        panelLocalVariableValue.setPreferredSize(new Dimension(getWidth(), 70));

        backButtonFieldReference = Rounded.HeaderButtonHelper.createBackButton(null);
        configButtonFieldReference = Rounded.HeaderButtonHelper.createConfigButton(null);

        JLabel titleLocalVariableValue = new JLabel("LEAGUE CALENDAR", SwingConstants.CENTER);
        titleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 28));
        titleLocalVariableValue.setForeground(Color.WHITE);
        titleLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        panelLocalVariableValue.add(backButtonFieldReference, BorderLayout.WEST);
        panelLocalVariableValue.add(titleLocalVariableValue, BorderLayout.CENTER);
        panelLocalVariableValue.add(configButtonFieldReference, BorderLayout.EAST);
        return panelLocalVariableValue;
    }

    /**
     * Crea un panel compacto con botones para cada ronda disponible.
     * Permite navegar entre rondas haciendo clic en cada botón.
     *
     * @return JPanel con scroll horizontal para navegar rondas.
     */
    private JPanel createCompactRoundsPanel() {
        JPanel containerLocalVariableValue = new JPanel(new BorderLayout());
        containerLocalVariableValue.setBackground(LIGHT_BLUE);
        containerLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        containerLocalVariableValue.setPreferredSize(new Dimension(getWidth(), 60));

        JPanel roundsPanelLocalVariableValue = new JPanel();
        roundsPanelLocalVariableValue.setLayout(new BoxLayout(roundsPanelLocalVariableValue, BoxLayout.X_AXIS));
        roundsPanelLocalVariableValue.setBackground(LIGHT_BLUE);

        int totalRoundsLocalVariableValue = 2 * (teamsFieldReference.size() - 1);
        for (int indexCounterLocalVariableValue = 1; indexCounterLocalVariableValue <= totalRoundsLocalVariableValue; indexCounterLocalVariableValue++) {
            JButton roundButtonLocalVariableValue = new JButton("ROUND " + indexCounterLocalVariableValue);
            roundButtonLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 14));
            roundButtonLocalVariableValue.setForeground(DARK_BLUE);
            roundButtonLocalVariableValue.setBackground(Color.WHITE);
            roundButtonLocalVariableValue.setFocusPainted(false);
            roundButtonLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(DARK_BLUE, 1),
                    BorderFactory.createEmptyBorder(5, 12, 5, 12)
            ));

            if (indexCounterLocalVariableValue > 1) {
                roundsPanelLocalVariableValue.add(Box.createRigidArea(new Dimension(8, 0)));
            }

            final int roundNumericValueLocalVariableValue = indexCounterLocalVariableValue;
            roundButtonLocalVariableValue.addActionListener(eventArgumentParameterValue2 -> showMatchesForRound(roundNumericValueLocalVariableValue));

            roundsPanelLocalVariableValue.add(roundButtonLocalVariableValue);
        }

        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(roundsPanelLocalVariableValue);
        scrollPaneLocalVariableValue.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPaneLocalVariableValue.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneLocalVariableValue.getViewport().setBackground(LIGHT_BLUE);

        containerLocalVariableValue.add(scrollPaneLocalVariableValue, BorderLayout.CENTER);
        return containerLocalVariableValue;
    }

    /**
     * Muestra los partidos correspondientes a la ronda seleccionada.
     * Limpia el panel de partidos y añade paneles individuales por partido.
     *
     * @param roundNumber Número de la ronda a mostrar.
     */
    private void showMatchesForRound(int roundNumericValueParameterValue) {
        matchesPanelFieldReference.removeAll();
        roundTitleLabelFieldReference.setText("MATCHES - ROUND " + roundNumericValueParameterValue);

        String[][] roundMatchesLocalVariableValue = generateMatchesForRound(roundNumericValueParameterValue);

        for (String[] matchInformationLocalVariableValue : roundMatchesLocalVariableValue) {
            JPanel matchPanelLocalVariableValue = new JPanel(new BorderLayout());
            matchPanelLocalVariableValue.setBackground(Color.WHITE);
            matchPanelLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, DIVIDER_COLOR),
                    BorderFactory.createEmptyBorder(12, 15, 12, 15)
            ));
            matchPanelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

            JLabel teamsLabelLocalVariableValue = new JLabel(matchInformationLocalVariableValue[0]);
            teamsLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 16));
            teamsLabelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

            JLabel detailsLabelLocalVariableValue = new JLabel(matchInformationLocalVariableValue[1]);
            detailsLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 14));
            detailsLabelLocalVariableValue.setForeground(new Color(100, 100, 100));

            JPanel leftPanelLocalVariableValue = new JPanel(new BorderLayout());
            leftPanelLocalVariableValue.setBackground(Color.WHITE);
            leftPanelLocalVariableValue.add(teamsLabelLocalVariableValue, BorderLayout.NORTH);
            leftPanelLocalVariableValue.add(detailsLabelLocalVariableValue, BorderLayout.SOUTH);

            matchPanelLocalVariableValue.add(leftPanelLocalVariableValue, BorderLayout.CENTER);
            matchesPanelFieldReference.add(matchPanelLocalVariableValue);
        }

        matchesPanelFieldReference.revalidate();
        matchesPanelFieldReference.repaint();
    }


    /**
     * Genera una matriz de información de partidos para una ronda dada,
     * extrayendo los partidos correspondientes de la lista de juegos.
     *
     * @param roundNumber Número de la ronda.
     * @return Matriz de cadenas donde cada fila contiene descripción del partido y detalles.
     */
    private String[][] generateMatchesForRound(int roundNumericValueParameterValue2) {
        List<String[]> roundMatchesLocalVariableValue2 = new ArrayList<>();

        for (Game gameEntityLocalVariableValue : gamesFieldReference) {
            if (gameEntityLocalVariableValue.getJornada() == roundNumericValueParameterValue2) {  // Aquí usas getJornada()
                roundMatchesLocalVariableValue2.add(createMatch(gameEntityLocalVariableValue));
            }
        }

        return roundMatchesLocalVariableValue2.toArray(new String[0][]);
    }

    /**
     * Crea un arreglo de información textual para un partido,
     * incluyendo equipos y fecha/hora del partido.
     *
     * @param game Partido para crear la descripción.
     * @return Arreglo con el texto para mostrar en la vista.
     */
    private String[] createMatch(Game gameEntityParameterValue) {
        String homeTeamReferenceLocalVariableValue = gameEntityParameterValue.getNomLocal();
        String awayTeamReferenceLocalVariableValue = gameEntityParameterValue.getNomVisitant();
        String stadiumLocalVariableValue = ""; // Si tienes campo para estadio en Game, úsalo; si no, deja vacío o fija uno.
        String dateTimeLocalVariableValue = gameEntityParameterValue.getData().format(DATE_TIME_FORMATTER);

        return new String[] {
                homeTeamReferenceLocalVariableValue + " vs " + awayTeamReferenceLocalVariableValue,
                stadiumLocalVariableValue + (stadiumLocalVariableValue.isEmpty() ? "" : " • ") + dateTimeLocalVariableValue
        };
    }

    /**
     * Registra el controlador de eventos para los botones interactivos de la vista.
     *
     * @param controller Acción a ejecutar cuando se presionan los botones.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.setActionCommand(BACK);
    }

}