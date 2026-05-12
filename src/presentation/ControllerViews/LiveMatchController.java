package presentation.ControllerViews;

import bussines.LiveMatchesRegistry;
import bussines.LiveMatchesScoreboard;
import bussines.managers.GameManager;
import bussines.managers.TeamInfoManager;
import bussines.objects.Game;
import presentation.AppNavigator;
import presentation.Views.LiveMatchView;
import presentation.Views.MainView;


/**
 * Coordina la pantalla del partido en directo.
 */
public class LiveMatchController implements LiveMatchesRegistry.AbortableMatch {


    private LiveMatchView simulateViewInterfaceFieldReference;


    private Game gameEntityFieldReference;


    private GameManager gameEntityManagerServiceFieldReference;


    private int gameEntityIdentifierFieldReference;
    private int leagueReferenceIdentifierFieldReference;

    // Identificador único de pantalla en el CardLayout (uno por partido)
    private String screenIdFieldReference;


    /**
     * Crea una instancia de el directo partido.
     *
     * @param gameEntityParameterValue partido que usa la operacion.
     * @param gameEntityManagerServiceParameterValue partido que usa la operacion.
     * @param gameEntityIdentifierParameterValue partido identificador.
     * @param leagueReferenceIdentifierParameterValue liga identificador.
     */
    public LiveMatchController(Game gameEntityParameterValue,
                               GameManager gameEntityManagerServiceParameterValue,
                               int gameEntityIdentifierParameterValue,
                               int leagueReferenceIdentifierParameterValue) {

        this.gameEntityFieldReference = gameEntityParameterValue;
        this.gameEntityManagerServiceFieldReference =
                gameEntityManagerServiceParameterValue;
        this.gameEntityIdentifierFieldReference =
                gameEntityIdentifierParameterValue;
        this.leagueReferenceIdentifierFieldReference =
                leagueReferenceIdentifierParameterValue;

        initializeView();
    }


    /**
     * Gestiona esta operacion.
     */
    private void initializeView() {
        simulateViewInterfaceFieldReference = new LiveMatchView(
                gameEntityFieldReference.getNomLocal(),
                gameEntityFieldReference.getNomVisitant(),
                gameEntityIdentifierFieldReference
        );
        simulateViewInterfaceFieldReference.setLiveMatchViewController(this);

        // Identificador único de pantalla para este partido
        this.screenIdFieldReference =
                AppNavigator.LIVE_MATCH + "_" + gameEntityIdentifierFieldReference;

        // Registra la pantalla como tarjeta en el CardLayout principal.
        // De esta forma showMatchWindow() puede navegar a ella en lugar
        // de abrir una ventana JFrame nueva.
        javax.swing.SwingUtilities.invokeLater(() -> {
            AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();
            if (navigatorLocalVariableValue != null) {
                MainView mainViewLocalVariableValue =
                        navigatorLocalVariableValue.getMainView();
                if (mainViewLocalVariableValue != null) {
                    mainViewLocalVariableValue.addScreen(
                            screenIdFieldReference,
                            simulateViewInterfaceFieldReference
                    );
                }
                // BACK del partido vuelve a la pantalla de Live Matches
                simulateViewInterfaceFieldReference.setBackButtonListener(
                        eventArgumentParameterValue ->
                                navigatorLocalVariableValue.show(AppNavigator.LIVE_MATCHES)
                );
            }
        });

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


        LiveMatchesRegistry.getInstance().register(this);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param homeGoalsParameterValue dato de entrada de la operacion.
     * @param awayGoalsParameterValue dato de entrada de la operacion.
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


    /**
     * Devuelve el vista.
     *
     * @return el vista.
     */
    public LiveMatchView getSimulateView() {
        return simulateViewInterfaceFieldReference;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param nomEquipGuanyadorParameterValue dato de entrada de la operacion.
     * @param gameEntityIdentifierParameterValue2 partido identificador.
     */
    public void finalitzarPartit(String nomEquipGuanyadorParameterValue,
                                 int gameEntityIdentifierParameterValue2) {

        gameEntityManagerServiceFieldReference.marcarPartitComAcabat(
                gameEntityIdentifierParameterValue2
        );


        gameEntityManagerServiceFieldReference.setGameWinner(
                gameEntityIdentifierParameterValue2,
                nomEquipGuanyadorParameterValue
        );


        LiveMatchesScoreboard.getInstance().remove(
                gameEntityIdentifierParameterValue2
        );

        TeamInfoManager managerServiceLocalVariableValue = new TeamInfoManager();


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

            managerServiceLocalVariableValue.afegirPuntsPerEmpat(
                    gameEntityFieldReference.getNomLocal(),
                    gameEntityFieldReference.getNomVisitant(),
                    leagueReferenceIdentifierFieldReference
            );
        }


        LiveMatchesRegistry.getInstance().unregister(
                gameEntityIdentifierParameterValue2
        );
    }


    /**
     * Devuelve el partido.
     *
     * @return el partido.
     */
    @Override
    public int getGameId() {
        return gameEntityIdentifierFieldReference;
    }


    /**
     * Devuelve el liga.
     *
     * @return el liga.
     */
    @Override
    public int getLeagueId() {
        return leagueReferenceIdentifierFieldReference;
    }


    /**
     * Devuelve el equipo nombre.
     *
     * @return el equipo nombre.
     */
    @Override
    public String getHomeTeamName() {
        return gameEntityFieldReference.getNomLocal();
    }


    /**
     * Devuelve el equipo nombre.
     *
     * @return el equipo nombre.
     */
    @Override
    public String getAwayTeamName() {
        return gameEntityFieldReference.getNomVisitant();
    }


    /**
     * Muestra el partido como una tarjeta del CardLayout principal
     * (ya no abre ventana nueva). Navega vía AppNavigator usando el
     * identificador único de esta partida.
     */
    @Override
    public void showMatchWindow() {
        if (simulateViewInterfaceFieldReference == null
                || screenIdFieldReference == null) {
            return;
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();
            if (navigatorLocalVariableValue == null) {
                return;
            }
            navigatorLocalVariableValue.show(screenIdFieldReference);
        });
    }


    /**
     * Aborta el partido: detiene la simulación, marca como acabado en BD
     * y elimina la tarjeta del CardLayout (ya no hay JFrame que cerrar).
     */
    @Override
    public void abortMatch() {

        if (simulateViewInterfaceFieldReference != null) {
            simulateViewInterfaceFieldReference.stopSimulation();
        }


        gameEntityManagerServiceFieldReference.marcarPartitComAcabat(
                gameEntityIdentifierFieldReference
        );
        gameEntityManagerServiceFieldReference.setGameWinner(
                gameEntityIdentifierFieldReference,
                null
        );


        LiveMatchesScoreboard.getInstance().remove(
                gameEntityIdentifierFieldReference
        );

        // Si la pantalla actual del navegador es esta partida, volvemos
        // al listado de partidos en directo para no dejar al usuario
        // mirando un panel sin contenido.
        if (screenIdFieldReference != null) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                AppNavigator navigatorLocalVariableValue = AppNavigator.getInstance();
                if (navigatorLocalVariableValue != null
                        && screenIdFieldReference.equals(
                                navigatorLocalVariableValue.getCurrentScreenIdentifier())) {
                    navigatorLocalVariableValue.show(AppNavigator.LIVE_MATCHES);
                }
            });
        }
    }
}


