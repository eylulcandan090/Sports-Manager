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
    private String lastGoalEvent = "";
    private String lastCardEvent = "";
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
        lastGoalEvent = "";
        lastCardEvent = "";
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
        int homeAdd, awayAdd;
        if (maxPeriod == 4) { // basketball: ~18-25 pts per quarter
            homeAdd = (int)(Math.random() * 8 * factor) + 18;
            awayAdd = (int)(Math.random() * 8) + 18;
        } else { // football: 0-2 goals per half
            homeAdd = (int)(Math.random() * 3 * factor);
            awayAdd = (int)(Math.random() * 3);
        }

        currentGame.setScore(
                currentGame.getHomeScore() + homeAdd,
                currentGame.getAwayScore() + awayAdd
        );

        // Goal event (football only — basketball always scores so no individual events)
        lastGoalEvent = "";
        if (maxPeriod == 2) {
            boolean isUserHome = currentFixture != null && currentFixture.getHomeId() == getGameTeamId();
            StringBuilder sb = new StringBuilder();
            if (homeAdd > 0) {
                if (isUserHome && currentSquad != null && !currentSquad.isEmpty()) {
                    Player s = currentSquad.get((int)(Math.random() * currentSquad.size()));
                    sb.append("⚽  ").append(s.getName()).append(" scored!");
                } else {
                    sb.append("⚽  ").append(currentGame.getHomeTeam().getName()).append(" scored!");
                }
            }
            if (awayAdd > 0) {
                if (sb.length() > 0) sb.append("  •  ");
                if (!isUserHome && currentSquad != null && !currentSquad.isEmpty()) {
                    Player s = currentSquad.get((int)(Math.random() * currentSquad.size()));
                    sb.append("⚽  ").append(s.getName()).append(" scored!");
                } else {
                    sb.append("⚽  ").append(currentGame.getAwayTeam().getName()).append(" scored!");
                }
            }
            lastGoalEvent = sb.toString();
        }

        lastCardEvent = "";
        if (currentSquad != null && !currentSquad.isEmpty() && Math.random() < 0.3) {
            int idx = (int)(Math.random() * currentSquad.size());
            Player cardPlayer = currentSquad.get(idx);
            if (Math.random() < 0.3 && currentSquad.size() > 1) { // red card — only if squad has spare players
                lastCardEvent = cardPlayer.getName() + " received a red card 🟥";
                currentSquad.remove(idx);
            } else {
                lastCardEvent = cardPlayer.getName() + " received a yellow card 🟨";
            }
        }

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

    public String getLastGoalEvent() {
        return lastGoalEvent;
    }

    public String getLastCardEvent() {
        return lastCardEvent;
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

        // Basketball cannot end in a draw — break the tie with overtime
        if (maxPeriod == 4 && homeScore == awayScore) {
            if (Math.random() < 0.5) homeScore += (int)(Math.random() * 2) + 1;
            else                     awayScore += (int)(Math.random() * 2) + 1;
            currentGame.setScore(homeScore, awayScore); // update display
        }

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
                int hScore, aScore;
                if (maxPeriod == 4) { // basketball: realistic full-game score
                    hScore = (int)(Math.random() * 30) + 70;
                    aScore = (int)(Math.random() * 30) + 70;
                    if (hScore == aScore) { if (Math.random() < 0.5) hScore++; else aScore++; }
                } else { // football
                    hScore = (int)(Math.random() * 4);
                    aScore = (int)(Math.random() * 4);
                }
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

    public String getHomeTeamName() {
        return currentGame != null ? currentGame.getHomeTeam().getName() : "Home";
    }

    public String getAwayTeamName() {
        return currentGame != null ? currentGame.getAwayTeam().getName() : "Away";
    }

    public int getMaxPeriod() {
        return maxPeriod;
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




