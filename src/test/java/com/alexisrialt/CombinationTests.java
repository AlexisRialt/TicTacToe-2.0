package com.alexisrialt;

import com.alexisrialt.implementations.HumanPlayer;
import com.alexisrialt.implementations.Move;
import com.alexisrialt.implementations.TicTacToeCombinationFactory;
import com.alexisrialt.mocks.MockInput;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CombinationTests {
    private int gridSize = 3;
    private int rowCoordinate = 1;
    private int columnCoordinate = 1;

    private CombinationFactory combinationFactory = new TicTacToeCombinationFactory();

    @Test
    void testThatRowCombinationDeclaresVictory() {
        Combination combination = getRowCombination();
        testThatCombinationOutputsVictory(combination, getRowMoves(gridSize, rowCoordinate));
    }

    @Test
    void testThatColumnCombinationDeclaresVictory() {
        Combination combination = getColumnCombination();
        testThatCombinationOutputsVictory(combination, getColumnMoves(gridSize, columnCoordinate));
    }

    @Test
    void testThatDiagonalCombinationDeclaresVictory() {
        int startCoordinate = 1;
        Combination combination = getDiagonalCombination(startCoordinate);
        testThatCombinationOutputsVictory(combination, getDiagonalMoves(gridSize, startCoordinate));
    }

    @Test
    void testThatRowCombinationDoesNotDeclareVictoryWhen2DifferentPlayersPlayInThatPosition() {
        Combination combination = getRowCombination();
        testThatCombinationDoesNotOutputVictory(combination, getRowMoves(gridSize, rowCoordinate));
    }

    @Test
    void testThatColumnCombinationDoesNotDeclareVictoryWhen2DifferentPlayersPlayInThatPosition() {
        Combination combination = getColumnCombination();
        testThatCombinationDoesNotOutputVictory(combination, getColumnMoves(gridSize, columnCoordinate));
    }

    @Test
    void testThatDiagonalCombinationDoesNotDeclareVictoryWhen2DifferentPlayersPlayInThatPosition() {
        Combination combination = getDiagonalCombination(gridSize);
        testThatCombinationDoesNotOutputVictory(combination, getDiagonalMoves(gridSize, gridSize));
    }

    @Test
    void testThatRemainingMovesUntilWinningPositionAreCorrect() {
        Combination combination = getRowCombination();
        Move move = new Move("1,1", gridSize);
        combination.isWinningMove(move, null);
        Set<String> remainingMoves = combination.getRemainingMovesUntilWin();

        Set<String> expectedRemainingMoves = new HashSet<>();
        expectedRemainingMoves.add("1,2");
        expectedRemainingMoves.add("1,3");
        assertTrue(remainingMoves.equals(expectedRemainingMoves), "The remaining moves should match the expected remaining moves.");
    }

    @Test
    void testThatFirstPlayerToPlayIsCorrect() {
        Combination combination = getRowCombination();
        Move move = new Move("1,1", gridSize);
        Player player = getPlayer();
        assertNull(combination.getFirstPlayerToPlay(), "Nobody should have played that position yet.");
        combination.isWinningMove(move, player);
        Player firstPlayerToPlay = combination.getFirstPlayerToPlay();
        assertEquals(firstPlayerToPlay, player, "Nobody should have played that position yet.");
    }

    private void testThatCombinationOutputsVictory(Combination rowCombination, Queue<Move> moves) {
        Player player = getPlayer();
        assertFalse(rowCombination.isWinningMove(moves.poll(), player), "Should not be winning move");
        assertFalse(rowCombination.isWinningMove(moves.poll(), player), "Should not be winning move");
        assertTrue(rowCombination.isWinningMove(moves.poll(), player), "Should be winning move");
    }

    private HumanPlayer getPlayer() {
        return new HumanPlayer("Alexis", "X", new MockInput());
    }

    private void testThatCombinationDoesNotOutputVictory(Combination combination, Queue<Move> moves) {
        Player player1 = getPlayer();
        Player player2 = new HumanPlayer("Bob", "O", new MockInput());
        assertFalse(combination.isWinningMove(moves.poll(), player1), "Should not be winning move 1");
        assertFalse(combination.cantBeWon(), "The position should not be flagged as can\'t be won.");
        assertFalse(combination.isWinningMove(moves.poll(), player2), "Should not be winning move 2");
        assertTrue(combination.cantBeWon(), "The position should be flagged as can\'t be won.");
        assertFalse(combination.isWinningMove(moves.poll(), player2), "Should not be winning move 3");
    }

    private Combination getRowCombination() {
        return combinationFactory.getInstance("RowCombination", rowCoordinate, gridSize);
    }

    private Combination getColumnCombination() {
        return combinationFactory.getInstance("ColumnCombination", rowCoordinate, gridSize);
    }

    private Combination getDiagonalCombination(int startCoordinate) {
        return combinationFactory.getInstance("DiagonalCombination", startCoordinate, gridSize);
    }

    private Queue<Move> getRowMoves(int gridSize, int rowCoordinate) {
        Queue<Move> moves = new LinkedList<>();
        for (int column = 1; column <= gridSize; column++) {
            moves.add(new Move(rowCoordinate + "," + column, gridSize));
        }
        return moves;
    }

    private Queue<Move> getColumnMoves(int gridSize, int columnCoordinate) {
        Queue<Move> moves = new LinkedList<>();
        for (int row = 1; row <= gridSize; row++) {
            moves.add(new Move(row + "," + columnCoordinate, gridSize));
        }
        return moves;
    }

    private Queue<Move> getDiagonalMoves(int gridSize, int diagonalCoordinate) {
        Queue<Move> moves = new LinkedList<>();
        if (gridSize == diagonalCoordinate) {
            moves.add(new Move("3,1", gridSize));
            moves.add(new Move("2,2", gridSize));
            moves.add(new Move("1,3", gridSize));
        } else {
            moves.add(new Move("1,1", gridSize));
            moves.add(new Move("2,2", gridSize));
            moves.add(new Move("3,3", gridSize));
        }
        return moves;
    }
}