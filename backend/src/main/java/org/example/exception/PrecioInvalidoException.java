package org.example.exception;

public class PrecioInvalidoException extends ErrorDeNegocioException {
    public PrecioInvalidoException(String message) {
        super(message);
    }
}
