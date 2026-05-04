package Repository;

import Model.Fixture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FixtureRepo {
    private Connection connection;

    public FixtureRepo(Connection connection){
        this.connection=connection;
    }


    public List<Fixture> getAllFixture(){
        ArrayList<Fixture> fixtures=new ArrayList<>();
        String query="SELECT*FROM fixtures";

        try(Statement statement=connection.createStatement()){
            ResultSet rs=statement.executeQuery(query);

            while (rs.next()){
                int homeId=rs.getInt("home_id");
                int awayId=rs.getInt("away_id");
                int week=rs.getInt("week");
                int isPlayed=rs.getInt("isPlayed");

                boolean flag=true;

                if(isPlayed==0) flag=false;
                fixtures.add(new Fixture(homeId,awayId,week,flag));
            }
            return fixtures;
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return new ArrayList<>();
    }
    public void markAsPlayed(int homeId, int awayId, int week){
        String query = "UPDATE fixtures SET isPlayed=1 WHERE home_id=? AND away_id=? AND week=?";

        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, homeId);
            ps.setInt(2, awayId);
            ps.setInt(3, week);
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }




}
