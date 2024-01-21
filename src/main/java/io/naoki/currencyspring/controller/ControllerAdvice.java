package io.naoki.currencyspring.controller;

import io.naoki.currencyspring.exceptions.ErrorResponse;
import io.naoki.currencyspring.exceptions.ResourceAlreadyExistException;
import io.naoki.currencyspring.exceptions.ResourceNotFoundException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleNotFound(ResourceNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ErrorResponse handleIllegalState(ResourceAlreadyExistException e) {
        return new ErrorResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String detail = extractValidationMessage(e.getAllErrors());
        return new ErrorResponse(HttpStatus.BAD_REQUEST, detail);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ErrorResponse handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        String detail = extractValidationMessage(e.getAllErrors());
        return new ErrorResponse(HttpStatus.BAD_REQUEST, detail);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String detail = e.getMostSpecificCause().getMessage();
        return new ErrorResponse(HttpStatus.BAD_REQUEST, detail);
    }


    private String extractValidationMessage(List<? extends MessageSourceResolvable> list) {
        return list.stream()
                .findFirst()
                .map(MessageSourceResolvable::getDefaultMessage)
                .orElse("Request failed validation");
    }

}
