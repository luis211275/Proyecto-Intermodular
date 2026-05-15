package org.example.model;

/**
 * Representa una ciudad.
 */
public class Ciudad {
  private int idCiudad;
  private String nombre;
  private boolean activo;

  /**
   * Instancia una nueva Ciudad vacía.
   */
  public Ciudad() {
  }

  /**
   * Instancia una nueva Ciudad con datos.
   *
   * @param idCiudad el ID de la ciudad.
   * @param nombre   el nombre de la ciudad.
   * @param activo   indica si la ciudad está activa.
   */
  public Ciudad(int idCiudad, String nombre, boolean activo) {
    this.idCiudad = idCiudad;
    this.nombre = nombre;
    this.activo = activo;
  }

  /**
   * Obtiene el ID de la ciudad.
   *
   * @return el ID de la ciudad.
   */
  public int getIdCiudad() {
    return idCiudad;
  }

  /**
   * Establece el ID de la ciudad.
   *
   * @param idCiudad el ID de la ciudad.
   */
  public void setIdCiudad(int idCiudad) {
    this.idCiudad = idCiudad;
  }

  /**
   * Obtiene el nombre de la ciudad.
   *
   * @return el nombre de la ciudad.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre de la ciudad.
   *
   * @param nombre el nombre de la ciudad.
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Verifica si la ciudad está activa.
   *
   * @return true si está activa, false de lo contrario.
   */
  public boolean isActivo() {
    return activo;
  }

  /**
   * Establece el estado de actividad de la ciudad.
   *
   * @param activo true para activar, false para desactivar.
   */
  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}