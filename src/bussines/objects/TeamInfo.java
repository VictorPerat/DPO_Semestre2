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
     * Actualiza el liga.
     *
     * @param leagueReferenceIdentifierParameterValue2 liga identificador.
     */
    public void setLeagueId(int leagueReferenceIdentifierParameterValue2) {
        this.leagueReferenceIdentifierFieldReference =
                leagueReferenceIdentifierParameterValue2;
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
     * Actualiza el equipo.
     *
     * @param teamReferenceIdentifierParameterValue2 equipo identificador.
     */
    public void setTeamId(int teamReferenceIdentifierParameterValue2) {
        this.teamReferenceIdentifierFieldReference =
                teamReferenceIdentifierParameterValue2;
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
     * Actualiza el contenido.
     *
     * @param winsParameterValue2 dato de entrada de la operacion.
     */
    public void setWins(int winsParameterValue2) {
        this.winsFieldReference = winsParameterValue2;
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
     * Actualiza el contenido.
     *
     * @param defeatsParameterValue2 dato de entrada de la operacion.
     */
    public void setDefeats(int defeatsParameterValue2) {
        this.defeatsFieldReference = defeatsParameterValue2;
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
     * Actualiza el contenido.
     *
     * @param tiesParameterValue2 dato de entrada de la operacion.
     */
    public void setTies(int tiesParameterValue2) {
        this.tiesFieldReference = tiesParameterValue2;
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
     * Actualiza el contenido.
     *
     * @param pointsParameterValue2 dato de entrada de la operacion.
     */
    public void setPoints(int pointsParameterValue2) {
        this.pointsFieldReference = pointsParameterValue2;
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


