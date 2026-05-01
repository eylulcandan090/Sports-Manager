package Model;

import java.util.List;

public class GameState {
    private Team homeTeam;
    private Team awayTeam;
    private int homeScore;
    private int awayScore;
    private int elapsedTime; // dakika veya quarter
    private List<MatchEvent> events; // tüm olayların listesi

    // Her spor buraya event ekleyebilir
    public void applyEvent(MatchEvent event) {
        events.add(event);
        // skoru güncelle vs.
    }

    public GameState(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public List<MatchEvent> getEvents() {
        return events;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }
}
