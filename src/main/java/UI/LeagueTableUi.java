package UI;

import Database.Database;
import Model.Team;
import Repository.GameRepo;
import Repository.TeamRepo;
import Service.GameService;
import Service.LeagueService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Collections;

public class LeagueTableUi {

    public Parent getView(GameService gameService, LeagueService leagueService, TeamRepo teamRepo) {
        int teamId   = gameService.getGameTeamId();
        int leagueId = leagueService.getLeagueIdByTeamName(teamId);

        String userTeamName = new GameRepo(Database.getInstance().getConnection())
                                  .getGameTeamById(teamId);

        ArrayList<Team> teams = teamRepo.getAllTeamsByLeagueId(leagueId);
        Collections.sort(teams);

        // Find user position and points
        int userPos    = 1;
        int userPoints = 0;
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getId() == teamId) {
                userPos    = i + 1;
                userPoints = teams.get(i).getPoint();
                break;
            }
        }
        String leaderName = teams.isEmpty() ? "—" : teams.get(0).getName();
        int    total      = teams.size();

        // ── Root ──────────────────────────────────────────────────────────────
        BorderPane root = new BorderPane();
        root.setMaxWidth(Double.MAX_VALUE);
        root.setMaxHeight(Double.MAX_VALUE);
        root.setStyle(
            "-fx-background-color: #020c1b;" +
            "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
        );
        root.setTop(buildTopBar(userTeamName, gameService));

        // ── Title ─────────────────────────────────────────────────────────────
        Label titleLbl = new Label("LEAGUE TABLE");
        titleLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;" +
            "-fx-font-family: 'Impact', 'Arial Black', sans-serif; -fx-letter-spacing: 3;"
        );
        Label subtitleLbl = new Label("Current standings");
        subtitleLbl.setStyle("-fx-text-fill: #607080; -fx-font-size: 12px;");

        VBox titleBox = new VBox(5, titleLbl, subtitleLbl);
        titleBox.setAlignment(Pos.CENTER);

        // ── Summary cards ──────────────────────────────────────────────────────
        VBox posCard    = statCard("YOUR POSITION", ordinal(userPos) + " / " + total,
                                   userPos == 1 ? "#ffd54f" : "#90caf9");
        VBox ptsCard    = statCard("YOUR POINTS",   String.valueOf(userPoints), "#26a69a");
        VBox leaderCard = statCard("LEADER",         leaderName,                "#f48fb1");

        HBox summaryRow = new HBox(12, posCard, ptsCard, leaderCard);
        HBox.setHgrow(posCard,    Priority.ALWAYS);
        HBox.setHgrow(ptsCard,    Priority.ALWAYS);
        HBox.setHgrow(leaderCard, Priority.ALWAYS);

        // ── Column headers ─────────────────────────────────────────────────────
        Label hPos  = new Label("#");
        Label hTeam = new Label("TEAM");
        Label hPts  = new Label("PTS");
        String hStyle = "-fx-text-fill: #2a4060; -fx-font-size: 10px; -fx-font-weight: bold; -fx-letter-spacing: 1;";
        hPos.setStyle(hStyle);  hPos.setMinWidth(36);
        hTeam.setStyle(hStyle); HBox.setHgrow(new Region(), Priority.ALWAYS);
        hPts.setStyle(hStyle);  hPts.setMinWidth(40); hPts.setAlignment(Pos.CENTER_RIGHT);

        Region hSpacer = new Region();
        HBox.setHgrow(hSpacer, Priority.ALWAYS);
        HBox colHeader = new HBox(0, hPos, hTeam, hSpacer, hPts);
        colHeader.setPadding(new Insets(0, 16, 4, 16));
        colHeader.setAlignment(Pos.CENTER_LEFT);

        // ── Standings rows ─────────────────────────────────────────────────────
        VBox standingsBox = new VBox(5);
        standingsBox.setMaxWidth(Double.MAX_VALUE);

        for (int i = 0; i < teams.size(); i++) {
            Team t      = teams.get(i);
            int  pos    = i + 1;
            boolean isUser = (t.getId() == teamId);
            boolean isTop3 = (pos <= 3);
            boolean isRel  = (pos > total - 3) && total > 6;

            standingsBox.getChildren().add(buildRow(pos, t.getName(), t.getPoint(),
                                                     isUser, isTop3, isRel, total));
        }

        // ── Scroll ────────────────────────────────────────────────────────────
        VBox content = new VBox(18, titleBox, summaryRow, colHeader, standingsBox);
        content.setPadding(new Insets(20, 28, 20, 28));
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

    // ── Standing row ──────────────────────────────────────────────────────────

    private HBox buildRow(int pos, String name, int pts,
                          boolean isUser, boolean isTop3, boolean isRel, int total) {

        // Position number
        String posColor = pos == 1   ? "#ffd54f"
                        : isTop3     ? "#90caf9"
                        : isRel      ? "#ff4757"
                        : "#3a5272";
        Label posLbl = new Label(String.valueOf(pos));
        posLbl.setStyle(
            "-fx-text-fill: " + posColor + "; -fx-font-size: 13px; -fx-font-weight: bold;"
        );
        posLbl.setMinWidth(36);
        posLbl.setAlignment(Pos.CENTER);

        // Medal emoji for top 3
        String medal = pos == 1 ? "🥇 " : pos == 2 ? "🥈 " : pos == 3 ? "🥉 " : "";

        // Team name
        Label nameLbl = new Label(medal + name);
        nameLbl.setStyle(
            "-fx-text-fill: " + (isUser ? "#ffd54f" : "white") + ";" +
            "-fx-font-size: 13px;" +
            (isUser ? " -fx-font-weight: bold;" : "")
        );
        HBox.setHgrow(nameLbl, Priority.ALWAYS);
        nameLbl.setMaxWidth(Double.MAX_VALUE);

        // Points
        Label ptsLbl = new Label(String.valueOf(pts));
        ptsLbl.setStyle(
            "-fx-text-fill: " + (isUser ? "#ffd54f" : "#90caf9") + ";" +
            "-fx-font-size: 13px; -fx-font-weight: bold;"
        );
        ptsLbl.setMinWidth(40);
        ptsLbl.setAlignment(Pos.CENTER_RIGHT);

        // You badge
        if (isUser) {
            Label youBadge = new Label("YOU");
            youBadge.setStyle(
                "-fx-background-color: #ffd54f; -fx-text-fill: #010811;" +
                "-fx-font-size: 8px; -fx-font-weight: bold;" +
                "-fx-padding: 1 5; -fx-background-radius: 3;"
            );
            HBox row = new HBox(0, posLbl, nameLbl, youBadge, ptsLbl);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(9, 14, 9, 14));
            row.setSpacing(8);
            row.setMaxWidth(Double.MAX_VALUE);
            String userStyle =
                "-fx-background-color: #0d2a1a; -fx-border-color: #ffd54f;" +
                "-fx-border-width: 1.5; -fx-border-radius: 7; -fx-background-radius: 7;";
            row.setStyle(userStyle);
            return row;
        }

        HBox row = new HBox(0, posLbl, nameLbl, ptsLbl);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(9, 14, 9, 14));
        row.setSpacing(8);
        row.setMaxWidth(Double.MAX_VALUE);

        String bgColor    = isTop3 ? "#0a1e38" : isRel ? "#1a0808" : "#0a1628";
        String borderColor = isTop3 ? "#1a3a60" : isRel ? "#3a0808" : "#0d2040";
        String baseStyle  =
            "-fx-background-color: " + bgColor + ";" +
            "-fx-border-color: " + borderColor + ";" +
            "-fx-border-width: 1; -fx-border-radius: 7; -fx-background-radius: 7;";
        String hoverStyle =
            "-fx-background-color: #0d2040; -fx-border-color: #2a5aaa;" +
            "-fx-border-width: 1; -fx-border-radius: 7; -fx-background-radius: 7;";

        row.setStyle(baseStyle);
        row.setOnMouseEntered(e -> row.setStyle(hoverStyle));
        row.setOnMouseExited(e  -> row.setStyle(baseStyle));
        return row;
    }

    // ── Summary card ──────────────────────────────────────────────────────────

    private VBox statCard(String key, String value, String color) {
        Label valLbl = new Label(value);
        valLbl.setStyle(
            "-fx-text-fill: " + color + "; -fx-font-size: 20px; -fx-font-weight: bold;"
        );
        valLbl.setWrapText(true);
        Label keyLbl = new Label(key);
        keyLbl.setStyle(
            "-fx-text-fill: #2a4060; -fx-font-size: 10px;" +
            "-fx-letter-spacing: 1; -fx-font-weight: bold;"
        );
        VBox card = new VBox(3, valLbl, keyLbl);
        card.setPadding(new Insets(12, 14, 12, 14));
        card.setStyle(
            "-fx-background-color: #0a1628; -fx-border-color: #1e3a5a;" +
            "-fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8;"
        );
        return card;
    }

    // ── Top bar ───────────────────────────────────────────────────────────────

    private HBox buildTopBar(String teamName, GameService gameService) {
        HBox bar = new HBox(14);
        bar.setStyle(Styles.TOPBAR);
        bar.setPadding(new Insets(12, 16, 12, 16));
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setMaxWidth(Double.MAX_VALUE);

        Label sectionLbl = new Label("🏆  LEAGUE TABLE");
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

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static String ordinal(int n) {
        if (n == 1) return "1st";
        if (n == 2) return "2nd";
        if (n == 3) return "3rd";
        return n + "th";
    }
}
