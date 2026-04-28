package UI;

import Model.Sport;
import Model.SportEntity;
import Model.Team;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigator {
    private static Stage stage;


    public static void init(Stage primaryStage){
        stage=primaryStage;
    }


    public static void navigate(ViewType type){
        Parent view=null;

        switch (type){
            case START:
                view=new StartUi().getView();
                break;
            case SPORTSELECTION:
                view=new SportSelectionUi().getView();
                break;
            case TEAMSELECTION:
//                view=new TeamSelectionUi().getView();
                break;
            case MENU:
                view=new MainMenuUi().getView();
                break;
            case MATCHSCREEN:
                //
                break;
            case LEAGUETABLE:
                view=new LeagueTableUi().getView();
                break;
        }

        if(view!=null){
            stage.setScene(new Scene(view,500,400));
            stage.setResizable(false);
        }

    }


    public static void navigate(ViewType type, SportEntity sport){
        Parent view=null;

        switch (type){
            case START:
                view=new StartUi().getView();
                break;
            case SPORTSELECTION:
                view=new SportSelectionUi().getView();
                break;
            case TEAMSELECTION:
                view=new TeamSelectionUi().getView(sport);
                break;
            case MENU:
                view=new MainMenuUi().getView();
                break;
            case MATCHSCREEN:
                //
                break;
        }

        if(view!=null){
            stage.setScene(new Scene(view,500,400));
            stage.setResizable(false);
        }
    }




    public static void navigate(ViewType type,Team team){
        navigate(type);
    }




}
