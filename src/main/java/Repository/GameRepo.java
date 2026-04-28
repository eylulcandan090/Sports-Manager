package Repository;

import Model.Game;
import Model.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void createNewGame(int team_id){
        String query="UPDATE game_status SET selected_teamId=? WHERE id=1";

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setInt(1,team_id);
            ps.executeUpdate();
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }

    public int getGameTeamId(){
        String query="SELECT selected_teamId FROM game_status WHERE id=1";

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                return rs.getInt("selected_teamId");
            }

        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return -1;
    }


    public String getGameTeamById(int id) {
        String query = "SELECT team_name FROM teams WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet set = ps.executeQuery();

            if (set.next()) {
                return set.getString("team_name");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return "";
    }

}

