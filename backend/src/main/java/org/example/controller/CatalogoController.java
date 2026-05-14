package org.example.controller;

import org.example.dao.CatalogoDAO;
import org.example.dao.impl.CatalogoDAOImpl;
import org.example.exception.ErrorDeAccesoADatosException;
import org.example.model.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar las operaciones relacionadas con los catálogos.
 */
public class CatalogoController {
    private CatalogoDAO catalogoDAO = new CatalogoDAOImpl();

  /**
   * Obtiene la lista de marcas.
   *
   * @return una cadena JSON con la lista de marcas.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public String obtenerMarcas() throws ErrorDeAccesoADatosException {
        List<Marca> marcas = catalogoDAO.getMarcas();
        return "[" + marcas.stream()
                .map(m -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", m.getIdMarca(), m.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

  /**
   * Obtiene la lista de ciudades.
   *
   * @return una cadena JSON con la lista de ciudades.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public String obtenerCiudades() throws ErrorDeAccesoADatosException {
        List<Ciudad> ciudades = catalogoDAO.getCiudades();
        return "[" + ciudades.stream()
                .map(c -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", c.getIdCiudad(), c.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

  /**
   * Obtiene la lista de tipos de combustible.
   *
   * @return una cadena JSON con la lista de tipos de combustible.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public String obtenerCombustibles() throws ErrorDeAccesoADatosException {
        List<TipoCombustible> lista = catalogoDAO.getCombustibles();
        return "[" + lista.stream()
                .map(tc -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", tc.getIdCombustible(), tc.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

  /**
   * Obtiene la lista de tipos de transmisión.
   *
   * @return una cadena JSON con la lista de tipos de transmisión.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public String obtenerTransmisiones() throws ErrorDeAccesoADatosException {
        List<TipoTransmision> lista = catalogoDAO.getTransmisiones();
        return "[" + lista.stream()
                .map(tt -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", tt.getIdTransmision(), tt.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

  /**
   * Obtiene la lista de categorías.
   *
   * @return una cadena JSON con la lista de categorías.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public String obtenerCategorias() throws ErrorDeAccesoADatosException {
        List<Categoria> lista = catalogoDAO.getCategorias();
        return "[" + lista.stream()
                .map(c -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", c.getIdCategoria(), c.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

  /**
   * Obtiene la lista de colores.
   *
   * @return una cadena JSON con la lista de colores.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public String obtenerColores() throws ErrorDeAccesoADatosException {
        List<Color> lista = catalogoDAO.getColores();
        return "[" + lista.stream()
                .map(c -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", c.getIdColor(), c.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

  /**
   * Obtiene la lista de etiquetas ambientales.
   *
   * @return una cadena JSON con la lista de etiquetas ambientales.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public String obtenerEtiquetas() throws ErrorDeAccesoADatosException {
        List<EtiquetaAmbiental> lista = catalogoDAO.getEtiquetas();
        return "[" + lista.stream()
                .map(e -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", e.getIdEtiqueta(), e.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

  /**
   * Obtiene la lista de modelos de una marca específica.
   *
   * @param marcaId el ID de la marca.
   * @return una cadena JSON con la lista de modelos.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public String obtenerModelos(int marcaId) throws ErrorDeAccesoADatosException {
        List<Modelo> lista = catalogoDAO.getModelos(marcaId);
        return "[" + lista.stream()
                .map(m -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", m.getIdModelo(), m.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }

  /**
   * Obtiene la lista de versiones de un modelo específico.
   *
   * @param modeloId el ID del modelo.
   * @return una cadena JSON con la lista de versiones.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public String obtenerVersiones(int modeloId) throws ErrorDeAccesoADatosException {
        List<Version> lista = catalogoDAO.getVersiones(modeloId);
        return "[" + lista.stream()
                .map(v -> String.format("{\"id\":%d,\"nombre\":\"%s\"}", v.getIdVersion(), v.getNombre()))
                .collect(Collectors.joining(",")) + "]";
    }
}