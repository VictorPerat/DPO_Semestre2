package bussines.managers;

import bussines.objects.TeamInfo;
import persistance.TeamInfoDao;
import persistance.LeagueDao;
import persistance.TeamDao;

import java.util.ArrayList;

/**
 * Esta clase se encarga de gestionar la información de los equipos dentro de las ligas.
 */
public class TeamInfoManager {

    // DAO que se usa para acceder a la información de los equipos en las ligas
    private final TeamInfoDao informationTeamReferenceDataAccessObjectFieldReference;

    // DAO relacionado con ligas
    private LeagueDao leagueReferenceDataAccessObjectFieldReference;

    // DAO relacionado con equipos
    private TeamDao teamReferenceDataAccessObjectFieldReference;

    // Constructor que inicializa los DAOs necesarios
    public TeamInfoManager() {
        informationTeamReferenceDataAccessObjectFieldReference = new TeamInfoDao();
        this.leagueReferenceDataAccessObjectFieldReference = new LeagueDao();
        this.teamReferenceDataAccessObjectFieldReference = new TeamDao();
    }

    // Crea y guarda la información de un equipo dentro de una liga
    public void createInfoTeam(int leagueReferenceIdentifierParameterValue,
                               int teamReferenceIdentifierParameterValue,
                               int winsParameterValue,
                               int defeatsParameterValue,
                               int tiesParameterValue,
                               int pointsParameterValue) {

        TeamInfo logicTeamReferenceLocalVariableValue = new TeamInfo(
                leagueReferenceIdentifierParameterValue,
                teamReferenceIdentifierParameterValue,
                winsParameterValue,
                defeatsParameterValue,
                tiesParameterValue,
                pointsParameterValue
        );

        informationTeamReferenceDataAccessObjectFieldReference.createInfoTeam(logicTeamReferenceLocalVariableValue);
    }

    // Devuelve la información de todos los equipos de una liga
    public ArrayList<TeamInfo> getInfoTeamsOfLeague(int leagueReferenceIdentifierParameterValue2) {
        ArrayList<TeamInfo> teamsLocalVariableValue =
                informationTeamReferenceDataAccessObjectFieldReference.getTeamsInLeague(
                        leagueReferenceIdentifierParameterValue2
                );

        return teamsLocalVariableValue;
    }

    // Borra la información de un equipo usando su id
    public void deleteInfoTeam(int idteamParameterValue) {
        informationTeamReferenceDataAccessObjectFieldReference.deleteInfoTeam(idteamParameterValue);
    }

    // Añade 3 puntos al equipo ganador
    public void afegirPuntsPerVictoria(String equipGuanyadorParameterValue,
                                       int leagueReferenceIdentifierParameterValue3) {

        int teamReferenceIdentifierLocalVariableValue =
                teamReferenceDataAccessObjectFieldReference.getTeamId(equipGuanyadorParameterValue);

        if (teamReferenceIdentifierLocalVariableValue != -1) {
            informationTeamReferenceDataAccessObjectFieldReference.afegirPunts(
                    equipGuanyadorParameterValue,
                    leagueReferenceIdentifierParameterValue3,
                    3
            );
        }
    }

    // Añade 1 punto a cada equipo si el partido termina en empate
    public void afegirPuntsPerEmpat(String equip1ParameterValue,
                                    String equip2ParameterValue,
                                    int leagueReferenceIdentifierParameterValue4) {

        int teamReferenceIdentifier1LocalVariableValue =
                teamReferenceDataAccessObjectFieldReference.getTeamId(equip1ParameterValue);

        int teamReferenceIdentifier2LocalVariableValue =
                teamReferenceDataAccessObjectFieldReference.getTeamId(equip2ParameterValue);

        if (teamReferenceIdentifier1LocalVariableValue != -1) {
            informationTeamReferenceDataAccessObjectFieldReference.afegirPunts(
                    equip1ParameterValue,
                    leagueReferenceIdentifierParameterValue4,
                    1
            );
        }

        if (teamReferenceIdentifier2LocalVariableValue != -1) {
            informationTeamReferenceDataAccessObjectFieldReference.afegirPunts(
                    equip2ParameterValue,
                    leagueReferenceIdentifierParameterValue4,
                    1
            );
        }
    }
}