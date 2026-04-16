import Model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FootballMatchEngineTest {

    @Test
    void testMatchPlayedAndScoreGenerated() {
        // Setup
        Sport football = new Football();

        Team team1 = new FootballTeam(football, "Model.Team A", "Model.Coach A", "Stadium A");
        Team team2 = new FootballTeam(football, "Model.Team B", "Model.Coach B", "Stadium B");

        // Oyuncular ekle (dummy)
        Player p1=new Player() {};
        p1.setAttribute("rating",80);
        team1.addPlayer(p1);

        Player p2=new Player() {};
        p2.setAttribute("rating",85);
        team1.addPlayer(p2);

        Player p3=new Player() {};
        p3.setAttribute("rating",78);
        team2.addPlayer(p3);

        Player p4=new Player() {};
        p4.setAttribute("rating",82);
        team2.addPlayer(p4);

        // Model.Match an engine
        Match match = new Match(team1, team2);
        MatchEngine engine = new FootballMatchEngine(match);

        // Maçı oynat
        engine.playMatch();


        assertTrue(match.isPlayed(), "Model.Match should be marked as played");
        assertTrue(match.getHomeScore() >= 0, "Home score cannot be negative");
        assertTrue(match.getAwayScore() >= 0, "Away score cannot be negative");

        // Puanlar update edilmiş mi?
        int totalPoints = team1.getPoint() + team2.getPoint();
        assertTrue(totalPoints > 0, "Points should be updated after match");
    }

    @Test
    void testPointsLogicForWinDrawLose() {
        Sport football = new Football();

        Team strongTeam = new FootballTeam(football, "Strong", "Model.Coach S", "Stadium S");
        Team weakTeam = new FootballTeam(football, "Weak", "Model.Coach W", "Stadium W");

        // Strength farkı
        Player p1=new Player() {};
        p1.setAttribute("rating",90);
        strongTeam.addPlayer(p1);

        Player p2=new Player() {};
        p2.setAttribute("rating",60);
        weakTeam.addPlayer(p2);

        Match match = new Match(strongTeam, weakTeam);
        MatchEngine engine = new FootballMatchEngine(match);

        engine.playMatch();

        // Model.Match sonucu ne olursa olsun puanlar doğru olmalı
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

        Player p1=new Player() {};
        p1.setAttribute("rating",80);
        team1.addPlayer(p1);

        Player p2=new Player() {};
        p2.setAttribute("rating",80);
        team2.addPlayer(p2);

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