package com.example.demov3;

import com.example.demov3.Entities.Proveedor;
import com.example.demov3.Repositories.ProveedorRepository;
import com.example.demov3.Services.ProveedorService;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProveedorServiceTests {
    private final ProveedorRepository repository = mock(ProveedorRepository.class);
    private final ProveedorService service = new ProveedorService(repository);

    private Proveedor datos() {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre("F-001");
        proveedor.setEmpresa("Ana");
        proveedor.setEmail("prueba@example.com");
        proveedor.setTelefono("70000000");
        return proveedor;
    }

    @Test
    void crearGeneraUnaEntidadNuevaSinUsarElIdDeEntrada() {
        Proveedor entrada = datos();
        entrada.setId(99L);
        when(repository.save(any(Proveedor.class))).thenAnswer(call -> {
            Proveedor nueva = call.getArgument(0);
            assertNull(nueva.getId());
            nueva.setId(1L);
            return nueva;
        });
        Proveedor creada = service.crear(entrada);
        assertEquals(1L, creada.getId());
        assertEquals("F-001", creada.getNombre());
        assertEquals("Ana", creada.getEmpresa());
        assertEquals("prueba@example.com", creada.getEmail());
        assertEquals("70000000", creada.getTelefono());
    }

    @Test
    void actualizarConservaElIdDeLaRuta() {
        Proveedor existente = datos();
        existente.setId(1L);
        Proveedor entrada = datos();
        entrada.setId(99L);
        entrada.setEmpresa("Luis");
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);
        Proveedor actualizada = service.actualizar(1L, entrada).orElseThrow();
        assertEquals(1L, actualizada.getId());
        assertEquals("Luis", actualizada.getEmpresa());
        verify(repository).save(existente);
    }

    @Test
    void actualizarAusenteNoCreaOtroProveedor() {
        when(repository.findById(8L)).thenReturn(Optional.empty());
        assertTrue(service.actualizar(8L, datos()).isEmpty());
        verify(repository, never()).save(any());
    }

    @Test
    void eliminarSoloBorraCuandoExiste() {
        when(repository.existsById(1L)).thenReturn(true);
        assertTrue(service.eliminar(1L));
        verify(repository).deleteById(1L);
        assertFalse(service.eliminar(8L));
        verify(repository, never()).deleteById(8L);
    }
}

