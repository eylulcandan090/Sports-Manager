package UI;

import Database.Database;
import Model.Fixture;
import Model.Team;
import Repository.GameRepo;
import Repository.TeamRepo;
import Service.FixtureService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.*;

public class FixtureUi {

    private static final String[] KICKOFF_TIMES = {
        "14:00", "16:30", "19:00", "20:45", "15:30", "17:00", "12:30", "18:00"
    };

    // Shared card styles — built once, reused for every card
    private static final String CARD_NORMAL =
        "-fx-background-color: #0a1628; -fx-border-color: #1a3050;" +
        "-fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8;";

    private static final String CARD_USER =
        "-fx-background-color: #0a1a38; -fx-border-color: #1565c0;" +
        "-fx-border-width: 1.5; -fx-border-radius: 8; -fx-background-radius: 8;";

    private static final String CARD_NEXT =
        "-fx-background-color: #0b1e40; -fx-border-color: #00c8f0;" +
        "-fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8;";

    private static final String CARD_PLAYED =
        "-fx-background-color: #060d1a; -fx-border-color: #0a1628;" +
        "-fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8;";

    private static final String CARD_HOVER =
        "-fx-background-color: #0d2040; -fx-border-color: #2a5aaa;" +
        "-fx-border-width: 1.5; -fx-border-radius: 8; -fx-background-radius: 8;";

    private static final String CARD_NEXT_HOVER =
        "-fx-background-color: #0d2550; -fx-border-color: #48e0ff;" +
        "-fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8;";

    public Parent getView(FixtureService fixtureService) {
        Database db    = Database.getInstance();
        TeamRepo teamRepo = new TeamRepo(db.getConnection());
        GameRepo gameRepo = new GameRepo(db.getConnection());
        int userTeamId = gameRepo.getGameTeamId();

        List<Fixture> fixtures = fixtureService.getFixtures();

        // Pre-cache all team names in one pass (avoids per-card DB calls)
        Map<Integer, String> nameCache = new HashMap<>();
        Set<Integer> ids = new HashSet<>();
        for (Fixture f : fixtures) { ids.add(f.getHomeId()); ids.add(f.getAwayId()); }
        for (int id : ids) {
            Team t = teamRepo.getTeamByTeamId(id);
            if (t != null) nameCache.put(id, t.getName());
        }

        // First unplayed user fixture → NEXT MATCH badge
        Fixture nextUserFixture = null;
        for (Fixture f : fixtures) {
            if (!f.getIsPlayed() &&
                (f.getHomeId() == userTeamId || f.getAwayId() == userTeamId)) {
                nextUserFixture = f;
                break;
            }
        }
        final Fixture nextFixture = nextUserFixture;

        // Group by week
        Map<Integer, List<Fixture>> byWeek = new TreeMap<>();
        for (Fixture f : fixtures)
            byWeek.computeIfAbsent(f.getWeek(), k -> new ArrayList<>()).add(f);

        // ── Root ──────────────────────────────────────────────────────────────
        BorderPane root = new BorderPane();
        root.setMaxWidth(Double.MAX_VALUE);
        root.setMaxHeight(Double.MAX_VALUE);
        root.setStyle(
            "-fx-background-color: #020c1b;" +
            "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
        );
        root.setTop(buildTopBar());

        // ── Title ─────────────────────────────────────────────────────────────
        Label titleLbl = new Label("FIXTURES & SCHEDULE");
        titleLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;" +
            "-fx-font-family: 'Impact', 'Arial Black', sans-serif; -fx-letter-spacing: 3;"
        );
        Label subtitleLbl = new Label("Upcoming league matches and weekly schedule");
        subtitleLbl.setStyle("-fx-text-fill: #607080; -fx-font-size: 12px;");

        VBox titleBox = new VBox(5, titleLbl, subtitleLbl);
        titleBox.setAlignment(Pos.CENTER);

        // ── Weekly sections ────────────────────────────────────────────────────
        VBox weeksBox = new VBox(18);
        weeksBox.setMaxWidth(Double.MAX_VALUE);

        for (Map.Entry<Integer, List<Fixture>> entry : byWeek.entrySet()) {
            int week = entry.getKey();

            // Week header row
            Label weekLbl = new Label("WEEK  " + week);
            weekLbl.setStyle(
                "-fx-text-fill: #2a4060; -fx-font-size: 10px;" +
                "-fx-font-weight: bold; -fx-letter-spacing: 3;"
            );
            Region hLine = new Region();
            hLine.setPrefHeight(1);
            hLine.setMaxWidth(Double.MAX_VALUE);
            hLine.setStyle("-fx-background-color: #0d2040;");
            HBox weekHeader = new HBox(10, weekLbl, hLine);
            weekHeader.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(hLine, Priority.ALWAYS);

            VBox weekBox = new VBox(6, weekHeader);
            weekBox.setMaxWidth(Double.MAX_VALUE);

            for (Fixture f : entry.getValue()) {
                boolean isNext = (f == nextFixture);
                boolean isUser = (f.getHomeId() == userTeamId || f.getAwayId() == userTeamId);
                String home    = nameCache.getOrDefault(f.getHomeId(), "?");
                String away    = nameCache.getOrDefault(f.getAwayId(), "?");
                String kickoff = KICKOFF_TIMES[
                    (f.getHomeId() + f.getAwayId() + f.getWeek()) % KICKOFF_TIMES.length
                ];
                weekBox.getChildren().add(
                    buildCard(f.getIsPlayed(), isNext, isUser, home, away, kickoff)
                );
            }

            weeksBox.getChildren().add(weekBox);
        }

