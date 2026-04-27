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
        this.configJsonDataAccessObjectFieldReference = new ConfigJsonDao();
    }

    // Devuelve el puerto de la base de datos
    public static String getDatabasePort() {
        return configJsonDataAccessObjectFieldReference.getDatabasePort();
    }

    // Devuelve la IP de la base de datos
    public static String getDatabaseIP() {
        return configJsonDataAccessObjectFieldReference.getDatabaseIP();
    }

    // Devuelve el nombre de la base de datos
    public static String getDatabaseName() {
        return configJsonDataAccessObjectFieldReference.getDatabaseName();
    }

    // Devuelve el usuario de acceso a la base de datos
    public static String getDatabaseUser() {
        return configJsonDataAccessObjectFieldReference.getDatabaseUser();
    }

    // Devuelve la contraseña de la base de datos
    public static String getDatabasePassword() {
        return configJsonDataAccessObjectFieldReference.getDatabasePassword();
    }

    // Devuelve la contraseña del administrador
    public static String getAdminPassword() {
        return configJsonDataAccessObjectFieldReference.getAdminPassword();
    }

    // Devuelve la duración configurada de los partidos
    public static int getDurationMatch() {
        return configJsonDataAccessObjectFieldReference.getDurationMatch();
    }
}