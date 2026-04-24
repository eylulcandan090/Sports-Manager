package Service;

import Model.Player;
import Model.Team;
import Repository.TeamRepo;

import java.util.ArrayList;

public class TeamService {
    private TeamRepo teamRepo;

    public TeamService(TeamRepo repo){
        this.teamRepo=repo;
    }


    public ArrayList<Player> getPlayersByTeamId(int teamId){
        return null;
    }

    public void saveTeam(String name,int leagueId,int sportId){
        teamRepo.addTeam(name,leagueId,sportId);
    }

    public ArrayList<Team> getAllTeamsByLeagueId(int leagueId){
        return teamRepo.getAllTeamsByLeagueId(leagueId);
    }


    public int getOrSave(String name,int leagueId,int sportId){
        int id=teamRepo.findByName(name,leagueId);

        if(id==-1){
            saveTeam(name,leagueId,sportId);
            return teamRepo.findByName(name,leagueId);
        }
        return id;
    }



}
