package UI;

import Database.Database;
import Model.Fixture;
import Model.GameSession;
import Model.Team;
import Repository.GameRepo;
import Repository.TeamRepo;
import Service.FixtureService;
import Service.GameService;
import Service.LeagueService;
import Service.TeamService;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class MainMenuUi {

    private BorderPane root;

    public Parent getView(GameService gameService, TeamService teamService,
                          FixtureService fixtureService, LeagueService leagueService,
                          TeamRepo teamRepo) {
        root = new BorderPane();
        root.setStyle(
            "-fx-background-color: radial-gradient(center 30% 20%, radius 70%," +
            " #0d1f3a, #020c1b);" +
            "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
        );
        root.setTop(createTopBar(gameService));
        root.setLeft(createMenu());
        root.setCenter(createDashboard(gameService, teamService, fixtureService, leagueService, teamRepo));
        return root;
    }

    // ── Dashboard ─────────────────────────────────────────────────────────────

    private ScrollPane createDashboard(GameService gameService, TeamService teamService,
                                       FixtureService fixtureService, LeagueService leagueService,
                                       TeamRepo teamRepo) {
        int teamId = gameService.getGameTeamId();
        Team myTeam = teamRepo.getTeamByTeamId(teamId);

        Fixture nextFixture = null;
        for (Fixture f : fixtureService.getFixtures()) {
            if (!f.getIsPlayed() && (f.getHomeId() == teamId || f.getAwayId() == teamId)) {
                nextFixture = f;
                break;
            }
        }

        int leagueId = leagueService.getLeagueIdByTeamName(teamId);
        ArrayList<Team> leagueTeams = teamService.getAllTeamsByLeagueId(leagueId);
        Collections.sort(leagueTeams);
        int position = 1;
        for (Team t : leagueTeams) {
            if (t.getId() == teamId) break;
            position++;
        }

        int healthy = teamService.getHealthyPlayers(teamId).size();
        int total   = teamService.getPlayersByTeamId(teamId).size();

        VBox nextMatchCard = buildNextMatchCard(nextFixture, teamRepo);
        VBox overviewCard  = buildOverviewCard(myTeam, total);
        VBox leagueCard    = buildLeagueCard(position, leagueTeams, myTeam);
        VBox fitnessCard   = buildFitnessCard(healthy, total);

        HBox bottomRow = new HBox(14, overviewCard, leagueCard, fitnessCard);
        HBox.setHgrow(overviewCard, Priority.ALWAYS);
        HBox.setHgrow(leagueCard,   Priority.ALWAYS);
        HBox.setHgrow(fitnessCard,  Priority.ALWAYS);

        VBox dashboard = new VBox(16, nextMatchCard, bottomRow);
        dashboard.setPadding(new Insets(20));

        ScrollPane scroll = new ScrollPane(dashboard);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle(
            "-fx-background: transparent; -fx-background-color: transparent;" +
            "-fx-border-color: transparent;"
        );
        return scroll;
    }

    // ── Next Match card ───────────────────────────────────────────────────────

    private VBox buildNextMatchCard(Fixture fixture, TeamRepo teamRepo) {
        String homeName = "—", awayName = "—";
        String homeRaw  = "",  awayRaw  = "";
        String weekText = "SEASON COMPLETE";

        if (fixture != null) {
            homeRaw  = teamRepo.getTeamByTeamId(fixture.getHomeId()).getName();
            awayRaw  = teamRepo.getTeamByTeamId(fixture.getAwayId()).getName();
            homeName = homeRaw.toUpperCase();
            awayName = awayRaw.toUpperCase();
            weekText = "WEEK  " + fixture.getWeek();
        }

        Label sectionLbl = new Label("⚽  NEXT MATCH");
        sectionLbl.setStyle(sectionHeaderStyle("#f48fb1"));

        Label weekLbl = new Label(weekText);
        weekLbl.setStyle("-fx-text-fill: #90caf9; -fx-font-size: 11px; -fx-letter-spacing: 1;");

        Region hSp = new Region();
        HBox.setHgrow(hSp, Priority.ALWAYS);
        HBox cardHeader = new HBox(sectionLbl, hSp, weekLbl);
        cardHeader.setAlignment(Pos.CENTER_LEFT);

        // ── Home side ─────────────────────────────────────────────────────────
        ImageView homeLogo = loadLogo(homeRaw, 40);
        Label homeLbl = new Label(homeName);
        homeLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;"
        );
        HBox homeContent = new HBox(10, homeLogo, homeLbl);
        homeContent.setAlignment(Pos.CENTER_RIGHT);
        homeContent.setPadding(new Insets(10, 18, 10, 18));
        homeContent.setStyle(
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #5a0000, #9b1c1c88);" +
            "-fx-background-radius: 10 0 0 10;"
        );
        homeContent.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(homeContent, Priority.ALWAYS);

        // ── VS badge with pulse animation ─────────────────────────────────────
        Label vsLbl = new Label("VS");
        vsLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;" +
            "-fx-padding: 10 24;" +
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #aa0000ee, #0000aaee);" +
            "-fx-border-color: rgba(255,255,255,0.25); -fx-border-width: 0 1 0 1;" +
            "-fx-effect: dropshadow(gaussian, rgba(220,100,255,0.85), 26, 0, 0, 0);" +
            "-fx-min-width: 74px;"
        );
        vsLbl.setAlignment(Pos.CENTER);

        FadeTransition pulse = new FadeTransition(Duration.millis(900), vsLbl);
        pulse.setFromValue(0.75);
        pulse.setToValue(1.0);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(FadeTransition.INDEFINITE);
        pulse.play();

        // ── Away side ─────────────────────────────────────────────────────────
        Label awayLbl = new Label(awayName);
        awayLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;"
        );
        ImageView awayLogo = loadLogo(awayRaw, 40);
        HBox awayContent = new HBox(10, awayLbl, awayLogo);
        awayContent.setAlignment(Pos.CENTER_LEFT);
        awayContent.setPadding(new Insets(10, 18, 10, 18));
        awayContent.setStyle(
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #0e2f6688, #1060c0);" +
            "-fx-background-radius: 0 10 10 0;"
        );
        awayContent.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(awayContent, Priority.ALWAYS);

        HBox teamsRow = new HBox(0, homeContent, vsLbl, awayContent);
        teamsRow.setAlignment(Pos.CENTER);

        Button playBtn = new Button("▶   PLAY MATCH");
        playBtn.setPrefWidth(190);
        Styles.styleGreenButton(playBtn);
        if (fixture == null) playBtn.setDisable(true);
        playBtn.setOnAction(e -> Navigator.navigate(ViewType.MYSQUAD));

        HBox btnRow = new HBox(playBtn);
        btnRow.setAlignment(Pos.CENTER);
        btnRow.setPadding(new Insets(14, 0, 4, 0));

        VBox card = new VBox(12, cardHeader, divider(), teamsRow, btnRow);
        card.setPadding(new Insets(18, 20, 18, 20));
        card.setStyle(cardStyle("#1e4a8a"));
        return card;
    }

    // ── Team Overview card ────────────────────────────────────────────────────

    private VBox buildOverviewCard(Team team, int totalPlayers) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(16, 18, 16, 18));
        card.setStyle(cardStyle("#1e3a5a"));
        card.getChildren().addAll(
            cardHeader("👥  TEAM OVERVIEW", "#f48fb1"),
            divider(),
            bigStat(String.valueOf(team.getPoint()), "POINTS"),
            statRow("Squad size", totalPlayers + " players")
        );
        return card;
    }

    // ── League Position card ──────────────────────────────────────────────────

    private VBox buildLeagueCard(int position, ArrayList<Team> sorted, Team myTeam) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(16, 18, 16, 18));
        card.setStyle(cardStyle("#1e3a5a"));
        card.getChildren().addAll(
            cardHeader("🏆  LEAGUE POSITION", "#ffd54f"),
            divider(),
            bigStat(ordinal(position), "OF " + sorted.size() + " TEAMS")
        );
        if (!sorted.isEmpty()) {
            Team leader = sorted.get(0);
            int gap = leader.getPoint() - myTeam.getPoint();
            card.getChildren().add(statRow("Leader", leader.getName()));
            card.getChildren().add(statRow("Gap", gap <= 0 ? "—" : "-" + gap + " pts"));
        }
        return card;
    }

    // ── Fitness card ──────────────────────────────────────────────────────────

    private VBox buildFitnessCard(int healthy, int total) {
        int pct = total > 0 ? (healthy * 100 / total) : 100;
        String color = pct >= 80 ? "#26a69a" : pct >= 60 ? "#ffd54f" : "#ff4757";
        VBox card = new VBox(10);
        card.setPadding(new Insets(16, 18, 16, 18));
        card.setStyle(cardStyle("#1e3a5a"));
        Label pctLbl = new Label(pct + "%");
        pctLbl.setStyle(
            "-fx-text-fill: " + color + "; -fx-font-size: 30px; -fx-font-weight: bold;" +
            "-fx-effect: dropshadow(gaussian, " + color + "88, 10, 0, 0, 0);"
        );
        card.getChildren().addAll(
            cardHeader("💪  TEAM FITNESS", "#26a69a"),
            divider(), pctLbl,
            statRow("Fit players", healthy + " / " + total),
            statRow("Injured",     (total - healthy) + " players")
        );
        return card;
    }

    // ── Logo loader ───────────────────────────────────────────────────────────

    private ImageView loadLogo(String teamName, double size) {
        ImageView iv = new ImageView();
        if (teamName == null || teamName.isEmpty()) return iv;
        String path = logoPath(teamName);
        URL url = getClass().getResource(path);
        if (url != null) {
            iv.setImage(new Image(url.toExternalForm()));
            iv.setFitWidth(size);
            iv.setFitHeight(size);
            iv.setPreserveRatio(true);
        }
        return iv;
    }

    private static String logoPath(String n) {
        switch (n) {
            case "Arsenal":             return "/images/teams/arsenal.png";
            case "Aston Villa":         return "/images/teams/astonvilla.png";
            case "Bournemouth":         return "/images/teams/bournemouth.png";
            case "Brentford": case "Brentford FC": return "/images/teams/brentford.png";
            case "Brighton": case "Brighton & Hove Albion": return "/images/teams/brighton.png";
            case "Chelsea":             return "/images/teams/chelsea.png";
            case "Crystal Palace":      return "/images/teams/crystal.png";
            case "Everton":             return "/images/teams/everton.png";
            case "Nottingham Forest": case "Nott'm Forest": return "/images/teams/forest.png";
            case "Fulham":              return "/images/teams/fullham.png";
            case "Ipswich": case "Ipswich Town": return "/images/teams/ıpswich.png";
            case "Leicester": case "Leicester City": return "/images/teams/leicester.png";
            case "Liverpool":           return "/images/teams/liverpool.png";
            case "Manchester City":     return "/images/teams/manchester.png";
            case "Manchester United":   return "/images/teams/manchesterunited.png";
            case "Newcastle United": case "Newcastle": return "/images/teams/newcastleunited.png";
            case "Southampton":         return "/images/teams/southampton.png";
            case "Tottenham": case "Tottenham Hotspur": return "/images/teams/tottenham.png";
            case "West Ham": case "West Ham United": return "/images/teams/westham.png";
            case "Wolverhampton": case "Wolverhampton Wanderers": case "Wolves": return "/images/teams/wolverhampton.png";
            case "Dallas Mavericks":    return "/images/basketteams/dallas.png";
            case "Denver Nuggets":      return "/images/basketteams/denvernuggets.png";
            case "Golden State Warriors": return "/images/basketteams/goldenstate.png";
            case "Houston Rockets":     return "/images/basketteams/houston.png";
            case "Los Angeles Lakers":  return "/images/basketteams/losangeleslakers.png";
            case "Miami Heat":          return "/images/basketteams/miamiheat.png";
            case "Milwaukee Bucks":     return "/images/basketteams/milwaukee.png";
            case "Minnesota Timberwolves": return "/images/basketteams/minnesotatimber.png";
            case "New York Knicks":     return "/images/basketteams/newyorkkknicks.png";
            case "OKC Thunder":         return "/images/basketteams/okcthunder.png";
            case "Philadelphia 76ers":  return "/images/basketteams/philedelphia.png";
            case "Phoenix Suns":        return "/images/basketteams/phoenix.png";
            case "San Antonio Spurs":   return "/images/basketteams/sanantonio.png";
            default:                    return "/images/teams/default_team.png";
        }
    }

    // ── Sidebar ───────────────────────────────────────────────────────────────

    private VBox createMenu() {
        VBox menu = new VBox(2);
        menu.setStyle(
            "-fx-background-color: #010d1f;" +
            "-fx-border-color: #1565c0; -fx-border-width: 0 1 0 0;"
        );
        menu.setPadding(new Insets(16, 10, 16, 10));
        menu.setPrefWidth(160);

        Button teamBtn     = sidebarBtn("👥  My Team");
        Button trainingBtn = sidebarBtn("🏋  Training");
        Button fixturesBtn = sidebarBtn("📅  Fixtures");
        Button tableBtn    = sidebarBtn("🏆  League Table");

        teamBtn.setOnAction(e     -> Navigator.navigate(ViewType.MYTEAM));
        trainingBtn.setOnAction(e -> Navigator.navigate(ViewType.TRAINING));
        fixturesBtn.setOnAction(e -> Navigator.navigate(ViewType.FIXTURE));
        tableBtn.setOnAction(e    -> Navigator.navigate(ViewType.LEAGUETABLE));

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button backBtn = new Button("← Main Menu");
        Styles.styleDangerButton(backBtn);
        backBtn.setOnAction(e -> Navigator.navigate(ViewType.START));

        menu.getChildren().addAll(teamBtn, trainingBtn, fixturesBtn, tableBtn, spacer, backBtn);
        return menu;
    }

    private Button sidebarBtn(String text) {
        Button btn = new Button(text);
        final String normal =
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #90caf9; -fx-font-size: 13px;" +
            "-fx-alignment: CENTER-LEFT; -fx-padding: 10 16;" +
            "-fx-background-radius: 8; -fx-pref-width: 145px; -fx-cursor: hand;";
        final String hover =
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #1565c033, #1565c011);" +
            "-fx-text-fill: #e3f2fd; -fx-font-size: 13px;" +
            "-fx-alignment: CENTER-LEFT; -fx-padding: 10 16;" +
            "-fx-background-radius: 8; -fx-pref-width: 145px; -fx-cursor: hand;" +
            "-fx-border-color: #1565c066; -fx-border-width: 0 0 0 3; -fx-border-radius: 0;";
        final String pressed =
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #1565c055, #1565c022);" +
            "-fx-text-fill: white; -fx-font-size: 13px;" +
            "-fx-alignment: CENTER-LEFT; -fx-padding: 10 16;" +
            "-fx-background-radius: 8; -fx-pref-width: 145px; -fx-cursor: hand;" +
            "-fx-border-color: #42a5f5; -fx-border-width: 0 0 0 3; -fx-border-radius: 0;" +
            "-fx-effect: dropshadow(gaussian, rgba(66,165,245,0.35), 10, 0, 0, 0);";
        btn.setStyle(normal);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(normal));
        btn.setOnMousePressed(e -> btn.setStyle(pressed));
        btn.setOnMouseReleased(e -> btn.setStyle(hover));
        return btn;
    }

    // ── Small helpers ─────────────────────────────────────────────────────────

    private String cardStyle(String borderColor) {
        return "-fx-background-color: #0a1628; -fx-border-color: " + borderColor + ";" +
               "-fx-border-width: 1; -fx-border-radius: 12; -fx-background-radius: 12;" +
               "-fx-effect: dropshadow(gaussian, rgba(0,80,200,0.2), 18, 0, 0, 4);";
    }

    private String sectionHeaderStyle(String color) {
        return "-fx-text-fill: " + color + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 2;";
    }

    private Label cardHeader(String text, String color) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 1;");
        return lbl;
    }

    private VBox bigStat(String value, String label) {
        Label valLbl = new Label(value);
        valLbl.setStyle("-fx-text-fill: #e3f2fd; -fx-font-size: 30px; -fx-font-weight: bold;");
        Label keyLbl = new Label(label);
        keyLbl.setStyle("-fx-text-fill: #607080; -fx-font-size: 10px; -fx-letter-spacing: 1;");
        return new VBox(0, valLbl, keyLbl);
    }

    private HBox statRow(String key, String value) {
        Label keyLbl = new Label(key);
        keyLbl.setStyle("-fx-text-fill: #607080; -fx-font-size: 12px;");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Label valLbl = new Label(value);
        valLbl.setStyle("-fx-text-fill: #e3f2fd; -fx-font-size: 12px; -fx-font-weight: bold;");
        HBox row = new HBox(keyLbl, sp, valLbl);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private Region divider() {
        Region d = new Region();
        d.setPrefHeight(1);
        d.setMaxWidth(Double.MAX_VALUE);
        d.setStyle("-fx-background-color: #1e3a5a66;");
        return d;
    }

    private String ordinal(int n) {
        if (n == 1) return "1st";
        if (n == 2) return "2nd";
        if (n == 3) return "3rd";
        return n + "th";
    }

    // ── Top bar ───────────────────────────────────────────────────────────────

    private HBox createTopBar(GameService gameService) {
        HBox topBar = new HBox();
        topBar.setStyle(Styles.TOPBAR);
        topBar.setPadding(new Insets(12, 16, 12, 16));
        topBar.setSpacing(20);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Database database = Database.getInstance();
        GameRepo repo = new GameRepo(database.getConnection());
        String teamName = repo.getGameTeamById(repo.getGameTeamId());

        Label teamLabel = new Label("⚽  " + teamName);
        teamLabel.setStyle(Styles.TEAM_NAME_LABEL);

        HBox left = new HBox(16, teamLabel);
        left.setAlignment(Pos.CENTER_LEFT);
        String mgr = GameSession.getManagerName();
        if (!mgr.isEmpty()) {
            Label mgrLabel = new Label("Manager: " + GameSession.getManagerFirstName());
            mgrLabel.setStyle(
                "-fx-text-fill: #90caf9; -fx-font-size: 12px;" +
                "-fx-border-color: #1e3a5a; -fx-border-width: 0 0 0 1;" +
                "-fx-padding: 0 0 0 16;"
            );
            left.getChildren().add(mgrLabel);
        }

        Label weekLabel = new Label("Week " + gameService.getCurrentWeek());
        weekLabel.setStyle(Styles.TOPBAR_LABEL);

        Button musicBtn = new Button(Navigator.isMusicPlaying() ? "🔊" : "🔇");
        musicBtn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: #ccd6f6;" +
            "-fx-font-size: 16px; -fx-cursor: hand; -fx-padding: 4 8;"
        );
        musicBtn.setOnAction(e -> {
            Navigator.toggleMusic();
            musicBtn.setText(Navigator.isMusicPlaying() ? "🔊" : "🔇");
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(left, spacer, weekLabel, musicBtn);
        return topBar;
    }
}
