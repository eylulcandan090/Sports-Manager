package Model.Football;

public class AttackerRating implements FootballRatingStrategy{

    @Override
    public int getAverageRating(FootballPlayer player) {
        int shoot=player.getShooting();
        int pass=player.getPassing();
        int defance=player.getDefance();

        double sum=0;

        sum+=shoot*0.75;
        sum+=pass*0.20;
        sum+=defance*0.05;

        return (int) sum;
    }
}
