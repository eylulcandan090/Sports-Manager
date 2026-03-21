import java.util.Random;

//For random score we have a scale system
// If a team in their home they get 1 point
//Also they can get point for their overall strength
//But  random generation is also important



public class MatchEngine {
   private Match match;
   private Random random;


    MatchEngine(Match currentMatch){
       this.match=currentMatch;
       this.random=new Random();
    }



    public void playMatch(){
        Team home=match.getHome();
        Team away=match.getAway();

        int homeScore=generateScore(home,away,true);
        int awayScore=generateScore(away,home,false);

        match.setScore(homeScore,awayScore);
        match.setPlayed(true);
        Sport sport = home.getSport();
        sport.updatePoints(home,away,homeScore,awayScore);
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
