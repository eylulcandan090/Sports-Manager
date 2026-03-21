import java.util.List;
import java.util.Random;

public abstract class TrainingSystem {
    protected Sport sport;
    protected Random rand;
    public TrainingSystem(Sport sport) {
        this.sport=sport;
        this.rand=new Random();
    }
    public abstract void applyTrainingEffect(Player player);
    public abstract List<String> getTrainableAttributes();
    public void conductTraining() {
        List<String> attributes=getTrainableAttributes();
        if(attributes != null && attributes.size()>0) {
            rand.nextInt(attributes.size());
        }
    }
    public String getSessionSummary(){
        return "Training session";
    }
}
