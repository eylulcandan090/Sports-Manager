package Model.Football;

import Model.Player;
import Model.*;

public class CalculateAverageRating {
    public  int calculateAverage(FootballPlayer player){
        switch (player.getPosition()){
            case "GK":
                return new GKRating().getAverageRating(player);

            case "CB", "RB", "LB", "RWB", "LWB":
                return new DefanceRating().getAverageRating(player);

            case "CDM", "CM", "CAM", "RM", "LM":
                return new MidfielderRating().getAverageRating(player);

            case "RW", "LW", "ST", "CF":
                return new AttackerRating().getAverageRating(player);
        }
        return -1;
    }

}
