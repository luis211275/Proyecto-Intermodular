package intermodular.dao.impl;

import intermodular.config.ConnectionDataBase;
import intermodular.dao.CocheDAO;
import intermodular.exception.ErrorDeAccesoADatosException;
import intermodular.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CocheDAOImpl implements CocheDAO {

  private Connection obtenerConexion() throws ErrorDeAccesoADatosException {
    try {
      return ConnectionDataBase.obtenerConexion();
    } catch (Exception e) {
      throw new ErrorDeAccesoADatosException("Error al conectar con la base de datos", e);
    }
  }

  @Override
  public List<Coche> listarCochesDisponibles(Map<String, String> filtros) throws ErrorDeAccesoADatosException {
    List<Coche> coches = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT " +
        "c.ID_COCHE, c.ANIO_FABRICACION, c.KILOMETRAJE, c.PRECIO_VENTA, c.ESTADO, c.FECHA_PUBLICACION, c.URL_IMAGEN, " +
        "(CASE WHEN c.ESTADO = 'Desactivado' THEN FALSE ELSE TRUE END) AS ACTIVO, " +
        "v.NOMBRE as version_nombre, m.NOMBRE as modelo_nombre, ma.NOMBRE as marca_nombre, " +
        "ciu.NOMBRE as ciudad_nombre, col.NOMBRE as color_nombre, comb.NOMBRE as combustible_nombre, " +
        "trans.NOMBRE as transmision_nombre, etiq.NOMBRE as etiqueta_nombre, cat.NOMBRE as categoria_nombre, " +
        "u.NOMBRES as vendedor_nombre, u.APELLIDOS as vendedor_apellidos, u.EMAIL as vendedor_email, u.DNI as vendedor_dni, u.TELEFONO as vendedor_telefono " +
        "FROM COCHES c " +
        "JOIN VERSIONES v ON c.VERSION_ID = v.ID_VERSION " +
        "JOIN MODELOS m ON v.MODELO_ID = m.ID_MODELO " +
        "JOIN MARCAS ma ON m.MARCA_ID = ma.ID_MARCA " +
        "JOIN CIUDADES ciu ON c.CIUDAD_ID = ciu.ID_CIUDAD " +
        "JOIN COLORES col ON c.COLOR_ID = col.ID_COLOR " +
        "JOIN TIPOS_COMBUSTIBLE comb ON c.COMBUSTIBLE_ID = comb.ID_COMBUSTIBLE " +
        "JOIN TIPOS_TRANSMISION trans ON c.TRANSMISION_ID = trans.ID_TRANSMISION " +
        "JOIN ETIQUETAS_AMBIENTALES etiq ON c.ETIQUETA_ID = etiq.ID_ETIQUETA " +
        "JOIN CATEGORIAS cat ON c.CATEGORIA_ID = cat.ID_CATEGORIA " +
        "JOIN USUARIOS u ON c.VENDEDOR_ID = u.ID_USUARIO " +
        "WHERE c.ESTADO = 'Disponible' ");

    List<Object> params = new ArrayList<>();
    if (filtros != null) {
      if (filtros.containsKey("marcaId")) {
        sql.append(" AND ma.ID_MARCA = ?");
        params.add(Integer.parseInt(filtros.get("marcaId")));
      }
      if (filtros.containsKey("modeloId")) {
        sql.append(" AND m.ID_MODELO = ?");
        params.add(Integer.parseInt(filtros.get("modeloId")));
      }
      if (filtros.containsKey("combustibleId")) {
        sql.append(" AND c.COMBUSTIBLE_ID = ?");
        params.add(Integer.parseInt(filtros.get("combustibleId")));
      }
      if (filtros.containsKey("ciudadId")) {
        sql.append(" AND c.CIUDAD_ID = ?");
        params.add(Integer.parseInt(filtros.get("ciudadId")));
      }
      if (filtros.containsKey("precioMax")) {
        sql.append(" AND c.PRECIO_VENTA <= ?");
        params.add(new java.math.BigDecimal(filtros.get("precioMax")));
      }
    }

    sql.append(" ORDER BY c.FECHA_PUBLICACION DESC");

    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
      for (int i = 0; i < params.size(); i++) {
        pstmt.setObject(i + 1, params.get(i));
      }
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          coches.add(mapResultSetToCoche(rs));
        }
      }
    } catch (SQLException e) {
      throw new ErrorDeAccesoADatosException("Error al listar coches disponibles", e);
    }
    return coches;
  }

  @Override
  public Coche buscarPorId(int id) throws ErrorDeAccesoADatosException {
    String sql = "SELECT " +
        "c.ID_COCHE, c.ANIO_FABRICACION, c.KILOMETRAJE, c.PRECIO_VENTA, c.ESTADO, c.FECHA_PUBLICACION, c.URL_IMAGEN, " +
        "(CASE WHEN c.ESTADO = 'Desactivado' THEN FALSE ELSE TRUE END) AS ACTIVO, " +
        "v.NOMBRE as version_nombre, m.NOMBRE as modelo_nombre, ma.NOMBRE as marca_nombre, " +
        "ciu.NOMBRE as ciudad_nombre, col.NOMBRE as color_nombre, comb.NOMBRE as combustible_nombre, " +
        "trans.NOMBRE as transmision_nombre, etiq.NOMBRE as etiqueta_nombre, cat.NOMBRE as categoria_nombre, " +
        "u.NOMBRES as vendedor_nombre, u.APELLIDOS as vendedor_apellidos, u.EMAIL as vendedor_email, u.DNI as vendedor_dni, u.TELEFONO as vendedor_telefono " +
        "FROM COCHES c " +
        "JOIN VERSIONES v ON c.VERSION_ID = v.ID_VERSION " +
        "JOIN MODELOS m ON v.MODELO_ID = m.ID_MODELO " +
        "JOIN MARCAS ma ON m.MARCA_ID = ma.ID_MARCA " +
        "JOIN CIUDADES ciu ON c.CIUDAD_ID = ciu.ID_CIUDAD " +
        "JOIN COLORES col ON c.COLOR_ID = col.ID_COLOR " +
        "JOIN TIPOS_COMBUSTIBLE comb ON c.COMBUSTIBLE_ID = comb.ID_COMBUSTIBLE " +
        "JOIN TIPOS_TRANSMISION trans ON c.TRANSMISION_ID = trans.ID_TRANSMISION " +
        "JOIN ETIQUETAS_AMBIENTALES etiq ON c.ETIQUETA_ID = etiq.ID_ETIQUETA " +
        "JOIN CATEGORIAS cat ON c.CATEGORIA_ID = cat.ID_CATEGORIA " +
        "JOIN USUARIOS u ON c.VENDEDOR_ID = u.ID_USUARIO " +
        "WHERE c.ID_COCHE = ?";

    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          return mapResultSetToCoche(rs);
        }
      }
    } catch (SQLException e) {
      throw new ErrorDeAccesoADatosException("Error al buscar coche por ID", e);
    }
    return null;
  }

  private Coche mapResultSetToCoche(ResultSet rs) throws SQLException {
    Coche c = new Coche();
    c.setIdCoche(rs.getInt("ID_COCHE"));
    c.setAnioFabricacion(rs.getInt("ANIO_FABRICACION"));
    c.setKilometraje(rs.getInt("KILOMETRAJE"));
    c.setPrecioVenta(rs.getBigDecimal("PRECIO_VENTA"));
    c.setEstado(rs.getString("ESTADO"));
    c.setFechaPublicacion(rs.getTimestamp("FECHA_PUBLICACION"));
    c.setUrlImagen(rs.getString("URL_IMAGEN"));
    c.setActivo(rs.getBoolean("ACTIVO"));

    Marca marca = new Marca(0, rs.getString("marca_nombre"));
    Modelo modelo = new Modelo();
    modelo.setNombre(rs.getString("modelo_nombre"));
    modelo.setMarca(marca);
    Version version = new Version();
    version.setNombre(rs.getString("version_nombre"));
    version.setModelo(modelo);
    c.setVersion(version);

    Ciudad ciudad = new Ciudad();
    ciudad.setNombre(rs.getString("ciudad_nombre"));
    c.setCiudad(ciudad);

    Color color = new Color();
    color.setNombre(rs.getString("color_nombre"));
    c.setColor(color);

    TipoCombustible comb = new TipoCombustible();
    comb.setNombre(rs.getString("combustible_nombre"));
    c.setCombustible(comb);

    TipoTransmision trans = new TipoTransmision();
    trans.setNombre(rs.getString("transmision_nombre"));
    c.setTransmision(trans);

    EtiquetaAmbiental etiq = new EtiquetaAmbiental();
    etiq.setNombre(rs.getString("etiqueta_nombre"));
    c.setEtiqueta(etiq);

    Categoria cat = new Categoria();
    cat.setNombre(rs.getString("categoria_nombre"));
    c.setCategoria(cat);

    Usuario vend = new Usuario();
    vend.setNombres(rs.getString("vendedor_nombre"));
    vend.setApellidos(rs.getString("vendedor_apellidos"));
    vend.setEmail(rs.getString("vendedor_email"));
    vend.setDni(rs.getString("vendedor_dni"));
    vend.setTelefono(rs.getString("vendedor_telefono"));
    c.setVendedor(vend);

    return c;
  }

  @Override
  public int insertarCocheNuevo(Coche c) throws ErrorDeAccesoADatosException {
    String sql = "INSERT INTO COCHES (ANIO_FABRICACION, KILOMETRAJE, PRECIO_VENTA, URL_IMAGEN, VERSION_ID, COMBUSTIBLE_ID, TRANSMISION_ID, CIUDAD_ID, COLOR_ID, ETIQUETA_ID, CATEGORIA_ID, VENDEDOR_ID, ESTADO) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      pstmt.setInt(1, c.getAnioFabricacion());
      pstmt.setInt(2, c.getKilometraje());
      pstmt.setBigDecimal(3, c.getPrecioVenta());
      pstmt.setString(4, c.getUrlImagen());
      pstmt.setInt(5, c.getVersion().getIdVersion());
      pstmt.setInt(6, c.getCombustible().getIdCombustible());
      pstmt.setInt(7, c.getTransmision().getIdTransmision());
      pstmt.setInt(8, c.getCiudad().getIdCiudad());
      pstmt.setInt(9, c.getColor().getIdColor());
      pstmt.setInt(10, c.getEtiqueta().getIdEtiqueta());
      pstmt.setInt(11, c.getCategoria().getIdCategoria());
      pstmt.setInt(12, c.getVendedor().getIdUsuario());
      pstmt.setString(13, c.getEstado() != null ? c.getEstado() : "Disponible");

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows == 0) {
        throw new ErrorDeAccesoADatosException("No se pudo insertar el coche.");
      }

      try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          return generatedKeys.getInt(1);
        } else {
          throw new ErrorDeAccesoADatosException("No se obtuvo el ID generado.");
        }
      }
    } catch (SQLException e) {
      throw new ErrorDeAccesoADatosException("Error al insertar coche", e);
    }
  }

  @Override
  public boolean cambiarEstado(int id, String nuevoEstado) throws ErrorDeAccesoADatosException {
    String sql = "UPDATE COCHES SET ESTADO = ? WHERE ID_COCHE = ?";
    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, nuevoEstado);
      pstmt.setInt(2, id);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new ErrorDeAccesoADatosException("Error al cambiar estado del coche", e);
    }
  }

  @Override
  public boolean actualizarEstadoAVendido(int id) throws ErrorDeAccesoADatosException {
    String sql = "UPDATE COCHES SET ESTADO = 'Vendido' WHERE ID_COCHE = ?";
    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new ErrorDeAccesoADatosException("Error al marcar como vendido", e);
    }
  }

  @Override
  public boolean desactivarAnuncioPorEliminacion(int id) throws ErrorDeAccesoADatosException {
    String sql = "UPDATE COCHES SET ESTADO = 'Desactivado' WHERE ID_COCHE = ?";
    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new ErrorDeAccesoADatosException("Error al desactivar anuncio", e);
    }
  }

  @Override
  public void agregarFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException {
    String sql = "INSERT INTO FAVORITOS (USUARIO_ID, COCHE_ID) VALUES (?, ?) ON CONFLICT DO NOTHING";
    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, usuarioId);
      pstmt.setInt(2, cocheId);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new ErrorDeAccesoADatosException("Error al agregar favorito", e);
    }
  }

  @Override
  public void eliminarFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException {
    String sql = "DELETE FROM FAVORITOS WHERE USUARIO_ID = ? AND COCHE_ID = ?";
    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, usuarioId);
      pstmt.setInt(2, cocheId);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new ErrorDeAccesoADatosException("Error al eliminar favorito", e);
    }
  }

  @Override
  public List<Coche> listarFavoritos(int usuarioId) throws ErrorDeAccesoADatosException {
    List<Coche> coches = new ArrayList<>();
    String sql = "SELECT " +
        "c.ID_COCHE, c.ANIO_FABRICACION, c.KILOMETRAJE, c.PRECIO_VENTA, c.ESTADO, c.FECHA_PUBLICACION, c.URL_IMAGEN, " +
        "TRUE AS ACTIVO, " +
        "v.NOMBRE as version_nombre, m.NOMBRE as modelo_nombre, ma.NOMBRE as marca_nombre, " +
        "ciu.NOMBRE as ciudad_nombre, col.NOMBRE as color_nombre, comb.NOMBRE as combustible_nombre, " +
        "trans.NOMBRE as transmision_nombre, etiq.NOMBRE as etiqueta_nombre, cat.NOMBRE as categoria_nombre, " +
        "u.NOMBRES as vendedor_nombre, u.APELLIDOS as vendedor_apellidos, u.EMAIL as vendedor_email, u.DNI as vendedor_dni, u.TELEFONO as vendedor_telefono " +
        "FROM FAVORITOS f " +
        "JOIN COCHES c ON f.COCHE_ID = c.ID_COCHE " +
        "JOIN VERSIONES v ON c.VERSION_ID = v.ID_VERSION " +
        "JOIN MODELOS m ON v.MODELO_ID = m.ID_MODELO " +
        "JOIN MARCAS ma ON m.MARCA_ID = ma.ID_MARCA " +
        "JOIN CIUDADES ciu ON c.CIUDAD_ID = ciu.ID_CIUDAD " +
        "JOIN COLORES col ON c.COLOR_ID = col.ID_COLOR " +
        "JOIN TIPOS_COMBUSTIBLE comb ON c.COMBUSTIBLE_ID = comb.ID_COMBUSTIBLE " +
        "JOIN TIPOS_TRANSMISION trans ON c.TRANSMISION_ID = trans.ID_TRANSMISION " +
        "JOIN ETIQUETAS_AMBIENTALES etiq ON c.ETIQUETA_ID = etiq.ID_ETIQUETA " +
        "JOIN CATEGORIAS cat ON c.CATEGORIA_ID = cat.ID_CATEGORIA " +
        "JOIN USUARIOS u ON c.VENDEDOR_ID = u.ID_USUARIO " +
        "WHERE f.USUARIO_ID = ? " +
        "ORDER BY f.FECHA_AGREGADO DESC";

    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, usuarioId);
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          coches.add(mapResultSetToCoche(rs));
        }
      }
    } catch (SQLException e) {
      throw new ErrorDeAccesoADatosException("Error al listar favoritos", e);
    }
    return coches;
  }

  @Override
  public boolean esFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException {
    String sql = "SELECT 1 FROM FAVORITOS WHERE USUARIO_ID = ? AND COCHE_ID = ?";
    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, usuarioId);
      pstmt.setInt(2, cocheId);
      try (ResultSet rs = pstmt.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException e) {
      throw new ErrorDeAccesoADatosException("Error al verificar favorito", e);
    }
  }
}
