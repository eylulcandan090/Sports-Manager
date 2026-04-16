import Model.Match;
import Model.Sport;
import Model.Team;

import java.util.Random;

public class FootballMatchEngine extends MatchEngine {

    private Random random;

    public FootballMatchEngine(Match match) {
        super(match);
        this.random=new Random();
    }

    @Override
    public void playMatch() {
        Team home=super.match.getHome();
        Team away=super.match.getAway();

        int homeGoals=generateGoals(home,away,true);
        int awayGoals=generateGoals(away,home,false);

        super.match.setScore(homeGoals,awayGoals);
        //super.match.setPlayed(true);

        Sport sport=home.getSport();
        sport.updatePoints(home,away,homeGoals,awayGoals);

        System.out.println("Full Time: " +
                home.getTeamName() + " " + homeGoals + " - " +
                awayGoals + " " + away.getTeamName());
    }

    private int generateGoals(Team team,Team opponent,boolean isHome) {

        double attack=team.getOverallPlayerRating();
        double defense=opponent.getOverallPlayerRating();

        if (defense == 0) defense=1;

        double xG =attack/defense;

        if (isHome) {
            xG+=0.5;
        }

        xG+=random.nextDouble();


        if (xG < 0.2)xG=0.2;
        if (xG > 4)xG=4;

        return generateFromProbability(xG);
    }

    private int generateFromProbability(double xG) {
        int goals = 0;
        for (int i = 0; i < 5; i++) {
            if (random.nextDouble() < xG / 5) {
                goals++;
            }
        }
        return goals;
    }
}