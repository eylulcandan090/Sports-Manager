package UI;

import Model.Player;
import Model.Sport;
import Service.GameService;
import Service.TeamService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class MatchSquadUi {

    public Parent getView(GameService gameService, TeamService teamService) {
        int teamId   = gameService.getGameTeamId();
        Sport sport  = teamService.getSportByTeamId(teamId);
        ArrayList<Player> players = teamService.getHealthyPlayers(teamId);
        int squadSize = sport.getPlayersPerTeam();

        // ── Lists ─────────────────────────────────────────────────────────────
        ObservableList<Player> availableList = FXCollections.observableArrayList(players);
        ObservableList<Player> selectedList  = FXCollections.observableArrayList();

        ListView<Player> availableView = new ListView<>(availableList);
        ListView<Player> selectedView  = new ListView<>(selectedList);
        availableView.setCellFactory(lv -> playerCell());
        selectedView.setCellFactory(lv  -> playerCell());
        styleList(availableView);
        styleList(selectedView);

        // ── Transfer buttons ──────────────────────────────────────────────────
        Button addBtn    = new Button("▶");
        Button removeBtn = new Button("◀");
        styleTransferBtn(addBtn);
        styleTransferBtn(removeBtn);

        addBtn.setOnAction(e -> {
            Player p = availableView.getSelectionModel().getSelectedItem();
            if (p != null && selectedList.size() < squadSize) {
                selectedList.add(p);
                availableList.remove(p);
            }
        });
        removeBtn.setOnAction(e -> {
            Player p = selectedView.getSelectionModel().getSelectedItem();
            if (p != null) {
                availableList.add(p);
                selectedList.remove(p);
            }
        });

        // ── Labels ────────────────────────────────────────────────────────────
        Label counterLabel = new Label();
        counterLabel.textProperty().bind(
            Bindings.createStringBinding(
                () -> selectedList.size() + " / " + squadSize,
                selectedList
            )
        );
        counterLabel.setStyle("-fx-text-fill: #e3f2fd; -fx-font-size: 15px; -fx-font-weight: bold;");

        Label statusLabel = new Label();
        statusLabel.textProperty().bind(
            Bindings.createStringBinding(
                () -> {
                    int diff = squadSize - selectedList.size();
                    if (diff > 0) return "⚠  Add " + diff + " more player" + (diff > 1 ? "s" : "");
                    return "✓  Squad complete — ready to kick off";
                },
                selectedList
            )
        );
        statusLabel.setStyle("-fx-text-fill: #ffa502; -fx-font-size: 12px;");
        selectedList.addListener((javafx.collections.ListChangeListener<Player>) c -> {
            if (selectedList.size() == squadSize) {
                statusLabel.setStyle("-fx-text-fill: #26a69a; -fx-font-size: 12px; -fx-font-weight: bold;");
            } else {
                statusLabel.setStyle("-fx-text-fill: #ffa502; -fx-font-size: 12px;");
            }
        });

        // ── Confirm button ────────────────────────────────────────────────────
        Button confirmBtn = new Button("⚽  KICK OFF");
        confirmBtn.setPrefWidth(150);
        Styles.styleGreenButton(confirmBtn);
        confirmBtn.disableProperty().bind(Bindings.size(selectedList).isNotEqualTo(squadSize));
        confirmBtn.setOnAction(e -> {
            ArrayList<Player> matchSquad = new ArrayList<>(selectedList);
            ArrayList<Player> bench      = new ArrayList<>(availableList);
            gameService.startMatch(matchSquad, bench);
            Navigator.navigate(ViewType.MATCHPLAY);
        });

        // ── Header bar ────────────────────────────────────────────────────────
        Label titleLbl = new Label("SQUAD SELECTION");
        titleLbl.setStyle(
            "-fx-text-fill: #00d4ff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-letter-spacing: 2;"
        );
        Label counterBox = new Label();
        counterBox.textProperty().bind(counterLabel.textProperty());
        counterBox.setStyle(
            "-fx-text-fill: #e3f2fd; -fx-font-size: 14px; -fx-font-weight: bold;" +
            "-fx-padding: 3 12; -fx-background-color: #1565c055; -fx-background-radius: 12;"
        );

        Region hSpacer = new Region();
        HBox.setHgrow(hSpacer, Priority.ALWAYS);
        HBox header = new HBox(titleLbl, hSpacer, counterBox);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(13, 20, 13, 20));
        header.setStyle("-fx-background-color: #060d1a; -fx-border-color: #1565c0; -fx-border-width: 0 0 1 0;");

        // ── Panel headers ─────────────────────────────────────────────────────
        Label availHdr = makePanelHeader("AVAILABLE  (" + players.size() + ")");
        Label squadHdr = makePanelHeader("MATCH SQUAD");

        VBox leftPanel = new VBox(8, availHdr, availableView);
        leftPanel.setPadding(new Insets(14));
        leftPanel.setStyle(Styles.panelStyle("#1e3a5a"));
        VBox.setVgrow(availableView, Priority.ALWAYS);

        VBox rightPanel = new VBox(8, squadHdr, selectedView);
        rightPanel.setPadding(new Insets(14));
        rightPanel.setStyle(Styles.panelStyle("#1e3a5a"));
        VBox.setVgrow(selectedView, Priority.ALWAYS);

        VBox middleButtons = new VBox(14, addBtn, removeBtn);
        middleButtons.setAlignment(Pos.CENTER);
        middleButtons.setPadding(new Insets(0, 8, 0, 8));

        HBox listsArea = new HBox(12, leftPanel, middleButtons, rightPanel);
        HBox.setHgrow(leftPanel,  Priority.ALWAYS);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);
        listsArea.setPadding(new Insets(14));

        // ── Bottom bar ────────────────────────────────────────────────────────
        Region bSpacer = new Region();
        HBox.setHgrow(bSpacer, Priority.ALWAYS);
        HBox bottomBar = new HBox(12, statusLabel, bSpacer, confirmBtn);
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(11, 20, 11, 20));
        bottomBar.setStyle("-fx-background-color: #060d1a; -fx-border-color: #1565c0; -fx-border-width: 1 0 0 0;");

        // ── Root ──────────────────────────────────────────────────────────────
        BorderPane root = new BorderPane();
        root.setStyle(Styles.rootBg());
        root.setTop(header);
        root.setCenter(listsArea);
        root.setBottom(bottomBar);

        Styles.applyFadeIn(root);
        return root;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private Label makePanelHeader(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
            "-fx-text-fill: #90caf9; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 1;" +
            "-fx-padding: 0 0 4 0;"
        );
        return lbl;
    }

    private void styleList(ListView<Player> lv) {
        lv.setStyle(
            "-fx-background-color: #061120; -fx-border-color: #1a2e4a;" +
            "-fx-border-width: 1; -fx-border-radius: 6; -fx-background-radius: 6;"
        );
    }

    private void styleTransferBtn(Button btn) {
        String normal =
            "-fx-background-color: #1565c0; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;" +
            "-fx-pref-width: 42px; -fx-pref-height: 38px; -fx-background-radius: 8; -fx-cursor: hand;";
        String hover =
            "-fx-background-color: #1976d2; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;" +
            "-fx-pref-width: 42px; -fx-pref-height: 38px; -fx-background-radius: 8; -fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(66,165,245,0.5), 10, 0, 0, 2);";
        btn.setStyle(normal);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(normal));
    }

    private ListCell<Player> playerCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(Player player, boolean empty) {
                super.updateItem(player, empty);
                if (empty || player == null) {
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                } else {
                    Label nameL = new Label(player.getName());
                    nameL.setStyle(
                        "-fx-text-fill: #e3f2fd; -fx-font-size: 13px; -fx-font-weight: bold;"
                    );

                    Label posL = new Label(player.getPosition());
                    posL.setStyle(
                        "-fx-text-fill: #90caf9; -fx-font-size: 10px; -fx-padding: 2 6;" +
                        "-fx-background-color: #1565c055; -fx-background-radius: 4;"
                    );

                    Label ovrL = new Label(String.valueOf(player.getOverall()));
                    ovrL.setStyle(
                        "-fx-text-fill: #ffd54f; -fx-font-size: 13px; -fx-font-weight: bold;"
                    );

                    Region sp = new Region();
                    HBox.setHgrow(sp, Priority.ALWAYS);

                    HBox row = new HBox(8, nameL, posL, sp, ovrL);
                    row.setAlignment(Pos.CENTER_LEFT);
                    row.setPadding(new Insets(7, 10, 7, 10));

                    setGraphic(row);
                    setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                }
            }

            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                setStyle(selected
                    ? "-fx-background-color: #1565c0bb; -fx-padding: 0; -fx-background-radius: 5;"
                    : "-fx-background-color: transparent; -fx-padding: 0;");
            }
        };
    }
}
