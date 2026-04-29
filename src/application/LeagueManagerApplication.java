package application;

import bussines.MatchSimulationScheduler;
import bussines.managers.ConfigManager;
import bussines.managers.GameManager;
import bussines.managers.LeagueManager;
import bussines.managers.PlayerManager;
import persistance.DatabaseConnector;
import presentation.AppNavigator;
import presentation.LiveMatchesWidgetService;
import presentation.ControllerViews.*;
import presentation.Views.*;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class LeagueManagerApplication {

    private LeagueManagerApplication() { }

    public static void launch() {
        ConfigManager configManagerServiceLocalVariableValue = new ConfigManager();

        if (!isDatabaseAvailable()) {
            configureLookAndFeel();
            SwingUtilities.invokeLater(LeagueManagerApplication::showDatabaseErrorOnly);
            return;
        }

        DatabaseConnector.getInstance().ensureSchema();
        configureLookAndFeel();
        startMatchSimulation(configManagerServiceLocalVariableValue);
        SwingUtilities.invokeLater(LeagueManagerApplication::initializeApplication);
    }

    private static void startMatchSimulation(ConfigManager configParameterValue) {
        GameManager gameEntityManagerServiceLocalVariableValue = new GameManager();
        LeagueManager leagueReferenceManagerServiceLocalVariableValue = new LeagueManager();
        MatchSimulationScheduler.start(gameEntityManagerServiceLocalVariableValue, leagueReferenceManagerServiceLocalVariableValue, configParameterValue);
    }

    private static boolean isDatabaseAvailable() {
        DatabaseConnector sqlConnectorLocalVariableValue = DatabaseConnector.getInstance();
        return sqlConnectorLocalVariableValue.isConnectionAvailable();
    }

    private static void configureLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception eventArgumentExceptionParameterValue) {
            eventArgumentExceptionParameterValue.printStackTrace();
        }
    }

    private static void showDatabaseErrorOnly() {
        MainView mainViewInterfaceLocalVariableValue = new MainView();
        AppNavigator navigatorLocalVariableValue = new AppNavigator(mainViewInterfaceLocalVariableValue);
        DatabaseErrorView databaseErrorViewInterfaceLocalVariableValue = new DatabaseErrorView();
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.DB_ERROR, databaseErrorViewInterfaceLocalVariableValue);
        navigatorLocalVariableValue.show(AppNavigator.DB_ERROR);
        mainViewInterfaceLocalVariableValue.setVisible(true);
    }

    private static void initializeApplication() {
        PlayerManager playerProfileManagerServiceLocalVariableValue = new PlayerManager();
        MainView mainViewInterfaceLocalVariableValue = new MainView();
        AppNavigator navigatorLocalVariableValue = new AppNavigator(mainViewInterfaceLocalVariableValue);

        LoginView loginViewInterfaceLocalVariableValue = new LoginView();
        SignUpView signUpViewInterfaceLocalVariableValue = new SignUpView();
        UserProfileView userProfileViewInterfaceLocalVariableValue = new UserProfileView();
        ChangePasswordView changePasswordViewInterfaceLocalVariableValue = new ChangePasswordView();
        AdminMenuView adminMenuViewInterfaceLocalVariableValue = new AdminMenuView();
        PlayerMenuView playerMenuViewInterfaceLocalVariableValue = new PlayerMenuView();
        AvailableLeaguesView availableLeaguesViewInterfaceLocalVariableValue = new AvailableLeaguesView();
        LeagueDetailView leagueDetailViewInterfaceLocalVariableValue = new LeagueDetailView();
        TeamDetailView teamDetailViewInterfaceLocalVariableValue = new TeamDetailView();
        CreateLeagueView createLeagueViewInterfaceLocalVariableValue = new CreateLeagueView();
        TeamSelectionView teamSelectionViewInterfaceLocalVariableValue = new TeamSelectionView();
        CalendarView calendarViewInterfaceLocalVariableValue = new CalendarView();
        CreateTeamView createTeamViewInterfaceLocalVariableValue = new CreateTeamView();
        DeleteLeagueView deleteLeagueViewInterfaceLocalVariableValue = new DeleteLeagueView();
        DeleteTeamView deleteTeamViewInterfaceLocalVariableValue = new DeleteTeamView();
        DeletePlayerView deletePlayerViewInterfaceLocalVariableValue = new DeletePlayerView();
        StatisticsGraphView statisticsGraphViewInterfaceLocalVariableValue = new StatisticsGraphView();
        LiveMatchesView liveMatchesViewInterfaceLocalVariableValue = new LiveMatchesView();
        DatabaseErrorView databaseErrorViewInterfaceLocalVariableValue = new DatabaseErrorView();

        new LoginController(loginViewInterfaceLocalVariableValue, playerProfileManagerServiceLocalVariableValue, navigatorLocalVariableValue);
        new SignUpController(signUpViewInterfaceLocalVariableValue, playerProfileManagerServiceLocalVariableValue, navigatorLocalVariableValue);
        UserProfileController userProfileControllerHandlerLocalVariableValue = new UserProfileController(userProfileViewInterfaceLocalVariableValue, playerProfileManagerServiceLocalVariableValue, navigatorLocalVariableValue);
        new ChangePasswordController(changePasswordViewInterfaceLocalVariableValue, playerProfileManagerServiceLocalVariableValue, navigatorLocalVariableValue);

        AdminMenuController adminMenuControllerHandlerLocalVariableValue = new AdminMenuController(adminMenuViewInterfaceLocalVariableValue, playerProfileManagerServiceLocalVariableValue, navigatorLocalVariableValue);
        PlayerMenuController playerMenuControllerHandlerLocalVariableValue = new PlayerMenuController(playerMenuViewInterfaceLocalVariableValue, playerProfileManagerServiceLocalVariableValue, navigatorLocalVariableValue);

        CalendarController calendarControllerHandlerLocalVariableValue = new CalendarController(calendarViewInterfaceLocalVariableValue, navigatorLocalVariableValue);
        TeamDetailController teamDetailControllerHandlerLocalVariableValue = new TeamDetailController(teamDetailViewInterfaceLocalVariableValue, navigatorLocalVariableValue);
        StatisticsGraphController statisticsGraphControllerHandlerLocalVariableValue = new StatisticsGraphController(statisticsGraphViewInterfaceLocalVariableValue, playerProfileManagerServiceLocalVariableValue, navigatorLocalVariableValue);
        LeagueDetailController leagueDetailControllerHandlerLocalVariableValue = new LeagueDetailController(leagueDetailViewInterfaceLocalVariableValue, playerProfileManagerServiceLocalVariableValue, navigatorLocalVariableValue);
        leagueDetailControllerHandlerLocalVariableValue.setTeamDetailController(teamDetailControllerHandlerLocalVariableValue);
        leagueDetailControllerHandlerLocalVariableValue.setStatisticsGraphController(statisticsGraphControllerHandlerLocalVariableValue);
        leagueDetailControllerHandlerLocalVariableValue.setCalendarViewLoader(calendarControllerHandlerLocalVariableValue::loadCalendar);

        AvailableLeaguesController availableLeaguesControllerHandlerLocalVariableValue = new AvailableLeaguesController(
                availableLeaguesViewInterfaceLocalVariableValue,
                playerProfileManagerServiceLocalVariableValue,
                navigatorLocalVariableValue,
                leagueDetailControllerHandlerLocalVariableValue
        );

        TeamSelectionController teamSelectionControllerHandlerLocalVariableValue = new TeamSelectionController(
                teamSelectionViewInterfaceLocalVariableValue,
                navigatorLocalVariableValue,
                calendarControllerHandlerLocalVariableValue::loadCalendar
        );
        new CreateLeagueController(createLeagueViewInterfaceLocalVariableValue, navigatorLocalVariableValue, teamSelectionControllerHandlerLocalVariableValue);
        new CreateTeamController(createTeamViewInterfaceLocalVariableValue, navigatorLocalVariableValue);
        DeleteLeagueController deleteLeagueControllerHandlerLocalVariableValue = new DeleteLeagueController(deleteLeagueViewInterfaceLocalVariableValue, navigatorLocalVariableValue);
        DeleteTeamController deleteTeamControllerHandlerLocalVariableValue = new DeleteTeamController(deleteTeamViewInterfaceLocalVariableValue, navigatorLocalVariableValue);
        DeletePlayerController deletePlayerControllerHandlerLocalVariableValue = new DeletePlayerController(deletePlayerViewInterfaceLocalVariableValue, adminMenuControllerHandlerLocalVariableValue, playerProfileManagerServiceLocalVariableValue, navigatorLocalVariableValue);
        LiveMatchesController liveMatchesControllerHandlerLocalVariableValue = new LiveMatchesController(liveMatchesViewInterfaceLocalVariableValue, playerProfileManagerServiceLocalVariableValue, navigatorLocalVariableValue);

        navigatorLocalVariableValue.registerOnShowHook(AppNavigator.ADMIN_MENU, () -> {
            adminMenuControllerHandlerLocalVariableValue.startLiveMatchesPreviewAutoRefresh();
        });

        navigatorLocalVariableValue.registerOnHideHook(
                AppNavigator.ADMIN_MENU,
                adminMenuControllerHandlerLocalVariableValue::stopLiveMatchesPreviewAutoRefresh
        );

        navigatorLocalVariableValue.registerOnShowHook(AppNavigator.PLAYER_MENU, () -> {
            playerMenuControllerHandlerLocalVariableValue.startLiveMatchesPreviewAutoRefresh();
        });

        navigatorLocalVariableValue.registerOnHideHook(
                AppNavigator.PLAYER_MENU,
                playerMenuControllerHandlerLocalVariableValue::stopLiveMatchesPreviewAutoRefresh
        );

        navigatorLocalVariableValue.registerOnShowHook(AppNavigator.PROFILE, userProfileControllerHandlerLocalVariableValue::refreshCurrentPlayer);
        navigatorLocalVariableValue.registerOnShowHook(AppNavigator.LOGIN, LiveMatchesWidgetService::hide);
        navigatorLocalVariableValue.registerOnShowHook(AppNavigator.CHANGE_PASSWORD, changePasswordViewInterfaceLocalVariableValue::clearForm);
        navigatorLocalVariableValue.registerOnShowHook(AppNavigator.AVAILABLE_LEAGUES, availableLeaguesControllerHandlerLocalVariableValue::startAutoRefresh);
        navigatorLocalVariableValue.registerOnHideHook(AppNavigator.AVAILABLE_LEAGUES, availableLeaguesControllerHandlerLocalVariableValue::stopAutoRefresh);
        navigatorLocalVariableValue.registerOnShowHook(AppNavigator.DELETE_LEAGUE, deleteLeagueControllerHandlerLocalVariableValue::refreshLeagues);
        navigatorLocalVariableValue.registerOnShowHook(AppNavigator.DELETE_TEAM, deleteTeamControllerHandlerLocalVariableValue::refreshTeams);
        navigatorLocalVariableValue.registerOnShowHook(AppNavigator.DELETE_PLAYER, deletePlayerControllerHandlerLocalVariableValue::refreshPlayers);
        navigatorLocalVariableValue.registerOnShowHook(AppNavigator.STATISTICS, statisticsGraphControllerHandlerLocalVariableValue::startChartAutoRefresh);
        navigatorLocalVariableValue.registerOnHideHook(AppNavigator.STATISTICS, statisticsGraphControllerHandlerLocalVariableValue::stopChartAutoRefresh);
        navigatorLocalVariableValue.registerOnShowHook(AppNavigator.LIVE_MATCHES, () -> {
            liveMatchesControllerHandlerLocalVariableValue.refreshLiveGames();
            liveMatchesControllerHandlerLocalVariableValue.startAutoRefresh();
        });
        navigatorLocalVariableValue.registerOnHideHook(AppNavigator.LIVE_MATCHES, liveMatchesControllerHandlerLocalVariableValue::stopAutoRefresh);

        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.LOGIN, loginViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.SIGNUP, signUpViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.PROFILE, userProfileViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.CHANGE_PASSWORD, changePasswordViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.ADMIN_MENU, adminMenuViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.PLAYER_MENU, playerMenuViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.AVAILABLE_LEAGUES, availableLeaguesViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.LEAGUE_DETAIL, leagueDetailViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.TEAM_DETAIL, teamDetailViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.CREATE_LEAGUE, createLeagueViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.TEAM_SELECTION, teamSelectionViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.CALENDAR, calendarViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.CREATE_TEAM, createTeamViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.DELETE_LEAGUE, deleteLeagueViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.DELETE_TEAM, deleteTeamViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.DELETE_PLAYER, deletePlayerViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.STATISTICS, statisticsGraphViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.LIVE_MATCHES, liveMatchesViewInterfaceLocalVariableValue);
        mainViewInterfaceLocalVariableValue.addScreen(AppNavigator.DB_ERROR, databaseErrorViewInterfaceLocalVariableValue);

        navigatorLocalVariableValue.show(AppNavigator.LOGIN);
        mainViewInterfaceLocalVariableValue.setVisible(true);
    }
}
