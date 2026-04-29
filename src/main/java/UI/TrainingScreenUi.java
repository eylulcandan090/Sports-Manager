package UI;

import Model.Sport;
import Model.TrainingType;
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

public class TrainingScreenUi {
    public Parent getView(GameService gameService,TeamService teamService){

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
