package org.example.exception;

/**
 * Excepción para errores de lógica de negocio.
 */
public class ErrorDeNegocioException extends Exception {
  /**
   * Instancia una nueva excepción de error de negocio.
   *
   * @param message el mensaje de error.
   */
  public ErrorDeNegocioException(String message) {
    super(message);
  }
}