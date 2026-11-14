package cl.volvo.productoservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import cl.volvo.productoservice.dto.ProductoResponseDTO;
import cl.volvo.productoservice.dto.ProductoRequestDTO;
import cl.volvo.productoservice.exception.RecursoNoEncontradoException;
import cl.volvo.productoservice.model.Categoria;
import cl.volvo.productoservice.model.Producto;
import cl.volvo.productoservice.repository.ProductoRepository;


@ExtendWith(MockitoExtension.class)
@DisplayName("PRUEBAS UNITARIAS DEL SERVICE DE PRODUCTOS")
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepo;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProductoService productoService;

    private Producto warframe;

    @BeforeEach
    void setUp() {
        Categoria accion = new Categoria(1L, "Acción", "acc", true);
        warframe = new Producto(1L, "WFRM-01", "Warframe", 0, 100, "Shooter cooperativo", "/card.jpg", "/detalle.jpg", "Acción, Cooperativo", accion, true);
    }

    @Test
    @DisplayName("OBTENER POR ID: DEVUELVE EL PRODUCTO Y SI isGratis si el precio es 0")
    void obtenerPorId_existe() {
        when(productoRepo.findByIdAndActivoTrue(1L)).thenReturn(Optional.of(warframe));

        ProductoResponseDTO resultado = productoService.obtenerPorId(1L);

        assertEquals("Warframe", resultado.getNombre());
        assertTrue(resultado.isEsGratis());
        assertEquals("Acción", resultado.getCategoria());

    }

    @Test
    @DisplayName("OBTENER POR ID: SI NO EXISTE LANZA EL RESCURSONOTFOUND EXCEPTION")
    void obtenerPorId_noExiste() {
        when(productoRepo.findByIdAndActivoTrue(99L)).thenReturn(Optional.empty());

        RecursoNoEncontradoException exception = assertThrows(RecursoNoEncontradoException.class, () -> productoService.obtenerPorId(99L));
        //super(recurso.toUpperCase() + " CON EL ID " + id + " NO ENCONTRADO");
        assertEquals("PRODUCTO CON EL ID 99 NO ENCONTRADO", exception.getMessage());
    }

    @Test
    @DisplayName("ELIMINAR: BORRADO LÓGICO DENUEVO, NO BORRA LA FILA DE LA BD")
    void eliminar_borradoLogico() {
        when(productoRepo.findByIdAndActivoTrue(1L)).thenReturn(Optional.of(warframe));

        productoService.eliminar(1L);

        assertFalse(warframe.isActivo());
        verify(productoRepo).save(warframe);
        verify(productoRepo, never()).deleteById(1L);
    }

    @Test
    @DisplayName("CREAR: GUARDA EL PRODUCTO CON SU CATEGORÍA")
    void crear_ok() {

        ProductoRequestDTO dto = new ProductoRequestDTO("WFRM-01", "Warframe", 0, 100, "Shooter cooperativo", "/card.jpg", "/detalle.jpg", "Acción, Cooperativo", 1L);

        when(categoriaService.buscarActiva(1L)).thenReturn(warframe.getCategoria());
        when(productoRepo.save(any(Producto.class))).thenAnswer(inv -> {
            Producto p = inv.getArgument(0);
            p.setId(10L);
            return p;
        });

        ProductoResponseDTO resultado = productoService.crear(dto);

        assertEquals(10L, resultado.getId());
        assertEquals("Acción", resultado.getCategoria());
        assertEquals("acc", resultado.getFiltro());

    }

    @Test
    @DisplayName("CREAR: SI LA CATEGORIA NO EXISTE, NO SE GUARDA NADA")
    void crear_categoriaNoExiste(){
        ProductoRequestDTO dto = new ProductoRequestDTO("WFRM-01", "Warframe", 0, 100, null, "/card.jpg", "/detalle.jpg", "Acción, Cooperativo", 99L);

        when(categoriaService.buscarActiva(99L)).thenThrow(new RecursoNoEncontradoException("Categoria", 99L));

        assertThrows(RecursoNoEncontradoException.class, () -> productoService.crear(dto));
        verify(productoRepo, never()).save(any());


    }


    @Test
    @DisplayName("ESGRATIS: SI EL PRECIO ES MAYOR A 0, ISGRATIS -> FALSE")
    void esGratis_conPrecio(){
        warframe.setPrecio(99999999);
        when(productoRepo.findByIdAndActivoTrue(1L)).thenReturn(Optional.of(warframe));

        ProductoResponseDTO resultado = productoService.obtenerPorId(1L);

        assertFalse(resultado.isEsGratis());
    }
}
