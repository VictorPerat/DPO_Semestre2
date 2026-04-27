package presentation.ControllerViews;

import bussines.managers.LeagueManager;
import bussines.managers.PlayerManager;
import bussines.managers.TeamManager;
import bussines.managers.TeamInfoManager;
import bussines.objects.League;
import bussines.objects.Player;
import bussines.objects.Team;
import bussines.objects.TeamInfo;
import presentation.AppNavigator;
import presentation.Views.StatisticsGraphView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador encargado de gestionar la vista {@link StatisticsGraphView},
 * mostrando los datos estadísticos de las ligas disponibles.
 *
 * Maneja las interacciones del usuario con la interfaz de estadísticas,
 * carga los datos desde la base de datos utilizando {@link LeagueManager},
 * y construye los datos de puntos por equipo para ser mostrados en una gráfica.
 *
 * También permite acceder a la configuración del usuario y cerrar sesión.
 */
public class StatisticsGraphController implements ActionListener {
    private final StatisticsGraphView viewInterfaceFieldReference;
    private final AdminMenuController menuControllerHandlerFieldReference;
    private final List<League> leaguesFieldReference;
    private final TeamInfoManager teamReferenceManagerServiceFieldReference;
    private TeamManager realTeamReferenceManagerServiceFieldReference;
    private PlayerManager playerProfileManagerServiceFieldReference;
    private LeagueManager leagueReferenceManagerServiceFieldReference;

    /**
     * Constructor del controlador.
     *
     * @param view             Vista de estadísticas.
     * @param menuController   Controlador del menú principal.
     * @param playerManager    Gestor de jugador actual.
     */
    public StatisticsGraphController(StatisticsGraphView viewInterfaceParameterValue, AdminMenuController menuControllerHandlerParameterValue, PlayerManager playerProfileManagerServiceParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.menuControllerHandlerFieldReference = menuControllerHandlerParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.leagueReferenceManagerServiceFieldReference = new LeagueManager();
        this.realTeamReferenceManagerServiceFieldReference =  new TeamManager();
        this.viewInterfaceFieldReference.registerController(this);
        if (playerProfileManagerServiceParameterValue.getCurrentIdentifier().equals("admin")) {
            this.leaguesFieldReference = leagueReferenceManagerServiceFieldReference.getAllLeagues(); // Obtenemos las ligas de la BD
        }
        else {
            Player actualPlayerProfileLocalVariableValue = playerProfileManagerServiceParameterValue.getCurrentPlayer();
            this.leaguesFieldReference = leagueReferenceManagerServiceFieldReference.getLeaguesByUserTeam(actualPlayerProfileLocalVariableValue.getTeam());
        }
        this.teamReferenceManagerServiceFieldReference = new TeamInfoManager();

        viewInterfaceParameterValue.setLeagues(leaguesFieldReference, this);  // Establecemos las ligas en la vista
    }

