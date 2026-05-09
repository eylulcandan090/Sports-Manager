package UI;

import Database.Database;
import Model.Basketball.BasketballPlayer;
import Model.Football.FootballPlayer;
import Model.Player;
import Repository.BasketballPlayerRepo;
import Repository.FootballPlayerRepo;
import Service.GameService;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.util.Duration;

public class MatchPlayUi {

    public Parent getView(GameService gameService) {

        String homeTeam   = gameService.getHomeTeamName();
        String awayTeam   = gameService.getAwayTeamName();
        String periodName = gameService.getMaxPeriod() == 2 ? "HALF" : "QUARTER";

        // ── Live display labels ────────────────────────────────────────────────
        Label scoreLbl  = createScoreLabel("0  -  0");
        Label periodLbl = createInfoLabel(
            periodName + "  1 / " + gameService.getMaxPeriod()
        );
        Label weekLbl   = createInfoLabel("WEEK  " + gameService.getCurrentWeek());

        Label goalLbl = new Label();
        goalLbl.setStyle(
            "-fx-text-fill: #ffd54f; -fx-font-size: 13px; -fx-font-weight: bold;" +
            "-fx-padding: 6 18; -fx-background-color: #ffd54f18;" +
            "-fx-background-radius: 6; -fx-border-color: #ffd54f44;" +
            "-fx-border-radius: 6; -fx-border-width: 1;"
        );
        goalLbl.setVisible(false);
        goalLbl.setManaged(false);

        Label cardLbl = new Label();
        cardLbl.setVisible(false);
        cardLbl.setManaged(false);

        Label injuryLbl = new Label();
        injuryLbl.setStyle(
            "-fx-text-fill: #ffa502; -fx-font-size: 12px; -fx-font-weight: bold;" +
            "-fx-padding: 6 18; -fx-background-color: #ffa50218;" +
            "-fx-background-radius: 6; -fx-border-color: #ffa50244;" +
            "-fx-border-radius: 6; -fx-border-width: 1;"
        );
        injuryLbl.setVisible(false);
        injuryLbl.setManaged(false);

        Label resultLbl = new Label();
        resultLbl.setVisible(false);
        resultLbl.setManaged(false);

        // ── Scoreboard panel ───────────────────────────────────────────────────
        VBox scoreArea = new VBox(8, scoreLbl, periodLbl, weekLbl);
        scoreArea.setAlignment(Pos.CENTER);
        scoreArea.setPadding(new Insets(22, 30, 20, 30));

        VBox injuryResultArea = new VBox(10, goalLbl, cardLbl, injuryLbl, resultLbl);
        injuryResultArea.setAlignment(Pos.CENTER);
        injuryResultArea.setPadding(new Insets(0, 20, 14, 20));

        VBox scorePanel = new VBox(0,
            createTeamHeader(homeTeam, awayTeam),
            createDivider(),
            scoreArea,
            injuryResultArea
        );
        scorePanel.setAlignment(Pos.TOP_CENTER);
        scorePanel.setMaxWidth(590);
        scorePanel.setMinWidth(460);
        scorePanel.setStyle(
            "-fx-background-color: rgba(3, 10, 28, 0.90);" +
            "-fx-border-color: #1e4a9a;" +
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 16;" +
            "-fx-background-radius: 16;" +
            "-fx-effect: dropshadow(gaussian, rgba(0, 100, 255, 0.55), 42, 0, 0, 0);"
        );

        // ── Buttons ────────────────────────────────────────────────────────────
        Button continueBtn = createGameButton("▶  Continue",     false);
        Button subBtn      = createGameButton("⇄  Substitution", true);
        Button tacticBtn   = createGameButton("⚙  Tactics",      true);
        Button backBtn     = new Button("↩  Back to Menu");
        styleDangerBtn(backBtn);
        backBtn.setVisible(false);
        backBtn.setManaged(false);

        // ── Event handlers (game logic unchanged) ──────────────────────────────
        backBtn.setOnAction(e  -> Navigator.navigate(ViewType.MENU));
        tacticBtn.setOnAction(e -> Navigator.navigate(ViewType.TACTIC));
        subBtn.setOnAction(e    -> Navigator.navigate(ViewType.SUBSTITUTION));

        continueBtn.setOnAction(e -> {
            if (!gameService.isMatchFinished()) {
                gameService.playNextPeriod(gameService.getCurrentSquad());

                scoreLbl.setText(gameService.getHomeScore() + "  -  " + gameService.getAwayScore());
                periodLbl.setText(periodName + "  " + gameService.getCurrentPeriod() + " / " + gameService.getMaxPeriod());

                String goal = gameService.getLastGoalEvent();
                if (goal != null && !goal.isEmpty()) {
                    goalLbl.setText(goal);
                    goalLbl.setVisible(true);
                    goalLbl.setManaged(true);
                } else {
                    goalLbl.setVisible(false);
                    goalLbl.setManaged(false);
                }

                String card = gameService.getLastCardEvent();
                if (card != null && !card.isEmpty()) {
                    boolean isRed = card.contains("red");
                    cardLbl.setText(card);
                    cardLbl.setStyle(
                        "-fx-text-fill: " + (isRed ? "#ff4757" : "#ffd54f") + "; -fx-font-size: 13px; -fx-font-weight: bold;" +
                        "-fx-padding: 6 18; -fx-background-color: " + (isRed ? "#ff475718" : "#ffd54f18") + ";" +
                        "-fx-background-radius: 6; -fx-border-color: " + (isRed ? "#ff475744" : "#ffd54f44") + ";" +
                        "-fx-border-radius: 6; -fx-border-width: 1;"
                    );
                    cardLbl.setVisible(true);
                    cardLbl.setManaged(true);
                } else {
                    cardLbl.setVisible(false);
                    cardLbl.setManaged(false);
                }

                String injury = gameService.getInjuryMessage();
                if (injury != null && !injury.isEmpty()) {
                    injuryLbl.setText("⚠  " + injury);
                    injuryLbl.setVisible(true);
                    injuryLbl.setManaged(true);
                } else {
                    injuryLbl.setVisible(false);
                    injuryLbl.setManaged(false);
                }
            }

            if (gameService.isMatchFinished()) {
                gameService.finishMatch();
                persistSquadInjuries(gameService);

                scoreLbl.setText(gameService.getHomeScore() + "  -  " + gameService.getAwayScore());
                periodLbl.setText("FULL  TIME");
                periodLbl.setStyle(
                    "-fx-text-fill: #90caf9; -fx-font-size: 13px;" +
                    "-fx-font-weight: bold; -fx-letter-spacing: 2;"
                );
                weekLbl.setVisible(false);
                weekLbl.setManaged(false);

                String result = gameService.getMatchResult();
                if (result.contains("Won")) {
                    resultLbl.setText("🏆  " + result);
                    resultLbl.setStyle(
                        "-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #00e676;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,230,118,0.75), 30, 0, 0, 0);"
                    );
                } else if (result.contains("Lost")) {
                    resultLbl.setText(result);
                    resultLbl.setStyle(
                        "-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #ff4757;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255,71,87,0.55), 24, 0, 0, 0);"
                    );
                } else {
                    resultLbl.setText(result);
                    resultLbl.setStyle(
                        "-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #ffa502;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255,165,2,0.55), 24, 0, 0, 0);"
                    );
                }
                resultLbl.setVisible(true);
                resultLbl.setManaged(true);
                Styles.applyFadeIn(resultLbl);

                goalLbl.setVisible(false);   goalLbl.setManaged(false);
                cardLbl.setVisible(false);   cardLbl.setManaged(false);
                injuryLbl.setVisible(false); injuryLbl.setManaged(false);
                continueBtn.setVisible(false); continueBtn.setManaged(false);
                subBtn.setVisible(false);      subBtn.setManaged(false);
                tacticBtn.setVisible(false);   tacticBtn.setManaged(false);
                backBtn.setVisible(true);      backBtn.setManaged(true);
            }
        });

        // ── Button bar ─────────────────────────────────────────────────────────
        HBox buttonBar = new HBox(14, continueBtn, subBtn, tacticBtn, backBtn);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(14, 20, 18, 20));
        buttonBar.setStyle(
            "-fx-background-color: rgba(2, 7, 18, 0.92);" +
            "-fx-border-color: #1e3a6a; -fx-border-width: 1 0 0 0;"
        );

