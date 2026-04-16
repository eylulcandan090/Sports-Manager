import Model.Match;
import Model.Sport;
import Model.Team;

import java.util.Random;

//For random score we have a scale system
// If a team in their home they get 1 point
//Also they can get point for their overall strength
//But  random generation is also important



public abstract class MatchEngine {
   protected Match match;
   private Random random;


    MatchEngine(Match currentMatch){
       this.match=currentMatch;
       this.random=new Random();
    }


    public void playMatch(){
        Team home=match.getHome();
        Team away=match.getAway();

        int homeFirst=generateScore(home,away,true);
        int awayFirst=generateScore(away,home,false);

        match.setScore(homeFirst,awayFirst);

        System.out.println("1st Half: " +
                home.getTeamName() + " " + match.getHomeScore() + " - " +
                match.getAwayScore() + " " + away.getTeamName());

        int homeSecond=generateScore(home,away,true);
        int awaySecond=generateScore(away,home,false);

        match.addScore(homeSecond,awaySecond);
        //match.setPlayed(true);

        Sport sport=home.getSport();
        sport.updatePoints(home,away,match.getHomeScore(),match.getAwayScore());

        System.out.println("Full Time: " +
                home.getTeamName() + " " + match.getHomeScore() + " - " +
                match.getAwayScore() + " " + away.getTeamName());
    }


    private int generateScore(Team team,Team opponent,boolean isHome){
        double scale=0;

        if(isHome){
            scale++;
        }

        double team_overall=team.getOverallPlayerRating();
        double opponent_overall=opponent.getOverallPlayerRating();

        if(opponent_overall==0) return 0;

        if(team_overall>opponent_overall){
            scale+=(team_overall/opponent_overall);
        }

        scale+=random.nextInt(team.getSport().getMaxRandomScore());

        if(scale<0){
            return 0;
        }

        return (int)Math.round(scale);
    }
}
