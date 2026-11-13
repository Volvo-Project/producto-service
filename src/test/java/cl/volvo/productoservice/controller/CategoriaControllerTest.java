package cl.volvo.productoservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import cl.volvo.productoservice.config.SecurityConfig;
import cl.volvo.productoservice.dto.CategoriaRequestDTO;
import cl.volvo.productoservice.dto.CategoriaResponseDTO;
import cl.volvo.productoservice.exception.RecursoNoEncontradoException;
import cl.volvo.productoservice.service.CategoriaService;

@WebMvcTest(CategoriaController.class)
@Import(SecurityConfig.class)
@DisplayName("PRUEBAS UNITARIAS DEL CONTROLLER DE CATEGORÍAS")
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoriaService categoriaService;

    private CategoriaResponseDTO respuesta;
    private CategoriaRequestDTO request;

    @BeforeEach
    void setUp() {
        respuesta = new CategoriaResponseDTO(1L, "Acción", "acc");
        request = new CategoriaRequestDTO("Acción", "acc");
    }

    @Test
    @DisplayName("GET: LISTA LAS CATEGORÍAS (200)")
    void listar() throws Exception {
        when(categoriaService.obtenerTodas()).thenReturn(List.of(respuesta));

        mockMvc.perform(get("/api/v1/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Acción"))
                .andExpect(jsonPath("$[0].filtro").value("acc"));
    }

    @Test
    @DisplayName("GET POR ID: SI NO EXISTE RESPONDE 404")
    void obtenerPorId_noExiste() throws Exception {
        when(categoriaService.obtenerPorId(99L)).thenThrow(new RecursoNoEncontradoException("Categoria", 99L));

        mockMvc.perform(get("/api/v1/categorias/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.ERROR").value("CATEGORIA CON EL ID 99 NO ENCONTRADO"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST: ADMIN CREA LA CATEGORÍA (201)")
    void crear_ok() throws Exception {
        when(categoriaService.crear(any(CategoriaRequestDTO.class))).thenReturn(respuesta);

        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Acción"));
    }

    @Test
    @DisplayName("POST: SIN USUARIO RESPONDE 403 Y NO LLAMA AL SERVICE")
    void crear_sinUsuario() throws Exception {
        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.ERROR").value("NO TIENES PERMISOS PARA REALIZAR ESTA ACCIÓN"));

        verify(categoriaService, never()).crear(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST: SIN FILTRO RESPONDE 400 Y NO LLAMA AL SERVICE")
    void crear_sinFiltro() throws Exception {
        request.setFiltro("");

        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.filtro").value("EL FILTRO ES OBLIGATORIO"));

        verify(categoriaService, never()).crear(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("DELETE: ADMIN DESACTIVA LA CATEGORÍA (200)")
    void eliminar() throws Exception {
        mockMvc.perform(delete("/api/v1/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.MENSAJE").value("CATEGORIA ELIMINADA CORRECTAMENTE"));

        verify(categoriaService).eliminar(1L);
    }

    @Test
    @WithMockUser(roles = "CLIENTE")
    @DisplayName("DELETE: CLIENTE NO PUEDE ELIMINAR (403)")
    void eliminar_comoCliente() throws Exception {
        mockMvc.perform(delete("/api/v1/categorias/1"))
                .andExpect(status().isForbidden());

        verify(categoriaService, never()).eliminar(any());
    }
}
