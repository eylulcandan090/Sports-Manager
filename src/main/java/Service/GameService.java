package Service;


import Model.GameState;
import Model.Player;
import Model.Team;
import Repository.GameRepo;
import java.util.ArrayList;


public class GameService {
    private GameRepo repo;
    private GameState currentGame;
    private ArrayList<Player> currentSquad;
    private int maxPeriod;

    public GameService(GameRepo repo){
        this.repo=repo;

    }
    public static void resetGame(){
        String delete="DELETE FROM matches";
    }

    public boolean hasGame(){
        return repo.isGameStarted();
    }

    public int getGameTeamId(){
        return repo.getGameTeamId();
    }
    public void startMatch(ArrayList<Player> squad){
        this.currentSquad = squad;

        Team homeTeam = new Team(getGameTeamId(), "Your Team", 0);
        Team awayTeam = new Team(999, "Opponent", 0);
        currentGame = new GameState(homeTeam, awayTeam);
        if(squad.size() > 5){
            maxPeriod = 2;
        }else{
            maxPeriod = 4;
        }
        playNextPeriod(squad);
    }
    public void playNextPeriod(ArrayList<Player> squad){
        if(currentGame == null) return;

        currentGame.nextPeriod();

        int homeAdd = (int)(Math.random() * 3);
        int awayAdd = (int)(Math.random() * 3);

        currentGame.setScore(
                currentGame.getHomeScore() + homeAdd,
                currentGame.getAwayScore() + awayAdd
        );

        System.out.println("PERIOD: " + currentGame.getCurrentPeriod());
        System.out.println("SCORE: " + currentGame.getHomeScore() + " - " + currentGame.getAwayScore());
    }
    public boolean isMatchFinished(){
        return currentGame.getCurrentPeriod() >= maxPeriod;
    }

    public int getHomeScore(){
        return currentGame.getHomeScore();
    }
    public int getAwayScore(){
        return currentGame.getAwayScore();
    }
    public int getCurrentPeriod(){
        return currentGame.getCurrentPeriod();
    }
    public ArrayList<Player> getCurrentSquad(){
        return currentSquad;
    }

}
