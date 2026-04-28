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

    /**
     * Reconstruye la evolución de puntos por jornada de cada equipo de
     * una liga (apartado 2.7.1 del enunciado).
     *
     * Recorre los partidos por orden de jornada y asigna 3 puntos al
     * ganador (o 1 a cada uno si fue empate). Los partidos no acabados
     * no aportan puntos. Devuelve un objeto con:
     *  - matriz [equipo][jornada] con los puntos acumulados.
     *  - lista de nombres de equipos (mismo orden que la matriz).
     *  - número total de jornadas de la liga.
     */
    public StandingsTimeline computeStandingsTimeline(int leagueReferenceIdentifierParameterValue) {
        ArrayList<bussines.objects.Game> gamesLocalVariableValue =
                gameEntityManagerServiceFieldReference.getGamesByLeague(
                        leagueReferenceIdentifierParameterValue
                );

        if (gamesLocalVariableValue == null || gamesLocalVariableValue.isEmpty()) {
            return new StandingsTimeline(new String[0], new int[0][0], 0);
        }

        // Determinamos el conjunto de equipos y el número total de jornadas
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

        // Mapa nombre -> índice en el array final
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

        // Inicialmente todos los equipos tienen 0 puntos en cada jornada
        int[][] cumulativePointsLocalVariableValue =
                new int[teamCountLocalVariableValue][totalRoundsLocalVariableValue];

        // Para cada jornada, los puntos acumulados son los de la
        // jornada anterior + lo ganado en esta jornada concreta.
        for (int roundIndexLocalVariableValue = 0;
             roundIndexLocalVariableValue < totalRoundsLocalVariableValue;
             roundIndexLocalVariableValue++) {

            int currentRoundLocalVariableValue = roundIndexLocalVariableValue + 1;

            // Copiar acumulado de la jornada anterior
            if (roundIndexLocalVariableValue > 0) {
                for (int teamIdxLocalVariableValue = 0;
                     teamIdxLocalVariableValue < teamCountLocalVariableValue;
                     teamIdxLocalVariableValue++) {
                    cumulativePointsLocalVariableValue[teamIdxLocalVariableValue][roundIndexLocalVariableValue] =
                            cumulativePointsLocalVariableValue[teamIdxLocalVariableValue][roundIndexLocalVariableValue - 1];
                }
            }

            // Aplicar resultados de los partidos acabados en esta jornada
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
     * Resultado del cálculo de evolución de puntos por jornada.
     */
    public static class StandingsTimeline {
        private final String[] teamNamesFieldReference;
        private final int[][] cumulativePointsFieldReference;
        private final int totalRoundsFieldReference;

        public StandingsTimeline(String[] teamNamesParameterValue,
                                 int[][] cumulativePointsParameterValue,
                                 int totalRoundsParameterValue) {
            this.teamNamesFieldReference = teamNamesParameterValue;
            this.cumulativePointsFieldReference = cumulativePointsParameterValue;
            this.totalRoundsFieldReference = totalRoundsParameterValue;
        }

        public String[] getTeamNames() { return teamNamesFieldReference; }
        public int[][] getCumulativePoints() { return cumulativePointsFieldReference; }
        public int getTotalRounds() { return totalRoundsFieldReference; }
    }

    /**
     * Calcula la etiqueta de estado de una liga (apartado 2.7 del
     * enunciado): "Finished" si todos los partidos han acabado,
     * "Round X" si la liga está en curso (X = jornada actual mayor con
     * algún partido iniciado) o "Pending" si todavía no ha empezado.
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
     * Construye la lista de entradas que verá la pantalla de ligas
     * disponibles. Cada entrada incluye nombre, número de equipos y
     * estado actual (apartado 2.7 del enunciado).
     *
     * @param isAdminParameterValue si es true se devuelven todas las
     *        ligas; si es false se filtran por las del equipo del
     *        jugador.
     * @param userTeamNameParameterValue nombre del equipo del jugador
     *        actual (ignorado cuando isAdmin = true).
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

    /**
     * Genera los partidos de una liga usando round-robin a doble vuelta
     * y los guarda en la base de datos (apartado 2.6.1 del enunciado).
     *
     * Reglas aplicadas:
     *  - Todos los equipos juegan contra todos una vez por vuelta.
     *  - Un equipo no juega contra sí mismo.
     *  - Si el número de equipos es impar, se añade un equipo ficticio
     *    "DESCANSA" para que cada jornada quede uno sin jugar.
     *  - La primera jornada empieza 1 minuto después de la fecha y hora
     *    indicadas por el administrador.
     *  - Las jornadas siguientes se separan por
     *    {@code matchTime + 1} minutos (duración del partido + 1 minuto
     *    de espera, según el apartado 2.6.1).
     *  - En la segunda vuelta se invierte local/visitante.
     *
     * @param teamReferenceNamesParameterValue2 nombres de los equipos
     *        que participan en la liga.
     * @param leagueReferenceIdentifierParameterValue id de la liga.
     * @param timeParameterValue2 fecha y hora introducidas por el admin
     *        al crear la liga (sin el +1 minuto, este método lo aplica).
     */
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

        // La primera jornada empieza 1 minuto después de la fecha-hora
        // configurada por el admin (apartado 2.6.1 del enunciado).
        LocalDateTime jornadaTimeLocalVariableValue = timeParameterValue2.plusMinutes(1);

        // Separación entre jornadas: duración del partido + 1 minuto de
        // espera. Se lee de config.json para que sea coherente con el
        // valor "matchTime" usado por la simulación.
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
                    // Segunda vuelta: se invierte local/visitante.
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
                        jornadaTimeLocalVariableValue
                );
            }

            // Pasa al siguiente bloque horario respetando el tiempo de
            // partido + 1 minuto de espera entre jornadas.
            jornadaTimeLocalVariableValue =
                    jornadaTimeLocalVariableValue.plusMinutes(minutesBetweenRoundsLocalVariableValue);

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

            // Antes de borrar nada, paramos los partidos en curso de
            // esta liga (apartado 2.10 del enunciado).
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