import java.util.List;

public class FootballTraining extends TrainingSystem{
    public FootballTraining(Sport sport) {
        super(sport);
    }

    @Override
    public void applyTrainingEffect(Player player, String attribute) {
        if(player==null||attribute==null){
            return;
    }
       int current=player.getAttribute(attribute);
        player.setAttribute(attribute,current +1);

    }

    @Override
    public List<String> getTrainableAttributes() {
        return List.of("shooting","passing","defense","fitness","attack");
    }
}
