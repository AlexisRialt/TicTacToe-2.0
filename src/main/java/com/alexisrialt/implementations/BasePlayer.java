package com.alexisrialt.implementations;

import com.alexisrialt.Player;

abstract class BasePlayer implements Player {
    private final String name;
    private final String playChar;

    BasePlayer(String name, String playChar) {
        this.name = name;
        this.playChar = playChar;
    }

    @Override
    final public String getName() {
        return name;
    }

    @Override
    final public String getPlayChar() {
        return playChar;
    }

}
