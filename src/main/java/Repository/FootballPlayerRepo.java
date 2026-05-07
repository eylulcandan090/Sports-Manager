package Repository;

import Model.Football.FootballPlayer;
import Model.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FootballPlayerRepo {
    private Connection connection;

    public FootballPlayerRepo(Connection connection){
        this.connection=connection;
    }


    public void addFootballPlayer(String name, int age, int injuryStatus, int team_id, int shooting, int passing, int goalkeeping,String position,int defance){
        String query="INSERT INTO football_players(name,age,injuryStatus,team_id,shooting,passing,goalkeeping,position,defance) "+
                "VALUES(?,?,?,?,?,?,?,?,?) ";

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,name);
            ps.setInt(2,age);
            ps.setInt(3,injuryStatus);
            ps.setInt(4,team_id);
            ps.setInt(5,shooting);
            ps.setInt(6,passing);
            ps.setInt(7,goalkeeping);
            ps.setString(8,position);
            ps.setInt(9,defance);
            ps.executeUpdate();
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }

    }

    public ArrayList<Player> getFootballPlayersByTeamId(int team_id){
        String query="SELECT*FROM football_players WHERE team_id=?";

        ArrayList<Player> players=new ArrayList<>();

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setInt(1,team_id);
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                int age=rs.getInt("age");
                int injuryStatus=rs.getInt("injuryStatus");
                int shooting=rs.getInt("shooting");
                int passing=rs.getInt("passing");
                int goalkeeping=rs.getInt("goalkeeping");
                String position=rs.getString("position");
                int defance=rs.getInt("defance");
                FootballPlayer fp=new FootballPlayer(name,age,injuryStatus,team_id,shooting,passing,goalkeeping,position,defance);
                fp.setId(id);
                players.add(fp);
            }
            return players;

        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return new ArrayList<>();
    }


    public ArrayList<Player> getHealthyFootballPlayers(int team_id){
        String query="SELECT*FROM football_players WHERE team_id=? AND injuryStatus=0";

        ArrayList<Player> players=new ArrayList<>();

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setInt(1,team_id);
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                int age=rs.getInt("age");
                int injuryStatus=rs.getInt("injuryStatus");
                int shooting=rs.getInt("shooting");
                int passing=rs.getInt("passing");
                int goalkeeping=rs.getInt("goalkeeping");
                String position=rs.getString("position");
                int defance=rs.getInt("defance");
                FootballPlayer fp=new FootballPlayer(name,age,injuryStatus,team_id,shooting,passing,goalkeeping,position,defance);
                fp.setId(id);
                players.add(fp);
            }
            return players;

        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return new ArrayList<>();
    }


    public FootballPlayer getFootballPlayerById(int id){
        String query="SELECT*FROM football_players WHERE id=?";

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setInt(1,id);

            ResultSet rs=ps.executeQuery();
            if(rs.next()) {

                String name = rs.getString("name");
                int age = rs.getInt("age");
                int injuryStatus = rs.getInt("injury");
                int team_id = rs.getInt("team_id");
                int shooting = rs.getInt("shooting");
                int passing = rs.getInt("passing");
                int goalkeeping = rs.getInt("goalkeeping");
                String position = rs.getString("position");
                int defance = rs.getInt("defance");
                return new FootballPlayer(name, age, injuryStatus, team_id, shooting, passing, goalkeeping, position,defance);

            }

        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }

        return null;
    }


    public void updatePlayer(FootballPlayer footballPlayer){
        String query="UPDATE football_players SET "+
                "shooting=?,"+
                "passing=?,"+
                "goalkeeping=?,"+
                "injuryStatus=?,"+
                "defance=? "+
                "WHERE id=?";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setInt(1,footballPlayer.getShooting());
            ps.setInt(2,footballPlayer.getPassing());
            ps.setInt(3,footballPlayer.getGoalkeeping());
            ps.setInt(4,footballPlayer.getInjuryStatus());
            ps.setInt(5,footballPlayer.getDefance());
            ps.setInt(6,footballPlayer.getId());
            ps.executeUpdate();
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }

    public boolean hasPlayers(int teamId){
        String query="SELECT 1 FROM football_players WHERE team_id=? LIMIT 1";

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setInt(1,teamId);

            ResultSet rs=ps.executeQuery();

            return rs.next();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
