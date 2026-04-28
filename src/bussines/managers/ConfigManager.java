package bussines.managers;

import persistance.ConfigJsonDao;

/**
 * Esta clase se encarga de leer y dar acceso a la configuración de la aplicación.
 */
public class ConfigManager {

    // Referencia al DAO que lee el archivo config.Json
    private static ConfigJsonDao configJsonDataAccessObjectFieldReference;

    // Constructor que inicializa el lector de configuración
    public ConfigManager() {
        ensureInitialized();
    }

    /**
     * Inicializa el DAO de configuración la primera vez que se necesita.
     * Garantiza que cualquier llamada a los getters estáticos devuelva
     * datos válidos aunque nadie haya creado un {@link ConfigManager}
     * todavía.
     */
    private static synchronized void ensureInitialized() {
        if (configJsonDataAccessObjectFieldReference == null) {
            configJsonDataAccessObjectFieldReference = new ConfigJsonDao();
        }
    }

    // Devuelve el puerto de la base de datos
    public static String getDatabasePort() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getDatabasePort();
    }

    // Devuelve la IP de la base de datos
    public static String getDatabaseIP() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getDatabaseIP();
    }

    // Devuelve el nombre de la base de datos
    public static String getDatabaseName() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getDatabaseName();
    }

    // Devuelve el usuario de acceso a la base de datos
    public static String getDatabaseUser() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getDatabaseUser();
    }

    // Devuelve la contraseña de la base de datos
    public static String getDatabasePassword() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getDatabasePassword();
    }

    // Devuelve la contraseña del administrador
    public static String getAdminPassword() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getAdminPassword();
    }

    // Devuelve la duración configurada de los partidos
    public static int getDurationMatch() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getDurationMatch();
    }
}