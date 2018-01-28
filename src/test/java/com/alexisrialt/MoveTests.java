package com.alexisrialt;

import com.alexisrialt.implementations.Move;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTests {
    @Test
    void testThatValidMoveIsValid(){
        Move move = new Move("1,1", 10);
        assertTrue(move.validate(), "The move should be valid.");

        move = new Move("5,5", 10);
        assertTrue(move.validate(), "The move should be valid.");

        move = new Move("10,10", 10);
        assertTrue(move.validate(), "The move should be valid.");
    }

    @Test
    void testThatOutOfBoundsMoveIsInvalid(){
        Integer gridSize = 10;
        Move move = new Move("0,0", gridSize);
        assertFalse(move.validate(), "The move should be invalid because it's out of bounds.");

        move = new Move("0,1", gridSize);
        assertFalse(move.validate(), "The move should be invalid because it's out of bounds.");

        move = new Move("1,0", gridSize);
        assertFalse(move.validate(), "The move should be invalid because it's out of bounds.");

        move = new Move("11,10", gridSize);
        assertFalse(move.validate(), "The move should be invalid because it's out of bounds.");

        move = new Move("10,11", gridSize);
        assertFalse(move.validate(), "The move should be invalid because it's out of bounds.");

        move = new Move("11,11", gridSize);
        assertFalse(move.validate(), "The move should be invalid because it's out of bounds.");
    }

    @Test
    void testThatInvalidMoveIsInvalid(){
        Integer gridSize = 10;
        Move move = new Move("1:1", gridSize);
        assertFalse(move.validate(), "The move should be invalid.");

        move = new Move("w1,1", gridSize);
        assertFalse(move.validate(), "The move should be invalid.");

        move = new Move("1,", gridSize);
        assertFalse(move.validate(), "The move should be invalid.");

        move = new Move("1,w", gridSize);
        assertFalse(move.validate(), "The move should be invalid.");

        move = new Move("1,1p", gridSize);
        assertFalse(move.validate(), "The move should be invalid.");
    }

    @Test
    void testThatRowGetterIsValid(){
        Integer gridSize = 10;
        Move move = new Move("1,1", gridSize);
        move.validate();
        assertEquals(1, move.getRow(), "The row should be 1.");
        assertEquals(1, move.getColumn(), "The column should be 1.");

        move = new Move("10,10", gridSize);
        move.validate();
        assertEquals(10, move.getRow(), "The row should be 1.");
        assertEquals(10, move.getColumn(), "The column should be 1.");
    }
}