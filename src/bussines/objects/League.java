package bussines.objects;

import java.util.ArrayList;


/**
 * Agrupa la logica de el liga.
 */
public class League {


    private int identifierFieldReference;
    private String displayNameFieldReference;


    private String creationDateFieldReference;
    private ArrayList<String> participatingTeamsFieldReference;


    /**
     * Crea una instancia de el liga.
     *
     * @param displayNameParameterValue nombre que se muestra.
     * @param creationDateParameterValue dato de entrada de la operacion.
     * @param participatingTeamsParameterValue equipos que usa la operacion.
     * @param identifierParameterValue identificador del usuario.
     */
    public League(String displayNameParameterValue,
                  String creationDateParameterValue,
                  ArrayList<String> participatingTeamsParameterValue,
                  int identifierParameterValue) {
        this.identifierFieldReference = identifierParameterValue;
        this.displayNameFieldReference = displayNameParameterValue;
        this.creationDateFieldReference = creationDateParameterValue;
        this.participatingTeamsFieldReference = participatingTeamsParameterValue;
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public int getId() { return identifierFieldReference; }


    /**
     * Devuelve el nombre.
     *
     * @return el nombre.
     */
    public String getName() {
        return displayNameFieldReference;
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public String getStartDate() {
        return creationDateFieldReference;
    }


    /**
     * Devuelve los equipos.
     *
     * @return los equipos.
     */
    public ArrayList<String> getParticipatingTeams() {
        return participatingTeamsFieldReference;
    }
}


