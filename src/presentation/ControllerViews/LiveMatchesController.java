package presentation.ControllerViews;

import bussines.LiveMatchesRegistry;
import bussines.managers.ConfigManager;
import bussines.managers.GameManager;
import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.Views.LiveMatchesView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


/**
 * Coordina la pantalla de los partidos en directo.
 */
public class LiveMatchesController implements ActionListener {
    private final LiveMatchesView viewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final GameManager gameEntityManagerServiceFieldReference = new GameManager();
    private final AppNavigator navigatorFieldReference;


    /**
     * Crea una instancia de los directo partidos.
     *
     * @param viewInterfaceParameterValue vista que usa la operacion.
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
    public LiveMatchesController(LiveMatchesView viewInterfaceParameterValue,
                                 PlayerManager playerProfileManagerServiceParameterValue,
                                 AppNavigator navigatorParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        setupListeners();
    }


    /**
     * Actualiza el contenido.
     */
    private void setupListeners() {
        viewInterfaceFieldReference.setBackButtonListener(this);
        viewInterfaceFieldReference.setConfigController(this);
        viewInterfaceFieldReference.setMatchClickListener(this);
    }


    /**
     * Devuelve los directo partidos.
     *
     * @return los directo partidos.
     */
    public List<String[]> getterLiveGames() {
        if (ConfigManager.getAdminIdentifier().equalsIgnoreCase(playerProfileManagerServiceFieldReference.getCurrentIdentifier())) {
            return gameEntityManagerServiceFieldReference.getLiveGames();
        }
        return playerProfileManagerServiceFieldReference.getLiveMatches();
    }


    /**
     * Gestiona esta operacion.
     */
    public void refreshLiveGames() {
        viewInterfaceFieldReference.updateMatches(getterLiveGames());
    }


    /**
     * Gestiona esta operacion.
     */
    public void startAutoRefresh() { viewInterfaceFieldReference.startAutoRefresh(this); }


    /**
     * Gestiona esta operacion.
     */
    public void stopAutoRefresh() { viewInterfaceFieldReference.stopAutoRefresh(); }


    /**
     * Gestiona esta operacion.
     *
     * @param eventArgumentParameterValue dato de entrada de la operacion.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();
        if ("BACK".equals(commandLocalVariableValue)) {
            if (ConfigManager.getAdminIdentifier().equalsIgnoreCase(playerProfileManagerServiceFieldReference.getCurrentIdentifier())) {
                navigatorFieldReference.show(AppNavigator.ADMIN_MENU);
            } else {
                navigatorFieldReference.show(AppNavigator.PLAYER_MENU);
            }
        } else if (LiveMatchesView.CONFIG.equals(commandLocalVariableValue)) {
            showConfigDialog();
        } else if ("MATCH_CLICK".equals(commandLocalVariableValue)) {
            Object sourceLocalVariableValue = eventArgumentParameterValue.getSource();
            if (sourceLocalVariableValue instanceof String[]) {
                String[] matchLocalVariableValue = (String[]) sourceLocalVariableValue;
                if (matchLocalVariableValue.length >= 3) {
                    try {
                        int gameIdLocalVariableValue = Integer.parseInt(matchLocalVariableValue[2]);
                        boolean openedExistingMatchLocalVariableValue =
                                LiveMatchesRegistry.getInstance().showMatchWindow(
                                        gameIdLocalVariableValue
                                );

                        if (!openedExistingMatchLocalVariableValue) {
                            viewInterfaceFieldReference.showMessageDialog(
                                    "This live match is still starting. Try again in a moment."
                            );
                        }
                    } catch (NumberFormatException ignoredExceptionParameterValue) {
                        viewInterfaceFieldReference.showMessageDialog("Cannot open this match.");
                    }
                }
            }
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
                AppNavigator.LIVE_MATCHES
        );
        configDialogLocalVariableValue.setVisible(true);
    }
}


