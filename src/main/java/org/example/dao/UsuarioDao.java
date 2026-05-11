package org.example.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {

    public boolean findByEmail(String emailBuscado) {
        boolean found = true;
        String sql = "SELECT id_usuario, nombres FROM usuarios WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Sustituye el ? por el nombre que queremos buscar.
            stmt.setString(1, emailBuscado);

            // Ejecuta la consulta SELECT.
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Si existe al menos un usuario con ese nombre...
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



    public boolean findByDni(String dniBuscado) {
        boolean found = true;
        String sql = "SELECT id_usuario, nombres FROM usuarios WHERE dni = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Sustituye el ? por el nombre que queremos buscar.
            stmt.setString(1, dniBuscado);

            // Ejecuta la consulta SELECT.
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Si existe al menos un usuario con ese nombre...
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






    public int validarLogin(String body){

        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
        String email = json.get("email").getAsString();
        String password = json.get("password").getAsString();

        String sql = "SELECT password FROM usuarios WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //email existe, comprobamos contraseña
                String contraseña = rs.getString("password");

                if (contraseña.equals(password)) {
                    return 0;
                }else  {
                    return 2; //contraseña mal introducida
                }
            }else {
                return 1;//Email inexistente en la base de datos
            }
        }catch(Exception e){
            e.printStackTrace();
            return 3;//error en la base de datos
        }

    }




    public void insertarUsuario(String nombre, String apellido, String email, String dni, String telefono, String password) {
        String sql = "INSERT INTO usuarios (nombres, apellidos, email, dni, telefono, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String passwordEncriptada = BCrypt.withDefaults().hashToString(12, password.toCharArray());


            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, email);
            stmt.setString(4, dni);
            stmt.setString(5, telefono);
            stmt.setString(6, passwordEncriptada);

            stmt.executeUpdate();
            System.out.println("Usuario insertado en DB: " + nombre);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
