package Database;

import java.sql.*;

public class Database {
    private static Database database;
    private Connection connection;

    private Database(){
        connect();
        enableForeignKeys();
        createLeague();
        createFootballPlayerTable();
        createTeamsTable();
        createMatchTable();
        createCoachTables();
        createGameTable();
        createSportTable();
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


    private void createLeague(){
        String query="CREATE TABLE IF NOT EXISTS leagues ( "+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT NOT NULL,"+
                "sport_id INTEGER,"+
                "FOREIGN KEY(sport_id) REFERENCES sports(id) )";
        try (Statement stmt=connection.createStatement()){
            stmt.execute(query);
        }catch(SQLException s){
            System.out.println(s.getMessage());
        }
    }

    private void createTeamsTable(){
        String query="CREATE TABLE  IF NOT EXISTS teams("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "team_name TEXT NOT NULL,"+
                "league_id INTEGER,"+
                "points INTEGER DEFAULT 0,"+
                "sport_id INTEGER,"+
                "FOREIGN KEY(league_id) REFERENCES leagues(id),"+
                "FOREIGN KEY(sport_id) REFERENCES sports(id) )";

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
                "age INTEGER,"+
                "position TEXT,"+
                "injuryStatus INTEGER,"+
                "shooting INTEGER,"+
                "passing INTEGER,"+
                "goalkeeping INTEGER,"+
                "team_id INTEGER,"+
                "FOREIGN KEY(team_id) REFERENCES teams(id) )";
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
                " FOREIGN KEY(home_team_id) REFERENCES teams(id)," +
                " FOREIGN KEY(away_team_id) REFERENCES teams(id)" +
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
                "FOREIGN KEY(team_id) REFERENCES teams(id) )";

        try(Statement stmt=connection.createStatement()){
            stmt.execute(query);
        }catch(SQLException s){
            System.out.println(s.getMessage());
        }
    }

    private void createGameTable(){
        String query="CREATE TABLE IF NOT EXISTS game_status(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "isStarted INTEGER," +
                "selected_teamId INTEGER," +
                "FOREIGN KEY(selected_teamId) REFERENCES teams(id) )";

        String insert= "INSERT INTO game_status(id,isStarted) " +
                "SELECT 1,0 WHERE NOT EXISTS (SELECT 1 FROM game_status)";

        try (Statement statement=connection.createStatement()) {
            statement.execute(query);
            statement.executeUpdate(insert);


        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    private void createSportTable(){
        String query="CREATE TABLE IF NOT EXISTS sports("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "sport_name VARCHAR(40) )";

        try(Statement statement=connection.createStatement()){
            statement.executeUpdate(query);
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }




}
