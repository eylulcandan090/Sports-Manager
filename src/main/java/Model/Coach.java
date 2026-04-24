package Model;

import Model.Football.FootballPlayer;

public class Coach {
    private int id;
    private String name;
    private TrainingType trainingType;
    private int skillLevel;
    private int teamId;


    public Coach(String name, TrainingType trainingType, int skillLevel, int teamId) {
        this.name = name;
        this.trainingType = trainingType;
        this.skillLevel = skillLevel;
        this.teamId = teamId;
    }

    public void train(FootballPlayer player){
        switch (trainingType){
            case SHOOT:
                player.setShooting(player.getShooting()+skillLevel);
                break;
            case PASS:
                player.setPassing(player.getPassing()+skillLevel);
                break;
            case GOALKEEPER:
                player.setGoalkeeping(player.getGoalkeeping()+skillLevel);
                break;
            case DEFANCE:
                player.setDefance(player.getDefance()+skillLevel);
                break;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
