import Model.Match;
import Model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {

    static class TeamImpl extends Team {
        public TeamImpl(String name) {
            super(null, name, "Model.Coach", "Stadium");
        }
    }

    private TeamImpl home;
    private TeamImpl away;
    private Match match;

    @BeforeEach
    void setUp() {
        home = new TeamImpl("Home FC");
        away = new TeamImpl("Away FC");
        match = new Match(home, away);
    }

    @Test
    void constructionTest_storesHomeAndAwayTeams() {
        assertSame(home, match.getHome());
        assertSame(away, match.getAway());
    }

    @Test
    void constructionTest_lineupArrayHasTwoSlots() {
        LineUp[] lineups = match.getLineup();
        assertNotNull(lineups);
        assertEquals(2, lineups.length);
    }

    @Test
    void initialState_isPlayedReturnsFalse() {
        assertFalse(match.isPlayed());
    }

    @Test
    void afterSetResult_isPlayedReturnsTrue() {
        match.setResult(MatchResult.HOME_WIN);
        assertTrue(match.isPlayed());
    }

    @Test
    void afterSetResult_getResultReturnsCorrectValue() {
        match.setResult(MatchResult.AWAY_WIN);
        assertEquals(MatchResult.AWAY_WIN, match.getResult());
    }

    @Test
    void afterSetResult_drawIsStoredCorrectly() {
        match.setResult(MatchResult.DRAW);
        assertEquals(MatchResult.DRAW, match.getResult());
        assertTrue(match.isPlayed());
    }

    @Test
    void lineupAccess_getLineupByIndexReturnsCorrectLineup() {
        LineUp homeLineup = new LineUp();
        LineUp awayLineup = new LineUp();

        match.lineup[0] = homeLineup;
        match.lineup[1] = awayLineup;

        assertSame(homeLineup, match.getLineup(0), "getLineup(0) should return the home lineup");
        assertSame(awayLineup, match.getLineup(1), "getLineup(1) should return the away lineup");
    }

    @Test
    void lineupAccess_getLineupArrayReturnsBothLineups() {
        LineUp homeLineup = new LineUp();
        LineUp awayLineup = new LineUp();

        match.lineup[0] = homeLineup;
        match.lineup[1] = awayLineup;

        LineUp[] lineups = match.getLineup();
        assertSame(homeLineup, lineups[0]);
        assertSame(awayLineup, lineups[1]);
    }
}
