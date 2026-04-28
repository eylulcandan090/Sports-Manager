import Model.Football.FootballTraining;
import Model.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

    public class FootballTrainingTest {

        @Test
        void testApplyTraining() {
            FootballTraining training = new FootballTraining(null);

            Player player = new Player() {};
            player.setAttribute("shooting", 5);

            training.applyTrainingEffect(player, "shooting");

            assertEquals(6, player.getAttribute("shooting"));
        }

        @Test
        void testAttributes() {
            FootballTraining training = new FootballTraining(null);

            assertFalse(training.getTrainableAttributes().isEmpty());
        }
    }

