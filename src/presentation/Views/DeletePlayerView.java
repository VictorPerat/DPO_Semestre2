package presentation.Views;

import bussines.managers.PlayerManager;
import bussines.objects.Player;
import shared.ProjectPathResolver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/** Vista para borrar jugadores como JPanel. */
public class DeletePlayerView extends JPanel {

    public final String DELETE_PLAYERS_BUTTON = "DELETE_PLAYERS";

    private static final Color ACCENT_COLOR = new Color(55, 109, 230);
    private static final Color TITLE_WHITE = new Color(245, 247, 250);
    private static final Color SUBTITLE_WHITE = new Color(238, 241, 247);

    private static final Color CARD_TITLE_COLOR = new Color(28, 35, 51);
    private static final Color CARD_BODY_COLOR = new Color(100, 112, 135);
    private static final Color FIELD_BACKGROUND = new Color(245, 249, 255);
    private static final Color FIELD_BORDER = new Color(190, 210, 235);

    private static final Color DANGER_RED = new Color(220, 60, 60);
    private static final Color DANGER_RED_DARK = new Color(170, 35, 35);

    private static final String BACKGROUND_IMAGE_PATH =
            ProjectPathResolver.resolveProjectPath("photos/login_background.jpg");

    private static final String DELETE_PLAYER_ICON_PATH =
            ProjectPathResolver.resolveProjectPath("photos/Eliminar_Cuenta.png");

    private ActionListener controllerHandlerFieldReference;

    private ArrayList<Player> loadedPlayersFieldReference = new ArrayList<>();
    private final ArrayList<JCheckBox> playerProfileCheckboxesFieldReference = new ArrayList<>();
    private final PlayerManager playerProfileManagerServiceFieldReference = new PlayerManager();

    private JPanel playersListPanelFieldReference;

    private Rounded.RoundedButton backButtonFieldReference;
    private Rounded.RoundedButton deleteButtonFieldReference;
    private JButton configButtonFieldReference;

    public DeletePlayerView() {
        setLayout(new BorderLayout());
        loadedPlayersFieldReference = playerProfileManagerServiceFieldReference.getPlayers();
        add(buildBackgroundPanel(), BorderLayout.CENTER);
    }

    public void setController(ActionListener controllerHandlerParameterValue) {
        this.controllerHandlerFieldReference = controllerHandlerParameterValue;
    }

    public void loadPlayers(ArrayList<Player> playersParameterValue) {
        loadedPlayersFieldReference =
                playersParameterValue == null ? new ArrayList<>() : playersParameterValue;
        populatePlayersList();
    }

    public void refreshPlayersList() {
        loadedPlayersFieldReference = playerProfileManagerServiceFieldReference.getPlayers();
        populatePlayersList();
    }

