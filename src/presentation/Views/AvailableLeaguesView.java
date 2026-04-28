package presentation.Views;

import presentation.ControllerViews.AvailableLeaguesController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import bussines.objects.League;
import bussines.objects.LeagueListEntry;
import presentation.ControllerViews.MenuController;
import shared.ProjectPathResolver;

/**
 * Vista que muestra las ligas de fútbol disponibles.
 * Permite a los usuarios (y administradores) ver una lista de ligas y acceder a sus detalles.
 * Incorpora botones de navegación y configuración en el encabezado.
 */
public class AvailableLeaguesView extends JFrame {

    // Colores personalizados para mantener consistencia visual
    private static final Color DARK_BLUE = new Color(22, 49, 72);    // #163148
    private static final Color LIGHT_BLUE = new Color(195, 216, 236); // #C3D8EC
    private static final Color BACKGROUND = new Color(240, 240, 240);
    private static final Color DIVIDER_COLOR = Color.BLACK;

    private JButton configButtonFieldReference;
    private ActionListener configControllerHandlerFieldReference;
    private JPanel leaguesListPanelFieldReference;
    private JScrollPane leaguesScrollPaneFieldReference;
    private ActionListener backControllerHandlerFieldReference;
    private MenuController menuControllerHandlerFieldReference;

    /**
     * Establece el controlador que maneja la acción del botón de configuración.
     *
     * @param configController El {@link ActionListener} que maneja la acción.
     */
    public void setConfigController(ActionListener configControllerHandlerParameterValue) {
        this.configControllerHandlerFieldReference = configControllerHandlerParameterValue;
    }


    /**
     * Constructor que inicializa la interfaz gráfica de la pantalla de ligas disponibles.
     */
    public AvailableLeaguesView() {
        setTitle("Gestión de Ligas de Fútbol");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanelLocalVariableValue = new JPanel(new BorderLayout());
        mainPanelLocalVariableValue.setBackground(BACKGROUND);


        // Añadir componentes al panel principal
        mainPanelLocalVariableValue.add(createTitlePanel(), BorderLayout.NORTH);
        mainPanelLocalVariableValue.add(createLeaguesScrollPane(), BorderLayout.CENTER);

        add(mainPanelLocalVariableValue);
    }

    public void setBackButtonListener(ActionListener listenerParameterValue) {
        this.backControllerHandlerFieldReference = listenerParameterValue;
    }

    // nuevo campo

    public void setMenuController(MenuController controllerHandlerParameterValue) {
        this.menuControllerHandlerFieldReference = controllerHandlerParameterValue;
    }

