package org.example.model;

/**
 * Representa una versión de un modelo de coche.
 */
public class Version {
  private int idVersion;
  private String nombre;
  private Modelo modelo;
  private boolean activo;

  /**
   * Instancia una nueva Versión vacía.
   */
  public Version() {
  }

  /**
   * Instancia una nueva Versión con datos.
   *
   * @param idVersion el ID de la versión.
   * @param nombre    el nombre de la versión.
   * @param modelo    el modelo al que pertenece.
   * @param activo    indica si la versión está activa.
   */
  public Version(int idVersion, String nombre, Modelo modelo, boolean activo) {
    this.idVersion = idVersion;
    this.nombre = nombre;
    this.modelo = modelo;
    this.activo = activo;
  }

  /**
   * Obtiene el ID de la versión.
   *
   * @return el ID de la versión.
   */
  public int getIdVersion() {
    return idVersion;
  }

  /**
   * Establece el ID de la versión.
   *
   * @param idVersion el ID de la versión.
   */
  public void setIdVersion(int idVersion) {
    this.idVersion = idVersion;
  }

  /**
   * Obtiene el nombre de la versión.
   *
   * @return el nombre de la versión.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre de la versión.
   *
   * @param nombre el nombre de la versión.
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Obtiene el modelo.
   *
   * @return el modelo.
   */
  public Modelo getModelo() {
    return modelo;
  }

  /**
   * Establece el modelo.
   *
   * @param modelo el modelo.
   */
  public void setModelo(Modelo modelo) {
    this.modelo = modelo;
  }

  /**
   * Verifica si la versión está activa.
   *
   * @return true si está activa, false de lo contrario.
   */
  public boolean isActivo() {
    return activo;
  }

  /**
   * Establece el estado de actividad de la versión.
   *
   * @param activo true para activar, false para desactivar.
   */
  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}
