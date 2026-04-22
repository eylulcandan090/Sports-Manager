package Model;

public class ShootTraining implements TrainingStrategy{

    @Override
    public void train(Player player,Coach coach){
       FootballPlayer footballPlayer=(FootballPlayer) player;
       footballPlayer.setShooting(footballPlayer.getShooting()+coach.getSkillLevel());
    }
}
