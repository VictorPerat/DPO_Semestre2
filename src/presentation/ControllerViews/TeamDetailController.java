package presentation.ControllerViews;

import presentation.Views.LeagueDetailView;
import presentation.Views.TeamDetailView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador para la vista de detalles de un equipo.
 * Gestiona la interacción de usuario, especialmente el botón de volver atrás.
 */
public class TeamDetailController implements ActionListener {
    private final TeamDetailView viewInterfaceFieldReference;
    private final LeagueDetailView previousViewInterfaceFieldReference;

    /**
     * Constructor que recibe la vista actual y la vista previa a la que se debe volver.
     *
     * @param view Vista de detalle del equipo.
     * @param previousView Vista anterior (detalle de liga) para mostrar al cerrar esta vista.
     */
    public TeamDetailController(TeamDetailView viewInterfaceParameterValue, LeagueDetailView previousViewInterfaceParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.previousViewInterfaceFieldReference = previousViewInterfaceParameterValue;
    }

    /**
     * Maneja eventos de acción, como el clic en el botón de volver.
     *
     * @param e Evento de acción generado.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();

        switch (commandLocalVariableValue) {
            case TeamDetailView.BACK:
                Window windowLocalVariableValue = SwingUtilities.getWindowAncestor(viewInterfaceFieldReference);
                if (windowLocalVariableValue != null) windowLocalVariableValue.dispose();
                this.viewInterfaceFieldReference.dispose();
                previousViewInterfaceFieldReference.setVisible(true);
                break;

        }
    }
}

