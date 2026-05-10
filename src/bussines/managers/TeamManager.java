package bussines.managers;

import bussines.LiveMatchesRegistry;
import bussines.objects.Team;
import persistance.TeamDao;
import java.util.ArrayList;
import java.util.Set;


/**
 * Gestiona las operaciones del equipo.
 */
public class TeamManager {


    private final TeamDao teamReferenceDataAccessObjectFieldReference;


    /**
     * Crea una instancia de el equipo.
     */
    public TeamManager() {
        teamReferenceDataAccessObjectFieldReference = new TeamDao();
    }


    /**
     * Devuelve los equipos.
     *
     * @return los equipos.
     */
    public ArrayList<Team> getAllTeams() {
        return teamReferenceDataAccessObjectFieldReference.getAllTeams();
    }


    /**
     * Elimina el equipo nombre.
     *
     * @param displayNameParameterValue nombre que se muestra.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean deleteTeamByName(String displayNameParameterValue) {
        return teamReferenceDataAccessObjectFieldReference.deleteTeamByName(displayNameParameterValue);
    }


    /**
     * Devuelve el equipo.
     *
     * @param teamReferenceDisplayNameParameterValue nombre del equipo.
     * @return el equipo.
     */
    public int getTeamId(String teamReferenceDisplayNameParameterValue) {
        return teamReferenceDataAccessObjectFieldReference.getTeamId(teamReferenceDisplayNameParameterValue);
    }


    /**
     * Elimina los equipos.
     *
     * @param selectedTeamsParameterValue equipos que usa la operacion.
     * @return resultado de la operacion.
     */
    public String deleteTeams(ArrayList<String> selectedTeamsParameterValue) {
        StringBuilder resultLocalVariableValue = new StringBuilder("Deleted teams:\n");

        for (String teamReferenceDisplayNameLocalVariableValue : selectedTeamsParameterValue) {

            LiveMatchesRegistry.getInstance().abortMatchesByTeam(
                    teamReferenceDisplayNameLocalVariableValue
            );

            boolean successLocalVariableValue =
                    teamReferenceDataAccessObjectFieldReference.deleteTeamByName(
                            teamReferenceDisplayNameLocalVariableValue
                    );

            if (successLocalVariableValue) {
                resultLocalVariableValue.append("- ")
                        .append(teamReferenceDisplayNameLocalVariableValue)
                        .append("\n");
            } else {
                resultLocalVariableValue.append("- Failed to delete: ")
                        .append(teamReferenceDisplayNameLocalVariableValue)
                        .append("\n");
            }
        }

        return resultLocalVariableValue.toString();
    }


    /**
     * Crea el equipo.
     *
     * @param teamReferenceParameterValue equipo asociado.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean createTeam(Team teamReferenceParameterValue) {
        return teamReferenceDataAccessObjectFieldReference.insertTeam(
                teamReferenceParameterValue.getName()
        ) > 0;
    }


    /**
     * Devuelve el equipos liga.
     *
     * @param allTeamsParameterValue equipos que usa la operacion.
     * @return el equipos liga.
     */
    public ArrayList<Team> getTeamsNotInLeague(ArrayList<Team> allTeamsParameterValue) {
        ArrayList<Team> availableTeamsLocalVariableValue = new ArrayList<>();
        Set<Integer> assignedTeamReferenceIdentifiersLocalVariableValue;

        assignedTeamReferenceIdentifiersLocalVariableValue =
                teamReferenceDataAccessObjectFieldReference.getAssignedTeamIds();

        for (Team teamReferenceLocalVariableValue : allTeamsParameterValue) {
            if (!assignedTeamReferenceIdentifiersLocalVariableValue.contains(
                    teamReferenceLocalVariableValue.getId())) {
                availableTeamsLocalVariableValue.add(teamReferenceLocalVariableValue);
            }
        }

        return availableTeamsLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param teamReferenceDisplayNameParameterValue2 nombre del equipo.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean teamExists(String teamReferenceDisplayNameParameterValue2) {
        return teamReferenceDataAccessObjectFieldReference.getTeamId(teamReferenceDisplayNameParameterValue2) != -1;
    }
}