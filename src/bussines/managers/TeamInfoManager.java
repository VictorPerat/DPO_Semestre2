package bussines.managers;

import bussines.objects.TeamInfo;
import persistance.TeamDao;
import persistance.TeamInfoDao;

import java.util.ArrayList;


/**
 * Gestiona las operaciones del equipo.
 */
public class TeamInfoManager {


    private final TeamInfoDao informationTeamReferenceDataAccessObjectFieldReference;


    private TeamDao teamReferenceDataAccessObjectFieldReference;


    /**
     * Crea una instancia de el equipo.
     */
    public TeamInfoManager() {
        informationTeamReferenceDataAccessObjectFieldReference = new TeamInfoDao();
        this.teamReferenceDataAccessObjectFieldReference = new TeamDao();
    }


    /**
     * Crea el equipo.
     *
     * @param leagueReferenceIdentifierParameterValue liga identificador.
     * @param teamReferenceIdentifierParameterValue equipo identificador.
     * @param winsParameterValue dato de entrada de la operacion.
     * @param defeatsParameterValue dato de entrada de la operacion.
     * @param tiesParameterValue dato de entrada de la operacion.
     * @param pointsParameterValue dato de entrada de la operacion.
     */
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


    /**
     * Devuelve el equipos liga.
     *
     * @param leagueReferenceIdentifierParameterValue2 liga identificador.
     * @return el equipos liga.
     */
    public ArrayList<TeamInfo> getInfoTeamsOfLeague(int leagueReferenceIdentifierParameterValue2) {
        ArrayList<TeamInfo> teamsLocalVariableValue =
                informationTeamReferenceDataAccessObjectFieldReference.getTeamsInLeague(
                        leagueReferenceIdentifierParameterValue2
                );

        return teamsLocalVariableValue;
    }


    /**
     * Elimina el equipo.
     *
     * @param idteamParameterValue dato de entrada de la operacion.
     */
    public void deleteInfoTeam(int idteamParameterValue) {
        informationTeamReferenceDataAccessObjectFieldReference.deleteInfoTeam(idteamParameterValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param equipGuanyadorParameterValue dato de entrada de la operacion.
     * @param leagueReferenceIdentifierParameterValue3 liga identificador.
     */
    public void afegirPuntsPerVictoria(String equipGuanyadorParameterValue,
                                       int leagueReferenceIdentifierParameterValue3) {
        afegirPuntsPerVictoria(equipGuanyadorParameterValue, null,
                leagueReferenceIdentifierParameterValue3);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param equipGuanyadorParameterValue dato de entrada de la operacion.
     * @param equipPerdedorParameterValue dato de entrada de la operacion.
     * @param leagueReferenceIdentifierParameterValue3 liga identificador.
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
     * Gestiona esta operacion.
     *
     * @param equip1ParameterValue dato de entrada de la operacion.
     * @param equip2ParameterValue dato de entrada de la operacion.
     * @param leagueReferenceIdentifierParameterValue4 liga identificador.
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


