package com.alexisrialt.implementations;

import com.alexisrialt.Player;

public class DiagonalCombination extends BaseCombination {
    private final int startPosition;
    private final int endPosition;

    DiagonalCombination(int startPosition, int gridSize) {
        super(gridSize);
        this.startPosition = startPosition;
        this.endPosition = startPosition == 1 ? gridSize : 1;
        getRelatedPosition();
    }

    final protected void getRelatedPosition() {
        Integer rowCoordinate;
        Integer columnCoordinate;
        for (int increment = 0; increment < gridSize; increment++) {
            if (startPosition < endPosition) {
                rowCoordinate = startPosition + increment;
                columnCoordinate = startPosition + increment;
            } else {
                rowCoordinate = startPosition - increment;
                columnCoordinate = endPosition + increment;
            }
            positions.add(rowCoordinate.toString() + "," + columnCoordinate.toString());
        }
    }
}