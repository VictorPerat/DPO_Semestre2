package bussines.objects;

/**
 * DTO que agrupa la información que se muestra por cada liga en la
 * pantalla de "Available Leagues" (apartado 2.7 del enunciado):
 *  - Liga en sí.
 *  - Número de equipos que participan.
 *  - Etiqueta de estado (jornada actual, "Pending" o "Finished").
 *
 * Sirve para que la vista no tenga que calcular nada por su cuenta.
 */
public class LeagueListEntry {

    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_FINISHED = "Finished";

    private final League leagueReferenceFieldReference;
    private final int teamCountFieldReference;
    private final String statusLabelFieldReference;

    public LeagueListEntry(League leagueReferenceParameterValue,
                           int teamCountParameterValue,
                           String statusLabelParameterValue) {
        this.leagueReferenceFieldReference = leagueReferenceParameterValue;
        this.teamCountFieldReference = teamCountParameterValue;
        this.statusLabelFieldReference = statusLabelParameterValue;
    }

    public League getLeague() {
        return leagueReferenceFieldReference;
    }

    public int getTeamCount() {
        return teamCountFieldReference;
    }

    public String getStatusLabel() {
        return statusLabelFieldReference;
    }
}
