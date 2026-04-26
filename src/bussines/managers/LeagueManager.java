package bussines.managers;

import bussines.objects.League;
import bussines.objects.Team;
import bussines.objects.TeamInfo;
import persistance.LeagueDao;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * La clase {@code LeagueManager} gestiona las operaciones relacionadas con ligas,
 * incluyendo la creación, validación de fechas y tiempos, obtención y eliminación de ligas,
 * y la generación de partidos con algoritmo round-robin.
 */
public class LeagueManager {
    private final LeagueDao leagueReferenceDataAccessObjectFieldReference;
    private TeamInfoManager informationTeamReferenceManagerServiceFieldReference;
    private TeamManager teamReferenceManagerServiceFieldReference;
    private GameManager gameEntityManagerServiceFieldReference;
    /**
     * Constructor que inicializa los DAOs y gestores necesarios.
     */
    public LeagueManager() {
        leagueReferenceDataAccessObjectFieldReference = new LeagueDao();
        this.informationTeamReferenceManagerServiceFieldReference = new TeamInfoManager();
        this.teamReferenceManagerServiceFieldReference = new TeamManager();
        this.gameEntityManagerServiceFieldReference = new GameManager();
    }
    /**
     * Devuelve una lista de todas las ligas existentes.
     * @return lista de ligas
     */
    public ArrayList<League> getAllLeagues() {
        return leagueReferenceDataAccessObjectFieldReference.getAllLeagues();
    }
    /**
     * Devuelve las ligas en las que participa un equipo determinado.
     * @param teamName nombre del equipo
     * @return lista de ligas
     */
    public ArrayList<League> getLeaguesByUserTeam(String teamReferenceDisplayNameParameterValue) { return leagueReferenceDataAccessObjectFieldReference.getLeaguesByUserTeam(teamReferenceDisplayNameParameterValue);}
    /**
     * Elimina una liga por su nombre.
     * @param name nombre de la liga
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean deleteLeagueByName(String displayNameParameterValue) {
        return leagueReferenceDataAccessObjectFieldReference.deleteLeagueByName(displayNameParameterValue);
    }
    /**
     * Verifica si una liga con cierto nombre existe en la lista.
     * @param leagues lista de ligas
     * @param name nombre a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean leagueExist(ArrayList<League> leaguesParameterValue, String displayNameParameterValue2) {
        for (League leagueReferenceLocalVariableValue : leaguesParameterValue) {
            if (leagueReferenceLocalVariableValue.getName().equalsIgnoreCase(displayNameParameterValue2)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Obtiene los identificadores de todas las ligas.
     * @return lista de IDs
     */
    public ArrayList<Integer> getAllLeagueIds() {
        ArrayList<Integer> leagueReferenceIdentifiersLocalVariableValue = new ArrayList<>();
        ArrayList<League> leaguesLocalVariableValue = leagueReferenceDataAccessObjectFieldReference.getAllLeagues();

        for (League leagueReferenceLocalVariableValue2 : leaguesLocalVariableValue) {
            int leagueReferenceIdentifierLocalVariableValue = leagueReferenceDataAccessObjectFieldReference.getLeagueIdByName(leagueReferenceLocalVariableValue2.getName());
            leagueReferenceIdentifiersLocalVariableValue.add(leagueReferenceIdentifierLocalVariableValue);
        }

        return leagueReferenceIdentifiersLocalVariableValue;
    }
    /**
     * Verifica si una fecha es anterior a la actual.
     * @param date fecha en formato yyyy-MM-dd
     * @return 0 si válida, 1 si anterior, 2 si formato incorrecto
     */
    public int checkDateStatus(String dateParameterValue) {
        try {
            LocalDate inputDateLocalVariableValue = LocalDate.parse(dateParameterValue);
            LocalDate todayLocalVariableValue = LocalDate.now();

            if (inputDateLocalVariableValue.isBefore(todayLocalVariableValue)) {
                return 1; // Data anterior
            } else {
                return 0; // Data vàlida
            }
        } catch (DateTimeParseException eventArgumentExceptionParameter) {
            return 2; // Format incorrecte
        }
    }
    /**
     * Verifica si una fecha y hora combinadas son anteriores al momento actual.
     * @param time hora en formato HH:mm
     * @param date fecha en formato yyyy-MM-dd
     * @return 0 si válida, 1 si anterior, 2 si formato incorrecto
     */
    public int checkTimeStatus(String timeParameterValue, String dateParameterValue2) {
        try {
            LocalDate inputDateLocalVariableValue2 = LocalDate.parse(dateParameterValue2);
            LocalTime inputTimeLocalVariableValue = LocalTime.parse(timeParameterValue);
            LocalDateTime inputDateTimeLocalVariableValue = LocalDateTime.of(inputDateLocalVariableValue2, inputTimeLocalVariableValue);
            LocalDateTime nowLocalVariableValue = LocalDateTime.now();
            if (inputDateTimeLocalVariableValue.isBefore(nowLocalVariableValue)) {
                return 1;
            }
            return 0; // formato correcto
        } catch (DateTimeParseException eventArgumentExceptionParameter2) {
            return 2; // formato incorrecto
        }
    }
    /**
     * Crea una nueva liga y la guarda en la base de datos.
     * @param leagueName nombre de la liga
     * @param startDate fecha de inicio
     * @param teamNames lista de equipos participantes
     */
    public void createLeague(String leagueReferenceDisplayNameParameterValue, String startDateParameterValue, ArrayList<String> teamReferenceNamesParameterValue) {
        League newLeagueReferenceLocalVariableValue = new League(leagueReferenceDisplayNameParameterValue, startDateParameterValue, teamReferenceNamesParameterValue, 0);
        leagueReferenceDataAccessObjectFieldReference.createLeague(newLeagueReferenceLocalVariableValue);
    }
    /**
     * Obtiene el ID de una liga a partir de su nombre.
     * @param leagueName nombre de la liga
     * @return ID de la liga
     */
    public int getLeagueIdByName(String leagueReferenceDisplayNameParameterValue2) {
        return leagueReferenceDataAccessObjectFieldReference.getLeagueIdByName(leagueReferenceDisplayNameParameterValue2);
    }
    /**
     * Obtiene el ID de la liga en la que participa un equipo.
     * @param teamName nombre del equipo
     * @return ID de la liga
     */
    public int getLeagueIdByTeam(String teamReferenceDisplayNameParameterValue2) {
        return leagueReferenceDataAccessObjectFieldReference.getLeagueIdByTeam(teamReferenceDisplayNameParameterValue2);
    }
    /**
     * Genera e inserta los partidos de una liga usando el algoritmo round-robin.
     * @param teamNames nombres de los equipos
     * @param leagueId ID de la liga
     * @param time fecha y hora inicial para los partidos
     */
    public void generateAndInsertMatchesForLeague(ArrayList<String> teamReferenceNamesParameterValue2, int leagueReferenceIdentifierParameterValue, LocalDateTime timeParameterValue2) {

        // Algoritmo round-robin
        int numberTeamsLocalVariableValue = teamReferenceNamesParameterValue2.size();
        boolean evenLocalVariableValue = (numberTeamsLocalVariableValue % 2 == 0);
        if (!evenLocalVariableValue) teamReferenceNamesParameterValue2.add("DESCANSA");  // Añadir equipo fantasma si impar

        int totalRoundsLocalVariableValue = teamReferenceNamesParameterValue2.size() - 1;
        int numberMatchesPerRoundLocalVariableValue = teamReferenceNamesParameterValue2.size() / 2;

        ArrayList<String> rotatedTeamsLocalVariableValue = new ArrayList<>(teamReferenceNamesParameterValue2);
        String fixedTeamReferenceLocalVariableValue = rotatedTeamsLocalVariableValue.remove(0);  // Primer equipo fijo

        int matchIdentifierCounterLocalVariableValue = 1;

        for (int roundLocalVariableValue = 1; roundLocalVariableValue <= totalRoundsLocalVariableValue * 2; roundLocalVariableValue++) {
            for (int indexCounterLocalVariableValue = 0; indexCounterLocalVariableValue < numberMatchesPerRoundLocalVariableValue; indexCounterLocalVariableValue++) {
                String localLocalVariableValue, visitanteLocalVariableValue;

                if (roundLocalVariableValue <= totalRoundsLocalVariableValue) {
                    localLocalVariableValue = (indexCounterLocalVariableValue == 0) ? fixedTeamReferenceLocalVariableValue : rotatedTeamsLocalVariableValue.get(indexCounterLocalVariableValue - 1);
                    visitanteLocalVariableValue = rotatedTeamsLocalVariableValue.get(rotatedTeamsLocalVariableValue.size() - indexCounterLocalVariableValue - 1);
                } else {
                    visitanteLocalVariableValue = (indexCounterLocalVariableValue == 0) ? fixedTeamReferenceLocalVariableValue : rotatedTeamsLocalVariableValue.get(indexCounterLocalVariableValue - 1);
                    localLocalVariableValue = rotatedTeamsLocalVariableValue.get(rotatedTeamsLocalVariableValue.size() - indexCounterLocalVariableValue - 1);
                }

                // Ignorar partidos con "DESCANSA"
                if (localLocalVariableValue.equals("DESCANSA") || visitanteLocalVariableValue.equals("DESCANSA")) continue;
                leagueReferenceDataAccessObjectFieldReference.insertGame(localLocalVariableValue, visitanteLocalVariableValue, leagueReferenceIdentifierParameterValue, roundLocalVariableValue, timeParameterValue2);
            }
            timeParameterValue2 = timeParameterValue2.plusMinutes(2); //añade

            // Rotar equipos
            String lastLocalVariableValue = rotatedTeamsLocalVariableValue.remove(rotatedTeamsLocalVariableValue.size() - 1);
            rotatedTeamsLocalVariableValue.add(0, lastLocalVariableValue);
        }
    }

