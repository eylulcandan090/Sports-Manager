package UI;

import Database.Database;
import Model.Basketball.Basketball;
import Model.Basketball.BasketballPlayer;
import Model.Coach;
import Model.Football.Football;
import Model.Football.FootballPlayer;
import Model.Player;
import Model.Sport;
import Model.TrainingType;
import Repository.BasketballPlayerRepo;
import Repository.FootballPlayerRepo;
import Repository.GameRepo;
import Service.GameService;
import Service.TeamService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class TrainingScreenUi {

    private static final String CARD_NORMAL =
        "-fx-background-color: #0a1628; -fx-border-color: #1e3a6e;" +
        "-fx-border-width: 1.5; -fx-border-radius: 12; -fx-background-radius: 12;" +
        "-fx-cursor: hand;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 2);";

    private static final String CARD_HOVER =
        "-fx-background-color: #0d2040; -fx-border-color: #2a5aaa;" +
        "-fx-border-width: 2; -fx-border-radius: 12; -fx-background-radius: 12;" +
        "-fx-effect: dropshadow(gaussian, rgba(42,90,170,0.55), 20, 0, 0, 0);" +
        "-fx-cursor: hand;";

    private static final String CARD_SELECTED =
        "-fx-background-color: #0c2650; -fx-border-color: #00c8f0;" +
        "-fx-border-width: 2.5; -fx-border-radius: 12; -fx-background-radius: 12;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,200,240,0.70), 24, 0, 0, 0);" +
        "-fx-cursor: hand;";

    public Parent getView(GameService gameService, TeamService teamService,
                          FootballPlayerRepo footballPlayerRepo,
                          BasketballPlayerRepo basketballPlayerRepo) {

        int teamId = gameService.getGameTeamId();
        Sport sport = teamService.getSportByTeamId(teamId);
        List<TrainingType> types = sport.getTrainingTypes();
        String teamName = new GameRepo(Database.getInstance().getConnection()).getGameTeamById(teamId);

        // ── Root ──────────────────────────────────────────────────────────────
        BorderPane root = new BorderPane();
        root.setStyle(
            "-fx-background-color: radial-gradient(center 30% 20%, radius 70%, #0d1f3a, #020c1b);" +
            "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
        );
        root.setTop(buildTopBar(teamName, gameService));

        // ── Selection state ────────────────────────────────────────────────────
        TrainingType[] selected = {null};
        List<VBox> cards = new ArrayList<>();

        // ── Info + feedback labels ─────────────────────────────────────────────
        Label selInfoLbl = new Label("Select a training type below to begin");
        selInfoLbl.setStyle("-fx-text-fill: #2a4060; -fx-font-size: 13px; -fx-font-style: italic;");

        Label feedbackLbl = new Label("");
        feedbackLbl.setStyle(
            "-fx-text-fill: #26a69a; -fx-font-size: 13px; -fx-font-weight: bold;" +
            "-fx-effect: dropshadow(gaussian, rgba(38,166,154,0.5), 8, 0, 0, 0);"
        );
        feedbackLbl.setVisible(false);

        // ── Card grid ──────────────────────────────────────────────────────────
        int cols = Math.min(3, types.size());
        TilePane grid = new TilePane();
        grid.setPrefColumns(cols);
        grid.setHgap(16);
        grid.setVgap(16);
        grid.setAlignment(Pos.CENTER);

        for (TrainingType type : types) {
            VBox card = buildCard(type);
            cards.add(card);

            card.setOnMouseEntered(e -> {
                if (selected[0] != type) card.setStyle(CARD_HOVER);
            });
            card.setOnMouseExited(e -> {
                if (selected[0] != type) card.setStyle(CARD_NORMAL);
            });
            card.setOnMouseClicked(e -> {
                for (VBox c : cards) c.setStyle(CARD_NORMAL);
                card.setStyle(CARD_SELECTED);
                selected[0] = type;
                feedbackLbl.setVisible(false);
                selInfoLbl.setText(
                    "Selected:  " + displayName(type) +
                    "  ·  All healthy players will gain  " + statEffect(type)
                );
                selInfoLbl.setStyle("-fx-text-fill: #90caf9; -fx-font-size: 13px;");
            });

            grid.getChildren().add(card);
        }

        // ── Selection info panel ───────────────────────────────────────────────
        HBox selPanel = new HBox(selInfoLbl);
        selPanel.setAlignment(Pos.CENTER_LEFT);
        selPanel.setPadding(new Insets(11, 18, 11, 18));
        selPanel.setStyle(
            "-fx-background-color: #0a1628; -fx-border-color: #1e3a5a;" +
            "-fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8;"
        );

        // ── Title section ──────────────────────────────────────────────────────
        Label titleLbl = new Label("TRAINING CENTER");
        titleLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 36px; -fx-font-weight: bold;" +
            "-fx-font-family: 'Impact', 'Arial Black', sans-serif; -fx-letter-spacing: 3;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,200,255,1.0), 42, 0.45, 0, 2)," +
            "            dropshadow(gaussian, rgba(0,80,180,0.7), 16, 0.25, 0, 3);"
        );
        Label subtitleLbl = new Label("Improve your squad abilities before the next match");
        subtitleLbl.setStyle(
            "-fx-text-fill: #90caf9; -fx-font-size: 14px;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,100,200,0.4), 8, 0, 0, 0);"
        );

        VBox titleBox = new VBox(6, titleLbl, subtitleLbl);
        titleBox.setAlignment(Pos.CENTER);

        // ── Center scroll ─────────────────────────────────────────────────────
        VBox center = new VBox(28, titleBox, grid, selPanel);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(32, 40, 24, 40));

        ScrollPane scroll = new ScrollPane(center);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle(
            "-fx-background: transparent; -fx-background-color: transparent;" +
            "-fx-border-color: transparent;"
        );
        root.setCenter(scroll);

        // ── Buttons + bottom bar ───────────────────────────────────────────────
        Button backBtn = new Button("← Back");
        Styles.styleDangerButton(backBtn);
        backBtn.setOnAction(e -> Navigator.navigate(ViewType.MENU));

        Button trainBtn = new Button("▶  TRAIN");
        Styles.styleGreenButton(trainBtn);
        trainBtn.setOnAction(e -> {
            TrainingType sel = selected[0];
            if (sel == null) {
                AlertUtility.showWarning("No Selection", "Please select a training type.");
                return;
            }

            StringBuilder sb = new StringBuilder();

            if (sport instanceof Football) {
                ArrayList<Player> players = footballPlayerRepo.getHealthyFootballPlayers(teamId);
                Coach tempCoach = new Coach("Trainer", sel, 1, teamId);
                for (Player p : players) {
                    FootballPlayer fp = (FootballPlayer) p;
                    switch (sel) {
                        case SHOOT:   fp.setShooting(fp.getShooting() + 1);   break;
                        case PASS:    fp.setPassing(fp.getPassing() + 1);     break;
                        case DEFANCE: fp.setDefance(fp.getDefance() + 1);     break;
                    }
                    footballPlayerRepo.updatePlayer(fp);
                    sb.append(fp.getName()).append("\n");
                }
            } else if (sport instanceof Basketball) {
                ArrayList<BasketballPlayer> players = basketballPlayerRepo.getPlayersByTeam(teamId);
                for (BasketballPlayer bp : players) {
                    switch (sel) {
                        case SHOOT:     bp.setShooting(bp.getShooting() + 1);     break;
                        case PASS:      bp.setPassing(bp.getPassing() + 1);       break;
                        case DEFANCE:   bp.setDefense(bp.getDefense() + 1);       break;
                        case BLOCKING:  bp.setBlock(bp.getBlock() + 1);           break;
                        case DRIBBLING: bp.setDribbling(bp.getDribbling() + 1);   break;
                        case STEALING:  bp.setSteal(bp.getSteal() + 1);           break;
                    }
                    basketballPlayerRepo.updatePlayer(bp);
                    sb.append(bp.getName()).append("\n");
                }
            }

            if (sb.length() == 0) {
                AlertUtility.showWarning("No Players", "No healthy players found for this team.");
                return;
            }

            feedbackLbl.setText("✅  Training complete!  " + statEffect(sel) + " applied to all healthy players.");
            feedbackLbl.setVisible(true);
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox bottomBar = new HBox(16, backBtn, spacer, feedbackLbl, trainBtn);
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(14, 20, 14, 20));
        bottomBar.setStyle(
            "-fx-background-color: #010d1f;" +
            "-fx-border-color: #1565c0; -fx-border-width: 1 0 0 0;"
        );
        root.setBottom(bottomBar);

        Styles.applyFadeIn(root);
        return root;
    }

    // ── Card builder ──────────────────────────────────────────────────────────

    private VBox buildCard(TrainingType type) {
        Label iconLbl = new Label(icon(type));
        iconLbl.setStyle("-fx-font-size: 30px;");

        Label nameLbl = new Label(displayName(type));
        nameLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-letter-spacing: 1;" +
            "-fx-text-alignment: center;"
        );
        nameLbl.setAlignment(Pos.CENTER);
        nameLbl.setWrapText(true);
        nameLbl.setMaxWidth(170);

        Label descLbl = new Label(description(type));
        descLbl.setStyle(
            "-fx-text-fill: #3a5272; -fx-font-size: 11px; -fx-text-alignment: center; -fx-wrap-text: true;"
        );
        descLbl.setMaxWidth(170);
        descLbl.setAlignment(Pos.CENTER);
        descLbl.setWrapText(true);

        Label effectLbl = new Label(statEffect(type));
        effectLbl.setStyle(
            "-fx-text-fill: #00c8f0; -fx-font-size: 11px; -fx-font-weight: bold;" +
            "-fx-background-color: rgba(0,200,240,0.12);" +
            "-fx-background-radius: 8; -fx-padding: 2 12;"
        );

        VBox card = new VBox(8, iconLbl, nameLbl, descLbl, effectLbl);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(22, 16, 22, 16));
        card.setPrefWidth(195);
        card.setPrefHeight(160);
        card.setStyle(CARD_NORMAL);
        return card;
    }

    // ── Card metadata ─────────────────────────────────────────────────────────

    private static String displayName(TrainingType t) {
        switch (t) {
            case PASS:      return "PASSING DRILLS";
            case SHOOT:     return "SHOOTING PRACTICE";
            case DEFANCE:   return "DEFENSIVE WORK";
            case DRIBBLING: return "DRIBBLING DRILLS";
            case BLOCKING:  return "SHOT BLOCKING";
            case STEALING:  return "STEAL PRACTICE";
            default:        return t.name();
        }
    }

    private static String description(TrainingType t) {
        switch (t) {
            case PASS:      return "Improve passing accuracy\nand team movement";
            case SHOOT:     return "Improve finishing and\nattacking power";
            case DEFANCE:   return "Improve defensive\nawareness and tackling";
            case DRIBBLING: return "Improve ball handling\nand court movement";
            case BLOCKING:  return "Improve shot blocking\nand rim protection";
            case STEALING:  return "Improve steal ability\nand ball pressure";
            default:        return "";
        }
    }

    private static String icon(TrainingType t) {
        switch (t) {
            case PASS:      return "⚡";
            case SHOOT:     return "🎯";
            case DEFANCE:   return "🛡";
            case DRIBBLING: return "🔥";
            case BLOCKING:  return "🏀";
            case STEALING:  return "✂";
            default:        return "📋";
        }
    }

    private static String statEffect(TrainingType t) {
        switch (t) {
            case PASS:      return "+1 Passing";
            case SHOOT:     return "+1 Shooting";
            case DEFANCE:   return "+1 Defence";
            case DRIBBLING: return "+1 Dribbling";
            case BLOCKING:  return "+1 Blocking";
            case STEALING:  return "+1 Stealing";
            default:        return "+1 " + t.name();
        }
    }

    // ── Top bar ───────────────────────────────────────────────────────────────

    private HBox buildTopBar(String teamName, GameService gameService) {
        HBox bar = new HBox(14);
        bar.setStyle(Styles.TOPBAR);
        bar.setPadding(new Insets(12, 16, 12, 16));
        bar.setAlignment(Pos.CENTER_LEFT);

        Label sectionLbl = new Label("🏋  TRAINING");
        sectionLbl.setStyle(
            "-fx-text-fill: #f48fb1; -fx-font-size: 13px;" +
            "-fx-font-weight: bold; -fx-letter-spacing: 2;"
        );
        Label teamLbl = new Label(teamName);
        teamLbl.setStyle(Styles.TEAM_NAME_LABEL);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label weekLbl = new Label("Week " + gameService.getCurrentWeek());
        weekLbl.setStyle(Styles.TOPBAR_LABEL);

        bar.getChildren().addAll(sectionLbl, teamLbl, spacer, weekLbl);
        return bar;
    }
}
