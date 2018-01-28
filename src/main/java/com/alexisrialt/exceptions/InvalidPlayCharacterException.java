package com.alexisrialt.exceptions;

public class InvalidPlayCharacterException extends BaseException {
    public InvalidPlayCharacterException(String playCharacter) {
        super("Invalid play character : " + playCharacter + "\nThe play character should be at most one character long.");
    }
}
