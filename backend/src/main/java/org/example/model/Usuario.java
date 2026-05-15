package org.example.model;

/**
 * Representa un usuario del sistema.
 */
public class Usuario {
  private int idUsuario;
  private String nombres;
  private String apellidos;
  private String email;
  private String password;
  private String telefono;
  private String dni;

  /**
   * Instancia un nuevo Usuario vacío.
   */
  public Usuario() {
  }

  /**
   * Instancia un nuevo Usuario con datos.
   *
   * @param idUsuario el ID del usuario.
   * @param nombre    los nombres del usuario.
   * @param apellido  los apellidos del usuario.
   * @param email     el email del usuario.
   * @param password  la contraseña del usuario.
   * @param telefono  el teléfono del usuario.
   * @param dni       el DNI del usuario.
   */
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

  /**
   * Obtiene el ID del usuario.
   *
   * @return el ID del usuario.
   */
  public int getIdUsuario() {
    return idUsuario;
  }

  /**
   * Establece el ID del usuario.
   *
   * @param idUsuario el ID del usuario.
   */
  public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
  }

  /**
   * Obtiene los nombres del usuario.
   *
   * @return los nombres del usuario.
   */
  public String getNombres() {
    return nombres;
  }

  /**
   * Establece los nombres del usuario.
   *
   * @param nombres los nombres del usuario.
   */
  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  /**
   * Obtiene los apellidos del usuario.
   *
   * @return los apellidos del usuario.
   */
  public String getApellidos() {
    return apellidos;
  }

  /**
   * Establece los apellidos del usuario.
   *
   * @param apellidos los apellidos del usuario.
   */
  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  /**
   * Obtiene el email del usuario.
   *
   * @return el email del usuario.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Establece el email del usuario.
   *
   * @param email el email del usuario.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Obtiene la contraseña del usuario.
   *
   * @return la contraseña del usuario.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Establece la contraseña del usuario.
   *
   * @param password la contraseña del usuario.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Obtiene el teléfono del usuario.
   *
   * @return el teléfono del usuario.
   */
  public String getTelefono() {
    return telefono;
  }

  /**
   * Establece el teléfono del usuario.
   *
   * @param telefono el teléfono del usuario.
   */
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  /**
   * Obtiene el DNI del usuario.
   *
   * @return el DNI del usuario.
   */
  public String getDni() {
    return dni;
  }

  /**
   * Establece el DNI del usuario.
   *
   * @param dni el DNI del usuario.
   */
  public void setDni(String dni) {
    this.dni = dni;
  }
}
