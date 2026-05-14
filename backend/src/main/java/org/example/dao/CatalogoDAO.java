package org.example.dao;

import org.example.exception.ErrorDeAccesoADatosException;
import org.example.model.*;

import java.util.List;

/**
 * Interfaz para las operaciones de acceso a datos del catálogo.
 */
public interface CatalogoDAO {
  /**
   * Obtiene la lista de marcas.
   *
   * @return la lista de marcas.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  List<Marca> getMarcas() throws ErrorDeAccesoADatosException;

  /**
   * Obtiene la lista de modelos de una marca.
   *
   * @param idMarca el ID de la marca.
   * @return la lista de modelos.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  List<Modelo> getModelos(int idMarca) throws ErrorDeAccesoADatosException;

  /**
   * Obtiene la lista de versiones de un modelo.
   *
   * @param idModelo el ID del modelo.
   * @return la lista de versiones.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  List<Version> getVersiones(int idModelo) throws ErrorDeAccesoADatosException;

  /**
   * Obtiene la lista de ciudades.
   *
   * @return la lista de ciudades.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  List<Ciudad> getCiudades() throws ErrorDeAccesoADatosException;

  /**
   * Obtiene la lista de colores.
   *
   * @return la lista de colores.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  List<Color> getColores() throws ErrorDeAccesoADatosException;

  /**
   * Obtiene la lista de tipos de combustible.
   *
   * @return la lista de tipos de combustible.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  List<TipoCombustible> getCombustibles() throws ErrorDeAccesoADatosException;

  /**
   * Obtiene la lista de tipos de transmisión.
   *
   * @return la lista de tipos de transmisión.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  List<TipoTransmision> getTransmisiones() throws ErrorDeAccesoADatosException;

  /**
   * Obtiene la lista de categorías.
   *
   * @return la lista de categorías.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  List<Categoria> getCategorias() throws ErrorDeAccesoADatosException;

  /**
   * Obtiene la lista de etiquetas ambientales.
   *
   * @return la lista de etiquetas ambientales.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  List<EtiquetaAmbiental> getEtiquetas() throws ErrorDeAccesoADatosException;
}