package Service;

import Model.Fixture;
import Model.Team;
import Repository.FixtureRepo;
import Repository.TeamRepo;

import java.util.ArrayList;
import java.util.List;

public class FixtureService {
    private FixtureRepo repo;
    private TeamRepo teamRepo;

    public FixtureService(FixtureRepo repo, TeamRepo teamRepo) {
        this.repo=repo;
        this.teamRepo=teamRepo;
    }
    public List<Fixture> getFixtures(){
        return repo.getAllFixture();
    }
    public void markAsPlayed(Fixture fixture){
        repo.markAsPlayed(
                fixture.getHomeId(),
                fixture.getAwayId(),
                fixture.getWeek()
        );
    }
    public int getCurrentWeek(){
        List<Fixture> fixtures = repo.getAllFixture();
        for(Fixture fixture : fixtures){
            if(!fixture.getIsPlayed()){
                return fixture.getWeek();
            }
        }
        return 12;
    }
    public  ArrayList<String>  printFixture(){
        List<Fixture> teams=repo.getAllFixture();
        ArrayList<String> fixturesTable=new ArrayList<>();

        for(Fixture fixture:teams){
            Team homeTeam=teamRepo.getTeamByTeamId(fixture.getHomeId());
            Team awayTeam=teamRepo.getTeamByTeamId(fixture.getAwayId());
            int week=fixture.getWeek();
            boolean isPlayed=fixture.getIsPlayed();
            fixturesTable.add("Week "+week+"  "+homeTeam.getName()+" vs "+awayTeam.getName());  //oynanmış maçlar için match tablosundan skorları getirelim ileride
        }

        return fixturesTable;
    }



}
