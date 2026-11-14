package cl.volvo.productoservice.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import cl.volvo.productoservice.config.SecurityConfig;
import cl.volvo.productoservice.dto.ProductoRequestDTO;
import cl.volvo.productoservice.dto.ProductoResponseDTO;
import cl.volvo.productoservice.exception.RecursoNoEncontradoException;
import cl.volvo.productoservice.service.ProductoService;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(ProductoController.class)
@Import(SecurityConfig.class)
@DisplayName("PRUEBAS UNITARIAS DEL CONTROLLER DE PRODUCTOS")
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductoService productoService;

    private ProductoResponseDTO respuesta;

    private ProductoRequestDTO request;

    @BeforeEach
    void setUp() {
        respuesta = new ProductoResponseDTO(1L, "WFRM-01", "Warframe", 0, 100, true, "Shooter cooperativo", "/card.jpg", "/detalle.jpg", "Acción, Cooperativo", "Acción", "acc");

        request = new ProductoRequestDTO("WFRM-01", "Warframe", 0, 100, "Shooter cooperativo", "/card.jpg", "/detalle.jpg", "Acción, Cooperativo", 1L);

    }

    @Test
    @DisplayName("GET: LISTA LOS PRODUCTOS (200)")
    void listar() throws Exception {
        when(productoService.obtenerTodos()).thenReturn(List.of(respuesta));

        mockMvc.perform(get("/api/v1/productos")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1)).andExpect(jsonPath("$[0].nombre").value("Warframe")).andExpect(jsonPath("$[0].esGratis").value(true));
    }

    @Test
    @DisplayName("GET POR ID: SI NO EXISTE RETORNA UN 404")
    void obtenerPorId_noExiste() throws Exception {
        when(productoService.obtenerPorId(99L)).thenThrow(new RecursoNoEncontradoException("Producto", 99L));

        mockMvc.perform(get("/api/v1/productos/99")).andExpect(status().isNotFound()).andExpect(jsonPath("$.ERROR").value("PRODUCTO CON EL ID 99 NO ENCONTRADO"));
    }

    @Test
    @DisplayName("OBTENER ID: SI EL ID NO ES UN NUMERO RESPONDE CON UN 400")
    void obtenerPorId_textoEnVezDeNumero() throws Exception {
        mockMvc.perform(get("/api/v1/productos/asdjjas")).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST: CREAR EL PRODUCTO (201)")
    void crear_ok() throws Exception {
        when(productoService.crear(any(ProductoRequestDTO.class))).thenReturn(respuesta);
        mockMvc.perform(post("/api/v1/productos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.codigo").value("WFRM-01"));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST : SIN NOMBRE RESPONDE 400 Y NO LLAMA AL SERVICE")
    void crear_sinNombre() throws Exception {
        request.setNombre("");

        mockMvc.perform(post("/api/v1/productos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.nombre").value("EL NOMBRE ES OBLIGATORIO"));

    }

}
