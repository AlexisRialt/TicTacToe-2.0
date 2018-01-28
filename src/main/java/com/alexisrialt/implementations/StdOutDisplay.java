package com.alexisrialt.implementations;

import com.alexisrialt.Display;

public class StdOutDisplay implements Display {
    public void output(String content){
        System.out.println(content);
    }
}