    /**
     * Maneja los eventos de acción realizados en la vista.
     *
     * @param e Evento de acción disparado.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();

        switch (commandLocalVariableValue) {
            case "BACK":
                viewInterfaceFieldReference.dispose();
                break;
            case "CONFIG":
                showConfigDialog();
                break;
            default:
                if (commandLocalVariableValue.startsWith("LEAGUE_")) {
                    int indexLocalVariableValue = Integer.parseInt(commandLocalVariableValue.split("_")[1]);
                    League selectedLeagueReferenceLocalVariableValue = leaguesFieldReference.get(indexLocalVariableValue);
                    showLeagueData(selectedLeagueReferenceLocalVariableValue.getId(), selectedLeagueReferenceLocalVariableValue.getName());
                }
                break;
        }
    }

    /**
     * Muestra los datos de una liga específica generando los puntos por semana
     * para cada equipo y actualizando la gráfica en la vista.
     *
     * @param leagueId   ID de la liga seleccionada.
     * @param leagueName Nombre de la liga seleccionada.
     */
    public void showLeagueData(int leagueReferenceIdentifierParameterValue, String leagueReferenceDisplayNameParameterValue) {
        ArrayList<TeamInfo> teamsLocalVariableValue = teamReferenceManagerServiceFieldReference.getInfoTeamsOfLeague(leagueReferenceIdentifierParameterValue);
        ArrayList<Team> teamsListLocalVariableValue = realTeamReferenceManagerServiceFieldReference.getAllTeams();
        ArrayList<Team> matchingTeamsLocalVariableValue = new ArrayList<>();

        for (TeamInfo informationLocalVariableValue : teamsLocalVariableValue) {
            int informationTeamReferenceIdentifierLocalVariableValue = informationLocalVariableValue.getTeamId();

            for (Team teamReferenceLocalVariableValue : teamsListLocalVariableValue) {
                if (teamReferenceLocalVariableValue.getId() == informationTeamReferenceIdentifierLocalVariableValue) {
                    matchingTeamsLocalVariableValue.add(teamReferenceLocalVariableValue);
                    break; // Si no puede haber más de un match, esto mejora eficiencia
                }
            }
        }

        int[] teamReferenceIdentifierLocalVariableValue = new int[100];
        int countIdentifiersLocalVariableValue = 0;
        int numberWeeksLocalVariableValue = 6; // weeks hardcodeadas

        String[] teamReferenceNamesLocalVariableValue = new String[teamsLocalVariableValue.size()];
        int[][] teamReferenceDataLocalVariableValue = new int[teamsLocalVariableValue.size()][numberWeeksLocalVariableValue];

        for (int indexCounterLocalVariableValue = 0; indexCounterLocalVariableValue < teamsLocalVariableValue.size(); indexCounterLocalVariableValue++) {
            int identifierLocalVariableValue = teamsLocalVariableValue.get(indexCounterLocalVariableValue).getTeamId();

            // Buscar el nombre del equipo que coincide con ese ID
            String displayNameLocalVariableValue = "";
            for (Team teamReferenceLocalVariableValue2 : teamsListLocalVariableValue) {
                if (teamReferenceLocalVariableValue2.getId() == identifierLocalVariableValue) {
                    displayNameLocalVariableValue = teamReferenceLocalVariableValue2.getName(); // o getTeamName() si se llama así
                    break;
                }
            }
            teamReferenceNamesLocalVariableValue[indexCounterLocalVariableValue] = displayNameLocalVariableValue; // Guardar el nombre correspondiente
            countIdentifiersLocalVariableValue++;
            int cumulativePointsLocalVariableValue = teamsLocalVariableValue.get(indexCounterLocalVariableValue).getPoints();
            int pointsPerWeekLocalVariableValue = cumulativePointsLocalVariableValue / numberWeeksLocalVariableValue;
            int remainderLocalVariableValue = cumulativePointsLocalVariableValue % numberWeeksLocalVariableValue;

            for (int secondaryIndexCounterLocalVariableValue = 0; secondaryIndexCounterLocalVariableValue < numberWeeksLocalVariableValue; secondaryIndexCounterLocalVariableValue++) {
                teamReferenceDataLocalVariableValue[indexCounterLocalVariableValue][secondaryIndexCounterLocalVariableValue] = pointsPerWeekLocalVariableValue * (secondaryIndexCounterLocalVariableValue + 1);
                if (secondaryIndexCounterLocalVariableValue < remainderLocalVariableValue) teamReferenceDataLocalVariableValue[indexCounterLocalVariableValue][secondaryIndexCounterLocalVariableValue] += secondaryIndexCounterLocalVariableValue + 1;
            }
        }

        viewInterfaceFieldReference.updateChartData(leagueReferenceDisplayNameParameterValue, teamReferenceDataLocalVariableValue, numberWeeksLocalVariableValue, teamReferenceNamesLocalVariableValue, countIdentifiersLocalVariableValue);

        System.out.println("Data for the league: " + leagueReferenceDisplayNameParameterValue);
        for (int indexCounterLocalVariableValue2 = 0; indexCounterLocalVariableValue2 < teamReferenceDataLocalVariableValue.length; indexCounterLocalVariableValue2++) {
            System.out.print("Team " + indexCounterLocalVariableValue2 + ": ");
            for (int secondaryIndexCounterLocalVariableValue2 = 0; secondaryIndexCounterLocalVariableValue2 < teamReferenceDataLocalVariableValue[indexCounterLocalVariableValue2].length; secondaryIndexCounterLocalVariableValue2++) {
                System.out.print(teamReferenceDataLocalVariableValue[indexCounterLocalVariableValue2][secondaryIndexCounterLocalVariableValue2] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Muestra el diálogo de configuración (logout, eliminar cuenta, cambiar contraseña).
     */
    public void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = Rounded.ConfigDialog.getInstance(viewInterfaceFieldReference);
        configDialogLocalVariableValue.registerController(eventArgumentParameterValue2 -> {
            configDialogLocalVariableValue.setVisible(false);
            handleConfigAction(eventArgumentParameterValue2.getActionCommand());
        });
        configDialogLocalVariableValue.setVisible(true);
    }

    /**
     * Maneja las acciones seleccionadas en el diálogo de configuración.
     *
     * @param action Acción seleccionada (logout, delete, change password).
     */
    private void handleConfigAction(String actionParameterValue) { //totalmente inhabilitado
        switch(actionParameterValue) {
            case Rounded.ConfigDialog.LOGOUT:
                //handleLogout();
                break;
            case Rounded.ConfigDialog.DELETE_ACCOUNT:
                //handleDeleteAccount();
                break;
            case Rounded.ConfigDialog.CHANGE_PASSWORD:
                //openChangePasswordView();
                break;
        }
    }

    /**
     * Cierra sesión y vuelve al menú principal.
     */
    private void handleLogout() {
        Rounded.ConfigDialog.closeInstance();
        viewInterfaceFieldReference.dispose();
        menuControllerHandlerFieldReference.handleLogout();
    }

    /**
     * Solicita confirmación al usuario para eliminar su cuenta. Si acepta,
     * muestra un mensaje y cierra la sesión.
     */
    private void handleDeleteAccount() {
        int confirmLocalVariableValue = JOptionPane.showConfirmDialog(
                viewInterfaceFieldReference,
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmLocalVariableValue == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(viewInterfaceFieldReference, "Account deleted successfully");
            handleLogout();
        }
    }

    /**
     * Abre la vista para cambiar la contraseña a través del navegador,
     * registrando el retorno a la vista de estadísticas al finalizar.
     */
    private void openChangePasswordView() {
        final StatisticsGraphView previousScreenLocalVariableValue =
                viewInterfaceFieldReference;
        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();

        previousScreenLocalVariableValue.setVisible(false);
        navigatorLocalVariableValue.setChangePasswordReturnAction(() -> {
            navigatorLocalVariableValue.hideMainWindow();
            previousScreenLocalVariableValue.setVisible(true);
        });
        navigatorLocalVariableValue.show(AppNavigator.CHANGE_PASSWORD);
    }

}
