package presentation.ControllerViews;

import bussines.managers.*;
import bussines.objects.League;
import bussines.objects.Team;
import bussines.objects.TeamInfo;
import presentation.AppNavigator;
import presentation.Views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Esta clase se encarga de controlar la pantalla de ligas disponibles.
 */
public class AvailableLeaguesController implements ActionListener {

    // Vista principal de ligas disponibles
    private final AvailableLeaguesView viewInterfaceFieldReference;

    // Controlador del menú desde el que se ha abierto esta vista
    private final MenuController menuControllerHandlerFieldReference;

    // Referencia a acciones especiales si el usuario es admin
    private LeagueViewActions leagueReferenceActionsFieldReference = null;

    // Referencias a los controladores de menú
    private final AdminMenuController adminMenuControllerHandlerFieldReference;
    private PlayerMenuController playerProfileMenuControllerHandlerFieldReference;

    // Indica si el usuario actual es admin
    private final boolean isAdminFieldReference;

    // Lista de ligas cargadas actualmente
    private List<League> currentLeaguesFieldReference;

    // Managers usados para obtener la información necesaria
    private final LeagueManager leagueReferenceManagerServiceFieldReference;
    private PlayerManager playerProfileManagerServiceFieldReference;
    private TeamInfoManager informationTeamReferenceManagerServiceFieldReference;
    private TeamManager teamReferenceManagerServiceFieldReference;
    private GameManager gameEntityManagerServiceFieldReference;

    // Constructor para el caso de administrador
    public AvailableLeaguesController(AvailableLeaguesView viewInterfaceParameterValue,
                                      AdminMenuController adminMenuControllerHandlerParameterValue,
                                      PlayerManager playerProfileManagerServiceParameterValue) {

        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.adminMenuControllerHandlerFieldReference = adminMenuControllerHandlerParameterValue;
        this.menuControllerHandlerFieldReference = adminMenuControllerHandlerParameterValue;
        this.leagueReferenceManagerServiceFieldReference = new LeagueManager();
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.informationTeamReferenceManagerServiceFieldReference = new TeamInfoManager();
        this.teamReferenceManagerServiceFieldReference = new TeamManager();
        this.gameEntityManagerServiceFieldReference = new GameManager();
        this.viewInterfaceFieldReference.setConfigController(this);

        // Si el menú admin permite acciones sobre ligas, se guarda la referencia
        if (adminMenuControllerHandlerParameterValue instanceof LeagueViewActions) {
            this.leagueReferenceActionsFieldReference =
                    (LeagueViewActions) adminMenuControllerHandlerParameterValue;
        }

        this.isAdminFieldReference = true;

        setupView();
        setupListeners();
        loadAndDisplayLeagues();
    }

    // Constructor para el caso de jugador
    public AvailableLeaguesController(AvailableLeaguesView viewInterfaceParameterValue2,
                                      PlayerMenuController playerProfileMenuControllerHandlerParameterValue,
                                      PlayerManager playerProfileManagerServiceParameterValue2) {

        this.viewInterfaceFieldReference = viewInterfaceParameterValue2;
        this.adminMenuControllerHandlerFieldReference = null;
        this.playerProfileMenuControllerHandlerFieldReference =
                playerProfileMenuControllerHandlerParameterValue;
        this.menuControllerHandlerFieldReference =
                playerProfileMenuControllerHandlerParameterValue;
        this.leagueReferenceManagerServiceFieldReference = new LeagueManager();
        this.playerProfileManagerServiceFieldReference =
                playerProfileManagerServiceParameterValue2;
        this.informationTeamReferenceManagerServiceFieldReference =
                new TeamInfoManager();
        this.teamReferenceManagerServiceFieldReference = new TeamManager();
        this.gameEntityManagerServiceFieldReference = new GameManager();
        this.viewInterfaceFieldReference.setConfigController(this);

        this.leagueReferenceActionsFieldReference = null;
        this.isAdminFieldReference = false;

        setupView();
        setupListeners();
        loadAndDisplayLeagues();
    }

    // Configura la vista según si el usuario es admin o jugador
    private boolean setupView() {
        boolean isAdminLocalVariableValue =
                (leagueReferenceActionsFieldReference != null);

        viewInterfaceFieldReference.setAdminFunctionsVisible(isAdminLocalVariableValue);
        viewInterfaceFieldReference.setMenuController(menuControllerHandlerFieldReference);

        return isAdminLocalVariableValue;
    }

