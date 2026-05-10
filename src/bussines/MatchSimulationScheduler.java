package bussines;

import bussines.managers.ConfigManager;
import bussines.managers.GameManager;
import bussines.managers.LeagueManager;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * Agrupa la logica de el partido.
 */
public final class MatchSimulationScheduler {


    private static final long SCAN_INTERVAL_MS = 30_000;


    private static final long RUNNER_INTERVAL_MS = 2_000;


    private static volatile boolean startedFieldReference = false;


    private static final Set<Integer> trackedLeagueIdsFieldReference =
            new CopyOnWriteArraySet<>();


    /**
     * Crea una instancia de el partido.
     */
    private MatchSimulationScheduler() {
    }


    /**
     * Gestiona esta operacion.
     *
     * @param gameEntityManagerServiceParameterValue partido que usa la operacion.
     * @param leagueReferenceManagerServiceParameterValue liga que usa la operacion.
     * @param configParameterValue configuracion que usa la operacion.
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
     * Gestiona esta operacion.
     *
     * @param gameEntityManagerServiceParameterValue partido que usa la operacion.
     * @param leagueReferenceManagerServiceParameterValue liga que usa la operacion.
     * @param configParameterValue configuracion que usa la operacion.
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
                shared.DaoErrorHandler.log("MatchSimulationScheduler", eventArgumentExceptionParameterValue);
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
     * Gestiona esta operacion.
     *
     * @param gameEntityManagerServiceParameterValue partido que usa la operacion.
     * @param leagueIdParameterValue liga que usa la operacion.
     * @param configParameterValue configuracion que usa la operacion.
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


