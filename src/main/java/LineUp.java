import Model.Player;
import Model.Substitution;

import java.util.ArrayList;
import java.util.List;

public class LineUp {
    private List<Player> starters;
    private List<Player> substitutes;
    private String tactic;
    private List<Substitution> substitutions;

    public LineUp() {
        this.starters = new ArrayList<>();
        this.substitutes = new ArrayList<>();
        this.tactic = "default";
        this.substitutions = new ArrayList<>();
    }

    public List<Player> getStarters() {
        return starters;
    }

    public List<Player> getSubstitutes() {
        return substitutes;
    }

    public String getTactic() {
        return tactic;
    }

    public void setTactic(String tactic) {
        this.tactic = tactic;
    }

    public List<Substitution> getSubstitutions() {
        return substitutions;
    }

    public void addStarter(Player player) {
        if (player != null && !starters.contains(player)) {
            starters.add(player);
        }
    }

    public void addSubstitute(Player player) {
        if (player != null && !substitutes.contains(player)) {
            substitutes.add(player);
        }
    }

    public void makeSubstitution(Player playerOut, Player playerIn, int minute) {
        if (playerOut == null || playerIn == null) return;
        if (!starters.contains(playerOut)) return;
        if (!substitutes.contains(playerIn)) return;

        starters.remove(playerOut);
        starters.add(playerIn);
        substitutes.remove(playerIn);

        substitutions.add(new Substitution(playerOut, playerIn, minute));
    }

    public boolean isValid(int requiredStarters) {
        return starters.size() == requiredStarters;
    }

    @Override
    public String toString() {
        return "Tactic: " + tactic + " | Starters: " + starters.size() +
                " | Substitutes: " + substitutes.size();
    }
}