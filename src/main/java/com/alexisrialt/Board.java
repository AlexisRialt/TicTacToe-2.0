package com.alexisrialt;

import java.util.Map;

public interface Board {
    void display(Map<String, Player> movesPlayed);
    void displayEmptyBoard();
}
