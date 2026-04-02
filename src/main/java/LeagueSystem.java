import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeagueSystem {
    private final List<Team> teams;
    private final Fixture fixture;
    private final List<Standing> standings;
    private int currentWeek;

    public LeagueSystem(List<Team> teams) {
        this.teams = new ArrayList<>(teams);
        this.standings = new ArrayList<>();
        this.currentWeek = 1;

        for (Team team : teams) {
            standings.add(new Standing(team));
        }

        int totalWeeks = (teams.size() - 1) * 2;
        this.fixture = new Fixture(totalWeeks);
    }

    public void generateFixture() {
        int n = teams.size();
        int halfSeasonWeeks = n - 1;

        List<Team> rotation = new ArrayList<>(teams);


        for (int week = 1; week <= halfSeasonWeeks; week++) {

            int matchesPerWeek = n / 2;
            for (int i = 0; i < matchesPerWeek; i++) {
                Team home = rotation.get(i);
                Team away = rotation.get(n - 1 - i);
                fixture.addMatch(week, new Match(home, away));
            }
            rotateExceptFirst(rotation);
        }
        for (int week = 1; week <= halfSeasonWeeks; week++) {
            int mirrorWeek = week + halfSeasonWeeks;
            for (Match match : fixture.getWeek(week)) {
                fixture.addMatch(mirrorWeek, new Match(match.getAway(), match.getHome()));
            }
        }
    }

    private void rotateExceptFirst(List<Team> list) {
        if (list.size() < 2) return;
        Team last = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        list.add(1, last);
    }

    public List<Standing> getStandings() {
        Collections.sort(standings);
        return standings;
    }

    public boolean isSeasonComplete() {
        return currentWeek > fixture.getTotalWeeks();
    }

    public Team getChampion() {
        if (!isSeasonComplete()) return null;
        return getStandings().get(0).getTeam();
    }

    public void advanceWeek() {
        currentWeek++;
    }

    public int getCurrentWeek() {
        return currentWeek;
    }

    public Fixture getFixture() {
        return fixture;
    }

    private Standing findStanding(Team team) {
        for (Standing s : standings) {
            if (s.getTeam().equals(team)) return s;
        }
        return null;
    }
}