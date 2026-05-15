package org.example.model;

/**
 * Representa un modelo de coche.
 */
public class Modelo {
  private int idModelo;
  private String nombre;
  private Marca marca;
  private boolean activo;

  /**
   * Instancia un nuevo Modelo vacío.
   */
  public Modelo() {
  }

  /**
   * Instancia un nuevo Modelo con datos.
   *
   * @param idModelo el ID del modelo.
   * @param nombre   el nombre del modelo.
   * @param marca    la marca a la que pertenece.
   * @param activo   indica si el modelo está activo.
   */
  public Modelo(int idModelo, String nombre, Marca marca, boolean activo) {
    this.idModelo = idModelo;
    this.nombre = nombre;
    this.marca = marca;
    this.activo = activo;
  }

  /**
   * Obtiene el ID del modelo.
   *
   * @return el ID del modelo.
   */
  public int getIdModelo() {
    return idModelo;
  }

  /**
   * Establece el ID del modelo.
   *
   * @param idModelo el ID del modelo.
   */
  public void setIdModelo(int idModelo) {
    this.idModelo = idModelo;
  }

  /**
   * Obtiene el nombre del modelo.
   *
   * @return el nombre del modelo.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre del modelo.
   *
   * @param nombre el nombre del modelo.
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Obtiene la marca.
   *
   * @return la marca.
   */
  public Marca getMarca() {
    return marca;
  }

  /**
   * Establece la marca.
   *
   * @param marca la marca.
   */
  public void setMarca(Marca marca) {
    this.marca = marca;
  }

  /**
   * Verifica si el modelo está activo.
   *
   * @return true si está activo, false de lo contrario.
   */
  public boolean isActivo() {
    return activo;
  }

  /**
   * Establece el estado de actividad del modelo.
   *
   * @param activo true para activar, false para desactivar.
   */
  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}
