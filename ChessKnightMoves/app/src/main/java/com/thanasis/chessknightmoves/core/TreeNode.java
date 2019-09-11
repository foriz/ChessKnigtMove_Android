package com.thanasis.chessknightmoves.core;

import com.thanasis.chessknightmoves.utils.Utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TreeNode {
    private int chessboard_letter;
    private int chessboard_number;

    private boolean isLeaf;

    private Set<TreeNode> children_nodes;

    public TreeNode(int position_letter, int position_number) {
        this.chessboard_letter = position_letter;
        this.chessboard_number = position_number;

        this.isLeaf = false;

        children_nodes = new HashSet<TreeNode>();
    }

    public TreeNode(int position_letter, int position_number, boolean isLeafNode) {
        this.chessboard_letter = position_letter;
        this.chessboard_number = position_number;

        this.isLeaf = isLeafNode;

        children_nodes = new HashSet<TreeNode>();
    }

    public int getPositionLetter() {
        return chessboard_letter;
    }

    public int getPositionNumber() {
        return chessboard_number;
    }

    public Set<TreeNode> getChildrenNodes() {
        return children_nodes;
    }

    public void addChildrenNodes(Set<TreeNode> childrenNodes) {
        if(childrenNodes != null && !childrenNodes.isEmpty()) {
            this.children_nodes.addAll(childrenNodes);
        }
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public String toChessString() {
        return Utils.convertPositionToLetter(chessboard_letter)+ Integer.toString(chessboard_number);
    }
}
