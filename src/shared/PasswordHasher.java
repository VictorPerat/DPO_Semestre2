package shared;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Utilidad estatica para cifrar contrasenas con SHA-256
public final class PasswordHasher {

    // Evita instancias de una clase de utilidad
    private PasswordHasher() {
    }

    // Convierte una contrasena en su hash hexadecimal
    public static String hashPassword(String plainPasswordParameterValue) {
        try {
            MessageDigest digestLocalVariableValue = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytesLocalVariableValue =
                    digestLocalVariableValue.digest(
                            plainPasswordParameterValue.getBytes(StandardCharsets.UTF_8)
                    );

            StringBuilder hashedPasswordLocalVariableValue = new StringBuilder();

            // Transformamos cada byte en texto para poder almacenarlo
            for (byte byteValueLocalVariableValue : hashedBytesLocalVariableValue) {
                hashedPasswordLocalVariableValue.append(
                        String.format("%02x", byteValueLocalVariableValue)
                );
            }

            return hashedPasswordLocalVariableValue.toString();
        } catch (NoSuchAlgorithmException eventArgumentExceptionParameterValue) {
            throw new RuntimeException(
                    "Unable to hash password using SHA-256",
                    eventArgumentExceptionParameterValue
            );
        }
    }
}
