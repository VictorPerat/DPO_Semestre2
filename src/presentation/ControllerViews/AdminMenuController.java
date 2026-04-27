package presentation.ControllerViews;

import bussines.managers.GameManager;
import bussines.objects.League;
import bussines.managers.LeagueManager;
import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.Views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se encarga de controlar el menú del administrador.
 */
public class AdminMenuController implements ActionListener, DeletePlayerListener, MenuController, LeagueViewActions {

    // Vista principal del menú de administrador
    private AdminMenuView adminMenuViewInterfaceFieldReference;

    // Vistas secundarias que se abren desde el menú
    private CreateLeagueView createLeagueReferenceViewInterfaceFieldReference;
    private AvailableLeaguesView availableLeaguesViewInterfaceFieldReference;
    private DeleteLeagueView deleteLeagueReferenceViewInterfaceFieldReference;
    private DeletePlayerView deletePlayerProfileViewInterfaceFieldReference;
    private DeleteTeamView deleteTeamReferenceScreenInterfaceFieldReference;
    private StatisticsGraphView statisticsGraphViewInterfaceFieldReference;
    private LiveMatchView matchViewInterfaceFieldReference;

    // Managers que se usan para manejar la lógica de negocio
    private PlayerManager playerProfileManagerServiceFieldReference;
    private GameManager gameEntityManagerServiceFieldReference = new GameManager();
    private LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();

    // Constructor que conecta la vista con este controlador
    public AdminMenuController(AdminMenuView adminMenuViewInterfaceParameterValue, PlayerManager playerProfileManagerServiceParameterValue) {
        this.adminMenuViewInterfaceFieldReference = adminMenuViewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.adminMenuViewInterfaceFieldReference.registerController(this);
    }

    // Muestra el menú del administrador
    @Override
    public void showMenu() {
        showAdminMenu();
    }

    // Vuelve al menú del administrador
    @Override
    public void returnToMenu() {
        showAdminMenu();
    }

    // Cuando se borran jugadores, vuelve al menú
    @Override
    public void onPlayersDeleted() {
        showAdminMenu();
    }

    // Gestiona las acciones que llegan desde la vista
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

    // Abre la pantalla de estadísticas
    public void handleStats() {
        adminMenuViewInterfaceFieldReference.dispose();
        statisticsGraphViewInterfaceFieldReference = new StatisticsGraphView();
        statisticsGraphViewInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        new StatisticsGraphController(
                statisticsGraphViewInterfaceFieldReference,
                this,
                playerProfileManagerServiceFieldReference
        );
        statisticsGraphViewInterfaceFieldReference.setVisible(true);
    }

    // Abre la pantalla para crear una liga
    public void handleCreateLeague() {
        adminMenuViewInterfaceFieldReference.dispose();
        createLeagueReferenceViewInterfaceFieldReference = new CreateLeagueView();
        createLeagueReferenceViewInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        new CreateLeagueController(createLeagueReferenceViewInterfaceFieldReference, this);
    }

    // Abre la pantalla para borrar ligas
    public void handleDeleteLeague() {
        ArrayList<League> leaguesLocalVariableValue = leagueReferenceManagerServiceFieldReference.getAllLeagues();

        adminMenuViewInterfaceFieldReference.dispose();
        deleteLeagueReferenceViewInterfaceFieldReference = new DeleteLeagueView(leaguesLocalVariableValue);
        deleteLeagueReferenceViewInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        new DeleteLeagueController(deleteLeagueReferenceViewInterfaceFieldReference, this);
        deleteLeagueReferenceViewInterfaceFieldReference.setVisible(true);
    }

    // Abre la pantalla para ver ligas
    public void handleViewLeagues() {
        adminMenuViewInterfaceFieldReference.dispose();
        AvailableLeaguesView viewInterfaceLocalVariableValue = new AvailableLeaguesView();
        new AvailableLeaguesController(
                viewInterfaceLocalVariableValue,
                this,
                playerProfileManagerServiceFieldReference
        );
        viewInterfaceLocalVariableValue.setVisible(true);
    }

    // Cierra sesión y vuelve al login
    public void handleLogout() {
        adminMenuViewInterfaceFieldReference.dispose();

        // Cierra posibles ventanas abiertas relacionadas con partidos o equipos
        for (Window windowLocalVariableValue : Window.getWindows()) {
            if (windowLocalVariableValue instanceof LiveMatchesView) {
                windowLocalVariableValue.dispose();
            }
            if (windowLocalVariableValue instanceof CreateTeamView) {
                windowLocalVariableValue.dispose();
            }
        }

        // Limpia el usuario actual
        playerProfileManagerServiceFieldReference.setCurrentIdentifier(null);

        // Vuelve a la pantalla de login
        SwingUtilities.invokeLater(() -> {
            AppNavigator.getInstance().show(AppNavigator.LOGIN);
        });
    }

    // Muestra de nuevo el menú del administrador
    public void showAdminMenu() {
        if (createLeagueReferenceViewInterfaceFieldReference != null
                && createLeagueReferenceViewInterfaceFieldReference.isVisible()) {
            createLeagueReferenceViewInterfaceFieldReference.dispose();
        }

        if (availableLeaguesViewInterfaceFieldReference != null
                && availableLeaguesViewInterfaceFieldReference.isVisible()) {
            availableLeaguesViewInterfaceFieldReference.dispose();
        }

        if (deleteLeagueReferenceViewInterfaceFieldReference != null
                && deleteLeagueReferenceViewInterfaceFieldReference.isVisible()) {
            deleteLeagueReferenceViewInterfaceFieldReference.dispose();
        }

        adminMenuViewInterfaceFieldReference.setVisible(true);
    }

