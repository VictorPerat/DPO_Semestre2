package bussines.managers;

import bussines.LiveMatchesRegistry;
import bussines.objects.Game;
import bussines.objects.League;
import bussines.objects.LeagueListEntry;
import bussines.objects.Team;
import bussines.objects.TeamInfo;
import persistance.LeagueDao;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


/**
 * Gestiona las operaciones del liga.
 */
public class LeagueManager {


    private final LeagueDao leagueReferenceDataAccessObjectFieldReference;


    private TeamInfoManager informationTeamReferenceManagerServiceFieldReference;
    private TeamManager teamReferenceManagerServiceFieldReference;
    private GameManager gameEntityManagerServiceFieldReference;


    /**
     * Crea una instancia de el liga.
     */
    public LeagueManager() {
        leagueReferenceDataAccessObjectFieldReference = new LeagueDao();
        this.informationTeamReferenceManagerServiceFieldReference = new TeamInfoManager();
        this.teamReferenceManagerServiceFieldReference = new TeamManager();
        this.gameEntityManagerServiceFieldReference = new GameManager();
    }


    /**
     * Devuelve los ligas.
     *
     * @return los ligas.
     */
    public ArrayList<League> getAllLeagues() {
        return leagueReferenceDataAccessObjectFieldReference.getAllLeagues();
    }


    /**
     * Gestiona esta operacion.
     *
     * @param leagueReferenceIdentifierParameterValue liga identificador.
     * @return resultado de la operacion.
     */
    public StandingsTimeline computeStandingsTimeline(int leagueReferenceIdentifierParameterValue) {
        ArrayList<bussines.objects.Game> gamesLocalVariableValue =
                gameEntityManagerServiceFieldReference.getGamesByLeague(
                        leagueReferenceIdentifierParameterValue
                );

        if (gamesLocalVariableValue == null || gamesLocalVariableValue.isEmpty()) {
            return new StandingsTimeline(new String[0], new int[0][0], 0);
        }


        java.util.LinkedHashSet<String> teamSetLocalVariableValue =
                new java.util.LinkedHashSet<>();
        int totalRoundsLocalVariableValue = 0;
        for (bussines.objects.Game gameEntityLocalVariableValue : gamesLocalVariableValue) {
            teamSetLocalVariableValue.add(gameEntityLocalVariableValue.getNomLocal());
            teamSetLocalVariableValue.add(gameEntityLocalVariableValue.getNomVisitant());
            if (gameEntityLocalVariableValue.getJornada() > totalRoundsLocalVariableValue) {
                totalRoundsLocalVariableValue = gameEntityLocalVariableValue.getJornada();
            }
        }

        String[] teamNamesLocalVariableValue =
                teamSetLocalVariableValue.toArray(new String[0]);
        int teamCountLocalVariableValue = teamNamesLocalVariableValue.length;


        java.util.HashMap<String, Integer> teamIndexLocalVariableValue =
                new java.util.HashMap<>();
        for (int indexCounterLocalVariableValue = 0;
             indexCounterLocalVariableValue < teamCountLocalVariableValue;
             indexCounterLocalVariableValue++) {
            teamIndexLocalVariableValue.put(
                    teamNamesLocalVariableValue[indexCounterLocalVariableValue],
                    indexCounterLocalVariableValue
            );
        }


        int[][] cumulativePointsLocalVariableValue =
                new int[teamCountLocalVariableValue][totalRoundsLocalVariableValue];


        for (int roundIndexLocalVariableValue = 0;
             roundIndexLocalVariableValue < totalRoundsLocalVariableValue;
             roundIndexLocalVariableValue++) {

            int currentRoundLocalVariableValue = roundIndexLocalVariableValue + 1;


            if (roundIndexLocalVariableValue > 0) {
                for (int teamIdxLocalVariableValue = 0;
                     teamIdxLocalVariableValue < teamCountLocalVariableValue;
                     teamIdxLocalVariableValue++) {
                    cumulativePointsLocalVariableValue[teamIdxLocalVariableValue][roundIndexLocalVariableValue] =
                            cumulativePointsLocalVariableValue[teamIdxLocalVariableValue][roundIndexLocalVariableValue - 1];
                }
            }


            for (bussines.objects.Game gameEntityLocalVariableValue : gamesLocalVariableValue) {
                if (gameEntityLocalVariableValue.getJornada() != currentRoundLocalVariableValue) {
                    continue;
                }
                if (!gameEntityLocalVariableValue.isAcabat()) {
                    continue;
                }

                String winnerLocalVariableValue =
                        gameEntityLocalVariableValue.getWinnerName();
                String homeLocalVariableValue =
                        gameEntityLocalVariableValue.getNomLocal();
                String awayLocalVariableValue =
                        gameEntityLocalVariableValue.getNomVisitant();

                Integer homeIdxLocalVariableValue =
                        teamIndexLocalVariableValue.get(homeLocalVariableValue);
                Integer awayIdxLocalVariableValue =
                        teamIndexLocalVariableValue.get(awayLocalVariableValue);

                if (winnerLocalVariableValue == null
                        || winnerLocalVariableValue.equalsIgnoreCase("DRAW")) {
                    if (homeIdxLocalVariableValue != null) {
                        cumulativePointsLocalVariableValue[homeIdxLocalVariableValue][roundIndexLocalVariableValue] += 1;
                    }
                    if (awayIdxLocalVariableValue != null) {
                        cumulativePointsLocalVariableValue[awayIdxLocalVariableValue][roundIndexLocalVariableValue] += 1;
                    }
                } else {
                    Integer winnerIdxLocalVariableValue =
                            teamIndexLocalVariableValue.get(winnerLocalVariableValue);
                    if (winnerIdxLocalVariableValue != null) {
                        cumulativePointsLocalVariableValue[winnerIdxLocalVariableValue][roundIndexLocalVariableValue] += 3;
                    }
                }
            }
        }

        return new StandingsTimeline(
                teamNamesLocalVariableValue,
                cumulativePointsLocalVariableValue,
                totalRoundsLocalVariableValue
        );
    }


