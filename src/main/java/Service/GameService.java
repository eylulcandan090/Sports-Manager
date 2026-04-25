package Service;

import Database.Database;
import Repository.GameRepo;

import java.sql.Connection;

public class GameService {
    private GameRepo repo;

    public GameService(GameRepo repo){
        this.repo=repo;
    }



    public static void resetGame(){
        String delete="DELETE FROM matches";
    }

    public boolean hasGame(){
        return repo.isGameStarted();
    }


    public int getGameTeamId(){
        return repo.getGameTeamId();
    }

}