    // Abre la pantalla para borrar jugadores
    private void handleDeletePlayer() {
        adminMenuViewInterfaceFieldReference.dispose();
        deletePlayerProfileViewInterfaceFieldReference = new DeletePlayerView();
        deletePlayerProfileViewInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        new DeletePlayerController(deletePlayerProfileViewInterfaceFieldReference, this);
        deletePlayerProfileViewInterfaceFieldReference.setVisible(true);
    }

    // Abre la pantalla para borrar equipos
    private void handleDeleteTeam() {
        adminMenuViewInterfaceFieldReference.dispose();
        deleteTeamReferenceScreenInterfaceFieldReference = new DeleteTeamView();
        deleteTeamReferenceScreenInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DeleteTeamController controllerHandlerLocalVariableValue =
                new DeleteTeamController(deleteTeamReferenceScreenInterfaceFieldReference, this);

        deleteTeamReferenceScreenInterfaceFieldReference.setConfigController(controllerHandlerLocalVariableValue);
        deleteTeamReferenceScreenInterfaceFieldReference.setVisible(true);
    }

    // Devuelve el manager de jugadores
    public PlayerManager getPlayerManager() {
        return playerProfileManagerServiceFieldReference;
    }

    // Abre la pantalla de partidos en directo
    private void handleGames() {
        List<String[]> liveGamesLocalVariableValue = gameEntityManagerServiceFieldReference.getLiveGames();

        if (liveGamesLocalVariableValue.isEmpty()) {
            adminMenuViewInterfaceFieldReference.showMessageDialog("No live game currently.");
        } else {
            adminMenuViewInterfaceFieldReference.setVisible(false);

            LiveMatchesView screenInterfaceLocalVariableValue =
                    new LiveMatchesView(liveGamesLocalVariableValue);

            LiveMatchesController matchesControllerHandlerLocalVariableValue =
                    new LiveMatchesController(
                            screenInterfaceLocalVariableValue,
                            null,
                            playerProfileManagerServiceFieldReference,
                            this
                    );

            screenInterfaceLocalVariableValue.startAutoRefresh(matchesControllerHandlerLocalVariableValue);
        }
    }

    // Abre la pantalla para crear un equipo nuevo
    private void handleCreateTeam() {
        SwingUtilities.invokeLater(() -> {
            adminMenuViewInterfaceFieldReference.setVisible(false);

            CreateTeamView createNewTeamReferenceScreenInterfaceLocalVariableValue = new CreateTeamView();
            createNewTeamReferenceScreenInterfaceLocalVariableValue.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            new CreateTeamController(createNewTeamReferenceScreenInterfaceLocalVariableValue, this);

            // Botón para volver atrás
            createNewTeamReferenceScreenInterfaceLocalVariableValue.setBackButtonListener(eventArgumentParameterValue2 -> {
                createNewTeamReferenceScreenInterfaceLocalVariableValue.dispose();
                adminMenuViewInterfaceFieldReference.setVisible(true);
            });

            // Botón de configuración
            createNewTeamReferenceScreenInterfaceLocalVariableValue.setConfigController(eventArgumentParameterValue3 -> {
                if ("CONFIG".equals(eventArgumentParameterValue3.getActionCommand())) {
                    showConfigDialog();
                }
            });

            createNewTeamReferenceScreenInterfaceLocalVariableValue.setVisible(true);
        });
    }

    // Muestra el panel de configuración del administrador
    public void showConfigDialog() {
        adminMenuViewInterfaceFieldReference.setVisible(false);
        Rounded.ConfigDialog configDialogLocalVariableValue =
                new Rounded.ConfigDialog(adminMenuViewInterfaceFieldReference);

        // Opciones principales del diálogo
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

        // Botón para volver atrás
        configDialogLocalVariableValue.setBackButtonListener(eventArgumentParameterValue5 -> {
            configDialogLocalVariableValue.dispose();
            adminMenuViewInterfaceFieldReference.setVisible(true);
        });

        configDialogLocalVariableValue.setVisible(true);
    }

    // Abre la pantalla para cambiar la contraseña
    private void openChangePasswordView() {
        final AdminMenuView previousScreenLocalVariableValue =
                adminMenuViewInterfaceFieldReference;

        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();

        previousScreenLocalVariableValue.setVisible(false);

        // Guarda la acción que se hará al volver desde change password
        navigatorLocalVariableValue.setChangePasswordReturnAction(() -> {
            navigatorLocalVariableValue.hideMainWindow();
            previousScreenLocalVariableValue.setVisible(true);
        });

        navigatorLocalVariableValue.show(AppNavigator.CHANGE_PASSWORD);
    }

    // Gestiona el intento de borrar la cuenta del administrador
    private void handleDeleteAccount() {
        int confirmLocalVariableValue = JOptionPane.showConfirmDialog(
                adminMenuViewInterfaceFieldReference,
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmLocalVariableValue == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                    adminMenuViewInterfaceFieldReference,
                    "Admin accounts cannot be deleted. Returning to login."
            );
            handleLogout();
        } else {
            showConfigDialog();
        }
    }
}