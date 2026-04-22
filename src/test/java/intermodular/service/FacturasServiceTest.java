package intermodular.service;

import intermodular.dao.FacturasDAO;
import intermodular.exception.FacturasException;
import intermodular.model.Factura;
import intermodular.model.LineaFactura;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacturasServiceTest {

    @Mock
    private FacturasDAO dao;

    private FacturasService service;

    @BeforeEach
    void setUp() {
        service = new FacturasService(dao);
    }

    @Test
    void testCalcularPresupuesto() {
        Map<String, Object> presupuesto = service.calcularPresupuesto(1000.0, 1);
        
        assertEquals(1000.0, presupuesto.get("precioBase"));
        assertEquals(210.0, presupuesto.get("iva"));
        assertEquals(30.0, presupuesto.get("comisionPlataforma"));
        assertEquals(1240.0, presupuesto.get("totalPagado"));
    }

    @Test
    void testCrearFacturaParaCoche() throws FacturasException {
        when(dao.crearFactura(any(Factura.class))).thenReturn(100);

        int id = service.crearFacturaParaCoche(5, 1, 1000.0);

        assertEquals(100, id);
        verify(dao).crearFactura(any(Factura.class));
        verify(dao).crearLineaFactura(any(LineaFactura.class));
    }

    @Test
    void testListarFacturas() throws FacturasException {
        when(dao.listarFacturasPorComprador(5)).thenReturn(Collections.emptyList());
        List<Factura> list = service.listarFacturas(5);
        assertTrue(list.isEmpty());
    }

    @Test
    void testObtenerFactura() throws FacturasException {
        Factura f = new Factura();
        when(dao.obtenerFacturaPorId(100)).thenReturn(f);
        Factura res = service.obtenerFactura(100);
        assertNotNull(res);
    }

    @Test
    void testObtenerFacturaInexistenteLanzaExcepcion() throws FacturasException {
        when(dao.obtenerFacturaPorId(999)).thenReturn(null);
        assertThrows(FacturasException.class, () -> service.obtenerFactura(999));
    }
}
