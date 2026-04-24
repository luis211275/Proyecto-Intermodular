package org.example.service;

import org.example.dao.UsuarioDAO;
import org.example.dao.impl.UsuarioDAOImpl;
import org.example.exception.ErrorDeAccesoADatosException;
import org.example.exception.ErrorDeNegocioException;
import org.example.exception.CredencialesInvalidasException;
import org.example.exception.DatosIncompletosException;
import org.example.model.Usuario;

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    public Usuario login(String email, String password) throws ErrorDeAccesoADatosException, ErrorDeNegocioException {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new DatosIncompletosException("Email y contraseña son obligatorios");
        }
        Usuario usuario = usuarioDAO.login(email, password);
        if (usuario == null) {
            throw new CredencialesInvalidasException("Email o contraseña incorrectos");
        }
        return usuario;
    }
}