    private JPanel buildBackgroundPanel() {
        Image backgroundImageLocalVariableValue =
                new ImageIcon(BACKGROUND_IMAGE_PATH).getImage();

        JPanel backgroundPanelLocalVariableValue = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphicsParameterValue) {
                super.paintComponent(graphicsParameterValue);

                graphicsParameterValue.drawImage(
                        backgroundImageLocalVariableValue,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        this
                );

                Graphics2D g2LocalVariableValue =
                        (Graphics2D) graphicsParameterValue.create();
                g2LocalVariableValue.setColor(new Color(0, 0, 0, 55));
                g2LocalVariableValue.fillRect(0, 0, getWidth(), getHeight());
                g2LocalVariableValue.dispose();
            }
        };

        backgroundPanelLocalVariableValue.setLayout(new BorderLayout());
        backgroundPanelLocalVariableValue.add(buildTopBar(), BorderLayout.NORTH);
        backgroundPanelLocalVariableValue.add(buildCenterContent(), BorderLayout.CENTER);
        backgroundPanelLocalVariableValue.add(buildBottomBar(), BorderLayout.SOUTH);

        return backgroundPanelLocalVariableValue;
    }

    private JPanel buildTopBar() {
        JPanel topBarLocalVariableValue = new JPanel(new BorderLayout());
        topBarLocalVariableValue.setOpaque(false);
        topBarLocalVariableValue.setBorder(new EmptyBorder(28, 40, 0, 40));

        JPanel rightPanelLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanelLocalVariableValue.setOpaque(false);

        backButtonFieldReference = new Rounded.RoundedButton("← BACK", 18);
        backButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 15));
        backButtonFieldReference.setForeground(ACCENT_COLOR);
        backButtonFieldReference.setBackground(new Color(255, 255, 255, 230));
        backButtonFieldReference.setOutlineMode(ACCENT_COLOR, 2);
        backButtonFieldReference.setShadowEnabled(false);
        backButtonFieldReference.setPreferredSize(new Dimension(145, 44));

        backButtonFieldReference.addActionListener(
                eventArgumentParameterValue -> fireCommand("BACK")
        );

        rightPanelLocalVariableValue.add(backButtonFieldReference);
        topBarLocalVariableValue.add(rightPanelLocalVariableValue, BorderLayout.EAST);

        return topBarLocalVariableValue;
    }

    private JPanel buildBottomBar() {
        JPanel bottomBarLocalVariableValue =
                new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 8));
        bottomBarLocalVariableValue.setOpaque(false);
        bottomBarLocalVariableValue.setBorder(new EmptyBorder(0, 20, 18, 0));

        configButtonFieldReference = buildConfigButton();
        bottomBarLocalVariableValue.add(configButtonFieldReference);

        return bottomBarLocalVariableValue;
    }

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
                    rawIconLocalVariableValue.getScaledInstance(
                            82,
                            82,
                            Image.SCALE_SMOOTH
                    );

            buttonControlLocalVariableValue.setIcon(
                    new ImageIcon(scaledIconLocalVariableValue)
            );
            buttonControlLocalVariableValue.setPreferredSize(new Dimension(120, 120));
        } catch (Exception ignoredExceptionParameterValue) {
            buttonControlLocalVariableValue.setText("⚙");
            buttonControlLocalVariableValue.setForeground(Color.WHITE);
            buttonControlLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 32));
        }

        buttonControlLocalVariableValue.addActionListener(
                eventArgumentParameterValue -> fireCommand("CONFIG")
        );

        return buttonControlLocalVariableValue;
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
        JPanel deleteCardLocalVariableValue = buildDeleteCard();

        titleBlockLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteCardLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanelLocalVariableValue.add(titleBlockLocalVariableValue);
        contentPanelLocalVariableValue.add(Box.createVerticalStrut(38));
        contentPanelLocalVariableValue.add(deleteCardLocalVariableValue);

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

        JLabel deleteLabelLocalVariableValue = new JLabel("DELETE ");
        deleteLabelLocalVariableValue.setForeground(ACCENT_COLOR);
        deleteLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        JLabel playerLabelLocalVariableValue = new JLabel("PLAYER");
        playerLabelLocalVariableValue.setForeground(TITLE_WHITE);
        playerLabelLocalVariableValue.setFont(
                new Font("Arial", Font.BOLD, mainTitleSizeLocalVariableValue)
        );

        titleLineLocalVariableValue.add(deleteLabelLocalVariableValue);
        titleLineLocalVariableValue.add(playerLabelLocalVariableValue);

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
                "Select players to remove from the system",
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

    private JPanel buildDeleteCard() {
        JPanel cardLocalVariableValue = new RoundedCardPanel();
        cardLocalVariableValue.setOpaque(false);
        cardLocalVariableValue.setLayout(
                new BoxLayout(cardLocalVariableValue, BoxLayout.Y_AXIS)
        );
        cardLocalVariableValue.setBorder(new EmptyBorder(24, 34, 30, 34));

        cardLocalVariableValue.setPreferredSize(new Dimension(820, 560));
        cardLocalVariableValue.setMinimumSize(new Dimension(820, 560));
        cardLocalVariableValue.setMaximumSize(new Dimension(820, 560));

        JPanel iconPanelLocalVariableValue = buildDeleteIconPanel();
        iconPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabelLocalVariableValue =
                new JLabel("PLAYER LIST", SwingConstants.CENTER);
        titleLabelLocalVariableValue.setForeground(CARD_TITLE_COLOR);
        titleLabelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel helperLabelLocalVariableValue = new JLabel(
                "Mark one or more players and confirm the deletion",
                SwingConstants.CENTER
        );
        helperLabelLocalVariableValue.setForeground(CARD_BODY_COLOR);
        helperLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 14));
        helperLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardLocalVariableValue.add(iconPanelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(6));
        cardLocalVariableValue.add(titleLabelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(8));
        cardLocalVariableValue.add(helperLabelLocalVariableValue);
        cardLocalVariableValue.add(Box.createVerticalStrut(18));

        JScrollPane playersScrollPaneLocalVariableValue = createPlayersScrollPane();
        playersScrollPaneLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardLocalVariableValue.add(playersScrollPaneLocalVariableValue);

        cardLocalVariableValue.add(Box.createVerticalStrut(18));

        deleteButtonFieldReference = new Rounded.RoundedButton("DELETE SELECTED", 18);
        deleteButtonFieldReference.setFont(new Font("Arial", Font.BOLD, 15));
        deleteButtonFieldReference.setForeground(Color.WHITE);
        deleteButtonFieldReference.setBackground(DANGER_RED);
        deleteButtonFieldReference.setGradientColors(DANGER_RED, DANGER_RED_DARK);
        deleteButtonFieldReference.setPreferredSize(new Dimension(440, 46));
        deleteButtonFieldReference.setMaximumSize(new Dimension(440, 46));
        deleteButtonFieldReference.setMinimumSize(new Dimension(440, 46));
        deleteButtonFieldReference.setAlignmentX(Component.CENTER_ALIGNMENT);

        deleteButtonFieldReference.addActionListener(
                eventArgumentParameterValue -> fireCommand(DELETE_PLAYERS_BUTTON)
        );

        cardLocalVariableValue.add(deleteButtonFieldReference);

        return cardLocalVariableValue;
    }

    private JPanel buildDeleteIconPanel() {
        JPanel iconPanelLocalVariableValue = new JPanel(new GridBagLayout());
        iconPanelLocalVariableValue.setOpaque(false);

        iconPanelLocalVariableValue.setPreferredSize(new Dimension(120, 120));
        iconPanelLocalVariableValue.setMaximumSize(new Dimension(120, 120));
        iconPanelLocalVariableValue.setMinimumSize(new Dimension(120, 120));

        JLabel iconLabelLocalVariableValue = new JLabel();
        iconLabelLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabelLocalVariableValue.setVerticalAlignment(SwingConstants.CENTER);

        try {
            Image iconImageLocalVariableValue =
                    new ImageIcon(DELETE_PLAYER_ICON_PATH).getImage();

            Image scaledIconLocalVariableValue =
                    iconImageLocalVariableValue.getScaledInstance(
                            115,
                            115,
                            Image.SCALE_SMOOTH
                    );

            iconLabelLocalVariableValue.setIcon(
                    new ImageIcon(scaledIconLocalVariableValue)
            );
        } catch (Exception ignoredExceptionParameterValue) {
            iconLabelLocalVariableValue.setText("🗑");
            iconLabelLocalVariableValue.setFont(new Font("Arial", Font.PLAIN, 62));
        }

        iconPanelLocalVariableValue.add(iconLabelLocalVariableValue);

        return iconPanelLocalVariableValue;
    }

    private JScrollPane createPlayersScrollPane() {
        playersListPanelFieldReference = new JPanel();
        playersListPanelFieldReference.setLayout(
                new BoxLayout(playersListPanelFieldReference, BoxLayout.Y_AXIS)
        );
        playersListPanelFieldReference.setBackground(FIELD_BACKGROUND);
        playersListPanelFieldReference.setBorder(new EmptyBorder(10, 12, 10, 12));

        JScrollPane scrollPaneLocalVariableValue =
                new JScrollPane(playersListPanelFieldReference);

        scrollPaneLocalVariableValue.setBorder(
                BorderFactory.createLineBorder(FIELD_BORDER, 1)
        );
        scrollPaneLocalVariableValue.getViewport().setBackground(FIELD_BACKGROUND);
        scrollPaneLocalVariableValue.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPaneLocalVariableValue.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollPaneLocalVariableValue.setPreferredSize(new Dimension(720, 235));
        scrollPaneLocalVariableValue.setMaximumSize(new Dimension(720, 235));
        scrollPaneLocalVariableValue.setMinimumSize(new Dimension(720, 235));

        populatePlayersList();

        return scrollPaneLocalVariableValue;
    }

    private void populatePlayersList() {
        if (playersListPanelFieldReference == null) {
            return;
        }

        playersListPanelFieldReference.removeAll();
        playerProfileCheckboxesFieldReference.clear();

        JPanel headerPanelLocalVariableValue = buildListHeader();
        playersListPanelFieldReference.add(headerPanelLocalVariableValue);
        playersListPanelFieldReference.add(Box.createVerticalStrut(8));

        if (loadedPlayersFieldReference == null || loadedPlayersFieldReference.isEmpty()) {
            JLabel emptyLabelLocalVariableValue = new JLabel(
                    "No players available.",
                    SwingConstants.CENTER
            );
            emptyLabelLocalVariableValue.setForeground(new Color(120, 132, 155));
            emptyLabelLocalVariableValue.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyLabelLocalVariableValue.setBorder(new EmptyBorder(62, 0, 20, 0));

            playersListPanelFieldReference.add(emptyLabelLocalVariableValue);
        } else {
            for (Player playerProfileLocalVariableValue : loadedPlayersFieldReference) {
                playersListPanelFieldReference.add(
                        createPlayerRow(playerProfileLocalVariableValue)
                );
                playersListPanelFieldReference.add(Box.createVerticalStrut(8));
            }
        }

        playersListPanelFieldReference.revalidate();
        playersListPanelFieldReference.repaint();
    }

    private JPanel buildListHeader() {
        JPanel headerPanelLocalVariableValue = new JPanel(new GridLayout(1, 4));
        headerPanelLocalVariableValue.setOpaque(false);
        headerPanelLocalVariableValue.setMaximumSize(new Dimension(680, 34));
        headerPanelLocalVariableValue.setPreferredSize(new Dimension(680, 34));
        headerPanelLocalVariableValue.setMinimumSize(new Dimension(680, 34));
        headerPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] headersLocalVariableValue = {"SELECT", "PLAYER", "DNI", "TEAM"};

        for (String headerLocalVariableValue : headersLocalVariableValue) {
            JLabel labelLocalVariableValue =
                    new JLabel(headerLocalVariableValue, SwingConstants.CENTER);
            labelLocalVariableValue.setFont(new Font("Arial", Font.BOLD, 13));
            labelLocalVariableValue.setForeground(new Color(75, 88, 115));
            headerPanelLocalVariableValue.add(labelLocalVariableValue);
        }

        return headerPanelLocalVariableValue;
    }

    private JPanel createPlayerRow(Player playerProfileParameterValue) {
        JPanel rowPanelLocalVariableValue = new SelectableRowPanel();
        rowPanelLocalVariableValue.setLayout(new GridLayout(1, 4));
        rowPanelLocalVariableValue.setOpaque(false);
        rowPanelLocalVariableValue.setBorder(new EmptyBorder(8, 16, 8, 16));
        rowPanelLocalVariableValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        rowPanelLocalVariableValue.setPreferredSize(new Dimension(680, 48));
        rowPanelLocalVariableValue.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rowPanelLocalVariableValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JCheckBox checkBoxLocalVariableValue = new JCheckBox();
        checkBoxLocalVariableValue.setOpaque(false);
        checkBoxLocalVariableValue.setHorizontalAlignment(SwingConstants.CENTER);
        checkBoxLocalVariableValue.setCursor(new Cursor(Cursor.HAND_CURSOR));

        playerProfileCheckboxesFieldReference.add(checkBoxLocalVariableValue);

        JLabel nameLabelLocalVariableValue = createRowLabel(
                playerProfileParameterValue.getNamePlayer(),
                Font.BOLD,
                CARD_TITLE_COLOR
        );

        JLabel dniLabelLocalVariableValue = createRowLabel(
                playerProfileParameterValue.getDniPlayer(),
                Font.PLAIN,
                new Color(54, 66, 87)
        );

        JLabel teamLabelLocalVariableValue = createRowLabel(
                playerProfileParameterValue.getTeam(),
                Font.PLAIN,
                new Color(54, 66, 87)
        );

        rowPanelLocalVariableValue.add(checkBoxLocalVariableValue);
        rowPanelLocalVariableValue.add(nameLabelLocalVariableValue);
        rowPanelLocalVariableValue.add(dniLabelLocalVariableValue);
        rowPanelLocalVariableValue.add(teamLabelLocalVariableValue);

        rowPanelLocalVariableValue.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent eventArgumentParameterValue) {
                checkBoxLocalVariableValue.setSelected(
                        !checkBoxLocalVariableValue.isSelected()
                );
            }
        });

        return rowPanelLocalVariableValue;
    }

    private JLabel createRowLabel(String textParameterValue,
                                  int styleParameterValue,
                                  Color colorParameterValue) {
        JLabel labelLocalVariableValue =
                new JLabel(textParameterValue == null ? "-" : textParameterValue,
                        SwingConstants.CENTER);
        labelLocalVariableValue.setFont(new Font("Arial", styleParameterValue, 14));
        labelLocalVariableValue.setForeground(colorParameterValue);
        return labelLocalVariableValue;
    }

    private void fireCommand(String commandParameterValue) {
        if (controllerHandlerFieldReference != null) {
            controllerHandlerFieldReference.actionPerformed(
                    new ActionEvent(
                            this,
                            ActionEvent.ACTION_PERFORMED,
                            commandParameterValue
                    )
            );
        }
    }

    public ArrayList<Player> getSelectedPlayers() {
        ArrayList<Player> selectedLocalVariableValue = new ArrayList<>();

        for (int indexCounterLocalVariableValue = 0;
             indexCounterLocalVariableValue < playerProfileCheckboxesFieldReference.size();
             indexCounterLocalVariableValue++) {

            if (playerProfileCheckboxesFieldReference
                    .get(indexCounterLocalVariableValue)
                    .isSelected()
                    && indexCounterLocalVariableValue < loadedPlayersFieldReference.size()) {

                selectedLocalVariableValue.add(
                        loadedPlayersFieldReference.get(indexCounterLocalVariableValue)
                );
            }
        }

        return selectedLocalVariableValue;
    }

    public int confirmDeletePlayers(int numberPlayersParameterValue) {
        return JOptionPane.showConfirmDialog(
                SwingUtilities.getWindowAncestor(this),
                "Are you sure you want to delete "
                        + numberPlayersParameterValue
                        + " player(s)?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
        );
    }

    public void showDeletionResult(String messageParameterValue) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                messageParameterValue,
                "Players Deleted",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void showMessageDialog(String messageParameterValue) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                messageParameterValue,
                "DELETE",
                JOptionPane.WARNING_MESSAGE
        );
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

            g2LocalVariableValue.setColor(new Color(8, 20, 46, 42));
            g2LocalVariableValue.fillRoundRect(
                    8,
                    10,
                    widthLocalVariableValue - 16,
                    heightLocalVariableValue - 10,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.setColor(new Color(255, 255, 255, 250));
            g2LocalVariableValue.fillRoundRect(
                    0,
                    0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 4,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.setColor(new Color(22, 150, 83));
            g2LocalVariableValue.setStroke(new BasicStroke(2f));
            g2LocalVariableValue.drawRoundRect(
                    0,
                    0,
                    widthLocalVariableValue - 1,
                    heightLocalVariableValue - 5,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }

    private static class SelectableRowPanel extends JPanel {
        private boolean hoverFieldReference = false;

        SelectableRowPanel() {
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
            });
        }

        @Override
        protected void paintComponent(Graphics graphicsParameterValue) {
            Graphics2D g2LocalVariableValue =
                    (Graphics2D) graphicsParameterValue.create();

            g2LocalVariableValue.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int radiusLocalVariableValue = 16;

            g2LocalVariableValue.setColor(
                    hoverFieldReference
                            ? new Color(255, 238, 238)
                            : Color.WHITE
            );

            g2LocalVariableValue.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.setColor(
                    hoverFieldReference
                            ? DANGER_RED
                            : new Color(218, 228, 242)
            );

            g2LocalVariableValue.setStroke(new BasicStroke(1.4f));
            g2LocalVariableValue.drawRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radiusLocalVariableValue,
                    radiusLocalVariableValue
            );

            g2LocalVariableValue.dispose();
            super.paintComponent(graphicsParameterValue);
        }
    }
}