    /**
     * Agrupa la logica de esta parte de la aplicacion.
     */
    public static class StandingsTimeline {
        private final String[] teamNamesFieldReference;
        private final int[][] cumulativePointsFieldReference;
        private final int totalRoundsFieldReference;


        /**
         * Crea una instancia de standingstimeline.
         *
         * @param teamNamesParameterValue equipo que usa la operacion.
         * @param cumulativePointsParameterValue dato de entrada de la operacion.
         * @param totalRoundsParameterValue dato de entrada de la operacion.
         */
        public StandingsTimeline(String[] teamNamesParameterValue,
                                 int[][] cumulativePointsParameterValue,
                                 int totalRoundsParameterValue) {
            this.teamNamesFieldReference = teamNamesParameterValue;
            this.cumulativePointsFieldReference = cumulativePointsParameterValue;
            this.totalRoundsFieldReference = totalRoundsParameterValue;
        }


        /**
         * Devuelve el equipo.
         *
         * @return el equipo.
         */
        public String[] getTeamNames() { return teamNamesFieldReference; }


        /**
         * Devuelve el contenido.
         *
         * @return el contenido.
         */
        public int[][] getCumulativePoints() { return cumulativePointsFieldReference; }


        /**
         * Devuelve el contenido.
         *
         * @return el contenido.
         */
        public int getTotalRounds() { return totalRoundsFieldReference; }
    }


