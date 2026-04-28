package Model;

public interface SportFactory {
    public Player createPlayer();
    public TrainingStrategy createTraining(TrainingType type);
    public Coach createCoach();
}
