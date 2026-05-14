package bussines.objects;


/**
 * Agrupa la logica de el equipo.
 */
public class TeamInfo {


    private int leagueReferenceIdentifierFieldReference;
    private int teamReferenceIdentifierFieldReference;


    private int winsFieldReference;
    private int defeatsFieldReference;
    private int tiesFieldReference;
    private int pointsFieldReference;


    /**
     * Crea una instancia de el equipo.
     *
     * @param leagueReferenceIdentifierParameterValue liga identificador.
     * @param teamReferenceIdentifierParameterValue equipo identificador.
     * @param winsParameterValue dato de entrada de la operacion.
     * @param defeatsParameterValue dato de entrada de la operacion.
     * @param tiesParameterValue dato de entrada de la operacion.
     * @param pointsParameterValue dato de entrada de la operacion.
     */
    public TeamInfo(int leagueReferenceIdentifierParameterValue,
                    int teamReferenceIdentifierParameterValue,
                    int winsParameterValue,
                    int defeatsParameterValue,
                    int tiesParameterValue,
                    int pointsParameterValue) {
        this.leagueReferenceIdentifierFieldReference =
                leagueReferenceIdentifierParameterValue;
        this.teamReferenceIdentifierFieldReference =
                teamReferenceIdentifierParameterValue;
        this.winsFieldReference = winsParameterValue;
        this.defeatsFieldReference = defeatsParameterValue;
        this.tiesFieldReference = tiesParameterValue;
        this.pointsFieldReference = pointsParameterValue;
    }


    /**
     * Devuelve el liga.
     *
     * @return el liga.
     */
    public int getLeagueId() {
        return leagueReferenceIdentifierFieldReference;
    }


    /**
     * Devuelve el equipo.
     *
     * @return el equipo.
     */
    public int getTeamId() {
        return teamReferenceIdentifierFieldReference;
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public int getWins() {
        return winsFieldReference;
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public int getDefeats() {
        return defeatsFieldReference;
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public int getTies() {
        return tiesFieldReference;
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public int getPoints() {
        return pointsFieldReference;
    }


    /**
     * Gestiona esta operacion.
     *
     * @return resultado de la operacion.
     */
    @Override
    public String toString() {
        return "TeamLeague{"
                + "leagueId=" + leagueReferenceIdentifierFieldReference
                + ", teamId=" + teamReferenceIdentifierFieldReference
                + ", wins=" + winsFieldReference
                + ", defeats=" + defeatsFieldReference
                + ", ties=" + tiesFieldReference
                + ", points=" + pointsFieldReference
                + '}';
    }
}


