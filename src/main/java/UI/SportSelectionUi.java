package UI;

import Database.Database;
import Model.FixtureGenerator;
import Model.SportEntity;
import Model.Team;
import Repository.SportRepo;
import Repository.TeamRepo;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class SportSelectionUi {
    private static final String FOOTBALL="Football";

    public Parent getView(TeamRepo teamRepo,Database database){
        VBox root=new VBox();
        Label header=new Label("Sport");
        root.getChildren().add(header);
        header.setFont(Font.font(30));


        //ileride sporlar database den çekilip otomatik buton yaratabiliriz






        Button football=new Button("  Football  ");
        Button basketball=new Button("Basketball");


        football.setOnAction(e->{
            SportEntity sport=new SportEntity(FOOTBALL);
            ArrayList<Team> teams=teamRepo.getAllTeamsBySport(FOOTBALL);
            FixtureGenerator.generateAndSave(teams,database.getConnection());



            Navigator.navigate(ViewType.TEAMSELECTION,sport);
        });

        basketball.setOnAction(e->{
            SportEntity sport=new SportEntity("Basketball");
            ArrayList<Team> teams=teamRepo.getAllTeamsBySport("Basketball");
            FixtureGenerator.generateAndSave(teams,database.getConnection());
            Navigator.navigate(ViewType.TEAMSELECTION,sport);
        });



        root.getChildren().add(football);
        root.getChildren().add(basketball);

        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);

        return root;

    }
}
