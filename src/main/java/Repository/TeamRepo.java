package Repository;

import Model.Basketball.Basketball;
import Model.Football.Football;
import Model.Sport;
import Model.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeamRepo {
    private Connection connection;

    public TeamRepo(Connection connection) {
        this.connection = connection;
    }


    public void addTeam(String name,int leagueId,int sportId){
        String query="INSERT INTO teams(team_name,league_id,sport_id) "+
                "VALUES(?,?,?)";

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,name);
            ps.setInt(2,leagueId);
            ps.setInt(3,sportId);
            ps.executeUpdate();
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }


    public ArrayList<Team> getAllTeamsBySport(String sport){
        String query="SELECT*FROM teams WHERE sport_id=(SELECT id FROM sports WHERE sport_name=?)";
        ArrayList<Team> list=new ArrayList<>();

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,sport);
            ResultSet resultSet=ps.executeQuery();

            while(resultSet.next()){
                String name=resultSet.getString("team_name");
                int point=resultSet.getInt("points");
                int id=resultSet.getInt("id");
                list.add(new Team(id,name,point));
            }

            return list;

        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return new ArrayList<>();
    }


    public ArrayList<Team> getAllTeamsByLeagueId(int leagueId){
        String query="SELECT*FROM teams WHERE league_id=?";
        ArrayList<Team> list=new ArrayList<>();

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setInt(1,leagueId);
            ResultSet resultSet=ps.executeQuery();

            while(resultSet.next()){
                String name=resultSet.getString("team_name");
                int point=resultSet.getInt("points");
                int id=resultSet.getInt("id");
                list.add(new Team(id,name,point));
            }

            return list;

        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return new ArrayList<>();
    }

    public int findByName(String name,int leagueId){
        String query="SELECT id FROM teams WHERE team_name=? AND league_id=?";

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,name);
            ps.setInt(2,leagueId);
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

     public Team getTeamByTeamId(int teamId){
        String query="SELECT*FROM teams WHERE id=?";

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ResultSet rs=ps.executeQuery();

            String name=rs.getString("team_name");
            int point=rs.getInt("points");
            int id=rs.getInt("id");
            return (new Team(id,name,point));

        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return null;
    }

    public Sport getSportByTeamId(int id){
        String query="SELECT*FROM teams WHERE id=?";

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();

            int sportId=0;
            if(rs.next()){
                sportId=rs.getInt("sport_id");
            }

            return getSportById(sportId);

        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return null;
    }


    private Sport getSportById(int sportId){
        if(sportId==0) return null;

        String sql="SELECT sport_name FROM sports WHERE id=?";

        try(PreparedStatement ps=connection.prepareStatement(sql)){
            ps.setInt(1,sportId);
            ResultSet rs=ps.executeQuery();
            String sportName="";

            if(rs.next()){
                sportName=rs.getString("sport_name");
            }

            switch(sportName){
                case "Football":
                    return new Football();
                case "Basketball":
                    return new Basketball();
            }

        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }

        return null;

    }



}
