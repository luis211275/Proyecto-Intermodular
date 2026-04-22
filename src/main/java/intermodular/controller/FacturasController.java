package intermodular.controller;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import intermodular.exception.FacturasException;
import intermodular.model.Factura;
import intermodular.service.FacturasService;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FacturasController {
    private final FacturasService service;
    private final Gson gson = new Gson();

    public FacturasController(FacturasService service) {
        this.service = service;
    }

    public void handle(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            if (method.equalsIgnoreCase("GET") && path.equals("/apis/facturas/listar")) {
                listarFacturas(exchange);
            } else if (method.equalsIgnoreCase("GET") && path.equals("/apis/facturas/detalle")) {
                obtenerFactura(exchange);
            } else {
                sendResponse(exchange, 405, "{\"error\": \"METHOD_NOT_ALLOWED\", \"message\": \"Método o ruta no permitida.\"}");
            }
        } catch (FacturasException e) {
            sendResponse(exchange, 400, "{\"error\": \"FACTURA_ERROR\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            sendResponse(exchange, 500, "{\"error\": \"INTERNAL_ERROR\", \"message\": \"" + e.getMessage() + "\"}");
        }
    }

    private void listarFacturas(HttpExchange exchange) throws FacturasException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.contains("compradorId=")) {
            sendResponse(exchange, 400, "{\"error\": \"BAD_REQUEST\", \"message\": \"Falta el parámetro compradorId.\"}");
            return;
        }

        int compradorId = Integer.parseInt(query.split("compradorId=")[1].split("&")[0]);
        List<Factura> facturas = service.listarFacturas(compradorId);
        sendResponse(exchange, 200, gson.toJson(facturas));
    }

    private void obtenerFactura(HttpExchange exchange) throws FacturasException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.contains("idFactura=")) {
            sendResponse(exchange, 400, "{\"error\": \"BAD_REQUEST\", \"message\": \"Falta el parámetro idFactura.\"}");
            return;
        }

        int idFactura = Integer.parseInt(query.split("idFactura=")[1].split("&")[0]);
        Factura factura = service.obtenerFactura(idFactura);
        sendResponse(exchange, 200, gson.toJson(factura));
    }

    private void sendResponse(HttpExchange exchange, int status, String json) {
        try {
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
