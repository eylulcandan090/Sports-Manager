package UI;

import Model.SportEntity;
import Model.Team;
import Repository.GameRepo;
import Repository.TeamRepo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TeamSelectionUi {

    private static final String CARD_NORMAL =
        "-fx-background-color: #0d1f35; -fx-border-color: #1a3a5a;" +
        "-fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;" +
        "-fx-cursor: hand;";

    private static final String CARD_HOVER =
        "-fx-background-color: #142840; -fx-border-color: #2a5a8a;" +
        "-fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;" +
        "-fx-cursor: hand;";

    private static final String CARD_SELECTED =
        "-fx-background-color: #0d2a4a; -fx-border-color: #42a5f5;" +
        "-fx-border-width: 2.5; -fx-border-radius: 10; -fx-background-radius: 10;" +
        "-fx-cursor: hand;" +
        "-fx-effect: dropshadow(gaussian, rgba(66,165,245,0.55), 14, 0, 0, 0);";

    public Parent getView(SportEntity sport, TeamRepo repo, GameRepo gameRepo) {
        List<Team> teams;
        switch (sport.getSport()) {
            case "Football":   teams = repo.getAllTeamsBySport("Football");   break;
            case "Basketball": teams = repo.getAllTeamsBySport("Basketball"); break;
            default:           teams = new ArrayList<>();
        }

        Team[] selectedTeam = {null};
        List<VBox> cards = new ArrayList<>();

        // ── Title ─────────────────────────────────────────────────────────────
        Label title = new Label("SELECT YOUR TEAM");
        title.setStyle(
            "-fx-text-fill: #f48fb1; -fx-font-size: 22px; -fx-font-weight: bold;" +
            "-fx-letter-spacing: 2;"
        );
        VBox topArea = new VBox(title);
        topArea.setAlignment(Pos.CENTER);
        topArea.setPadding(new Insets(20, 20, 10, 20));

        // ── Card grid ─────────────────────────────────────────────────────────
        TilePane grid = new TilePane();
        grid.setPrefColumns(4);
        grid.setHgap(14);
        grid.setVgap(14);
        grid.setPadding(new Insets(14));
        grid.setAlignment(Pos.TOP_CENTER);

        for (Team team : teams) {
            VBox card = buildCard(team);
            cards.add(card);

            card.setOnMouseEntered(e -> {
                if (team != selectedTeam[0]) card.setStyle(CARD_HOVER);
            });
            card.setOnMouseExited(e -> {
                if (team != selectedTeam[0]) card.setStyle(CARD_NORMAL);
            });
            card.setOnMouseClicked(e -> {
                selectedTeam[0] = team;
                for (VBox c : cards) c.setStyle(CARD_NORMAL);
                card.setStyle(CARD_SELECTED);
            });

            grid.getChildren().add(card);
        }

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle(
            "-fx-background: transparent; -fx-background-color: transparent;" +
            "-fx-border-color: transparent;"
        );

        // ── Buttons ───────────────────────────────────────────────────────────
        Button backButton = new Button("← Change sport");
        Styles.styleDangerButton(backButton);
        backButton.setOnAction(e -> Navigator.navigate(ViewType.SPORTSELECTION));

        Button startGame = new Button("Start");
        Styles.styleButton(startGame);
        startGame.setOnAction(e -> {
            if (selectedTeam[0] != null) {
                gameRepo.createNewGame(selectedTeam[0].getId());
                gameRepo.setGameStarted(true);
                Navigator.navigate(ViewType.MENU);
            } else {
                AlertUtility.showWarning("No Team Selected", "Please select a team to continue.");
            }
        });

        HBox buttons = new HBox(10, backButton, startGame);
        buttons.setPadding(new Insets(12, 20, 12, 20));
        buttons.setAlignment(Pos.CENTER_LEFT);
        buttons.setStyle(
            "-fx-background-color: #060d1a; -fx-border-color: #1565c0; -fx-border-width: 1 0 0 0;"
        );

        // ── Root ──────────────────────────────────────────────────────────────
        BorderPane root = new BorderPane();
        root.setStyle(Styles.rootBg());
        root.setTop(topArea);
        root.setCenter(scroll);
        root.setBottom(buttons);

        return root;
    }

    private static String logoPath(String teamName) {
        switch (teamName) {
            case "Arsenal":
                return "/images/teams/arsenal.png";
            case "Aston Villa":
                return "/images/teams/astonvilla.png";
            case "Bournemouth":
                return "/images/teams/bournemouth.png";
            case "Brentford": case "Brentford FC":
                return "/images/teams/brentford.png";
            case "Brighton": case "Brighton & Hove Albion":
                return "/images/teams/brighton.png";
            case "Chelsea":
                return "/images/teams/chelsea.png";
            case "Crystal Palace":
                return "/images/teams/crystal.png";
            case "Everton":
                return "/images/teams/everton.png";
            case "Nottingham Forest": case "Nott'm Forest":
                return "/images/teams/forest.png";
            case "Fulham":
                return "/images/teams/fullham.png";
            case "Ipswich": case "Ipswich Town":
                return "/images/teams/ıpswich.png";
            case "Leicester": case "Leicester City":
                return "/images/teams/leicester.png";
            case "Liverpool":
                return "/images/teams/liverpool.png";
            case "Manchester City":
                return "/images/teams/manchester.png";
            case "Manchester United":
                return "/images/teams/manchesterunited.png";
            case "Newcastle United": case "Newcastle":
                return "/images/teams/newcastleunited.png";
            case "Southampton":
                return "/images/teams/southampton.png";
            case "Tottenham": case "Tottenham Hotspur":
                return "/images/teams/tottenham.png";
            case "West Ham": case "West Ham United":
                return "/images/teams/westham.png";
            case "Wolverhampton": case "Wolverhampton Wanderers": case "Wolves":
                return "/images/teams/wolverhampton.png";

            // Basketball
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

            default:
                return "/images/teams/default_team.png";
        }
    }

    private VBox buildCard(Team team) {
        String imagePath = logoPath(team.getName());

        ImageView logo = new ImageView();
        URL url = getClass().getResource(imagePath);
        if (url != null) {
            logo.setImage(new Image(url.toExternalForm()));
            logo.setFitWidth(64);
            logo.setFitHeight(64);
            logo.setPreserveRatio(true);
        } else {
            logo.setFitWidth(64);
            logo.setFitHeight(64);
        }

        Label nameLabel = new Label(team.getName());
        nameLabel.setStyle(
            "-fx-text-fill: #e3f2fd; -fx-font-size: 12px; -fx-font-weight: bold;" +
            "-fx-text-alignment: center; -fx-wrap-text: true;"
        );
        nameLabel.setMaxWidth(110);
        nameLabel.setAlignment(Pos.CENTER);

        VBox card = new VBox(8, logo, nameLabel);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(14));
        card.setPrefWidth(120);
        card.setPrefHeight(110);
        card.setStyle(CARD_NORMAL);

        return card;
    }
}
