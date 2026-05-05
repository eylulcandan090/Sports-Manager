package UI;
import Service.GameService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

    public class TacticUi {

        public Parent getView(GameService gameService) {
            Label title = new Label("Choose Tactic");
            Label current = new Label("Current: " + gameService.getTactic());
            title.setStyle("-fx-text-fill: #f48fb1; -fx-font-size: 20px; -fx-font-weight: bold;");
            current.setStyle(Styles.TOPBAR_LABEL);

            Button defensiveBtn = new Button("Defensive");
            Button attackingBtn = new Button("Attacking");
            Button backBtn = new Button("Back to Match");
            Styles.styleButton(defensiveBtn);
            Styles.styleButton(attackingBtn);
            Styles.styleDangerButton(backBtn);

            defensiveBtn.setOnAction(e -> {
                gameService.setTactic("Defensive");
                current.setText("Current: Defensive");
            });

            attackingBtn.setOnAction(e -> {
                gameService.setTactic("Attacking");
                current.setText("Current: Attacking");
            });

            backBtn.setOnAction(e -> {
                Navigator.navigate(ViewType.MATCHPLAY);
            });

            VBox root = new VBox(15, title, current, defensiveBtn, attackingBtn, backBtn);
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(30));
            root.setStyle(Styles.rootBg());

            return root;
        }
    }

