package UI;

import Model.FormationSlot;
import Model.Sport;
import Service.GameService;
import Service.TeamService;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.List;

public class MatchSquad {
    public Parent getView(GameService gameService, TeamService teamService){
        List<FormationSlot> slotList=new ArrayList<>();
        int teamId=gameService.getGameTeamId();
        Sport sport=teamService.getSportByTeamId(teamId);





    }
}
