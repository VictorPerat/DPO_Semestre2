package bussines.objects;

/**
 * La clase {@code infoTeam} representa las estadísticas de un equipo en una liga determinada.
 * Contiene información como el número de victorias, derrotas, empates y puntos obtenidos por el equipo.
 */
public class TeamInfo {
    /** Identificador de la liga a la que pertenece el equipo. */
    private int leagueReferenceIdentifierFieldReference;
    /** Identificador del equipo. */
    private int teamReferenceIdentifierFieldReference;
    /** Estadísticas adicionales del equipo (no utilizada actualmente). */
    private String statsFieldReference;
    /** Número de jugadores en el equipo (no utilizada actualmente). */
    private int numericValuePlayersFieldReference;
    /** Número de victorias del equipo. */
    private int winsFieldReference;
    /** Número de derrotas del equipo. */
    private int defeatsFieldReference;
    /** Número de empates del equipo. */
    private int tiesFieldReference;
    /** Total de puntos obtenidos por el equipo. */
    private int pointsFieldReference;
    /**
     * Constructor para crear una instancia de {@code infoTeam} con los datos esenciales.
     *
     * @param leagueId identificador de la liga
     * @param teamId identificador del equipo
     * @param wins número de victorias
     * @param defeats número de derrotas
     * @param ties número de empates
     * @param points total de puntos
     */
    public TeamInfo(int leagueReferenceIdentifierParameterValue, int teamReferenceIdentifierParameterValue, int winsParameterValue, int defeatsParameterValue, int tiesParameterValue, int pointsParameterValue) {
        this.leagueReferenceIdentifierFieldReference = leagueReferenceIdentifierParameterValue;
        this.teamReferenceIdentifierFieldReference = teamReferenceIdentifierParameterValue;
        this.winsFieldReference = winsParameterValue;
        this.defeatsFieldReference = defeatsParameterValue;
        this.tiesFieldReference = tiesParameterValue;
        this.pointsFieldReference = pointsParameterValue;
    }
    /** @return el identificador de la liga */
    public int getLeagueId() {
        return leagueReferenceIdentifierFieldReference;
    }
    /** @param leagueId nuevo identificador de la liga */
    public void setLeagueId(int leagueReferenceIdentifierParameterValue2) {
        this.leagueReferenceIdentifierFieldReference = leagueReferenceIdentifierParameterValue2;
    }
    /** @return el identificador del equipo */
    public int getTeamId() {
        return teamReferenceIdentifierFieldReference;
    }
    /** @param teamId nuevo identificador del equipo */
    public void setTeamId(int teamReferenceIdentifierParameterValue2) {
        this.teamReferenceIdentifierFieldReference = teamReferenceIdentifierParameterValue2;
    }
    /** @return número de victorias del equipo */
    public int getWins() {
        return winsFieldReference;
    }
    /** @param wins nuevo número de victorias */
    public void setWins(int winsParameterValue2) {
        this.winsFieldReference = winsParameterValue2;
    }
    /** @return número de derrotas del equipo */
    public int getDefeats() {
        return defeatsFieldReference;
    }
    /** @param defeats nuevo número de derrotas */
    public void setDefeats(int defeatsParameterValue2) {
        this.defeatsFieldReference = defeatsParameterValue2;
    }
    /** @return número de empates del equipo */
    public int getTies() {
        return tiesFieldReference;
    }
    /** @param ties nuevo número de empates */
    public void setTies(int tiesParameterValue2) {
        this.tiesFieldReference = tiesParameterValue2;
    }
    /** @return total de puntos del equipo */
    public int getPoints() {
        return pointsFieldReference;
    }
    /** @param points nuevo total de puntos */
    public void setPoints(int pointsParameterValue2) {
        this.pointsFieldReference = pointsParameterValue2;
    }
    /**
     * Devuelve una representación en cadena del objeto {@code infoTeam}.
     *
     * @return una cadena que representa el estado del equipo en la liga
     */
    @Override
    public String toString() {
        return "TeamLeague{" +
                "leagueId=" + leagueReferenceIdentifierFieldReference +
                ", teamId=" + teamReferenceIdentifierFieldReference +
                ", wins=" + winsFieldReference +
                ", defeats=" + defeatsFieldReference +
                ", ties=" + tiesFieldReference +
                ", points=" + pointsFieldReference +
                '}';
    }
}
