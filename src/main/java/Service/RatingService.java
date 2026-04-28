package Service;

import Model.Football.FootballPlayer;
import Model.Football.FootballRatingStrategy;

public class RatingService {
    FootballRatingStrategy ratingStartegy;

    public RatingService(FootballRatingStrategy startegy){
        this.ratingStartegy=startegy;
    }

    public int getRating(FootballPlayer player){
        return this.ratingStartegy.getAverageRating(player);
    }

}
