import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FootballMatchEngineTest {

    @Test
    void testMatchPlayedAndScoreGenerated() {
        // Setup
        Sport football = new Football();

        Team team1 = new FootballTeam(football, "Team A", "Coach A", "Stadium A");
        Team team2 = new FootballTeam(football, "Team B", "Coach B", "Stadium B");

        // Oyuncular ekle (dummy)
        team1.addPlayer(new FootballPlayer(80));
        team1.addPlayer(new FootballPlayer(85));

        team2.addPlayer(new FootballPlayer(78));
        team2.addPlayer(new FootballPlayer(82));

        // Match ve engine
        Match match = new Match(team1, team2);
        MatchEngine engine = new FootballMatchEngine(match);

        // Maçı oynat
        engine.playMatch();

        // Testler
        assertTrue(match.isPlayed(), "Match should be marked as played");
        assertTrue(match.getHomeScore() >= 0, "Home score cannot be negative");
        assertTrue(match.getAwayScore() >= 0, "Away score cannot be negative");

        // Puanlar update edilmiş mi?
        int totalPoints = team1.getPoint() + team2.getPoint();
        assertTrue(totalPoints > 0, "Points should be updated after match");
    }

    @Test
    void testPointsLogicForWinDrawLose() {
        Sport football = new Football();

        Team strongTeam = new FootballTeam("football", "Strong", "Coach S", "Stadium S");
        Team weakTeam = new FootballTeam(football, "Weak", "Coach W", "Stadium W");

        // Strength farkı
        strongTeam.addPlayer(new FootballPlayer(90));
        weakTeam.addPlayer(new FootballPlayer(60));

        Match match = new Match(strongTeam, weakTeam);
        MatchEngine engine = new FootballMatchEngine(match);

        engine.playMatch();

        // Match sonucu ne olursa olsun puanlar doğru olmalı
        if (match.getHomeScore() > match.getAwayScore()) {
            assertEquals(3, strongTeam.getPoint(), "Winner should get 3 points");
            assertEquals(0, weakTeam.getPoint(), "Loser should get 0 points");
        } else if (match.getHomeScore() < match.getAwayScore()) {
            assertEquals(0, strongTeam.getPoint(), "Loser should get 0 points");
            assertEquals(3, weakTeam.getPoint(), "Winner should get 3 points");
        } else {
            assertEquals(1, strongTeam.getPoint(), "Draw gives 1 point");
            assertEquals(1, weakTeam.getPoint(), "Draw gives 1 point");
        }
    }

    @Test
    void testRandomnessProducesDifferentScores() {
        Sport football = new Football();

        Team team1 = new FootballTeam(football, "Team1", "Coach1", "Stadium1");
        Team team2 = new FootballTeam(football, "Team2", "Coach2", "Stadium2");

        team1.addPlayer(new FootballPlayer(80));
        team2.addPlayer(new FootballPlayer(80));

        Match match1 = new Match(team1, team2);
        Match match2 = new Match(team1, team2);

        MatchEngine engine1 = new FootballMatchEngine(match1);
        MatchEngine engine2 = new FootballMatchEngine(match2);

        engine1.playMatch();
        engine2.playMatch();

        boolean different = match1.getHomeScore() != match2.getHomeScore() ||
                match1.getAwayScore() != match2.getAwayScore();

        assertTrue(different, "Randomness should produce different scores in repeated matches");
    }
}