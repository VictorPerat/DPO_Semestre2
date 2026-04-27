package presentation.ControllerViews;

import bussines.managers.ConfigManager;
import bussines.objects.Game;
import bussines.managers.GameManager;
import bussines.managers.TeamInfoManager;
import presentation.Views.LiveMatchView;

// Controlador que prepara la vista de un partido y procesa su resultado
public class LiveMatchController {

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

    // Crea la vista de simulacion y la enlaza con este controlador
    private void initializeView() {
        simulateViewInterfaceFieldReference = new LiveMatchView(
                gameEntityFieldReference.getNomLocal(),
                gameEntityFieldReference.getNomVisitant(),
                gameEntityIdentifierFieldReference
        );
        simulateViewInterfaceFieldReference.setVisible(false);
        simulateViewInterfaceFieldReference.setLiveMatchViewController(this);
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

        TeamInfoManager managerServiceLocalVariableValue = new TeamInfoManager();

        // Si no hay empate, se suman los puntos al ganador
        if (!nomEquipGuanyadorParameterValue.equalsIgnoreCase("DRAW")) {
            managerServiceLocalVariableValue.afegirPuntsPerVictoria(
                    nomEquipGuanyadorParameterValue,
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
    }
}
