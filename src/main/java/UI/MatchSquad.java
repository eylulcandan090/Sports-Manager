package UI;

import Model.FormationSlot;
import Model.Player;
import Model.Sport;
import Service.GameService;
import Service.TeamService;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MatchSquad {
    public Parent getView(GameService gameService, TeamService teamService){
        List<FormationSlot> slotList=new ArrayList<>();
        int teamId=gameService.getGameTeamId();
        Sport sport=teamService.getSportByTeamId(teamId);

        ArrayList<Player> players=teamService.getHealtyPlayers(teamId);

        ListView<Player> listView=new ListView<>();
        System.out.println(players.size());

        listView.getItems().addAll(players);

        System.out.println("I am working...");

        return listView;



    }
}
