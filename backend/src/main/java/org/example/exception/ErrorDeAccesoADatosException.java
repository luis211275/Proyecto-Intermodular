package org.example.exception;

public class ErrorDeAccesoADatosException extends Exception {
    public ErrorDeAccesoADatosException(String message) {
        super(message);
    }
    public ErrorDeAccesoADatosException(String message, Throwable cause) {
        super(message, cause);
    }
}
