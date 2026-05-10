package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import presentation.AppNavigator;
import presentation.Views.SignUpView;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Coordina la pantalla de esta parte de la aplicacion.
 */
public class SignUpController implements ActionListener {

    private final SignUpView signUpViewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;
    private final AppNavigator navigatorFieldReference;


    /**
     * Crea una instancia de signupcontroller.
     *
     * @param signUpViewInterfaceParameterValue vista que usa la operacion.
     * @param playerProfileManagerServiceParameterValue jugador perfil.
     * @param navigatorParameterValue navegacion que usa la operacion.
     */
    public SignUpController(
            SignUpView signUpViewInterfaceParameterValue,
            PlayerManager playerProfileManagerServiceParameterValue,
            AppNavigator navigatorParameterValue) {
        this.signUpViewInterfaceFieldReference = signUpViewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.navigatorFieldReference = navigatorParameterValue;
        this.signUpViewInterfaceFieldReference.registerController(this);
    }


    /**
     * Gestiona esta operacion.
     *
     * @param eventArgumentParameterValue dato de entrada de la operacion.
     */
    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();

        if (SignUpView.BACK_TO_LOGIN.equals(commandLocalVariableValue)) {
            backToLogin();
            return;
        }

        if (SignUpView.REGISTER_BUTTON.equals(commandLocalVariableValue)) {
            handleRegister();
        }
    }


    /**
     * Gestiona esta operacion.
     */
    private void handleRegister() {
        String nationalIdentityDocumentLocalVariableValue = signUpViewInterfaceFieldReference.getDni().trim();
        String displayNameLocalVariableValue = signUpViewInterfaceFieldReference.getName().trim();
        String emailAddressLocalVariableValue = signUpViewInterfaceFieldReference.getEmail().trim();
        String jerseyNumberLocalVariableValue = signUpViewInterfaceFieldReference.getDorsal().trim();
        String teamReferenceLocalVariableValue = signUpViewInterfaceFieldReference.getTeam().trim();
        String phoneNumberLocalVariableValue = signUpViewInterfaceFieldReference.getPhone().trim();

        if (nationalIdentityDocumentLocalVariableValue.isEmpty()
                || displayNameLocalVariableValue.isEmpty() || emailAddressLocalVariableValue.isEmpty()
                || jerseyNumberLocalVariableValue.isEmpty() || teamReferenceLocalVariableValue.isEmpty()
                || phoneNumberLocalVariableValue.isEmpty()) {
            signUpViewInterfaceFieldReference.showMessageDialog("All fields are required.");
            return;
        }

        if (!nationalIdentityDocumentLocalVariableValue.matches("^\\d{8}[A-Z]$")) {
            signUpViewInterfaceFieldReference.showMessageDialog("Invalid DNI (expected format: 8 digits + uppercase letter).");
            return;
        }

        if (!emailAddressLocalVariableValue.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            signUpViewInterfaceFieldReference.showMessageDialog("Invalid email (expected format: example@domain.com).");
            return;
        }

        if (!phoneNumberLocalVariableValue.matches("^\\d{9}$")) {
            signUpViewInterfaceFieldReference.showMessageDialog("Invalid phone number (must be 9 digits).");
            return;
        }

        try {
            int jerseyNumberNumericValueLocalVariableValue = Integer.parseInt(jerseyNumberLocalVariableValue);
            if (jerseyNumberNumericValueLocalVariableValue < 0 || jerseyNumberNumericValueLocalVariableValue > 99) {
                signUpViewInterfaceFieldReference.showMessageDialog("Dorsal must be between 0 and 99.");
                return;
            }
        } catch (NumberFormatException eventArgumentExceptionParameterValue) {
            signUpViewInterfaceFieldReference.showMessageDialog("Dorsal must be an integer number.");
            return;
        }

        String generatedPasswordLocalVariableValue = generateRandomPassword(8);
        boolean successLocalVariableValue = playerProfileManagerServiceFieldReference.registerPlayer(
                nationalIdentityDocumentLocalVariableValue,
                displayNameLocalVariableValue,
                emailAddressLocalVariableValue,
                generatedPasswordLocalVariableValue,
                jerseyNumberLocalVariableValue,
                teamReferenceLocalVariableValue,
                phoneNumberLocalVariableValue
        );

        if (!successLocalVariableValue) {
            signUpViewInterfaceFieldReference.showMessageDialog("Error: a player with this email or DNI already exists, or the insert failed.");
            return;
        }

        showCopyablePasswordDialog(generatedPasswordLocalVariableValue, nationalIdentityDocumentLocalVariableValue);
        backToLogin();
    }


    /**
     * Muestra el contrasena dialogo.
     *
     * @param generatedPasswordParameterValue contrasena que usa la operacion.
     * @param nationalIdentityDocumentParameterValue documento de identidad del jugador.
     */
    private void showCopyablePasswordDialog(String generatedPasswordParameterValue, String nationalIdentityDocumentParameterValue) {
        JTextArea textAreaLocalVariableValue = new JTextArea(
                "Registration completed successfully!\n\n" +
                "DNI: " + nationalIdentityDocumentParameterValue + "\n" +
                "Generated password: " + generatedPasswordParameterValue + "\n\n" +
                "You can copy this password."
        );
        textAreaLocalVariableValue.setEditable(false);
        textAreaLocalVariableValue.setLineWrap(true);
        textAreaLocalVariableValue.setWrapStyleWord(true);
        textAreaLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 15));
        textAreaLocalVariableValue.setBackground(UIManager.getColor("Panel.background"));

        JButton copyButtonLocalVariableValue = new JButton("Copy password");
        copyButtonLocalVariableValue.addActionListener(eventArgumentParameterValue -> {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                    new StringSelection(generatedPasswordParameterValue),
                    null
            );
        });

        JPanel panelLocalVariableValue = new JPanel(new BorderLayout(10, 10));
        panelLocalVariableValue.add(new JScrollPane(textAreaLocalVariableValue), BorderLayout.CENTER);
        panelLocalVariableValue.add(copyButtonLocalVariableValue, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(signUpViewInterfaceFieldReference),
                panelLocalVariableValue,
                "Registration",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    /**
     * Gestiona esta operacion.
     */
    private void backToLogin() {
        signUpViewInterfaceFieldReference.clearForm();
        navigatorFieldReference.show(AppNavigator.LOGIN);
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

        StringBuilder sbLocalVariableValue = new StringBuilder();
        for (char charValueLocalVariableValue : passwordCharsLocalVariableValue) {
            sbLocalVariableValue.append(charValueLocalVariableValue);
        }

        return sbLocalVariableValue.toString();
    }
}


