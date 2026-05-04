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

            StringBuilder sb=new StringBuilder();
            if(sport instanceof Football){
                //ArrayList<Player> players=footballPlayerRepo.getFootballPlayersByTeamId(teamId);
                ArrayList<Player> players=footballPlayerRepo.getHealthyFootballPlayers(teamId);
                Coach tempCoach=new Coach("Trainer",selected,1,teamId);
                for(Player p:players){
                    FootballPlayer fp=(FootballPlayer) p;
                   switch (selected){
                       case SHOOT:   fp.setShooting(fp.getShooting()+1);   break;
                       case PASS:    fp.setPassing(fp.getPassing()+1);     break;
                       case DEFANCE: fp.setDefance(fp.getDefance()+1);     break;
                   }
                   footballPlayerRepo.updatePlayer(fp);
                   sb.append(fp.getName()+"\n");
                }

            } else if(sport instanceof Basketball){
                ArrayList<BasketballPlayer> players=basketballPlayerRepo.getPlayersByTeam(teamId);
                for(BasketballPlayer bp:players){
                    switch(selected){
                        case SHOOT:   bp.setShooting(bp.getShooting()+1);   break;
                        case PASS:    bp.setPassing(bp.getPassing()+1);     break;
                        case DEFANCE: bp.setDefense(bp.getDefense()+1);     break;
                        case BLOCKING: bp.setBlock(bp.getBlock()+1); break;
                        case DRIBBLING: bp.setDribbling(bp.getDribbling()+1); break;
                        case STEALING: bp.setSteal(bp.getSteal()+1); break;
                    }
                    basketballPlayerRepo.updatePlayer(bp);
                }
            }

            AlertUtility.showInfo("Training Applied Successfully ✅","Trained Players:\n"+sb.toString());
        });

        VBox.setVgrow(trainings, javafx.scene.layout.Priority.ALWAYS);

        HBox buttons=new HBox(20, back, train);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new javafx.geometry.Insets(12));

        root.getChildren().addAll(head, trainings, buttons);

        return root;
    }
}
