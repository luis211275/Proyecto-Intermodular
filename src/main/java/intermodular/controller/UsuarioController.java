package intermodular.controller;

import intermodular.exception.ErrorDeAccesoADatosException;
import intermodular.exception.ErrorDeNegocioException;
import intermodular.model.Usuario;
import intermodular.service.UsuarioService;

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
