package com.alexisrialt;

import com.alexisrialt.implementations.*;
import com.alexisrialt.mocks.MockBoard;
import com.alexisrialt.mocks.MockCombinationLoosingFactory;
import com.alexisrialt.mocks.MockDisplay;
import com.alexisrialt.mocks.MockInput;
import com.alexisrialt.services.TicTacToeCombinationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTests extends BaseTest{
    private TicTacToe ticTacToe;
    private MockDisplay display;
    private MockInput input;
    private Config config;
    private final String ALREADY_PLAYED_MOVE = "1,1";
    private final String FAIL_INPUT = "2:2";

    private Config getConfig(Properties properties) {
        Config config = new Config(properties);
        config.extract();
        return config;
    }

    @BeforeEach
    void initTicTacToe() {
        display = new MockDisplay();
        input = new MockInput();
    }

    @Test
    void testThatGameStartUpIsSuccessful() {
        config = getConfig(getProperties());
        ticTacToe = new TicTacToe(display, input, config, new MockBoard(), null, getPlayers());
        input.addInput("EnterKeyPressMock");
        ticTacToe.init();

        assertEquals(Label.WELCOME, display.getOutput(), "Should be welcome message.");

        assertEquals(Label.CONFIGURED_GRID_SIZE + config.getGridSize(),
                display.getOutput(),
                "Should display the grid size.");

        for (Player player : getPlayers()) {
            assertEquals(Label.PLAYER_SELECTED_CHAR + player.getName() + " is : " + player.getPlayChar(),
                    display.getOutput(),
                    "Should display the selected player character.");
        }

        assertEquals(Label.PRESS_ENTER_TO_START, display.getOutput(), "Should be \'Press Enter\' message.");
    }

    @Test
    void testStartGame() {
        config = getConfig(getProperties());
        ticTacToe = new TicTacToe(display, input, config, new MockBoard(), getCombinationService(), getPlayers());
        input.addInput("EnterKeyPressMock");
        ticTacToe.init();

        display.flushAll();
        ticTacToe.start();
        testThatLabelsAreDisplayedCorrectly();
        testThatSelectedCharsAreDisplayedCorrectly();
    }

    @Test
    void testThatFailInputAndAlreadyPlayedMoveAreHandledCorrectly() {
        config = getConfig(getProperties());
        ticTacToe = new TicTacToe(display, input, config, new MockBoard(), getCombinationService(), getPlayers());
        input.addInput("EnterKeyPressMock");
        ticTacToe.init();

        ticTacToe.start();
        display.flushAll();

        addMockInputsWithFailInput(config.getGridSize());

        ticTacToe.run();

        testThatGameAsksForMoveAndGetsTheInputWithFailInput(1, 3);
    }

    private TicTacToeCombinationService getCombinationService() {
        return new TicTacToeCombinationService(new TicTacToeCombinationFactory(), config.getGridSize());
    }

    private TicTacToeCombinationService getMockCombinationService() {
        return new TicTacToeCombinationService(new MockCombinationLoosingFactory(), config.getGridSize());
    }

    @Test
    void playEvenMoveCount() {
        // Each player will play the same amount of times.
        Properties properties = getProperties();
        properties.setProperty("GRID_SIZE", "6");
        config = getConfig(properties);

        playGameUntilTheLastMove(12, 36);

        assertEquals(Label.GAME_IS_OVER_DRAW,
                display.getOutput(),
                "Should be a draw");
    }

    @Test
    void playNotEvenMoveCount() {
        // Each player will not play the same amount of times.
        Properties properties = getProperties();
        properties.setProperty("GRID_SIZE", "10");
        config = getConfig(properties);
        playGameUntilTheLastMove(34, 100);

        assertEquals(Label.GAME_IS_OVER_DRAW,
                display.getOutput(),
                "Should be a draw");
    }

    @Test
    void playDrawGame() {
        TicTacToe ticTacToeGame = getTicTacToeGame();

        input.addInput("1,1");
        input.addInput("1,2");
        input.addInput("2,1");

        input.addInput("2,2");
        input.addInput("3,3");
        input.addInput("1,3");

        input.addInput("3,1");
        input.addInput("3,2");
        input.addInput("2,3");

        ticTacToeGame.run();

        removeOutputUntil(9);

        assertEquals(Label.GAME_IS_OVER_DRAW,
                display.getOutput(),
                "The game should end in a draw.");
    }

    @Test
    void playWinningTopDownDiagonalGame() {
        TicTacToe ticTacToeGame = getTicTacToeGame();

        input.addInput("1,1");
        input.addInput("2,1");
        input.addInput("1,2");

        input.addInput("2,2");
        input.addInput("2,3");
        input.addInput("1,3");

        input.addInput("3,3");
        input.addInput("3,1");
        input.addInput("3,2");

        ticTacToeGame.run();

        removeOutputUntil(7);

        assertEquals(Label.GAME_IS_OVER_PLAYER_HAS_WON + ticTacToeGame.getPlayOrder().get(0).getName(),
                display.getOutput(),
                "Should be a win.");
    }

    @Test
    void playWinningDownTopDiagonalGame() {
        TicTacToe ticTacToeGame = getTicTacToeGame();

        input.addInput("3,1");
        input.addInput("2,1");
        input.addInput("1,1");

        input.addInput("2,2");
        input.addInput("2,3");
        input.addInput("3,2");

        input.addInput("1,3");
        input.addInput("1,2");
        input.addInput("3,3");

        ticTacToeGame.run();

        removeOutputUntil(7);

        assertEquals(Label.GAME_IS_OVER_PLAYER_HAS_WON + ticTacToeGame.getPlayOrder().get(0).getName(),
                display.getOutput(),
                "Should be a win.");
    }

    @Test
    void playWinningRowGame() {
        TicTacToe ticTacToeGame = getTicTacToeGame();

        input.addInput("1,1");
        input.addInput("2,1");
        input.addInput("3,1");

        input.addInput("1,2");
        input.addInput("2,2");
        input.addInput("3,2");

        input.addInput("1,3");
        input.addInput("2,3");
        input.addInput("3,3");

        ticTacToeGame.run();

        removeOutputUntil(7);

        assertEquals(Label.GAME_IS_OVER_PLAYER_HAS_WON + ticTacToeGame.getPlayOrder().get(0).getName(),
                display.getOutput(),
                "Should be a win.");
    }

    @Test
    void playWinningColumnGame() {
        TicTacToe ticTacToeGame = getTicTacToeGame();

        input.addInput("1,1");
        input.addInput("1,2");
        input.addInput("1,3");

        input.addInput("2,1");
        input.addInput("2,2");
        input.addInput("2,3");

        input.addInput("3,1");
        input.addInput("3,2");
        input.addInput("3,3");

        ticTacToeGame.run();

        removeOutputUntil(7);

        assertEquals(Label.GAME_IS_OVER_PLAYER_HAS_WON + ticTacToeGame.getPlayOrder().get(0).getName(),
                display.getOutput(),
                "Should be a win.");
    }

    private List<Player> getPlayers(){
        List<Player> players = new LinkedList<>();
        players.add(new HumanPlayer(Players.PLAYER_1.name(), config.getPlayChar(Players.PLAYER_1), input));
        players.add(new HumanPlayer(Players.PLAYER_2.name(), config.getPlayChar(Players.PLAYER_2), input));
        players.add(new HumanPlayer(Players.PLAYER_3.name(), config.getPlayChar(Players.PLAYER_3), input));
        return players;
    }

    private void playGameUntilTheLastMove(int playRounds, int maxMoves) {
        ticTacToe = new TicTacToe(display,
                input,
                config,
                new TicTacToeBoard(display, config.getGridSize()),
                getMockCombinationService(),
                getPlayers());

        input.addInput("EnterKeyPressMock");

        ticTacToe.init();
        ticTacToe.start();
        display.flushAll();

        addMockInputs(config.getGridSize());

        ticTacToe.run();

        testThatGameAsksPlayerForHisMoveAndGetsTheInput(playRounds, maxMoves);
    }

    private TicTacToe getTicTacToeGame() {
        Properties properties = getProperties();
        properties.setProperty("GRID_SIZE", "3");
        config = getConfig(properties);
        ticTacToe = new TicTacToe(display,
                input,
                config,
                new TicTacToeBoard(new VoidDisplay(), config.getGridSize()),
                getCombinationService(),
                getPlayers());

        ticTacToe.start();
        display.flushAll();
        return ticTacToe;
    }

    private void removeOutputUntil(int move) {
        for (int i = 0; i < move; i++) {
            display.getOutput();
        }
    }

    private void testThatGameAsksPlayerForHisMoveAndGetsTheInput(Integer maxPlayRounds, Integer maxPlayMoves) {
        assertFalse(display.getOutput().isEmpty(),"Should be the empty grid.");

        Integer playedMoves = 1;
        for (int round = 1; round <= maxPlayRounds; round++) {
            for (Player player : ticTacToe.getPlayOrder()) {
                if (playedMoves > maxPlayMoves) {
                    return;
                }
                assertEquals("\n" + player.getName() + Label.PLEASE_ENTER_YOUR_NEXT_MOVE,
                        display.getOutput(),
                        "Should ask player " + player.getName() + " to play his move.");
                assertFalse(display.getOutput().isEmpty(),
                        "Should be the updated grid.");
                playedMoves++;
            }
        }
    }

    private void testThatGameAsksForMoveAndGetsTheInputWithFailInput(Integer maxPlayRounds, Integer maxPlayMoves) {
        Integer playedMoves = 1;
        for (int round = 1; round <= maxPlayRounds; round++) {
            for (Player player : ticTacToe.getPlayOrder()) {
                if (playedMoves > maxPlayMoves) {
                    return;
                }
                assertEquals("\n" + player.getName() + Label.PLEASE_ENTER_YOUR_NEXT_MOVE,
                        display.getOutput(),
                        "Should ask player " + player.getName() + " to play his move.");
                if (playedMoves == 1) {
                    testThatInvalidInputIsHandledCorrectly(player);
                }
                if (playedMoves == 2) {
                    testThatDuplicatedMoveIsHandledCorrectly(player);
                }

                playedMoves++;
            }
        }
    }

    private void testThatDuplicatedMoveIsHandledCorrectly(Player player) {
        assertEquals(Label.ERROR_MOVE_ALREADY_PLAYED,
                display.getOutput(),
                "Should ask player " + player + " to input a valid move that was not played previously.");
        assertEquals("\n" + player.getName() + Label.PLEASE_ENTER_YOUR_NEXT_MOVE,
                display.getOutput(),
                "Should ask player " + player + " to play his move.");
    }

    private void testThatInvalidInputIsHandledCorrectly(Player player) {
        assertEquals(Label.ERROR_MOVE_IS_INVALID + FAIL_INPUT,
                display.getOutput(),
                "Should ask player " + player.getName() + " to input a valid move.");
        assertEquals("\n" + player.getName() + Label.PLEASE_ENTER_YOUR_NEXT_MOVE,
                display.getOutput(),
                "Should ask player " + player.getName() + " to play his move.");
    }

    private void testThatSelectedCharsAreDisplayedCorrectly() {
        Integer playerCounter = 1;
        for (Player player : ticTacToe.getPlayOrder()) {
            assertEquals(playerCounter.toString() + " - " + player.getName(),
                    display.getOutput(),
                    "Should display the selected player character.");
            playerCounter++;
        }
    }

    private void testThatLabelsAreDisplayedCorrectly() {
        assertEquals(Label.NEW_GAME_IS_STARTING,
                display.getOutput(),
                "Should be \'New Game is starting\' message.");

        assertEquals(Label.PLAYING_ORDER,
                display.getOutput(),
                "Should display the playing order label.");
    }

    private void addMockInputs(Integer gridSize) {
        for (Integer row = 1; row <= gridSize; row++) {
            for (Integer column = 1; column <= gridSize; column++) {
                input.addInput(row.toString() + "," + column.toString());
            }
        }
    }

    private void addMockInputsWithFailInput(Integer gridSize) {
        input.addInput(FAIL_INPUT);
        input.addInput(ALREADY_PLAYED_MOVE);
        addMockInputs(gridSize);
    }
}