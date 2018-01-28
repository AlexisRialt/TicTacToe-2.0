package com.alexisrialt;

import com.alexisrialt.implementations.ColumnCombination;
import com.alexisrialt.implementations.DiagonalCombination;
import com.alexisrialt.implementations.RowCombination;
import com.alexisrialt.implementations.TicTacToeCombinationFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombinationFactoryTests {
    private final TicTacToeCombinationFactory factory = new TicTacToeCombinationFactory();

    @Test
    void testThatFactoryReturnsRowCombination(){
        Combination rowCombination = factory.getInstance("RowCombination", 1, 3);
        assertTrue(rowCombination instanceof RowCombination, "Should be an instance of RowCombination.");
    }

    @Test
    void testThatFactoryReturnsColumnCombination(){
        Combination rowCombination = factory.getInstance("ColumnCombination", 1, 3);
        assertTrue(rowCombination instanceof ColumnCombination, "Should be an instance of ColumnCombination.");
    }

    @Test
    void testThatFactoryReturnsDiagonalCombination(){
        Combination rowCombination = factory.getInstance("DiagonalCombination", 1, 3);
        assertTrue(rowCombination instanceof DiagonalCombination, "Should be an instance of DiagonalCombination.");
    }
}