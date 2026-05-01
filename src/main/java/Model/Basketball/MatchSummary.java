package Model.Basketball;

import Model.Team;
import java.util.List;

public class MatchSummary {
    public final Team home;
    public final Team away;
    public final int homeScore;
    public final int awayScore;
    public final List<PlayerStat> stats;

    MatchSummary(Team home, int homeScore, Team away, int awayScore, List<PlayerStat> stats) {
        this.home = home;
        this.homeScore = homeScore;
        this.away = away;
        this.awayScore = awayScore;
        this.stats = stats;
    }
}
