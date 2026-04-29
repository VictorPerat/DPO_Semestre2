package bussines;

import bussines.managers.ConfigManager;
import bussines.managers.GameManager;
import bussines.objects.Game;
import presentation.ControllerViews.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Esta clase se encarga de revisar si hay partidos que ya tienen que empezar.
 */
public class MatchRunner implements Runnable {

    // Manager de partidos
    private GameManager gameEntityManagerServiceFieldReference;

    // Id de la liga que se está controlando
    private int leagueReferenceIdentifierFieldReference;

    // Tiempo entre revisiones
    private long intervalFieldReference;

    // Indica si hay partidos en juego

    // Configuración general del sistema
    private ConfigManager configFieldReference;

    // Manager de equipos

    // Constructor que guarda la información necesaria
    public MatchRunner(GameManager gameEntityManagerServiceParameterValue,
                       int leagueReferenceIdentifierParameterValue,
                       long intervalParameterValue,
                       ConfigManager configParameterValue) {

        this.gameEntityManagerServiceFieldReference = gameEntityManagerServiceParameterValue;
        this.leagueReferenceIdentifierFieldReference = leagueReferenceIdentifierParameterValue;
        this.intervalFieldReference = intervalParameterValue;
        this.configFieldReference = configParameterValue;
    }

    // Método que se ejecuta cuando se lanza el hilo
    public void run() {
        while (true) {
            revisarPartits();

            try {
                Thread.sleep(intervalFieldReference);
            } catch (InterruptedException eventArgumentExceptionParameter) {
                eventArgumentExceptionParameter.printStackTrace();
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    // Revisa los partidos de la liga y comprueba si alguno debe empezar
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

            // Si la BD ya marcaba el partido como iniciado (por ejemplo
            // tras cargar un seed demo), se reconstruye su simulación
            // una sola vez para poblar el scoreboard y el widget global.
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

            // Si el partido todavía no ha empezado pero ya toca jugarlo, se marca como iniciado
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

    private void createLiveMatchController(Game gameEntityParameterValue,
                                           int gameEntityIdentifierParameterValue) {
        new LiveMatchController(
                gameEntityParameterValue,
                configFieldReference,
                gameEntityManagerServiceFieldReference,
                gameEntityIdentifierParameterValue,
                leagueReferenceIdentifierFieldReference
        );
    }
}
