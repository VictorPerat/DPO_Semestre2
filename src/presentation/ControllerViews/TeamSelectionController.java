package presentation.ControllerViews;

import bussines.managers.LeagueManager;
import bussines.objects.Game;
import bussines.objects.Team;
import presentation.AppNavigator;
import presentation.Views.TeamSelectionView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * Coordina la pantalla del equipo.
 */
public class TeamSelectionController implements ActionListener {
    private final TeamSelectionView teamReferenceSelectionViewInterfaceFieldReference;
    private final LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();
    private final AppNavigator navigatorFieldReference;
    private LeagueDetailController.CalendarViewLoader calendarViewLoaderHandlerFieldReference;
    private String leagueNameFieldReference;
    private String startDateFieldReference;
    private String startHourFieldReference;


    /**
     * Crea una instancia de el equipo.
     *
     * @param teamReferenceSelectionViewInterfaceParameterValue equipo vista.
     * @param navigatorParameterValue navegacion que usa la operacion.
     * @param calendarViewLoaderParameterValue calendario vista.
     */
    public TeamSelectionController(TeamSelectionView teamReferenceSelectionViewInterfaceParameterValue,
                                   AppNavigator navigatorParameterValue,
                                   LeagueDetailController.CalendarViewLoader calendarViewLoaderParameterValue) {
        this.teamReferenceSelectionViewInterfaceFieldReference = teamReferenceSelectionViewInterfaceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.calendarViewLoaderHandlerFieldReference = calendarViewLoaderParameterValue;
        this.teamReferenceSelectionViewInterfaceFieldReference.registerController(this);
        this.teamReferenceSelectionViewInterfaceFieldReference.setBackButtonListener(eventArgumentParameterValue -> navigatorFieldReference.show(AppNavigator.CREATE_LEAGUE));
    }


    /**
     * Actualiza el calendario vista.
     *
     * @param loaderParameterValue dato de entrada de la operacion.
     */
    public void setCalendarViewLoader(LeagueDetailController.CalendarViewLoader loaderParameterValue) {
        this.calendarViewLoaderHandlerFieldReference = loaderParameterValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param leagueNameParameterValue nombre de la liga.
     * @param startDateParameterValue dato de entrada de la operacion.
     * @param startHourParameterValue dato de entrada de la operacion.
     * @param teamsParameterValue equipos que usa la operacion.
     */
    public void prepareLeagueCreation(String leagueNameParameterValue,
                                      String startDateParameterValue,
                                      String startHourParameterValue,
                                      ArrayList<Team> teamsParameterValue) {
        this.leagueNameFieldReference = leagueNameParameterValue;
        this.startDateFieldReference = startDateParameterValue;
        this.startHourFieldReference = startHourParameterValue;
        teamReferenceSelectionViewInterfaceFieldReference.loadAvailableTeams(teamsParameterValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param eventArgumentParameterValue dato de entrada de la operacion.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        if (TeamSelectionView.CREATE_LEAGUE.equals(eventArgumentParameterValue.getActionCommand())) {
            createLeague();
        }
    }


    /**
     * Crea el liga.
     */
    private void createLeague() {
        ArrayList<String> selectedTeamsLocalVariableValue = teamReferenceSelectionViewInterfaceFieldReference.getSelectedTeamNames();
        if (selectedTeamsLocalVariableValue.size() < 2) {
            teamReferenceSelectionViewInterfaceFieldReference.showMessageDialog("Select at least two teams.");
            return;
        }
        leagueReferenceManagerServiceFieldReference.createLeague(leagueNameFieldReference, startDateFieldReference, selectedTeamsLocalVariableValue);
        int leagueIdLocalVariableValue = leagueReferenceManagerServiceFieldReference.getLeagueIdByName(leagueNameFieldReference);
        LocalDateTime startDateTimeLocalVariableValue = LocalDateTime.parse(startDateFieldReference + "T" + startHourFieldReference);
        leagueReferenceManagerServiceFieldReference.generateAndInsertMatchesForLeague(new ArrayList<>(selectedTeamsLocalVariableValue), leagueIdLocalVariableValue, startDateTimeLocalVariableValue);
        ArrayList<Game> gamesLocalVariableValue = new bussines.managers.GameManager().getGamesByLeague(leagueIdLocalVariableValue);
        if (calendarViewLoaderHandlerFieldReference != null) {
            calendarViewLoaderHandlerFieldReference.loadCalendar(selectedTeamsLocalVariableValue, gamesLocalVariableValue);
        }
        navigatorFieldReference.setReturnAction(() -> navigatorFieldReference.show(AppNavigator.ADMIN_MENU));
        navigatorFieldReference.show(AppNavigator.CALENDAR);
    }
}


