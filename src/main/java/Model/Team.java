package Model;

public class Team {
    private int id;
    private String name;
    private int point;

    public Team(String name) {
        this.name = name;
        this.point=0;
    }

    public Team(String name,int point){
        this.name=name;
        this.point=point;
    }

    public Team(int id, String name, int point) {
        this.id = id;
        this.name = name;
        this.point = point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void addPoint(int point){
        this.point+=point;
    }

    @Override
    public String toString(){
        return this.id+")"+" "+this.name+" "+this.point;
    }

}
