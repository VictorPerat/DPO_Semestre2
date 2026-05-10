package bussines.objects;

import java.time.LocalDateTime;


/**
 * Agrupa la logica de el partido.
 */
public class Game {


    private int gameEntityIdentifierFieldReference;


    private String nomLocalFieldReference;
    private String nomVisitantFieldReference;


    private LocalDateTime dataFieldReference;
    private int lligaIdentifierFieldReference;


    private int jornadaFieldReference;
    private boolean començatFieldReference;
    private boolean acabatFieldReference;


    private String winnerNameFieldReference;


    /**
     * Crea una instancia de el partido.
     *
     * @param gameEntityIdentifierParameterValue partido identificador.
     * @param nomLocalParameterValue dato de entrada de la operacion.
     * @param nomVisitantParameterValue dato de entrada de la operacion.
     * @param dataParameterValue dato de entrada de la operacion.
     * @param lligaIdentifierParameterValue identificador que usa la operacion.
     * @param jornadaParameterValue dato de entrada de la operacion.
     * @param començatParameterValue dato de entrada de la operacion.
     * @param acabatParameterValue dato de entrada de la operacion.
     */
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


    /**
     * Devuelve el partido.
     *
     * @return el partido.
     */
    public int getGameId() { return gameEntityIdentifierFieldReference; }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public String getNomLocal() { return nomLocalFieldReference; }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public String getNomVisitant() { return nomVisitantFieldReference; }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public LocalDateTime getData() { return dataFieldReference; }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public int getLligaId() { return lligaIdentifierFieldReference; }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    public int getJornada() { return jornadaFieldReference; }


    /**
     * Indica el estado actual.
     *
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean isComençat() { return començatFieldReference; }


    /**
     * Indica el estado actual.
     *
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean isAcabat() { return acabatFieldReference; }


    /**
     * Actualiza el partido.
     *
     * @param gameEntityIdentifierParameterValue2 partido identificador.
     */
    public void setGameId(int gameEntityIdentifierParameterValue2) {
        this.gameEntityIdentifierFieldReference = gameEntityIdentifierParameterValue2;
    }


    /**
     * Actualiza el contenido.
     *
     * @param nomLocalParameterValue2 dato de entrada de la operacion.
     */
    public void setNomLocal(String nomLocalParameterValue2) {
        this.nomLocalFieldReference = nomLocalParameterValue2;
    }


    /**
     * Actualiza el contenido.
     *
     * @param nomVisitantParameterValue2 dato de entrada de la operacion.
     */
    public void setNomVisitant(String nomVisitantParameterValue2) {
        this.nomVisitantFieldReference = nomVisitantParameterValue2;
    }


    /**
     * Actualiza el contenido.
     *
     * @param dataParameterValue2 dato de entrada de la operacion.
     */
    public void setData(LocalDateTime dataParameterValue2) {
        this.dataFieldReference = dataParameterValue2;
    }


    /**
     * Actualiza el contenido.
     *
     * @param lligaIdentifierParameterValue2 identificador que usa la operacion.
     */
    public void setLligaId(int lligaIdentifierParameterValue2) {
        this.lligaIdentifierFieldReference = lligaIdentifierParameterValue2;
    }


    /**
     * Actualiza el contenido.
     *
     * @param jornadaParameterValue2 dato de entrada de la operacion.
     */
    public void setJornada(int jornadaParameterValue2) {
        this.jornadaFieldReference = jornadaParameterValue2;
    }


    /**
     * Actualiza el contenido.
     *
     * @param començatParameterValue2 dato de entrada de la operacion.
     */
    public void setComençat(boolean començatParameterValue2) {
        this.començatFieldReference = començatParameterValue2;
    }


    /**
     * Actualiza el contenido.
     *
     * @param acabatParameterValue2 dato de entrada de la operacion.
     */
    public void setAcabat(boolean acabatParameterValue2) {
        this.acabatFieldReference = acabatParameterValue2;
    }


    /**
     * Devuelve el nombre.
     *
     * @return el nombre.
     */
    public String getWinnerName() {
        return winnerNameFieldReference;
    }


    /**
     * Actualiza el nombre.
     *
     * @param winnerNameParameterValue nombre que usa la operacion.
     */
    public void setWinnerName(String winnerNameParameterValue) {
        this.winnerNameFieldReference = winnerNameParameterValue;
    }
}


