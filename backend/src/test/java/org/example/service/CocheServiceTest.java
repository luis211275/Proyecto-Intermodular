package org.example.service;

import org.example.exception.DatosIncompletosException;
import org.example.exception.ErrorDeNegocioException;
import org.example.exception.PrecioInvalidoException;
import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CocheServiceTest {

    private CocheService cocheService;
    private Coche coche;

    @BeforeEach
    public void setUp() {
        cocheService = new CocheService();
        coche = new Coche();
        
        // Datos base para un coche válido
        coche.setPrecioVenta(new BigDecimal("15000"));
        coche.setAnioFabricacion(2020);
        coche.setKilometraje(50000);

        Version version = new Version();
        version.setIdVersion(1);
        coche.setVersion(version);

        TipoCombustible combustible = new TipoCombustible();
        combustible.setIdCombustible(1);
        coche.setCombustible(combustible);

        TipoTransmision transmision = new TipoTransmision();
        transmision.setIdTransmision(1);
        coche.setTransmision(transmision);

        Ciudad ciudad = new Ciudad();
        ciudad.setIdCiudad(1);
        coche.setCiudad(ciudad);

        Color color = new Color();
        color.setIdColor(1);
        coche.setColor(color);

        EtiquetaAmbiental etiqueta = new EtiquetaAmbiental();
        etiqueta.setIdEtiqueta(1);
        coche.setEtiqueta(etiqueta);

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        coche.setCategoria(categoria);

        Usuario vendedor = new Usuario();
        vendedor.setIdUsuario(1);
        coche.setVendedor(vendedor);
    }

    @Test
    public void testPublicarCocheConPrecioInvalido() {
        // Condición a probar: precio inválido
        coche.setPrecioVenta(new BigDecimal("-100"));

        Exception exception = assertThrows(PrecioInvalidoException.class, () -> {
            cocheService.publicarCoche(coche);
        });

        String expectedMessage = "El precio debe ser mayor a 0";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Debería lanzar una excepción por precio inválido.");
    }

    @Test
    public void testPublicarCocheConAnioInvalido() {
        // Condición a probar: año de fabricación inválido
        coche.setAnioFabricacion(1899);

        Exception exception = assertThrows(ErrorDeNegocioException.class, () -> {
            cocheService.publicarCoche(coche);
        });

        String expectedMessage = "Año de fabricación inválido";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Debería lanzar una excepción por año de fabricación inválido.");
    }

    @Test
    public void testPublicarCocheSinVersion() {
        // Condición a probar: Faltan datos (versión)
        coche.setVersion(null);

        Exception exception = assertThrows(DatosIncompletosException.class, () -> {
            cocheService.publicarCoche(coche);
        });

        String expectedMessage = "Debe especificar una versión válida";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Debería lanzar una excepción por datos incompletos (versión).");
    }
}