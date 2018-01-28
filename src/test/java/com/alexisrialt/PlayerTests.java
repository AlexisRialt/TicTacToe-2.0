package com.alexisrialt;

import com.alexisrialt.exceptions.NPCNoNextMoveException;
import com.alexisrialt.implementations.HumanPlayer;
import com.alexisrialt.implementations.Move;
import com.alexisrialt.implementations.NPCPlayer;
import com.alexisrialt.implementations.TicTacToeCombinationFactory;
import com.alexisrialt.mocks.MockInput;
import com.alexisrialt.services.TicTacToeCombinationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTests {
    private MockInput mockInput;
    private int gridSize = 3;

    @BeforeEach
    void setMockInput() {
        mockInput = new MockInput();
    }

    @Test
    void testThatNameGetterReturnsCorrectValue() {
        Player player = getHumanPlayer();
        assertEquals(player.getName(), "Alexis");
    }

    @Test
    void testThatPlayCharGetterReturnsCorrectValue() {
        Player player = getHumanPlayer();
        assertEquals(player.getPlayChar(), "X");
    }

    @Test
    void testThatHumanPlayerCanPlayHisNextMove() {
        String input = "1,1";
        mockInput.addInput(input);
        Player player = getHumanPlayer();
        assertEquals(input, player.getNextMove(), "Player should have played his move.");
    }

    @Test
    void testThatNPCPlayerCanPlayHisNextMove() {
        CombinationService combinationService = getCombinationService();

        Player player1 = new HumanPlayer("Bob", "X", null);
        Player player2 = new HumanPlayer("Alexis", "X", null);
        Player player3 = getNPCPlayer(combinationService);

        HashMap<String, Player> allMoves = getAllMoves(player1, player2, player3);
        allMoves.remove("1,1");
        for (Map.Entry<String, Player> entry : allMoves.entrySet())
        {
            combinationService.isWinningMove(new Move(entry.getKey(), gridSize), entry.getValue());
        }

        String nextMove = player3.getNextMove();
        assertEquals(nextMove, "1,1", "The move should be 1,1");
    }

    @Test
    void testThatIfNPCHasNoPlayableMoveAnExceptionIsThrown() {
        CombinationService combinationService = getCombinationService();

        Player player1 = new HumanPlayer("Bob", "X", null);
        Player player2 = new HumanPlayer("Alexis", "X", null);
        Player player3 = getNPCPlayer(combinationService);

        HashMap<String, Player> allMoves = getAllMoves(player1, player2, player3);
        for (Map.Entry<String, Player> entry : allMoves.entrySet())
        {
            combinationService.isWinningMove(new Move(entry.getKey(), gridSize), entry.getValue());
        }
        assertThrows(NPCNoNextMoveException.class, player3::getNextMove,
                "There should be an exception.");
    }

    private CombinationService getCombinationService() {
        CombinationService combinationService = new TicTacToeCombinationService(new TicTacToeCombinationFactory(), gridSize);
        combinationService.createCombinations();
        return combinationService;
    }

    private HashMap<String, Player> getAllMoves(Player player1, Player player2, Player player3) {
        HashMap<String, Player> movesPlayed = new HashMap<>();
        movesPlayed.put("1,1", player1);
        movesPlayed.put("3,2", player1);
        movesPlayed.put("3,1", player1);

        movesPlayed.put("1,2", player2);
        movesPlayed.put("2,1", player2);
        movesPlayed.put("3,3", player2);

        movesPlayed.put("1,3", player3);
        movesPlayed.put("2,3", player3);
        movesPlayed.put("2,2", player3);
        return movesPlayed;
    }

    private HumanPlayer getHumanPlayer() {
        return new HumanPlayer("Alexis", "X", mockInput);
    }

    private NPCPlayer getNPCPlayer(CombinationService combinationService) {
        return new NPCPlayer(combinationService,"Bob", "X", gridSize);
    }
}