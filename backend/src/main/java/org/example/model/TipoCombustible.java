package org.example.model;

public class TipoCombustible {
  private int idCombustible;
  private String nombre;
  private boolean activo;

  public TipoCombustible() {
  }

  public TipoCombustible(int idCombustible, String nombre, boolean activo) {
    this.idCombustible = idCombustible;
    this.nombre = nombre;
    this.activo = activo;
  }

  public int getIdCombustible() {
    return idCombustible;
  }

  public void setIdCombustible(int idCombustible) {
    this.idCombustible = idCombustible;
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
