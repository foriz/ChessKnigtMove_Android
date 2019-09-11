package com.thanasis.chessknightmoves.core;

import com.thanasis.chessknightmoves.utils.Utils;

import java.util.*;

public class Algorithm {
    ArrayList<TreeNode> stack;
    ArrayList<String> move_sequences;

    // Input data
    String start_position = null;
    String end_position = null;
    int max_number_of_moves = -1;

    public Algorithm(String start, String end, int max_moves) {
        this.start_position = start;
        this.end_position = end;
        this.max_number_of_moves = max_moves;

        stack = new ArrayList<TreeNode>();
        move_sequences = new ArrayList<String>();
    }

    public ArrayList<String> start() {
        Utils.createLetterMapping();

        int start_position_letter = Utils.convertLetterToPosition(start_position);

        int start_position_number = Integer.parseInt(start_position.substring(1));

        int end_position_letter = Utils.convertLetterToPosition(end_position);
        int end_position_number = Integer.parseInt(end_position.substring(1));

        // We can represent the problem with a tree, which has as root node the start position.
        // Each child is an available move.
        // We will create the tree until depth equal to maximum number of moves.
        // If a child is not in equal depth with maximum number of moves, but it is equal with the end position
        // we do not calculate its children.

        // Create root node of the tree. Root node is the start position
        TreeNode root = new TreeNode(start_position_letter, start_position_number);
        createMovesetTree(root, 1, max_number_of_moves, end_position_letter, end_position_number);

        findMovePaths(root, 1, max_number_of_moves);

        return move_sequences;
    }

    public Set<TreeNode> findAvailableMoves(int position_letter, int position_number,
                                                   int end_position_letter, int end_position_number) {
        Set<TreeNode> possibleMoves = new HashSet<TreeNode>();

        // Knights can move two squares left/right and one square up/down
        // Knights can move two squares up/down and one square left/right
        // Knights can jump over pieces

        if(position_letter-2 > 0) {
            // Check if knight can move 2 squares left
            if(position_number-1 > 0) {
                // Check if knight can move 1 square down
                if((position_letter-2 == end_position_letter) && (position_number-1 == end_position_number)) {
                    // If the node is the requested end position, we consider it a leaf.
                    possibleMoves.add(new TreeNode(position_letter-2, position_number-1, true));
                }
                else {
                    possibleMoves.add(new TreeNode(position_letter-2, position_number-1));
                }
            }
            if(position_number+1 <= 8) {
                // Check if knight can move 1 square up
                if((position_letter-2 == end_position_letter) && (position_number+1 == end_position_number)) {
                    // If the node is the requested end position, we consider it a leaf.
                    possibleMoves.add(new TreeNode(position_letter-2, position_number+1, true));
                }
                else {
                    possibleMoves.add(new TreeNode(position_letter-2, position_number+1));
                }
            }
        }
        if(position_letter+2 <= 8) {
            // Check if knight can move 2 squares right
            if(position_number-1 > 0) {
                // Check if knight can move 1 square down
                if((position_letter+2 == end_position_letter) && (position_number-1 == end_position_number)) {
                    // If the node is the requested end position, we consider it a leaf.
                    possibleMoves.add(new TreeNode(position_letter+2, position_number-1, true));
                }
                else {
                    possibleMoves.add(new TreeNode(position_letter+2, position_number-1));
                }
            }
            if(position_number+1 <= 8) {
                // Check if knight can move 1 square up
                if((position_letter+2 == end_position_letter) && (position_number+1 == end_position_number)) {
                    // If the node is the requested end position, we consider it a leaf.
                    possibleMoves.add(new TreeNode(position_letter+2, position_number+1, true));
                }
                else {
                    possibleMoves.add(new TreeNode(position_letter+2, position_number+1));
                }
            }
        }
        if(position_number-2 > 0) {
            // Check if knight can move 2 squares down
            if(position_letter-1 > 0) {
                // Check if knight can move 1 square left
                if((position_letter-1 == end_position_letter) && (position_number-2 == end_position_number)) {
                    // If the node is the requested end position, we consider it a leaf.
                    possibleMoves.add(new TreeNode(position_letter-1, position_number-2, true));
                }
                else {
                    possibleMoves.add(new TreeNode(position_letter-1, position_number-2));
                }
            }
            if(position_letter+1 <= 8) {
                // Check if knight can move 1 square right
                if((position_letter+1 == end_position_letter) && (position_number-2 == end_position_number)) {
                    // If the node is the requested end position, we consider it a leaf.
                    possibleMoves.add(new TreeNode(position_letter+1, position_number-2, true));
                }
                else {
                    possibleMoves.add(new TreeNode(position_letter+1, position_number-2));
                }
            }
        }
        if(position_number+2 <= 8) {
            // Check if knight can move 2 squares up
            if(position_letter-1 > 0) {
                // Check if knight can move 1 square left
                if((position_letter-1 == end_position_letter) && (position_number+2 == end_position_number)) {
                    // If the node is the requested end position, we consider it a leaf.
                    possibleMoves.add(new TreeNode(position_letter-1, position_number+2, true));
                }
                else {
                    possibleMoves.add(new TreeNode(position_letter-1, position_number+2));
                }
            }
            if(position_letter+1 <= 8) {
                // Check if knight can move 1 square right
                if((position_letter+1 == end_position_letter) && (position_number+2 == end_position_number)) {
                    // If the node is the requested end position, we consider it a leaf.
                    possibleMoves.add(new TreeNode(position_letter+1, position_number+2, true));
                }
                else {
                    possibleMoves.add(new TreeNode(position_letter+1, position_number+2));
                }
            }
        }

        return possibleMoves;
    }

