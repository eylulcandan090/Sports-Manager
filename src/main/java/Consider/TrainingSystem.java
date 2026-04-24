package Consider;

import Model.Player;
import Model.Sport;
import Model.Team;

import java.util.List;
import java.util.Random;

public abstract class TrainingSystem {
    protected Sport sport;
    protected Random rand;
    protected String focusAttribute;

    public TrainingSystem(Sport sport) {
        this.sport = sport;
        this.rand = new Random();
    }

    public abstract void applyTrainingEffect(Player player, String attribute);

    public abstract List<String> getTrainableAttributes();

    public void conductTraining(Team team) {
        if (team == null || team.getPlayerList()==null||team.getPlayerList().isEmpty()) {
            return;
        }
        List<String> attributes = getTrainableAttributes();
        if (attributes == null || attributes.isEmpty()) {
            return;
        }
        focusAttribute = attributes.get(rand.nextInt(attributes.size()));
        for (Player p : team.getPlayerList()) {
            applyTrainingEffect(p, focusAttribute);
        }
    }

    public String getSessionSummary() {
        if (focusAttribute == null) {
            return "Training session";
        }
        return "Focus: " + focusAttribute;
    }
    public void setFocus(String focusAttribute) {
        this.focusAttribute = focusAttribute;
    }
}

