package presentation.ControllerViews;

import bussines.managers.GameManager;
import bussines.managers.LeagueManager;
import bussines.managers.PlayerManager;
import bussines.managers.TeamInfoManager;
import bussines.managers.TeamManager;
import bussines.objects.Game;
import bussines.objects.League;
import bussines.objects.Player;
import bussines.objects.Team;
import bussines.objects.TeamInfo;
import presentation.AppNavigator;
import presentation.Views.LeagueDetailView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * Coordina la pantalla del liga detalle.
 */
public class LeagueDetailController implements ActionListener {
    private final LeagueDetailView viewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();
    private final TeamInfoManager informationTeamReferenceManagerServiceFieldReference = new TeamInfoManager();
    private final TeamManager teamReferenceManagerServiceFieldReference = new TeamManager();
    private final GameManager gameEntityManagerServiceFieldReference = new GameManager();
    private final AppNavigator navigatorFieldReference;
    private TeamDetailController teamDetailControllerHandlerFieldReference;
    private StatisticsGraphController statisticsGraphControllerHandlerFieldReference;
    private CalendarViewLoader calendarViewLoaderHandlerFieldReference;
    private League currentLeagueFieldReference;
    private Timer standingsAutoRefreshTimerFieldReference;
    private static final int REFRESH_INTERVAL_MS = 5_000;


    /**
     * Define el contrato del calendario vista.
     */
    public interface CalendarViewLoader {


        /**
         * Carga el calendario.
         *
         * @param teamsParameterValue equipos que usa la operacion.
         * @param gamesParameterValue partidos que usa la operacion.
         */
        void loadCalendar(ArrayList<String> teamsParameterValue, ArrayList<Game> gamesParameterValue);
    }


    /**
     * Crea una instancia de el liga detalle.
     *
     * @param viewInterfaceParameterValue vista que usa la operacion.
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
    public LeagueDetailController(LeagueDetailView viewInterfaceParameterValue,
                                  PlayerManager playerProfileManagerServiceParameterValue,
                                  AppNavigator navigatorParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.viewInterfaceFieldReference.registerController(this);
        this.viewInterfaceFieldReference.getStandingsTable().addMouseListener(new MouseAdapter() {

            @Override public void mouseClicked(MouseEvent eventArgumentParameterValue) {
                if (eventArgumentParameterValue.getClickCount() >= 2) {
                    openSelectedTeam();
                }
            }
        });
        this.viewInterfaceFieldReference.addHierarchyListener(eventArgumentParameterValue -> {
            if ((eventArgumentParameterValue.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (viewInterfaceFieldReference.isShowing()) { startStandingsAutoRefresh(); }
                else { stopStandingsAutoRefresh(); }
            }
        });
    }


    /**
     * Actualiza el equipo detalle.
     *
     * @param controllerParameterValue dato de entrada de la operacion.
     */
    public void setTeamDetailController(TeamDetailController controllerParameterValue) { this.teamDetailControllerHandlerFieldReference = controllerParameterValue; }


    /**
     * Actualiza el estadisticas grafica.
     *
     * @param controllerParameterValue dato de entrada de la operacion.
     */
    public void setStatisticsGraphController(StatisticsGraphController controllerParameterValue) { this.statisticsGraphControllerHandlerFieldReference = controllerParameterValue; }


    /**
     * Actualiza el calendario vista.
     *
     * @param loaderParameterValue dato de entrada de la operacion.
     */
    public void setCalendarViewLoader(CalendarViewLoader loaderParameterValue) { this.calendarViewLoaderHandlerFieldReference = loaderParameterValue; }


    /**
     * Abre el liga.
     *
     * @param leagueReferenceParameterValue liga que usa la operacion.
     */
    public void openLeague(League leagueReferenceParameterValue) {
        this.currentLeagueFieldReference = leagueReferenceParameterValue;
        refreshCurrentLeague();
        navigatorFieldReference.show(AppNavigator.LEAGUE_DETAIL);
    }


    /**
     * Gestiona esta operacion.
     */
    public void refreshCurrentLeague() {
        if (currentLeagueFieldReference == null) { return; }
        int identifierLocalVariableValue = leagueReferenceManagerServiceFieldReference.getLeagueIdByName(currentLeagueFieldReference.getName());
        ArrayList<TeamInfo> informationOfTeamsLocalVariableValue = informationTeamReferenceManagerServiceFieldReference.getInfoTeamsOfLeague(identifierLocalVariableValue);
        ArrayList<Team> teamsLocalVariableValue = teamReferenceManagerServiceFieldReference.getAllTeams();
        viewInterfaceFieldReference.loadLeague(currentLeagueFieldReference, informationOfTeamsLocalVariableValue, teamsLocalVariableValue, playerProfileManagerServiceFieldReference);
    }


