package presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Consumer;

/**
 * Vista de interfaz gráfica para crear un nuevo equipo a partir de archivos JSON.
 * Permite visualizar archivos existentes, subir nuevos y continuar con la validación.
 */
public class CreateTeamView extends JFrame {
    private static final Color DARK_BLUE = new Color(22, 49, 72);
    private static final Color LIGHT_BLUE = new Color(195, 216, 236);
    private static final Color BACKGROUND = new Color(240, 240, 240);
    private static final String TEAMS_DIR = "data/teams";

    private JButton backButtonFieldReference;
    private JButton configButtonFieldReference;
    private JButton validateButtonFieldReference;
    private ActionListener backButtonListenerFieldReference;
    private ActionListener configControllerHandlerFieldReference;
    private ActionListener validateListenerFieldReference;
    private Consumer<File> fileUploadListenerFieldReference;
    private JPanel filesPanelFieldReference;

    /**
     * Constructor que inicializa y configura la interfaz de creación de nuevo equipo.
     */
    public CreateTeamView() {
        setTitle("Create New Team");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanelLocalVariableValue = new JPanel(new BorderLayout());
        mainPanelLocalVariableValue.setBackground(BACKGROUND);

        JPanel titlePanelLocalVariableValue = createTitlePanel();
        JScrollPane filesScrollPaneLocalVariableValue = createFilesScrollPane();

        // Botón para cargar nuevo archivo
        JButton uploadButtonLocalVariableValue = new JButton("Load new File");
        uploadButtonLocalVariableValue.addActionListener(eventArgumentParameterValue -> openFileChooser());
        uploadButtonLocalVariableValue.setBackground(Color.ORANGE);
        uploadButtonLocalVariableValue.setForeground(Color.black);
        uploadButtonLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 14));

        // Botón para validar y continuar
        validateButtonFieldReference = new JButton("Validate and continue");
        validateButtonFieldReference.setBackground(new Color(0, 153, 0));
        validateButtonFieldReference.setForeground(Color.WHITE);
        validateButtonFieldReference.addActionListener(eventArgumentParameterValue2 -> {
            if (validateListenerFieldReference != null) {
                validateListenerFieldReference.actionPerformed(eventArgumentParameterValue2);
            }
        });
        validateButtonFieldReference.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel bottomPanelLocalVariableValue = new JPanel(new GridLayout(2, 1, 10, 10));
        bottomPanelLocalVariableValue.setBackground(BACKGROUND);
        bottomPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        bottomPanelLocalVariableValue.add(validateButtonFieldReference);
        bottomPanelLocalVariableValue.add(uploadButtonLocalVariableValue);

        mainPanelLocalVariableValue.add(titlePanelLocalVariableValue, BorderLayout.NORTH);
        mainPanelLocalVariableValue.add(filesScrollPaneLocalVariableValue, BorderLayout.CENTER);
        mainPanelLocalVariableValue.add(bottomPanelLocalVariableValue, BorderLayout.SOUTH);

        add(mainPanelLocalVariableValue);
    }

    /**
     * Crea el panel del encabezado con botones de navegación y configuración.
     * @return JPanel del encabezado.
     */
    private JPanel createTitlePanel() {
        JPanel panelLocalVariableValue = new JPanel(new BorderLayout());
        panelLocalVariableValue.setBackground(DARK_BLUE);
        panelLocalVariableValue.setPreferredSize(new Dimension(getWidth(), 60));

        backButtonFieldReference = Rounded.HeaderButtonHelper.createBackButton(eventArgumentParameterValue3 -> {
            if (backButtonListenerFieldReference != null) backButtonListenerFieldReference.actionPerformed(eventArgumentParameterValue3);
        });

        configButtonFieldReference = Rounded.HeaderButtonHelper.createConfigButton(eventArgumentParameterValue4 -> { //inhabilitamos
           // if (configController != null) configController.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "CONFIG"));
        });

        JLabel titleLocalVariableValue = new JLabel("CREATE NEW TEAM", SwingConstants.CENTER);
        titleLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 28));
        titleLocalVariableValue.setForeground(Color.WHITE);
        titleLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        panelLocalVariableValue.add(backButtonFieldReference, BorderLayout.WEST);
        panelLocalVariableValue.add(titleLocalVariableValue, BorderLayout.CENTER);
        panelLocalVariableValue.add(configButtonFieldReference, BorderLayout.EAST);
        return panelLocalVariableValue;
    }

    /**
     * Crea el panel con scroll que lista los archivos JSON disponibles.
     * @return JScrollPane con los archivos listados.
     */
    private JScrollPane createFilesScrollPane() {
        JPanel containerLocalVariableValue = new JPanel(new BorderLayout());
        containerLocalVariableValue.setBackground(BACKGROUND);

        File teamsDirLocalVariableValue = new File(TEAMS_DIR);
        if (!teamsDirLocalVariableValue.exists()) teamsDirLocalVariableValue.mkdirs();

        filesPanelFieldReference = new JPanel();
        filesPanelFieldReference.setLayout(new BoxLayout(filesPanelFieldReference, BoxLayout.Y_AXIS));
        filesPanelFieldReference.setBackground(LIGHT_BLUE);
        filesPanelFieldReference.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        refreshFileList();

        JPanel blueContainerLocalVariableValue = new JPanel(new BorderLayout());
        blueContainerLocalVariableValue.setBackground(LIGHT_BLUE);
        blueContainerLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        blueContainerLocalVariableValue.add(filesPanelFieldReference, BorderLayout.NORTH);

        containerLocalVariableValue.add(blueContainerLocalVariableValue, BorderLayout.CENTER);
        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(containerLocalVariableValue);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        scrollPaneLocalVariableValue.getViewport().setBackground(BACKGROUND);

        return scrollPaneLocalVariableValue;
    }

    /**
     * Abre un selector de archivos para cargar un archivo JSON.
     */
    private void openFileChooser() {
        JFileChooser fileChooserLocalVariableValue = new JFileChooser();
        fileChooserLocalVariableValue.setDialogTitle("Seleccionar archivo JSON");
        fileChooserLocalVariableValue.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooserLocalVariableValue.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON files", "json"));

        int resultLocalVariableValue = fileChooserLocalVariableValue.showOpenDialog(this);
        if (resultLocalVariableValue == JFileChooser.APPROVE_OPTION && fileUploadListenerFieldReference != null) {
            fileUploadListenerFieldReference.accept(fileChooserLocalVariableValue.getSelectedFile());
        }
    }

    /**
     * Refresca la lista de archivos JSON disponibles en el directorio de equipos.
     */
    public void refreshFileList() {
        filesPanelFieldReference.removeAll();

        JLabel headerLabelLocalVariableValue = new JLabel("CHOOSE FILE", SwingConstants.CENTER);
        headerLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabelLocalVariableValue.setForeground(Color.WHITE);
        JPanel headerPanelLocalVariableValue = new JPanel(new BorderLayout());
        headerPanelLocalVariableValue.setBackground(DARK_BLUE);
        headerPanelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        headerPanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        headerPanelLocalVariableValue.add(headerLabelLocalVariableValue, BorderLayout.CENTER);
        filesPanelFieldReference.add(headerPanelLocalVariableValue);

        File[] filesLocalVariableValue = new File(TEAMS_DIR).listFiles();
        if (filesLocalVariableValue != null) {
            for (File fileLocalVariableValue : filesLocalVariableValue) {
                if (fileLocalVariableValue.isFile() && fileLocalVariableValue.getName().toLowerCase().endsWith(".json")) {
                    JPanel filePanelLocalVariableValue = new JPanel(new BorderLayout());
                    filePanelLocalVariableValue.setBackground(Color.WHITE);
                    filePanelLocalVariableValue.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
                    filePanelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

                    JLabel labelLocalVariableValue = new JLabel(fileLocalVariableValue.getName());
                    labelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 16));
                    filePanelLocalVariableValue.add(labelLocalVariableValue, BorderLayout.WEST);

                    filesPanelFieldReference.add(Box.createVerticalStrut(2));
                    filesPanelFieldReference.add(filePanelLocalVariableValue);
                }
            }
        }
        filesPanelFieldReference.revalidate();
        filesPanelFieldReference.repaint();
    }

    /**
     * Establece el controlador del botón de retroceso.
     * @param listener ActionListener a ejecutar.
     */
    public void setBackButtonListener(ActionListener listenerParameterValue) { this.backButtonListenerFieldReference = listenerParameterValue; }

    /**
     * Establece el controlador del botón de configuración.
     * @param listener ActionListener a ejecutar.
     */
    public void setConfigController(ActionListener listenerParameterValue2) { this.configControllerHandlerFieldReference = listenerParameterValue2; }

    /**
     * Establece el controlador del botón de validación.
     * @param listener ActionListener a ejecutar.
     */
    public void setValidateListener(ActionListener listenerParameterValue3) { this.validateListenerFieldReference = listenerParameterValue3; }

    /**
     * Establece el consumidor de archivos cargados desde el sistema.
     * @param listener Consumer que acepta un archivo seleccionado.
     */
    public void setFileUploadListener(Consumer<File> listenerParameterValue4) { this.fileUploadListenerFieldReference = listenerParameterValue4; }
}