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
    private ArrayList<Player> currentBench;
    private String injuryMessage="";
    private Player injuredPlayer;
    private int injuryCount = 0;
    private int maxInjuries = 2;
    private int subsCount=0;
    private int maxSubs=3;
    private String tactic = "Balanced";

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
    public void startMatch(ArrayList<Player> squad,ArrayList<Player> bench){
        this.currentSquad = squad;
        this.currentBench=bench;

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
        double factor = 1.0;

        if(tactic.equals("Attacking")){
            factor = 1.5;
        }
        else if(tactic.equals("Defensive")){
            factor = 0.7;
        }
        int homeAdd = (int)(Math.random() * 3* factor);
        int awayAdd = (int)(Math.random() * 3);

        currentGame.setScore(
                currentGame.getHomeScore() + homeAdd,
                currentGame.getAwayScore() + awayAdd
        );
        injuryMessage = "";
        double injuryChance = 0.2;
        if(tactic.equals("Attacking")){
            injuryChance = 0.3;
        } else if(tactic.equals("Defensive")){
            injuryChance = 0.1;
        }
        if (injuryCount < maxInjuries && Math.random()< injuryChance ){
            if (currentSquad != null && !currentSquad.isEmpty()) {
                int index = (int)(Math.random() * currentSquad.size());
                Player injuredPlayer = currentSquad.get(index);

                if (injuredPlayer.getInjuryStatus() == 0) {
                    injuredPlayer.setInjuryStatus(1);
                    this.injuredPlayer=injuredPlayer;
                    injuryCount++;

                    injuryMessage = injuredPlayer.getName() + " got injured! You should substitute him.";
                }
            }
        }

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
    public ArrayList<Player> getCurrentBench(){
        return currentBench;
    }
    public String getInjuryMessage() {
        return injuryMessage;
    }
    public int getSubsCount(){
        return subsCount;
    }
    public int getMaxSubs(){
        return maxSubs;
    }
    public String getTactic(){
        return tactic;
    }
    public void setTactic(String tactic){
        this.tactic = tactic;
    }
    public Player getInjuredPlayer(){
        return injuredPlayer;
    }

    public boolean canSubs(){
        return subsCount < maxSubs;
    }
    public void increaseSubsCount(){
        subsCount++;
    }
}
