package org.example.model;

/**
 * Representa una etiqueta ambiental.
 */
public class EtiquetaAmbiental {
  private int idEtiqueta;
  private String nombre;
  private boolean activo;

  /**
   * Instancia una nueva Etiqueta Ambiental vacía.
   */
  public EtiquetaAmbiental() {
  }

  /**
   * Instancia una nueva Etiqueta Ambiental con datos.
   *
   * @param idEtiqueta el ID de la etiqueta.
   * @param nombre     el nombre de la etiqueta.
   * @param activo     indica si la etiqueta está activa.
   */
  public EtiquetaAmbiental(int idEtiqueta, String nombre, boolean activo) {
    this.idEtiqueta = idEtiqueta;
    this.nombre = nombre;
    this.activo = activo;
  }

  /**
   * Obtiene el ID de la etiqueta.
   *
   * @return el ID de la etiqueta.
   */
  public int getIdEtiqueta() {
    return idEtiqueta;
  }

  /**
   * Establece el ID de la etiqueta.
   *
   * @param idEtiqueta el ID de la etiqueta.
   */
  public void setIdEtiqueta(int idEtiqueta) {
    this.idEtiqueta = idEtiqueta;
  }

  /**
   * Obtiene el nombre de la etiqueta.
   *
   * @return el nombre de la etiqueta.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre de la etiqueta.
   *
   * @param nombre el nombre de la etiqueta.
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Verifica si la etiqueta está activa.
   *
   * @return true si está activa, false de lo contrario.
   */
  public boolean isActivo() {
    return activo;
  }

  /**
   * Establece el estado de actividad de la etiqueta.
   *
   * @param activo true para activar, false para desactivar.
   */
  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}
