package presentation.ControllerViews;

import bussines.LiveMatchesRegistry;
import bussines.LiveMatchesScoreboard;
import bussines.managers.ConfigManager;
import bussines.objects.Game;
import bussines.managers.GameManager;
import bussines.managers.TeamInfoManager;
import presentation.Views.LiveMatchView;

// Controlador que prepara la vista de un partido y procesa su resultado
public class LiveMatchController implements LiveMatchesRegistry.AbortableMatch {

    // Ventana que muestra la simulacion del partido
    private LiveMatchView simulateViewInterfaceFieldReference;

    // Partido sobre el que trabaja este controlador
    private Game gameEntityFieldReference;

    // Servicios auxiliares para actualizar configuracion y resultados
    private ConfigManager configFieldReference;
    private GameManager gameEntityManagerServiceFieldReference;

    // Identificadores necesarios para localizar partido y liga
    private int gameEntityIdentifierFieldReference;
    private int leagueReferenceIdentifierFieldReference;

    // Guarda las dependencias y deja preparada la vista del partido
    public LiveMatchController(Game gameEntityParameterValue,
                               ConfigManager configParameterValue,
                               GameManager gameEntityManagerServiceParameterValue,
                               int gameEntityIdentifierParameterValue,
                               int leagueReferenceIdentifierParameterValue) {

        this.gameEntityFieldReference = gameEntityParameterValue;
        this.configFieldReference = configParameterValue;
        this.gameEntityManagerServiceFieldReference =
                gameEntityManagerServiceParameterValue;
        this.gameEntityIdentifierFieldReference =
                gameEntityIdentifierParameterValue;
        this.leagueReferenceIdentifierFieldReference =
                leagueReferenceIdentifierParameterValue;

        initializeView();
    }

    // Crea la vista de simulacion y la enlaza con este controlador.
    // También publica un snapshot inicial 0-0 al scoreboard global para
    // que el widget de "Live Matches" pueda mostrar el partido en
    // cuanto empieza, antes de que se marque ningún gol.
    private void initializeView() {
        simulateViewInterfaceFieldReference = new LiveMatchView(
                gameEntityFieldReference.getNomLocal(),
                gameEntityFieldReference.getNomVisitant(),
                gameEntityIdentifierFieldReference
        );
        simulateViewInterfaceFieldReference.setVisible(false);
        simulateViewInterfaceFieldReference.setLiveMatchViewController(this);

        LiveMatchesScoreboard.getInstance().updateScore(
                gameEntityIdentifierFieldReference,
                new LiveMatchesScoreboard.ScoreSnapshot(
                        gameEntityIdentifierFieldReference,
                        leagueReferenceIdentifierFieldReference,
                        gameEntityFieldReference.getNomLocal(),
                        gameEntityFieldReference.getNomVisitant(),
                        0,
                        0
                )
        );

        // Registramos el partido para que pueda ser abortado si el
        // admin borra la liga o uno de los equipos (apartados 2.10/2.11).
        LiveMatchesRegistry.getInstance().register(this);
    }

    /**
     * Notificación desde {@link LiveMatchView} cuando se marca un gol:
     * actualizamos el scoreboard global para que el widget repinte el
     * marcador en tiempo real.
     */
    public void reportScoreUpdate(int homeGoalsParameterValue,
                                  int awayGoalsParameterValue) {
        LiveMatchesScoreboard.getInstance().updateScore(
                gameEntityIdentifierFieldReference,
                new LiveMatchesScoreboard.ScoreSnapshot(
                        gameEntityIdentifierFieldReference,
                        leagueReferenceIdentifierFieldReference,
                        gameEntityFieldReference.getNomLocal(),
                        gameEntityFieldReference.getNomVisitant(),
                        homeGoalsParameterValue,
                        awayGoalsParameterValue
                )
        );
    }

    // Devuelve la ventana asociada a este partido
    public LiveMatchView getSimulateView() {
        return simulateViewInterfaceFieldReference;
    }

