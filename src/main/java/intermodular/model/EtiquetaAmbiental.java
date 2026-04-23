package intermodular.model;

public class EtiquetaAmbiental {
  private int idEtiqueta;
  private String nombre;
  private boolean activo;

  public EtiquetaAmbiental() {
  }

  public EtiquetaAmbiental(int idEtiqueta, String nombre, boolean activo) {
    this.idEtiqueta = idEtiqueta;
    this.nombre = nombre;
    this.activo = activo;
  }

  public int getIdEtiqueta() {
    return idEtiqueta;
  }

  public void setIdEtiqueta(int idEtiqueta) {
    this.idEtiqueta = idEtiqueta;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public boolean isActivo() {
    return activo;
  }

  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}
