package presentation.ControllerViews;

import bussines.managers.LeagueManager;
import bussines.objects.Game;
import bussines.objects.Team;
import presentation.AppNavigator;
import presentation.Views.TeamSelectionView;
import presentation.Views.CreateLeagueView;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Controlador del paso de selección de equipos para crear liga. */
public class TeamSelectionController implements ActionListener {
    private final TeamSelectionView teamReferenceSelectionViewInterfaceFieldReference;
    private final LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();
    private final AppNavigator navigatorFieldReference;
    private LeagueDetailController.CalendarViewLoader calendarViewLoaderHandlerFieldReference;
    private String leagueNameFieldReference;
    private String startDateFieldReference;
    private String startHourFieldReference;

    public TeamSelectionController(TeamSelectionView teamReferenceSelectionViewInterfaceParameterValue,
                                   AppNavigator navigatorParameterValue,
                                   LeagueDetailController.CalendarViewLoader calendarViewLoaderParameterValue) {
        this.teamReferenceSelectionViewInterfaceFieldReference = teamReferenceSelectionViewInterfaceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.calendarViewLoaderHandlerFieldReference = calendarViewLoaderParameterValue;
        this.teamReferenceSelectionViewInterfaceFieldReference.registerController(this);
        this.teamReferenceSelectionViewInterfaceFieldReference.setBackButtonListener(eventArgumentParameterValue -> navigatorFieldReference.show(AppNavigator.CREATE_LEAGUE));
    }

    public TeamSelectionController(TeamSelectionView teamReferenceSelectionViewInterfaceParameterValue,
                                   CreateLeagueView createLeagueReferenceViewInterfaceParameterValue) {
        this(teamReferenceSelectionViewInterfaceParameterValue, AppNavigator.getInstance(), null);
    }

    public void setCalendarViewLoader(LeagueDetailController.CalendarViewLoader loaderParameterValue) {
        this.calendarViewLoaderHandlerFieldReference = loaderParameterValue;
    }

    public void prepareLeagueCreation(String leagueNameParameterValue,
                                      String startDateParameterValue,
                                      String startHourParameterValue,
                                      ArrayList<Team> teamsParameterValue) {
        this.leagueNameFieldReference = leagueNameParameterValue;
        this.startDateFieldReference = startDateParameterValue;
        this.startHourFieldReference = startHourParameterValue;
        teamReferenceSelectionViewInterfaceFieldReference.loadAvailableTeams(teamsParameterValue);
    }

    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        if (TeamSelectionView.CREATE_LEAGUE.equals(eventArgumentParameterValue.getActionCommand())) {
            createLeague();
        }
    }

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
