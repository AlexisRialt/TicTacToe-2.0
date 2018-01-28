package com.alexisrialt.mocks;

import com.alexisrialt.Board;
import com.alexisrialt.Player;

import java.util.Map;

public class MockBoard implements Board {
    @Override
    public void display(Map<String, Player> movesPlayed) {}

    @Override
    public void displayEmptyBoard() {
    }
}
