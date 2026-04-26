package persistance;

import bussines.managers.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestiona la base de datos del proyecto
 */

public class DatabaseConnector {
    private static DatabaseConnector instanceFieldReference = null;
    private Connection connFieldReference;
    private final ConfigManager configManagerServiceFieldReference;

    // Constructor
    private DatabaseConnector() {
        this.configManagerServiceFieldReference = new ConfigManager();
    }

    public static synchronized DatabaseConnector getInstance() {
        // Si no existe la creamos
        if (instanceFieldReference == null) {

            // Creamos la instancia
            instanceFieldReference = new DatabaseConnector();

            // Nos intentamos conectar
            instanceFieldReference.connect();
        }

        // Devolvemos la referencia de la instancia
        return instanceFieldReference;
    }

    // Función para construir la URL de conexión a la base de datos
    private String buildUrl() {

        // Obtenemos la IP
        String ipLocalVariableValue = configManagerServiceFieldReference.getDatabaseIP();

        // Obtenemos el puerto
        String portLocalVariableValue = configManagerServiceFieldReference.getDatabasePort();

        // Obtenemos el nombre de la base de datos
        String databaseLocalVariableValue = configManagerServiceFieldReference.getDatabaseName();

        // Aquí devolvemos l'URL final
        return "jdbc:mysql://" + ipLocalVariableValue + ":" + portLocalVariableValue + "/" + databaseLocalVariableValue + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    }

    // Función para intentar la conexión a la base de datos
    private void connect() {

        // ¿Tengo una conexión guardada, está abierta?
        try {

            // Si es asi salimos, ya que se ha conectado exitosamente
            if (connFieldReference != null && !connFieldReference.isClosed()) {
                return;
            }

            // Si no es asi nos intentamos conectar

            // Obtiene el usuario de la base de datos
            String userIdentifierLocalVariableValue = configManagerServiceFieldReference.getDatabaseUser();

            // Obtiene la contraseña de la base de datos
            String userPasswordLocalVariableValue = configManagerServiceFieldReference.getDatabasePassword();

            // Utilizamos getConnection para abrir la conexión real
            connFieldReference = DriverManager.getConnection(buildUrl(), userIdentifierLocalVariableValue, userPasswordLocalVariableValue);

            // Y si va bien imprimimos el mensaje de conexión exitosa.
            System.out.println("Conexión exitosa a la base de datos.");

            // Si falla
        } catch (SQLException eventArgumentExceptionParameter) {

            // Ponemos la conexión a NULL ya que no tenemos nada conectado
            connFieldReference = null;

            // Imprimimos el mensaje de error (Lo he hecho de esta forma para evitar abrir ninguna view, con
            // un mensaje de error por consola ya te acuerda que tienes que encender la base de datos y ya
            // una vez tengas la base de datos ya podemos seguir con el programa, también porque lo dice
            // el enunciado de la práctica).
            System.err.println("La base de datos no esta conectada. Inicia MySQL en XAMPP antes de abrir la aplicacion.");
        }
    }

    /**
     * Devuelve la conexión de la base de datos
     */
    public Connection getConnection() {
        try {
            // Si no hay ninguna conexión creada o ha habido una conexión y se ha cerrado
            if (connFieldReference == null || connFieldReference.isClosed()) {

                // Entonces reconectamos
                connect();
            }

            // Si al probar isClosed se produce una excepción, entonces la imprimimos por consola
        } catch (SQLException eventArgumentExceptionParameter2) {
            eventArgumentExceptionParameter2.printStackTrace();
        }

        // Al final devuelve la conexión compartida que guarda la clase
        return connFieldReference;
    }

    /**
     * Comprobamos si la conexión a la base de datos está conectada
     */
    public boolean isConnectionAvailable() {
        try {
            //True si la conexión sigue y esta abierta
            return connFieldReference != null && !connFieldReference.isClosed();

            // False si no esta  abierta, la conexión esta cerrada o habido algun error al comprovarlo.
        } catch (SQLException eventArgumentExceptionParameter3) {
            return false;
        }
    }

    /**
     * Crea una conexion nueva pensada para usar con try-with-resources.
     */
    public Connection createConnection() throws SQLException {
        String userIdentifierLocalVariableValue = configManagerServiceFieldReference.getDatabaseUser();
        String userPasswordLocalVariableValue = configManagerServiceFieldReference.getDatabasePassword();
        return DriverManager.getConnection(buildUrl(), userIdentifierLocalVariableValue, userPasswordLocalVariableValue);
    }

    public void insertQuery(String queryParameterValue) {
        try (Statement statementLocalVariableValue = getConnection().createStatement()) {
            statementLocalVariableValue.executeUpdate(queryParameterValue);
        } catch (SQLException eventArgumentExceptionParameter4) {
            System.err.println("Error al ejecutar la consulta: " + queryParameterValue);
            System.err.println(eventArgumentExceptionParameter4.getMessage());
        }
    }

    public void deleteQuery(String queryParameterValue2) {
        try (Statement statementLocalVariableValue = getConnection().createStatement()) {
            statementLocalVariableValue.executeUpdate(queryParameterValue2);
        } catch (SQLException eventArgumentExceptionParameter5) {
            System.err.println("Error al ejecutar la consulta: " + queryParameterValue2);
            System.err.println(eventArgumentExceptionParameter5.getMessage());
        }
    }

    public void updateQuery(String queryParameterValue3) {
        try (Statement statementLocalVariableValue = getConnection().createStatement()) {
            statementLocalVariableValue.executeUpdate(queryParameterValue3);
        } catch (SQLException eventArgumentExceptionParameter6) {
            System.err.println(queryParameterValue3);
            System.err.println("Problema when updating --> " + eventArgumentExceptionParameter6.getSQLState() + " (" + eventArgumentExceptionParameter6.getMessage() + ")");
        }
    }

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
