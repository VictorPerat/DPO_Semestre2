package bussines.managers;

import bussines.objects.TeamInfo;
import persistance.TeamInfoDao;
import persistance.LeagueDao;
import persistance.TeamDao;

import java.util.ArrayList;

/**
 * La clase {@code infoTeamManager} se encarga de gestionar las operaciones lógicas
 * relacionadas con la información de los equipos dentro de las ligas.
 * Usa DAOs para acceder a la base de datos y realizar operaciones CRUD sobre los equipos.
 */
public class TeamInfoManager {
    /** DAO para manejar operaciones sobre información de equipos en la base de datos. */
    private final TeamInfoDao informationTeamReferenceDataAccessObjectFieldReference;
    /** DAO para manejar operaciones relacionadas con ligas. */
    private LeagueDao leagueReferenceDataAccessObjectFieldReference;
    /** DAO para manejar operaciones relacionadas con equipos. */
    private TeamDao teamReferenceDataAccessObjectFieldReference;
    /**
     * Constructor que inicializa los DAOs necesarios para gestionar información de equipos.
     */
    public TeamInfoManager() {
        informationTeamReferenceDataAccessObjectFieldReference = new TeamInfoDao();
        this.leagueReferenceDataAccessObjectFieldReference = new LeagueDao();
        this.teamReferenceDataAccessObjectFieldReference = new TeamDao();
    }
    /**
     * Crea y guarda una nueva instancia de {@code infoTeam} en la base de datos.
     *
     * @param leagueId identificador de la liga
     * @param teamId identificador del equipo
     * @param wins número de victorias
     * @param defeats número de derrotas
     * @param ties número de empates
     * @param points total de puntos
     */
    public void createInfoTeam(int leagueReferenceIdentifierParameterValue, int teamReferenceIdentifierParameterValue, int winsParameterValue, int defeatsParameterValue, int tiesParameterValue, int pointsParameterValue) {
        TeamInfo logicTeamReferenceLocalVariableValue = new TeamInfo(leagueReferenceIdentifierParameterValue, teamReferenceIdentifierParameterValue, winsParameterValue, defeatsParameterValue, tiesParameterValue, pointsParameterValue);
        informationTeamReferenceDataAccessObjectFieldReference.createInfoTeam(logicTeamReferenceLocalVariableValue);
    }
    /**
     * Obtiene la lista de equipos con su información estadística dentro de una liga específica.
     *
     * @param leagueId identificador de la liga
     * @return una lista de objetos {@code infoTeam} pertenecientes a la liga
     */
    public ArrayList<TeamInfo> getInfoTeamsOfLeague(int leagueReferenceIdentifierParameterValue2) {
        ArrayList<TeamInfo> teamsLocalVariableValue = informationTeamReferenceDataAccessObjectFieldReference.getTeamsInLeague(leagueReferenceIdentifierParameterValue2);
        return teamsLocalVariableValue;
    }
    /**
     * Elimina de la base de datos la información del equipo con el ID indicado.
     *
     * @param idteam identificador del equipo a eliminar
     */
    public void deleteInfoTeam(int idteamParameterValue) {
        informationTeamReferenceDataAccessObjectFieldReference.deleteInfoTeam(idteamParameterValue);
    }
    /**
     * Añade 3 puntos al equipo ganador dentro de una liga específica.
     *
     * @param equipGuanyador nombre del equipo que ganó
     * @param leagueId identificador de la liga
     */
    public void afegirPuntsPerVictoria(String equipGuanyadorParameterValue, int leagueReferenceIdentifierParameterValue3) {
        int teamReferenceIdentifierLocalVariableValue = teamReferenceDataAccessObjectFieldReference.getTeamId(equipGuanyadorParameterValue);
        if (teamReferenceIdentifierLocalVariableValue != -1) {
            informationTeamReferenceDataAccessObjectFieldReference.afegirPunts(equipGuanyadorParameterValue, leagueReferenceIdentifierParameterValue3, 3);
        }
    }
    /**
     * Añade 1 punto a cada equipo en caso de empate dentro de una liga específica.
     *
     * @param equip1 nombre del primer equipo
     * @param equip2 nombre del segundo equipo
     * @param leagueId identificador de la liga
     */
    public void afegirPuntsPerEmpat(String equip1ParameterValue, String equip2ParameterValue, int leagueReferenceIdentifierParameterValue4) {
        int teamReferenceIdentifier1LocalVariableValue = teamReferenceDataAccessObjectFieldReference.getTeamId(equip1ParameterValue);
        int teamReferenceIdentifier2LocalVariableValue = teamReferenceDataAccessObjectFieldReference.getTeamId(equip2ParameterValue);

        if (teamReferenceIdentifier1LocalVariableValue != -1) {
            informationTeamReferenceDataAccessObjectFieldReference.afegirPunts(equip1ParameterValue, leagueReferenceIdentifierParameterValue4, 1);
        }
        if (teamReferenceIdentifier2LocalVariableValue != -1) {
            informationTeamReferenceDataAccessObjectFieldReference.afegirPunts(equip2ParameterValue, leagueReferenceIdentifierParameterValue4, 1);
        }
    }


}
