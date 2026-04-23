package intermodular.model;

public class Color {
  private int idColor;
  private String nombre;
  private boolean activo;

  public Color() {
  }

  public Color(int idColor, String nombre, boolean activo) {
    this.idColor = idColor;
    this.nombre = nombre;
    this.activo = activo;
  }

  public int getIdColor() {
    return idColor;
  }

  public void setIdColor(int idColor) {
    this.idColor = idColor;
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
