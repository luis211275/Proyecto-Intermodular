package router;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.CochesController;
import controller.FacturasController;
import service.FacturasService;

import java.io.IOException;

public class RouterHandler implements HttpHandler {
    private final FacturasService facturasService = new FacturasService();
    private final CochesController cochesController = new CochesController();
    private final FacturasController facturasController = new FacturasController(facturasService);

    @Override
    public void handle(HttpExchange exchange) {
        try {
            String path = exchange.getRequestURI().getPath();

            if (path.startsWith("/imagenes/ver/")) {
                cochesController.servirImagen(exchange);
            } else if (path.startsWith("/apis/coches")) {
                cochesController.handle(exchange);
            } else if (path.startsWith("/apis/facturas")) {
                facturasController.handle(exchange);
            } else if (path.equals("/docs")) {
                // Se carga el archivo desde resources para que funcione tanto en IDE como empaquetado
                try (java.io.InputStream is = getClass().getClassLoader().getResourceAsStream("openapi-coches.html")) {
                    if (is != null) {
                        byte[] bytes = is.readAllBytes();
                        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
                        exchange.sendResponseHeaders(200, bytes.length);
                        exchange.getResponseBody().write(bytes);
                        exchange.getResponseBody().close();
                    } else {
                        byte[] response = "Archivo de documentacion no encontrado en resources".getBytes();
                        exchange.sendResponseHeaders(404, response.length);
                        exchange.getResponseBody().write(response);
                        exchange.getResponseBody().close();
                    }
                }
            } else if (path.equals("/")) {
                String response = "<h1>Bienvenido al Marketplace</h1><p>El frontend estara aqui. Accede a la <a href='/docs'>Documentacion de la API</a></p>";
                byte[] bytes = response.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                exchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
                exchange.sendResponseHeaders(200, bytes.length);
                exchange.getResponseBody().write(bytes);
                exchange.getResponseBody().close();
            } else {
                byte[] response = "404 Not Found".getBytes();
                exchange.sendResponseHeaders(404, response.length);
                exchange.getResponseBody().write(response);
                exchange.getResponseBody().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                byte[] response = "Error interno en el RouterHandler".getBytes();
                exchange.sendResponseHeaders(500, response.length);
                exchange.getResponseBody().write(response);
                exchange.getResponseBody().close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}