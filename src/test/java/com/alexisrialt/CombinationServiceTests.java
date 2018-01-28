package com.alexisrialt;

import com.alexisrialt.implementations.*;
import com.alexisrialt.mocks.MockCombinationWinningFactory;
import com.alexisrialt.mocks.MockInput;
import com.alexisrialt.services.TicTacToeCombinationService;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CombinationServiceTests {

    @Test
    void testCombinationsAreEmptyBeforeCreatingThem() {
        int gridSize = 3;
        CombinationService combinationService = new TicTacToeCombinationService(new TicTacToeCombinationFactory(), gridSize);

        assertEquals(0,
                combinationService.getCombinations().size(),
                "There should be no combinations before the createCombinations function is called.");
    }

    @Test
    void testThatTheAmountOfCombinationsIsCorrectGrid3() {
        int gridSize = 3;
        CombinationService combinationService = new TicTacToeCombinationService(new TicTacToeCombinationFactory(), gridSize);
        testAmountOfCombinations(gridSize, combinationService);
    }

    @Test
    void testThatTheAmountOfCombinationsIsCorrectGrid10(){
        int gridSize = 10;
        CombinationService combinationService = new TicTacToeCombinationService(new TicTacToeCombinationFactory(), gridSize);
        testAmountOfCombinations(gridSize, combinationService);
    }

    @Test
    void testThatAddingANewMoveIsSuccessful(){
        int gridSize = 10;
        CombinationService combinationService = new TicTacToeCombinationService(new TicTacToeCombinationFactory(), gridSize);
        combinationService.isWinningMove(new Move("1,1", gridSize), getHumanPlayer());
        Map<String, Player> movesPlayed = combinationService.getMovesPlayed();
        assertEquals(1, movesPlayed.size(), "There should be one move only.");
        assertTrue(movesPlayed.containsKey("1,1"), "1,1 should be contained in the played moves.");
    }

    private HumanPlayer getHumanPlayer() {
        return new HumanPlayer("Alexis", "X", new MockInput());
    }

    @Test
    void testThatMoveAlreadyPlayedWorks(){
        int gridSize = 3;
        String move = "1,1";
        CombinationService combinationService = new TicTacToeCombinationService(new TicTacToeCombinationFactory(), gridSize);
        assertFalse(combinationService.moveAlreadyPlayed(move), "Move should not have been played yet.");
        combinationService.isWinningMove(new Move("1,1", gridSize), getHumanPlayer());
        assertTrue(combinationService.moveAlreadyPlayed(move), "Move should have already been played.");
    }

    @Test
    void testThatWinningMoveReturnsTrue(){
        int gridSize = 3;
        Move move = new Move("1,1", 3);
        Player player = getHumanPlayer();
        CombinationService combinationService = new TicTacToeCombinationService(new MockCombinationWinningFactory(), gridSize);
        combinationService.createCombinations();
        assertTrue(combinationService.isWinningMove(move, player), "Move should be a winning move.");
    }

    private void testAmountOfCombinations(int gridSize, CombinationService combinationService) {
        combinationService.createCombinations();
        List<Combination> combinations = combinationService.getCombinations();
        Iterator<Combination> iterator = combinations.iterator();
        int rowCombinationCounter = 0;
        int columnCombinationCounter = 0;
        int diagonalCombinationCounter = 0;
        while(iterator.hasNext()){
            Combination combination = iterator.next();
            if(combination instanceof RowCombination){
                rowCombinationCounter++;
            } else if(combination instanceof ColumnCombination){
                columnCombinationCounter++;
            } else if(combination instanceof DiagonalCombination){
                diagonalCombinationCounter++;
            }
        }

        assertEquals(gridSize, rowCombinationCounter, "The amount of rows should be the same as the size of the grid.");
        assertEquals(gridSize, columnCombinationCounter, "The amount of columns should be the same as the size of the grid.");
        assertEquals(2, diagonalCombinationCounter, "The amount of diagonals should be 2.");
    }
}
