package bussines.objects;

import java.time.LocalDateTime;

/**
 * La clase {@code Game} representa un partido de una liga deportiva.
 * Contiene información relevante como los equipos que participan, la fecha,
 * la jornada, el estado del partido, y el identificador de la liga.
 */
public class Game {
    /** Identificador único del partido. */
    private int gameEntityIdentifierFieldReference;
    /** Nombre del equipo local. */
    private String nomLocalFieldReference;
    /** Nombre del equipo visitante. */
    private String nomVisitantFieldReference;
    /** Fecha y hora programada del partido. */
    private LocalDateTime dataFieldReference;
    /** Identificador de la liga a la que pertenece el partido. */
    private int lligaIdentifierFieldReference;
    /** Número de la jornada del partido dentro de la liga. */
    private int jornadaFieldReference;
    /** Indica si el partido ha comenzado. */
    private boolean començatFieldReference;
    /** Indica si el partido ha finalizado. */
    private boolean acabatFieldReference;


    /**
     * Constructor para crear un nuevo objeto {@code Game}.
     *
     * @param gameId identificador único del partido
     * @param nomLocal nombre del equipo local
     * @param nomVisitant nombre del equipo visitante
     * @param data fecha y hora del partido
     * @param lligaId identificador de la liga
     * @param jornada número de jornada del partido
     * @param començat indica si el partido ha comenzado
     * @param acabat indica si el partido ha finalizado
     */
    public Game(int gameEntityIdentifierParameterValue, String nomLocalParameterValue, String nomVisitantParameterValue, LocalDateTime dataParameterValue, int lligaIdentifierParameterValue, int jornadaParameterValue, boolean començatParameterValue, boolean acabatParameterValue) {
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
     * Obtiene el identificador del partido.
     *
     * @return el ID del partido
     */
    public int getGameId() { return gameEntityIdentifierFieldReference; }
    /**
     * Obtiene el nombre del equipo local.
     *
     * @return nombre del equipo local
     */
    public String getNomLocal() { return nomLocalFieldReference; }
    /**
     * Obtiene el nombre del equipo visitante.
     *
     * @return nombre del equipo visitante
     */
    public String getNomVisitant() { return nomVisitantFieldReference; }
    /**
     * Obtiene la fecha y hora del partido.
     *
     * @return fecha y hora del partido
     */
    public LocalDateTime getData() { return dataFieldReference; }
    /**
     * Obtiene el identificador de la liga.
     *
     * @return ID de la liga
     */
    public int getLligaId() { return lligaIdentifierFieldReference; }
    /**
     * Obtiene el número de la jornada del partido.
     *
     * @return número de jornada
     */
    public int getJornada() { return jornadaFieldReference; }
    /**
     * Indica si el partido ha comenzado.
     *
     * @return {@code true} si ha comenzado, {@code false} en caso contrario
     */
    public boolean isComençat() { return començatFieldReference; }
    /**
     * Indica si el partido ha finalizado.
     *
     * @return {@code true} si ha finalizado, {@code false} en caso contrario
     */
    public boolean isAcabat() { return acabatFieldReference; }

    /**
     * Establece el identificador del partido.
     *
     * @param gameId nuevo ID del partido
     */
    public void setGameId(int gameEntityIdentifierParameterValue2) { this.gameEntityIdentifierFieldReference = gameEntityIdentifierParameterValue2; }
    /**
     * Establece el nombre del equipo local.
     *
     * @param nomLocal nuevo nombre del equipo local
     */
    public void setNomLocal(String nomLocalParameterValue2) { this.nomLocalFieldReference = nomLocalParameterValue2; }
    /**
     * Establece el nombre del equipo visitante.
     *
     * @param nomVisitant nuevo nombre del equipo visitante
     */
    public void setNomVisitant(String nomVisitantParameterValue2) { this.nomVisitantFieldReference = nomVisitantParameterValue2; }
    /**
     * Establece la fecha y hora del partido.
     *
     * @param data nueva fecha y hora del partido
     */
    public void setData(LocalDateTime dataParameterValue2) { this.dataFieldReference = dataParameterValue2; }
    /**
     * Establece el identificador de la liga.
     *
     * @param lligaId nuevo ID de la liga
     */
    public void setLligaId(int lligaIdentifierParameterValue2) { this.lligaIdentifierFieldReference = lligaIdentifierParameterValue2; }
    /**
     * Establece el número de la jornada.
     *
     * @param jornada nuevo número de jornada
     */
    public void setJornada(int jornadaParameterValue2) { this.jornadaFieldReference = jornadaParameterValue2; }
    /**
     * Establece si el partido ha comenzado.
     *
     * @param començat {@code true} si ha comenzado, {@code false} si no
     */
    public void setComençat(boolean començatParameterValue2) { this.començatFieldReference = començatParameterValue2; }
    /**
     * Establece si el partido ha finalizado.
     *
     * @param acabat {@code true} si ha finalizado, {@code false} si no
     */
    public void setAcabat(boolean acabatParameterValue2) { this.acabatFieldReference = acabatParameterValue2; }

}

