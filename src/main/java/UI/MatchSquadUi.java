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
        int teamId    = gameService.getGameTeamId();
        Sport sport   = teamService.getSportByTeamId(teamId);
        ArrayList<Player> players = teamService.getHealthyPlayers(teamId);
        int squadSize = sport.getPlayersPerTeam();

        ObservableList<Player> availableList = FXCollections.observableArrayList(players);
        ObservableList<Player> selectedList  = FXCollections.observableArrayList();

        ListView<Player> availableView = new ListView<>(availableList);
        ListView<Player> selectedView  = new ListView<>(selectedList);

        // Available list — click anywhere on the row to add
        availableView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Player player, boolean empty) {
                super.updateItem(player, empty);
                if (empty || player == null) {
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                    setOnMouseClicked(null);
                    setOnMouseEntered(null);
                    setOnMouseExited(null);
                } else {
                    setGraphic(buildRow(player, true));
                    setStyle("-fx-background-color: transparent; -fx-padding: 0; -fx-cursor: hand;");
                    setOnMouseEntered(e -> setStyle(
                        "-fx-background-color: #26a69a22; -fx-padding: 0; -fx-cursor: hand;"));
                    setOnMouseExited(e -> setStyle(
                        "-fx-background-color: transparent; -fx-padding: 0; -fx-cursor: hand;"));
                    setOnMouseClicked(e -> {
                        if (selectedList.size() < squadSize) {
                            selectedList.add(player);
                            availableList.remove(player);
                        }
                    });
                }
            }
            @Override public void updateSelected(boolean s) { super.updateSelected(s); }
        });

        // Squad list — each row has an ✕ button to send back
        selectedView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Player player, boolean empty) {
                super.updateItem(player, empty);
                if (empty || player == null) {
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                } else {
                    Button removeBtn = new Button("✕");
                    removeBtn.setStyle(xBtnStyle("#c0392b"));
                    removeBtn.setOnMouseEntered(e -> removeBtn.setStyle(xBtnStyle("#e53935")));
                    removeBtn.setOnMouseExited(e  -> removeBtn.setStyle(xBtnStyle("#c0392b")));
                    removeBtn.setOnAction(e -> {
                        availableList.add(player);
                        selectedList.remove(player);
                    });

                    HBox row = buildRow(player, false);
                    row.getChildren().add(removeBtn);
                    setGraphic(row);
                    setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                }
            }
            @Override public void updateSelected(boolean s) { super.updateSelected(s); }
        });

        styleList(availableView);
        styleList(selectedView);

        // ── Labels ────────────────────────────────────────────────────────────
        Label counterLabel = new Label();
        counterLabel.textProperty().bind(
            Bindings.createStringBinding(
                () -> selectedList.size() + " / " + squadSize, selectedList));
        counterLabel.setStyle("-fx-text-fill: #e3f2fd; -fx-font-size: 15px; -fx-font-weight: bold;");

        Label statusLabel = new Label();
        statusLabel.textProperty().bind(
            Bindings.createStringBinding(() -> {
                int diff = squadSize - selectedList.size();
                if (diff > 0) return "⚠  Add " + diff + " more player" + (diff > 1 ? "s" : "");
                return "✓  Squad complete — ready to kick off";
            }, selectedList));
        statusLabel.setStyle("-fx-text-fill: #ffa502; -fx-font-size: 12px;");
        selectedList.addListener((javafx.collections.ListChangeListener<Player>) c -> {
            if (selectedList.size() == squadSize)
                statusLabel.setStyle(
                    "-fx-text-fill: #26a69a; -fx-font-size: 12px; -fx-font-weight: bold;");
            else
                statusLabel.setStyle("-fx-text-fill: #ffa502; -fx-font-size: 12px;");
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
            "-fx-text-fill: #00d4ff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-letter-spacing: 2;");
        Label counterBox = new Label();
        counterBox.textProperty().bind(counterLabel.textProperty());
        counterBox.setStyle(
            "-fx-text-fill: #e3f2fd; -fx-font-size: 14px; -fx-font-weight: bold;" +
            "-fx-padding: 3 12; -fx-background-color: #1565c055; -fx-background-radius: 12;");
        Region hSpacer = new Region();
        HBox.setHgrow(hSpacer, Priority.ALWAYS);
        HBox header = new HBox(titleLbl, hSpacer, counterBox);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(13, 20, 13, 20));
        header.setStyle(
            "-fx-background-color: #060d1a; -fx-border-color: #1565c0; -fx-border-width: 0 0 1 0;");

        // ── Panel headers ─────────────────────────────────────────────────────
        Label availHdr = makePanelHeader("AVAILABLE  (" + players.size() + ")  —  tap to add");
        Label squadHdr = makePanelHeader("MATCH SQUAD  —  ✕ to remove");

        VBox leftPanel = new VBox(8, availHdr, availableView);
        leftPanel.setPadding(new Insets(14));
        leftPanel.setStyle(Styles.panelStyle("#1e3a5a"));
        VBox.setVgrow(availableView, Priority.ALWAYS);

        VBox rightPanel = new VBox(8, squadHdr, selectedView);
        rightPanel.setPadding(new Insets(14));
        rightPanel.setStyle(Styles.panelStyle("#1e3a5a"));
        VBox.setVgrow(selectedView, Priority.ALWAYS);

        HBox listsArea = new HBox(12, leftPanel, rightPanel);
        HBox.setHgrow(leftPanel,  Priority.ALWAYS);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);
        listsArea.setPadding(new Insets(14));

        // ── Bottom bar ────────────────────────────────────────────────────────
        Region bSpacer = new Region();
        HBox.setHgrow(bSpacer, Priority.ALWAYS);
        HBox bottomBar = new HBox(12, statusLabel, bSpacer, confirmBtn);
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(11, 20, 11, 20));
        bottomBar.setStyle(
            "-fx-background-color: #060d1a; -fx-border-color: #1565c0; -fx-border-width: 1 0 0 0;");

        BorderPane root = new BorderPane();
        root.setStyle(Styles.rootBg());
        root.setTop(header);
        root.setCenter(listsArea);
        root.setBottom(bottomBar);

        Styles.applyFadeIn(root);
        return root;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private HBox buildRow(Player player, boolean withAddHint) {
        Label nameL = new Label(player.getName());
        nameL.setStyle(
            "-fx-text-fill: #e3f2fd; -fx-font-size: 13px; -fx-font-weight: bold;");
        Label posL = new Label(player.getPosition());
        posL.setStyle(
            "-fx-text-fill: #90caf9; -fx-font-size: 10px; -fx-padding: 2 6;" +
            "-fx-background-color: #1565c055; -fx-background-radius: 4;");
        Label ovrL = new Label(String.valueOf(player.getOverall()));
        ovrL.setStyle(
            "-fx-text-fill: #ffd54f; -fx-font-size: 13px; -fx-font-weight: bold;");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        HBox row;
        if (withAddHint) {
            Label hint = new Label("＋");
            hint.setStyle("-fx-text-fill: #26a69a; -fx-font-size: 13px; -fx-font-weight: bold;");
            row = new HBox(8, nameL, posL, sp, ovrL, hint);
        } else {
            row = new HBox(8, nameL, posL, sp, ovrL);
        }
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(7, 10, 7, 10));
        return row;
    }

    private Label makePanelHeader(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
            "-fx-text-fill: #90caf9; -fx-font-size: 11px; -fx-font-weight: bold;" +
            "-fx-letter-spacing: 1; -fx-padding: 0 0 4 0;");
        return lbl;
    }

    private void styleList(ListView<Player> lv) {
        lv.setStyle(
            "-fx-background-color: #061120; -fx-border-color: #1a2e4a;" +
            "-fx-border-width: 1; -fx-border-radius: 6; -fx-background-radius: 6;");
    }

    private String xBtnStyle(String color) {
        return "-fx-background-color: " + color + "; -fx-text-fill: white; " +
               "-fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 5; " +
               "-fx-padding: 2 7; -fx-cursor: hand;";
    }
}
