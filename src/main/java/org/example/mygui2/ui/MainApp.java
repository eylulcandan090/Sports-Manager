package org.example.mygui2.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage){
        Scene scene=new StartScreenUi().getScene(stage);
        stage.setScene(scene);
        stage.show();
    }
}
