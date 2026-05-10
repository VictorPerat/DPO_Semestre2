package bussines.objects;


/**
 * Agrupa la logica de el liga.
 */
public class LeagueListEntry {

    /**
     * Constante para el valor.
     */
    public static final String STATUS_PENDING = "Pending";
    /**
     * Constante para el valor.
     */
    public static final String STATUS_FINISHED = "Finished";

    private final League leagueReferenceFieldReference;
    private final int teamCountFieldReference;
    private final String statusLabelFieldReference;


    /**
     * Crea una instancia de el liga.
     *
     * @param leagueReferenceParameterValue liga que usa la operacion.
     * @param teamCountParameterValue equipo que usa la operacion.
     * @param statusLabelParameterValue dato de entrada de la operacion.
     */
    public LeagueListEntry(League leagueReferenceParameterValue,
                           int teamCountParameterValue,
                           String statusLabelParameterValue) {
        this.leagueReferenceFieldReference = leagueReferenceParameterValue;
        this.teamCountFieldReference = teamCountParameterValue;
        this.statusLabelFieldReference = statusLabelParameterValue;
    }


    /**
     * Devuelve el liga.
     *
     * @return el liga.
     */
    public League getLeague() {
        return leagueReferenceFieldReference;
    }


    /**
     * Devuelve el equipo.
     *
     * @return el equipo.
     */
    public int getTeamCount() {
        return teamCountFieldReference;
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public String getStatusLabel() {
        return statusLabelFieldReference;
    }
}


