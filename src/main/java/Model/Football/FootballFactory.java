package Model.Football;

import Model.*;

public class FootballFactory {


    public Player createPlayer(String name, int age, int injuryStatus, int team_id, int shooting, int passing, int goalkeeping,String position,int defance) {
        return new FootballPlayer(name, age, injuryStatus, team_id, shooting, passing, goalkeeping, position, defance);
    }


    public TrainingStrategy createTraining(TrainingType type) {
        switch (type){
            case SHOOT:
                return new ShootTraining();
            case PASS:
                return new PassTraining();
            case GOALKEEPER:
                return new GoalKeeping();
        }
        throw new IllegalArgumentException("Unknown training type");
    }


    public Coach createCoach(String name, TrainingType trainingType, int skillLevel, int teamId) {
        return new Coach(name, trainingType, skillLevel, teamId);
    }
}
