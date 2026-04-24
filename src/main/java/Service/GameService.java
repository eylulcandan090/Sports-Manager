package Service;

import java.sql.Connection;

public class GameService {
    private Connection connection;

    public static void resetGame(){
        String delete="DELETE FROM matches";
    }

    public static boolean hasGame(){
        return true;
    }



}
