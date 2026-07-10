package com.bryangabriel.bytecurto.business.dto.ExceptionsDto;


import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponse(int status, String message, List<ErrorField> errors) {
    public static ErrorResponse responseStandard(String message){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }
    public static ErrorResponse responseConflit(String message){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), message, List.of());
    }
}
