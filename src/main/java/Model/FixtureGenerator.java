package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class FixtureGenerator {

    public static void generateAndSave(List<Team> teams,Connection connection) {

        Collections.shuffle(teams);

        int n = teams.size();

        String insertQuery =
                "INSERT INTO fixtures (week,home_id,away_id,isPlayed) VALUES (?, ?, ?, ?)";

        String delete="DELETE FROM fixtures";

       try{
           Statement statement=connection.createStatement();
           statement.executeUpdate(delete);
       }catch (SQLException sqlException){
           System.out.println(sqlException.getMessage());
       }


        

        try {

            for (int round=0;round<n - 1;round++) {

                for (int i=0;i<n/2;i++) {

                    Team home=teams.get(i);
                    Team away=teams.get(n - 1 - i);

                    try (PreparedStatement ps=connection.prepareStatement(insertQuery)) {

                        ps.setInt(1,round + 1);      // week
                        ps.setInt(2,home.getId());   // home_id
                        ps.setInt(3,away.getId());   // away_id
                        ps.setInt(4,0);              // not played

                        ps.executeUpdate();
                    }
                }

                // rotation
                Team last=teams.remove(n - 1);
                teams.add(1,last);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
