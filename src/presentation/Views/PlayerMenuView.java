package presentation.Views;

import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Representa la vista del jugador menu.
 */
public class PlayerMenuView extends JPanel {


    /**
     * Constante para los partidos.
     */
    public static final String WATCH_MATCHES = "WATCH_MATCHES";
    /**
     * Constante para los vista ligas.
     */
    public static final String VIEW_LEAGUES = "VIEW_LEAGUES";
    /**
     * Constante para el jugador.
     */
    public static final String DELETE_PLAYER = "DELETE_PLAYER";
    /**
     * Constante para el valor.
     */
    public static final String LOGOUT = "LOGOUT";


    private MenuCardPanel viewLeaguesCardFieldReference;
    private MenuCardPanel deleteAccountCardFieldReference;
    private MenuCardPanel logoutCardFieldReference;


    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);


    private static final Color CARD_GREEN = new Color(38, 166, 91);
    private static final Color CARD_ORANGE = new Color(245, 137, 47);
    private static final Color CARD_PURPLE = new Color(110, 70, 220);


    private static final Color CARD_TITLE_COLOR = new Color(28, 35, 51);
    private static final Color CARD_BODY_COLOR = new Color(110, 122, 142);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");


    /**
     * Crea una instancia de el jugador menu.
     */
    public PlayerMenuView() {
        setLayout(new BorderLayout());
        buildCards();
        add(buildBackgroundPanel(), BorderLayout.CENTER);
    }


    /**
     * Construye el contenido.
     */
    private void buildCards() {
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
    }


    /**
     * Carga el contenido.
     *
     * @param relativePathParameterValue ruta que usa la operacion.
     * @return resultado de la operacion.
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


    /**
     * Construye el contenido.
     *
     * @return resultado de la operacion.
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


        JPanel centerWrapperLocalVariableValue = new JPanel(new GridBagLayout());
        centerWrapperLocalVariableValue.setOpaque(false);

        JPanel contentPanelLocalVariableValue = new JPanel();
        contentPanelLocalVariableValue.setOpaque(false);
        contentPanelLocalVariableValue.setLayout(
                new BoxLayout(contentPanelLocalVariableValue, BoxLayout.Y_AXIS)
        );
        contentPanelLocalVariableValue.setBorder(new EmptyBorder(0, 60, 165, 60));

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

        constraintsLocalVariableValue.insets = new Insets(0, 0, 0, 310);

        centerWrapperLocalVariableValue.add(contentPanelLocalVariableValue, constraintsLocalVariableValue);

        backgroundPanelLocalVariableValue.add(centerWrapperLocalVariableValue, BorderLayout.CENTER);

        return backgroundPanelLocalVariableValue;
    }


    /**
     * Construye el titulo.
     *
     * @return resultado de la operacion.
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
     * Construye el contenido.
     *
     * @return resultado de la operacion.
     */
    private JPanel buildCardsRow() {
        JPanel rowLocalVariableValue = new JPanel(new GridLayout(1, 3, 35, 0));
        rowLocalVariableValue.setOpaque(false);

        rowLocalVariableValue.add(viewLeaguesCardFieldReference);
        rowLocalVariableValue.add(deleteAccountCardFieldReference);
        rowLocalVariableValue.add(logoutCardFieldReference);

        return rowLocalVariableValue;
    }


    /**
     * Registra la accion.
     *
     * @param controllerHandlerParameterValue dato de entrada de la operacion.
     */
    public void registerController(ActionListener controllerHandlerParameterValue) {
        viewLeaguesCardFieldReference.setActionListener(controllerHandlerParameterValue);
        deleteAccountCardFieldReference.setActionListener(controllerHandlerParameterValue);
        logoutCardFieldReference.setActionListener(controllerHandlerParameterValue);
    }


    /**
     * Muestra el dialogo.
     *
     * @param messageParameterValue dato de entrada de la operacion.
     */
    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                messageParameterValue,
                "Player Menu",
                JOptionPane.WARNING_MESSAGE
        );
    }


    /**
     * Agrupa la logica de el menu.
     */
    private static class MenuCardPanel extends JPanel {

        private final String actionCommandFieldReference;
        private final Color accentColorFieldReference;

        private final JLabel iconHolderLabelFieldReference;
        private final JLabel titleLabelFieldReference;
        private final JLabel descriptionLabelFieldReference;

        private ActionListener listenerFieldReference;
        private boolean hoverFieldReference = false;


        /**
         * Actualiza el contenido.
         *
         * @param htmlContentParameterValue dato de entrada de la operacion.
         */
        public void setDescriptionHtml(String htmlContentParameterValue) {
            descriptionLabelFieldReference.setText(
                    "<html><div style='text-align:center;'>"
                            + htmlContentParameterValue
                            + "</div></html>"
            );
            descriptionLabelFieldReference.revalidate();
            descriptionLabelFieldReference.repaint();
        }


        /**
         * Crea una instancia de el menu.
         *
         * @param titleParameterValue titulo que usa la operacion.
         * @param descriptionParameterValue dato de entrada de la operacion.
         * @param accentColorParameterValue dato de entrada de la operacion.
         * @param actionCommandParameterValue accion que usa la operacion.
         */
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


            iconHolderLabelFieldReference = new JLabel();
            iconHolderLabelFieldReference.setHorizontalAlignment(SwingConstants.CENTER);
            iconHolderLabelFieldReference.setVerticalAlignment(SwingConstants.CENTER);
            iconHolderLabelFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);
            iconHolderLabelFieldReference.setPreferredSize(new Dimension(200, 200));
            iconHolderLabelFieldReference.setMinimumSize(new Dimension(200, 200));
            iconHolderLabelFieldReference.setMaximumSize(new Dimension(200, 200));


            titleLabelFieldReference = new JLabel(titleParameterValue, SwingConstants.CENTER);
            titleLabelFieldReference.setFont(new Font("Arial", Font.BOLD, 26));
            titleLabelFieldReference.setForeground(CARD_TITLE_COLOR);
            titleLabelFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);


            JPanel titleAccentBarLocalVariableValue = new JPanel();
            titleAccentBarLocalVariableValue.setBackground(accentColorFieldReference);
            titleAccentBarLocalVariableValue.setPreferredSize(new Dimension(60, 4));
            titleAccentBarLocalVariableValue.setMaximumSize(new Dimension(60, 4));
            titleAccentBarLocalVariableValue.setMinimumSize(new Dimension(60, 4));
            titleAccentBarLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);


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


        /**
         * Actualiza el accion.
         *
         * @param listenerParameterValue listener que se registra.
         */
        public void setActionListener(ActionListener listenerParameterValue) {
            this.listenerFieldReference = listenerParameterValue;
        }


        /**
         * Actualiza el contenido.
         *
         * @param iconParameterValue dato de entrada de la operacion.
         */
        public void setIcon(Icon iconParameterValue) {
            iconHolderLabelFieldReference.setIcon(iconParameterValue);
            iconHolderLabelFieldReference.repaint();
        }


        /**
         * Gestiona esta operacion.
         *
         * @param graphicsParameterValue dato de entrada de la operacion.
         */
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


            g2LocalVariableValue.setColor(new Color(8, 20, 46, 38));
            g2LocalVariableValue.fillRoundRect(
                    8, 14,
                    widthLocalVariableValue - 16,
                    heightLocalVariableValue - 18,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );


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


