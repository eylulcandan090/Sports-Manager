package Model;

import Model.Basketball.MatchSummary;
import java.util.List;

public interface IGameEngine<P extends Player> {
    MatchSummary simulateMatch(Team home, Team away,
                               List<P> homePlayers, List<P> awayPlayers);
}