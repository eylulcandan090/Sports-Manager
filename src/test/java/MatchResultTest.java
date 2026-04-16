import Model.FootballTeam;
import Model.Match;
import Model.Sport;
import Model.Team;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MatchResultTest {

    @Test
    void testEnumValues() {
        MatchResult[] results=MatchResult.values();
        assertEquals(3,results.length,"MatchResult should have exactly 3 values");
        assertEquals(MatchResult.HOME_WIN,MatchResult.valueOf("HOME_WIN"));
        assertEquals(MatchResult.AWAY_WIN,MatchResult.valueOf("AWAY_WIN"));
        assertEquals(MatchResult.DRAW,MatchResult.valueOf("DRAW"));
    }

    @Test
    void testMatchResultIntegrationWithMatch() {
        Sport football=new Football();
        Team home=new FootballTeam(football,"Home","Coach1","Stadium1");
        Team away=new FootballTeam(football,"Away","Coach2","Stadium2");

        Match match=new Match(home, away);
        match.setResult(MatchResult.HOME_WIN);
        assertEquals(MatchResult.HOME_WIN, match.getResult());

        match.setResult(MatchResult.AWAY_WIN);
        assertEquals(MatchResult.AWAY_WIN, match.getResult());

        match.setResult(MatchResult.DRAW);
        assertEquals(MatchResult.DRAW, match.getResult());
    }
}