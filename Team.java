import java.util.ArrayList;

abstract class Team {
    private String teamName;
    private String coachName;
    private int point;
    private ArrayList<Player> playerList;


    public Team(String teamName, String coachName) {
        this.teamName = teamName;
        this.coachName = coachName;
        this.point=0;
        this.playerList=new ArrayList<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }
}
