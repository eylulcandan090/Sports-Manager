package UI;

import Database.Database;
import Repository.GameRepo;
import Service.GameService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;


public class MainMenuUi {

    private BorderPane root;
    private StackPane centerPane;

    public Parent getView(GameService gameService) {
        root = new BorderPane();

        root.setTop(createTopBar(gameService));
        root.setLeft(createMenu());

        centerPane = new StackPane();
        Label welcome = new Label("Select an option from the menu.");
        welcome.setStyle(Styles.SUBTITLE_LABEL);
        centerPane.getChildren().add(welcome);
        centerPane.setStyle("-fx-background-color: #0d1b2a;");
        root.setCenter(centerPane);

        return root;
    }

    private HBox createTopBar(GameService gameService) {
        HBox topBar = new HBox();
        topBar.setStyle(Styles.TOPBAR);
        topBar.setPadding(new Insets(12, 16, 12, 16));
        topBar.setSpacing(20);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Database database = Database.getInstance();
        GameRepo repo = new GameRepo(database.getConnection());
        String teamName = repo.getGameTeamById(repo.getGameTeamId());

        Label teamLabel = new Label("⚽  " + teamName);
        teamLabel.setStyle(Styles.TEAM_NAME_LABEL);

        Label weekLabel = new Label("Week " + gameService.getCurrentWeek());
        weekLabel.setStyle(Styles.TOPBAR_LABEL);

        Button musicBtn = new Button(Navigator.isMusicPlaying() ? "🔊" : "🔇");
        musicBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #ccd6f6;" +
            "-fx-font-size: 16px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 4 8;"
        );
        musicBtn.setOnAction(e -> {
            Navigator.toggleMusic();
            musicBtn.setText(Navigator.isMusicPlaying() ? "🔊" : "🔇");
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(teamLabel, spacer, weekLabel, musicBtn);
        return topBar;
    }

    private VBox createMenu() {
        VBox menu = new VBox(4);
        menu.setStyle(Styles.SIDEBAR);
        menu.setPadding(new Insets(16, 10, 16, 10));
        menu.setPrefWidth(160);

        Button teamBtn     = menuButton("👥  My Team");
        Button trainingBtn = menuButton("🏋  Training");
        Button fixturesBtn = menuButton("📅  Fixtures");
        Button tableBtn    = menuButton("🏆  League Table");
        Button matchBtn    = menuButton("▶  Play Match");

        teamBtn.setOnAction(e     -> Navigator.navigate(ViewType.MYTEAM));
        trainingBtn.setOnAction(e -> Navigator.navigate(ViewType.TRAINING));
        fixturesBtn.setOnAction(e -> Navigator.navigate(ViewType.FIXTURE));
        tableBtn.setOnAction(e    -> Navigator.navigate(ViewType.LEAGUETABLE));
        matchBtn.setOnAction(e    -> Navigator.navigate(ViewType.MYSQUAD));

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button backBtn = menuButton("← Main Menu");
        Styles.styleDangerButton(backBtn);
        backBtn.setOnAction(e -> Navigator.navigate(ViewType.START));

        menu.getChildren().addAll(teamBtn, trainingBtn, fixturesBtn, tableBtn, matchBtn, spacer, backBtn);
        return menu;
    }

    private Button menuButton(String text) {
        Button btn = new Button(text);
        Styles.styleMenuButton(btn);
        return btn;
    }

    private void showContent(Parent content) {
        centerPane.getChildren().clear();
        centerPane.getChildren().add(content);
    }
}
