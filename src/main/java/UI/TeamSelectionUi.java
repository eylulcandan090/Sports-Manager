package UI;
import javafx.scene.control.Label;
import Database.Database;
import Model.Sport;
import Model.SportEntity;
import Model.Team;
import Repository.GameRepo;
import Repository.TeamRepo;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TeamSelectionUi {
    public Parent getView(SportEntity sport,TeamRepo repo,GameRepo gameRepo){
        VBox root=new VBox();
        root.setStyle(Styles.rootBg());
        root.setPadding(new javafx.geometry.Insets(20));
        root.setSpacing(12);
        Label title = new Label("Select your team");
        title.setStyle("-fx-text-fill: #f48fb1; -fx-font-size: 22px; -fx-font-weight: bold;");
        title.setAlignment(Pos.CENTER);

        ListView<Team> teamListView=new ListView<>();



        switch (sport.getSport()) {
            case "Football":
                teamListView.getItems().addAll(repo.getAllTeamsBySport("Football"));
                break;
            case "Basketball":
                teamListView.getItems().addAll(repo.getAllTeamsBySport("Basketball"));
                break;
        }

        Button startGame=new Button("Start");
        Styles.styleButton(startGame);

        startGame.setOnAction(e->{
            Team selected=teamListView.getSelectionModel().getSelectedItem();

            if(selected!=null){
                gameRepo.createNewGame(selected.getId());
                gameRepo.setGameStarted(true);
                System.out.println(selected.getId());
                Navigator.navigate(ViewType.MENU);
            }
            else{
                AlertUtility.showWarning("No Team Selected","Please select a team to continue.");
            }
        });
Button backButton =new Button("<-Change sport");
Styles.styleDangerButton(backButton);
backButton.setOnAction(e->Navigator.navigate(ViewType.SPORTSELECTION));
        HBox buttons =new HBox(10,backButton,startGame);

        root.getChildren().addAll(title,teamListView,buttons);
        root.setAlignment(Pos.CENTER);

        return root;

    }
}
