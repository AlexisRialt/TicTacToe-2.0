package com.alexisrialt;

import com.alexisrialt.implementations.Move;

import java.util.Set;

public interface Combination {
    boolean isWinningMove(Move move, Player player);
    Set<String> getRemainingMovesUntilWin();
    Player getFirstPlayerToPlay();
    boolean cantBeWon();
}
