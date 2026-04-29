package presentation.ControllerViews;

import bussines.managers.LeagueManager;
import bussines.managers.TeamManager;
import bussines.objects.League;
import bussines.objects.Team;
import presentation.AppNavigator;
import presentation.Views.CreateLeagueView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** Controlador del formulario de creación de liga. */
public class CreateLeagueController implements ActionListener {
    private final CreateLeagueView createLeagueReferenceViewInterfaceFieldReference;
    private final LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();
    private final TeamManager teamReferenceManagerServiceFieldReference = new TeamManager();
    private final AppNavigator navigatorFieldReference;
    private TeamSelectionController teamReferenceSelectionControllerHandlerFieldReference;
    private String pendingLeagueNameFieldReference;
    private String pendingStartDateFieldReference;
    private String pendingStartHourFieldReference;

    public CreateLeagueController(CreateLeagueView createLeagueReferenceViewInterfaceParameterValue,
                                  AppNavigator navigatorParameterValue,
                                  TeamSelectionController teamSelectionControllerParameterValue) {
        this.createLeagueReferenceViewInterfaceFieldReference = createLeagueReferenceViewInterfaceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.teamReferenceSelectionControllerHandlerFieldReference = teamSelectionControllerParameterValue;
        this.createLeagueReferenceViewInterfaceFieldReference.registerController(this);
        this.createLeagueReferenceViewInterfaceFieldReference.setConfigController(this);
    }

    public CreateLeagueController(CreateLeagueView createLeagueReferenceViewInterfaceParameterValue,
                                  AdminMenuController adminMenuControllerHandlerParameterValue) {
        this(createLeagueReferenceViewInterfaceParameterValue, AppNavigator.getInstance(), null);
    }

    public void setTeamSelectionController(TeamSelectionController controllerParameterValue) {
        this.teamReferenceSelectionControllerHandlerFieldReference = controllerParameterValue;
    }

    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();
        if (CreateLeagueView.AVAILABLE_TEAMS_BUTTON.equals(commandLocalVariableValue)) {
            handleAvailableTeams();
        } else if (CreateLeagueView.BACK.equals(commandLocalVariableValue)) {
            navigatorFieldReference.show(AppNavigator.ADMIN_MENU);
        } else if (CreateLeagueView.CONFIG.equals(commandLocalVariableValue) || "CONFIG".equals(commandLocalVariableValue)) {
            showConfigDialog();
        }
    }

    public String getLeagueName() { return createLeagueReferenceViewInterfaceFieldReference.getLeagueName(); }
    public String getStartDate() { return createLeagueReferenceViewInterfaceFieldReference.getDate(); }
    public String getStartHour() { return createLeagueReferenceViewInterfaceFieldReference.getStartTime(); }

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

    public void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        configDialogLocalVariableValue.registerController(eventArgumentParameterValue -> {
            configDialogLocalVariableValue.dispose();
            if (Rounded.ConfigDialog.LOGOUT.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.show(AppNavigator.LOGIN);
            } else if (Rounded.ConfigDialog.CHANGE_PASSWORD.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.setChangePasswordReturnAction(() -> navigatorFieldReference.show(AppNavigator.CREATE_LEAGUE));
                navigatorFieldReference.show(AppNavigator.CHANGE_PASSWORD);
            }
        });
        configDialogLocalVariableValue.setBackButtonListener(eventArgumentParameterValue -> configDialogLocalVariableValue.dispose());
        configDialogLocalVariableValue.setVisible(true);
    }
}
