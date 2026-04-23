package intermodular.model;

public class Usuario {
  private int idUsuario;
  private String nombres;
  private String apellidos;
  private String dni;
  private String email;
  private String password;
  private String telefono;
  private String tipoUsuario;
  private boolean activo;

  public Usuario() {
  }


  public Usuario(int idUsuario, String nombres, String apellidos, String dni, String email,
                 String password, String telefono, String tipoUsuario, boolean activo) {

    this.idUsuario = idUsuario;
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.dni = dni;
    this.email = email;
    this.password = password;
    this.telefono = telefono;
    this.tipoUsuario = tipoUsuario;
    this.activo = activo;
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

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
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

  public String getTipoUsuario() {
    return tipoUsuario;
  }

  public void setTipoUsuario(String tipoUsuario) {
    this.tipoUsuario = tipoUsuario;
  }

  public boolean isActivo() {
    return activo;
  }

  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}
