package Model.Basketball;

public class CenterRating implements BasketballRatingStrategy{

    @Override
    public int getAverageRating(BasketballPlayer player) {
        int shooting=player.getShooting();
        int dribbling=player.getDribbling();
        int passing=player.getPassing();
        int finishing=player.getFinishing();
        int defense=player.getDefense();
        int steal=player.getSteal();
        int block=player.getBlock();

        double rating=shooting*0.30+
                finishing*0.25+
                defense*0.20+
                block*0.10+
                dribbling*0.05+
                passing*0.05+
                steal*0.05;

        return (int)rating;
    }
}
