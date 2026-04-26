package bussines.managers;

import bussines.objects.Team;
import persistance.TeamDao;
import java.util.ArrayList;
import java.util.Set;
/**
 * Clase encargada de la gestión de equipos. Proporciona métodos para crear, eliminar
 * y recuperar equipos desde la capa de persistencia.
 */
public class TeamManager {
    private final TeamDao teamReferenceDataAccessObjectFieldReference;
    /**
     * Constructor de TeamManager. Inicializa el DAO de equipos.
     */
    public TeamManager() {
        teamReferenceDataAccessObjectFieldReference = new TeamDao();
    }
    /**
     * Recupera todos los equipos existentes.
     *
     * @return Lista de objetos {@link Team}.
     */
    public ArrayList<Team> getAllTeams() {
        return teamReferenceDataAccessObjectFieldReference.getAllTeams();
    }
    /**
     * Elimina un equipo por su nombre.
     *
     * @param name Nombre del equipo a eliminar.
     * @return {@code true} si se eliminó correctamente, {@code false} en caso contrario.
     */
    public boolean deleteTeamByName(String displayNameParameterValue) {
        return teamReferenceDataAccessObjectFieldReference.deleteTeamByName(displayNameParameterValue);
    }
    /**
     * Obtiene el identificador de un equipo a partir de su nombre.
     *
     * @param teamName Nombre del equipo.
     * @return Identificador del equipo, o -1 si no existe.
     */
    public int getTeamId(String teamReferenceDisplayNameParameterValue){
        return teamReferenceDataAccessObjectFieldReference.getTeamId(teamReferenceDisplayNameParameterValue);
    }
    /**
     * Elimina varios equipos dados por su nombre.
     *
     * @param selectedTeams Lista de nombres de los equipos a eliminar.
     * @return Cadena con los resultados de la eliminación de cada equipo.
     */
    public String deleteTeams(ArrayList<String> selectedTeamsParameterValue) {
        StringBuilder resultLocalVariableValue = new StringBuilder("Deleted teams:\n");

        for (String teamReferenceDisplayNameLocalVariableValue : selectedTeamsParameterValue) {
            boolean successLocalVariableValue = teamReferenceDataAccessObjectFieldReference.deleteTeamByName(teamReferenceDisplayNameLocalVariableValue);
            if (successLocalVariableValue) {
                resultLocalVariableValue.append("- ").append(teamReferenceDisplayNameLocalVariableValue).append("\n");
            } else {
                resultLocalVariableValue.append("- Failed to delete: ").append(teamReferenceDisplayNameLocalVariableValue).append("\n");
            }
        }
        return resultLocalVariableValue.toString();
    }
    /**
     * Crea un nuevo equipo.
     *
     * @param team Objeto {@link Team} con la información del nuevo equipo.
     * @return {@code true} si se insertó correctamente, {@code false} en caso contrario.
     */
    public boolean createTeam(Team teamReferenceParameterValue) {
        return teamReferenceDataAccessObjectFieldReference.insertTeam(teamReferenceParameterValue.getName()) > 0;
    }
    /**
     * Devuelve una lista de equipos que no están asignados a ninguna liga.
     *
     * @param allTeams Lista de todos los equipos disponibles.
     * @return Lista de equipos no asignados a ninguna liga.
     */
    public ArrayList<Team> getTeamsNotInLeague(ArrayList<Team> allTeamsParameterValue) {
        ArrayList<Team> availableTeamsLocalVariableValue = new ArrayList<>();
        Set<Integer> assignedTeamReferenceIdentifiersLocalVariableValue;

        assignedTeamReferenceIdentifiersLocalVariableValue = teamReferenceDataAccessObjectFieldReference.getAssignedTeamIds();
        for (Team teamReferenceLocalVariableValue : allTeamsParameterValue) {
            if (!assignedTeamReferenceIdentifiersLocalVariableValue.contains(teamReferenceLocalVariableValue.getId())) {
                availableTeamsLocalVariableValue.add(teamReferenceLocalVariableValue);
            }
        }

        return availableTeamsLocalVariableValue;
    }

    // Prova Pablo
    /**
     * Comprueba si un equipo ya existe en la base de datos.
     *
     * @param teamName Nombre del equipo.
     * @return {@code true} si el equipo existe, {@code false} en caso contrario.
     */
    public boolean teamExists(String teamReferenceDisplayNameParameterValue2) {
        return teamReferenceDataAccessObjectFieldReference.getTeamId(teamReferenceDisplayNameParameterValue2) != -1;
    }


}
