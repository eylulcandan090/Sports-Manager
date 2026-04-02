import java.util.regex.MatchResult;

public class Match{
    final Team home;
    final Team away;
    private int homeScore;
    private int awayScore;
    private boolean isPlayed;
    final LineUp[] lineup;
    MatchResult result;



    public Match(Team home, Team away){ //constructor
        this.home=home;
        this.away=away;
        this.lineup = new LineUp[2];
        this.result = null;
    }


     public void addScore(int home,int away){
       this.homeScore+=home;
       this.awayScore+=away;
    }
    


    public boolean isPlayedd() {
        return result != null;
    }

    public Team getHome() {
        return home;
    }

    public Team getAway() {
        return away;
    }

    public LineUp getLineup(int index) {
        return lineup[index];
    }

    public LineUp[] getLineup(){
        return lineup;
    }

    public MatchResult getResult(){
        return result;
    }

    public void setResult(MatchResult result){
        this.result = result;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }
    public void setScore(int homeScore,int awayScore){
        this.homeScore=homeScore;
        this.awayScore=awayScore;
    }

    public String getScore(){
        return home.getTeamName() + " " + homeScore + " - " + awayScore + " " + away.getTeamName();
    }

}
