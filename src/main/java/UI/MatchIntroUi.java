package UI;

import Service.GameService;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;

public class MatchIntroUi {

    public Parent getView(GameService gameService) {
        String homeTeam = gameService.getHomeTeamName().toUpperCase();
        String awayTeam = gameService.getAwayTeamName().toUpperCase();

        // ── Full-screen background ─────────────────────────────────────────────
        ImageView bg = new ImageView();
        URL bgUrl = getClass().getResource("/images/startscreen/champ.png");
        if (bgUrl != null) bg.setImage(new Image(bgUrl.toExternalForm()));
        bg.setPreserveRatio(false);

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #010811;");
        bg.fitWidthProperty().bind(root.widthProperty());
        bg.fitHeightProperty().bind(root.heightProperty());

        // ── Dark overlay ───────────────────────────────────────────────────────
        Region overlay = new Region();
        overlay.setStyle("-fx-background-color: rgba(1,8,17,0.72);");
        overlay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // ── Team names + VS ────────────────────────────────────────────────────
        Label homeLbl = new Label(homeTeam);
        homeLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 58px; -fx-font-weight: bold;" +
            "-fx-font-family: 'Impact', 'Arial Black', sans-serif;" +
            "-fx-effect: dropshadow(gaussian, rgba(220,60,60,0.85), 36, 0.45, 0, 0);"
        );

        Label vsLbl = new Label("VS");
        vsLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 40px; -fx-font-weight: bold;" +
            "-fx-font-family: 'Impact', 'Arial Black', sans-serif;" +
            "-fx-padding: 0 36;" +
            "-fx-effect: dropshadow(gaussian, rgba(200,80,255,1.0), 44, 0.55, 0, 0);"
        );

        Label awayLbl = new Label(awayTeam);
        awayLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 58px; -fx-font-weight: bold;" +
            "-fx-font-family: 'Impact', 'Arial Black', sans-serif;" +
            "-fx-effect: dropshadow(gaussian, rgba(60,120,255,0.85), 36, 0.45, 0, 0);"
        );

        HBox content = new HBox(homeLbl, vsLbl, awayLbl);
        content.setAlignment(Pos.CENTER);

        // ── Animations ─────────────────────────────────────────────────────────
        root.setOpacity(0);
        content.setScaleX(0.82);
        content.setScaleY(0.82);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(700), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ScaleTransition zoom = new ScaleTransition(Duration.millis(1100), content);
        zoom.setFromX(0.82);
        zoom.setFromY(0.82);
        zoom.setToX(1.0);
        zoom.setToY(1.0);
        zoom.setInterpolator(Interpolator.EASE_OUT);

        new ParallelTransition(fadeIn, zoom).play();

        // ── Auto-navigate after 2.5 s ──────────────────────────────────────────
        PauseTransition pause = new PauseTransition(Duration.millis(2500));
        pause.setOnFinished(e -> Navigator.navigate(ViewType.MATCHPLAY));
        Navigator.registerPendingNavigation(pause);
        pause.play();

        root.getChildren().addAll(bg, overlay, content);
        return root;
    }
}
