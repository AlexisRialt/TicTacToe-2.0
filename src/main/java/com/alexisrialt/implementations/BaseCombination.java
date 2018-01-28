package com.alexisrialt.implementations;

import com.alexisrialt.Combination;
import com.alexisrialt.Player;

import java.util.HashSet;
import java.util.Set;

abstract class BaseCombination implements Combination {
    final int gridSize;
    final Set<String> positions = new HashSet<>();

    private boolean positionCantBeWon;
    private Player firstPlayerToPlay;

    BaseCombination(int gridSize) {
        this.gridSize = gridSize;
    }

    @Override
    public final boolean isWinningMove(Move move, Player player) {
        if (positionCantBeWon) {
            return false;
        }

        if (notRelated(move)) {
            return false;
        }

        return evaluatePosition(move, player);
    }

    @Override
    public final boolean cantBeWon() {
        return positionCantBeWon;
    }

    @Override
    public final Set<String> getRemainingMovesUntilWin(){
        return positions;
    }

    @Override
    public Player getFirstPlayerToPlay() {
        return firstPlayerToPlay;
    }

    private boolean notRelated(Move move) {
        return !positions.contains(move.toString());
    }

    private boolean evaluatePosition(Move move, Player player) {
        if (firstPlayerToPlay == null) {
            firstPlayerToPlay = player;
        } else if (firstPlayerToPlay != player) {
            positionCantBeWon = true;
            return false;
        }

        String m = move.toString();
        positions.remove(m);
        // It was the last position needed to win.
        return positions.isEmpty();
    }

    protected abstract void getRelatedPosition();
}
