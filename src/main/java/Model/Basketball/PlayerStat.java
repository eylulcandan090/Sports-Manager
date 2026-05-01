package Model.Basketball;

public class PlayerStat {

    private final BasketballPlayer player;
    private int points;
    private int assists;
    private int rebounds;
    private int fouls;
    private boolean fouledOut;

    PlayerStat(BasketballPlayer player) {
        this.player = player;
    }

    void addPoints(int pts) { points += pts; }
    void addAssist()        { assists++;     }
    void addRebound()       { rebounds++;    }

    void addFoul() {
        if (++fouls >= 5) fouledOut = true;
    }

    boolean isActive() {
        return !fouledOut && player.getInjuryStatus() == 0;
    }

    public BasketballPlayer getPlayer()  { return player;    }
    public int getPoints()               { return points;    }
    public int getAssists()              { return assists;   }
    public int getRebounds()             { return rebounds;  }
    public int getFouls()                { return fouls;     }
    public boolean hasFouledOut()        { return fouledOut; }
}