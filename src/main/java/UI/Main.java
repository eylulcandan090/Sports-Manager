package UI;

import DataFeed.DataFeed;
import Database.Database;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        Database.getInstance();
        DataFeed.feed();

        Navigator.init(stage);
        Navigator.navigate(ViewType.START);
        stage.show();
    }
}
