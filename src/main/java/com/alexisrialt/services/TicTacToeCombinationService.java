package com.alexisrialt.services;

import com.alexisrialt.Combination;
import com.alexisrialt.CombinationFactory;
import com.alexisrialt.CombinationService;
import com.alexisrialt.Player;
import com.alexisrialt.implementations.Move;

import java.util.*;

public class TicTacToeCombinationService implements CombinationService {
    private final int gridSize;
    private final CombinationFactory combinationFactory;
    private final LinkedList<Combination> combinations = new LinkedList<>();
    private final Map<String, Player> movesPlayed = new HashMap<>();

    public TicTacToeCombinationService(CombinationFactory factory, int gridSize){
        this.combinationFactory = factory;
        this.gridSize = gridSize;
    }

    @Override
    public List<Combination> getCombinations() {
        return combinations;
    }

    @Override
    final public void createCombinations() {
        combinations.clear();
        for (int position = 1; position <= gridSize; position++) {
            createRowCombination(position);
            createColumnCombination(position);
        }

        createDiagonalCombination(1);
        createDiagonalCombination(gridSize);
    }

    @Override
    final public boolean moveAlreadyPlayed(String rawMove) {
        return movesPlayed.containsKey(rawMove);
    }

    @Override
    final public Map<String, Player> getMovesPlayed() {
        return movesPlayed;
    }

    @Override
    final public boolean isWinningMove(Move move, Player player) {
        movesPlayed.put(move.toString(), player);
        Iterator<Combination> iterator = combinations.iterator();
        while(iterator.hasNext()){
            Combination combination = iterator.next();
            if(combination.isWinningMove(move, player)){
                return true;
            }
            if(combination.cantBeWon()){
                iterator.remove();
            }
        }
        return false;
    }

    private void createRowCombination(int position) {
        combinations.add(
                combinationFactory.getInstance(
                        "RowCombination",
                        position,
                        gridSize));
    }

    private void createColumnCombination(int position) {
        combinations.add(
                combinationFactory.getInstance(
                        "ColumnCombination",
                        position,
                        gridSize));
    }

    private void createDiagonalCombination(int startCoordinate) {
        combinations.add(
                combinationFactory.getInstance(
                        "DiagonalCombination",
                        startCoordinate,
                        gridSize));
    }
}
