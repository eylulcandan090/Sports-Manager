package Repo;

import Model.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class TeamRepo {
    private Connection connection;

    public TeamRepo(Connection connection){
        this.connection=connection;
    }

    public void addTeam(String team_name,int league_id){
       String query="INSERT INTO football_teams(team_name,league_id)" +
               "VALUES(?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1,team_name);
            ps.setInt(2,league_id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public int getTeamRating(String team_name){
        String query="SELECT AVG(rating) FROM football_players"+
                "WHERE team_id IN(SELECT id FROM football_teams WHERE team_name=?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1,team_name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return (int) rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Team> getTeams(){
        String query="SELECT team_name FROM football_teams";
        ArrayList<Team> list=new ArrayList<>();


        return list;


    }


}
