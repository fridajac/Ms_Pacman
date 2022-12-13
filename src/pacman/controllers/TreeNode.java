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

    public TreeNode(Attribute attribute) {
        this.attribute = attribute;
        this.children = new ArrayList<TreeNode>();
    }

    public TreeNode addChild(Attribute attribute) {
        TreeNode childNode = new TreeNode(attribute);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    //Is used if all tuples in the parent node has the same value
    public void makeLeafNode(Constants.MOVE label){
        this.label = label;
    }
}

