package bussines.objects;

import java.time.LocalDateTime;

// Objeto que representa un partido con sus datos basicos de estado
public class Game {

    // Identificador unico del partido
    private int gameEntityIdentifierFieldReference;

    // Nombre del equipo local y del visitante
    private String nomLocalFieldReference;
    private String nomVisitantFieldReference;

    // Fecha del partido y liga a la que pertenece
    private LocalDateTime dataFieldReference;
    private int lligaIdentifierFieldReference;

    // Jornada y estado actual del partido
    private int jornadaFieldReference;
    private boolean començatFieldReference;
    private boolean acabatFieldReference;

    // Nombre del equipo ganador (o "DRAW" si fue empate, null si el
    // partido todavía no ha acabado).
    private String winnerNameFieldReference;

    // Construye un partido con todos sus datos persistidos
    public Game(int gameEntityIdentifierParameterValue,
                String nomLocalParameterValue,
                String nomVisitantParameterValue,
                LocalDateTime dataParameterValue,
                int lligaIdentifierParameterValue,
                int jornadaParameterValue,
                boolean començatParameterValue,
                boolean acabatParameterValue) {
        this.gameEntityIdentifierFieldReference = gameEntityIdentifierParameterValue;
        this.nomLocalFieldReference = nomLocalParameterValue;
        this.nomVisitantFieldReference = nomVisitantParameterValue;
        this.dataFieldReference = dataParameterValue;
        this.lligaIdentifierFieldReference = lligaIdentifierParameterValue;
        this.jornadaFieldReference = jornadaParameterValue;
        this.començatFieldReference = començatParameterValue;
        this.acabatFieldReference = acabatParameterValue;
    }

    // Devuelve el id del partido
    public int getGameId() { return gameEntityIdentifierFieldReference; }

    // Devuelve el nombre del equipo local
    public String getNomLocal() { return nomLocalFieldReference; }

    // Devuelve el nombre del equipo visitante
    public String getNomVisitant() { return nomVisitantFieldReference; }

    // Devuelve la fecha y hora del partido
    public LocalDateTime getData() { return dataFieldReference; }

    // Devuelve el id de la liga
    public int getLligaId() { return lligaIdentifierFieldReference; }

    // Devuelve la jornada del partido
    public int getJornada() { return jornadaFieldReference; }

    // Indica si el partido ya ha comenzado
    public boolean isComençat() { return començatFieldReference; }

    // Indica si el partido ya ha finalizado
    public boolean isAcabat() { return acabatFieldReference; }

    // Actualiza el id del partido
    public void setGameId(int gameEntityIdentifierParameterValue2) {
        this.gameEntityIdentifierFieldReference = gameEntityIdentifierParameterValue2;
    }

    // Actualiza el equipo local
    public void setNomLocal(String nomLocalParameterValue2) {
        this.nomLocalFieldReference = nomLocalParameterValue2;
    }

    // Actualiza el equipo visitante
    public void setNomVisitant(String nomVisitantParameterValue2) {
        this.nomVisitantFieldReference = nomVisitantParameterValue2;
    }

    // Actualiza la fecha del partido
    public void setData(LocalDateTime dataParameterValue2) {
        this.dataFieldReference = dataParameterValue2;
    }

    // Actualiza el id de la liga
    public void setLligaId(int lligaIdentifierParameterValue2) {
        this.lligaIdentifierFieldReference = lligaIdentifierParameterValue2;
    }

    // Actualiza la jornada del partido
    public void setJornada(int jornadaParameterValue2) {
        this.jornadaFieldReference = jornadaParameterValue2;
    }

    // Marca si el partido ha empezado o no
    public void setComençat(boolean començatParameterValue2) {
        this.començatFieldReference = començatParameterValue2;
    }

    // Marca si el partido ha acabado o no
    public void setAcabat(boolean acabatParameterValue2) {
        this.acabatFieldReference = acabatParameterValue2;
    }

    // Devuelve el nombre del ganador (o "DRAW" / null)
    public String getWinnerName() {
        return winnerNameFieldReference;
    }

    // Asigna el nombre del ganador
    public void setWinnerName(String winnerNameParameterValue) {
        this.winnerNameFieldReference = winnerNameParameterValue;
    }
}
