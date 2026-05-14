package org.example.service;

import org.example.dao.CocheDAO;
import org.example.dao.impl.CocheDAOImpl;
import org.example.exception.DatosIncompletosException;
import org.example.exception.ErrorDeAccesoADatosException;
import org.example.exception.ErrorDeNegocioException;
import org.example.exception.PrecioInvalidoException;
import org.example.model.Coche;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Servicio para gestionar la lógica de negocio de los coches.
 */
public class CocheService {
    private CocheDAO cocheDAO = new CocheDAOImpl();

  /**
   * Obtiene la lista de coches disponibles filtrada.
   *
   * @param filtros los filtros a aplicar.
   * @return la lista de coches disponibles.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public List<Coche> obtenerCochesDisponibles(Map<String, String> filtros) throws ErrorDeAccesoADatosException {
        List<Coche> coches = cocheDAO.listarCochesDisponibles(filtros);
        for (Coche c : coches) {
            calcularPrecios(c);
        }
        return coches;
    }

  /**
   * Obtiene un coche por su ID.
   *
   * @param id el ID del coche.
   * @return el coche encontrado o null.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public Coche obtenerCochePorId(int id) throws ErrorDeAccesoADatosException {
        Coche c = cocheDAO.buscarPorId(id);
        if (c != null) {
            calcularPrecios(c);
        }
        return c;
    }

    private void calcularPrecios(Coche c) {
        if (c.getPrecioVenta() != null) {
            double precio = c.getPrecioVenta().doubleValue();
            double iva = Math.round(precio * 0.21 * 100.0) / 100.0;
            double comision = Math.round(precio * 0.03 * 100.0) / 100.0;
            double total = Math.round((precio + iva + comision) * 100.0) / 100.0;

            c.setSubtotal(BigDecimal.valueOf(precio));
            c.setIva(BigDecimal.valueOf(iva));
            c.setComision(BigDecimal.valueOf(comision));
            c.setTotal(BigDecimal.valueOf(total));
        }
    }

  /**
   * Publica un nuevo coche.
   *
   * @param c el coche a publicar.
   * @return el ID del coche publicado.
   * @throws ErrorDeNegocioException      si hay un error en la lógica de negocio.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   * @throws PrecioInvalidoException      si el precio es inválido.
   * @throws DatosIncompletosException    si faltan datos obligatorios.
   */
  public int publicarCoche(Coche c) throws ErrorDeNegocioException, ErrorDeAccesoADatosException, PrecioInvalidoException, DatosIncompletosException {
        validarCoche(c);
        return cocheDAO.insertarCocheNuevo(c);
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
        if (c.getVendedor() == null || c.getVendedor().getIdUsuario() == 0) {
            throw new DatosIncompletosException("Debe existir un vendedor válido para publicar el anuncio");
        }
    }

  /**
   * Registra la compra de un coche.
   *
   * @param idCoche     el ID del coche.
   * @param compradorId el ID del comprador.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   * @throws DatosIncompletosException    si faltan datos obligatorios para la compra.
   */
  public void registrarCompra(int idCoche, int compradorId) throws ErrorDeAccesoADatosException, DatosIncompletosException {
        Coche coche = obtenerCochePorId(idCoche);

        if (coche == null) {
            throw new DatosIncompletosException("El coche no existe");
        }
        if (compradorId <= 0) {
            throw new DatosIncompletosException("Debe existir un comprador válido");
        }
        if (coche.getVendedor() == null || coche.getVendedor().getIdUsuario() <= 0) {
            throw new DatosIncompletosException("Debe existir un vendedor válido");
        }

        calcularPrecios(coche);

        cocheDAO.registrarCompra(
            idCoche,
            compradorId,
            coche.getVendedor().getIdUsuario(),
            coche.getSubtotal(),
            coche.getIva(),
            coche.getComision(),
            coche.getTotal()
        );
    }

  /**
   * Cambia el estado de un anuncio.
   *
   * @param idCoche     el ID del coche.
   * @param nuevoEstado el nuevo estado a establecer.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public void cambiarEstadoAnuncio(int idCoche, String nuevoEstado) throws ErrorDeAccesoADatosException {
        cocheDAO.cambiarEstado(idCoche, nuevoEstado);
    }

  /**
   * Agrega un coche a favoritos.
   *
   * @param usuarioId el ID del usuario.
   * @param cocheId   el ID del coche.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
// Gestionamos favoritos.
    public void agregarFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException {
        cocheDAO.agregarFavorito(usuarioId, cocheId);
    }

  /**
   * Elimina un coche de favoritos.
   *
   * @param usuarioId el ID del usuario.
   * @param cocheId   el ID del coche.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public void eliminarFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException {
        cocheDAO.eliminarFavorito(usuarioId, cocheId);
    }

  /**
   * Lista los coches favoritos de un usuario.
   *
   * @param usuarioId el ID del usuario.
   * @return la lista de coches favoritos.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public List<Coche> listarFavoritos(int usuarioId) throws ErrorDeAccesoADatosException {
        List<Coche> favoritos = cocheDAO.listarFavoritos(usuarioId);
        for (Coche c : favoritos) {
            calcularPrecios(c);
        }
        return favoritos;
    }

  /**
   * Comprueba si un coche es favorito de un usuario.
   *
   * @param usuarioId el ID del usuario.
   * @param cocheId   el ID del coche.
   * @return true si es favorito, false en caso contrario.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public boolean esFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException {
        return cocheDAO.esFavorito(usuarioId, cocheId);
    }

  /**
   * Elimina un anuncio (borrado lógico).
   *
   * @param idCoche el ID del coche.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  public void eliminarAnuncio(int idCoche) throws ErrorDeAccesoADatosException {
        cocheDAO.desactivarAnuncioPorEliminacion(idCoche);
    }
}