package pacman.controllers;

import dataRecording.Attribute;
import dataRecording.DataTuple;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

import static dataRecording.DataSaverLoader.LoadPacManData;

public class PacManAIController extends Controller<Constants.MOVE> {

    private ArrayList<Attribute> attributes;

    public PacManAIController() {
        this.attributes = new ArrayList<>();
        ArrayList<String> scoreConditions = new ArrayList<String>();
        scoreConditions.add("<50");
        scoreConditions.add(">=50");
        attributes.add(new Attribute("Score", scoreConditions));
    }

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        //tuples is the complete set of training tuples and their associated class labels.
        DataTuple[] tuples = LoadPacManData();
        //attributes => The parameter attribute list is a list of attributes describing the tuples.
        TreeNode node = generateTree(tuples, attributes);
        Constants.MOVE responseMove = node.getLabel();
        //Should return one of the valid moves contained in Move: {UP, LEFT, RIGHT, DOWN, NEUTRAL}s
        return responseMove;
    }

    private TreeNode generateTree(DataTuple[] dataTuples, ArrayList<Attribute> attributes) {
        //1. create Node N
        TreeNode node = new TreeNode();
        //2. if every tuple in dataTuples has the same class C, return N as a leaf node labeled as  C.
        boolean sameLabel = hasSameLabels(dataTuples);
        if (sameLabel) {
            Constants.MOVE label = dataTuples[0].getDirection();
            node.makeLeafNode(label);
            return node;
        }
        //3. If the attribute list is empty, return N as a leaf node labeled with the majority class in dataTuples.
        else if (attributes.size() == 0) {
            Constants.MOVE label = getMostCommonLabel(dataTuples);
            node.makeLeafNode(label);
            return node;
        }
         /*  Otherwise:
            1. Call the attribute selection method on D and the attribute list, in order to choose the current
            attribute A:
            2. Label N as A and remove A from the attribute list.
          */
        Attribute chosenAttribute = attributeSelectionMethod(dataTuples, attributes);
        node.setAttribute(chosenAttribute);
        attributes.remove(chosenAttribute);
        /* 3. For each value in attribute A:
        a) Separate all tuples in D so that attribute A takes the value aj, creating the subset Dj.*/
        ArrayList<String> conditions = chosenAttribute.getConditions();
        for (String condition : conditions) {
            // a) Separate all tuples in D so that attribute A takes the value aj, creating the subset Dj.
            DataTuple[] subsetD = getSubsetBasedOnAttributeValue(condition);
            // b) If Dj is empty, add a child node to N labeled with the majority class in D.
            TreeNode newNode;
            if (subsetD.length == 0) {
                newNode = new TreeNode();
                Constants.MOVE label = getMostCommonLabel(dataTuples);
                newNode.makeLeafNode(label);
            }
            //c) Otherwise, add the resulting node from calling Generate_Tree(Dj, attribute) as a child node to N.
            else {
                newNode = generateTree(subsetD, attributes);
            }
            node.addChildNode(newNode);
        }
        //4. Return N.
        return node;
    }

    private DataTuple[] getSubsetBasedOnAttributeValue(String condition) {
     /*  Create a helper method that subdivide (partition)
                datasets based on attributes value → Test this good!*/
        return new DataTuple[3];
    }

    private Constants.MOVE getMostCommonLabel(DataTuple[] dataTuples) {
        return null;
    }

    private boolean hasSameLabels(DataTuple[] dataTuples) {
        return true;
    }

    private Attribute attributeSelectionMethod(DataTuple[] dataset, ArrayList<Attribute> attribute) {
        //TODO: Create a real attribute selection method --> function for calc max benefit. AKA calc information Gain.
        return attributes.get(1);
    }
}
