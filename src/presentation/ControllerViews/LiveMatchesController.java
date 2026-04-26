package presentation.ControllerViews;

import bussines.managers.GameManager;
import bussines.managers.LeagueManager;
import bussines.managers.PlayerManager;
import presentation.Views.LiveMatchView;
import presentation.Views.LiveMatchesView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la pantalla de partidos en vivo.
 * Gestiona la interacción entre la vista LiveMatchesScreen y los modelos PlayerManager, GameManager y LeagueManager.
 */
public class LiveMatchesController implements ActionListener {

    /** Vista principal que muestra la lista de partidos en vivo */
    private final LiveMatchesView viewInterfaceFieldReference;

    /** Controlador del menú de jugador */
    private final PlayerMenuController playerProfileMenuControllerHandlerFieldReference;

    /** Gestor de jugadores */
    private final PlayerManager playerProfileManagerServiceFieldReference;

    /** Controlador del menú de administrador */
    private AdminMenuController adminMenuControllerHandlerFieldReference;

    /** Gestor de juegos */
    private GameManager gameEntityManagerServiceFieldReference;

    /** Gestor de ligas */
    private LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();

    /** Controladores individuales para cada partido en vivo */
    private final Map<Integer, LiveMatchController> liveMatchControllersFieldReference = new HashMap<>();

    /**
     * Constructor del controlador LiveMatchesController.
     * Inicializa la vista, los controladores y gestores necesarios y configura la vista y los listeners.
     *
     * @param view Vista principal de partidos en vivo
     * @param menuController Controlador del menú de jugador
     * @param playerManager Gestor de jugadores
     * @param adminMenuController Controlador del menú de administrador
     */
    public LiveMatchesController(LiveMatchesView viewInterfaceParameterValue, PlayerMenuController menuControllerHandlerParameterValue, PlayerManager playerProfileManagerServiceParameterValue, AdminMenuController adminMenuControllerHandlerParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.playerProfileMenuControllerHandlerFieldReference = menuControllerHandlerParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.adminMenuControllerHandlerFieldReference = adminMenuControllerHandlerParameterValue;
        this.gameEntityManagerServiceFieldReference = new GameManager();


//        view.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                returnToMenu();
//            }
//
//            @Override
//            public void windowClosed(WindowEvent e) {
//                returnToMenu();
//            }
//        });


        setupView();
        setupListeners();
    }



    /**
     * Configura la vista cargando los partidos iniciales.
     */
    private void setupView() {
        refreshMatches();
    }


    /**
     * Configura los listeners para la vista.
     * Incluye la gestión del cierre de ventana, configuración y selección de partidos.
     */
    private void setupListeners() {
        viewInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        viewInterfaceFieldReference.setBackButtonListener(eventArgumentParameterValue -> {
            viewInterfaceFieldReference.dispose();
            if (playerProfileManagerServiceFieldReference.getCurrentIdentifier().equals("admin")) {
                adminMenuControllerHandlerFieldReference.showMenu();
            } else {
                playerProfileMenuControllerHandlerFieldReference.showMenu();
            }
        });

        viewInterfaceFieldReference.setConfigController(eventArgumentParameterValue2 -> {

            if (LiveMatchesView.CONFIG.equals(eventArgumentParameterValue2.getActionCommand())) {
                if (playerProfileManagerServiceFieldReference.getCurrentIdentifier().equals("admin")) {
                    adminMenuControllerHandlerFieldReference.showConfigDialog();
                } else {
                    playerProfileMenuControllerHandlerFieldReference.showConfigDialog();
                }
            }
        });

        viewInterfaceFieldReference.setMatchClickListener(eventArgumentParameterValue3 -> {
            String[] teamsLocalVariableValue = (String[]) eventArgumentParameterValue3.getSource();
            showLiveMatchView(teamsLocalVariableValue);
        });
    }

    /**
     * Refresca la lista de partidos en vivo en la vista.
     * Obtiene los partidos dependiendo del tipo de usuario (admin o jugador).
     */
    private void refreshMatches() {
        if (playerProfileManagerServiceFieldReference.getCurrentIdentifier().equals("admin")) {
            List<String[]> matchesLocalVariableValue = gameEntityManagerServiceFieldReference.getLiveGames();
            if (matchesLocalVariableValue == null) {
                viewInterfaceFieldReference.showMessageDialog("There are no live matches currently.");
            }
            viewInterfaceFieldReference.updateMatches(matchesLocalVariableValue);
        }
        else {
            List<String[]> matchesLocalVariableValue2 = playerProfileManagerServiceFieldReference.getLiveMatches();
            if (matchesLocalVariableValue2 == null) {
                viewInterfaceFieldReference.showMessageDialog("There are no live matches currently.");
            }
            viewInterfaceFieldReference.updateMatches(matchesLocalVariableValue2);
        }

    }

    /**
     * Método para gestionar eventos de acción (por implementar).
     *
     * @param e Evento de acción
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue4) {
        // Gestiona acciones de botones si hay
    }

    /**
     * Obtiene la lista de juegos en vivo desde GameManager.
     *
     * @return Lista de juegos en vivo representados como arrays de String
     */
    public List<String[]> getterLiveGames() {
        return gameEntityManagerServiceFieldReference.getLiveGames();
    }

    /**
     * Muestra la vista de un partido en vivo dado el equipo local y visitante.
     * Si ya existe una ventana para ese partido, la muestra; si no, no hace nada.
     *
     * @param gameInfo Array con el nombre del equipo local en la posición 0 y del equipo visitante en la posición 1
     */
    private void showLiveMatchView(String[] gameEntityInformationParameterValue) {

        String localTeamReferenceLocalVariableValue = gameEntityInformationParameterValue[0].trim();
        String awayTeamReferenceLocalVariableValue = gameEntityInformationParameterValue[1].trim();

        int leagueReferenceIdentifierLocalVariableValue = leagueReferenceManagerServiceFieldReference.getLeagueIdByTeam(localTeamReferenceLocalVariableValue);
        int gameEntityIdentifierLocalVariableValue = gameEntityManagerServiceFieldReference.getGameIdByLeague(localTeamReferenceLocalVariableValue, awayTeamReferenceLocalVariableValue, leagueReferenceIdentifierLocalVariableValue);

        for (Window windowLocalVariableValue : Window.getWindows()) {
            if (windowLocalVariableValue instanceof LiveMatchView) {
                LiveMatchView matchViewInterfaceLocalVariableValue = (LiveMatchView) windowLocalVariableValue;
                // Comprobar si los detalles del juego coinciden con la ventana actual
                if (matchViewInterfaceLocalVariableValue.getHomeTeamName().equals(localTeamReferenceLocalVariableValue) && matchViewInterfaceLocalVariableValue.getAwayTeamName().equals(awayTeamReferenceLocalVariableValue) && matchViewInterfaceLocalVariableValue.getGameId() == gameEntityIdentifierLocalVariableValue) {
                    matchViewInterfaceLocalVariableValue.setVisible(true);
                    return;
                }
            }
        }
    }

}