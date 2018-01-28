package com.alexisrialt.implementations;

import com.alexisrialt.Input;
import com.alexisrialt.Player;

import java.util.Map;

public class HumanPlayer extends BasePlayer {

    private final Input input;

    public HumanPlayer(String name, String playChar, Input input){
        super(name, playChar);
        this.input = input;
    }

    @Override
    final public String getNextMove() {
        return input.read();
    }
}
