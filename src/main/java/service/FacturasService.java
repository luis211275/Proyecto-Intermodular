package service;

import dao.FacturasDAO;
import dao.impl.FacturasDAOImpl;
import exceptions.FacturasException;
import model.Factura;
import model.LineaFactura;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacturasService {
    private final FacturasDAO dao;

    public FacturasService(FacturasDAO dao) {
        this.dao = dao;
    }

    public FacturasService() {
        this.dao = new FacturasDAOImpl();
    }

    public Map<String, Object> calcularPresupuesto(double precioBase, int idCoche) {
        double iva = precioBase * 0.21;
        double comision = precioBase * 0.03;
        double total = precioBase + iva + comision;

        Map<String, Object> res = new HashMap<>();
        res.put("idCoche", idCoche);
        res.put("precioBase", precioBase);
        res.put("iva", iva);
        res.put("comisionPlataforma", comision);
        res.put("totalPagado", total);
        return res;
    }

    public int crearFacturaParaCoche(int idComprador, int idCoche, double precioVenta) throws FacturasException {
        Map<String, Object> presupuesto = calcularPresupuesto(precioVenta, idCoche);

        Factura factura = new Factura();
        factura.setCompradorId(idComprador);
        factura.setTotalBase((Double) presupuesto.get("precioBase"));
        factura.setIvaImporte((Double) presupuesto.get("iva"));
        factura.setComisionPlataforma((Double) presupuesto.get("comisionPlataforma"));
        factura.setTotalPagado((Double) presupuesto.get("totalPagado"));

        int facturaId = dao.crearFactura(factura);

        LineaFactura linea = new LineaFactura();
        linea.setFacturaId(facturaId);
        linea.setCocheId(idCoche);
        linea.setPrecioVentaMomento(precioVenta);

        dao.crearLineaFactura(linea);
        return facturaId;
    }

    public List<Factura> listarFacturas(int compradorId) throws FacturasException {
        return dao.listarFacturasPorComprador(compradorId);
    }

    public Factura obtenerFactura(int facturaId) throws FacturasException {
        Factura factura = dao.obtenerFacturaPorId(facturaId);
        if (factura == null) {
            throw new FacturasException("La factura con ID " + facturaId + " no existe.");
        }
        return factura;
    }
}