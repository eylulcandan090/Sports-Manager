package Model;

public class Fixture {
    private int week;
    private int homeId;
    private int awayId;
    private boolean isPlayed;


    public Fixture(int homeId, int awayId,int week, boolean isPlayed) {
        this.homeId = homeId;
        this.awayId = awayId;
        this.week=week;
        this.isPlayed = isPlayed;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public int getAwayId() {
        return awayId;
    }

    public void setAwayId(int awayId) {
        this.awayId = awayId;
    }

    public boolean getIsPlayed() {
        return isPlayed;
    }

    public void setIsPlayed(boolean isPlayed) {
        this.isPlayed = isPlayed;
    }
}
