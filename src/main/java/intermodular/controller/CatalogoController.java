package intermodular.controller;

import intermodular.dao.CatalogoDAO;
import intermodular.dao.impl.CatalogoDAOImpl;
import intermodular.exception.ErrorDeAccesoADatosException;
import intermodular.model.*;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogoController {
    private CatalogoDAO catalogoDAO = new CatalogoDAOImpl();

    public String obtenerMarcas() throws ErrorDeAccesoADatosException {
        List<Marca> marcas = catalogoDAO.getMarcas();
        return "[" + marcas.stream()
                .map(m -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", m.getIdMarca(), m.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

    public String obtenerCiudades() throws ErrorDeAccesoADatosException {
        List<Ciudad> ciudades = catalogoDAO.getCiudades();
        return "[" + ciudades.stream()
                .map(c -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", c.getIdCiudad(), c.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

    public String obtenerCombustibles() throws ErrorDeAccesoADatosException {
        List<TipoCombustible> lista = catalogoDAO.getCombustibles();
        return "[" + lista.stream()
                .map(tc -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", tc.getIdCombustible(), tc.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

    public String obtenerTransmisiones() throws ErrorDeAccesoADatosException {
        List<TipoTransmision> lista = catalogoDAO.getTransmisiones();
        return "[" + lista.stream()
                .map(tt -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", tt.getIdTransmision(), tt.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

    public String obtenerCategorias() throws ErrorDeAccesoADatosException {
        List<Categoria> lista = catalogoDAO.getCategorias();
        return "[" + lista.stream()
                .map(c -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", c.getIdCategoria(), c.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

    public String obtenerColores() throws ErrorDeAccesoADatosException {
        List<Color> lista = catalogoDAO.getColores();
        return "[" + lista.stream()
                .map(c -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", c.getIdColor(), c.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

    public String obtenerEtiquetas() throws ErrorDeAccesoADatosException {
        List<EtiquetaAmbiental> lista = catalogoDAO.getEtiquetas();
        return "[" + lista.stream()
                .map(e -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", e.getIdEtiqueta(), e.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

    public String obtenerModelos(int marcaId) throws ErrorDeAccesoADatosException {
        List<Modelo> lista = catalogoDAO.getModelos(marcaId);
        return "[" + lista.stream()
                .map(m -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", m.getIdModelo(), m.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

    public String obtenerVersiones(int modeloId) throws ErrorDeAccesoADatosException {
        List<Version> lista = catalogoDAO.getVersiones(modeloId);
        return "[" + lista.stream()
                .map(v -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", v.getIdVersion(), v.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }
}