    /**
     * Gestiona esta operacion.
     */
    public void startStandingsAutoRefresh() {
        refreshCurrentLeague();
        if (standingsAutoRefreshTimerFieldReference != null && standingsAutoRefreshTimerFieldReference.isRunning()) { return; }
        standingsAutoRefreshTimerFieldReference = new Timer(REFRESH_INTERVAL_MS, eventArgumentParameterValue -> refreshCurrentLeague());
        standingsAutoRefreshTimerFieldReference.start();
    }


    /**
     * Gestiona esta operacion.
     */
    public void stopStandingsAutoRefresh() {
        if (standingsAutoRefreshTimerFieldReference != null) {
            standingsAutoRefreshTimerFieldReference.stop();
            standingsAutoRefreshTimerFieldReference = null;
        }
    }


    /**
     * Gestiona esta operacion.
     *
     * @param eventArgumentParameterValue dato de entrada de la operacion.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        switch (eventArgumentParameterValue.getActionCommand()) {
            case LeagueDetailView.BACK:
                navigatorFieldReference.show(AppNavigator.AVAILABLE_LEAGUES);
                break;
            case LeagueDetailView.CONFIG:
                showConfigDialog();
                break;
            case LeagueDetailView.SHOW_STATS:
                showStats();
                break;
            case LeagueDetailView.SHOW_CALENDAR:
                showCalendar();
                break;
            default:
                break;
        }
    }


    /**
     * Abre el equipo.
     */
    private void openSelectedTeam() {
        int rowLocalVariableValue = viewInterfaceFieldReference.getStandingsTable().getSelectedRow();
        String teamNameLocalVariableValue = viewInterfaceFieldReference.getTeamNameAtViewRow(rowLocalVariableValue);
        if (teamNameLocalVariableValue == null) { return; }
        ArrayList<Player> playersLocalVariableValue = playerProfileManagerServiceFieldReference.getPlayersByTeam(teamNameLocalVariableValue);
        if (teamDetailControllerHandlerFieldReference != null) {
            teamDetailControllerHandlerFieldReference.openTeam(teamNameLocalVariableValue, playersLocalVariableValue);
        }
    }


    /**
     * Muestra el contenido.
     */
    private void showStats() {
        if (currentLeagueFieldReference == null) { return; }
        if (statisticsGraphControllerHandlerFieldReference != null) {
            int leagueIdLocalVariableValue = leagueReferenceManagerServiceFieldReference.getLeagueIdByName(currentLeagueFieldReference.getName());
            statisticsGraphControllerHandlerFieldReference.showLeagueData(leagueIdLocalVariableValue, currentLeagueFieldReference.getName());
        }
        navigatorFieldReference.show(AppNavigator.STATISTICS);
    }


    /**
     * Muestra el calendario.
     */
    private void showCalendar() {
        if (currentLeagueFieldReference == null) { return; }
        int leagueIdLocalVariableValue = leagueReferenceManagerServiceFieldReference.getLeagueIdByName(currentLeagueFieldReference.getName());
        ArrayList<Game> gamesLocalVariableValue = gameEntityManagerServiceFieldReference.getGamesByLeague(leagueIdLocalVariableValue);
        ArrayList<String> teamNamesLocalVariableValue = new ArrayList<>();
        for (Team teamLocalVariableValue : viewInterfaceFieldReference.getCurrentTeams()) {
            teamNamesLocalVariableValue.add(teamLocalVariableValue.getName());
        }
        navigatorFieldReference.setReturnAction(() -> navigatorFieldReference.show(AppNavigator.LEAGUE_DETAIL));
        if (calendarViewLoaderHandlerFieldReference != null) {
            calendarViewLoaderHandlerFieldReference.loadCalendar(teamNamesLocalVariableValue, gamesLocalVariableValue);
        }
        navigatorFieldReference.show(AppNavigator.CALENDAR);
    }


    /**
     * Muestra el configuracion dialogo.
     */
    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue =
                Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        presentation.AccountSettingsWidgetService.configureDialogForCurrentSession(
                configDialogLocalVariableValue,
                AppNavigator.LEAGUE_DETAIL
        );
        configDialogLocalVariableValue.setVisible(true);
    }
}


