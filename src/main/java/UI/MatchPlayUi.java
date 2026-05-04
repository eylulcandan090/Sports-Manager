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
            scoreLabel.setText(
                    "Period: " + gameService.getCurrentPeriod()
                            + " | Score: " + gameService.getHomeScore()
                            + " - " + gameService.getAwayScore()
            );


            Button continueBtn = new Button("Continue");
            Button subBtn = new Button("Substitution");
            subBtn.setOnAction(e -> {
                Navigator.navigate(ViewType.SUBSTITUTION);
            });

            continueBtn.setOnAction(e -> {
                if (!gameService.isMatchFinished()) {
                    gameService.playNextPeriod(gameService.getCurrentSquad());
                    updateScore(title, scoreLabel, gameService);
                }
                if (gameService.isMatchFinished()) {
                    title.setText("Match Finished");
                    scoreLabel.setText(
                            "FINAL SCORE: " +
                                    gameService.getHomeScore() +
                                    " - " +
                                    gameService.getAwayScore()
                    );
                    continueBtn.setDisable(true);
                }
            });

            VBox root = new VBox(20, title, scoreLabel, continueBtn,subBtn);
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(30));

            return root;
        }

        private void updateScore(Label title, Label scoreLabel, GameService gameService) {
            if (gameService.isMatchFinished()) {
                title.setText("Match Finished");
                scoreLabel.setText(
                        "Final Score: " +
                                gameService.getHomeScore() +
                                " - " +
                                gameService.getAwayScore()
                );
            } else {
                title.setText("");
                scoreLabel.setText(
                        "Period: " + gameService.getCurrentPeriod()
                                + " | Score: " + gameService.getHomeScore()
                                + " - " + gameService.getAwayScore()
                );
            }
        }
    }

