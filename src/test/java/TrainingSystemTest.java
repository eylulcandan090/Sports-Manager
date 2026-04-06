import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class TrainingSystemTest {

    static class TrainingSystemImpl extends TrainingSystem {

        public TrainingSystemImpl() {
            super(null);
        }

        @Override
        public void applyTrainingEffect(Player player, String attribute) {
            player.setAttribute(attribute, player.getAttribute(attribute) + 1);
        }

        @Override
        public List<String> getTrainableAttributes() {
            return Arrays.asList("speed");
        }
    }

    static class PlayerImpl extends Player {
    }

    static class TeamImpl extends Team {
        public TeamImpl() {
            super(null, "Test Team", "Test Coach","Test Stadium");
        }
    }

    @Test
    void conductTrainingTest() {
        TrainingSystem training = new TrainingSystemImpl();

        Player player = new PlayerImpl();
        player.setAttribute("speed", 5);

        Team team = new TeamImpl();
        team.getPlayerList().add(player);

        training.conductTraining(team);

        assertEquals(6, player.getAttribute("speed"));
    }

    @Test
    void defaultSummaryTest() {
        TrainingSystem training = new TrainingSystemImpl();

        assertEquals("Training session", training.getSessionSummary());
    }

    @Test
    void setFocusTest() {
        TrainingSystem training = new TrainingSystemImpl();

        training.setFocus("speed");

        assertEquals("Focus: speed", training.getSessionSummary());
    }
}


