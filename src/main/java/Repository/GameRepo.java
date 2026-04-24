package Repository;

import Model.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GameRepo {
    private Connection connection;

    public GameRepo(Connection connection){
        this.connection=connection;
    }


    public boolean isGameStarted(){
        String sql = "SELECT isStarted FROM game_status WHERE id=1";
        try(PreparedStatement ps=connection.prepareStatement(sql)){
            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                return rs.getInt("isStarted")==1;
            }

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void setGameStarted(boolean started){
        String sql="UPDATE game_status SET isStarted=? WHERE id=1";

        try(PreparedStatement ps=connection.prepareStatement(sql)){
            ps.setInt(1,started ? 1 : 0);
            ps.executeUpdate();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


}
