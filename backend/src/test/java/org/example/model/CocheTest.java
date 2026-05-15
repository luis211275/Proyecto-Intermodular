package org.example.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class CocheTest {

    @Test
    public void testAsignacionAtributosBasicos() {
        Coche coche = new Coche();
        coche.setAnioFabricacion(2020);
        coche.setKilometraje(15000);
        coche.setPrecioVenta(new BigDecimal("15000.50"));
        coche.setEstado("Usado");
        
        assertEquals(2020, coche.getAnioFabricacion(), "El año de fabricación debería ser 2020");
        assertEquals(15000, coche.getKilometraje(), "El kilometraje debería ser 15000");
        assertEquals(new BigDecimal("15000.50"), coche.getPrecioVenta(), "El precio debería coincidir");
        assertEquals("Usado", coche.getEstado(), "El estado debería ser 'Usado'");
    }
}