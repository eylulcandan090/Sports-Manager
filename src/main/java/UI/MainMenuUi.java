package UI;

import Database.Database;
import Model.Fixture;
import Model.Team;
import Repository.GameRepo;
import Repository.TeamRepo;
import Service.FixtureService;
import Service.GameService;
import Service.LeagueService;
import Service.TeamService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Collections;

public class MainMenuUi {

    private BorderPane root;

    public Parent getView(GameService gameService, TeamService teamService,
                          FixtureService fixtureService, LeagueService leagueService,
                          TeamRepo teamRepo) {
        root = new BorderPane();
        root.setStyle(Styles.rootBg());
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

        // Next unplayed fixture for user's team
        Fixture nextFixture = null;
        for (Fixture f : fixtureService.getFixtures()) {
            if (!f.getIsPlayed() && (f.getHomeId() == teamId || f.getAwayId() == teamId)) {
                nextFixture = f;
                break;
            }
        }

        // League standings
        int leagueId = leagueService.getLeagueIdByTeamName(teamId);
        ArrayList<Team> leagueTeams = teamService.getAllTeamsByLeagueId(leagueId);
        Collections.sort(leagueTeams);
        int position = 1;
        for (Team t : leagueTeams) {
            if (t.getId() == teamId) break;
            position++;
        }

        // Fitness
        int healthy = teamService.getHealthyPlayers(teamId).size();
        int total   = teamService.getPlayersByTeamId(teamId).size();

        // ── Cards ─────────────────────────────────────────────────────────────
        VBox nextMatchCard  = buildNextMatchCard(nextFixture, teamRepo);
        VBox overviewCard   = buildOverviewCard(myTeam, total);
        VBox leagueCard     = buildLeagueCard(position, leagueTeams, myTeam);
        VBox fitnessCard    = buildFitnessCard(healthy, total);

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
        String weekText = "SEASON COMPLETE";
        if (fixture != null) {
            homeName = teamRepo.getTeamByTeamId(fixture.getHomeId()).getName().toUpperCase();
            awayName = teamRepo.getTeamByTeamId(fixture.getAwayId()).getName().toUpperCase();
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

        // Home
        Label homeLbl = new Label(homeName);
        homeLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 17px; -fx-font-weight: bold;" +
            "-fx-padding: 12 22;" +
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #6b0000, #9b1c1c88);" +
            "-fx-background-radius: 10 0 0 10;"
        );
        homeLbl.setMaxWidth(Double.MAX_VALUE);
        homeLbl.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(homeLbl, Priority.ALWAYS);

        // VS badge
        Label vsLbl = new Label("VS");
        vsLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;" +
            "-fx-padding: 12 22;" +
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #7b0000cc, #00007bcc);" +
            "-fx-border-color: rgba(255,255,255,0.15); -fx-border-width: 0 1 0 1;" +
            "-fx-effect: dropshadow(gaussian, rgba(180,80,255,0.6), 18, 0, 0, 0);" +
            "-fx-min-width: 70px;"
        );
        vsLbl.setAlignment(Pos.CENTER);

        // Away
        Label awayLbl = new Label(awayName);
        awayLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 17px; -fx-font-weight: bold;" +
            "-fx-padding: 12 22;" +
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #0e2f6688, #1565c0);" +
            "-fx-background-radius: 0 10 10 0;"
        );
        awayLbl.setMaxWidth(Double.MAX_VALUE);
        awayLbl.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(awayLbl, Priority.ALWAYS);

        HBox teamsRow = new HBox(0, homeLbl, vsLbl, awayLbl);
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
            divider(),
            pctLbl,
            statRow("Fit players",     healthy + " / " + total),
            statRow("Injured",         (total - healthy) + " players")
        );
        return card;
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

    // ── Top bar (unchanged) ───────────────────────────────────────────────────

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
        topBar.getChildren().addAll(teamLabel, spacer, weekLabel, musicBtn);
        return topBar;
    }

    // ── Sidebar (unchanged) ───────────────────────────────────────────────────

    private VBox createMenu() {
        VBox menu = new VBox(4);
        menu.setStyle(Styles.SIDEBAR);
        menu.setPadding(new Insets(16, 10, 16, 10));
        menu.setPrefWidth(160);

        Button teamBtn     = menuButton("👥  My Team");
        Button trainingBtn = menuButton("🏋  Training");
        Button fixturesBtn = menuButton("📅  Fixtures");
        Button tableBtn    = menuButton("🏆  League Table");
        Button matchBtn    = menuButton("▶  Play Match");

        teamBtn.setOnAction(e     -> Navigator.navigate(ViewType.MYTEAM));
        trainingBtn.setOnAction(e -> Navigator.navigate(ViewType.TRAINING));
        fixturesBtn.setOnAction(e -> Navigator.navigate(ViewType.FIXTURE));
        tableBtn.setOnAction(e    -> Navigator.navigate(ViewType.LEAGUETABLE));
        matchBtn.setOnAction(e    -> Navigator.navigate(ViewType.MYSQUAD));

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button backBtn = menuButton("← Main Menu");
        Styles.styleDangerButton(backBtn);
        backBtn.setOnAction(e -> Navigator.navigate(ViewType.START));

        menu.getChildren().addAll(teamBtn, trainingBtn, fixturesBtn, tableBtn, matchBtn, spacer, backBtn);
        return menu;
    }

    private Button menuButton(String text) {
        Button btn = new Button(text);
        Styles.styleMenuButton(btn);
        return btn;
    }
}
