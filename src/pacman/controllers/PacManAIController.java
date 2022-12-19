package pacman.controllers;

import pacman.game.Constants;
import pacman.game.Game;

public class PacManAIController extends Controller<Constants.MOVE> {

    @Override
    //Should return one of the valid moves contained in Move: {UP, LEFT, RIGHT, DOWN, NEUTRAL}s
    public Constants.MOVE getMove(Game game, long timeDue) {
        /*
        TODO: We should use the decision tree as the AI controller for Pacman.
         And we should traverse the tree using the game state!
         The node we get back will tell us where to go! --> with node.getLabel().
         */
        return Constants.MOVE.DOWN;
    }
}
