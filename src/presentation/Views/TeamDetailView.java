package presentation.Views;

import bussines.objects.Player;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Representa la vista del equipo detalle.
 *
 * Refactorizada para alinearse con el resto de vistas del proyecto:
 *  - Fondo con imagen y overlay oscuro (alpha 35).
 *  - Título grande "TEAM DETAILS" con acento + subrayado + subtítulo.
 *  - Card blanca redondeada con la tabla de jugadores dentro.
 *  - Botón BACK en la esquina superior izquierda.
 *
 * La API pública (constantes BACK/CONFIG, loadTeam, registerController)
 * se mantiene para no romper el controller.
 */
public class TeamDetailView extends JPanel {

    /**
     * Constante para el vuelta.
     */
    public static final String BACK = "BACK";

    /**
     * Constante para el configuracion.
     */
    public static final String CONFIG = "CONFIG";

    private static final String[] COLUMN_NAMES = { "Name", "Email", "DNI", "Number", "Phone" };

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);

    private static final Color CARD_TITLE_COLOR = new Color(28, 35, 51);
    private static final Color CARD_BODY_COLOR = new Color(100, 112, 135);
    private static final Color FIELD_BORDER = new Color(190, 210, 235);

    private static final Color TABLE_HEADER_BG = new Color(34, 64, 110);
    private static final Color TABLE_HEADER_FG = Color.WHITE;
    private static final Color TABLE_ROW_ALT = new Color(245, 249, 255);
    private static final Color TABLE_ROW_BASE = Color.WHITE;
    private static final Color TABLE_GRID = new Color(220, 230, 245);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    private static final String TEAM_ICON_PATH =
            ProjectPathResolver.resolveProjectPath("photos/create_team.png");

    private JLabel teamTitleLabelFieldReference;
    private JTable playersTableFieldReference;
    private DefaultTableModel tableModelFieldReference;
    private Rounded.RoundedButton backButtonFieldReference;
    private JButton configButtonFieldReference;


    /**
     * Crea una instancia de el equipo detalle.
     */
    public TeamDetailView() {
        setLayout(new BorderLayout());

        // configButton "phantom": el controller sigue registrando CONFIG
        // pero la rueda real la pinta el widget global.
        configButtonFieldReference = new JButton();
        configButtonFieldReference.setVisible(false);

        add(buildBackgroundPanel(), BorderLayout.CENTER);
    }


    // ────────────────────────────────────────────────────────────
    //  Construcción visual
    // ────────────────────────────────────────────────────────────

    private JPanel buildBackgroundPanel() {
        Image backgroundImageLocalVariableValue =
                new ImageIcon(BACKGROUND_IMAGE_PATH).getImage();

        JPanel backgroundPanelLocalVariableValue = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                super.paintComponent(graphicsParameterValue);
                graphicsParameterValue.drawImage(
                        backgroundImageLocalVariableValue,
                        0, 0, getWidth(), getHeight(), this
                );
                Graphics2D g2LocalVariableValue =
                        (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setColor(new Color(0, 0, 0, 35));
                g2LocalVariableValue.fillRect(0, 0, getWidth(), getHeight());
                g2LocalVariableValue.dispose();
            }
        };

        backgroundPanelLocalVariableValue.setLayout(new BorderLayout());
        backgroundPanelLocalVariableValue.add(buildTopBar(), BorderLayout.NORTH);
        backgroundPanelLocalVariableValue.add(buildCenterContent(), BorderLayout.CENTER);

        return backgroundPanelLocalVariableValue;
    }


    private JPanel buildTopBar() {
        JPanel topBarLocalVariableValue = new JPanel(new BorderLayout());
        topBarLocalVariableValue.setOpaque(false);
        topBarLocalVariableValue.setBorder(new EmptyBorder(28, 40, 0, 40));

        JPanel leftPanelLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanelLocalVariableValue.setOpaque(false);

        backButtonFieldReference = new Rounded.RoundedButton("< BACK", 18);
        backButtonFieldReference.setActionCommand(BACK);
        backButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 15));
        backButtonFieldReference.setForeground(ACCENT_COLOR);
        backButtonFieldReference.setBackground(new Color(255, 255, 255, 230));
        backButtonFieldReference.setOutlineMode(ACCENT_COLOR, 2);
        backButtonFieldReference.setShadowEnabled(false);
        backButtonFieldReference.setPreferredSize(new Dimension(145, 44));

        leftPanelLocalVariableValue.add(backButtonFieldReference);
        topBarLocalVariableValue.add(leftPanelLocalVariableValue, BorderLayout.WEST);

        return topBarLocalVariableValue;
    }


    private JPanel buildCenterContent() {
        JPanel centerWrapperLocalVariableValue = new JPanel(new GridBagLayout());
        centerWrapperLocalVariableValue.setOpaque(false);

        JPanel contentPanelLocalVariableValue = new JPanel();
        contentPanelLocalVariableValue.setOpaque(false);
        contentPanelLocalVariableValue.setLayout(
                new BoxLayout(contentPanelLocalVariableValue, BoxLayout.Y_AXIS)
        );
        contentPanelLocalVariableValue.setBorder(new EmptyBorder(0, 50, 16, 50));

        JPanel titleBlockLocalVariableValue = buildTitleBlock();
        JPanel cardLocalVariableValue = buildContentCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(28));
        contentPanelLocalVariableValue.add(cardLocalVariableValue);

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.weighty = 1.0;
        constraintsLocalVariableValue.anchor = GridBagConstraints.CENTER;

        centerWrapperLocalVariableValue.add(
                contentPanelLocalVariableValue,
                constraintsLocalVariableValue
        );

        return centerWrapperLocalVariableValue;
    }


    private JPanel buildTitleBlock() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeightLocalVariableValue = screenSizeLocalVariableValue.height;
        int mainTitleSizeLocalVariableValue =
                Math.max(58, (int) (screenHeightLocalVariableValue * 0.075));
        int subtitleSizeLocalVariableValue =
                Math.max(20, (int) (screenHeightLocalVariableValue * 0.025));

        JPanel titleContainerLocalVariableValue = new JPanel();
        titleContainerLocalVariableValue.setOpaque(false);
        titleContainerLocalVariableValue.setLayout(
                new BoxLayout(titleContainerLocalVariableValue, BoxLayout.Y_AXIS)
        );

        JPanel titleLineLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLineLocalVariableValue.setOpaque(false);

        JLabel teamLabelLocalVariableValue = new JLabel("TEAM ");
        teamLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        teamLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        JLabel detailsLabelLocalVariableValue = new JLabel("DETAILS");
        detailsLabelLocalVariableValue.setForeground(TITLE_WHITE);
        detailsLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        titleLineLocalVariableValue.add(teamLabelLocalVariableValue);
        titleLineLocalVariableValue.add(detailsLabelLocalVariableValue);

        JPanel underlineWrapperLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        underlineWrapperLocalVariableValue.setOpaque(false);

        JPanel underlinePanelLocalVariableValue = new JPanel();
        underlinePanelLocalVariableValue.setBackground(ACCENT_COLOR);
        underlinePanelLocalVariableValue.setPreferredSize(new Dimension(170, 6));
        underlinePanelLocalVariableValue.setMinimumSize(new Dimension(170, 6));
        underlinePanelLocalVariableValue.setMaximumSize(new Dimension(170, 6));

        underlineWrapperLocalVariableValue.add(underlinePanelLocalVariableValue);

        JLabel subtitleLabelLocalVariableValue = new JLabel(
                "Squad list, contact info and jersey numbers",
                SwingConstants.CENTER
        );
        subtitleLabelLocalVariableValue.setForeground(SUBTITLE_WHITE);
        subtitleLabelLocalVariableValue.setFont(
                new Font("Arial", Font.PLAIN, subtitleSizeLocalVariableValue)
        );
        subtitleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabelLocalVariableValue.setBorder(new EmptyBorder(18, 0, 0, 0));

        titleContainerLocalVariableValue.add(titleLineLocalVariableValue);
        titleContainerLocalVariableValue.add(Box.createVerticalStrut(12));
        titleContainerLocalVariableValue.add(underlineWrapperLocalVariableValue);
        titleContainerLocalVariableValue.add(subtitleLabelLocalVariableValue);

        return titleContainerLocalVariableValue;
    }


    /**
     * Card central blanca con: icono del equipo, nombre del equipo y
     * tabla de jugadores.
     */
    private JPanel buildContentCard() {
        JPanel cardLocalVariableValue = new RoundedCardPanel();
        cardLocalVariableValue.setOpaque(false);
        cardLocalVariableValue.setLayout(
                new BoxLayout(cardLocalVariableValue, BoxLayout.Y_AXIS)
        );
        cardLocalVariableValue.setBorder(new EmptyBorder(22, 32, 26, 32));

        cardLocalVariableValue.setPreferredSize(new Dimension(960, 560));
        cardLocalVariableValue.setMinimumSize(new Dimension(960, 560));
        cardLocalVariableValue.setMaximumSize(new Dimension(960, 560));

        JPanel iconPanelLocalVariableValue = buildTeamIconPanel();
        iconPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        teamTitleLabelFieldReference = new JLabel(
                "No team selected",
                SwingConstants.CENTER
        );
        teamTitleLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 22));
        teamTitleLabelFieldReference.setForeground(CARD_TITLE_COLOR);
        teamTitleLabelFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel helperLabelLocalVariableValue = new JLabel(
                "Players currently on the squad",
                SwingConstants.CENTER
        );
        helperLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 14));
        helperLabelLocalVariableValue.setForeground(CARD_BODY_COLOR);
        helperLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComponent tablePanelLocalVariableValue = buildPlayersTablePanel();
        tablePanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardLocalVariableValue.add(iconPanelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(6));
        cardLocalVariableValue.add(teamTitleLabelFieldReference);
        cardLocalVariableValue.add(Box.createVerticalStrut(6));
        cardLocalVariableValue.add(helperLabelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(18));
        cardLocalVariableValue.add(tablePanelLocalVariableValue);

        return cardLocalVariableValue;
    }


    private JPanel buildTeamIconPanel() {
        JPanel iconPanelLocalVariableValue = new JPanel(new GridBagLayout());
        iconPanelLocalVariableValue.setOpaque(false);
        iconPanelLocalVariableValue.setPreferredSize(new Dimension(96, 96));
        iconPanelLocalVariableValue.setMaximumSize(new Dimension(96, 96));
        iconPanelLocalVariableValue.setMinimumSize(new Dimension(96, 96));

        JLabel iconLabelLocalVariableValue = new JLabel();
        iconLabelLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabelLocalVariableValue.setVerticalAlignment(SwingConstants.CENTER);

        try {
            Image rawImageLocalVariableValue =
                    new ImageIcon(TEAM_ICON_PATH).getImage();
            Image scaledImageLocalVariableValue =
                    rawImageLocalVariableValue.getScaledInstance(86, 86, Image.SCALE_SMOOTH);
            iconLabelLocalVariableValue.setIcon(new ImageIcon(scaledImageLocalVariableValue));
        } catch (Exception ignoredExceptionParameterValue) {
            iconLabelLocalVariableValue.setText("T");
            iconLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 60));
            iconLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        }

        iconPanelLocalVariableValue.add(iconLabelLocalVariableValue);
        return iconPanelLocalVariableValue;
    }


    /**
     * Construye la tabla de jugadores con cabecera azul oscuro, filas
     * alternas y scroll vertical cuando hay muchos jugadores.
     */
    private JComponent buildPlayersTablePanel() {
        tableModelFieldReference = new DefaultTableModel(
                new Object[0][COLUMN_NAMES.length],
                COLUMN_NAMES
        ) {
            @Override
            public boolean isCellEditable(int rowParameterValue, int columnParameterValue) {
                return false;
            }
        };

        playersTableFieldReference = new JTable(tableModelFieldReference) {
            @Override
            public Component prepareRenderer(TableCellRenderer rendererParameter,
                                             int rowParameterValue,
                                             int columnParameterValue) {
                Component componentLocalVariableValue =
                        super.prepareRenderer(rendererParameter,
                                rowParameterValue, columnParameterValue);
                if (!isRowSelected(rowParameterValue)) {
                    componentLocalVariableValue.setBackground(
                            rowParameterValue % 2 == 0 ? TABLE_ROW_BASE : TABLE_ROW_ALT
                    );
                    componentLocalVariableValue.setForeground(CARD_TITLE_COLOR);
                } else {
                    componentLocalVariableValue.setBackground(new Color(220, 232, 252));
                    componentLocalVariableValue.setForeground(CARD_TITLE_COLOR);
                }
                return componentLocalVariableValue;
            }
        };
        playersTableFieldReference.setFont(new Font("Arial", Font.PLAIN, 14));
        playersTableFieldReference.setRowHeight(32);
        playersTableFieldReference.setShowGrid(true);
        playersTableFieldReference.setGridColor(TABLE_GRID);
        playersTableFieldReference.setAutoCreateRowSorter(false);
        playersTableFieldReference.setFillsViewportHeight(true);
        playersTableFieldReference.setSelectionBackground(new Color(220, 232, 252));
        playersTableFieldReference.setSelectionForeground(CARD_TITLE_COLOR);

        JTableHeader headerLocalVariableValue = playersTableFieldReference.getTableHeader();
        headerLocalVariableValue.setReorderingAllowed(false);
        headerLocalVariableValue.setResizingAllowed(false);
        headerLocalVariableValue.setPreferredSize(new Dimension(0, 42));
        headerLocalVariableValue.setDefaultRenderer(buildHeaderRenderer());

        applyTableRenderers();

        JScrollPane scrollPaneLocalVariableValue = new JScrollPane(playersTableFieldReference);
        scrollPaneLocalVariableValue.setBorder(BorderFactory.createLineBorder(FIELD_BORDER, 1));
        scrollPaneLocalVariableValue.getViewport().setBackground(TABLE_ROW_BASE);

        scrollPaneLocalVariableValue.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneLocalVariableValue.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scrollPaneLocalVariableValue.getVerticalScrollBar().setUnitIncrement(16);
        scrollPaneLocalVariableValue.getVerticalScrollBar()
                .setPreferredSize(new Dimension(10, 0));

        scrollPaneLocalVariableValue.setPreferredSize(new Dimension(880, 290));
        scrollPaneLocalVariableValue.setMinimumSize(new Dimension(880, 290));
        scrollPaneLocalVariableValue.setMaximumSize(new Dimension(880, 290));

        return scrollPaneLocalVariableValue;
    }


    /**
     * Renderer custom de la cabecera: fondo azul oscuro sólido, texto
     * blanco en negrita centrado y línea inferior de acento.
     */
    private TableCellRenderer buildHeaderRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tableParameterValue,
                                                           Object valueParameterValue,
                                                           boolean isSelectedParameterValue,
                                                           boolean hasFocusParameterValue,
                                                           int rowParameterValue,
                                                           int columnParameterValue) {
                JLabel labelLocalVariableValue = (JLabel) super.getTableCellRendererComponent(
                        tableParameterValue,
                        valueParameterValue,
                        isSelectedParameterValue,
                        hasFocusParameterValue,
                        rowParameterValue,
                        columnParameterValue
                );
                labelLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
                labelLocalVariableValue.setBackground(TABLE_HEADER_BG);
                labelLocalVariableValue.setForeground(TABLE_HEADER_FG);
                labelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 14));
                labelLocalVariableValue.setOpaque(true);
                labelLocalVariableValue.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 3, 0, ACCENT_COLOR),
                        BorderFactory.createEmptyBorder(0, 8, 0, 8)
                ));
                return labelLocalVariableValue;
            }
        };
    }


    /**
     * Aplica el renderer centrado a todas las celdas y reparte anchos
     * razonables por columna. Se vuelve a llamar tras cada setRowCount.
     */
    private void applyTableRenderers() {
        DefaultTableCellRenderer cellRendererLocalVariableValue = new DefaultTableCellRenderer();
        cellRendererLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);

        // Name · Email · DNI · Number · Phone
        int[] columnWidthsLocalVariableValue = { 180, 260, 130, 80, 150 };

        for (int columnIndexLocalVariableValue = 0;
             columnIndexLocalVariableValue < playersTableFieldReference.getColumnCount();
             columnIndexLocalVariableValue++) {
            javax.swing.table.TableColumn columnLocalVariableValue =
                    playersTableFieldReference.getColumnModel()
                            .getColumn(columnIndexLocalVariableValue);
            columnLocalVariableValue.setCellRenderer(cellRendererLocalVariableValue);
            if (columnIndexLocalVariableValue < columnWidthsLocalVariableValue.length) {
                columnLocalVariableValue.setPreferredWidth(
                        columnWidthsLocalVariableValue[columnIndexLocalVariableValue]
                );
            }
        }
    }


    // ────────────────────────────────────────────────────────────
    //  API pública (sin cambios respecto a la versión anterior)
    // ────────────────────────────────────────────────────────────

    /**
     * Carga el equipo.
     *
     * @param teamReferenceDisplayNameParameterValue nombre del equipo.
     * @param playersParameterValue jugadores que usa la operacion.
     */
    public void loadTeam(String teamReferenceDisplayNameParameterValue,
                         ArrayList<Player> playersParameterValue) {
        if (teamReferenceDisplayNameParameterValue == null
                || teamReferenceDisplayNameParameterValue.isEmpty()) {
            teamTitleLabelFieldReference.setText("No team selected");
        } else {
            teamTitleLabelFieldReference.setText(teamReferenceDisplayNameParameterValue);
        }

        tableModelFieldReference.setRowCount(0);
        List<Player> playersLocalVariableValue =
                playersParameterValue == null ? new ArrayList<>() : playersParameterValue;

        for (Player playerProfileLocalVariableValue : playersLocalVariableValue) {
            tableModelFieldReference.addRow(new Object[] {
                    playerProfileLocalVariableValue.getName(),
                    playerProfileLocalVariableValue.getEmail(),
                    playerProfileLocalVariableValue.getDni(),
                    playerProfileLocalVariableValue.getNumber(),
                    playerProfileLocalVariableValue.getPhoneNumber()
            });
        }

        // Tras setRowCount/addRow las columnas se reaplican los renderers.
        applyTableRenderers();
    }


    /**
     * Registra la accion.
     *
     * @param controllerHandlerParameterValue dato de entrada de la operacion.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        backButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        backButtonFieldReference.setActionCommand(BACK);
        // configButton es phantom (no visible). Se mantiene el registro
        // por compatibilidad con el controller existente.
        configButtonFieldReference.addActionListener(controllerHandlerParameterValue);
        configButtonFieldReference.setActionCommand(CONFIG);
    }


    // ────────────────────────────────────────────────────────────
    //  Card redondeada con sombra y borde de acento (mismo estilo
    //  visual que el resto de cards del proyecto).
    // ────────────────────────────────────────────────────────────

    private static class RoundedCardPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics graphicsParameterValue) {
            Graphics2D g2LocalVariableValue =
                    (Graphics2D) graphicsParameterValue.create();
            g2LocalVariableValue.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int widthLocalVariableValue = getWidth();
            int heightLocalVariableValue = getHeight();
            int radiusLocalVariableValue = 22;

            // Sombra suave
            g2LocalVariableValue.setColor(new Color(8, 20, 46, 42));
            g2LocalVariableValue.fillRoundRect(
                    8, 10,
                    widthLocalVariableValue - 16,
                    heightLocalVariableValue - 10,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            // Cuerpo blanco
            g2LocalVariableValue.setColor(new Color(255, 255, 255, 250));
            g2LocalVariableValue.fillRoundRect(
                    0, 0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 4,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            // Borde de acento
            g2LocalVariableValue.setColor(ACCENT_COLOR);
            g2LocalVariableValue.setStroke(new BasicStroke(2f));
            g2LocalVariableValue.drawRoundRect(
                    0, 0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 5,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }
}
