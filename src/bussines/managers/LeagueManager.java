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
 * Esta clase se encarga de gestionar lo relacionado con las ligas.
 */
public class LeagueManager {

    // DAO que se usa para acceder a los datos de las ligas
    private final LeagueDao leagueReferenceDataAccessObjectFieldReference;

    // Managers auxiliares para trabajar con equipos, información y partidos
    private TeamInfoManager informationTeamReferenceManagerServiceFieldReference;
    private TeamManager teamReferenceManagerServiceFieldReference;
    private GameManager gameEntityManagerServiceFieldReference;

    // Constructor que inicializa los objetos necesarios
    public LeagueManager() {
        leagueReferenceDataAccessObjectFieldReference = new LeagueDao();
        this.informationTeamReferenceManagerServiceFieldReference = new TeamInfoManager();
        this.teamReferenceManagerServiceFieldReference = new TeamManager();
        this.gameEntityManagerServiceFieldReference = new GameManager();
    }

    // Devuelve todas las ligas guardadas
    public ArrayList<League> getAllLeagues() {
        return leagueReferenceDataAccessObjectFieldReference.getAllLeagues();
    }

    // Devuelve las ligas en las que participa un equipo
    public ArrayList<League> getLeaguesByUserTeam(String teamReferenceDisplayNameParameterValue) {
        return leagueReferenceDataAccessObjectFieldReference.getLeaguesByUserTeam(teamReferenceDisplayNameParameterValue);
    }

    // Borra una liga usando su nombre
    public boolean deleteLeagueByName(String displayNameParameterValue) {
        return leagueReferenceDataAccessObjectFieldReference.deleteLeagueByName(displayNameParameterValue);
    }

    // Comprueba si una liga ya existe en una lista
    public boolean leagueExist(ArrayList<League> leaguesParameterValue, String displayNameParameterValue2) {
        for (League leagueReferenceLocalVariableValue : leaguesParameterValue) {
            if (leagueReferenceLocalVariableValue.getName().equalsIgnoreCase(displayNameParameterValue2)) {
                return true;
            }
        }
        return false;
    }

    // Devuelve una lista con los ids de todas las ligas
    public ArrayList<Integer> getAllLeagueIds() {
        ArrayList<Integer> leagueReferenceIdentifiersLocalVariableValue = new ArrayList<>();
        ArrayList<League> leaguesLocalVariableValue = leagueReferenceDataAccessObjectFieldReference.getAllLeagues();

        for (League leagueReferenceLocalVariableValue2 : leaguesLocalVariableValue) {
            int leagueReferenceIdentifierLocalVariableValue =
                    leagueReferenceDataAccessObjectFieldReference.getLeagueIdByName(
                            leagueReferenceLocalVariableValue2.getName()
                    );
            leagueReferenceIdentifiersLocalVariableValue.add(leagueReferenceIdentifierLocalVariableValue);
        }

        return leagueReferenceIdentifiersLocalVariableValue;
    }

    // Comprueba si una fecha es válida o si ya ha pasado
    public int checkDateStatus(String dateParameterValue) {
        try {
            LocalDate inputDateLocalVariableValue = LocalDate.parse(dateParameterValue);
            LocalDate todayLocalVariableValue = LocalDate.now();

            if (inputDateLocalVariableValue.isBefore(todayLocalVariableValue)) {
                return 1;
            } else {
                return 0;
            }
        } catch (DateTimeParseException eventArgumentExceptionParameter) {
            return 2;
        }
    }

    // Comprueba si una fecha y una hora son válidas o si ya han pasado
    public int checkTimeStatus(String timeParameterValue, String dateParameterValue2) {
        try {
            LocalDate inputDateLocalVariableValue2 = LocalDate.parse(dateParameterValue2);
            LocalTime inputTimeLocalVariableValue = LocalTime.parse(timeParameterValue);
            LocalDateTime inputDateTimeLocalVariableValue = LocalDateTime.of(inputDateLocalVariableValue2, inputTimeLocalVariableValue);
            LocalDateTime nowLocalVariableValue = LocalDateTime.now();

            if (inputDateTimeLocalVariableValue.isBefore(nowLocalVariableValue)) {
                return 1;
            }
            return 0;
        } catch (DateTimeParseException eventArgumentExceptionParameter2) {
            return 2;
        }
    }

    // Crea una nueva liga y la guarda en la base de datos
    public void createLeague(String leagueReferenceDisplayNameParameterValue,
                             String startDateParameterValue,
                             ArrayList<String> teamReferenceNamesParameterValue) {

        League newLeagueReferenceLocalVariableValue = new League(
                leagueReferenceDisplayNameParameterValue,
                startDateParameterValue,
                teamReferenceNamesParameterValue,
                0
        );

        leagueReferenceDataAccessObjectFieldReference.createLeague(newLeagueReferenceLocalVariableValue);
    }

    // Devuelve el id de una liga a partir de su nombre
    public int getLeagueIdByName(String leagueReferenceDisplayNameParameterValue2) {
        return leagueReferenceDataAccessObjectFieldReference.getLeagueIdByName(leagueReferenceDisplayNameParameterValue2);
    }

    // Devuelve el id de la liga donde juega un equipo
    public int getLeagueIdByTeam(String teamReferenceDisplayNameParameterValue2) {
        return leagueReferenceDataAccessObjectFieldReference.getLeagueIdByTeam(teamReferenceDisplayNameParameterValue2);
    }

