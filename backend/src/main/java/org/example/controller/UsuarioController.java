package org.example.controller;

import org.example.exception.ErrorDeAccesoADatosException;
import org.example.exception.ErrorDeNegocioException;
import org.example.model.Usuario;
import org.example.service.UsuarioService;

public class UsuarioController {
    private UsuarioService usuarioService = new UsuarioService();

    public String iniciarSesion(String email, String password) throws ErrorDeAccesoADatosException, ErrorDeNegocioException {
        Usuario u = usuarioService.login(email, password);
        return String.format(
            "{\"id\":%d,\"nombre\":\"%s\",\"apellido\":\"%s\",\"dni\":\"%s\",\"email\":\"%s\",\"telefono\":\"%s\",\"tipo\":\"%s\"}",
            u.getIdUsuario(), u.getNombres(), u.getApellidos(), u.getDni(), u.getEmail(), u.getTelefono(), u.getTipoUsuario()
        );
    }
}
