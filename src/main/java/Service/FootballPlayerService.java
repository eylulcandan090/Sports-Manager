package Service;

import Model.Coach;
import Model.Football.FootballPlayer;
import Repository.CoachRepo;
import Repository.FootballPlayerRepo;

public class FootballPlayerService {
    private FootballPlayerRepo playerRepo;
    private CoachRepo coachRepo;

    public FootballPlayerService(FootballPlayerRepo footballPlayerRepo,CoachRepo coachRepo){
        this.playerRepo=footballPlayerRepo;
        this.coachRepo=coachRepo;
    }

    public void applyTraining(int player_id,int coachId){
        FootballPlayer player=playerRepo.getFootballPlayerById(player_id);
        Coach coach=coachRepo.getCoachById(coachId);

        if(player!=null && coach!=null){
            coach.train(player);
            playerRepo.updatePlayer(player);
        }
    }


    public void save(String name, int age, int injuryStatus, int team_id, int shooting, int passing, int goalkeeping,String position){
        playerRepo.addFootballPlayer(name,age,injuryStatus,team_id,shooting,passing,goalkeeping,position);
    }









}
