import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SportInterfaceTest {

    private Football football;
    private FootballTeam teamA;
    private FootballTeam teamB;

    @BeforeEach
    public void setup() {
        football = new Football();
        teamA = new FootballTeam("Team A", "Coach A");
        teamB = new FootballTeam("Team B", "Coach B");

        // Her takıma 11 ana oyuncu ekleyelim
        for (int i = 1; i <= teamA.getPlayersOnField(); i++) {
            teamA.addPlayerToTeam(new FootballPlayer("A" + i, 70 + i)); // rating 71-81
            teamB.addPlayerToTeam(new FootballPlayer("B" + i, 60 + i)); // rating 61-71
        }

        // Yedek oyuncular
        for (int i = 1; i <= football.maxSubstitute(); i++) {
            teamA.addPlayerToSubstitute(new FootballPlayer("A_sub" + i, 65));
            teamB.addPlayerToSubstitute(new FootballPlayer("B_sub" + i, 65));
        }
    }

    @Test
    public void testPlayerLimits() {
        assertEquals(11, teamA.getTeamPlayerCount());
        assertEquals(11, teamB.getTeamPlayerCount());
        assertEquals(5, teamA.getTeamSubstituteCount()); // maxSubstitute() = 5
        assertEquals(5, teamB.getTeamSubstituteCount());
    }

    @Test
    public void testUpdatePointsHomeWin() {
        football.updatePoints(teamA, teamB, 2, 0);
        assertEquals(football.teamWinPoint(), teamA.getPoint());
        assertEquals(football.teamLosePoint(), teamB.getPoint());
    }

    @Test
    public void testUpdatePointsAwayWin() {
        football.updatePoints(teamA, teamB, 0, 3);
        assertEquals(football.teamLosePoint(), teamA.getPoint());
        assertEquals(football.teamWinPoint(), teamB.getPoint());
    }

    @Test
    public void testUpdatePointsDraw() {
        football.updatePoints(teamA, teamB, 1, 1);
        assertEquals(football.teamDrawPoint(), teamA.getPoint());
        assertEquals(football.teamDrawPoint(), teamB.getPoint());
    }

    @Test
    public void testTeamOverallRating() {
        double ratingA = teamA.getOverallPlayerRating();
        double ratingB = teamB.getOverallPlayerRating();
        assertTrue(ratingA > ratingB, "Team A overall rating should be higher than Team B");
    }

    @Test
    public void testGetMaxRandomScore() {
        assertEquals(football.getMaxRandomScore(), 3);
    }
}