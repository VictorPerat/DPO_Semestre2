package presentation.ControllerViews;

import bussines.managers.LeagueManager;
import bussines.objects.League;
import presentation.AppNavigator;
import presentation.Views.DeleteLeagueView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Coordina la pantalla del liga.
 */
public class DeleteLeagueController implements ActionListener {
    private final DeleteLeagueView viewInterfaceFieldReference;
    private final LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();
    private final AppNavigator navigatorFieldReference;


    /**
     * Crea una instancia de el liga.
     *
     * @param viewInterfaceParameterValue vista que usa la operacion.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
    public DeleteLeagueController(DeleteLeagueView viewInterfaceParameterValue, AppNavigator navigatorParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.viewInterfaceFieldReference.setController(this);
        refreshLeagues();
    }


    /**
     * Gestiona esta operacion.
     */
    public void refreshLeagues() {
        ArrayList<League> leaguesLocalVariableValue = leagueReferenceManagerServiceFieldReference.getAllLeagues();
        viewInterfaceFieldReference.loadLeagues(leaguesLocalVariableValue);
    }


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
        } else if ("DELETE_LEAGUES".equals(commandLocalVariableValue)) {
            deleteSelectedLeagues();
        }
    }


    /**
     * Elimina los ligas.
     */
    private void deleteSelectedLeagues() {
        ArrayList<String> selectedLeaguesLocalVariableValue = viewInterfaceFieldReference.getSelectedLeagues();
        if (selectedLeaguesLocalVariableValue.isEmpty()) {
            viewInterfaceFieldReference.showMessageDialog("No leagues selected!");
            return;
        }
        if (viewInterfaceFieldReference.confirmDelete(selectedLeaguesLocalVariableValue) == 0) {
            String resultLocalVariableValue = leagueReferenceManagerServiceFieldReference.deleteLeague(selectedLeaguesLocalVariableValue);
            viewInterfaceFieldReference.messageDelete(resultLocalVariableValue);
            refreshLeagues();
        }
    }
}


