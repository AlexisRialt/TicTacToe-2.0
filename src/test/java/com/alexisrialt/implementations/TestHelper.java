package com.alexisrialt.implementations;

public class TestHelper {
    static String removeLineEndings(String s){
        return s.replace(getLineEndingSeparator(), "");
    }

    static String getLineEndingSeparator(){
        return System.getProperty("line.separator");
    }
}
