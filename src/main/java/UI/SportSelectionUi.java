package UI;

import Model.SportEntity;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SportSelectionUi {
    public Parent getView(){
        VBox root=new VBox();
        Label header=new Label("Sport");
        root.getChildren().add(header);
        header.setFont(Font.font(30));


        //ileride sporlar database den çekilip otomatik buton yaratabiliriz


        Button football=new Button("  Model.Football.Football  ");
        Button basketball=new Button("Basketball");


        football.setOnAction(e->{
            SportEntity sport=new SportEntity("Model.Football.Football");
            Navigator.navigate(ViewType.TEAMSELECTION,sport);
        });

        basketball.setOnAction(e->{
            SportEntity sport=new SportEntity("Basketball");
            Navigator.navigate(ViewType.TEAMSELECTION,sport);
        });



        root.getChildren().add(football);
        root.getChildren().add(basketball);

        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);

        return root;

    }
}
