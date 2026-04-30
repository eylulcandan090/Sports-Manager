package UI;

import Service.GameService;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class StartUi {
    public Parent getView(GameService gameService) {

        VBox root = new VBox();

        // Fotoğraf
        Image image = new Image(getClass().getResourceAsStream("/images/sports.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        Label header = new Label("Sport Manager");
        header.setFont(Font.font(30));

        Button newGame = new Button("    New Game    ");

        root.getChildren().addAll(imageView, header, newGame);

        if (gameService.hasGame()) {
            Button continueGame = new Button("Continue Game");
            root.getChildren().add(continueGame);
            continueGame.setOnAction(e -> Navigator.navigate(ViewType.MENU));
        }

        newGame.setOnAction(e -> Navigator.navigate(ViewType.SPORTSELECTION));

        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        return root;
    }
}