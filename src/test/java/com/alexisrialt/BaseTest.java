package com.alexisrialt;

import java.util.Properties;

class BaseTest {
    Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("GRID_SIZE", "3");
        properties.setProperty("PLAYER_1_CHAR", "X");
        properties.setProperty("PLAYER_2_CHAR", "O");
        properties.setProperty("PLAYER_3_CHAR", "Z");
        return properties;
    }
}
