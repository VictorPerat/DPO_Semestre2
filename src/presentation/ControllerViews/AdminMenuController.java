package presentation.ControllerViews;

import bussines.managers.GameManager;
import bussines.objects.League;
import bussines.managers.LeagueManager;
import bussines.managers.PlayerManager;
import presentation.Views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador principal para el menú de administrador.
 * Gestiona todas las acciones disponibles para un administrador del sistema.
 */
public class AdminMenuController implements ActionListener, DeletePlayerListener, MenuController, LeagueViewActions {

    private AdminMenuView adminMenuViewInterfaceFieldReference;
    private CreateLeagueView createLeagueReferenceViewInterfaceFieldReference;
    private AvailableLeaguesView availableLeaguesViewInterfaceFieldReference;
    private DeleteLeagueView deleteLeagueReferenceViewInterfaceFieldReference;
    private DeletePlayerView deletePlayerProfileViewInterfaceFieldReference;
    private DeleteTeamView deleteTeamReferenceScreenInterfaceFieldReference;
    private StatisticsGraphView statisticsGraphViewInterfaceFieldReference;
    private LiveMatchView matchViewInterfaceFieldReference;
    private PlayerManager playerProfileManagerServiceFieldReference;
    private GameManager gameEntityManagerServiceFieldReference = new GameManager();
    private LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();


    public AdminMenuController(AdminMenuView adminMenuViewInterfaceParameterValue, PlayerManager playerProfileManagerServiceParameterValue) {
        this.adminMenuViewInterfaceFieldReference = adminMenuViewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.adminMenuViewInterfaceFieldReference.registerController(this);
    }

    /**
     * Muestra el menú del administrador.
     */
    @Override
    public void showMenu() {
        showAdminMenu();
    }

    /**
     * Retorna al menú del administrador desde otra vista.
     */
    @Override
    public void returnToMenu() {
        showAdminMenu();
    }

    /**
     * Acción tras eliminar jugadores, vuelve a mostrar el menú.
     */
    @Override
    public void onPlayersDeleted() {
        showAdminMenu(); // opcional
    }

    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();

