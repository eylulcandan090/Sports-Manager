package UI;

import Database.Database;
import Model.Basketball.BasketballPlayer;
import Model.Football.FootballPlayer;
import Model.Player;
import Model.Sport;
import Model.Football.Football;
import Model.Basketball.Basketball;
import Repository.BasketballPlayerRepo;
import Repository.FootballPlayerRepo;
import Repository.GameRepo;
import Service.GameService;
import Service.TeamService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;

public class MyTeamUi {

    // ── Table dark-theme CSS loaded via data URI (%23 = #) ───────────────────
    private static final String TABLE_CSS = "data:text/css," +
        ".squad-table{-fx-background-color:%23060f1e;-fx-border-color:%231e3a6e;" +
            "-fx-border-width:1;-fx-border-radius:10;-fx-background-radius:10;}" +
        ".squad-table .column-header-background{-fx-background-color:%230a1a35;}" +
        ".squad-table .column-header{-fx-background-color:transparent;" +
            "-fx-border-color:%231e3a6e transparent transparent transparent;-fx-size:40px;}" +
        ".squad-table .column-header .label{-fx-text-fill:%2390caf9;-fx-font-weight:bold;" +
            "-fx-font-size:12px;-fx-alignment:CENTER-LEFT;-fx-padding:0 10;}" +
        ".squad-table .table-row-cell{-fx-background-color:%23060f1e;" +
            "-fx-table-cell-border-color:%230d2040;-fx-cell-size:44px;}" +
        ".squad-table .table-row-cell:odd{-fx-background-color:%23081730;}" +
        ".squad-table .table-row-cell:selected{-fx-background-color:%23103d80;}" +
        ".squad-table .table-row-cell:hover{-fx-background-color:%230d2a55;}" +
        ".squad-table .table-cell{-fx-text-fill:%23e3f2fd;-fx-font-size:13px;" +
            "-fx-alignment:CENTER-LEFT;-fx-padding:0 10;-fx-border-color:transparent;}" +
        ".squad-table .scroll-bar:vertical{-fx-background-color:%23060f1e;}" +
        ".squad-table .scroll-bar:vertical .thumb{-fx-background-color:%231565c0;-fx-background-radius:4;}" +
        ".squad-table .scroll-bar:vertical .track{-fx-background-color:%23060f1e;}" +
        ".squad-table .filler{-fx-background-color:%230a1a35;}";

    public Parent getView(GameService gameService, TeamService teamService,
                          FootballPlayerRepo footballPlayerRepo,
                          BasketballPlayerRepo basketballPlayerRepo) {

        int teamId = gameService.getGameTeamId();
        Sport sport = teamService.getSportByTeamId(teamId);
        String teamName = new GameRepo(Database.getInstance().getConnection()).getGameTeamById(teamId);

        BorderPane root = new BorderPane();
        root.setStyle(
            "-fx-background-color: radial-gradient(center 30% 20%, radius 70%, #0d1f3a, #020c1b);" +
            "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
        );
        root.setTop(buildTopBar(teamName, gameService));

        if (sport instanceof Football) {
            ArrayList<Player> players = footballPlayerRepo.getFootballPlayersByTeamId(teamId);
            root.setCenter(buildFootballCenter(players, teamName));
        } else if (sport instanceof Basketball) {
            ArrayList<BasketballPlayer> players = basketballPlayerRepo.getPlayersByTeam(teamId);
            root.setCenter(buildBasketballCenter(players, teamName));
        } else {
            Label err = new Label("Sport not found.");
            err.setStyle("-fx-text-fill: #90caf9; -fx-font-size: 14px;");
            root.setCenter(err);
        }

        Styles.applyFadeIn(root);
        return root;
    }

    // ── Top Bar ───────────────────────────────────────────────────────────────

