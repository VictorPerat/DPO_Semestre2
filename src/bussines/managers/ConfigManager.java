package bussines.managers;
import persistance.ConfigJsonDao;

/**
 * La clase {@code ConfigManager} se encarga de proporcionar acceso a los datos de configuración
 * de la aplicación, tales como parámetros de conexión a base de datos y configuración del sistema.
 * <p>
 * Utiliza la clase {@code ConfigJsonDao} para obtener los valores desde un archivo JSON.
 * Todos los métodos son estáticos para facilitar el acceso global sin necesidad de instanciar la clase.
 */
public class ConfigManager {
    /** Objeto DAO que gestiona la lectura del archivo JSON de configuración. */
    private static ConfigJsonDao configJsonDataAccessObjectFieldReference;

    /**
     * Constructor de la clase {@code ConfigManager}.
     * Inicializa la instancia estática del DAO de configuración.
     */
    public ConfigManager() {
        this.configJsonDataAccessObjectFieldReference = new ConfigJsonDao();
    }
    /**
     * Obtiene el puerto de la base de datos configurado.
     *
     * @return el puerto de la base de datos como {@code String}.
     */
    public static String getDatabasePort() { return configJsonDataAccessObjectFieldReference.getDatabasePort(); }
    /**
     * Obtiene la dirección IP de la base de datos.
     *
     * @return la IP de la base de datos como {@code String}.
     */
    public static String getDatabaseIP() { return configJsonDataAccessObjectFieldReference.getDatabaseIP(); }
    /**
     * Obtiene el nombre de la base de datos configurado.
     *
     * @return el nombre de la base de datos como {@code String}.
     */

    public static String getDatabaseName() { return configJsonDataAccessObjectFieldReference.getDatabaseName(); }
    /**
     * Obtiene el nombre de usuario para acceder a la base de datos.
     *
     * @return el nombre de usuario como {@code String}.
     */
    public static String getDatabaseUser() { return configJsonDataAccessObjectFieldReference.getDatabaseUser(); }
    /**
     * Obtiene la contraseña del usuario para la base de datos.
     *
     * @return la contraseña de la base de datos como {@code String}.
     */
    public static String getDatabasePassword() { return configJsonDataAccessObjectFieldReference.getDatabasePassword(); }
    /**
     * Obtiene la contraseña del administrador de la aplicación.
     *
     * @return la contraseña del administrador como {@code String}.
     */
    public static String getAdminPassword() { return configJsonDataAccessObjectFieldReference.getAdminPassword(); }
    /**
     * Obtiene la duración configurada para los partidos en minutos.
     *
     * @return la duración de los partidos como {@code int}.
     */
    public static int getDurationMatch() { return configJsonDataAccessObjectFieldReference.getDurationMatch();}

}
