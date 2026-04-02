public class Coach {
    private String name;
    private String specialization;
    private int rating;

    public Coach(String name, String specialization, int rating) {
        this.name = name;
        this.specialization = specialization;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getRating() {
        return rating;
    }

    public void trainPlayer(Player player) {
        if (player == null) return;

        int boost = rating / 20;
        if (boost < 1) boost = 1;

        int current = player.getAttribute(specialization);
        player.setAttribute(specialization, current + boost);
    }

    @Override
    public String toString() {
        return name + " | " + specialization + " | Rating: " + rating;
    }
}