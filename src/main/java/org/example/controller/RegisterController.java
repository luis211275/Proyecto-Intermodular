package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import org.example.service.RegisterService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class RegisterController {
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

                    RegisterService usuario = new RegisterService();

                    usuario.insertarUsuario(body);


                    responseJson.addProperty("status", "ok");
                    responseJson.addProperty("message", "Guardado correctamente");

                    sendResponse(exchange, 200, body);
                }else{
                    sendResponse(exchange, 400, "Endpoint no valido");
                }

                if (path.equals("/user/logger")) {
                    String body = new String(
                            exchange.getRequestBody().readAllBytes(),
                            StandardCharsets.UTF_8
                    );
                    System.out.println(body);

                    JsonObject responseJson = new JsonObject();

                    RegisterService usuario = new RegisterService();

                    //0=OK, 1=  Eamil no existe, 2= Contraseña incorrecta

                    int resultado = RegisterService.validarLogin(body);

                    if (resultado == 0) {
                        responseJson.addProperty("status", "ok");

                        responseJson.addProperty("message", "Login correcto");

                        sendResponse(exchange, 200, responseJson.toString());
                    } else if (resultado == 1) {
                        responseJson.addProperty("status", "error");
                        responseJson.addProperty("message", "Este correo no esta registrado");
                        sendResponse(exchange, 404, responseJson.toString());
                    }else {
                        responseJson.addProperty("status", "error");
                        responseJson.addProperty("message", "Contraseña incorrecta");
                        sendResponse(exchange, 401, responseJson.toString());
                    }


                }
            }else{
                sendResponse(exchange, 405, "Metodo no valido");
            }
        }catch (Exception e) {
            sendResponse(exchange, 200, "Error ");
        }
    }


    private void sendResponse(HttpExchange exchange, int status, String body) throws IOException {

        exchange.getResponseHeaders().add("Content-Type", "application/json");

        byte[] bytes = body.getBytes();

        exchange.sendResponseHeaders(status, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
