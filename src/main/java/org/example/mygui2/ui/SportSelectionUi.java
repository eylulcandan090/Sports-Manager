package org.example.mygui2.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class SportSelectionUi {

    public Scene getScene(Stage stage){

        VBox root = new VBox();
        VBox labelLayout=new VBox();

        Label sportSelect=new Label("Select Model.Sport");
        labelLayout.getChildren().add(sportSelect);

        labelLayout.setAlignment(Pos.TOP_CENTER);

        sportSelect.setFont(Font.font(30));

        VBox buttonLayout=new VBox();

        Button football=new Button("Football");
        Button basketball=new Button("Basketball");
        Button volleyball=new Button("Voleybal");

        football.setMaxWidth(90);
        basketball.setMaxWidth(90);
        volleyball.setMaxWidth(90);

        basketball.setDisable(true);
        volleyball.setDisable(true);

        buttonLayout.setAlignment(Pos.CENTER);

        football.setOnAction(e -> buttonFootball(root, stage));

        VBox.setMargin(buttonLayout, new Insets(20, 0, 0, 0));

        buttonLayout.getChildren().addAll(football,basketball,volleyball);

        root.getChildren().addAll(labelLayout,buttonLayout);



        return new Scene(root, 500, 300);
    }


    private void buttonFootball(VBox root, Stage stage){
        root.getChildren().clear();


        StartScreenUi start = new StartScreenUi();
        Scene scene = start.getScene(stage);

        stage.setScene(scene);
        stage.show();
    }
}