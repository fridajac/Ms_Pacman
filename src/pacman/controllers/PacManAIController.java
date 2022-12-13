package pacman.controllers;

import dataRecording.AttributeList;
import dataRecording.DataTuple;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

import static dataRecording.DataSaverLoader.LoadPacManData;


public class PacManAIController extends Controller<Constants.MOVE> {

    private AttributeList attributes;

    public PacManAIController() {
        this.attributes = new AttributeList();
    }

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        //D => the complete set of training tuples and their associated class labels.
        DataTuple[] D = LoadPacManData();
        //attributes => The parameter attribute list is a list of attributes describing the tuples.

        //Should return one of the valid moves contained in Move: {UP, LEFT, RIGHT, DOWN, NEUTRAL}
        Constants.MOVE responseMove = generateTree(D, attributes);
        return responseMove;
    }

    private Constants.MOVE generateTree (DataTuple[] D, AttributeList attributes) {
        //1. create Node n
        //2. if every tuple in D has the same class C, return N as a leaf node labeled as  C.
        //3. otherwise, if the attribute list is empty, return N as a leaf node labeled with the majority class in D.
        //etc...
        return Constants.MOVE.UP;
    }

    private void attributeSelectionMethod() {
        //create attribute selection method --> function for calc max benefit. AKA calc information Gain.
    }
}