    // Genera los partidos de una liga usando round-robin y los guarda
    public void generateAndInsertMatchesForLeague(ArrayList<String> teamReferenceNamesParameterValue2,
                                                  int leagueReferenceIdentifierParameterValue,
                                                  LocalDateTime timeParameterValue2) {

        int numberTeamsLocalVariableValue = teamReferenceNamesParameterValue2.size();
        boolean evenLocalVariableValue = (numberTeamsLocalVariableValue % 2 == 0);

        // Si hay un número impar de equipos, se añade un equipo ficticio
        if (!evenLocalVariableValue) {
            teamReferenceNamesParameterValue2.add("DESCANSA");
        }

        int totalRoundsLocalVariableValue = teamReferenceNamesParameterValue2.size() - 1;
        int numberMatchesPerRoundLocalVariableValue = teamReferenceNamesParameterValue2.size() / 2;

        ArrayList<String> rotatedTeamsLocalVariableValue = new ArrayList<>(teamReferenceNamesParameterValue2);
        String fixedTeamReferenceLocalVariableValue = rotatedTeamsLocalVariableValue.remove(0);

        for (int roundLocalVariableValue = 1; roundLocalVariableValue <= totalRoundsLocalVariableValue * 2; roundLocalVariableValue++) {
            for (int indexCounterLocalVariableValue = 0; indexCounterLocalVariableValue < numberMatchesPerRoundLocalVariableValue; indexCounterLocalVariableValue++) {

                String localLocalVariableValue;
                String visitanteLocalVariableValue;

                if (roundLocalVariableValue <= totalRoundsLocalVariableValue) {
                    localLocalVariableValue = (indexCounterLocalVariableValue == 0)
                            ? fixedTeamReferenceLocalVariableValue
                            : rotatedTeamsLocalVariableValue.get(indexCounterLocalVariableValue - 1);

                    visitanteLocalVariableValue =
                            rotatedTeamsLocalVariableValue.get(rotatedTeamsLocalVariableValue.size() - indexCounterLocalVariableValue - 1);
                } else {
                    visitanteLocalVariableValue = (indexCounterLocalVariableValue == 0)
                            ? fixedTeamReferenceLocalVariableValue
                            : rotatedTeamsLocalVariableValue.get(indexCounterLocalVariableValue - 1);

                    localLocalVariableValue =
                            rotatedTeamsLocalVariableValue.get(rotatedTeamsLocalVariableValue.size() - indexCounterLocalVariableValue - 1);
                }

                // Si uno de los equipos descansa, no se crea partido
                if (localLocalVariableValue.equals("DESCANSA") || visitanteLocalVariableValue.equals("DESCANSA")) {
                    continue;
                }

                leagueReferenceDataAccessObjectFieldReference.insertGame(
                        localLocalVariableValue,
                        visitanteLocalVariableValue,
                        leagueReferenceIdentifierParameterValue,
                        roundLocalVariableValue,
                        timeParameterValue2
                );
            }

            // Pasa al siguiente bloque horario
            timeParameterValue2 = timeParameterValue2.plusMinutes(2);

            // Rota los equipos para la siguiente jornada
            String lastLocalVariableValue =
                    rotatedTeamsLocalVariableValue.remove(rotatedTeamsLocalVariableValue.size() - 1);
            rotatedTeamsLocalVariableValue.add(0, lastLocalVariableValue);
        }
    }

    // Borra varias ligas y también elimina sus equipos y partidos relacionados
    public String deleteLeague(ArrayList<String> leaguesToDeleteParameterValue) {
        StringBuilder messageLocalVariableValue = new StringBuilder("Deleted leagues:\n");

        for (String leagueReferenceDisplayNameLocalVariableValue : leaguesToDeleteParameterValue) {

            int leagueReferenceIdentifierLocalVariableValue2 =
                    getLeagueIdByName(leagueReferenceDisplayNameLocalVariableValue);

            ArrayList<TeamInfo> teamsLocalVariableValue =
                    informationTeamReferenceManagerServiceFieldReference.getInfoTeamsOfLeague(
                            leagueReferenceIdentifierLocalVariableValue2
                    );

            ArrayList<Team> matchingTeamsLocalVariableValue = new ArrayList<>();
            ArrayList<Team> teamslistLocalVariableValue = teamReferenceManagerServiceFieldReference.getAllTeams();

            for (TeamInfo informationLocalVariableValue : teamsLocalVariableValue) {
                int informationTeamReferenceIdentifierLocalVariableValue =
                        informationLocalVariableValue.getTeamId();

                for (Team teamReferenceLocalVariableValue : teamslistLocalVariableValue) {
                    if (teamReferenceLocalVariableValue.getId() == informationTeamReferenceIdentifierLocalVariableValue) {
                        matchingTeamsLocalVariableValue.add(teamReferenceLocalVariableValue);
                        break;
                    }
                }
            }

            for (TeamInfo teamReferenceInformationLocalVariableValue : teamsLocalVariableValue) {
                informationTeamReferenceManagerServiceFieldReference.deleteInfoTeam(
                        teamReferenceInformationLocalVariableValue.getTeamId()
                );
            }

            for (Team teamReferenceLocalVariableValue2 : matchingTeamsLocalVariableValue) {
                gameEntityManagerServiceFieldReference.deleteGamesByTeam(
                        teamReferenceLocalVariableValue2.getName()
                );
            }

            boolean successLocalVariableValue =
                    leagueReferenceDataAccessObjectFieldReference.deleteLeagueByName(
                            leagueReferenceDisplayNameLocalVariableValue
                    );

            if (successLocalVariableValue) {
                messageLocalVariableValue.append("- ")
                        .append(leagueReferenceDisplayNameLocalVariableValue)
                        .append("\n");
            } else {
                messageLocalVariableValue.append("- Failed to delete: ")
                        .append(leagueReferenceDisplayNameLocalVariableValue)
                        .append("\n");
            }
        }

        return messageLocalVariableValue.toString();
    }
}