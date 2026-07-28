package com.bryangabriel.bytecurto.infrastructure.exceptions.handler;


import com.bryangabriel.bytecurto.business.dto.ExceptionsDto.ErrorField;
import com.bryangabriel.bytecurto.business.dto.ExceptionsDto.ErrorResponse;
import com.bryangabriel.bytecurto.infrastructure.exceptions.EmailAlreadyExistsException;
import com.bryangabriel.bytecurto.infrastructure.exceptions.UrlNotFound;
import com.bryangabriel.bytecurto.infrastructure.exceptions.UserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErrorField> listErros = fieldErrors
                .stream()
                .map(fieldError ->
                        new ErrorField(fieldError.getField(),
                                fieldError.getDefaultMessage()))
                .collect(Collectors.
                        toList());
        String camposComErro = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        log.warn("Falha de validação nos campos: [{}]", camposComErro);

        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro Validação", listErros);
    }

    @ExceptionHandler(UserNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse userNotFound(UserNotFound e){
        log.warn("Usuário não encontrado {}", e.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse emailAlreadyExistsException(EmailAlreadyExistsException e){
        log.warn("Tentativa de cadastro com e-mail duplicado: {}", e.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(UrlNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse urlNotFound(UrlNotFound e){
        log.warn("Url não encontrada: {}", e.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalServerError(Exception e){
        log.error("Erro interno inesperado no servidor: ", e);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro interno inesperado no servidor.", List.of());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("Corpo da requisição inválido ou mal formatado: {}", e.getMostSpecificCause().getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Corpo da requisição inválido ou mal formatado.", List.of());
    }

    @ExceptionHandler({BadCredentialsException.class,
            AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUnauthorized(Exception e) {
        log.warn("Falha de autenticação: {}", e.getMessage());
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "E-mail, senha ou token inválidos.", List.of());
    }
}
