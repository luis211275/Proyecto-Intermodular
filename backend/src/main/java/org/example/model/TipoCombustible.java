package org.example.model;

/**
 * Representa un tipo de combustible.
 */
public class TipoCombustible {
  private int idCombustible;
  private String nombre;
  private boolean activo;

  /**
   * Instancia un nuevo Tipo de Combustible vacío.
   */
  public TipoCombustible() {
  }

  /**
   * Instancia un nuevo Tipo de Combustible con datos.
   *
   * @param idCombustible el ID del tipo de combustible.
   * @param nombre        el nombre del tipo de combustible.
   * @param activo        indica si el tipo de combustible está activo.
   */
  public TipoCombustible(int idCombustible, String nombre, boolean activo) {
    this.idCombustible = idCombustible;
    this.nombre = nombre;
    this.activo = activo;
  }

  /**
   * Obtiene el ID del tipo de combustible.
   *
   * @return el ID del tipo de combustible.
   */
  public int getIdCombustible() {
    return idCombustible;
  }

  /**
   * Establece el ID del tipo de combustible.
   *
   * @param idCombustible el ID del tipo de combustible.
   */
  public void setIdCombustible(int idCombustible) {
    this.idCombustible = idCombustible;
  }

  /**
   * Obtiene el nombre del tipo de combustible.
   *
   * @return el nombre del tipo de combustible.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre del tipo de combustible.
   *
   * @param nombre el nombre del tipo de combustible.
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Verifica si el tipo de combustible está activo.
   *
   * @return true si está activo, false de lo contrario.
   */
  public boolean isActivo() {
    return activo;
  }

  /**
   * Establece el estado de actividad del tipo de combustible.
   *
   * @param activo true para activar, false para desactivar.
   */
  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}
