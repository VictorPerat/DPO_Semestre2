package presentation.ControllerViews;

import bussines.managers.GameManager;
import bussines.managers.PlayerManager;
import bussines.managers.TeamManager;
import bussines.managers.TeamInfoManager;
import bussines.objects.League;
import bussines.objects.Player;
import bussines.objects.Team;
import bussines.objects.TeamInfo;
import presentation.AppNavigator;
import presentation.Views.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Controlador para la vista de detalle de una liga.
 * Gestiona las interacciones del usuario con la pantalla de detalles de liga,
 * como mostrar estadísticas, calendario, configuración, y gestionar sesión.
 */
public class LeagueDetailController implements ActionListener {

    /** Vista de detalle de liga */
    private LeagueDetailView viewInterfaceFieldReference;

    /** Controlador del menú administrador */
    private AdminMenuController adminMenuControllerHandlerFieldReference;

    /** Controlador del menú jugador */
    private PlayerMenuController playerProfileMenuControllerHandlerFieldReference;

    /** Gestor de jugadores */
    private final PlayerManager playerProfileManagerServiceFieldReference;

    /** Gestor de juegos */
    private final GameManager gameEntityManagerServiceFieldReference;

    /** Liga actual cuyo detalle se muestra */
    private final League leagueReferenceFieldReference;

    /** Gestor de información de equipos */
    private final TeamInfoManager informationTeamReferenceManagerServiceFieldReference;

    /** Gestor de equipos */
    private final TeamManager teamReferenceManagerServiceFieldReference;
    private static int flagFieldReference = 0;

    /**
     * Constructor que inicializa el controlador con la vista y los gestores necesarios.
     *
     * @param view Vista de detalle de liga
     * @param adminMenuController Controlador del menú administrador
     * @param playerMenuController Controlador del menú jugador
     * @param playerManager Gestor de jugadores
     * @param gameManager Gestor de juegos
     * @param league Liga cuyo detalle se muestra
     * @param infoTeamManager Gestor de información de equipos
     * @param teamManager Gestor de equipos
     */
    public LeagueDetailController(LeagueDetailView viewInterfaceParameterValue, AdminMenuController adminMenuControllerHandlerParameterValue, PlayerMenuController playerProfileMenuControllerHandlerParameterValue, PlayerManager playerProfileManagerServiceParameterValue, GameManager gameEntityManagerServiceParameterValue, League leagueReferenceParameterValue, TeamInfoManager informationTeamReferenceManagerServiceParameterValue, TeamManager teamReferenceManagerServiceParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.adminMenuControllerHandlerFieldReference = adminMenuControllerHandlerParameterValue;
        this.playerProfileMenuControllerHandlerFieldReference = playerProfileMenuControllerHandlerParameterValue;
        this.viewInterfaceFieldReference.registerController(this);
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.gameEntityManagerServiceFieldReference = gameEntityManagerServiceParameterValue;
        this.leagueReferenceFieldReference = leagueReferenceParameterValue;
        this.informationTeamReferenceManagerServiceFieldReference = informationTeamReferenceManagerServiceParameterValue;
        this.teamReferenceManagerServiceFieldReference = teamReferenceManagerServiceParameterValue;
    }

