public class Player {
    private String name;
    private String position;
    private int rating;
    private boolean injured;

    public Player(String name, String position, int rating) {
        this.name = name;
        this.position = position;
        this.rating = rating;
        this.injured = false; //at the beginning the player is not injured
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if(rating >= 0) {
            this.rating = rating;
        }
    }

    public boolean isInjured() { //bu sakatlanma sayısıysa icine ekleme yap
        return injured;
    }

    public void setInjured(boolean injured) {
        this.injured = injured;
    }

    public boolean isAvailable() {
        return !injured;
    }

    @Override
    public String toString() {
        return name + " (" + position + ") Rating: " + rating + " Injured: " + injured;
    }

}
