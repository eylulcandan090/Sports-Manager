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

            Button defensiveBtn = new Button("Defensive");
            Button attackingBtn = new Button("Attacking");
            Button backBtn = new Button("Back to Match");

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

            VBox root = new VBox(15, title, current, defensiveBtn,attackingBtn, backBtn);
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(30));

            return root;
        }
    }

