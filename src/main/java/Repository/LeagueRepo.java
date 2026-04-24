package Repository;

import Model.Basketball.Basketball;
import Model.Football.Football;
import Model.League;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LeagueRepo {
    private Connection connection;

    public LeagueRepo(Connection connection){
        this.connection=connection;
    }

    public void addLeague(String name,int sportId){
        String query="INSERT INTO leagues(name,sport_id) VALUES(?,?)";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,name);
            ps.setInt(2,sportId);
            ps.executeUpdate();
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }

    public ArrayList<League> getAllLeagues(){
        String query="SELECT*FROM leagues";
        ArrayList<League> leagues=new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ResultSet resultSet=ps.executeQuery();

            while (resultSet.next()){
                String name=resultSet.getString("name");
                int sportId=resultSet.getInt("sport_id");

                String subquery="SELECT sport_name FROM sports WHERE id=?";

                PreparedStatement preparedStatement=connection.prepareStatement(subquery);
                preparedStatement.setInt(1,sportId);
                ResultSet set=preparedStatement.executeQuery();

                if(set.next()){
                    String sportName=set.getString("sport_name");

                    switch (sportName){
                        case "Football":
                            leagues.add(new League(name,new Football()));
                            break;
                        case "Basketball":
                            leagues.add(new League(name,new Basketball()));
                            break;
                    }
                }

                return leagues;
            }

        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }

        return new ArrayList<>();
    }

    public int findByName(String league){
        String query="SELECT id FROM leagues WHERE name=?";

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1,league);
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
