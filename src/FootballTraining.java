import java.util.List;

public class FootballTraining extends TrainingSystem{
    public FootballTraining(Sport sport) {
        super(sport);
    }

    @Override
    public void applyTrainingEffect(Player player, String attribute) {
        if(player==null){
            return;
    }
        if(attribute!=null&&attribute.equals("strength")){
            player.setStrength(player.getStrength()+1);
        }
    }

    @Override
    public List<String> getTrainableAttributes() {
        return List.of("strength");
    }
}
