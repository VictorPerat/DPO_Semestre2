package persistance;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.ProjectPathResolver;

import java.io.FileReader;
import java.io.IOException;

/**
 * Esta clase se encarga de leer el archivo de configuración del proyecto.
 */
public class ConfigJsonDao {

    // Ruta del archivo de configuración
    private static final String FILE_PATH = "dataconfig/config.Json";

    // Objeto que guarda los datos leídos del JSON
    private static JsonObject configFieldReference;

    // Constructor que carga el archivo solo una vez
    public ConfigJsonDao() {
        if (configFieldReference != null) {
            return;
        }

        JsonParser parserLocalVariableValue = new JsonParser();

        try (FileReader readerLocalVariableValue =
                     new FileReader(ProjectPathResolver.requireExistingFile(FILE_PATH))) {

            configFieldReference = parserLocalVariableValue
                    .parse(readerLocalVariableValue)
                    .getAsJsonObject();

        } catch (IOException eventArgumentExceptionParameter) {
            throw new RuntimeException(
                    "No se pudo leer el archivo de configuracion.",
                    eventArgumentExceptionParameter
            );
        }
    }

    // Devuelve el puerto de la base de datos
    public static String getDatabasePort() {
        return configFieldReference.get("databasePort").getAsString();
    }

    // Devuelve la IP de la base de datos
    public static String getDatabaseIP() {
        return configFieldReference.get("databaseIP").getAsString();
    }

    // Devuelve el nombre de la base de datos
    public static String getDatabaseName() {
        return configFieldReference.get("databaseName").getAsString();
    }

    // Devuelve el usuario de la base de datos
    public static String getDatabaseUser() {
        return configFieldReference.get("databaseUser").getAsString();
    }

    // Devuelve la contraseña de la base de datos
    public static String getDatabasePassword() {
        return configFieldReference.get("databasePassword").getAsString();
    }

    // Devuelve la contraseña del administrador
    public static String getAdminPassword() {
        return configFieldReference.get("adminPassword").getAsString();
    }

    // Devuelve la duración de los partidos
    public static int getDurationMatch() {
        return configFieldReference.get("matchTime").getAsInt();
    }
}