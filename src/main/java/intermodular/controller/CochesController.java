package intermodular.controller;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import intermodular.exception.CochesException;
import intermodular.model.CocheResponse;
import intermodular.model.Coches;
import intermodular.service.CochesService;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CochesController {
  private final Gson gson = new Gson();
  private final CochesService cochesService;

  public CochesController() {
    this.cochesService = new CochesService();
  }

  public CochesController(CochesService cochesService) {
    this.cochesService = cochesService;
  }


  public void handle(HttpExchange exchange) {
    try {
      String method = exchange.getRequestMethod();
      String path = exchange.getRequestURI().getPath();

      if (path.startsWith("/imagenes/ver/")) {
        servirImagen(exchange);
      } else if (method.equalsIgnoreCase("POST") && path.equals("/apis/coches/publicarcoche")) {
        publicarCoche(exchange);
      } else if (method.equalsIgnoreCase("POST") && path.equals("/apis/coches/subirimagenes")) {
        subirImagenes(exchange);
      } else if (method.equalsIgnoreCase("GET") && path.equals("/apis/coches/listarcoches")) {
        listarCoches(exchange);
      } else if (method.equalsIgnoreCase("GET") && path.equals("/apis/coches/listarrecomendados")) {
        listarRecomendados(exchange);
      } else if (method.equalsIgnoreCase("GET") && path.equals("/apis/coches/listarfavoritos")) {
        listarFavoritos(exchange);
      } else if (method.equalsIgnoreCase("POST") && path.equals("/apis/coches/marcarfavoritos")) {
        marcarFavoritos(exchange);
      } else if (method.equalsIgnoreCase("DELETE") && path.equals("/apis/coches/borrarcoche")) {
        borrarCoche(exchange);
      } else if ((method.equalsIgnoreCase("PATCH") || method.equalsIgnoreCase("POST")) && path.equals("/apis/coches/actualizarestado")) {
        actualizarEstado(exchange);
      } else if (method.equalsIgnoreCase("GET") && path.equals("/apis/coches/detalle")) {
        obtenerCochePorId(exchange);
      } else if (method.equalsIgnoreCase("GET") && path.equals("/apis/coches/presupuesto")) {
        obtenerPresupuesto(exchange);
      } else if (method.equalsIgnoreCase("POST") && path.equals("/apis/coches/comprar")) {
        comprarCoche(exchange);
      } else {
        sendTextResponse(exchange, 405, "Método o ruta no permitida.");
      }
    } catch (CochesException e) {
      sendTextResponse(exchange, 400, e.getMessage());
    } catch (intermodular.exception.FacturasException e) {
      sendTextResponse(exchange, 400, "ERROR_FACTURA: " + e.getMessage());
    } catch (Exception e) {
      try {
        sendTextResponse(exchange, 500, "Error en el servidor: " + e.getMessage());
      } catch (Exception ex) {
        e.printStackTrace();
      }
    }
  }

  // --- MÉTODOS DE ACCIÓN ---

  /**
   * Procesa la publicación de un nuevo anuncio (POST).
   */
  private void publicarCoche(HttpExchange exchange) throws CochesException {
    try {
      String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
      Coches coche = gson.fromJson(requestBody, Coches.class);
      CocheResponse cocheCreado = cochesService.publicarCoche(coche);
      sendResponse(exchange, 201, gson.toJson(cocheCreado));
    } catch (IOException e) {
      throw new CochesException("Error al leer el cuerpo de la petición: " + e.getMessage());
    }
  }

  /**
   * Procesa la subida de imágenes (POST).
   * Se recibe la imagen de forma binaria en el body.
   * El ID del coche y la extensión se pasan por parámetros query: ?idCoche=1&extension=jpg
   */
  private void subirImagenes(HttpExchange exchange) throws CochesException {
    try {
      String query = exchange.getRequestURI().getQuery();
      if (query == null || !query.contains("idCoche=")) {
        sendTextResponse(exchange, 400, "Falta parámetro idCoche en la URL");
        return;
      }

      // Extraer parámetros del query string
      java.util.Map<String, String> params = new java.util.HashMap<>();
      for (String param : query.split("&")) {
        String[] pair = param.split("=");
        if (pair.length > 1) params.put(pair[0], pair[1]);
      }

      int idCoche = Integer.parseInt(params.get("idCoche"));
      String extension = params.getOrDefault("extension", "jpg").toLowerCase();
      // Validación básica de extensión para evitar Path Traversal
      if (!extension.matches("^[a-z0-9]{2,4}$")) {
          sendTextResponse(exchange, 400, "Extensión de archivo no permitida.");
          return;
      }
      boolean esPrincipal = "true".equalsIgnoreCase(params.get("esPrincipal"));

      // Leer el body como chorro de bytes (Binario puro, sin Base64)
      byte[] imageBytes = exchange.getRequestBody().readAllBytes();

      if (imageBytes.length == 0) {
        sendTextResponse(exchange, 400, "El cuerpo de la petición está vacío (sin imagen)");
        return;
      }

      // Guardar en la base de datos como BLOB
      cochesService.guardarImagenCoche(idCoche, imageBytes, extension, esPrincipal);

      // Guardar imagen en disco y registrar URL
      String urlGenerada = guardarImagenEnDisco(idCoche, imageBytes, extension);
      cochesService.guardarUrlImagen(idCoche, urlGenerada, esPrincipal);

      sendResponse(exchange, 200, "{\"mensaje\": \"Imagen guardada y URL generada correctamente\", \"url\": \"" + urlGenerada + "\"}");
    } catch (IOException | NumberFormatException e) {
      throw new CochesException("Error al procesar subida de imagen binaria: " + e.getMessage());
    }
  }

  /**
   * Simula el guardado en un servidor de archivos y devuelve una URL ficticia.
   */
  private String guardarImagenEnDisco(int idCoche, byte[] bytes, String extension) throws IOException {
    String nombreArchivo = "coche_" + idCoche + "_" + System.currentTimeMillis() + "." + extension;
    java.nio.file.Path rutaDestino = java.nio.file.Paths.get("uploads", nombreArchivo);
    
    // Crear carpeta si no existe
    java.nio.file.Files.createDirectories(rutaDestino.getParent());
    
    // Guardar archivo
    java.nio.file.Files.write(rutaDestino, bytes);
    
    // En producción esto sería "https://mi-servidor.com/uploads/..."
    return "/uploads/" + nombreArchivo;
  }

  /**
   * Devuelve el detalle de un coche específico por su ID (GET).
   * Espera parámetro query: ?idCoche=X
   */
  private void obtenerCochePorId(HttpExchange exchange) throws CochesException {
    String query = exchange.getRequestURI().getQuery();
    if (query == null || !query.contains("idCoche=")) {
      sendTextResponse(exchange, 400, "Falta parámetro de consulta: idCoche (Ej: ?idCoche=1)");
      return;
    }

    try {
      int idCoche = Integer.parseInt(query.split("idCoche=")[1].split("&")[0]);
      CocheResponse coche = cochesService.obtenerCochePorId(idCoche);
      sendResponse(exchange, 200, gson.toJson(coche));
    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
      sendTextResponse(exchange, 400, "ID de coche inválido.");
    } catch (CochesException e) {
      sendTextResponse(exchange, 404, e.getMessage());
    }
  }

  /**
   * Obtiene el presupuesto detallado de compra para un coche (GET).
   * Parámetro: ?idCoche=X
   */
  private void obtenerPresupuesto(HttpExchange exchange) throws CochesException {
    String query = exchange.getRequestURI().getQuery();
    if (query == null || !query.contains("idCoche=")) {
      sendTextResponse(exchange, 400, "Falta parámetro idCoche");
      return;
    }

    try {
      int id = Integer.parseInt(query.split("idCoche=")[1].split("&")[0]);
      Map<String, Object> presupuesto = cochesService.obtenerPresupuesto(id);
      sendResponse(exchange, 200, gson.toJson(presupuesto));
    } catch (Exception e) {
      throw new CochesException("Error al calcular presupuesto: " + e.getMessage());
    }
  }

  /**
   * Procesa la compra de un coche (POST).
   * Recibe JSON con idCoche e idComprador.
   */
  private void comprarCoche(HttpExchange exchange) throws CochesException, intermodular.exception.FacturasException {
    try {
      String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
      Map<String, Double> data = gson.fromJson(requestBody, Map.class);
      
      if (data == null || !data.containsKey("idCoche") || !data.containsKey("idComprador")) {
          sendTextResponse(exchange, 400, "Faltan datos idCoche o idComprador");
          return;
      }

      int idCoche = data.get("idCoche").intValue();
      int idComprador = data.get("idComprador").intValue();

      cochesService.comprarCoche(idCoche, idComprador);
      sendResponse(exchange, 200, "{\"mensaje\": \"Compra realizada con éxito. Coche marcado como Vendido.\"}");
    } catch (Exception e) {
      throw new CochesException("Error al procesar la compra: " + e.getMessage());
    }
  }

  /**
   * Devuelve la lista de todos los coches (GET).
   */
  private void listarCoches(HttpExchange exchange) throws CochesException {
    List<CocheResponse> lista = cochesService.listarCoches();
    sendResponse(exchange, 200, gson.toJson(lista));
  }

  /**
   * Devuelve la lista de coches recomendados (GET).
   */
  private void listarRecomendados(HttpExchange exchange) throws CochesException {
    List<CocheResponse> lista = cochesService.listarRecomendados();
    sendResponse(exchange, 200, gson.toJson(lista));
  }

  /**
   * Devuelve la lista de coches favoritos de un usuario (GET).
   * Espera parámetro query: ?usuarioId=X
   */
  private void listarFavoritos(HttpExchange exchange) throws CochesException {
    String query = exchange.getRequestURI().getQuery();
    if (query == null || !query.contains("usuarioId=")) {
      sendTextResponse(exchange, 400, "Falta parámetro de consulta: usuarioId (Ej: ?usuarioId=1)");
      return;
    }

    try {
      int usuarioId = Integer.parseInt(query.split("usuarioId=")[1].split("&")[0]);
      List<CocheResponse> lista = cochesService.listarFavoritos(usuarioId);
      sendResponse(exchange, 200, gson.toJson(lista));
    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
      sendTextResponse(exchange, 400, "ID de usuario inválido.");
    }
  }

  /**
   * Marca o desmarca un coche como favorito (POST).
   * Espera un JSON con { "usuarioId": 1, "cocheId": 1 }
   */
  private void marcarFavoritos(HttpExchange exchange) throws CochesException {
    try {
      String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
      // Usamos una clase anónima o un mapa para leer el JSON simple
      java.util.Map<String, Double> data = gson.fromJson(requestBody, java.util.Map.class);
      
      if (data.get("usuarioId") == null || data.get("cocheId") == null) {
        sendTextResponse(exchange, 400, "Faltan parámetros: usuarioId o cocheId");
        return;
      }

      int usuarioId = data.get("usuarioId").intValue();
      int cocheId = data.get("cocheId").intValue();
      
      boolean esFavorito = cochesService.marcarFavoritos(usuarioId, cocheId);
      
      String mensaje = esFavorito ? "Coche añadido a favoritos" : "Coche eliminado de favoritos";
      sendResponse(exchange, 200, "{\"mensaje\": \"" + mensaje + "\", \"esFavorito\": " + esFavorito + "}");
    } catch (IOException e) {
      throw new CochesException("Error al procesar la petición de favorito: " + e.getMessage());
    }
  }

  /**
   * Procesa el borrado lógico de un coche (DELETE).
   */
  private void borrarCoche(HttpExchange exchange) throws CochesException {
    try {
      String query = exchange.getRequestURI().getQuery();
      if (query == null || !query.contains("idCoche=")) {
        sendTextResponse(exchange, 400, "Falta parámetro idCoche en la URL");
        return;
      }
      
      java.util.Map<String, String> params = new java.util.HashMap<>();
      for (String param : query.split("&")) {
        String[] pair = param.split("=");
        if (pair.length > 1) params.put(pair[0], pair[1]);
      }
      
      int idCoche = Integer.parseInt(params.get("idCoche"));
      cochesService.borrarCoche(idCoche);
      
      sendResponse(exchange, 200, "{\"mensaje\": \"Coche desactivado correctamente (borrado lógico)\", \"idCoche\": " + idCoche + "}");
    } catch (CochesException e) {
      sendTextResponse(exchange, 404, e.getMessage());
    } catch (Exception e) {
      sendTextResponse(exchange, 500, "Error al borrar coche: " + e.getMessage());
    }
  }

  /**
   * Actualiza el estado de un coche (PATCH/POST).
   * Espera un JSON con { "idCoche": 1, "estado": "Vendido" }
   */
  private void actualizarEstado(HttpExchange exchange) throws CochesException {
    try {
      String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
      java.util.Map<String, Object> data = gson.fromJson(requestBody, java.util.Map.class);

      if (data.get("idCoche") == null || data.get("estado") == null) {
        sendTextResponse(exchange, 400, "Faltan parámetros: idCoche o estado");
        return;
      }

      int idCoche = ((Double) data.get("idCoche")).intValue();
      String nuevoEstado = (String) data.get("estado");

      cochesService.actualizarEstado(idCoche, nuevoEstado);

      sendResponse(exchange, 200, "{\"mensaje\": \"Estado actualizado correctamente\", \"idCoche\": " + idCoche + ", \"nuevoEstado\": \"" + nuevoEstado + "\"}");
    } catch (IOException e) {
      throw new CochesException("Error al actualizar el estado: " + e.getMessage());
    }
  }

  // --- MÉTODOS AUXILIARES DE RESPUESTA ---

  public void servirImagen(HttpExchange exchange) {
    try {
      String path = exchange.getRequestURI().getPath();
      String idStr = path.substring("/imagenes/ver/".length());
      int idImagen = Integer.parseInt(idStr);

      Map<String, Object> imagen = cochesService.obtenerImagen(idImagen);

      if (imagen != null) {
        byte[] datos = (byte[]) imagen.get("datos");
        String ext = (String) imagen.get("extension");

        String contentType = "image/jpeg";
        if ("png".equalsIgnoreCase(ext)) contentType = "image/png";
        else if ("gif".equalsIgnoreCase(ext)) contentType = "image/gif";
        else if ("webp".equalsIgnoreCase(ext)) contentType = "image/webp";

        exchange.getResponseHeaders().add("Content-Type", contentType);
        exchange.sendResponseHeaders(200, datos.length);
        try (java.io.OutputStream os = exchange.getResponseBody()) {
          os.write(datos);
        }
      } else {
        sendTextResponse(exchange, 404, "Imagen no encontrada en la BD");
      }
    } catch (Exception e) {
      sendTextResponse(exchange, 500, "Error al recuperar imagen: " + e.getMessage());
    }
  }

  private void sendResponse(HttpExchange exchange, int status, String body) {
    try {
      exchange.getResponseHeaders().add("Content-Type", "application/json");
      byte[] bytes = body.getBytes();
      exchange.sendResponseHeaders(status, bytes.length);
      OutputStream os = exchange.getResponseBody();
      os.write(bytes);
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void sendTextResponse(HttpExchange exchange, int status, String body) {
    try {
      exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
      String jsonError = String.format("{\"error\": \"%s\", \"status\": %d}", body.replace("\"", "\\\""), status);
      byte[] bytes = jsonError.getBytes(StandardCharsets.UTF_8);
      exchange.sendResponseHeaders(status, bytes.length);
      OutputStream os = exchange.getResponseBody();
      os.write(bytes);
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}