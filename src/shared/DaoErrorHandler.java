package shared;


/**
 * Agrupa la logica de el error.
 */
public final class DaoErrorHandler {


    /**
     * Crea una instancia de el error.
     */
    private DaoErrorHandler() {

    }


    /**
     * Gestiona esta operacion.
     *
     * @param contextParameterValue dato de entrada de la operacion.
     * @param errorParameterValue error que usa la operacion.
     */
    public static void log(String contextParameterValue,
                           Throwable errorParameterValue) {
        String typeLocalVariableValue =
                errorParameterValue != null
                        ? errorParameterValue.getClass().getSimpleName()
                        : "UnknownError";
        String messageLocalVariableValue =
                errorParameterValue != null && errorParameterValue.getMessage() != null
                        ? errorParameterValue.getMessage()
                        : "(sin mensaje)";

        System.err.println(
                "[" + contextParameterValue + "] "
                        + typeLocalVariableValue + ": "
                        + messageLocalVariableValue
        );
    }
}


