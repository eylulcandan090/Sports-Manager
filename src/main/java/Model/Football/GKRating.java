package Model.Football;

public class GKRating implements FootballRatingStrategy{

    @Override
    public int getAverageRating(FootballPlayer player) {
        return player.getGoalkeeping();
    }
}
