package UI;

import Model.GameSession;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;

public class ManagerSetupUi {

    private static final String FIELD_NORMAL =
        "-fx-background-color: rgba(10,22,40,0.88);" +
        "-fx-border-color: #1e4a8a; -fx-border-width: 1.5; -fx-border-radius: 10;" +
        "-fx-background-radius: 10; -fx-text-fill: #e3f2fd; -fx-font-size: 15px;" +
        "-fx-padding: 12 18; -fx-prompt-text-fill: #455a72;";

    private static final String FIELD_FOCUSED =
        "-fx-background-color: rgba(10,22,40,0.95);" +
        "-fx-border-color: #42a5f5; -fx-border-width: 2; -fx-border-radius: 10;" +
        "-fx-background-radius: 10; -fx-text-fill: #e3f2fd; -fx-font-size: 15px;" +
        "-fx-padding: 12 18; -fx-prompt-text-fill: #455a72;" +
        "-fx-effect: dropshadow(gaussian, rgba(66,165,245,0.45), 12, 0, 0, 0);";

    public Parent getView() {
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
            "rgba(1,8,17,0.55) 0%," +
            "rgba(1,8,17,0.72) 50%," +
            "rgba(1,8,17,0.90) 100%);"
        );
        overlay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // ── Title ──────────────────────────────────────────────────────────────
        Label title = new Label("CREATE YOUR MANAGER");
        title.setStyle(
            "-fx-text-fill: white; -fx-font-size: 44px; -fx-font-weight: bold;" +
            "-fx-font-family: 'Impact', 'Arial Black', sans-serif;" +
            "-fx-letter-spacing: 3;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,210,255,1.0), 48, 0.55, 0, 2)," +
            "            dropshadow(gaussian, rgba(0,80,180,0.85), 18, 0.3, 2, 4);"
        );

        Label subtitle = new Label("Your career starts here.");
        subtitle.setStyle(
            "-fx-text-fill: #90caf9; -fx-font-size: 14px; -fx-letter-spacing: 1;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,100,200,0.5), 8, 0, 0, 0);"
        );

        // ── Name field ─────────────────────────────────────────────────────────
        TextField nameField = new TextField();
        nameField.setPromptText("Enter Manager Name");
        nameField.setMaxWidth(320);
        nameField.setStyle(FIELD_NORMAL);
        nameField.focusedProperty().addListener((obs, was, now) ->
            nameField.setStyle(now ? FIELD_FOCUSED : FIELD_NORMAL)
        );

        // ── Warning label ──────────────────────────────────────────────────────
        Label warning = new Label("⚠  Please enter a manager name");
        warning.setStyle(
            "-fx-text-fill: #ff4757; -fx-font-size: 12px;" +
            "-fx-effect: dropshadow(gaussian, rgba(255,70,87,0.5), 6, 0, 0, 0);"
        );
        warning.setVisible(false);
        warning.setManaged(false);

        // ── Continue button ────────────────────────────────────────────────────
        Button continueBtn = new Button("CONTINUE  →");
        continueBtn.setPrefWidth(240);
        styleMainBtn(continueBtn,
            "linear-gradient(from 0% 0% to 100% 0%, #00c8f0, #0066cc)",
            "linear-gradient(from 0% 0% to 100% 0%, #48e0ff, #0090e0)",
            "rgba(0,200,240,0.80)"
        );

        continueBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                warning.setVisible(true);
                warning.setManaged(true);
                nameField.setStyle(
                    FIELD_NORMAL.replace("#1e4a8a", "#ff4757")
                );
            } else {
                GameSession.setManagerName(name);
                Navigator.navigate(ViewType.SPORTSELECTION);
            }
        });

        nameField.setOnAction(e -> continueBtn.fire());

        nameField.textProperty().addListener((obs, old, now) -> {
            if (!now.isEmpty() && warning.isVisible()) {
                warning.setVisible(false);
                warning.setManaged(false);
                nameField.setStyle(FIELD_NORMAL);
            }
        });

        // ── Back button ────────────────────────────────────────────────────────
        Button backBtn = new Button("← Back");
        Styles.styleDangerButton(backBtn);
        backBtn.setOnAction(e -> Navigator.navigate(ViewType.START));

        // ── Layout ─────────────────────────────────────────────────────────────
        VBox form = new VBox(8, nameField, warning);
        form.setAlignment(Pos.CENTER);

        VBox content = new VBox(18, title, subtitle, form, continueBtn, backBtn);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(0, 40, 90, 40));

        root.getChildren().addAll(bg, overlay, content);
        Styles.applyFadeIn(root);
        return root;
    }

    private void styleMainBtn(Button btn, String normal, String hover, String glow) {
        String n =
            "-fx-background-color: " + normal + ";" +
            "-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold;" +
            "-fx-padding: 13 36; -fx-background-radius: 30; -fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, " + glow + ", 22, 0.3, 0, 3);";
        String h =
            "-fx-background-color: " + hover + ";" +
            "-fx-text-fill: white; -fx-font-size: 15.5px; -fx-font-weight: bold;" +
            "-fx-padding: 13 36; -fx-background-radius: 30; -fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, " + glow + ", 36, 0.45, 0, 5);" +
            "-fx-scale-x: 1.04; -fx-scale-y: 1.04;";
        btn.setStyle(n);
        btn.setOnMouseEntered(e -> btn.setStyle(h));
        btn.setOnMouseExited(e  -> btn.setStyle(n));
    }
}
