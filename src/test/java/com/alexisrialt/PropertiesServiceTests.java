package com.alexisrialt;

import com.alexisrialt.exceptions.NoConfigFileException;
import com.alexisrialt.mocks.MockDisplay;
import com.alexisrialt.services.PropertiesService;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesServiceTests {
    @Test
    void testThatWhenNoFilePathIsGivenAtStartupAnErrorOccurs() {
        MockDisplay mockDisplay = new MockDisplay();
        PropertiesService propertiesService = new PropertiesService(mockDisplay, new String[0]);
        propertiesService.get();
        String output = mockDisplay.getOutput();

        assertEquals(NoConfigFileException.defaultMessage, output,
                "There should be an exception message.");
    }

    @Test
    void testThatWhenConfigFileIsNotReadableAnErrorOccurs() {
        MockDisplay mockDisplay = new MockDisplay();
        String[] args = new String[1];
        String ps = File.pathSeparator;
        String fileName = ps+"home"+ps+"alexis"+ps+"SomeFileThatDoesNotExist";
        args[0] = fileName;

        PropertiesService propertiesService = new PropertiesService(mockDisplay, args);
        propertiesService.get();
        String output = mockDisplay.getOutput();

        assertEquals(fileName + " (The system cannot find the file specified)", output,
                "There should be an exception message.");
    }
}