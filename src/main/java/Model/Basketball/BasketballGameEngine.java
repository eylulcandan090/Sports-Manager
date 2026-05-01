package Model.Basketball;

import Model.Team;
import java.util.*;

public class BasketballGameEngine {

    private static final Random RNG = new Random();

    public MatchSummary simulateMatch(Team home, List<BasketballPlayer> homePlayers,
                                      Team away, List<BasketballPlayer> awayPlayers) {
        Map<BasketballPlayer, PlayerStat> stats = new LinkedHashMap<>();
        for (BasketballPlayer p : homePlayers) stats.put(p, new PlayerStat(p));
        for (BasketballPlayer p : awayPlayers) stats.put(p, new PlayerStat(p));

        int[] scores = {0, 0};
        runPossessions(homePlayers, awayPlayers, stats, scores, 100);
        while (scores[0] == scores[1]) {
            runPossessions(homePlayers, awayPlayers, stats, scores, 10);
        }

        return new MatchSummary(home, scores[0], away, scores[1], new ArrayList<>(stats.values()));
    }

    private void runPossessions(List<BasketballPlayer> home, List<BasketballPlayer> away,
                                Map<BasketballPlayer, PlayerStat> stats, int[] scores, int possessions) {
        int homeOff = avgOffense(home);
        int homeDef = avgDefense(home);
        int awayOff = avgOffense(away);
        int awayDef = avgDefense(away);

        for (int i = 0; i < possessions; i++) {
            boolean homeAttacks = (i % 2 == 0);
            List<BasketballPlayer> attackers = homeAttacks ? home : away;
            int atkOff = homeAttacks ? homeOff : awayOff;
            int defDef = homeAttacks ? awayDef : homeDef;

            BasketballPlayer player = randomActive(attackers, stats);
            if (player == null) continue;

            PlayerStat stat = stats.get(player);
            int skill = (player.getShooting() + player.getFinishing()) / 2;

            if (skill + atkOff + RNG.nextInt(21) > defDef + RNG.nextInt(21)) {
                int pts = RNG.nextInt(10) < 3 ? 3 : 2;
                stat.addPoints(pts);
                scores[homeAttacks ? 0 : 1] += pts;
            }

            if (RNG.nextInt(100) < 5) stat.addFoul();
            if (RNG.nextInt(100) < 1) player.setInjuryStatus(1);
        }
    }

    private BasketballPlayer randomActive(List<BasketballPlayer> players,
                                          Map<BasketballPlayer, PlayerStat> stats) {
        List<BasketballPlayer> active = new ArrayList<>();
        for (BasketballPlayer p : players) {
            PlayerStat st = stats.get(p);
            if (st != null && st.isActive()) active.add(p);
        }
        return active.isEmpty() ? null : active.get(RNG.nextInt(active.size()));
    }

    private int avgOffense(List<BasketballPlayer> players) {
        if (players.isEmpty()) return 50;
        int sum = 0;
        for (BasketballPlayer p : players) sum += (p.getShooting() + p.getFinishing()) / 2;
        return sum / players.size();
    }

    private int avgDefense(List<BasketballPlayer> players) {
        if (players.isEmpty()) return 50;
        int sum = 0;
        for (BasketballPlayer p : players) sum += p.getDefense();
        return sum / players.size();
    }
}