package UI;
import Service.GameService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

    public class MatchPlayUi {

        public Parent getView(GameService gameService) {
            Label title = new Label("Match Started");

            Label scoreLabel = new Label();
            updateScore(scoreLabel, gameService);

            Button continueBtn = new Button("Continue");


            continueBtn.setOnAction(e -> {
                if(!gameService.isMatchFinished()){
                gameService.playNextPeriod(gameService.getCurrentSquad());
                updateScore(scoreLabel, gameService);
            }
            if(gameService.isMatchFinished()){
                scoreLabel.setText(
                        "FINAL SCORE: " +
                                gameService.getHomeScore() +
                                " - " +
                                gameService.getAwayScore()
                );
                continueBtn.setDisable(true);
            }
        });

            VBox root = new VBox(20, title, scoreLabel, continueBtn);
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(30));

            return root;
        }

        private void updateScore(Label scoreLabel, GameService gameService) {
            scoreLabel.setText(
                    "Period: " + gameService.getCurrentPeriod()
                            + " | Score: " + gameService.getHomeScore()
                            + " - " + gameService.getAwayScore()
            );
        }
    }

