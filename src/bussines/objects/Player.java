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
     * Devuelve el nombre.
     *
     * @return el nombre.
     */
    public String getName() {
        return displayNameFieldReference;
    }


    /**
     * Devuelve el dni.
     *
     * @return el dni.
     */
    public String getDni() {
        return nationalIdentityDocumentFieldReference;
    }


    /**
     * Devuelve el email.
     *
     * @return el email.
     */
    public String getEmail() {
        return emailAddressFieldReference;
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
     * Actualiza el contrasena.
     *
     * @param userPasswordParameterValue2 contrasena del usuario.
     */
    public void setPassword(String userPasswordParameterValue2) {
        this.userPasswordFieldReference = userPasswordParameterValue2;
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
     * Actualiza el equipo.
     *
     * @param teamReferenceIdentifierParameterValue2 equipo identificador.
     */
    public void setTeamId(String teamReferenceIdentifierParameterValue2) {
        this.teamReferenceFieldReference = teamReferenceIdentifierParameterValue2;
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
     * Actualiza el dni jugador.
     *
     * @param nationalIdentityDocumentPlayerProfileParameterValue2 jugador perfil.
     */
    public void setDniPlayer(String nationalIdentityDocumentPlayerProfileParameterValue2) {
        this.nationalIdentityDocumentFieldReference =
                nationalIdentityDocumentPlayerProfileParameterValue2;
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
     * Actualiza el contenido.
     *
     * @param emailAddressParameterValue2 email que usa la operacion.
     */
    public void setMail(String emailAddressParameterValue2) {
        this.emailAddressFieldReference = emailAddressParameterValue2;
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
     * Actualiza el nombre jugador.
     *
     * @param displayNamePlayerProfileParameterValue2 nombre jugador perfil.
     */
    public void setNamePlayer(String displayNamePlayerProfileParameterValue2) {
        this.displayNameFieldReference = displayNamePlayerProfileParameterValue2;
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public int getDorsal() {
        return jerseyNumberFieldReference;
    }


    /**
     * Actualiza el contenido.
     *
     * @param jerseyNumberParameterValue2 numero que usa la operacion.
     */
    public void setDorsal(int jerseyNumberParameterValue2) {
        this.jerseyNumberFieldReference = jerseyNumberParameterValue2;
    }


    /**
     * Actualiza el telefono numero.
     *
     * @param phoneNumberNumericValueParameterValue2 telefono numero.
     */
    public void setPhoneNumber(int phoneNumberNumericValueParameterValue2) {
        this.phoneNumberNumericValueFieldReference =
                phoneNumberNumericValueParameterValue2;
    }
}


