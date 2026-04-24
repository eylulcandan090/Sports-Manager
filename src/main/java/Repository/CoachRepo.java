package Repository;

import Model.Coach;
import Model.TrainingType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoachRepo {
    private Connection connection;

    public CoachRepo(Connection connection){
        this.connection=connection;
    }


    public Coach getCoachById(int id){
        String query="SELECT*FROM coach WHERE id=?";

        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setInt(1,id);
            ResultSet resultSet=ps.executeQuery();

            if(resultSet.next()){
                String name=resultSet.getString("name");
                int skill=resultSet.getInt("skill");
                String type=resultSet.getString("type");
                int teamId=resultSet.getInt("team_id");

                TrainingType trainingType=TrainingType.valueOf(type);
                return new Coach(name,trainingType,skill,teamId);
            }

        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return null;
    }





}