    /**
     * Devuelve el liga.
     *
     * @param leagueReferenceIdentifierParameterValue liga identificador.
     * @return el liga.
     */
    public String getLeagueStatusLabel(int leagueReferenceIdentifierParameterValue) {
        ArrayList<Game> gamesLocalVariableValue =
                gameEntityManagerServiceFieldReference.getGamesByLeague(
                        leagueReferenceIdentifierParameterValue
                );

        if (gamesLocalVariableValue == null || gamesLocalVariableValue.isEmpty()) {
            return LeagueListEntry.STATUS_PENDING;
        }

        boolean allFinishedLocalVariableValue = true;
        int currentRoundLocalVariableValue = 0;

        for (Game gameEntityLocalVariableValue : gamesLocalVariableValue) {
            if (!gameEntityLocalVariableValue.isAcabat()) {
                allFinishedLocalVariableValue = false;
            }
            if (gameEntityLocalVariableValue.isComençat()
                    && gameEntityLocalVariableValue.getJornada() > currentRoundLocalVariableValue) {
                currentRoundLocalVariableValue = gameEntityLocalVariableValue.getJornada();
            }
        }

        if (allFinishedLocalVariableValue) {
            return LeagueListEntry.STATUS_FINISHED;
        }
        if (currentRoundLocalVariableValue == 0) {
            return LeagueListEntry.STATUS_PENDING;
        }
        return "Round " + currentRoundLocalVariableValue;
    }


    /**
     * Devuelve el liga.
     *
     * @param isAdminParameterValue administrador que usa la operacion.
     * @param userTeamNameParameterValue nombre del equipo.
     * @return el liga.
     */
    public ArrayList<LeagueListEntry> getLeagueListEntries(boolean isAdminParameterValue,
                                                           String userTeamNameParameterValue) {
        ArrayList<League> leaguesLocalVariableValue;
        if (isAdminParameterValue) {
            leaguesLocalVariableValue = getAllLeagues();
        } else if (userTeamNameParameterValue != null) {
            leaguesLocalVariableValue = getLeaguesByUserTeam(userTeamNameParameterValue);
        } else {
            leaguesLocalVariableValue = new ArrayList<>();
        }

        ArrayList<LeagueListEntry> entriesLocalVariableValue = new ArrayList<>();
        for (League leagueReferenceLocalVariableValue : leaguesLocalVariableValue) {
            int leagueReferenceIdentifierLocalVariableValue =
                    getLeagueIdByName(leagueReferenceLocalVariableValue.getName());

            int teamCountLocalVariableValue =
                    informationTeamReferenceManagerServiceFieldReference
                            .getInfoTeamsOfLeague(leagueReferenceIdentifierLocalVariableValue)
                            .size();

            String statusLabelLocalVariableValue =
                    getLeagueStatusLabel(leagueReferenceIdentifierLocalVariableValue);

            entriesLocalVariableValue.add(new LeagueListEntry(
                    leagueReferenceLocalVariableValue,
                    teamCountLocalVariableValue,
                    statusLabelLocalVariableValue
            ));
        }

        return entriesLocalVariableValue;
    }


    /**
     * Devuelve el ligas usuario equipo.
     *
     * @param teamReferenceDisplayNameParameterValue nombre del equipo.
     * @return el ligas usuario equipo.
     */
    public ArrayList<League> getLeaguesByUserTeam(String teamReferenceDisplayNameParameterValue) {
        return leagueReferenceDataAccessObjectFieldReference.getLeaguesByUserTeam(teamReferenceDisplayNameParameterValue);
    }


    /**
     * Elimina el liga nombre.
     *
     * @param displayNameParameterValue nombre que se muestra.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean deleteLeagueByName(String displayNameParameterValue) {
        return leagueReferenceDataAccessObjectFieldReference.deleteLeagueByName(displayNameParameterValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param leaguesParameterValue ligas que usa la operacion.
     * @param displayNameParameterValue2 nombre que usa la operacion.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
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
     * Devuelve el liga.
     *
     * @return el liga.
     */
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


    /**
     * Gestiona esta operacion.
     *
     * @param dateParameterValue dato de entrada de la operacion.
     * @return resultado de la operacion.
     */
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


