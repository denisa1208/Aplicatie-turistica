package org.example;

public class PersonNoExistsException extends Exception {
    public PersonNoExistsException(String message) {
        super(message);
    }
}
