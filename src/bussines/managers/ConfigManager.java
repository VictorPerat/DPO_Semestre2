package bussines.managers;

import persistance.ConfigJsonDao;


/**
 * Gestiona las operaciones de la configuracion.
 */
public class ConfigManager {


    private static ConfigJsonDao configJsonDataAccessObjectFieldReference;


    /**
     * Crea una instancia de el configuracion.
     */
    public ConfigManager() {
        ensureInitialized();
    }


    /**
     * Gestiona esta operacion.
     */
    private static synchronized void ensureInitialized() {
        if (configJsonDataAccessObjectFieldReference == null) {
            configJsonDataAccessObjectFieldReference = new ConfigJsonDao();
        }
    }


    /**
     * Devuelve los base de datos.
     *
     * @return los base de datos.
     */
    public static String getDatabasePort() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getDatabasePort();
    }


    /**
     * Devuelve los base de datos.
     *
     * @return los base de datos.
     */
    public static String getDatabaseIP() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getDatabaseIP();
    }


    /**
     * Devuelve el base de datos nombre.
     *
     * @return el base de datos nombre.
     */
    public static String getDatabaseName() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getDatabaseName();
    }


    /**
     * Devuelve el base de datos usuario.
     *
     * @return el base de datos usuario.
     */
    public static String getDatabaseUser() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getDatabaseUser();
    }


    /**
     * Devuelve el base de datos contrasena.
     *
     * @return el base de datos contrasena.
     */
    public static String getDatabasePassword() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getDatabasePassword();
    }


    /**
     * Devuelve el administrador contrasena.
     *
     * @return el administrador contrasena.
     */
    public static String getAdminPassword() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getAdminPassword();
    }


    /**
     * Actualiza el administrador contrasena.
     *
     * @param newAdminPasswordParameterValue administrador contrasena.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public static boolean updateAdminPassword(
            String newAdminPasswordParameterValue) {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.updateAdminPassword(
                newAdminPasswordParameterValue
        );
    }


    /**
     * Devuelve el administrador identificador.
     *
     * @return el administrador identificador.
     */
    public static String getAdminIdentifier() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getAdminIdentifier();
    }


    /**
     * Devuelve el partido.
     *
     * @return el partido.
     */
    public static int getDurationMatch() {
        ensureInitialized();
        return configJsonDataAccessObjectFieldReference.getDurationMatch();
    }
}


