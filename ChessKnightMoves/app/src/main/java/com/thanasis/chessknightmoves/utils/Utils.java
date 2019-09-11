package com.thanasis.chessknightmoves.utils;

import java.util.HashMap;

public class Utils {
    private static String startPosition = null;
    private static String endPosition = null;

    private static int maxMoves = 3;

    static HashMap<Integer, String> letter_codes = new HashMap<Integer, String>();

    public static void createLetterMapping() {
        for(int i=1;i<9;i++) {
            char c = (char) ('A' + (i-1));
            letter_codes.put(i, Character.toString(c));
        }
    }

    public static int convertLetterToPosition(String position_str) {
        return (position_str.charAt(0) % 8);
    }

    public static String convertPositionToLetter(int code) {
        return letter_codes.get(code);
    }

    public static String getStartPosition() {
        if(startPosition != null) {
            return startPosition;
        }
        return null;
    }

    public static String getEndPosition() {
        if(endPosition != null) {
            return endPosition;
        }
        return null;
    }

    public static void updateStartPosition(String start) {
        startPosition = start;
    }
    public static void updateEndPosition(String end) {
        endPosition = end;
    }

    public static int getMaxMoves() {
        return maxMoves;
    }

    public static void updateMaxMoves(int max) {
        maxMoves = max;
    }

}
