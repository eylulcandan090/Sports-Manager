package UI;

import Model.FormationSlot;
import Model.Player;
import Model.Sport;
import Service.GameService;
import Service.TeamService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MatchSquadUi {
    public Parent getView(GameService gameService, TeamService teamService) {
        List<FormationSlot> slotList = new ArrayList<>();
        int teamId = gameService.getGameTeamId();
        Sport sport = teamService.getSportByTeamId(teamId);

        ArrayList<Player> players = teamService.getHealtyPlayers(teamId);


        int squadSize=sport.getPlayersPerTeam();


        ObservableList<Player> availableList=FXCollections.observableArrayList(players);
        ListView<Player> availableView=new ListView<>(availableList);

        ObservableList<Player> selectedList=FXCollections.observableArrayList();
        ListView<Player> selectedView=new ListView<>(selectedList);

        Button addBtn=new Button(">>");
        Button removeBtn=new Button("<<");

        addBtn.setOnAction(e -> {
            Player p = availableView.getSelectionModel().getSelectedItem();
            if (p != null && selectedList.size() < squadSize) {
                selectedList.add(p);
                availableList.remove(p);
            }
        });

        removeBtn.setOnAction(e -> {
            Player p = selectedView.getSelectionModel().getSelectedItem();
            if (p != null) {
                availableList.add(p);
                selectedList.remove(p);
            }
        });

        VBox middle = new VBox(10, addBtn, removeBtn);
        middle.setAlignment(Pos.CENTER);
        middle.setPadding(new Insets(10));


        Label counter = new Label();
        counter.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Squad: " + selectedList.size() + " / " + squadSize,
                        selectedList
                )
        );


        Button confirmBtn=new Button("Start Match");
        confirmBtn.disableProperty().bind(
                Bindings.size(selectedList).isNotEqualTo(squadSize)
        );


        Label leftHeader =new Label("Available Players (" + players.size() + ")");
        Label rightHeader=new Label("Match Squad");

        VBox leftBox=new VBox(5, leftHeader, availableView);
        VBox rightBox=new VBox(5, rightHeader, selectedView);
        VBox.setVgrow(availableView,Priority.ALWAYS);
        VBox.setVgrow(selectedView,Priority.ALWAYS);

        HBox center=new HBox(10,leftBox,middle,rightBox);
        HBox.setHgrow(leftBox,Priority.ALWAYS);
        HBox.setHgrow(rightBox,Priority.ALWAYS);
        center.setPadding(new Insets(10));

        HBox bottom=new HBox(15,counter,confirmBtn);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        bottom.setPadding(new Insets(10));

        BorderPane root=new BorderPane();
        root.setCenter(center);
        root.setBottom(bottom);

        return root;
    }
}
