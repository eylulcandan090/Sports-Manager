package Model.Football;

public class MidfielderRating implements FootballRatingStrategy{

    @Override
    public int getAverageRating(FootballPlayer player) {
        int shoot=player.getShooting();
        int pass=player.getPassing();
        int defance=player.getDefance();

        double sum=0;

        sum+=shoot*0.15;
        sum+=pass*0.5;
        sum+=defance*0.35;
        return (int) sum;
    }
}
