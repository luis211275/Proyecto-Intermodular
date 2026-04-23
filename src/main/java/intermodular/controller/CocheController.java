package intermodular.controller;

import com.sun.net.httpserver.HttpExchange;
import intermodular.exception.ErrorDeAccesoADatosException;
import intermodular.exception.ErrorDeNegocioException;
import intermodular.model.*;
import intermodular.service.CocheService;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CocheController {
  private CocheService cocheService = new CocheService();
  private static final String DIRECTORIO_SUBIDA = "src/main/resources/public/assets/img/cars/";

  public String listarCoches(Map<String, String> filtros) throws ErrorDeAccesoADatosException {
    List<Coche> coches = cocheService.obtenerCochesDisponibles(filtros);
    return "[" + coches.stream()
        .map(this::convertirCocheAJson)
        .collect(Collectors.joining(",")) + "]";
  }

  public String obtenerVehiculoPorId(int id) throws ErrorDeAccesoADatosException {
    Coche c = cocheService.obtenerCochePorId(id);
    if (c != null) {
      return convertirCocheAJson(c);
    }
    return null;
  }

  public String publicarVehiculo(HttpExchange intercambio) throws Exception {
    String tipoContenido = intercambio.getRequestHeaders().getFirst("Content-Type");
    if (tipoContenido == null || !tipoContenido.contains("multipart/form-data")) {
      throw new ErrorDeNegocioException("Petición debe ser multipart/form-data");
    }

    String delimitador = tipoContenido.split("boundary=")[1];
    InputStream flujoEntrada = intercambio.getRequestBody();

    // Mapa para guardar los campos del formulario
    Map<String, String> campos = new HashMap<>();
    String urlImagen = "";
    
    byte[] buffer = flujoEntrada.readAllBytes();
    String cuerpo = new String(buffer, StandardCharsets.ISO_8859_1);
    String[] partes = cuerpo.split("--" + delimitador);

    for (String parte : partes) {
      if (parte.contains("name=\"")) {
        String nombreCampo = parte.split("name=\"")[1].split("\"")[0];
        if (parte.contains("filename=\"")) {
          // Es un archivo
          String nombreArchivo = parte.split("filename=\"")[1].split("\"")[0];
          if (!nombreArchivo.isEmpty()) {
            String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf("."));
            String idVendedor = campos.getOrDefault("id_vendedor", "1");
            // Generación de nombre único: timestamp_vendedorId.jpg
            String nombreUnico = System.currentTimeMillis() + "_" + idVendedor + extension;
            Path rutaArchivo = Paths.get(DIRECTORIO_SUBIDA + nombreUnico);

            int finEncabezado = parte.indexOf("\r\n\r\n") + 4;
            int inicioPie = parte.lastIndexOf("\r\n");
            
            if (finEncabezado > 4 && inicioPie > finEncabezado) {
                String contenidoArchivo = parte.substring(finEncabezado, inicioPie);
                // Usamos Files.copy con un InputStream del contenido extraído para cumplir la regla
                java.io.ByteArrayInputStream bis = new java.io.ByteArrayInputStream(contenidoArchivo.getBytes(StandardCharsets.ISO_8859_1));
                Files.copy(bis, rutaArchivo, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                urlImagen = "/assets/img/cars/" + nombreUnico;
            }
          }
        } else {
          // Es un campo
          int finEncabezado = parte.indexOf("\r\n\r\n") + 4;
          int inicioPie = parte.lastIndexOf("\r\n");
          if (finEncabezado > 4 && inicioPie > finEncabezado) {
            String valor = parte.substring(finEncabezado, inicioPie).trim();
            campos.put(nombreCampo, valor);
          }
        }
      }
    }

    Coche coche = new Coche();
    coche.setAnioFabricacion(Integer.parseInt(campos.getOrDefault("anio", "0")));
    coche.setKilometraje(Integer.parseInt(campos.getOrDefault("km", "0")));
    coche.setPrecioVenta(new BigDecimal(campos.getOrDefault("precio", "0")));
    coche.setUrlImagen(urlImagen);
    coche.setEstado("Disponible"); // REGLA DE HIERRO: Debe insertar el coche con ESTADO = 'Disponible'

    Version version = new Version();
    version.setIdVersion(Integer.parseInt(campos.getOrDefault("id_version", "0")));
    coche.setVersion(version);

    TipoCombustible combustible = new TipoCombustible();
    combustible.setIdCombustible(Integer.parseInt(campos.getOrDefault("id_combustible", "0")));
    coche.setCombustible(combustible);

    TipoTransmision transmision = new TipoTransmision();
    transmision.setIdTransmision(Integer.parseInt(campos.getOrDefault("id_transmision", "0")));
    coche.setTransmision(transmision);

    Ciudad ciudad = new Ciudad();
    ciudad.setIdCiudad(Integer.parseInt(campos.getOrDefault("id_ciudad", "0")));
    coche.setCiudad(ciudad);

    Color color = new Color();
    color.setIdColor(Integer.parseInt(campos.getOrDefault("id_color", "0")));
    coche.setColor(color);

    EtiquetaAmbiental etiqueta = new EtiquetaAmbiental();
    etiqueta.setIdEtiqueta(Integer.parseInt(campos.getOrDefault("id_etiqueta", "0")));
    coche.setEtiqueta(etiqueta);

    Categoria categoria = new Categoria();
    categoria.setIdCategoria(Integer.parseInt(campos.getOrDefault("id_categoria", "0")));
    coche.setCategoria(categoria);

    Usuario vendedor = new Usuario();
    vendedor.setIdUsuario(Integer.parseInt(campos.getOrDefault("id_vendedor", "1")));
    coche.setVendedor(vendedor);

    int idGenerado = cocheService.publicarCoche(coche);
    return "{\"status\": \"success\", \"message\":\"Coche publicado correctamente\", \"id\":" + idGenerado + "}";
  }

  public String marcarVehiculoComoVendido(int id) throws ErrorDeAccesoADatosException {
    cocheService.marcarComoVendido(id);
    return "{\"status\": \"success\", \"message\":\"Coche marcado como vendido\"}";
  }

  public String cambiarEstadoAnuncioVehiculo(int id, String nuevoEstado) throws ErrorDeAccesoADatosException {
    cocheService.cambiarEstadoAnuncio(id, nuevoEstado);
    return "{\"status\": \"success\", \"message\":\"Estado del anuncio actualizado a " + nuevoEstado + "\"}";
  }

  public String eliminarAnuncioVehiculo(int id) throws ErrorDeAccesoADatosException {
    cocheService.eliminarAnuncio(id);
    return "{\"status\": \"success\", \"message\":\"Anuncio eliminado (borrado funcional)\"}";
  }

  // Favoritos
  public String listarFavoritosUsuario(int usuarioId) throws ErrorDeAccesoADatosException {
    List<Coche> favoritos = cocheService.listarFavoritos(usuarioId);
    return "[" + favoritos.stream()
        .map(this::convertirCocheAJson)
        .collect(Collectors.joining(",")) + "]";
  }

  public String agregarVehiculoAFavoritos(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException {
    cocheService.agregarFavorito(usuarioId, cocheId);
    return "{\"status\": \"success\", \"message\":\"Coche añadido a favoritos\"}";
  }

  public String eliminarVehiculoDeFavoritos(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException {
    cocheService.eliminarFavorito(usuarioId, cocheId);
    return "{\"status\": \"success\", \"message\":\"Coche eliminado de favoritos\"}";
  }

  private String convertirCocheAJson(Coche c) {
    return String.format(
        "{\"idCoche\":%d,\"anio\":%d,\"km\":%d,\"precio\":%s,\"estado\":\"%s\",\"imagen\":\"%s\"," +
            "\"marca\":\"%s\",\"modelo\":\"%s\",\"version\":\"%s\",\"ciudad\":\"%s\",\"activo\":%b," +
            "\"combustible\":\"%s\",\"transmision\":\"%s\",\"etiquetaAmbiental\":\"%s\",\"categoria\":\"%s\"," +
            "\"color\":\"%s\",\"fechaPublicacion\":\"%s\",\"vendedorId\":%d," +
            "\"vendedorNombre\":\"%s\",\"vendedorEmail\":\"%s\",\"vendedorDni\":\"%s\",\"vendedorTelefono\":\"%s\"," +
            "\"subtotal\":%s,\"iva\":%s,\"comision\":%s,\"total\":%s}",
        c.getIdCoche(), c.getAnioFabricacion(), c.getKilometraje(), c.getPrecioVenta(), c.getEstado(), normalizarUrlImagen(c.getUrlImagen()),
        c.getVersion().getModelo().getMarca().getNombre(),
        c.getVersion().getModelo().getNombre(),
        c.getVersion().getNombre(),
        c.getCiudad().getNombre(),
        c.isActivo(),
        c.getCombustible().getNombre(),
        c.getTransmision().getNombre(),
        c.getEtiqueta().getNombre(),
        c.getCategoria().getNombre(),
        c.getColor() != null ? c.getColor().getNombre() : "No disponible",
        c.getFechaPublicacion() != null ? c.getFechaPublicacion().toString() : "",
        c.getVendedor() != null ? c.getVendedor().getIdUsuario() : 0,
        c.getVendedor() != null ? c.getVendedor().getNombres() + " " + c.getVendedor().getApellidos() : "Desconocido",
        c.getVendedor() != null && c.getVendedor().getEmail() != null ? c.getVendedor().getEmail() : "",
        c.getVendedor() != null && c.getVendedor().getDni() != null ? c.getVendedor().getDni() : "",
        c.getVendedor() != null && c.getVendedor().getTelefono() != null ? c.getVendedor().getTelefono() : "",
        c.getSubtotal() != null ? c.getSubtotal().toString() : "0",
        c.getIva() != null ? c.getIva().toString() : "0",
        c.getComision() != null ? c.getComision().toString() : "0",
        c.getTotal() != null ? c.getTotal().toString() : "0"
    );
  }

  private String normalizarUrlImagen(String url) {
    if (url == null) return "";
    String u = url.trim().replace("\\", "/");
    if (u.isEmpty()) return "";

    // Compatibilidad con datos antiguos en BD
    String prefix1 = "src/main/resources/public";
    if (u.startsWith(prefix1)) {
      u = u.substring(prefix1.length());
    }
    if (u.startsWith("public/")) {
      u = u.substring("public".length()); // deja "/assets/..."
    } else if (u.startsWith("public")) {
      u = u.substring("public".length());
    }

    if (!u.startsWith("/")) u = "/" + u;
    return u;
  }
}
