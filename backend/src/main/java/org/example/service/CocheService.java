package org.example.service;

import org.example.dao.CocheDAO;
import org.example.dao.impl.CocheDAOImpl;
import org.example.exception.ErrorDeNegocioException;
import org.example.exception.ErrorDeAccesoADatosException;
import org.example.exception.PrecioInvalidoException;
import org.example.exception.DatosIncompletosException;
import org.example.model.Coche;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public class CocheService {
    private CocheDAO cocheDAO = new CocheDAOImpl();

    public List<Coche> obtenerCochesDisponibles(Map<String, String> filtros) throws ErrorDeAccesoADatosException {
        List<Coche> coches = cocheDAO.listarCochesDisponibles(filtros);
        for (Coche c : coches) {
            calcularPrecios(c);
        }
        return coches;
    }

    public Coche obtenerCochePorId(int id) throws ErrorDeAccesoADatosException {
        Coche c = cocheDAO.buscarPorId(id);
        if (c != null) {
            calcularPrecios(c);
        }
        return c;
    }

    private void calcularPrecios(Coche c) {
        if (c.getPrecioVenta() != null) {
            BigDecimal subtotal = c.getPrecioVenta();
            BigDecimal iva = subtotal.multiply(new BigDecimal("0.21")).setScale(2, RoundingMode.HALF_UP);
            BigDecimal comision = subtotal.multiply(new BigDecimal("0.05")).setScale(2, RoundingMode.HALF_UP);
            BigDecimal total = subtotal.add(iva).add(comision).setScale(2, RoundingMode.HALF_UP);

            c.setSubtotal(subtotal);
            c.setIva(iva);
            c.setComision(comision);
            c.setTotal(total);
        }
    }

    public int publicarCoche(Coche c) throws ErrorDeNegocioException, ErrorDeAccesoADatosException, PrecioInvalidoException, DatosIncompletosException {
        // Validaciones de negocio
        validarCoche(c);
        
        int idGenerado = 0;
        try {
            idGenerado = cocheDAO.insertarCocheNuevo(c);
        } catch (ErrorDeAccesoADatosException e) {
            // Si falla la BD, intentamos borrar la imagen si existe (Transaccionalidad de Disco)
            if (c.getImagen() != null && !c.getImagen().isEmpty()) {
                borrarImagen(c.getImagen());
            }
            throw e;
        }
        return idGenerado;
    }

    private void validarCoche(Coche c) throws ErrorDeNegocioException, PrecioInvalidoException, DatosIncompletosException {
        if (c.getPrecioVenta() == null || c.getPrecioVenta().doubleValue() <= 0) {
            throw new PrecioInvalidoException("El precio debe ser mayor a 0");
        }
        if (c.getAnioFabricacion() < 1900) {
            throw new ErrorDeNegocioException("Año de fabricación inválido");
        }
        if (c.getKilometraje() < 0) {
            throw new ErrorDeNegocioException("El kilometraje no puede ser negativo");
        }
        if (c.getVersion() == null || c.getVersion().getIdVersion() == 0) {
            throw new DatosIncompletosException("Debe especificar una versión válida");
        }
        if (c.getCombustible() == null || c.getCombustible().getIdCombustible() == 0) {
            throw new DatosIncompletosException("Debe especificar un tipo de combustible");
        }
        if (c.getTransmision() == null || c.getTransmision().getIdTransmision() == 0) {
            throw new DatosIncompletosException("Debe especificar un tipo de transmisión");
        }
        if (c.getCiudad() == null || c.getCiudad().getIdCiudad() == 0) {
            throw new DatosIncompletosException("Debe especificar una ciudad");
        }
        if (c.getColor() == null || c.getColor().getIdColor() == 0) {
            throw new DatosIncompletosException("Debe especificar un color");
        }
        if (c.getEtiqueta() == null || c.getEtiqueta().getIdEtiqueta() == 0) {
            throw new DatosIncompletosException("Debe especificar una etiqueta ambiental");
        }
        if (c.getCategoria() == null || c.getCategoria().getIdCategoria() == 0) {
            throw new DatosIncompletosException("Debe especificar una categoría");
        }
    }

    private void borrarImagen(String urlRelativa) {
        try {
            String pathStr = "src/main/resources/public" + urlRelativa;
            java.nio.file.Path path = java.nio.file.Paths.get(pathStr);
            java.nio.file.Files.deleteIfExists(path);
        } catch (Exception e) {
            System.err.println("No se pudo borrar la imagen tras error en BD: " + e.getMessage());
        }
    }

    public void marcarComoVendido(int idCoche) throws ErrorDeAccesoADatosException {
        cocheDAO.actualizarEstadoAVendido(idCoche);
    }

    public void cambiarEstadoAnuncio(int idCoche, String nuevoEstado) throws ErrorDeAccesoADatosException {
        cocheDAO.cambiarEstado(idCoche, nuevoEstado);
    }

    // Favoritos
    public void agregarFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException {
        cocheDAO.agregarFavorito(usuarioId, cocheId);
    }

    public void eliminarFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException {
        cocheDAO.eliminarFavorito(usuarioId, cocheId);
    }

    public List<Coche> listarFavoritos(int usuarioId) throws ErrorDeAccesoADatosException {
        List<Coche> favoritos = cocheDAO.listarFavoritos(usuarioId);
        for (Coche c : favoritos) {
            calcularPrecios(c);
        }
        return favoritos;
    }

    public boolean esFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException {
        return cocheDAO.esFavorito(usuarioId, cocheId);
    }

    public void eliminarAnuncio(int idCoche) throws ErrorDeAccesoADatosException {
        cocheDAO.desactivarAnuncioPorEliminacion(idCoche);
    }
}
