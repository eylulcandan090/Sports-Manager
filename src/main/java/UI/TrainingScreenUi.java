package UI;

import Model.Basketball.Basketball;
import Model.Basketball.BasketballPlayer;
import Model.Coach;
import Model.Football.Football;
import Model.Football.FootballPlayer;
import Model.Player;
import Model.Sport;
import Model.TrainingType;
import Repository.BasketballPlayerRepo;
import Repository.FootballPlayerRepo;
import Service.GameService;
import Service.TeamService;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class TrainingScreenUi {
    public Parent getView(GameService gameService, TeamService teamService,
                          FootballPlayerRepo footballPlayerRepo, BasketballPlayerRepo basketballPlayerRepo){

        VBox root=new VBox();
        ListView<TrainingType> trainings=new ListView<>();

        int teamId=gameService.getGameTeamId();

        HBox head=new HBox();

        Label header=new Label("Training Type");
        header.setFont(Font.font(20));
        head.setStyle("-fx-background-color: #34495e;");
        head.getChildren().add(header);
        head.setAlignment(Pos.TOP_CENTER);

        Sport sport=teamService.getSportByTeamId(teamId);
        trainings.getItems().addAll(sport.getTrainingTypes());

        Button train=new Button("Train");
        Button back=new Button("<-Back");
        back.setOnAction(e->Navigator.navigate(ViewType.MENU));

        train.setOnAction(e->{
            TrainingType selected=trainings.getSelectionModel().getSelectedItem();
            if(selected==null){
                AlertUtility.showWarning("No Selection","Please select a training type.");
                return;
            }

            if(sport instanceof Football){
                ArrayList<Player> players=footballPlayerRepo.getFootballPlayersByTeamId(teamId);
                Coach tempCoach=new Coach("Trainer",selected,1,teamId);
                for(Player p:players){
                    if(p instanceof FootballPlayer){
                        FootballPlayer fp=(FootballPlayer) p;
                        tempCoach.train(fp);
                        footballPlayerRepo.updatePlayer(fp);
                    }
                }
            } else if(sport instanceof Basketball){
                ArrayList<BasketballPlayer> players=basketballPlayerRepo.getPlayersByTeam(teamId);
                for(BasketballPlayer bp:players){
                    switch(selected){
                        case SHOOT:   bp.setShooting(bp.getShooting()+1);   break;
                        case PASS:    bp.setPassing(bp.getPassing()+1);     break;
                        case DEFANCE: bp.setDefense(bp.getDefense()+1);     break;
                    }
                    basketballPlayerRepo.updatePlayer(bp);
                }
            }

            AlertUtility.showInfo("Training Completed", selected+" training applied to all players.");
        });

        HBox backH=new HBox();
        backH.getChildren().add(back);
        backH.setAlignment(Pos.BASELINE_LEFT);

        HBox trainH=new HBox();
        trainH.setAlignment(Pos.CENTER);
        trainH.getChildren().add(train);

        root.getChildren().addAll(head,trainings,backH,trainH);

        return root;
    }
}
