public class Football implements Sport {

    private static final int PLAYERS_PER_TEAM = 11;
    private static final int PLAYERS_TOTAL = 23;
    private static final int WIN_POINT = 3;
    private static final int DRAW_POINT = 1;
    private static final int LOSE_POINT = 0;
    private static final int MAX_SUBSTITUTE = 5;
    private static final int MAX_RANDOM_SCORE = 3;

    @Override
    public int getPlayersPerTeam() {
        return PLAYERS_PER_TEAM;
    }

    @Override
    public int getPlayersTotal() {
        return PLAYERS_TOTAL;
    }

    @Override
    public int teamWinPoint() {
        return WIN_POINT;
    }

    @Override
    public int teamDrawPoint() {
        return DRAW_POINT;
    }

    @Override
    public int teamLosePoint() {
        return LOSE_POINT;
    }

    @Override
    public int maxSubstitute() {
        return MAX_SUBSTITUTE;
    }

    @Override
    public int getMaxRandomScore() {
        return MAX_RANDOM_SCORE;
    }

    @Override
    public void updatePoints(Team home, Team away, int homeScore, int awayScore) {
        if (homeScore > awayScore) {
            home.addPoint(WIN_POINT);
            away.addPoint(LOSE_POINT);
        } else if (homeScore == awayScore) {
            home.addPoint(DRAW_POINT);
            away.addPoint(DRAW_POINT);
        } else {
            home.addPoint(LOSE_POINT);
            away.addPoint(WIN_POINT);
        }
    }
}