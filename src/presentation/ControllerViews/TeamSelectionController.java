package presentation.ControllerViews;

import bussines.managers.GameManager;
import bussines.managers.LeagueManager;
import bussines.managers.TeamManager;
import bussines.managers.TeamInfoManager;
import bussines.objects.Game;
import presentation.Views.CalendarView;
import presentation.Views.CreateLeagueView;
import presentation.Views.TeamSelectionView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

// Controlador que valida los equipos elegidos y crea la liga definitiva
public class TeamSelectionController implements ActionListener {

    // Vista donde se mueven los equipos disponibles y seleccionados
    private TeamSelectionView teamReferenceSelectionViewInterfaceFieldReference;

    // Controlador y vista anteriores para recuperar datos del formulario
    private CreateLeagueController createLeagueReferenceControllerHandlerFieldReference;
    private CreateLeagueView createLeagueReferenceViewInterfaceFieldReference;

    // Comando asociado al boton de crear liga
    public static final String CREATE_LEAGUE = "CREATE_LEAGUE";

    // Servicios necesarios para crear liga, equipos asociados y calendario
    private LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();
    private TeamManager teamReferenceManagerServiceFieldReference = new TeamManager();
    private GameManager gameEntityManagerServiceFieldReference;
    TeamInfoManager informationTeamReferenceManagerServiceFieldReference =
            new TeamInfoManager();

    // Lista temporal de equipos seleccionados e identificador de la nueva liga
    private ArrayList<String> selectedTeamsFieldReference = new ArrayList<>();
    private int leagueReferenceIdentifierFieldReference;

    // Registra la vista y deja preparado el boton de volver
    public TeamSelectionController(
            TeamSelectionView teamReferenceSelectionViewInterfaceParameterValue,
            CreateLeagueController createLeagueReferenceControllerHandlerParameterValue,
            CreateLeagueView createLeagueReferenceViewInterfaceParameterValue) {

        this.teamReferenceSelectionViewInterfaceFieldReference =
                teamReferenceSelectionViewInterfaceParameterValue;
        this.createLeagueReferenceControllerHandlerFieldReference =
                createLeagueReferenceControllerHandlerParameterValue;
        this.createLeagueReferenceViewInterfaceFieldReference =
                createLeagueReferenceViewInterfaceParameterValue;
        this.gameEntityManagerServiceFieldReference = new GameManager();
        this.teamReferenceSelectionViewInterfaceFieldReference.registerController(this);

        this.teamReferenceSelectionViewInterfaceFieldReference.setBackButtonListener(
                eventArgumentParameterValue -> {
                    this.teamReferenceSelectionViewInterfaceFieldReference.dispose();
                    this.createLeagueReferenceViewInterfaceFieldReference.setVisible(true);
                }
        );
    }

    // Atiende el boton de crear liga usando los datos del formulario anterior
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue2) {
        String commandLocalVariableValue = eventArgumentParameterValue2.getActionCommand();
        String leagueReferenceDisplayNameLocalVariableValue =
                createLeagueReferenceControllerHandlerFieldReference.getLeagueName();
        String startDateLocalVariableValue =
                createLeagueReferenceControllerHandlerFieldReference.getStartDate();
        String startHourLocalVariableValue =
                createLeagueReferenceControllerHandlerFieldReference.getStartHour();

        switch (commandLocalVariableValue) {
            case CREATE_LEAGUE:
                int correctTeamsLocalVariableValue = handleSelectTeams();

                if (correctTeamsLocalVariableValue == 0) {
                    teamReferenceSelectionViewInterfaceFieldReference.showMessageDialog(
                            "You must choose at least two teams"
                    );
                } else {
                    leagueReferenceManagerServiceFieldReference.createLeague(
                            leagueReferenceDisplayNameLocalVariableValue,
                            startDateLocalVariableValue,
                            selectedTeamsFieldReference
                    );

                    leagueReferenceIdentifierFieldReference =
                            leagueReferenceManagerServiceFieldReference.getLeagueIdByName(
                                    leagueReferenceDisplayNameLocalVariableValue
                            );

                    // Se crea el registro estadistico inicial de cada equipo en la liga
                    for (String teamReferenceDisplayNameLocalVariableValue
                            : selectedTeamsFieldReference) {
                        int teamReferenceIdentifierLocalVariableValue =
                                teamReferenceManagerServiceFieldReference.getTeamId(
                                        teamReferenceDisplayNameLocalVariableValue
                                );
                        informationTeamReferenceManagerServiceFieldReference.createInfoTeam(
                                leagueReferenceIdentifierFieldReference,
                                teamReferenceIdentifierLocalVariableValue,
                                0,
                                0,
                                0,
                                0
                        );
                    }

                    handleCreateLeague(
                            startDateLocalVariableValue,
                            startHourLocalVariableValue
                    );
                }
                break;
        }
    }

    // Combina la fecha y la hora escritas en el formulario
    public static LocalDateTime combineToDateTime(String startDateParameterValue,
                                                  String startHourParameterValue) {
        LocalDate dateLocalVariableValue = LocalDate.parse(startDateParameterValue);
        LocalTime timeLocalVariableValue = LocalTime.parse(startHourParameterValue);
        return LocalDateTime.of(dateLocalVariableValue, timeLocalVariableValue);
    }

    // Genera los partidos, abre el calendario y define que hacer al cerrarlo
    public void handleCreateLeague(String startDateParameterValue2,
                                   String startHourParameterValue2) {
        LocalDateTime resultLocalVariableValue = combineToDateTime(
                startDateParameterValue2,
                startHourParameterValue2
        );

        // Se escriben los partidos de la nueva liga en la base de datos
        leagueReferenceManagerServiceFieldReference.generateAndInsertMatchesForLeague(
                selectedTeamsFieldReference,
                leagueReferenceIdentifierFieldReference,
                resultLocalVariableValue
        );
        System.out.println("This is the league ID" + leagueReferenceIdentifierFieldReference);

        ArrayList<Game> gamesLocalVariableValue =
                gameEntityManagerServiceFieldReference.getGamesByLeague(
                        leagueReferenceIdentifierFieldReference
                );

        CalendarView calendarScreenInterfaceLocalVariableValue =
                new CalendarView(selectedTeamsFieldReference, gamesLocalVariableValue);
        calendarScreenInterfaceLocalVariableValue.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        // Cuando se cierre el calendario se vuelve al menu de admin
        calendarScreenInterfaceLocalVariableValue.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent eventArgumentParameterValue3) {
                        createLeagueReferenceControllerHandlerFieldReference
                                .getAdminMenuController()
                                .showAdminMenu();
                    }
                }
        );

        calendarScreenInterfaceLocalVariableValue.setVisible(true);
    }

    // Lee los equipos movidos al panel derecho y valida que haya suficientes
    public int handleSelectTeams() {
        for (Component compLocalVariableValue
                : teamReferenceSelectionViewInterfaceFieldReference
                .getAddedTeamsPanel()
                .getComponents()) {

            if (compLocalVariableValue instanceof JButton) {
                selectedTeamsFieldReference.add(
                        ((JButton) compLocalVariableValue).getText()
                );
            }
        }

        if (selectedTeamsFieldReference.size() < 2) {
            return 0;
        } else {
            teamReferenceSelectionViewInterfaceFieldReference.dispose();
            return 1;
        }
    }
}
