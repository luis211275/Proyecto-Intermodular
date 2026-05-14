package org.example.model;

/**
 * Representa una categoría de coche.
 */
public class Categoria {
  private int idCategoria;
  private String nombre;
  private boolean activo;

  /**
   * Instancia una nueva Categoría vacía.
   */
  public Categoria() {
  }

  /**
   * Instancia una nueva Categoría con datos.
   *
   * @param idCategoria el ID de la categoría.
   * @param nombre      el nombre de la categoría.
   * @param activo      indica si la categoría está activa.
   */
  public Categoria(int idCategoria, String nombre, boolean activo) {
    this.idCategoria = idCategoria;
    this.nombre = nombre;
    this.activo = activo;
  }

  /**
   * Obtiene el ID de la categoría.
   *
   * @return el ID de la categoría.
   */
  public int getIdCategoria() {
    return idCategoria;
  }

  /**
   * Establece el ID de la categoría.
   *
   * @param idCategoria el ID de la categoría.
   */
  public void setIdCategoria(int idCategoria) {
    this.idCategoria = idCategoria;
  }

  /**
   * Obtiene el nombre de la categoría.
   *
   * @return el nombre de la categoría.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre de la categoría.
   *
   * @param nombre el nombre de la categoría.
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Verifica si la categoría está activa.
   *
   * @return true si está activa, false de lo contrario.
   */
  public boolean isActivo() {
    return activo;
  }

  /**
   * Establece el estado de actividad de la categoría.
   *
   * @param activo true para activar, false para desactivar.
   */
  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}