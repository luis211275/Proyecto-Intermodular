package org.example.dao;

import org.example.model.Usuario;

/**
 * Interfaz para las operaciones de acceso a datos de los usuarios.
 */
public interface UsuarioDao {

  /**
   * Comprueba si un usuario existe por su email.
   *
   * @param emailBuscado el email a buscar.
   * @return true si existe, false en caso contrario.
   */
  boolean existePorEmail(String emailBuscado);

  /**
   * Comprueba si un usuario existe por su DNI.
   *
   * @param dniBuscado el DNI a buscar.
   * @return true si existe, false en caso contrario.
   */
  boolean existePorDni(String dniBuscado);

  /**
   * Valida las credenciales de inicio de sesión de un usuario.
   *
   * @param body los datos de inicio de sesión en formato JSON.
   * @return 0 si el inicio de sesión es exitoso, un código de error de lo contrario.
   */
  int validarLogin(String body);

  /**
   * Obtiene un usuario por su email.
   *
   * @param email el email del usuario.
   * @return el usuario encontrado o null.
   */
  Usuario obtenerUsuarioPorEmail(String email);

  /**
   * Obtiene un usuario por su ID.
   *
   * @param idUsuario el ID del usuario.
   * @return el usuario encontrado o null.
   */
  Usuario obtenerUsuarioPorId(int idUsuario);

  /**
   * Inserta un nuevo usuario en la base de datos.
   *
   * @param nombre   el nombre del usuario.
   * @param apellido el apellido del usuario.
   * @param email    el email del usuario.
   * @param dni      el DNI del usuario.
   * @param telefono el teléfono del usuario.
   * @param password la contraseña del usuario.
   */
  void insertarUsuario(String nombre, String apellido, String email, String dni, String telefono, String password);

}