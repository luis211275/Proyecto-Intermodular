package intermodular.dao;

import intermodular.exception.CochesException;
import intermodular.model.CocheResponse;
import intermodular.model.Coches;

import java.util.List;
import java.util.Map;

public interface CochesDAO {
  int publicarCoche(Coches coche) throws CochesException;
  List<CocheResponse> listarCoches() throws CochesException;
  List<CocheResponse> listarRecomendados() throws CochesException;
  CocheResponse obtenerCochePorId(int idCoche) throws CochesException;
  boolean marcarFavoritos(int usuarioId, int cocheId) throws CochesException;
  List<CocheResponse> listarFavoritos(int usuarioId) throws CochesException;
  void actualizarEstado(int idCoche, String nuevoEstado) throws CochesException;
  void desactivarCoche(int idCoche) throws CochesException;
  void guardarImagenCoche(int cocheId, byte[] datos, String extension, boolean esPrincipal) throws CochesException;
  void guardarUrlImagen(int cocheId, String url, boolean esPrincipal) throws CochesException;
  Map<String, Object> obtenerImagenCompleta(int idImagen) throws CochesException;
}
