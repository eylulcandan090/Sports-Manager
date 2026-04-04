import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StandingTest {

    static class TeamImpl extends Team {
        public TeamImpl(String name) {
            super(null, name, "Coach" , "Chobani");
        }
    }

    private Standing standing;

    @BeforeEach
    void setUp() {
        standing = new Standing(new TeamImpl("Team A"));
    }

    @Test
    void initialStateTest() {
        assertEquals(0, standing.getPoints());
        assertEquals(0, standing.getPlayed());
        assertEquals(0, standing.getWon());
        assertEquals(0, standing.getDrawn());
        assertEquals(0, standing.getLost());
        assertEquals(0, standing.getGoalsFor());
        assertEquals(0, standing.getGoalsAgainst());
    }

    @Test
    void winScenarioTest() {
        standing.addResult(3, 1);

        assertEquals(3, standing.getPoints());
        assertEquals(1, standing.getWon());
        assertEquals(3, standing.getGoalsFor());
        assertEquals(1, standing.getGoalsAgainst());
        assertEquals(1, standing.getPlayed());
        assertEquals(0, standing.getDrawn());
        assertEquals(0, standing.getLost());
    }

    @Test
    void drawScenarioTest() {
        standing.addResult(1, 1);

        assertEquals(1, standing.getPoints());
        assertEquals(1, standing.getDrawn());
        assertEquals(1, standing.getPlayed());
        assertEquals(0, standing.getWon());
        assertEquals(0, standing.getLost());
    }

    @Test
    void lossScenarioTest() {
        standing.addResult(0, 2);

        assertEquals(0, standing.getPoints());
        assertEquals(1, standing.getLost());
        assertEquals(1, standing.getPlayed());
        assertEquals(0, standing.getWon());
        assertEquals(0, standing.getDrawn());
    }

    @Test
    void goalDifferenceTest() {
        standing.addResult(3, 1); //goal difference = 2
        assertEquals(2, standing.getGoalDifference());

        standing.addResult(0, 2); // goalsFor=3 , goalsAgainst=3
        assertEquals(0, standing.getGoalDifference());

        standing.addResult(1, 4); // goalsFor=4 , goalsAgainst=7
        assertEquals(-3, standing.getGoalDifference());
    }

    @Test
    void compareToByPointsTest() {
        Standing highPoints = new Standing(new TeamImpl("High"));
        highPoints.addResult(3, 0); // 3 points

        Standing lowPoints = new Standing(new TeamImpl("Low"));
        lowPoints.addResult(1, 1); // 1 point

        // Higher points should sort first (compareTo returns negative)
        assertTrue(highPoints.compareTo(lowPoints) < 0);
        assertTrue(lowPoints.compareTo(highPoints) > 0);
    }

    @Test
    void compareToByGoalDifferenceOnTiedPointsTest() {
        Standing betterGD = new Standing(new TeamImpl("Better GD"));
        betterGD.addResult(1, 1); // 1 pt, GD=0

        Standing worseGD = new Standing(new TeamImpl("Worse GD"));
        worseGD.addResult(1, 1); // 1 pt, GD=0
        worseGD.addResult(0, 2); // 1 pt, GD=-2

        // Both have 1 point; betterGD (GD=0) should come before worseGD (GD=-2)
        assertTrue(betterGD.compareTo(worseGD) < 0);
        assertTrue(worseGD.compareTo(betterGD) > 0);
    }

    @Test
    void compareToEqualStandingsTest() {
        Standing other = new Standing(new TeamImpl("Other"));
        assertEquals(0, standing.compareTo(other));
    }
}