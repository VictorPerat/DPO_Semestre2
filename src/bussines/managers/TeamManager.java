package bussines.managers;

import bussines.objects.Team;
import persistance.TeamDao;
import java.util.ArrayList;
import java.util.Set;

/**
 * Esta clase se encarga de gestionar las operaciones relacionadas con los equipos.
 */
public class TeamManager {

    // DAO que se usa para acceder a los datos de los equipos
    private final TeamDao teamReferenceDataAccessObjectFieldReference;

    // Constructor que inicializa el DAO de equipos
    public TeamManager() {
        teamReferenceDataAccessObjectFieldReference = new TeamDao();
    }

    // Devuelve todos los equipos guardados
    public ArrayList<Team> getAllTeams() {
        return teamReferenceDataAccessObjectFieldReference.getAllTeams();
    }

    // Borra un equipo usando su nombre
    public boolean deleteTeamByName(String displayNameParameterValue) {
        return teamReferenceDataAccessObjectFieldReference.deleteTeamByName(displayNameParameterValue);
    }

    // Devuelve el id de un equipo a partir de su nombre
    public int getTeamId(String teamReferenceDisplayNameParameterValue) {
        return teamReferenceDataAccessObjectFieldReference.getTeamId(teamReferenceDisplayNameParameterValue);
    }

    // Borra varios equipos y devuelve un mensaje con el resultado
    public String deleteTeams(ArrayList<String> selectedTeamsParameterValue) {
        StringBuilder resultLocalVariableValue = new StringBuilder("Deleted teams:\n");

        for (String teamReferenceDisplayNameLocalVariableValue : selectedTeamsParameterValue) {
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

    // Crea un nuevo equipo
    public boolean createTeam(Team teamReferenceParameterValue) {
        return teamReferenceDataAccessObjectFieldReference.insertTeam(
                teamReferenceParameterValue.getName()
        ) > 0;
    }

    // Devuelve los equipos que todavía no están en ninguna liga
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

    // Comprueba si un equipo ya existe
    public boolean teamExists(String teamReferenceDisplayNameParameterValue2) {
        return teamReferenceDataAccessObjectFieldReference.getTeamId(teamReferenceDisplayNameParameterValue2) != -1;
    }
}