package UI;

import Database.Database;
import Model.SportEntity;
import Model.Team;
import Repository.*;
import Service.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.sql.Connection;

public class Navigator {
    private static Stage stage;
    private static MediaPlayer mediaPlayer;

    private static Database database;
    private static TeamService teamService;
    private static GameService gameService;
    private static FixtureService fixtureService;
    private static LeagueService leagueService;

    private static TeamRepo teamRepo;
    private static GameRepo gameRepo;
    private static FixtureRepo fixtureRepo;
    private static LeagueRepo leagueRepo;


    private static FootballPlayerRepo footballPlayerRepo;
    private static BasketballPlayerRepo basketballPlayerRepo;

    public static void init(Stage primaryStage) {
        stage = primaryStage;

        database = Database.getInstance();
        Connection connection = database.getConnection();

        teamRepo = new TeamRepo(connection);
        teamService = new TeamService(teamRepo);

        fixtureRepo = new FixtureRepo(database.getConnection());
        fixtureService = new FixtureService(fixtureRepo, teamRepo);

        gameRepo = new GameRepo(connection);
        gameService = new GameService(gameRepo,fixtureService,teamRepo);

        leagueRepo = new LeagueRepo(connection);
        leagueService = new LeagueService(leagueRepo);


        footballPlayerRepo = new FootballPlayerRepo(connection);
        basketballPlayerRepo = new BasketballPlayerRepo(connection);

        playMusic("/music/background.mp3");
    }

    public static void navigate(ViewType type) {
        Parent view = null;

        switch (type) {
            case START:
                view = new StartUi().getView(gameService);
                break;
            case SPORTSELECTION:
                view = new SportSelectionUi().getView(teamRepo, database);
                break;
            case TEAMSELECTION:
                break;
            case MENU:
                view = new MainMenuUi().getView(gameService, teamService, fixtureService, leagueService, teamRepo);
                break;
            case MATCHSCREEN:
                break;
            case LEAGUETABLE:
                view = new LeagueTableUi().getView(gameService, leagueService, teamRepo);
                break;
            case FIXTURE:
                view = new FixtureUi().getView(fixtureService);
                break;
            case TRAINING:
                view = new TrainingScreenUi().getView(gameService, teamService, footballPlayerRepo, basketballPlayerRepo);
                break;

            case MYTEAM:
                view = new MyTeamUi().getView(gameService, teamService, footballPlayerRepo, basketballPlayerRepo);
                break;

            case MYSQUAD:
                view=new MatchSquadUi().getView(gameService,teamService);
                break;

            case MATCHPLAY:
                view=new MatchPlayUi().getView(gameService);
                break;
            case SUBSTITUTION:
                view = new SubstitutionUi().getView(gameService);
                break;
            case TACTIC:
                view = new TacticUi().getView(gameService);
                break;
        }

        if (view != null) {
            Scene scene = new Scene(view, 650, 500);
            stage.setScene(scene);
            stage.setResizable(true);
        }
    }

    public static void navigate(ViewType type, SportEntity sport) {
        Parent view = null;

        switch (type) {
            case START:
                view = new StartUi().getView(gameService);
                break;
            case SPORTSELECTION:
                view = new SportSelectionUi().getView(teamRepo, database);
                break;
            case TEAMSELECTION:
                view = new TeamSelectionUi().getView(sport, teamRepo, gameRepo);
                break;
            case MENU:
                view = new MainMenuUi().getView(gameService, teamService, fixtureService, leagueService, teamRepo);
                break;
            case MATCHSCREEN:
                break;
            case LEAGUETABLE:
                view = new LeagueTableUi().getView(gameService, leagueService, teamRepo);
                break;
        }

        if (view != null) {
            Scene scene = new Scene(view, 650, 500);
            stage.setScene(scene);
            stage.setResizable(true);
        }
    }

    public static void navigate(ViewType type, Team team) {
        navigate(type);
    }

    public static void playMusic(String resourcePath) {
        if (mediaPlayer != null) return;
        try {
            String url = Navigator.class.getResource(resourcePath).toExternalForm();
            Media media = new Media(url);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.4);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Music could not be loaded: " + e.getMessage());
        }
    }

    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    public static void toggleMusic() {
        if (mediaPlayer == null) return;
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.play();
        }
    }

    public static boolean isMusicPlaying() {
        return mediaPlayer != null &&
               mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }
}