package presentation.ControllerViews;

import bussines.managers.LeagueManager;
import bussines.managers.TeamManager;
import bussines.objects.League;
import bussines.objects.Team;
import presentation.AppNavigator;
import presentation.Views.CreateLeagueView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Coordina la pantalla del liga.
 */
public class CreateLeagueController implements ActionListener {
    private final CreateLeagueView createLeagueReferenceViewInterfaceFieldReference;
    private final LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();
    private final TeamManager teamReferenceManagerServiceFieldReference = new TeamManager();
    private final AppNavigator navigatorFieldReference;
    private TeamSelectionController teamReferenceSelectionControllerHandlerFieldReference;
    private String pendingLeagueNameFieldReference;
    private String pendingStartDateFieldReference;
    private String pendingStartHourFieldReference;


    /**
     * Crea una instancia de el liga.
     *
     * @param createLeagueReferenceViewInterfaceParameterValue liga vista.
     * @param navigatorParameterValue navegacion que usa la operacion.
     * @param teamSelectionControllerParameterValue equipo que usa la operacion.
     */
    public CreateLeagueController(CreateLeagueView createLeagueReferenceViewInterfaceParameterValue,
                                  AppNavigator navigatorParameterValue,
                                  TeamSelectionController teamSelectionControllerParameterValue) {
        this.createLeagueReferenceViewInterfaceFieldReference = createLeagueReferenceViewInterfaceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.teamReferenceSelectionControllerHandlerFieldReference = teamSelectionControllerParameterValue;
        this.createLeagueReferenceViewInterfaceFieldReference.registerController(this);
    }


    /**
     * Actualiza el equipo.
     *
     * @param controllerParameterValue dato de entrada de la operacion.
     */
    public void setTeamSelectionController(TeamSelectionController controllerParameterValue) {
        this.teamReferenceSelectionControllerHandlerFieldReference = controllerParameterValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param eventArgumentParameterValue dato de entrada de la operacion.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();
        if (CreateLeagueView.AVAILABLE_TEAMS_BUTTON.equals(commandLocalVariableValue)) {
            handleAvailableTeams();
        } else if (CreateLeagueView.BACK.equals(commandLocalVariableValue)) {
            navigatorFieldReference.show(AppNavigator.ADMIN_MENU);
        }
    }


    /**
     * Devuelve el liga nombre.
     *
     * @return el liga nombre.
     */
    public String getLeagueName() { return createLeagueReferenceViewInterfaceFieldReference.getLeagueName(); }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public String getStartDate() { return createLeagueReferenceViewInterfaceFieldReference.getDate(); }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public String getStartHour() { return createLeagueReferenceViewInterfaceFieldReference.getStartTime(); }


    /**
     * Gestiona esta operacion.
     */
    private void handleAvailableTeams() {
        pendingLeagueNameFieldReference = getLeagueName();
        pendingStartDateFieldReference = getStartDate();
        pendingStartHourFieldReference = getStartHour();

        if (pendingLeagueNameFieldReference == null || pendingLeagueNameFieldReference.trim().isEmpty()) {
            createLeagueReferenceViewInterfaceFieldReference.showMessageDialog("League name is required.");
            return;
        }
        ArrayList<League> leaguesLocalVariableValue = leagueReferenceManagerServiceFieldReference.getAllLeagues();
        if (leagueReferenceManagerServiceFieldReference.leagueExist(leaguesLocalVariableValue, pendingLeagueNameFieldReference)) {
            createLeagueReferenceViewInterfaceFieldReference.showMessageDialog("This league already exists.");
            return;
        }
        if (leagueReferenceManagerServiceFieldReference.checkDateStatus(pendingStartDateFieldReference) != 0) {
            createLeagueReferenceViewInterfaceFieldReference.showMessageDialog("The date is invalid or already in the past.");
            return;
        }
        if (leagueReferenceManagerServiceFieldReference.checkTimeStatus(pendingStartHourFieldReference, pendingStartDateFieldReference) != 0) {
            createLeagueReferenceViewInterfaceFieldReference.showMessageDialog("The start time is invalid or already in the past.");
            return;
        }

        ArrayList<Team> availableTeamsLocalVariableValue = teamReferenceManagerServiceFieldReference.getAllTeams();
        if (teamReferenceSelectionControllerHandlerFieldReference != null) {
            teamReferenceSelectionControllerHandlerFieldReference.prepareLeagueCreation(
                    pendingLeagueNameFieldReference,
                    pendingStartDateFieldReference,
                    pendingStartHourFieldReference,
                    availableTeamsLocalVariableValue
            );
        }
        navigatorFieldReference.show(AppNavigator.TEAM_SELECTION);
    }
}


