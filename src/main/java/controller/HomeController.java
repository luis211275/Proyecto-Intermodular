package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import dao.HomeDAO;
import model.Coche;
import service.HomeService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HomeController {

    private final HttpClient client = HttpClient.newHttpClient();

    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if (method.equalsIgnoreCase("GET") && path.equals("/coches")) {
            HomeDAO dao = new HomeDAO();
            List<Coche> lista = dao.getListaCoches();
            String json = new Gson().toJson(lista); // Convierte la lista a JSON
            sendResponse(exchange, 200, json);
            return;
        }

        try {

            if (path.equals("/coches/add")) {
                JsonObject result = HomeService.getInformacion(path, exchange);
                sendResponse(exchange, 200, result.toString());
                return;
            }

            if (path.equals("/coches/listar")) {
                // Aquí podrías llamar a un metodo que devuelva la lista en JSON
                HomeDAO dao = new HomeDAO();
                dao.getListaCoches(); // Esto imprime en consola, lo ideal sería devolver JSON
                sendResponse(exchange, 200, "{\"status\":\"Check console\"}");
                return;
            }

            sendResponse(exchange, 404, "Endpoint coches no válido");

        } catch (Exception e) {
            sendResponse(exchange, 500, "Error");
        }
    }

    private static void sendResponse(HttpExchange exchange, int status, String body) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
