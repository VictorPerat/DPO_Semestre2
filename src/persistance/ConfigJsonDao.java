package persistance;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Lee la configuración del proyecto desde config.Json.
 */
public class ConfigJsonDao {
    private static final String FILE_PATH = "dataconfig/config.Json";
    private static JsonObject configFieldReference;

    public ConfigJsonDao() {
        if (configFieldReference != null) {
            return;
        }

        JsonParser parserLocalVariableValue = new JsonParser();
        try {
            FileReader readerLocalVariableValue = new FileReader(FILE_PATH);
            configFieldReference = parserLocalVariableValue.parse(readerLocalVariableValue).getAsJsonObject();
        } catch (FileNotFoundException eventArgumentExceptionParameter) {
            throw new RuntimeException(eventArgumentExceptionParameter);
        }
    }

    public static String getDatabasePort() { return configFieldReference.get("databasePort").getAsString(); }
    public static String getDatabaseIP() { return configFieldReference.get("databaseIP").getAsString(); }
    public static String getDatabaseName() { return configFieldReference.get("databaseName").getAsString(); }
    public static String getDatabaseUser() { return configFieldReference.get("databaseUser").getAsString(); }
    public static String getDatabasePassword() { return configFieldReference.get("databasePassword").getAsString(); }
    public static String getAdminPassword() { return configFieldReference.get("adminPassword").getAsString(); }
    public static int getDurationMatch() { return configFieldReference.get("matchTime").getAsInt(); }
}
