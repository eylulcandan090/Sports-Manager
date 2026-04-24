package Model.Basketball;

import Model.Player;

public class BasketballPlayer extends Player {
    private int id;
    private int shooting;
    private int dribbling;
    private int passing;
    private int finishing;
    private int defense;
    private int steal;
    private int block;


    public BasketballPlayer(String name, int age, int injuryStatus, int team_id, String position, int shooting, int dribbling, int passing, int finishing, int defense, int steal, int block) {
        super(name, age, injuryStatus, team_id, position);
        this.shooting = shooting;
        this.dribbling = dribbling;
        this.passing = passing;
        this.finishing = finishing;
        this.defense = defense;
        this.steal = steal;
        this.block = block;
    }

    public int getShooting() {
        return shooting;
    }

    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    public int getDribbling() {
        return dribbling;
    }

    public void setDribbling(int dribbling) {
        this.dribbling = dribbling;
    }

    public int getPassing() {
        return passing;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public int getFinishing() {
        return finishing;
    }

    public void setFinishing(int finishing) {
        this.finishing = finishing;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSteal() {
        return steal;
    }

    public void setSteal(int steal) {
        this.steal = steal;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }
}
