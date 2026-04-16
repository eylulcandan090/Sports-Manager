package Model;

import java.util.ArrayList;

public abstract class Team {
    private String teamName;
    private String coachName;
    private String stadium;
    private int points;
    private ArrayList<Player> playerList;
    private ArrayList<Player> substitutes;
    private String tactics;
    private Sport sport;



    public Team(Sport sport, String teamName, String coachName, String stadium) {
        this.teamName=teamName;
        this.coachName=coachName;
        this.points=0;
        this.playerList=new ArrayList<>();
        this.substitutes=new ArrayList<>();
        tactics="default";
        this.sport=sport;
        this.stadium=stadium;
    }


    public String getStadiumName(){
        return this.stadium;
    }
    

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName=teamName;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName=coachName;
    }

    public int getPoint() {
        return points;
    }

    public void addPoint(int point) {
        this.points+=point;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public int getTeamPlayerCount(){
        return this.playerList.size();
    }

    public void addPlayer(Player player){
        if(sport.getPlayersPerTeam()>playerList.size()){
            playerList.add(player);
        }
    }

    public void removePlayer(Player player){
        this.playerList.remove(player);
    }

    

    public ArrayList<Player> getSubstitutes() {
        return substitutes;
    }

    public void addSubstitute(Player player){
        this.substitutes.add(player);
    }

    public int getTeamSubstituteCount(){
        return this.substitutes.size();
    }


    /*public double getOverallPlayerRating() {
        return totalPlayerRating()/(double)playerList.size();
    }*/

    public double getOverallPlayerRating() {
    if (playerList.size() == 0) return 50; 
    return totalPlayerRating()/(double) playerList.size();
}

    private int totalPlayerRating(){
        int total=0;
        for(Player p:playerList){
            total+=p.getStrength();
        }
        return total;
    }

    public void setTactics(String tactic){
        this.tactics=tactic;
    }

    public String getTactics(){
        return this.tactics;
    }


    public Sport getSport() {
        return this.sport;
    }
}
