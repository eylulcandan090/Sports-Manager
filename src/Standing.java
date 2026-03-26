public class Standing implements Comparable<Standing> {
    private final Team team;
    private int points;
    private int played;
    private int won;
    private int drawn;
    private int lost;
    private int goalsFor;
    private int goalsAgainst;

    public Standing(Team team) {
        this.team = team;
        this.points = 0;
        this.played = 0;
        this.won = 0;
        this.drawn = 0;
        this.lost = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
    }

    public void addResult(int gf, int ga) {
        played++;
        goalsFor += gf;
        goalsAgainst += ga;
        if (gf > ga) {
            won++;
            points += 3;
        } else if (gf == ga) {
            drawn++;
            points += 1;
        } else {
            lost++;
        }
    }

    public int getGoalDifference() {
        return goalsFor - goalsAgainst;
    }

    @Override
    public int compareTo(Standing other) {
        if (this.points != other.points) {
            return Integer.compare(other.points, this.points);
        }
        return Integer.compare(other.getGoalDifference(), this.getGoalDifference());
    }

    public Team getTeam() {
        return team;
    }

    public int getPoints() {
        return points;
    }
    public int getPlayed() {
        return played;
    }

    public int getWon() {
        return won;
    }

    public int getDrawn() {
        return drawn;
    }

    public int getLost() {
        return lost;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    @Override
    public String toString() {
        return String.format(
                "%s | P:%d W:%d D:%d L:%d GF:%d GA:%d GD:%+d Pts:%d",
                team, played, won, drawn, lost, goalsFor, goalsAgainst,
                getGoalDifference(), points);
    }
}