        // ── Content layout ─────────────────────────────────────────────────────
        VBox centerContent = new VBox(scorePanel);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(14, 20, 8, 20));

        BorderPane contentPane = new BorderPane();
        contentPane.setBackground(Background.EMPTY);
        contentPane.setCenter(centerContent);
        contentPane.setBottom(buttonBar);

        // ── Stadium background (Canvas, redraws on resize) ─────────────────────
        Canvas bgCanvas = new Canvas(650, 500);
        drawBackground(bgCanvas, 650, 500);

        StackPane root = new StackPane(bgCanvas, contentPane);
        root.setStyle("-fx-background-color: #010811;");
        StackPane.setAlignment(bgCanvas, Pos.TOP_LEFT);

        root.widthProperty().addListener((obs, ov, nv) -> {
            bgCanvas.setWidth(nv.doubleValue());
            drawBackground(bgCanvas, nv.doubleValue(), bgCanvas.getHeight());
        });
        root.heightProperty().addListener((obs, ov, nv) -> {
            bgCanvas.setHeight(nv.doubleValue());
            drawBackground(bgCanvas, bgCanvas.getWidth(), nv.doubleValue());
        });

        Styles.applyFadeIn(root);
        return root;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  Background
    // ═══════════════════════════════════════════════════════════════════════════

    private void drawBackground(Canvas canvas, double w, double h) {
        canvas.setWidth(w);
        canvas.setHeight(h);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, w, h);

        // ── Base dark navy gradient ──────────────────────────────────────────
        gc.setFill(new LinearGradient(0, 0, 0, h, false, CycleMethod.NO_CYCLE,
            new Stop(0,    Color.web("#010811")),
            new Stop(0.6,  Color.web("#020c1b")),
            new Stop(1,    Color.web("#04101f"))
        ));
        gc.fillRect(0, 0, w, h);

        // ── Left stadium light glow ──────────────────────────────────────────
        gc.setFill(new RadialGradient(0, 0, 0, h * 0.16, w * 0.48, false, CycleMethod.NO_CYCLE,
            new Stop(0,    Color.web("#1a5aaa50")),
            new Stop(0.45, Color.web("#0a2a6625")),
            new Stop(1,    Color.TRANSPARENT)
        ));
        gc.fillOval(-w * 0.22, -h * 0.08, w * 0.72, h * 0.58);

        // ── Right stadium light glow ─────────────────────────────────────────
        gc.setFill(new RadialGradient(0, 0, w, h * 0.16, w * 0.48, false, CycleMethod.NO_CYCLE,
            new Stop(0,    Color.web("#1a4a9a50")),
            new Stop(0.45, Color.web("#0a1a6625")),
            new Stop(1,    Color.TRANSPARENT)
        ));
        gc.fillOval(w * 0.5, -h * 0.08, w * 0.72, h * 0.58);

        // ── Football pitch ───────────────────────────────────────────────────
        double fieldY = h * 0.76;
        gc.setFill(new LinearGradient(0, fieldY, 0, h, false, CycleMethod.NO_CYCLE,
            new Stop(0,   Color.web("#0b300b")),
            new Stop(0.5, Color.web("#0d3a0d")),
            new Stop(1,   Color.web("#0f400f"))
        ));
        gc.fillRect(0, fieldY, w, h - fieldY);

        // Alternating grass stripes
        double stripeW = w / 8.0;
        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0) {
                gc.setFill(Color.web("#ffffff07"));
                gc.fillRect(i * stripeW, fieldY, stripeW, h - fieldY);
            }
        }

        // ── Field markings ───────────────────────────────────────────────────
        gc.setStroke(Color.web("#1e661e55"));
        gc.setLineWidth(1.2);

        // Touchline (top of field)
        gc.strokeLine(0, fieldY, w, fieldY);

        // Center circle (partially visible)
        gc.strokeOval(w / 2 - 65, fieldY - 28, 130, 72);

        // Center spot
        gc.setFill(Color.web("#1e661e88"));
        gc.fillOval(w / 2 - 3, fieldY + 6, 7, 7);

        // Penalty area
        gc.setStroke(Color.web("#1e661e44"));
        gc.strokeRect(w * 0.31, fieldY, w * 0.38, h * 0.14);

        // Goal area (small box inside penalty)
        gc.setStroke(Color.web("#1e661e33"));
        gc.strokeRect(w * 0.40, fieldY, w * 0.20, h * 0.07);
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  Scoreboard helpers
    // ═══════════════════════════════════════════════════════════════════════════

    private HBox createTeamHeader(String homeTeam, String awayTeam) {
        // Home (left, red)
        Label homeLbl = new Label(homeTeam.toUpperCase());
        homeLbl.setMaxWidth(Double.MAX_VALUE);
        homeLbl.setAlignment(Pos.CENTER_RIGHT);
        homeLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;" +
            "-fx-padding: 13 20;" +
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #6b0000, #9b1c1c88);" +
            "-fx-background-radius: 14 0 0 0;"
        );
        HBox.setHgrow(homeLbl, Priority.ALWAYS);

        // VS badge (center)
        Label vs = createVsBadge();

        // Away (right, blue)
        Label awayLbl = new Label(awayTeam.toUpperCase());
        awayLbl.setMaxWidth(Double.MAX_VALUE);
        awayLbl.setAlignment(Pos.CENTER_LEFT);
        awayLbl.setStyle(
            "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;" +
            "-fx-padding: 13 20;" +
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #0e2f6688, #1565c0);" +
            "-fx-background-radius: 0 14 0 0;"
        );
        HBox.setHgrow(awayLbl, Priority.ALWAYS);

        HBox header = new HBox(0, homeLbl, vs, awayLbl);
        header.setAlignment(Pos.CENTER);
        return header;
    }

    private Label createVsBadge() {
        Label vs = new Label("VS");
        vs.setAlignment(Pos.CENTER);
        vs.setStyle(
            "-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;" +
            "-fx-padding: 12 24;" +
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #7b0000cc, #00007bcc);" +
            "-fx-border-color: rgba(255,255,255,0.18); -fx-border-width: 0 1 0 1;" +
            "-fx-effect: dropshadow(gaussian, rgba(180, 80, 255, 0.65), 20, 0, 0, 0);" +
            "-fx-min-width: 72px;"
        );
        return vs;
    }

    private Label createScoreLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
            "-fx-text-fill: #e8f8ff; -fx-font-size: 66px; -fx-font-weight: bold;" +
            "-fx-font-family: 'Courier New', monospace;" +
            "-fx-effect: dropshadow(gaussian, rgba(0, 220, 255, 0.80), 36, 0, 0, 0);"
        );
        return lbl;
    }

    private Label createInfoLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
            "-fx-text-fill: #90caf9; -fx-font-size: 12px; -fx-letter-spacing: 1;"
        );
        return lbl;
    }

    private Region createDivider() {
        Region div = new Region();
        div.setPrefHeight(1);
        div.setMaxWidth(Double.MAX_VALUE);
        div.setStyle("-fx-background-color: #1e4a8a55;");
        return div;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  Button helpers
    // ═══════════════════════════════════════════════════════════════════════════

    private Button createGameButton(String text, boolean secondary) {
        Button btn = new Button(text);
        btn.setPrefWidth(secondary ? 146 : 152);

        final String normalStyle;
        final String hoverStyle;

        if (secondary) {
            normalStyle =
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #90caf9; -fx-font-size: 13px; -fx-font-weight: bold;" +
                "-fx-padding: 10 18; -fx-background-radius: 10; -fx-cursor: hand;" +
                "-fx-border-color: #1565c0; -fx-border-width: 1.5; -fx-border-radius: 10;";
            hoverStyle =
                "-fx-background-color: #1565c033;" +
                "-fx-text-fill: #e3f2fd; -fx-font-size: 13px; -fx-font-weight: bold;" +
                "-fx-padding: 10 18; -fx-background-radius: 10; -fx-cursor: hand;" +
                "-fx-border-color: #42a5f5; -fx-border-width: 1.5; -fx-border-radius: 10;" +
                "-fx-effect: dropshadow(gaussian, rgba(66,165,245,0.45), 10, 0, 0, 2);";
        } else {
            normalStyle =
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #1976d2, #1565c0);" +
                "-fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold;" +
                "-fx-padding: 10 22; -fx-background-radius: 10; -fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(25,118,210,0.45), 12, 0, 0, 3);";
            hoverStyle =
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #42a5f5, #1976d2);" +
                "-fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold;" +
                "-fx-padding: 10 22; -fx-background-radius: 10; -fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(66,165,245,0.75), 18, 0, 0, 4);";
        }

        btn.setStyle(normalStyle);

        btn.setOnMouseEntered(e -> {
            if (!btn.isDisabled()) { btn.setStyle(hoverStyle); applyHoverScale(btn, 1.06); }
        });
        btn.setOnMouseExited(e -> {
            if (!btn.isDisabled()) { btn.setStyle(normalStyle); applyHoverScale(btn, 1.0); }
        });
        btn.disabledProperty().addListener((obs, wasDisabled, nowDisabled) ->
            btn.setStyle(nowDisabled
                ? "-fx-background-color: #12202e; -fx-text-fill: #2a3a4a; -fx-font-size: 13px;" +
                  "-fx-padding: 10 18; -fx-background-radius: 10;" +
                  "-fx-border-color: #12202e; -fx-border-width: 1.5; -fx-border-radius: 10;"
                : normalStyle)
        );

        return btn;
    }

    private void styleDangerBtn(Button btn) {
        final String normal =
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #b71c1c, #880e4f);" +
            "-fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold;" +
            "-fx-padding: 10 24; -fx-background-radius: 10; -fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(183,28,28,0.45), 14, 0, 0, 3);";
        final String hover =
            "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #e53935, #b71c1c);" +
            "-fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold;" +
            "-fx-padding: 10 24; -fx-background-radius: 10; -fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(229,57,53,0.65), 20, 0, 0, 4);";
        btn.setStyle(normal);
        btn.setOnMouseEntered(e -> { btn.setStyle(hover);  applyHoverScale(btn, 1.06); });
        btn.setOnMouseExited(e  -> { btn.setStyle(normal); applyHoverScale(btn, 1.0);  });
    }

    // Persist in-memory injury state to DB for all injured squad members.
    // Called once after finishMatch() so MyTeam/MySquad screens show correct status.
    private void persistSquadInjuries(GameService gameService) {
        java.util.ArrayList<Player> squad = gameService.getCurrentSquad();
        if (squad == null || squad.isEmpty()) return;

        Database db = Database.getInstance();
        boolean isFootball = squad.stream().anyMatch(p -> p instanceof FootballPlayer);

        if (isFootball) {
            FootballPlayerRepo fpr = new FootballPlayerRepo(db.getConnection());
            for (Player p : squad) {
                if (p instanceof FootballPlayer && p.getInjuryStatus() != 0) {
                    fpr.updatePlayer((FootballPlayer) p);
                }
            }
        } else {
            BasketballPlayerRepo bpr = new BasketballPlayerRepo(db.getConnection());
            for (Player p : squad) {
                if (p instanceof BasketballPlayer && p.getInjuryStatus() != 0) {
                    bpr.updatePlayer((BasketballPlayer) p);
                }
            }
        }
    }

    private void applyHoverScale(Button btn, double scale) {
        ScaleTransition st = new ScaleTransition(Duration.millis(100), btn);
        st.setToX(scale);
        st.setToY(scale);
        st.play();
    }
}
