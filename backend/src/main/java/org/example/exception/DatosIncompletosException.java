package org.example.exception;

public class DatosIncompletosException extends ErrorDeNegocioException {
    public DatosIncompletosException(String message) {
        super(message);
    }
}
