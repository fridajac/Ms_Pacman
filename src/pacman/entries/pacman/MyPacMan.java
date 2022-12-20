package pacman.entries.pacman;

import pacman.controllers.AIController;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE>
{
	private MOVE myMove=MOVE.NEUTRAL;
	private AIController aiController;

	public MyPacMan() {
		this.aiController = new AIController();
	}
	
	public MOVE getMove(Game game, long timeDue) {
		myMove = MOVE.RIGHT;
		//return aiController.classify(new DataTuple(game.getScore()));
		return myMove;
	}
}