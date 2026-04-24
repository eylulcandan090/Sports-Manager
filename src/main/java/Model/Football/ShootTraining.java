package Model.Football;

import Model.Coach;
import Model.Player;
import Model.TrainingStrategy;

public class ShootTraining implements TrainingStrategy {

    @Override
    public void train(Player player, Coach coach){
       FootballPlayer footballPlayer=(FootballPlayer) player;
       footballPlayer.setShooting(footballPlayer.getShooting()+coach.getSkillLevel());
    }
}
