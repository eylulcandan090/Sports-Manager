package UI;

import Database.Database;
import Model.Team;
import Repository.GameRepo;
import Repository.LeagueRepo;
import Repository.TeamRepo;
import Service.GameService;
import Service.LeagueService;
import Service.TeamService;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import javafx.scene.control.ListCell;

import java.util.ArrayList;
import java.util.Collections;

public class LeagueTableUi {
    public Parent getView(){
        Database database=Database.getInstance();
        GameRepo gameRepo=new GameRepo(database.getConnection());
        GameService gameService=new GameService(gameRepo);
        LeagueRepo leagueRepo=new LeagueRepo(database.getConnection());
        LeagueService leagueService=new LeagueService(leagueRepo);
        TeamRepo teamRepo=new TeamRepo(database.getConnection());
        ListView<String> listView=new ListView<>();

        int teamId= gameService.getGameTeamId();
        int leagueId=leagueService.getLeagueIdByTeamName(teamId);

        ArrayList<Team> teamArrayList=teamRepo.getAllTeamsByLeagueId(leagueId);
        Collections.sort(teamArrayList);

        int i=1;
        for(Team t:teamArrayList){
            listView.getItems().add(i+"-"+t.getName()+"   "+t.getPoint());
            i++;
        }

        listView.setCellFactory(lv->new ListCell<String>() {
            @Override
            protected void updateItem(String item,boolean empty) {
                super.updateItem(item,empty);

                if (empty||item==null) {
                    setText(null);
                    setStyle("");
                    return;
                }

                setText(item);

                int index=getIndex();
                int size=lv.getItems().size();

                if (index==0) {
                    setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
                }

                else if (index>=1 && index<=3) {
                    setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                }
                else if (index>=size-3) {
                    setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
                }
                else {
                    setStyle("");
                }
            }
        });


        Button back=new Button("<-Back");
        back.setOnAction(e->{
            Navigator.navigate(ViewType.MENU);
        });




        VBox root=new VBox();
        root.getChildren().add(listView);
        root.getChildren().add(back);
        root.setAlignment(Pos.BOTTOM_LEFT);


        return root;

    }
}