    public static int getFlag() {
        return flagFieldReference;
    }
    /**
     * Gestiona las acciones de la vista según el comando recibido.
     * Comandos manejados incluyen configuración, volver atrás, mostrar estadísticas y mostrar calendario.
     *
     * @param e Evento de acción
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();

        switch (commandLocalVariableValue) {
            case LeagueDetailView.CONFIG:
                showConfigDialog();
                flagFieldReference = 0;
                break;

            case LeagueDetailView.BACK:
                viewInterfaceFieldReference.dispose();
                flagFieldReference = 1;
                break;

            case "Show Stats":
                flagFieldReference = 0;
                viewInterfaceFieldReference.dispose();
                StatisticsGraphView statsViewInterfaceLocalVariableValue = new StatisticsGraphView();
                statsViewInterfaceLocalVariableValue.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent eventArgumentParameterValue2) {
                            viewInterfaceFieldReference.setVisible(true);
                    }
                });

                new StatisticsGraphController(statsViewInterfaceLocalVariableValue, adminMenuControllerHandlerFieldReference, playerProfileManagerServiceFieldReference);
                statsViewInterfaceLocalVariableValue.setVisible(true);
                break;

            case "Show Calendar":
                flagFieldReference = 0;
                viewInterfaceFieldReference.dispose();
                ArrayList<String> leagueReferenceTeamsLocalVariableValue = new ArrayList<>();
                ArrayList<TeamInfo> infoteamsLocalVariableValue = informationTeamReferenceManagerServiceFieldReference.getInfoTeamsOfLeague(leagueReferenceFieldReference.getId());
                for (TeamInfo informationTeamReferenceLocalVariableValue : infoteamsLocalVariableValue) {
                    for (Team teamReferenceLocalVariableValue : teamReferenceManagerServiceFieldReference.getAllTeams()){
                        if (teamReferenceLocalVariableValue.getId() == informationTeamReferenceLocalVariableValue.getTeamId()){
                            leagueReferenceTeamsLocalVariableValue.add(teamReferenceLocalVariableValue.getName());
                        }
                    }
                }
                CalendarView calendarScreenInterfaceLocalVariableValue = new CalendarView(
                        leagueReferenceTeamsLocalVariableValue,
                        gameEntityManagerServiceFieldReference.getGamesByLeague(leagueReferenceFieldReference.getId())
                );
                calendarScreenInterfaceLocalVariableValue.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent eventArgumentParameterValue3) {
                                viewInterfaceFieldReference.setVisible(true);

                    }
                });
                CalendarController controllerHandlerLocalVariableValue = new CalendarController(calendarScreenInterfaceLocalVariableValue, adminMenuControllerHandlerFieldReference);
                calendarScreenInterfaceLocalVariableValue.registerController(controllerHandlerLocalVariableValue);
                calendarScreenInterfaceLocalVariableValue.setVisible(true);

                break;

            case "Show Team Info":
                Object sourceLocalVariableValue = eventArgumentParameterValue.getSource();
                if (sourceLocalVariableValue instanceof String) {
                    String teamName = (String) sourceLocalVariableValue;
                    viewInterfaceFieldReference.setVisible(false);
                    ArrayList<Player> playersLocalVariableValue = playerProfileManagerServiceFieldReference.getPlayersByTeam(teamName);
                    TeamDetailView teamReferenceDetailViewInterfaceLocalVariableValue = new TeamDetailView(teamName, playersLocalVariableValue);
                    TeamDetailController teamReferenceDetailControllerHandlerLocalVariableValue = new TeamDetailController(teamReferenceDetailViewInterfaceLocalVariableValue, viewInterfaceFieldReference);
                    teamReferenceDetailViewInterfaceLocalVariableValue.registerController(teamReferenceDetailControllerHandlerLocalVariableValue);
                    teamReferenceDetailViewInterfaceLocalVariableValue.setVisible(true);
                }
                break;

            default:
                System.out.println("Unknown command: " + commandLocalVariableValue);
        }
    }

    /**
     * Muestra el diálogo de configuración con opciones como cerrar sesión, eliminar cuenta o cambiar contraseña.
     */
    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = new Rounded.ConfigDialog(viewInterfaceFieldReference);
        configDialogLocalVariableValue.registerController(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgumentParameterValue4) {
                configDialogLocalVariableValue.dispose();
                handleConfigAction(eventArgumentParameterValue4.getActionCommand());
            }
        });
        configDialogLocalVariableValue.setVisible(true);
    }

    /**
     * Maneja las acciones seleccionadas dentro del diálogo de configuración.
     *
     * @param action Acción seleccionada en el diálogo de configuración
     */
    private void handleConfigAction(String actionParameterValue) {
        switch (actionParameterValue) {
            case Rounded.ConfigDialog.LOGOUT:
                handleLogout(playerProfileManagerServiceFieldReference.getCurrentIdentifier());
                break;
            case Rounded.ConfigDialog.DELETE_ACCOUNT:
                handleDeleteAccount();

                break;
            case Rounded.ConfigDialog.CHANGE_PASSWORD:
                openChangePasswordView();
                break;
        }
    }


    /**
     * Cierra la vista actual y redirige al menú correspondiente según el tipo de usuario.
     * @param identifier identificador del usuario.
     */
    private void handleLogout(String identifierParameterValue) {
        viewInterfaceFieldReference.dispose();
        if ("admin".equals(identifierParameterValue)) {
            adminMenuControllerHandlerFieldReference.handleLogout();
        } else {
            playerProfileMenuControllerHandlerFieldReference.handleLogout();
        }
    }

    /**
     * Abre la vista para cambiar la contraseña a través del navegador,
     * registrando el retorno a la vista de detalle de liga al finalizar.
     */
    private void openChangePasswordView() {
        final LeagueDetailView previousScreenLocalVariableValue =
                viewInterfaceFieldReference;
        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();

        previousScreenLocalVariableValue.setVisible(false);
        navigatorLocalVariableValue.setChangePasswordReturnAction(() -> {
            navigatorLocalVariableValue.hideMainWindow();
            previousScreenLocalVariableValue.setVisible(true);
        });
        navigatorLocalVariableValue.show(AppNavigator.CHANGE_PASSWORD);
    }

    /**
     * Muestra un diálogo de confirmación para eliminar la cuenta actual.
     * Si el usuario confirma, se elimina la cuenta y se realiza el logout.
     */
    private void handleDeleteAccount() {
        int confirmLocalVariableValue = JOptionPane.showConfirmDialog(
                viewInterfaceFieldReference,
                "Are you sure you want to delete your account?\nThis action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmLocalVariableValue == JOptionPane.YES_OPTION) {
            String currentIdentifierLocalVariableValue = playerProfileManagerServiceFieldReference.getCurrentIdentifier();

            if ("admin".equals(currentIdentifierLocalVariableValue)) {
                // Si es admin, no eliminar cuenta, solo hacer logout
                adminMenuControllerHandlerFieldReference.handleLogout();
                viewInterfaceFieldReference.dispose();
                return;
            }

            // Si no es admin, borrar cuenta y logout
            boolean deletedLocalVariableValue = playerProfileManagerServiceFieldReference.deleteCurrentPlayer();

            if (deletedLocalVariableValue) {
                JOptionPane.showMessageDialog(viewInterfaceFieldReference, "Account deleted successfully.");
                handleLogout(currentIdentifierLocalVariableValue);
            } else {
                JOptionPane.showMessageDialog(viewInterfaceFieldReference, "Error deleting account.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }}
