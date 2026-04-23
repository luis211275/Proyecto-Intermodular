package intermodular.model;

public class Modelo {
  private int idModelo;
  private String nombre;
  private Marca marca;
  private boolean activo;

  public Modelo() {
  }

  public Modelo(int idModelo, String nombre, Marca marca, boolean activo) {
    this.idModelo = idModelo;
    this.nombre = nombre;
    this.marca = marca;
    this.activo = activo;
  }

  public int getIdModelo() {
    return idModelo;
  }

  public void setIdModelo(int idModelo) {
    this.idModelo = idModelo;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Marca getMarca() {
    return marca;
  }

  public void setMarca(Marca marca) {
    this.marca = marca;
  }

  public boolean isActivo() {
    return activo;
  }

  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}
