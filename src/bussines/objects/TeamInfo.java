package bussines.objects;

// Objeto que guarda la clasificacion de un equipo dentro de una liga
public class TeamInfo {

    // Identificadores de liga y de equipo
    private int leagueReferenceIdentifierFieldReference;
    private int teamReferenceIdentifierFieldReference;

    // Campos legacy reservados para informacion extra
    private String statsFieldReference;
    private int numericValuePlayersFieldReference;

    // Estadisticas acumuladas del equipo
    private int winsFieldReference;
    private int defeatsFieldReference;
    private int tiesFieldReference;
    private int pointsFieldReference;

    // Construye el registro estadistico principal del equipo
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

    // Devuelve el id de la liga
    public int getLeagueId() {
        return leagueReferenceIdentifierFieldReference;
    }

    // Actualiza el id de la liga
    public void setLeagueId(int leagueReferenceIdentifierParameterValue2) {
        this.leagueReferenceIdentifierFieldReference =
                leagueReferenceIdentifierParameterValue2;
    }

    // Devuelve el id del equipo
    public int getTeamId() {
        return teamReferenceIdentifierFieldReference;
    }

    // Actualiza el id del equipo
    public void setTeamId(int teamReferenceIdentifierParameterValue2) {
        this.teamReferenceIdentifierFieldReference =
                teamReferenceIdentifierParameterValue2;
    }

    // Devuelve el numero de victorias
    public int getWins() {
        return winsFieldReference;
    }

    // Actualiza el numero de victorias
    public void setWins(int winsParameterValue2) {
        this.winsFieldReference = winsParameterValue2;
    }

    // Devuelve el numero de derrotas
    public int getDefeats() {
        return defeatsFieldReference;
    }

    // Actualiza el numero de derrotas
    public void setDefeats(int defeatsParameterValue2) {
        this.defeatsFieldReference = defeatsParameterValue2;
    }

    // Devuelve el numero de empates
    public int getTies() {
        return tiesFieldReference;
    }

    // Actualiza el numero de empates
    public void setTies(int tiesParameterValue2) {
        this.tiesFieldReference = tiesParameterValue2;
    }

    // Devuelve los puntos totales
    public int getPoints() {
        return pointsFieldReference;
    }

    // Actualiza los puntos totales
    public void setPoints(int pointsParameterValue2) {
        this.pointsFieldReference = pointsParameterValue2;
    }

    // Devuelve una representacion legible del estado del equipo
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
