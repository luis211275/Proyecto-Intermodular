package org.example.model;

public class Version {
  private int idVersion;
  private String nombre;
  private Modelo modelo;
  private boolean activo;

  public Version() {
  }

  public Version(int idVersion, String nombre, Modelo modelo, boolean activo) {
    this.idVersion = idVersion;
    this.nombre = nombre;
    this.modelo = modelo;
    this.activo = activo;
  }

  public int getIdVersion() {
    return idVersion;
  }

  public void setIdVersion(int idVersion) {
    this.idVersion = idVersion;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Modelo getModelo() {
    return modelo;
  }

  public void setModelo(Modelo modelo) {
    this.modelo = modelo;
  }

  public boolean isActivo() {
    return activo;
  }

  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}
