package presentation.ControllerViews;

import bussines.managers.TeamManager;
import presentation.AppNavigator;
import presentation.Views.DeleteTeamView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** Controlador para borrar equipos desde CardLayout. */
public class DeleteTeamController implements ActionListener {
    private final DeleteTeamView viewInterfaceFieldReference;
    private final TeamManager teamReferenceManagerServiceFieldReference = new TeamManager();
    private final AppNavigator navigatorFieldReference;

    public DeleteTeamController(DeleteTeamView viewInterfaceParameterValue, AppNavigator navigatorParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.viewInterfaceFieldReference.setConfigController(this);
    }

    public DeleteTeamController(DeleteTeamView viewInterfaceParameterValue, AdminMenuController adminMenuControllerHandlerParameterValue) {
        this(viewInterfaceParameterValue, AppNavigator.getInstance());
    }

    public void refreshTeams() { viewInterfaceFieldReference.refreshTeamsList(); }

    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();
        if ("BACK".equals(commandLocalVariableValue)) {
            navigatorFieldReference.show(AppNavigator.ADMIN_MENU);
        } else if ("CONFIG".equals(commandLocalVariableValue)) {
            showConfigDialog();
        } else if ("DELETE_TEAMS".equals(commandLocalVariableValue)) {
            deleteSelectedTeams();
        }
    }

    private void deleteSelectedTeams() {
        ArrayList<String> selectedTeamsLocalVariableValue = viewInterfaceFieldReference.getSelectedTeams();
        if (selectedTeamsLocalVariableValue.isEmpty()) {
            viewInterfaceFieldReference.showMessageDialog("No teams selected!");
            return;
        }
        if (viewInterfaceFieldReference.confirmDeleteTeams(selectedTeamsLocalVariableValue.size()) == 0) {
            String resultLocalVariableValue = teamReferenceManagerServiceFieldReference.deleteTeams(selectedTeamsLocalVariableValue);
            viewInterfaceFieldReference.showDeletionResult(resultLocalVariableValue);
            refreshTeams();
        }
    }

    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        configDialogLocalVariableValue.registerController(eventArgumentParameterValue -> {
            configDialogLocalVariableValue.dispose();
            if (Rounded.ConfigDialog.LOGOUT.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.show(AppNavigator.LOGIN);
            } else if (Rounded.ConfigDialog.CHANGE_PASSWORD.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.setChangePasswordReturnAction(() -> navigatorFieldReference.show(AppNavigator.DELETE_TEAM));
                navigatorFieldReference.show(AppNavigator.CHANGE_PASSWORD);
            }
        });
        configDialogLocalVariableValue.setBackButtonListener(eventArgumentParameterValue -> configDialogLocalVariableValue.dispose());
        configDialogLocalVariableValue.setVisible(true);
    }
}
