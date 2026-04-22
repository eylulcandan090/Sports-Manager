package Model;

public abstract class Player {
    private int id;
    private String name;
    private int age;
    private int injuryStatus;
    private int team_id;
    private String position;

    public Player(String name, int age, int injuryStatus, int team_id,String position) {
        this.name = name;
        this.age = age;
        this.injuryStatus = injuryStatus;
        this.team_id = team_id;
        this.position=position;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getInjuryStatus() {
        return injuryStatus;
    }

    public void setInjuryStatus(int injuryStatus) {
        this.injuryStatus = injuryStatus;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}









