package Service;
import Model.Fixture;
import Model.GameState;
import Model.Player;
import Model.Team;
import Repository.GameRepo;
import Repository.TeamRepo;
import UI.AlertUtility;

import java.util.ArrayList;
import java.util.List;


public class GameService {
    private GameRepo repo;
    private FixtureService fixtureService;
    private TeamRepo teamRepo;
    private GameState currentGame;
    private ArrayList<Player> currentSquad;
    private int maxPeriod;
    private ArrayList<Player> currentBench;
    private Fixture currentFixture;
    private String injuryMessage = "";
    private Player injuredPlayer;
    private int injuryCount = 0;
    private int maxInjuries = 2;
    private int subsCount = 0;
    private int maxSubs = 3;
    private String tactic = "Balanced";

    public GameService(GameRepo repo, FixtureService fixtureService, TeamRepo teamRepo) {
        this.repo = repo;
        this.fixtureService = fixtureService;
        this.teamRepo = teamRepo;

    }

    public void resetGame() {
        repo.resetSeason();
    }

    public boolean hasGame() {
        return repo.isGameStarted();
    }

    public int getGameTeamId() {
        return repo.getGameTeamId();
    }

    public void startMatch(ArrayList<Player> squad, ArrayList<Player> bench) {
        List<Fixture> fixtures = fixtureService.getFixtures();
        currentFixture = null;

        for (Fixture f : fixtures) {
            if (!f.getIsPlayed() &&
                    (f.getHomeId() == getGameTeamId() || f.getAwayId() == getGameTeamId())) {
                currentFixture = f;
                break;
            }
        }
        if (currentFixture == null) {
            AlertUtility.showWarning("Season Finished", "No more fixtures left!");
            return;
        }
        this.currentSquad = squad;
        this.currentBench = bench;

        Team homeTeam = teamRepo.getTeamByTeamId(currentFixture.getHomeId());
        Team awayTeam = teamRepo.getTeamByTeamId(currentFixture.getAwayId());

        currentGame = new GameState(homeTeam, awayTeam);
        if (squad.size() > 5) {
            maxPeriod = 2;
        } else {
            maxPeriod = 4;
        }
        playNextPeriod(squad);
    }

    public void playNextPeriod(ArrayList<Player> squad) {
        if (currentGame == null) return;

        currentGame.nextPeriod();
        double factor = 1.0;

        if (tactic.equals("Attacking")) {
            factor = 1.5;
        } else if (tactic.equals("Defensive")) {
            factor = 0.7;
        }
        int homeAdd = (int) (Math.random() * 3 * factor);
        int awayAdd = (int) (Math.random() * 3);

        currentGame.setScore(
                currentGame.getHomeScore() + homeAdd,
                currentGame.getAwayScore() + awayAdd
        );
        injuryMessage = "";
        double injuryChance = 0.2;
        if (tactic.equals("Attacking")) {
            injuryChance = 0.3;
        } else if (tactic.equals("Defensive")) {
            injuryChance = 0.1;
        }
        if (injuryCount < maxInjuries && Math.random() < injuryChance) {
            if (currentSquad != null && !currentSquad.isEmpty()) {
                int index = (int) (Math.random() * currentSquad.size());
                Player injuredPlayer = currentSquad.get(index);

                if (injuredPlayer.getInjuryStatus() == 0) {
                    injuredPlayer.setInjuryStatus(1);
                    this.injuredPlayer = injuredPlayer;
                    injuryCount++;

                    injuryMessage = injuredPlayer.getName() + " got injured! ";
                }
            }
        }

    }

    public boolean isMatchFinished() {
        return currentGame.getCurrentPeriod() >= maxPeriod;
    }

    public int getHomeScore() {
        return currentGame.getHomeScore();
    }

    public int getAwayScore() {
        return currentGame.getAwayScore();
    }

    public int getCurrentPeriod() {
        return currentGame.getCurrentPeriod();
    }

    public ArrayList<Player> getCurrentSquad() {
        return currentSquad;
    }

    public ArrayList<Player> getCurrentBench() {
        return currentBench;
    }

    public String getInjuryMessage() {
        return injuryMessage;
    }

    public int getSubsCount() {
        return subsCount;
    }

    public int getMaxSubs() {
        return maxSubs;
    }

    public String getTactic() {
        return tactic;
    }

    public void setTactic(String tactic) {
        this.tactic = tactic;
    }

    public Player getInjuredPlayer() {
        return injuredPlayer;
    }

    public boolean canSubs() {
        return subsCount < maxSubs;
    }

    public void increaseSubsCount() {
        subsCount++;
    }

    public String getMatchTitle() {
        return currentGame.getHomeTeam().getName() + " vs " + currentGame.getAwayTeam().getName();
    }

    public int getCurrentWeek() {
        return fixtureService.getCurrentWeek();
    }

    public void finishMatch() {
        if (currentFixture == null || currentGame == null) return;

        // 1. Save and update points for user's match
        int homeScore = currentGame.getHomeScore();
        int awayScore = currentGame.getAwayScore();

        repo.saveMatch(
                currentFixture.getHomeId(),
                currentFixture.getAwayId(),
                homeScore,
                awayScore
        );
        applyPoints(currentFixture.getHomeId(), currentFixture.getAwayId(), homeScore, awayScore);
        fixtureService.markAsPlayed(currentFixture);

        // 2. Simulate other matches this week, save results and update their points
        for (Fixture fixture : fixtureService.getFixtures()) {
            boolean sameWeek    = fixture.getWeek() == currentFixture.getWeek();
            boolean notPlayed   = !fixture.getIsPlayed();
            boolean isUserMatch = fixture.getHomeId() == getGameTeamId() ||
                                  fixture.getAwayId() == getGameTeamId();

            if (sameWeek && notPlayed && !isUserMatch) {
                int hScore = (int) (Math.random() * 4);
                int aScore = (int) (Math.random() * 4);

                repo.saveMatch(fixture.getHomeId(), fixture.getAwayId(), hScore, aScore);
                applyPoints(fixture.getHomeId(), fixture.getAwayId(), hScore, aScore);
                fixtureService.markAsPlayed(fixture);
            }
        }

        // Reset match state
        injuryCount = 0;
        subsCount   = 0;
        tactic      = "Balanced";
    }

    private void applyPoints(int homeId, int awayId, int homeScore, int awayScore) {
        if (homeScore > awayScore) {
            teamRepo.addPoints(homeId, 3);
        } else if (awayScore > homeScore) {
            teamRepo.addPoints(awayId, 3);
        } else {
            teamRepo.addPoints(homeId, 1);
            teamRepo.addPoints(awayId, 1);
        }
    }

    public String getMatchResult() {
        if (currentFixture == null || currentGame == null) return "";
        boolean isHome = currentFixture.getHomeId() == getGameTeamId();
        int myScore  = isHome ? currentGame.getHomeScore() : currentGame.getAwayScore();
        int oppScore = isHome ? currentGame.getAwayScore() : currentGame.getHomeScore();
        if (myScore > oppScore) return "You Won! 🏆";
        if (myScore < oppScore) return "You Lost";
        return "Draw";
    }
}




