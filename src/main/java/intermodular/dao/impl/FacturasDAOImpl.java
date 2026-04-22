package intermodular.dao.impl;

import intermodular.config.ConnectionDataBase;
import intermodular.dao.FacturasDAO;
import intermodular.exception.FacturasException;
import intermodular.model.Factura;
import intermodular.model.LineaFactura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacturasDAOImpl implements FacturasDAO {

    @Override
    public int crearFactura(Factura factura) throws FacturasException {
        String sql = "INSERT INTO facturas (comprador_id, total_base, iva_importe, comision_plataforma, total_pagado) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, factura.getCompradorId());
            pstmt.setDouble(2, factura.getTotalBase());
            pstmt.setDouble(3, factura.getIvaImporte());
            pstmt.setDouble(4, factura.getComisionPlataforma());
            pstmt.setDouble(5, factura.getTotalPagado());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new FacturasException("Error al crear factura: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public void crearLineaFactura(LineaFactura linea) throws FacturasException {
        String sql = "INSERT INTO lineas_factura (factura_id, coche_id, precio_venta_momento) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, linea.getFacturaId());
            pstmt.setInt(2, linea.getCocheId());
            pstmt.setDouble(3, linea.getPrecioVentaMomento());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new FacturasException("Error al crear línea de factura: " + e.getMessage());
        }
    }

    @Override
    public List<Factura> listarFacturasPorComprador(int compradorId) throws FacturasException {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM facturas WHERE comprador_id = ? ORDER BY fecha_factura DESC";
        try (Connection conn = ConnectionDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, compradorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    facturas.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new FacturasException("Error al listar facturas: " + e.getMessage());
        }
        return facturas;
    }

    @Override
    public Factura obtenerFacturaPorId(int facturaId) throws FacturasException {
        String sql = "SELECT * FROM facturas WHERE id_factura = ?";
        try (Connection conn = ConnectionDataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, facturaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new FacturasException("Error al obtener factura: " + e.getMessage());
        }
        return null;
    }

    private Factura mapRow(ResultSet rs) throws SQLException {
        Factura f = new Factura();
        f.setId(rs.getInt("id_factura"));
        f.setCompradorId(rs.getInt("comprador_id"));
        f.setTotalBase(rs.getDouble("total_base"));
        f.setIvaImporte(rs.getDouble("iva_importe"));
        f.setComisionPlataforma(rs.getDouble("comision_plataforma"));
        f.setTotalPagado(rs.getDouble("total_pagado"));
        
        Timestamp ts = rs.getTimestamp("fecha_factura");
        if (ts != null) {
            f.setFechaEmision(new Date(ts.getTime()));
        }
        
        return f;
    }
}
