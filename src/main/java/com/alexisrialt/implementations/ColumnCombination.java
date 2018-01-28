package com.alexisrialt.implementations;

public class ColumnCombination extends BaseCombination {
    private final Integer columnCoordinate;

    ColumnCombination(int columnCoordinate, int gridSize) {
        super(gridSize);
        this.columnCoordinate = columnCoordinate;
        getRelatedPosition();
    }

    final protected void getRelatedPosition() {
        Integer rowCoordinate;
        for (int i = 1; i <= gridSize; i++) {
            rowCoordinate = i;
            positions.add(rowCoordinate.toString() + "," + columnCoordinate.toString());
        }
    }
}
