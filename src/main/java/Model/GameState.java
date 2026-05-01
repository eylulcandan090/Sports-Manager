package Model;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private Team homeTeam;
    private Team awayTeam;
    private int homeScore;
    private int awayScore;
    private int elapsedTime; // dakika veya quarter
    private List<MatchEvent> events;


    public GameState(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.events = new ArrayList<>();
        this.elapsedTime = 0;
    }
public void incrementTime(){
        this.elapsedTime++;
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

    public void applyEvent(MatchEvent event) {
        events.add(event);
        if(event.getType().equals("GOAL")){
            if (event.getTeam()==homeTeam) homeScore++;
            else awayScore++;

        }
        if(event.getType().equals("BASKET")){
        int pts=Math.random()<0.3 ? 3:2;
        if(event.getTeam()==homeTeam) homeScore+=pts;
        else awayScore+=pts;

        }
    }
}
