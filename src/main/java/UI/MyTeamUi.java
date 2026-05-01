package UI;

import Model.Basketball.BasketballPlayer;
import Model.Football.FootballPlayer;
import Model.Player;
import Model.Sport;
import Model.Football.Football;
import Model.Basketball.Basketball;
import Repository.BasketballPlayerRepo;
import Repository.FootballPlayerRepo;
import Service.GameService;
import Service.TeamService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class MyTeamUi {

    public Parent getView(GameService gameService, TeamService teamService,
                          FootballPlayerRepo footballPlayerRepo,
                          BasketballPlayerRepo basketballPlayerRepo) {

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setPrefHeight(Double.MAX_VALUE);

        int teamId = gameService.getGameTeamId();
        Sport sport = teamService.getSportByTeamId(teamId);

        if (sport instanceof Football) {
            // FUTBOL TABLOSU
            ArrayList<Player> players = footballPlayerRepo.getFootballPlayersByTeamId(teamId);

            TableView<FootballPlayer> table = new TableView<>();
            VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);

            TableColumn<FootballPlayer, String> nameCol = new TableColumn<>("Name");
            nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
            nameCol.setPrefWidth(160);

            TableColumn<FootballPlayer, String> posCol = new TableColumn<>("Position");
            posCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPosition()));
            posCol.setPrefWidth(80);

            TableColumn<FootballPlayer, Integer> ageCol = new TableColumn<>("Age");
            ageCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAge()).asObject());
            ageCol.setPrefWidth(50);

            TableColumn<FootballPlayer, Integer> shootCol = new TableColumn<>("Shooting");
            shootCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getShooting()).asObject());
            shootCol.setPrefWidth(80);

            TableColumn<FootballPlayer, Integer> passCol = new TableColumn<>("Passing");
            passCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPassing()).asObject());
            passCol.setPrefWidth(80);

            TableColumn<FootballPlayer, Integer> defCol = new TableColumn<>("Defence");
            defCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getDefance()).asObject());
            defCol.setPrefWidth(80);

            TableColumn<FootballPlayer, String> injuryCol = new TableColumn<>("Injury");
            injuryCol.setCellValueFactory(data -> {
                int status = data.getValue().getInjuryStatus();
                return new SimpleStringProperty(status == 0 ? "Healthy" : "Injured (" + status + ")");
            });
            injuryCol.setPrefWidth(110);

            table.getColumns().addAll(nameCol, posCol, ageCol, shootCol, passCol, defCol, injuryCol);

            for (Player p : players) {
                if (p instanceof FootballPlayer) {
                    table.getItems().add((FootballPlayer) p);
                }
            }




            root.getChildren().add(table);

        } else if (sport instanceof Basketball) {
            // BASKETBOL TABLOSU
            ArrayList<BasketballPlayer> players = basketballPlayerRepo.getPlayersByTeam(teamId);

            TableView<BasketballPlayer> table = new TableView<>();
            VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);

            TableColumn<BasketballPlayer, String> nameCol = new TableColumn<>("Name");
            nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
            nameCol.setPrefWidth(160);

            TableColumn<BasketballPlayer, String> posCol = new TableColumn<>("Position");
            posCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPosition()));
            posCol.setPrefWidth(60);

            TableColumn<BasketballPlayer, Integer> ageCol = new TableColumn<>("Age");
            ageCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAge()).asObject());
            ageCol.setPrefWidth(45);

            TableColumn<BasketballPlayer, Integer> shootCol = new TableColumn<>("Shoot");
            shootCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getShooting()).asObject());
            shootCol.setPrefWidth(55);

            TableColumn<BasketballPlayer, Integer> dribCol = new TableColumn<>("Dribble");
            dribCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getDribbling()).asObject());
            dribCol.setPrefWidth(60);

            TableColumn<BasketballPlayer, Integer> passCol = new TableColumn<>("Pass");
            passCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPassing()).asObject());
            passCol.setPrefWidth(50);

            TableColumn<BasketballPlayer, Integer> defCol = new TableColumn<>("Defense");
            defCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getDefense()).asObject());
            defCol.setPrefWidth(60);

            TableColumn<BasketballPlayer, Integer> blockCol = new TableColumn<>("Block");
            blockCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getBlock()).asObject());
            blockCol.setPrefWidth(55);

            TableColumn<BasketballPlayer, String> injuryCol = new TableColumn<>("Injury");
            injuryCol.setCellValueFactory(data -> {
                int status = data.getValue().getInjuryStatus();
                return new SimpleStringProperty(status == 0 ? "Healthy" : "Injured (" + status + ")");
            });
            injuryCol.setPrefWidth(110);

            table.getColumns().addAll(nameCol, posCol, ageCol, shootCol, dribCol, passCol, defCol, blockCol, injuryCol);
            table.getItems().addAll(players);


            root.getChildren().add(table);


        } else {
            root.getChildren().add(new Label("Sport not found."));
        }

        Button backBtn = new Button("⬅ Back");
        backBtn.setOnAction(e -> Navigator.navigate(ViewType.MENU));

        HBox backBox=new HBox(backBtn);
        backBox.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().add(backBox);


        root.setAlignment(Pos.TOP_CENTER);
        return root;
    }
}