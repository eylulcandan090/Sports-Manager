package UI;

import Database.Database;
import Repository.FixtureRepo;
import Repository.TeamRepo;
import Service.FixtureService;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class FixtureUi {
    public Parent getView(FixtureService fixtureService){
        VBox root=new VBox();
        ListView<String> listView=new ListView<>();

        Button back=new Button("<-Back");
        back.setOnAction(e->{
            Navigator.navigate(ViewType.MENU);
        });



        listView.getItems().addAll(fixtureService.printFixture());

        root.getChildren().addAll(listView,back);

        root.setAlignment(Pos.BOTTOM_LEFT);

        return root;
    }
}
