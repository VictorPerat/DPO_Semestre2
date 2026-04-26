package presentation.ControllerViews;

import bussines.managers.PlayerManager;
import presentation.Views.LoginView;
import presentation.Views.SignUpView;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Controlador simplificado para registro de usuarios.
 */
public class SignUpController implements ActionListener {
    private final SignUpView signUpViewInterfaceFieldReference;
    private final PlayerManager playerProfileManagerServiceFieldReference;

    public SignUpController(SignUpView signUpViewInterfaceParameterValue, PlayerManager playerProfileManagerServiceParameterValue) {
        this.signUpViewInterfaceFieldReference = signUpViewInterfaceParameterValue;
        this.playerProfileManagerServiceFieldReference = playerProfileManagerServiceParameterValue;
        this.signUpViewInterfaceFieldReference.registerController(this);
    }

    @Override
    public void actionPerformed(ActionEvent eventArgumentParameterValue) {
        String commandLocalVariableValue = eventArgumentParameterValue.getActionCommand();

        if (SignUpView.BACK_TO_LOGIN.equals(commandLocalVariableValue)) {
            openLoginView();
            return;
        }

        if (SignUpView.REGISTER_BUTTON.equals(commandLocalVariableValue)) {
            handleRegister();
        }
    }

    private void handleRegister() {
        String nationalIdentityDocumentLocalVariableValue = signUpViewInterfaceFieldReference.getDni().trim();
        String displayNameLocalVariableValue = signUpViewInterfaceFieldReference.getName().trim();
        String emailAddressLocalVariableValue = signUpViewInterfaceFieldReference.getEmail().trim();
        String jerseyNumberLocalVariableValue = signUpViewInterfaceFieldReference.getDorsal().trim();
        String teamReferenceLocalVariableValue = signUpViewInterfaceFieldReference.getTeam().trim();
        String phoneNumberLocalVariableValue = signUpViewInterfaceFieldReference.getPhone().trim();

        if (nationalIdentityDocumentLocalVariableValue.isEmpty() || displayNameLocalVariableValue.isEmpty() || emailAddressLocalVariableValue.isEmpty()
                || jerseyNumberLocalVariableValue.isEmpty() || teamReferenceLocalVariableValue.isEmpty() || phoneNumberLocalVariableValue.isEmpty()) {
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
        openLoginView();
    }

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
                signUpViewInterfaceFieldReference,
                panelLocalVariableValue,
                "Registration",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void openLoginView() {
        signUpViewInterfaceFieldReference.dispose();
        LoginView loginViewInterfaceLocalVariableValue = new LoginView();
        new LoginController(loginViewInterfaceLocalVariableValue, playerProfileManagerServiceFieldReference);
    }

    private String generateRandomPassword(int lengthParameterValue) {
        String charsLocalVariableValue = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sbLocalVariableValue = new StringBuilder();
        Random rndLocalVariableValue = new Random();
        for (int indexCounterLocalVariableValue = 0; indexCounterLocalVariableValue < lengthParameterValue; indexCounterLocalVariableValue++) {
            sbLocalVariableValue.append(charsLocalVariableValue.charAt(rndLocalVariableValue.nextInt(charsLocalVariableValue.length())));
        }
        return sbLocalVariableValue.toString();
    }
}
