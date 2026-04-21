package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import dao.HomeDAO;
import model.Coche;

public class HomeService {
    private final static Gson gson = new Gson();
    private final HomeDAO homeDAO = new HomeDAO();

    public static JsonObject getInformacion(String path, HttpExchange exchange) throws IOException {
        JsonObject jsonRaiz = new JsonObject();

        if (path.endsWith("/coches/add")) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            // Convertimos el JSON recibido directamente a un objeto Coche
            Coche nuevoCoche = gson.fromJson(body, Coche.class);

            HomeDAO dao = new HomeDAO();
            dao.insertarCoche(nuevoCoche);

            jsonRaiz.addProperty("status", "success");
            jsonRaiz.addProperty("message", "Coche guardado correctamente");
        }
        return jsonRaiz;
    }
}