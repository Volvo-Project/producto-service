package cl.volvo.productoservice.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.List;

import cl.volvo.productoservice.model.Categoria;
import cl.volvo.productoservice.model.Producto;

@DataJpaTest
@DisplayName("PRUEBAS UNITARIAS DEL REPOSITORY DE PRODUCTOS")
public class ProductoRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ProductoRepository productoRepository;

    private Categoria accion;

    @BeforeEach
    void setUp(){
        accion = em.persist(new Categoria(null, "Acción", "acc", true));
        Categoria terror = em.persist(new Categoria(null,"Terror", "hor", true));

        em.persist(new Producto(null, "WFRM-01", "Warframe", 0, 100, null, "/a.jpg", "/b.jpg", "Acción", accion, true));
        em.persist(new Producto(null, "SPPL-03", "Super People", 7990, 100, null, "/a.jpg", "/b.jpg", "Battle Royale", accion, false));
        em.persist(new Producto(null, "TERR-01", "Juego de Terror", 5990, 100, null, "/a.jpg", "/b.jpg", "Terror", terror, true));
    }

    @Test
    @DisplayName("findByActivoTrue: NO DEVUELVE LOS PRODUCTOS DESACTIVADOS")
    void findByActivoTrue_ignoraInactivos(){
        List<Producto> resultado = productoRepository.findByActivoTrue();

        assertEquals(2,resultado.size());
    }

    @Test
    @DisplayName("findByCategoriaIdAndActivoTrue: SOLO LOS ACTIVOS DE CATEGORIA")
    void findByCategoria(){
        List<Producto> resultado = productoRepository.findByCategoriaIdAndActivoTrue(accion.getId());

        assertEquals(1,resultado.size());
        assertEquals("Warframe", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("findByCodigoAndActivoTrue: ENCUENTRA POR CÓDIGO, PERO NO SI ESTÁ DESACTIVADO")
    void findByCodigo(){
        assertTrue(productoRepository.findByCodigoAndActivoTrue("WFRM-01").isPresent());
        assertTrue(productoRepository.findByCodigoAndActivoTrue("SPPL-03").isEmpty());
    }






}
