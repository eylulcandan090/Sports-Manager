public class GameManager {
    private Sport sport;
    private int currentWeek;
    private int season;
    private boolean seasonOver;

    public GameManager() {
        this.sport=null;
        this.currentWeek = 0;
        this.season = 0;
        this.seasonOver = false;
    }
    public void startNewGame(Sport sport) {
        this.sport = sport;
        this.currentWeek = 1;
        this.season = 1;
        this.seasonOver = false;
    }
    public int getCurrentWeek() {
        return currentWeek;
    }
    public int getSeason() {
        return season;
    }
    public boolean isSeasonOver() {
        return seasonOver;
    }
    public void advanceWeek() {
        // waiting for LeagueSystem
    }

    /*
    public LeagueSystem getLeagueSystem() {
    // not ready yet(LeagueSystem)
    return null;
    }
    */
}
