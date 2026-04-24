package Model.Football;

import Model.Player;

public class FootballPlayer extends Player {
    private int shooting;
    private int passing;
    private int goalkeeping;
    private int defance;

    public FootballPlayer(String name, int age, int injuryStatus, int team_id, int shooting, int passing, int goalkeeping, String position, int defance) {
        super(name, age, injuryStatus, team_id,position);
        this.shooting = shooting;
        this.passing = passing;
        this.goalkeeping = goalkeeping;
        this.defance=defance;
    }



    public int getShooting() {
        return shooting;
    }

    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    public int getPassing() {
        return passing;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public int getGoalkeeping() {
        return goalkeeping;
    }

    public void setGoalkeeping(int goalkeeping) {
        this.goalkeeping = goalkeeping;
    }

    public int getDefance() {
        return defance;
    }

    public void setDefance(int defance) {
        this.defance = defance;
    }
}
