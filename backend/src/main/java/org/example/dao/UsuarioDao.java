package org.example.dao;

public interface UsuarioDao {

  boolean findByEmail(String emailBuscado);

  boolean findByDni(String dniBuscado);

  boolean findByTelefono(String telefonoBuscado);

  int validarLogin(String body);

  void insertarUsuario(String nombre, String apellido, String email, String dni, String telefono, String password);

  void listarUsuarios();
}