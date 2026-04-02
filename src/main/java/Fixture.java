import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fixture {

    private final Map<Integer, List<Match>> weeklyMatches;
    private final int totalWeeks;

    public Fixture(int totalWeeks) { //constructor
        this.totalWeeks = totalWeeks;
        this.weeklyMatches = new HashMap<>();
    }

    //methods
    public void addMatch(int week, Match match) {
        weeklyMatches.computeIfAbsent(week, k -> new ArrayList<>()).add(match);
    }

    public List<Match> getWeek(int week) {
        return weeklyMatches.getOrDefault(week, new ArrayList<>());
    }

    public int getTotalWeeks() {
        return totalWeeks;
    }

    public Map<Integer, List<Match>> getWeeklyMatches(){
        return weeklyMatches;
    }
}