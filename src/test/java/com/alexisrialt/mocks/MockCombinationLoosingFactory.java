package com.alexisrialt.mocks;

import com.alexisrialt.Combination;
import com.alexisrialt.CombinationFactory;

public class MockCombinationLoosingFactory implements CombinationFactory {

    @Override
    public Combination getInstance(String combination, int coordinate, int gridSize) {
        return new MockCombinationLoosing();
    }
}
