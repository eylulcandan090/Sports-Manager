import Model.FootballTeam;
import Model.Player;
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
        teamA = new FootballTeam(football, "Model.Team A","Model.Coach A","Stadium A");
        teamB = new FootballTeam(football,"Model.Team B" , "Model.Coach B","Stadium B");

        // Add 11 starting players to each team
        for (int i = 1; i <= teamA.getPlayersOnField(); i++) {
            Player playerA=new Player() {};
            playerA.setName("A"+i);
            playerA.setAttribute("rating",70 + i); // rating 71-81
            teamA.addPlayerToTeam(playerA);

            Player playerB=new Player() {};
            playerB.setName("B"+i);
            playerB.setAttribute("rating",60 + i); // rating 61-71
            teamB.addPlayerToTeam(playerB);
        }

        // Substitute players
        for (int i = 1; i <= football.maxSubstitute(); i++) {
            Player subA=new Player() {};
            subA.setName("A_sub"+i);
            subA.setAttribute("rating",65);
            teamA.addPlayerToSubstitute(subA);

            Player subB=new Player() {};
            subB.setName("B_sub"+i);
            subB.setAttribute("rating",65);
            teamB.addPlayerToSubstitute(subB);
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
        assertTrue(ratingA > ratingB, "Model.Team A overall rating should be higher than Model.Team B");
    }

    @Test
    public void testGetMaxRandomScore() {
        assertEquals(football.getMaxRandomScore(), 3);
    }
}