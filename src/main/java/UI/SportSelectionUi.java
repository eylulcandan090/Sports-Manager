package UI;

import Database.Database;
import Model.FixtureGenerator;
import Model.SportEntity;
import Model.Team;
import Repository.TeamRepo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class SportSelectionUi {

    public Parent getView(TeamRepo teamRepo, Database database) {
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));

        Label header = new Label("SELECT A SPORT");
        header.getStyleClass().add("title-label");

        Label sub = new Label("Choose your sport to start the game");
        sub.getStyleClass().add("subtitle-label");

        Button football   = sportCard("⚽", "Football",   "#e040fb");
        Button basketball = sportCard("🏀", "Basketball", "#ffd54f");

        football.setOnAction(e -> {
            ArrayList<Team> teams = teamRepo.getAllTeamsBySport("Football");
            FixtureGenerator.generateAndSave(teams, database.getConnection());
            Navigator.navigate(ViewType.TEAMSELECTION, new SportEntity("Football"));
        });

        basketball.setOnAction(e -> {
            ArrayList<Team> teams = teamRepo.getAllTeamsBySport("Basketball");
            FixtureGenerator.generateAndSave(teams, database.getConnection());
            Navigator.navigate(ViewType.TEAMSELECTION, new SportEntity("Basketball"));
        });

        HBox cards = new HBox(30, football, basketball);
        cards.setAlignment(Pos.CENTER);

        root.getChildren().addAll(header, sub, cards);
        return root;
    }

    private Button sportCard(String emoji, String name, String color) {
        Button btn = new Button(emoji + "\n" + name);
        btn.setStyle(
            "-fx-background-color: #112240;" +
            "-fx-text-fill: " + color + ";" +
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;" +
            "-fx-pref-width: 160px;" +
            "-fx-pref-height: 120px;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: " + color + ";" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 12;" +
            "-fx-cursor: hand;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;" +
            "-fx-pref-width: 160px;" +
            "-fx-pref-height: 120px;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: " + color + ";" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 12;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 12, 0, 0, 4);"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: #112240;" +
            "-fx-text-fill: " + color + ";" +
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;" +
            "-fx-pref-width: 160px;" +
            "-fx-pref-height: 120px;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: " + color + ";" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 12;" +
            "-fx-cursor: hand;"
        ));
        return btn;
    }
}
