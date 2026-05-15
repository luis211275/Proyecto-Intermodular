package org.example.model;

/**
 * Representa una marca.
 */
public class Marca {
  private int idMarca;
  private String nombre;
  private boolean activo;

  /**
   * Instancia una nueva Marca vacía.
   */
  public Marca() {
  }

  /**
   * Instancia una nueva Marca con datos.
   *
   * @param idMarca el ID de la marca.
   * @param nombre  el nombre de la marca.
   */
  public Marca(int idMarca, String nombre) {
    this.idMarca = idMarca;
    this.nombre = nombre;
  }

  /**
   * Obtiene el ID de la marca.
   *
   * @return el ID de la marca.
   */
  public int getIdMarca() {
    return idMarca;
  }

  /**
   * Establece el ID de la marca.
   *
   * @param idMarca el ID de la marca.
   */
  public void setIdMarca(int idMarca) {
    this.idMarca = idMarca;
  }

  /**
   * Obtiene el nombre de la marca.
   *
   * @return el nombre de la marca.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre de la marca.
   *
   * @param nombre el nombre de la marca.
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Verifica si la marca está activa.
   *
   * @return true si está activa, false de lo contrario.
   */
  public boolean isActivo() {
    return activo;
  }

  /**
   * Establece el estado de actividad de la marca.
   *
   * @param activo true para activar, false para desactivar.
   */
  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}