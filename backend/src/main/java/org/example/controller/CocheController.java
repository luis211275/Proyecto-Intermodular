package org.example.controller;

import com.sun.net.httpserver.HttpExchange;
import org.example.exception.*;
import org.example.model.Categoria;
import org.example.model.Ciudad;
import org.example.model.Coche;
import org.example.model.Color;
import org.example.model.EtiquetaAmbiental;
import org.example.model.TipoCombustible;
import org.example.model.TipoTransmision;
import org.example.model.Usuario;
import org.example.model.Version;
import org.example.service.CocheService;

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
  private static final String DIRECTORIO_SUBIDA = "frontend/assets/img/cars/";
  private CocheService cocheService = new CocheService();

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
    String cuerpo = new String(intercambio.getRequestBody().readAllBytes(), StandardCharsets.ISO_8859_1);
    String[] partes = cuerpo.split("--" + delimitador);

    Map<String, String> campos = new HashMap<>();
    String rutaImagen = "";

    for (String parte : partes) {
      if (!parte.contains("name=\"")) {
        continue;
      }

      String nombreCampo = obtenerValorEntre(parte, "name=\"", "\"");
      if (nombreCampo == null) {
        continue;
      }

      if (parte.contains("filename=\"")) {
        if (rutaImagen.isEmpty()) {
          rutaImagen = guardarImagen(parte, campos.get("id_vendedor"));
        }
      } else {
        String valorCampo = extraerContenidoParte(parte);
        if (valorCampo != null) {
          campos.put(nombreCampo, valorCampo.trim());
        }
      }
    }

    Coche coche = crearCoche(campos, rutaImagen);
    int idGenerado = cocheService.publicarCoche(coche);
    return "{\"status\": \"success\", \"message\":\"Coche publicado correctamente\", \"id\":" + idGenerado + "}";
  }

  public String marcarVehiculoComoVendido(int id, int compradorId) throws ErrorDeAccesoADatosException, DatosIncompletosException {
    cocheService.registrarCompra(id, compradorId);
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

  private Coche crearCoche(Map<String, String> campos, String rutaImagen) {
    Coche coche = new Coche();
    coche.setAnioFabricacion(Integer.parseInt(campos.getOrDefault("anio", "0")));
    coche.setKilometraje(Integer.parseInt(campos.getOrDefault("km", "0")));
    coche.setPrecioVenta(new BigDecimal(campos.getOrDefault("precio", "0")));
    coche.setImagen(rutaImagen);
    coche.setEstado("Disponible");

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
    vendedor.setIdUsuario(Integer.parseInt(campos.getOrDefault("id_vendedor", "0")));
    coche.setVendedor(vendedor);

    return coche;
  }

  private String guardarImagen(String parte, String idVendedor) throws Exception {
    String nombreOriginal = obtenerValorEntre(parte, "filename=\"", "\"");
    if (nombreOriginal == null || nombreOriginal.isBlank()) {
      return "";
    }

    String contenidoArchivo = extraerContenidoParte(parte);
    if (contenidoArchivo == null) {
      return "";
    }

    String extension = ".jpg";
    int ultimoPunto = nombreOriginal.lastIndexOf('.');
    if (ultimoPunto >= 0) {
      extension = nombreOriginal.substring(ultimoPunto);
    }

    String vendedor = (idVendedor == null || idVendedor.isBlank()) ? "0" : idVendedor;
    String nombreArchivo = "coche_" + vendedor + "_" + System.currentTimeMillis() + extension;

    Files.createDirectories(Paths.get(DIRECTORIO_SUBIDA));

    Path rutaArchivo = Paths.get(DIRECTORIO_SUBIDA, nombreArchivo);
    Files.write(rutaArchivo, contenidoArchivo.getBytes(StandardCharsets.ISO_8859_1));

    return "/assets/img/cars/" + nombreArchivo;
  }

  private String extraerContenidoParte(String parte) {
    int inicio = parte.indexOf("\r\n\r\n");
    int fin = parte.lastIndexOf("\r\n");

    if (inicio < 0 || fin <= inicio) {
      return null;
    }

    return parte.substring(inicio + 4, fin);
  }

  private String obtenerValorEntre(String texto, String inicio, String fin) {
    int desde = texto.indexOf(inicio);
    if (desde < 0) {
      return null;
    }

    desde += inicio.length();
    int hasta = texto.indexOf(fin, desde);
    if (hasta < 0) {
      return null;
    }

    return texto.substring(desde, hasta);
  }

  private String convertirCocheAJson(Coche c) {
    return String.format(
        "{\"idCoche\":%d,\"anio\":%d,\"km\":%d,\"precio\":%s,\"estado\":\"%s\",\"imagen\":\"%s\"," +
            "\"marca\":\"%s\",\"modelo\":\"%s\",\"version\":\"%s\",\"ciudad\":\"%s\",\"activo\":%b," +
            "\"combustible\":\"%s\",\"transmision\":\"%s\",\"etiquetaAmbiental\":\"%s\",\"categoria\":\"%s\"," +
            "\"color\":\"%s\",\"fechaPublicacion\":\"%s\",\"vendedorId\":%d," +
            "\"vendedorNombre\":\"%s\",\"vendedorEmail\":\"%s\",\"vendedorDni\":\"%s\",\"vendedorTelefono\":\"%s\"," +
            "\"subtotal\":%s,\"iva\":%s,\"comision\":%s,\"total\":%s}",
        c.getIdCoche(), c.getAnioFabricacion(), c.getKilometraje(), c.getPrecioVenta(), c.getEstado(),
        normalizarUrlImagen(c.getImagen()),
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
        c.getTotal() != null ? c.getTotal().toString() : "0");
  }

  private String normalizarUrlImagen(String url) {
    if (url == null)
      return "";
    String u = url.trim().replace("\\", "/");
    if (u.isEmpty())
      return "";

    // Compatibilidad con datos antiguos en BD
    String prefix1 = "src/main/resources/public";
    if (u.startsWith(prefix1)) {
      u = u.substring(prefix1.length());
    }
    if (u.startsWith("fontend/")) {
      u = u.substring("fontend".length()); // deja "/assets/..."
    } else if (u.startsWith("fontend")) {
      u = u.substring("fontend".length());
    }

    if (!u.startsWith("/"))
      u = "/" + u;
    return u;
  }
}
