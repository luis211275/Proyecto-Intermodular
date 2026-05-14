package org.example.router;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.controller.CatalogoController;
import org.example.controller.CocheController;
import org.example.controller.UsuarioController;
import org.example.exception.ErrorDeAccesoADatosException;
import org.example.exception.ErrorDeNegocioException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RouterHandler implements HttpHandler {

  private static final String PUBLIC_DIR = "frontend";

  private final CocheController cocheController = new CocheController();
  private final CatalogoController catalogoController = new CatalogoController();

  private final UsuarioController registerController = new UsuarioController();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String path = exchange.getRequestURI().getPath();
    String method = exchange.getRequestMethod();
    String query = exchange.getRequestURI().getQuery();

    addCorsHeaders(exchange);

    if ("OPTIONS".equalsIgnoreCase(method)) {
      exchange.sendResponseHeaders(204, -1);
      return;
    }

    if (path.equals("/")) {
      exchange.getResponseHeaders().set("Location", "/html/home.html");
      exchange.sendResponseHeaders(302, -1);
      return;
    }

    // Redirigir peticiones de HTML en raíz a la carpeta /html/
    if (path.endsWith(".html") && !path.startsWith("/html/")) {
      exchange.getResponseHeaders().set("Location", "/html" + path);
      exchange.sendResponseHeaders(302, -1);
      return;
    }

    try {
      if (path.startsWith("/user")) {
        registerController.handle(exchange);
        return;
      }

      if (esRutaEstatico(path)) {
        servirArchivoPublic(exchange, path);
        return;
      }

      boolean esApi = path.startsWith("/api/");

      if (!esApi) {
        // Si no es API y no se capturó como estático arriba, redirigir a home
        exchange.getResponseHeaders().set("Location", "/html/home.html");
        exchange.sendResponseHeaders(302, -1);
        return;
      }

      if (path.equals("/api/coches") && method.equals("GET")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        java.util.Map<String, String> filtros = new java.util.HashMap<>();

        if (query != null) {
          String[] pairs = query.split("&");

          for (String pair : pairs) {
            String[] kv = pair.split("=", 2);

            if (kv.length > 1) {
              filtros.put(kv[0], kv[1]);
            }
          }
        }

        sendResponse(exchange, 200, cocheController.listarCoches(filtros));

      } else if (path.equals("/api/favoritos") && method.equals("GET")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        int usuarioId = 0;

        if (query != null && query.contains("usuarioId=")) {
          usuarioId = Integer.parseInt(query.split("usuarioId=")[1].split("&")[0]);
        }

        sendResponse(exchange, 200, cocheController.listarFavoritosUsuario(usuarioId));

      } else if (path.equals("/api/favoritos") && method.equals("POST")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        java.util.Scanner s = new java.util.Scanner(exchange.getRequestBody()).useDelimiter("\\A");
        String body = s.hasNext() ? s.next() : "";

        int uId = Integer.parseInt(body.split("\"usuarioId\"\\s*:\\s*")[1].split("[^0-9]")[0]);
        int cId = Integer.parseInt(body.split("\"cocheId\"\\s*:\\s*")[1].split("[^0-9]")[0]);

        sendResponse(exchange, 200, cocheController.agregarVehiculoAFavoritos(uId, cId));

      } else if (path.equals("/api/favoritos") && method.equals("DELETE")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        int uId = 0;
        int cId = 0;

        if (query != null) {
          if (query.contains("usuarioId=")) {
            uId = Integer.parseInt(query.split("usuarioId=")[1].split("&")[0]);
          }

          if (query.contains("cocheId=")) {
            cId = Integer.parseInt(query.split("cocheId=")[1].split("&")[0]);
          }
        }

        sendResponse(exchange, 200, cocheController.eliminarVehiculoDeFavoritos(uId, cId));

      } else if (path.equals("/api/marcas") && method.equals("GET")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        sendResponse(exchange, 200, catalogoController.obtenerMarcas());

      } else if (path.equals("/api/ciudades") && method.equals("GET")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        sendResponse(exchange, 200, catalogoController.obtenerCiudades());

      } else if (path.equals("/api/combustibles") && method.equals("GET")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        sendResponse(exchange, 200, catalogoController.obtenerCombustibles());

      } else if (path.equals("/api/transmisiones") && method.equals("GET")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        sendResponse(exchange, 200, catalogoController.obtenerTransmisiones());

      } else if (path.equals("/api/categorias") && method.equals("GET")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        sendResponse(exchange, 200, catalogoController.obtenerCategorias());

      } else if (path.equals("/api/colores") && method.equals("GET")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        sendResponse(exchange, 200, catalogoController.obtenerColores());

      } else if (path.equals("/api/etiquetas") && method.equals("GET")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        sendResponse(exchange, 200, catalogoController.obtenerEtiquetas());

      } else if (path.startsWith("/api/modelos") && method.equals("GET")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        int marcaId = 0;

        if (query != null && query.contains("marcaId=")) {
          marcaId = Integer.parseInt(query.split("marcaId=")[1].split("&")[0]);
        }

        sendResponse(exchange, 200, catalogoController.obtenerModelos(marcaId));

      } else if (path.startsWith("/api/versiones") && method.equals("GET")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        int modeloId = 0;

        if (query != null && query.contains("modeloId=")) {
          modeloId = Integer.parseInt(query.split("modeloId=")[1].split("&")[0]);
        }

        sendResponse(exchange, 200, catalogoController.obtenerVersiones(modeloId));

      } else if (path.equals("/api/publicarvehiculo") && method.equals("POST")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        sendResponse(exchange, 201, cocheController.publicarVehiculo(exchange));

      } else if (path.equals("/api/marcarvehiculocomovendido") && method.equals("PATCH")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        java.util.Scanner s = new java.util.Scanner(exchange.getRequestBody()).useDelimiter("\\A");
        String body = s.hasNext() ? s.next() : "";

        int id = Integer.parseInt(body.split("\"id\"\\s*:\\s*")[1].split("[^0-9]")[0]);
        int compradorId = Integer.parseInt(body.split("\"compradorId\"\\s*:\\s*")[1].split("[^0-9]")[0]);

        sendResponse(exchange, 200, cocheController.marcarVehiculoComoVendido(id, compradorId));

      } else if (path.equals("/api/cambiarestadoanunciovehiculo") && method.equals("PATCH")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        java.util.Scanner s = new java.util.Scanner(exchange.getRequestBody()).useDelimiter("\\A");
        String body = s.hasNext() ? s.next() : "";

        int id = Integer.parseInt(body.split("\"id\"\\s*:\\s*")[1].split("[^0-9]")[0]);
        String estado = body.split("\"estado\"\\s*:\\s*\"")[1].split("\"")[0];

        sendResponse(exchange, 200, cocheController.cambiarEstadoAnuncioVehiculo(id, estado));

      } else if (path.equals("/api/eliminaranunciovehiculo") && method.equals("DELETE")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        int id = 0;

        if (query != null && query.contains("id=")) {
          id = Integer.parseInt(query.split("id=")[1].split("&")[0]);
        }

        sendResponse(exchange, 200, cocheController.eliminarAnuncioVehiculo(id));

      } else if (path.equals("/api/docs") && method.equals("GET")) {
        servirArchivo(exchange, "backend/src/main/resources/openapi-coches.html", "text/html; charset=UTF-8");

      } else if (path.startsWith("/api/coches/") && method.equals("GET")) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        int id = Integer.parseInt(path.substring("/api/coches/".length()));
        String json = cocheController.obtenerVehiculoPorId(id);

        if (json != null) {
          sendResponse(exchange, 200, json);
        } else {
          sendResponse(exchange, 404, "{\"error\":\"Coche no encontrado\"}");
        }

      } else {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        sendResponse(
            exchange,
            404,
            "{\"status\":\"error\",\"message\":\"Ruta no encontrada en el servidor: " + path + "\"}"
        );
      }

    } catch (ErrorDeNegocioException e) {
      exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

      sendResponse(
          exchange,
          400,
          "{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}"
      );

    } catch (ErrorDeAccesoADatosException e) {
      exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

      sendResponse(
          exchange,
          500,
          "{\"status\":\"error\",\"message\":\"Error de acceso a datos: " + e.getMessage() + "\"}"
      );

    } catch (Exception e) {
      System.err.println("Error no controlado: " + e.getMessage());

      exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

      sendResponse(
          exchange,
          500,
          "{\"status\":\"error\",\"message\":\"Error interno del servidor: " + e.getMessage() + "\"}"
      );
    }
  }

  private static void addCorsHeaders(HttpExchange exchange) {
    exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
    exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
    exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");
  }

  private void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
    byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

    exchange.sendResponseHeaders(status, bytes.length);

    try (OutputStream os = exchange.getResponseBody()) {
      os.write(bytes);
    }
  }

  private void servirArchivo(HttpExchange exchange, String filePath, String contentType) throws IOException {
    java.io.File file = new java.io.File(filePath);

    if (file.exists()) {
      exchange.getResponseHeaders().set("Content-Type", contentType);
      exchange.sendResponseHeaders(200, file.length());

      try (
          OutputStream os = exchange.getResponseBody();
          java.io.FileInputStream fis = new java.io.FileInputStream(file)
      ) {
        fis.transferTo(os);
      }

    } else {
      exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
      sendResponse(exchange, 404, "{\"error\":\"Archivo no encontrado\"}");
    }
  }

  private boolean esRutaEstatico(String path) {
    return path.startsWith("/css/")
        || path.startsWith("/js/")
        || path.startsWith("/html/")
        || path.startsWith("/assets/")
        || path.endsWith(".html");
  }

  private boolean esRutaSpa(String path) {
    if (path == null || path.isEmpty()) {
      return false;
    }

    if (path.startsWith("/api/")) {
      return false;
    }

    return !path.contains(".");
  }

  private void servirArchivoPublic(HttpExchange exchange, String requestPath) throws IOException {
    Path publicRoot = Paths.get(PUBLIC_DIR).toAbsolutePath().normalize();
    Path resolved = publicRoot.resolve(requestPath.substring(1)).normalize();

    if (!resolved.startsWith(publicRoot) || !Files.exists(resolved) || Files.isDirectory(resolved)) {
      exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
      sendResponse(exchange, 404, "{\"error\":\"Archivo no encontrado\"}");
      return;
    }

    String contentType = contentTypePorExtension(requestPath);

    exchange.getResponseHeaders().set("Content-Type", contentType);

    long length = Files.size(resolved);
    exchange.sendResponseHeaders(200, length);

    try (OutputStream os = exchange.getResponseBody()) {
      Files.copy(resolved, os);
    }
  }

  private String contentTypePorExtension(String path) {
    String lower = path.toLowerCase();

    if (lower.endsWith(".html")) return "text/html; charset=UTF-8";
    if (lower.endsWith(".css")) return "text/css; charset=UTF-8";
    if (lower.endsWith(".js")) return "text/javascript; charset=UTF-8";
    if (lower.endsWith(".json")) return "application/json; charset=UTF-8";
    if (lower.endsWith(".png")) return "image/png";
    if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return "image/jpeg";
    if (lower.endsWith(".gif")) return "image/gif";
    if (lower.endsWith(".svg")) return "image/svg+xml";
    if (lower.endsWith(".webp")) return "image/webp";

    return "application/octet-stream";
  }
}
