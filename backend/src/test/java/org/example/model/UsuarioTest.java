package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    public void testCreacionUsuarioVacioYAsignacion() {
        Usuario usuario = new Usuario();
        usuario.setNombres("Juan");
        usuario.setApellidos("Pérez");
        usuario.setEmail("juan.perez@email.com");
        
        assertEquals("Juan", usuario.getNombres(), "El nombre debería ser Juan");
        assertEquals("Pérez", usuario.getApellidos(), "El apellido debería ser Pérez");
        assertEquals("juan.perez@email.com", usuario.getEmail(), "El email debería coincidir");
    }

    @Test
    public void testCreacionUsuarioConConstructor() {
        Usuario usuario = new Usuario(1, "Ana", "García", "ana@email.com", "123456", "123456789", "12345678A");
        
        assertEquals(1, usuario.getIdUsuario(), "El ID debería ser 1");
        assertEquals("Ana", usuario.getNombres(), "El nombre debería ser Ana");
        assertEquals("12345678A", usuario.getDni(), "El DNI debería coincidir");
    }
}