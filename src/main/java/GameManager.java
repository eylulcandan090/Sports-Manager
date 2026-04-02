import java.util.List;
public class GameManager {
    private Sport sport;
    private int currentWeek;
    private int season;
    private LeagueSystem leagueSystem;

    public GameManager() {
        this.sport=null;
        this.currentWeek = 0;
        this.season = 0;
        this.leagueSystem = null;
    }
    public void startNewGame(Sport sport,List<Team> teams) {
        this.sport = sport;
        this.currentWeek = 1;
        this.season = 1;
        this.leagueSystem = new LeagueSystem(teams);
        this.leagueSystem.generateFixture();
    }
    public int getCurrentWeek() {
        if(leagueSystem!=null)return leagueSystem.getCurrentWeek();
        return currentWeek;
    }
    public int getSeason() {
        return season;
    }

    public boolean isSeasonOver(){
    if(leagueSystem==null) return false;
    return leagueSystem.isSeasonComplete();
    }
    public void advanceWeek() {
        if(leagueSystem==null) return;

        //play all matches scheduled for this week
        List<Match> weekMatches=leagueSystem.getFixture().getWeek(currentWeek);
        for (Match match : weekMatches) {
            MatchEngine engine = new MatchEngine(match);
            engine.playMatch();
            sport.updatePoints(
            match.getHome(),match.getAway(), match.getHomeScore(),match.getAwayScore());
        }
        // conduct training for every team in the league
        TrainingSystem training=new FootballTraining(sport);
        for(Standing standing:leagueSystem.getStandings()){
            training.conductTraining((standing.getTeam()));
        }

        leagueSystem.advanceWeek();
        currentWeek=leagueSystem.getCurrentWeek();
    }
    public LeagueSystem getLeagueSystem() {
        return leagueSystem;
    }

}
