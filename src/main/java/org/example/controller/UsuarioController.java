package org.example.controller;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import org.example.dao.UsuarioDao;
import org.example.service.UsuarioService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UsuarioController {
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        //  CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");


        if (method.equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        try{
            if (method.equalsIgnoreCase("POST")) {
                if (path.equals("/user/register")) {
                    String body = new String(
                            exchange.getRequestBody().readAllBytes(),
                            StandardCharsets.UTF_8
                    );
                    System.out.println(body);

                    JsonObject responseJson = new JsonObject();

                    UsuarioService usuario = new UsuarioService();



                    int resultado = usuario.procesarRegistro(body);

                    if (resultado == 0) {
                        responseJson.addProperty("status", "ok");

                        responseJson.addProperty("message", "Usuario registrado exitosamente");
                        sendResponse(exchange, 200, responseJson.toString());
                    }else {
                        responseJson.addProperty("status", "error");

                        String msg = (resultado == 1) ? "El email ya esta registrado" : "El DNI ya esta registrado";

                        responseJson.addProperty("message", msg);
                        sendResponse(exchange, 400, responseJson.toString());
                    }


                    responseJson.addProperty("status", "ok");
                    responseJson.addProperty("message", "Guardado correctamente");

                    sendResponse(exchange, 200, body);
                }

                else if (path.equals("/user/logger")) {
                        String body = new String(
                                exchange.getRequestBody().readAllBytes(),
                                StandardCharsets.UTF_8
                        );

                        System.out.println(body);

                        JsonObject responseJson = new JsonObject();
                        UsuarioDao dao = new UsuarioDao();

                        // Llamamos a la lógica del service
                        int resultado = dao.validarLogin(body);

                        if (resultado == 0) {
                            responseJson.addProperty("status", "ok");
                            responseJson.addProperty("message", "Login correcto");
                            sendResponse(exchange, 200, responseJson.toString());
                        } else {
                            responseJson.addProperty("status", "error");

                            String msg = (resultado == 1) ? "Correo no registrado" : "Error al iniciar sesion";
                            int code = (resultado == 1) ? 404 : 401;

                            responseJson.addProperty("message", msg);
                            sendResponse(exchange, code, responseJson.toString());
                        }
                    }
            }else{
                sendResponse(exchange, 405, "Metodo no valido");
            }
        }catch (Exception e) {
            sendResponse(exchange, 200, "Error ");
        }
    }


    public void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        // IMPORTANTE: Asegúrate de que estas líneas NO estén repetidas en otro lado
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
