package dao;

import exceptions.FacturasException;
import model.Factura;
import model.LineaFactura;

import java.util.List;

public interface FacturasDAO {
    int crearFactura(Factura factura) throws FacturasException;
    void crearLineaFactura(LineaFactura linea) throws FacturasException;
    List<Factura> listarFacturasPorComprador(int compradorId) throws FacturasException;
    Factura obtenerFacturaPorId(int facturaId) throws FacturasException;
}