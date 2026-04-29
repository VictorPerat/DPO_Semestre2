package presentation.ControllerViews;

import bussines.managers.GameManager;
import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.Views.LiveMatchView;
import presentation.Views.LiveMatchesView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/** Controlador de la lista de partidos en directo. */
public class LiveMatchesController implements ActionListener {
    private final LiveMatchesView viewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final GameManager gameEntityManagerServiceFieldReference = new GameManager();
    private final AppNavigator navigatorFieldReference;

    public LiveMatchesController(LiveMatchesView viewInterfaceParameterValue,
                                 PlayerManager playerProfileManagerServiceParameterValue,
                                 AppNavigator navigatorParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        setupListeners();
    }

    public LiveMatchesController(LiveMatchesView viewInterfaceParameterValue,
                                 PlayerMenuController playerMenuControllerHandlerParameterValue,
                                 PlayerManager playerProfileManagerServiceParameterValue,
                                 AdminMenuController adminMenuControllerHandlerParameterValue) {
        this(viewInterfaceParameterValue, playerProfileManagerServiceParameterValue, AppNavigator.getInstance());
    }

    private void setupListeners() {
        viewInterfaceFieldReference.setBackButtonListener(this);
        viewInterfaceFieldReference.setConfigController(this);
        viewInterfaceFieldReference.setMatchClickListener(this);
    }

    public List<String[]> getterLiveGames() {
        if (PlayerManager.ADMIN_IDENTIFIER.equalsIgnoreCase(playerProfileManagerServiceFieldReference.getCurrentIdentifier())) {
            return gameEntityManagerServiceFieldReference.getLiveGames();
        }
        return playerProfileManagerServiceFieldReference.getLiveMatches();
    }

    public void refreshLiveGames() {
        viewInterfaceFieldReference.updateMatches(getterLiveGames());
    }

    public void startAutoRefresh() { viewInterfaceFieldReference.startAutoRefresh(this); }
    public void stopAutoRefresh() { viewInterfaceFieldReference.stopAutoRefresh(); }

    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();
        if ("BACK".equals(commandLocalVariableValue)) {
            if (PlayerManager.ADMIN_IDENTIFIER.equalsIgnoreCase(playerProfileManagerServiceFieldReference.getCurrentIdentifier())) {
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
                        LiveMatchView matchViewInterfaceLocalVariableValue = new LiveMatchView(matchLocalVariableValue[0], matchLocalVariableValue[1], gameIdLocalVariableValue);
                        matchViewInterfaceLocalVariableValue.setVisible(true);
                    } catch (NumberFormatException ignoredExceptionParameterValue) {
                        viewInterfaceFieldReference.showMessageDialog("Cannot open this match.");
                    }
                }
            }
        }
    }

    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        configDialogLocalVariableValue.registerController(eventArgumentParameterValue -> {
            configDialogLocalVariableValue.dispose();
            if (Rounded.ConfigDialog.LOGOUT.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.show(AppNavigator.LOGIN);
            } else if (Rounded.ConfigDialog.CHANGE_PASSWORD.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.setChangePasswordReturnAction(() -> navigatorFieldReference.show(AppNavigator.LIVE_MATCHES));
                navigatorFieldReference.show(AppNavigator.CHANGE_PASSWORD);
            }
        });
        configDialogLocalVariableValue.setBackButtonListener(eventArgumentParameterValue -> configDialogLocalVariableValue.dispose());
        configDialogLocalVariableValue.setVisible(true);
    }
}
