package com.alexisrialt.exceptions;

public class PlayCharacterNotFoundException extends BaseException {
    public PlayCharacterNotFoundException(String playerName){
        super("No play character for player : " + playerName + " was found.");
    }
}
