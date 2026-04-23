package intermodular.model;

public class TipoTransmision {
  private int idTransmision;
  private String nombre;
  private boolean activo;

  public TipoTransmision() {
  }

  public TipoTransmision(int idTransmision, String nombre, boolean activo) {
    this.idTransmision = idTransmision;
    this.nombre = nombre;
    this.activo = activo;
  }

  public int getIdTransmision() {
    return idTransmision;
  }

  public void setIdTransmision(int idTransmision) {
    this.idTransmision = idTransmision;
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
