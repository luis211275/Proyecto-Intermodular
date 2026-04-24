package org.example.dao;

import org.example.exception.ErrorDeAccesoADatosException;
import org.example.model.Coche;
import java.util.Map;
import java.util.List;

public interface CocheDAO {
    List<Coche> listarCochesDisponibles(Map<String, String> filtros) throws ErrorDeAccesoADatosException;
    Coche buscarPorId(int id) throws ErrorDeAccesoADatosException;
    int insertarCocheNuevo(Coche c) throws ErrorDeAccesoADatosException;
    boolean actualizarEstadoAVendido(int id) throws ErrorDeAccesoADatosException;
    boolean desactivarAnuncioPorEliminacion(int id) throws ErrorDeAccesoADatosException;
    boolean cambiarEstado(int id, String nuevoEstado) throws ErrorDeAccesoADatosException;

    // Favoritos
    void agregarFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException;
    void eliminarFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException;
    List<Coche> listarFavoritos(int usuarioId) throws ErrorDeAccesoADatosException;
    boolean esFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException;
}
