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

    /**
     * Aplica el resultado de una victoria: +3 puntos y +1 win al
     * ganador, +1 defeat al perdedor.
     */
    public void afegirPuntsPerVictoria(String equipGuanyadorParameterValue,
                                       int leagueReferenceIdentifierParameterValue3) {
        afegirPuntsPerVictoria(equipGuanyadorParameterValue, null,
                leagueReferenceIdentifierParameterValue3);
    }

    /**
     * Versión con perdedor explícito para que se contabilicen también
     * las defeats. El segundo parámetro puede ser null si no se conoce.
     */
    public void afegirPuntsPerVictoria(String equipGuanyadorParameterValue,
                                       String equipPerdedorParameterValue,
                                       int leagueReferenceIdentifierParameterValue3) {

        int teamReferenceIdentifierLocalVariableValue =
                teamReferenceDataAccessObjectFieldReference.getTeamId(equipGuanyadorParameterValue);

        if (teamReferenceIdentifierLocalVariableValue != -1) {
            informationTeamReferenceDataAccessObjectFieldReference.afegirPunts(
                    equipGuanyadorParameterValue,
                    leagueReferenceIdentifierParameterValue3,
                    3
            );
            informationTeamReferenceDataAccessObjectFieldReference.incrementCounter(
                    equipGuanyadorParameterValue,
                    leagueReferenceIdentifierParameterValue3,
                    "wins"
            );
        }

        if (equipPerdedorParameterValue != null) {
            int loserIdLocalVariableValue =
                    teamReferenceDataAccessObjectFieldReference.getTeamId(equipPerdedorParameterValue);
            if (loserIdLocalVariableValue != -1) {
                informationTeamReferenceDataAccessObjectFieldReference.incrementCounter(
                        equipPerdedorParameterValue,
                        leagueReferenceIdentifierParameterValue3,
                        "defeats"
                );
            }
        }
    }

    /**
     * Aplica un empate: +1 punto y +1 tie a ambos equipos.
     */
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
            informationTeamReferenceDataAccessObjectFieldReference.incrementCounter(
                    equip1ParameterValue,
                    leagueReferenceIdentifierParameterValue4,
                    "ties"
            );
        }

        if (teamReferenceIdentifier2LocalVariableValue != -1) {
            informationTeamReferenceDataAccessObjectFieldReference.afegirPunts(
                    equip2ParameterValue,
                    leagueReferenceIdentifierParameterValue4,
                    1
            );
            informationTeamReferenceDataAccessObjectFieldReference.incrementCounter(
                    equip2ParameterValue,
                    leagueReferenceIdentifierParameterValue4,
                    "ties"
            );
        }
    }
}