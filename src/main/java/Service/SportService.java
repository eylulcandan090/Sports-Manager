package Service;

import Repository.SportRepo;

import java.util.ArrayList;

public class SportService {
    private SportRepo repo;

    public SportService(SportRepo sportRepo){
        this.repo=sportRepo;
    }

    public void saveSport(String name){
        repo.addSport(name);
    }

    public ArrayList<String> getSports(){
        return repo.getAllSports();
    }


    public int getOrSave(String sport){
        int id=repo.findByName(sport);

        if(id==-1){
            saveSport(sport);
            return repo.findByName(sport);
        }
        else{
            return id;
        }
    }

}
