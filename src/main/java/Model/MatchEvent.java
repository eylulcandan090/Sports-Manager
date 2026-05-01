package Model;

public class MatchEvent {
    private String type;    // "GOAL", "RED_CARD", "BASKET", "FOUL"
    private Player player;
    private Team team;
    private int time;

    public MatchEvent(String type, Player player, Team team, int time) {
        this.type = type;
        this.player = player;
        this.team = team;
        this.time = time;
    }
    public String getType() {
        return type;
    }
    public Player getPlayer() {
        return player;
    }
    public Team getTeam() {
        return team;
    }

    }
