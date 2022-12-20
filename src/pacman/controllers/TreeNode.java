package pacman.controllers;

import dataRecording.Attribute;
import pacman.game.Constants;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private Attribute attribute;
    private TreeNode parent;
    private List<TreeNode> children;
    private Constants.MOVE label;

    public TreeNode() {
        this.children = new ArrayList<TreeNode>();
    }

    public void addChildNode(TreeNode childNode) {
        childNode.parent = this;
        this.children.add(childNode);
    }

    //Is used if all tuples in the parent node has the same value
    public void makeLeafNode(Constants.MOVE label){
        this.label = label;
    }

    public Constants.MOVE getLabel() {
        return label;
    }

    public void setAttribute(Attribute chosenAttribute) {
        this.attribute = chosenAttribute;
    }

    public void printSubTree() {
    }
}