    /**
     * Crea el panel del título que contiene el nombre, el ícono y los botones de navegación/configuración.
     *
     * @return Panel del encabezado configurado.
     */
    private JPanel createTitlePanel() {
        JPanel panelLocalVariableValue = new JPanel(new BorderLayout());
        panelLocalVariableValue.setBackground(DARK_BLUE);
        panelLocalVariableValue.setPreferredSize(new Dimension(getWidth(), 60));

        // Botón de volver atrás
        JButton backButtonLocalVariableValue = Rounded.HeaderButtonHelper.createBackButton(eventArgumentParameterValue -> {
            if (backControllerHandlerFieldReference != null) {
                backControllerHandlerFieldReference.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "BACK"));
            } else {
                dispose();
                menuControllerHandlerFieldReference.showMenu();// comportamiento por defecto si no hay listener
            }
        });
        panelLocalVariableValue.add(backButtonLocalVariableValue, BorderLayout.WEST);

        // Íconos de pelota de fútbol
        ImageIcon ballIconLocalVariableValue =
                new ImageIcon(ProjectPathResolver.resolveProjectPath("photos/football.png"));
        Image scaledLocalVariableValue = ballIconLocalVariableValue.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledBallIconLocalVariableValue = new ImageIcon(scaledLocalVariableValue);

        JLabel leftBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);
        JLabel rightBallLocalVariableValue = new JLabel(scaledBallIconLocalVariableValue);

        JLabel titleLocalVariableValue = new JLabel("AVAILABLE LEAGUES");
        titleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 20));
        titleLocalVariableValue.setForeground(Color.WHITE);

        JPanel centerPanelLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanelLocalVariableValue.setOpaque(false);
        centerPanelLocalVariableValue.add(leftBallLocalVariableValue);
        centerPanelLocalVariableValue.add(titleLocalVariableValue);
        centerPanelLocalVariableValue.add(rightBallLocalVariableValue);

        panelLocalVariableValue.add(centerPanelLocalVariableValue, BorderLayout.CENTER);

        configButtonFieldReference = Rounded.HeaderButtonHelper.createConfigButton(eventArgumentParameterValue2 -> { //inhabilitamos
//            if (configController != null) {
//                ((AvailableLeaguesController) configController).showConfigDialog();
//            }
        });
        panelLocalVariableValue.add(configButtonFieldReference, BorderLayout.EAST);

        return panelLocalVariableValue;
    }

    /**
     * Crea el panel principal scrollable que contiene la lista de ligas.
     *
     * @return Un {@link JScrollPane} que envuelve la lista de ligas.
     */
    private JScrollPane createLeaguesScrollPane() {
        JPanel containerLocalVariableValue = new JPanel(new BorderLayout());
        containerLocalVariableValue.setBackground(BACKGROUND);

        JPanel leaguesPanelLocalVariableValue = new JPanel();
        leaguesPanelLocalVariableValue.setLayout(new BoxLayout(leaguesPanelLocalVariableValue, BoxLayout.Y_AXIS));
        leaguesPanelLocalVariableValue.setBackground(LIGHT_BLUE);
        leaguesPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        JPanel namesHeaderLocalVariableValue = new JPanel(new GridLayout(1, 3));
        namesHeaderLocalVariableValue.setBackground(DARK_BLUE);
        namesHeaderLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        namesHeaderLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel namesLabelLocalVariableValue = new JLabel("LEAGUE", SwingConstants.CENTER);
        namesLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 16));
        namesLabelLocalVariableValue.setForeground(Color.WHITE);

        JLabel teamsLabelLocalVariableValue = new JLabel("TEAMS", SwingConstants.CENTER);
        teamsLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 16));
        teamsLabelLocalVariableValue.setForeground(Color.WHITE);

        JLabel statusLabelHeaderLocalVariableValue = new JLabel("STATUS", SwingConstants.CENTER);
        statusLabelHeaderLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabelHeaderLocalVariableValue.setForeground(Color.WHITE);

        namesHeaderLocalVariableValue.add(namesLabelLocalVariableValue);
        namesHeaderLocalVariableValue.add(teamsLabelLocalVariableValue);
        namesHeaderLocalVariableValue.add(statusLabelHeaderLocalVariableValue);

        leaguesPanelLocalVariableValue.add(namesHeaderLocalVariableValue);
        leaguesPanelLocalVariableValue.add(Box.createRigidArea(new Dimension(0, 5)));

        leaguesListPanelFieldReference = new JPanel();
        leaguesListPanelFieldReference.setLayout(new BoxLayout(leaguesListPanelFieldReference, BoxLayout.Y_AXIS));
        leaguesListPanelFieldReference.setBackground(LIGHT_BLUE);
        leaguesPanelLocalVariableValue.add(leaguesListPanelFieldReference);

        JPanel blueContainerLocalVariableValue = new JPanel(new BorderLayout());
        blueContainerLocalVariableValue.setBackground(LIGHT_BLUE);
        blueContainerLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        blueContainerLocalVariableValue.add(leaguesPanelLocalVariableValue, BorderLayout.CENTER);

        containerLocalVariableValue.add(blueContainerLocalVariableValue, BorderLayout.CENTER);

        leaguesScrollPaneFieldReference = new JScrollPane(containerLocalVariableValue);
        leaguesScrollPaneFieldReference.setBorder(BorderFactory.createEmptyBorder());
        leaguesScrollPaneFieldReference.getViewport().setBackground(BACKGROUND);


        return leaguesScrollPaneFieldReference;
    }

    /**
     * Muestra dinámicamente la lista de ligas con tres columnas:
     * nombre, número de equipos y estado actual (apartado 2.7 del
     * enunciado).
     *
     * @param entriesParameterValue lista de entradas a mostrar.
     * @param isAdminParameterValue si es true se puede acceder a
     *        funcionalidades extra de administrador.
     * @param listenerParameterValue2 controlador que abre el detalle de
     *        una liga al hacer clic.
     */
    public void displayLeagues(List<LeagueListEntry> entriesParameterValue,
                               boolean isAdminParameterValue,
                               ActionListener listenerParameterValue2) {
        leaguesListPanelFieldReference.removeAll();

        if (entriesParameterValue == null || entriesParameterValue.isEmpty()) {
            JLabel noLeaguesLabelLocalVariableValue = new JLabel("No leagues available.", SwingConstants.CENTER);
            noLeaguesLabelLocalVariableValue.setFont(new Font("Arial", Font.ITALIC, 16));
            noLeaguesLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
            noLeaguesLabelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            leaguesListPanelFieldReference.add(noLeaguesLabelLocalVariableValue);
        } else {
            for (int indexCounterLocalVariableValue = 0;
                 indexCounterLocalVariableValue < entriesParameterValue.size();
                 indexCounterLocalVariableValue++) {

                LeagueListEntry entryLocalVariableValue =
                        entriesParameterValue.get(indexCounterLocalVariableValue);
                League leagueReferenceLocalVariableValue = entryLocalVariableValue.getLeague();

                JPanel leagueReferencePanelLocalVariableValue = new JPanel(new GridLayout(1, 3));
                leagueReferencePanelLocalVariableValue.setBackground(Color.WHITE);
                leagueReferencePanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
                leagueReferencePanelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
                leagueReferencePanelLocalVariableValue.setMinimumSize(new Dimension(100, 45));
                leagueReferencePanelLocalVariableValue.setPreferredSize(new Dimension(300, 45));
                leagueReferencePanelLocalVariableValue.setCursor(new Cursor(Cursor.HAND_CURSOR));
                leagueReferencePanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel leagueNameLabelLocalVariableValue =
                        new JLabel(leagueReferenceLocalVariableValue.getName(), SwingConstants.CENTER);
                leagueNameLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel teamCountLabelLocalVariableValue = new JLabel(
                        String.valueOf(entryLocalVariableValue.getTeamCount()),
                        SwingConstants.CENTER
                );
                teamCountLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 15));

                JLabel statusLabelLocalVariableValue = new JLabel(
                        entryLocalVariableValue.getStatusLabel(),
                        SwingConstants.CENTER
                );
                statusLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 15));

                leagueReferencePanelLocalVariableValue.add(leagueNameLabelLocalVariableValue);
                leagueReferencePanelLocalVariableValue.add(teamCountLabelLocalVariableValue);
                leagueReferencePanelLocalVariableValue.add(statusLabelLocalVariableValue);

                leagueReferencePanelLocalVariableValue.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent eventArgumentParameterValue3) {
                        if (listenerParameterValue2 instanceof AvailableLeaguesController) {
                            ((AvailableLeaguesController) listenerParameterValue2).openLeagueDetails(leagueReferenceLocalVariableValue);
                        }
                    }
                });
                leaguesListPanelFieldReference.add(leagueReferencePanelLocalVariableValue);

                if (indexCounterLocalVariableValue < entriesParameterValue.size() - 1) {
                    JSeparator separatorLocalVariableValue = new JSeparator();
                    separatorLocalVariableValue.setForeground(DIVIDER_COLOR);
                    separatorLocalVariableValue.setPreferredSize(new Dimension(leaguesListPanelFieldReference.getWidth() - 40, 2));
                    separatorLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
                    separatorLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
                    leaguesListPanelFieldReference.add(Box.createRigidArea(new Dimension(0, 1)));
                    leaguesListPanelFieldReference.add(separatorLocalVariableValue);
                    leaguesListPanelFieldReference.add(Box.createRigidArea(new Dimension(0, 1)));
                }
            }
        }
        leaguesListPanelFieldReference.revalidate();
        leaguesListPanelFieldReference.repaint();
    }

    /**
     * Permite mostrar u ocultar funciones exclusivas para administradores.
     * Actualmente sin implementación.
     *
     * @param visible true para mostrar, false para ocultar.
     */
    public void setAdminFunctionsVisible(boolean visibleParameterValue) {
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de advertencia sobre ligas disponibles.
     *
     * @param message Texto que se mostrará en el cuadro de diálogo.
     */
    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(this, messageParameterValue, "Avaliable leagues", JOptionPane.WARNING_MESSAGE);
    }

}
