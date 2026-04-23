package intermodular.service;

import intermodular.dao.UsuarioDAO;
import intermodular.dao.impl.UsuarioDAOImpl;
import intermodular.exception.ErrorDeAccesoADatosException;
import intermodular.exception.ErrorDeNegocioException;
import intermodular.exception.CredencialesInvalidasException;
import intermodular.exception.DatosIncompletosException;
import intermodular.model.Usuario;

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
