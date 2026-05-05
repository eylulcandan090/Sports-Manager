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
            Label title = new Label(gameService.getMatchTitle());

            Label scoreLabel = new Label();
            Label weekLabel = new Label("Week " + gameService.getCurrentWeek());
            Label injuryLabel = new Label();
            Label resultLabel = new Label();
            resultLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            scoreLabel.setText(
                    "Period: " + gameService.getCurrentPeriod()
                            + " | Score: " + gameService.getHomeScore()
                            + " - " + gameService.getAwayScore()
            );

            Button continueBtn = new Button("Continue");
            Button subBtn = new Button("Substitution");
            Button tacticBtn = new Button("Change Tactic");
            Button backBtn = new Button("Back to Menu");
            backBtn.setVisible(false);

            backBtn.setOnAction(e -> {
                Navigator.navigate(ViewType.MENU);
            });

            tacticBtn.setOnAction(e -> {
                Navigator.navigate(ViewType.TACTIC);
            });

            subBtn.setOnAction(e -> {
                Navigator.navigate(ViewType.SUBSTITUTION);
            });

            continueBtn.setOnAction(e -> {
                if (!gameService.isMatchFinished()) {
                    gameService.playNextPeriod(gameService.getCurrentSquad());
                    updateScore(title, scoreLabel, gameService);
                    injuryLabel.setText(gameService.getInjuryMessage());
                }
                if (gameService.isMatchFinished()) {
                    title.setText("Match Finished");
                    scoreLabel.setText(
                            "FINAL SCORE: " +
                                    gameService.getHomeScore() +
                                    " - " +
                                    gameService.getAwayScore()
                    );
                    gameService.finishMatch();
                    String result = gameService.getMatchResult();
                    resultLabel.setText(result);
                    if (result.contains("Won")) {
                        resultLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2ecc71;");
                    } else if (result.contains("Lost")) {
                        resultLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
                    } else {
                        resultLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #f39c12;");
                    }
                    continueBtn.setDisable(true);
                    subBtn.setDisable(true);
                    tacticBtn.setDisable(true);
                    backBtn.setVisible(true);
                }
            });

            VBox root = new VBox(20, title, weekLabel, scoreLabel, injuryLabel, resultLabel, continueBtn, subBtn, tacticBtn, backBtn);
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

