package persistance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.ProjectPathResolver;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


/**
 * Gestiona el acceso a datos del configuracion json.
 */
public class ConfigJsonDao {


    private static final String FILE_PATH = "dataconfig/config.Json";
    private static final Gson GSON_FIELD_REFERENCE =
            new GsonBuilder().setPrettyPrinting().create();


    private static JsonObject configFieldReference;
    private static File configFileFieldReference;


    /**
     * Crea una instancia de el configuracion json.
     */
    public ConfigJsonDao() {
        ensureLoaded();
    }


    /**
     * Gestiona esta operacion.
     */
    private static synchronized void ensureLoaded() {
        if (configFieldReference != null && configFileFieldReference != null) {
            return;
        }

        JsonParser parserLocalVariableValue = new JsonParser();

        try {
            configFileFieldReference =
                    ProjectPathResolver.requireExistingFile(FILE_PATH);

            try (Reader readerLocalVariableValue = Files.newBufferedReader(
                    configFileFieldReference.toPath(),
                    StandardCharsets.UTF_8
            )) {
                configFieldReference = parserLocalVariableValue
                        .parse(readerLocalVariableValue)
                        .getAsJsonObject();
            }

        } catch (IOException eventArgumentExceptionParameter) {
            throw new RuntimeException(
                    "No se pudo leer el archivo de configuracion.",
                    eventArgumentExceptionParameter
            );
        }
    }


    /**
     * Devuelve los base de datos.
     *
     * @return los base de datos.
     */
    public static String getDatabasePort() {
        ensureLoaded();
        return configFieldReference.get("databasePort").getAsString();
    }


    /**
     * Devuelve los base de datos.
     *
     * @return los base de datos.
     */
    public static String getDatabaseIP() {
        ensureLoaded();
        return configFieldReference.get("databaseIP").getAsString();
    }


    /**
     * Devuelve el base de datos nombre.
     *
     * @return el base de datos nombre.
     */
    public static String getDatabaseName() {
        ensureLoaded();
        return configFieldReference.get("databaseName").getAsString();
    }


    /**
     * Devuelve el base de datos usuario.
     *
     * @return el base de datos usuario.
     */
    public static String getDatabaseUser() {
        ensureLoaded();
        return configFieldReference.get("databaseUser").getAsString();
    }


    /**
     * Devuelve el base de datos contrasena.
     *
     * @return el base de datos contrasena.
     */
    public static String getDatabasePassword() {
        ensureLoaded();
        return configFieldReference.get("databasePassword").getAsString();
    }


    /**
     * Devuelve el administrador contrasena.
     *
     * @return el administrador contrasena.
     */
    public static String getAdminPassword() {
        ensureLoaded();
        return configFieldReference.get("adminPassword").getAsString();
    }


    /**
     * Devuelve el administrador identificador.
     *
     * @return el administrador identificador.
     */
    public static String getAdminIdentifier() {
        ensureLoaded();
        return configFieldReference.get("admin").getAsString();
    }


    /**
     * Devuelve el partido.
     *
     * @return el partido.
     */
    public static int getDurationMatch() {
        ensureLoaded();
        return configFieldReference.get("matchTime").getAsInt();
    }


    /**
     * Actualiza el administrador contrasena.
     *
     * @param newAdminPasswordParameterValue administrador contrasena.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    public synchronized boolean updateAdminPassword(
            String newAdminPasswordParameterValue) {
        ensureLoaded();

        String previousAdminPasswordLocalVariableValue =
                getAdminPassword();

        configFieldReference.addProperty(
                "adminPassword",
                newAdminPasswordParameterValue
        );

        try {
            Files.write(
                    configFileFieldReference.toPath(),
                    GSON_FIELD_REFERENCE.toJson(configFieldReference)
                            .getBytes(StandardCharsets.UTF_8)
            );
            return true;
        } catch (IOException eventArgumentExceptionParameter) {
            configFieldReference.addProperty(
                    "adminPassword",
                    previousAdminPasswordLocalVariableValue
            );
            return false;
        }
    }
}


