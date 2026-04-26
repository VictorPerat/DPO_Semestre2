package presentation.ControllerViews;

import bussines.managers.ConfigManager;
import bussines.objects.Game;
import bussines.managers.GameManager;
import bussines.managers.TeamInfoManager;
import presentation.Views.LiveMatchView;

/**
 * Controlador para gestionar la vista de un partido en vivo (LiveMatchView).
 * Permite inicializar la vista de simulación del partido y manejar su finalización.
 */
public class LiveMatchController {
    private LiveMatchView simulateViewInterfaceFieldReference;
    private Game gameEntityFieldReference;
    private ConfigManager configFieldReference;
    private GameManager gameEntityManagerServiceFieldReference;
    private int gameEntityIdentifierFieldReference;
    private int leagueReferenceIdentifierFieldReference;

    /**
     * Constructor del controlador de vista de partido en vivo.
     *
     * @param game         Objeto Game que representa el partido.
     * @param config       Configuración global del sistema.
     * @param gameManager  Gestor de lógica de partidos.
     * @param gameID       Identificador único del partido.
     * @param leagueID     Identificador de la liga a la que pertenece el partido.
     */
    public LiveMatchController(Game gameEntityParameterValue, ConfigManager configParameterValue, GameManager gameEntityManagerServiceParameterValue, int gameEntityIdentifierParameterValue, int leagueReferenceIdentifierParameterValue) {
        this.gameEntityFieldReference = gameEntityParameterValue;
        this.configFieldReference = configParameterValue;
        this.gameEntityManagerServiceFieldReference = gameEntityManagerServiceParameterValue;
        this.gameEntityIdentifierFieldReference = gameEntityIdentifierParameterValue;
        this.leagueReferenceIdentifierFieldReference = leagueReferenceIdentifierParameterValue;
        initializeView();
    }

    /**
     * Inicializa la vista del partido en vivo con los nombres de los equipos y asigna este controlador.
     */
    private void initializeView() {
        simulateViewInterfaceFieldReference = new LiveMatchView(gameEntityFieldReference.getNomLocal(), gameEntityFieldReference.getNomVisitant(), gameEntityIdentifierFieldReference);
        simulateViewInterfaceFieldReference.setVisible(false);
        simulateViewInterfaceFieldReference.setLiveMatchViewController(this);
    }

    /**
     * Devuelve la vista del partido en vivo.
     *
     * @return Vista de LiveMatchView asociada al controlador.
     */
    public LiveMatchView getSimulateView() {
        return simulateViewInterfaceFieldReference;
    }

    /**
     * Finaliza un partido, marcándolo como terminado en el GameManager y
     * actualiza la puntuación de los equipos dependiendo del resultado.
     *
     * @param nomEquipGuanyador Nombre del equipo ganador o "DRAW" si es empate.
     * @param gameId            Identificador del partido que ha terminado.
     */
    public void finalitzarPartit(String nomEquipGuanyadorParameterValue, int gameEntityIdentifierParameterValue2) {
        gameEntityManagerServiceFieldReference.marcarPartitComAcabat(gameEntityIdentifierParameterValue2);

        TeamInfoManager managerServiceLocalVariableValue = new TeamInfoManager();
        if (!nomEquipGuanyadorParameterValue.equalsIgnoreCase("DRAW")) {
            managerServiceLocalVariableValue.afegirPuntsPerVictoria(nomEquipGuanyadorParameterValue, leagueReferenceIdentifierFieldReference);
        } else {
            managerServiceLocalVariableValue.afegirPuntsPerEmpat(gameEntityFieldReference.getNomLocal(), gameEntityFieldReference.getNomVisitant(), leagueReferenceIdentifierFieldReference);
        }
    }
}