        switch (commandLocalVariableValue) {
            case AdminMenuView.CREATE_LEAGUE:
                handleCreateLeague();
                break;
            case AdminMenuView.DELETE_LEAGUE:
                handleDeleteLeague();
                break;
            case AdminMenuView.VIEW_LEAGUES:
                handleViewLeagues();
                break;
            case AdminMenuView.CREATE_TEAM:
                handleCreateTeam();
                break;
            case AdminMenuView.DELETE_TEAM:
                handleDeleteTeam();
                break;
            case AdminMenuView.LOGOUT:
                handleLogout();
                break;
            case AdminMenuView.DELETE_PLAYER:
                handleDeletePlayer();
                break;
            case AdminMenuView.VIEW_GAMES:
                handleGames();
                break;
        }
    }

    /**
     * Abre la vista de estadísticas gráficas.
     */
    public void handleStats() {
        adminMenuViewInterfaceFieldReference.dispose();
        statisticsGraphViewInterfaceFieldReference = new StatisticsGraphView();
        statisticsGraphViewInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        new StatisticsGraphController(statisticsGraphViewInterfaceFieldReference, this, playerProfileManagerServiceFieldReference);
        statisticsGraphViewInterfaceFieldReference.setVisible(true);
    }

    /**
     * Muestra la vista para crear una nueva liga.
     */
    public void handleCreateLeague() {
        adminMenuViewInterfaceFieldReference.dispose();
        createLeagueReferenceViewInterfaceFieldReference = new CreateLeagueView();
        createLeagueReferenceViewInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        new CreateLeagueController(createLeagueReferenceViewInterfaceFieldReference, this);
    }

    /**
     * Muestra la vista para eliminar ligas existentes.
     */
    public void handleDeleteLeague() {
        ArrayList<League> leaguesLocalVariableValue = leagueReferenceManagerServiceFieldReference.getAllLeagues();
        adminMenuViewInterfaceFieldReference.dispose();
        deleteLeagueReferenceViewInterfaceFieldReference = new DeleteLeagueView(leaguesLocalVariableValue);
        deleteLeagueReferenceViewInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        new DeleteLeagueController(deleteLeagueReferenceViewInterfaceFieldReference, this);
        deleteLeagueReferenceViewInterfaceFieldReference.setVisible(true);
    }

    /**
     * Muestra todas las ligas disponibles.
     */
    public void handleViewLeagues() {
        adminMenuViewInterfaceFieldReference.dispose();
        AvailableLeaguesView viewInterfaceLocalVariableValue = new AvailableLeaguesView();
        new AvailableLeaguesController(viewInterfaceLocalVariableValue, this, playerProfileManagerServiceFieldReference);
        viewInterfaceLocalVariableValue.setVisible(true);
    }

    /**
     * Cierra la sesión del usuario actual y vuelve a la pantalla de login.
     * Además, cierra las ventanas relacionadas con partidos en vivo y creación de equipos.
     */
    public void handleLogout() {
        adminMenuViewInterfaceFieldReference.dispose();
        for (Window windowLocalVariableValue : Window.getWindows()) {
            if (windowLocalVariableValue instanceof LiveMatchesView) {
                windowLocalVariableValue.dispose();
            }
            if (windowLocalVariableValue instanceof CreateTeamView) {
                windowLocalVariableValue.dispose();
            }
        }
        playerProfileManagerServiceFieldReference.setCurrentIdentifier(null);
        SwingUtilities.invokeLater(() -> {
            LoginView loginViewInterfaceLocalVariableValue = new LoginView();
            new LoginController(loginViewInterfaceLocalVariableValue, new PlayerManager());
        });
    }

    /**
     * Vuelve a mostrar el menú principal del administrador,
     * cerrando las vistas secundarias si están visibles.
     */
    public void showAdminMenu() {
        if (createLeagueReferenceViewInterfaceFieldReference != null && createLeagueReferenceViewInterfaceFieldReference.isVisible()) {
            createLeagueReferenceViewInterfaceFieldReference.dispose();
        }
        if (availableLeaguesViewInterfaceFieldReference != null && availableLeaguesViewInterfaceFieldReference.isVisible()) {
            availableLeaguesViewInterfaceFieldReference.dispose();
        }
        if (deleteLeagueReferenceViewInterfaceFieldReference != null && deleteLeagueReferenceViewInterfaceFieldReference.isVisible()) {
            deleteLeagueReferenceViewInterfaceFieldReference.dispose();
        }

        adminMenuViewInterfaceFieldReference.setVisible(true);
    }

    /**
     * Muestra la pantalla para eliminar jugadores.
     */
    private void handleDeletePlayer() {
        adminMenuViewInterfaceFieldReference.dispose();
        deletePlayerProfileViewInterfaceFieldReference = new DeletePlayerView();
        deletePlayerProfileViewInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        new DeletePlayerController(deletePlayerProfileViewInterfaceFieldReference, this);
        deletePlayerProfileViewInterfaceFieldReference.setVisible(true);
    }

    /**
     * Muestra la pantalla para eliminar equipos.
     */
    private void handleDeleteTeam() {
        adminMenuViewInterfaceFieldReference.dispose();
        deleteTeamReferenceScreenInterfaceFieldReference = new DeleteTeamView();
        deleteTeamReferenceScreenInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DeleteTeamController controllerHandlerLocalVariableValue = new DeleteTeamController(deleteTeamReferenceScreenInterfaceFieldReference, this);
        deleteTeamReferenceScreenInterfaceFieldReference.setConfigController(controllerHandlerLocalVariableValue);
        deleteTeamReferenceScreenInterfaceFieldReference.setVisible(true);
    }

    public PlayerManager getPlayerManager() {
        return playerProfileManagerServiceFieldReference;
    }

    /**
     * Muestra los partidos en vivo disponibles.
     * Si no hay partidos, muestra un mensaje informativo.
     */
    private void handleGames() {
        List<String[]> liveGamesLocalVariableValue = gameEntityManagerServiceFieldReference.getLiveGames();
        if (liveGamesLocalVariableValue.isEmpty()) {
            adminMenuViewInterfaceFieldReference.showMessageDialog("No live game currently.");
        } else {
            adminMenuViewInterfaceFieldReference.setVisible(false);
            //adminMenuView.dispose();
            LiveMatchesView screenInterfaceLocalVariableValue = new LiveMatchesView(liveGamesLocalVariableValue);
            LiveMatchesController matchesControllerHandlerLocalVariableValue = new LiveMatchesController(screenInterfaceLocalVariableValue, null, playerProfileManagerServiceFieldReference, this);
            screenInterfaceLocalVariableValue.startAutoRefresh(matchesControllerHandlerLocalVariableValue);
        }
    }

    /**
     * Muestra la pantalla para crear un nuevo equipo y configura los listeners necesarios.
     */
    private void handleCreateTeam() {
        SwingUtilities.invokeLater(() -> {
            adminMenuViewInterfaceFieldReference.setVisible(false);
            //adminMenuView.dispose();
            CreateTeamView createNewTeamReferenceScreenInterfaceLocalVariableValue = new CreateTeamView();
            createNewTeamReferenceScreenInterfaceLocalVariableValue.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            new CreateTeamController(createNewTeamReferenceScreenInterfaceLocalVariableValue, this);

            createNewTeamReferenceScreenInterfaceLocalVariableValue.setBackButtonListener(eventArgumentParameterValue2 -> {
                createNewTeamReferenceScreenInterfaceLocalVariableValue.dispose();
                adminMenuViewInterfaceFieldReference.setVisible(true);
            });

            createNewTeamReferenceScreenInterfaceLocalVariableValue.setConfigController(eventArgumentParameterValue3 -> {
                if ("CONFIG".equals(eventArgumentParameterValue3.getActionCommand())) {
                    showConfigDialog();
                }
            });

            createNewTeamReferenceScreenInterfaceLocalVariableValue.setVisible(true);
        });
    }

    /**
     * Muestra el diálogo de configuración con opciones para cerrar sesión,
     * eliminar cuenta o cambiar la contraseña.
     */
    public void showConfigDialog() {
        adminMenuViewInterfaceFieldReference.setVisible(false); // Oculta el menú admin
        Rounded.ConfigDialog configDialogLocalVariableValue = new Rounded.ConfigDialog(adminMenuViewInterfaceFieldReference);

        // Listener para el botón de configuración principal
        configDialogLocalVariableValue.registerController(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgumentParameterValue4) {
                configDialogLocalVariableValue.dispose();
                switch (eventArgumentParameterValue4.getActionCommand()) {
                    case Rounded.ConfigDialog.LOGOUT:
                        handleLogout();
                        break;
                    case Rounded.ConfigDialog.DELETE_ACCOUNT:
                        handleDeleteAccount();
                        break;
                    case Rounded.ConfigDialog.CHANGE_PASSWORD:
                        openChangePasswordView();
                        break;
                }
            }
        });

        // Listener para el botón "Back" para volver al menú admin
        configDialogLocalVariableValue.setBackButtonListener(eventArgumentParameterValue5 -> {
            configDialogLocalVariableValue.dispose();
            adminMenuViewInterfaceFieldReference.setVisible(true);
        });

        configDialogLocalVariableValue.setVisible(true);
    }

    /**
     * Abre la vista para cambiar la contraseña.
     * Configura el listener del botón "Back" para cerrar la vista
     * y volver a mostrar el menú principal del administrador.
     */
    private void openChangePasswordView() {
        ChangePasswordView changeUserPasswordViewInterfaceLocalVariableValue =
                new ChangePasswordView();

        adminMenuViewInterfaceFieldReference.setVisible(false);

        new ChangePasswordController(
                changeUserPasswordViewInterfaceLocalVariableValue,
                playerProfileManagerServiceFieldReference,
                adminMenuViewInterfaceFieldReference
        );

        changeUserPasswordViewInterfaceLocalVariableValue.setVisible(true);
    }


    /**
     * Pregunta al usuario si desea eliminar su cuenta y lo desconecta si acepta.
     */
    private void handleDeleteAccount() {
        int confirmLocalVariableValue = JOptionPane.showConfirmDialog(
                adminMenuViewInterfaceFieldReference,
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmLocalVariableValue == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(adminMenuViewInterfaceFieldReference, "Admin accounts cannot be deleted. Returning to login.");
            handleLogout();

        } else {
showConfigDialog();        }
    }
}
