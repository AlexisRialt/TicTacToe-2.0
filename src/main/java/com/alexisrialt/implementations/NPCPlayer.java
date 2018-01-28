package com.alexisrialt.implementations;

import com.alexisrialt.Combination;
import com.alexisrialt.CombinationService;
import com.alexisrialt.exceptions.NPCNoNextMoveException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NPCPlayer extends BasePlayer {
    private final CombinationService combinationService;
    private final int gridSize;
    private final HashSet<String> allMoves = new HashSet<>();

    public NPCPlayer(CombinationService combinationService, String name, String playChar, int gridSize) {
        super(name, playChar);
        this.combinationService = combinationService;
        this.gridSize = gridSize;
        generateAllMoves();
    }

    @Override
    final public String getNextMove() {
        allMoves.removeAll(combinationService.getMovesPlayed().keySet());
        Iterator<Combination> combinations = combinationService.getCombinations().iterator();
        Combination winningCombination = null;
        Combination preventLossCombination = null;
        Combination optimalCombination = null;
        int minRemainMovesToWin = 10;

        if (combinations.hasNext()) {
            Combination combination = combinations.next();
            if (anotherPlayerIsAboutToWin(combination)) {
                preventLossCombination = combination;
            } else if (npcCanWinThisCombination(combination)) {
                int size = combination.getRemainingMovesUntilWin().size();
                if (size == 1) {
                    winningCombination = combination;
                } else if (size < minRemainMovesToWin) {
                    optimalCombination = combination;
                    minRemainMovesToWin = size;
                }
            }
        }

        if (winningCombination != null) {
            return getMoveToPlay(winningCombination);
        } else if (preventLossCombination != null) {
            return getMoveToPlay(preventLossCombination);
        } else if (optimalCombination != null) {
            return getMoveToPlay(optimalCombination);
        } else if (!allMoves.isEmpty()) {
            return allMoves.iterator().next();
        }

        throw new NPCNoNextMoveException("NPC has no available move to play.");
    }

    private String getMoveToPlay(Combination combination) {
        Set<String> remainingMovesUntilWin = combination.getRemainingMovesUntilWin();
        if (!remainingMovesUntilWin.isEmpty()) {
            return remainingMovesUntilWin.iterator().next();
        }

        return null;
    }

    private boolean npcCanWinThisCombination(Combination combination) {
        return combination.getFirstPlayerToPlay() == this;
    }

    private boolean anotherPlayerIsAboutToWin(Combination combination) {
        return combination.getRemainingMovesUntilWin().size() == 1
                && combination.getFirstPlayerToPlay() != this;
    }

    private void generateAllMoves() {
        for (Integer row = 1; row <= gridSize; row++) {
            for (Integer column = 1; column <= gridSize; column++) {
                allMoves.add(row.toString() + "," + column.toString());
            }
        }
    }
}