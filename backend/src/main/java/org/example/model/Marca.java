package org.example.model;

public class Marca {
  private int idMarca;
  private String nombre;
  private boolean activo;

  public Marca() {
  }

  public Marca(int idMarca, String nombre) {
    this.idMarca = idMarca;
    this.nombre = nombre;
  }

  public int getIdMarca() {
    return idMarca;
  }

  public void setIdMarca(int idMarca) {
    this.idMarca = idMarca;
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
