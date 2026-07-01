package com.bryangabriel.bytecurto.infrastructure.exceptions;



public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
