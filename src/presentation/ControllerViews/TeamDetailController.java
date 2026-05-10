package presentation.ControllerViews;

import bussines.objects.Player;
import presentation.AppNavigator;
import presentation.Views.TeamDetailView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Coordina la pantalla del equipo detalle.
 */
public class TeamDetailController implements ActionListener {
    private final TeamDetailView viewInterfaceFieldReference;
    private final AppNavigator navigatorFieldReference;


    /**
     * Crea una instancia de el equipo detalle.
     *
     * @param viewInterfaceParameterValue vista que usa la operacion.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
    public TeamDetailController(TeamDetailView viewInterfaceParameterValue, AppNavigator navigatorParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.viewInterfaceFieldReference.registerController(this);
    }


    /**
     * Abre el equipo.
     *
     * @param teamReferenceDisplayNameParameterValue nombre del equipo.
     * @param playersParameterValue jugadores que usa la operacion.
     */
    public void openTeam(String teamReferenceDisplayNameParameterValue, ArrayList<Player> playersParameterValue) {
        viewInterfaceFieldReference.loadTeam(teamReferenceDisplayNameParameterValue, playersParameterValue);
        navigatorFieldReference.show(AppNavigator.TEAM_DETAIL);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param eventArgumentParameterValue dato de entrada de la operacion.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        if (TeamDetailView.BACK.equals(eventArgumentParameterValue.getActionCommand())) {
            navigatorFieldReference.show(AppNavigator.LEAGUE_DETAIL);
        } else if (TeamDetailView.CONFIG.equals(eventArgumentParameterValue.getActionCommand())) {
            showConfigDialog();
        }
    }


    /**
     * Muestra el configuracion dialogo.
     */
    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue =
                Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        presentation.AccountSettingsWidgetService.configureDialogForCurrentSession(
                configDialogLocalVariableValue,
                AppNavigator.TEAM_DETAIL
        );
        configDialogLocalVariableValue.setVisible(true);
    }
}


