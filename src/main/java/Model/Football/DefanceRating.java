package Model.Football;

public class DefanceRating implements FootballRatingStrategy{

    @Override
    public int getAverageRating(FootballPlayer player) {
        int defance=player.getDefance();
        int shoot=player.getShooting();
        int pass=player.getPassing();

        double sum=0;
        sum+=defance*0.7;
        sum+=shoot*0.1;
        sum+=pass*0.2;
        return (int) sum;
    }
}
