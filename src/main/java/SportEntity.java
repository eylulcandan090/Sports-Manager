package Model;

public class SportEntity {
    private int id;
    private String sport;

    public SportEntity(String sport) {
        this.sport = sport;
    }

    public int getId() {
        return id;
    }


    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
}
