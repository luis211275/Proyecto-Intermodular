package org.example.dao;

import org.example.model.Usuario;

public interface UsuarioDao {

  boolean findByEmail(String emailBuscado);

  boolean findByDni(String dniBuscado);

  boolean findByTelefono(String telefonoBuscado);

  int validarLogin(String body);

  int obtenerIdUsuarioPorEmail(String email);

  Usuario obtenerUsuarioPorEmail(String email);

  Usuario obtenerUsuarioPorId(int idUsuario);

  void insertarUsuario(String nombre, String apellido, String email, String dni, String telefono, String password);

  void listarUsuarios();
}
