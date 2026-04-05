import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FixtureTest {

    private static final int TOTAL_WEEKS = 10;
    private Fixture fixture;

    @BeforeEach
    void setUp() {
        fixture = new Fixture(TOTAL_WEEKS);
    }

    @Test
    void initialization_correctTotalWeeksAndEmptyMatchMap() {
        assertEquals(TOTAL_WEEKS, fixture.getTotalWeeks());
        assertTrue(fixture.getWeeklyMatches().isEmpty());
    }

    @Test
    void addMatch_singleMatch_retrievedFromCorrectWeek() {
        Match match = mock(Match.class);

        fixture.addMatch(1, match);
        List<Match> week1 = fixture.getWeek(1);

        assertEquals(1, week1.size());
        assertSame(match, week1.get(0));
    }

    @Test
    void addMatch_multipleMatchesSameWeek_allRetrievedCorrectly() {
        Match match1 = mock(Match.class);
        Match match2 = mock(Match.class);
        Match match3 = mock(Match.class);

        fixture.addMatch(3, match1);
        fixture.addMatch(3, match2);
        fixture.addMatch(3, match3);

        List<Match> week3 = fixture.getWeek(3);

        assertEquals(3, week3.size());
        assertTrue(week3.contains(match1));
        assertTrue(week3.contains(match2));
        assertTrue(week3.contains(match3));
    }

    @Test
    void getWeek_weekWithNoMatches_returnsEmptyListNotException() {
        List<Match> emptyWeek = fixture.getWeek(5);

        assertNotNull(emptyWeek);
        assertTrue(emptyWeek.isEmpty());
    }

    @Test
    void addMatch_lastWeek_matchStoredAndRetrievedCorrectly() {
        Match match = mock(Match.class);

        fixture.addMatch(TOTAL_WEEKS, match);
        List<Match> lastWeek = fixture.getWeek(TOTAL_WEEKS);

        assertEquals(1, lastWeek.size());
        assertSame(match, lastWeek.get(0));
    }
}