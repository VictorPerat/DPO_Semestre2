package presentation.ControllerViews;

import presentation.Views.LeagueDetailView;
import presentation.Views.TeamDetailView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Controlador simple para cerrar el detalle de un equipo y volver atras
public class TeamDetailController implements ActionListener {

    // Vista actual con el detalle del equipo
    private final TeamDetailView viewInterfaceFieldReference;

    // Vista anterior a la que se vuelve al cerrar esta pantalla
    private final LeagueDetailView previousViewInterfaceFieldReference;

    // Guarda la vista actual y la pantalla que debe restaurarse despues
    public TeamDetailController(TeamDetailView viewInterfaceParameterValue,
                                LeagueDetailView previousViewInterfaceParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.previousViewInterfaceFieldReference = previousViewInterfaceParameterValue;
    }

    // Atiende el boton de volver de la vista
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();

        switch (commandLocalVariableValue) {
            case TeamDetailView.BACK:
                Window windowLocalVariableValue =
                        SwingUtilities.getWindowAncestor(viewInterfaceFieldReference);

                // Cerramos la ventana del detalle y recuperamos la vista anterior
                if (windowLocalVariableValue != null) {
                    windowLocalVariableValue.dispose();
                }
                this.viewInterfaceFieldReference.dispose();
                previousViewInterfaceFieldReference.setVisible(true);
                break;
        }
    }
}
