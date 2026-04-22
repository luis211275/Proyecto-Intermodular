package intermodular.controller;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import intermodular.model.CocheResponse;
import intermodular.model.Coches;
import intermodular.service.CochesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CochesControllerTest {

    private CochesService cochesService;
    private CochesController cochesController;
    private HttpExchange exchange;
    private final Gson gson = new Gson();

    @BeforeEach
    void setUp() {
        cochesService = mock(CochesService.class);
        cochesController = new CochesController(cochesService);
        exchange = mock(HttpExchange.class);
        when(exchange.getResponseHeaders()).thenReturn(new com.sun.net.httpserver.Headers());
    }

    @Test
    void testListarCoches() throws IOException, intermodular.exception.CochesException {
        List<CocheResponse> listaMock = Arrays.asList(new CocheResponse(), new CocheResponse());
        when(cochesService.listarCoches()).thenReturn(listaMock);
        when(exchange.getRequestMethod()).thenReturn("GET");
        when(exchange.getRequestURI()).thenReturn(URI.create("/apis/coches/listarcoches"));
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(exchange.getResponseBody()).thenReturn(outputStream);

        cochesController.handle(exchange);

        verify(cochesService).listarCoches();
        verify(exchange).sendResponseHeaders(eq(200), anyLong());
        assertEquals(gson.toJson(listaMock), outputStream.toString());
    }

    @Test
    void testListarRecomendados() throws IOException, intermodular.exception.CochesException {
        List<CocheResponse> listaMock = Arrays.asList(new CocheResponse());
        when(cochesService.listarRecomendados()).thenReturn(listaMock);
        when(exchange.getRequestMethod()).thenReturn("GET");
        when(exchange.getRequestURI()).thenReturn(URI.create("/apis/coches/listarrecomendados"));
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(exchange.getResponseBody()).thenReturn(outputStream);

        cochesController.handle(exchange);

        verify(cochesService).listarRecomendados();
        verify(exchange).sendResponseHeaders(eq(200), anyLong());
        assertEquals(gson.toJson(listaMock), outputStream.toString());
    }

    @Test
    void testPublicarCoche() throws IOException, intermodular.exception.CochesException {
        Coches coche = new Coches(2023, 10000, 25000.0, 1, 1);
        String json = gson.toJson(coche);
        
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        when(exchange.getRequestBody()).thenReturn(inputStream);
        when(exchange.getRequestMethod()).thenReturn("POST");
        when(exchange.getRequestURI()).thenReturn(URI.create("/apis/coches/publicarcoche"));
        
        CocheResponse responseMock = new CocheResponse();
        responseMock.setId(123);
        when(cochesService.publicarCoche(any(Coches.class))).thenReturn(responseMock);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(exchange.getResponseBody()).thenReturn(outputStream);

        cochesController.handle(exchange);

        verify(cochesService).publicarCoche(any(Coches.class));
        verify(exchange).sendResponseHeaders(eq(201), anyLong());
        assertTrue(outputStream.toString().contains("123"));
    }

    @Test
    void testMarcarFavoritos() throws IOException, intermodular.exception.CochesException {
        // Preparación
        String json = "{\"usuarioId\": 1, \"cocheId\": 10}";
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        when(exchange.getRequestBody()).thenReturn(inputStream);
        when(exchange.getRequestMethod()).thenReturn("POST");
        when(exchange.getRequestURI()).thenReturn(URI.create("/apis/coches/marcarfavoritos"));
        
        when(cochesService.marcarFavoritos(1, 10)).thenReturn(true);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(exchange.getResponseBody()).thenReturn(outputStream);

        // Ejecución
        cochesController.handle(exchange);

        // Verificación
        verify(cochesService).marcarFavoritos(1, 10);
        verify(exchange).sendResponseHeaders(eq(200), anyLong());
        assertTrue(outputStream.toString().contains("añadido a favoritos"));
    }

    @Test
    void testActualizarEstado() throws IOException, intermodular.exception.CochesException {
        // Preparación
        String json = "{\"idCoche\": 5, \"estado\": \"Vendido\"}";
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        when(exchange.getRequestBody()).thenReturn(inputStream);
        when(exchange.getRequestMethod()).thenReturn("POST");
        when(exchange.getRequestURI()).thenReturn(URI.create("/apis/coches/actualizarestado"));
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(exchange.getResponseBody()).thenReturn(outputStream);

        // Ejecución
        cochesController.handle(exchange);

        // Verificación
        verify(cochesService).actualizarEstado(5, "Vendido");
        verify(exchange).sendResponseHeaders(eq(200), anyLong());
        assertTrue(outputStream.toString().contains("Estado actualizado correctamente"));
    }

    @Test
    void testRutaNoEncontrada() throws IOException {
        // Preparación
        when(exchange.getRequestMethod()).thenReturn("GET");
        when(exchange.getRequestURI()).thenReturn(URI.create("/apis/coches/ruta-inexistente"));
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(exchange.getResponseBody()).thenReturn(outputStream);

        // Ejecución
        cochesController.handle(exchange);

        // Verificación
        verify(exchange).sendResponseHeaders(eq(405), anyLong());
        assertTrue(outputStream.toString().contains("no permitida"));
    }
}
