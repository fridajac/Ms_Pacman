package pacman.controllers;

import dataRecording.Attribute;
import dataRecording.DataTuple;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;
import java.util.HashMap;

import static dataRecording.DataSaverLoader.LoadPacManData;


public class PacManAIController extends Controller<Constants.MOVE> {

    private ArrayList<Attribute> attributes;

    public PacManAIController() {
        this.attributes = new ArrayList<>();
        String[] scoreConditions = new String[2];
        attributes.add(new Attribute("Score", scoreConditions));
    }

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        //D => the complete set of training tuples and their associated class labels.
        DataTuple[] tuples = LoadPacManData();
        //attributes => The parameter attribute list is a list of attributes describing the tuples.
        Constants.MOVE responseMove = generateTree(tuples, attributes);
        //Should return one of the valid moves contained in Move: {UP, LEFT, RIGHT, DOWN, NEUTRAL}s
        return responseMove;
    }

    private Constants.MOVE generateTree(DataTuple[] dataTuples, ArrayList<Attribute> attributes) {
        Attribute chosenAttribute = attributeSelectionMethod(dataTuples, attributes);
        //1. create Node n
        TreeNode node = new TreeNode(chosenAttribute);
        //check values in all tuples
        HashMap<DataTuple, Integer> values = new HashMap<>();
        for (DataTuple tuple : dataTuples) {
            int score = tuple.getCurrentScore();
            values.put(tuple, score);
        }

        //2. if every tuple in dataTuples has the same class C, return N as a leaf node labeled as  C.
        long uniqueValues = values.values().stream().distinct().count();
        if (uniqueValues == 1) {
            Constants.MOVE label = dataTuples[0].getDirection();
            node.makeLeafNode(label);
            return label;
        }
        //3. If the attribute list is empty, return N as a leaf node labeled with the majority class in dataTuples.
        else if (attributes.size() == 0) {
            //TODO: Find most common values! Return that label.
        } else {

            attributes.remove(chosenAttribute);
            //Call the attribute selection method on D and the attribute list, in order to choose the current attribute A:
            Attribute newChosenAttribute = attributeSelectionMethod(dataTuples, attributes);

        }
        //etc...
        return Constants.MOVE.UP;
    }

    private Attribute attributeSelectionMethod(DataTuple[] dataset, ArrayList<Attribute> attributes) {
        //TODO: Create a real attribute selection method --> function for calc max benefit. AKA calc information Gain.
        return attributes.get(1);
    }
}
