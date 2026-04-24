package Repository;

import Model.Basketball.Basketball;
import Model.Sport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SportRepo {
    private Connection connection;

    public SportRepo(Connection connection) {
        this.connection = connection;
    }


    public ArrayList<String> getAllSports(){
        String query="SELECT sport_name FROM sports";
        ArrayList<String> list=new ArrayList<>();

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                list.add(rs.getString("sport_name"));
            }

            return list;

        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return new ArrayList<>();
    }


    public void addSport(String sport){
        String query="INSERT INTO sports(sport_name) VALUES(?) ";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,sport);
            ps.executeUpdate();
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }

    public int findByName(String sport){
        String query="SELECT id FROM sports WHERE sport_name=?";

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,sport);
            ResultSet set=ps.executeQuery();

            if(set.next()){
                int id=set.getInt("id");
                return id;
            }
            return -1;
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return -1;
    }



}
