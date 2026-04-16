package org.example.mygui2.ui;

import Model.FootballTeam;
import Model.Team;
import Repo.TeamRepo;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TeamSelectionUi {
    private TeamRepo teamRepo;

    public TeamSelectionUi(TeamRepo repo){
        this.teamRepo=repo;
    }

    public Scene getScene(Stage stage){
        ArrayList<Team> teams=teamRepo.getTeams();
        ListView<String> listView=new ListView<>();

        for(Team t:teams){
            listView.getItems().add(t.getTeamName()+" "+teamRepo.getTeamRating(t.getTeamName()));
        }

        Scene scene=new Scene(listView,500,300);

        return scene;



    }
}
