package com.alexisrialt.implementations;

import com.alexisrialt.Combination;
import com.alexisrialt.CombinationFactory;
import com.alexisrialt.exceptions.BaseException;
import com.alexisrialt.exceptions.CombinationNotFoundException;

public class TicTacToeCombinationFactory implements CombinationFactory {
    public Combination getInstance(String combination, int coordinate, int gridSize) {
        switch (combination){
            case "RowCombination":
                return new RowCombination(coordinate, gridSize);
            case "ColumnCombination":
                return new ColumnCombination(coordinate, gridSize);
            case "DiagonalCombination":
                return new DiagonalCombination(coordinate, gridSize);
            default:
                throw new CombinationNotFoundException("No such object : " + combination);
        }
    }
}
