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
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MatchSquadUi {

    private static final int MAX_PRESETS = 3;
    private static final List<Preset> PRESETS = new ArrayList<>();

    private static class Preset {
        String name;
        List<Integer> playerIds;
        Preset(String name, List<Integer> ids) {
            this.name = name;
            this.playerIds = new ArrayList<>(ids);
        }
    }

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
            Navigator.navigate(ViewType.MATCHINTRO);
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

        // ── Quick Select bar ──────────────────────────────────────────────────
        int[] activePresetIdx = { -1 };
        boolean[] loadingPreset = { false };

        selectedList.addListener((javafx.collections.ListChangeListener<Player>) c -> {
            if (loadingPreset[0]) return;
            int idx = activePresetIdx[0];
            if (idx >= 0 && idx < PRESETS.size()) {
                Preset p = PRESETS.get(idx);
                p.playerIds.clear();
                for (Player pl : selectedList) p.playerIds.add(pl.getId());
            }
        });

        HBox quickBar = new HBox(10);
        quickBar.setAlignment(Pos.CENTER_LEFT);

        Runnable[] redrawRef = new Runnable[1];
        redrawRef[0] = () -> {
            quickBar.getChildren().clear();
            for (int i = 0; i < PRESETS.size(); i++) {
                final int idx = i;
                final Preset preset = PRESETS.get(i);
                final boolean active = (idx == activePresetIdx[0]);

                Label nameL = new Label(preset.name);
                nameL.setStyle(
                    "-fx-text-fill: #e3f2fd; -fx-font-size: 12px; -fx-font-weight: bold;");

                Button xBtn = new Button("✕");
                xBtn.setStyle(
                    "-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-size: 9px;" +
                    "-fx-font-weight: bold; -fx-background-radius: 4; -fx-padding: 1 5;" +
                    "-fx-cursor: hand;");
                xBtn.setOnMouseEntered(e -> xBtn.setStyle(
                    "-fx-background-color: #e53935; -fx-text-fill: white; -fx-font-size: 9px;" +
                    "-fx-font-weight: bold; -fx-background-radius: 4; -fx-padding: 1 5;" +
                    "-fx-cursor: hand;"));
                xBtn.setOnMouseExited(e -> xBtn.setStyle(
                    "-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-size: 9px;" +
                    "-fx-font-weight: bold; -fx-background-radius: 4; -fx-padding: 1 5;" +
                    "-fx-cursor: hand;"));
                xBtn.setOnAction(e -> {
                    PRESETS.remove(idx);
                    if (activePresetIdx[0] == idx) activePresetIdx[0] = -1;
                    else if (activePresetIdx[0] > idx) activePresetIdx[0]--;
                    redrawRef[0].run();
                });

                Region sp = new Region();
                HBox.setHgrow(sp, Priority.ALWAYS);
                HBox topRow = new HBox(6, nameL, sp, xBtn);
                topRow.setAlignment(Pos.CENTER_LEFT);

                Label hint = new Label(active ? "● selected" : "tap to load · double-click to rename");
                hint.setStyle(
                    "-fx-text-fill: " + (active ? "#26a69a" : "#90caf9aa") +
                    "; -fx-font-size: 9px;");

                VBox card = new VBox(3, topRow, hint);
                card.setPrefSize(150, 50);
                card.setPadding(new Insets(6, 8, 6, 10));
                card.setAlignment(Pos.CENTER_LEFT);
                card.setStyle(presetCardStyle(active));
                card.setOnMouseEntered(e -> {
                    if (idx != activePresetIdx[0]) card.setStyle(presetCardHover());
                });
                card.setOnMouseExited(e -> card.setStyle(
                    presetCardStyle(idx == activePresetIdx[0])));
                card.setOnMouseClicked(e -> {
                    if (e.getTarget() instanceof javafx.scene.Node
                        && isInsideButton((javafx.scene.Node) e.getTarget())) return;
                    if (e.getClickCount() >= 2) {
                        TextInputDialog dlg = new TextInputDialog(preset.name);
                        dlg.setTitle("Rename Preset");
                        dlg.setHeaderText(null);
                        dlg.setContentText("Team name:");
                        Optional<String> r = dlg.showAndWait();
                        r.ifPresent(name -> {
                            String trimmed = name.trim();
                            if (!trimmed.isEmpty()) {
                                preset.name = trimmed;
                                redrawRef[0].run();
                            }
                        });
                    } else {
                        loadingPreset[0] = true;
                        try {
                            availableList.addAll(selectedList);
                            selectedList.clear();
                            int filled = 0;
                            for (int pid : preset.playerIds) {
                                if (filled >= squadSize) break;
                                Player match = null;
                                for (Player p : availableList) {
                                    if (p.getId() == pid) { match = p; break; }
                                }
                                if (match != null) {
                                    availableList.remove(match);
                                    selectedList.add(match);
                                    filled++;
                                }
                            }
                        } finally {
                            loadingPreset[0] = false;
                        }
                        activePresetIdx[0] = idx;
                        Preset p = PRESETS.get(idx);
                        p.playerIds.clear();
                        for (Player pl : selectedList) p.playerIds.add(pl.getId());
                        redrawRef[0].run();
                    }
                });

                quickBar.getChildren().add(card);
            }

            if (PRESETS.size() < MAX_PRESETS) {
                Label plus = new Label("＋  Create Team");
                plus.setStyle(
                    "-fx-text-fill: #90caf9; -fx-font-size: 11px; -fx-font-weight: bold;");
                VBox createCard = new VBox(plus);
                createCard.setPrefSize(150, 50);
                createCard.setAlignment(Pos.CENTER);
                createCard.setStyle(createCardStyle());
                createCard.setOnMouseEntered(e -> createCard.setStyle(createCardHover()));
                createCard.setOnMouseExited(e -> createCard.setStyle(createCardStyle()));
                createCard.setOnMouseClicked(e -> {
                    String name = nextDefaultName();
                    List<Integer> ids = new ArrayList<>();
                    for (Player pl : selectedList) ids.add(pl.getId());
                    PRESETS.add(new Preset(name, ids));
                    activePresetIdx[0] = PRESETS.size() - 1;
                    redrawRef[0].run();
                });
                quickBar.getChildren().add(createCard);
            }
        };
        redrawRef[0].run();

        Label quickLbl = new Label("QUICK SELECT");
        quickLbl.setStyle(
            "-fx-text-fill: #00d4ff; -fx-font-size: 11px; -fx-font-weight: bold;" +
            "-fx-letter-spacing: 1;");
        HBox quickWrap = new HBox(14, quickLbl, quickBar);
        quickWrap.setAlignment(Pos.CENTER_LEFT);
        quickWrap.setPadding(new Insets(0, 14, 12, 14));

        VBox centerArea = new VBox(listsArea, quickWrap);
        VBox.setVgrow(listsArea, Priority.ALWAYS);

        // ── Bottom bar ────────────────────────────────────────────────────────
        Button backBtn = new Button("↩  Menu");
        Styles.styleDangerButton(backBtn);
        backBtn.setOnAction(e -> Navigator.navigate(ViewType.MENU));

        Region bSpacer = new Region();
        HBox.setHgrow(bSpacer, Priority.ALWAYS);
        HBox bottomBar = new HBox(12, backBtn, statusLabel, bSpacer, confirmBtn);
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(11, 20, 11, 20));
        bottomBar.setStyle(
            "-fx-background-color: #060d1a; -fx-border-color: #1565c0; -fx-border-width: 1 0 0 0;");

        BorderPane root = new BorderPane();
        root.setStyle(Styles.rootBg());
        root.setTop(header);
        root.setCenter(centerArea);
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

    private static String presetCardStyle(boolean active) {
        return "-fx-background-color: " + (active ? "#0d2a4a" : "#0d1f35") + ";" +
               "-fx-border-color: " + (active ? "#42a5f5" : "#1a3a5a") + ";" +
               "-fx-border-width: 1.5; -fx-border-radius: 8; -fx-background-radius: 8;" +
               "-fx-cursor: hand;" +
               (active ? "-fx-effect: dropshadow(gaussian, rgba(66,165,245,0.45), 8, 0, 0, 0);" : "");
    }

    private static String presetCardHover() {
        return "-fx-background-color: #142840; -fx-border-color: #2a5a8a;" +
               "-fx-border-width: 1.5; -fx-border-radius: 8; -fx-background-radius: 8;" +
               "-fx-cursor: hand;";
    }

    private static String createCardStyle() {
        return "-fx-background-color: #061120; -fx-border-color: #1a3a5a;" +
               "-fx-border-width: 1.5; -fx-border-radius: 8; -fx-background-radius: 8;" +
               "-fx-cursor: hand;";
    }

    private static String createCardHover() {
        return "-fx-background-color: #142840; -fx-border-color: #42a5f5;" +
               "-fx-border-width: 1.5; -fx-border-radius: 8; -fx-background-radius: 8;" +
               "-fx-cursor: hand;";
    }

    private static String nextDefaultName() {
        Set<String> used = new HashSet<>();
        for (Preset p : PRESETS) used.add(p.name);
        for (int i = 1; i <= MAX_PRESETS; i++) {
            String candidate = "Team " + i;
            if (!used.contains(candidate)) return candidate;
        }
        return "Team " + (PRESETS.size() + 1);
    }

    private static boolean isInsideButton(javafx.scene.Node n) {
        while (n != null) {
            if (n instanceof Button) return true;
            n = n.getParent();
        }
        return false;
    }
}
