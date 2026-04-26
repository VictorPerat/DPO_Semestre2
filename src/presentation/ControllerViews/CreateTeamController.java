
    package presentation.ControllerViews;

    import bussines.objects.Player;
    import bussines.objects.Team;
    import bussines.managers.TeamManager;
    import bussines.managers.PlayerManager;
    import org.json.JSONObject;
    import org.json.JSONArray;
    import presentation.Views.CreateTeamView;

    import javax.swing.*;
    import java.io.File;
    import java.nio.file.Files;
    import java.nio.file.StandardCopyOption;
    import java.nio.charset.StandardCharsets;
    import java.util.ArrayList;
    import java.util.Random;

    /**
     * Controlador para la creación de nuevos equipos a partir de archivos JSON.
     * Permite cargar archivos, validar y crear equipos junto con sus jugadores asociados.
     * También maneja la navegación de vuelta al menú administrador y el acceso a configuraciones.
     */
    public class CreateTeamController {

        /**
         * Vista para la creación de nuevos equipos.
         */
        private final CreateTeamView viewInterfaceFieldReference;

        /**
         * Controlador del menú administrador para navegación y gestión de configuraciones.
         */
        private final AdminMenuController adminMenuControllerHandlerFieldReference;

        /**
         * Gestor de lógica para equipos.
         */
        private final TeamManager teamReferenceManagerServiceFieldReference = new TeamManager();

        /**
         * Gestor de lógica para jugadores.
         */
        private final PlayerManager playerProfileManagerServiceFieldReference = new PlayerManager();

        /**
         * Constructor que inicializa la vista, el controlador del menú y configura los listeners necesarios.
         * Además, muestra la vista y refresca la lista de archivos disponibles.
         *
         * @param view Vista para crear nuevos equipos.
         * @param adminMenuController Controlador del menú administrador.
         */
        public CreateTeamController(CreateTeamView viewInterfaceParameterValue, AdminMenuController adminMenuControllerHandlerParameterValue) {
            this.viewInterfaceFieldReference = viewInterfaceParameterValue;
            this.adminMenuControllerHandlerFieldReference = adminMenuControllerHandlerParameterValue;
            setupListeners();
            viewInterfaceParameterValue.setVisible(true);
            viewInterfaceParameterValue.refreshFileList();
        }

        /**
         * Configura los listeners para botones y acciones de la vista.
         * Incluye la carga y validación de archivos JSON, navegación y configuración.
         */
        private void setupListeners() {
            // Listener para botón "Back" que vuelve al menú administrador.
            viewInterfaceFieldReference.setBackButtonListener(eventArgumentParameterValue -> {
                viewInterfaceFieldReference.dispose();
                adminMenuControllerHandlerFieldReference.showAdminMenu();
            });

            // Listener para abrir diálogo de configuración.
            viewInterfaceFieldReference.setConfigController(eventArgumentParameterValue2 -> {
                if ("CONFIG".equals(eventArgumentParameterValue2.getActionCommand())) {
                    adminMenuControllerHandlerFieldReference.showConfigDialog();
                }
            });

            // Listener para validar y procesar los archivos JSON de equipos.
            viewInterfaceFieldReference.setValidateListener(eventArgumentParameterValue3 -> {
                File folderLocalVariableValue = new File("data/teams");
                File[] filesLocalVariableValue = folderLocalVariableValue.listFiles((dirParameterValue, displayNameParameterValue) -> displayNameParameterValue.toLowerCase().endsWith(".json"));

                if (filesLocalVariableValue == null || filesLocalVariableValue.length == 0) {
                    JOptionPane.showMessageDialog(viewInterfaceFieldReference, "No .json files found to import.");
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

                        ArrayList<Player> playersLocalVariableValue = new ArrayList<>();

                        boolean validTeamReferenceLocalVariableValue = true;
                        for (int indexCounterLocalVariableValue = 0; indexCounterLocalVariableValue < playersArrayLocalVariableValue.length(); indexCounterLocalVariableValue++) {
                            JSONObject itemValueLocalVariableValue = playersArrayLocalVariableValue.getJSONObject(indexCounterLocalVariableValue);
                            String displayNameLocalVariableValue = itemValueLocalVariableValue.getString("name");
                            String emailAddressLocalVariableValue = itemValueLocalVariableValue.getString("email");
                            String nationalIdentityDocumentLocalVariableValue = itemValueLocalVariableValue.getString("dni");
                            int jerseyNumberLocalVariableValue = itemValueLocalVariableValue.getInt("number");

                            if (!isValidEmail(emailAddressLocalVariableValue)) {
                                JOptionPane.showMessageDialog(viewInterfaceFieldReference, "Invalid email for player: " + displayNameLocalVariableValue);
                                validTeamReferenceLocalVariableValue = false;
                                break;
                            }
                                if (!isValidDNI(nationalIdentityDocumentLocalVariableValue)) {
                                    JOptionPane.showMessageDialog(viewInterfaceFieldReference, "Invalid DNI for player: " + displayNameLocalVariableValue);
                                    validTeamReferenceLocalVariableValue = false;
                                    break;
                                }

                                if (jerseyNumberLocalVariableValue < 0) {
                                    JOptionPane.showMessageDialog(viewInterfaceFieldReference, "Invalid jersey number for player: " + displayNameLocalVariableValue);
                                    validTeamReferenceLocalVariableValue = false;
                                break;
                            }

                            // si pasa validaciones, agrega jugador
                            String randomUserPasswordLocalVariableValue = generateRandomPassword(8);
                            Player playerProfileLocalVariableValue = new Player(displayNameLocalVariableValue, emailAddressLocalVariableValue, nationalIdentityDocumentLocalVariableValue, itemValueLocalVariableValue.getString("phone"), jerseyNumberLocalVariableValue, randomUserPasswordLocalVariableValue, 0);
                            playerProfileLocalVariableValue.setTeamId(teamReferenceDisplayNameLocalVariableValue);
                            playersLocalVariableValue.add(playerProfileLocalVariableValue);
                        }

                        if (!validTeamReferenceLocalVariableValue) {
                            JOptionPane.showMessageDialog(viewInterfaceFieldReference, "The team '" + teamReferenceDisplayNameLocalVariableValue + "' will not be created because some players have wrong data");
                            continue; // pasa al siguiente archivo JSON
                        }

                        if (playersLocalVariableValue.isEmpty()) {
                            JOptionPane.showMessageDialog(viewInterfaceFieldReference, "The team '" + teamReferenceDisplayNameLocalVariableValue + "' does not have players. Will not be created");
                            continue;
                        }


                        if (teamReferenceManagerServiceFieldReference.teamExists(teamReferenceDisplayNameLocalVariableValue)) {
                            JOptionPane.showMessageDialog(viewInterfaceFieldReference, "The team '" + teamReferenceDisplayNameLocalVariableValue + "' already exists. Will not be loaded in JSON file");
                            continue;
                        }

                        Team teamReferenceLocalVariableValue = new Team(teamReferenceDisplayNameLocalVariableValue);
                        boolean createdLocalVariableValue = teamReferenceManagerServiceFieldReference.createTeam(teamReferenceLocalVariableValue);
                        if (createdLocalVariableValue) {
                            JOptionPane.showMessageDialog(viewInterfaceFieldReference, "The team '" + teamReferenceDisplayNameLocalVariableValue + "' has been created.");

                            for (Player itemValueLocalVariableValue2 : playersLocalVariableValue) {
                                boolean insertedLocalVariableValue = playerProfileManagerServiceFieldReference.registerPlayer(
                                        itemValueLocalVariableValue2.getDniPlayer(),
                                        itemValueLocalVariableValue2.getNamePlayer(),
                                        itemValueLocalVariableValue2.getMail(),
                                        itemValueLocalVariableValue2.getPassword(),
                                        String.valueOf(itemValueLocalVariableValue2.getDorsal()),
                                        itemValueLocalVariableValue2.getTeamId(),
                                        String.valueOf(itemValueLocalVariableValue2.getPhoneNumber())
                                );
                                if (insertedLocalVariableValue) {
                                    createdAccountsLocalVariableValue.add("DNI: " + itemValueLocalVariableValue2.getDniPlayer() + " | Password: " + itemValueLocalVariableValue2.getPassword());
                                } else {
                                    System.out.println("Player already exists:" + itemValueLocalVariableValue2.getNamePlayer());
                                }
                            }
                            countLocalVariableValue++;
                        } else {
                            JOptionPane.showMessageDialog(viewInterfaceFieldReference, "Error creating the team: " + teamReferenceDisplayNameLocalVariableValue + "'.");
                        }

                    } catch (Exception exExceptionParameter) {
                        JOptionPane.showMessageDialog(viewInterfaceFieldReference, "Error processing " + fileLocalVariableValue.getName() + ": " + exExceptionParameter.getMessage());
                    }
                }

                // Mostrar cuentas creadas con DNI y contraseña.
                if (!createdAccountsLocalVariableValue.isEmpty()) {
                    StringBuilder sbLocalVariableValue = new StringBuilder("Added players: \n\n");
                    for (String accLocalVariableValue : createdAccountsLocalVariableValue) {
                        sbLocalVariableValue.append(accLocalVariableValue).append("  \n");
                    }
                    JOptionPane.showMessageDialog(viewInterfaceFieldReference, sbLocalVariableValue.toString());
                }

                // Cerramos la vista y volvemos al menú administrador.
                viewInterfaceFieldReference.dispose();
                adminMenuControllerHandlerFieldReference.showAdminMenu();
            });

            // Listener para cargar archivos JSON al directorio de equipos.
            viewInterfaceFieldReference.setFileUploadListener(fileParameterValue -> {
                try {
                    File destDirLocalVariableValue = new File("data/teams");
                    if (!destDirLocalVariableValue.exists()) destDirLocalVariableValue.mkdirs();
                    File destinationLocalVariableValue = new File(destDirLocalVariableValue, fileParameterValue.getName());
                    Files.copy(fileParameterValue.toPath(), destinationLocalVariableValue.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(viewInterfaceFieldReference, "File loaded: " + fileParameterValue.getName());
                    viewInterfaceFieldReference.refreshFileList();
                } catch (Exception exExceptionParameter2) {
                    JOptionPane.showMessageDialog(viewInterfaceFieldReference, "Error loading File: " + exExceptionParameter2.getMessage());
                }
            });
        }
        private String generateRandomPassword(int lengthParameterValue) {
            String charsLocalVariableValue = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            Random rndLocalVariableValue = new Random();
            StringBuilder sbLocalVariableValue2 = new StringBuilder(lengthParameterValue);
            for (int indexCounterLocalVariableValue2 = 0; indexCounterLocalVariableValue2 < lengthParameterValue; indexCounterLocalVariableValue2++) {
                sbLocalVariableValue2.append(charsLocalVariableValue.charAt(rndLocalVariableValue.nextInt(charsLocalVariableValue.length())));
            }
            return sbLocalVariableValue2.toString();
        }
        private boolean isValidEmail(String emailAddressParameterValue) {
            return emailAddressParameterValue != null && emailAddressParameterValue.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        }

        private boolean isValidDNI(String nationalIdentityDocumentParameterValue) {
            return nationalIdentityDocumentParameterValue != null && nationalIdentityDocumentParameterValue.matches("^\\d{8}[A-Z]$");
        }



    }