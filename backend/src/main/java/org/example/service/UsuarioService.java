package org.example.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.dao.UsuarioDao;
import org.example.dao.impl.UsuarioDaoImpl;

/**
 * Servicio para gestionar la lógica de negocio de los usuarios.
 */
public class UsuarioService {
  private UsuarioDao usuarioDao = new UsuarioDaoImpl();

  /**
   * Procesa el registro de un nuevo usuario.
   *
   * @param body los datos del usuario en formato JSON.
   * @return 0 si el registro es exitoso, 1 si el email ya existe, 2 si el DNI ya existe.
   */
  public int procesarRegistro(String body) {
    JsonObject json = new JsonParser().parse(body).getAsJsonObject();

    String nombre = json.get("nombres").getAsString();
    String apellido = json.get("apellidos").getAsString();
    String email = json.get("email").getAsString();
    String dni = json.get("dni").getAsString();
    String telefono = json.get("telefono").getAsString();
    String password = json.get("password").getAsString();

    if (usuarioDao.existePorEmail(email)) {
      return 1;
    }
    if (usuarioDao.existePorDni(dni)) {
      return 2;
    }
    usuarioDao.insertarUsuario(nombre, apellido, email, dni, telefono, password);
    return 0;
  }
}