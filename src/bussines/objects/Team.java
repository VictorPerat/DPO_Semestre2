package bussines.objects;

// Objeto simple que representa un equipo
public class Team {

    // Identificador y nombre visible del equipo
    private int identifierFieldReference;
    private String displayNameFieldReference;

    // Construye un equipo con id y nombre
    public Team(int identifierParameterValue, String displayNameParameterValue) {
        this.identifierFieldReference = identifierParameterValue;
        this.displayNameFieldReference = displayNameParameterValue;
    }

    // Construye un equipo solo con el nombre
    public Team(String displayNameParameterValue2) {
        this.displayNameFieldReference = displayNameParameterValue2;
    }

    // Devuelve el nombre del equipo
    public String getName() {
        return displayNameFieldReference;
    }

    // Devuelve el identificador del equipo
    public int getId() { return identifierFieldReference; }
}
