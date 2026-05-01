package Model;

public class GameEngine {

    public GameState simulate (Sport sport, Team home, Team away) { // it is supposed to return Game
        GameState state = new GameState(home, away);

        while (!sport.isFinalState(state)) {
            MatchEvent event = sport.generateNextEvent(state);
            if(event != null) {
                state.applyEvent(event);
                }
                state.incrementTime();
        }
        sport.updatePoints(
                home,
                away,
                state.getHomeScore(),
                state.getAwayScore()
        );
        return state;
    }
}
