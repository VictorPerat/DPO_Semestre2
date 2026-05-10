package presentation.ControllerViews;

import bussines.managers.TeamManager;
import presentation.AppNavigator;
import presentation.Views.DeleteTeamView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Coordina la pantalla del equipo.
 */
public class DeleteTeamController implements ActionListener {
    private final DeleteTeamView viewInterfaceFieldReference;
    private final TeamManager teamReferenceManagerServiceFieldReference = new TeamManager();
    private final AppNavigator navigatorFieldReference;


    /**
     * Crea una instancia de el equipo.
     *
     * @param viewInterfaceParameterValue vista que usa la operacion.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
    public DeleteTeamController(DeleteTeamView viewInterfaceParameterValue, AppNavigator navigatorParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.viewInterfaceFieldReference.setController(this);
    }


    /**
     * Gestiona esta operacion.
     */
    public void refreshTeams() { viewInterfaceFieldReference.refreshTeamsList(); }


    /**
     * Gestiona esta operacion.
     *
     * @param eventArgumentParameterValue dato de entrada de la operacion.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();
        if ("BACK".equals(commandLocalVariableValue)) {
            navigatorFieldReference.show(AppNavigator.ADMIN_MENU);
        } else if ("DELETE_TEAMS".equals(commandLocalVariableValue)) {
            deleteSelectedTeams();
        }
    }


    /**
     * Elimina los equipos.
     */
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
}


