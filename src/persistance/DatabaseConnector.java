package persistance;

import bussines.managers.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Esta clase se encarga de gestionar la conexión con la base de datos.
 */
public class DatabaseConnector {

    // Instancia única de la clase
    private static DatabaseConnector instanceFieldReference = null;

    // Conexión compartida con la base de datos
    private Connection connFieldReference;

    // Bandera para imprimir el mensaje de éxito solo una vez
    private static boolean firstConnectionLoggedFieldReference = false;

    // Manager que lee la configuración del proyecto
    private final ConfigManager configManagerServiceFieldReference;

    // Constructor privado para aplicar el patrón singleton
    private DatabaseConnector() {
        this.configManagerServiceFieldReference = new ConfigManager();
    }

    // Devuelve la única instancia de la clase
    public static synchronized DatabaseConnector getInstance() {

        // Si no existe, se crea
        if (instanceFieldReference == null) {
            instanceFieldReference = new DatabaseConnector();

            // Después intenta conectarse a la base de datos
            instanceFieldReference.connect();
        }

        return instanceFieldReference;
    }

    // Construye la URL de conexión con los datos del config
    private String buildUrl() {

        String ipLocalVariableValue = configManagerServiceFieldReference.getDatabaseIP();
        String portLocalVariableValue = configManagerServiceFieldReference.getDatabasePort();
        String databaseLocalVariableValue = configManagerServiceFieldReference.getDatabaseName();

        return "jdbc:mysql://" + ipLocalVariableValue + ":" + portLocalVariableValue + "/" +
                databaseLocalVariableValue +
                "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    }

    // Intenta abrir la conexión con la base de datos
    private void connect() {
        try {

            // Si ya hay una conexión abierta, no hace nada
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

            // Solo se notifica la primera vez que se establece conexión
            // para no inundar la consola con cada reconexión.
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
     * Devuelve la conexión actual.
     */
    public Connection getConnection() {
        try {
            // Si no hay conexión o se cerró, vuelve a conectar
            if (connFieldReference == null || connFieldReference.isClosed()) {
                connect();
            }
        } catch (SQLException eventArgumentExceptionParameter2) {
            eventArgumentExceptionParameter2.printStackTrace();
        }

        return connFieldReference;
    }

    /**
     * Comprueba si la conexión está disponible.
     */
    public boolean isConnectionAvailable() {
        try {
            return connFieldReference != null && !connFieldReference.isClosed();
        } catch (SQLException eventArgumentExceptionParameter3) {
            return false;
        }
    }

    /**
     * Aplica migraciones de esquema mínimas si todavía no se han
     * aplicado. Se llama una vez al arrancar la aplicación. Si la
     * columna o tabla ya existe el ALTER falla silenciosamente.
     */
    public void ensureSchema() {
        runSilentDdl("ALTER TABLE games ADD COLUMN winner_name VARCHAR(100) NULL DEFAULT NULL");
    }

    /**
     * Ejecuta una sentencia DDL (ALTER, CREATE…) ignorando errores.
     * Útil para migraciones idempotentes que pueden fallar si ya están
     * aplicadas.
     */
    private void runSilentDdl(String ddlStatementParameterValue) {
        try (Connection ddlConnectionLocalVariableValue = createConnection();
             Statement ddlStatementLocalVariableValue = ddlConnectionLocalVariableValue.createStatement()) {
            ddlStatementLocalVariableValue.executeUpdate(ddlStatementParameterValue);
        } catch (SQLException ignoredExceptionParameterValue) {
            // Si la columna/tabla ya existe, ignoramos el error.
        }
    }

    /**
     * Crea una conexión nueva pensada para usar con try-with-resources.
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

    // Ejecuta una consulta de inserción
    public void insertQuery(String queryParameterValue) {
        try (Statement statementLocalVariableValue = getConnection().createStatement()) {
            statementLocalVariableValue.executeUpdate(queryParameterValue);
        } catch (SQLException eventArgumentExceptionParameter4) {
            System.err.println("Error al ejecutar la consulta: " + queryParameterValue);
            System.err.println(eventArgumentExceptionParameter4.getMessage());
        }
    }

    // Ejecuta una consulta de borrado
    public void deleteQuery(String queryParameterValue2) {
        try (Statement statementLocalVariableValue = getConnection().createStatement()) {
            statementLocalVariableValue.executeUpdate(queryParameterValue2);
        } catch (SQLException eventArgumentExceptionParameter5) {
            System.err.println("Error al ejecutar la consulta: " + queryParameterValue2);
            System.err.println(eventArgumentExceptionParameter5.getMessage());
        }
    }

    // Ejecuta una consulta de actualización
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

    // Ejecuta una consulta select y devuelve el resultado
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

    // Cierra la conexión con la base de datos
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