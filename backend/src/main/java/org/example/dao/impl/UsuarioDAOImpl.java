package org.example.dao.impl;

import org.example.config.ConnectionDataBase;
import org.example.dao.UsuarioDAO;
import org.example.exception.ErrorDeAccesoADatosException;
import org.example.model.Usuario;
import java.sql.*;

public class UsuarioDAOImpl implements UsuarioDAO {

    private Connection obtenerConexion() throws ErrorDeAccesoADatosException {
        try {
            return ConnectionDataBase.obtenerConexion();
        } catch (Exception e) {
            throw new ErrorDeAccesoADatosException("Error al conectar con la base de datos", e);
        }
    }

    @Override
    public Usuario login(String email, String password) throws ErrorDeAccesoADatosException {
        String sql = "SELECT * FROM USUARIOS WHERE EMAIL = ? AND PASSWORD = ? AND ACTIVO = TRUE";
        try (Connection conn = obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("ID_USUARIO"));
                    u.setNombres(rs.getString("NOMBRES"));
                    u.setApellidos(rs.getString("APELLIDOS"));
                    u.setDni(rs.getString("DNI"));
                    u.setEmail(rs.getString("EMAIL"));
                    u.setTelefono(rs.getString("TELEFONO"));
                    u.setTipoUsuario(rs.getString("TIPO_USUARIO"));
                    return u;
                }
            }
        } catch (SQLException e) {
            throw new ErrorDeAccesoADatosException("Error durante el login del usuario", e);
        }
        return null;
    }
}
