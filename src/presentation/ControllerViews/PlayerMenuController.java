package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.Views.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controlador principal del menú de jugador.
 * Gestiona las acciones disponibles desde el menú de jugador, como ver partidos en vivo,
 * ver ligas disponibles, eliminar cuenta o acceder a la configuración.
 */
public class PlayerMenuController implements ActionListener, MenuController, DeletePlayerListener {
    private PlayerMenuView playerProfileMenuScreenInterfaceFieldReference;
    private AvailableLeaguesView availableLeaguesViewInterfaceFieldReference;
    private LiveMatchesView liveMatchesViewInterfaceFieldReference;
    private DeletePlayerView deletePlayerProfileViewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;

    /**
     * Constructor del controlador del menú de jugador.
     *
     * @param playerMenuScreen Vista del menú del jugador.
     * @param playerManager Gestor de jugadores.
     */
    public PlayerMenuController(PlayerMenuView playerProfileMenuScreenInterfaceParameterValue, PlayerManager playerProfileManagerServiceParameterValue) {
        this.playerProfileMenuScreenInterfaceFieldReference = playerProfileMenuScreenInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.playerProfileMenuScreenInterfaceFieldReference.registerController(this);
        this.playerProfileMenuScreenInterfaceFieldReference.setConfigController(this);
    }

    /**
     * Muestra el menú del jugador.
     */
    @Override
    public void showMenu() {
        showPlayerMenu();
    }

    /**
     * Llamado cuando se elimina un jugador (modo jugador).
     */
    @Override
    public void onPlayersDeleted() {
        System.out.println("Players deleted (player mode)");
    }

    /**
     * Regresa al menú principal del jugador.
     */
    @Override
    public void returnToMenu() {
        showPlayerMenu();
    }

    /**
     * Maneja el cierre de sesión del jugador actual.
     */
    @Override
    public void handleLogout() {
        playerProfileManagerServiceFieldReference.setCurrentIdentifier(null);
        playerProfileMenuScreenInterfaceFieldReference.dispose();
        for (Window windowLocalVariableValue : Window.getWindows()) {
            if (windowLocalVariableValue instanceof LiveMatchesView) {
                windowLocalVariableValue.dispose();
            }
        }
        // Cierra el widget de partidos en directo (apartado 2.9)
        presentation.LiveMatchesWidgetService.hide();

        SwingUtilities.invokeLater(() -> {
            // Navegación centralizada al login (carta dentro de MainView).
            AppNavigator.getInstance().show(AppNavigator.LOGIN);
        });
    }

    /**
     * Maneja los eventos de acción del menú del jugador.
     *
     * @param e Evento de acción recibido.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();

        switch (commandLocalVariableValue) {
            case PlayerMenuView.WATCH_MATCHES:
                handleWatchMatches();
                break;
            case PlayerMenuView.VIEW_LEAGUES:
                handleViewLeagues();
                break;
            case PlayerMenuView.DELETE_PLAYER:
                handleDeleteAccount();
                break;
            case PlayerMenuView.LOGOUT:
                handleLogout();
                break;
            case PlayerMenuView.CONFIG:
                showConfigDialog();
                break;
        }
    }

    /**
     * Maneja la visualización de los partidos en vivo.
     */
    private void handleWatchMatches() {
        List<String[]> liveGamesLocalVariableValue = playerProfileManagerServiceFieldReference.getLiveMatches();
        if (liveGamesLocalVariableValue.isEmpty()) {
            playerProfileMenuScreenInterfaceFieldReference.showMessageDialog("There are no live matches at the moment.");
        } else {
            playerProfileMenuScreenInterfaceFieldReference.setVisible(false);
            LiveMatchesView screenInterfaceLocalVariableValue = new LiveMatchesView(liveGamesLocalVariableValue);
            LiveMatchesController matchesControllerHandlerLocalVariableValue = new LiveMatchesController(screenInterfaceLocalVariableValue, this, playerProfileManagerServiceFieldReference, null);
            screenInterfaceLocalVariableValue.startAutoRefresh(matchesControllerHandlerLocalVariableValue);
        }
    }

