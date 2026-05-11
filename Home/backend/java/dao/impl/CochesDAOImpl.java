package dao.impl;

import config.ConnectionBBDD;
import dao.CochesDAO;
import exceptions.CochesException;
import model.CocheResponse;
import model.Coches;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CochesDAOImpl implements CochesDAO {

    private final String SELECT_BASE = "SELECT c.id_coche, c.anio_fabricacion, c.kilometraje, c.precio_venta, c.es_premium, c.estado, " +
            "TO_CHAR(c.fecha_publicacion, 'YYYY-MM-DD HH24:MI:SS') as fecha_publicacion, " +
            "c.version_id, v.nombre as version_nombre, " +
            "m.nombre as modelo_nombre, " +
            "ma.nombre as marca_nombre, " +
            "c.combustible_id, tc.nombre as combustible_nombre, " +
            "c.transmision_id, tt.nombre as transmision_nombre, " +
            "c.ciudad_id, ciu.nombre as ciudad_nombre, " +
            "c.color_id, col.nombre as color_nombre, " +
            "c.etiqueta_id, et.nombre as etiqueta_nombre, " +
            "c.categoria_id, cat.nombre as categoria_nombre, " +
            "c.vendedor_id, u.nombres || ' ' || u.apellidos as vendedor_nombre, u.email as vendedor_email " +
            "FROM coches c " +
            "JOIN versiones v ON c.version_id = v.id_version " +
            "JOIN modelos m ON v.modelo_id = m.id_modelo " +
            "JOIN marcas ma ON m.marca_id = ma.id_marca " +
            "JOIN tipos_combustible tc ON c.combustible_id = tc.id_combustible " +
            "JOIN tipos_transmision tt ON c.transmision_id = tt.id_transmision " +
            "JOIN ciudades ciu ON c.ciudad_id = ciu.id_ciudad " +
            "JOIN colores col ON c.color_id = col.id_color " +
            "JOIN etiquetas_ambientales et ON c.etiqueta_id = et.id_etiqueta " +
            "JOIN categorias cat ON c.categoria_id = cat.id_categoria " +
            "JOIN usuarios u ON c.vendedor_id = u.id_usuario ";

    @Override
    public int publicarCoche(Coches coche) throws CochesException {
        if (coche == null) {
            throw new CochesException("El coche no puede ser nulo");
        }

        String sqlInsert = "INSERT INTO coches (anio_fabricacion, kilometraje, precio_venta, es_premium, estado, " +
                "version_id, combustible_id, transmision_id, ciudad_id, color_id, etiqueta_id, categoria_id, vendedor_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionBBDD.getConnection()) {
            conn.setAutoCommit(false);

            int idGenerado = -1;
            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, coche.getAnioFabricacion());
                pstmt.setInt(2, coche.getKilometraje());
                pstmt.setDouble(3, coche.getPrecioVenta());
                pstmt.setBoolean(4, coche.getEsPremium() != null ? coche.getEsPremium() : false);
                pstmt.setString(5, coche.getEstado() != null ? coche.getEstado() : "Disponible");
                pstmt.setInt(6, coche.getVersionId());
                pstmt.setInt(7, coche.getCombustibleId());
                pstmt.setInt(8, coche.getTransmisionId());
                pstmt.setInt(9, coche.getCiudadId());
                pstmt.setInt(10, coche.getColorId());
                pstmt.setInt(11, coche.getEtiquetaId());
                pstmt.setInt(12, coche.getCategoriaId());
                pstmt.setInt(13, coche.getVendedorId());

                pstmt.executeUpdate();

                ResultSet rsKeys = pstmt.getGeneratedKeys();
                if (rsKeys.next()) {
                    idGenerado = rsKeys.getInt(1);
                }
            }

            conn.commit();
            return idGenerado;

        } catch (SQLException e) {
            throw new CochesException("Error en la base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<CocheResponse> listarCoches() throws CochesException {
        return ejecutarConsulta(SELECT_BASE + " WHERE c.estado != 'Desactivado' ORDER BY c.fecha_publicacion DESC");
    }

    @Override
    public List<CocheResponse> listarRecomendados() throws CochesException {
        return ejecutarConsulta(SELECT_BASE + " WHERE c.es_premium = true AND c.estado = 'Disponible' ORDER BY c.fecha_publicacion DESC LIMIT 6");
    }

    @Override
    public CocheResponse obtenerCochePorId(int idCoche) throws CochesException {
        List<CocheResponse> lista = ejecutarConsulta(SELECT_BASE + " WHERE c.id_coche = " + idCoche);
        return lista.isEmpty() ? null : lista.get(0);
    }

    private CocheResponse mapRow(ResultSet rs) throws SQLException {
        CocheResponse c = new CocheResponse();
        c.setId(rs.getInt("id_coche"));
        c.setAnioFabricacion(rs.getInt("anio_fabricacion"));
        c.setKilometraje(rs.getInt("kilometraje"));
        c.setPrecioVenta(rs.getDouble("precio_venta"));
        c.setEsPremium(rs.getBoolean("es_premium"));
        c.setEstado(rs.getString("estado"));
        c.setFechaPublicacion(rs.getString("fecha_publicacion"));
        c.setVersion(rs.getString("version_nombre"));
        c.setModelo(rs.getString("modelo_nombre"));
        c.setMarca(rs.getString("marca_nombre"));
        c.setCombustible(rs.getString("combustible_nombre"));
        c.setTransmision(rs.getString("transmision_nombre"));
        c.setCiudad(rs.getString("ciudad_nombre"));
        c.setColor(rs.getString("color_nombre"));
        c.setEtiquetaAmbiental(rs.getString("etiqueta_nombre"));
        c.setCategoria(rs.getString("categoria_nombre"));
        c.setVendedorId(rs.getInt("vendedor_id"));
        c.setVendedorNombre(rs.getString("vendedor_nombre"));
        c.setVendedorEmail(rs.getString("vendedor_email"));
        return c;
    }

    private List<CocheResponse> ejecutarConsulta(String sql) throws CochesException {
        List<CocheResponse> lista = new ArrayList<>();
        try (Connection conn = ConnectionBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new CochesException("Error al consultar coches: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean marcarFavoritos(int usuarioId, int cocheId) throws CochesException {
        String sqlCheck = "SELECT 1 FROM favoritos WHERE usuario_id = ? AND coche_id = ?";
        String sqlInsert = "INSERT INTO favoritos (usuario_id, coche_id) VALUES (?, ?)";
        String sqlDelete = "DELETE FROM favoritos WHERE usuario_id = ? AND coche_id = ?";

        try (Connection conn = ConnectionBBDD.getConnection()) {
            boolean yaEsFavorito = false;
            try (PreparedStatement pstmt = conn.prepareStatement(sqlCheck)) {
                pstmt.setInt(1, usuarioId);
                pstmt.setInt(2, cocheId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    yaEsFavorito = rs.next();
                }
            }

            if (yaEsFavorito) {
                try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
                    pstmt.setInt(1, usuarioId);
                    pstmt.setInt(2, cocheId);
                    pstmt.executeUpdate();
                    return false;
                }
            } else {
                try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                    pstmt.setInt(1, usuarioId);
                    pstmt.setInt(2, cocheId);
                    pstmt.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new CochesException("Error al gestionar favoritos: " + e.getMessage());
        }
    }

    @Override
    public List<CocheResponse> listarFavoritos(int usuarioId) throws CochesException {
        String sql = SELECT_BASE +
                " JOIN favoritos f ON c.id_coche = f.coche_id " +
                " WHERE f.usuario_id = ? " +
                " ORDER BY f.fecha_agregado DESC";

        List<CocheResponse> lista = new ArrayList<>();
        try (Connection conn = ConnectionBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CocheResponse c = mapRow(rs);
                    c.setEsFavorito(true);
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            throw new CochesException("Error al consultar favoritos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void actualizarEstado(int idCoche, String nuevoEstado) throws CochesException {
        String sql = "UPDATE coches SET estado = ? WHERE id_coche = ?";

        try (Connection conn = ConnectionBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, idCoche);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new CochesException("No se encontró el coche con ID: " + idCoche);
            }
        } catch (SQLException e) {
            throw new CochesException("Error al actualizar estado: " + e.getMessage());
        }
    }

    @Override
    public void desactivarCoche(int idCoche) throws CochesException {
        String sql = "UPDATE coches SET estado = 'Desactivado' WHERE id_coche = ?";
        try (Connection conn = ConnectionBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCoche);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new CochesException("No se encontró el coche con ID: " + idCoche);
            }
        } catch (SQLException e) {
            throw new CochesException("Error al desactivar coche: " + e.getMessage());
        }
    }

    @Override
    public void guardarImagenCoche(int cocheId, byte[] datos, String extension, boolean esPrincipal) throws CochesException {
        String sql = "INSERT INTO imagenes_coches (coche_id, datos_imagen, extension, es_principal) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cocheId);
            pstmt.setBytes(2, datos);
            pstmt.setString(3, extension);
            pstmt.setBoolean(4, esPrincipal);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new CochesException("Error al guardar imagen en la base de datos: " + e.getMessage());
        }
    }

    @Override
    public void guardarUrlImagen(int cocheId, String url, boolean esPrincipal) throws CochesException {
        String sql = "INSERT INTO imagenes_coches (coche_id, url_imagen, es_principal) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cocheId);
            pstmt.setString(2, url);
            pstmt.setBoolean(3, esPrincipal);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new CochesException("Error al guardar URL de imagen: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> obtenerImagenCompleta(int idImagen) throws CochesException {
        String sql = "SELECT datos_imagen, extension FROM imagenes_coches WHERE id_imagen = ?";
        try (Connection conn = ConnectionBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idImagen);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> res = new HashMap<>();
                    res.put("datos", rs.getBytes("datos_imagen"));
                    res.put("extension", rs.getString("extension"));
                    return res;
                }
            }
        } catch (SQLException e) {
            throw new CochesException("Error al obtener imagen: " + e.getMessage());
        }
        return null;
    }
}