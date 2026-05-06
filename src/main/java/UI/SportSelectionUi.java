package UI;

import Database.Database;
import Model.FixtureGenerator;
import Model.SportEntity;
import Model.Team;
import Repository.TeamRepo;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;

public class SportSelectionUi {

    private static final String CARD_NORMAL   =
        "-fx-background-color: transparent;" +
        "-fx-border-color: rgba(255,255,255,0.18); -fx-border-width: 2;" +
        "-fx-border-radius: 16; -fx-background-radius: 16;";

    private static final String CARD_SELECTED_FOOTBALL =
        "-fx-background-color: transparent;" +
        "-fx-border-color: #42e06a; -fx-border-width: 3;" +
        "-fx-border-radius: 16; -fx-background-radius: 16;" +
        "-fx-effect: dropshadow(gaussian, rgba(66,224,106,0.75), 28, 0, 0, 0);";

    private static final String CARD_SELECTED_BASKETBALL =
        "-fx-background-color: transparent;" +
        "-fx-border-color: #ffaa00; -fx-border-width: 3;" +
        "-fx-border-radius: 16; -fx-background-radius: 16;" +
        "-fx-effect: dropshadow(gaussian, rgba(255,170,0,0.75), 28, 0, 0, 0);";

    public Parent getView(TeamRepo teamRepo, Database database) {

        // ── Full-screen background (same as StartUi) ───────────────────────────
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
            "rgba(1,8,17,0.50) 0%," +
            "rgba(1,8,17,0.68) 55%," +
            "rgba(1,8,17,0.85) 100%);"
        );
        overlay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // ── Header ─────────────────────────────────────────────────────────────
        Label header = new Label("SELECT A SPORT");
        header.setStyle(
            "-fx-text-fill: white; -fx-font-size: 42px; -fx-font-weight: bold;" +
            "-fx-font-family: 'Impact', 'Arial Black', sans-serif;" +
            "-fx-letter-spacing: 3;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,210,255,0.9), 38, 0.45, 0, 2);"
        );

        Label sub = new Label("Choose your sport to start the game");
        sub.setStyle(
            "-fx-text-fill: #90caf9; -fx-font-size: 14px; -fx-letter-spacing: 1;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,100,200,0.5), 8, 0, 0, 0);"
        );

        // ── Sport cards ────────────────────────────────────────────────────────
        StackPane[] selectedCard = {null};

        StackPane footballCard   = buildCard(true);   // football field
        StackPane basketballCard = buildCard(false);  // basketball court

        setupCard(footballCard,   1.05, false);
        setupCard(basketballCard, 1.05, false);

        footballCard.setOnMouseEntered(e -> {
            if (selectedCard[0] != footballCard) scale(footballCard, 1.05);
        });
        footballCard.setOnMouseExited(e -> {
            if (selectedCard[0] != footballCard) scale(footballCard, 1.0);
        });
        footballCard.setOnMouseClicked(e -> {
            selectedCard[0] = footballCard;
            footballCard.setStyle(CARD_SELECTED_FOOTBALL);
            basketballCard.setStyle(CARD_NORMAL);
            scale(basketballCard, 1.0);
            scale(footballCard, 1.05);
            ArrayList<Team> teams = teamRepo.getAllTeamsBySport("Football");
            FixtureGenerator.generateAndSave(teams, database.getConnection());
            Navigator.navigate(ViewType.TEAMSELECTION, new SportEntity("Football"));
        });

        basketballCard.setOnMouseEntered(e -> {
            if (selectedCard[0] != basketballCard) scale(basketballCard, 1.05);
        });
        basketballCard.setOnMouseExited(e -> {
            if (selectedCard[0] != basketballCard) scale(basketballCard, 1.0);
        });
        basketballCard.setOnMouseClicked(e -> {
            selectedCard[0] = basketballCard;
            basketballCard.setStyle(CARD_SELECTED_BASKETBALL);
            footballCard.setStyle(CARD_NORMAL);
            scale(footballCard, 1.0);
            scale(basketballCard, 1.05);
            ArrayList<Team> teams = teamRepo.getAllTeamsBySport("Basketball");
            FixtureGenerator.generateAndSave(teams, database.getConnection());
            Navigator.navigate(ViewType.TEAMSELECTION, new SportEntity("Basketball"));
        });

        HBox cards = new HBox(36, footballCard, basketballCard);
        cards.setAlignment(Pos.CENTER);

