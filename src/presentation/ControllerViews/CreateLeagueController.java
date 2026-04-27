package presentation.ControllerViews;

import bussines.objects.League;
import bussines.managers.LeagueManager;
import bussines.objects.Team;
import bussines.managers.TeamManager;
import presentation.AppNavigator;
import presentation.Views.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para la creación de ligas.
 * Gestiona la interacción con la vista CreateLeagueView, validación de datos,
 * selección de equipos disponibles y navegación hacia la selección de equipos.
 */
public class CreateLeagueController implements ActionListener {

    /**
     * Vista para crear una nueva liga.
     */
    private CreateLeagueView createLeagueReferenceViewInterfaceFieldReference;

    /**
     * Controlador del menú administrador para navegación y gestión.
     */
    private AdminMenuController adminMenuControllerHandlerFieldReference;

    /**
     * Vista para seleccionar equipos que participarán en la liga.
     */
    private TeamSelectionView teamReferenceSelectionViewInterfaceFieldReference;

    /**
     * Lista de equipos seleccionados para la liga.
     */
    private List<String> selectedTeamsFieldReference;

    /**
     * Gestor de la lógica para ligas.
     */
    private LeagueManager leagueReferenceManagerServiceFieldReference;

    /**
     * Gestor de la lógica para equipos.
     */
    private TeamManager teamReferenceManagerServiceFieldReference;

    /**
     * Lista de todas las ligas existentes.
     */
    private ArrayList<League> leaguesFieldReference;

    /**
     * Lista de todos los equipos existentes.
     */
    private ArrayList<Team> teamsFieldReference;

    /**
     * Lista de equipos disponibles para asignar a la liga (no asignados a otra liga).
     */
    private ArrayList<Team> dispoTeamsFieldReference;

    /**
     * Nombre de la liga a crear.
     */
    public String leagueReferenceDisplayNameFieldReference;

    /**
     * Fecha de inicio de la liga.
     */
    public String startDateFieldReference;

    /**
     * Hora de inicio de la liga.
     */
    public String startHourFieldReference;
    public boolean logoutFieldReference;

