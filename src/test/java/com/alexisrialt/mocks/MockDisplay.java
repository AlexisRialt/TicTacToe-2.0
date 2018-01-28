package com.alexisrialt.mocks;

import com.alexisrialt.Display;

import java.util.LinkedList;
import java.util.Queue;

public class MockDisplay implements Display {
    private Queue<String> output = new LinkedList<>();

    @Override
    public void output(String s) {
        output.add(s);
    }

    public String getOutput(){
        return output.poll();
    }

    public void flushAll(){
        output.clear();
    }
}
