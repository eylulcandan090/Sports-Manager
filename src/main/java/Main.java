import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Sport football = SportFactory.createSport("football");

        List<Team> teams = new ArrayList<>();
        String[][] teamData = {
            {"Manchester City",  "Pep Guardiola",   "Etihad Stadium"},
            {"Arsenal",          "Mikel Arteta",    "Emirates Stadium"},
            {"Liverpool",        "Arne Slot",        "Anfield"},
            {"Chelsea",          "Enzo Maresca",    "Stamford Bridge"},
            {"Tottenham",        "Ange Postecoglou","Tottenham Hotspur Stadium"},
            {"Newcastle",        "Eddie Howe",      "St. James' Park"},
        };

        for (String[] data : teamData) {
            FootballTeam team = new FootballTeam(football, data[0], data[1], data[2]);
            fillTeamWithPlayers(team, data[0]);
            teams.add(team);
        }

        GameManager manager = new GameManager();
        manager.startNewGame(football, teams, "Premier League");

        System.out.println("=== Premier League Season Started ===");
        System.out.println("Teams: " + teams.size());
        System.out.println("Total weeks: " + (teams.size() - 1) * 2);
        System.out.println();

        while (!manager.isSeasonOver()) {
            int week = manager.getCurrentWeek();
            System.out.println("--- Week " + week + " ---");
            manager.advanceWeek();
            printStandings(manager.getLeagueSystem().getStandings());
            System.out.println();
        }

        Team champion = manager.getLeagueSystem().getChampion();
        System.out.println("=== SEASON OVER ===");
        System.out.println("Champion: " + champion.getTeamName());
    }

    private static void fillTeamWithPlayers(FootballTeam team, String teamName) {
        Random rng = new Random(teamName.hashCode());
        String[] positions = {"shooting", "passing", "defense", "fitness", "attack"};

        for (int i = 1; i <= 11; i++) {
            final int playerIndex = i;
            final String name = teamName + " Player " + playerIndex;
            Player player = new Player() {};
            player.setName(name);
            player.setAge(18 + rng.nextInt(17));
            for (String attr : positions) {
                player.setAttribute(attr, 50 + rng.nextInt(51));
            }
            team.addPlayer(player);
        }
    }

    private static void printStandings(List<Standing> standings) {
        System.out.printf("%-30s %3s %3s %3s %3s %3s %3s %4s %3s%n",
                "Team", "P", "W", "D", "L", "GF", "GA", "GD", "Pts");
        System.out.println("-".repeat(65));
        for (Standing s : standings) {
            System.out.printf("%-30s %3d %3d %3d %3d %3d %3d %+4d %3d%n",
                    s.getTeam().getTeamName(),
                    s.getPlayed(), s.getWon(), s.getDrawn(), s.getLost(),
                    s.getGoalsFor(), s.getGoalsAgainst(),
                    s.getGoalDifference(), s.getPoints());
        }
    }
}
