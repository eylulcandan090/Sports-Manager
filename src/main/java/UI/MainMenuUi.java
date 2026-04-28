package UI;

import Database.Database;
import Model.Team;
import Repository.GameRepo;
import Repository.TeamRepo;
import Service.TeamService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class MainMenuUi {

    private BorderPane root;
    private StackPane centerPane;

    public Parent getView() {

        root = new BorderPane();

        // TOP BAR
        HBox topBar = createTopBar();
        root.setTop(topBar);

        // LEFT MENU
        VBox menu = createMenu();
        root.setLeft(menu);

        // CENTER PANEL
        centerPane = new StackPane();
        centerPane.getChildren().add(new Label("Welcome! Select an option from the left."));
        root.setCenter(centerPane);

        return root;
    }

    // 🔷 TOP BAR
    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(20);
        topBar.setStyle("-fx-background-color: #2c3e50;");

        Database database=Database.getInstance();
        GameRepo repo=new GameRepo(database.getConnection());

        String x=repo.getGameTeamById(repo.getGameTeamId());

        Label teamLabel = new Label("Team:"+x);



        teamLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        Label weekLabel = new Label("Week: 1");
        weekLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(teamLabel, spacer, weekLabel);

        return topBar;
    }


    private VBox createMenu() {
        VBox menu=new VBox();
        menu.setPadding(new Insets(15));
        menu.setSpacing(10);
        menu.setPrefWidth(150);
        menu.setStyle("-fx-background-color: #34495e;");

        Button teamBtn = createMenuButton("My Team");
        Button trainingBtn = createMenuButton("Training");
        Button fixturesBtn = createMenuButton("Fixtures");
        Button tableBtn = createMenuButton("League Table");
        Button matchBtn = createMenuButton("Play Match");

        // 🎯 BUTTON ACTIONS
        teamBtn.setOnAction(e -> showContent(new Label("Team Screen")));
        trainingBtn.setOnAction(e -> showContent(new Label("Training Screen")));
        fixturesBtn.setOnAction(e -> Navigator.navigate(ViewType.FIXTURE));

        tableBtn.setOnAction(e->{
            System.out.println("buse");
            Navigator.navigate(ViewType.LEAGUETABLE);
        });

        //tableBtn.setOnAction(e -> showContent(new Label("League Table")));
        matchBtn.setOnAction(e -> showContent(new Label("Match Screen")));

        menu.getChildren().addAll(
                teamBtn,
                trainingBtn,
                fixturesBtn,
                tableBtn,
                matchBtn
        );

        return menu;
    }

    // 🔷 BUTTON STYLE
    private Button createMenuButton(String text) {
        Button btn=new Button(text);
        btn.setPrefWidth(130);
        btn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-font-size: 14px;"
        );
        return btn;
    }

    // 🔷 CENTER CHANGE
    private void showContent(Parent content) {
        centerPane.getChildren().clear();
        centerPane.getChildren().add(content);
    }
}