package presentation.Views;

import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Vista del menú principal del jugador.
 *
 * Mantiene el lenguaje gráfico de Login/SignUp (fondo + título +
 * subtítulo) pero cambia la zona central por una fila de "menu cards"
 * con icono circular, título, barra de acento y descripción —
 * inspirada en el mockup proporcionado por el usuario.
 *
 * La API pública (constantes y métodos) permanece intacta:
 *   - registerController(ActionListener)
 *   - showMessageDialog(String)
 *   - setConfigController(ActionListener)
 *   - constantes WATCH_MATCHES, VIEW_LEAGUES, DELETE_PLAYER, LOGOUT, CONFIG.
 */
public class PlayerMenuView extends JPanel {

    // Comandos de acción para los botones
    public static final String WATCH_MATCHES = "WATCH_MATCHES";
    public static final String VIEW_LEAGUES = "VIEW_LEAGUES";
    public static final String DELETE_PLAYER = "DELETE_PLAYER";
    public static final String LOGOUT = "LOGOUT";
    public static final String CONFIG = "CONFIG";

    // Cards principales de la vista
    private MenuCardPanel watchMatchesCardFieldReference;
    private MenuCardPanel viewLeaguesCardFieldReference;
    private MenuCardPanel deleteAccountCardFieldReference;
    private MenuCardPanel logoutCardFieldReference;

    private JButton configButtonFieldReference;
    private ActionListener configControllerHandlerFieldReference;

