package Model.Football;

import Model.*;

import java.util.ArrayList;
import java.util.List;

public class Football implements Sport {

    private static final int PLAYERS_PER_TEAM = 11;
    private static final int PLAYERS_TOTAL = 23;
    private static final int WIN_POINT = 3;
    private static final int DRAW_POINT = 1;
    private static final int LOSE_POINT = 0;
    private static final int MAX_SUBSTITUTE = 5;
    private static final int MAX_RANDOM_SCORE = 3;

    @Override
    public int getPlayersPerTeam() {
        return PLAYERS_PER_TEAM;
    }

    @Override
    public int getPlayersTotal() {
        return PLAYERS_TOTAL;
    }

    @Override
    public int teamWinPoint() {
        return WIN_POINT;
    }

    @Override
    public int teamDrawPoint() {
        return DRAW_POINT;
    }

    @Override
    public int teamLosePoint() {
        return LOSE_POINT;
    }

    @Override
    public int maxSubstitute() {
        return MAX_SUBSTITUTE;
    }

    @Override
    public int getMaxRandomScore() {
        return MAX_RANDOM_SCORE;
    }

    @Override
    public void updatePoints(Team home, Team away, int homeScore, int awayScore) {
        if (homeScore > awayScore) {
            home.addPoint(WIN_POINT);
            away.addPoint(LOSE_POINT);
        } else if (homeScore == awayScore) {
            home.addPoint(DRAW_POINT);
            away.addPoint(DRAW_POINT);
        } else {
            home.addPoint(LOSE_POINT);
            away.addPoint(WIN_POINT);
        }
    }

    @Override
    public List<TrainingType> getTrainingTypes(){
        ArrayList<TrainingType> trainingTypes=new ArrayList<>();
        trainingTypes.add(TrainingType.PASS);
        trainingTypes.add(TrainingType.SHOOT);
        trainingTypes.add(TrainingType.DEFANCE);
        return trainingTypes;
    }

    @Override
    public String getSportType() {
        return "Football";
    }

    //@Override
    public boolean isFinalState(GameState state){
        if(state.getElapsedTime()<90){ //asla uzatma olmuyor?
            return false;
        }
        else{
            return true;
        }
    }

    //@Override
    public void generateNextEvent(GameState state){ //it is supposed to return MatchEvent
        int limit = (state.getHomeTeam().teamStrength()+state.getAwayTeam().teamStrength())/2;



        for(int i=0 ; i<=90 ; i++){ //every loop is a minute
            int sayi = (int)(Math.random() * 10) + 1;
            if(sayi>=6){ //an event will occur if dice rolls higher than 5
                //buradaki sayı takımın statlarına göre belirlenmeli!!!
                //kaliteli takımlarda daha çok olay yaşanacak
                int event = (int)(Math.random() * 100) + 1;

                if(event <=35){
                    //faul
                    //KİM YAPTI?
                }

            }

        }
    }

}