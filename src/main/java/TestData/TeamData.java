package TestData;

import Database.Database;
import Model.Team;
import Repo.TeamRepo;

public class TeamData {

    public static void main(String[] args) {
        Database db=new Database();
        TeamRepo repo=new TeamRepo(db.getConnection());
        repo.addTeam("Arsenal", 1);
        repo.addTeam("Aston Villa", 1);
        repo.addTeam("Bournemouth", 1);
        repo.addTeam("Brentford", 1);
        repo.addTeam("Brighton & Hove Albion", 1);
        repo.addTeam("Chelsea", 1);
        repo.addTeam("Crystal Palace", 1);
        repo.addTeam("Everton", 1);
        repo.addTeam("Fulham", 1);
        repo.addTeam("Liverpool", 1);
        repo.addTeam("Manchester City", 1);
        repo.addTeam("Manchester United", 1);
        repo.addTeam("Newcastle United", 1);
        repo.addTeam("Nottingham Forest", 1);
        repo.addTeam("Tottenham Hotspur", 1);
        repo.addTeam("West Ham United", 1);
        repo.addTeam("Wolverhampton Wanderers", 1);
    }



}
