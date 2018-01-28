package com.alexisrialt.implementations;

public class RowCombination extends BaseCombination {
    private final Integer rowCoordinate;

    RowCombination(int rowCoordinate, int gridSize) {
        super(gridSize);
        this.rowCoordinate = rowCoordinate;
        getRelatedPosition();
    }

    final protected void getRelatedPosition() {
        Integer columnCoordinate;
        for (int i = 1; i <= gridSize; i++) {
            columnCoordinate = i;
            positions.add(rowCoordinate.toString() + "," + columnCoordinate.toString());
        }
    }
}
