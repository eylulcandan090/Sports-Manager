package UI;

import Model.Player;
import Service.GameService;
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

public class SubstitutionUi {

    public Parent getView(GameService gameService) {
        Label title = new Label("Substitution");
        title.setStyle("-fx-text-fill: #f48fb1; -fx-font-size: 20px; -fx-font-weight: bold;");

        Label subsLabel = new Label(
            "Subs: " + gameService.getSubsCount() + " / " + gameService.getMaxSubs());
        subsLabel.setStyle(Styles.TOPBAR_LABEL);

        ObservableList<Player> squadList = FXCollections.observableArrayList(gameService.getCurrentSquad());
        ObservableList<Player> benchList = FXCollections.observableArrayList(gameService.getCurrentBench());

        ListView<Player> squadView = new ListView<>(squadList);
        ListView<Player> benchView = new ListView<>(benchList);

        if (gameService.getInjuredPlayer() != null)
            squadView.getSelectionModel().select(gameService.getInjuredPlayer());

        // ── On-field list — X button to select who goes OFF ──────────────────
        squadView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Player player, boolean empty) {
                super.updateItem(player, empty);
                if (empty || player == null) {
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                } else {
                    Button selectBtn = new Button("✕");
                    selectBtn.setStyle(actionBtnStyle("#c0392b"));
                    selectBtn.setOnMouseEntered(e -> selectBtn.setStyle(actionBtnStyle("#e53935")));
                    selectBtn.setOnMouseExited(e  -> selectBtn.setStyle(actionBtnStyle("#c0392b")));
                    selectBtn.setOnAction(e -> squadView.getSelectionModel().select(player));

                    HBox row = buildRow(player);
                    row.getChildren().add(selectBtn);
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
        });

        // ── Bench list — ＋ to sub in, ✕ to exclude ──────────────────────────
        benchView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Player player, boolean empty) {
                super.updateItem(player, empty);
                if (empty || player == null) {
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                } else {
                    Button subInBtn = new Button("+");
                    subInBtn.setStyle(actionBtnStyle("#26a69a"));
                    subInBtn.setDisable(!gameService.canSubs());
                    subInBtn.setOnMouseEntered(e -> {
                        if (!subInBtn.isDisabled()) subInBtn.setStyle(actionBtnStyle("#00897b"));
                    });
                    subInBtn.setOnMouseExited(e -> {
                        if (!subInBtn.isDisabled()) subInBtn.setStyle(actionBtnStyle("#26a69a"));
                    });
                    subInBtn.setOnAction(e -> {
                        Player outPlayer = squadView.getSelectionModel().getSelectedItem();
                        if (outPlayer != null && gameService.canSubs()) {
                            squadList.remove(outPlayer);
                            benchList.remove(player);
                            squadList.add(player);
                            benchList.add(outPlayer);

                            gameService.getCurrentSquad().clear();
                            gameService.getCurrentSquad().addAll(squadList);
                            gameService.getCurrentBench().clear();
                            gameService.getCurrentBench().addAll(benchList);
                            gameService.increaseSubsCount();

                            subsLabel.setText(
                                "Subs: " + gameService.getSubsCount() + " / " + gameService.getMaxSubs());
                            benchView.refresh();
                        }
                    });

                    HBox row = buildRow(player);
                    row.getChildren().add(subInBtn);
                    setGraphic(row);
                    setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                }
            }
        });

        styleList(squadView);
        styleList(benchView);

        // ── Labels ────────────────────────────────────────────────────────────
        Label onFieldLbl = new Label("On Field  —  ✕ to select who goes off");
        Label benchLbl   = new Label("Bench  —  ＋ to sub in");
        onFieldLbl.setStyle(Styles.SUBTITLE_LABEL);
        benchLbl.setStyle(Styles.SUBTITLE_LABEL);

        VBox leftBox  = new VBox(8, onFieldLbl, squadView);
        VBox rightBox = new VBox(8, benchLbl, benchView);
        VBox.setVgrow(squadView, Priority.ALWAYS);
        VBox.setVgrow(benchView, Priority.ALWAYS);
        leftBox.setPrefWidth(280);
        rightBox.setPrefWidth(360);

        HBox lists = new HBox(16, leftBox, rightBox);
        lists.setAlignment(Pos.CENTER);
        VBox.setVgrow(lists, Priority.ALWAYS);

        // ── Bottom bar ────────────────────────────────────────────────────────
        Button backBtn = new Button("Back to Match");
        Styles.styleButton(backBtn);
        backBtn.setOnAction(e -> Navigator.navigate(ViewType.MATCHPLAY));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        HBox bottomBar = new HBox(12, subsLabel, sp, backBtn);
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(10, 0, 0, 0));

        VBox root = new VBox(16, title, lists, bottomBar);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle(Styles.rootBg());

        return root;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private HBox buildRow(Player player) {
        Label nameL = new Label(player.getName());
        nameL.setStyle(
            "-fx-text-fill: #e3f2fd; -fx-font-size: 13px; -fx-font-weight: bold;");
        Label posL = new Label(player.getPosition());
        posL.setStyle(
            "-fx-text-fill: #90caf9; -fx-font-size: 10px; -fx-padding: 2 5;" +
            "-fx-background-color: #1565c055; -fx-background-radius: 4;");
        Label ovrL = new Label(String.valueOf(player.getOverall()));
        ovrL.setStyle(
            "-fx-text-fill: #ffd54f; -fx-font-size: 12px; -fx-font-weight: bold;");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        HBox row = new HBox(8, nameL, posL, sp, ovrL);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(5, 8, 5, 10));
        return row;
    }

    private void styleList(ListView<Player> lv) {
        lv.setStyle(
            "-fx-background-color: #061120; -fx-border-color: #1a2e4a;" +
            "-fx-border-width: 1; -fx-border-radius: 6; -fx-background-radius: 6;");
        lv.setPrefHeight(300);
    }

    private String actionBtnStyle(String color) {
        return "-fx-background-color: " + color + "; -fx-text-fill: white; " +
               "-fx-font-size: 12px; -fx-font-weight: bold; -fx-background-radius: 5; " +
               "-fx-padding: 3 9; -fx-cursor: hand;";
    }
}
