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



    public boolean findByTelefono(String telefonoBuscado) {
        boolean found = true;
        String sql = "SELECT id_usuario, nombres FROM usuarios WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Sustituye el ? por el nombre que queremos buscar.
            stmt.setString(1, telefonoBuscado);

            // Ejecuta la consulta SELECT.
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Si existe al menos un usuario con ese nombre...
                System.out.println("Ya existe este telefono en la Base de datos pon otro");
            } else {
                System.out.println("No existe ningún usuario con el telefono: " + telefonoBuscado);
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



    public void listarUsuarios() {

        // Definimos la consulta SQL que queremos ejecutar sobre la base de datos.
        String sql = "SELECT * FROM usuarios";

        // try-with-resources: abre los recursos y los cierra automáticamente al terminar.
        // Establece la conexión con la base de datos usando nuestra clase ConnectionBBDD.
        try (Connection conn = DatabaseConfig.getConnection();
             // Prepara la sentencia SQL para evitar errores y ataques (SQL Injection).
             PreparedStatement stmt = conn.prepareStatement(sql);
             // Ejecuta la consulta y guarda los resultados en un ResultSet.
             ResultSet rs = stmt.executeQuery()) {

            // Itera por cada fila devuelta por la consulta.
            while (rs.next()) {
                // Obtiene los datos de cada columna ("id" y "nombre") y los imprime por consola.
                System.out.println(rs.getString("nombres") + " - " + rs.getString("apellidos")+ " - "+ rs.getString("email") + "-" + rs.getString("dni") + " - "+ rs.getString("password") + "-" + rs.getString("telefono") );
            }

        } catch (Exception e) {
            // Si ocurre cualquier error (conexión, SQL, lectura), se imprime la traza para depurar.
            e.printStackTrace();
        }
    }
}
