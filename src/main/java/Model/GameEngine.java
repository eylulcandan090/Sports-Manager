package Model;

public class GameEngine {

    public void simulate(Sport sport, Team home, Team away) { // it is supposed to return Game
        GameState state = new GameState(home, away);

        //while (!sport.isFinalState(state)) {
            //MatchEvent event = sport.generateNextEvent(state);
            //state.applyEvent(event);
        //}

    }
}
