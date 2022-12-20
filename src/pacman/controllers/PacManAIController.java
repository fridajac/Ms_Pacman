package pacman.controllers;

import dataRecording.DataTuple;
import pacman.game.Constants;
import pacman.game.Game;

public class PacManAIController extends Controller<Constants.MOVE> {

    private AIController aiController;

    public PacManAIController() {
        this.aiController = new AIController();
    }

    @Override
    /**
     * Return one of the valid moves contained in Move: {UP, LEFT, RIGHT, DOWN, NEUTRAL}
     * Uses the decision tree as AI controller for Pacman
     */
    public Constants.MOVE getMove(Game game, long timeDue) {
        return aiController.classify(new DataTuple(game.getScore()));
    }
}
