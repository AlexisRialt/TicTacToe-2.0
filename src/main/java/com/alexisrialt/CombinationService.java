package com.alexisrialt;

import com.alexisrialt.implementations.Move;

import java.util.List;
import java.util.Map;

public interface CombinationService {
    boolean isWinningMove(Move move, Player player);
    List<Combination> getCombinations();
    void createCombinations();

    boolean moveAlreadyPlayed(String rawMove);

    Map<String,Player> getMovesPlayed();
}
