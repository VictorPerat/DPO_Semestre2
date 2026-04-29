package presentation.ControllerViews;

import bussines.managers.LeagueManager;
import bussines.objects.League;
import presentation.AppNavigator;
import presentation.Views.DeleteLeagueView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** Controlador para borrar ligas desde CardLayout. */
public class DeleteLeagueController implements ActionListener {
    private final DeleteLeagueView viewInterfaceFieldReference;
    private final LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();
    private final AppNavigator navigatorFieldReference;

    public DeleteLeagueController(DeleteLeagueView viewInterfaceParameterValue, AppNavigator navigatorParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.viewInterfaceFieldReference.setConfigController(this);
        refreshLeagues();
    }

    public DeleteLeagueController(DeleteLeagueView viewInterfaceParameterValue, AdminMenuController adminMenuControllerHandlerParameterValue) {
        this(viewInterfaceParameterValue, AppNavigator.getInstance());
    }

    public void refreshLeagues() {
        ArrayList<League> leaguesLocalVariableValue = leagueReferenceManagerServiceFieldReference.getAllLeagues();
        viewInterfaceFieldReference.loadLeagues(leaguesLocalVariableValue);
    }

    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();
        if ("BACK".equals(commandLocalVariableValue)) {
            navigatorFieldReference.show(AppNavigator.ADMIN_MENU);
        } else if ("CONFIG".equals(commandLocalVariableValue)) {
            showConfigDialog();
        } else if ("DELETE_LEAGUES".equals(commandLocalVariableValue)) {
            deleteSelectedLeagues();
        }
    }

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

    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        configDialogLocalVariableValue.registerController(eventArgumentParameterValue -> {
            configDialogLocalVariableValue.dispose();
            if (Rounded.ConfigDialog.LOGOUT.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.show(AppNavigator.LOGIN);
            } else if (Rounded.ConfigDialog.CHANGE_PASSWORD.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.setChangePasswordReturnAction(() -> navigatorFieldReference.show(AppNavigator.DELETE_LEAGUE));
                navigatorFieldReference.show(AppNavigator.CHANGE_PASSWORD);
            }
        });
        configDialogLocalVariableValue.setBackButtonListener(eventArgumentParameterValue -> configDialogLocalVariableValue.dispose());
        configDialogLocalVariableValue.setVisible(true);
    }
}
