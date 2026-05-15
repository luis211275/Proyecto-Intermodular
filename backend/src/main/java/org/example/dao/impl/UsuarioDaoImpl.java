package org.example.dao.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.config.DatabaseConfig;
import org.example.dao.UsuarioDao;
import org.example.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementación de la interfaz UsuarioDao para interactuar con la base de datos.
 */
public class UsuarioDaoImpl implements UsuarioDao {

  @Override
  public boolean existePorEmail(String emailBuscado) {
    boolean found = true;
    String sql = "SELECT id_usuario, nombres FROM usuarios WHERE email = ?";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      // Pasamos el valor.
      stmt.setString(1, emailBuscado);

      // Lanzamos la consulta.
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        // Revisamos si existe.
        System.out.println("Ya existe este email en la Base de datos pon otro");
      } else {
        System.out.println("No existe ningún usuario con el email: " + emailBuscado);
        found = false;

      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return found;
  }

  @Override
  public boolean existePorDni(String dniBuscado) {
    boolean found = true;
    String sql = "SELECT id_usuario, nombres FROM usuarios WHERE dni = ?";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      // Pasamos el valor.
      stmt.setString(1, dniBuscado);

      // Lanzamos la consulta.
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        // Revisamos si existe.
        System.out.println("Ya existe este DNI en la Base de datos pon otro");
      } else {
        System.out.println("No existe ningún usuario con el DNI: " + dniBuscado);
        found = false;

      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return found;
  }

  @Override
  public int validarLogin(String body) {

    JsonObject json = JsonParser.parseString(body).getAsJsonObject();
    String email = json.get("email").getAsString();
    String password = json.get("password").getAsString();

    String sql = "SELECT password FROM usuarios WHERE email = ?";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, email);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        // Revisamos la clave.
        String contraseña = rs.getString("password");

        if (contraseña.equals(password)) {
          return 0;
        } else {
          return 2; // Marcamos clave incorrecta.
        }
      } else {
        return 1;// Marcamos email ausente.
      }
    } catch (Exception e) {
      e.printStackTrace();
      return 3;// Marcamos error de datos.
    }

  }

  @Override
  public Usuario obtenerUsuarioPorEmail(String email) {
    String sql = "SELECT id_usuario, nombres, apellidos, email, dni, telefono FROM usuarios WHERE email = ?";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, email);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setNombres(rs.getString("nombres"));
        usuario.setApellidos(rs.getString("apellidos"));
        usuario.setEmail(rs.getString("email"));
        usuario.setDni(rs.getString("dni"));
        usuario.setTelefono(rs.getString("telefono"));
        return usuario;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public Usuario obtenerUsuarioPorId(int idUsuario) {
    String sql = "SELECT id_usuario, nombres, apellidos, email, dni, telefono FROM usuarios WHERE id_usuario = ?";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, idUsuario);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setNombres(rs.getString("nombres"));
        usuario.setApellidos(rs.getString("apellidos"));
        usuario.setEmail(rs.getString("email"));
        usuario.setDni(rs.getString("dni"));
        usuario.setTelefono(rs.getString("telefono"));
        return usuario;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public void insertarUsuario(String nombre, String apellido, String email, String dni, String telefono,
      String password) {
    String sql = "INSERT INTO usuarios (nombres, apellidos, email, dni, telefono, password) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, nombre);
      stmt.setString(2, apellido);
      stmt.setString(3, email);
      stmt.setString(4, dni);
      stmt.setString(5, telefono);
      stmt.setString(6, password);

      stmt.executeUpdate();
      System.out.println("Usuario insertado en DB: " + nombre);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}