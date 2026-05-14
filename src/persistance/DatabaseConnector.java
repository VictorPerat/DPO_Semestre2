package persistance;

import bussines.managers.ConfigManager;
import shared.DaoErrorHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Gestiona la conexion de la base de datos.
 */
public class DatabaseConnector {


    private static DatabaseConnector instanceFieldReference = null;


    private Connection connFieldReference;


    private final ConfigManager configManagerServiceFieldReference;


    /**
     * Crea una instancia de los base de datos.
     */
    private DatabaseConnector() {
        this.configManagerServiceFieldReference = new ConfigManager();
    }


    /**
     * Devuelve el instancia.
     *
     * @return el instancia.
     */
    public static synchronized DatabaseConnector getInstance() {


        if (instanceFieldReference == null) {
            instanceFieldReference = new DatabaseConnector();


            instanceFieldReference.connect();
        }

        return instanceFieldReference;
    }


    /**
     * Construye el url.
     *
     * @return resultado de la operacion.
     */
    private String buildUrl() {

        String ipLocalVariableValue = configManagerServiceFieldReference.getDatabaseIP();
        String portLocalVariableValue = configManagerServiceFieldReference.getDatabasePort();
        String databaseLocalVariableValue = configManagerServiceFieldReference.getDatabaseName();

        return "jdbc:mysql://" + ipLocalVariableValue + ":" + portLocalVariableValue + "/" +
                databaseLocalVariableValue +
                "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    }


    /**
     * Gestiona esta operacion.
     */
    private void connect() {
        try {


            if (connFieldReference != null && !connFieldReference.isClosed()) {
                return;
            }

            String userIdentifierLocalVariableValue = configManagerServiceFieldReference.getDatabaseUser();
            String userPasswordLocalVariableValue = configManagerServiceFieldReference.getDatabasePassword();

            connFieldReference = DriverManager.getConnection(
                    buildUrl(),
                    userIdentifierLocalVariableValue,
                    userPasswordLocalVariableValue
            );


        } catch (SQLException eventArgumentExceptionParameter) {
            connFieldReference = null;
            System.err.println("La base de datos no esta conectada. Inicia MySQL en XAMPP antes de abrir la aplicacion.");
        }
    }


    /**
     * Devuelve el conexion.
     *
     * @return el conexion.
     */
    public Connection getConnection() {
        try {

            if (connFieldReference == null || connFieldReference.isClosed()) {
                connect();
            }
        } catch (SQLException eventArgumentExceptionParameter2) {
            DaoErrorHandler.log("DatabaseConnector.getConnection", eventArgumentExceptionParameter2);
        }

        return connFieldReference;
    }


    /**
     * Indica el estado actual.
     *
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public boolean isConnectionAvailable() {
        try {
            return connFieldReference != null && !connFieldReference.isClosed();
        } catch (SQLException eventArgumentExceptionParameter3) {
            return false;
        }
    }


    /**
     * Gestiona esta operacion.
     */
    public void ensureSchema() {

        runSilentDdl("ALTER TABLE games ADD COLUMN winner_name VARCHAR(100) NULL DEFAULT NULL");


        runSilentDdl("ALTER TABLE players DROP INDEX username");
        runSilentDdl("ALTER TABLE players DROP COLUMN username");
    }


    /**
     * Gestiona esta operacion.
     *
     * @param ddlStatementParameterValue dato de entrada de la operacion.
     */
    private void runSilentDdl(String ddlStatementParameterValue) {
        try (Connection ddlConnectionLocalVariableValue = createConnection();
             Statement ddlStatementLocalVariableValue = ddlConnectionLocalVariableValue.createStatement()) {
            ddlStatementLocalVariableValue.executeUpdate(ddlStatementParameterValue);
        } catch (SQLException ignoredExceptionParameterValue) {

        }
    }


    /**
     * Crea el conexion.
     *
     * @return elemento creado por el metodo.
     */
    public Connection createConnection() throws SQLException {
        String userIdentifierLocalVariableValue = configManagerServiceFieldReference.getDatabaseUser();
        String userPasswordLocalVariableValue = configManagerServiceFieldReference.getDatabasePassword();

        return DriverManager.getConnection(
                buildUrl(),
                userIdentifierLocalVariableValue,
                userPasswordLocalVariableValue
        );
    }


}