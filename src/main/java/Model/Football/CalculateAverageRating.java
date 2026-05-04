package Model.Football;

import Model.Basketball.BasketballPlayer;
import Model.Basketball.CenterRating;
import Model.Basketball.ForwardRating;
import Model.Basketball.GuardRating;
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


    public int calculateAverage(BasketballPlayer player){
        switch (player.getPosition()){
            case "PG":
            case "SG":
                return new GuardRating().getAverageRating(player);

            case "SF":
            case "PF":
                return new ForwardRating().getAverageRating(player);

            case "C":
                return new CenterRating().getAverageRating(player);
        }
        return -1;
    }



}
