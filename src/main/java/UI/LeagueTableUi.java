package UI;

import Database.Database;
import Model.Team;
import Repository.GameRepo;
import Repository.LeagueRepo;
import Repository.TeamRepo;
import Service.GameService;
import Service.LeagueService;
import javafx.scene.Parent;
import javafx.scene.control.ListView;

import java.util.ArrayList;

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

        int i=1;
        for(Team t:teamArrayList){
            listView.getItems().add(i+"-"+t.getName()+"   "+t.getPoint());
            i++;
        }


        return listView;

    }
}