    // Configura algunos listeners básicos de la ventana
    private void setupListeners() {
        viewInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Cierra sesión y vuelve al login
    private void handleLogout() {
        viewInterfaceFieldReference.dispose();

        // Cierra las ventanas abiertas menos la principal
        MainView mainViewInterfaceLocalVariableValue =
                AppNavigator.getInstance().getMainView();

        for (Frame frameLocalVariableValue : JFrame.getFrames()) {
            if (frameLocalVariableValue != mainViewInterfaceLocalVariableValue) {
                frameLocalVariableValue.dispose();
            }
        }

        SwingUtilities.invokeLater(() -> {
            AppNavigator.getInstance().show(AppNavigator.LOGIN);
        });
    }

    // Abre la pantalla de detalles de una liga
    public void openLeagueDetails(League leagueReferenceParameterValue) {
        ArrayList<Team> filteredTeamsLocalVariableValue = new ArrayList<>();

        int identifierLocalVariableValue =
                leagueReferenceManagerServiceFieldReference.getLeagueIdByName(
                        leagueReferenceParameterValue.getName()
                );

        ArrayList<TeamInfo> informationOfTeamsLocalVariableValue =
                informationTeamReferenceManagerServiceFieldReference.getInfoTeamsOfLeague(
                        identifierLocalVariableValue
                );

        ArrayList<Team> teamsLocalVariableValue =
                teamReferenceManagerServiceFieldReference.getAllTeams();

        // Filtra solo los equipos que participan en esa liga
        for (Team teamReferenceLocalVariableValue : teamsLocalVariableValue) {
            for (TeamInfo informationLocalVariableValue : informationOfTeamsLocalVariableValue) {
                if (teamReferenceLocalVariableValue.getId() ==
                        informationLocalVariableValue.getTeamId()) {

                    filteredTeamsLocalVariableValue.add(teamReferenceLocalVariableValue);
                    break;
                }
            }
        }

        viewInterfaceFieldReference.dispose();

        LeagueDetailView detailScreenInterfaceLocalVariableValue =
                new LeagueDetailView(
                        leagueReferenceParameterValue,
                        informationOfTeamsLocalVariableValue,
                        filteredTeamsLocalVariableValue,
                        playerProfileManagerServiceFieldReference
                );

        // Si el usuario es admin, crea el controlador con el menú admin
        if (playerProfileManagerServiceFieldReference.getCurrentIdentifier().equals("admin")) {
            new LeagueDetailController(
                    detailScreenInterfaceLocalVariableValue,
                    adminMenuControllerHandlerFieldReference,
                    null,
                    playerProfileManagerServiceFieldReference,
                    gameEntityManagerServiceFieldReference,
                    leagueReferenceParameterValue,
                    informationTeamReferenceManagerServiceFieldReference,
                    new TeamManager()
            );
        } else {
            // Si es jugador, crea el controlador con el menú de jugador
            new LeagueDetailController(
                    detailScreenInterfaceLocalVariableValue,
                    null,
                    playerProfileMenuControllerHandlerFieldReference,
                    playerProfileManagerServiceFieldReference,
                    gameEntityManagerServiceFieldReference,
                    leagueReferenceParameterValue,
                    informationTeamReferenceManagerServiceFieldReference,
                    new TeamManager()
            );
        }

        detailScreenInterfaceLocalVariableValue.setVisible(true);

        // Si se cierra la ventana de detalle, vuelve a mostrar la de ligas
        detailScreenInterfaceLocalVariableValue.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent eventArgumentParameterValue) {
                if (1 == LeagueDetailController.getFlag()) {
                    viewInterfaceFieldReference.setVisible(true);
                }
            }
        });
    }

    // Método para manejar acciones de botones o eventos
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue2) {
        String commandLocalVariableValue =
                eventArgumentParameterValue2.getActionCommand();
    }

    // Carga las ligas y las muestra en la vista
    private void loadAndDisplayLeagues() {
        this.currentLeaguesFieldReference = buscaLigas(this.isAdminFieldReference);

        viewInterfaceFieldReference.displayLeagues(
                this.currentLeaguesFieldReference,
                this.isAdminFieldReference,
                this
        );
    }

    // Busca las ligas disponibles según el tipo de usuario
    private List<League> buscaLigas(boolean isAdminParameterValue) {
        List<League> leaguesLocalVariableValue = new ArrayList<>();

        if (isAdminParameterValue) {
            return leagueReferenceManagerServiceFieldReference.getAllLeagues();
        } else {
            if (playerProfileManagerServiceFieldReference != null
                    && playerProfileManagerServiceFieldReference.getCurrentPlayer() != null) {

                String teamReferenceDisplayNameLocalVariableValue =
                        playerProfileManagerServiceFieldReference
                                .getCurrentPlayer()
                                .getTeam();

                return leagueReferenceManagerServiceFieldReference.getLeaguesByUserTeam(
                        teamReferenceDisplayNameLocalVariableValue
                );
            }
        }

        return leaguesLocalVariableValue;
    }
}