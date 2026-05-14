package org.example.controller;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import org.example.dao.UsuarioDao;
import org.example.dao.impl.UsuarioDaoImpl;
import org.example.model.Usuario;
import org.example.service.UsuarioService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UsuarioController {
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        String query = exchange.getRequestURI().getQuery();

        // CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        if (method.equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        try {
            if (method.equalsIgnoreCase("GET")) {
                if (path.equals("/user/email")) {
                    String email = null;

                    if (query != null && query.contains("value=")) {
                        email = java.net.URLDecoder.decode(
                                query.split("value=", 2)[1].split("&")[0],
                                StandardCharsets.UTF_8
                        );
                    }

                    if (email == null || email.isBlank()) {
                        sendResponse(exchange, 400, "{\"status\":\"error\",\"message\":\"Email no informado\"}");
                        return;
                    }

                    UsuarioDao dao = new UsuarioDaoImpl();
                    Usuario usuario = dao.obtenerUsuarioPorEmail(email);

                    if (usuario == null) {
                        sendResponse(exchange, 404, "{\"status\":\"error\",\"message\":\"Usuario no encontrado\"}");
                        return;
                    }

                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("id", usuario.getIdUsuario());
                    responseJson.addProperty("nombres", usuario.getNombres());
                    responseJson.addProperty("apellidos", usuario.getApellidos());
                    responseJson.addProperty("email", usuario.getEmail());
                    responseJson.addProperty("dni", usuario.getDni());
                    responseJson.addProperty("telefono", usuario.getTelefono());
                    sendResponse(exchange, 200, responseJson.toString());
                    return;
                }

                if (path.startsWith("/user/") && path.length() > "/user/".length()) {
                    String idTexto = path.substring("/user/".length());
                    int idUsuario = Integer.parseInt(idTexto);
                    UsuarioDao dao = new UsuarioDaoImpl();
                    Usuario usuario = dao.obtenerUsuarioPorId(idUsuario);

                    if (usuario == null) {
                        sendResponse(exchange, 404, "{\"status\":\"error\",\"message\":\"Usuario no encontrado\"}");
                        return;
                    }

                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("id", usuario.getIdUsuario());
                    responseJson.addProperty("nombres", usuario.getNombres());
                    responseJson.addProperty("apellidos", usuario.getApellidos());
                    responseJson.addProperty("email", usuario.getEmail());
                    responseJson.addProperty("dni", usuario.getDni());
                    responseJson.addProperty("telefono", usuario.getTelefono());
                    sendResponse(exchange, 200, responseJson.toString());
                    return;
                }
            }

            if (method.equalsIgnoreCase("POST")) {
                if (path.equals("/user/register")) {
                    String body = new String(
                            exchange.getRequestBody().readAllBytes(),
                            StandardCharsets.UTF_8);
                    System.out.println(body);

                    JsonObject responseJson = new JsonObject();

                    UsuarioService usuario = new UsuarioService();
                    UsuarioDao dao = new UsuarioDaoImpl();

                    int resultado = usuario.procesarRegistro(body);

                    if (resultado == 0) {
                        responseJson.addProperty("status", "ok");
                        responseJson.addProperty("message", "Usuario registrado exitosamente");
                        sendResponse(exchange, 200, responseJson.toString());
                        return;
                    } else {
                        responseJson.addProperty("status", "error");
                        String msg = (resultado == 1) ? "El email ya esta registrado" : "El DNI ya esta registrado";
                        responseJson.addProperty("message", msg);
                        sendResponse(exchange, 400, responseJson.toString());
                        return;
                    }
                }

                else if (path.equals("/user/logger")) {
                    String body = new String(
                            exchange.getRequestBody().readAllBytes(),
                            StandardCharsets.UTF_8);

                    System.out.println(body);

                    JsonObject responseJson = new JsonObject();
                    UsuarioService servicio = new UsuarioService();
                    UsuarioDao dao = new UsuarioDaoImpl();

                    // Llamamos a la lógica del service
                    int resultado = dao.validarLogin(body);

                    if (resultado == 0) {
                        JsonObject loginJson = com.google.gson.JsonParser.parseString(body).getAsJsonObject();
                        String email = loginJson.get("email").getAsString();
                        Usuario usuario = dao.obtenerUsuarioPorEmail(email);
                        int idUsuario = usuario != null ? usuario.getIdUsuario() : 0;

                        responseJson.addProperty("status", "ok");
                        responseJson.addProperty("message", "Login correcto");
                        // Antes solo devolvíamos "Login correcto" y el frontend no sabía
                        // qué id de usuario guardar para publicar el coche.
                        // Ahora también devolvemos el id para que el anuncio se asocie
                        // al usuario que ha iniciado sesión.
                        responseJson.addProperty("userId", idUsuario);
                        responseJson.addProperty("email", email);
                        if (usuario != null) {
                            responseJson.addProperty("nombres", usuario.getNombres());
                            responseJson.addProperty("apellidos", usuario.getApellidos());
                            responseJson.addProperty("dni", usuario.getDni());
                            responseJson.addProperty("telefono", usuario.getTelefono());
                        }
                        sendResponse(exchange, 200, responseJson.toString());
                        return;
                    } else {
                        responseJson.addProperty("status", "error");

                        String msg = (resultado == 1) ? "Correo no registrado" : "Error al iniciar sesion";
                        int code = (resultado == 1) ? 404 : 401;

                        responseJson.addProperty("message", msg);
                        sendResponse(exchange, code, responseJson.toString());
                        return;
                    }
                }
            }

            sendResponse(exchange, 405, "Metodo no valido");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"status\":\"error\",\"message\":\"Error interno\"}");
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
