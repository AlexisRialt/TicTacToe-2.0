package com.alexisrialt.mocks;

import com.alexisrialt.Input;

import java.util.LinkedList;
import java.util.Queue;

public class MockInput implements Input {
    private Queue<String> input = new LinkedList<>();

    @Override
    public String read() {
        String userInput;
        do {
            userInput = input.poll();
        } while (userInput == null);
        return userInput;
    }

    public void addInput(String s){
        input.add(s);
    }
}