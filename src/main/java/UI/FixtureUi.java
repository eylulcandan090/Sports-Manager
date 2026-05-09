package UI;

import Database.Database;
import Model.Fixture;
import Model.Team;
import Repository.GameRepo;
import Repository.TeamRepo;
import Service.FixtureService;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class FixtureUi {

    private static final String[] KICKOFF_TIMES = {
        "14:00", "16:30", "19:00", "20:45", "15:30", "17:00", "12:30", "18:00"
    };

    public Parent getView(FixtureService fixtureService) {
        Database db = Database.getInstance();
        TeamRepo teamRepo  = new TeamRepo(db.getConnection());
        GameRepo gameRepo  = new GameRepo(db.getConnection());
        int userTeamId = gameRepo.getGameTeamId();

        List<Fixture> fixtures = fixtureService.getFixtures();

        // Pre-load all team names in one pass to avoid per-fixture DB queries
        Map<Integer, String> teamNames = new HashMap<>();
        Set<Integer> ids = new HashSet<>();
        for (Fixture f : fixtures) { ids.add(f.getHomeId()); ids.add(f.getAwayId()); }
        for (int id : ids) {
            Team t = teamRepo.getTeamByTeamId(id);
            if (t != null) teamNames.put(id, t.getName());
        }

        // First unplayed user fixture → gets NEXT MATCH badge
        Fixture nextUserFixture = null;
        for (Fixture f : fixtures) {
            if (!f.getIsPlayed() &&
                (f.getHomeId() == userTeamId || f.getAwayId() == userTeamId)) {
                nextUserFixture = f;
                break;
            }
        }
        final Fixture nextFixture = nextUserFixture;

        // Group by week, sorted
        Map<Integer, List<Fixture>> byWeek = new TreeMap<>();
        for (Fixture f : fixtures)
            byWeek.computeIfAbsent(f.getWeek(), k -> new ArrayList<>()).add(f);

        // ── Root ──────────────────────────────────────────────────────────────
        BorderPane root = new BorderPane();
        root.setMaxWidth(Double.MAX_VALUE);
        root.setMaxHeight(Double.MAX_VALUE);
        root.setStyle(
            "-fx-background-color: radial-gradient(center 30% 20%, radius 70%, #0d1f3a, #020c1b);" +
            "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
        );
        root.setTop(buildTopBar());

        // ── Title ─────────────────────────────────────────────────────────────
        Label titleLbl = new Label("FIXTURES & SCHEDULE");
        titleLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;" +
            "-fx-font-family: 'Impact', 'Arial Black', sans-serif; -fx-letter-spacing: 3;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,200,255,1.0), 42, 0.45, 0, 2)," +
            "            dropshadow(gaussian, rgba(0,80,180,0.7), 16, 0.25, 0, 3);"
        );
        Label subtitleLbl = new Label("Upcoming league matches and weekly schedule");
        subtitleLbl.setStyle("-fx-text-fill: #90caf9; -fx-font-size: 13px;");

        VBox titleBox = new VBox(6, titleLbl, subtitleLbl);
        titleBox.setAlignment(Pos.CENTER);

        // ── Weekly sections ────────────────────────────────────────────────────
        VBox weeksBox = new VBox(20);
        weeksBox.setMaxWidth(Double.MAX_VALUE);

        for (Map.Entry<Integer, List<Fixture>> entry : byWeek.entrySet()) {
            int week = entry.getKey();
            List<Fixture> wfList = entry.getValue();

            // Week header
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

            VBox weekBox = new VBox(8, weekHeader);
            weekBox.setMaxWidth(Double.MAX_VALUE);

            for (Fixture f : wfList) {
                boolean isNext  = (f == nextFixture);
                boolean isUser  = (f.getHomeId() == userTeamId || f.getAwayId() == userTeamId);
                String homeName = teamNames.getOrDefault(f.getHomeId(), "Unknown");
                String awayName = teamNames.getOrDefault(f.getAwayId(), "Unknown");
                String kickoff  = KICKOFF_TIMES[
                    (f.getHomeId() + f.getAwayId() + f.getWeek()) % KICKOFF_TIMES.length
                ];
                weekBox.getChildren().add(
                    buildFixtureCard(f, homeName, awayName, userTeamId, isNext, isUser, kickoff)
                );
            }

            weeksBox.getChildren().add(weekBox);
        }

        // ── Center scroll ─────────────────────────────────────────────────────
        VBox content = new VBox(24, titleBox, weeksBox);
        content.setPadding(new Insets(24, 36, 24, 36));
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

        // ── Bottom bar ─────────────────────────────────────────────────────────
        Button backBtn = new Button("← Back");
        Styles.styleDangerButton(backBtn);
        backBtn.setOnAction(e -> Navigator.navigate(ViewType.MENU));

        HBox bottomBar = new HBox(backBtn);
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

    // ── Fixture card ──────────────────────────────────────────────────────────

    private Node buildFixtureCard(Fixture f, String homeName, String awayName,
                                   int userTeamId, boolean isNext,
                                   boolean isUser, String kickoff) {
        boolean played = f.getIsPlayed();

        // Border / background based on state
        String bg      = played ? "#060d1a" : (isNext ? "#0b1e40" : "#0a1628");
        String border  = isNext  ? "#00c8f0"
                       : isUser  ? "#1565c0"
                       : played  ? "#0a1628"
                       : "#1a3050";
        String bWidth  = (isNext || isUser) ? "2" : "1";
        String glow    = isNext
            ? "-fx-effect: dropshadow(gaussian, rgba(0,200,240,0.50), 22, 0, 0, 0);"
            : isUser
            ? "-fx-effect: dropshadow(gaussian, rgba(21,101,192,0.35), 16, 0, 0, 0);"
            : "";

        String baseStyle =
            "-fx-background-color: " + bg + ";" +
            "-fx-border-color: " + border + ";" +
            "-fx-border-width: " + bWidth + ";" +
            "-fx-border-radius: 10; -fx-background-radius: 10;" + glow;

        // ── Home side (right-aligned) ──────────────────────────────────────────
        ImageView homeLogo = logoView(homeName, 32);
        Label homeLbl = new Label(homeName.toUpperCase());
        homeLbl.setStyle(
            "-fx-text-fill: " + (played ? "#2a4060" : "white") + ";" +
            "-fx-font-size: 13px; -fx-font-weight: bold;"
        );
        HBox homeBox = new HBox(10, homeLbl, homeLogo);
        homeBox.setAlignment(Pos.CENTER_RIGHT);
        homeBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(homeBox, Priority.ALWAYS);

        // ── Center VS / FT ─────────────────────────────────────────────────────
        Label vsLbl = new Label(played ? "·" : "VS");
        vsLbl.setStyle(
            "-fx-text-fill: " + (played ? "#1e3a5a" : "#90caf9") + ";" +
            "-fx-font-size: 14px; -fx-font-weight: bold;"
        );
        Label timeLbl = new Label(played ? "FT" : kickoff);
        timeLbl.setStyle(
            "-fx-text-fill: " + (played ? "#1a3050" : "#3a5272") + "; -fx-font-size: 10px;"
        );
        VBox center = new VBox(2, vsLbl, timeLbl);
        center.setAlignment(Pos.CENTER);
        center.setMinWidth(72);
        center.setPadding(new Insets(0, 10, 0, 10));

        if (played) {
            Label ftBadge = new Label("PLAYED");
            ftBadge.setStyle(
                "-fx-text-fill: #1e3a5a; -fx-font-size: 8px; -fx-font-weight: bold;" +
                "-fx-padding: 1 5; -fx-border-color: #1e3a5a;" +
                "-fx-border-width: 1; -fx-border-radius: 3;"
            );
            center.getChildren().add(ftBadge);
        }

        // ── Away side (left-aligned) ────────────────────────────────────────────
        ImageView awayLogo = logoView(awayName, 32);
        Label awayLbl = new Label(awayName.toUpperCase());
        awayLbl.setStyle(
            "-fx-text-fill: " + (played ? "#2a4060" : "white") + ";" +
            "-fx-font-size: 13px; -fx-font-weight: bold;"
        );
        HBox awayBox = new HBox(10, awayLogo, awayLbl);
        awayBox.setAlignment(Pos.CENTER_LEFT);
        awayBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(awayBox, Priority.ALWAYS);

        // ── Assemble card ──────────────────────────────────────────────────────
        HBox card = new HBox(0, homeBox, center, awayBox);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(12, 18, 12, 18));
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle(baseStyle);

        // Hover glow on upcoming fixtures
        if (!played) {
            String hoverStyle =
                "-fx-background-color: " + (isNext ? "#0d2550" : "#0c1e38") + ";" +
                "-fx-border-color: " + (isNext ? "#48e0ff" : "#2a5aaa") + ";" +
                "-fx-border-width: " + bWidth + ";" +
                "-fx-border-radius: 10; -fx-background-radius: 10;" +
                "-fx-effect: dropshadow(gaussian," +
                (isNext ? "rgba(0,200,240,0.65)" : "rgba(42,90,170,0.45)") +
                ", 20, 0, 0, 0);";
            card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
            card.setOnMouseExited(e  -> card.setStyle(baseStyle));
        }

        // ── NEXT MATCH badge overlay ───────────────────────────────────────────
        if (isNext) {
            Label badge = new Label("▶  NEXT MATCH");
            badge.setStyle(
                "-fx-background-color: #00c8f0; -fx-text-fill: #010811;" +
                "-fx-font-size: 9px; -fx-font-weight: bold;" +
                "-fx-padding: 2 8; -fx-background-radius: 0 8 0 8;"
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
        HBox bar = new HBox(14);
        bar.setStyle(Styles.TOPBAR);
        bar.setPadding(new Insets(12, 16, 12, 16));
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setMaxWidth(Double.MAX_VALUE);

        Label sectionLbl = new Label("📅  FIXTURES & SCHEDULE");
        sectionLbl.setStyle(
            "-fx-text-fill: #f48fb1; -fx-font-size: 13px;" +
            "-fx-font-weight: bold; -fx-letter-spacing: 2;"
        );
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bar.getChildren().addAll(sectionLbl, spacer);
        return bar;
    }

    // ── Logo loader ───────────────────────────────────────────────────────────

    private ImageView logoView(String teamName, double size) {
        ImageView iv = new ImageView();
        URL url = getClass().getResource(logoPath(teamName));
        if (url != null) {
            iv.setImage(new Image(url.toExternalForm()));
            iv.setFitWidth(size);
            iv.setFitHeight(size);
            iv.setPreserveRatio(true);
        } else {
            iv.setFitWidth(size);
            iv.setFitHeight(size);
        }
        return iv;
    }

    private static String logoPath(String n) {
        switch (n) {
            case "Arsenal":             return "/images/teams/arsenal.png";
            case "Aston Villa":         return "/images/teams/astonvilla.png";
            case "Bournemouth":         return "/images/teams/bournemouth.png";
            case "Brentford": case "Brentford FC":          return "/images/teams/brentford.png";
            case "Brighton": case "Brighton & Hove Albion": return "/images/teams/brighton.png";
            case "Chelsea":             return "/images/teams/chelsea.png";
            case "Crystal Palace":      return "/images/teams/crystal.png";
            case "Everton":             return "/images/teams/everton.png";
            case "Nottingham Forest": case "Nott'm Forest": return "/images/teams/forest.png";
            case "Fulham":              return "/images/teams/fullham.png";
            case "Ipswich": case "Ipswich Town":            return "/images/teams/ıpswich.png";
            case "Leicester": case "Leicester City":        return "/images/teams/leicester.png";
            case "Liverpool":           return "/images/teams/liverpool.png";
            case "Manchester City":     return "/images/teams/manchester.png";
            case "Manchester United":   return "/images/teams/manchesterunited.png";
            case "Newcastle United": case "Newcastle":      return "/images/teams/newcastleunited.png";
            case "Southampton":         return "/images/teams/southampton.png";
            case "Tottenham": case "Tottenham Hotspur":     return "/images/teams/tottenham.png";
            case "West Ham": case "West Ham United":        return "/images/teams/westham.png";
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
