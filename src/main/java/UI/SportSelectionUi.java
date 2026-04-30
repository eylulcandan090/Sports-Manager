package UI;

import Database.Database;
import Model.FixtureGenerator;
import Model.SportEntity;
import Model.Team;
import Repository.TeamRepo;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class SportSelectionUi {

    public Parent getView(TeamRepo teamRepo, Database database) {
        VBox root = new VBox();
        Label header = new Label("Sport");
        Label sub = new Label("Choose a sport");
        header.setFont(Font.font(30));
        sub.setFont(Font.font(15));
        root.getChildren().addAll(header,sub);

        Button football = new Button("⚽ Football");
        Button basketball = new Button("🏀 Basketball");


        football.setPrefWidth(160);
        basketball.setPrefWidth(160);


        football.setOnAction(e -> {
            SportEntity sport = new SportEntity("Football");
            ArrayList<Team> teams = teamRepo.getAllTeamsBySport("Football");
            FixtureGenerator.generateAndSave(teams, database.getConnection());
            Navigator.navigate(ViewType.TEAMSELECTION, sport);
        });

        basketball.setOnAction(e -> {
            SportEntity sport = new SportEntity("Basketball");
            ArrayList<Team> teams = teamRepo.getAllTeamsBySport("Basketball");
            FixtureGenerator.generateAndSave(teams, database.getConnection());
            Navigator.navigate(ViewType.TEAMSELECTION, sport);
        });

        root.getChildren().addAll(football, basketball);
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);

        return root;
    }
}