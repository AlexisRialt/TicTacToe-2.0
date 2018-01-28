package com.alexisrialt;

import com.alexisrialt.exceptions.*;
import com.alexisrialt.implementations.Config;
import com.alexisrialt.implementations.Players;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConfigTests {

    @Test
    void testUsingInvalidGridSizeFails() {
        Properties properties = getProperties("11", "X", "T", "O");
        Config config = new Config(properties);
        assertThrows(InvalidGridSizeException.class, config::extract,
                "There should be an exception.");

        properties = getProperties("2", "X", "T", "O");
        config = new Config(properties);
        assertThrows(InvalidGridSizeException.class, config::extract,
                "There should be an exception.");
    }

    @Test
    void testThatReadingValidConfigFileSucceeds() {
        Properties properties = getProperties("3", "X", "T", "O");
        Config config = new Config(properties);
        config.extract();
        testValidityOfConfigProperties(3, config);

        properties = getProperties("10", "X", "T", "O");
        config = new Config(properties);
        config.extract();
        testValidityOfConfigProperties(10, config);
    }

    @Test
    void testThatUsingInvalidPlayerCharacterFails() {
        Properties properties = getProperties("3", "XX", "T", "O");
        Config config = new Config(properties);
        assertThrows(InvalidPlayCharacterException.class, config::extract);
    }

    @Test
    void testThatUsingEmptyPlayerCharacterFails() {
        Properties properties = getProperties("3", "", "T", "O");
        Config config = new Config(properties);
        assertThrows(PlayCharacterNotFoundException.class, config::extract);
    }

    @Test
    void testThatIfKeyIsInvalidOrMissingAnErrorOccurs() {
        Properties properties = getProperties("3", "", "T", "O");
        properties.remove("PLAYER_1_CHAR");
        Config config = new Config(properties);
        assertThrows(PlayCharacterNotFoundException.class, config::extract);
    }

    @Test
    void testThatUsingDuplicatePlayerCharacterFails() {
        Properties properties = getProperties("3", "T", "T", "O");
        Config config = new Config(properties);
        assertThrows(DuplicatePlayCharacterException.class, config::extract);
    }

    @Test
    void testThatIfConfigKeyIsDuplicatedTakeValueOfLastKey() throws IOException {
        String config = "GRID_SIZE=3\n" +
                "PLAYER_1_CHAR=X\n" +
                "PLAYER_2_CHAR=T\n" +
                "PLAYER_3_CHAR=O\n" +
                "PLAYER_3_CHAR=Z";

        Properties properties = new Properties();
        properties.load(new ByteArrayInputStream(config.getBytes()));
        Config appConfig = new Config(properties);
        appConfig.extract();
        assertEquals("Z",
                appConfig.getPlayChar(Players.PLAYER_3),
                "Player 3 play Character should be Z.");
    }

    @Test
    void testThatConfigIsParsedCorrectlyWithRealFile() throws IOException {
        Properties properties = new Properties();
        properties.load(
                new FileInputStream(
                        new File("src/test/resources/config.properties")));
        Config config = new Config(properties);
        config.extract();
        testValidityOfConfigProperties(3, config);
    }

    private Properties getProperties(String size, String p1, String p2, String p3) {
        Properties properties = new Properties();
        properties.setProperty("GRID_SIZE", size);
        properties.setProperty("PLAYER_1_CHAR", p1);
        properties.setProperty("PLAYER_2_CHAR", p2);
        properties.setProperty("PLAYER_3_CHAR", p3);
        return properties;
    }

    private void testValidityOfConfigProperties(int size, Config config) {
        assertEquals(size, config.getGridSize(), "grid size should be 3.");
        assertEquals("X", config.getPlayChar(Players.PLAYER_1), "Player 1 play Character should be X.");
        assertEquals("T", config.getPlayChar(Players.PLAYER_2), "Player 2 play Character should be T.");
        assertEquals("O", config.getPlayChar(Players.PLAYER_3), "Player 3 play Character should be O.");
    }
}