    private HBox buildTopBar(String teamName, GameService gameService) {
        HBox bar = new HBox(14);
        bar.setStyle(Styles.TOPBAR);
        bar.setPadding(new Insets(12, 16, 12, 16));
        bar.setAlignment(Pos.CENTER_LEFT);

        Label sectionLbl = new Label("👥  MY SQUAD");
        sectionLbl.setStyle(
            "-fx-text-fill: #f48fb1; -fx-font-size: 13px; -fx-font-weight: bold; -fx-letter-spacing: 2;"
        );

        Label teamLbl = new Label(teamName);
        teamLbl.setStyle(Styles.TEAM_NAME_LABEL);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label weekLbl = new Label("Week " + gameService.getCurrentWeek());
        weekLbl.setStyle(Styles.TOPBAR_LABEL);

        Button backBtn = new Button("← Back");
        Styles.styleDangerButton(backBtn);
        backBtn.setOnAction(e -> Navigator.navigate(ViewType.MENU));

        bar.getChildren().addAll(sectionLbl, teamLbl, spacer, weekLbl, backBtn);
        return bar;
    }

    // ── Football Center ───────────────────────────────────────────────────────

    private ScrollPane buildFootballCenter(ArrayList<Player> rawPlayers, String teamName) {
        ArrayList<FootballPlayer> players = new ArrayList<>();
        for (Player p : rawPlayers) {
            if (p instanceof FootballPlayer) players.add((FootballPlayer) p);
        }

        int total    = players.size();
        long injured = players.stream().filter(p -> p.getInjuryStatus() != 0).count();
        double avgAge    = players.stream().mapToInt(Player::getAge).average().orElse(0);
        double avgRating = players.stream().mapToInt(FootballPlayer::getOverall).average().orElse(0);

        // ── Table ──────────────────────────────────────────────────────────────
        TableView<FootballPlayer> table = buildStyledTable();

        TableColumn<FootballPlayer, String> nameCol = col("NAME", 170);
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));

        TableColumn<FootballPlayer, String> posCol = col("POS", 65);
        posCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getPosition()));
        posCol.setCellFactory(c -> new TableCell<>() {
            @Override protected void updateItem(String v, boolean empty) {
                super.updateItem(v, empty);
                if (empty || v == null) { setGraphic(null); setText(null); return; }
                Label b = new Label(v);
                b.setStyle(badgeStyle(v));
                b.setPadding(new Insets(1, 8, 1, 8));
                setGraphic(b);
                setText(null);
                setStyle("-fx-alignment: CENTER-LEFT; -fx-padding: 0 0 0 6;");
            }
        });

        TableColumn<FootballPlayer, Integer> ageCol = col("AGE", 50);
        ageCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getAge()).asObject());

        TableColumn<FootballPlayer, Integer> ovrCol = col("OVR", 55);
        ovrCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getOverall()).asObject());
        ovrCol.setCellFactory(c -> ratingCell());

        TableColumn<FootballPlayer, Integer> shoCol = col("SHO", 55);
        shoCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getShooting()).asObject());

        TableColumn<FootballPlayer, Integer> pasCol = col("PAS", 55);
        pasCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getPassing()).asObject());

        TableColumn<FootballPlayer, Integer> defCol = col("DEF", 55);
        defCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getDefance()).asObject());

        TableColumn<FootballPlayer, String> statusCol = col("STATUS", 90);
        statusCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getInjuryStatus() == 0 ? "✔  Fit" : "✖  Injured"));
        statusCol.setCellFactory(c -> fitnessCell());

        table.getColumns().addAll(nameCol, posCol, ageCol, ovrCol, shoCol, pasCol, defCol, statusCol);
        table.getItems().addAll(players);

        // ── Detail panel ───────────────────────────────────────────────────────
        VBox detail = emptyDetailPanel();
        table.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) fillFootballDetail(detail, sel);
        });

        return wrapInScrollPane(teamName, total, avgAge, avgRating, (int) injured, table, detail);
    }

    // ── Basketball Center ─────────────────────────────────────────────────────

    private ScrollPane buildBasketballCenter(ArrayList<BasketballPlayer> players, String teamName) {
        int total    = players.size();
        long injured = players.stream().filter(p -> p.getInjuryStatus() != 0).count();
        double avgAge    = players.stream().mapToInt(Player::getAge).average().orElse(0);
        double avgRating = players.stream().mapToInt(BasketballPlayer::getOverall).average().orElse(0);

        TableView<BasketballPlayer> table = buildStyledTable();

        TableColumn<BasketballPlayer, String> nameCol = col("NAME", 160);
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));

        TableColumn<BasketballPlayer, String> posCol = col("POS", 55);
        posCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getPosition()));
        posCol.setCellFactory(c -> new TableCell<>() {
            @Override protected void updateItem(String v, boolean empty) {
                super.updateItem(v, empty);
                if (empty || v == null) { setGraphic(null); setText(null); return; }
                Label b = new Label(v);
                b.setStyle(badgeStyle(v));
                b.setPadding(new Insets(1, 8, 1, 8));
                setGraphic(b);
                setText(null);
                setStyle("-fx-alignment: CENTER-LEFT; -fx-padding: 0 0 0 6;");
            }
        });

        TableColumn<BasketballPlayer, Integer> ageCol = col("AGE", 50);
        ageCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getAge()).asObject());

        TableColumn<BasketballPlayer, Integer> ovrCol = col("OVR", 55);
        ovrCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getOverall()).asObject());
        ovrCol.setCellFactory(c -> ratingCell());

        TableColumn<BasketballPlayer, Integer> shoCol = col("SHO", 50);
        shoCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getShooting()).asObject());

        TableColumn<BasketballPlayer, Integer> drbCol = col("DRB", 50);
        drbCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getDribbling()).asObject());

        TableColumn<BasketballPlayer, Integer> pasCol = col("PAS", 50);
        pasCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getPassing()).asObject());

        TableColumn<BasketballPlayer, Integer> defCol = col("DEF", 50);
        defCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getDefense()).asObject());

        TableColumn<BasketballPlayer, String> statusCol = col("STATUS", 90);
        statusCol.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getInjuryStatus() == 0 ? "✔  Fit" : "✖  Injured"));
        statusCol.setCellFactory(c -> fitnessCell());

        table.getColumns().addAll(nameCol, posCol, ageCol, ovrCol, shoCol, drbCol, pasCol, defCol, statusCol);
        table.getItems().addAll(players);

        VBox detail = emptyDetailPanel();
        table.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) fillBasketballDetail(detail, sel);
        });

        return wrapInScrollPane(teamName, total, avgAge, avgRating, (int) injured, table, detail);
    }

    // ── Layout assembly ───────────────────────────────────────────────────────

    private <T> ScrollPane wrapInScrollPane(String teamName, int total, double avgAge,
                                             double avgRating, int injured,
                                             TableView<T> table, VBox detail) {
        HBox tableRow = new HBox(14, table, detail);
        tableRow.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(table, Priority.ALWAYS);
        VBox.setVgrow(tableRow, Priority.ALWAYS);

        VBox center = new VBox(16,
            teamHeaderRow(teamName),
            statCardsRow(total, avgAge, avgRating, injured),
            tableRow
        );
        center.setPadding(new Insets(20));

        ScrollPane scroll = new ScrollPane(center);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle(
            "-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;"
        );
        return scroll;
    }

    // ── Team header row ───────────────────────────────────────────────────────

    private HBox teamHeaderRow(String teamName) {
        ImageView logo = new ImageView();
        URL url = getClass().getResource(logoPath(teamName));
        if (url != null) {
            logo.setImage(new Image(url.toExternalForm()));
            logo.setFitWidth(48);
            logo.setFitHeight(48);
            logo.setPreserveRatio(true);
        }

        Label nameLbl = new Label(teamName.toUpperCase());
        nameLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;" +
            "-fx-font-family: 'Impact', 'Arial Black', sans-serif; -fx-letter-spacing: 2;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,200,255,0.5), 16, 0.28, 0, 0);"
        );
        Label subLbl = new Label("SQUAD");
        subLbl.setStyle(
            "-fx-text-fill: #90caf9; -fx-font-size: 11px; -fx-letter-spacing: 3; -fx-font-weight: bold;"
        );

        VBox nameBox = new VBox(2, nameLbl, subLbl);
        nameBox.setAlignment(Pos.CENTER_LEFT);

        HBox row = new HBox(14, logo, nameBox);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    // ── Stat cards ────────────────────────────────────────────────────────────

    private HBox statCardsRow(int total, double avgAge, double avgRating, int injured) {
        VBox c1 = statCard("PLAYERS",  String.valueOf(total),                  "#90caf9");
        VBox c2 = statCard("AVG AGE",  String.format("%.1f", avgAge),          "#ffd54f");
        VBox c3 = statCard("AVG OVR",  String.format("%.0f", avgRating),       "#26a69a");
        VBox c4 = statCard("INJURED",  String.valueOf(injured),
                           injured > 0 ? "#ff4757" : "#26a69a");

        HBox row = new HBox(12, c1, c2, c3, c4);
        HBox.setHgrow(c1, Priority.ALWAYS);
        HBox.setHgrow(c2, Priority.ALWAYS);
        HBox.setHgrow(c3, Priority.ALWAYS);
        HBox.setHgrow(c4, Priority.ALWAYS);
        return row;
    }

    private VBox statCard(String key, String value, String color) {
        Label valLbl = new Label(value);
        valLbl.setStyle(
            "-fx-text-fill: " + color + "; -fx-font-size: 26px; -fx-font-weight: bold;" +
            "-fx-effect: dropshadow(gaussian, " + color + "88, 10, 0, 0, 0);"
        );
        Label keyLbl = new Label(key);
        keyLbl.setStyle(
            "-fx-text-fill: #3a5272; -fx-font-size: 10px; -fx-letter-spacing: 1.5; -fx-font-weight: bold;"
        );
        VBox card = new VBox(2, valLbl, keyLbl);
        card.setPadding(new Insets(14, 16, 14, 16));
        card.setStyle(
            "-fx-background-color: #0a1628; -fx-border-color: #1e3a5a;" +
            "-fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,80,200,0.18), 14, 0, 0, 3);"
        );
        return card;
    }

    // ── Player detail panel ───────────────────────────────────────────────────

    private VBox emptyDetailPanel() {
        VBox panel = new VBox();
        panel.setPrefWidth(215);
        panel.setMinWidth(215);
        panel.setMaxWidth(215);
        panel.setMinHeight(300);
        panel.setPadding(new Insets(20, 16, 16, 16));
        panel.setAlignment(Pos.CENTER);
        panel.setStyle(
            "-fx-background-color: #0a1628; -fx-border-color: #1e3a6e;" +
            "-fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10;"
        );
        Label hint = new Label("Select a player\nto view details");
        hint.setStyle(
            "-fx-text-fill: #1e3a5a; -fx-font-size: 13px; -fx-text-alignment: center;"
        );
        hint.setAlignment(Pos.CENTER);
        panel.getChildren().add(hint);
        return panel;
    }

    private void fillFootballDetail(VBox panel, FootballPlayer p) {
        panel.getChildren().clear();
        panel.setAlignment(Pos.TOP_LEFT);

        Label nameLbl = new Label(p.getName());
        nameLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-wrap-text: true;"
        );
        nameLbl.setMaxWidth(190);

        Label badge = new Label(p.getPosition());
        badge.setStyle(badgeStyle(p.getPosition()) + " -fx-font-size: 11px;");
        badge.setPadding(new Insets(2, 10, 2, 10));

        Label ovrVal = new Label(String.valueOf(p.getOverall()));
        ovrVal.setStyle(
            "-fx-text-fill: #ffd54f; -fx-font-size: 38px; -fx-font-weight: bold;" +
            "-fx-effect: dropshadow(gaussian, rgba(255,213,79,0.6), 14, 0, 0, 0);"
        );
        Label ovrKey = new Label("OVERALL");
        ovrKey.setStyle("-fx-text-fill: #2a4060; -fx-font-size: 10px; -fx-letter-spacing: 1;");

        VBox ovrBox = new VBox(0, ovrVal, ovrKey);
        ovrBox.setAlignment(Pos.CENTER_LEFT);

        panel.getChildren().addAll(
            nameLbl, badge,
            spacer(8), ovrBox, spacer(4),
            dividerLine(),
            spacer(6),
            statBar("SHO", p.getShooting()),
            spacer(4),
            statBar("PAS", p.getPassing()),
            spacer(4),
            statBar("DEF", p.getDefance()),
            spacer(4),
            statBar("GK",  p.getGoalkeeping()),
            spacer(10),
            fitnessLabel(p.getInjuryStatus())
        );
    }

    private void fillBasketballDetail(VBox panel, BasketballPlayer p) {
        panel.getChildren().clear();
        panel.setAlignment(Pos.TOP_LEFT);

        Label nameLbl = new Label(p.getName());
        nameLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-wrap-text: true;"
        );
        nameLbl.setMaxWidth(190);

        Label badge = new Label(p.getPosition());
        badge.setStyle(badgeStyle(p.getPosition()) + " -fx-font-size: 11px;");
        badge.setPadding(new Insets(2, 10, 2, 10));

        Label ovrVal = new Label(String.valueOf(p.getOverall()));
        ovrVal.setStyle(
            "-fx-text-fill: #ffd54f; -fx-font-size: 38px; -fx-font-weight: bold;" +
            "-fx-effect: dropshadow(gaussian, rgba(255,213,79,0.6), 14, 0, 0, 0);"
        );
        Label ovrKey = new Label("OVERALL");
        ovrKey.setStyle("-fx-text-fill: #2a4060; -fx-font-size: 10px; -fx-letter-spacing: 1;");

        VBox ovrBox = new VBox(0, ovrVal, ovrKey);
        ovrBox.setAlignment(Pos.CENTER_LEFT);

        panel.getChildren().addAll(
            nameLbl, badge,
            spacer(8), ovrBox, spacer(4),
            dividerLine(),
            spacer(6),
            statBar("SHO", p.getShooting()),
            spacer(4),
            statBar("DRB", p.getDribbling()),
            spacer(4),
            statBar("PAS", p.getPassing()),
            spacer(4),
            statBar("DEF", p.getDefense()),
            spacer(4),
            statBar("BLK", p.getBlock()),
            spacer(10),
            fitnessLabel(p.getInjuryStatus())
        );
    }

    // ── Stat bar (mini progress bar for detail panel) ─────────────────────────

    private HBox statBar(String key, int value) {
        Label keyLbl = new Label(key);
        keyLbl.setStyle(
            "-fx-text-fill: #607080; -fx-font-size: 11px; -fx-font-weight: bold;"
        );
        keyLbl.setMinWidth(32);

        int clamped  = Math.max(0, Math.min(100, value));
        String color = clamped >= 75 ? "#26a69a" : clamped >= 50 ? "#90caf9" : "#ffd54f";
        double barW  = clamped * 0.95; // max 95px for 100

        Region fill = new Region();
        fill.setPrefHeight(5);
        fill.setPrefWidth(barW);
        fill.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 3;");

        Region track = new Region();
        track.setPrefHeight(5);
        track.setPrefWidth(95);
        track.setMinWidth(95);
        track.setMaxWidth(95);
        track.setStyle("-fx-background-color: #1a2535; -fx-background-radius: 3;");

        StackPane bar = new StackPane(track, fill);
        StackPane.setAlignment(fill, Pos.CENTER_LEFT);
        bar.setMaxWidth(95);
        bar.setMinWidth(95);

        Label valLbl = new Label(String.valueOf(value));
        valLbl.setStyle("-fx-text-fill: #e3f2fd; -fx-font-size: 11px; -fx-font-weight: bold;");
        valLbl.setMinWidth(26);
        valLbl.setAlignment(Pos.CENTER_RIGHT);

        HBox row = new HBox(6, keyLbl, bar, valLbl);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    // ── Cell factories ────────────────────────────────────────────────────────

    private <S> TableCell<S, Integer> ratingCell() {
        return new TableCell<>() {
            @Override protected void updateItem(Integer val, boolean empty) {
                super.updateItem(val, empty);
                if (empty || val == null) { setText(null); setStyle(""); return; }
                String color = val >= 75 ? "#26a69a" : val >= 60 ? "#90caf9" : "#ffd54f";
                setText(String.valueOf(val));
                setStyle(
                    "-fx-text-fill: " + color + "; -fx-font-weight: bold;" +
                    "-fx-alignment: CENTER-LEFT; -fx-padding: 0 10;" +
                    "-fx-effect: dropshadow(gaussian," + color + "66, 6, 0, 0, 0);"
                );
            }
        };
    }

    private <S> TableCell<S, String> fitnessCell() {
        return new TableCell<>() {
            @Override protected void updateItem(String val, boolean empty) {
                super.updateItem(val, empty);
                if (empty || val == null) { setText(null); setStyle(""); return; }
                boolean fit = val.contains("Fit");
                setText(val);
                setStyle(
                    "-fx-text-fill: " + (fit ? "#26a69a" : "#ff4757") + ";" +
                    "-fx-font-weight: bold; -fx-alignment: CENTER-LEFT; -fx-padding: 0 10;"
                );
            }
        };
    }

    // ── Column helper ─────────────────────────────────────────────────────────

    private static <S, T> TableColumn<S, T> col(String header, int pref) {
        TableColumn<S, T> c = new TableColumn<>(header);
        c.setPrefWidth(pref);
        c.setMinWidth(pref - 10);
        return c;
    }

    // ── Table base setup ──────────────────────────────────────────────────────

    private static <T> TableView<T> buildStyledTable() {
        TableView<T> table = new TableView<>();
        table.getStyleClass().add("squad-table");
        table.getStylesheets().add(TABLE_CSS);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No players found."));
        VBox.setVgrow(table, Priority.ALWAYS);
        return table;
    }

    // ── Small UI helpers ──────────────────────────────────────────────────────

    private Label fitnessLabel(int status) {
        boolean fit  = status == 0;
        Label lbl    = new Label(fit ? "✔  Fit" : "✖  Injured");
        lbl.setStyle(
            "-fx-text-fill: " + (fit ? "#26a69a" : "#ff4757") + ";" +
            "-fx-font-size: 13px; -fx-font-weight: bold;"
        );
        return lbl;
    }

    private Region spacer(int h) {
        Region r = new Region();
        r.setPrefHeight(h);
        return r;
    }

    private Region dividerLine() {
        Region d = new Region();
        d.setPrefHeight(1);
        d.setMaxWidth(Double.MAX_VALUE);
        d.setStyle("-fx-background-color: #1e3a5a66;");
        return d;
    }

    // ── Position badge style ──────────────────────────────────────────────────

    private static String badgeStyle(String pos) {
        if (pos == null) return "";
        switch (pos.toUpperCase()) {
            case "GK":
                return "-fx-background-color: #4a148c; -fx-text-fill: #e1bee7;" +
                       "-fx-background-radius: 4; -fx-font-size: 10px; -fx-font-weight: bold;";
            case "CB": case "LB": case "RB": case "DEF":
                return "-fx-background-color: #0d47a1; -fx-text-fill: #bbdefb;" +
                       "-fx-background-radius: 4; -fx-font-size: 10px; -fx-font-weight: bold;";
            case "CM": case "CAM": case "CDM": case "LM": case "RM": case "MID":
                return "-fx-background-color: #1b5e20; -fx-text-fill: #c8e6c9;" +
                       "-fx-background-radius: 4; -fx-font-size: 10px; -fx-font-weight: bold;";
            case "ST": case "CF": case "LW": case "RW": case "FWD":
                return "-fx-background-color: #b71c1c; -fx-text-fill: #ffcdd2;" +
                       "-fx-background-radius: 4; -fx-font-size: 10px; -fx-font-weight: bold;";
            case "PG": case "SG": case "SF": case "PF": case "C":
                return "-fx-background-color: #e65100; -fx-text-fill: #ffe0b2;" +
                       "-fx-background-radius: 4; -fx-font-size: 10px; -fx-font-weight: bold;";
            default:
                return "-fx-background-color: #263238; -fx-text-fill: #b0bec5;" +
                       "-fx-background-radius: 4; -fx-font-size: 10px; -fx-font-weight: bold;";
        }
    }

    // ── Logo path ─────────────────────────────────────────────────────────────

    private static String logoPath(String n) {
        switch (n) {
            case "Arsenal":             return "/images/teams/arsenal.png";
            case "Aston Villa":         return "/images/teams/astonvilla.png";
            case "Bournemouth":         return "/images/teams/bournemouth.png";
            case "Brentford": case "Brentford FC":
                                        return "/images/teams/brentford.png";
            case "Brighton": case "Brighton & Hove Albion":
                                        return "/images/teams/brighton.png";
            case "Chelsea":             return "/images/teams/chelsea.png";
            case "Crystal Palace":      return "/images/teams/crystal.png";
            case "Everton":             return "/images/teams/everton.png";
            case "Nottingham Forest": case "Nott'm Forest":
                                        return "/images/teams/forest.png";
            case "Fulham":              return "/images/teams/fullham.png";
            case "Ipswich": case "Ipswich Town":
                                        return "/images/teams/ıpswich.png";
            case "Leicester": case "Leicester City":
                                        return "/images/teams/leicester.png";
            case "Liverpool":           return "/images/teams/liverpool.png";
            case "Manchester City":     return "/images/teams/manchester.png";
            case "Manchester United":   return "/images/teams/manchesterunited.png";
            case "Newcastle United": case "Newcastle":
                                        return "/images/teams/newcastleunited.png";
            case "Southampton":         return "/images/teams/southampton.png";
            case "Tottenham": case "Tottenham Hotspur":
                                        return "/images/teams/tottenham.png";
            case "West Ham": case "West Ham United":
                                        return "/images/teams/westham.png";
            case "Wolverhampton": case "Wolverhampton Wanderers": case "Wolves":
                                        return "/images/teams/wolverhampton.png";
            case "Dallas Mavericks":        return "/images/basketteams/dallas.png";
            case "Denver Nuggets":          return "/images/basketteams/denvernuggets.png";
            case "Golden State Warriors":   return "/images/basketteams/goldenstate.png";
            case "Houston Rockets":         return "/images/basketteams/houston.png";
            case "Los Angeles Lakers":      return "/images/basketteams/losangeleslakers.png";
            case "Miami Heat":              return "/images/basketteams/miamiheat.png";
            case "Milwaukee Bucks":         return "/images/basketteams/milwaukee.png";
            case "Minnesota Timberwolves":  return "/images/basketteams/minnesotatimber.png";
            case "New York Knicks":         return "/images/basketteams/newyorkkknicks.png";
            case "OKC Thunder":             return "/images/basketteams/okcthunder.png";
            case "Philadelphia 76ers":      return "/images/basketteams/philedelphia.png";
            case "Phoenix Suns":            return "/images/basketteams/phoenix.png";
            case "San Antonio Spurs":       return "/images/basketteams/sanantonio.png";
            default:                        return "/images/sports.png";
        }
    }
}
