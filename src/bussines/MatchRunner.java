package bussines;

import bussines.managers.ConfigManager;
import bussines.managers.GameManager;
import bussines.objects.Game;
import presentation.ControllerViews.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


/**
 * Agrupa la logica de el partido.
 */
public class MatchRunner implements Runnable {


    private GameManager gameEntityManagerServiceFieldReference;


    private int leagueReferenceIdentifierFieldReference;


    private long intervalFieldReference;


    private ConfigManager configFieldReference;


    /**
     * Crea una instancia de el partido.
     *
     * @param gameEntityManagerServiceParameterValue partido que usa la operacion.
     * @param leagueReferenceIdentifierParameterValue liga identificador.
     * @param intervalParameterValue dato de entrada de la operacion.
     * @param configParameterValue configuracion que usa la operacion.
     */
    public MatchRunner(GameManager gameEntityManagerServiceParameterValue,
                       int leagueReferenceIdentifierParameterValue,
                       long intervalParameterValue,
                       ConfigManager configParameterValue) {

        this.gameEntityManagerServiceFieldReference = gameEntityManagerServiceParameterValue;
        this.leagueReferenceIdentifierFieldReference = leagueReferenceIdentifierParameterValue;
        this.intervalFieldReference = intervalParameterValue;
        this.configFieldReference = configParameterValue;
    }


    /**
     * Gestiona esta operacion.
     */
    public void run() {
        while (true) {
            revisarPartits();

            try {
                Thread.sleep(intervalFieldReference);
            } catch (InterruptedException eventArgumentExceptionParameter) {
                shared.DaoErrorHandler.log("MatchRunner", eventArgumentExceptionParameter);
                Thread.currentThread().interrupt();
                break;
            }
        }
    }


    /**
     * Gestiona esta operacion.
     */
    private void revisarPartits() {
        LocalDateTime ahoraLocalVariableValue =
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        ArrayList<Game> listaPartidosLocalVariableValue =
                gameEntityManagerServiceFieldReference.getGamesByLeague(
                        leagueReferenceIdentifierFieldReference
                );

        for (Game partidoLocalVariableValue : listaPartidosLocalVariableValue) {
            if (partidoLocalVariableValue.isAcabat()) {
                continue;
            }

            int identifierPartidoLocalVariableValue =
                    partidoLocalVariableValue.getGameId();

            if (identifierPartidoLocalVariableValue <= 0) {
                identifierPartidoLocalVariableValue =
                        gameEntityManagerServiceFieldReference.getGameIdByLeague(
                                partidoLocalVariableValue.getNomLocal(),
                                partidoLocalVariableValue.getNomVisitant(),
                                leagueReferenceIdentifierFieldReference
                        );
            }

            if (identifierPartidoLocalVariableValue <= 0) {
                continue;
            }


            if (partidoLocalVariableValue.isComençat()) {
                if (!LiveMatchesRegistry.getInstance().isRegistered(
                        identifierPartidoLocalVariableValue)) {
                    createLiveMatchController(
                            partidoLocalVariableValue,
                            identifierPartidoLocalVariableValue
                    );
                }
                continue;
            }

            LocalDateTime inicioPartidoLocalVariableValue = partidoLocalVariableValue.getData();
            LocalDateTime finPartidoLocalVariableValue =
                    inicioPartidoLocalVariableValue.plusMinutes(
                            configFieldReference.getDurationMatch()
                    );

            boolean noHaEmpezadoLocalVariableValue = !partidoLocalVariableValue.isComençat();
            boolean haLlegadoHoraInicioLocalVariableValue =
                    !ahoraLocalVariableValue.isBefore(inicioPartidoLocalVariableValue);
            boolean aunNoHaTerminadoLocalVariableValue =
                    ahoraLocalVariableValue.isBefore(finPartidoLocalVariableValue);


            if (noHaEmpezadoLocalVariableValue
                    && haLlegadoHoraInicioLocalVariableValue
                    && aunNoHaTerminadoLocalVariableValue) {
                gameEntityManagerServiceFieldReference.actualitzaComençat(
                        identifierPartidoLocalVariableValue,
                        true
                );

                System.out.println(
                        "¡Partido iniciado! "
                                + partidoLocalVariableValue.getNomLocal()
                                + " vs "
                                + partidoLocalVariableValue.getNomVisitant()
                );
                createLiveMatchController(
                        partidoLocalVariableValue,
                        identifierPartidoLocalVariableValue
                );
            }
        }
    }


    /**
     * Crea el directo partido.
     *
     * @param gameEntityParameterValue partido que usa la operacion.
     * @param gameEntityIdentifierParameterValue partido identificador.
     */
    private void createLiveMatchController(Game gameEntityParameterValue,
                                           int gameEntityIdentifierParameterValue) {
        new LiveMatchController(
                gameEntityParameterValue,
                gameEntityManagerServiceFieldReference,
                gameEntityIdentifierParameterValue,
                leagueReferenceIdentifierFieldReference
        );
    }
}


