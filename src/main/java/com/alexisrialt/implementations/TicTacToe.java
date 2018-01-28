package com.alexisrialt.implementations;

import com.alexisrialt.*;

import java.util.*;

public class TicTacToe {
    private final Display display;
    private final Input input;
    private final Config config;
    private final Board board;
    private final CombinationService combinationService;
    private final List<Player> players;

    private Integer maxPlayRounds;
    private Integer maxPlayMoves;
    private Integer currentRound = 1;
    private Integer currentMove = 1;
    private boolean gameIsOver = false;

    public TicTacToe(Display display,
                     Input input,
                     Config config,
                     Board board,
                     CombinationService combinationService,
                     List<Player> players) {
        this.display = display;
        this.input = input;
        this.config = config;
        this.board = board;
        this.combinationService = combinationService;
        this.players = players;
        this.maxPlayRounds = getMaxPlayRounds();
        this.maxPlayMoves = getMaxPlayMoves();
    }

    public TicTacToe init() {
        display.output(Label.WELCOME);
        display.output(Label.CONFIGURED_GRID_SIZE + config.getGridSize());

        displayPlayCharacterChosen();

        display.output(Label.PRESS_ENTER_TO_START);
        input.read();
        return this;
    }

    public TicTacToe start() {
        display.output(Label.NEW_GAME_IS_STARTING);

        randomizePlayingOrder();
        displayPlayingOrder();
        combinationService.createCombinations();
        return this;
    }

    public List<Player> getPlayOrder() {
        return players;
    }

    public void run() {
        board.displayEmptyBoard();

        while (!gameIsOver) {
            Player winningPlayer = playRound();
            if (winningPlayer != null) {
                endGame(winningPlayer);
                return;
            }

            currentRound++;
            if (maxPlayRounds < currentRound) {
                endGame(null);
            }
        }
    }

    private Player playRound() {
        for (Player player : players) {
            boolean isWinningMove = playMove(player);
            if (isWinningMove) {
                return player;
            }
            currentMove++;
            if (currentMove > maxPlayMoves) {
                return null;
            }
        }
        return null;
    }

    private boolean playMove(Player player) {
        while (true) {
            display.output(getLineJump() + player.getName() + Label.PLEASE_ENTER_YOUR_NEXT_MOVE);

            String rawMove = player.getNextMove();
            if (combinationService.moveAlreadyPlayed(rawMove)) {
                display.output(Label.ERROR_MOVE_ALREADY_PLAYED);
                continue;
            }

            Move move = new Move(rawMove, config.getGridSize());
            if (move.validate()) {
                boolean winningMove = combinationService.isWinningMove(move, player);
                board.display(combinationService.getMovesPlayed());
                return winningMove;
            } else {
                display.output(Label.ERROR_MOVE_IS_INVALID + move.toString());
            }
        }
    }

    private String getLineJump(){
        return "\n";
    }

    private void endGame(Player player) {
        gameIsOver = true;
        if (player != null) {
            display.output(Label.GAME_IS_OVER_PLAYER_HAS_WON + player.getName());
        } else {
            display.output(Label.GAME_IS_OVER_DRAW);
        }
    }

    private void randomizePlayingOrder() {
        Collections.shuffle(players);
    }

    private void displayPlayCharacterChosen() {
        for (Player player : players) {
            display.output(Label.PLAYER_SELECTED_CHAR + player.getName() + " is : " + player.getPlayChar());
        }
    }

    private void displayPlayingOrder() {
        display.output(Label.PLAYING_ORDER);

        Integer counter = 1;
        for (Player player : getPlayOrder()) {
            display.output(counter.toString() + " - " + player.getName());
            counter++;
        }

        display.output(getLineJump() + getLineJump());
    }

    private Integer getMaxPlayRounds() {
        int playerCount = players.size();
        int gridSlots = config.getGridSize() * config.getGridSize();
        int maxRounds = gridSlots / playerCount;

        if (gridSlots % playerCount != 0) {
            maxRounds += 1;
        }
        return maxRounds;
    }

    private Integer getMaxPlayMoves() {
        return config.getGridSize() * config.getGridSize();
    }
}
