package org.example.model;

/**
 * Representa un tipo de transmisión.
 */
public class TipoTransmision {
  private int idTransmision;
  private String nombre;
  private boolean activo;

  /**
   * Instancia un nuevo Tipo de Transmisión vacío.
   */
  public TipoTransmision() {
  }

  /**
   * Instancia un nuevo Tipo de Transmisión con datos.
   *
   * @param idTransmision el ID del tipo de transmisión.
   * @param nombre        el nombre del tipo de transmisión.
   * @param activo        indica si el tipo de transmisión está activo.
   */
  public TipoTransmision(int idTransmision, String nombre, boolean activo) {
    this.idTransmision = idTransmision;
    this.nombre = nombre;
    this.activo = activo;
  }

  /**
   * Obtiene el ID del tipo de transmisión.
   *
   * @return el ID del tipo de transmisión.
   */
  public int getIdTransmision() {
    return idTransmision;
  }

  /**
   * Establece el ID del tipo de transmisión.
   *
   * @param idTransmision el ID del tipo de transmisión.
   */
  public void setIdTransmision(int idTransmision) {
    this.idTransmision = idTransmision;
  }

  /**
   * Obtiene el nombre del tipo de transmisión.
   *
   * @return el nombre del tipo de transmisión.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre del tipo de transmisión.
   *
   * @param nombre el nombre del tipo de transmisión.
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Verifica si el tipo de transmisión está activo.
   *
   * @return true si está activo, false de lo contrario.
   */
  public boolean isActivo() {
    return activo;
  }

  /**
   * Establece el estado de actividad del tipo de transmisión.
   *
   * @param activo true para activar, false para desactivar.
   */
  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}
