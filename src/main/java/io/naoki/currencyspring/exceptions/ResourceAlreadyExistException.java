package io.naoki.currencyspring.exceptions;

public class ResourceAlreadyExistException extends RuntimeException {

    public ResourceAlreadyExistException() {
    }

    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
