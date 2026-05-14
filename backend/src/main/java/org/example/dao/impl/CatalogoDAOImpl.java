package org.example.dao.impl;

import org.example.config.DatabaseConfig;
import org.example.dao.CatalogoDAO;
import org.example.exception.ErrorDeAccesoADatosException;
import org.example.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz CatalogoDAO para interactuar con la base de datos.
 */
public class CatalogoDAOImpl implements CatalogoDAO {

    private Connection obtenerConexion() throws ErrorDeAccesoADatosException {
        try {
            return DatabaseConfig.getConnection();
        } catch (Exception e) {
            throw new ErrorDeAccesoADatosException("Error al conectar con la base de datos" + ": " + e.getMessage());
        }
    }

    @Override
    public List<Marca> getMarcas() throws ErrorDeAccesoADatosException {
        List<Marca> marcas = new ArrayList<>();
        String sql = "SELECT ID_MARCA, NOMBRE FROM MARCAS WHERE ACTIVO = TRUE ORDER BY NOMBRE";
        try (Connection conn = obtenerConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                marcas.add(new Marca(rs.getInt("ID_MARCA"), rs.getString("NOMBRE")));
            }
        } catch (SQLException e) {
            throw new ErrorDeAccesoADatosException("Error al obtener marcas" + ": " + e.getMessage());
        }
        return marcas;
    }

    @Override
    public List<Modelo> getModelos(int idMarca) throws ErrorDeAccesoADatosException {
        List<Modelo> modelos = new ArrayList<>();
        String sql = "SELECT ID_MODELO, NOMBRE FROM MODELOS WHERE MARCA_ID = ? AND ACTIVO = TRUE ORDER BY NOMBRE";
        try (Connection conn = obtenerConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idMarca);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Modelo m = new Modelo();
                    m.setIdModelo(rs.getInt("ID_MODELO"));
                    m.setNombre(rs.getString("NOMBRE"));
                    modelos.add(m);
                }
            }
        } catch (SQLException e) {
            throw new ErrorDeAccesoADatosException("Error al obtener modelos" + ": " + e.getMessage());
        }
        return modelos;
    }

    @Override
    public List<Version> getVersiones(int idModelo) throws ErrorDeAccesoADatosException {
        List<Version> versiones = new ArrayList<>();
        String sql = "SELECT ID_VERSION, NOMBRE FROM VERSIONES WHERE MODELO_ID = ? AND ACTIVO = TRUE ORDER BY NOMBRE";
        try (Connection conn = obtenerConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idModelo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Version v = new Version();
                    v.setIdVersion(rs.getInt("ID_VERSION"));
                    v.setNombre(rs.getString("NOMBRE"));
                    versiones.add(v);
                }
            }
        } catch (SQLException e) {
            throw new ErrorDeAccesoADatosException("Error al obtener versiones" + ": " + e.getMessage());
        }
        return versiones;
    }

    @Override
    public List<Ciudad> getCiudades() throws ErrorDeAccesoADatosException {
        List<Ciudad> ciudades = new ArrayList<>();
        String sql = "SELECT ID_CIUDAD, NOMBRE FROM CIUDADES WHERE ACTIVO = TRUE ORDER BY NOMBRE";
        try (Connection conn = obtenerConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ciudad c = new Ciudad();
                c.setIdCiudad(rs.getInt("ID_CIUDAD"));
                c.setNombre(rs.getString("NOMBRE"));
                ciudades.add(c);
            }
        } catch (SQLException e) {
            throw new ErrorDeAccesoADatosException("Error al obtener ciudades" + ": " + e.getMessage());
        }
        return ciudades;
    }

    @Override
    public List<Color> getColores() throws ErrorDeAccesoADatosException {
        List<Color> colores = new ArrayList<>();
        String sql = "SELECT ID_COLOR, NOMBRE FROM COLORES WHERE ACTIVO = TRUE ORDER BY NOMBRE";
        try (Connection conn = obtenerConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Color c = new Color();
                c.setIdColor(rs.getInt("ID_COLOR"));
                c.setNombre(rs.getString("NOMBRE"));
                colores.add(c);
            }
        } catch (SQLException e) {
            throw new ErrorDeAccesoADatosException("Error al obtener colores" + ": " + e.getMessage());
        }
        return colores;
    }

    @Override
    public List<TipoCombustible> getCombustibles() throws ErrorDeAccesoADatosException {
        List<TipoCombustible> lista = new ArrayList<>();
        String sql = "SELECT ID_COMBUSTIBLE, NOMBRE FROM TIPOS_COMBUSTIBLE WHERE ACTIVO = TRUE ORDER BY NOMBRE";
        try (Connection conn = obtenerConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TipoCombustible tc = new TipoCombustible();
                tc.setIdCombustible(rs.getInt("ID_COMBUSTIBLE"));
                tc.setNombre(rs.getString("NOMBRE"));
                lista.add(tc);
            }
        } catch (SQLException e) {
            throw new ErrorDeAccesoADatosException("Error al obtener combustibles" + ": " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<TipoTransmision> getTransmisiones() throws ErrorDeAccesoADatosException {
        List<TipoTransmision> lista = new ArrayList<>();
        String sql = "SELECT ID_TRANSMISION, NOMBRE FROM TIPOS_TRANSMISION WHERE ACTIVO = TRUE ORDER BY NOMBRE";
        try (Connection conn = obtenerConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TipoTransmision tt = new TipoTransmision();
                tt.setIdTransmision(rs.getInt("ID_TRANSMISION"));
                tt.setNombre(rs.getString("NOMBRE"));
                lista.add(tt);
            }
        } catch (SQLException e) {
            throw new ErrorDeAccesoADatosException("Error al obtener transmisiones" + ": " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Categoria> getCategorias() throws ErrorDeAccesoADatosException {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT ID_CATEGORIA, NOMBRE FROM CATEGORIAS WHERE ACTIVO = TRUE ORDER BY NOMBRE";
        try (Connection conn = obtenerConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getInt("ID_CATEGORIA"));
                c.setNombre(rs.getString("NOMBRE"));
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new ErrorDeAccesoADatosException("Error al obtener categorías" + ": " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<EtiquetaAmbiental> getEtiquetas() throws ErrorDeAccesoADatosException {
        List<EtiquetaAmbiental> lista = new ArrayList<>();
        String sql = "SELECT ID_ETIQUETA, NOMBRE FROM ETIQUETAS_AMBIENTALES WHERE ACTIVO = TRUE ORDER BY NOMBRE";
        try (Connection conn = obtenerConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                EtiquetaAmbiental e = new EtiquetaAmbiental();
                e.setIdEtiqueta(rs.getInt("ID_ETIQUETA"));
                e.setNombre(rs.getString("NOMBRE"));
                lista.add(e);
            }
        } catch (SQLException e) {
            throw new ErrorDeAccesoADatosException("Error al obtener etiquetas" + ": " + e.getMessage());
        }
        return lista;
    }
}