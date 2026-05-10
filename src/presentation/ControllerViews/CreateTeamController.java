package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import bussines.managers.TeamManager;
import bussines.objects.Player;
import bussines.objects.Team;
import org.json.JSONArray;
import org.json.JSONObject;
import presentation.AppNavigator;
import presentation.Views.CreateTeamView;

import javax.swing.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Coordina la pantalla del equipo.
 */
public class CreateTeamController {
    private static final String TEAMS_DIRECTORY_PATH = "data/teams";
    private final CreateTeamView viewInterfaceFieldReference;
    private final AppNavigator navigatorFieldReference;
    private final TeamManager teamReferenceManagerServiceFieldReference = new TeamManager();
    private final PlayerManager playerProfileManagerServiceFieldReference = new PlayerManager();


    /**
     * Crea una instancia de el equipo.
     *
     * @param viewInterfaceParameterValue vista que usa la operacion.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
    public CreateTeamController(CreateTeamView viewInterfaceParameterValue,
                                AppNavigator navigatorParameterValue) {
        this.viewInterfaceFieldReference = viewInterfaceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        setupListeners();
        viewInterfaceParameterValue.refreshFileList();
    }


    /**
     * Actualiza el contenido.
     */
    private void setupListeners() {
        viewInterfaceFieldReference.setBackButtonListener(eventArgumentParameterValue -> navigatorFieldReference.show(AppNavigator.ADMIN_MENU));
        viewInterfaceFieldReference.setValidateListener(eventArgumentParameterValue -> validateAndImportTeams());
        viewInterfaceFieldReference.setFileUploadListener(this::copyUploadedFile);
    }


    /**
     * Devuelve los equipos.
     *
     * @return los equipos.
     */
    private File getTeamsDirectory() {
        File folderLocalVariableValue = new File(TEAMS_DIRECTORY_PATH);
        if (!folderLocalVariableValue.exists()) { folderLocalVariableValue.mkdirs(); }
        return folderLocalVariableValue;
    }