        VBox content = new VBox(22, header, sub, cards);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(0, 40, 60, 40));

        root.getChildren().addAll(bg, overlay, content);
        Styles.applyFadeIn(root);
        return root;
    }

    // ── Card builder ──────────────────────────────────────────────────────────

    private StackPane buildCard(boolean isFootball) {
        // Canvas background: drawn field / court
        Canvas canvas = new Canvas(220, 160);
        if (isFootball) drawFootballField(canvas);
        else            drawBasketballCourt(canvas);

        // Dark overlay inside the card for text readability
        Region cardOverlay = new Region();
        cardOverlay.setStyle("-fx-background-color: rgba(0,0,0,0.42);");
        cardOverlay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Text content
        Label name = new Label(isFootball ? "Football" : "Basketball");
        name.setStyle(
            "-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 6, 0, 0, 1);"
        );

        VBox text = new VBox(8, name);
        text.setAlignment(Pos.CENTER);

        StackPane card = new StackPane(canvas, cardOverlay, text);
        card.setPrefSize(220, 160);
        card.setMaxSize(220, 160);
        card.setStyle(CARD_NORMAL);
        card.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: rgba(255,255,255,0.18); -fx-border-width: 2;" +
            "-fx-border-radius: 16; -fx-background-radius: 16;" +
            "-fx-cursor: hand;"
        );
        return card;
    }

    private void setupCard(StackPane card, double hoverScale, boolean selected) {
        card.setScaleX(1.0);
        card.setScaleY(1.0);
    }

    // ── Canvas drawings ───────────────────────────────────────────────────────

    private void drawFootballField(Canvas canvas) {
        double w = canvas.getWidth(), h = canvas.getHeight();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Green pitch
        gc.setFill(new LinearGradient(0, 0, 0, h, false, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web("#1a6b1a")),
            new Stop(1, Color.web("#0f4a0f"))
        ));
        gc.fillRect(0, 0, w, h);

        // Stripe pattern
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                gc.setFill(Color.web("#ffffff08"));
                gc.fillRect(i * (w / 6), 0, w / 6, h);
            }
        }

        // White lines
        gc.setStroke(Color.web("#ffffffaa"));
        gc.setLineWidth(1.2);
        gc.strokeRect(6, 6, w - 12, h - 12);        // outer border
        gc.strokeLine(w / 2, 6, w / 2, h - 6);       // center line
        gc.strokeOval(w / 2 - 28, h / 2 - 28, 56, 56); // center circle
        gc.setFill(Color.web("#ffffffcc"));
        gc.fillOval(w / 2 - 2.5, h / 2 - 2.5, 5, 5); // center spot

        // Penalty areas
        gc.setStroke(Color.web("#ffffff88"));
        gc.strokeRect(6, h / 2 - 26, 36, 52);
        gc.strokeRect(w - 42, h / 2 - 26, 36, 52);
    }

    private void drawBasketballCourt(Canvas canvas) {
        double w = canvas.getWidth(), h = canvas.getHeight();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Wood floor
        gc.setFill(new LinearGradient(0, 0, w, 0, false, CycleMethod.NO_CYCLE,
            new Stop(0,   Color.web("#b5641c")),
            new Stop(0.5, Color.web("#d4803a")),
            new Stop(1,   Color.web("#b5641c"))
        ));
        gc.fillRect(0, 0, w, h);

        // Wood grain lines
        gc.setStroke(Color.web("#00000018"));
        gc.setLineWidth(1);
        for (int i = 0; i < h; i += 10) gc.strokeLine(0, i, w, i);

        // Court lines
        gc.setStroke(Color.web("#ffffffbb"));
        gc.setLineWidth(1.5);
        gc.strokeRect(6, 6, w - 12, h - 12);           // border
        gc.strokeLine(w / 2, 6, w / 2, h - 6);          // half-court line
        gc.strokeOval(w / 2 - 22, h / 2 - 22, 44, 44); // center circle

        // Paint / key
        gc.setStroke(Color.web("#ffffff99"));
        gc.strokeRect(6, h / 2 - 22, 50, 44);
        gc.strokeRect(w - 56, h / 2 - 22, 50, 44);

        // 3-point arc (simplified)
        gc.strokeArc(6 - 28, h / 2 - 52, 112, 104, -70, 140,
            javafx.scene.shape.ArcType.OPEN);
        gc.strokeArc(w - 6 - 84, h / 2 - 52, 112, 104, 110, 140,
            javafx.scene.shape.ArcType.OPEN);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void scale(StackPane card, double target) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), card);
        st.setToX(target);
        st.setToY(target);
        st.play();
    }
}