    // Paleta de los títulos / textos (heredada de Login/SignUp).
    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);

    // Paleta de cada card (icono + barra + footer-pill).
    private static final Color CARD_BLUE = new Color(46, 95, 215);
    private static final Color CARD_GREEN = new Color(38, 166, 91);
    private static final Color CARD_ORANGE = new Color(245, 137, 47);
    private static final Color CARD_PURPLE = new Color(110, 70, 220);

    // Texto interno de las cards.
    private static final Color CARD_TITLE_COLOR = new Color(28, 35, 51);
    private static final Color CARD_BODY_COLOR = new Color(110, 122, 142);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    public PlayerMenuView() {
        setLayout(new BorderLayout());
        buildCards();
        add(buildBackgroundPanel(), BorderLayout.CENTER);
    }

    /**
     * Crea las cards y les asigna los iconos PNG correspondientes
     * desde la carpeta photos/.
     */
    private void buildCards() {
        watchMatchesCardFieldReference = new MenuCardPanel(
                "LIVE MATCHES",
                "Watch live matches and enjoy real-time action",
                CARD_BLUE,
                WATCH_MATCHES
        );
        watchMatchesCardFieldReference.setIcon(loadCardIcon("photos/Partidos_Live.png"));

        viewLeaguesCardFieldReference = new MenuCardPanel(
                "LEAGUES",
                "Browse and explore the leagues you take part in",
                CARD_GREEN,
                VIEW_LEAGUES
        );
        viewLeaguesCardFieldReference.setIcon(loadCardIcon("photos/Leagues.png"));

        deleteAccountCardFieldReference = new MenuCardPanel(
                "DELETE ACCOUNT",
                "Permanently delete your account and all your data",
                CARD_ORANGE,
                DELETE_PLAYER
        );
        deleteAccountCardFieldReference.setIcon(loadCardIcon("photos/Eliminar_Cuenta.png"));

        logoutCardFieldReference = new MenuCardPanel(
                "LOGOUT",
                "Logout from your account and sign in again",
                CARD_PURPLE,
                LOGOUT
        );
        logoutCardFieldReference.setIcon(loadCardIcon("photos/Salir_Meu_Principal.png"));

        configButtonFieldReference = buildConfigButton();
    }

    /**
     * Construye el botón de configuración con la imagen
     * "Rueda Ajustes.png" en lugar del icono por defecto del
     * HeaderButtonHelper, y sin el fondo redondeado para que se vea
     * directamente la rueda dentada.
     */
    private JButton buildConfigButton() {
        JButton buttonControlLocalVariableValue = new JButton();
        buttonControlLocalVariableValue.setBorder(BorderFactory.createEmptyBorder());
        buttonControlLocalVariableValue.setContentAreaFilled(false);
        buttonControlLocalVariableValue.setFocusPainted(false);
        buttonControlLocalVariableValue.setOpaque(false);
        buttonControlLocalVariableValue.setCursor(new Cursor(Cursor.HAND_CURSOR));

        try {
            String iconPathLocalVariableValue = resolveExistingIconPath(
                    "photos/Rueda_Ajustes.png",
                    "photos/Rueda Ajustes.png"
            );
            Image rawIconLocalVariableValue =
                    new ImageIcon(iconPathLocalVariableValue).getImage();
            Image scaledIconLocalVariableValue =
                    rawIconLocalVariableValue.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            buttonControlLocalVariableValue.setIcon(new ImageIcon(scaledIconLocalVariableValue));

            buttonControlLocalVariableValue.setPreferredSize(new Dimension(170, 170));
            buttonControlLocalVariableValue.setMinimumSize(new Dimension(170, 170));
            buttonControlLocalVariableValue.setMaximumSize(new Dimension(170, 170));
        } catch (Exception ignoredExceptionParameterValue) {
            // Si la imagen no se puede cargar, queda un botón vacío.
        }

        buttonControlLocalVariableValue.addActionListener(
                eventArgumentParameterValue -> {
                    if (configControllerHandlerFieldReference != null) {
                        configControllerHandlerFieldReference.actionPerformed(
                                new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CONFIG)
                        );
                    }
                }
        );

        return buttonControlLocalVariableValue;
    }

    public void updateLiveMatches(List<String[]> liveGamesParameterValue) {
        List<String[]> liveGamesLocalVariableValue =
                liveGamesParameterValue == null ? new ArrayList<>() : liveGamesParameterValue;

        if (liveGamesLocalVariableValue.isEmpty()) {
            watchMatchesCardFieldReference.setDescriptionHtml(
                    "No hay partidos en directo actualmente."
            );
            return;
        }

        StringBuilder htmlBuilderLocalVariableValue = new StringBuilder();

        int maxRowsLocalVariableValue = Math.min(3, liveGamesLocalVariableValue.size());

        for (int indexCounterLocalVariableValue = 0;
             indexCounterLocalVariableValue < maxRowsLocalVariableValue;
             indexCounterLocalVariableValue++) {

            String[] matchLocalVariableValue = liveGamesLocalVariableValue.get(indexCounterLocalVariableValue);

            String homeLocalVariableValue =
                    matchLocalVariableValue.length > 0 ? matchLocalVariableValue[0] : "Home";
            String awayLocalVariableValue =
                    matchLocalVariableValue.length > 1 ? matchLocalVariableValue[1] : "Away";

            htmlBuilderLocalVariableValue
                    .append("<b>LIVE</b> ")
                    .append(homeLocalVariableValue)
                    .append(" vs ")
                    .append(awayLocalVariableValue);

            if (indexCounterLocalVariableValue < maxRowsLocalVariableValue - 1) {
                htmlBuilderLocalVariableValue.append("<br>");
            }
        }

        if (liveGamesLocalVariableValue.size() > maxRowsLocalVariableValue) {
            htmlBuilderLocalVariableValue
                    .append("<br>+")
                    .append(liveGamesLocalVariableValue.size() - maxRowsLocalVariableValue)
                    .append(" more");
        }

        watchMatchesCardFieldReference.setDescriptionHtml(htmlBuilderLocalVariableValue.toString());
    }



    /**
     * Carga un icono PNG de la carpeta photos/ y lo escala al tamaño
     * adecuado para mostrarlo en cada card (sin círculo de fondo).
     */
    private Icon loadCardIcon(String relativePathParameterValue) {
        try {
            String absolutePathLocalVariableValue =
                    ProjectPathResolver.resolveProjectPath(relativePathParameterValue);
            Image rawImageLocalVariableValue =
                    new ImageIcon(absolutePathLocalVariableValue).getImage();
            Image scaledImageLocalVariableValue =
                    rawImageLocalVariableValue.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImageLocalVariableValue);
        } catch (Exception ignoredExceptionParameterValue) {
            return null;
        }
    }


    private String resolveExistingIconPath(String primaryRelativePathParameterValue,
                                           String fallbackRelativePathParameterValue) {
        String primaryAbsolutePathLocalVariableValue =
                ProjectPathResolver.resolveProjectPath(primaryRelativePathParameterValue);

        if (new java.io.File(primaryAbsolutePathLocalVariableValue).exists()) {
            return primaryAbsolutePathLocalVariableValue;
        }

        return ProjectPathResolver.resolveProjectPath(fallbackRelativePathParameterValue);
    }

    /**
     * Panel de fondo que pinta la imagen + overlay y centra el bloque
     * de título y la fila de cards.
     */
    private JPanel buildBackgroundPanel() {
        Image backgroundImageLocalVariableValue = new ImageIcon(BACKGROUND_IMAGE_PATH).getImage();

        JPanel backgroundPanelLocalVariableValue = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                super.paintComponent(graphicsParameterValue);
                graphicsParameterValue.drawImage(
                        backgroundImageLocalVariableValue,
                        0, 0, getWidth(), getHeight(), this
                );

                Graphics2D g2LocalVariableValue = (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setColor(new Color(0, 0, 0, 35));
                g2LocalVariableValue.fillRect(0, 0, getWidth(), getHeight());
                g2LocalVariableValue.dispose();
            }
        };

        backgroundPanelLocalVariableValue.setLayout(new BorderLayout());

        // Barra inferior con el botón de configuración abajo a la izquierda
        JPanel bottomBarLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 10));
        bottomBarLocalVariableValue.setOpaque(false);
        bottomBarLocalVariableValue.setBorder(new EmptyBorder(0, 20, 20, 0));
        bottomBarLocalVariableValue.add(configButtonFieldReference);

        // Centro: bloque de título + fila de cards, todo centrado
        JPanel centerWrapperLocalVariableValue = new JPanel(new GridBagLayout());
        centerWrapperLocalVariableValue.setOpaque(false);

        JPanel contentPanelLocalVariableValue = new JPanel();
        contentPanelLocalVariableValue.setOpaque(false);
        contentPanelLocalVariableValue.setLayout(
                new BoxLayout(contentPanelLocalVariableValue, BoxLayout.Y_AXIS)
        );
        contentPanelLocalVariableValue.setBorder(new EmptyBorder(0, 60, 40, 60));

        JPanel titleBlockLocalVariableValue = buildTitleBlock();
        JPanel cardsRowLocalVariableValue = buildCardsRow();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardsRowLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(50));
        contentPanelLocalVariableValue.add(cardsRowLocalVariableValue);

        GridBagConstraints constraintsLocalVariableValue = new GridBagConstraints();
        constraintsLocalVariableValue.gridx = 0;
        constraintsLocalVariableValue.gridy = 0;
        constraintsLocalVariableValue.weightx = 1.0;
        constraintsLocalVariableValue.weighty = 1.0;
        constraintsLocalVariableValue.anchor = GridBagConstraints.CENTER;

        centerWrapperLocalVariableValue.add(contentPanelLocalVariableValue, constraintsLocalVariableValue);

        backgroundPanelLocalVariableValue.add(centerWrapperLocalVariableValue, BorderLayout.CENTER);
        backgroundPanelLocalVariableValue.add(bottomBarLocalVariableValue, BorderLayout.SOUTH);

        return backgroundPanelLocalVariableValue;
    }

    /**
     * Bloque de título "PLAYER MENU" con subrayado de acento.
     */
    private JPanel buildTitleBlock() {
        Dimension screenSizeLocalVariableValue = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeightLocalVariableValue = screenSizeLocalVariableValue.height;

        int mainTitleSizeLocalVariableValue = Math.max(58, (int) (screenHeightLocalVariableValue * 0.075));
        int subtitleSizeLocalVariableValue = Math.max(22, (int) (screenHeightLocalVariableValue * 0.028));

        JPanel titleContainerLocalVariableValue = new JPanel();
        titleContainerLocalVariableValue.setOpaque(false);
        titleContainerLocalVariableValue.setLayout(
                new BoxLayout(titleContainerLocalVariableValue, BoxLayout.Y_AXIS)
        );

        JPanel titleLineLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLineLocalVariableValue.setOpaque(false);

        JLabel playerLabelLocalVariableValue = new JLabel("PLAYER ");
        playerLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        playerLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue));

        JLabel menuLabelLocalVariableValue = new JLabel("MENU");
        menuLabelLocalVariableValue.setForeground(TITLE_WHITE);
        menuLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue));

        titleLineLocalVariableValue.add(playerLabelLocalVariableValue);
        titleLineLocalVariableValue.add(menuLabelLocalVariableValue);

        JPanel underlineWrapperLocalVariableValue = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        underlineWrapperLocalVariableValue.setOpaque(false);

        JPanel underlinePanelLocalVariableValue = new JPanel();
        underlinePanelLocalVariableValue.setBackground(ACCENT_COLOR);
        underlinePanelLocalVariableValue.setPreferredSize(new Dimension(190, 6));
        underlinePanelLocalVariableValue.setMinimumSize(new Dimension(190, 6));
        underlinePanelLocalVariableValue.setMaximumSize(new Dimension(190, 6));

        underlineWrapperLocalVariableValue.add(underlinePanelLocalVariableValue);

        JLabel subtitleLabelLocalVariableValue = new JLabel(
                "Choose an option to continue",
                SwingConstants.CENTER
        );
        subtitleLabelLocalVariableValue.setForeground(SUBTITLE_WHITE);
        subtitleLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, subtitleSizeLocalVariableValue));
        subtitleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabelLocalVariableValue.setBorder(new EmptyBorder(22, 0, 0, 0));

        titleContainerLocalVariableValue.add(titleLineLocalVariableValue);
        titleContainerLocalVariableValue.add(Box.createVerticalStrut(14));
        titleContainerLocalVariableValue.add(underlineWrapperLocalVariableValue);
        titleContainerLocalVariableValue.add(subtitleLabelLocalVariableValue);

        return titleContainerLocalVariableValue;
    }

    /**
     * Fila horizontal con las 4 cards: Live Matches, Leagues,
     * Delete Account, Logout. Cada una con su color de acento.
     */
    private JPanel buildCardsRow() {
        JPanel rowLocalVariableValue = new JPanel(new GridLayout(1, 4, 35, 0));
        rowLocalVariableValue.setOpaque(false);
        rowLocalVariableValue.add(watchMatchesCardFieldReference);
        rowLocalVariableValue.add(viewLeaguesCardFieldReference);
        rowLocalVariableValue.add(deleteAccountCardFieldReference);
        rowLocalVariableValue.add(logoutCardFieldReference);
        return rowLocalVariableValue;
    }

    public void registerController(ActionListener controllerHandlerParameterValue) {
        watchMatchesCardFieldReference.setActionListener(controllerHandlerParameterValue);
        viewLeaguesCardFieldReference.setActionListener(controllerHandlerParameterValue);
        deleteAccountCardFieldReference.setActionListener(controllerHandlerParameterValue);
        logoutCardFieldReference.setActionListener(controllerHandlerParameterValue);
    }

    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                messageParameterValue,
                "Player Menu",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public void setConfigController(ActionListener controllerHandlerParameterValue2) {
        this.configControllerHandlerFieldReference = controllerHandlerParameterValue2;
    }

    /**
     * Permite asignar más tarde el icono de cada card. Útil para que
     * el usuario añada los iconos PNG cuando los tenga sin tocar el
     * código del menú.
     */
    public void setWatchMatchesIcon(Icon iconParameterValue) {
        watchMatchesCardFieldReference.setIcon(iconParameterValue);
    }
    public void setViewLeaguesIcon(Icon iconParameterValue) {
        viewLeaguesCardFieldReference.setIcon(iconParameterValue);
    }
    public void setDeleteAccountIcon(Icon iconParameterValue) {
        deleteAccountCardFieldReference.setIcon(iconParameterValue);
    }
    public void setLogoutIcon(Icon iconParameterValue) {
        logoutCardFieldReference.setIcon(iconParameterValue);
    }

    // ============================================================
    //                    MenuCardPanel (interna)
    // ============================================================

    /**
     * Card visual con: círculo coloreado para el icono, título, barra
     * de acento, descripción y footer-pill al fondo. Funciona como
     * botón: al hacer clic dispara el {@link ActionListener} con el
     * actionCommand asignado.
     */
    private static class MenuCardPanel extends JPanel {

        private final String actionCommandFieldReference;
        private final Color accentColorFieldReference;

        private final JLabel iconHolderLabelFieldReference;
        private final JLabel titleLabelFieldReference;
        private final JLabel descriptionLabelFieldReference;

        private ActionListener listenerFieldReference;
        private boolean hoverFieldReference = false;


        public void setDescriptionHtml(String htmlContentParameterValue) {
            descriptionLabelFieldReference.setText(
                    "<html><div style='text-align:center;'>"
                            + htmlContentParameterValue
                            + "</div></html>"
            );
            descriptionLabelFieldReference.revalidate();
            descriptionLabelFieldReference.repaint();
        }

        MenuCardPanel(String titleParameterValue,
                      String descriptionParameterValue,
                      Color accentColorParameterValue,
                      String actionCommandParameterValue) {

            this.actionCommandFieldReference = actionCommandParameterValue;
            this.accentColorFieldReference = accentColorParameterValue;

            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new EmptyBorder(34, 28, 28, 28));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(290, 410));
            setMinimumSize(new Dimension(290, 410));
            setMaximumSize(new Dimension(290, 410));

            // Icono central de la card (sin círculo de fondo). Se ve
            // directamente la imagen PNG al tamaño que devuelve
            // loadCardIcon.
            iconHolderLabelFieldReference = new JLabel();
            iconHolderLabelFieldReference.setHorizontalAlignment(SwingConstants.CENTER);
            iconHolderLabelFieldReference.setVerticalAlignment(SwingConstants.CENTER);
            iconHolderLabelFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);
            iconHolderLabelFieldReference.setPreferredSize(new Dimension(200, 200));
            iconHolderLabelFieldReference.setMinimumSize(new Dimension(200, 200));
            iconHolderLabelFieldReference.setMaximumSize(new Dimension(200, 200));

            // Título
            titleLabelFieldReference = new JLabel(titleParameterValue, SwingConstants.CENTER);
            titleLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 26));
            titleLabelFieldReference.setForeground(CARD_TITLE_COLOR);
            titleLabelFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Barra de acento bajo el título
            JPanel titleAccentBarLocalVariableValue = new JPanel();
            titleAccentBarLocalVariableValue.setBackground(accentColorFieldReference);
            titleAccentBarLocalVariableValue.setPreferredSize(new Dimension(60, 4));
            titleAccentBarLocalVariableValue.setMaximumSize(new Dimension(60, 4));
            titleAccentBarLocalVariableValue.setMinimumSize(new Dimension(60, 4));
            titleAccentBarLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Descripción
            descriptionLabelFieldReference = new JLabel(
                    "<html><div style='text-align:center;'>"
                            + descriptionParameterValue
                            + "</div></html>",
                    SwingConstants.CENTER
            );
            descriptionLabelFieldReference.setFont(new Font("Arial", Font.PLAIN, 16));
            descriptionLabelFieldReference.setForeground(CARD_BODY_COLOR);
            descriptionLabelFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);

            add(iconHolderLabelFieldReference);
            add(Box.createVerticalStrut(20));
            add(titleLabelFieldReference);
            add(Box.createVerticalStrut(12));
            add(titleAccentBarLocalVariableValue);
            add(Box.createVerticalStrut(18));
            add(descriptionLabelFieldReference);
            add(Box.createVerticalGlue());

            // Click → dispara ActionListener
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent eventArgumentParameterValue) {
                    hoverFieldReference = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent eventArgumentParameterValue) {
                    hoverFieldReference = false;
                    repaint();
                }

                @Override
                public void mouseClicked(MouseEvent eventArgumentParameterValue) {
                    if (listenerFieldReference != null) {
                        listenerFieldReference.actionPerformed(new ActionEvent(
                                MenuCardPanel.this,
                                ActionEvent.ACTION_PERFORMED,
                                actionCommandFieldReference
                        ));
                    }
                }
            });

        }

        public void setActionListener(ActionListener listenerParameterValue) {
            this.listenerFieldReference = listenerParameterValue;
        }

        public void setIcon(Icon iconParameterValue) {
            iconHolderLabelFieldReference.setIcon(iconParameterValue);
            iconHolderLabelFieldReference.repaint();
        }

        @Override
        protected void paintComponent(Graphics graphicsParameterValue) {
            Graphics2D g2LocalVariableValue = (Graphics2D) graphicsParameterValue.create();
            g2LocalVariableValue.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int widthLocalVariableValue = getWidth();
            int heightLocalVariableValue = getHeight();
            int radiusLocalVariableValue = 22;

            // Sombra suave detrás de la card
            g2LocalVariableValue.setColor(new Color(8, 20, 46, 38));
            g2LocalVariableValue.fillRoundRect(
                    8, 14,
                    widthLocalVariableValue - 16,
                    heightLocalVariableValue - 18,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            // Cuerpo blanco (sin barra de color en el fondo).
            int alphaLocalVariableValue = hoverFieldReference ? 255 : 250;
            g2LocalVariableValue.setColor(new Color(255, 255, 255, alphaLocalVariableValue));
            g2LocalVariableValue.fillRoundRect(
                    0, 0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 16,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }
}
