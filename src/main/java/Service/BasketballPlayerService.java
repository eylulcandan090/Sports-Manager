package Service;

import Repository.BasketballPlayerRepo;

public class BasketballPlayerService {
    private BasketballPlayerRepo repo;
    public BasketballPlayerService(BasketballPlayerRepo repo){
        this.repo=repo;
    }

    public void addBasketPlayer(String name, int age, int injuryStatus, int team_id, String position, int shooting, int dribbling, int passing, int finishing, int defense, int steal, int block){
        repo.addBasketPlayer(name, age, injuryStatus, team_id, position, shooting, dribbling, passing, finishing, defense, steal, block);
    }


}
