package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection connection;

    public Database(){
        connect();
        enableForeignKeys();
        createFootballLeague();
        createFootballPlayerTable();
        createFootballTeamsTable();
    }

    public void connect(){
        try {
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:sportmanager.db"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection(){
        return this.connection;
    }


    private void createFootballLeague(){
        String query="CREATE TABLE IF NOT EXISTS football_leagues ( "+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT NOT NULL);";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(query);
        }catch(SQLException s){
            System.out.println(s.getMessage());
        }
    }

    private void createFootballTeamsTable(){
        String query="CREATE TABLE football_teams IF NOT EXISTS("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "team_name TEXT NOT NULL,"+
                "league_id INTEGER,"+
                "FOREIGN KEY(league_id) REFERENCES football_leagues(id)";

        try {
            Statement stmt = connection.createStatement();
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
                "FOREIGN KEY(team_id) REFERENCES football_teams(id))";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(query);
        }catch(SQLException s){
            System.out.println(s.getMessage());
        }
    }

    private void enableForeignKeys() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void createMatchTable(){
        String query="CREATE TABLE IF NOT EXISTS matches (" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    home_team_id INTEGER NOT NULL," +
                "    away_team_id INTEGER NOT NULL," +
                "    home_score INTEGER DEFAULT 0," +
                "    away_score INTEGER DEFAULT 0," +
                "    match_date TEXT," +
                "    FOREIGN KEY(home_team_id) REFERENCES football_teams(id)," +
                "    FOREIGN KEY(away_team_id) REFERENCES football_teams(id)" +
                ");";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(query);
        }catch(SQLException s){
            System.out.println(s.getMessage());
        }


    }








}
