package org.example.exception;

/**
 * Excepción para errores de acceso a datos.
 */
public class ErrorDeAccesoADatosException extends Exception {

  /**
   * Instancia una nueva excepción de error de acceso a datos.
   *
   * @param message el mensaje de error.
   */
  public ErrorDeAccesoADatosException(String message) {
    super(message);
  }
}