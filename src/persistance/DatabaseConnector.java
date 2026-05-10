package persistance;

import bussines.managers.ConfigManager;
import shared.DaoErrorHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Gestiona la conexion de la base de datos.
 */
public class DatabaseConnector {


    private static DatabaseConnector instanceFieldReference = null;


    private Connection connFieldReference;


    private static boolean firstConnectionLoggedFieldReference = false;


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


            if (!firstConnectionLoggedFieldReference) {
                System.out.println("Conexión exitosa a la base de datos.");
                firstConnectionLoggedFieldReference = true;
            }

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


    /**
     * Gestiona esta operacion.
     *
     * @param queryParameterValue consulta que se ejecuta.
     */
    public void insertQuery(String queryParameterValue) {
        try (Statement statementLocalVariableValue = getConnection().createStatement()) {
            statementLocalVariableValue.executeUpdate(queryParameterValue);
        } catch (SQLException eventArgumentExceptionParameter4) {
            System.err.println("Error al ejecutar la consulta: " + queryParameterValue);
            System.err.println(eventArgumentExceptionParameter4.getMessage());
        }
    }


    /**
     * Elimina el consulta.
     *
     * @param queryParameterValue2 consulta que usa la operacion.
     */
    public void deleteQuery(String queryParameterValue2) {
        try (Statement statementLocalVariableValue = getConnection().createStatement()) {
            statementLocalVariableValue.executeUpdate(queryParameterValue2);
        } catch (SQLException eventArgumentExceptionParameter5) {
            System.err.println("Error al ejecutar la consulta: " + queryParameterValue2);
            System.err.println(eventArgumentExceptionParameter5.getMessage());
        }
    }


    /**
     * Actualiza el consulta.
     *
     * @param queryParameterValue3 consulta que usa la operacion.
     */
    public void updateQuery(String queryParameterValue3) {
        try (Statement statementLocalVariableValue = getConnection().createStatement()) {
            statementLocalVariableValue.executeUpdate(queryParameterValue3);
        } catch (SQLException eventArgumentExceptionParameter6) {
            System.err.println(queryParameterValue3);
            System.err.println("Problema when updating --> " +
                    eventArgumentExceptionParameter6.getSQLState() +
                    " (" + eventArgumentExceptionParameter6.getMessage() + ")");
        }
    }


    /**
     * Gestiona esta operacion.
     *
     * @param queryParameterValue4 consulta que usa la operacion.
     * @return resultado de la operacion.
     */
    public ResultSet selectQuery(String queryParameterValue4) {
        try {
            Statement statementLocalVariableValue = getConnection().createStatement();
            return statementLocalVariableValue.executeQuery(queryParameterValue4);
        } catch (SQLException eventArgumentExceptionParameter7) {
            System.err.println("Error al ejecutar la consulta: " + queryParameterValue4);
            System.err.println(eventArgumentExceptionParameter7.getMessage());
            return null;
        }
    }


    /**
     * Gestiona esta operacion.
     */
    public void disconnect() {
        try {
            if (connFieldReference != null && !connFieldReference.isClosed()) {
                connFieldReference.close();
                System.out.println("Conexion cerrada.");
            }
        } catch (SQLException eventArgumentExceptionParameter8) {
            System.out.println("Problem disconnecting: " + eventArgumentExceptionParameter8.getMessage());
        }
    }
}