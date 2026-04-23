package intermodular.model;

public class Ciudad {
  private int idCiudad;
  private String nombre;
  private boolean activo;

  public Ciudad() {
  }

  public Ciudad(int idCiudad, String nombre, boolean activo) {
    this.idCiudad = idCiudad;
    this.nombre = nombre;
    this.activo = activo;
  }

  public int getIdCiudad() {
    return idCiudad;
  }

  public void setIdCiudad(int idCiudad) {
    this.idCiudad = idCiudad;
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
