package presentation.ControllerViews;

import bussines.managers.GameManager;
import bussines.managers.LeagueManager;
import bussines.managers.PlayerManager;
import presentation.Views.LiveMatchView;
import presentation.Views.LiveMatchesView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Controlador que carga, refresca y abre los partidos en directo
public class LiveMatchesController implements ActionListener {

    // Vista principal que lista los partidos en directo
    private final LiveMatchesView viewInterfaceFieldReference;

    // Controlador del menu de jugador para volver atras o abrir ajustes
    private final PlayerMenuController playerProfileMenuControllerHandlerFieldReference;

    // Manager de jugador para saber quien esta logado
    private final PlayerManager playerProfileManagerServiceFieldReference;

    // Controlador del menu de admin cuando el usuario actual es admin
    private AdminMenuController adminMenuControllerHandlerFieldReference;

    // Servicios usados para recuperar partidos y ligas
    private GameManager gameEntityManagerServiceFieldReference;
    private LeagueManager leagueReferenceManagerServiceFieldReference =
            new LeagueManager();

    // Cache de controladores de partidos en vivo
    private final Map<Integer, LiveMatchController> liveMatchControllersFieldReference =
            new HashMap<>();

    // Guarda dependencias y deja registrada toda la interaccion de la vista
    public LiveMatchesController(
            LiveMatchesView viewInterfaceParameterValue,
            PlayerMenuController menuControllerHandlerParameterValue,
            PlayerManager playerProfileManagerServiceParameterValue,
            AdminMenuController adminMenuControllerHandlerParameterValue) {

        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.playerProfileMenuControllerHandlerFieldReference =
                menuControllerHandlerParameterValue;
        this.playerProfileManagerServiceFieldReference =
                playerProfileManagerServiceParameterValue;
        this.adminMenuControllerHandlerFieldReference =
                adminMenuControllerHandlerParameterValue;
        this.gameEntityManagerServiceFieldReference = new GameManager();

        setupView();
        setupListeners();
    }

    // Carga el estado inicial de los partidos mostrados
    private void setupView() {
        refreshMatches();
    }

    // Conecta la vista con las acciones de volver, config y apertura de partido
    private void setupListeners() {
        viewInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        viewInterfaceFieldReference.setBackButtonListener(eventArgumentParameterValue -> {
            viewInterfaceFieldReference.dispose();

            if (playerProfileManagerServiceFieldReference
                    .getCurrentIdentifier()
                    .equals("admin")) {
                adminMenuControllerHandlerFieldReference.showMenu();
            } else {
                playerProfileMenuControllerHandlerFieldReference.showMenu();
            }
        });

        viewInterfaceFieldReference.setConfigController(eventArgumentParameterValue2 -> {
            if (LiveMatchesView.CONFIG.equals(
                    eventArgumentParameterValue2.getActionCommand())) {
                if (playerProfileManagerServiceFieldReference
                        .getCurrentIdentifier()
                        .equals("admin")) {
                    adminMenuControllerHandlerFieldReference.showConfigDialog();
                } else {
                    playerProfileMenuControllerHandlerFieldReference.showConfigDialog();
                }
            }
        });

        viewInterfaceFieldReference.setMatchClickListener(eventArgumentParameterValue3 -> {
            String[] teamsLocalVariableValue =
                    (String[]) eventArgumentParameterValue3.getSource();
            showLiveMatchView(teamsLocalVariableValue);
        });
    }

    // Pide los partidos en directo y actualiza la vista segun el tipo de usuario
    private void refreshMatches() {
        if (playerProfileManagerServiceFieldReference
                .getCurrentIdentifier()
                .equals("admin")) {
            List<String[]> matchesLocalVariableValue =
                    gameEntityManagerServiceFieldReference.getLiveGames();

            if (matchesLocalVariableValue == null) {
                viewInterfaceFieldReference.showMessageDialog(
                        "There are no live matches currently."
                );
            }
            viewInterfaceFieldReference.updateMatches(matchesLocalVariableValue);
        } else {
            List<String[]> matchesLocalVariableValue2 =
                    playerProfileManagerServiceFieldReference.getLiveMatches();

            if (matchesLocalVariableValue2 == null) {
                viewInterfaceFieldReference.showMessageDialog(
                        "There are no live matches currently."
                );
            }
            viewInterfaceFieldReference.updateMatches(matchesLocalVariableValue2);
        }
    }

    // Punto comun para futuras acciones de botones si se necesitan
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue4) {
        // Gestiona acciones de botones si hay
    }

    // Expone la lista de partidos en directo para el auto-refresh de la vista
    public List<String[]> getterLiveGames() {
        return gameEntityManagerServiceFieldReference.getLiveGames();
    }

    // Intenta abrir la ventana ya existente del partido seleccionado
    private void showLiveMatchView(String[] gameEntityInformationParameterValue) {
        String localTeamReferenceLocalVariableValue =
                gameEntityInformationParameterValue[0].trim();
        String awayTeamReferenceLocalVariableValue =
                gameEntityInformationParameterValue[1].trim();

        int leagueReferenceIdentifierLocalVariableValue =
                leagueReferenceManagerServiceFieldReference.getLeagueIdByTeam(
                        localTeamReferenceLocalVariableValue
                );
        int gameEntityIdentifierLocalVariableValue =
                gameEntityManagerServiceFieldReference.getGameIdByLeague(
                        localTeamReferenceLocalVariableValue,
                        awayTeamReferenceLocalVariableValue,
                        leagueReferenceIdentifierLocalVariableValue
                );

        for (Window windowLocalVariableValue : Window.getWindows()) {
            if (windowLocalVariableValue instanceof LiveMatchView) {
                LiveMatchView matchViewInterfaceLocalVariableValue =
                        (LiveMatchView) windowLocalVariableValue;

                // Si ya existe la ventana de ese partido, solo la volvemos a mostrar
                if (matchViewInterfaceLocalVariableValue
                        .getHomeTeamName()
                        .equals(localTeamReferenceLocalVariableValue)
                        && matchViewInterfaceLocalVariableValue
                        .getAwayTeamName()
                        .equals(awayTeamReferenceLocalVariableValue)
                        && matchViewInterfaceLocalVariableValue.getGameId()
                        == gameEntityIdentifierLocalVariableValue) {
                    matchViewInterfaceLocalVariableValue.setVisible(true);
                    return;
                }
            }
        }
    }
}
