package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Database database;
    private Connection connection;

    private Database(){
        connect();
        enableForeignKeys();
        createFootballLeague();
        createFootballPlayerTable();
        createFootballTeamsTable();
        createMatchTable();
        createCoachTables();
    }

    public static Database getInstance(){
        if(database==null){
            database=new Database();
        }
        return database;
    }




    public void connect(){
        try {
            connection= DriverManager.getConnection(
                    "jdbc:sqlite:sportmanager.db"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection(){
        return connection;
    }


    private void createFootballLeague(){
        String query="CREATE TABLE IF NOT EXISTS football_leagues ( "+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT NOT NULL);";
        try (Statement stmt=connection.createStatement()){
            stmt.execute(query);
        }catch(SQLException s){
            System.out.println(s.getMessage());
        }
    }

    private void createFootballTeamsTable(){
        String query="CREATE TABLE  IF NOT EXISTS football_teams("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "team_name TEXT NOT NULL,"+
                "league_id INTEGER,"+
                "FOREIGN KEY(league_id) REFERENCES football_leagues(id) )";

        try(Statement stmt=connection.createStatement()) {
            stmt.execute(query);
        }catch(SQLException s){
            System.out.println(s.getMessage());
        }

    }


    private void createFootballPlayerTable() {
        String query="CREATE TABLE IF NOT EXISTS football_players("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT NOT NULL,"+
                "rating INTEGER NOT NULL,"+
                "position TEXT,"+
                "injured BOOLEAN,"+
                "team_id INTEGER,"+
                "FOREIGN KEY(team_id) REFERENCES football_teams(id) )";
        try(Statement stmt=connection.createStatement()) {
            stmt.execute(query);
        }catch(SQLException s){
            System.out.println(s.getMessage());
        }
    }

    private void enableForeignKeys() {
        try(Statement stmt=connection.createStatement()){

            stmt.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void createMatchTable(){
        String query="CREATE TABLE IF NOT EXISTS matches (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " home_team_id INTEGER NOT NULL," +
                " away_team_id INTEGER NOT NULL," +
                " home_score INTEGER DEFAULT 0," +
                " away_score INTEGER DEFAULT 0," +
                " match_date TEXT," +
                " FOREIGN KEY(home_team_id) REFERENCES football_teams(id)," +
                " FOREIGN KEY(away_team_id) REFERENCES football_teams(id)" +
                ");";
        try(Statement stmt=connection.createStatement()){
            stmt.execute(query);
        }catch(SQLException s){
            System.out.println(s.getMessage());
        }
    }


    private void createCoachTables(){
        String query="CREATE TABLE IF NOT EXISTS coaches("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "coach_name VARCHAR(50) NOT NULL,"+
                "team_id INTEGER, "+
                "FOREIGN KEY(team_id) REFERENCES football_teams(id) )";

        try(Statement stmt=connection.createStatement()){
            stmt.execute(query);
        }catch(SQLException s){
            System.out.println(s.getMessage());
        }

    }









}
