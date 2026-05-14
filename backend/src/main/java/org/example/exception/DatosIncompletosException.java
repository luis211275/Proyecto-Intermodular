package org.example.exception;

/**
 * Excepción para errores de datos incompletos.
 */
public class DatosIncompletosException extends Exception {
  /**
   * Instancia una nueva excepción de datos incompletos.
   *
   * @param message el mensaje de error.
   */
  public DatosIncompletosException(String message) {
    super(message);
  }
}