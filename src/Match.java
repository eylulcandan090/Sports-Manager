public class Match{
    Team home;
    Team away;
    private int homeScore;
    private int awayScore;
    private boolean isPlayed;



    public Match(Team home,Team away){
        this.home=home;
        this.away=away;
    }


     public void addScore(int home,int away){
       this.homeScore+=home;
       this.awayScore+=away;
    }
    


    public void startMatch(){

    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public Team getAway() {
        return away;
    }

    public void setAway(Team away) {
        this.away = away;
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
