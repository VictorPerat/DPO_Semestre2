package presentation.ControllerViews;

import bussines.objects.Game;
import presentation.AppNavigator;
import presentation.Views.CalendarView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Coordina la pantalla del calendario.
 */
public class CalendarController implements ActionListener {
    private final CalendarView calendarScreenInterfaceFieldReference;
    private final AppNavigator navigatorFieldReference;


    /**
     * Crea una instancia de el calendario.
     *
     * @param calendarScreenInterfaceParameterValue calendario pantalla.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
    public CalendarController(CalendarView calendarScreenInterfaceParameterValue, AppNavigator navigatorParameterValue) {
        this.calendarScreenInterfaceFieldReference = calendarScreenInterfaceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.calendarScreenInterfaceFieldReference.registerController(this);
    }


    /**
     * Carga el calendario.
     *
     * @param teamsParameterValue equipos que usa la operacion.
     * @param gamesParameterValue partidos que usa la operacion.
     */
    public void loadCalendar(ArrayList<String> teamsParameterValue, ArrayList<Game> gamesParameterValue) {
        calendarScreenInterfaceFieldReference.loadCalendar(teamsParameterValue, gamesParameterValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param eventArgumentParameterValue dato de entrada de la operacion.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        if (CalendarView.BACK.equals(eventArgumentParameterValue.getActionCommand())) {
            navigatorFieldReference.runReturnActionOrShow(AppNavigator.ADMIN_MENU);
        } else if (CalendarView.CONFIG.equals(eventArgumentParameterValue.getActionCommand())) {
            showConfigDialog();
        }
    }


    /**
     * Muestra el configuracion dialogo.
     */
    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue =
                Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        presentation.AccountSettingsWidgetService.configureDialogForCurrentSession(
                configDialogLocalVariableValue,
                AppNavigator.CALENDAR
        );
        configDialogLocalVariableValue.setVisible(true);
    }
}


