package Model;

public class Substitution {
    private Player playerOut;
    private Player playerIn;
    private int minute;

    public Substitution(Player playerOut, Player playerIn, int minute) {
        this.playerOut = playerOut;
        this.playerIn = playerIn;
        this.minute = minute;
    }

    public Player getPlayerOut() {
        return playerOut;
    }

    public Player getPlayerIn() {
        return playerIn;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public String toString() {
        return "Min " + minute + ": " + playerOut.getName() + " -> " + playerIn.getName();
    }
}