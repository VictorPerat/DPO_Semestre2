package bussines;

import bussines.managers.ConfigManager;
import bussines.managers.GameManager;
import bussines.managers.TeamManager;
import bussines.objects.Game;
import presentation.ControllerViews.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
/**
 * Clase que implementa un hilo encargado de comprobar periódicamente si hay partidos
 * que deben comenzar, basándose en la hora actual y la configuración del sistema.
 */
public class MatchRunner implements Runnable {
    private GameManager gameEntityManagerServiceFieldReference;
    private int leagueReferenceIdentifierFieldReference;
    private long intervalFieldReference;
    private boolean isPlayingFieldReference;
    private ConfigManager configFieldReference;
    private TeamManager teamReferenceManagerServiceFieldReference = new TeamManager();
    /**
     * Constructor de la clase RunnerPartits.
     *
     * @param gameManager Referencia al gestor de partidos.
     * @param leagueId    Identificador de la liga que se está supervisando.
     * @param interval    Intervalo de tiempo (en milisegundos) entre comprobaciones.
     * @param config      Configuración general del sistema.
     */
    public MatchRunner(GameManager gameEntityManagerServiceParameterValue, int leagueReferenceIdentifierParameterValue, long intervalParameterValue, ConfigManager configParameterValue) {
        this.gameEntityManagerServiceFieldReference = gameEntityManagerServiceParameterValue;
        this.leagueReferenceIdentifierFieldReference = leagueReferenceIdentifierParameterValue;
        this.intervalFieldReference = intervalParameterValue;
        this.isPlayingFieldReference = false;
        this.configFieldReference = configParameterValue;
    }
    /**
     * Método que ejecuta el hilo. Revisa continuamente los partidos en función del intervalo definido.
     */
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

    /**
     * Revisa los partidos de la liga y actualiza su estado si ha llegado la hora de inicio
     * y el partido aún no ha comenzado ni terminado.
     */
private void revisarPartits() {
    LocalDateTime ahoraLocalVariableValue = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    ArrayList<Game> listaPartidosLocalVariableValue = gameEntityManagerServiceFieldReference.getGamesByLeague(leagueReferenceIdentifierFieldReference);

    for (Game partidoLocalVariableValue : listaPartidosLocalVariableValue) {
        LocalDateTime inicioPartidoLocalVariableValue = partidoLocalVariableValue.getData();
        LocalDateTime finPartidoLocalVariableValue = inicioPartidoLocalVariableValue.plusMinutes(configFieldReference.getDurationMatch());

        boolean noHaEmpezadoLocalVariableValue = !partidoLocalVariableValue.isComençat();
        boolean haLlegadoHoraInicioLocalVariableValue = !ahoraLocalVariableValue.isBefore(inicioPartidoLocalVariableValue);
        boolean aunNoHaTerminadoLocalVariableValue = ahoraLocalVariableValue.isBefore(finPartidoLocalVariableValue);

        if (noHaEmpezadoLocalVariableValue && haLlegadoHoraInicioLocalVariableValue && aunNoHaTerminadoLocalVariableValue) {
            int identifierPartidoLocalVariableValue = gameEntityManagerServiceFieldReference.getGameIdByLeague(partidoLocalVariableValue.getNomLocal(), partidoLocalVariableValue.getNomVisitant(), leagueReferenceIdentifierFieldReference);
            gameEntityManagerServiceFieldReference.actualitzaComençat(identifierPartidoLocalVariableValue, true);

            System.out.println("¡Partido iniciado! " + partidoLocalVariableValue.getNomLocal() + " vs " + partidoLocalVariableValue.getNomVisitant());
            List<String[]> liveMatchesLocalVariableValue = gameEntityManagerServiceFieldReference.getLiveGames();
            LiveMatchController liveMatchViewInterfaceLocalVariableValue = new LiveMatchController(partidoLocalVariableValue, configFieldReference, gameEntityManagerServiceFieldReference, identifierPartidoLocalVariableValue, leagueReferenceIdentifierFieldReference);
        }
    }
}

}
