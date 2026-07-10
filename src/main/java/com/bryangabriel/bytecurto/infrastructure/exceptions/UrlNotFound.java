package com.bryangabriel.bytecurto.infrastructure.exceptions;



public class UrlNotFound extends RuntimeException {
    public UrlNotFound(String message) {
        super(message);
    }
}
