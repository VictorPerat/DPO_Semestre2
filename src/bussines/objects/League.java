package bussines.objects;

import java.util.ArrayList;

// Objeto que representa una liga con su fecha y equipos participantes
public class League {

    // Identificador unico y nombre visible de la liga
    private int identifierFieldReference;
    private String displayNameFieldReference;

    // Fecha de inicio y lista de equipos asociados
    private String creationDateFieldReference;
    private ArrayList<String> participatingTeamsFieldReference;

    // Construye una liga con todos sus datos principales
    public League(String displayNameParameterValue,
                  String creationDateParameterValue,
                  ArrayList<String> participatingTeamsParameterValue,
                  int identifierParameterValue) {
        this.identifierFieldReference = identifierParameterValue;
        this.displayNameFieldReference = displayNameParameterValue;
        this.creationDateFieldReference = creationDateParameterValue;
        this.participatingTeamsFieldReference = participatingTeamsParameterValue;
    }

    // Devuelve el id de la liga
    public int getId() { return identifierFieldReference; }

    // Devuelve el nombre de la liga
    public String getName() {
        return displayNameFieldReference;
    }

    // Devuelve la fecha de inicio
    public String getStartDate() {
        return creationDateFieldReference;
    }

    // Devuelve la lista de equipos participantes
    public ArrayList<String> getParticipatingTeams() {
        return participatingTeamsFieldReference;
    }
}
