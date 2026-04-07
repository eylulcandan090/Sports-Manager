import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeagueSystemTest {

    static class TeamImpl extends Team {
        public TeamImpl(String name) {
            super(null, name, "Coach", "Stadium");
        }
    }

    private TeamImpl teamA, teamB, teamC, teamD;
    private List<Team> fourTeams;
    private LeagueSystem league;

    @BeforeEach
    void setUp() {
        teamA = new TeamImpl("Team A");
        teamB = new TeamImpl("Team B");
        teamC = new TeamImpl("Team C");
        teamD = new TeamImpl("Team D");

        fourTeams = List.of(teamA, teamB, teamC, teamD);
        league = new LeagueSystem("Test League", fourTeams);
    }

    @Test
    void generateFixture_fourTeams_produces12MatchesAndEachPairPlaysTwice() {
        league.generateFixture();

        Fixture fixture = league.getFixture();
        int totalWeeks = fixture.getTotalWeeks(); // (4-1)*2 = 6

        int totalMatches = 0;
        for (int week = 1; week <= totalWeeks; week++) {
            totalMatches += fixture.getWeek(week).size();
        }
        assertEquals(12, totalMatches, "4 teams should produce 4×3 = 12 matches");

        for (Team home : fourTeams) {
            for (Team away : fourTeams) {
                if (home == away) continue;

                long count = 0;
                for (int week = 1; week <= totalWeeks; week++) {
                    for (Match match : fixture.getWeek(week)) {
                        if (match.getHome() == home && match.getAway() == away) {
                            count++;
                        }
                    }
                }
                assertEquals(1, count,
                        home + " (home) vs " + away + " (away) should appear exactly once");
            }
        }
    }

    @Test
    void standingsUpdate_afterWinResult_pointsAndStatsAreCorrect() {
        Standing standingA = league.getStandings().stream()
                .filter(s -> s.getTeam() == teamA)
                .findFirst()
                .orElseThrow();

        standingA.addResult(2, 0);

        assertEquals(3, standingA.getPoints());
        assertEquals(1, standingA.getWon());
        assertEquals(1, standingA.getPlayed());
        assertEquals(2, standingA.getGoalsFor());
        assertEquals(0, standingA.getGoalsAgainst());
    }

    @Test
    void getStandings_teamWithMostPoints_isFirstInList() {
        List<Standing> standings = league.getStandings();

        Standing standingD = standings.stream()
                .filter(s -> s.getTeam() == teamD)
                .findFirst()
                .orElseThrow();

        standingD.addResult(3, 0);
        standingD.addResult(2, 0);
        standingD.addResult(1, 0);

        Standing standingA = standings.stream()
                .filter(s -> s.getTeam() == teamA)
                .findFirst()
                .orElseThrow();

        standingA.addResult(1, 1);

        List<Standing> sorted = league.getStandings();
        assertSame(teamD, sorted.get(0).getTeam());
    }

    @Test
    void isSeasonComplete_falseAtStartTrueAfterAllWeeksAdvanced() {
        assertFalse(league.isSeasonComplete());

        int totalWeeks = league.getFixture().getTotalWeeks();
        for (int i = 0; i < totalWeeks; i++) {
            assertFalse(league.isSeasonComplete());
            league.advanceWeek();
        }

        assertTrue(league.isSeasonComplete());
    }

    @Test
    void getChampion_afterSeasonComplete_returnsTeamWithMostPoints() {
        List<Standing> standings = league.getStandings();

        Standing standingB = standings.stream()
                .filter(s -> s.getTeam() == teamB)
                .findFirst()
                .orElseThrow();

        standingB.addResult(3, 0);
        standingB.addResult(2, 1);
        standingB.addResult(1, 0);

        assertNull(league.getChampion());

        int totalWeeks = league.getFixture().getTotalWeeks();
        for (int i = 0; i < totalWeeks; i++) {
            league.advanceWeek();
        }

        assertTrue(league.isSeasonComplete());
        assertSame(teamB, league.getChampion());

    }
}