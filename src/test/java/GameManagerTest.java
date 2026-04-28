
import Consider.GameManager;
import Model.Football.Football;
import Model.Sport;
import Model.Team;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

    public class GameManagerTest {
        @Test
        void constructorTest() {
            GameManager gm = new GameManager();

            assertEquals(0, gm.getCurrentWeek());
            assertEquals(0, gm.getSeason());
            assertNull(gm.getLeagueSystem());
            assertFalse(gm.isSeasonOver());
        }
        @Test
        void startGameTest() {
            GameManager gm = new GameManager();
            Sport sport = new Football();
            ArrayList<Team> teams = createTeams(sport);

            gm.startNewGame(sport, teams, "League");

            assertEquals(1, gm.getSeason());
            assertEquals(1, gm.getCurrentWeek());
            assertNotNull(gm.getLeagueSystem());
            assertEquals(teams.size(), gm.getLeagueSystem().getStandings().size());
            assertFalse(gm.getLeagueSystem().getFixture().getWeek(1).isEmpty());
        }
        @Test
        void weekBeforeStartTest() {
            GameManager gm = new GameManager();
            assertEquals(0, gm.getCurrentWeek());
        }
        @Test
        void seasonValueTest() {
            GameManager gm = new GameManager();
            Sport sport = new Football();
            ArrayList<Team> teams = createTeams(sport);

            assertEquals(0, gm.getSeason());

            gm.startNewGame(sport, teams, "League");

            assertEquals(1, gm.getSeason());
        }
        @Test
        void seasonOverAtStartTest() {
            GameManager gm = new GameManager();

            assertFalse(gm.isSeasonOver());
        }
        @Test
        void advanceBeforeStartTest() {
            GameManager gm = new GameManager();
            gm.advanceWeek();

            assertEquals(0, gm.getCurrentWeek());
            assertEquals(0, gm.getSeason());
            assertNull(gm.getLeagueSystem());
            assertFalse(gm.isSeasonOver());
        }

        @Test
        void advanceWeekTest() {
            GameManager gm = new GameManager();
            Sport sport = new Football();
            ArrayList<Team> teams = createTeams(sport);

            gm.startNewGame(sport, teams, "League");

            int beforeWeek = gm.getCurrentWeek();
            int beforeSize = gm.getLeagueSystem().getStandings().size();

            gm.advanceWeek();

            int afterWeek = gm.getCurrentWeek();
            int afterSize = gm.getLeagueSystem().getStandings().size();

            assertEquals(beforeWeek + 1, afterWeek);
            assertEquals(beforeSize, afterSize);
        }
        @Test
        void seasonEndTest() {
            GameManager gm = new GameManager();
            Sport sport = new Football();
            ArrayList<Team> teams = createTeams(sport);

            gm.startNewGame(sport, teams, "League");

            int count = 0;
            while (!gm.isSeasonOver() && count < 50) {
                gm.advanceWeek();
                count++;
            }
            assertTrue(gm.isSeasonOver());
        }
        private ArrayList<Team> createTeams(Sport sport) {
            ArrayList<Team> teams = new ArrayList<>();
            teams.add(new FootballTeam(sport, "T1", "C1", "S1"));
            teams.add(new FootballTeam(sport, "T2", "C2", "S2"));
            teams.add(new FootballTeam(sport, "T3", "C3", "S3"));
            teams.add(new FootballTeam(sport, "T4", "C4", "S4"));
            return teams;
        }
    }
