package Model;

import java.util.List;

public interface Sport {

    int getPlayersPerTeam(); //starting team
    int getPlayersTotal();   // starting+substitute
    int teamWinPoint();
    int teamDrawPoint();
    int teamLosePoint();
    int maxSubstitute();
    void updatePoints(Team home, Team away, int homeScore, int awayScore);
    int getMaxRandomScore();
    List<TrainingType> getTrainingTypes();














}
