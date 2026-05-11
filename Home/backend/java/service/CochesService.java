package service;

import dao.CochesDAO;
import dao.impl.CochesDAOImpl;
import exceptions.CochesException;
import exceptions.FacturasException;
import model.CocheResponse;
import model.Coches;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class CochesService {
    private final CochesDAO dao;
    private final FacturasService facturasService;

    public CochesService(CochesDAO dao, FacturasService facturasService) {
        this.dao = dao;
        this.facturasService = facturasService;
    }

    public CochesService() {
        this.dao = new CochesDAOImpl();
        this.facturasService = new FacturasService();
    }

    public CocheResponse publicarCoche(Coches coche) throws CochesException {
        if (coche.getAnioFabricacion() < 1900) {
            throw new CochesException("El año de fabricación debe ser posterior a 1900.");
        }
        if (coche.getPrecioVenta() <= 0) {
            throw new CochesException("El precio de venta debe ser positivo.");
        }

        int id = dao.publicarCoche(coche);
        return obtenerCochePorId(id);
    }

    public List<CocheResponse> listarCoches() throws CochesException {
        return dao.listarCoches();
    }

    public List<CocheResponse> listarRecomendados() throws CochesException {
        return dao.listarRecomendados();
    }

    public CocheResponse obtenerCochePorId(int idCoche) throws CochesException {
        CocheResponse coche = dao.obtenerCochePorId(idCoche);
        if (coche == null) {
            throw new CochesException("No se encontró el coche con ID: " + idCoche);
        }
        return coche;
    }

    public boolean marcarFavoritos(int usuarioId, int cocheId) throws CochesException {
        // Regla de negocio: No se puede marcar como favorito un coche desactivado
        List<CocheResponse> coches = dao.listarCoches(); // listarCoches ya filtra desactivados
        boolean existeYActivo = coches.stream().anyMatch(c -> c.getId() == cocheId);

        if (!existeYActivo) {
            throw new CochesException("El coche no está disponible o ha sido retirado.");
        }

        return dao.marcarFavoritos(usuarioId, cocheId);
    }

    public List<CocheResponse> listarFavoritos(int usuarioId) throws CochesException {
        return dao.listarFavoritos(usuarioId);
    }

    public void actualizarEstado(int idCoche, String nuevoEstado) throws CochesException {
        if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
            throw new CochesException("El estado no puede estar vacío.");
        }

        List<String> estadosValidos = Arrays.asList("Disponible", "Vendido", "Desactivado");

        if (!estadosValidos.contains(nuevoEstado)) {
            throw new CochesException("Estado no válido: " + nuevoEstado);
        }

        dao.actualizarEstado(idCoche, nuevoEstado);
    }

    public void borrarCoche(int idCoche) throws CochesException {
        dao.desactivarCoche(idCoche);
    }

    public void guardarImagenCoche(int cocheId, byte[] datos, String extension, boolean esPrincipal) throws CochesException {
        dao.guardarImagenCoche(cocheId, datos, extension, esPrincipal);
    }

    public void guardarUrlImagen(int cocheId, String url, boolean esPrincipal) throws CochesException {
        dao.guardarUrlImagen(cocheId, url, esPrincipal);
    }

    public Map<String, Object> obtenerImagen(int idImagen) throws CochesException {
        return dao.obtenerImagenCompleta(idImagen);
    }

    public Map<String, Object> obtenerPresupuesto(int idCoche) throws CochesException {
        CocheResponse coche = obtenerCochePorId(idCoche);
        return facturasService.calcularPresupuesto(coche.getPrecioVenta(), idCoche);
    }

    public void comprarCoche(int idCoche, int idComprador) throws CochesException, FacturasException {
        CocheResponse coche = obtenerCochePorId(idCoche);
        if (!"Disponible".equals(coche.getEstado())) {
            throw new CochesException("El coche no está disponible para la compra.");
        }

        facturasService.crearFacturaParaCoche(idComprador, idCoche, coche.getPrecioVenta());
        dao.actualizarEstado(idCoche, "Vendido");
    }
}