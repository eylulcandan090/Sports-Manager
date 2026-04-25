package UI;

import Database.Database;
import Repository.GameRepo;
import Service.GameService;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class StartUi {
    public Parent getView(){
        Database database=Database.getInstance();
        GameRepo gameRepo=new GameRepo(database.getConnection());
        GameService gameService=new GameService(gameRepo);

        VBox root=new VBox();
        Label header=new Label("Sport Manager");
        header.setFont(Font.font(30));

        root.getChildren().add(header);

        Button newGame=new Button("    New Game    ");
        root.getChildren().add(newGame);

        if(gameService.hasGame()){
            Button continueGame=new Button("Continue Game");
            root.getChildren().add(continueGame);
        }

        newGame.setOnAction(e->Navigator.navigate(ViewType.SPORTSELECTION));

        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        return root;
    }
}
