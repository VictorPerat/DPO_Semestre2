package bussines.objects;

import java.util.ArrayList;
/**
 * La clase {@code League} representa una liga deportiva, incluyendo su nombre,
 * fecha de creación, lista de equipos participantes e identificador único.
 */
public class League {
    /** Identificador único de la liga. */
    private int identifierFieldReference;
    /** Nombre de la liga. */
    private String displayNameFieldReference;
    /** Fecha de creación de la liga en formato de texto. */
    private String creationDateFieldReference;
    /** Lista de nombres de los equipos que participan en la liga. */
    private ArrayList<String> participatingTeamsFieldReference;
    /**
     * Crea una nueva instancia de {@code League}.
     *
     * @param name nombre de la liga
     * @param creationDate fecha de creación de la liga
     * @param participatingTeams lista de equipos que participan en la liga
     * @param id identificador único de la liga
     */
    public League(String displayNameParameterValue, String creationDateParameterValue, ArrayList<String> participatingTeamsParameterValue, int identifierParameterValue) {
        this.identifierFieldReference = identifierParameterValue;
        this.displayNameFieldReference = displayNameParameterValue;
        this.creationDateFieldReference = creationDateParameterValue;
        this.participatingTeamsFieldReference = participatingTeamsParameterValue;

    }
    /**
     * Devuelve el identificador único de la liga.
     *
     * @return ID de la liga
     */
    public int getId() {return identifierFieldReference;}
    /**
     * Devuelve el nombre de la liga.
     *
     * @return nombre de la liga
     */
    public String getName() {
        return displayNameFieldReference;
    }
    /**
     * Devuelve la fecha de creación de la liga.
     *
     * @return fecha de creación de la liga
     */
    public String getStartDate() {
        return creationDateFieldReference;
    }
    /**
     * Devuelve la lista de equipos participantes en la liga.
     *
     * @return lista de nombres de los equipos participantes
     */
    public ArrayList<String> getParticipatingTeams() {
        return participatingTeamsFieldReference;
    }
}
