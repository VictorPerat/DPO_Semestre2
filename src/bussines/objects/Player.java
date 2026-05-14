package bussines.objects;


/**
 * Agrupa la logica de el jugador.
 */
public class Player {


    private String displayNameFieldReference;
    private String emailAddressFieldReference;
    private String nationalIdentityDocumentFieldReference;


    private String teamReferenceFieldReference;
    private int jerseyNumberFieldReference;
    private String userPasswordFieldReference;
    private int phoneNumberNumericValueFieldReference;


    /**
     * Crea una instancia de el jugador.
     *
     * @param displayNamePlayerProfileParameterValue nombre jugador perfil.
     * @param emailAddressParameterValue direccion de email.
     * @param nationalIdentityDocumentPlayerProfileParameterValue jugador perfil.
     * @param teamReferenceIdentifierParameterValue equipo identificador.
     * @param jerseyNumberParameterValue dorsal del jugador.
     * @param userPasswordParameterValue contrasena del usuario.
     * @param phoneNumberNumericValueParameterValue telefono numero.
     */
    public Player(String displayNamePlayerProfileParameterValue,
                  String emailAddressParameterValue,
                  String nationalIdentityDocumentPlayerProfileParameterValue,
                  String teamReferenceIdentifierParameterValue,
                  int jerseyNumberParameterValue,
                  String userPasswordParameterValue,
                  int phoneNumberNumericValueParameterValue) {
        this.teamReferenceFieldReference = teamReferenceIdentifierParameterValue;
        this.nationalIdentityDocumentFieldReference =
                nationalIdentityDocumentPlayerProfileParameterValue;
        this.emailAddressFieldReference = emailAddressParameterValue;
        this.displayNameFieldReference = displayNamePlayerProfileParameterValue;
        this.jerseyNumberFieldReference = jerseyNumberParameterValue;
        this.phoneNumberNumericValueFieldReference =
                phoneNumberNumericValueParameterValue;
        this.userPasswordFieldReference = userPasswordParameterValue;
    }


    /**
     * Devuelve el equipo.
     *
     * @return el equipo.
     */
    public String getTeam() {
        return teamReferenceFieldReference;
    }


    /**
     * Devuelve el numero.
     *
     * @return el numero.
     */
    public int getNumber() {
        return jerseyNumberFieldReference;
    }


    /**
     * Devuelve el telefono numero.
     *
     * @return el telefono numero.
     */
    public int getPhoneNumber() {
        return phoneNumberNumericValueFieldReference;
    }


    /**
     * Devuelve el equipo.
     *
     * @return el equipo.
     */
    public String getTeamId() {
        return teamReferenceFieldReference;
    }


    /**
     * Devuelve el contrasena.
     *
     * @return el contrasena.
     */
    public String getPassword() {
        return userPasswordFieldReference;
    }


    /**
     * Devuelve el dni jugador.
     *
     * @return el dni jugador.
     */
    public String getDniPlayer() {
        return nationalIdentityDocumentFieldReference;
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public String getMail() {
        return emailAddressFieldReference;
    }


    /**
     * Devuelve el nombre jugador.
     *
     * @return el nombre jugador.
     */
    public String getNamePlayer() {
        return displayNameFieldReference;
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public int getDorsal() {
        return jerseyNumberFieldReference;
    }


}


