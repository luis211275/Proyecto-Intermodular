package org.example.dao;

import org.example.exception.ErrorDeAccesoADatosException;
import org.example.model.Coche;

import java.util.List;
import java.util.Map;

/**
 * Interfaz para las operaciones de acceso a datos de los coches.
 */
public interface CocheDAO {
  /**
   * Obtiene la lista de coches disponibles según los filtros.
   *
   * @param filtros los filtros de búsqueda.
   * @return la lista de coches disponibles.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  List<Coche> listarCochesDisponibles(Map<String, String> filtros) throws ErrorDeAccesoADatosException;

  /**
   * Busca un coche por su ID.
   *
   * @param id el ID del coche.
   * @return el coche encontrado o null.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  Coche buscarPorId(int id) throws ErrorDeAccesoADatosException;

  /**
   * Inserta un nuevo coche.
   *
   * @param c el coche a insertar.
   * @return el ID generado para el coche.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  int insertarCocheNuevo(Coche c) throws ErrorDeAccesoADatosException;

  /**
   * Registra la compra de un coche.
   *
   * @param cocheId            el ID del coche.
   * @param compradorId        el ID del comprador.
   * @param vendedorId         el ID del vendedor.
   * @param totalBase          el precio base.
   * @param ivaImporte         el importe del IVA.
   * @param comisionPlataforma la comisión de la plataforma.
   * @param totalPagado        el total pagado.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  void registrarCompra(int cocheId, int compradorId, int vendedorId,
      java.math.BigDecimal totalBase, java.math.BigDecimal ivaImporte,
      java.math.BigDecimal comisionPlataforma, java.math.BigDecimal totalPagado) throws ErrorDeAccesoADatosException;

  /**
   * Actualiza el estado de un coche a vendido.
   *
   * @param id el ID del coche.
   * @return true si se actualizó correctamente, false en caso contrario.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  boolean actualizarEstadoAVendido(int id) throws ErrorDeAccesoADatosException;

  /**
   * Desactiva el anuncio de un coche (borrado lógico).
   *
   * @param id el ID del coche.
   * @return true si se desactivó correctamente, false en caso contrario.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  boolean desactivarAnuncioPorEliminacion(int id) throws ErrorDeAccesoADatosException;

  /**
   * Cambia el estado de un coche.
   *
   * @param id          el ID del coche.
   * @param nuevoEstado el nuevo estado a asignar.
   * @return true si se cambió correctamente, false en caso contrario.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  boolean cambiarEstado(int id, String nuevoEstado) throws ErrorDeAccesoADatosException;

  /**
   * Añade un coche a la lista de favoritos de un usuario.
   *
   * @param usuarioId el ID del usuario.
   * @param cocheId   el ID del coche.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  void agregarFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException;

  /**
   * Elimina un coche de la lista de favoritos de un usuario.
   *
   * @param usuarioId el ID del usuario.
   * @param cocheId   el ID del coche.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  void eliminarFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException;

  /**
   * Obtiene la lista de coches favoritos de un usuario.
   *
   * @param usuarioId el ID del usuario.
   * @return la lista de coches favoritos.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  List<Coche> listarFavoritos(int usuarioId) throws ErrorDeAccesoADatosException;

  /**
   * Comprueba si un coche es favorito de un usuario.
   *
   * @param usuarioId el ID del usuario.
   * @param cocheId   el ID del coche.
   * @return true si es favorito, false en caso contrario.
   * @throws ErrorDeAccesoADatosException si ocurre un error al acceder a los datos.
   */
  boolean esFavorito(int usuarioId, int cocheId) throws ErrorDeAccesoADatosException;
}