    /**
     * Elimina múltiples ligas, así como sus equipos y partidos asociados.
     * @param leaguesToDelete lista de nombres de ligas a eliminar
     * @return mensaje indicando qué ligas se eliminaron o fallaron
     */
    public String deleteLeague(ArrayList <String> leaguesToDeleteParameterValue) {
        StringBuilder messageLocalVariableValue = new StringBuilder("Deleted leagues:\n");

        for (String leagueReferenceDisplayNameLocalVariableValue : leaguesToDeleteParameterValue) {

            int leagueReferenceIdentifierLocalVariableValue2 = getLeagueIdByName(leagueReferenceDisplayNameLocalVariableValue);
            ArrayList<TeamInfo> teamsLocalVariableValue =  informationTeamReferenceManagerServiceFieldReference.getInfoTeamsOfLeague(leagueReferenceIdentifierLocalVariableValue2);

            ArrayList<Team> matchingTeamsLocalVariableValue = new ArrayList<>();
            ArrayList<Team> teamslistLocalVariableValue = teamReferenceManagerServiceFieldReference.getAllTeams();
            for (TeamInfo informationLocalVariableValue : teamsLocalVariableValue) {
                int informationTeamReferenceIdentifierLocalVariableValue = informationLocalVariableValue.getTeamId();
                for (Team teamReferenceLocalVariableValue : teamslistLocalVariableValue) {
                    if (teamReferenceLocalVariableValue.getId() == informationTeamReferenceIdentifierLocalVariableValue) {
                        matchingTeamsLocalVariableValue.add(teamReferenceLocalVariableValue);
                        break; // opcional, si los IDs son únicos
                    }
                }
            }

            for (TeamInfo teamReferenceInformationLocalVariableValue : teamsLocalVariableValue) {
                informationTeamReferenceManagerServiceFieldReference.deleteInfoTeam(teamReferenceInformationLocalVariableValue.getTeamId());
            }
            for (Team teamReferenceLocalVariableValue2 : matchingTeamsLocalVariableValue) {
                gameEntityManagerServiceFieldReference.deleteGamesByTeam(teamReferenceLocalVariableValue2.getName());
            }
            boolean successLocalVariableValue = leagueReferenceDataAccessObjectFieldReference.deleteLeagueByName(leagueReferenceDisplayNameLocalVariableValue);
            if (successLocalVariableValue) {
                messageLocalVariableValue.append("- ").append(leagueReferenceDisplayNameLocalVariableValue).append("\n");
            } else {
                messageLocalVariableValue.append("- Failed to delete: ").append(leagueReferenceDisplayNameLocalVariableValue).append("\n");
            }
        }
        return messageLocalVariableValue.toString();
    }
}
