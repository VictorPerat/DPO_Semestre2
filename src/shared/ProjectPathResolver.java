package shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


/**
 * Resuelve la ruta del ruta.
 */
public final class ProjectPathResolver {

    private static final String PROJECT_DIRECTORY_NAME = "Project";


    /**
     * Crea una instancia de el ruta.
     */
    private ProjectPathResolver() {
    }


    /**
     * Gestiona esta operacion.
     *
     * @param relativePathParameterValue ruta que usa la operacion.
     * @return resultado de la operacion.
     */
    public static File requireExistingFile(String relativePathParameterValue) throws FileNotFoundException {
        List<Path> candidatePathsLocalVariableValue = buildCandidatePaths(relativePathParameterValue);

        for (Path candidatePathLocalVariableValue : candidatePathsLocalVariableValue) {
            File resolvedFileLocalVariableValue = candidatePathLocalVariableValue.toFile();
            if (resolvedFileLocalVariableValue.isFile()) {
                return resolvedFileLocalVariableValue;
            }
        }

        throw new FileNotFoundException(buildNotFoundMessage(relativePathParameterValue, candidatePathsLocalVariableValue));
    }


    /**
     * Gestiona esta operacion.
     *
     * @param relativePathParameterValue ruta que usa la operacion.
     * @return resultado de la operacion.
     */
    public static File resolveProjectFile(String relativePathParameterValue) {
        List<Path> candidatePathsLocalVariableValue = buildCandidatePaths(relativePathParameterValue);

        for (Path candidatePathLocalVariableValue : candidatePathsLocalVariableValue) {
            File resolvedFileLocalVariableValue = candidatePathLocalVariableValue.toFile();
            if (resolvedFileLocalVariableValue.exists()) {
                return resolvedFileLocalVariableValue;
            }
        }

        return candidatePathsLocalVariableValue.get(0).toFile();
    }


    /**
     * Gestiona esta operacion.
     *
     * @param relativePathParameterValue ruta que usa la operacion.
     * @return resultado de la operacion.
     */
    public static String resolveProjectPath(String relativePathParameterValue) {
        return resolveProjectFile(relativePathParameterValue).getPath();
    }


    /**
     * Construye el contenido.
     *
     * @param relativePathParameterValue ruta que usa la operacion.
     * @return resultado de la operacion.
     */
    private static List<Path> buildCandidatePaths(String relativePathParameterValue) {
        LinkedHashSet<Path> candidatePathsLocalVariableValue = new LinkedHashSet<>();

        for (Path searchRootLocalVariableValue : getSearchRoots()) {
            Path currentRootLocalVariableValue = searchRootLocalVariableValue;

            while (currentRootLocalVariableValue != null) {
                addCandidatesForRoot(candidatePathsLocalVariableValue, currentRootLocalVariableValue, relativePathParameterValue);
                currentRootLocalVariableValue = currentRootLocalVariableValue.getParent();
            }
        }

        return new ArrayList<>(candidatePathsLocalVariableValue);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param candidatePathsParameterValue dato de entrada de la operacion.
     * @param rootPathParameterValue ruta que usa la operacion.
     * @param relativePathParameterValue ruta que usa la operacion.
     */
    private static void addCandidatesForRoot(LinkedHashSet<Path> candidatePathsParameterValue,
                                             Path rootPathParameterValue,
                                             String relativePathParameterValue) {
        if (rootPathParameterValue.getFileName() != null
                && PROJECT_DIRECTORY_NAME.equalsIgnoreCase(rootPathParameterValue.getFileName().toString())) {
            candidatePathsParameterValue.add(rootPathParameterValue.resolve(relativePathParameterValue).normalize().toAbsolutePath());
        }

        candidatePathsParameterValue.add(
                rootPathParameterValue.resolve(PROJECT_DIRECTORY_NAME).resolve(relativePathParameterValue).normalize().toAbsolutePath()
        );
        candidatePathsParameterValue.add(rootPathParameterValue.resolve(relativePathParameterValue).normalize().toAbsolutePath());
    }


    /**
     * Devuelve el contenido.
     *
     * @return el contenido.
     */
    private static List<Path> getSearchRoots() {
        LinkedHashSet<Path> searchRootsLocalVariableValue = new LinkedHashSet<>();

        searchRootsLocalVariableValue.add(Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize());

        try {
            URL codeSourceLocationLocalVariableValue =
                    ProjectPathResolver.class.getProtectionDomain().getCodeSource().getLocation();
            if (codeSourceLocationLocalVariableValue != null) {
                Path codeSourcePathLocalVariableValue =
                        Paths.get(codeSourceLocationLocalVariableValue.toURI()).toAbsolutePath().normalize();
                if (codeSourcePathLocalVariableValue.toFile().isFile()) {
                    codeSourcePathLocalVariableValue = codeSourcePathLocalVariableValue.getParent();
                }
                searchRootsLocalVariableValue.add(codeSourcePathLocalVariableValue);
            }
        } catch (URISyntaxException ignoredExceptionParameterValue) {
        }

        return new ArrayList<>(searchRootsLocalVariableValue);
    }


    /**
     * Construye el contenido.
     *
     * @param relativePathParameterValue ruta que usa la operacion.
     * @param candidatePathsParameterValue dato de entrada de la operacion.
     * @return resultado de la operacion.
     */
    private static String buildNotFoundMessage(String relativePathParameterValue, List<Path> candidatePathsParameterValue) {
        StringBuilder messageBuilderLocalVariableValue =
                new StringBuilder("No se encontro el recurso '")
                        .append(relativePathParameterValue)
                        .append("'. Rutas comprobadas:");

        for (Path candidatePathLocalVariableValue : candidatePathsParameterValue) {
            messageBuilderLocalVariableValue
                    .append(System.lineSeparator())
                    .append(" - ")
                    .append(candidatePathLocalVariableValue);
        }

        return messageBuilderLocalVariableValue.toString();
    }
}


