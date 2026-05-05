package UI;

import Service.GameService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StartUi {
    public Parent getView(GameService gameService) {

        // Hoparlör butonu — sağ üst köşe
        Button musicBtn = new Button(Navigator.isMusicPlaying() ? "🔊" : "🔇");
        musicBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #ccd6f6;" +
            "-fx-font-size: 20px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8;"
        );
        musicBtn.setOnAction(e -> {
            Navigator.toggleMusic();
            musicBtn.setText(Navigator.isMusicPlaying() ? "🔊" : "🔇");
        });

        HBox topRight = new HBox(musicBtn);
        topRight.setAlignment(Pos.TOP_RIGHT);
        topRight.setPadding(new Insets(8, 12, 0, 0));

        // Orta içerik
        VBox center = new VBox(16);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(20, 40, 40, 40));

        Image image = new Image(getClass().getResourceAsStream("/images/sports.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(160);
        imageView.setPreserveRatio(true);

        Label header = new Label("SPORT MANAGER");
        header.getStyleClass().add("title-label");

        Label sub = new Label("Build your team. Win the league.");
        sub.getStyleClass().add("subtitle-label");

        Button newGame = new Button("⚽  New Game");
        newGame.setPrefWidth(200);
        newGame.setOnAction(e -> {
            gameService.resetGame();
            Navigator.navigate(ViewType.SPORTSELECTION);
        });

        center.getChildren().addAll(imageView, header, sub, newGame);

        if (gameService.hasGame()) {
            Button continueGame = new Button("▶  Continue Game");
            continueGame.setPrefWidth(200);
            continueGame.setStyle("-fx-background-color: #1e3a5f; -fx-text-fill: #64ffda;");
            continueGame.setOnAction(e -> Navigator.navigate(ViewType.MENU));
            center.getChildren().add(continueGame);
        }

        BorderPane root = new BorderPane();
        root.setTop(topRight);
        root.setCenter(center);
        root.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #020c1b, #0a1628, #010810);");

        return root;
    }
}
