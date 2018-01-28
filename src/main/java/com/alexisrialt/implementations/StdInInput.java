package com.alexisrialt.implementations;

import com.alexisrialt.Input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StdInInput implements Input {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public StdInInput(){}

    public String read() {
        String input = null;
        do{
            try {
                input = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (input == null);

        return input;
    }
}
