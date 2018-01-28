package com.alexisrialt.implementations;

import com.alexisrialt.Board;
import com.alexisrialt.Display;
import com.alexisrialt.Player;

import java.util.Map;

public class TicTacToeBoard implements Board {
    private final Display display;
    private final int gridSize;
    private final StringBuffer grid = new StringBuffer();

    public TicTacToeBoard(Display display, int gridSize) {
        this.display = display;
        this.gridSize = gridSize;
    }

    @Override
    public void display(Map<String, Player> movesPlayed) {
        addColumnCoordinates();
        for (int row = 1; row <= gridSize; row++) {
            addRowCoordinate(row);
            drawRow(movesPlayed, row);
        }

        display.output(grid.toString());
    }

    @Override
    public void displayEmptyBoard() {
        display(null);
    }

    private void addRowCoordinate(int row) {
        String frontSpacing = "  ";
        if(row > 9){
            frontSpacing = " ";
        }
        grid.append(frontSpacing).append(row).append(" ");
    }

    private void drawRow(Map<String, Player> movesPlayed, int row) {
        for (int column = 1; column <= gridSize; column++) {
            drawColumn(movesPlayed, row, column);
        }
        addBottomMargin();
    }

    private void addBottomMargin() {
        addSingleSpace();
        addFreeSpace();
        addHorizontalLine();
        addLineBreak();
    }

    private void addSingleSpace() {
        grid.append(" ");
    }

    private void drawColumn(Map<String, Player> movesPlayed, int row, int column) {
        addVerticalLine();
        addMove(movesPlayed, row, column);
        if (isLastColumn(column)) {
            addVerticalLine();
            addLineBreak();
        }
    }

    private boolean isLastColumn(int column){
        return column == gridSize;
    }

    private void addColumnCoordinates() {
        grid.append("   ");
        for (int i = 1; i <= gridSize; i++) {
            addFreeSpace();
            grid.append(i);
        }
        addLineBreak();
        addBottomMargin();
    }

    private void addFreeSpace() {
        grid.append("   ");
    }

    private void addLineBreak() {
        grid.append("\n");
    }

    private void addMove(Map<String, Player> movesPlayed, Integer row, Integer column) {
        String move = row + "," + column;
        if (movesPlayed != null && movesPlayed.containsKey(move)) {
            Player player = movesPlayed.get(move);
            String playerPlayChar = player.getPlayChar();
            grid.append(" ").append(playerPlayChar).append(" ");
        } else {
            addFreeSpace();
        }
    }

    private void addVerticalLine() {
        grid.append("|");
    }

    private void addHorizontalLine() {
        for (int i = 0; i < gridSize; i++) {
            grid.append("----");
        }
        grid.append("-");
    }
}
