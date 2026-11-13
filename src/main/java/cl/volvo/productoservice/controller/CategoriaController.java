package cl.volvo.productoservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import cl.volvo.productoservice.dto.CategoriaRequestDTO;
import cl.volvo.productoservice.dto.CategoriaResponseDTO;
import cl.volvo.productoservice.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "CRUD DE CATEGORÍAS DE JUEGOS")
public class CategoriaController {

    private final CategoriaService categoriaService;


    @GetMapping
    @Operation(summary = "Listar todas las categorías activas")
    public ResponseEntity<List<CategoriaResponseDTO>> listar() {
        log.info("GET /api/v1/categorias - LISTAR TODAS");
        return ResponseEntity.ok(categoriaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una categoria por id")
    public ResponseEntity<CategoriaResponseDTO> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/categorias/{} - BUSCAR POR ID", id);
        return ResponseEntity.ok(categoriaService.obtenerPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear una categoria")
    public ResponseEntity<CategoriaResponseDTO> crear(@Valid @RequestBody CategoriaRequestDTO dto) {
        log.info("POST /api/v1/categorias - CREAR CATEGORIA {}", dto.getNombre());
        return ResponseEntity.status(201).body(categoriaService.crear(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar una categoria")
    public ResponseEntity<CategoriaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO dto) {
        log.info("PUT /api/v1/categorias/{} - ACTUALIZAR CATEGORIA", id);
        return ResponseEntity.ok(categoriaService.actualizar(id, dto));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desactivar una categoria (usando borrado lógico)")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/categorias/{} - ELIMINAR CATEGORIA", id);
        categoriaService.eliminar(id);
        return ResponseEntity.ok(Map.of("MENSAJE", "CATEGORIA ELIMINADA CORRECTAMENTE"));
    }

}
