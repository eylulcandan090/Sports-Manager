package org.example.mygui2.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartScreenUi {

    public Scene getScene(Stage stage){
        VBox root=new VBox();
        VBox layoutLabel=new VBox();
        VBox vbox=new VBox();

        Label label=new Label("Sport Manager");
        label.setFont(Font.font(30));

        layoutLabel.getChildren().add(label);
        layoutLabel.setAlignment(Pos.CENTER);

        Button newGame=new Button("New Game");
        Button continueGame=new Button("Continue");

        newGame.setBackground(Background.fill(Paint.valueOf("Green")));

        newGame.setMaxHeight(30);
        newGame.setMaxWidth(90);
        continueGame.setMaxHeight(30);
        continueGame.setMaxWidth(90);

        newGame.setOnAction(e -> buttonNewGame(root, stage));

        vbox.getChildren().addAll(newGame,continueGame);
        vbox.setAlignment(Pos.TOP_CENTER);

        root.getChildren().addAll(layoutLabel,vbox);
        VBox.setMargin(vbox, new Insets(20, 0, 0, 0));

        Scene scene=new Scene(root,500,300);
        stage.setTitle("Sport Manager");

        return scene;
    }

    private void buttonNewGame(Pane root, Stage stage){
        root.getChildren().clear();

        SportSelectionUi sportSelectionUi =new SportSelectionUi();
        Scene scene= sportSelectionUi.getScene(stage);

        stage.setScene(scene);
        stage.show();
    }
}