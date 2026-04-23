package intermodular.dao;

import intermodular.exception.ErrorDeAccesoADatosException;
import intermodular.model.Usuario;

public interface UsuarioDAO {
    Usuario login(String email, String password) throws ErrorDeAccesoADatosException;
}
