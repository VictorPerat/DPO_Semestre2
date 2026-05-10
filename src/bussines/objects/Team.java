package bussines.objects;


/**
 * Agrupa la logica de el equipo.
 */
public class Team {


    private int identifierFieldReference;
    private String displayNameFieldReference;


    /**
     * Crea una instancia de el equipo.
     *
     * @param identifierParameterValue identificador del usuario.
     * @param displayNameParameterValue nombre que se muestra.
     */
    public Team(int identifierParameterValue, String displayNameParameterValue) {
        this.identifierFieldReference = identifierParameterValue;
        this.displayNameFieldReference = displayNameParameterValue;
    }


    /**
     * Crea una instancia de el equipo.
     *
     * @param displayNameParameterValue2 nombre que usa la operacion.
     */
    public Team(String displayNameParameterValue2) {
        this.displayNameFieldReference = displayNameParameterValue2;
    }


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
    public int getId() { return identifierFieldReference; }
}


