package Model;

import java.util.ArrayList;
import java.util.List;

public class Team implements Comparable<Team>{
    private int id;
    private String name;
    private int point;
    private List<Player> players = new ArrayList<>();

    public Team(String name) {
        this.name = name;
        this.point=0;
    }

    public Team(String name,int point){
        this.name=name;
        this.point=point;
    }

    public Team(int id, String name, int point) {
        this.id = id;
        this.name = name;
        this.point = point;
    }

    public Team(int id, String name, int point, List<Player> players) {
        this.id = id;
        this.name = name;
        this.point = point;
        this.players = players;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void addPoint(int point){
        this.point+=point;
    }

    @Override
    public String toString(){
        return this.name;
    }


    @Override
    public int compareTo(Team o) {
        return Integer.compare(o.getPoint(),this.getPoint());
    }

    public void setPlayers(List<Player> players) { this.players = players; }

    public int teamStrength() {
        if (players.isEmpty()) return 0;
        return (int) players.stream()
                .mapToInt(Player::getOverall)
                .average()
                .orElse(0);
    }

}
