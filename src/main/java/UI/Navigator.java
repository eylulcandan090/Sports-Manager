package UI;

import Database.Database;
import Model.Sport;
import Model.SportEntity;
import Model.Team;
import Repository.FixtureRepo;
import Repository.GameRepo;
import Repository.LeagueRepo;
import Repository.TeamRepo;
import Service.FixtureService;
import Service.GameService;
import Service.LeagueService;
import Service.TeamService;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

public class Navigator {
    private static Stage stage;


    private static Database database;
    private static TeamService teamService;
    private static GameService gameService;
    private static FixtureService fixtureService;
    private static LeagueService leagueService;

    private static TeamRepo teamRepo;
    private static GameRepo gameRepo;
    private static FixtureRepo fixtureRepo;
    private static LeagueRepo leagueRepo;



    public static void init(Stage primaryStage){
        stage=primaryStage;

        database=Database.getInstance();
        Connection connection=database.getConnection();

        teamRepo=new TeamRepo(connection);
        teamService=new TeamService(teamRepo);

        gameRepo=new GameRepo(connection);
        gameService=new GameService(gameRepo);

        fixtureRepo=new FixtureRepo(database.getConnection());
        fixtureService=new FixtureService(fixtureRepo,teamRepo);

        leagueRepo=new LeagueRepo(connection);
        leagueService=new LeagueService(leagueRepo);





    }


    public static void navigate(ViewType type){
        Parent view=null;

        switch (type){
            case START:
                view=new StartUi().getView(gameService);
                break;
            case SPORTSELECTION:
                view=new SportSelectionUi().getView(teamRepo,database);
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
                System.out.println("yessss");
                view=new LeagueTableUi().getView(gameService,leagueService,teamRepo);
                break;
            case FIXTURE:
                view=new FixtureUi().getView(fixtureService);
                break;
        }

        if(view!=null){
            stage.setScene(new Scene(view,650,500));  //500 400
            stage.setResizable(false);
        }

    }


    public static void navigate(ViewType type, SportEntity sport){
        Parent view=null;

        switch (type){
            case START:
                view=new StartUi().getView(gameService);
                break;
            case SPORTSELECTION:
                view=new SportSelectionUi().getView(teamRepo,database);
                break;
            case TEAMSELECTION:
                view=new TeamSelectionUi().getView(sport,teamRepo,gameRepo);
                break;
            case MENU:
                view=new MainMenuUi().getView();
                break;
            case MATCHSCREEN:
                //
                break;
            case LEAGUETABLE:
                view=new LeagueTableUi().getView(gameService,leagueService,teamRepo);
                break;
        }

        if(view!=null){
            stage.setScene(new Scene(view,650,500));
            stage.setResizable(false);
        }
    }




    public static void navigate(ViewType type,Team team){
        navigate(type);
    }




}
