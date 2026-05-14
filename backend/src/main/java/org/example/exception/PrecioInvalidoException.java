package org.example.exception;

/**
 * Excepción para errores de precio inválido.
 */
public class PrecioInvalidoException extends Exception {
  /**
   * Instancia una nueva excepción de precio inválido.
   *
   * @param message el mensaje de error.
   */
  public PrecioInvalidoException(String message) {
    super(message);
  }
}