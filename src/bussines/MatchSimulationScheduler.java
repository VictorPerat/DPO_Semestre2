package bussines;

import bussines.managers.ConfigManager;
import bussines.managers.GameManager;
import bussines.managers.LeagueManager;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Servicio en segundo plano que mantiene un {@link MatchRunner} activo
 * por cada liga existente en el sistema.
 *
 * Responsabilidades:
 *  - Al arrancar la aplicación, descubre todas las ligas y lanza un
 *    {@link MatchRunner} (en su propio hilo) para cada una.
 *  - Periódicamente vuelve a consultar la lista de ligas y arranca un
 *    runner también para las ligas creadas en tiempo de ejecución.
 *  - Todos los hilos son daemon, así que la JVM puede terminar sin
 *    esperar a que el bucle de simulación se detenga.
 *
 * Sustituye al patrón "while(true) + Thread.sleep" del Main del año
 * pasado, que filtraba threads por cada liga en cada iteración.
 */
public final class MatchSimulationScheduler {

    /** Cada cuánto se vuelve a comprobar si han aparecido ligas nuevas. */
    private static final long SCAN_INTERVAL_MS = 30_000;

    /** Cada cuánto un MatchRunner revisa los partidos de su liga. */
    private static final long RUNNER_INTERVAL_MS = 2_000;

    /** Garantiza que el scheduler solo se arranque una vez. */
    private static volatile boolean startedFieldReference = false;

    /** Identificadores de ligas que ya tienen un MatchRunner asociado. */
    private static final Set<Integer> trackedLeagueIdsFieldReference =
            new CopyOnWriteArraySet<>();

    private MatchSimulationScheduler() {
    }

    /**
     * Arranca el supervisor. Llamadas adicionales son ignoradas.
     */
    public static synchronized void start(GameManager gameEntityManagerServiceParameterValue,
                                          LeagueManager leagueReferenceManagerServiceParameterValue,
                                          ConfigManager configParameterValue) {
        if (startedFieldReference) {
            return;
        }
        startedFieldReference = true;

        Thread supervisorThreadLocalVariableValue = new Thread(
                () -> supervisorLoop(
                        gameEntityManagerServiceParameterValue,
                        leagueReferenceManagerServiceParameterValue,
                        configParameterValue
                ),
                "MatchSimulationScheduler-Supervisor"
        );
        supervisorThreadLocalVariableValue.setDaemon(true);
        supervisorThreadLocalVariableValue.start();
    }

    /**
     * Bucle del supervisor. Cada SCAN_INTERVAL_MS recoge las ligas y
     * arranca un MatchRunner para cada nueva.
     */
    private static void supervisorLoop(GameManager gameEntityManagerServiceParameterValue,
                                       LeagueManager leagueReferenceManagerServiceParameterValue,
                                       ConfigManager configParameterValue) {
        while (true) {
            try {
                for (Integer leagueIdLocalVariableValue :
                        leagueReferenceManagerServiceParameterValue.getAllLeagueIds()) {

                    if (trackedLeagueIdsFieldReference.add(leagueIdLocalVariableValue)) {
                        startRunnerForLeague(
                                gameEntityManagerServiceParameterValue,
                                leagueIdLocalVariableValue,
                                configParameterValue
                        );
                    }
                }

                Thread.sleep(SCAN_INTERVAL_MS);
            } catch (InterruptedException eventArgumentInterruptedExceptionParameterValue) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception eventArgumentExceptionParameterValue) {
                eventArgumentExceptionParameterValue.printStackTrace();
                try {
                    Thread.sleep(SCAN_INTERVAL_MS);
                } catch (InterruptedException eventArgumentInterruptedExceptionParameterValue2) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    /**
     * Lanza un MatchRunner en un hilo daemon para la liga indicada.
     */
    private static void startRunnerForLeague(GameManager gameEntityManagerServiceParameterValue,
                                             int leagueIdParameterValue,
                                             ConfigManager configParameterValue) {
        MatchRunner runnerLocalVariableValue = new MatchRunner(
                gameEntityManagerServiceParameterValue,
                leagueIdParameterValue,
                RUNNER_INTERVAL_MS,
                configParameterValue
        );

        Thread runnerThreadLocalVariableValue = new Thread(
                runnerLocalVariableValue,
                "MatchRunner-League-" + leagueIdParameterValue
        );
        runnerThreadLocalVariableValue.setDaemon(true);
        runnerThreadLocalVariableValue.start();
    }
}
