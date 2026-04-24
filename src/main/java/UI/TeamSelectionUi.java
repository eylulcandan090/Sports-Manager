package UI;

import Database.Database;
import Model.Sport;
import Model.SportEntity;
import Model.Team;
import Repository.TeamRepo;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class TeamSelectionUi {
    public Parent getView(SportEntity sport){
        VBox root=new VBox();
        ListView<Team> teamListView=new ListView<>();

        Database database=Database.getInstance();
        TeamRepo repo=new TeamRepo(database.getConnection());

        switch (sport.getSport()){
            case "Football":
                System.out.println("You are here");
                teamListView.getItems().addAll(repo.getAllTeamsBySport("Football"));
                break;
            case "Basketball":
                teamListView.getItems().addAll(repo.getAllTeamsBySport("Basketball"));
                break;
        }

        Button startGame=new Button("Start");

        startGame.setOnAction(e->{
            Team selected=teamListView.getSelectionModel().getSelectedItem();

            if(selected!=null){
                Navigator.navigate(ViewType.MENU);
            }
            else{
                AlertUtility.showWarning("No Team Selected","Please select a team to continue.");
            }
        });


        root.getChildren().addAll(teamListView,startGame);
        root.setAlignment(Pos.CENTER);

        return root;

    }
}
