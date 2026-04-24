package org.example.exception;

public class CredencialesInvalidasException extends ErrorDeNegocioException {
    public CredencialesInvalidasException(String message) {
        super(message);
    }
}
