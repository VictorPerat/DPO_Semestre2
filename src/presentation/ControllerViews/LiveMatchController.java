package presentation.ControllerViews;

import bussines.LiveMatchesRegistry;
import bussines.LiveMatchesScoreboard;
import bussines.managers.GameManager;
import bussines.managers.TeamInfoManager;
import bussines.objects.Game;
import presentation.Views.LiveMatchView;


/**
 * Coordina la pantalla del partido en directo.
 */
public class LiveMatchController implements LiveMatchesRegistry.AbortableMatch {


    private LiveMatchView simulateViewInterfaceFieldReference;


    private Game gameEntityFieldReference;


    private GameManager gameEntityManagerServiceFieldReference;


    private int gameEntityIdentifierFieldReference;
    private int leagueReferenceIdentifierFieldReference;


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
     * Muestra el partido ventana.
     */
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
     * Gestiona esta operacion.
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


        if (simulateViewInterfaceFieldReference != null) {
            javax.swing.SwingUtilities.invokeLater(
                    simulateViewInterfaceFieldReference::dispose
            );
        }
    }
}


