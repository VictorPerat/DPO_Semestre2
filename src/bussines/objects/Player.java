package bussines.objects;

// Objeto que representa a un jugador con sus datos personales y de equipo
public class Player {

    // Datos basicos de identificacion y contacto
    private String displayNameFieldReference;
    private String emailAddressFieldReference;
    private String nationalIdentityDocumentFieldReference;

    // Equipo, dorsal, contrasena y telefono del jugador
    private String teamReferenceFieldReference;
    private int jerseyNumberFieldReference;
    private String userPasswordFieldReference;
    private int phoneNumberNumericValueFieldReference;

    // Construye un jugador con todos los datos usados por la aplicacion
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

    // Devuelve el nombre del jugador
    public String getName() {
        return displayNameFieldReference;
    }

    // Devuelve el DNI del jugador
    public String getDni() {
        return nationalIdentityDocumentFieldReference;
    }

    // Devuelve el email del jugador
    public String getEmail() {
        return emailAddressFieldReference;
    }

    // Devuelve el nombre o id del equipo asociado
    public String getTeam() {
        return teamReferenceFieldReference;
    }

    // Devuelve el dorsal del jugador
    public int getNumber() {
        return jerseyNumberFieldReference;
    }

    // Devuelve el telefono del jugador
    public int getPhoneNumber() {
        return phoneNumberNumericValueFieldReference;
    }

    // Devuelve el identificador del equipo
    public String getTeamId() {
        return teamReferenceFieldReference;
    }

    // Actualiza la contrasena almacenada
    public void setPassword(String userPasswordParameterValue2) {
        this.userPasswordFieldReference = userPasswordParameterValue2;
    }

    // Devuelve la contrasena almacenada
    public String getPassword() {
        return userPasswordFieldReference;
    }

    // Cambia el equipo asignado al jugador
    public void setTeamId(String teamReferenceIdentifierParameterValue2) {
        this.teamReferenceFieldReference = teamReferenceIdentifierParameterValue2;
    }

    // Devuelve el DNI con el nombre legacy del metodo
    public String getDniPlayer() {
        return nationalIdentityDocumentFieldReference;
    }

    // Actualiza el DNI del jugador
    public void setDniPlayer(String nationalIdentityDocumentPlayerProfileParameterValue2) {
        this.nationalIdentityDocumentFieldReference =
                nationalIdentityDocumentPlayerProfileParameterValue2;
    }

    // Devuelve el email con el nombre legacy del metodo
    public String getMail() {
        return emailAddressFieldReference;
    }

    // Actualiza el email del jugador
    public void setMail(String emailAddressParameterValue2) {
        this.emailAddressFieldReference = emailAddressParameterValue2;
    }

    // Devuelve el nombre con el nombre legacy del metodo
    public String getNamePlayer() {
        return displayNameFieldReference;
    }

    // Actualiza el nombre del jugador
    public void setNamePlayer(String displayNamePlayerProfileParameterValue2) {
        this.displayNameFieldReference = displayNamePlayerProfileParameterValue2;
    }

    // Devuelve el dorsal con el nombre legacy del metodo
    public int getDorsal() {
        return jerseyNumberFieldReference;
    }

    // Actualiza el dorsal del jugador
    public void setDorsal(int jerseyNumberParameterValue2) {
        this.jerseyNumberFieldReference = jerseyNumberParameterValue2;
    }

    // Actualiza el telefono del jugador
    public void setPhoneNumber(int phoneNumberNumericValueParameterValue2) {
        this.phoneNumberNumericValueFieldReference =
                phoneNumberNumericValueParameterValue2;
    }
}
