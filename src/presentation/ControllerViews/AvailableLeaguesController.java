package presentation.ControllerViews;

import bussines.managers.*;
import bussines.objects.League;
import bussines.objects.Team;
import bussines.objects.TeamInfo;
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
 * Controlador para la vista de ligas disponibles.
 * Maneja la interacción con la lista de ligas, mostrando detalles según el tipo de usuario (admin o jugador),
 * y proporciona funcionalidades como abrir detalles de liga, gestión de configuración y cierre de sesión.
 */
public class AvailableLeaguesController implements ActionListener {
    private final AvailableLeaguesView viewInterfaceFieldReference;
    private final MenuController menuControllerHandlerFieldReference;
    private LeagueViewActions leagueReferenceActionsFieldReference = null;
    private final AdminMenuController adminMenuControllerHandlerFieldReference;
    private PlayerMenuController playerProfileMenuControllerHandlerFieldReference;
    private final boolean isAdminFieldReference;
    private List<League> currentLeaguesFieldReference;
    private final LeagueManager leagueReferenceManagerServiceFieldReference;
    private PlayerManager playerProfileManagerServiceFieldReference;
    private TeamInfoManager informationTeamReferenceManagerServiceFieldReference;
    private TeamManager teamReferenceManagerServiceFieldReference;
    private GameManager gameEntityManagerServiceFieldReference;

    /**
     * Constructor para administrador.
     * Inicializa el controlador con la vista, controlador del menú admin y manager de jugadores.
     * Configura la vista y carga ligas.
     *
     * @param view Vista de ligas disponibles.
     * @param adminMenuController Controlador del menú administrador.
     * @param playerManager Manager de jugadores.
     */
    public AvailableLeaguesController(AvailableLeaguesView viewInterfaceParameterValue, AdminMenuController adminMenuControllerHandlerParameterValue, PlayerManager playerProfileManagerServiceParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.adminMenuControllerHandlerFieldReference = adminMenuControllerHandlerParameterValue;
        this.menuControllerHandlerFieldReference = adminMenuControllerHandlerParameterValue;
        this.leagueReferenceManagerServiceFieldReference = new LeagueManager();
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.informationTeamReferenceManagerServiceFieldReference = new TeamInfoManager();
        this.teamReferenceManagerServiceFieldReference = new TeamManager();
        this.gameEntityManagerServiceFieldReference = new GameManager();
        this.viewInterfaceFieldReference.setConfigController(this);

        if (adminMenuControllerHandlerParameterValue instanceof LeagueViewActions) {
            this.leagueReferenceActionsFieldReference = (LeagueViewActions) adminMenuControllerHandlerParameterValue;
        }

        this.isAdminFieldReference = true;
        setupView();
        setupListeners();
        loadAndDisplayLeagues();
    }

    /**
     * Constructor para jugador.
     * Inicializa el controlador con la vista, controlador de menú jugador y manager de jugadores.
     * Configura la vista y carga ligas correspondientes al equipo del jugador.
     *
     * @param view Vista de ligas disponibles.
     * @param playerMenuController Controlador del menú jugador.
     * @param playerManager Manager de jugadores.
     */
    public AvailableLeaguesController(AvailableLeaguesView viewInterfaceParameterValue2, PlayerMenuController playerProfileMenuControllerHandlerParameterValue, PlayerManager playerProfileManagerServiceParameterValue2) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue2;
        this.adminMenuControllerHandlerFieldReference = null;
        this.playerProfileMenuControllerHandlerFieldReference = playerProfileMenuControllerHandlerParameterValue;
        this.menuControllerHandlerFieldReference = playerProfileMenuControllerHandlerParameterValue;
        this.leagueReferenceManagerServiceFieldReference = new LeagueManager();
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue2;
        this.informationTeamReferenceManagerServiceFieldReference = new TeamInfoManager();
        this.teamReferenceManagerServiceFieldReference = new TeamManager();
        this.gameEntityManagerServiceFieldReference = new GameManager();
        this.viewInterfaceFieldReference.setConfigController(this);

