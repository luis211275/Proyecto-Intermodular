package org.example.model;

public class Usuario {
  private int idUsuario;
  private String nombres;
  private String apellidos;
  private String email;
  private String password;
  private String telefono;
  private String dni;

  public Usuario() {
  }

  public Usuario(int idUsuario, String nombre, String apellido, String email, String password, String telefono,
      String dni) {
    this.idUsuario = idUsuario;
    this.nombres = nombre;
    this.apellidos = apellido;
    this.email = email;
    this.password = password;
    this.telefono = telefono;
    this.dni = dni;
  }

  public int getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }
}