    /**
     * Gestiona esta operacion.
     *
     * @param timeParameterValue dato de entrada de la operacion.
     * @param dateParameterValue2 dato de entrada de la operacion.
     * @return resultado de la operacion.
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
            return 0;
        } catch (DateTimeParseException eventArgumentExceptionParameter2) {
            return 2;
        }
    }


    /**
     * Crea el liga.
     *
     * @param leagueReferenceDisplayNameParameterValue nombre de la liga.
     * @param startDateParameterValue dato de entrada de la operacion.
     * @param teamReferenceNamesParameterValue equipo que usa la operacion.
     */
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


    /**
     * Devuelve el liga nombre.
     *
     * @param leagueReferenceDisplayNameParameterValue2 nombre de la liga.
     * @return el liga nombre.
     */
    public int getLeagueIdByName(String leagueReferenceDisplayNameParameterValue2) {
        return leagueReferenceDataAccessObjectFieldReference.getLeagueIdByName(leagueReferenceDisplayNameParameterValue2);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param teamReferenceNamesParameterValue2 equipo que usa la operacion.
     * @param leagueReferenceIdentifierParameterValue liga identificador.
     * @param timeParameterValue2 dato de entrada de la operacion.
     */
    public void generateAndInsertMatchesForLeague(ArrayList<String> teamReferenceNamesParameterValue2,
                                                  int leagueReferenceIdentifierParameterValue,
                                                  LocalDateTime timeParameterValue2) {

        int numberTeamsLocalVariableValue = teamReferenceNamesParameterValue2.size();
        boolean evenLocalVariableValue = (numberTeamsLocalVariableValue % 2 == 0);


        if (!evenLocalVariableValue) {
            teamReferenceNamesParameterValue2.add("DESCANSA");
        }

        int totalRoundsLocalVariableValue = teamReferenceNamesParameterValue2.size() - 1;
        int numberMatchesPerRoundLocalVariableValue = teamReferenceNamesParameterValue2.size() / 2;

        ArrayList<String> rotatedTeamsLocalVariableValue = new ArrayList<>(teamReferenceNamesParameterValue2);
        String fixedTeamReferenceLocalVariableValue = rotatedTeamsLocalVariableValue.remove(0);


        LocalDateTime jornadaTimeLocalVariableValue = timeParameterValue2.plusMinutes(1);


        int matchDurationMinutesLocalVariableValue = ConfigManager.getDurationMatch();
        long minutesBetweenRoundsLocalVariableValue = matchDurationMinutesLocalVariableValue + 1L;

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


                if (localLocalVariableValue.equals("DESCANSA") || visitanteLocalVariableValue.equals("DESCANSA")) {
                    continue;
                }

                leagueReferenceDataAccessObjectFieldReference.insertGame(
                        localLocalVariableValue,
                        visitanteLocalVariableValue,
                        leagueReferenceIdentifierParameterValue,
                        roundLocalVariableValue,
                        jornadaTimeLocalVariableValue
                );
            }


            jornadaTimeLocalVariableValue =
                    jornadaTimeLocalVariableValue.plusMinutes(minutesBetweenRoundsLocalVariableValue);


            String lastLocalVariableValue =
                    rotatedTeamsLocalVariableValue.remove(rotatedTeamsLocalVariableValue.size() - 1);
            rotatedTeamsLocalVariableValue.add(0, lastLocalVariableValue);
        }
    }


    /**
     * Elimina el liga.
     *
     * @param leaguesToDeleteParameterValue ligas que usa la operacion.
     * @return resultado de la operacion.
     */
    public String deleteLeague(ArrayList<String> leaguesToDeleteParameterValue) {
        StringBuilder messageLocalVariableValue = new StringBuilder("Deleted leagues:\n");

        for (String leagueReferenceDisplayNameLocalVariableValue : leaguesToDeleteParameterValue) {

            int leagueReferenceIdentifierLocalVariableValue2 =
                    getLeagueIdByName(leagueReferenceDisplayNameLocalVariableValue);


            LiveMatchesRegistry.getInstance().abortMatchesByLeague(
                    leagueReferenceIdentifierLocalVariableValue2
            );

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