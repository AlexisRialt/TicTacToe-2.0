package com.alexisrialt.implementations;

import com.alexisrialt.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Move {
    private final String move;
    private final Integer gridSize;
    private int row;
    private int column;

    public Move(String move, Integer gridSize) {
        this.move = move;
        this.gridSize = gridSize;
    }

    public boolean validate() {
        Pattern pattern = Pattern.compile("^([0-9]+)(,)([0-9]+)$");
        Matcher matcher = pattern.matcher(move);

        if (!matcher.matches()) {
            return false;
        }

        int rowCoordinate = Integer.parseInt(matcher.group(1));
        String secondGroup = matcher.group(2);
        int columnCoordinate = Integer.parseInt(matcher.group(3));

        if (isValidCoordinate(rowCoordinate)
                && secondGroup.equals(",")
                    && isValidCoordinate(columnCoordinate)) {
            row = rowCoordinate;
            column = columnCoordinate;
            return true;
        }
        return false;
    }

    private boolean isValidCoordinate(int coordinate) {
        return coordinate > 0 && coordinate <= gridSize;
    }

    @Override
    public String toString() {
        return move;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
