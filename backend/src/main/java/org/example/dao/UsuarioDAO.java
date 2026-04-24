package org.example.dao;

import org.example.exception.ErrorDeAccesoADatosException;
import org.example.model.Usuario;

public interface UsuarioDAO {
    Usuario login(String email, String password) throws ErrorDeAccesoADatosException;
}
