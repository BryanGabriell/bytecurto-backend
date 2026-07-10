package com.bryangabriel.bytecurto.infrastructure.exceptions;



public class UserNotFound extends RuntimeException {
    public UserNotFound(String message) {
        super(message);
    }
}
