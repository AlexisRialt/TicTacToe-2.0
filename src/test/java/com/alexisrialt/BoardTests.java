package com.alexisrialt;

import com.alexisrialt.implementations.*;
import com.alexisrialt.mocks.MockDisplay;
import com.alexisrialt.mocks.MockInput;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTests extends BaseTest {
    private MockDisplay mockDisplay = new MockDisplay();

    @Test
    void testThatFullBoardDisplayIsCorrect() {
        Board board = getBoard();

        HumanPlayer player1 = new HumanPlayer(Players.PLAYER_1.name(), "X", new MockInput());
        HumanPlayer player2 = new HumanPlayer(Players.PLAYER_2.name(), "Z", new MockInput());
        NPCPlayer player3 = new NPCPlayer(null, Players.PLAYER_3.name(), "O", getConfig().getGridSize());

        Map<String, Player> movesPlayed = new HashMap<>();
        movesPlayed.put("1,1", player1);
        movesPlayed.put("1,2", player1);
        movesPlayed.put("1,3", player1);

        movesPlayed.put("2,1", player2);
        movesPlayed.put("2,2", player2);
        movesPlayed.put("2,3", player2);

        movesPlayed.put("3,1", player3);
        movesPlayed.put("3,2", player3);
        movesPlayed.put("3,3", player3);

        board.display(movesPlayed);

        String expectedBoard =  "      1   2   3\n" +
                                "    -------------\n" +
                                "  1 | X | X | X |\n" +
                                "    -------------\n" +
                                "  2 | Z | Z | Z |\n" +
                                "    -------------\n" +
                                "  3 | O | O | O |\n" +
                                "    -------------\n";

        assertEquals(expectedBoard, mockDisplay.getOutput(), "The boards should be the same.");
    }

    @Test
    void testThatEmptyDisplayIsValid() {
        Board board = getBoard();
        Map<String, Player> movesPlayed = new HashMap<>();
        board.display(movesPlayed);

        String expectedBoard =  "      1   2   3\n" +
                                "    -------------\n" +
                                "  1 |   |   |   |\n" +
                                "    -------------\n" +
                                "  2 |   |   |   |\n" +
                                "    -------------\n" +
                                "  3 |   |   |   |\n" +
                                "    -------------\n";

        assertEquals(expectedBoard, mockDisplay.getOutput(), "The boards should be the same.");
    }

    @Test
    void testThatDisplayEmptyBoardDisplaysAnEmptyBoard() {
        Board board = getBoard();
        board.displayEmptyBoard();

        String expectedBoard =  "      1   2   3\n" +
                                "    -------------\n" +
                                "  1 |   |   |   |\n" +
                                "    -------------\n" +
                                "  2 |   |   |   |\n" +
                                "    -------------\n" +
                                "  3 |   |   |   |\n" +
                                "    -------------\n";

        assertEquals(expectedBoard, mockDisplay.getOutput(), "The boards should be the same.");
    }

    private Board getBoard() {
        return new TicTacToeBoard(mockDisplay, getConfig().getGridSize());
    }

    private Config getConfig() {
        Properties properties = getProperties();
        Config config = new Config(properties);
        config.extract();
        return config;
    }
}
