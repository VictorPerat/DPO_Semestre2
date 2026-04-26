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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.awt.*;

/**
 * Controlador para la vista de selección de equipos al crear una liga.
 * Gestiona la lógica para seleccionar equipos, crear la liga y abrir el calendario de partidos.
 */
public class TeamSelectionController implements ActionListener {
    private TeamSelectionView teamReferenceSelectionViewInterfaceFieldReference;
    private CreateLeagueController createLeagueReferenceControllerHandlerFieldReference;
    public static final String CREATE_LEAGUE = "CREATE_LEAGUE";
    private LeagueManager leagueReferenceManagerServiceFieldReference = new LeagueManager();
    private ArrayList<String> selectedTeamsFieldReference = new ArrayList<>();
    private TeamManager teamReferenceManagerServiceFieldReference = new TeamManager();
    private CreateLeagueView createLeagueReferenceViewInterfaceFieldReference;
    private GameManager gameEntityManagerServiceFieldReference;
    private int leagueReferenceIdentifierFieldReference;
    TeamInfoManager informationTeamReferenceManagerServiceFieldReference = new TeamInfoManager();

    /**
     * Constructor que inicializa el controlador, registra eventos y configura el botón de volver.
     *
     * @param teamSelectionView Vista para seleccionar equipos.
     * @param createLeagueController Controlador de la vista de creación de liga.
     * @param createLeagueView Vista de creación de liga.
     */
    public TeamSelectionController(TeamSelectionView teamReferenceSelectionViewInterfaceParameterValue, CreateLeagueController createLeagueReferenceControllerHandlerParameterValue, CreateLeagueView createLeagueReferenceViewInterfaceParameterValue) {
        this.teamReferenceSelectionViewInterfaceFieldReference = teamReferenceSelectionViewInterfaceParameterValue;
        this.createLeagueReferenceControllerHandlerFieldReference = createLeagueReferenceControllerHandlerParameterValue;
        this.createLeagueReferenceViewInterfaceFieldReference = createLeagueReferenceViewInterfaceParameterValue;
        this.gameEntityManagerServiceFieldReference = new GameManager();
        this.teamReferenceSelectionViewInterfaceFieldReference.registerController(this);

        this.teamReferenceSelectionViewInterfaceFieldReference.setBackButtonListener(eventArgumentParameterValue -> {
            this.teamReferenceSelectionViewInterfaceFieldReference.dispose(); // Tanca la selecció d’equips
            this.createLeagueReferenceViewInterfaceFieldReference.setVisible(true); // Torna a la vista anterior
        });
    }

    /**
     * Maneja los eventos de acción generados en la vista.
     * En particular, gestiona la creación de la liga tras validar equipos seleccionados.
     *
     * @param e Evento de acción generado.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue2) {
        String commandLocalVariableValue = eventArgumentParameterValue2.getActionCommand();
        String leagueReferenceDisplayNameLocalVariableValue = createLeagueReferenceControllerHandlerFieldReference.getLeagueName();
        String startDateLocalVariableValue = createLeagueReferenceControllerHandlerFieldReference.getStartDate();
        String startHourLocalVariableValue = createLeagueReferenceControllerHandlerFieldReference.getStartHour();

        switch (commandLocalVariableValue) {
            case CREATE_LEAGUE:
                int correctTeamsLocalVariableValue = handleSelectTeams();
                if (correctTeamsLocalVariableValue == 0) {
                    teamReferenceSelectionViewInterfaceFieldReference.showMessageDialog("You must choose at least two teams");
                }
                else {
                    leagueReferenceManagerServiceFieldReference.createLeague(leagueReferenceDisplayNameLocalVariableValue, startDateLocalVariableValue, selectedTeamsFieldReference);
                    leagueReferenceIdentifierFieldReference = leagueReferenceManagerServiceFieldReference.getLeagueIdByName(leagueReferenceDisplayNameLocalVariableValue);
                    for (String teamReferenceDisplayNameLocalVariableValue : selectedTeamsFieldReference) {
                        int teamReferenceIdentifierLocalVariableValue = teamReferenceManagerServiceFieldReference.getTeamId(teamReferenceDisplayNameLocalVariableValue);
                        informationTeamReferenceManagerServiceFieldReference.createInfoTeam(leagueReferenceIdentifierFieldReference, teamReferenceIdentifierLocalVariableValue, 0, 0, 0, 0);
                    }
                    handleCreateLeague(startDateLocalVariableValue, startHourLocalVariableValue);
                }
                break;
        }
    }

    /**
     * Combina la fecha y hora dadas en cadenas para crear un objeto LocalDateTime.
     *
     * @param startDate Fecha en formato "yyyy-MM-dd".
     * @param startHour Hora en formato "HH:mm".
     * @return Objeto LocalDateTime que representa la combinación de fecha y hora.
     */
    public static LocalDateTime combineToDateTime(String startDateParameterValue, String startHourParameterValue) {
        LocalDate dateLocalVariableValue = LocalDate.parse(startDateParameterValue);     // formato: "yyyy-MM-dd"
        LocalTime timeLocalVariableValue = LocalTime.parse(startHourParameterValue);     // formato: "HH:mm"
        return LocalDateTime.of(dateLocalVariableValue, timeLocalVariableValue);
    }

    /**
     * Gestiona la creación de la liga, generando los partidos y mostrando el calendario.
     *
     * @param startDate Fecha de inicio de la liga.
     * @param startHour Hora de inicio de la liga.
     */
    public void handleCreateLeague(String startDateParameterValue2, String startHourParameterValue2) {
        LocalDateTime resultLocalVariableValue = combineToDateTime(startDateParameterValue2, startHourParameterValue2);

        //escribe en db
        leagueReferenceManagerServiceFieldReference.generateAndInsertMatchesForLeague(selectedTeamsFieldReference, leagueReferenceIdentifierFieldReference, resultLocalVariableValue);
        System.out.println("This is the league ID" + leagueReferenceIdentifierFieldReference);

        ArrayList<Game> gamesLocalVariableValue = gameEntityManagerServiceFieldReference.getGamesByLeague(leagueReferenceIdentifierFieldReference);
        CalendarView calendarScreenInterfaceLocalVariableValue = new CalendarView(selectedTeamsFieldReference, gamesLocalVariableValue);
        calendarScreenInterfaceLocalVariableValue.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Listener para cuando se cierre el calendario
        calendarScreenInterfaceLocalVariableValue.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent eventArgumentParameterValue3) {
                createLeagueReferenceControllerHandlerFieldReference.getAdminMenuController().showAdminMenu();
            }
        });

        calendarScreenInterfaceLocalVariableValue.setVisible(true);

    }

    /**
     * Maneja la selección de equipos desde la vista.
     * Valida que haya al menos dos equipos seleccionados.
     *
     * @return 0 si hay menos de dos equipos seleccionados, 1 si la selección es correcta.
     */
    public int handleSelectTeams() {
        for (Component compLocalVariableValue : teamReferenceSelectionViewInterfaceFieldReference.getAddedTeamsPanel().getComponents()) {
            if (compLocalVariableValue instanceof JButton) {
                selectedTeamsFieldReference.add(((JButton) compLocalVariableValue).getText());
            }
        }

        if (selectedTeamsFieldReference.size() < 2) {
            return 0;
        }
        else {
            teamReferenceSelectionViewInterfaceFieldReference.dispose();
            return 1;
        }


    }
}