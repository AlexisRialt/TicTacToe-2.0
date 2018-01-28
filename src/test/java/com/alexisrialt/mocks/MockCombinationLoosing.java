package com.alexisrialt.mocks;

import com.alexisrialt.Combination;
import com.alexisrialt.Player;
import com.alexisrialt.implementations.Move;

import java.util.HashSet;
import java.util.Set;

public class MockCombinationLoosing implements Combination {
    @Override
    public boolean isWinningMove(Move move, Player player) {
        return false;
    }

    @Override
    public Set<String> getRemainingMovesUntilWin() {
        return new HashSet<>();
    }

    @Override
    public Player getFirstPlayerToPlay() {
        return null;
    }

    @Override
    public boolean cantBeWon() {
        return false;
    }
}
