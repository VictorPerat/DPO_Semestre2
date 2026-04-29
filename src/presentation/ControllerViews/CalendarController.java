package presentation.ControllerViews;

import bussines.objects.Game;
import presentation.AppNavigator;
import presentation.Views.CalendarView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** Controlador del calendario de partidos. */
public class CalendarController implements ActionListener {
    private final CalendarView calendarScreenInterfaceFieldReference;
    private final AppNavigator navigatorFieldReference;

    public CalendarController(CalendarView calendarScreenInterfaceParameterValue, AppNavigator navigatorParameterValue) {
        this.calendarScreenInterfaceFieldReference = calendarScreenInterfaceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.calendarScreenInterfaceFieldReference.registerController(this);
    }

    public CalendarController(CalendarView calendarScreenInterfaceParameterValue,
                              MenuController menuControllerHandlerParameterValue) {
        this(calendarScreenInterfaceParameterValue, AppNavigator.getInstance());
    }

    public void loadCalendar(ArrayList<String> teamsParameterValue, ArrayList<Game> gamesParameterValue) {
        calendarScreenInterfaceFieldReference.loadCalendar(teamsParameterValue, gamesParameterValue);
    }

    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        if (CalendarView.BACK.equals(eventArgumentParameterValue.getActionCommand())) {
            navigatorFieldReference.runReturnActionOrShow(AppNavigator.ADMIN_MENU);
        } else if (CalendarView.CONFIG.equals(eventArgumentParameterValue.getActionCommand())) {
            showConfigDialog();
        }
    }

    private void showConfigDialog() {
        Rounded.ConfigDialog configDialogLocalVariableValue = Rounded.ConfigDialog.getInstance(navigatorFieldReference.getMainView());
        configDialogLocalVariableValue.registerController(eventArgumentParameterValue -> {
            configDialogLocalVariableValue.dispose();
            if (Rounded.ConfigDialog.LOGOUT.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.show(AppNavigator.LOGIN);
            } else if (Rounded.ConfigDialog.CHANGE_PASSWORD.equals(eventArgumentParameterValue.getActionCommand())) {
                navigatorFieldReference.setChangePasswordReturnAction(() -> navigatorFieldReference.show(AppNavigator.CALENDAR));
                navigatorFieldReference.show(AppNavigator.CHANGE_PASSWORD);
            }
        });
        configDialogLocalVariableValue.setBackButtonListener(eventArgumentParameterValue -> configDialogLocalVariableValue.dispose());
        configDialogLocalVariableValue.setVisible(true);
    }
}
