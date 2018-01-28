package com.alexisrialt;

public interface CombinationFactory {
    Combination getInstance(String combination, int coordinate, int gridSize);
}
