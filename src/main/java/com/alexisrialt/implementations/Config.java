package com.alexisrialt.implementations;

import com.alexisrialt.exceptions.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

public class Config {
    private final Properties properties;
    private final HashMap<Players, String> playerPlayChars = new HashMap<>();
    private final HashSet<String> uniquePlayCharacter = new HashSet<>();
    private final String SUFFIX = "_CHAR";
    private final String GRID_SIZE_KEY = "GRID_SIZE";

    private int gridSize;

    public Config(Properties properties) {
        this.properties = properties;
    }

    public void extract() {
        extractGridSize();
        extractPlayerSymbols();
    }

    private void extractPlayerSymbols() {
        for (Players player : Players.values()) {
            String playCharacter = properties.getProperty(player.name() + SUFFIX);

            if(playCharacter == null || playCharacter.isEmpty()) {
                throw new PlayCharacterNotFoundException(player.name());
            }

            if (playCharacter.length() > 1) {
                throw new InvalidPlayCharacterException(playCharacter);
            }

            if (uniquePlayCharacter.contains(playCharacter)){
                throw new DuplicatePlayCharacterException(playCharacter);
            }

            uniquePlayCharacter.add(playCharacter);
            playerPlayChars.put(player, playCharacter);
        }
    }

    private void extractGridSize() {
        String value = properties.getProperty(GRID_SIZE_KEY);
        try {
            this.gridSize = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new GridSizeInvalidTypeException();
        }

        if (gridSize < 3 || gridSize > 10) {
            throw new InvalidGridSizeException(value);
        }
    }

    public int getGridSize() {
        return gridSize;
    }

    public String getPlayChar(Players player) {
        return playerPlayChars.get(player);
    }
}