package Model.Basketball;

import Model.*;

import java.util.List;

public class Basketball implements Sport {

    private static final int PLAYERS_PER_TEAM = 5;
    private static final int PLAYERS_TOTAL = 12;
    private static final int WIN_POINT = 2;
    private static final int DRAW_POINT = 0;
    private static final int LOSE_POINT = 0;
    private static final int MAX_SUBSTITUTE = 12;
    private static final int MAX_RANDOM_SCORE = 120;

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
    public List<TrainingType> getTrainingTypes() {
        return List.of(TrainingType.SHOOT, TrainingType.PASS, TrainingType.DEFANCE, TrainingType.BLOCKING, TrainingType.DRIBBLING, TrainingType.STEALING);
    }

    @Override
    public String getSportType() {
        return "Basketball";
    }

    @Override
    public void updatePoints(Team home, Team away, int homeScore, int awayScore) {

        if (homeScore > awayScore) {
            home.addPoint(WIN_POINT);
        } else {
            away.addPoint(WIN_POINT);
        }
    }

    //@Override
    public boolean isFinalState(GameState state) {
        if (state.getAwayScore() == state.getHomeScore()) {
            return false; //+5 dk verilmeli ???
        }
        return true;
    }

    @Override
    public MatchEvent generateNextEvent(GameState state) {
        int chance = (int)(Math.random() * 100);

        if (chance < 70) {
            return new MatchEvent(
                    "BASKET",
                    null,
                    state.getHomeTeam(),
                    state.getElapsedTime()
            );
        }

        if (chance < 90) {
            return new MatchEvent(
                    "FOUL",
                    null,
                    state.getAwayTeam(),
                    state.getElapsedTime()
            );
        }

        return null;
    }
    }
