package presentation.ControllerViews;

import bussines.objects.Player;
import presentation.AppNavigator;
import presentation.Views.TeamDetailView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** Controlador del detalle de equipo. */
public class TeamDetailController implements ActionListener {
    private final TeamDetailView viewInterfaceFieldReference;
    private final AppNavigator navigatorFieldReference;

    public TeamDetailController(TeamDetailView viewInterfaceParameterValue, AppNavigator navigatorParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.viewInterfaceFieldReference.registerController(this);
    }

    public TeamDetailController(TeamDetailView viewInterfaceParameterValue, TeamDetailView previousViewInterfaceParameterValue) {
        this(viewInterfaceParameterValue, AppNavigator.getInstance());
    }

    public void openTeam(String teamReferenceDisplayNameParameterValue, ArrayList<Player> playersParameterValue) {
        viewInterfaceFieldReference.loadTeam(teamReferenceDisplayNameParameterValue, playersParameterValue);
        navigatorFieldReference.show(AppNavigator.TEAM_DETAIL);
    }

    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        if (TeamDetailView.BACK.equals(eventArgumentParameterValue.getActionCommand())) {
            navigatorFieldReference.show(AppNavigator.LEAGUE_DETAIL);
        } else if (TeamDetailView.CONFIG.equals(eventArgumentParameterValue.getActionCommand())) {
            showConfigDialog();
        }
    }

    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        configDialogLocalVariableValue.registerController(eventArgumentParameterValue -> {
            configDialogLocalVariableValue.dispose();
            if (Rounded.ConfigDialog.LOGOUT.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.show(AppNavigator.LOGIN);
            } else if (Rounded.ConfigDialog.CHANGE_PASSWORD.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.setChangePasswordReturnAction(() -> navigatorFieldReference.show(AppNavigator.TEAM_DETAIL));
                navigatorFieldReference.show(AppNavigator.CHANGE_PASSWORD);
            }
        });
        configDialogLocalVariableValue.setBackButtonListener(eventArgumentParameterValue -> configDialogLocalVariableValue.dispose());
        configDialogLocalVariableValue.setVisible(true);
    }
}