    /**
     * Constructor que inicializa la vista y controlador del menú,
     * registra el controlador en la vista y añade listener para cierre de ventana.
     *
     * @param createLeagueView Vista para crear ligas.
     * @param adminMenuController Controlador del menú administrador.
     */
    public CreateLeagueController(CreateLeagueView createLeagueReferenceViewInterfaceParameterValue, AdminMenuController adminMenuControllerHandlerParameterValue) {
        this.createLeagueReferenceViewInterfaceFieldReference = createLeagueReferenceViewInterfaceParameterValue;
        this.adminMenuControllerHandlerFieldReference = adminMenuControllerHandlerParameterValue;
        this.createLeagueReferenceViewInterfaceFieldReference.registerController(this);
        this.createLeagueReferenceViewInterfaceFieldReference.setConfigController(this);
        this.leagueReferenceManagerServiceFieldReference = new LeagueManager();
        this.teamReferenceManagerServiceFieldReference = new TeamManager();

        // ESTO
        createLeagueReferenceViewInterfaceParameterValue.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent eventArgumentParameterValue) {
                if (!logoutFieldReference) {
                    adminMenuControllerHandlerParameterValue.showAdminMenu();
                }
            }
        });
    }

    /**
     * Método que maneja eventos de acción de la vista.
     * Principalmente valida y maneja el botón para mostrar equipos disponibles.
     *
     * @param e Evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue2) {
        String commandLocalVariableValue = eventArgumentParameterValue2.getActionCommand();

        if (CreateLeagueView.AVAILABLE_TEAMS_BUTTON.equals(commandLocalVariableValue)) {

            leaguesFieldReference = leagueReferenceManagerServiceFieldReference.getAllLeagues();
            leagueReferenceDisplayNameFieldReference = createLeagueReferenceViewInterfaceFieldReference.getLeagueName();
            startDateFieldReference = createLeagueReferenceViewInterfaceFieldReference.getDate();
            startHourFieldReference = createLeagueReferenceViewInterfaceFieldReference.getStartTime();

            if (!leagueReferenceManagerServiceFieldReference.leagueExist(leaguesFieldReference, leagueReferenceDisplayNameFieldReference)) {
                int dateStatusLocalVariableValue = leagueReferenceManagerServiceFieldReference.checkDateStatus(startDateFieldReference);
                int hourStatusLocalVariableValue = leagueReferenceManagerServiceFieldReference.checkTimeStatus(startHourFieldReference, startDateFieldReference);

                if (dateStatusLocalVariableValue == 0 && hourStatusLocalVariableValue == 0) {
                    teamsFieldReference = teamReferenceManagerServiceFieldReference.getAllTeams();
                    dispoTeamsFieldReference = teamReferenceManagerServiceFieldReference.getTeamsNotInLeague(teamsFieldReference);
                    handleAvailableTeams();
                } else if (dateStatusLocalVariableValue == 1) {
                    createLeagueReferenceViewInterfaceFieldReference.showMessageDialog("The entered date has already passed. Please enter a future date.");
                } else if (dateStatusLocalVariableValue == 2) {
                    createLeagueReferenceViewInterfaceFieldReference.showMessageDialog("Incorrect date format. Use the format: YYYY-MM-DD (e.g., 2025-05-20)");
                } else if (hourStatusLocalVariableValue == 2) {
                    createLeagueReferenceViewInterfaceFieldReference.showMessageDialog("Incorrect time format, follow HH:mm");
                } else if (hourStatusLocalVariableValue == 1) {
                    createLeagueReferenceViewInterfaceFieldReference.showMessageDialog("The entered date has already passed. Please enter a future date.");
                }
            } else {
                createLeagueReferenceViewInterfaceFieldReference.showMessageDialog("This league already exists!");
            }

        }
    }

    /**
     * Obtiene el nombre de la liga ingresado.
     *
     * @return Nombre de la liga.
     */
    public String getLeagueName() {
        return leagueReferenceDisplayNameFieldReference;
    }

    /**
     * Obtiene la fecha de inicio ingresada para la liga.
     *
     * @return Fecha de inicio.
     */
    public String getStartDate() {
        return startDateFieldReference;
    }

    /**
     * Obtiene la hora de inicio ingresada para la liga.
     *
     * @return Hora de inicio.
     */
    public String getStartHour() {
        return startHourFieldReference;
    }

    /**
     * Oculta la vista de creación de liga y abre la vista para selección de equipos disponibles.
     */
    private void handleAvailableTeams() {
        createLeagueReferenceViewInterfaceFieldReference.setVisible(false);
        teamReferenceSelectionViewInterfaceFieldReference = new TeamSelectionView(dispoTeamsFieldReference);
        teamReferenceSelectionViewInterfaceFieldReference.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        new TeamSelectionController(teamReferenceSelectionViewInterfaceFieldReference, this, createLeagueReferenceViewInterfaceFieldReference);
        teamReferenceSelectionViewInterfaceFieldReference.setVisible(true);
    }

    /**
     * Obtiene los nombres de los equipos seleccionados a partir de una lista de JCheckBox.
     *
     * @param checkBoxes Lista de JCheckBox que representan equipos.
     * @return Lista de nombres de equipos seleccionados.
     */
    private ArrayList<String> getSelectedTeamsFromCheckBoxes(ArrayList<JCheckBox> checkBoxesParameterValue) {
        ArrayList<String> selectedTeamsLocalVariableValue = new ArrayList<>();
        for (JCheckBox checkBoxLocalVariableValue : checkBoxesParameterValue) {
            if (checkBoxLocalVariableValue.isSelected()) {
                selectedTeamsLocalVariableValue.add(checkBoxLocalVariableValue.getText());
            }
        }
        return selectedTeamsLocalVariableValue;
    }

    /**
     * Obtiene el controlador del menú administrador.
     *
     * @return AdminMenuController asociado.
     */
    public AdminMenuController getAdminMenuController() {
        return this.adminMenuControllerHandlerFieldReference;
    }

    /**
     * Muestra el diálogo de configuración para opciones como cerrar sesión, eliminar cuenta o cambiar contraseña.
     */
    public void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = new Rounded.ConfigDialog(createLeagueReferenceViewInterfaceFieldReference);
        configDialogLocalVariableValue.registerController(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventArgumentParameterValue3) {
                if (eventArgumentParameterValue3.getActionCommand() == Rounded.ConfigDialog.LOGOUT) {
                    logoutFieldReference = true;
                }
                else {
                    logoutFieldReference = false;
                }
                configDialogLocalVariableValue.dispose();
                handleConfigAction(eventArgumentParameterValue3.getActionCommand());
            }
        });
        configDialogLocalVariableValue.setVisible(true);
    }

    /**
     * Maneja la acción seleccionada en el diálogo de configuración.
     *
     * @param action Comando de acción seleccionado.
     */
    private void handleConfigAction(String actionParameterValue) {
        switch(actionParameterValue) {
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

    /**
     * Maneja el cierre de sesión.
     * Cierra la vista actual y delega la acción al controlador del menú.
     */
    private void handleLogout() {
        //System.out.println("k");
        createLeagueReferenceViewInterfaceFieldReference.dispose();
        adminMenuControllerHandlerFieldReference.handleLogout();

    }

    /**
     * Maneja la eliminación de cuenta con confirmación previa.
     * Si se confirma, muestra mensaje de éxito y realiza el logout.
     */
    private void handleDeleteAccount() {
        int confirmLocalVariableValue = JOptionPane.showConfirmDialog(
                createLeagueReferenceViewInterfaceFieldReference,
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmLocalVariableValue == JOptionPane.YES_OPTION) {
            logoutFieldReference = true;
            JOptionPane.showMessageDialog(createLeagueReferenceViewInterfaceFieldReference, "Account deleted successfully");
            createLeagueReferenceViewInterfaceFieldReference.dispose();
            adminMenuControllerHandlerFieldReference.handleLogout();
        }
    }

    /**
     * Abre la vista para cambiar la contraseña a través del navegador,
     * registrando el retorno a la vista de creación de liga al finalizar.
     */
    private void openChangePasswordView() {
        final CreateLeagueView previousScreenLocalVariableValue =
                createLeagueReferenceViewInterfaceFieldReference;
        AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();

        previousScreenLocalVariableValue.setVisible(false);
        navigatorLocalVariableValue.setChangePasswordReturnAction(() -> {
            navigatorLocalVariableValue.hideMainWindow();
            previousScreenLocalVariableValue.setVisible(true);
        });
        navigatorLocalVariableValue.show(AppNavigator.CHANGE_PASSWORD);
    }

}