        // ── Scroll ────────────────────────────────────────────────────────────
        VBox content = new VBox(20, titleBox, weeksBox);
        content.setPadding(new Insets(20, 32, 20, 32));
        content.setMaxWidth(Double.MAX_VALUE);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setMaxWidth(Double.MAX_VALUE);
        scroll.setMaxHeight(Double.MAX_VALUE);
        scroll.setStyle(
            "-fx-background: transparent; -fx-background-color: transparent;" +
            "-fx-border-color: transparent;"
        );
        root.setCenter(scroll);

        // ── Bottom ────────────────────────────────────────────────────────────
        Button backBtn = new Button("← Back");
        Styles.styleDangerButton(backBtn);
        backBtn.setOnAction(e -> Navigator.navigate(ViewType.MENU));

        HBox bottomBar = new HBox(backBtn);
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(12, 20, 12, 20));
        bottomBar.setStyle(
            "-fx-background-color: #010d1f;" +
            "-fx-border-color: #1565c0; -fx-border-width: 1 0 0 0;"
        );
        root.setBottom(bottomBar);

        Styles.applyFadeIn(root);
        return root;
    }

    // ── Fixture card (text-only, no images) ───────────────────────────────────

    private Node buildCard(boolean played, boolean isNext, boolean isUser,
                           String home, String away, String kickoff) {

        String base = played ? CARD_PLAYED : isNext ? CARD_NEXT : isUser ? CARD_USER : CARD_NORMAL;

        // Home label
        Label homeLbl = new Label(home.toUpperCase());
        homeLbl.setStyle(
            "-fx-text-fill: " + (played ? "#2a4060" : "white") + ";" +
            "-fx-font-size: 13px; -fx-font-weight: bold;"
        );
        HBox homeBox = new HBox(homeLbl);
        homeBox.setAlignment(Pos.CENTER_RIGHT);
        homeBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(homeBox, Priority.ALWAYS);

        // Center
        Label vsLbl = new Label(played ? "·" : "VS");
        vsLbl.setStyle(
            "-fx-text-fill: " + (played ? "#1a3050" : isNext ? "#00c8f0" : "#455a72") + ";" +
            "-fx-font-size: 12px; -fx-font-weight: bold;"
        );
        Label timeLbl = new Label(played ? "FT" : kickoff);
        timeLbl.setStyle(
            "-fx-text-fill: " + (played ? "#1a3050" : "#2a4060") + "; -fx-font-size: 10px;"
        );
        VBox centerBox = new VBox(2, vsLbl, timeLbl);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setMinWidth(64);
        centerBox.setPadding(new Insets(0, 12, 0, 12));

        // Away label
        Label awayLbl = new Label(away.toUpperCase());
        awayLbl.setStyle(
            "-fx-text-fill: " + (played ? "#2a4060" : "white") + ";" +
            "-fx-font-size: 13px; -fx-font-weight: bold;"
        );
        HBox awayBox = new HBox(awayLbl);
        awayBox.setAlignment(Pos.CENTER_LEFT);
        awayBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(awayBox, Priority.ALWAYS);

        HBox card = new HBox(0, homeBox, centerBox, awayBox);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(10, 16, 10, 16));
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle(base);

        // Hover (only for upcoming)
        if (!played) {
            String hover = isNext ? CARD_NEXT_HOVER : CARD_HOVER;
            card.setOnMouseEntered(e -> card.setStyle(hover));
            card.setOnMouseExited(e  -> card.setStyle(base));
        }

        // NEXT MATCH badge — overlay via StackPane
        if (isNext) {
            Label badge = new Label("▶  NEXT MATCH");
            badge.setStyle(
                "-fx-background-color: #00c8f0; -fx-text-fill: #010811;" +
                "-fx-font-size: 9px; -fx-font-weight: bold;" +
                "-fx-padding: 2 7; -fx-background-radius: 0 7 0 7;"
            );
            StackPane wrapper = new StackPane(card, badge);
            StackPane.setAlignment(badge, Pos.TOP_RIGHT);
            wrapper.setMaxWidth(Double.MAX_VALUE);
            return wrapper;
        }

        return card;
    }

    // ── Top bar ───────────────────────────────────────────────────────────────

    private HBox buildTopBar() {
        HBox bar = new HBox();
        bar.setStyle(Styles.TOPBAR);
        bar.setPadding(new Insets(12, 16, 12, 16));
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setMaxWidth(Double.MAX_VALUE);

        Label lbl = new Label("📅  FIXTURES & SCHEDULE");
        lbl.setStyle(
            "-fx-text-fill: #f48fb1; -fx-font-size: 13px;" +
            "-fx-font-weight: bold; -fx-letter-spacing: 2;"
        );
        bar.getChildren().add(lbl);
        return bar;
    }
}
