package org.example.dao;

import org.example.exception.ErrorDeAccesoADatosException;
import org.example.model.*;

import java.util.List;

public interface CatalogoDAO {
    List<Marca> getMarcas() throws ErrorDeAccesoADatosException;
    List<Modelo> getModelos(int idMarca) throws ErrorDeAccesoADatosException;
    List<Version> getVersiones(int idModelo) throws ErrorDeAccesoADatosException;
    List<Ciudad> getCiudades() throws ErrorDeAccesoADatosException;
    List<Color> getColores() throws ErrorDeAccesoADatosException;
    List<TipoCombustible> getCombustibles() throws ErrorDeAccesoADatosException;
    List<TipoTransmision> getTransmisiones() throws ErrorDeAccesoADatosException;
    List<Categoria> getCategorias() throws ErrorDeAccesoADatosException;
    List<EtiquetaAmbiental> getEtiquetas() throws ErrorDeAccesoADatosException;
}