    /**
     * Gestiona esta operacion.
     *
     * @param selectedFileParameterValue dato de entrada de la operacion.
     */
    private void copyUploadedFile(File selectedFileParameterValue) {
        try {
            File destinationLocalVariableValue = new File(getTeamsDirectory(), selectedFileParameterValue.getName());
            Files.copy(selectedFileParameterValue.toPath(), destinationLocalVariableValue.toPath(), StandardCopyOption.REPLACE_EXISTING);
            viewInterfaceFieldReference.refreshFileList();
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(viewInterfaceFieldReference), "File uploaded successfully.");
        } catch (Exception exceptionParameterValue) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(viewInterfaceFieldReference), "Error uploading file: " + exceptionParameterValue.getMessage());
        }
    }


    /**
     * Gestiona esta operacion.
     */
    private void validateAndImportTeams() {
        File[] filesLocalVariableValue = getTeamsDirectory().listFiles((dirParameterValue, displayNameParameterValue) -> displayNameParameterValue.toLowerCase().endsWith(".json"));
        if (filesLocalVariableValue == null || filesLocalVariableValue.length == 0) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(viewInterfaceFieldReference), "No .json files found to import.");
            return;
        }
        int countLocalVariableValue = 0;
        ArrayList<String> createdAccountsLocalVariableValue = new ArrayList<>();
        for (File fileLocalVariableValue : filesLocalVariableValue) {
            try {
                String contentLocalVariableValue = new String(Files.readAllBytes(fileLocalVariableValue.toPath()), StandardCharsets.UTF_8);
                JSONObject jsonLocalVariableValue = new JSONObject(contentLocalVariableValue);
                String teamReferenceDisplayNameLocalVariableValue = jsonLocalVariableValue.getString("team_name");
                JSONArray playersArrayLocalVariableValue = jsonLocalVariableValue.getJSONArray("players");
                if (teamReferenceManagerServiceFieldReference.teamExists(teamReferenceDisplayNameLocalVariableValue)) {
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(viewInterfaceFieldReference), "The team '" + teamReferenceDisplayNameLocalVariableValue + "' already exists.");
                    continue;
                }
                ArrayList<Player> playersLocalVariableValue = new ArrayList<>();
                boolean validTeamReferenceLocalVariableValue = true;
                for (int indexCounterLocalVariableValue = 0; indexCounterLocalVariableValue < playersArrayLocalVariableValue.length(); indexCounterLocalVariableValue++) {
                    JSONObject itemValueLocalVariableValue = playersArrayLocalVariableValue.getJSONObject(indexCounterLocalVariableValue);
                    String displayNameLocalVariableValue = itemValueLocalVariableValue.getString("name");
                    String emailAddressLocalVariableValue = itemValueLocalVariableValue.getString("email");
                    String nationalIdentityDocumentLocalVariableValue = itemValueLocalVariableValue.getString("dni");
                    int jerseyNumberLocalVariableValue = itemValueLocalVariableValue.getInt("number");
                    if (!isValidEmail(emailAddressLocalVariableValue) || !isValidDNI(nationalIdentityDocumentLocalVariableValue) || jerseyNumberLocalVariableValue < 0) {
                        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(viewInterfaceFieldReference), "Invalid data for player: " + displayNameLocalVariableValue);
                        validTeamReferenceLocalVariableValue = false;
                        break;
                    }
                    String randomPasswordLocalVariableValue = generateRandomPassword(10);
                    Player playerProfileLocalVariableValue = new Player(displayNameLocalVariableValue, emailAddressLocalVariableValue, nationalIdentityDocumentLocalVariableValue, teamReferenceDisplayNameLocalVariableValue, jerseyNumberLocalVariableValue, randomPasswordLocalVariableValue, 0);
                    playersLocalVariableValue.add(playerProfileLocalVariableValue);
                    createdAccountsLocalVariableValue.add(nationalIdentityDocumentLocalVariableValue + " -> " + randomPasswordLocalVariableValue);
                }
                if (!validTeamReferenceLocalVariableValue || playersLocalVariableValue.isEmpty()) { continue; }
                Team teamReferenceLocalVariableValue = new Team(teamReferenceDisplayNameLocalVariableValue);
                if (teamReferenceManagerServiceFieldReference.createTeam(teamReferenceLocalVariableValue)) {
                    for (Player playerProfileLocalVariableValue : playersLocalVariableValue) {
                        if (!playerProfileManagerServiceFieldReference.playerExists(playerProfileLocalVariableValue.getDniPlayer())) {
                            playerProfileManagerServiceFieldReference.registerPlayer(
                                    playerProfileLocalVariableValue.getDniPlayer(),
                                    playerProfileLocalVariableValue.getNamePlayer(),
                                    playerProfileLocalVariableValue.getMail(),
                                    playerProfileLocalVariableValue.getPassword(),
                                    String.valueOf(playerProfileLocalVariableValue.getDorsal()),
                                    teamReferenceDisplayNameLocalVariableValue,
                                    String.valueOf(playerProfileLocalVariableValue.getPhoneNumber())
                            );
                        }
                    }
                    countLocalVariableValue++;
                }
            } catch (Exception exceptionParameterValue) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(viewInterfaceFieldReference), "Error processing file " + fileLocalVariableValue.getName() + ": " + exceptionParameterValue.getMessage());
            }
        }
        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(viewInterfaceFieldReference), "Imported teams: " + countLocalVariableValue + (createdAccountsLocalVariableValue.isEmpty() ? "" : "\nCreated accounts:\n" + String.join("\n", createdAccountsLocalVariableValue)));
        viewInterfaceFieldReference.refreshFileList();
    }


    /**
     * Indica el estado actual.
     *
     * @param emailAddressParameterValue direccion de email.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    private boolean isValidEmail(String emailAddressParameterValue) {
        return emailAddressParameterValue != null && emailAddressParameterValue.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }


    /**
     * Indica el estado actual.
     *
     * @param dniParameterValue dni que usa la operacion.
     * @return {@code true} si la operacion se completa correctamente; en caso contrario, {@code false}.
     */
    private boolean isValidDNI(String dniParameterValue) {
        return dniParameterValue != null && dniParameterValue.matches("^[0-9]{8}[A-Za-z]$");
    }


    /**
     * Gestiona esta operacion.
     *
     * @param lengthParameterValue dato de entrada de la operacion.
     * @return resultado de la operacion.
     */
    private String generateRandomPassword(int lengthParameterValue) {
        int effectiveLengthLocalVariableValue = Math.max(lengthParameterValue, 8);

        String lowercaseCharsLocalVariableValue = "abcdefghijklmnopqrstuvwxyz";
        String uppercaseCharsLocalVariableValue = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digitCharsLocalVariableValue = "0123456789";
        String allCharsLocalVariableValue =
                lowercaseCharsLocalVariableValue
                + uppercaseCharsLocalVariableValue
                + digitCharsLocalVariableValue;

        SecureRandom secureRandomLocalVariableValue = new SecureRandom();
        ArrayList<Character> passwordCharsLocalVariableValue = new ArrayList<>();


        passwordCharsLocalVariableValue.add(
                lowercaseCharsLocalVariableValue.charAt(
                        secureRandomLocalVariableValue.nextInt(
                                lowercaseCharsLocalVariableValue.length())));
        passwordCharsLocalVariableValue.add(
                uppercaseCharsLocalVariableValue.charAt(
                        secureRandomLocalVariableValue.nextInt(
                                uppercaseCharsLocalVariableValue.length())));
        passwordCharsLocalVariableValue.add(
                digitCharsLocalVariableValue.charAt(
                        secureRandomLocalVariableValue.nextInt(
                                digitCharsLocalVariableValue.length())));


        for (int indexCounterLocalVariableValue = 3;
             indexCounterLocalVariableValue < effectiveLengthLocalVariableValue;
             indexCounterLocalVariableValue++) {
            passwordCharsLocalVariableValue.add(
                    allCharsLocalVariableValue.charAt(
                            secureRandomLocalVariableValue.nextInt(
                                    allCharsLocalVariableValue.length())));
        }


        Collections.shuffle(passwordCharsLocalVariableValue, secureRandomLocalVariableValue);

        StringBuilder builderLocalVariableValue = new StringBuilder();
        for (char charValueLocalVariableValue : passwordCharsLocalVariableValue) {
            builderLocalVariableValue.append(charValueLocalVariableValue);
        }

        return builderLocalVariableValue.toString();
    }
}


