package org.example.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.dao.UsuarioDao;
import org.example.dao.impl.UsuarioDaoImpl;

public class UsuarioService {
  private UsuarioDao usuarioDao = new UsuarioDaoImpl();

  public int procesarRegistro(String body) {
    JsonObject json = new JsonParser().parse(body).getAsJsonObject();

    String nombre = json.get("nombres").getAsString();
    String apellido = json.get("apellidos").getAsString();
    String email = json.get("email").getAsString();
    String dni = json.get("dni").getAsString();
    String telefono = json.get("telefono").getAsString();
    String password = json.get("password").getAsString();

    if (usuarioDao.findByEmail(email)) {
      return 1;
    }
    if (usuarioDao.findByDni(dni)) {
      return 2;
    }
    usuarioDao.insertarUsuario(nombre, apellido, email, dni, telefono, password);
    return 0;
  }
}