    public void createMovesetTree(TreeNode root, int depth, int max_depth, int end_position_letter, int end_position_number) {
        if(depth > max_depth) {
            return;
        }

        // We find all available moves that can start for the given root node.
        root.addChildrenNodes(findAvailableMoves(root.getPositionLetter(), root.getPositionNumber(), end_position_letter, end_position_number));

        // For each children we repeat searching for available moves, until we reach depth equal to maximum number of
        // moves. If the child is the requested position, we do not search for its children and we consider it as leaf.
        Iterator<TreeNode> iterator = root.getChildrenNodes().iterator();
        while(iterator.hasNext()) {
            TreeNode childNode = iterator.next();
            if(!childNode.isLeaf()) {
                createMovesetTree(childNode, depth+1, max_depth, end_position_letter, end_position_number);
            }
        }
    }

    public void findMovePaths(TreeNode root, int depth, int max_depth) {
        if(depth > max_depth) {
            return;
        }

        stack.add(root);

        // We traverse the tree with DFS method in order to find all available paths until the end node.
        // We represent the traversed path in a stack. When we visit a node we push it to the stack.
        // When we find the requested position, which it is a leaf node, we keep the whole stack as the path
        // from start position to end position. When we finish visiting a node and its child and we do not find
        // the end position, we pop this node from the stack. We repeat this operation until we have traversed
        // the whole tree.
        Iterator<TreeNode> iterator = root.getChildrenNodes().iterator();
        while(iterator.hasNext()) {
            TreeNode childNode = iterator.next();
            if(!childNode.isLeaf()) {
                findMovePaths(childNode, depth+1, max_depth);
            }
            else {
                stack.add(childNode);
                saveMoveSequence();
                stack.remove(stack.size()-1);
            }
        }
        stack.remove(stack.size()-1);
    }

    public void saveMoveSequence() {
        String move_seq = "";

        for(int i=0;i<stack.size();i++) {
            if(i == stack.size()-1) {
                move_seq = move_seq.concat(stack.get(i).toChessString());
            }
            else {
                move_seq = move_seq.concat(stack.get(i).toChessString()+"->");
            }
        }
        move_sequences.add(move_seq);
    }
}
