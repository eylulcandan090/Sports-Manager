package UI;

import DataFeed.DataFeed;
import Database.Database;
import Model.Team;
import Repository.SportRepo;
import Repository.TeamRepo;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        Database database=Database.getInstance();
        DataFeed.feed();

        SportRepo repo=new SportRepo(database.getConnection());
        for(String s:repo.getAllSports()){
            System.out.println(s);
        }
        System.out.println("--------");

        TeamRepo repo1=new TeamRepo(database.getConnection());
        for(Team s:repo1.getAllTeamsByLeagueId(1)){
            System.out.println(s);
        }
        System.out.println("---------------------");
        System.out.println("---------------------");
        System.out.println("---------------------");

        for(Team s:repo1.getAllTeamsBySport("Model.Football.Football")){
            System.out.println(s);
        }




        Navigator.init(stage);
        Navigator.navigate(ViewType.START);
        stage.show();
    }
}
