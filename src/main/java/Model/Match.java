package Model;

public class Match {
    private int id;
    private int homeTeamId;
    private int awayTeamId;
    private int homeScore;
    private int awayScore;
    private String matchDate;

    public Match(int homeTeamId,int awayTeamId,String matchDate) {
        this.homeTeamId=homeTeamId;
        this.awayTeamId=awayTeamId;
        this.homeScore=0;
        this.awayScore=0;
        this.matchDate=matchDate;
    }

    public Match(int homeTeamId,int awayTeamId,int homeScore,int awayScore,String matchDate) {
        this.homeTeamId=homeTeamId;
        this.awayTeamId=awayTeamId;
        this.homeScore=homeScore;
        this.awayScore=awayScore;
        this.matchDate=matchDate;
    }

    public Match(int id,int homeTeamId,int awayTeamId,int homeScore,int awayScore,String matchDate) {
        this.id=id;
        this.homeTeamId=homeTeamId;
        this.awayTeamId=awayTeamId;
        this.homeScore=homeScore;
        this.awayScore=awayScore;
        this.matchDate=matchDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public int getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(int homeTeamId) {
        this.homeTeamId=homeTeamId;
    }

    public int getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(int awayTeamId) {
        this.awayTeamId=awayTeamId;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore=homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore=awayScore;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate=matchDate;
    }

    public void setScore(int homeScore,int awayScore) {
        this.homeScore=homeScore;
        this.awayScore=awayScore;
    }

    public boolean isDraw() {
        return homeScore==awayScore;
    }

    public int getWinnerTeamId() {
        if (homeScore>awayScore) return homeTeamId;
        if (awayScore>homeScore) return awayTeamId;
        return -1;
    }


    public int getLoserTeamId() {
        if (homeScore<awayScore) return homeTeamId;
        if (awayScore<homeScore) return awayTeamId;
        return -1;
    }

    @Override
    public String toString() {
        return "Match{"+
                "id=" +id+
                ", home="+homeTeamId+
                ", away="+awayTeamId+
                ", score="+homeScore+"-" +awayScore+
                ", date='"+matchDate+'\''+
                '}';
    }
}
