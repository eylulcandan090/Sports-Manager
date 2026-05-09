package UI;

import Model.GameSession;
import Model.Team;
import Repository.TeamRepo;
import Service.GameService;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;

public class WelcomeScreenUi {

    public Parent getView(GameService gameService, TeamRepo teamRepo) {
        int teamId     = gameService.getGameTeamId();
        Team team      = teamRepo.getTeamByTeamId(teamId);
        String teamName   = (team != null) ? team.getName() : "Your Team";
        String managerRaw = GameSession.getManagerName();
        String managerUpper = managerRaw.isEmpty() ? "MANAGER" : managerRaw.toUpperCase();

        // ── Background ─────────────────────────────────────────────────────────
        ImageView bg = new ImageView();
        URL bgUrl = getClass().getResource("/images/startscreen/champ.png");
        if (bgUrl != null) bg.setImage(new Image(bgUrl.toExternalForm()));
        bg.setPreserveRatio(false);

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #010811;");
        bg.fitWidthProperty().bind(root.widthProperty());
        bg.fitHeightProperty().bind(root.heightProperty());

        Region overlay = new Region();
        overlay.setStyle(
            "-fx-background-color: linear-gradient(" +
            "from 0% 0% to 0% 100%," +
            "rgba(1,8,17,0.65) 0%," +
            "rgba(1,8,17,0.80) 50%," +
            "rgba(1,8,17,0.93) 100%);"
        );
        overlay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // ── Team logo ──────────────────────────────────────────────────────────
        ImageView logo = new ImageView();
        URL logoUrl = getClass().getResource(logoPath(teamName));
        if (logoUrl != null) {
            logo.setImage(new Image(logoUrl.toExternalForm()));
        }
        logo.setFitWidth(110);
        logo.setFitHeight(110);
        logo.setPreserveRatio(true);
        logo.setOpacity(0);
        logo.setScaleX(0.4);
        logo.setScaleY(0.4);

        // ── Labels ─────────────────────────────────────────────────────────────
        Label welcomeLbl = new Label("WELCOME, MANAGER");
        welcomeLbl.setStyle(
            "-fx-text-fill: #90caf9; -fx-font-size: 13px; -fx-letter-spacing: 5;" +
            "-fx-font-weight: bold;"
        );
        welcomeLbl.setOpacity(0);

        Label nameLbl = new Label(managerUpper);
        nameLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 50px; -fx-font-weight: bold;" +
            "-fx-font-family: 'Impact', 'Arial Black', sans-serif;" +
            "-fx-letter-spacing: 2;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,210,255,1.0), 48, 0.55, 0, 2)," +
            "            dropshadow(gaussian, rgba(0,80,180,0.75), 16, 0.28, 0, 3);"
        );
        nameLbl.setOpacity(0);

        Label teamLbl = new Label("You are now managing " + teamName + ".");
        teamLbl.setStyle(
            "-fx-text-fill: #ffd54f; -fx-font-size: 15px;" +
            "-fx-effect: dropshadow(gaussian, rgba(255,213,79,0.55), 10, 0, 0, 0);"
        );
        teamLbl.setOpacity(0);

        Label preparingLbl = new Label("Preparing Season...");
        preparingLbl.setStyle(
            "-fx-text-fill: #3a5272; -fx-font-size: 12px; -fx-letter-spacing: 2;"
        );
        preparingLbl.setOpacity(0);

        // ── Dots animation ─────────────────────────────────────────────────────
        Timeline dots = new Timeline(
            new KeyFrame(Duration.ZERO,         e -> preparingLbl.setText("Preparing Season")),
            new KeyFrame(Duration.millis(550),  e -> preparingLbl.setText("Preparing Season .")),
            new KeyFrame(Duration.millis(1100), e -> preparingLbl.setText("Preparing Season . .")),
            new KeyFrame(Duration.millis(1650), e -> preparingLbl.setText("Preparing Season . . ."))
        );
        dots.setCycleCount(Timeline.INDEFINITE);

        // ── Layout ─────────────────────────────────────────────────────────────
        VBox content = new VBox(14, logo, welcomeLbl, nameLbl, teamLbl, preparingLbl);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(0, 40, 70, 40));

        root.getChildren().addAll(bg, overlay, content);

        // ── Animations ─────────────────────────────────────────────────────────
        ParallelTransition logoAnim = new ParallelTransition(
            scaleTo(logo, 0.4, 1.0, 750),
            fadeTo(logo, 0, 1, 650)
        );

        SequentialTransition seq = new SequentialTransition(
            pause(280),
            logoAnim,
            pause(160),
            fadeTo(welcomeLbl, 0, 1, 480),
            pause(80),
            fadeTo(nameLbl, 0, 1, 580),
            pause(180),
            fadeTo(teamLbl, 0, 1, 480),
            pause(380),
            fadeTo(preparingLbl, 0, 1, 380)
        );
        seq.setOnFinished(e -> dots.play());
        seq.play();

        // ── Auto-navigate to main menu ─────────────────────────────────────────
        PauseTransition autoNav = new PauseTransition(Duration.millis(4400));
        autoNav.setOnFinished(e -> {
            dots.stop();
            Navigator.navigate(ViewType.MENU);
        });
        Navigator.registerPendingNavigation(autoNav);
        autoNav.play();

        return root;
    }

    private FadeTransition fadeTo(Node node, double from, double to, int ms) {
        FadeTransition ft = new FadeTransition(Duration.millis(ms), node);
        ft.setFromValue(from);
        ft.setToValue(to);
        return ft;
    }

    private ScaleTransition scaleTo(Node node, double from, double to, int ms) {
        ScaleTransition st = new ScaleTransition(Duration.millis(ms), node);
        st.setFromX(from); st.setFromY(from);
        st.setToX(to);     st.setToY(to);
        st.setInterpolator(Interpolator.EASE_OUT);
        return st;
    }

    private PauseTransition pause(int ms) {
        return new PauseTransition(Duration.millis(ms));
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
