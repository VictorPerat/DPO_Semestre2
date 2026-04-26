package bussines.objects;
/**
 * La clase Player representa a un jugador de un equipo, incluyendo su información personal
 * y de contacto, así como su pertenencia a un equipo específico.
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
     * Constructor de la clase Player.
     *
     * @param namePlayer   Nombre del jugador.
     * @param mail         Correo electrónico del jugador.
     * @param dniPlayer    DNI del jugador.
     * @param team_id      Identificador del equipo al que pertenece.
     * @param dorsal       Número de dorsal del jugador.
     * @param password     Contraseña del jugador.
     * @param phoneNumber  Número de teléfono del jugador.
     */
    public Player(String displayNamePlayerProfileParameterValue, String emailAddressParameterValue, String nationalIdentityDocumentPlayerProfileParameterValue, String teamReferenceIdentifierParameterValue, int jerseyNumberParameterValue, String userPasswordParameterValue, int phoneNumberNumericValueParameterValue) {
        this.teamReferenceFieldReference = teamReferenceIdentifierParameterValue;
        this.nationalIdentityDocumentFieldReference = nationalIdentityDocumentPlayerProfileParameterValue;
        this.emailAddressFieldReference = emailAddressParameterValue;
        this.displayNameFieldReference = displayNamePlayerProfileParameterValue;
        this.jerseyNumberFieldReference = jerseyNumberParameterValue;
        this.phoneNumberNumericValueFieldReference = phoneNumberNumericValueParameterValue;
        this.userPasswordFieldReference = userPasswordParameterValue;
    }
    /**
     * @return Nombre del jugador.
     */
    public String getName() {
        return displayNameFieldReference;
    }
    /**
     * @return DNI del jugador.
     */
    public String getDni() {
        return nationalIdentityDocumentFieldReference;
    }
    /**
     * @return Correo electrónico del jugador.
     */
    public String getEmail() {
        return emailAddressFieldReference;
    }
    /**
     * @return ID del equipo al que pertenece el jugador.
     */
    public String getTeam() {
        return teamReferenceFieldReference;
    }
    /**
     * @return Número de dorsal del jugador.
     */
    public int getNumber() {
        return jerseyNumberFieldReference;
    }
    /**
     * @return Número de teléfono del jugador.
     */
    public int getPhoneNumber () {
        return phoneNumberNumericValueFieldReference;
    }
    /**
     * @return ID del equipo al que pertenece el jugador.
     */
    public String getTeamId() {
        return teamReferenceFieldReference;
    }
    /**
     * Establece la contraseña del jugador.
     *
     * @param password Nueva contraseña.
     */
    public void setPassword(String userPasswordParameterValue2) {
        this.userPasswordFieldReference = userPasswordParameterValue2;
    }
    /**
     * @return Contraseña del jugador.
     */
    public String getPassword() {
        return userPasswordFieldReference;
    }
    /**
     * Establece el ID del equipo del jugador.
     *
     * @param teamId ID del nuevo equipo.
     */
    public void setTeamId(String teamReferenceIdentifierParameterValue2) {
        this.teamReferenceFieldReference = teamReferenceIdentifierParameterValue2;
    }
    /**
     * @return DNI del jugador.
     */
    public String getDniPlayer() {
        return nationalIdentityDocumentFieldReference;
    }
    /**
     * Establece el DNI del jugador.
     *
     * @param dniPlayer Nuevo DNI.
     */
    public void setDniPlayer(String nationalIdentityDocumentPlayerProfileParameterValue2) {
        this.nationalIdentityDocumentFieldReference = nationalIdentityDocumentPlayerProfileParameterValue2;
    }
    /**
     * @return Correo electrónico del jugador.
     */
    public String getMail() {
        return emailAddressFieldReference;
    }
    /**
     * Establece el correo electrónico del jugador.
     *
     * @param mail Nuevo correo electrónico.
     */
    public void setMail(String emailAddressParameterValue2) {
        this.emailAddressFieldReference = emailAddressParameterValue2;
    }
    /**
     * @return Nombre del jugador.
     */
    public String getNamePlayer() {
        return displayNameFieldReference;
    }
    /**
     * Establece el nombre del jugador.
     *
     * @param namePlayer Nuevo nombre.
     */
    public void setNamePlayer(String displayNamePlayerProfileParameterValue2) {
        this.displayNameFieldReference = displayNamePlayerProfileParameterValue2;
    }
    /**
     * @return Número de dorsal del jugador.
     */
    public int getDorsal() {
        return jerseyNumberFieldReference;
    }
    /**
     * Establece el número de dorsal del jugador.
     *
     * @param dorsal Nuevo número de dorsal.
     */
    public void setDorsal(int jerseyNumberParameterValue2) {
        this.jerseyNumberFieldReference = jerseyNumberParameterValue2;
    }
    /**
     * Establece el número de teléfono del jugador.
     *
     * @param phoneNumber Nuevo número de teléfono.
     */
    public void setPhoneNumber(int phoneNumberNumericValueParameterValue2) {
        this.phoneNumberNumericValueFieldReference = phoneNumberNumericValueParameterValue2;
    }


}