    /**
     * Maneja la visualización de ligas disponibles.
     */
    private void handleViewLeagues() {
        playerProfileMenuScreenInterfaceFieldReference.setVisible(false);
        AvailableLeaguesView viewInterfaceLocalVariableValue = new AvailableLeaguesView();
        new AvailableLeaguesController(viewInterfaceLocalVariableValue, this, playerProfileManagerServiceFieldReference);
        viewInterfaceLocalVariableValue.setVisible(true);
    }

    /**
     * Muestra el menú del jugador, cerrando vistas secundarias si están abiertas.
     */
    public void showPlayerMenu() {
        if (availableLeaguesViewInterfaceFieldReference != null && availableLeaguesViewInterfaceFieldReference.isVisible()) {
            availableLeaguesViewInterfaceFieldReference.dispose();
        }
        if (liveMatchesViewInterfaceFieldReference != null && liveMatchesViewInterfaceFieldReference.isVisible()) {
            liveMatchesViewInterfaceFieldReference.dispose();
        }
        if (deletePlayerProfileViewInterfaceFieldReference != null && deletePlayerProfileViewInterfaceFieldReference.isVisible()) {
            deletePlayerProfileViewInterfaceFieldReference.dispose();
        }

        SwingUtilities.invokeLater(() -> {
            playerProfileMenuScreenInterfaceFieldReference.setVisible(true);
        });
    }

    /**
     * Muestra el cuadro de diálogo de configuración.
     */
    @Override
    public void showConfigDialog() {
        playerProfileMenuScreenInterfaceFieldReference.setVisible(false);
        Rounded.ConfigDialog configDialogLocalVariableValue = new Rounded.ConfigDialog(playerProfileMenuScreenInterfaceFieldReference);
        configDialogLocalVariableValue.registerController(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgumentParameterValue2) {
                configDialogLocalVariableValue.dispose();
                switch(eventArgumentParameterValue2.getActionCommand()) {
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
        configDialogLocalVariableValue.setBackButtonListener(eventArgumentParameterValue3 -> {
            configDialogLocalVariableValue.dispose();
            playerProfileMenuScreenInterfaceFieldReference.setVisible(true);
        });
        configDialogLocalVariableValue.setVisible(true);
    }

    /**
     * Abre la vista para cambiar la contraseña del jugador actual.
     */
    private void openChangePasswordView() {
        // Navegamos al CHANGE_PASSWORD (carta dentro de MainView)
        // y registramos a dónde queremos volver al terminar.
        final PlayerMenuView previousScreenLocalVariableValue =
                playerProfileMenuScreenInterfaceFieldReference;
        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();

        previousScreenLocalVariableValue.setVisible(false);
        navigatorLocalVariableValue.setChangePasswordReturnAction(() -> {
            navigatorLocalVariableValue.hideMainWindow();
            previousScreenLocalVariableValue.setVisible(true);
        });
        navigatorLocalVariableValue.show(AppNavigator.CHANGE_PASSWORD);
    }

    /**
     * Maneja el proceso de eliminación de la cuenta del jugador.
     * Incluye confirmación y mensajes de éxito o error.
     */
    private void handleDeleteAccount() {
        int confirmLocalVariableValue = JOptionPane.showConfirmDialog(
                playerProfileMenuScreenInterfaceFieldReference,
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmLocalVariableValue == JOptionPane.YES_OPTION) {
            boolean successLocalVariableValue = playerProfileManagerServiceFieldReference.deleteCurrentPlayer();

            if (successLocalVariableValue) {
                playerProfileMenuScreenInterfaceFieldReference.showMessageDialog("Account deleted successfully");
                handleLogout();
            } else {
                playerProfileMenuScreenInterfaceFieldReference.showMessageDialog("Error deleting account");
            }
        }else{
                showConfigDialog();
            }
        }
    }