        this.leagueReferenceActionsFieldReference = null;
        this.isAdminFieldReference = false;
        setupView();
        setupListeners();
        loadAndDisplayLeagues();
    }

    /**
     * Configura la vista para mostrar u ocultar funcionalidades según el rol (admin o jugador).
     *
     * @return true si es admin, false si es jugador.
     */
    private boolean setupView() {
        // Mostrar/amagar funcions d'admin segons correspongui
        boolean isAdminLocalVariableValue = (leagueReferenceActionsFieldReference != null);
        viewInterfaceFieldReference.setAdminFunctionsVisible(isAdminLocalVariableValue);
        viewInterfaceFieldReference.setMenuController(menuControllerHandlerFieldReference);
        return isAdminLocalVariableValue;
    }

    /**
     * Configura listeners y comportamiento de la ventana.
     */
    private void setupListeners() {
        viewInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Maneja la acción de cierre de sesión:
     * cierra todas las ventanas abiertas y abre la vista de login.
     */
    private void handleLogout() {
        viewInterfaceFieldReference.dispose();
        for (Frame frameLocalVariableValue : JFrame.getFrames()) {
            frameLocalVariableValue.dispose();
        }
        SwingUtilities.invokeLater(() -> {
            LoginView loginViewInterfaceLocalVariableValue = new LoginView();
            new LoginController(loginViewInterfaceLocalVariableValue, new PlayerManager());
        });
    }

    /**
     * Abre los detalles de una liga, cargando los equipos relacionados y la información de equipo.
     * Dependiendo del rol, crea el controlador correspondiente y muestra la vista de detalles.
     *
     * @param league Liga seleccionada.
     */
    public void openLeagueDetails(League leagueReferenceParameterValue) {
        ArrayList<Team> filteredTeamsLocalVariableValue = new ArrayList<>();
        // Abre una nueva vista con detalles de la liga
        int identifierLocalVariableValue = leagueReferenceManagerServiceFieldReference.getLeagueIdByName(leagueReferenceParameterValue.getName());

        ArrayList<TeamInfo> informationOfTeamsLocalVariableValue = informationTeamReferenceManagerServiceFieldReference.getInfoTeamsOfLeague(identifierLocalVariableValue);
        ArrayList<Team> teamsLocalVariableValue = teamReferenceManagerServiceFieldReference.getAllTeams();
        for (Team teamReferenceLocalVariableValue : teamsLocalVariableValue) {
            for (TeamInfo informationLocalVariableValue : informationOfTeamsLocalVariableValue) {
                if (teamReferenceLocalVariableValue.getId() == informationLocalVariableValue.getTeamId()) {
                    filteredTeamsLocalVariableValue.add(teamReferenceLocalVariableValue);
                    break; // ya no hace falta seguir buscando ese team
                }
            }
        }
        viewInterfaceFieldReference.dispose();
        LeagueDetailView detailScreenInterfaceLocalVariableValue = new LeagueDetailView(leagueReferenceParameterValue, informationOfTeamsLocalVariableValue, filteredTeamsLocalVariableValue, playerProfileManagerServiceFieldReference);

        if (playerProfileManagerServiceFieldReference.getCurrentIdentifier().equals("admin")) {
            new LeagueDetailController(detailScreenInterfaceLocalVariableValue, adminMenuControllerHandlerFieldReference, null, playerProfileManagerServiceFieldReference, gameEntityManagerServiceFieldReference, leagueReferenceParameterValue, informationTeamReferenceManagerServiceFieldReference, new TeamManager());
        }
        else {
            new LeagueDetailController(detailScreenInterfaceLocalVariableValue, null, playerProfileMenuControllerHandlerFieldReference, playerProfileManagerServiceFieldReference, gameEntityManagerServiceFieldReference, leagueReferenceParameterValue, informationTeamReferenceManagerServiceFieldReference, new TeamManager());
        }
        detailScreenInterfaceLocalVariableValue.setVisible(true);
        detailScreenInterfaceLocalVariableValue.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent eventArgumentParameterValue) {
                if (1 == LeagueDetailController.getFlag()) {
                    viewInterfaceFieldReference.setVisible(true);
                }
            }
        });
    }

    /**
     * Método para manejar eventos de acción (vacío en esta versión).
     *
     * @param e Evento de acción recibido.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue2) {
        String commandLocalVariableValue = eventArgumentParameterValue2.getActionCommand();


    }

    /**
     * Carga las ligas disponibles según el rol (admin o jugador) y las muestra en la vista.
     */
    private void loadAndDisplayLeagues() {
        //Llama al LeagueManager para obtener las ligas reales
        this.currentLeaguesFieldReference = buscaLigas(this.isAdminFieldReference);
        //Pasa la lista real a la vista
        viewInterfaceFieldReference.displayLeagues(this.currentLeaguesFieldReference, this.isAdminFieldReference, this);
    }

    /**
     * Obtiene la lista de ligas disponibles según el rol del usuario.
     * Admin obtiene todas; jugador solo las ligas donde participa su equipo.
     *
     * @param isAdmin Indica si el usuario es administrador.
     * @return Lista de ligas correspondientes.
     */
    private List<League> buscaLigas(boolean isAdminParameterValue) {
        List<League> leaguesLocalVariableValue = new ArrayList<>();
        if (isAdminParameterValue) {
            return leagueReferenceManagerServiceFieldReference.getAllLeagues();
        }else{
            if (playerProfileManagerServiceFieldReference != null && playerProfileManagerServiceFieldReference.getCurrentPlayer() != null) {
                String teamReferenceDisplayNameLocalVariableValue = playerProfileManagerServiceFieldReference.getCurrentPlayer().getTeam();
                return leagueReferenceManagerServiceFieldReference.getLeaguesByUserTeam(teamReferenceDisplayNameLocalVariableValue);
            }
        }
        return leaguesLocalVariableValue;
    }

}