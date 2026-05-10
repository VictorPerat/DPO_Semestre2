package shared;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Genera el hash del contrasena.
 */
public final class PasswordHasher {


    /**
     * Crea una instancia de el contrasena.
     */
    private PasswordHasher() {
    }


    /**
     * Indica el estado actual.
     *
     * @param plainPasswordParameterValue contrasena que usa la operacion.
     * @return resultado de la operacion.
     */
    public static String hashPassword(String plainPasswordParameterValue) {
        try {
            MessageDigest digestLocalVariableValue = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytesLocalVariableValue =
                    digestLocalVariableValue.digest(
                            plainPasswordParameterValue.getBytes(StandardCharsets.UTF_8)
                    );

            StringBuilder hashedPasswordLocalVariableValue = new StringBuilder();


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


