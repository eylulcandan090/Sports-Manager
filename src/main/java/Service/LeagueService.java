package Service;

import Repository.LeagueRepo;

public class LeagueService {
    private LeagueRepo repo;

    public LeagueService(LeagueRepo repo){
        this.repo=repo;
    }

    public int getOrSave(String league,int sportId){
        int id=repo.findByName(league);

        if(id==-1){
            repo.addLeague(league,sportId);
            return repo.findByName(league);
        }
        return id;
    }



}
