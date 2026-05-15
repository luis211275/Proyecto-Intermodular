package org.example.model;

/**
 * Representa un color.
 */
public class Color {
  private int idColor;
  private String nombre;
  private boolean activo;

  /**
   * Instancia un nuevo Color vacío.
   */
  public Color() {
  }

  /**
   * Instancia un nuevo Color con datos.
   *
   * @param idColor el ID del color.
   * @param nombre  el nombre del color.
   * @param activo  indica si el color está activo.
   */
  public Color(int idColor, String nombre, boolean activo) {
    this.idColor = idColor;
    this.nombre = nombre;
    this.activo = activo;
  }

  /**
   * Obtiene el ID del color.
   *
   * @return el ID del color.
   */
  public int getIdColor() {
    return idColor;
  }

  /**
   * Establece el ID del color.
   *
   * @param idColor el ID del color.
   */
  public void setIdColor(int idColor) {
    this.idColor = idColor;
  }

  /**
   * Obtiene el nombre del color.
   *
   * @return el nombre del color.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre del color.
   *
   * @param nombre el nombre del color.
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Verifica si el color está activo.
   *
   * @return true si está activo, false de lo contrario.
   */
  public boolean isActivo() {
    return activo;
  }

  /**
   * Establece el estado de actividad del color.
   *
   * @param activo true para activar, false para desactivar.
   */
  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}