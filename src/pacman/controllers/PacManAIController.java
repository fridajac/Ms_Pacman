package pacman.controllers;

import pacman.game.Constants;
import pacman.game.Game;


public class PacManAIController extends Controller<Constants.MOVE> {

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        //Gets the game state as input and maximum time allowed for response (5000ms).

        //Should return one of the valid moves contained in Move: {UP, LEFT, RIGHT, DOWN, NEUTRAL}
        return null;
    }
}
