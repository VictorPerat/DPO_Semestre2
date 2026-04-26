package bussines.objects;
/**
 * Representa un equipo con un identificador único y un nombre.
 */
public class Team {
    private int identifierFieldReference;
    private String displayNameFieldReference;
    /**
     * Constructor que crea un equipo con un identificador y un nombre.
     *
     * @param id   Identificador único del equipo.
     * @param name Nombre del equipo.
     */
    public Team(int identifierParameterValue, String displayNameParameterValue) {
        this.identifierFieldReference = identifierParameterValue;
        this.displayNameFieldReference = displayNameParameterValue;
    }
    /**
     * Constructor que crea un equipo únicamente con su nombre.
     * El identificador puede ser asignado más adelante.
     *
     * @param name Nombre del equipo.
     */
    public Team(String displayNameParameterValue2) {
        this.displayNameFieldReference = displayNameParameterValue2;
    }
    /**
     * Devuelve el nombre del equipo.
     *
     * @return Nombre del equipo.
     */
    public String getName() {
        return displayNameFieldReference;
    }
    /**
     * Devuelve el identificador del equipo.
     *
     * @return Identificador del equipo.
     */
    public int getId() {return identifierFieldReference;}
}
