package com.alexisrialt.exceptions;

public class DuplicatePlayCharacterException extends BaseException {
    public DuplicatePlayCharacterException(String duplicatedSymbol){
        super("Duplicate play character found : " + duplicatedSymbol +
                "\nPlease make sure the play characters are unique.");
    }
}