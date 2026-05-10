package presentation.Views;

import bussines.objects.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Representa la vista del equipo detalle.
 */
public class TeamDetailView extends JPanel {
    /**
     * Constante para el vuelta.
     */
    public static final String BACK = "BACK";
    /**
     * Constante para el configuracion.
     */
    public static final String CONFIG = "CONFIG";

    private JLabel titleLabelFieldReference;
    private JTable playersTableFieldReference;
    private DefaultTableModel tableModelFieldReference;
    private JButton backButtonFieldReference;
    private JButton configButtonFieldReference;


    /**
     * Crea una instancia de el equipo detalle.
     */
    public TeamDetailView() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        JPanel headerPanelLocalVariableValue = new JPanel(new BorderLayout());
        headerPanelLocalVariableValue.setBackground(new Color(22, 49, 72));
        headerPanelLocalVariableValue.setPreferredSize(new Dimension(800, 55));
        backButtonFieldReference = Rounded.HeaderButtonHelper.createBackButton(null);
        configButtonFieldReference = Rounded.HeaderButtonHelper.createConfigButton(null);
        titleLabelFieldReference = new JLabel("TEAM DETAILS", SwingConstants.CENTER);
        titleLabelFieldReference.setForeground(Color.WHITE);
        titleLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanelLocalVariableValue.add(backButtonFieldReference, BorderLayout.WEST);
        headerPanelLocalVariableValue.add(titleLabelFieldReference, BorderLayout.CENTER);
        headerPanelLocalVariableValue.add(configButtonFieldReference, BorderLayout.EAST);

        String[] columnsLocalVariableValue = {"Name", "Email", "DNI", "Number", "Phone"};
        tableModelFieldReference = new DefaultTableModel(columnsLocalVariableValue, 0) {

            @Override public boolean isCellEditable(int rowParameterValue, int columnParameterValue) { return false; }
        };
        playersTableFieldReference = new JTable(tableModelFieldReference);
        playersTableFieldReference.setRowHeight(28);

        add(headerPanelLocalVariableValue, BorderLayout.NORTH);
        add(new JScrollPane(playersTableFieldReference), BorderLayout.CENTER);
    }


    /**
     * Carga el equipo.
     *
     * @param teamReferenceDisplayNameParameterValue nombre del equipo.
     * @param playersParameterValue jugadores que usa la operacion.
     */
    public void loadTeam(String teamReferenceDisplayNameParameterValue, ArrayList<Player> playersParameterValue) {
        titleLabelFieldReference.setText(teamReferenceDisplayNameParameterValue == null ? "TEAM DETAILS" : teamReferenceDisplayNameParameterValue.toUpperCase());
        tableModelFieldReference.setRowCount(0);
        List<Player> playersLocalVariableValue = playersParameterValue == null ? new ArrayList<>() : playersParameterValue;
        for (Player playerProfileLocalVariableValue : playersLocalVariableValue) {
            tableModelFieldReference.addRow(new Object[]{
                    playerProfileLocalVariableValue.getName(),
                    playerProfileLocalVariableValue.getEmail(),
                    playerProfileLocalVariableValue.getDni(),
                    playerProfileLocalVariableValue.getNumber(),
                    playerProfileLocalVariableValue.getPhoneNumber()
            });
        }
    }


    /**
     * Registra la accion.
     *
     * @param controllerHandlerParameterValue dato de entrada de la operacion.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.setActionCommand(BACK);
        configButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        configButtonFieldReference.setActionCommand(CONFIG);
    }
}


