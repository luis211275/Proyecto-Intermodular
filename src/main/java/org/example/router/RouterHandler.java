package org.example.router;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.controller.RegisterController;

import java.io.IOException;
import java.io.OutputStream;

public class RouterHandler implements HttpHandler {

    private final RegisterController registerController = new RegisterController();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        addCorsHeaders(exchange);

        if (method.equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        String path = exchange.getRequestURI().getPath();

        if (path.startsWith("/user")) {
            registerController.handle(exchange);
            return;
        }

        String responseJson = "{\"status\":\"error\",\"message\":\"Ruta no encontrada en el servidor: " + path + "\"}";
        addCorsHeaders(exchange);
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        exchange.sendResponseHeaders(404, responseJson.length());
        OutputStream os = exchange.getResponseBody();
        os.write(responseJson.getBytes());
        os.close();
    }


    private static void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}