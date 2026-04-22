package intermodular.service;

import intermodular.dao.CochesDAO;
import intermodular.exception.CochesException;
import intermodular.model.CocheResponse;
import intermodular.model.Coches;
import intermodular.model.Factura;
import intermodular.model.LineaFactura;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CochesServiceTest {

    private CochesDAO cochesDAO;
    private FacturasService facturasService;
    private CochesService cochesService;

    @BeforeEach
    void setUp() {
        cochesDAO = mock(CochesDAO.class);
        facturasService = mock(FacturasService.class);
        cochesService = new CochesService(cochesDAO, facturasService);
    }

    @Test
    void testPublicarCocheExito() throws CochesException {
        // Preparación
        Coches coche = new Coches(2022, 10000, 25000.0, 1, 2);
        CocheResponse response = new CocheResponse();
        response.setId(1);
        response.setMarca("Toyota");
        response.setVendedorId(2);

        when(cochesDAO.publicarCoche(coche)).thenReturn(1);
        when(cochesDAO.obtenerCochePorId(1)).thenReturn(response);

        // Ejecución
        CocheResponse resultado = cochesService.publicarCoche(coche);

        // Verificación
        assertNotNull(resultado);
        assertEquals("Toyota", resultado.getMarca());
        assertEquals(2, resultado.getVendedorId());
        verify(cochesDAO, times(1)).publicarCoche(coche);
    }

    @Test
    void testPublicarCocheAnioInvalido() throws CochesException {
        Coches coche = new Coches(1850, 10000, 20000.0, 1, 2);

        CochesException exception = assertThrows(CochesException.class, () -> {
            cochesService.publicarCoche(coche);
        });

        assertEquals("El año de fabricación debe ser posterior a 1900.", exception.getMessage());
        verifyNoInteractions(cochesDAO);
    }

    @Test
    void testPublicarCochePrecioInvalido() throws CochesException {
        Coches coche = new Coches(2020, 10000, 0.0, 1, 2);

        CochesException exception = assertThrows(CochesException.class, () -> {
            cochesService.publicarCoche(coche);
        });

        assertEquals("El precio de venta debe ser positivo.", exception.getMessage());
        verifyNoInteractions(cochesDAO);
    }

    @Test
    void testListarRecomendados() throws CochesException {
        List<CocheResponse> listaMock = Arrays.asList(new CocheResponse(), new CocheResponse());
        when(cochesDAO.listarRecomendados()).thenReturn(listaMock);

        List<CocheResponse> resultado = cochesService.listarRecomendados();

        assertEquals(2, resultado.size());
        verify(cochesDAO, times(1)).listarRecomendados();
    }

    @Test
    void testListarCoches() throws CochesException {
        List<CocheResponse> listaMock = Arrays.asList(new CocheResponse(), new CocheResponse(), new CocheResponse());
        when(cochesDAO.listarCoches()).thenReturn(listaMock);

        List<CocheResponse> resultado = cochesService.listarCoches();

        assertEquals(3, resultado.size());
        verify(cochesDAO, times(1)).listarCoches();
    }

    @Test
    void testMarcarFavoritos() throws CochesException {
        int usuarioId = 1;
        int cocheId = 10;
        CocheResponse c = new CocheResponse();
        c.setId(cocheId);
        c.setEstado("Disponible");
        
        when(cochesDAO.listarCoches()).thenReturn(Collections.singletonList(c));
        when(cochesDAO.marcarFavoritos(usuarioId, cocheId)).thenReturn(true);

        boolean resultado = cochesService.marcarFavoritos(usuarioId, cocheId);

        assertTrue(resultado);
        verify(cochesDAO, times(1)).marcarFavoritos(usuarioId, cocheId);
    }

    @Test
    void testMarcarFavoritosCocheDesactivado() throws CochesException {
        int usuarioId = 1;
        int cocheId = 10;
        when(cochesDAO.listarCoches()).thenReturn(Collections.emptyList());

        assertThrows(CochesException.class, () -> 
            cochesService.marcarFavoritos(usuarioId, cocheId)
        );
        verify(cochesDAO, never()).marcarFavoritos(anyInt(), anyInt());
    }

    @Test
    void testListarFavoritos() throws CochesException {
        int usuarioId = 1;
        List<CocheResponse> listaMock = Arrays.asList(new CocheResponse());
        when(cochesDAO.listarFavoritos(usuarioId)).thenReturn(listaMock);

        List<CocheResponse> resultado = cochesService.listarFavoritos(usuarioId);

        assertEquals(1, resultado.size());
        verify(cochesDAO, times(1)).listarFavoritos(usuarioId);
    }

    @Test
    void testActualizarEstado() throws CochesException {
        int idCoche = 5;
        String nuevoEstado = "Vendido";

        cochesService.actualizarEstado(idCoche, nuevoEstado);

        verify(cochesDAO, times(1)).actualizarEstado(idCoche, nuevoEstado);
    }

    @Test
    void testActualizarEstadoInvalido() throws CochesException {
        assertThrows(CochesException.class, () -> 
            cochesService.actualizarEstado(1, "Inexistente")
        );
    }

    @Test
    void testGuardarImagenCoche() throws CochesException {
        int cocheId = 1;
        byte[] datos = new byte[]{1, 2, 3};
        String extension = "jpg";
        boolean esPrincipal = true;

        cochesService.guardarImagenCoche(cocheId, datos, extension, esPrincipal);

        verify(cochesDAO, times(1)).guardarImagenCoche(cocheId, datos, extension, esPrincipal);
    }

    @Test
    void testGuardarUrlImagen() throws CochesException {
        int cocheId = 1;
        String url = "/uploads/test.jpg";
        boolean esPrincipal = true;

        cochesService.guardarUrlImagen(cocheId, url, esPrincipal);

        verify(cochesDAO, times(1)).guardarUrlImagen(cocheId, url, esPrincipal);
    }

    @Test
    void testBorrarCoche() throws CochesException {
        int idCoche = 5;

        cochesService.borrarCoche(idCoche);

        verify(cochesDAO, times(1)).desactivarCoche(idCoche);
    }

    @Test
    void testObtenerImagen() throws CochesException {
        Map<String, Object> imagenMock = new HashMap<>();
        imagenMock.put("id", 1);
        
        when(cochesDAO.obtenerImagenCompleta(1)).thenReturn(imagenMock);
        
        Map<String, Object> resultado = cochesService.obtenerImagen(1);
        
        assertEquals(imagenMock, resultado);
        verify(cochesDAO, times(1)).obtenerImagenCompleta(1);
    }

    @Test
    void testObtenerPresupuesto() throws CochesException {
        int idCoche = 1;
        CocheResponse response = new CocheResponse();
        response.setId(idCoche);
        response.setPrecioVenta(100.0);
        when(cochesDAO.obtenerCochePorId(idCoche)).thenReturn(response);
        
        Map<String, Object> presupuestoMock = new HashMap<>();
        presupuestoMock.put("totalPagado", 124.0);
        when(facturasService.calcularPresupuesto(100.0, idCoche)).thenReturn(presupuestoMock);

        Map<String, Object> presupuesto = cochesService.obtenerPresupuesto(idCoche);

        assertEquals(124.0, presupuesto.get("totalPagado"));
        verify(facturasService).calcularPresupuesto(100.0, idCoche);
    }

    @Test
    void testComprarCocheExito() throws CochesException, intermodular.exception.FacturasException {
        int idCoche = 1;
        int idComprador = 5;
        CocheResponse response = new CocheResponse();
        response.setId(idCoche);
        response.setPrecioVenta(1000.0);
        response.setEstado("Disponible");
        
        when(cochesDAO.obtenerCochePorId(idCoche)).thenReturn(response);

        cochesService.comprarCoche(idCoche, idComprador);

        verify(facturasService).crearFacturaParaCoche(idComprador, idCoche, 1000.0);
        verify(cochesDAO, times(1)).actualizarEstado(idCoche, "Vendido");
    }

    @Test
    void testComprarCocheNoDisponible() throws CochesException {
        int idCoche = 1;
        CocheResponse response = new CocheResponse();
        response.setEstado("Vendido");
        when(cochesDAO.obtenerCochePorId(idCoche)).thenReturn(response);

        assertThrows(CochesException.class, () -> cochesService.comprarCoche(idCoche, 5));
    }
}
