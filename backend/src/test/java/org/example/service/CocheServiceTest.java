package org.example.service;

import org.example.exception.ErrorDeNegocioException;
import org.example.exception.PrecioInvalidoException;
import org.example.model.Coche;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el servicio CocheService.
 */
public class CocheServiceTest {

    private CocheService cocheService = new CocheService();

  /**
   * Prueba la validación de un precio negativo.
   */
  @Test
    public void testValidarPrecioNegativo() {
        Coche c = new Coche();
        c.setPrecioVenta(new BigDecimal("-100"));
        
        Exception exception = assertThrows(PrecioInvalidoException.class, () -> {
            cocheService.publicarCoche(c);
        });

        assertEquals("El precio debe ser mayor a 0", exception.getMessage());
    }

  /**
   * Prueba la validación de un año de fabricación inválido.
   */
  @Test
    public void testValidarAnioInvalido() {
        Coche c = new Coche();
        c.setPrecioVenta(new BigDecimal("10000"));
        c.setAnioFabricacion(1899);
        
        Exception exception = assertThrows(ErrorDeNegocioException.class, () -> {
            cocheService.publicarCoche(c);
        });

        assertEquals("Año de fabricación inválido", exception.getMessage());
    }

  /**
   * Prueba la validación de un kilometraje negativo.
   */
  @Test
    public void testValidarKilometrajeNegativo() {
        Coche c = new Coche();
        c.setPrecioVenta(new BigDecimal("10000"));
        c.setAnioFabricacion(2020);
        c.setKilometraje(-50);
        
        Exception exception = assertThrows(ErrorDeNegocioException.class, () -> {
            cocheService.publicarCoche(c);
        });

        assertEquals("El kilometraje no puede ser negativo", exception.getMessage());
    }
}
