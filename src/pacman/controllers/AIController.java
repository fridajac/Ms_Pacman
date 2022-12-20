package pacman.controllers;

import dataRecording.Attribute;
import dataRecording.Condition;
import dataRecording.DataTuple;
import dataRecording.Operator;
import pacman.game.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static dataRecording.DataSaverLoader.LoadPacManData;

public class AIController {

    private final ArrayList<Attribute> attributes;

    public AIController() {
        DataTuple[] data = LoadPacManData();
        //Divide data set in: 80% training data. This is used to create decision tree.
        ArrayList<DataTuple> tuples = new ArrayList<>(Arrays.asList(data));
        int calcPercentage = (int) (data.length * 0.8);

        ArrayList<DataTuple> trainingData = new ArrayList<>();
        ArrayList<DataTuple> testingData = new ArrayList<>();

        for (int i = 0; i < tuples.size(); i++) {
            if (i <= calcPercentage) trainingData.add(tuples.get(i));
            else testingData.add(tuples.get(i));
        }

        this.attributes = createAttributes();

        //generate tree
        TreeNode rootNode = generateTree(trainingData, attributes);
        rootNode.printSubTree();

        // use the training data to test accuracy
        printAccuracy(trainingData, "training");

        // use the test data to test final accuracy
        printAccuracy(testingData, "testing");
    }

    /**
    Method that creates attributes with different conditions
     */
    private ArrayList<Attribute> createAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        Condition condition1 = new Condition(Operator.LESS_THAN, 500);
        Condition condition2 = new Condition(Operator.MORE_THAN, 500);
        ArrayList<Condition> allConditions = new ArrayList<>();
        allConditions.add(condition1);
        allConditions.add(condition2);
        attributes.add(new Attribute("Score", allConditions));
        return attributes;
    }

    /**
    Method to print accuracy from either training och testing dataset
     */
    private void printAccuracy(ArrayList<DataTuple> data, String typeOfDataset) {
        int numberOfCorrectPrediction = 0;
        for (DataTuple sample : data) {
            Constants.MOVE move = classify(sample);
            if (move == sample.getDirectionChosen()) {
                numberOfCorrectPrediction++;
            }
        }
        double accuracy = numberOfCorrectPrediction / data.size();
        System.out.println("Accuracy from " + typeOfDataset + " dataset: " + accuracy);
    }

    /**
     * Method to use decision tree to classify a sample
     */
    public static Constants.MOVE classify(DataTuple sample) {
        //TODO We should traverse the tree using the game state from sample!
        return null;
    }

    /**
     * Code below is based on the pseudocode in the presentation PDF and used the ID3 algorithm to create a decision tree.
     */
    private TreeNode generateTree(ArrayList<DataTuple> dataTuples, ArrayList<Attribute> attributes) {
        //1. create Node N
        TreeNode node = new TreeNode();
        //2. if every tuple in dataTuples has the same class C, return N as a leaf node labeled as  C.
        boolean sameLabel = hasSameLabels(dataTuples);
        if (sameLabel) {
            node.makeLeafNode(dataTuples.get(0).getDirectionChosen());
            return node;
        }
        //3. If the attribute list is empty, return N as a leaf node labeled with the majority class in dataTuples.
        else if (attributes.size() == 0) {
            Constants.MOVE label = getMostCommonLabel(dataTuples);
            node.makeLeafNode(label);
            return node;
        }
         /* Otherwise:
        1. Call the attribute selection method on D and the attribute list, in order to choose the current
        attribute A:
        2. Label N as A and remove A from the attribute list.
                */

        Attribute chosenAttribute = attributeSelectionMethod(dataTuples, attributes);
        node.setAttribute(chosenAttribute);
        attributes.remove(chosenAttribute);

        /* 3. For each value in attribute A:
        a) Separate all tuples in D so that attribute A takes the value aj, creating the subset Dj. */

        ArrayList<Condition> conditions = chosenAttribute.getConditions();

        for (Condition condition : conditions) {
            // a) Separate all tuples in D so that attribute A takes the value aj, creating the subset Dj.
            ArrayList<DataTuple> subsetD = getSubsetBasedOnAttributeValue(dataTuples, condition, chosenAttribute);
            // b) If Dj is empty, add a child node to N labeled with the majority class in D.
            TreeNode newNode;
            if (subsetD.size() == 0) {
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

    /**
     * This method return true or false depending on if all tuples contains the same label
     */
    private boolean hasSameLabels(ArrayList<DataTuple> tuples) {
        for (DataTuple d : tuples) {
            if (!d.getDirectionChosen().equals(tuples.get(0).getDirectionChosen()))
                return false;
        }
        return true;
    }

    /**
     * This method search through all tuples and find the most common label (direction)
     */
    private Constants.MOVE getMostCommonLabel(ArrayList<DataTuple> tuples) {
        HashMap<Constants.MOVE, Integer> labels = new HashMap<>();
        for (DataTuple tuple : tuples) {
            Constants.MOVE newLabel = tuple.getDirectionChosen();
            if (!labels.containsKey(tuple.getDirectionChosen())) {
                labels.put(newLabel, 1);
            } else {
                int count = labels.get(newLabel);
                labels.put(newLabel, count + 1);
            }
        }
        return labels.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    /**
     * TODO: Create a real attribute selection method --> function to calculate information Gain.
     * From https://github.com/Lastin/ID3/blob/master/ID3.java
     */
    private Attribute attributeSelectionMethod(ArrayList<DataTuple> dataset, ArrayList<Attribute> attribute) {
        return null;
    }


    /**
     * This method should find all the tuples that match the condition and then return a subset with all the tuples that do.
     */
    private ArrayList<DataTuple> getSubsetBasedOnAttributeValue(ArrayList<DataTuple> tuples, Condition condition, Attribute chosenAttribute) {
        ArrayList<DataTuple> subset = new ArrayList<>();
        for (DataTuple d : tuples) {
            if (chosenAttribute.getName() == "Score") {
                if (condition.getOperator() == Operator.MORE_THAN) {
                    if (d.getCurrentScore() > condition.getValue()) {
                        subset.add(d);
                    }
                }
                if (condition.getOperator() == Operator.LESS_THAN) {
                    if (d.getCurrentScore() <= condition.getValue()) {
                        subset.add(d);
                    }
                }
            }
        }
        return subset;
    }
}
