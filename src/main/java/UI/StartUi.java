package UI;

import Service.GameService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;

public class StartUi {

    public Parent getView(GameService gameService) {

        // ── Full-screen background ─────────────────────────────────────────────
        ImageView bg = new ImageView();
        URL bgUrl = getClass().getResource("/images/startscreen/champ.png");
        if (bgUrl != null) {
            bg.setImage(new Image(bgUrl.toExternalForm()));
        }
        bg.setPreserveRatio(false);

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #010811;");

        // Bind background size to root so it always fills the window
        bg.fitWidthProperty().bind(root.widthProperty());
        bg.fitHeightProperty().bind(root.heightProperty());
        StackPane.setAlignment(bg, Pos.TOP_LEFT);

        // Dark overlay so text is readable over the background
        Region overlay = new Region();
        overlay.setStyle("-fx-background-color: rgba(1, 8, 17, 0.52);");
        overlay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // ── Music button ───────────────────────────────────────────────────────
        Button musicBtn = new Button(Navigator.isMusicPlaying() ? "🔊" : "🔇");
        musicBtn.setStyle(
            "-fx-background-color: rgba(0,0,0,0.35); -fx-text-fill: #ccd6f6;" +
            "-fx-font-size: 18px; -fx-cursor: hand; -fx-padding: 6 10;" +
            "-fx-background-radius: 8;"
        );
        musicBtn.setOnAction(e -> {
            Navigator.toggleMusic();
            musicBtn.setText(Navigator.isMusicPlaying() ? "🔊" : "🔇");
        });

        HBox topBar = new HBox(musicBtn);
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setPadding(new Insets(14, 16, 0, 0));
        topBar.setMaxHeight(50);
        StackPane.setAlignment(topBar, Pos.TOP_RIGHT);

        // ── Title ──────────────────────────────────────────────────────────────
        Label title = new Label("SPORTS MANAGER");
        title.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-size: 54px;" +
            "-fx-font-weight: bold;" +
            "-fx-font-family: 'Impact', 'Arial Black', sans-serif;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,200,255,0.95), 32, 0.45, 0, 0);"
        );

        Label subtitle = new Label("Build your team. Win the league.");
        subtitle.setStyle(
            "-fx-text-fill: #90caf9;" +
            "-fx-font-size: 15px;" +
            "-fx-letter-spacing: 1;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,100,200,0.6), 10, 0, 0, 0);"
        );

        // ── New Game button ────────────────────────────────────────────────────
        Button newGame = new Button("⚽  NEW GAME");
        newGame.setPrefWidth(240);
        styleStartBtn(newGame,
            "linear-gradient(from 0% 0% to 100% 0%, #00b4d8, #0077b6)",
            "linear-gradient(from 0% 0% to 100% 0%, #48cae4, #00b4d8)",
            "rgba(0,180,216,0.65)"
        );
        newGame.setOnAction(e -> {
            gameService.resetGame();
            Navigator.navigate(ViewType.SPORTSELECTION);
        });

        // ── Center content ─────────────────────────────────────────────────────
        VBox content = new VBox(18, title, subtitle, newGame);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(0, 40, 80, 40));

        if (gameService.hasGame()) {
            Button continueGame = new Button("▶  CONTINUE");
            continueGame.setPrefWidth(240);
            styleStartBtn(continueGame,
                "linear-gradient(from 0% 0% to 100% 0%, #00695c, #004d40)",
                "linear-gradient(from 0% 0% to 100% 0%, #26a69a, #00897b)",
                "rgba(0,180,150,0.55)"
            );
            continueGame.setOnAction(e -> Navigator.navigate(ViewType.MENU));
            content.getChildren().add(continueGame);
        }

        root.getChildren().addAll(bg, overlay, content, topBar);
        Styles.applyFadeIn(root);
        return root;
    }

    private void styleStartBtn(Button btn, String normalGradient,
                                String hoverGradient, String glowColor) {
        String normal =
            "-fx-background-color: " + normalGradient + ";" +
            "-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold;" +
            "-fx-padding: 13 36; -fx-background-radius: 30; -fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, " + glowColor + ", 18, 0, 0, 3);";
        String hover =
            "-fx-background-color: " + hoverGradient + ";" +
            "-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold;" +
            "-fx-padding: 13 36; -fx-background-radius: 30; -fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, " + glowColor + ", 28, 0, 0, 4);";
        btn.setStyle(normal);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(normal));
    }
}
