package intermodular.model;

public class Categoria {
  private int idCategoria;
  private String nombre;
  private boolean activo;

  public Categoria() {
  }

  public Categoria(int idCategoria, String nombre, boolean activo) {
    this.idCategoria = idCategoria;
    this.nombre = nombre;
    this.activo = activo;
  }

  public int getIdCategoria() {
    return idCategoria;
  }

  public void setIdCategoria(int idCategoria) {
    this.idCategoria = idCategoria;
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
