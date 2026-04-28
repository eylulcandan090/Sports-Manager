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


    public  ArrayList<String>  printFixture(){
        List<Fixture> teams=repo.getAllFixture();

        ArrayList<String> fixturesTable=new ArrayList<>();

        for(Fixture fixture:teams){
            Team homeTeam=teamRepo.getTeamByTeamId(fixture.getHomeId());
            Team awayTeam=teamRepo.getTeamByTeamId(fixture.getAwayId());
            int week=fixture.getWeek();
            boolean isPlayed=fixture.getIsPlayed();

            System.out.println(homeTeam.getName()+" "+awayTeam.getName());

            fixturesTable.add("Week "+week+"  "+homeTeam.getName()+" vs "+awayTeam.getName());  //oynanmış maçlar için match tablosundan skorları getirelim ileride
        }

        return fixturesTable;
    }



}
