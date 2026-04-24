package Repository;

import Model.Basketball.BasketballPlayer;

import java.sql.*;
import java.util.ArrayList;

public class BasketballPlayerRepo {
    private Connection connection;

    public BasketballPlayerRepo(Connection connection){
        this.connection=connection;
    }

    public void addBasketPlayer(String name, int age, int injuryStatus, int team_id, String position, int shooting, int dribbling, int passing, int finishing, int defense, int steal, int block){
        String query="INSERT INTO basketball_players(name,age,injuryStatus,team_id,position,shooting,dribbling, int passing, int finishing, int defense, int steal, int block) "+
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,name);
            ps.setInt(2,age);
            ps.setInt(3,injuryStatus);
            ps.setInt(4,team_id);
            ps.setString(5,position);
            ps.setInt(6,shooting);
            ps.setInt(7,dribbling);
            ps.setInt(8,passing);
            ps.setInt(9,finishing);
            ps.setInt(10,defense);
            ps.setInt(11,steal);
            ps.setInt(12,block);
            ps.executeUpdate();
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }


    public ArrayList<BasketballPlayer> getPlayersByTeam(int team_id){
        ArrayList<BasketballPlayer> players=new ArrayList<>();

        String query="SELECT*FROM basketball_players WHERE team_id=?";

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setInt(1,team_id);
            ResultSet set=ps.executeQuery();

            while(set.next()){
                String name=set.getString("name");
                int age=set.getInt("age");
                int injuryStatus=set.getInt("injuryStatus");
                String position=set.getString("position");
                int shooting=set.getInt("shooting");
                int dribbling=set.getInt("dribbling");
                int passing=set.getInt("passing");
                int finishing=set.getInt("finishing");
                int defense=set.getInt("defense");
                int steal=set.getInt("steal");
                int block=set.getInt("block");

                players.add(new BasketballPlayer(name,age,injuryStatus,team_id,position,shooting,dribbling,passing,finishing,defense,steal,block));
            }
            return players;
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }

        return new ArrayList<>();
    }





}
