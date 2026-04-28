package Repository;

import Model.Fixture;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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





}
