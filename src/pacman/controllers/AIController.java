package pacman.controllers;

import dataRecording.Attribute;
import dataRecording.DataTuple;
import pacman.game.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static dataRecording.DataSaverLoader.LoadPacManData;

public class AIController {

    private ArrayList<Attribute> attributes;

    public AIController() {
        DataTuple[] tuples = LoadPacManData();

        /*Divide data set in: 80% training data. This is used to create decision tree.
         */
        DataTuple[] trainingData = LoadPacManData();

        //Prepare the attributes. TODO: How to represent attributes and conditions in Java in the best way?
        this.attributes = new ArrayList<>();
        ArrayList<String> scoreConditions = new ArrayList<>();
        scoreConditions.add("over 10");
        scoreConditions.add("between 5 and 10");
        scoreConditions.add("between 0 and 5");
        attributes.add(new Attribute("Distance to pellet", scoreConditions));

        TreeNode node = generateTree(trainingData, attributes);
        //TODO: What does this node tell us? Should we use function node.getLabel() --> which gives us that direction?

        /*and then 20% test data
        (Used to test the final accuracy of the tree
        DataTuple[] testingData =
        */
        //TODO: The accuracy is numberOfCorrectPredictions/numberOfTotalPredictions. How to calculate this?
        //TODO: Calculate the final accuracy against the test dataset
    }

    /**
    Code below is based on the pseudocode in the presentation PDF and used the ID3 algorithm to create a decision tree.
     */
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
            DataTuple[] subsetD = getSubsetBasedOnAttributeValue(dataTuples, condition);
            // b) If Dj is empty, add a child node to N labeled with the majority class in D.
            TreeNode newNode;
            if (subsetD.length == 0) {
                newNode = new TreeNode();
                Constants.MOVE label = getMostCommonLabel(dataTuples);
                newNode.makeLeafNode(label);
                return newNode;
            }
            //c) Otherwise, add the resulting node from calling Generate_Tree(Dj, attribute) as a child node to N.
            else {
                newNode = generateTree(subsetD, attributes);
                node.addChildNode(newNode);
            }
        }
        //4. Return N.
        return node;
    }

    /**
     * TODO: Create a helper method that subdivide (partition) datasets based on attributes value.
     * So this method should find all the tuples that match the condition and then return a subset with all the tuples that do.
     */
    private DataTuple[] getSubsetBasedOnAttributeValue(DataTuple[] tuples, String condition) {
        return null;
    }

    /**
    This method should search through all tuples and find the most common label (direction)
    */
    private Constants.MOVE getMostCommonLabel(DataTuple[] dataTuples) {
        HashMap<Constants.MOVE, Integer> labels = new HashMap<>();
        for (DataTuple tuple : dataTuples) {
            Constants.MOVE label = tuple.getDirection();
            if (!labels.containsKey(label)) {
                labels.put(label, 1);
            } else {
                int count = labels.get(label);
                labels.put(label, count + 1);
            }
        }
        return labels.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    /**
    This method should search true or false depending on if all tuples contains the same label
    */
    private boolean hasSameLabels(DataTuple[] dataTuples) {
        Constants.MOVE firstDirection = dataTuples[0].getDirection();
        for (int i = 1; i < dataTuples.length; i++) {
            if (dataTuples[0].getDirection() != firstDirection) return false;
        }
        return true;
    }

    /**
     TODO: Create a real attribute selection method --> function to calculate information Gain.
     OBS: Find one on internet to us.
     */
    private Attribute attributeSelectionMethod(DataTuple[] dataset, ArrayList<Attribute> attribute) {
        return attributes.get(1);
    }
}