    // Marca el partido como acabado y reparte los puntos correspondientes
    public void finalitzarPartit(String nomEquipGuanyadorParameterValue,
                                 int gameEntityIdentifierParameterValue2) {

        gameEntityManagerServiceFieldReference.marcarPartitComAcabat(
                gameEntityIdentifierParameterValue2
        );

        // Guardamos el ganador para poder reconstruir la evolución de
        // puntos por jornada en el gráfico de estadísticas.
        gameEntityManagerServiceFieldReference.setGameWinner(
                gameEntityIdentifierParameterValue2,
                nomEquipGuanyadorParameterValue
        );

        // Quitamos el partido del scoreboard global para que el widget
        // de "Live Matches" deje de mostrarlo (apartado 2.9).
        LiveMatchesScoreboard.getInstance().remove(
                gameEntityIdentifierParameterValue2
        );

        TeamInfoManager managerServiceLocalVariableValue = new TeamInfoManager();

        // Si no hay empate, se suman los puntos al ganador y se
        // incrementa la columna de derrotas del equipo perdedor.
        if (!nomEquipGuanyadorParameterValue.equalsIgnoreCase("DRAW")) {
            String loserNameLocalVariableValue =
                    gameEntityFieldReference.getNomLocal()
                            .equalsIgnoreCase(nomEquipGuanyadorParameterValue)
                    ? gameEntityFieldReference.getNomVisitant()
                    : gameEntityFieldReference.getNomLocal();

            managerServiceLocalVariableValue.afegirPuntsPerVictoria(
                    nomEquipGuanyadorParameterValue,
                    loserNameLocalVariableValue,
                    leagueReferenceIdentifierFieldReference
            );
        } else {
            // En empate se reparten los puntos entre ambos equipos
            managerServiceLocalVariableValue.afegirPuntsPerEmpat(
                    gameEntityFieldReference.getNomLocal(),
                    gameEntityFieldReference.getNomVisitant(),
                    leagueReferenceIdentifierFieldReference
            );
        }

        // El partido ha terminado normalmente: se desregistra del
        // registro de partidos en curso para que no se intente abortar.
        LiveMatchesRegistry.getInstance().unregister(
                gameEntityIdentifierParameterValue2
        );
    }

    // ---------- Implementación de AbortableMatch ----------

    @Override
    public int getGameId() {
        return gameEntityIdentifierFieldReference;
    }

    @Override
    public int getLeagueId() {
        return leagueReferenceIdentifierFieldReference;
    }

    @Override
    public String getHomeTeamName() {
        return gameEntityFieldReference.getNomLocal();
    }

    @Override
    public String getAwayTeamName() {
        return gameEntityFieldReference.getNomVisitant();
    }

    @Override
    public void showMatchWindow() {
        if (simulateViewInterfaceFieldReference == null) {
            return;
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            simulateViewInterfaceFieldReference.setVisible(true);
            simulateViewInterfaceFieldReference.toFront();
            simulateViewInterfaceFieldReference.requestFocus();
        });
    }

    /**
     * Detiene la simulación a medio curso, marca el partido como
     * acabado en BD (sin asignar puntos a nadie) y libera la vista.
     *
     * Se llama desde {@link LiveMatchesRegistry} cuando el admin borra
     * la liga (apartado 2.10) o uno de los equipos (apartado 2.11).
     */
    @Override
    public void abortMatch() {
        // 1. Para el bucle de simulación dentro de la vista
        if (simulateViewInterfaceFieldReference != null) {
            simulateViewInterfaceFieldReference.stopSimulation();
        }

        // 2. Marca el partido como finalizado sin ganador en BD
        gameEntityManagerServiceFieldReference.marcarPartitComAcabat(
                gameEntityIdentifierFieldReference
        );
        gameEntityManagerServiceFieldReference.setGameWinner(
                gameEntityIdentifierFieldReference,
                null
        );

        // 3. Saca el partido del widget global
        LiveMatchesScoreboard.getInstance().remove(
                gameEntityIdentifierFieldReference
        );

        // 4. Cierra la ventana de simulación si aún estaba abierta
        if (simulateViewInterfaceFieldReference != null) {
            javax.swing.SwingUtilities.invokeLater(
                    simulateViewInterfaceFieldReference::dispose
            );
        }
    }
}
