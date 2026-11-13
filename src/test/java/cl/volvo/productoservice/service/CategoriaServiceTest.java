package cl.volvo.productoservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import cl.volvo.productoservice.dto.CategoriaRequestDTO;
import cl.volvo.productoservice.dto.CategoriaResponseDTO;
import cl.volvo.productoservice.exception.RecursoNoEncontradoException;
import cl.volvo.productoservice.model.Categoria;
import cl.volvo.productoservice.repository.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("PRUEBAS UNITARIAS DEL SERVICE DE CATEGORÍAS")
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepo;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria accion;

    @BeforeEach
    void setUp() {
        accion = new Categoria(1L, "Acción", "acc", true);
    }

    @Test
    @DisplayName("OBTENER TODAS: DEVUELVE LAS CATEGORÍAS ACTIVAS")
    void obtenerTodas() {
        Categoria terror = new Categoria(2L, "Terror", "hor", true);
        when(categoriaRepo.findByActivoTrue()).thenReturn(List.of(accion, terror));

        List<CategoriaResponseDTO> resultado = categoriaService.obtenerTodas();

        assertEquals(2, resultado.size());
        assertEquals("Acción", resultado.get(0).getNombre());
        assertEquals("hor", resultado.get(1).getFiltro());
    }

    @Test
    @DisplayName("OBTENER POR ID: SI NO EXISTE LANZA RecursoNoEncontradoException")
    void obtenerPorId_noExiste() {
        when(categoriaRepo.findByIdAndActivoTrue(99L)).thenReturn(Optional.empty());

        RecursoNoEncontradoException ex = assertThrows(RecursoNoEncontradoException.class,
                () -> categoriaService.obtenerPorId(99L));

        assertEquals("CATEGORIA CON EL ID 99 NO ENCONTRADO", ex.getMessage());
    }

    @Test
    @DisplayName("CREAR: GUARDA LA CATEGORÍA Y DEVUELVE SU ID")
    void crear_ok() {
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Aventura", "avn");
        when(categoriaRepo.save(any(Categoria.class))).thenAnswer(inv -> {
            Categoria c = inv.getArgument(0);
            c.setId(3L);
            return c;
        });

        CategoriaResponseDTO resultado = categoriaService.crear(dto);

        assertEquals(3L, resultado.getId());
        assertEquals("Aventura", resultado.getNombre());
        assertEquals("avn", resultado.getFiltro());
    }

    @Test
    @DisplayName("ACTUALIZAR: CAMBIA EL NOMBRE Y EL FILTRO")
    void actualizar_ok() {
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Acción y aventura", "aya");
        when(categoriaRepo.findByIdAndActivoTrue(1L)).thenReturn(Optional.of(accion));
        when(categoriaRepo.save(accion)).thenReturn(accion);

        CategoriaResponseDTO resultado = categoriaService.actualizar(1L, dto);

        assertEquals("Acción y aventura", resultado.getNombre());
        assertEquals("aya", resultado.getFiltro());
    }

    @Test
    @DisplayName("ELIMINAR: ES BORRADO LÓGICO, NO BORRA LA FILA")
    void eliminar_borradoLogico() {
        when(categoriaRepo.findByIdAndActivoTrue(1L)).thenReturn(Optional.of(accion));

        categoriaService.eliminar(1L);

        assertFalse(accion.isActivo());
        verify(categoriaRepo).save(accion);
        verify(categoriaRepo, never()).deleteById(any());
    }
}
